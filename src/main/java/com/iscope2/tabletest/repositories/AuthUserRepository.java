package com.iscope2.tabletest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iscope2.tabletest.models.AuthUser;

public interface AuthUserRepository extends JpaRepository<AuthUser, Long>{

	public AuthUser findByUsername(String username);
}
