package com.iscope2.tabletest.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.iscope2.tabletest.models.AuthUser;
import com.iscope2.tabletest.repositories.AuthUserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	
	@Autowired
	private AuthUserRepository authUserRepository;
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		AuthUser authUser = authUserRepository.findByUsername(userName);
		if (authUser == null) {
			throw new UsernameNotFoundException("User not found");
		}
		return new CustomUserDetails(authUser);
	}
}
