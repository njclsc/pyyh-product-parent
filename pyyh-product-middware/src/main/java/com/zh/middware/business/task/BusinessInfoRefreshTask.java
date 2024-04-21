package com.zh.middware.business.task;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.zh.middware.util.ContainerUtil;

public class BusinessInfoRefreshTask extends QuartzJobBean{

	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub
		System.out.println("----------->>--" + ContainerUtil.getRemoteConfig().getDeviceInfoUrl());
	}

}
