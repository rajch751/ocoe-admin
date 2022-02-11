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

	@JsonAlias(value = "sub")
	private String username;
    
    private String password;
    
    @JsonAlias(value = "aud")
    private String clientId;
    
    @JsonAlias(value = "exp")
    private long expiryDateTime;
    
    
    @JsonAlias(value = "iat")
    private long issueDateTime;
    
   
    private String jti;
    
    
    @JsonAlias(value = "auth_time")
    private long authTime;
    
    
    private boolean tokenTimeUpdated;
    
	
	
    
    
}
