package com.zh.collection.business.task;

import java.util.HashMap;

import com.zh.collection.pojo.AreaPojo;
import com.zh.collection.pojo.DevicePojo;
import com.zh.collection.pojo.TimlyPojo;

public class BusinessForParkingTask implements Runnable{
	private TimlyPojo tp;
	private HashMap<String, DevicePojo> devices;
	private HashMap<String, AreaPojo> areas;
	public BusinessForParkingTask(TimlyPojo tp, HashMap<String, DevicePojo> devices, HashMap<String, AreaPojo> areas){
		this.tp = tp;
		this.devices = devices;
		this.areas = areas;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		String oldDevId = tp.getOldDeviceId();
		String curDevId = tp.getCurrentDeviceId();
		AreaPojo oap = areas.get("" + devices.get(oldDevId).getAreaIndex());
		AreaPojo cap = areas.get("" + devices.get(curDevId).getAreaIndex());
		System.out.println(oap.getType() + "   " + cap.getType());
		System.out.println("parking operate" + oldDevId + "  " + curDevId + "  " + tp.getCurrentDeviceTime());
		if(oap.getType() == 2 && cap.getType() == 2 && oldDevId.equals(curDevId)){
			System.out.println(cap.getAreaName());
		}
	}

}
