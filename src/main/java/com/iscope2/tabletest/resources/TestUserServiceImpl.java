package com.iscope2.tabletest.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.iscope2.tabletest.models.FilterDTO;
import com.iscope2.tabletest.models.TestUser;
import com.iscope2.tabletest.models.TestUserDTO;
import com.iscope2.tabletest.utils.QueryBuilder;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TestUserServiceImpl implements TestUserService{
	
	private TestUserRepository testUserRepository;
	private JdbcTemplate jdbcTemplate;
	public static String searchAllQuery = " (firstname LIKE '%__PLACEHOLDER__%' OR surname LIKE '%__PLACEHOLDER__%' OR email LIKE '%__PLACEHOLDER__%') ";

	@Override
	public TestUserDTO findWherePagination(String search, Pageable pageable) {
		List<TestUser> userList = new ArrayList<TestUser>();
		Page<TestUser> page = testUserRepository.findByFirstnameIgnoreCaseContainingOrSurnameIgnoreCaseContaining(search, search, pageable);
		if (page.hasContent()) {
			userList = page.getContent();
		}
		return new TestUserDTO(userList, Math.toIntExact(page.getTotalElements()), page.getTotalPages());
	}

	@Override
	public TestUser findById(Long id) {
		return testUserRepository.findById(id).orElse(null);
	}

	@Override
	public TestUser save(TestUser testUser) {
		return testUserRepository.save(testUser);
	}

	@Override
	public void delete(TestUser testUser) {
		testUserRepository.delete(testUser);
	}

	@Override
	public void deleteById(Long id) {
		testUserRepository.deleteById(id);
	}

	@Override
	public TestUserDTO findAllPagination(Pageable pageable) {
		List<TestUser> userList = new ArrayList<TestUser>();
		Page<TestUser> page = testUserRepository.findAll(pageable);
		if (page.hasContent()) {
			userList = page.getContent();
		}
		return new TestUserDTO(userList, Math.toIntExact(page.getTotalElements()), page.getTotalPages());
	}

	@Override
	public List<TestUser> findAll() {
		return testUserRepository.findAll();
	}
	
	@Override 
	public TestUserDTO getAllWithFilters(FilterDTO filterDTO) {
		int perPage = (filterDTO.getPerPage() == 0) ? 10 : filterDTO.getPerPage();
		String query = QueryBuilder.generateQuery("test_user", filterDTO, searchAllQuery, true);	
		List<TestUser> usersList = jdbcTemplate.query(query, new BeanPropertyRowMapper<TestUser>(TestUser.class));
		int count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM (" 
				+ QueryBuilder.generateQuery("test_user", filterDTO, searchAllQuery, false) + ")", Integer.class);

		TestUserDTO testUserDTO = new TestUserDTO();
		testUserDTO.setTotal(count);
		testUserDTO.setPages(count/perPage);
		testUserDTO.setData(usersList);
		
		return testUserDTO;
	}

}
