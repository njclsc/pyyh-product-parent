package com.zh.manager.business.serviceimp;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth0.jwt.interfaces.Claim;
import com.zh.manager.business.dao.IAreaManagerDao;
import com.zh.manager.business.pojo.AccountPojo;
import com.zh.manager.business.pojo.AreaPojo;
import com.zh.manager.business.pojo.ResponsePojo;
import com.zh.manager.business.pojo.ResultPojo;
import com.zh.manager.business.service.IManagerService;
import com.zh.manager.util.ToolUtil;

@Service("AreaManagerServiceImp")
public class AreaManagerServiceImp implements IManagerService{
	@Autowired
	private IAreaManagerDao amd;
	@Override
	public <T, P> T add(P p) {
		// TODO Auto-generated method stub
		ResponsePojo rp = new ResponsePojo();
		try{
			AreaPojo ap = (AreaPojo)p;
			Map<String, Claim> claims = ToolUtil.tokenParse(ap.getToken());
			int unitIndex = claims.get("unitIndex").asInt();
			List<String> userAuthority = claims.get("writeAuthority").asList(String.class);
			ap.setUnitIndex(unitIndex);
			if(!userAuthority.contains("2016")){
				rp.setMessage("权限不足");
				rp.setResult("fail");
				return (T)rp;
			}
			int flag = amd.add(ap);
			if(flag > 0){
				rp.setMessage("区域添加成功");
				rp.setResult("success");
			}else{
				rp.setMessage("区域添加失败");
				rp.setResult("fail");
			}
		}catch(Exception e){
			e.printStackTrace();
			rp.setMessage("区域添加失败");
			rp.setResult("fail");
		}
		return (T)rp;
	}

	@Override
	public <T, P> T delete(P p) {
		// TODO Auto-generated method stub
		ResponsePojo rp = new ResponsePojo();
		try{
			AreaPojo ap = (AreaPojo)p;
			Map<String, Claim> claims = ToolUtil.tokenParse(ap.getToken());
			int unitIndex = claims.get("unitIndex").asInt();
			List<String> userAuthority = claims.get("writeAuthority").asList(String.class);
			ap.setUnitIndex(unitIndex);
			if(!userAuthority.contains("2016")){
				rp.setMessage("权限不足");
				rp.setResult("fail");
				return (T)rp;
			}
			amd.delete(ap);
			rp.setMessage("区域删除成功");
			rp.setResult("success");
		}catch(Exception e){
			e.printStackTrace();
			rp.setMessage("区域添加失败");
			rp.setResult("fail");
		}
		return (T)rp;
	}

	@Override
	public <T, P> T update(P p) {
		// TODO Auto-generated method stub
		ResponsePojo rp = new ResponsePojo();
		try{
			AreaPojo ap = (AreaPojo)p;
			Map<String, Claim> claims = ToolUtil.tokenParse(ap.getToken());
			int unitIndex = claims.get("unitIndex").asInt();
			List<String> userAuthority = claims.get("writeAuthority").asList(String.class);
			ap.setUnitIndex(unitIndex);
			if(!userAuthority.contains("2016")){
				rp.setMessage("权限不足");
				rp.setResult("fail");
				return (T)rp;
			}
			amd.update(ap);
			rp.setMessage("区域修改成功");
			rp.setResult("success");
		}catch(Exception e){
			e.printStackTrace();
			rp.setMessage("区域修改失败");
			rp.setResult("fail");
		}
		return (T)rp;
	}

	@Override
	public <T, P> T findById(P p) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T, P> T findAll(P p) {
		// TODO Auto-generated method stub
		ResponsePojo rp = new ResponsePojo();
		AreaPojo ap = (AreaPojo)p;
		Map<String, Claim> claims = ToolUtil.tokenParse(ap.getToken());
		int unitIndex = claims.get("unitIndex").asInt();
		List<String> userAuthority = claims.get("writeAuthority").asList(String.class);
		ap.setUnitIndex(unitIndex);
		if(!userAuthority.contains("1016") && !userAuthority.contains("2016")){
			rp.setMessage("权限不足");
			rp.setResult("fail");
			return (T)rp;
		}
		int pages = ap.getPages();
		int rows = ap.getRows();
		int begin = (pages - 1) * rows;
		ap.setBegin(begin);
		
		List<AreaPojo> aps = amd.find(ap);
		int count = amd.count(ap);
		ResultPojo rp1 = new ResultPojo();
		rp1.setTotal(count);
		rp1.setData(aps);
		
		rp.setMessage("");
		rp.setResult("success");
		rp.setSource(rp1);
		return (T)rp;
	}

}
