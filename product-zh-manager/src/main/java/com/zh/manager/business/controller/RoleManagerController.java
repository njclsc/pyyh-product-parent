package com.zh.manager.business.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zh.manager.business.pojo.ResponsePojo;
import com.zh.manager.business.pojo.RolePojo;
import com.zh.manager.business.service.IManagerService;
import com.zh.manager.business.service.IMenuService;

@RestController
@RequestMapping("/role")
public class RoleManagerController {
	@Autowired
	@Qualifier("RoleManagerServiceImp")
	private IManagerService rms;
	@Autowired
	@Qualifier("RoleManagerServiceImp")
	private IMenuService ms;
	@RequestMapping("add")
	public ResponsePojo add(@RequestBody RolePojo rp, HttpServletRequest req){
		rp.setToken(req.getHeader("zh-token"));
		return rms.add(rp);
	}
	@RequestMapping("delete")
	public ResponsePojo delete(@RequestBody RolePojo rp, HttpServletRequest req){
		rp.setToken(req.getHeader("zh-token"));
		return rms.delete(rp);
	}
	@RequestMapping("update")
	public ResponsePojo update(@RequestBody RolePojo rp, HttpServletRequest req){
		rp.setToken(req.getHeader("zh-token"));
		return rms.update(rp);
	}
	@RequestMapping("find")
	public ResponsePojo find(@RequestBody RolePojo rp, HttpServletRequest req){
		rp.setToken(req.getHeader("zh-token"));
		return rms.findAll(rp);
	}
	@RequestMapping("loadMenu")
	public Object loadMenu(@RequestBody RolePojo rp, HttpServletRequest req){
		rp.setToken(req.getHeader("zh-token"));
		return ms.loadMenu(rp);
	}
}
