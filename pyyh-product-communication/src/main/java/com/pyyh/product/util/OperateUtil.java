package com.pyyh.product.util;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

public class OperateUtil {

	private static Scheduler scheduler;

	public static Scheduler getScheduler() {
		if(scheduler == null){
			synchronized(OperateUtil.class){
				if(scheduler == null){
					try {
						scheduler = new StdSchedulerFactory().getScheduler();
						scheduler.start();
					} catch (SchedulerException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		return scheduler;
	}

	
}
