package com.pyyh.product.logger;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class InitLogger {
	
	@Pointcut("execution (* com.pyyh.product.init.*.*(..))")
	public void initSource(){}
	
	@Around("initSource()")
	public Object around(ProceedingJoinPoint pjp){
		try {
			Object obj = pjp.proceed();
			return obj;
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			pw.flush();
			sw.flush();
			log.error(sw.toString());
			try {
				sw.close();
				pw.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return null;
	}
	
}
