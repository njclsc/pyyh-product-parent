package com.pyyh.product.init;

import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.pyyh.product.init.pojo.QuartzConfigPojo;
import com.pyyh.product.init.serviceimp.JobServiceImp;
import com.pyyh.product.init.serviceimp.QuartzJobSourceServiceImp;

@Component
public class InitStatusListener implements ApplicationListener<ContextRefreshedEvent>{
	@Autowired
	private Scheduler scheduler;
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		// TODO Auto-generated method stub
		loadJob(scheduler);
	}
	private void loadJob(Scheduler scheduler){
		try {
//			new JobServiceTest1().registJob(scheduler);
//			new JobServiceTest2().registJob(scheduler);
			QuartzJobSourceServiceImp quartzService = new QuartzJobSourceServiceImp();
			QuartzConfigPojo quartzConfig = quartzService.loadSource("business-config/quartzJob-config.pyyh");
			quartzConfig.setScheduler(scheduler);
			for(QuartzConfigPojo quartz : quartzConfig.getJobs()){
				new JobServiceImp(quartzConfig).registJob(null);
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
