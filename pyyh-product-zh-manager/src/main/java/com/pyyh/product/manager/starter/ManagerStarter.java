package com.pyyh.product.manager.starter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.pyyh.product.manager"})
@MapperScan(basePackages = {"com.pyyh.product.manager"})
@SpringBootApplication
public class ManagerStarter {

	public static void main(String[] args){
		SpringApplication.run(ManagerStarter.class, args);
	}
}
