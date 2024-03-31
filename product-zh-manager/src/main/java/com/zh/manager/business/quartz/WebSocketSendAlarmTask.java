package com.zh.manager.business.quartz;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import javax.websocket.Session;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.alibaba.fastjson.JSONObject;
import com.zh.manager.business.pojo.PushAlarmPojo;
import com.zh.manager.business.pojo.WebSocketPushPojo;
import com.zh.manager.config.DataSourceConfiguer;
import com.zh.manager.util.ContainerUtil;

public class WebSocketSendAlarmTask extends QuartzJobBean{

	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub
		try{
			Connection con = DataSourceConfiguer.getDataSource().getConnection();
			synchronized(ContainerUtil.getEndpointSession()){
			
			Iterator<Entry<String, Session>> itr = ContainerUtil.getEndpointSession().entrySet().iterator();
			while(itr.hasNext()){
				try{
//					WebSocketPushPojo wspp = new WebSocketPushPojo();
					Entry<String, Session> entry = itr.next();
					String key = entry.getKey();
					String[] keys = key.split("_");
					Session sess = entry.getValue();
					String sql = "SELECT tb_" + keys[0] + "_alarm.id, tb_" + keys[0] + "_vehicle.ownerType, tb_" + keys[0] + "_vehicle.ownerName, tb_" + keys[0] + "_vehicle.movePhone, tb_" + keys[0] + "_area.areaName, " + 
							"tb_" + keys[0] + "_alarm.dateTime, tb_" + keys[0] + "_alarm.alarmType  FROM tb_" + keys[0] + "_alarm " +
							"LEFT JOIN tb_" + keys[0] + "_tag ON tb_" + keys[0] + "_alarm.tagId = tb_" + keys[0] + "_tag.tagId " +
							"LEFT JOIN tb_" + keys[0] + "_vehicle ON tb_" + keys[0] + "_tag.vehicleIndex = tb_" + keys[0] + "_vehicle.id " + 
							"LEFT JOIN tb_" + keys[0] + "_area ON tb_" + keys[0] + "_area.id = tb_" + keys[0] + "_alarm.areaName where 1 = 1 and tb_" + keys[0] + "_alarm.status = 1 ";
					String tmp = "";
					if(ContainerUtil.getAlarmIds().get(key).size() > 0){
						sql = sql + "and tb_" + keys[0] + "_alarm.id not in (";
						for(String i : ContainerUtil.getAlarmIds().get(key)){
							tmp = tmp + "," + i;
						}
						tmp = tmp.substring(1) + ")";
					}
					sql = sql + tmp;
					Statement stat = con.createStatement();
					ResultSet rs = stat.executeQuery(sql);
					List<String> pks = ContainerUtil.getAlarmIds().get(key);
					List<PushAlarmPojo> paps = new ArrayList<>();
					while(rs.next()){
						PushAlarmPojo pap = new PushAlarmPojo();
						pks.add(rs.getString("id"));
						int type = rs.getInt("alarmType");
						String dt = rs.getString("dateTime");
						String areaName = rs.getString("areaName");
						String ownerName = rs.getString("ownerName");
						if(type == 0){
							pap.setAlarmType("过期警报");
						}else if(type == 1){
							pap.setAlarmType("违停警报");
						}else if(type == 2){
							pap.setAlarmType("电池入楼警报");
						}
						pap.setKey(rs.getInt("id"));
						pap.setOwnerName(ownerName);
						pap.setAreaName(areaName);
						pap.setDateTime(dt);
						paps.add(pap);
					}
					if(paps.size() > 0){
						JSONObject jb = new JSONObject();
						jb.put("alarms_delete", JSONObject.toJSONString(new ArrayList<PushAlarmPojo>()));
						jb.put("alarms_add", JSONObject.toJSONString(paps));
						sess.getBasicRemote().sendText(jb.toJSONString());
						System.out.println("========>>> " + jb.toJSONString());
					}
					rs.close();
					stat.close();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			
			}
			
			
			con.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
