package com.usersvc.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.javers.core.commit.Commit;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.usersvc.service.UserAuditProducerService;

@Component
@Aspect
@Order(Ordered.LOWEST_PRECEDENCE)
public class UserAuditJaversAspect {

	private final UserAuditProducerService entityChangeSevice;

	public UserAuditJaversAspect(UserAuditProducerService entityChangeSevice) {
		this.entityChangeSevice = entityChangeSevice;
	}
	
	@AfterReturning(pointcut = "execution(public * commit(..)) && this(org.javers.core.Javers)", returning = "commit")
	public void onJaversCommitExecution(JoinPoint jp, Commit commit)
	{
		this.entityChangeSevice.broadcastEntityChange(commit);
	}
	
	
	
}
