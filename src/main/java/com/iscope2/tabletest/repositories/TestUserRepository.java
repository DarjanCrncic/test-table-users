package com.iscope2.tabletest.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iscope2.tabletest.models.TestUser;

@Repository
public interface TestUserRepository extends JpaRepository<TestUser, Long> {

	Page<TestUser> findByFirstnameAndSurnameIgnoreCaseContaining(String searchName, String searchSurname, Pageable pageable);

	Page<TestUser> findByFirstnameIgnoreCaseContainingOrSurnameIgnoreCaseContaining(String search, String search2, Pageable pageable);

}
