package com.pyyh.product.login.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@ComponentScan(basePackages = {"com.pyyh.product.login"})
@SpringBootApplication
public class LoginStarter {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SpringApplication.run(LoginStarter.class, args);
	}

}
