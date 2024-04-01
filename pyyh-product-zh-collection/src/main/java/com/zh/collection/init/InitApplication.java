package com.zh.collection.init;

import java.net.InetSocketAddress;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.zh.collection.business.task.BusinessForCacheRefreshTask;
import com.zh.collection.business.task.BusinessForDeviceStatusTask;
import com.zh.collection.business.task.BusinessForSaveTask;
import com.zh.collection.business.task.BusinessForStartAllTask;
import com.zh.collection.lizer.UDPChannelinitializer;
import com.zh.collection.pojo.AreaPojo;
import com.zh.collection.pojo.CachePojo;
import com.zh.collection.pojo.DevicePojo;
import com.zh.collection.pojo.RulePojo;
import com.zh.collection.pojo.TagPojo;
import com.zh.collection.pojo.TimlyPojo;
import com.zh.collection.pojo.UDPConfigPojo;
import com.zh.collection.pojo.UnitPojo;
import com.zh.collection.pojo.VehiclePojo;
import com.zh.collection.util.ContainerUtil;
import com.zh.collection.util.OperateUtil;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.AdaptiveRecvByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

@Component
public class InitApplication {

	@Bean
	public Object initCommunications() throws Exception{
		//路径
		ContainerUtil.setRootPath(System.getProperty("user.dir"));
		//开启udp端口
		UDPConfigPojo udpCnf = JSONObject.toJavaObject(OperateUtil.readConfig("business-config/communication-config.zh").getJSONObject("udp"), UDPConfigPojo.class);
		if(udpCnf.isUsed()){
			EventLoopGroup work = new NioEventLoopGroup();
			Bootstrap boot = new Bootstrap();
			boot.group(work).channel(NioDatagramChannel.class).option(ChannelOption.RCVBUF_ALLOCATOR, 
					new AdaptiveRecvByteBufAllocator(udpCnf.getMiniBuf(),
						udpCnf.getInitBuf(), udpCnf.getMaxBuf())).handler(new UDPChannelinitializer());
			for(String s : udpCnf.getIpAddress()){
				String[] tmpAddress = s.split(":");
				ChannelFuture f = boot.bind(new InetSocketAddress(tmpAddress[0], Integer.parseInt(tmpAddress[1])));
			}
		}
		//缓存加载
		cacheLoad();
		//创建线程池
		ContainerUtil.setThreadPool(OperateUtil.createThreadPool());
		//业务处理
		ContainerUtil.getThreadPool().execute(new BusinessForStartAllTask());
		//数据持久化
		ContainerUtil.getThreadPool().execute(new BusinessForSaveTask());
		//设备状态刷新
		ContainerUtil.getThreadPool().execute(new BusinessForDeviceStatusTask());
		//管理事件与缓存更新
		ContainerUtil.getThreadPool().execute(new BusinessForCacheRefreshTask());
		return "";
	}
	private void cacheLoad() throws Exception{
		
		Connection con = ContainerUtil.getDataSource().getConnection();
		Statement stat = con.createStatement();
		String sqlUnit = "select * from tb_sys_unit";
		ResultSet rs = stat.executeQuery(sqlUnit);
		//单位
		
		while(rs.next()){
			try{
			CachePojo<String, UnitPojo, AreaPojo, DevicePojo, TagPojo, RulePojo, TimlyPojo, VehiclePojo> cp = new CachePojo<>();
			//单位
			HashMap<String, UnitPojo> ups = new HashMap<>();
			UnitPojo up = new UnitPojo();
			up.setId(rs.getInt("id"));
			up.setUnitName(rs.getString("unitName"));
			up.setUnitCode(rs.getString("unitCode"));
			up.setParentUnit(rs.getInt("parentUnit"));
			up.setUnitType(rs.getInt("unitType"));
			up.setChannelAddr(rs.getString("channelAddr"));
			ups.put(up.getChannelAddr(), up);
			cp.setUnitCache(ups);
			//区域
			HashMap<String, AreaPojo> unitAreaCache = new HashMap<>();
			Statement areaStat = con.createStatement();
			String sqlArea = "select * from tb_" + up.getId() + "_area";
			ResultSet rsArea = areaStat.executeQuery(sqlArea);
			while(rsArea.next()){
				AreaPojo ap = new AreaPojo();
				ap.setId(rsArea.getInt("id"));
				ap.setAreaName(rsArea.getString("areaName"));
				ap.setType(rsArea.getInt("type"));
				unitAreaCache.put("" + ap.getId(), ap);
			}
			rsArea.close();
			areaStat.close();
			cp.setAreaCache(unitAreaCache);
			//设备
			HashMap<String, DevicePojo> unitDeviceCache = new HashMap<>();
			Statement devStat = con.createStatement();
			String sqlDev = "select * from tb_" + up.getId() + "_device";
			ResultSet rsDev = devStat.executeQuery(sqlDev);
			while(rsDev.next()){
				DevicePojo dp = new DevicePojo();
				dp.setId(rsDev.getInt("id"));
				dp.setDeviceId(rsDev.getString("deviceId"));
				dp.setAreaIndex(rsDev.getInt("areaIndex"));
				dp.setType(rsDev.getInt("type"));
				unitDeviceCache.put(dp.getDeviceId(), dp);
			}
			rsDev.close();
			devStat.close();
			cp.setDeviceCache(unitDeviceCache);
			//标签
			HashMap<String, TagPojo> unitTagCache = new HashMap<>();
			Statement tagStat = con.createStatement();
			String sqlTag = "select * from tb_" + up.getId() + "_tag";
			ResultSet rsTag = tagStat.executeQuery(sqlTag);
			while(rsTag.next()){
				TagPojo tp = new TagPojo();
				tp.setId(rsTag.getInt("id"));
				tp.setTagId(rsTag.getString("tagId"));
				tp.setVehicleIndex(rsTag.getInt("vehicleIndex"));
				tp.setStatus(rsTag.getInt("status"));
				tp.setType(rsTag.getInt("type"));
				tp.setInstallDate(rsTag.getString("installDate"));
				tp.setExpire(rsTag.getBoolean("expire"));
				tp.setExpireDateTime(rsTag.getString("expireDateTime"));
				unitTagCache.put(tp.getTagId(), tp);
			}
			rsTag.close();
			tagStat.close();
			cp.setTagCache(unitTagCache);
			//规则
			HashMap<String, RulePojo> unitRuleCache = new HashMap<>();
			Statement ruleStat = con.createStatement();
			String sqlRule = "select * from tb_" + up.getId() + "_rule";
			ResultSet rsRule = ruleStat.executeQuery(sqlRule);
			while(rsRule.next()){
				RulePojo rp = new RulePojo();
				rp.setId(rsRule.getInt("id"));
				rp.setRuleName(rsRule.getString("ruleName"));
				rp.setRuleType(rsRule.getInt("ruleType"));
				rp.setTime(rsRule.getInt("time"));
//				rp.setAreaIndex(rsRule.getInt("areaIndex"));
				unitRuleCache.put("" + rp.getRuleType(), rp);
			}
			rsRule.close();
			ruleStat.close();
			cp.setRuleCache(unitRuleCache);
			//实时
			HashMap<String, TimlyPojo> unitTimlyCache = new HashMap<>();
			Statement timlyStat = con.createStatement();
			String sqlTimly = "select * from tb_" + up.getId() + "_timly";
			ResultSet rsTimly = timlyStat.executeQuery(sqlTimly);
			while(rsTimly.next()){
				TimlyPojo tp = new TimlyPojo();
				tp.setId(rsTimly.getInt("id"));
				tp.setTagId(rsTimly.getString("tagId"));
				tp.setOldDeviceId(rsTimly.getString("oldDeviceId"));
				tp.setCurrentDeviceId(rsTimly.getString("currentDeviceId"));
				tp.setOldDeviceTime(rsTimly.getString("oldDeviceTime"));
				tp.setCurrentDeviceTime(rsTimly.getString("currentDeviceTime"));
				tp.setHbStationId(rsTimly.getString("hbStationId"));
				unitTimlyCache.put(tp.getTagId(), tp);
			}
			rsTimly.close();
			timlyStat.close();
			cp.setTimlyCache(unitTimlyCache);
			//车辆
			HashMap<String, VehiclePojo> vehicleCache = new HashMap<>();
			Statement vehicleStat = con.createStatement();
			String sqlVehicle = "select * from tb_" + up.getId() + "_vehicle";
			ResultSet rsVehicle = vehicleStat.executeQuery(sqlVehicle);
			while(rsVehicle.next()){
				VehiclePojo vp = new VehiclePojo();
				vp.setId(rsVehicle.getInt("id"));
				vp.setOwnerName(rsVehicle.getString("ownerName"));
				vp.setOwnerType(rsVehicle.getInt("ownerType"));
				vp.setMovePhone(rsVehicle.getString("movePhone"));
				vp.setOwnerNumber(rsVehicle.getString("ownerNumber"));
				vp.setVehicleType(rsVehicle.getInt("vehicleType"));
				vp.setVehicleBrand(rsVehicle.getString("vehicleBrand"));
				vp.setVehicleColor(rsVehicle.getString("vehicleColor"));
				vp.setRfidId1(rsVehicle.getString("rfidId1"));
				vp.setRfidId2(rsVehicle.getString("rfidId2"));
				vp.setValidity(rsVehicle.getInt("validity"));
				vp.setRegistDate(rsVehicle.getString("registDate"));
				vp.setPhotos(rsVehicle.getString("photos"));
				vp.setStatus(rsVehicle.getInt("status"));
				vp.setReason(rsVehicle.getString("reason"));
				vehicleCache.put("" + vp.getId(), vp);
			}
			cp.setVehicleCache(vehicleCache);
			rsVehicle.close();
			vehicleStat.close();
			
			ContainerUtil.getCaches().put(up.getChannelAddr(), cp);
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
		
		System.out.println(JSONObject.toJSONString(ContainerUtil.getCaches()) + "  " + Runtime.getRuntime().availableProcessors());
		rs.close();
		stat.close();
		con.close();
	}
}
