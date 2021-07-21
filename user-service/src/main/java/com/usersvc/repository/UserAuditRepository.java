package com.usersvc.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.usersvc.models.UserAudit;

@Repository
public interface UserAuditRepository extends MongoRepository<UserAudit, String> {

}
