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

import com.zh.manager.business.quartz.Show_1_WebSocketTask;
import com.zh.manager.business.quartz.WebSocketCacheClearTask;
import com.zh.manager.business.quartz.WebSocketSendAlarmTask;
import com.zh.manager.business.quartz.WebSocketSendTask;

@Configuration
public class QuartzConfig {
	@Value("${custom.quartzCron}")
	private String cron;
	@Value("${custom.quartzCron_alarm}")
	private String cronAlarm;
	@Value("${custom.quartzCron_clear}")
	private String cronClear;
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
		JobDetail jobDetailAlarm = JobBuilder.newJob(WebSocketSendAlarmTask.class).withIdentity(websocketJobName + "alarm", jobGroupName).build();
		Trigger triggerAlarm = TriggerBuilder.newTrigger().withIdentity(websocketTriggerName + "alarm", triggerGroupName)
				.withSchedule(CronScheduleBuilder.cronSchedule(cronAlarm)).build();
		
		JobDetail jobDetailClear = JobBuilder.newJob(WebSocketCacheClearTask.class).withIdentity(websocketJobName + "clear", jobGroupName).build();
		Trigger triggerClear = TriggerBuilder.newTrigger().withIdentity(websocketTriggerName + "clear", triggerGroupName)
				.withSchedule(CronScheduleBuilder.cronSchedule(cronClear)).build();
		
//		JobDetail jobDetailAction = JobBuilder.newJob(Show_1_WebSocketTask.class).withIdentity(websocketJobName + "action", jobGroupName).build();
//		Trigger triggerAction = TriggerBuilder.newTrigger().withIdentity(websocketTriggerName + "action", triggerGroupName)
//				.withSchedule(CronScheduleBuilder.cronSchedule(cronAlarm)).build();
		try {
			scheduler.scheduleJob(jobDetail, trigger);
			scheduler.scheduleJob(jobDetailAlarm, triggerAlarm);
			scheduler.scheduleJob(jobDetailClear, triggerClear);
//			scheduler.scheduleJob(jobDetailAction, triggerAction);
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(websocketJobName + "---->" + cron);
		return null;
	}
}
