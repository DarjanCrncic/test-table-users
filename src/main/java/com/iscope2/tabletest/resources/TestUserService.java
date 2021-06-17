package com.iscope2.tabletest.resources;

import org.springframework.data.domain.Pageable;

public interface TestUserService extends CrudService<TestUser, Long>{

	TestUserDTO findAllPagination(Pageable pageable);

}
