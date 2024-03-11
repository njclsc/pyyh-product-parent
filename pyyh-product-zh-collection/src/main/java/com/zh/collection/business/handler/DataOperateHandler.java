package com.zh.collection.business.handler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

import com.alibaba.fastjson.JSONObject;
import com.zh.collection.pojo.AreaPojo;
import com.zh.collection.pojo.CachePojo;
import com.zh.collection.pojo.DevicePojo;
import com.zh.collection.pojo.RulePojo;
import com.zh.collection.pojo.TagPojo;
import com.zh.collection.pojo.TimlyPojo;
import com.zh.collection.pojo.UnitPojo;
import com.zh.collection.util.ContainerUtil;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class DataOperateHandler extends ChannelInboundHandlerAdapter{
	private SimpleDateFormat sdf;
	private ThreadPoolExecutor threadPool;
	private LinkedBlockingQueue<Object> inQueue;
	public DataOperateHandler(){
		this.sdf = ContainerUtil.getSdf();
		this.threadPool = ContainerUtil.getThreadPool();
		this.inQueue = ContainerUtil.getInQueue();
	}
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// TODO Auto-generated method stub
		//获取缓存
		String localAddress = ctx.channel().localAddress().toString().substring(1);
		CachePojo<String, UnitPojo, String, AreaPojo, String, DevicePojo, String, TagPojo, String, List<RulePojo>, String, TimlyPojo> cache = ContainerUtil.getCaches().get(localAddress);
		if(cache == null){
			return;
		}
		System.out.println(JSONObject.toJSONString(cache));
		//数据解析
		String data = String.valueOf(msg);
		String deviceId = data.substring(4, 8);
		String cmdType = data.substring(8, 10);
		if(cmdType.equals("41")){
			//设备处理
			HashMap<String, DevicePojo> devs = cache.getDeviceCache();
			System.out.println("设备" + devs);
			//------------------------------
			
			
			//实时缓存
			HashMap<String, TimlyPojo> timly = cache.getTimlyCache();
			//标签处理
//			HashMap<String, TagPojo> tags = cache.getTagCache();
			System.out.println("标签" + devs);
			int tagNum = Integer.parseInt(data.substring(12, 14), 16);
			for(int i = 0; i < tagNum; i++){
				String tagData = data.substring(i * 16 + 14, (i + 1) * 16 + 14);
				String tagId = tagData.substring(0, 6);
				String status = tagData.substring(6, 8);
				String antId = tagData.substring(8, 12);
				String lowrsi = tagData.substring(12, 14);
				String hrsi = tagData.substring(14, 16);
				System.out.println(tagId + "  " + status + "  " + antId + "  " + lowrsi + "  " + hrsi);
				//------------------------------
				TimlyPojo tp = timly.get(tagId);
				if(tp != null){
					tp.setOldDeviceId(tp.getCurrentDeviceId());
					tp.setOldDeviceTime(tp.getCurrentDeviceTime());
					tp.setCurrentDeviceId(antId);
					tp.setCurrentDeviceTime(sdf.format(new Date()));
					tp.setHbStationId(deviceId);
					tp.setMappingAddress(localAddress);
					//设置区域类型方便后续业务处理
					inQueue.offer(tp);
				}
				
				
				
			}
			System.out.println(deviceId + "  " + cmdType + "  " + tagNum + " " + ctx.channel().localAddress().toString().substring(1));
		}else{
			ctx.fireChannelRead(msg);
		}
	}

}
