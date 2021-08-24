package com.example.demo.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.demo.dto.UserInfo;
import com.example.demo.repository.UserInfoJpaRepository;
import com.example.demo.repository.UserJpaRepository;

public class UserInfoDetailsService implements UserDetailsService {
	
	@Autowired
	private UserInfoJpaRepository userInfoJpaRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserInfo user = userInfoJpaRepository.findByUsername(username);
		if(user == null) {
			throw new UsernameNotFoundException("User not found with username" + username);
		}
		return new User(user.getUsername(), user.getPassword(),getAuthorities(user));
		
	}
	
	private Collection<? extends GrantedAuthority> getAuthorities(UserInfo user) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities = AuthorityUtils.createAuthorityList(user.getRole());
		return authorities;
	}
	
	

}

	
