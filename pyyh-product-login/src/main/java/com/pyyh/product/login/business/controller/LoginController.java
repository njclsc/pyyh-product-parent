package com.pyyh.product.login.business.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.pyyh.product.login.business.service.ILoginService;
import com.pyyh.product.login.pojo.UserPojo;

@RestController
@RequestMapping("/check")
public class LoginController {
	@Autowired
	@Qualifier("LoginServiceImp")
	private ILoginService loginService;
	
	@RequestMapping("loginCheck")
	public String loginCheck(@RequestBody UserPojo user){
		return loginService.loginCheck(user);
	}
}
