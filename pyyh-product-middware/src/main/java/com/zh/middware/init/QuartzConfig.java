package com.zh.middware.init;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zh.middware.business.task.BusinessInfoRefreshTask;
import com.zh.middware.business.task.PushDataTask;

@Configuration
public class QuartzConfig {
	@Value("${custom.quartzCron}")
	private String cron;
	@Value("${custom.quartzPushCron}")
	private String pushCron;
	@Value("${custom.jobGroupName}")
	private String jobGroupName;
	@Value("${custom.triggerGroupName}")
	private String triggerGroupName;
	
	@Bean
	public Object loadQuartz(@Autowired Scheduler scheduler){
		JobDetail jobDetail = JobBuilder.newJob(BusinessInfoRefreshTask.class).withIdentity(jobGroupName, jobGroupName).build();
		Trigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerGroupName, triggerGroupName)
				.withSchedule(CronScheduleBuilder.cronSchedule(cron)).build();
		
//		JobDetail jobDetailPush = JobBuilder.newJob(PushDataTask.class).withIdentity("pushJob", jobGroupName).build();
//		Trigger triggerPush = TriggerBuilder.newTrigger().withIdentity("pushTrigger", triggerGroupName)
//				.withSchedule(CronScheduleBuilder.cronSchedule(pushCron)).build();
		try {
			scheduler.scheduleJob(jobDetail, trigger);
//			scheduler.scheduleJob(jobDetailPush, triggerPush);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return scheduler;
	}
}
