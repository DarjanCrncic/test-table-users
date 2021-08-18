package com.iscope2.tabletest.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.sun.istack.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class AuthUser {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	@NotNull
	public String username;
	
	@NotNull
	public String password;

	public AuthUser(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	
	
}
