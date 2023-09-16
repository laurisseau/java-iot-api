package com.laurisseau.iotapi.controller;
import com.laurisseau.iotapi.model.User;
import com.laurisseau.iotapi.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.laurisseau.iotapi.util.JwtUtil;
import org.springframework.web.bind.annotation.*;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminInitiateAuthResponse;

@RestController
@RequestMapping("/user")
public class AuthenticationController {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationService service;

    @PostMapping("/signUp")
    public ResponseEntity<String> signUp(@RequestBody User user) {

        String email = user.getEmail();
        String userName = user.getUserName();
        String dataWebsite = "www.website";
        String dataLink = "www.link/" + jwtUtil.generateToken(email);
        String dataRole = "user";
        String password = user.getPassword();

        return new ResponseEntity<>(service.signUp(email,
                userName, dataWebsite, dataLink, dataRole, password),
                HttpStatus.OK);

    }

    @PostMapping("/confirmEmail/{jwt}")
    public String confirmEmail(@PathVariable String jwt, @RequestBody User user) {
        String decodedEmail = jwtUtil.decodeEmail(jwt);
        String confirmationCode = user.getConfirmationCode();
        return service.confirmEmail(decodedEmail, confirmationCode);
    }

    @PostMapping("/login")
    public AdminInitiateAuthResponse login(@RequestBody User user) {
        String userName = user.getUserName();
        String password = user.getPassword();

        return service.login(userName, password);
    }


}
