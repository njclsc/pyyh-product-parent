package com.zh.collection.business.task;

import java.text.SimpleDateFormat;
import java.util.HashMap;

import com.zh.collection.pojo.AreaPojo;
import com.zh.collection.pojo.DevicePojo;
import com.zh.collection.pojo.TimlyPojo;
import com.zh.collection.util.ContainerUtil;

public class BusinessForDoorTask implements Runnable{
	private TimlyPojo tp;
	private HashMap<String, DevicePojo> devices;
	private HashMap<String, AreaPojo> areas;
	private SimpleDateFormat sdf;
	public BusinessForDoorTask(TimlyPojo tp, HashMap<String, DevicePojo> devices, HashMap<String, AreaPojo> areas){
		this.tp = tp;
		this.devices = devices;
		this.areas = areas;
		this.sdf = ContainerUtil.getSdf();
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try{
		String oldDevId = tp.getOldDeviceId();
		String curDevId = tp.getCurrentDeviceId();
		AreaPojo oap = areas.get("" + devices.get(oldDevId).getAreaIndex());
		AreaPojo cap = areas.get("" + devices.get(curDevId).getAreaIndex());
		System.out.println(oap.getType() + "   " + cap.getType());
		System.out.println("door operate " + oldDevId + "  " + curDevId + "  " + tp.getCurrentDeviceTime());
		if((oap.getType() == 1 && cap.getType() == 1) || cap.getType() == 1){
			System.out.println("进");
		}else if((oap.getType() == 0 && cap.getType() == 0) || cap.getType() == 0){
			System.out.println("出");
		}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
