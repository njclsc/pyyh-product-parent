package com.zh.collection.business.handler;

import java.text.SimpleDateFormat;
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

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class Show_1_DataOperateHandler extends ChannelInboundHandlerAdapter {
	private SimpleDateFormat sdf;
	private ThreadPoolExecutor threadPool;
	private LinkedBlockingQueue<Object> inQueue;

	public Show_1_DataOperateHandler() {
		this.sdf = ContainerUtil.getSdf();
		this.threadPool = ContainerUtil.getThreadPool();
		this.inQueue = ContainerUtil.getInQueue();
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// TODO Auto-generated method stub
		// 获取缓存
		String localAddress = ctx.channel().localAddress().toString().substring(1);
		CachePojo<String, UnitPojo, AreaPojo, DevicePojo, TagPojo, RulePojo, TimlyPojo, VehiclePojo> cache = ContainerUtil.getCaches().get(localAddress);
		if (cache == null) {
			return;
		}
		// 数据解析
		String data = String.valueOf(msg);
		String deviceId = data.substring(4, 8);
		String cmdType = data.substring(8, 10);
		String dt = sdf.format(new Date());
		HashMap<String, DevicePojo> devs = cache.getDeviceCache();
		if (cmdType.equals("41")) {
			// 设备处理
			// System.out.println("设备" + devs);
			// ------------------------------
			// 设备
//			System.out.println(deviceId);
			DevicePojo dp = devs.get(deviceId);
			if(dp == null){
				return;
			}
			dp.setRefreshTime(dt);
			// 实时缓存
			HashMap<String, TimlyPojo> timly = cache.getTimlyCache();
			// 标签处理
			// HashMap<String, TagPojo> tags = cache.getTagCache();
			// System.out.println("标签" + devs);
			int tagNum = Integer.parseInt(data.substring(12, 14), 16);
//			System.out.println(tagNum);
			for (int i = 0; i < tagNum; i++) {
				String tagData = data.substring(i * 14 + 14, (i + 1) * 14 + 14);
				String tagId = tagData.substring(0, 6);
				String status = tagData.substring(6, 8);
				String antId = tagData.substring(8, 12);
				String lowrsi = tagData.substring(12, 14);
				// String hrsi = tagData.substring(14, 16);
				// System.out.println(tagId + " " + status + " " + antId + " " +
				// lowrsi + " " + hrsi);
//				System.out.println(tagId + "  " + status + "  " + antId + "  " + lowrsi);
				// ------------------------------
				TimlyPojo tp = timly.get(tagId);
				if (tp != null) {
					tp.setStatus(Integer.parseInt(status, 16));
					tp.setOldDeviceId(tp.getCurrentDeviceId());
					tp.setOldDeviceTime(tp.getCurrentDeviceTime());
					tp.setCurrentDeviceId(antId);
					// if(!antId.equals(tp.getOldDeviceId())){
					tp.setCurrentDeviceTime(dt);
					// }
					tp.setHbStationId(deviceId);
					tp.setMappingAddress(localAddress);
					// 设置区域类型方便后续业务处理
					inQueue.offer(tp);
				}

			}
//			System.out.println(deviceId + "  " + cmdType + "  " + tagNum + " "
//					+ ctx.channel().localAddress().toString().substring(1));
		} else if (cmdType.equals("42")) {
			// 设备
			DevicePojo dp = devs.get(deviceId);
			if(dp == null){
				return;
			}
			dp.setRefreshTime(dt);
		} else {
//			ctx.fireChannelRead(msg);
		}
	}

}
