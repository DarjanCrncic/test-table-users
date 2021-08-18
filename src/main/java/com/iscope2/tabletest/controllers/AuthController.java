package com.iscope2.tabletest.controllers;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.iscope2.tabletest.models.AuthUser;
import com.iscope2.tabletest.models.LoginRequest;
import com.iscope2.tabletest.models.LoginResponse;
import com.iscope2.tabletest.models.SignupRequest;
import com.iscope2.tabletest.repositories.AuthUserRepository;
import com.iscope2.tabletest.security.CustomUserDetails;
import com.iscope2.tabletest.security.JwtUtils;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
	
	AuthenticationManager authenticationManager;
	JwtUtils jwtUtils;
	AuthUserRepository authUserRepository;
	PasswordEncoder encoder;

	@PostMapping("/login")
	public LoginResponse authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();		

		return new LoginResponse(userDetails.getUsername(), jwt);
	}

	@PostMapping("/signup")
	@ResponseStatus(HttpStatus.CREATED)
	public String registerUser(@RequestBody SignupRequest signUpRequest) {
		if (authUserRepository.findByUsername(signUpRequest.getUsername()) != null) {
			return "Username alredy exists...";
		}

		// Create new user's account
		AuthUser user = new AuthUser(signUpRequest.getUsername(), encoder.encode(signUpRequest.getPassword()));

		authUserRepository.save(user);

		return "User registered successfully!";
	}
}
