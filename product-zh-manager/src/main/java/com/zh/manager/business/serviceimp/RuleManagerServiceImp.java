package com.zh.manager.business.serviceimp;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth0.jwt.interfaces.Claim;
import com.zh.manager.business.dao.IRuleManagerDao;
import com.zh.manager.business.pojo.ResponsePojo;
import com.zh.manager.business.pojo.RulePojo;
import com.zh.manager.business.service.IManagerService;
import com.zh.manager.util.ToolUtil;

@Service("RuleManagerServiceImp")
public class RuleManagerServiceImp implements IManagerService{
	@Autowired
	private IRuleManagerDao rd;
	@Override
	public <T, P> T add(P p) {
		// TODO Auto-generated method stub
		ResponsePojo rp = new ResponsePojo();
		RulePojo rup = (RulePojo)p;
		Map<String, Claim> claims = ToolUtil.tokenParse(rup.getToken());
		int unitIndex = claims.get("unitIndex").asInt();
		List<String> userAuthority = claims.get("writeAuthority").asList(String.class);
		rup.setUnitIndex(unitIndex);
		if(!userAuthority.contains("2018")){
			rp.setMessage("权限不足");
			rp.setResult("fail");
			return (T)rp;
		}
		int flag = rd.add(rup);
		if(flag > 0){
			rp.setMessage("规则添加成功");
			rp.setResult("success");
		}else{
			rp.setMessage("规则添加失败");
			rp.setResult("fail");
		}
		return (T)rp;
	}

	@Override
	public <T, P> T delete(P p) {
		// TODO Auto-generated method stub
		ResponsePojo rp = new ResponsePojo();
		RulePojo rup = (RulePojo)p;
		Map<String, Claim> claims = ToolUtil.tokenParse(rup.getToken());
		int unitIndex = claims.get("unitIndex").asInt();
		List<String> userAuthority = claims.get("writeAuthority").asList(String.class);
		rup.setUnitIndex(unitIndex);
		if(!userAuthority.contains("2018")){
			rp.setMessage("权限不足");
			rp.setResult("fail");
			return (T)rp;
		}
		rd.delete(rup);
		rp.setMessage("规则删除成功");
		rp.setResult("success");
		return (T)rp;
	}

	@Override
	public <T, P> T update(P p) {
		// TODO Auto-generated method stub
		
		ResponsePojo rp = new ResponsePojo();
		RulePojo rup = (RulePojo)p;
		Map<String, Claim> claims = ToolUtil.tokenParse(rup.getToken());
		int unitIndex = claims.get("unitIndex").asInt();
		List<String> userAuthority = claims.get("writeAuthority").asList(String.class);
		rup.setUnitIndex(unitIndex);
		if(!userAuthority.contains("2018")){
			rp.setMessage("权限不足");
			rp.setResult("fail");
			return (T)rp;
		}
		
		rd.update(rup);
		rp.setMessage("规则修改成功");
		rp.setResult("success");
		return (T)rp;
	}

	@Override
	public <T, P> T findById(P p) {
		// TODO Auto-generated method stub
		RulePojo rp = rd.findById((RulePojo)p);
		ResponsePojo rep = new ResponsePojo();
		rep.setResult("success");
		rep.setSource(rp);
		return (T)rep;
	}

	@Override
	public <T, P> T findAll(P p) {
		// TODO Auto-generated method stub
		ResponsePojo rep = new ResponsePojo();
		RulePojo rup = (RulePojo)p;
		Map<String, Claim> claims = ToolUtil.tokenParse(rup.getToken());
		int unitIndex = claims.get("unitIndex").asInt();
		List<String> userAuthority = claims.get("writeAuthority").asList(String.class);
		rup.setUnitIndex(unitIndex);
		if(!userAuthority.contains("1018")&&!userAuthority.contains("2018")){
			rep.setMessage("权限不足");
			rep.setResult("fail");
			return (T)rep;
		}
		List<RulePojo> rp = rd.findAll(rup);
		rep.setResult("success");
		rep.setSource(rp);
		return (T)rep;
	}

}
