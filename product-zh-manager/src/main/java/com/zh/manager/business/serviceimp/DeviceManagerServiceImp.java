package com.zh.manager.business.serviceimp;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth0.jwt.interfaces.Claim;
import com.zh.manager.business.dao.IDeviceManagerDao;
import com.zh.manager.business.pojo.DevicePojo;
import com.zh.manager.business.pojo.ResponsePojo;
import com.zh.manager.business.service.IManagerService;
import com.zh.manager.util.ToolUtil;

@Service("DeviceManagerServiceImp")
public class DeviceManagerServiceImp implements IManagerService{
	@Autowired
	private IDeviceManagerDao dmd;
	@Override
	public <T, P> T add(P p) {
		// TODO Auto-generated method stub
		ResponsePojo rp = new ResponsePojo();
		try{
			DevicePojo tp = (DevicePojo)p;
			Map<String, Claim> claims = ToolUtil.tokenParse(tp.getToken());
			int unitIndex = claims.get("unitIndex").asInt();
			List<String> userAuthority = claims.get("writeAuthority").asList(String.class);
			tp.setUnitIndex(unitIndex);
			if(!userAuthority.contains("2007")){
				rp.setMessage("权限不足");
				rp.setResult("fail");
				return (T)rp;
			}
			int flag = dmd.add(tp);
			System.out.println(flag);
			if(flag > 0){
				rp.setMessage("设备添加成功");
				rp.setResult("success");
			}else{
				rp.setMessage("设备添加失败");
				rp.setResult("fail");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return (T)rp;
	}

	@Override
	public <T, P> T delete(P p) {
		// TODO Auto-generated method stub
		ResponsePojo rp = new ResponsePojo();
		DevicePojo tp = (DevicePojo)p;
		Map<String, Claim> claims = ToolUtil.tokenParse(tp.getToken());
		int unitIndex = claims.get("unitIndex").asInt();
		List<String> userAuthority = claims.get("writeAuthority").asList(String.class);
		tp.setUnitIndex(unitIndex);
		if(!userAuthority.contains("2007")){
			rp.setMessage("权限不足");
			rp.setResult("fail");
			return (T)rp;
		}
		
		dmd.delete(tp);
		rp.setMessage("设备删除成功");
		rp.setResult("success");
		return (T)rp;
	}

	@Override
	public <T, P> T update(P p) {
		// TODO Auto-generated method stub
		ResponsePojo rp = new ResponsePojo();
		DevicePojo tp = (DevicePojo)p;
		Map<String, Claim> claims = ToolUtil.tokenParse(tp.getToken());
		int unitIndex = claims.get("unitIndex").asInt();
		List<String> userAuthority = claims.get("writeAuthority").asList(String.class);
		tp.setUnitIndex(unitIndex);
		if(!userAuthority.contains("2007")){
			rp.setMessage("权限不足");
			rp.setResult("fail");
			return (T)rp;
		}
		dmd.update(tp);
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
		ResponsePojo rep = new ResponsePojo();
		DevicePojo tp = (DevicePojo)p;
		Map<String, Claim> claims = ToolUtil.tokenParse(tp.getToken());
		int unitIndex = claims.get("unitIndex").asInt();
		List<String> userAuthority = claims.get("writeAuthority").asList(String.class);
		tp.setUnitIndex(unitIndex);
		if(!userAuthority.contains("1007")&&!userAuthority.contains("2007")){
			rep.setMessage("权限不足");
			rep.setResult("fail");
			return (T)rep;
		}
		List<DevicePojo> rp = dmd.findAll(tp);
		rep.setResult("success");
		rep.setSource(rp);
		return (T)rep;
	}

}
