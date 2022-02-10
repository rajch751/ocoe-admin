package com.uob.auth.ocoeadmin.jwt.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize.Inclusion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonSerialize(include=Inclusion.NON_NULL)
public class JwtResponse {

	private String jwtToken;
	private String expiresDuration;
	private LocalDateTime expiryTime;

	private String errorMsg;

	public JwtResponse(String jwtToken, String expiresDuration, LocalDateTime expiryTime) {
		super();
		this.jwtToken = jwtToken;
		this.expiresDuration = expiresDuration;
		this.expiryTime = expiryTime;
	}

	public JwtResponse(String errorMsg) {
		super();
		this.errorMsg = errorMsg;
	}

}
