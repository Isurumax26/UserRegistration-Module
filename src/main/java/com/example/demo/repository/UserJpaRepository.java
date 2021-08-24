package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.dto.UserDTO;

public interface UserJpaRepository extends JpaRepository<UserDTO, Long> {
	
	UserDTO findByName(String name);

}
