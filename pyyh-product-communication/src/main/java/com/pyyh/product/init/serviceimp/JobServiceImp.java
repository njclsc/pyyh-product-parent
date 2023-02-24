package com.pyyh.product.init.serviceimp;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.pyyh.product.init.pojo.QuartzConfigPojo;

public class JobServiceImp extends AbstractJobServiceImp{
	private QuartzConfigPojo qcp;
	public JobServiceImp(QuartzConfigPojo qcp){
		this.qcp = qcp;
	}
	@Override
	public <T, E> T createJob(E e) throws Exception {
		// TODO Auto-generated method stub
		String jobGroup = qcp.getGroupJobName();
		String jobName = qcp.getJobName();
		String jobClassName = qcp.getJobClassName();
		System.out.println(jobGroup + "  " + jobName + "  " + jobClassName);
		Class<? extends QuartzJobBean> jobClass = (Class<? extends QuartzJobBean>)Class.forName(jobClassName);
		JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroup).build();
		return (T)jobDetail;
	}

	@Override
	public <T, E> T createTrigger(E e) throws Exception {
		// TODO Auto-generated method stub
		String triggerGroupName = qcp.getGroupTriggerName();
		String triggerName = qcp.getTriggerName();
		String cron = qcp.getCron();
		Trigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerName, triggerGroupName)
				.withSchedule(CronScheduleBuilder.cronSchedule(cron)).build();
		return (T)trigger;
	}

	@Override
	public <T, E> T registJob(E e) throws Exception {
		// TODO Auto-generated method stub
		Scheduler scheduler = (Scheduler)e;
		scheduler.scheduleJob(createJob(null), createTrigger(null));
		return null;
	}

}
