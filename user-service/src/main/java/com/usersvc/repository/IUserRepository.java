package com.usersvc.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import com.usersvc.models.User;

@Repository
@EnableJpaAuditing
//@JaversSpringDataAuditable
public interface IUserRepository 
extends PagingAndSortingRepository<User, Long>, JpaRepository<User, Long>{

		Page<User> findAllByUsernameContains(String username,Pageable pageable);

}
