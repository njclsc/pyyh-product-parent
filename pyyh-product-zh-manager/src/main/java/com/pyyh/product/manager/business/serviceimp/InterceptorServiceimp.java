package com.pyyh.product.manager.business.serviceimp;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.pyyh.product.manager.business.service.IInterceptorService;
import com.pyyh.product.manager.util.ToolUtil;

@Service("InterceptorServiceimp")
public class InterceptorServiceimp implements IInterceptorService{

	@Override
	public boolean loginCheck(String token) {
		// TODO Auto-generated method stub
		if(token != null && !token.equals("")){
			Map<String, Claim> tkv = ToolUtil.tokenParse(token);
			return tkv.get("isLogin").asBoolean();
		}else{
			return false;
		}
		
	}

}
