package com.pyyh.product.init.pojo;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HttpClientTaskPojo {
	private CloseableHttpClient clientBro;
	private HttpPost post;
	private HttpGet get;
}
