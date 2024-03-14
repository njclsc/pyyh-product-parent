package com.zh.manager.starter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.zh.manager"})
@MapperScan(basePackages = {"com.zh.manager"})
@SpringBootApplication
public class ManagerStarter {

	public static void main(String[] args){
		SpringApplication.run(ManagerStarter.class, args);
	}
}
