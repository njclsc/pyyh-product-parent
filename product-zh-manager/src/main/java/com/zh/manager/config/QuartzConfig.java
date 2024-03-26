package com.zh.manager.config;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zh.manager.business.quartz.WebSocketSendTask;

@Configuration
public class QuartzConfig {
	@Value("${custom.quartzCron}")
	private String cron;
	@Value("${custom.jobGroupName}")
	private String jobGroupName;
	@Value("${custom.triggerGroupName}")
	private String triggerGroupName;
	@Value("${custom.websocketJobName}")
	private String websocketJobName;
	@Value("${custom.websocketTriggerName}")
	private String websocketTriggerName;
	@Bean
	public Object loadQuartz(@Autowired Scheduler scheduler){
		JobDetail jobDetail = JobBuilder.newJob(WebSocketSendTask.class).withIdentity(websocketJobName, jobGroupName).build();
		Trigger trigger = TriggerBuilder.newTrigger().withIdentity(websocketTriggerName, triggerGroupName)
				.withSchedule(CronScheduleBuilder.cronSchedule(cron)).build();
		try {
			scheduler.scheduleJob(jobDetail, trigger);
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(websocketJobName + "---->" + cron);
		return null;
	}
}
