package com.zh.manager.business.serviceimp;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth0.jwt.interfaces.Claim;
import com.zh.manager.business.dao.IUnitManagerDao;
import com.zh.manager.business.pojo.ResponsePojo;
import com.zh.manager.business.pojo.ResultPojo;
import com.zh.manager.business.pojo.RolePojo;
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
		Map<String, Claim> tkInf = ToolUtil.tokenParse(up.getToken());
		int unitIndex = tkInf.get("unitIndex").asInt();
		List<String> userAuthority = tkInf.get("writeAuthority").asList(String.class);
		if(tkInf.get("unitType").asInt() != 0 && !userAuthority.contains("2013")){
			rp.setMessage("单位添加失败, 权限不足!");
			rp.setResult("fail");
			return (T)rp;
		}
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
		ResponsePojo rp = new ResponsePojo();
		try{
			UnitPojo up = (UnitPojo)p;
			Map<String, Claim> tkInf = ToolUtil.tokenParse(up.getToken());
			if(tkInf.get("unitType").asInt() != 0){
				rp.setMessage("单位删除失败, 权限不足!");
				rp.setResult("fail");
				return (T)rp;
			}
			umd.delete(up);
			umd.dropTable(up);
			rp.setMessage("单位删除成功!");
			rp.setResult("success");
			
		}catch(Exception e){
			e.printStackTrace();
			rp.setMessage("单位删除失败!");
			rp.setResult("fail");
		}
		return (T)rp;
	}

	@Override
	public <T, P> T update(P p) {
		// TODO Auto-generated method stub
		ResponsePojo rp = new ResponsePojo();
		UnitPojo up = (UnitPojo)p;
		Map<String, Claim> tkInf = ToolUtil.tokenParse(up.getToken());
		if(tkInf.get("unitType").asInt() != 0){
			rp.setMessage("单位修改失败, 权限不足!");
			rp.setResult("fail");
			return (T)rp;
		}
		umd.update(up);
		rp.setMessage("单位修改成功!");
		rp.setResult("success");
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
		UnitPojo ap = (UnitPojo)p;
		Map<String, Claim> tkInf = ToolUtil.tokenParse(ap.getToken());
		if(tkInf.get("unitType").asInt() != 0){
			rp.setMessage("单位查询失败, 权限不足!");
			rp.setResult("fail");
			return (T)rp;
		}
		int pages = ap.getPages();
		int rows = ap.getRows();
		int begin = (pages - 1) * rows;
		ap.setBegin(begin);
		List<UnitPojo> aps = umd.find(ap);
		int count = umd.count(ap);
		ResultPojo rp1 = new ResultPojo();
		rp1.setTotal(count);
		rp1.setData(aps);
		ResponsePojo rep = new ResponsePojo();
		rep.setMessage("");
		rep.setResult("success");
		rep.setSource(rp1);
		return (T)rep;
	}

}
