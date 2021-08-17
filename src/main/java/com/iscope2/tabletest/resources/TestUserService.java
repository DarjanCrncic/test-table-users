package com.iscope2.tabletest.resources;

import org.springframework.data.domain.Pageable;

import com.iscope2.tabletest.models.FilterDTO;
import com.iscope2.tabletest.models.ResultDTO;
import com.iscope2.tabletest.models.TestUser;

public interface TestUserService extends CrudService<TestUser, Long>{

	ResultDTO<TestUser> findAllPagination(Pageable pageable);

	ResultDTO<TestUser> findWherePagination(String search, Pageable pageable);

	ResultDTO<TestUser> getAllWithFilters(FilterDTO filterDTO);

}
