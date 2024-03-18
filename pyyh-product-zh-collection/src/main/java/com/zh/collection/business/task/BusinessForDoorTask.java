package com.zh.collection.business.task;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.concurrent.LinkedBlockingQueue;

import com.zh.collection.pojo.AreaPojo;
import com.zh.collection.pojo.DevicePojo;
import com.zh.collection.pojo.RulePojo;
import com.zh.collection.pojo.TimlyPojo;
import com.zh.collection.util.ContainerUtil;

public class BusinessForDoorTask implements Runnable{
	private TimlyPojo tp;
	private HashMap<String, DevicePojo> devices;
	private HashMap<String, AreaPojo> areas;
	private SimpleDateFormat sdf;
	private RulePojo doorRule;
	private RulePojo parkingRule;
	private RulePojo sureRule;
	private LinkedBlockingQueue<Object> inQueue;
	public BusinessForDoorTask(TimlyPojo tp, HashMap<String, DevicePojo> devices, HashMap<String, AreaPojo> areas, RulePojo doorRule, RulePojo parkingRule, RulePojo sureRule){
		this.tp = tp;
		this.devices = devices;
		this.areas = areas;
		this.sdf = ContainerUtil.getSdf();
		this.doorRule = doorRule;
		this.inQueue = ContainerUtil.getInQueue();
		this.parkingRule = parkingRule;
		this.sureRule = sureRule;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try{
			long timeFlag = doorRule.getTime();
			long currTime = sdf.parse(tp.getCurrentDeviceTime()).getTime();
			//进出延迟判断
			if(System.currentTimeMillis() - currTime < timeFlag){
				this.inQueue.offer(tp);
				return;
			}
			//违停通知判断
			String pt = tp.getPositionType();
			if(pt != null && pt.equals("into")){
				long timeFlag1 = parkingRule.getTime();
				long timeFlag2 = sureRule.getTime();
				String action = tp.getActionInfo();
				if(System.currentTimeMillis() - currTime >= timeFlag2 && action.equals("send")){
					System.out.println("保存违停记录！");
					
					tp.setActionInfo("save");
					return;
				}else if(System.currentTimeMillis() - currTime >= timeFlag1 && action.equals("none")){
					System.out.println("发送违停通知！");
					
					tp.setActionInfo("send");
					this.inQueue.offer(tp);
					return;
				}
				this.inQueue.offer(tp);
			}
			String oldDevId = tp.getOldDeviceId();
			String curDevId = tp.getCurrentDeviceId();
			AreaPojo oap = areas.get("" + devices.get(oldDevId).getAreaIndex());
			AreaPojo cap = areas.get("" + devices.get(curDevId).getAreaIndex());
//			System.out.println(oap.getType() + "   " + cap.getType());
//			System.out.println("door operate " + oldDevId + "  " + curDevId + "  " + tp.getCurrentDeviceTime());
			if(((oap.getType() == 1 && cap.getType() == 1) || cap.getType() == 1) && (!"into".equals(tp.getPositionType()))){
				System.out.println("进");
				//判断进入后  加入队列  违停通知判断
				tp.setActionInfo("none");
				tp.setPositionType("into");
				this.inQueue.offer(tp);
			}else if(((oap.getType() == 0 && cap.getType() == 0) || cap.getType() == 0) && (!"out".equals(tp.getPositionType()))){
				System.out.println("出");
				
				tp.setActionInfo("none");
				tp.setPositionType("out");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
