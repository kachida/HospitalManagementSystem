package com.usersvc.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.usersvc.models.UserAudit;
import com.usersvc.repository.UserAuditRepository;

import lombok.extern.slf4j.Slf4j;


// TODO: Auto-generated Javadoc
/**
 * The Class UserAuditConsumerService.
 *
 * @author : Kannappan
 * @version : 1.0
 */
@Component

/** The Constant log. */
@Slf4j
public class UserAuditConsumerService {
	
	/** The user audit repository. */
	private UserAuditRepository userAuditRepository;
	
	/**
	 * Instantiates a new user audit consumer service.
	 *
	 * @param userAuditRepository the user audit repository
	 */
	public UserAuditConsumerService(UserAuditRepository userAuditRepository) {
		this.userAuditRepository = userAuditRepository;
	}

	/**
	 * User create consumer.
	 *
	 * @param userAuditMessage the user audit message
	 */
	@KafkaListener(topics="USER_CREATE", groupId="group_id")
	public void userCreateConsumer(UserAudit userAuditMessage)
	{
		log.info("Message:{}",userAuditMessage.toString());
		userAuditRepository.save(userAuditMessage);
	}
	
	/**
	 * User update consumer.
	 *
	 * @param userAuditMessage the user audit message
	 */
	@KafkaListener(topics="USER_UPDATE", groupId="group_id")
	public void userUpdateConsumer(UserAudit userAuditMessage)
	{
		userAuditRepository.save(userAuditMessage);
	}
	
	/**
	 * User delete consumer.
	 *
	 * @param userAuditMessage the user audit message
	 */
	@KafkaListener(topics="USER_DELETE", groupId="group_id")
	public void userDeleteConsumer(UserAudit userAuditMessage)
	{
		userAuditRepository.save(userAuditMessage);
	}


	
}
