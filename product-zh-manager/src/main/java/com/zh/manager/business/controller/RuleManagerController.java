package com.zh.manager.business.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zh.manager.business.pojo.ResponsePojo;
import com.zh.manager.business.pojo.RulePojo;
import com.zh.manager.business.service.IManagerService;

@RestController
@RequestMapping("/rule")
public class RuleManagerController {
	@Autowired
	@Qualifier("RuleManagerServiceImp")
	private IManagerService rms;
	
	@RequestMapping("add")
	public ResponsePojo add(@RequestBody RulePojo rp, HttpServletRequest req){
		rp.setToken(req.getHeader("zh-token"));
		return rms.add(rp);
	}
	@RequestMapping("update")
	public ResponsePojo update(@RequestBody RulePojo rp, HttpServletRequest req){
		rp.setToken(req.getHeader("zh-token"));
		return rms.update(rp);
	}
	@RequestMapping("delete")
	public ResponsePojo delete(@RequestBody RulePojo rp, HttpServletRequest req){
		rp.setToken(req.getHeader("zh-token"));
		return rms.delete(rp);
	}
	@RequestMapping("findById")
	public ResponsePojo findById(@RequestBody RulePojo rp, HttpServletRequest req){
		rp.setToken(req.getHeader("zh-token"));
		return rms.findById(rp);
	}
	@RequestMapping("findAll")
	public ResponsePojo findAll(@RequestBody RulePojo rp, HttpServletRequest req){
		rp.setToken(req.getHeader("zh-token"));
		return rms.findAll(rp);
	}
}
