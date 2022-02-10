package com.uob.auth.ocoeadmin.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.uob.auth.ocoeadmin.jwt.model.JwtRequest;
import com.uob.auth.ocoeadmin.jwt.model.JwtResponse;
import com.uob.auth.ocoeadmin.jwt.utility.JWTUtility;
import com.uob.auth.ocoeadmin.service.UserService;

@RestController
public class LoginController {

    @Autowired
    private JWTUtility jwtUtility;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;
    
    
    

    @GetMapping("/")
    public String home() {
        return "Welcome to uob";
    }

    @PostMapping("/authenticate")
    public JwtResponse authenticate(@RequestBody JwtRequest jwtRequest) throws Exception{

        try {
        	Authentication authentication=  authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            jwtRequest.getUsername(),
                            jwtRequest.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
        
        //UserDetails userDetails=new User(jwtRequest.getUsername(),null,null);
        
        //final User userDetails = userService.loadUserByUsername(jwtRequest.getUsername());

		/*
		 * final UserDetails userDetails =
		 * userService.loadUserByUsername(jwtRequest.getUsername());
		 */

        final String token =
                jwtUtility.generateToken(jwtRequest.getUsername());
        
        System.out.println("token"+token);

        return  new JwtResponse(token);
    }
}
