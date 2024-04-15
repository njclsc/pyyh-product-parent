package com.zh.collection.business.task;

import java.net.InetSocketAddress;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

import com.zh.collection.pojo.AreaPojo;
import com.zh.collection.pojo.CachePojo;
import com.zh.collection.pojo.DevicePojo;
import com.zh.collection.pojo.RulePojo;
import com.zh.collection.pojo.TagPojo;
import com.zh.collection.pojo.TimlyPojo;
import com.zh.collection.pojo.UnitPojo;
import com.zh.collection.pojo.VehiclePojo;
import com.zh.collection.util.ContainerUtil;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.socket.DatagramPacket;

public class Show_1_BusinessForStartAllTask implements Runnable{
	private ThreadPoolExecutor threadPool;
	private LinkedBlockingQueue<Object> inQueue;
	private Connection con;
	public Show_1_BusinessForStartAllTask() throws Exception{
		this.threadPool = ContainerUtil.getThreadPool();
		this.inQueue = ContainerUtil.getInQueue();
		this.con = ContainerUtil.getDataSource().getConnection();
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			try{
				DatagramPacket dp1 = (DatagramPacket)ContainerUtil.getSendQueue().poll();
				if(dp1 != null){
					long cdt = System.currentTimeMillis();
					if(cdt - ContainerUtil.getAlarmSend() > 10000){
						ContainerUtil.getF().channel().writeAndFlush(dp1);
						ContainerUtil.setAlarmSend(cdt);
						System.out.println("send alarm -->" + ContainerUtil.getSendQueue().size());
					}else{
						ContainerUtil.getSendQueue().offer(dp1);
					}
				}
				TimlyPojo tp = (TimlyPojo)inQueue.poll();
				if(tp != null){
					this.con = ContainerUtil.getDataSource().getConnection();
//					System.out.println(tp.getTagId() + "  " + tp.getHbStationId() + "  " + tp.getCurrentDeviceId() + "  " + tp.getMappingAddress());
					String localAddress = tp.getMappingAddress();
					CachePojo<String, UnitPojo, AreaPojo, DevicePojo, TagPojo, RulePojo, TimlyPojo, VehiclePojo> cache = ContainerUtil.getCaches().get(localAddress);
					HashMap<String, AreaPojo> areas = cache.getAreaCache();
					HashMap<String, DevicePojo> devices = cache.getDeviceCache();
					HashMap<String, RulePojo> rules = cache.getRuleCache();
					HashMap<String, TagPojo> tags = cache.getTagCache();
					HashMap<String, VehiclePojo> vehicles = cache.getVehicleCache();
					UnitPojo up = cache.getUnitCache().get(localAddress);
					//判断区域类型
					String hbId = tp.getHbStationId();
					String lbId = tp.getCurrentDeviceId();
					DevicePojo hdp = devices.get(hbId);
					DevicePojo ldp = devices.get(lbId);
					int hbArea = hdp.getAreaIndex();
					AreaPojo aph = areas.get("" + hbArea);
					if(ldp == null){
						ldp = new DevicePojo();
					}
					int lbArea = ldp.getAreaIndex();
//					AreaPojo apl = areas.get("" + lbArea);
//					if(apl == null){
//						apl = new AreaPojo();
//					}
					String sql1 = "update tb_" + up.getId() + "_timly set hbStationId = '" + hbId + "' where tagId = '" + tp.getTagId() + "'"; 
					Statement stat1 = con.createStatement();
					stat1.executeUpdate(sql1);
					stat1.close();
					int a = ((tp.getStatus() & 0xFF) >> 7);
					//大门
					if(aph.getType() == 1 && a == 0 && !tp.getPositionType().equals("door")){
						System.out.println(tp.getTagId() + "  " + tp.getHbStationId() + "  " + tp.getCurrentDeviceId() + "  " + tp.getMappingAddress());
						System.out.println("into door");
						VehiclePojo vp = vehicles.get("" + tags.get(tp.getTagId()).getVehicleIndex());
						Statement stat = con.createStatement();
						String sql = "update tb_" + up.getId() + "_vehicle set position = 'into' where id = '" + 
								vp.getId() + "'";
						stat.executeUpdate(sql);
						stat.close();
						if(System.currentTimeMillis() - ContainerUtil.getAlarmSend() > 5000){
							String[] ads = ContainerUtil.getDevAddress().get(hbId).split(":");
							InetSocketAddress addr = new InetSocketAddress(ads[0], Integer.parseInt(ads[1]));
					        ByteBuf copiedBuffer = Unpooled.copiedBuffer("rrpc,setpio,29,1,1500".getBytes());
							DatagramPacket dp = new DatagramPacket(copiedBuffer, addr);
							ContainerUtil.getSendQueue().offer(dp);
						}
						
						tp.setPositionType("door");
						
						
					//进停车场
					}
					if(aph.getType() == 2 && a == 1 && hbArea == lbArea && !tp.getPositionType().equals("iparking")){
						System.out.println(tp.getTagId() + "  " + tp.getHbStationId() + "  " + tp.getStatus() + "  " + tp.getCurrentDeviceId() + "  " + tp.getMappingAddress());
						System.out.println("into parking");
						VehiclePojo vp = vehicles.get("" + tags.get(tp.getTagId()).getVehicleIndex());
						Statement stat = con.createStatement();
						String sql = "update tb_" + up.getId() + "_vehicle set position = 'iparking' where id = '" + 
								vp.getId() + "'";
						stat.executeUpdate(sql);
						stat.close();
						tp.setPositionType("iparking");
					//出停车场
					}
					if(aph.getType() == 2 && a == 0 && hbArea == lbArea && !tp.getPositionType().equals("oparking")){
						System.out.println(tp.getTagId() + "  " + tp.getHbStationId() + "  " + tp.getStatus() + "  " + tp.getCurrentDeviceId() + "  " + tp.getMappingAddress());
						System.out.println("out parking");
						VehiclePojo vp = vehicles.get("" + tags.get(tp.getTagId()).getVehicleIndex());
						Statement stat = con.createStatement();
						String sql = "update tb_" + up.getId() + "_vehicle set position = 'oparking' where id = '" + 
								vp.getId() + "'";
						stat.executeUpdate(sql);
						stat.close();
						tp.setPositionType("oparking");
					//进楼
					}
					if(aph.getType() == 3 && a == 1 && hbArea == lbArea && !tp.getPositionType().equals("ioffice")){
						System.out.println(tp.getTagId() + "  " + tp.getHbStationId() + "  " + tp.getStatus() + "  " + tp.getCurrentDeviceId() + "  " + tp.getMappingAddress());
						System.out.println("into office");
						VehiclePojo vp = vehicles.get("" + tags.get(tp.getTagId()).getVehicleIndex());
						Statement stat = con.createStatement();
						String sql = "update tb_" + up.getId() + "_vehicle set position = 'ioffice' where id = '" + 
								vp.getId() + "'";
						stat.executeUpdate(sql);
						stat.close();
						if(System.currentTimeMillis() - ContainerUtil.getAlarmSend() > 5000){
							String[] ads = ContainerUtil.getDevAddress().get(hbId).split(":");
							InetSocketAddress addr = new InetSocketAddress(ads[0], Integer.parseInt(ads[1]));
					        ByteBuf copiedBuffer = Unpooled.copiedBuffer("rrpc,setpio,26,1,1500".getBytes());
							DatagramPacket dp = new DatagramPacket(copiedBuffer, addr);
							ContainerUtil.getSendQueue().offer(dp);
						}
						tp.setPositionType("ioffice");
						//---------------------------------------------------------------------------
						Statement statx = con.createStatement();
						StringBuffer sb = new StringBuffer();
						sb.append("insert into tb_");sb.append(up.getId());
						sb.append("_alarm(tagId, alarmType, position, ownerName, areaName, ownerType, dateTime, status) values('");
						sb.append(up.getId());sb.append("', ");sb.append(2);sb.append(", '', '");sb.append(vp.getOwnerName());
						sb.append("', '");sb.append(aph.getId());sb.append("','', '");
						sb.append(ContainerUtil.getSdf().format(new Date()));sb.append("', 1");sb.append(")");
						statx.executeUpdate(sb.toString());
						statx.close();
						
						
						//---------------------------------------------------------------------------
						
					//出楼	
					}
					if(aph.getType() == 3 && a == 0 && hbArea == lbArea && !tp.getPositionType().equals("ooffice")){
						System.out.println(tp.getTagId() + "  " + tp.getHbStationId() + "  " + tp.getStatus() + "  " + tp.getCurrentDeviceId() + "  " + tp.getMappingAddress());
						System.out.println("out office");
						VehiclePojo vp = vehicles.get("" + tags.get(tp.getTagId()).getVehicleIndex());
						Statement stat = con.createStatement();
						String sql = "update tb_" + up.getId() + "_vehicle set position = 'ooffice' where id = '" + 
								vp.getId() + "'";
						stat.executeUpdate(sql);
						stat.close();
						tp.setPositionType("ooffice");
					}
				}
				

				Thread.sleep(100);
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
//----------------------------------------------------------------------------
	}

	
}
