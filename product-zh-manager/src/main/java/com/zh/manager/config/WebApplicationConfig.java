package com.zh.manager.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.zh.manager.business.controller.AuthInterceptor;

@Configuration
public class WebApplicationConfig extends WebMvcConfigurationSupport{
	@Autowired
	private AuthInterceptor authInterceptor;
	@Override
	protected void addInterceptors(InterceptorRegistry registry) {
		// TODO Auto-generated method stub
		System.out.println("------><>");
		registry.addInterceptor(authInterceptor)
		.addPathPatterns("/**");
//		.excludePathPatterns("/static/**", "/vehicle/regist", "/vehicle/scanCode");
		
//		super.addInterceptors(registry);
	}

	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		// TODO Auto-generated method stub
//		super.addResourceHandlers(registry);
	}

}
