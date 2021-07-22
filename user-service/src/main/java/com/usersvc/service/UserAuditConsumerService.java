package com.usersvc.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.usersvc.models.UserAudit;
import com.usersvc.repository.UserAuditRepository;

@Component
public class UserAuditConsumerService {
	
	private UserAuditRepository userAuditRepository;
	
	public UserAuditConsumerService(UserAuditRepository userAuditRepository) {
		this.userAuditRepository = userAuditRepository;
	}

	@KafkaListener(topics="USER_CREATE", groupId="group_id")
	public void userCreateConsumer(UserAudit userAuditMessage)
	{
		userAuditRepository.save(userAuditMessage);
	}
	
	@KafkaListener(topics="USER_UPDATE", groupId="group_id")
	public void userUpdateConsumer(UserAudit userAuditMessage)
	{
		userAuditRepository.save(userAuditMessage);
	}
	
	@KafkaListener(topics="USER_DELETE", groupId="group_id")
	public void userDeleteConsumer(UserAudit userAuditMessage)
	{
		userAuditRepository.save(userAuditMessage);
	}


	
}
