package com.usersvc.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.usersvc.models.YamlProperties;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


// TODO: Auto-generated Javadoc
/**
 * The Class JwtUtil.
 *
 * @author : Kannappan
 * @version : 1.0
 */
@Service
public class JwtUtil {

	/** The yaml props. */
	@Autowired
	private final YamlProperties yamlProps;

	/**
	 * Instantiates a new jwt util.
	 *
	 * @param yamlProps the yaml props
	 */
	public JwtUtil(YamlProperties yamlProps) {
		this.yamlProps = yamlProps;
	}

	/**
	 * Extract username.
	 *
	 * @param token the token
	 * @return the string
	 */
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	/**
	 * Extract expiration.
	 *
	 * @param token the token
	 * @return the date
	 */
	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	/**
	 * Extract claim.
	 *
	 * @param <T> the generic type
	 * @param token the token
	 * @param claimsResolver the claims resolver
	 * @return the t
	 */
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	/**
	 * Extract all claims.
	 *
	 * @param token the token
	 * @return the claims
	 */
	private Claims extractAllClaims(String token) {
		return Jwts.parser().setSigningKey(yamlProps.getSecret_key()).parseClaimsJws(token).getBody();
	}

	/**
	 * Checks if is token expired.
	 *
	 * @param token the token
	 * @return the boolean
	 */
	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	/**
	 * Generate token.
	 *
	 * @param userDetails the user details
	 * @return the string
	 */
	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<String, Object>();
		return createToken(claims, userDetails.getUsername());
	}

	/**
	 * Creates the token.
	 *
	 * @param claims the claims
	 * @param subject the subject
	 * @return the string
	 */
	private String createToken(Map<String, Object> claims, String subject) {
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // Set as 1 hour token expiration
																						// window
				.signWith(SignatureAlgorithm.HS256, yamlProps.getSecret_key()).compact();

	}

	/**
	 * Validate token.
	 *
	 * @param token the token
	 * @param userDetails the user details
	 * @return the boolean
	 */
	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

}
