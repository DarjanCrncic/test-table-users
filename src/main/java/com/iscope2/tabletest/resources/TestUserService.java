package com.iscope2.tabletest.resources;

import org.springframework.data.domain.Pageable;

import com.iscope2.tabletest.models.FilterDTO;
import com.iscope2.tabletest.models.TestUser;
import com.iscope2.tabletest.models.TestUserDTO;

public interface TestUserService extends CrudService<TestUser, Long>{

	TestUserDTO findAllPagination(Pageable pageable);

	TestUserDTO findWherePagination(String search, Pageable pageable);

	TestUserDTO getAllWithFilters(FilterDTO filterDTO);

}
