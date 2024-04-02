package com.zh.collection.business.task;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.zh.collection.pojo.AreaPojo;
import com.zh.collection.pojo.CachePojo;
import com.zh.collection.pojo.DevicePojo;
import com.zh.collection.pojo.RulePojo;
import com.zh.collection.pojo.TagPojo;
import com.zh.collection.pojo.TimlyPojo;
import com.zh.collection.pojo.UnitPojo;
import com.zh.collection.pojo.VehiclePojo;
import com.zh.collection.util.ContainerUtil;

public class BusinessForTagRecoveryExpire implements Runnable{
	private TimlyPojo tp;
	private CachePojo<String, UnitPojo, AreaPojo, DevicePojo, TagPojo, RulePojo, TimlyPojo, VehiclePojo> cache;
	private int unitIndex;
	public BusinessForTagRecoveryExpire(TimlyPojo tp, CachePojo<String, UnitPojo, AreaPojo, DevicePojo, TagPojo, RulePojo, TimlyPojo, VehiclePojo> cache, int unitIndex){
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
			String tagId = tp.getTagId();
			Statement stat1 = con.createStatement();
			String sql = "update tb_" + unitIndex + "_tag set expire = false where tagId = '" + tagId + "'";
			stat1.executeUpdate(sql);
			stat1.close();
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
