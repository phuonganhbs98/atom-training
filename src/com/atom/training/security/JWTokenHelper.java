package com.atom.training.security;

import java.security.Key;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import com.atom.training.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTokenHelper {
	private static final long EXPIRATION_LIMIT_IN_MINUTES = 120;
	private static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;

	private static final String SECRET_KEY = "pa-com-atom-training-android-secret-key-test-android-secret-key-test";

	public JWTokenHelper() {
		super();
	}

	public static String createJWT(User user) {
		long currentTimeInMillis = System.currentTimeMillis();
		Date now = new Date(currentTimeInMillis);
		long expirationTimeInMilliSeconds = TimeUnit.MINUTES.toMillis(EXPIRATION_LIMIT_IN_MINUTES);
		Date expirationDate = new Date(currentTimeInMillis + expirationTimeInMilliSeconds);
		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
		Key signingKey = new SecretKeySpec(apiKeySecretBytes, SIGNATURE_ALGORITHM.getJcaName());
		Claims claims = Jwts.claims().setSubject(user.getUserId());
		claims.put("roles", user.getAdmin());
		JwtBuilder builder = Jwts.builder()
				.setClaims(claims).setId(UUID.randomUUID().toString())
				.setIssuedAt(now)
				.setIssuer("PA")
				.setExpiration(expirationDate)
				.signWith(signingKey, SIGNATURE_ALGORITHM);

		return builder.compact();
	}

	/**
	 * Get User from the given token
	 */
	public static User getUserFromToken(String token) {
		final Claims claims = decodeJWT(token);
		User user = new User();
		user.setUserId(claims.getSubject());
		user.setAdmin((Integer) claims.get("roles"));
		return user;
	}

	/**
	 * Check if the token was issued by the server and if it's not expired
	 */
	public static Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	private static Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	private static <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = decodeJWT(token);
		return claimsResolver.apply(claims);
	}

	private static Claims decodeJWT(String jwt) {

		return Jwts.parser()
				.setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
				.parseClaimsJws(jwt)
				.getBody();
	}

}
