package com.pyyh.product.logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class InitLogger {
	
	@Pointcut("execution (* com.pyyh.product.init.*.*(..))")
	public void initSource(){}
	
	@Before("initSource()")
	public void before(JoinPoint joinPoint){
		System.out.println("before ");
	}
	@After("initSource()")
	public void after(JoinPoint joinPoint){
		System.out.println("after ");
	}
	@Around("initSource()")
	public Object around(ProceedingJoinPoint pjp){
		try {
			Object obj = pjp.proceed();
			System.out.println("around " + obj);
			return obj;
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}
