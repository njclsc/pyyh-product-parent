package com.zh.collection.business.task;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

import com.zh.collection.pojo.AreaPojo;
import com.zh.collection.pojo.CachePojo;
import com.zh.collection.pojo.DevicePojo;
import com.zh.collection.pojo.RulePojo;
import com.zh.collection.pojo.TagPojo;
import com.zh.collection.pojo.TimlyPojo;
import com.zh.collection.pojo.UnitPojo;
import com.zh.collection.util.ContainerUtil;

public class BusinessForSaveTask implements Runnable{
	private ThreadPoolExecutor threadPool;
	private LinkedBlockingQueue<Object> saveQueue;
	public BusinessForSaveTask(){
		this.threadPool = ContainerUtil.getThreadPool();
		this.saveQueue = ContainerUtil.getSaveQueue();
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			try{
				TimlyPojo tp = (TimlyPojo)saveQueue.poll();
				if(tp != null){
					CachePojo<String, UnitPojo, String, AreaPojo, String, DevicePojo, String, TagPojo, String, RulePojo, String, TimlyPojo> cache = ContainerUtil.getCaches().get(tp.getMappingAddress());
					String tagId = tp.getTagId();
					
					System.out.println("-->>>" + cache.getDeviceCache().get(tp.getCurrentDeviceId()).getType());
				}
				
				
				Thread.sleep(1);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

}
