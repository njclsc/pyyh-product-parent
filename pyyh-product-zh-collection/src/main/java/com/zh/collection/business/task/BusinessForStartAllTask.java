package com.zh.collection.business.task;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

import com.alibaba.fastjson.JSONObject;
import com.zh.collection.pojo.AreaPojo;
import com.zh.collection.pojo.CachePojo;
import com.zh.collection.pojo.DevicePojo;
import com.zh.collection.pojo.RulePojo;
import com.zh.collection.pojo.TagPojo;
import com.zh.collection.pojo.TimlyPojo;
import com.zh.collection.pojo.UnitPojo;
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
					String localAddress = tp.getMappingAddress();
					CachePojo<String, UnitPojo, String, AreaPojo, String, DevicePojo, String, TagPojo, String, List<RulePojo>, String, TimlyPojo> cache = ContainerUtil.getCaches().get(localAddress);
					HashMap<String, AreaPojo> areas = cache.getAreaCache();
					HashMap<String, DevicePojo> devices = cache.getDeviceCache();
					//door operate 
					String oldDevId = tp.getOldDeviceId();
					String curDevId = tp.getCurrentDeviceId();
					
					if(oldDevId != null && curDevId != null && !oldDevId.equals("") && !curDevId.equals("")){
						int curAreaType = areas.get("" + devices.get(curDevId).getAreaIndex()).getType();
						int oldAreaType = areas.get("" + devices.get(oldDevId).getAreaIndex()).getType();
						int hbArea = devices.get(tp.getHbStationId()).getAreaIndex();
						if(oldAreaType < 2 && curAreaType < 2 && hbArea < 1){
							threadPool.execute(new BusinessForDoorTask(tp, devices, areas));
						}else if(oldAreaType == 2 && curAreaType == 2 && hbArea < 1){
							threadPool.execute(new BusinessForParkingTask(tp, devices, areas));
						}else if(hbArea > 0){
							int hbAreaType = areas.get("" + hbArea).getType();
							if(hbAreaType == 3){
								threadPool.execute(new BusinessForApartmentTask(tp, devices, areas));
							}
						}
					}
				}
				Thread.sleep(1);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	
}
