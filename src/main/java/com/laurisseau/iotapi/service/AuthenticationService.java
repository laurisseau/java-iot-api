package com.laurisseau.iotapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AuthenticationService {
    private final String clientId;
    private final String poolId;

    @Autowired
    public AuthenticationService(
            @Value("${cloud.aws.credentials.cognito-client-id}") String clientId,
            @Value("${cloud.aws.credentials.cognito-pool-id}") String poolId) {
        this.clientId = clientId;
        this.poolId = poolId;
    }


    public String signUp(String email, String userName,
                         String dataWebsite, String dataLink, String dataRole,
                         String password) {

        CognitoIdentityProviderClient identityProviderClient = CognitoIdentityProviderClient.builder()
                .region(Region.US_EAST_1)
                .credentialsProvider(ProfileCredentialsProvider.create())
                .build();

        AttributeType attributeTypeEmail = AttributeType.builder()
                .name("email")
                .value(email)
                .build();

        AttributeType attributeTypeUserName = AttributeType.builder()
                .name("preferred_username")
                .value(userName)
                .build();

        AttributeType attributeTypeDataWebsite = AttributeType.builder()
                .name("custom:website")
                .value(dataWebsite)
                .build();

        AttributeType attributeTypeDataLink = AttributeType.builder()
                .name("custom:link")
                .value(dataLink)
                .build();

        AttributeType attributeTypeDataRole = AttributeType.builder()
                .name("custom:role")
                .value(dataRole)
                .build();

        List<AttributeType> attrs = new ArrayList<>();

        attrs.add(attributeTypeEmail);
        attrs.add(attributeTypeUserName);
        attrs.add(attributeTypeDataWebsite);
        attrs.add(attributeTypeDataLink);
        attrs.add(attributeTypeDataRole);

        try {

            SignUpRequest signUpRequest = SignUpRequest.builder()
                    .userAttributes(attrs)
                    .username(email)
                    .clientId(clientId)
                    .password(password)
                    .build();

            identityProviderClient.signUp(signUpRequest);
            return "User has been signed up";

        } catch (CognitoIdentityProviderException e) {
            return e.awsErrorDetails().errorMessage();
        }

    }

    public String confirmEmail(String userName, String confirmationCode) {

        CognitoIdentityProviderClient identityProviderClient = CognitoIdentityProviderClient.builder()
                .region(Region.US_EAST_1)
                .credentialsProvider(ProfileCredentialsProvider.create())
                .build();

        ConfirmSignUpRequest req = ConfirmSignUpRequest.builder()
                .clientId(clientId)
                .confirmationCode(confirmationCode)
                .username(userName)
                .build();

        identityProviderClient.confirmSignUp(req);

        return "User " + userName + " sign up confirmed";
    }



    public AdminInitiateAuthResponse login(String userName, String password) {
        try {
            Map<String,String> authParameters = new HashMap<>();
            authParameters.put("USERNAME", userName);
            authParameters.put("PASSWORD", password);

            CognitoIdentityProviderClient identityProviderClient = CognitoIdentityProviderClient.builder()
                    .region(Region.US_EAST_1)
                    .credentialsProvider(ProfileCredentialsProvider.create())
                    .build();

            AdminInitiateAuthRequest authRequest = AdminInitiateAuthRequest.builder()
                    .clientId(clientId)
                    .userPoolId(poolId)
                    .authParameters(authParameters)
                    .authFlow(AuthFlowType.ADMIN_USER_PASSWORD_AUTH)
                    .build();

            AdminInitiateAuthResponse response = identityProviderClient.adminInitiateAuth(authRequest);
            System.out.println("Result Challenge is : " + response.challengeName() );
            return response;

        } catch(CognitoIdentityProviderException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }

        return null;
    }


}
