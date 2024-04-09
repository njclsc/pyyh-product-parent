package com.zh.middware.init;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InitForSystem {

	@Bean
	public void initSource(){
		System.out.println("===>>");
	}
}
