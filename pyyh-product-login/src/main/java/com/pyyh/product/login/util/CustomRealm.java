package com.pyyh.product.login.util;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.pyyh.product.login.business.dao.ILoginDao;
import com.pyyh.product.login.pojo.UserPojo;

public class CustomRealm extends AuthorizingRealm{
	@Autowired
	private ILoginDao loginDao;
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken arg0) throws AuthenticationException {
		// TODO Auto-generated method stub
		String[] unitAccount = ((String)arg0.getPrincipal()).split("-->");
		UserPojo user = new UserPojo();
		user.setAccountName(unitAccount[1]);
		user.setUnitIndex(Integer.parseInt(unitAccount[0]));
		UserPojo _user = loginDao.findUser(user);
		if(_user != null){
			SimpleAuthenticationInfo authen = new SimpleAuthenticationInfo(_user.getAccountName(), _user.getPassword(), this.getName());
			return authen;
		}else{
			return null;
		}
		
	}

}
