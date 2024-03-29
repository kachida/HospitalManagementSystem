package com.usersvc.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import com.usersvc.models.Role;



/**
 * The Interface IRoleRepository.
 *
 * @author : Kannappan
 * @version : 1.0
 */
@Repository
public interface IRoleRepository extends JpaRepository<Role, Long>, JpaSpecificationExecutor<Role> {
}
