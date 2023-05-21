package com.pyyh.product.login.business;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pyyh.product.login.pojo.UserPojo;

@RestController
@RequestMapping("verify")
public class LoginController {

	@RequestMapping("in")
	public String loginIn(UserPojo userPojo){
		
		return "1";
	}
	@RequestMapping("out")
	public String loginOut(UserPojo userPojo){
		
		return "2";
	}
}
