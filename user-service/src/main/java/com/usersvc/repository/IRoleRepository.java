package com.usersvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.usersvc.models.Role;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Long> {
}
