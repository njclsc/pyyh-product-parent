package com.zh.middware.business.coder;


import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

import com.zh.middware.pojos.TagPojo;
import com.zh.middware.util.ContainerUtil;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ZCKF_ZH_Handler extends ChannelInboundHandlerAdapter{
	private LinkedBlockingQueue<Object> inQueue;
	private ConcurrentHashMap<String, TagPojo> tags;
	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		this.inQueue = ContainerUtil.getInQueue();
		this.tags = ContainerUtil.getTags();
	}
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// TODO Auto-generated method stub
		String data = String.valueOf(msg);
		String deviceId = data.substring(4, 8);
		String cmdType = data.substring(8, 10);
		int tagNum = Integer.parseInt(data.substring(12, 14), 16);
		switch (cmdType){
		case "41":
			for(int i = 0; i < tagNum; i++){
				String tagData = data.substring(i * 14 + 14, (i + 1) * 14 + 14);
				tagOperate(tagData, deviceId);
			}
			break;
		case "42":
			hearOperate(deviceId);
//			System.out.println("---> 设备心跳");
			break;
		}
		
	}
	private void tagOperate(String tagData, String stationId){
		String tagId = tagData.substring(0, 6);
		int status = Integer.parseInt(tagData.substring(6, 8), 16);
		String antId = tagData.substring(8, 12);
		int activation = ((status & 0x80) >> 7);
		int voltage = ((status & 0x40) >> 6);
		int disass = ((status & 0x20) >> 5);
		TagPojo tp = tags.get(tagId);
		if(tp == null){
			tp = new TagPojo();
			tp.setTagId(tagId);
			tp.setStationId(stationId);
			tp.setAntennalId(antId);
			if(activation == 0){
				tp.setActive(false);
			}else if(activation == 1){
				tp.setActive(true);
			}
			if(voltage == 0){
				tp.setVoltageOk(true);
			}else if(voltage == 1){
				tp.setVoltageOk(false);
			}
			if(disass == 0){
				tp.setDisassOk(true);
			}else if(disass == 1){
				tp.setDisassOk(false);
			}
			tags.put(tagId, tp);
		}else{
			tp.set_stationId(tp.getStationId());
			tp.set_antennalId(tp.getAntennalId());
			tp.set_isActive(tp.isActive());
			tp.setStationId(stationId);
			tp.setAntennalId(antId);
			if(activation == 0){
				tp.setActive(false);
			}else if(activation == 1){
				tp.setActive(true);
			}
			if(voltage == 0){
				tp.setVoltageOk(true);
			}else if(voltage == 1){
				tp.setVoltageOk(false);
			}
			if(disass == 0){
				tp.setDisassOk(true);
			}else if(disass == 1){
				tp.setDisassOk(false);
			}
			inQueue.offer(tp);
		}
	}
//	private void tagOperate(String tagData, String stationId){
//		String tagId = tagData.substring(0, 6);
//		int status = Integer.parseInt(tagData.substring(6, 8), 16);
//		String antId = tagData.substring(8, 10);
//		int activation = ((status & 0x80) >> 7);
//		int voltage = ((status & 0x40) >> 6);
//		int disass = ((status & 0x20) >> 5);
//		TagPojo tp = new TagPojo();
//		tp.setTagId(tagId);
//		tp.setStationId(stationId);
//		tp.setAntennalId(antId);
//		if(activation == 0){
//			tp.setActive(false);
//		}else if(activation == 1){
//			tp.setActive(true);
//		}
//		if(voltage == 0){
//			tp.setVoltageOk(true);
//		}else if(voltage == 1){
//			tp.setVoltageOk(false);
//		}
//		if(disass == 0){
//			tp.setDisassOk(true);
//		}else if(disass == 1){
//			tp.setDisassOk(false);
//		}
//		tp.setDateTime(System.currentTimeMillis());
//		inQueue.offer(tp);
////		System.out.println(tagId + "  " + status + "  " + antId + "  " + activation + "  " + voltage + "  " + disass);
////		System.out.println(Integer.toBinaryString(status));
//	}
	private void hearOperate(String deviceId){
		
	}
}
