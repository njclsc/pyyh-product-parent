package com.zh.manager.business.quartz;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.Session;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.alibaba.fastjson.JSONObject;
import com.zh.manager.business.pojo.PushAlarmPojo;
import com.zh.manager.config.DataSourceConfiguer;
import com.zh.manager.util.ContainerUtil;

public class Show_1_WebSocketTask  extends QuartzJobBean{

	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub
		Connection con = null;
		try{
			con = DataSourceConfiguer.getDataSource().getConnection();
			synchronized(ContainerUtil.getEndpointSession()){
			Iterator<Entry<String, Session>> itr = ContainerUtil.getEndpointSession().entrySet().iterator();
			while(itr.hasNext()){
				Entry<String, Session> entry = itr.next();
				String key = entry.getKey();
				String[] keys = key.split("_");
				Session sess = entry.getValue();
				show_x_(con, keys[0], sess);
			}
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
	public static void show_x_(Connection con, String unitIndex, Session sess) throws Exception{
		ConcurrentHashMap<String, PushAlarmPojo> show_1_InfoBuf = ContainerUtil.getShow_1_InfoBuf();
		String sql = "SELECT tb_" + unitIndex + "_device.deviceId, tb_" + unitIndex + "_vehicle.ownerName, "
				+ "tb_" + unitIndex + "_vehicle.position, tb_" + unitIndex + "_tag.tagId, tb_" + unitIndex + "_area.areaName "
				+ "FROM tb_" + unitIndex + "_vehicle " + 
				"LEFT JOIN tb_" + unitIndex + "_tag ON tb_" + unitIndex + "_vehicle.id = tb_" + unitIndex + "_tag.vehicleIndex " + 
				"LEFT JOIN tb_" + unitIndex + "_timly ON tb_" + unitIndex + "_tag.tagId = tb_" + unitIndex + "_timly.tagId " + 
				"LEFT JOIN tb_" + unitIndex + "_device ON tb_" + unitIndex + "_device.deviceId = tb_" + unitIndex + "_timly.hbStationId " + 
				"LEFT JOIN tb_" + unitIndex + "_area ON tb_" + unitIndex + "_device.areaIndex = tb_" + unitIndex + "_area.id";
//		System.out.println(sql);
		Statement stat = con.createStatement();
		ResultSet rs = stat.executeQuery(sql);
		List<PushAlarmPojo> paps = new ArrayList<PushAlarmPojo>();
		while(rs.next()){
			String tagId = rs.getString("tagId");
			String ownerName = rs.getString("ownerName");
			String position = rs.getString("position");
			String areaName = rs.getString("areaName");
			String deviceId = rs.getString("deviceId");
			if(!show_1_InfoBuf.contains(tagId)){
				PushAlarmPojo pap = new PushAlarmPojo();
				pap.setTagId(tagId);
				pap.setTagNum(Integer.parseInt(tagId));
				pap.setPosititon(position);
				pap.setAreaName(areaName);
				pap.setOwnerName(ownerName);
				pap.setDeviceId(deviceId);
				show_1_InfoBuf.put(tagId, pap);
				paps.add(pap);
			}else{
				PushAlarmPojo pap = show_1_InfoBuf.get(tagId);
				if(!deviceId.equals(pap.getDeviceId()) || !position.equals(pap.getPosititon())){
					if(position.equals("ioffice") || position.equals("iparking") || position.equals("into")){
						pap.setAction("进入");
					}else if(position.equals("ooffice")){
						pap.setAction("离开");
					}
					pap.setDeviceId(deviceId);
					pap.setPosititon(position);
					pap.setAreaName(areaName);
					paps.add(pap);
				}
				
				
			}
		}
		rs.close();
		stat.close();
		JSONObject jb = new JSONObject();
		jb.put("show_action", JSONObject.toJSONString(paps));
		System.out.println(jb.toJSONString());
		sess.getBasicRemote().sendText(jb.toJSONString());
	}
}
