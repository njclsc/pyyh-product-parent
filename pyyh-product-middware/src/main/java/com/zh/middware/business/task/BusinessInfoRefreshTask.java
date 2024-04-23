package com.zh.middware.business.task;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zh.middware.pojos.DevicePojo;
import com.zh.middware.util.ContainerUtil;

public class BusinessInfoRefreshTask extends QuartzJobBean{

	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub
		HttpGet httpPost = new HttpGet(ContainerUtil.getRemoteConfig().getDeviceInfoUrl());
		
		CloseableHttpResponse response = null;
		try {
			response = ContainerUtil.getHttpClient().execute(httpPost);
			HttpEntity reEntity = response.getEntity();
			String data = EntityUtils.toString(reEntity);
			if(reEntity != null){
				List<DevicePojo> dps = JSONObject.parseObject(data).getJSONObject("body").getJSONArray("list").toJavaList(DevicePojo.class);
				for(DevicePojo dp : dps){
					int deviceId = Integer.parseInt(dp.getDeviceId());
					int _did = deviceId & 0xFFFF;
					StringBuffer sb = new StringBuffer();
					sb.append(Integer.toHexString((_did & 0xF000) >> 12));
					sb.append(Integer.toHexString((_did & 0xF00) >> 8));
					sb.append(Integer.toHexString((_did & 0xF0) >> 4));
					sb.append(Integer.toHexString(_did & 0xF));
					System.out.println(sb.toString().toUpperCase());
				}
			}
		} catch(Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(response != null){
				try {
					response.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		System.out.println("----------->>--" + ContainerUtil.getRemoteConfig().getDeviceInfoUrl());
		
	}

}
