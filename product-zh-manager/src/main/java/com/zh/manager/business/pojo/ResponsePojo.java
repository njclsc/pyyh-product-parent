package com.zh.manager.business.pojo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResponsePojo {
	private String message;
	private String result;
	private String token;
	private Object source;
	
}
