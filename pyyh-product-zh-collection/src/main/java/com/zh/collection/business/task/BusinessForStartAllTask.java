package com.zh.collection.business.task;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

import com.zh.collection.pojo.TimlyPojo;
import com.zh.collection.util.ContainerUtil;

public class BusinessForStartAllTask implements Runnable{
	private ThreadPoolExecutor threadPool;
	private LinkedBlockingQueue<Object> inQueue;
	public BusinessForStartAllTask(){
		this.threadPool = ContainerUtil.getThreadPool();
		this.inQueue = ContainerUtil.getInQueue();
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			try{
				TimlyPojo tp = (TimlyPojo)inQueue.poll();
				if(tp != null){
					System.out.println(tp.getCurrentDeviceId());
				}
				
				
				
				Thread.sleep(1);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	
}
