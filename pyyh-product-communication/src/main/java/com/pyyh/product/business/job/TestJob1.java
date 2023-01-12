package com.pyyh.product.business.job;

import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class TestJob1 extends QuartzJobBean{

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		// TODO Auto-generated method stub
		System.out.println(new Date() + "  job1   " + context.getScheduler());
	}

}
