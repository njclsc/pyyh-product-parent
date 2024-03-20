package com.zh.manager.business.serviceimp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zh.manager.business.dao.IAccountManagerDao;
import com.zh.manager.business.pojo.AccountPojo;
import com.zh.manager.business.pojo.ResponsePojo;
import com.zh.manager.business.pojo.ResultPojo;
import com.zh.manager.business.service.IManagerService;

@Service("AccountManagerServiceImp")
public class AccountManagerServiceImp implements IManagerService{
	@Autowired
	private IAccountManagerDao amd;
	@Override
	public <T, P> T add(P p) {
		// TODO Auto-generated method stub
		ResponsePojo rp = new ResponsePojo();
		try{
		AccountPojo ap = (AccountPojo)p;
		int flag = -1;
		if(ap.getPassword().equals(ap.getConfirm())){
			flag = amd.add(ap);
		}
		System.out.println(flag);
		if(flag > 0){
			rp.setMessage("用户添加成功");
			rp.setResult("success");
		}else{
			rp.setMessage("用户添加失败");
			rp.setResult("fail");
		}
		}catch(Exception e){
			e.printStackTrace();
			rp.setMessage("用户添加失败");
			rp.setResult("fail");
		}
		return (T)rp;
	}

	@Override
	public <T, P> T delete(P p) {
		// TODO Auto-generated method stub
		ResponsePojo rp = new ResponsePojo();
		amd.delete((AccountPojo)p);
		rp.setMessage("账号删除成功");
		rp.setResult("success");
		return (T)rp;
	}

	@Override
	public <T, P> T update(P p) {
		// TODO Auto-generated method stub
		ResponsePojo rp = new ResponsePojo();
		AccountPojo ap = (AccountPojo)p;
		if(ap.getPassword().equals(ap.getConfirm())){
			amd.update(ap);
			rp.setMessage("账号修改成功");
			rp.setResult("success");
		}else{
			rp.setMessage("账号修改失败");
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
		AccountPojo ap = (AccountPojo)p;
		int pages = ap.getPages();
		int rows = ap.getRows();
		int begin = (pages - 1) * rows;
		ap.setBegin(begin);
		List<AccountPojo> aps = amd.find(ap);
		int count = amd.count(ap);
		ResultPojo rp = new ResultPojo();
		rp.setTotal(count);
		rp.setData(aps);
		ResponsePojo rep = new ResponsePojo();
		rep.setMessage("");
		rep.setResult("success");
		rep.setSource(rp);
		return (T)rep;
	}

}
