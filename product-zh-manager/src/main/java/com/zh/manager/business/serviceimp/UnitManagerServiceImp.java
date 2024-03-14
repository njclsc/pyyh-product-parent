package com.zh.manager.business.serviceimp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zh.manager.business.dao.IUnitManagerDao;
import com.zh.manager.business.pojo.ResponsePojo;
import com.zh.manager.business.pojo.UnitPojo;
import com.zh.manager.business.service.IManagerService;
import com.zh.manager.util.ToolUtil;

@Service("UnitManagerServiceImp")
public class UnitManagerServiceImp implements IManagerService{
	@Autowired
	private IUnitManagerDao umd;
	@Override
	public <T, P> T add(P p) {
		// TODO Auto-generated method stub
		ResponsePojo rp = new ResponsePojo();
		try{
		UnitPojo up = (UnitPojo)p;
		umd.add(up);
		System.out.println(up.getId());
		if(up.getId() > 0){
			rp.setMessage("单位添加成功!");
			rp.setResult("success");
			ToolUtil.createUnitAppDataTable(up);
		}else{
			rp.setMessage("单位添加失败!");
			rp.setResult("fail");
		}
		}catch(Exception e){
			e.printStackTrace();
			rp.setMessage(e.getClass().getSimpleName());
			rp.setResult("fail");
		}
		return (T)rp;
	}

	@Override
	public <T, P> T delete(P p) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T, P> T update(P p) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T, P> T findById(P p) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T, P> T findAll(P p) {
		// TODO Auto-generated method stub
		return null;
	}

}
