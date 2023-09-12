package com.laurisseau.iotapi.controller;
import com.laurisseau.iotapi.model.User;
import com.laurisseau.iotapi.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class AuthenticationController {

    @Autowired
    private AuthenticationService service;

    @PostMapping("/signUp")
    public ResponseEntity<String> signUp(@RequestBody User user) {

        String email = user.getEmail();
        String userName = user.getUserName();
        String dataWebsite = "www.website";
        String dataLink = "www.link/:jwt";
        String dataRole = "user";
        String password = user.getPassword();

        return new ResponseEntity<>(service.signUp(email,
                userName, dataWebsite, dataLink, dataRole, password),
                HttpStatus.OK);
    }

}
