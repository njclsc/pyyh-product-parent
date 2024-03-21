package com.zh.collection.business.task;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import javax.sql.DataSource;

import com.alibaba.fastjson.JSONObject;
import com.zh.collection.pojo.AreaPojo;
import com.zh.collection.pojo.CachePojo;
import com.zh.collection.pojo.DevicePojo;
import com.zh.collection.pojo.RulePojo;
import com.zh.collection.pojo.TagPojo;
import com.zh.collection.pojo.TimlyPojo;
import com.zh.collection.pojo.UnitPojo;
import com.zh.collection.util.ContainerUtil;

public class BusinessForCacheRefreshTask implements Runnable{
	private DataSource dataSource;
	private String sql_unit;
	private HashMap<String, CachePojo<String, UnitPojo, String, AreaPojo, String, DevicePojo, String, TagPojo, String, RulePojo, String, TimlyPojo>> caches;
	public BusinessForCacheRefreshTask(){
		this.dataSource = ContainerUtil.getDataSource();
		this.sql_unit = "select * from tb_sym_operate_logger where module = 'unit'";
		this.caches = ContainerUtil.getCaches();
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			try{
				cacheRefresh();
				
				Thread.sleep(1000);
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				try {
					Thread.sleep(1000);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
	}
	private void cacheRefresh() throws SQLException{
		Connection con = dataSource.getConnection();
		//单位
		Statement stat = con.createStatement();
		ResultSet rs = stat.executeQuery(sql_unit);
		while(rs.next()){
			try{
				int id = rs.getInt("id");
				int unitIndex = rs.getInt("unitIndex");
				String module = rs.getString("module");
				String action = rs.getString("action");
				String data = rs.getString("data");
				String oldAddress = rs.getString("oldAddress");
				String key = rs.getString("key");
				unitRefresh(unitIndex, action, data, oldAddress, key, con);
				Statement stat1 = con.createStatement();
				stat1.executeUpdate("delete from tb_sym_operate_logger where id = '" + id + "'");
				stat1.close();
				System.out.println(JSONObject.toJSONString(caches));
			}catch(Exception e){
				e.printStackTrace();
			}
		}
//		System.out.println("cache refresh...." );
		rs.close();
		stat.close();
		//-----------------
		con.close();
	}
	
	//单位同步
	private void unitRefresh(int unitIndex, String action, String data, String oldAddress, String key, Connection con){
		UnitPojo _up = JSONObject.toJavaObject(JSONObject.parseObject(data).getJSONObject(key), UnitPojo.class);
		String address = _up.getChannelAddr();
		System.out.println(address);
//		CachePojo<String, UnitPojo, String, AreaPojo, String, DevicePojo, String, TagPojo, String, RulePojo, String, TimlyPojo> cp = caches.get(address);
		//没有数据地址数据丢弃,不更新缓存
		if(address == null || address.equals("")){
			return;
		}else{
			if(action.equals("add")){
				CachePojo<String, UnitPojo, String, AreaPojo, String, DevicePojo, String, TagPojo, String, RulePojo, String, TimlyPojo> cp = new CachePojo<>();
				//单位[此缓存之保存本单位一个元素]
				HashMap<String, UnitPojo> ups = new HashMap<>();
				UnitPojo up = new UnitPojo();
				up.setId(_up.getId());
				up.setUnitName(_up.getUnitName());
				up.setUnitCode(_up.getUnitCode());
				up.setParentUnit(_up.getParentUnit());
				up.setUnitType(_up.getUnitType());
				up.setChannelAddr(_up.getChannelAddr());
				ups.put(up.getChannelAddr(), up);
				cp.setUnitCache(ups);
				//区域
				HashMap<String, AreaPojo> unitAreaCache = new HashMap<>();
				cp.setAreaCache(unitAreaCache);
				//设备
				HashMap<String, DevicePojo> unitDeviceCache = new HashMap<>();
				cp.setDeviceCache(unitDeviceCache);
				//标签
				HashMap<String, TagPojo> unitTagCache = new HashMap<>();
				cp.setTagCache(unitTagCache);
				//规则
				HashMap<String, RulePojo> unitRuleCache = new HashMap<>();
				cp.setRuleCache(unitRuleCache);
				//实时
				HashMap<String, TimlyPojo> unitTimlyCache = new HashMap<>();
				cp.setTimlyCache(unitTimlyCache);
				//车辆
				
				ContainerUtil.getCaches().put(address, cp);
			}else if(action.equals("update")){
				CachePojo<String, UnitPojo, String, AreaPojo, String, DevicePojo, String, TagPojo, String, RulePojo, String, TimlyPojo> cp = caches.get(oldAddress);
				cp.getUnitCache().remove(oldAddress);
				cp.getUnitCache().put(address, _up);
			}else if(action.equals("delete")){
				caches.remove(address);
			}
		}
		
	}
}
