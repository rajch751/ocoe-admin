package com.uob.auth.ocoeadmin.service;

import java.io.ByteArrayOutputStream;

import java.io.DataOutputStream;

import java.io.IOException;

import java.io.InputStream;

import java.net.URISyntaxException;

import java.nio.charset.StandardCharsets;

import java.security.KeyFactory;

import java.security.KeyStore;

import java.security.MessageDigest;

import java.security.NoSuchAlgorithmException;

import java.security.PublicKey;

import java.security.SecureRandom;

import java.security.interfaces.RSAPrivateCrtKey;

import java.security.interfaces.RSAPrivateKey;

import java.security.interfaces.RSAPublicKey;

import java.security.spec.RSAPublicKeySpec;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

import java.util.Collections;

import java.util.Date;

import java.util.HashSet;

import java.util.List;

import java.util.Random;

import java.util.Set;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import org.springframework.core.io.DefaultResourceLoader;

import org.springframework.core.io.ResourceLoader;

 

import com.nimbusds.jose.JOSEException;

import com.nimbusds.jose.JWSAlgorithm;

import com.nimbusds.jose.JWSHeader;

import com.nimbusds.jose.JWSSigner;

import com.nimbusds.jose.crypto.RSASSASigner;

import com.nimbusds.jose.jwk.KeyOperation;

import com.nimbusds.jose.jwk.KeyUse;

import com.nimbusds.jose.jwk.RSAKey;

import com.nimbusds.jwt.JWTClaimsSet;

import com.nimbusds.jwt.SignedJWT;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

 


public class JwtCreation {
	
	private static Logger log = LoggerFactory.getLogger(JwtCreation.class);

           

 

              public static void main(String[] args)

                                           throws NoSuchAlgorithmException, JOSEException, URISyntaxException, IOException {

                             // TODO Auto-generated method stub
            	  
            	  System.out.println("coming");
            	  
            	 // String exp= "1644498619";
            	  long currentDateTime = 1644498619;
            	     
                  //creating Date from millisecond
                  Date currentDate = new Date(currentDateTime);
                
                  //printing value of Date
                  System.out.println("current Date: " + currentDate);
                
                  DateFormat df = new SimpleDateFormat("dd:MM:yy:HH:mm:ss");
                
                  //formatted value of current Date
                  System.out.println("Milliseconds to Date: " + df.format(currentDate));
                
                  //Converting milliseconds to Date using Calendar
                  
                
                  //copying one Date's value into another Date in Java

                 


 

              }
 

}
