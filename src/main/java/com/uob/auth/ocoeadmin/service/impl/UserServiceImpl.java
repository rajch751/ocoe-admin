package com.uob.auth.ocoeadmin.service.impl;

import static com.uob.auth.ocoeadmin.jwt.utility.OCOEConstants.TOKENEXPIRYTIME;
import static com.uob.auth.ocoeadmin.jwt.utility.OCOEConstants.TOKENREFRESHTIME;

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
import java.time.LocalDateTime;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

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
import com.uob.auth.ocoeadmin.config.TokenProperties;
import com.uob.auth.ocoeadmin.jwt.model.JwtRequest;
import com.uob.auth.ocoeadmin.jwt.model.JwtResponse;
import com.uob.auth.ocoeadmin.jwt.model.RSAKeyDTO;
import com.uob.auth.ocoeadmin.jwt.model.TokenTimeDTO;
import com.uob.auth.ocoeadmin.repository.TblTokenTimeRepository;
import com.uob.auth.ocoeadmin.service.JwtCreation;
import com.uob.auth.ocoeadmin.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	private static Logger log = LoggerFactory.getLogger(JwtCreation.class);

	private static final String YES = "Y";

	private static final String CLIENTONLY = "clientonly";

	private static final String SCOPE = "scp";

	private static final String AUTHTIME = "auth_time";

	@Autowired
	TblTokenTimeRepository tblTokenRepo;

	// @Value("${token.expirymin}")
	@Autowired
	private TokenProperties tokenProps;

	@Autowired
	private ResourceLoader resourceLoader;

	@Override
	public RSAKeyDTO getRSAKey(String jksPath, String password, String alias)
			throws NoSuchAlgorithmException, IOException {
		// TODO Auto-generated method stub

		Set<KeyOperation> keyOperations = new HashSet<>();

		keyOperations.add(KeyOperation.SIGN);

		keyOperations.add(KeyOperation.VERIFY);

		java.security.KeyPair rsaKeyPair = getKeyPair("PKCS12", jksPath, password, alias, password);

		String keyId = createThumbPrint((RSAPublicKey) rsaKeyPair.getPublic());

		System.out.println("KeyId is ---" + keyId);

		RSAKey rsaJWK = new RSAKey(((RSAPublicKey) rsaKeyPair.getPublic()), ((RSAPrivateKey) rsaKeyPair.getPrivate()),

				KeyUse.SIGNATURE, keyOperations, JWSAlgorithm.RS256, keyId, null, null, null, null, null);

		RSAKeyDTO rsadto = new RSAKeyDTO();

		rsadto.setKeyId(keyId);
		rsadto.setRsaJWK(rsaJWK);
		System.out.println("RSAWK---" + rsaJWK.toJSONString());

		return rsadto;

	}

	@Override
	public JwtResponse createTokenjwt(JwtRequest jwtRequest, RSAKeyDTO rsaJWK) {
		// TODO Auto-generated method stub

		try {
			long expirytime;
			LocalDateTime expiryDateTime = null;
			LocalDateTime todaDate = LocalDateTime.now();
			Date issueDateTime = java.sql.Timestamp.valueOf(todaDate);
			Date nbf = java.sql.Timestamp.valueOf(todaDate.minusMinutes(1));
			Date authtime = null;
			String uid = null;
			

			if (jwtRequest.isTokenTimeUpdated()) {
				setTokenProps();
			}
			System.out.println(tokenProps+"tokenProps");
			if (jwtRequest.getExpiryDateTime() == 0) {
				expirytime = tokenProps.getCreateexpiryTime();
				expiryDateTime = todaDate.plusMinutes(expirytime);
				uid = UUID.randomUUID().toString();
				authtime = issueDateTime;
			}

			else {
				expirytime = tokenProps.getRefreshexpiryTime();
				if (expirytime > (System.currentTimeMillis() / 1000))
					log.info("token expired {} ms ", (expirytime * 1000));
				expiryDateTime = LocalDateTime.now().plusMinutes(expirytime);
				authtime = new Date(jwtRequest.getAuthTime() * 1000);
				uid = jwtRequest.getJti();

			}

			// System.out.println(expiryDateTime + "expiryDateTime");

			String token = createClientToken(jwtRequest.getClientId(),
					Collections.singletonList(jwtRequest.getClientId() + "-ms"), uid, expiryDateTime, rsaJWK.getKeyId(),
					rsaJWK.getRsaJWK(), jwtRequest.getUsername(), issueDateTime, authtime, nbf);

			return new JwtResponse(token, tokenProps.getCreateexpiryTime() + "MIN", expiryDateTime);

		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JOSEException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private void setTokenProps() {
		// TODO Auto-generated method stub

		List<TokenTimeDTO> tokenData = tblTokenRepo.listAllTokenName();

		for (TokenTimeDTO tokenTimeDTO : tokenData) {

			if (tokenTimeDTO.getTokenName().equalsIgnoreCase(TOKENEXPIRYTIME)) {
				tokenProps.setCreateexpiryTime(tokenTimeDTO.getTokenDuration());
			} else if (tokenTimeDTO.getTokenName().equalsIgnoreCase(TOKENREFRESHTIME)) {
				tokenProps.setRefreshexpiryTime(tokenTimeDTO.getTokenDuration());
			}
		}
	}

	public String createClientToken(String clientId, List<String> audiences, String jti,

			LocalDateTime expiryDateTime, String keyId, RSAKey rsaJWK, String userName, Date issueDateTime,
			Date authTime, Date nbf)

			throws JOSEException, URISyntaxException, NoSuchAlgorithmException {

		log.info("Creating token for client {} with audience {}, jti {} and expirydatetime as {}", clientId, audiences,

				jti, expiryDateTime, userName);

		/*
		 * Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new
		 * Date(System.currentTimeMillis())) .setExpiration(new
		 * Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
		 * .signWith(SignatureAlgorithm.HS512, secretKey).compact();
		 */

		JWTClaimsSet claimsSet = new JWTClaimsSet.Builder().subject(userName).issuer("ocoe")

				.expirationTime(java.sql.Timestamp.valueOf(expiryDateTime)).notBeforeTime(nbf)

				.audience(clientId).issueTime(issueDateTime).jwtID(jti)

				.claim(SCOPE, "READ_WRITE").claim(CLIENTONLY, YES).claim(AUTHTIME, authTime).build();

		log.info("Fetching a random key to create the token");

		log.info("Random key used is {}", keyId);

		SignedJWT signedJWT = new SignedJWT(new JWSHeader.Builder(JWSAlgorithm.RS256).keyID(keyId).build(), claimsSet);

		signedJWT.sign(getSigner(rsaJWK));

// signedJWT.sign(jwtPki.getSigner(keyId));

		log.info("Returing the created jwt");

		System.out.println(signedJWT.toString());

		return signedJWT.serialize();

	}

	public java.security.KeyPair getKeyPair(String keyStoreType, String keyStorePath, String keyStorePassword,

			String keyAlias, String keyPassword) {

		InputStream inputStream = null;

		KeyStore store;

		try {

			store = KeyStore.getInstance(keyStoreType);

			inputStream = resourceLoader.getResource(keyStorePath).getInputStream();

			// inputStream = new ClassPathResource(keyStorePath).getInputStream();

			store.load(inputStream, keyStorePassword.toCharArray());

			RSAPrivateCrtKey key = (RSAPrivateCrtKey) store.getKey(keyAlias, keyPassword.toCharArray());

			RSAPublicKeySpec spec = new RSAPublicKeySpec(key.getModulus(), key.getPublicExponent());

			PublicKey publicKey = KeyFactory.getInstance("RSA").generatePublic(spec);

			return new java.security.KeyPair(publicKey, key);

		} catch (Exception e) {

			throw new IllegalStateException("Cannot load keys from store: " + keyStorePath, e);

		} finally {

			try {

				if (inputStream != null) {

					inputStream.close();

				}

			} catch (IOException e) {

				log.warn("Cannot close open stream: ", e);

			}

		}

	}

	public static JWSSigner getSigner(RSAKey rsaJWK) throws JOSEException {

		return new RSASSASigner(rsaJWK);

	}

	public String getRandomKey() throws NoSuchAlgorithmException {

		Random random = null;

		try {

			random = SecureRandom.getInstance("NativePRNG");

		} catch (NoSuchAlgorithmException ne) {

			// do nothing

		}

		if (random == null) {

			random = SecureRandom.getInstance("Windows-PRNG");

		}

// int pos = random.nextInt(counter);

//return keyIds.get(pos);

		return null;

	}

	/*
	 * 
	 * public RSAKey getPublicKey(String keyId) { return
	 * 
	 * rsaKeyPairs.get(keyId).toPublicJWK(); }
	 *
	 * 
	 * 
	 * public JWSVerifier getVerifier(String keyId) throws JOSEException { return
	 * 
	 * new RSASSAVerifier(rsaKeyPairs.get(keyId).toPublicJWK()); }
	 * 
	 */

	private String createThumbPrint(RSAPublicKey rsapubkey) throws IOException, NoSuchAlgorithmException {

		byte[] n = rsapubkey.getModulus().toByteArray(); // Java is 2sC bigendian

		byte[] e = rsapubkey.getPublicExponent().toByteArray(); // and so is SSH

		byte[] tag = "ssh-rsa".getBytes(StandardCharsets.UTF_8);

		ByteArrayOutputStream os = new ByteArrayOutputStream();

		DataOutputStream dos = new DataOutputStream(os);

		dos.writeInt(tag.length);

		dos.write(tag);

		dos.writeInt(e.length);

		dos.write(e);

		dos.writeInt(n.length);

		dos.write(n);

		byte[] encoded = os.toByteArray();

		MessageDigest digest = MessageDigest.getInstance("SHA-512");

		byte[] result = digest.digest(encoded);

		return Base64.getEncoder().encodeToString(result);

	}

}
