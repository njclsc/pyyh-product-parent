package com.zh.middware.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.zh.middware"})
@SpringBootApplication
public class MiddwareStarter {
	public static void main(String[] args){
		SpringApplication.run(MiddwareStarter.class, args);
	}
}