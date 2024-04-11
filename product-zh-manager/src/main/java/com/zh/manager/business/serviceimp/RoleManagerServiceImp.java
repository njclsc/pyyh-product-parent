package com.zh.manager.business.serviceimp;

import java.util.ArrayList;
import java.util.HashMap;
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
		RolePojo role = (RolePojo)p;
		Map<String, Claim> claims = ToolUtil.tokenParse(role.getToken());
		int unitIndex = claims.get("unitIndex").asInt();
		List<String> userAuthority = claims.get("writeAuthority").asList(String.class);
		role.setUnitIndex(unitIndex);
		String authority = role.getAuthority();
		if(!userAuthority.contains("2015")){
			rp.setMessage("权限不足");
			rp.setResult("fail");
			return (T)rp;
		}
		int flag = rmd.add(role);
		if(flag > 0){
			rp.setMessage("角色添加成功");
			rp.setResult("success");
		}else{
			rp.setMessage("角色添加失败");
			rp.setResult("fail");
		}
		}catch(Exception e){
			e.printStackTrace();
			rp.setMessage("角色名称已存在");
			rp.setResult("fail");
		}
		return (T)rp;
	}

	@Override
	public <T, P> T delete(P p) {
		// TODO Auto-generated method stub
		ResponsePojo rp = new ResponsePojo();
		RolePojo role = (RolePojo)p;
		Map<String, Claim> claims = ToolUtil.tokenParse(role.getToken());
		int unitIndex = claims.get("unitIndex").asInt();
		List<String> userAuthority = claims.get("writeAuthority").asList(String.class);
		role.setUnitIndex(unitIndex);
		if(!userAuthority.contains("2015")){
			rp.setMessage("权限不足");
			rp.setResult("fail");
			return (T)rp;
		}
		rmd.delete(role);
		rp.setMessage("角色删除成功");
		rp.setResult("success");
		return (T)rp;
	}

	@Override
	public <T, P> T update(P p) {
		// TODO Auto-generated method stub
		ResponsePojo rp = new ResponsePojo();
		RolePojo role = (RolePojo)p;
		Map<String, Claim> claims = ToolUtil.tokenParse(role.getToken());
		int unitIndex = claims.get("unitIndex").asInt();
		List<String> userAuthority = claims.get("writeAuthority").asList(String.class);
		role.setUnitIndex(unitIndex);
		if(!userAuthority.contains("2015")){
			rp.setMessage("权限不足");
			rp.setResult("fail");
			return (T)rp;
		}
		rmd.update(role);
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
		ResponsePojo rep = new ResponsePojo();
		RolePojo role = (RolePojo)p;
		Map<String, Claim> claims = ToolUtil.tokenParse(role.getToken());
		int unitIndex = claims.get("unitIndex").asInt();
		List<String> readAuthority = claims.get("writeAuthority").asList(String.class);
		List<String> userAuthority = claims.get("writeAuthority").asList(String.class);
		role.setUnitIndex(unitIndex);
		if(!readAuthority.contains("1015") && !userAuthority.contains("2015")){
			rep.setMessage("权限不足");
			rep.setResult("fail");
			return (T)rep;
		}
		int pages = role.getPages();
		int rows = role.getRows();
		int begin = (pages - 1) * rows;
		role.setBegin(begin);
		List<RolePojo> aps = rmd.find(role);
		int count = rmd.count(role);
		ResultPojo rp = new ResultPojo();
		rp.setTotal(count);
		rp.setData(aps);
		
		rep.setMessage("");
		rep.setResult("success");
		rep.setSource(rp);
		return (T)rep;
	}
	public Object loadMenu(RolePojo p){
		RolePojo rp = null;
		if(p != null){
			rp = (RolePojo)p;
		}
		Map<String, Claim> claims = ToolUtil.tokenParse(rp.getToken());
		//权限菜单按unitType分割(> 0 剔除单位管理    == 0  添加单位管理)
		int unitIndex = claims.get("unitIndex").asInt();
		rp.setUnitIndex(unitIndex);
		//修改时回显(查出角色权限设置回显)
		
		MenuPojo m1v1 = new MenuPojo();
		m1v1.setParentIndex(-1);
		List<MenuPojo> mlv1s = rmd.loadMenu(m1v1);
		Iterator<MenuPojo> itrLv1 = mlv1s.iterator();
		while(itrLv1.hasNext()){
			MenuPojo mp = itrLv1.next();
			mp.setName(mp.getMenuName());
			MenuPojo mlv2 = new MenuPojo();
			mlv2.setParentIndex(mp.getId());
			if(rp.getUnitIndex() > 0){	
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
				if(rp.getAuthority() != null){
					if(rp.getAuthority().contains("" + mpr.getId())){
						mpr.setChecked(true);
					}else{
						mpr.setChecked(false);
					}
					if(rp.getAuthority().contains("" + mpw.getId())){
						mpw.setChecked(true);
					}else{
						mpw.setChecked(false);
					}
				}
				_mp.getChildren().add(mpr);_mp.getChildren().add(mpw);
			}
			mp.setChildren(mlv2s);
//			if(mp.getChildren().size() == 0){
//				itrLv1.remove();
//			}
		}
		return (Object)mlv1s;
	}
}
