package com.vitalsignsvc.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.slf4j.LoggerFactory;

@Aspect
@Component
public class Logger {
	
	private static org.slf4j.Logger logger = LoggerFactory.getLogger(Logger.class);

	@Around("@annotation(com.vitalsignsvc.aspect.Loggable)")
	public Object log(ProceedingJoinPoint proceedingJoinPoint) throws Throwable
	{
		long start = System.currentTimeMillis();
		Object result = proceedingJoinPoint.proceed();
		logger.info("className = {} , methodName = {}, timeMs ={}", proceedingJoinPoint.getSignature().getDeclaringTypeName(), proceedingJoinPoint.getSignature().getName() , System.currentTimeMillis() - start );
		return result;
		
	}
	
}
