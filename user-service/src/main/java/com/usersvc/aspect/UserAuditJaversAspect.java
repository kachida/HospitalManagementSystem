package com.usersvc.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.javers.core.commit.Commit;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.usersvc.service.UserAuditProducerService;


// TODO: Auto-generated Javadoc
/**
 * The Class UserAuditJaversAspect.
 *
 * @author : Kannappan
 * @version : 1.0
 */
@Component
@Aspect
@Order(Ordered.LOWEST_PRECEDENCE)
public class UserAuditJaversAspect {

	/** The entity change sevice. */
	private final UserAuditProducerService entityChangeSevice;

	/**
	 * Instantiates a new user audit javers aspect.
	 *
	 * @param entityChangeSevice the entity change sevice
	 */
	public UserAuditJaversAspect(UserAuditProducerService entityChangeSevice) {
		this.entityChangeSevice = entityChangeSevice;
	}
	
	/**
	 * On javers commit execution.
	 *
	 * @param jp the jp
	 * @param commit the commit
	 */
	@AfterReturning(pointcut = "execution(public * commit(..)) && this(org.javers.core.Javers)", returning = "commit")
	public void onJaversCommitExecution(JoinPoint jp, Commit commit)
	{
		this.entityChangeSevice.broadcastEntityChange(commit);
	}
	
	
	
}
