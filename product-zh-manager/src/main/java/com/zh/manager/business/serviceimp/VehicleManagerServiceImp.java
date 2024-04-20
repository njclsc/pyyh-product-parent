package com.zh.manager.business.serviceimp;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth0.jwt.interfaces.Claim;
import com.zh.manager.business.dao.IVehicleManagerDao;
import com.zh.manager.business.pojo.ResponsePojo;
import com.zh.manager.business.pojo.ResultPojo;
import com.zh.manager.business.pojo.VehiclePojo;
import com.zh.manager.business.service.IAuditingService;
import com.zh.manager.business.service.IManagerService;
import com.zh.manager.util.ContainerUtil;
import com.zh.manager.util.ToolUtil;

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
		
		Map<String, Claim> claims = ToolUtil.tokenParse(up.getToken());
		int unitIndex = claims.get("unitIndex").asInt();
		List<String> userAuthority = claims.get("writeAuthority").asList(String.class);
		up.setUnitIndex(unitIndex);
		if(!userAuthority.contains("2017")){
			rp.setMessage("权限不足");
			rp.setResult("fail");
			return (T)rp;
		}
		
		System.out.println(up.getUnitIndex());
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
		ResponsePojo rp = new ResponsePojo();
		VehiclePojo up = (VehiclePojo)p;
		
		Map<String, Claim> claims = ToolUtil.tokenParse(up.getToken());
		int unitIndex = claims.get("unitIndex").asInt();
		List<String> userAuthority = claims.get("writeAuthority").asList(String.class);
		up.setUnitIndex(unitIndex);
		if(!userAuthority.contains("2017")){
			rp.setMessage("权限不足");
			rp.setResult("fail");
			return (T)rp;
		}
		
		
		vmd.delete(up);
		
		rp.setMessage("车辆删除成功!");
		rp.setResult("success");
		return (T)rp;
	}

	@Override
	public <T, P> T update(P p) {
		// TODO Auto-generated method stub
		ResponsePojo rp = new ResponsePojo();
		VehiclePojo up = (VehiclePojo)p;
		
		Map<String, Claim> claims = ToolUtil.tokenParse(up.getToken());
		int unitIndex = claims.get("unitIndex").asInt();
		List<String> userAuthority = claims.get("writeAuthority").asList(String.class);
		up.setUnitIndex(unitIndex);
		if(!userAuthority.contains("2017")){
			rp.setMessage("权限不足");
			rp.setResult("fail");
			return (T)rp;
		}
		
		
		vmd.update(up);
		
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
		ResponsePojo rp = new ResponsePojo();
		VehiclePojo up = (VehiclePojo)p;
		Map<String, Claim> claims = ToolUtil.tokenParse(up.getToken());
		int unitIndex = claims.get("unitIndex").asInt();
		List<String> userAuthority = claims.get("writeAuthority").asList(String.class);
		up.setUnitIndex(unitIndex);
		if(!userAuthority.contains("1017") || (!userAuthority.contains("1017") && !userAuthority.contains("2017"))){
			rp.setMessage("权限不足");
			rp.setResult("fail");
			return (T)rp;
		}
		
		int pages = up.getPages();
		int rows = up.getRows();
		int begin = (pages - 1) * rows;
		up.setBegin(begin);
		
		List<VehiclePojo> ups = vmd.findByAll(up);
		ResultPojo rp1 = new ResultPojo();
		rp1.setTotal(vmd.count(up));
		rp1.setData(ups);
		
		
		rp.setResult("success");
		rp.setSource(rp1);
		return (T)rp;
	}

	@Override
	public ResponsePojo auditing(VehiclePojo vp) {
		// TODO Auto-generated method stub
		ResponsePojo rp = new ResponsePojo();
		VehiclePojo up = (VehiclePojo)vp;
		Map<String, Claim> claims = ToolUtil.tokenParse(up.getToken());
		int unitIndex = claims.get("unitIndex").asInt();
		List<String> userAuthority = claims.get("writeAuthority").asList(String.class);
		up.setUnitIndex(unitIndex);
		if(!userAuthority.contains("2017")){
			rp.setMessage("权限不足");
			rp.setResult("fail");
			return rp;
		}
		vmd.auditing(up);
		rp.setResult("success");
		if(vp.getStatus() == 2){
			rp.setMessage("已驳回");
		}else if(vp.getStatus() == 1){
			rp.setMessage("已通过");
		}else if(vp.getStatus() == 0){
			rp.setMessage("已安装");
			System.out.println(up.getRfidId1() + "---->>>" + up.getRfidId2());
		}
		return rp;
	}

}
