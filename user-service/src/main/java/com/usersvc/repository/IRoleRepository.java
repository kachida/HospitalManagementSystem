package com.usersvc.repository;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.usersvc.models.Role;
import com.usersvc.models.User;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Long>, JpaSpecificationExecutor<Role> {
}
