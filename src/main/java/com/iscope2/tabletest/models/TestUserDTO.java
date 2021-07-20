package com.iscope2.tabletest.models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestUserDTO {

	private List<TestUser> data;
	private int total;
	private int pages;

}
