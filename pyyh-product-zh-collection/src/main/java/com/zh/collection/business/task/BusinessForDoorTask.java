package com.zh.collection.business.task;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.concurrent.LinkedBlockingQueue;

import javax.sql.DataSource;

import com.zh.collection.pojo.AreaPojo;
import com.zh.collection.pojo.DevicePojo;
import com.zh.collection.pojo.RulePojo;
import com.zh.collection.pojo.TagPojo;
import com.zh.collection.pojo.TimlyPojo;
import com.zh.collection.pojo.UnitPojo;
import com.zh.collection.pojo.VehiclePojo;
import com.zh.collection.util.ContainerUtil;

public class BusinessForDoorTask implements Runnable{
	private Connection con;
	private UnitPojo up;
	private TimlyPojo tp;
	private HashMap<String, DevicePojo> devices;
	private HashMap<String, AreaPojo> areas;
	private HashMap<String, TagPojo> tags;
	private HashMap<String, VehiclePojo> vehicles;
	private SimpleDateFormat sdf;
	private RulePojo doorRule;
	private RulePojo parkingRule;
	private RulePojo sureRule;
	private LinkedBlockingQueue<Object> inQueue;
	private LinkedBlockingQueue<Object> saveQueue;
	public BusinessForDoorTask(UnitPojo up, TimlyPojo tp, HashMap<String, TagPojo> tags, HashMap<String, VehiclePojo> vehicles, HashMap<String, DevicePojo> devices, HashMap<String, AreaPojo> areas, RulePojo doorRule, RulePojo parkingRule, RulePojo sureRule){
		this.up = up;
		this.tp = tp;
		this.devices = devices;
		this.areas = areas;
		this.tags = tags;
		this.vehicles = vehicles;
		this.sdf = ContainerUtil.getSdf();
		this.doorRule = doorRule;
		this.inQueue = ContainerUtil.getInQueue();
		this.saveQueue = ContainerUtil.getSaveQueue();
		this.parkingRule = parkingRule;
		this.sureRule = sureRule;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try{
			this.con = ContainerUtil.getDataSource().getConnection();
			long timeFlag = doorRule.getTime();
			long currTime = sdf.parse(tp.getCurrentDeviceTime()).getTime();
			//进出延迟判断
			if(System.currentTimeMillis() - currTime < timeFlag){
				tp.setActionInfo("none");
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
					tp.setSaveType(1);
					this.saveQueue.offer(tp);
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
				VehiclePojo vp = vehicles.get("" + tags.get(tp.getTagId()).getVehicleIndex());
				vp.setPosition("into");
				saveVehicleStatus(vp);
				this.inQueue.offer(tp);
			}else if(((oap.getType() == 0 && cap.getType() == 0) || cap.getType() == 0) && (!"out".equals(tp.getPositionType()))){
				System.out.println("出");
				tp.setActionInfo("none");
				tp.setPositionType("out");
				VehiclePojo vp = vehicles.get("" + tags.get(tp.getTagId()).getVehicleIndex());
				vp.setPosition("out");
				saveVehicleStatus(vp);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	//报错车辆状态
	private void saveVehicleStatus(VehiclePojo vp){
		System.out.println(up.getId() + "---" + vp.getPosition());
	}
}
