package com.zh.collection.business.task;

import java.sql.Statement;
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
					System.out.println(tp.getTagId() + "  " + tp.getHbStationId() + "  " + tp.getCurrentDeviceId() + "  " + tp.getMappingAddress());
					String localAddress = tp.getMappingAddress();
					CachePojo<String, UnitPojo, AreaPojo, DevicePojo, TagPojo, RulePojo, TimlyPojo, VehiclePojo> cache = ContainerUtil.getCaches().get(localAddress);
					HashMap<String, AreaPojo> areas = cache.getAreaCache();
					HashMap<String, DevicePojo> devices = cache.getDeviceCache();
					HashMap<String, RulePojo> rules = cache.getRuleCache();
					HashMap<String, TagPojo> tags = cache.getTagCache();
					HashMap<String, VehiclePojo> vehicles = cache.getVehicleCache();
					UnitPojo up = cache.getUnitCache().get(localAddress);
					//判断区域类型
					String hbId = tp.getHbStationId();
					String lbId = tp.getCurrentDeviceId();
					DevicePojo hdp = devices.get(hbId);
					DevicePojo ldp = devices.get(lbId);
					int hbArea = hdp.getAreaIndex();
					AreaPojo aph = areas.get("" + hbArea);
					if(ldp == null){
						ldp = new DevicePojo();
					}
					System.out.println(ldp.getAreaIndex());
					AreaPojo apl = areas.get("" + ldp.getAreaIndex());
					if(apl == null){
						apl = new AreaPojo();
					}
					//大门
					if(aph.getType() == 1 && apl.getType() == 0){
						System.out.println("into door");
					//进停车场
					}else if(aph.getType() == 2 && apl.getType() == 2){
						System.out.println("into parking");
					//出停车场
					}else if(aph.getType() == 2 && apl.getType() == 0){
						System.out.println("out parking");
					//进楼
					}else if(aph.getType() == 3 && apl.getType() == 3){
						System.out.println("into office");
					//出楼	
					}else if(aph.getType() == 3 && apl.getType() == 0){
						System.out.println("out office");
					}
				}
				

				Thread.sleep(1);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
//----------------------------------------------------------------------------
	}

	
}
