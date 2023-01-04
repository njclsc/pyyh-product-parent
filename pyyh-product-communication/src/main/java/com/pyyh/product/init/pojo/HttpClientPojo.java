package com.pyyh.product.init.pojo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HttpClientPojo {
	private boolean used;
	private String key;
	private String url;
	private String requestType;
	private String parameterType;
}
