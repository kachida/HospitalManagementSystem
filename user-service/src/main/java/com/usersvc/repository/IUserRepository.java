package com.usersvc.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import com.usersvc.models.User;


// TODO: Auto-generated Javadoc
/**
 * The Interface IUserRepository.
 *
 * @author : Kannappan
 * @version : 1.0
 */
@Repository
@EnableJpaAuditing
//@JaversSpringDataAuditable
public interface IUserRepository 
extends PagingAndSortingRepository<User, Long>, JpaRepository<User, Long>{

		/**
		 * Find all by username contains.
		 *
		 * @param username the username
		 * @param pageable the pageable
		 * @return the page
		 */
		Page<User> findAllByUsernameContains(String username,Pageable pageable);

}
