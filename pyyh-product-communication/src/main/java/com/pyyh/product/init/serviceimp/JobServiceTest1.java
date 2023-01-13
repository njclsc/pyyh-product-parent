package com.pyyh.product.init.serviceimp;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

import com.pyyh.product.business.job.TestJob1;


public class JobServiceTest1 extends AbstractJobServiceImp{

	@Override
	public <T, E> T createJob(E e) throws Exception {
		// TODO Auto-generated method stub
		JobDetail jobDetail = JobBuilder.newJob(TestJob1.class).withIdentity("job1", "jgour1").build();
		return (T)jobDetail;
	}

	@Override
	public <T, E> T createTrigger(E e) throws Exception {
		// TODO Auto-generated method stub
		Trigger trigger = TriggerBuilder.newTrigger().withIdentity("t1", "t1")
				.withSchedule(
						CronScheduleBuilder.cronSchedule("*/2 * * * * ?")
						).build();
		return (T)trigger;
	}

	@Override
	public <T, E> T registJob(E e) throws Exception {
		// TODO Auto-generated method stub
		Scheduler scheduler = (Scheduler)e;
		System.out.println(scheduler.getSchedulerName());
		scheduler.scheduleJob((JobDetail)createJob(null), (Trigger)createTrigger(null));
		return null;
	}

}
