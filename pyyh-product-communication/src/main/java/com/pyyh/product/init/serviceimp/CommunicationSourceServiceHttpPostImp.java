package com.pyyh.product.init.serviceimp;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import com.alibaba.fastjson.JSONArray;
import com.pyyh.product.init.pojo.CommunicationConfigPojo;
import com.pyyh.product.init.pojo.HttpClientPojo;
import com.pyyh.product.init.pojo.HttpClientTaskPojo;

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
						JSONArray array = (JSONArray)client.getParameters();
						for(int i = 0; i < array.size(); i++){
							String[] kv = ((String)array.get(i)).split(":");
							list.add(new BasicNameValuePair(kv[0], kv[1]));
						}
						UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, "UTF-8");
						post.setEntity(entity);
						System.out.println("post--form   " + client.getParameters());
					}else if(client.getParameterType().equals("json")){
						StringEntity entity = new StringEntity(client.getParameters().toString(), "UTF-8");
						post.setEntity(entity);
						post.setHeader("Content-type", "application/json");
						System.out.println("post--json   " + client.getParameters().toString());
					}
					HttpClientTaskPojo hctp = new HttpClientTaskPojo();
					hctp.setClientBro(clientBro);
					hctp.setPost(post);
					System.out.println(hctp.getClientBro() + "   " + hctp.getPost());
				}
			}
		}
		//返回post的所有端口实例
		return null;
	}

}
