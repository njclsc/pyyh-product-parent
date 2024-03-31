package com.zh.manager.business.quartz;

import java.sql.Connection;
import java.sql.ResultSet;
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

public class WebSocketCacheClearTask extends QuartzJobBean{

	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub
		try{
		Connection con = DataSourceConfiguer.getDataSource().getConnection();
		Iterator<Entry<String, List<String>>> itr = ContainerUtil.getAlarmIds().entrySet().iterator();
		synchronized(ContainerUtil.getEndpointSession()){
		ConcurrentHashMap<String, Session> sesses = ContainerUtil.getEndpointSession();
		while(itr.hasNext()){
			try{
				Entry<String, List<String>> entry = itr.next();
				String key = entry.getKey();
				List<String> ids = entry.getValue();
				String sql1 = "select id from tb_" + key.split("_")[0] + "_alarm where status = 0";
				Statement stat1 = con.createStatement();
				ResultSet rs1 = stat1.executeQuery(sql1);
				List<PushAlarmPojo> paps = new ArrayList<>();
				while(rs1.next()){
					int tmp = rs1.getInt("id");
					if(ids.contains("" + tmp)){
						PushAlarmPojo pap = new PushAlarmPojo();
						pap.setKey(tmp);
						System.out.println(key + "bbb--->" + ids.size());
						ids.remove("" + tmp);
						paps.add(pap);
						System.out.println(key + "ccc--->" + ids.size());
					}
					
				}
				if(paps.size() > 0){
					JSONObject jb = new JSONObject();
					jb.put("alarms_add", JSONObject.toJSONString(new ArrayList<PushAlarmPojo>()));
					jb.put("alarms_delete", JSONObject.toJSONString(paps));
					sesses.get(key).getBasicRemote().sendText(jb.toJSONString());
					System.out.println(key + "--------" + jb.toJSONString());
				}
				rs1.close();
				stat1.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		}
		con.close();
		}catch(Exception e){
			e.printStackTrace();
		}
//		System.out.println("--->>");
	}

}
