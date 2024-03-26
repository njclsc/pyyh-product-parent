package com.zh.collection.business.task;

import java.util.HashMap;
import java.util.concurrent.LinkedBlockingQueue;

import com.zh.collection.pojo.AreaPojo;
import com.zh.collection.pojo.DevicePojo;
import com.zh.collection.pojo.TimlyPojo;
import com.zh.collection.util.ContainerUtil;

public class BusinessForApartmentTask implements Runnable{
	private TimlyPojo tp;
	private HashMap<String, DevicePojo> devices;
	private HashMap<String, AreaPojo> areas;
	private LinkedBlockingQueue<Object> saveQueue;
	public BusinessForApartmentTask(TimlyPojo tp, HashMap<String, DevicePojo> devices, HashMap<String, AreaPojo> areas){
		this.tp = tp;
		this.devices = devices;
		this.areas = areas;
		this.saveQueue = ContainerUtil.getSaveQueue();
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		String oldDevId = tp.getOldDeviceId();
		String curDevId = tp.getCurrentDeviceId();
		String hbDevId = tp.getHbStationId();
		AreaPojo cap = areas.get("" + devices.get(hbDevId).getAreaIndex());
//		System.out.println("apartment operate");
//		System.out.println(cap.getAreaName());
		if(tp.getPositionType() != null && (tp.getPositionType().equals("into") || tp.getPositionType().equals("parking"))){
//			System.out.println(cap.getAreaName());
			tp.setPositionType("apartment");
			tp.setActionInfo("alarm");
			this.saveQueue.offer(tp);
		}
	}
}
