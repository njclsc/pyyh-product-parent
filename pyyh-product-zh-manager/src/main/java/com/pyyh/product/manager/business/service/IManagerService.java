package com.pyyh.product.manager.business.service;

import org.springframework.web.bind.annotation.RequestBody;

import com.pyyh.product.manager.pojo.UnitPojo;

public interface IManagerService {

	public <T> T findAll(T t);
	public <T> T findById(T t);
	public <T> T add(T t);
	public <T> T delete(T t);
	public <T> T update(T t);
}
