package com.iscope2.tabletest.resources;

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

import com.iscope2.tabletest.models.FilterDTO;
import com.iscope2.tabletest.models.TestUser;
import com.iscope2.tabletest.models.TestUserDTO;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class UserController {

	private TestUserService testUserService;

	@GetMapping("/users/{id}")
	public TestUser getUserById(@PathVariable Long id) {
		return testUserService.findById(id);
	}

	@GetMapping("/users")
	public TestUserDTO getAllUsersPaginationSort(@RequestParam Optional<Integer> page, @RequestParam Optional<Integer> per_page, @RequestParam(defaultValue = "ASC") String order, @RequestParam(defaultValue = "id") String order_by, @RequestParam Optional<String> search) {

		Pageable pageable = generatePageable(page, per_page, order, order_by);
		TestUserDTO testUserDTO;

		if (search.isPresent()) {
			testUserDTO = testUserService.findWherePagination(search.get(), pageable);
		} else {
			testUserDTO = testUserService.findAllPagination(pageable);
		}

		return testUserDTO;
	}

	@PostMapping("/users/filter")
	public TestUserDTO getAllUsersPaginationSortFilter(@RequestBody FilterDTO filterDTO) {
		return testUserService.getAllWithFilters(filterDTO);
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

	private Pageable generatePageable(Optional<Integer> page, Optional<Integer> per_page, String order, String order_by) {
		Direction direction = (order.equals("ASC")) ? Sort.Direction.ASC : Sort.Direction.DESC;
		Pageable pageable = null;

		if (page.isPresent() && per_page.isPresent()) {
			pageable = PageRequest.of(page.get(), per_page.get(), Sort.by(direction, order_by));
		} else {
			pageable = Pageable.unpaged();
		}
		return pageable;
	}

	private Pageable generatePageableDefault(String page, String per_page, String order, String order_by) {
		Direction direction = (order.equals("ASC")) ? Sort.Direction.ASC : Sort.Direction.DESC;
		Pageable pageable = PageRequest.of(Integer.parseInt(page), Integer.parseInt(per_page), Sort.by(direction, order_by));
		return pageable;
	}

}
