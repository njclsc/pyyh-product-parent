package com.zh.manager.business.quartz;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.websocket.Session;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.zh.manager.business.pojo.WebSocketPushPojo;
import com.zh.manager.config.DataSourceConfiguer;
import com.zh.manager.util.ContainerUtil;

public class WebSocketSendAlarmTask extends QuartzJobBean{

	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub
		try{
			Connection con = DataSourceConfiguer.getDataSource().getConnection();
			Iterator<Entry<String, Session>> itr = ContainerUtil.getEndpointSession().entrySet().iterator();
			while(itr.hasNext()){
				try{
					WebSocketPushPojo wspp = new WebSocketPushPojo();
					Entry<String, Session> entry = itr.next();
					String[] keys = entry.getKey().split("_");
					Session sess = entry.getValue();
					String sql = "SELECT tb_" + keys[0] + "_alarm.id, tb_" + keys[0] + "_vehicle.ownerType, tb_" + keys[0] + "_vehicle.ownerName, tb_" + keys[0] + "_vehicle.movePhone, tb_" + keys[0] + "_area.areaName, " + 
							"tb_" + keys[0] + "_alarm.dateTime, tb_" + keys[0] + "_alarm.alarmType  FROM tb_" + keys[0] + "_alarm " +
							"LEFT JOIN tb_" + keys[0] + "_tag ON tb_" + keys[0] + "_alarm.tagId = tb_" + keys[0] + "_tag.tagId " +
							"LEFT JOIN tb_" + keys[0] + "_vehicle ON tb_" + keys[0] + "_tag.vehicleIndex = tb_" + keys[0] + "_vehicle.id " + 
							"LEFT JOIN tb_" + keys[0] + "_area ON tb_" + keys[0] + "_area.id = tb_" + keys[0] + "_alarm.areaName where 1 = 1 ";
					
//					if(ContainerUtil.getAlarmIds().size() > 0){
//						sql = sql + "and tb_" + keys[0] + "_alarm.id not in (";
//						String tmp = "";
//						for(Integer i : ContainerUtil.getAlarmIds()){
//							tmp = tmp + ", i";
//						}
//						tmp = tmp + ")";
//					}
					System.out.println("========>>> " + sql);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			con.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
