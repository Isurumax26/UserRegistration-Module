package com.example.demo.client;



import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

public class UserRegistrationClientBasicAuth {
	
	private static final String securityUserName = "admin";
	private static final String securityPassword = "password";
	
	private static final String USER_REGISTRATION_BASE_URL = "http://localhost:8080/api/user/";
	
	private static RestTemplate restTemplate = new RestTemplate();
	
	public void deleteUserById(Long userId) {
		String userCredentials = securityUserName + ":" + securityPassword;
		byte[] base64UserCredentialData = Base64.encodeBase64(userCredentials.getBytes());
		
		HttpHeaders authenticationHeaders = new HttpHeaders();
		authenticationHeaders.set("Authorization", "Basic" + new String(base64UserCredentialData));
		
		HttpEntity<Void> httpEntity  = new HttpEntity<>(authenticationHeaders);
		// RestTemplate's exchange method
		restTemplate.exchange(USER_REGISTRATION_BASE_URL + "/{id}", HttpMethod.DELETE, 
				httpEntity, Void.class, userId);
	}
	

}
