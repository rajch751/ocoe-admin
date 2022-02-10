package com.uob.auth.ocoeadmin.jwt.model;

import com.nimbusds.jose.jwk.RSAKey;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RSAKeyDTO {
	
	private RSAKey rsaJWK;
	
	private String keyId;

	
	
	

}
