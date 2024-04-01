package com.zh.collection.business.task;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.sql.DataSource;

import com.zh.collection.pojo.AreaPojo;
import com.zh.collection.pojo.CachePojo;
import com.zh.collection.pojo.DevicePojo;
import com.zh.collection.pojo.RulePojo;
import com.zh.collection.pojo.TagPojo;
import com.zh.collection.pojo.TimlyPojo;
import com.zh.collection.pojo.UnitPojo;
import com.zh.collection.pojo.VehiclePojo;
import com.zh.collection.util.ContainerUtil;

public class BusinessForDeviceStatusTask implements Runnable{
	private DataSource dataSource;
	private String sql_unit;
	private SimpleDateFormat sdf;
	private HashMap<String, CachePojo<String, UnitPojo, AreaPojo, DevicePojo, TagPojo, RulePojo, TimlyPojo, VehiclePojo>> caches;
	public BusinessForDeviceStatusTask(){
		this.dataSource = ContainerUtil.getDataSource();
		this.sql_unit = "select * from tb_sys_unit";
		this.caches = ContainerUtil.getCaches();
		this.sdf = ContainerUtil.getSdf();
		System.out.println(this.caches);
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			Connection con = null;
			try{
				con = dataSource.getConnection();
				Statement stat = con.createStatement();
				ResultSet rs = stat.executeQuery(sql_unit);
				while(rs.next()){
					String addr = rs.getString("channelAddr");
					CachePojo cache = caches.get(addr);
					UnitPojo up = (UnitPojo) cache.getUnitCache().get(addr);
					String id = "" + up.getId();
					long offlineRule = ((RulePojo)cache.getRuleCache().get("5")).getTime();
					HashMap<String, DevicePojo> dps = cache.getDeviceCache();
					Iterator<Map.Entry<String, DevicePojo>> itr = dps.entrySet().iterator();
					long currt = System.currentTimeMillis();
					while(itr.hasNext()){
						Entry<String, DevicePojo> entry = itr.next();
						String devId = entry.getKey();
						DevicePojo dp = dps.get(devId);
						String rt = dp.getRefreshTime();
						if(rt == null){
//							System.out.println(dp.getDeviceId() + "nu 离线");
							dp.setStatus(1);
						}else if(currt - sdf.parse(dp.getRefreshTime()).getTime() >= offlineRule){
//							System.out.println(dp.getDeviceId() + "nn 离线");
							dp.setStatus(1);
						}else if(currt - sdf.parse(dp.getRefreshTime()).getTime() < offlineRule){
//							System.out.println(dp.getDeviceId() + "nn 在线");
							dp.setStatus(0);
						}
						Statement stat1 = con.createStatement();
						String sql = "update tb_" + id + "_device set status = " + dp.getStatus() + ", refreshTime = '" + dp.getRefreshTime() + 
								"' where deviceId = '" + devId + "'";
						stat1.executeUpdate(sql);
						stat1.close();
					}
				}
				rs.close();
				stat.close();
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				try {
					con.close();
					Thread.sleep(2000);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
//		DevicePojo dp = devs.get(devId);
//		dp.setRefreshTime(sdf.format(new Date()));
//		try {
//			System.out.println(dataSource);
//			Connection con = dataSource.getConnection();
//			Statement stat = con.createStatement();
//			String sql = "update tb_" + unitIndex + "_device set status = 1, refreshTime = '" + dp.getRefreshTime() + 
//					"' where deviceId = '" + devId + "'";
//			stat.executeUpdate(sql);
//			stat.close();
//			con.close();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
	}

}
