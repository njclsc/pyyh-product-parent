package com.zh.collection.business.task;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import com.zh.collection.pojo.AreaPojo;
import com.zh.collection.pojo.CachePojo;
import com.zh.collection.pojo.DevicePojo;
import com.zh.collection.pojo.RulePojo;
import com.zh.collection.pojo.TagPojo;
import com.zh.collection.pojo.TimlyPojo;
import com.zh.collection.pojo.UnitPojo;
import com.zh.collection.pojo.VehiclePojo;
import com.zh.collection.util.ContainerUtil;

public class BusinessForSaveAlarm1 implements Runnable{
	private TimlyPojo tp;
	private CachePojo<String, UnitPojo, AreaPojo, DevicePojo, TagPojo, RulePojo, TimlyPojo, VehiclePojo> cache;
	private int unitIndex;
	public BusinessForSaveAlarm1(TimlyPojo tp, CachePojo<String, UnitPojo, AreaPojo, DevicePojo, TagPojo, RulePojo, TimlyPojo, VehiclePojo> cache, int unitIndex){
		this.tp = tp;
		this.cache = cache;
		this.unitIndex = unitIndex;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		Connection con = null;
		try{
			con = ContainerUtil.getDataSource().getConnection();
			String curDev = tp.getCurrentDeviceId();
			String hbDev = tp.getHbStationId();
			String curTime = ContainerUtil.getSdf().format(new Date());
			String tagId = tp.getTagId();
			int vehicleId = cache.getTagCache().get(tagId).getVehicleIndex();
			VehiclePojo vp = cache.getVehicleCache().get("" + vehicleId);
			DevicePojo dp = cache.getDeviceCache().get(hbDev);
			AreaPojo ap = cache.getAreaCache().get("" + dp.getAreaIndex());
			Statement stat = con.createStatement();
			StringBuffer sb = new StringBuffer();
			sb.append("insert into tb_");sb.append(unitIndex);
			sb.append("_alarm(tagId, alarmType, position, ownerName, areaName, ownerType, dateTime) values('");
			sb.append(tagId);sb.append("', ");sb.append(2);sb.append(", '', '");sb.append(vp.getOwnerName());
			sb.append("', '");sb.append(ap.getId());sb.append("','', '");
			sb.append(curTime); sb.append("')");
			stat.executeUpdate(sb.toString());
			stat.close();
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

}
