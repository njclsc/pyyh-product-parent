package com.zh.middware.business.task;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.alibaba.fastjson.JSONObject;
import com.zh.middware.pojos.DevicePojo;
import com.zh.middware.pojos.LogicDevicePojo;
import com.zh.middware.util.ContainerUtil;
import com.zh.middware.util.ToolUtil;

public class BusinessInfoRefreshTask extends QuartzJobBean{

	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub
		ToolUtil.forInitLoad();
		System.out.println("设备缓存定时更新完毕!");
	}
	
}
