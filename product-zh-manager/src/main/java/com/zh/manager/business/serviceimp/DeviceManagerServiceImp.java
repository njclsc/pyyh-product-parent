package com.zh.manager.business.serviceimp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zh.manager.business.dao.IDeviceManagerDao;
import com.zh.manager.business.pojo.DevicePojo;
import com.zh.manager.business.pojo.ResponsePojo;
import com.zh.manager.business.service.IManagerService;

@Service("DeviceManagerServiceImp")
public class DeviceManagerServiceImp implements IManagerService{
	@Autowired
	private IDeviceManagerDao dmd;
	@Override
	public <T, P> T add(P p) {
		// TODO Auto-generated method stub
		ResponsePojo rp = new ResponsePojo();
		int flag = dmd.add((DevicePojo)p);
		System.out.println(flag);
		if(flag > 0){
			rp.setMessage("设备添加成功");
			rp.setResult("success");
		}else{
			rp.setMessage("设备添加失败");
			rp.setResult("fail");
		}
		return (T)rp;
	}

	@Override
	public <T, P> T delete(P p) {
		// TODO Auto-generated method stub
		ResponsePojo rp = new ResponsePojo();
		dmd.delete((DevicePojo)p);
		rp.setMessage("设备删除成功");
		rp.setResult("success");
		return (T)rp;
	}

	@Override
	public <T, P> T update(P p) {
		// TODO Auto-generated method stub
		dmd.update((DevicePojo)p);
		ResponsePojo rp = new ResponsePojo();
		rp.setMessage("设备修改成功");
		rp.setResult("success");
		return (T)rp;
	}

	@Override
	public <T, P> T findById(P p) {
		// TODO Auto-generated method stub
		DevicePojo rp = dmd.findById((DevicePojo)p);
		ResponsePojo rep = new ResponsePojo();
		rep.setResult("success");
		rep.setSource(rp);
		return (T)rep;
	}

	@Override
	public <T, P> T findAll(P p) {
		// TODO Auto-generated method stub
		List<DevicePojo> rp = dmd.findAll((DevicePojo)p);
		ResponsePojo rep = new ResponsePojo();
		rep.setResult("success");
		rep.setSource(rp);
		return (T)rep;
	}

}
