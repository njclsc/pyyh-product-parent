package com.zh.collection.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.zh.collection"})
@SpringBootApplication
public class CollectionStarter {

	public static void main(String[] args){
		SpringApplication.run(CollectionStarter.class, args);
	}
}
