package com.zh.manager.business.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zh.manager.business.pojo.ResponsePojo;
import com.zh.manager.business.pojo.VehiclePojo;
import com.zh.manager.business.service.IManagerService;

@RestController
@RequestMapping("/vehicle")
public class VehicleManagerController {

	@Autowired
	@Qualifier("VehicleManagerServiceImp")
	private IManagerService vms;
	
	@RequestMapping("add")
	public ResponsePojo add(@RequestBody VehiclePojo vp){
		return vms.add(vp);
	}
	@RequestMapping("update")
	public ResponsePojo update(@RequestBody VehiclePojo vp){
		return vms.update(vp);
	}
	@RequestMapping("findById")
	public ResponsePojo findById(@RequestBody VehiclePojo vp){
		return vms.findById(vp);
	}
	@RequestMapping("findByAll")
	public ResponsePojo findByAll(@RequestBody VehiclePojo vp){
		return vms.findAll(vp);
	}
	@RequestMapping("delete")
	public ResponsePojo delete(@RequestBody VehiclePojo vp){
		return vms.delete(vp);
	}
	
}
