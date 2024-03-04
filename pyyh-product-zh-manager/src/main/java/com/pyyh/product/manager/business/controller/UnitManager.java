package com.pyyh.product.manager.business.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pyyh.product.manager.pojo.UnitPojo;

@RestController
@RequestMapping("/unit")
public class UnitManager {
	
	@RequestMapping("findUnit")
	public String findUnit(@RequestBody UnitPojo unit){
		
		return "xxx";
	}
}
