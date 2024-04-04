package com.pyyh.product.login.business.serviceimp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pyyh.product.login.business.dao.ILoginDao;
import com.pyyh.product.login.business.service.ILoginService;
import com.pyyh.product.login.pojo.MenuPojo;
import com.pyyh.product.login.pojo.ResponsePojo;
import com.pyyh.product.login.pojo.RolePojo;
import com.pyyh.product.login.pojo.UserPojo;
import com.pyyh.product.login.util.TokenUtil;

@Service("LoginServiceImp")
public class LoginServiceImp implements ILoginService{
	@Autowired
	private ILoginDao loginDao;
	
	@Override
	public Object loginCheck(UserPojo user) {
		// TODO Auto-generated method stub
		ResponsePojo rp = new ResponsePojo();
		try{
			UsernamePasswordToken token = new UsernamePasswordToken(user.getUnitIndex() + "-->" + user.getAccountName(), user.getPassword());
			Subject subject = SecurityUtils.getSubject();
			subject.login(token);
			user.setLogin(true);
			rp.setMessage("登录成功");
			rp.setResult("success");
			//设置token元素
			UserPojo _user = loginDao.findUser(user);
			user.setUnitType(_user.getUnitType());
			//加载权限资源
			JSONObject infos = loadMenu(user);
			rp.setToken(TokenUtil.createToken(user));
			rp.setSource(infos);
		}catch(UnknownAccountException e){
			rp.setMessage(user.getAccountName() + " 账号不存在");
			rp.setResult("fail");
		}catch(IncorrectCredentialsException e){
			rp.setMessage("密码错误");
			rp.setResult("fail");
		}
		return rp;
	}
	
	private JSONObject loadMenu(UserPojo user){
		//获取userId
		UserPojo up = loginDao.findUser(user);
		user.setId(up.getId());
		//获取角色
		RolePojo rp = loginDao.findRole(user);
		user.setRoleIndex(rp.getId());
		String[] rwInfo = rp.getAuthority().split(",");
		//菜单加载比较时使用
		List<String> children = new ArrayList<>();
		//权限加载时使用
		List<String> readAuthority = new ArrayList<>();
		List<String> writeAuthority = new ArrayList<>();
		for(String s : rwInfo){
			int menuIndex = Integer.parseInt(s);
			if(menuIndex < 2000 && menuIndex > 1000){
				children.add("" + (menuIndex - 1000));
				readAuthority.add("" + s);
			}else if(menuIndex > 2000){
				children.add("" + (menuIndex - 2000));
				writeAuthority.add("" + s);
			}
		}
		user.setReadAuthority(readAuthority);
		user.setWriteAuthority(writeAuthority);
		
		//获取一级菜单
		MenuPojo mlv1 = new MenuPojo();
		mlv1.setParentIndex(-1);
		List<MenuPojo> mlv1s = loginDao.loadMenu(mlv1);
		Iterator<MenuPojo> itrLv1 = mlv1s.iterator();
		while(itrLv1.hasNext()){
			MenuPojo mp = itrLv1.next();
			MenuPojo mlv2 = new MenuPojo();
			mlv2.setParentIndex(mp.getId());
			List<MenuPojo> mlv2s = loginDao.loadMenu(mlv2);
			Iterator<MenuPojo> itr = mlv2s.iterator();
			while(itr.hasNext()){
				MenuPojo mp1 = itr.next();
				if(!children.contains("" + mp1.getId())){
					itr.remove();
				}
			}
			mp.setChildren(mlv2s);
			if(mp.getChildren().size() == 0 && mp.getId() != 1 && mp.getId() != 3){
				itrLv1.remove();
			}
		}
		JSONObject obj = new JSONObject();
		obj.put("readAuthority", readAuthority);
		obj.put("wirtyAuthority", writeAuthority);
		obj.put("menuInfo", JSONArray.toJSON(mlv1s));
		return obj;
	}

}
