package com.pyyh.product.login.business.serviceimp;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.pyyh.product.login.business.service.ILoginService;
import com.pyyh.product.login.pojo.ResponsePojo;
import com.pyyh.product.login.pojo.UserPojo;
import com.pyyh.product.login.util.TokenUtil;

@Service("LoginServiceImp")
public class LoginServiceImp implements ILoginService{

	@Override
	public String loginCheck(UserPojo user) {
		// TODO Auto-generated method stub
		ResponsePojo rp = new ResponsePojo();
		try{
			UsernamePasswordToken token = new UsernamePasswordToken(user.getUnitIndex() + "-->" + user.getAccountName(), user.getPassword());
			Subject subject = SecurityUtils.getSubject();
			subject.login(token);
			user.setLogin(true);
			rp.setMessage("登录成功");
			rp.setResult("success");
			//加载权限资源
			
			rp.setToken(TokenUtil.createToken(user));
		}catch(UnknownAccountException e){
			rp.setMessage(user.getAccountName() + " 账号不存在");
			rp.setResult("fail");
		}catch(IncorrectCredentialsException e){
			rp.setMessage("密码错误");
			rp.setResult("fail");
		}
		return JSONObject.toJSONString(rp);
	}
	
	private String loadMenu(UserPojo user){
		
		
		return null;
	}

}
