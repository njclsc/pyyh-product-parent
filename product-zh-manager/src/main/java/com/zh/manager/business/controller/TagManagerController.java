package com.zh.manager.business.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zh.manager.business.pojo.DevicePojo;
import com.zh.manager.business.pojo.ResponsePojo;
import com.zh.manager.business.pojo.TagPojo;
import com.zh.manager.business.service.IManagerService;

@RestController
@RequestMapping("/tag")
public class TagManagerController {
	@Autowired
	@Qualifier("TagManagerServiceImp")
	private IManagerService tms;
	
	@RequestMapping("add")
	public ResponsePojo add(@RequestBody TagPojo rp){
		return tms.add(rp);
	}
	@RequestMapping("delete")
	public ResponsePojo delete(@RequestBody TagPojo rp){
		return tms.delete(rp);
	}
	@RequestMapping("update")
	public ResponsePojo update(@RequestBody TagPojo rp){
		return tms.update(rp);
	}
	@RequestMapping("find")
	public ResponsePojo find(@RequestBody TagPojo rp){
		return tms.findAll(rp);
	}
}
