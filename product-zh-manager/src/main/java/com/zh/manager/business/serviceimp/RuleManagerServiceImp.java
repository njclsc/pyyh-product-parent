package com.zh.manager.business.serviceimp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zh.manager.business.dao.IRuleManagerDao;
import com.zh.manager.business.pojo.ResponsePojo;
import com.zh.manager.business.pojo.RulePojo;
import com.zh.manager.business.service.IManagerService;

@Service("RuleManagerServiceImp")
public class RuleManagerServiceImp implements IManagerService{
	@Autowired
	private IRuleManagerDao rd;
	@Override
	public <T, P> T add(P p) {
		// TODO Auto-generated method stub
		ResponsePojo rp = new ResponsePojo();
		int flag = rd.add((RulePojo)p);
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
		rd.delete((RulePojo)p);
		rp.setMessage("规则删除成功");
		rp.setResult("success");
		return (T)rp;
	}

	@Override
	public <T, P> T update(P p) {
		// TODO Auto-generated method stub
		rd.update((RulePojo)p);
		ResponsePojo rp = new ResponsePojo();
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
		List<RulePojo> rp = rd.findAll((RulePojo)p);
		ResponsePojo rep = new ResponsePojo();
		rep.setResult("success");
		rep.setSource(rp);
		return (T)rep;
	}

}
