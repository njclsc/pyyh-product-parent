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
import com.zh.collection.pojo.VehiclePojo;
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
					CachePojo<String, UnitPojo, AreaPojo, DevicePojo, TagPojo, RulePojo, TimlyPojo, VehiclePojo> cache = ContainerUtil.getCaches().get(localAddress);
					HashMap<String, AreaPojo> areas = cache.getAreaCache();
					HashMap<String, DevicePojo> devices = cache.getDeviceCache();
					HashMap<String, RulePojo> rules = cache.getRuleCache();
					HashMap<String, TagPojo> tags = cache.getTagCache();
					
					
					//expire
					long diff = System.currentTimeMillis() - ContainerUtil.getSdf().parse(tags.get(tp.getTagId()).getInstallDate()).getTime();
					if(diff >= 0 && !tags.get(tp.getTagId()).isExpire()){
						tags.get(tp.getTagId()).setExpire(true);
						UnitPojo up = cache.getUnitCache().get(localAddress);
						threadPool.execute(new BusinessForTagExpire(tp, cache, up.getId()));
					}else if(diff < 0 && tags.get(tp.getTagId()).isExpire()){
						tags.get(tp.getTagId()).setExpire(false);
					}
					
					
					//door operate 
					String oldDevId = tp.getOldDeviceId();
					String curDevId = tp.getCurrentDeviceId();
					int hbArea = devices.get(tp.getHbStationId()).getAreaIndex();
					if(oldDevId != null && curDevId != null && !oldDevId.equals("") && !curDevId.equals("") && hbArea < 1){
						int curAreaType = areas.get("" + devices.get(curDevId).getAreaIndex()).getType();
						int oldAreaType = areas.get("" + devices.get(oldDevId).getAreaIndex()).getType();
						if(oldAreaType < 2 && curAreaType < 2){
							threadPool.execute(new BusinessForDoorTask(tp, devices, areas, rules.get("" + 2), rules.get("" + 0), rules.get("" + 1)));
						}else if(oldAreaType == 2 && curAreaType == 2){
							threadPool.execute(new BusinessForParkingTask(tp, devices, areas));
						}
					//apartment operate
					//由于只是一包数据判断  不可靠  有可能要增加一个oldHbStationId;
					}else if(hbArea > 0){
						int hbAreaType = areas.get("" + hbArea).getType();
						if(hbAreaType == 3){
							threadPool.execute(new BusinessForApartmentTask(tp, devices, areas));
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
