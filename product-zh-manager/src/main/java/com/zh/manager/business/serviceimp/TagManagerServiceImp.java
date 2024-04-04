package com.zh.manager.business.serviceimp;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth0.jwt.interfaces.Claim;
import com.zh.manager.business.dao.IDeviceManagerDao;
import com.zh.manager.business.dao.ITagManagerDao;
import com.zh.manager.business.pojo.DevicePojo;
import com.zh.manager.business.pojo.ResponsePojo;
import com.zh.manager.business.pojo.TagPojo;
import com.zh.manager.business.service.IManagerService;
import com.zh.manager.util.ToolUtil;

@Service("TagManagerServiceImp")
public class TagManagerServiceImp implements IManagerService{
	@Autowired
	private ITagManagerDao tmd;
	@Override
	public <T, P> T add(P p) {
		// TODO Auto-generated method stub
		ResponsePojo rp = new ResponsePojo();
		try{
		TagPojo tp = (TagPojo)p;
		Map<String, Claim> claims = ToolUtil.tokenParse(tp.getToken());
		int unitIndex = claims.get("unitIndex").asInt();
		List<String> userAuthority = claims.get("writeAuthority").asList(String.class);
		tp.setUnitIndex(unitIndex);
		if(!userAuthority.contains("2006")){
			rp.setMessage("权限不足");
			rp.setResult("fail");
			return (T)rp;
		}
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
		}catch(Exception e){
			e.printStackTrace();
			rp.setMessage("标签ID已存在");
			rp.setResult("fail");
		}
		return (T)rp;
	}

	@Override
	public <T, P> T delete(P p) {
		// TODO Auto-generated method stub
		ResponsePojo rp = new ResponsePojo();
		TagPojo tp = (TagPojo)p;
		Map<String, Claim> claims = ToolUtil.tokenParse(tp.getToken());
		int unitIndex = claims.get("unitIndex").asInt();
		List<String> userAuthority = claims.get("writeAuthority").asList(String.class);
		tp.setUnitIndex(unitIndex);
		if(!userAuthority.contains("2006")){
			rp.setMessage("权限不足");
			rp.setResult("fail");
			return (T)rp;
		}
		tmd.delete(tp);
		tmd.deleteTimly(tp);
		rp.setMessage("标签删除成功");
		rp.setResult("success");
		return (T)rp;
	}

	@Override
	public <T, P> T update(P p) {
		// TODO Auto-generated method stub
		ResponsePojo rp = new ResponsePojo();
		TagPojo tp = (TagPojo)p;
		Map<String, Claim> claims = ToolUtil.tokenParse(tp.getToken());
		int unitIndex = claims.get("unitIndex").asInt();
		List<String> userAuthority = claims.get("writeAuthority").asList(String.class);
		tp.setUnitIndex(unitIndex);
		if(!userAuthority.contains("2006")){
			rp.setMessage("权限不足");
			rp.setResult("fail");
			return (T)rp;
		}
		tmd.update(tp);
		rp.setMessage("标签修改成功");
		rp.setResult("success");
		return (T)rp;
	}

	@Override
	public <T, P> T findById(P p) {
		// TODO Auto-generated method stub
		ResponsePojo rep = new ResponsePojo();
		TagPojo tp = (TagPojo)p;
		Map<String, Claim> claims = ToolUtil.tokenParse(tp.getToken());
		int unitIndex = claims.get("unitIndex").asInt();
		List<String> userAuthority = claims.get("writeAuthority").asList(String.class);
		tp.setUnitIndex(unitIndex);
		if(!userAuthority.contains("1006")&&!userAuthority.contains("2006")){
			rep.setMessage("权限不足");
			rep.setResult("fail");
			return (T)rep;
		}
		TagPojo rp = tmd.findById(tp);
		rep.setResult("success");
		rep.setSource(rp);
		return (T)rep;
	}

	@Override
	public <T, P> T findAll(P p) {
		// TODO Auto-generated method stub
		ResponsePojo rep = new ResponsePojo();
		TagPojo tp = (TagPojo)p;
		Map<String, Claim> claims = ToolUtil.tokenParse(tp.getToken());
		int unitIndex = claims.get("unitIndex").asInt();
		List<String> userAuthority = claims.get("writeAuthority").asList(String.class);
		tp.setUnitIndex(unitIndex);
		if(!userAuthority.contains("1006")&&!userAuthority.contains("2006")){
			rep.setMessage("权限不足");
			rep.setResult("fail");
			return (T)rep;
		}
		int pages = tp.getPages();
		int rows = tp.getRows();
		int begin = (pages - 1) * rows;
		List<TagPojo> rp = tmd.findAll(tp);
		rep.setResult("success");
		rep.setSource(rp);
		return (T)rep;
	}

}
