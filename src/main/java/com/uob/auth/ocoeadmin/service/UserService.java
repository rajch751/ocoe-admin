package com.uob.auth.ocoeadmin.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nimbusds.jose.jwk.RSAKey;
import com.uob.auth.ocoeadmin.jwt.model.JwtRequest;
import com.uob.auth.ocoeadmin.jwt.model.JwtResponse;
import com.uob.auth.ocoeadmin.jwt.model.RSAKeyDTO;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;


public interface UserService  {

	
	public RSAKeyDTO getRSAKey(String jksPath,String password,String alias) throws NoSuchAlgorithmException, IOException;
	
	
	public JwtResponse createTokenjwt(JwtRequest jwtRequest,RSAKeyDTO rsaJWK);
}
