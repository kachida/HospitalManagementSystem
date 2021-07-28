package com.usersvc.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.usersvc.models.UserAudit;


/**
 * The Interface UserAuditRepository.
 *
 * @author : Kannappan
 * @version : 1.0
 */
@Repository
public interface UserAuditRepository extends MongoRepository<UserAudit, String> {

}
