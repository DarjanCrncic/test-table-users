package com.iscope2.tabletest.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.iscope2.tabletest.models.FilterDTO;
import com.iscope2.tabletest.models.ResultDTO;
import com.iscope2.tabletest.models.TestUser;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TestUserServiceImpl implements TestUserService{
	
	private TestUserRepository testUserRepository;
	public static String searchAllQuery = " (firstname LIKE '%__PLACEHOLDER__%' OR surname LIKE '%__PLACEHOLDER__%' OR email LIKE '%__PLACEHOLDER__%') ";
	GenericHelperService<TestUser> genericHelperService;

	@Override
	public ResultDTO<TestUser> findWherePagination(String search, Pageable pageable) {
		List<TestUser> userList = new ArrayList<TestUser>();
		Page<TestUser> page = testUserRepository.findByFirstnameIgnoreCaseContainingOrSurnameIgnoreCaseContaining(search, search, pageable);
		if (page.hasContent()) {
			userList = page.getContent();
		}
		return new ResultDTO<TestUser>(userList, Math.toIntExact(page.getTotalElements()), page.getTotalPages());
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
	public ResultDTO<TestUser> findAllPagination(Pageable pageable) {
		List<TestUser> userList = new ArrayList<TestUser>();
		Page<TestUser> page = testUserRepository.findAll(pageable);
		if (page.hasContent()) {
			userList = page.getContent();
		}
		return new ResultDTO<TestUser>(userList, Math.toIntExact(page.getTotalElements()), page.getTotalPages());
	}

	@Override
	public List<TestUser> findAll() {
		return testUserRepository.findAll();
	}
	
	@Override 
	public ResultDTO<TestUser> getAllWithFilters(FilterDTO filterDTO) {
		return genericHelperService.executeFilterQuery(filterDTO, TestUser.class, searchAllQuery, "test_user");
	}

}
