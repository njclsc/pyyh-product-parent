package com.zh.collection.business.task;

import java.util.HashMap;

import com.zh.collection.pojo.AreaPojo;
import com.zh.collection.pojo.DevicePojo;
import com.zh.collection.pojo.TimlyPojo;

public class BusinessForDoorTask implements Runnable{
	private TimlyPojo tp;
	private HashMap<String, DevicePojo> devices;
	private HashMap<String, AreaPojo> areas;
	public BusinessForDoorTask(TimlyPojo tp, HashMap<String, DevicePojo> devices, HashMap<String, AreaPojo> areas){
		this.tp = tp;
		this.devices = devices;
		this.areas = areas;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		String oldDevId = tp.getOldDeviceId();
		String curDevId = tp.getCurrentDeviceId();
		System.out.println("door operate " + oldDevId + "  " + curDevId);
	}

}
