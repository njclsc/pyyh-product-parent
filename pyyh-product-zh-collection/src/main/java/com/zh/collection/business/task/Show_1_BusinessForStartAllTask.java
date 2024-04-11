package com.zh.collection.business.task;

import java.util.HashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

import com.zh.collection.pojo.AreaPojo;
import com.zh.collection.pojo.CachePojo;
import com.zh.collection.pojo.DevicePojo;
import com.zh.collection.pojo.RulePojo;
import com.zh.collection.pojo.TagPojo;
import com.zh.collection.pojo.TimlyPojo;
import com.zh.collection.pojo.UnitPojo;
import com.zh.collection.pojo.VehiclePojo;
import com.zh.collection.util.ContainerUtil;

public class Show_1_BusinessForStartAllTask implements Runnable{
	private ThreadPoolExecutor threadPool;
	private LinkedBlockingQueue<Object> inQueue;
	public Show_1_BusinessForStartAllTask(){
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
					System.out.println("------>>ddd " + tp.getTagId());
				}
				

				Thread.sleep(1);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
//----------------------------------------------------------------------------
	}

	
}
