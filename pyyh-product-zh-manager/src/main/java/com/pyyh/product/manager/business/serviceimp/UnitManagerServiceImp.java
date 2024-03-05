package com.pyyh.product.manager.business.serviceimp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pyyh.product.manager.business.dao.IUnitPojoManagerDao;
import com.pyyh.product.manager.business.service.IManagerService;
import com.pyyh.product.manager.pojo.UnitPojo;

@SuppressWarnings("unchecked")
@Service("UnitManagerServiceImp")
public class UnitManagerServiceImp implements IManagerService{
	@Autowired
	private IUnitPojoManagerDao unitDao;

	
	@Override
	public <T> T findAll(T t) {
		// TODO Auto-generated method stub
		return (T)(unitDao.findAll((UnitPojo)t));
	}

	@Override
	public <T> T add(T t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T delete(T t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T update(T t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T findById(T t) {
		// TODO Auto-generated method stub
		return (T)(unitDao.findById((UnitPojo)t));
	}
	
	

}
