package com.pyyh.product.init.serviceimp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONArray;
import com.pyyh.product.init.pojo.CommunicationConfigPojo;
import com.pyyh.product.init.pojo.HttpClientPojo;
import com.pyyh.product.init.pojo.HttpClientTaskPojo;
import com.pyyh.product.util.ContainerUtil;

public class CommunicationSourceServiceHttpGetImp extends AbstractSourceServiceImp{

	@SuppressWarnings("unchecked")
	@Override
	public <T, P> T registSource(P p) throws Exception {
		// TODO Auto-generated method stub
		CommunicationConfigPojo ccp = (CommunicationConfigPojo)p;
		List<HttpClientPojo> https = ccp.getHttpClients();
		HashMap<String, HttpClientTaskPojo> gets = new HashMap<>();
		for(HttpClientPojo client : https){
			if(client.isUsed()){
				CloseableHttpClient clientBro = HttpClients.createDefault();
				String requestType = client.getRequestType();
				HttpClientTaskPojo hctp = null;
				if(requestType.equals("get")){
					List<BasicNameValuePair> list = new ArrayList<>();
					JSONArray array = (JSONArray)client.getParameters();
					for(int i = 0; i < array.size(); i++){
						String[] kv = ((String)array.get(i)).split(":");
						list.add(new BasicNameValuePair(kv[0], kv[1]));
					}
					String kvp = EntityUtils.toString(new UrlEncodedFormEntity(list), "UTF-8");
					HttpGet get = null;
					if(kvp.length() > 0){
						get = new HttpGet(client.getUrl() + "?" + kvp);
					}else{
						get = new HttpGet(client.getUrl());
					}
					hctp = new HttpClientTaskPojo();
					hctp.setClientBro(clientBro);
					hctp.setGet(get);
				}
				if(hctp != null){
					gets.put(client.getKey(), hctp);
				}
			}
		}
		//返回get的所有端口实例
		if(gets.size() > 0){
			ContainerUtil.getCommunicationSources().put("protocol_http_get", gets);
		}
		return null;
	}

}
