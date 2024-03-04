package com.pyyh.product.manager.business.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.pyyh.product.manager.business.service.IInterceptorService;

@Component
public class AuthInterceptor implements HandlerInterceptor{
	@Autowired
	@Qualifier("InterceptorServiceimp")
	private IInterceptorService interporterService;
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		return interporterService.loginCheck(request.getHeader("zh-token"));
	}

}
