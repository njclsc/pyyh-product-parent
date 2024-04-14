package com.zh.manager.business.quartz;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import com.zh.manager.business.pojo.WebSocketPushDevPojo;
import com.zh.manager.business.pojo.WebSocketPushPojo;
import com.zh.manager.business.pojo.WebSocketPushSevenDayPojo;
import com.zh.manager.config.DataSourceConfiguer;
import com.zh.manager.util.ContainerUtil;

public class WebSocketSendTask extends QuartzJobBean {
	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub
		try {
			Connection con = DataSourceConfiguer.getDataSource().getConnection();
			Iterator<Entry<String, Session>> itr = ContainerUtil.getEndpointSession().entrySet().iterator();
			while (itr.hasNext()) {
				try {
					WebSocketPushPojo wspp = new WebSocketPushPojo();
					Entry<String, Session> entry = itr.next();
					String[] keys = entry.getKey().split("_");
					// String[] keys = "1_2".split("_");
					Session sess = entry.getValue();
					// 注册总数
					String sql = "select count(*) as rgNum from tb_" + keys[0] + "_vehicle where status = 0";
					Statement stat = con.createStatement();
					ResultSet rs = stat.executeQuery(sql);
					rs.next();
					int total = rs.getInt("rgNum");
					rs.close();
					stat.close();
					wspp.setRegistNumber(total);
					// 今日注册数
					String today = ContainerUtil.getSdf().format(new Date()).split(" ")[0];
					String sql1 = "select count(*) as todayNum from tb_" + keys[0]
							+ "_vehicle where status = 0 and registDate LIKE '" + today + "%'";
					Statement stat1 = con.createStatement();
					ResultSet rs1 = stat1.executeQuery(sql1);
					rs1.next();
					int todayNum = rs1.getInt("todayNum");
					rs1.close();
					stat1.close();
					wspp.setTodayRegistNumber(todayNum);
					// 区域车辆
					String sql2 = "select count(*) as intoNum from tb_" + keys[0] + "_vehicle where position != 'out'";
					Statement stat2 = con.createStatement();
					ResultSet rs2 = stat2.executeQuery(sql2);
					rs2.next();
					int intoNum = rs2.getInt("intoNum");
					rs2.close();
					stat2.close();
					wspp.setAreaNumber(intoNum);
					// 近7日登记车辆
					long cur = System.currentTimeMillis();
					WebSocketPushSevenDayPojo wspsdp = new WebSocketPushSevenDayPojo();
					wspsdp.setDate(new ArrayList<String>());
					wspsdp.setRegistNumber(new ArrayList<Integer>());
					for (int i = 6; i >= 0; i--) {
						long bt = cur - (i * 86400000L);
						String sbt = ContainerUtil.getSdf().format(bt).split(" ")[0];
						String sql3 = "select count(*) as nearDay from tb_" + keys[0]
								+ "_vehicle where status = 0 and registDate like '" + sbt + "%'";
						Statement stat3 = con.createStatement();
						ResultSet rs3 = stat3.executeQuery(sql3);
						rs3.next();
						int nearDayNum = rs3.getInt("nearDay");
						rs3.close();
						stat3.close();
						wspsdp.getDate().add(sbt.substring(5));
						wspsdp.getRegistNumber().add(nearDayNum);
					}
					// long begin = cur - 604800000L;
					// String[] _begin =
					// ContainerUtil.getSdf().format(begin).split(" ");
					// String[] _cur =
					// ContainerUtil.getSdf().format(cur).split(" ");
					// String begin1 = _begin[0] + " 00:00:00";
					// String end = _cur[0] + " 23:59:59";
					// String sql3 = "select count(*) as nearDay from tb_" +
					// keys[0] + "_vehicle where status = 0 and registDate
					// between '" +
					// begin1 + "' and '" + end + "'";
					// Statement stat3 = con.createStatement();
					// ResultSet rs3 = stat3.executeQuery(sql3);
					// rs3.next();
					// int nearDayNum = rs3.getInt("nearDay");
					// rs3.close();
					// stat3.close();
					wspp.setNearNum(wspsdp);
					// 设备状态
					wspp.setDevNum(new ArrayList<WebSocketPushDevPojo>());
					WebSocketPushDevPojo nomoreDev = new WebSocketPushDevPojo();
					String sql4 = "select count(*) as devNum from tb_" + keys[0] + "_device where status = 0";
					Statement stat4 = con.createStatement();
					ResultSet rs4 = stat4.executeQuery(sql4);
					rs4.next();
					int devNum = rs4.getInt("devNum");
					nomoreDev.setValue(devNum);
					nomoreDev.setName("在线");
					// wspp.setDevNum(devNum);
					rs4.close();
					stat4.close();
					wspp.getDevNum().add(nomoreDev);
					// 问题设备
					WebSocketPushDevPojo excDev = new WebSocketPushDevPojo();
					String sql5 = "select count(*) as excNum from tb_" + keys[0] + "_device where status > 0";
					Statement stat5 = con.createStatement();
					ResultSet rs5 = stat5.executeQuery(sql5);
					rs5.next();
					int excNum = rs5.getInt("excNum");
					// wspp.setExcDevNum(excNum);
					// wspp.setNomoreNum(norNum);
					excDev.setValue(excNum);
					excDev.setName("离线");
					rs5.close();
					stat5.close();
					wspp.getDevNum().add(excDev);
					// 7日报警
					//7日报警统计
					wspp.setNearAlarmNum(alarmCountFormDays(sess, con, keys[0]));
					// System.out.println(total + " " + todayNum + " " + intoNum
					// + " " + nearDayNum + " " + devNum + " " +
					// excNum + " " + norNum);
					wspp.setActions(show_x_(con, keys[0]));
					sess.getBasicRemote().sendText(JSONObject.toJSONString(wspp));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			con.close();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	public static List<PushAlarmPojo> show_x_(Connection con, String unitIndex) throws Exception{
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
			if(!show_1_InfoBuf.containsKey(tagId)){
				PushAlarmPojo pap = new PushAlarmPojo();
				pap.setTagId(tagId);
				pap.setTagNum(Integer.parseInt(tagId, 16));
				pap.setPosititon(position);
				pap.setAreaName(areaName);
				pap.setOwnerName(ownerName);
				pap.setDeviceId(deviceId);
				pap.setAction("init");
				show_1_InfoBuf.put(tagId, pap);
//				paps.add(pap);
			}else{
				PushAlarmPojo pap = show_1_InfoBuf.get(tagId);
//				System.out.println(deviceId + "   " + pap + "   " + position);
				if(deviceId != null && position != null && (!deviceId.equals(pap.getDeviceId()) || !position.equals(pap.getPosititon()))){
					if(position.equals("ioffice") || position.equals("iparking") || position.equals("into")){
						pap.setAction("进入");
					}else if(position.equals("ooffice") || position.equals("oparking") || position.equals("out")){
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
		return paps;
	}
	// 7日报警数,报警及时表保存7天数据
	private WebSocketPushSevenDayPojo alarmCountFormDays(Session sess, Connection con, String key) throws Exception {
		SimpleDateFormat sdf = ContainerUtil.getSdf();

		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		WebSocketPushSevenDayPojo wspsdp = new WebSocketPushSevenDayPojo();
		wspsdp.setDate(new ArrayList<String>());
		wspsdp.setAlarmNum(new ArrayList<Integer>());
		wspsdp.setNoOperate(new ArrayList<Integer>());
		for (int i = 6; i >= 0; i--) {
			cal.add(Calendar.DAY_OF_YEAR, (-1 * i));
			String now_dateTime = sdf.format(cal.getTime()).split(" ")[0];
			// String days_dateTime = sdf.format(cal.getTime()).split(" ")[0];
			Statement stat = con.createStatement();
			String sql = "select count(*) as num from tb_" + key + "_alarm where dateTime like '" + now_dateTime + "%'";
			ResultSet rs = stat.executeQuery(sql);
			rs.next();
			int num = rs.getInt("num");
			rs.close();
			stat.close();
			Statement stat1 = con.createStatement();
			String sql1 = "select count(*) as num from tb_" + key + "_alarm where status = 1 and dateTime like '"
					+ now_dateTime + "%'";
			ResultSet rs1 = stat1.executeQuery(sql1);
			rs1.next();
			int noop = rs1.getInt("num");
			rs1.close();
			stat1.close();
			wspsdp.getDate().add(now_dateTime.substring(5));
			wspsdp.getAlarmNum().add(num);
			wspsdp.getNoOperate().add(noop);
			cal.setTime(date);
//			System.out.println(now_dateTime + "--->" + num);
			
		}
		return wspsdp;
	}
}
