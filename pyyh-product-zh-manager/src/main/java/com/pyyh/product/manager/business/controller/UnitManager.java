package com.pyyh.product.manager.business.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pyyh.product.manager.business.service.IManagerService;
import com.pyyh.product.manager.pojo.UnitPojo;

@RestController
@RequestMapping("/unit")
public class UnitManager {
	@Autowired
	@Qualifier("UnitManagerServiceImp")
	private IManagerService unitService;
	@RequestMapping("findUnit")
	public Object findUnit(@RequestBody UnitPojo unit){
		return unitService.findById(unit);
	}
	@RequestMapping("findUnits")
	public Object findUnits(@RequestBody UnitPojo unit){
		return unitService.findAll(unit);
	}
}
