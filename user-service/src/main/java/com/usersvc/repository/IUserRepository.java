package com.usersvc.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import com.usersvc.models.User;

@Repository
public interface IUserRepository 
extends PagingAndSortingRepository<User, Long>{

		Page<User> findAllByUsernameContains(String username,Pageable pageable);

}
