package com.pyyh.product.manager.util;

import java.util.Map;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;

public class ToolUtil {

	public static Map<String, Claim> tokenParse(String token){
		return JWT.decode(token).getClaims();
	}
}
