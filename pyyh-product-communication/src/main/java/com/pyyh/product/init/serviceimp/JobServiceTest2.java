package com.pyyh.product.init.serviceimp;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

import com.pyyh.product.business.job.TestJob2;

public class JobServiceTest2 extends AbstractJobServiceImp{

	@Override
	public <T, E> T createJob(E e) throws Exception {
		// TODO Auto-generated method stub
		JobDetail jobDetail = JobBuilder.newJob(TestJob2.class).withIdentity("job2", "jgour1").build();
		return (T)jobDetail;
	}

	@Override
	public <T, E> T createTrigger(E e) throws Exception {
		// TODO Auto-generated method stub
		Trigger trigger = TriggerBuilder.newTrigger().withIdentity("t2", "t1")
				.withSchedule(
						CronScheduleBuilder.cronSchedule("*/2 * * * * ?")
						).build();
		return (T)trigger;
	}

	@Override
	public <T, E> T registJob(E e) throws Exception {
		// TODO Auto-generated method stub
		this.getScheduler().scheduleJob(createJob(null), createTrigger(null));
		return null;
	}

}
