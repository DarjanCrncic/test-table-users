package com.iscope2.tabletest.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TestUserServiceImpl implements TestUserService{
	
	private TestUserRepository testUserRepository;

	public TestUserServiceImpl(TestUserRepository testUserRepository) {
		super();
		this.testUserRepository = testUserRepository;
	}

	@Override
	public List<TestUser> findAll() {
		return testUserRepository.findAll();
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

}
