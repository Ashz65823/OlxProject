package com.zensar.olx.DB;

import java.util.HashMap;
import java.util.Map;

public class TokenStorage {

	private static Map<String, String> tokenCache;
	static {
		tokenCache = new HashMap<>();
	}

	// This method is responsible for storing token in cache on server
	// Both token-key and token-value is token itself
	public static void storeToken(String key, String token) {
		tokenCache.put(key, token);
	}

	// This method is responsible for removing token from server Cache
	public static String removeToken(String key) {
		System.out.println(tokenCache.get(key));
		return tokenCache.remove(key);
	}

//This method is responsile for getting from cache
	// This is written to check if token is present in cache or not;
	public static String getCache(String key) {
		System.out.println("Storing token");
		return tokenCache.get(key);
	}

}
