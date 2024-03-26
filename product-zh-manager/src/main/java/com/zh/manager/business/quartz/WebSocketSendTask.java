package com.zh.manager.business.quartz;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.websocket.Session;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.alibaba.fastjson.JSONObject;
import com.zh.manager.business.pojo.WebSocketPushDevPojo;
import com.zh.manager.business.pojo.WebSocketPushPojo;
import com.zh.manager.business.pojo.WebSocketPushSevenDayPojo;
import com.zh.manager.config.DataSourceConfiguer;
import com.zh.manager.util.ContainerUtil;

public class WebSocketSendTask extends QuartzJobBean{
	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub
		try {
			Connection con = DataSourceConfiguer.getDataSource().getConnection();
			Iterator<Entry<String, Session>> itr = ContainerUtil.getEndpointSession().entrySet().iterator();
			while(itr.hasNext()){
				try{
					WebSocketPushPojo wspp = new WebSocketPushPojo();
					Entry<String, Session> entry = itr.next();
					String[] keys = entry.getKey().split("_");
//					String[] keys = "1_2".split("_");
					Session sess = entry.getValue();
					//注册总数
					String sql = "select count(*) as rgNum from tb_" + keys[0] + "_vehicle where status = 0";
					Statement stat = con.createStatement();
					ResultSet rs = stat.executeQuery(sql);
					rs.next();
					int total = rs.getInt("rgNum");
					rs.close();
					stat.close();
					wspp.setRegistNumber(total);
					//今日注册数
					String today = ContainerUtil.getSdf().format(new Date()).split(" ")[0];
					String sql1 = "select count(*) as todayNum from tb_" + keys[0] + 
							"_vehicle where status = 0 and registDate LIKE '" + today + "%'";
					Statement stat1 = con.createStatement();
					ResultSet rs1 = stat1.executeQuery(sql1);
					rs1.next();
					int todayNum = rs1.getInt("todayNum");
					rs1.close();
					stat1.close();
					wspp.setTodayRegistNumber(todayNum);
					//区域车辆
					String sql2 = "select count(*) as intoNum from tb_" + keys[0] + "_vehicle where position != 'out'";
					Statement stat2 = con.createStatement();
					ResultSet rs2 = stat2.executeQuery(sql2);
					rs2.next();
					int intoNum = rs2.getInt("intoNum");
					rs2.close();
					stat2.close();
					wspp.setAreaNumber(intoNum);
					//近7日登记车辆
					long cur = System.currentTimeMillis();
					WebSocketPushSevenDayPojo wspsdp = new WebSocketPushSevenDayPojo();
					wspsdp.setDate(new ArrayList<String>());
					wspsdp.setRegistNumber(new ArrayList<Integer>());
					for(int i = 6; i >= 0; i--){
						long bt = cur - (i * 86400000L);
						String sbt = ContainerUtil.getSdf().format(bt).split(" ")[0];
						String sql3 = "select count(*) as nearDay from tb_" + keys[0] + "_vehicle where status = 0 and registDate like '" +
								sbt + "%'";
						Statement stat3 = con.createStatement();
						ResultSet rs3 = stat3.executeQuery(sql3);
						rs3.next();
						int nearDayNum = rs3.getInt("nearDay");
						rs3.close();
						stat3.close();
						wspsdp.getDate().add(sbt.substring(5));
						wspsdp.getRegistNumber().add(nearDayNum);
					}
//					long begin = cur - 604800000L;
//					String[] _begin = ContainerUtil.getSdf().format(begin).split(" ");
//					String[] _cur = ContainerUtil.getSdf().format(cur).split(" ");
//					String begin1 = _begin[0] + " 00:00:00";
//					String end = _cur[0] + " 23:59:59";
//					String sql3 = "select count(*) as nearDay from tb_" + keys[0] + "_vehicle where status = 0 and registDate between '" + 
//							begin1 + "' and '" + end + "'";
//					Statement stat3 = con.createStatement();
//					ResultSet rs3 = stat3.executeQuery(sql3);
//					rs3.next();
//					int nearDayNum = rs3.getInt("nearDay");
//					rs3.close();
//					stat3.close();
					wspp.setNearNum(wspsdp);
					
					//设备状态
					wspp.setDevNum(new ArrayList<WebSocketPushDevPojo>());
					WebSocketPushDevPojo nomoreDev = new WebSocketPushDevPojo();
					String sql4 = "select count(*) as devNum from tb_" + keys[0] + "_device where status = 0";
					Statement stat4 = con.createStatement();
					ResultSet rs4 = stat4.executeQuery(sql4);
					rs4.next();
					int devNum = rs4.getInt("devNum");
					nomoreDev.setValue(devNum);
					nomoreDev.setName("在线");
//					wspp.setDevNum(devNum);
					rs4.close();
					stat4.close();
					wspp.getDevNum().add(nomoreDev);
					//问题设备
					WebSocketPushDevPojo excDev = new WebSocketPushDevPojo();
					String sql5 = "select count(*) as excNum from tb_" + keys[0] + "_device where status > 0";
					Statement stat5 = con.createStatement();
					ResultSet rs5 = stat5.executeQuery(sql5);
					rs5.next();
					int excNum = rs5.getInt("excNum");
//					wspp.setExcDevNum(excNum);
//					wspp.setNomoreNum(norNum);
					excDev.setValue(excNum);
					excDev.setName("离线");
					rs5.close();
					stat5.close();
					wspp.getDevNum().add(excDev);
					//报警
					
//					System.out.println(total + "  " + todayNum + "  " + intoNum + "  " + nearDayNum + "  " + devNum + "  " + 
//					excNum + "  " + norNum);
					sess.getBasicRemote().sendText(JSONObject.toJSONString(wspp));
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			con.close();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
