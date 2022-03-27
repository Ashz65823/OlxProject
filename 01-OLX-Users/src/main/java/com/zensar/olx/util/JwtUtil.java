package com.zensar.olx.util;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;//built in class
import com.auth0.jwt.algorithms.Algorithm;

@Component
public class JwtUtil {
private long expirationTime=60000*10;
//Secrete key is very sensitive should be complex and should not be share by anyone
private final String SECRETE_Key="zensar@12345";
	public String generateToken(String username)
	{
		//TODO generate token
		return JWT.create()
				.withClaim("username", username)
				.withExpiresAt(new Date(System.currentTimeMillis()+expirationTime))
				.sign(Algorithm.HMAC512(SECRETE_Key));//-->signature
	}
	public String validateToken(String token)
	{
		//TODO we need to validate token
		return 	JWT.require(Algorithm.HMAC512(SECRETE_Key))
				.build().verify(token)
				.getPayload(); 
	}
}
