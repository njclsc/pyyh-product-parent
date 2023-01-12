package com.pyyh.product.init;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.pyyh.product.init.serviceimp.JobServiceTest1;
import com.pyyh.product.init.serviceimp.JobServiceTest2;

@Component
public class InitStatusListener implements ApplicationListener<ContextRefreshedEvent>{

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		// TODO Auto-generated method stub
		loadJob();
	}
	private void loadJob(){
		try {
			new JobServiceTest1().registJob(null);
			new JobServiceTest2().registJob(null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
