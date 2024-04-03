package com.zh.manager.business.serviceimp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth0.jwt.interfaces.Claim;
import com.zh.manager.business.dao.IRoleManagerDao;
import com.zh.manager.business.pojo.MenuPojo;
import com.zh.manager.business.pojo.ResponsePojo;
import com.zh.manager.business.pojo.ResultPojo;
import com.zh.manager.business.pojo.RolePojo;
import com.zh.manager.business.service.IManagerService;
import com.zh.manager.business.service.IMenuService;
import com.zh.manager.util.ToolUtil;
@Service("RoleManagerServiceImp")
public class RoleManagerServiceImp implements IManagerService, IMenuService{
	@Autowired
	private IRoleManagerDao rmd;
	@Override
	public <T, P> T add(P p) {
		// TODO Auto-generated method stub
		ResponsePojo rp = new ResponsePojo();
		try{
			
		int flag = rmd.add((RolePojo)p);
		if(flag > 0){
			rp.setMessage("角色添加成功");
			rp.setResult("success");
		}else{
			rp.setMessage("角色添加失败");
			rp.setResult("fail");
		}
		}catch(Exception e){
			e.printStackTrace();
			rp.setMessage("角色添加失败");
			rp.setResult("fail");
		}
		return (T)rp;
	}

	@Override
	public <T, P> T delete(P p) {
		// TODO Auto-generated method stub
		ResponsePojo rp = new ResponsePojo();
		rmd.delete((RolePojo)p);
		rp.setMessage("角色删除成功");
		rp.setResult("success");
		return (T)rp;
	}

	@Override
	public <T, P> T update(P p) {
		// TODO Auto-generated method stub
		ResponsePojo rp = new ResponsePojo();
		RolePojo ap = (RolePojo)p;
		rmd.update(ap);
		rp.setMessage("角色修改成功");
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
		RolePojo ap = (RolePojo)p;
		Map<String, Claim> tkInf = ToolUtil.tokenParse(ap.getToken());
		ap.setUnitIndex(tkInf.get("unitIndex").asInt());
		int pages = ap.getPages();
		int rows = ap.getRows();
		int begin = (pages - 1) * rows;
		ap.setBegin(begin);
		List<RolePojo> aps = rmd.find(ap);
		int count = rmd.count(ap);
		ResultPojo rp = new ResultPojo();
		rp.setTotal(count);
		rp.setData(aps);
		ResponsePojo rep = new ResponsePojo();
		rep.setMessage("");
		rep.setResult("success");
		rep.setSource(rp);
		return (T)rep;
	}
	public Object loadMenu(String token){
		Map<String, Claim> tkInf = ToolUtil.tokenParse(token);
		//权限菜单按unitType分割(> 0 剔除单位管理    == 0  添加单位管理)
		int unitType = tkInf.get("unitType").asInt();
		MenuPojo m1v1 = new MenuPojo();
		m1v1.setParentIndex(-1);
		List<MenuPojo> mlv1s = rmd.loadMenu(m1v1);
		Iterator<MenuPojo> itrLv1 = mlv1s.iterator();
		while(itrLv1.hasNext()){
			MenuPojo mp = itrLv1.next();
			mp.setName(mp.getMenuName());
			MenuPojo mlv2 = new MenuPojo();
			mlv2.setParentIndex(mp.getId());
			if(unitType > 0){	
				mlv2.setExcludeIndex(13);
			}
			List<MenuPojo> mlv2s = rmd.loadMenu(mlv2);
			for(MenuPojo _mp : mlv2s){
				_mp.setChildren(new ArrayList<MenuPojo>());
				_mp.setName(_mp.getMenuName());
				MenuPojo mpr = new MenuPojo();
				mpr.setId(1000 + _mp.getId());
				mpr.setName("可读");
				MenuPojo mpw = new MenuPojo();
				mpw.setId(2000 + _mp.getId());
				mpw.setName("可写");
				_mp.getChildren().add(mpr);_mp.getChildren().add(mpw);
			}
			mp.setChildren(mlv2s);
			if(mp.getChildren().size() == 0){
				itrLv1.remove();
			}
		}
		return (Object)mlv1s;
	}
}
