package com.iscope2.tabletest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
public class TableTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(TableTestApplication.class, args);
	}

}
