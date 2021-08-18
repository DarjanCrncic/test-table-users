package com.iscope2.tabletest.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.iscope2.tabletest.models.AuthUser;

public class CustomUserDetails implements UserDetails{

	private static final long serialVersionUID = 6539177333720949995L;

	private AuthUser authUser;
	
	public CustomUserDetails(AuthUser authUser) {
		this.authUser = authUser;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public String getPassword() {
		return authUser.getPassword();
	}

	@Override
	public String getUsername() {
		return authUser.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
