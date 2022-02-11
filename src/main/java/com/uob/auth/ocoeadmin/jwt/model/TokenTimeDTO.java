package com.uob.auth.ocoeadmin.jwt.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@AllArgsConstructor
@NoArgsConstructor
public class TokenTimeDTO {
	
	
	private String tokenName;
	private Long  tokenDuration;
	public TokenTimeDTO(String tokenName, Long tokenDuration) {
		super();
		
		this.tokenName = tokenName;
		this.tokenDuration = tokenDuration;
	}
	
	
	
	

}
