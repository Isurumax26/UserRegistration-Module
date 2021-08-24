package com.example.demo.client;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.example.demo.dto.UserDTO;

public class UserRegistrationClient {
	
	private static RestTemplate restTemplate = new RestTemplate();
	private static final String USER_REGISTRATION_BASE_URL = "http://localhost:8080/api/user";
	
	public UserDTO[] getAllUsers() {
		return restTemplate.getForObject(USER_REGISTRATION_BASE_URL, UserDTO[].class);
	}
	
	public UserDTO getUserById(final Long userId) {
		return restTemplate.getForObject(USER_REGISTRATION_BASE_URL + "/{id}", UserDTO.class, userId);
	}
	
	public UserDTO createUser(final UserDTO user) {
		return restTemplate.postForObject(USER_REGISTRATION_BASE_URL, user, UserDTO.class);
	}
	
	public void updateUser(final Long userId, final UserDTO user) {
		restTemplate.put(USER_REGISTRATION_BASE_URL + "/{id}", user, userId);
	}
	
	public void deleteUser(final long userId) {
		restTemplate.delete(USER_REGISTRATION_BASE_URL + "/{id}", userId);
	}
	
	//Excahnge API
	public ResponseEntity<UserDTO> getUserByIdUsingExchangeAPI(final Long userId){
		HttpEntity<UserDTO> httpEntity = new HttpEntity<>(new UserDTO());
		return restTemplate.exchange(USER_REGISTRATION_BASE_URL + "/{id}", HttpMethod.GET, httpEntity, UserDTO.class, userId);
	}
	
	public static void main(String[] args) {
		UserRegistrationClient userRegistrationClient = new UserRegistrationClient();
		UserDTO user = userRegistrationClient.getUserById(1L);
		System.out.println("User-ID" + user.getId() + "User-Name" + user.getName());
		
		UserDTO user1 = new UserDTO();
		user.setName("Isuru");
		user.setAddress("45, nnme streat");
		user.setEmail("iii@gmail.com");
		
		UserDTO newUser = userRegistrationClient.createUser(user1);
		System.out.println(newUser.getId());
		
		UserDTO user2 = userRegistrationClient.getUserById(1L);
		System.out.println("old user name:" + user2.getName());
		
		user2.setName("eeeer");
		userRegistrationClient.updateUser(1L, user2);
		
		
		
		
	}
	
	

}
