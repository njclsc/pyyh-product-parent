package com.zh.manager.business.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zh.manager.business.pojo.AreaPojo;
import com.zh.manager.business.pojo.ResponsePojo;
import com.zh.manager.business.service.IManagerService;

@RestController
@RequestMapping("/area")
public class AreaManagerCotroller {
	@Autowired
	@Qualifier("AreaManagerServiceImp")
	private IManagerService ams;
	
	@RequestMapping("add")
	public ResponsePojo add(@RequestBody AreaPojo rp, HttpServletRequest req){
		rp.setToken(req.getHeader("zh-token"));
		return ams.add(rp);
	}
	@RequestMapping("delete")
	public ResponsePojo delete(@RequestBody AreaPojo rp, HttpServletRequest req){
		rp.setToken(req.getHeader("zh-token"));
		return ams.delete(rp);
	}
	@RequestMapping("update")
	public ResponsePojo update(@RequestBody AreaPojo rp, HttpServletRequest req){
		rp.setToken(req.getHeader("zh-token"));
		return ams.update(rp);
	}
	@RequestMapping("find")
	public ResponsePojo find(@RequestBody AreaPojo rp, HttpServletRequest req){
		rp.setToken(req.getHeader("zh-token"));
		return ams.findAll(rp);
	}
}
