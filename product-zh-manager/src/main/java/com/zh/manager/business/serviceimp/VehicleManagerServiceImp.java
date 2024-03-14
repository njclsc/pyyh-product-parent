package com.zh.manager.business.serviceimp;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zh.manager.business.dao.IVehicleManagerDao;
import com.zh.manager.business.pojo.ResponsePojo;
import com.zh.manager.business.pojo.VehiclePojo;
import com.zh.manager.business.service.IAuditingService;
import com.zh.manager.business.service.IManagerService;
import com.zh.manager.util.ContainerUtil;

@Service("VehicleManagerServiceImp")
public class VehicleManagerServiceImp implements IManagerService, IAuditingService{
	@Autowired
	private IVehicleManagerDao vmd;
	@Override
	public <T, P> T add(P p) {
		// TODO Auto-generated method stub
		ResponsePojo rp = new ResponsePojo();
		try{
		VehiclePojo up = (VehiclePojo)p;
		up.setStatus(3);
		up.setRegistDate(ContainerUtil.getSdf().format(new Date()));
		vmd.add(up);
		System.out.println(up.getId());
		if(up.getId() > 0){
			rp.setMessage("申请提交成功!");
			rp.setResult("success");
		}else{
			rp.setMessage("申请提交失败!");
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
		VehiclePojo up = (VehiclePojo)p;
		vmd.delete(up);
		ResponsePojo rp = new ResponsePojo();
		rp.setMessage("车辆删除成功!");
		rp.setResult("success");
		return (T)rp;
	}

	@Override
	public <T, P> T update(P p) {
		// TODO Auto-generated method stub
		VehiclePojo up = (VehiclePojo)p;
		vmd.update(up);
		ResponsePojo rp = new ResponsePojo();
		rp.setMessage("车辆修改成功!");
		rp.setResult("success");
		return (T)rp;
	}

	@Override
	public <T, P> T findById(P p) {
		// TODO Auto-generated method stub
		VehiclePojo up = (VehiclePojo)p;
		up = vmd.findById(up);
		ResponsePojo rp = new ResponsePojo();
		rp.setResult("success");
		rp.setSource(up);
		return (T)rp;
	}

	@Override
	public <T, P> T findAll(P p) {
		// TODO Auto-generated method stub
		VehiclePojo up = (VehiclePojo)p;
		List<VehiclePojo> ups = vmd.findByAll(up);
		ResponsePojo rp = new ResponsePojo();
		rp.setResult("success");
		rp.setSource(ups);
		return (T)rp;
	}

	@Override
	public ResponsePojo auditing(VehiclePojo vp) {
		// TODO Auto-generated method stub
		vmd.auditing(vp);
		ResponsePojo rp = new ResponsePojo();
		rp.setResult("success");
		if(vp.getStatus() == 2){
			rp.setMessage("已驳回");
		}else if(vp.getStatus() == 1){
			rp.setMessage("已通过");
		}else if(vp.getStatus() == 0){
			rp.setMessage("已安装");
		}
		return rp;
	}

}
