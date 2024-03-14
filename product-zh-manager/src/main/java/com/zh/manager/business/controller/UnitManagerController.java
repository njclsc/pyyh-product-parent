package com.zh.manager.business.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zh.manager.business.pojo.ResponsePojo;
import com.zh.manager.business.pojo.UnitPojo;
import com.zh.manager.business.service.IManagerService;

@RestController
@RequestMapping("/unit")
public class UnitManagerController {
	@Autowired
	@Qualifier("UnitManagerServiceImp")
	private IManagerService ums;
	
	@RequestMapping("add")
	public ResponsePojo add(@RequestBody UnitPojo up){
		return ums.add(up);
	}
}
