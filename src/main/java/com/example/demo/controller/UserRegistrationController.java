package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Exception.CustomErrorType;
import com.example.demo.dto.UserDTO;
import com.example.demo.repository.UserJpaRepository;


@RestController
@RequestMapping("/api/user")
public class UserRegistrationController {
	
	public static final Logger logger = LoggerFactory.getLogger(UserRegistrationController.class);
	private UserJpaRepository userJpaRepository;
	
	@Autowired
	public void setUserJpaRepository(UserJpaRepository userJpaRepository) {
		this.userJpaRepository = userJpaRepository;
	}
	
	@GetMapping("/")
	public ResponseEntity<List<UserDTO>> listAllUsers(){
		List<UserDTO> users = userJpaRepository.findAll();
		if(users.isEmpty()) {
			return new ResponseEntity<List<UserDTO>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<UserDTO>>(users, HttpStatus.OK);
	}
	
	@PostMapping(value = "/",consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserDTO> createUser(@Valid @RequestBody final UserDTO user){
		if(userJpaRepository.findById(user.getId()) != null) {
			return new ResponseEntity<UserDTO>(new CustomErrorType("Unable to create new user."
					+ "A User with name " + user.getName() + "already exist"), HttpStatus.CONFLICT);
		}
		userJpaRepository.save(user);
		
		// now the response will contain created data(user)
		return new ResponseEntity<UserDTO>(user,HttpStatus.CREATED);	
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UserDTO> getUserById(@PathVariable("id") final Long id){
		UserDTO user = userJpaRepository.findById(id).get();
		if (user==null) {
			return new ResponseEntity<UserDTO>(new CustomErrorType("user with id" + id + "not found"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<UserDTO>(user, HttpStatus.OK);	
	}
	
	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserDTO> updateUser(@PathVariable("id") final Long id, @RequestBody UserDTO user){
		
		UserDTO currentUser = userJpaRepository.findById(id).get();
		if (currentUser==null) {
			return new ResponseEntity<UserDTO>(new CustomErrorType("unable to update.user with id" + id + "not found"), HttpStatus.NOT_FOUND);
		}
		
		currentUser.setAddress(user.getAddress());
		currentUser.setName(user.getName());
		currentUser.setEmail(user.getEmail());
		
		userJpaRepository.saveAndFlush(currentUser);
		return new  ResponseEntity<UserDTO>(currentUser, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<UserDTO> deleteUser(@PathVariable("id") final Long id){
		if(userJpaRepository.findById(id) == null) {
			return new ResponseEntity<UserDTO>(new CustomErrorType("unable to delete.user with id" + id + "not found"), HttpStatus.NOT_FOUND);
	
		}
		userJpaRepository.deleteById(id);
		
		return new ResponseEntity<UserDTO>(HttpStatus.NO_CONTENT);
	}
}
