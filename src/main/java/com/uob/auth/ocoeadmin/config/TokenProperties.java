package com.uob.auth.ocoeadmin.config;

import java.io.Serializable;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "token")
@Data
public class TokenProperties implements Serializable{
	
	private long createexpiryTime;
	private long refreshexpiryTime;
	

}
