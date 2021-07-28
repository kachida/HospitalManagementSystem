package com.usersvc.models;



// TODO: Auto-generated Javadoc
/**
 * The Class AuthenticationResponse.
 *
 * @author : Kannappan
 * @version : 1.0
 */
public class AuthenticationResponse {

	/** The jwt. */
	private String jwt;
	
	/**
	 * Instantiates a new authentication response.
	 *
	 * @param jwt the jwt
	 */
	public AuthenticationResponse(String jwt)
	{
		this.jwt=jwt;
	}
	
	/**
	 * Gets the jwt.
	 *
	 * @return the jwt
	 */
	public String getJwt()
	{
		return jwt;
	}
}
