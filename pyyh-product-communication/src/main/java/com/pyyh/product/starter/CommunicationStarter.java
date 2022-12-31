package com.pyyh.product.starter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
@ComponentScan(basePackages = {"com.pyyh.product"})
@MapperScan(basePackages = {"com.pyyh.product"})
@SpringBootApplication
public class CommunicationStarter {

	public static void main(String[] args){
		SpringApplication.run(CommunicationStarter.class, args);
	}
}
