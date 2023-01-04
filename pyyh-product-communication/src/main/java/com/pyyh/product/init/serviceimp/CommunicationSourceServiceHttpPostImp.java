package com.pyyh.product.init.serviceimp;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import com.pyyh.product.init.pojo.CommunicationConfigPojo;
import com.pyyh.product.init.pojo.HttpClientPojo;

public class CommunicationSourceServiceHttpPostImp extends AbstractSourceServiceImp{

	@Override
	public <T, P> T registSource(P p) throws Exception {
		// TODO Auto-generated method stub
		CommunicationConfigPojo ccp = (CommunicationConfigPojo)p;
		List<HttpClientPojo> https = ccp.getHttpClients();
		for(HttpClientPojo client : https){
			if(client.isUsed()){
				CloseableHttpClient clientBro = HttpClients.createDefault();
				String requestType = client.getRequestType();
				if(requestType.equals("post")){
					HttpPost post = new HttpPost(client.getUrl());
					if(client.getParameterType().equals("form")){
						List<BasicNameValuePair> list = new ArrayList<>();
					}else if(client.getParameterType().equals("json")){
						
					}
					System.out.println(client.getKey());
				}else if(requestType.equals("get")){
					System.out.println(client.getKey());
				}
			}
		}
		return null;
	}

}
