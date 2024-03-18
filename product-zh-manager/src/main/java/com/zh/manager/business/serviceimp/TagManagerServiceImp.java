package com.zh.manager.business.serviceimp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zh.manager.business.dao.IDeviceManagerDao;
import com.zh.manager.business.dao.ITagManagerDao;
import com.zh.manager.business.pojo.DevicePojo;
import com.zh.manager.business.pojo.ResponsePojo;
import com.zh.manager.business.pojo.TagPojo;
import com.zh.manager.business.service.IManagerService;

@Service("TagManagerServiceImp")
public class TagManagerServiceImp implements IManagerService{
	@Autowired
	private ITagManagerDao tmd;
	@Override
	public <T, P> T add(P p) {
		// TODO Auto-generated method stub
		ResponsePojo rp = new ResponsePojo();
		TagPojo tp = (TagPojo)p;
		int flag = tmd.add(tp);
		int flag1 = tmd.addTimly(tp);
		System.out.println(flag);
		if(flag > 0 && flag1 > 0){
			rp.setMessage("标签添加成功");
			rp.setResult("success");
		}else{
			rp.setMessage("标签添加失败");
			rp.setResult("fail");
		}
		return (T)rp;
	}

	@Override
	public <T, P> T delete(P p) {
		// TODO Auto-generated method stub
		TagPojo tp = (TagPojo)p;
		ResponsePojo rp = new ResponsePojo();
		tmd.delete(tp);
		tmd.deleteTimly(tp);
		rp.setMessage("标签删除成功");
		rp.setResult("success");
		return (T)rp;
	}

	@Override
	public <T, P> T update(P p) {
		// TODO Auto-generated method stub
		tmd.update((TagPojo)p);
		ResponsePojo rp = new ResponsePojo();
		rp.setMessage("标签修改成功");
		rp.setResult("success");
		return (T)rp;
	}

	@Override
	public <T, P> T findById(P p) {
		// TODO Auto-generated method stub
		TagPojo rp = tmd.findById((TagPojo)p);
		ResponsePojo rep = new ResponsePojo();
		rep.setResult("success");
		rep.setSource(rp);
		return (T)rep;
	}

	@Override
	public <T, P> T findAll(P p) {
		// TODO Auto-generated method stub
		List<TagPojo> rp = tmd.findAll((TagPojo)p);
		ResponsePojo rep = new ResponsePojo();
		rep.setResult("success");
		rep.setSource(rp);
		return (T)rep;
	}

}
