package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.dto.UserInfo;

public interface UserInfoJpaRepository extends JpaRepository<UserInfo, Long> {
	
	public UserInfo findByUsername(String username);

}
