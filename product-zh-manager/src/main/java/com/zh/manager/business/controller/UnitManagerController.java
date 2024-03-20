package com.zh.manager.business.controller;

import javax.servlet.http.HttpServletRequest;

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
	public ResponsePojo add(@RequestBody UnitPojo up, HttpServletRequest req){
		up.setToken(req.getHeader("zh-token"));
		return ums.add(up);
	}
	@RequestMapping("delete")
	public ResponsePojo delete(@RequestBody UnitPojo up, HttpServletRequest req){
		up.setToken(req.getHeader("zh-token"));
		return ums.delete(up);
	}
	@RequestMapping("update")
	public ResponsePojo update(@RequestBody UnitPojo up, HttpServletRequest req){
		up.setToken(req.getHeader("zh-token"));
		return ums.update(up);
	}
	@RequestMapping("find")
	public ResponsePojo find(@RequestBody UnitPojo up, HttpServletRequest req){
		up.setToken(req.getHeader("zh-token"));
		return ums.findAll(up);
	}
}
