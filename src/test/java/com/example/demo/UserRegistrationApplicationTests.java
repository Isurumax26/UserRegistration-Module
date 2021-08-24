package com.example.demo;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import com.example.demo.controller.UserRegistrationController;
import com.example.demo.dto.UserDTO;
import com.example.demo.repository.UserJpaRepository;

import junit.framework.Assert;

@SpringBootTest(classes = UserRegistrationApplication.class,
				webEnvironment = WebEnvironment.RANDOM_PORT)
class UserRegistrationApplicationTests {
	
	@Spy
	private UserRegistrationController userRegRestController;
	
	@Mock
	private UserJpaRepository userJpaRepository;
	
	@Before
	public void setUp() {
		userRegRestController = new UserRegistrationController();
		ReflectionTestUtils.setField(userRegRestController, "userJpaRepository", userJpaRepository);		
	}
	
	// class is treated as a POJO, doesn't test the request mapping validation

	@Test
	public void testListAllUsers() {
		List<UserDTO> userList = new ArrayList<UserDTO>();
		userList.add(new UserDTO());
		// userJpa reposiotry mock's behaviour
		when(this.userJpaRepository.findAll()).thenReturn(userList);
		
		ResponseEntity<List<UserDTO>> responseEntity = this.userRegRestController.listAllUsers();
		
		Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		Assert.assertEquals(1, responseEntity.getBody().size());
		
	}
	
	@After
	public void teardown() {
		userRegRestController = null;
	}

}
