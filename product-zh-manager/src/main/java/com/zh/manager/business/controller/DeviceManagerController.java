package com.zh.manager.business.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zh.manager.business.pojo.DevicePojo;
import com.zh.manager.business.pojo.ResponsePojo;
import com.zh.manager.business.service.IManagerService;

@RestController
@RequestMapping("/device")
public class DeviceManagerController {
	@Autowired
	@Qualifier("DeviceManagerServiceImp")
	private IManagerService dms;
	@RequestMapping("add")
	public ResponsePojo add(@RequestBody DevicePojo rp, HttpServletRequest req){
		rp.setToken(req.getHeader("zh-token"));
		return dms.add(rp);
	}
	@RequestMapping("delete")
	public ResponsePojo delete(@RequestBody DevicePojo rp, HttpServletRequest req){
		rp.setToken(req.getHeader("zh-token"));
		return dms.delete(rp);
	}
	@RequestMapping("update")
	public ResponsePojo update(@RequestBody DevicePojo rp, HttpServletRequest req){
		rp.setToken(req.getHeader("zh-token"));
		return dms.update(rp);
	}
	@RequestMapping("findById")
	public ResponsePojo findById(@RequestBody DevicePojo rp, HttpServletRequest req){
		rp.setToken(req.getHeader("zh-token"));
		return dms.findById(rp);
	}
	@RequestMapping("findAll")
	public ResponsePojo findAll(@RequestBody DevicePojo rp, HttpServletRequest req){
		rp.setToken(req.getHeader("zh-token"));
		return dms.findAll(rp);
	}
}
