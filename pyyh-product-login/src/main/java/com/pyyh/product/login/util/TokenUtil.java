package com.pyyh.product.login.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.pyyh.product.login.pojo.UserPojo;

public class TokenUtil {
	private static String JWT_ALG = "HS256";
	private static String JWT_TYPE = "JWT";
	private static String JWT_SECRET = "zhdz";
	public static String createToken(Object info){
		//jwt头
		Map<String,Object> h_map=new HashMap<>();
		h_map.put("alg", JWT_ALG);
		h_map.put("typ", JWT_TYPE);
		//jwt体
		UserPojo user = (UserPojo)info;
		Map<String,Object> p_map=new HashMap<>();
		p_map.put("id", user.getId());
		p_map.put("accountName", user.getAccountName());
		p_map.put("unitIndex", user.getUnitIndex());
		p_map.put("isLogin", user.isLogin());
		p_map.put("authority", user.getSource());
		return JWT.create().withHeader(h_map).withIssuedAt(new Date())
				.withExpiresAt(new Date(System.currentTimeMillis() + (60L * 24L * 3600L * 1000L))).withPayload(p_map)
				.sign(Algorithm.HMAC256(JWT_SECRET));
	}
}
