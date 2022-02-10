package com.uob.auth.ocoeadmin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class OcoeAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(OcoeAdminApplication.class, args);
	}
	
	
	//it is for local
	  @Bean 
	  public PasswordEncoder passwordEncoder() { return
	  NoOpPasswordEncoder.getInstance(); }
	 
	
	
	 

}
