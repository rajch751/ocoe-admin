package com.uob.auth.ocoeadmin.jwt.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtRequest {

	@JsonAlias(value = "iss")
	private String username;
    
    private String password;
    
    @JsonAlias(value = "sub")
    private String clientId;
    
    @JsonAlias(value = "exp")
    private long expiryDateTime;
    
    
    @JsonAlias(value = "iat")
    private long issueDateTime;
    
   
    private String jti;
    
	
	
    
    
}
