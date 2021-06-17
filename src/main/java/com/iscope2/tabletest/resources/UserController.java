package com.iscope2.tabletest.resources;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
	
	private TestUserService testUserService;
	
	public UserController(TestUserService testUserService) {
		super();
		this.testUserService = testUserService;
	}
	
	@GetMapping("/users/{id}")
	public TestUser getUserById(@PathVariable Long id) {
		return testUserService.findById(id);
	}
	
	@GetMapping("/users")
	public TestUserDTO getAllUsersPaginationSort(@RequestParam Optional<Integer> page, @RequestParam Optional<Integer> per_page,
			@RequestParam(defaultValue = "id") String order, @RequestParam(defaultValue = "ASC") String orderby) {
		
		Direction direction = (orderby.equals("ASC")) ? Sort.Direction.ASC : Sort.Direction.DESC;
		Pageable pageable;
		
		if (page.isPresent() && per_page.isPresent()) {
			//System.out.println(order + " " + direction.toString());
			pageable = PageRequest.of(page.get(), per_page.get(), Sort.by(direction, order));
		} else {
			List<TestUser> list = testUserService.findAll();
			return new TestUserDTO(list, list.size(), 1);
		}
		
		return testUserService.findAllPagination(pageable);
	}
	
	@PutMapping("/users/{id}")
	public TestUser ediTestUser(@RequestBody TestUser testUser, @PathVariable Long id) {
		TestUser oldTestUser = testUserService.findById(id);
		oldTestUser.setFirstname(testUser.getFirstname());
		oldTestUser.setSurname(testUser.getSurname());
		oldTestUser.setEmail(testUser.getEmail());
		oldTestUser.setBirthdate(testUser.getBirthdate());
		oldTestUser.setPhone(testUser.getPhone());
		return testUserService.save(oldTestUser);
	}
	
	@PostMapping("/users")
	public TestUser createTestUser(@RequestBody TestUser testUser) {
		return testUserService.save(testUser);
	}
	
	@DeleteMapping("/users/{id}")
	public void deleteUserById(@PathVariable Long id) {
		testUserService.deleteById(id);
	}
	
	@DeleteMapping("/users")
	public void deleteUser(@RequestBody TestUser testUser) {
		testUserService.delete(testUser);
	}
	
}

