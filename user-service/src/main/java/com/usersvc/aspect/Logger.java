package com.usersvc.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.slf4j.LoggerFactory;


// TODO: Auto-generated Javadoc
/**
 * The Class Logger.
 *
 * @author : Kannappan
 * @version : 1.0
 */
@Aspect
@Component
public class Logger {
	
	/** The logger. */
	private static org.slf4j.Logger logger = LoggerFactory.getLogger(Logger.class);

	/**
	 * Log.
	 *
	 * @param proceedingJoinPoint the proceeding join point
	 * @return the object
	 * @throws Throwable the throwable
	 */
	@Around("@annotation(com.usersvc.aspect.Loggable)")
	public Object log(ProceedingJoinPoint proceedingJoinPoint) throws Throwable
	{
		long start = System.currentTimeMillis();
		Object result = proceedingJoinPoint.proceed();
		logger.info("className = {} , methodName = {}, timeMs ={}", proceedingJoinPoint.getSignature().getDeclaringTypeName(), proceedingJoinPoint.getSignature().getName() , System.currentTimeMillis() - start );
		return result;
		
	}
	
}
