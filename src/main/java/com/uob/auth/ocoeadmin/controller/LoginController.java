package com.uob.auth.ocoeadmin.controller;

import java.util.Base64;

import javax.servlet.http.HttpServletRequest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uob.auth.ocoeadmin.jwt.model.JwtRequest;
import com.uob.auth.ocoeadmin.jwt.model.JwtResponse;
import com.uob.auth.ocoeadmin.jwt.model.RSAKeyDTO;
import com.uob.auth.ocoeadmin.jwt.utility.JWTUtility;
import com.uob.auth.ocoeadmin.service.UserService;

@RestController
@RequestMapping(value = { "/v1/user/" })
public class LoginController {

	@Autowired
	private JWTUtility jwtUtility;

	@Value("${user.jkspath}")
	private String userJksPath;

	@Value("${user.jkspwd}")
	private String jkspassword;

	@Value("${user.jksalias}")
	private String alias;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService userService;

	@Autowired
	private ObjectMapper mapper;

	@GetMapping("/")
	public String home() {
		return "Welcome to uob";
	}

	@PostMapping("/authenticate")
	public JwtResponse authenticate(@RequestBody JwtRequest jwtRequest) throws Exception {

		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword()));
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}

		// UserDetails userDetails=new User(jwtRequest.getUsername(),null,null);

		// final User userDetails =
		// userService.loadUserByUsername(jwtRequest.getUsername());

		/*
		 * final UserDetails userDetails =
		 * userService.loadUserByUsername(jwtRequest.getUsername());
		 */

		final String token = jwtUtility.generateToken(jwtRequest.getUsername());

		System.out.println("token" + token);

		return null;
	}

	@PostMapping("/rsakey")
	public RSAKeyDTO rsakey() throws Exception {

		return userService.getRSAKey(userJksPath, jkspassword, alias);

		// System.out.println("token"+token);

		// return new JwtResponse(token);
	}

	@PostMapping("/token/create")
	public JwtResponse createToken(@RequestBody JwtRequest jwtRequest) throws Exception {

		try {

			if (StringUtils.isEmpty(jwtRequest.getUsername()) || StringUtils.isEmpty(jwtRequest.getClientId())) {

				return new JwtResponse(
						"user Name and client id are mandatory for create new token..if you are not sending bearer token for refresh..need to give User name and client id and expirationtime");
			}
			/* LDAP one */
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword()));
			/* end ldap */
			RSAKeyDTO rsaKey = rsakey();
			JwtResponse jwtResponse = userService.createTokenjwt(jwtRequest, rsaKey);

			return jwtResponse;
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}

		// UserDetails userDetails=new User(jwtRequest.getUsername(),null,null);

		// final User userDetails =
		// userService.loadUserByUsername(jwtRequest.getUsername());

		/*
		 * final UserDetails userDetails =
		 * userService.loadUserByUsername(jwtRequest.getUsername());
		 */

	}

	@PostMapping("/token/refresh")
	public JwtResponse refreshToken(@RequestBody(required = false) JwtRequest jwtRequest, HttpServletRequest request)
			throws Exception {

		String accessToken = request.getHeader("Authorization");

		if (null != accessToken && accessToken.startsWith("Bearer ")) {
			String refreshToken = accessToken.substring(7);
			// userName = jwtUtility.getUsernameFromToken(token);
			byte[] bytesEncoded = Base64.getDecoder()
					.decode(((accessToken.split("\\.")[1]).replace('-', '+').replace('_', '/')).getBytes());
			// Map<String, String> accountArr;

			// accountArr = mapper.readValue((new String(bytesEncoded)), Map.class);

			JwtRequest jwtreq = mapper.readValue((new String(bytesEncoded)), JwtRequest.class);
			RSAKeyDTO rsaKey = rsakey();

			// Date d=jwtUtility.getExpirationDateFromToken(refreshToken);
			// System.out.println("d"+d);
			JwtResponse jwtResponse = userService.createTokenjwt(jwtreq, rsaKey);

			return jwtResponse;

		} else {
			RSAKeyDTO rsaKey = rsakey();

			JwtResponse jwtResponse = userService.createTokenjwt(jwtRequest, rsaKey);
			return jwtResponse;
		}
		// return null;

	}

}
