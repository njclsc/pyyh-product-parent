package com.zh.middware.util;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;
import com.zh.middware.init.lizer.ZCKF_ZH_BusinessChain;
import com.zh.middware.pojos.CommunicationConfigPojo;
import com.zh.middware.pojos.DevicePojo;
import com.zh.middware.pojos.LogicDevicePojo;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.AdaptiveRecvByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

public class ToolUtil {
	
	
	public static void forInitLoad(){
		HttpGet httpGet = new HttpGet(ContainerUtil.getRemoteConfig().getDeviceInfoUrl());
		CloseableHttpResponse response = null;
		try {
			ConcurrentHashMap<String, LogicDevicePojo> devices = ContainerUtil.getLgDevices();
			response = ContainerUtil.getHttpClient().execute(httpGet);
			HttpEntity reEntity = response.getEntity();
			String data = EntityUtils.toString(reEntity);
			if(reEntity != null){
				List<DevicePojo> dps = JSONObject.parseObject(data).getJSONObject("body").getJSONArray("list").toJavaList(DevicePojo.class);
				ToolUtil.refreshDevBuffer(dps, devices);
				System.out.println("设备数：" + devices.size() + "   " + data);
			}
		} catch(Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(response != null){
				try {
					response.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
	}
	public static void refreshDevBuffer(List<DevicePojo> dps, ConcurrentHashMap<String, LogicDevicePojo> devices){
		for(DevicePojo dp : dps){
			int deviceId = Integer.parseInt(dp.getDeviceId());
			int devType = dp.getDeviceType();
			String antIdOut = dp.getSenseOut();
			String antIdIn = dp.getSenseIn();
			int _did = deviceId & 0xFFFF;
			StringBuffer sb = new StringBuffer();
			sb.append(Integer.toHexString((_did & 0xF000) >> 12));
			sb.append(Integer.toHexString((_did & 0xF00) >> 8));
			sb.append(Integer.toHexString((_did & 0xF0) >> 4));
			sb.append(Integer.toHexString(_did & 0xF));
			String key = sb.toString().toUpperCase();
			if(!devices.containsKey(key)){
				LogicDevicePojo ldp = new LogicDevicePojo();
				ldp.setType(devType);
				ldp.setDeviceId(key);
				ldp.setDeviceIdDec(deviceId);
				if(antIdOut != null && antIdOut.length() > 0){
					int antOutDec = Integer.parseInt(antIdOut);
					StringBuffer sb1 = new StringBuffer();
					sb1.append(Integer.toHexString((antOutDec & 0xF000) >> 12));
					sb1.append(Integer.toHexString((antOutDec & 0xF00) >> 8));
					sb1.append(Integer.toHexString((antOutDec & 0xF0) >> 4));
					sb1.append(Integer.toHexString(antOutDec & 0xF));
					ldp.setAntIdOut(sb1.toString().toUpperCase());
				}
				if(antIdIn != null && antIdIn.length() > 0){
					int antInDec = Integer.parseInt(antIdIn);
					StringBuffer sb1 = new StringBuffer();
					sb1.append(Integer.toHexString((antInDec & 0xF000) >> 12));
					sb1.append(Integer.toHexString((antInDec & 0xF00) >> 8));
					sb1.append(Integer.toHexString((antInDec & 0xF0) >> 4));
					sb1.append(Integer.toHexString(antInDec & 0xF));
					ldp.setAntIdIn(sb1.toString().toUpperCase());
				}
				if(antIdIn != null && antIdOut != null && antIdIn.length() > 0 && antIdOut.length() > 0){
					int antOutDec = Integer.parseInt(antIdOut);
					StringBuffer sb1 = new StringBuffer();
					sb1.append(Integer.toHexString((antOutDec & 0xF000) >> 12));
					sb1.append(Integer.toHexString((antOutDec & 0xF00) >> 8));
					sb1.append(Integer.toHexString((antOutDec & 0xF0) >> 4));
					sb1.append(Integer.toHexString(antOutDec & 0xF));
					ldp.setAntIdOut(sb1.toString().toUpperCase());
					int antInDec = Integer.parseInt(antIdIn);
					StringBuffer sb2 = new StringBuffer();
					sb2.append(Integer.toHexString((antInDec & 0xF000) >> 12));
					sb2.append(Integer.toHexString((antInDec & 0xF00) >> 8));
					sb2.append(Integer.toHexString((antInDec & 0xF0) >> 4));
					sb2.append(Integer.toHexString(antInDec & 0xF));
					ldp.setAntIdIn(sb2.toString().toUpperCase());
				}
				devices.put(key, ldp);
			}else{
				LogicDevicePojo ldp = devices.get(key);
				ldp.setType(devType);
				ldp.setDeviceId(key);
				ldp.setDeviceIdDec(deviceId);
				if(antIdOut != null && antIdOut.length() > 0){
					int antOutDec = Integer.parseInt(antIdOut);
					StringBuffer sb1 = new StringBuffer();
					sb1.append(Integer.toHexString((antOutDec & 0xF000) >> 12));
					sb1.append(Integer.toHexString((antOutDec & 0xF00) >> 8));
					sb1.append(Integer.toHexString((antOutDec & 0xF0) >> 4));
					sb1.append(Integer.toHexString(antOutDec & 0xF));
					ldp.setAntIdOut(sb1.toString().toUpperCase());
				}
				if(antIdIn != null && antIdIn.length() > 0){
					int antInDec = Integer.parseInt(antIdIn);
					StringBuffer sb1 = new StringBuffer();
					sb1.append(Integer.toHexString((antInDec & 0xF000) >> 12));
					sb1.append(Integer.toHexString((antInDec & 0xF00) >> 8));
					sb1.append(Integer.toHexString((antInDec & 0xF0) >> 4));
					sb1.append(Integer.toHexString(antInDec & 0xF));
					ldp.setAntIdIn(sb1.toString().toUpperCase());
				}
				if(antIdIn != null && antIdOut != null && antIdIn.length() > 0 && antIdOut.length() > 0){
					int antOutDec = Integer.parseInt(antIdOut);
					StringBuffer sb1 = new StringBuffer();
					sb1.append(Integer.toHexString((antOutDec & 0xF000) >> 12));
					sb1.append(Integer.toHexString((antOutDec & 0xF00) >> 8));
					sb1.append(Integer.toHexString((antOutDec & 0xF0) >> 4));
					sb1.append(Integer.toHexString(antOutDec & 0xF));
					ldp.setAntIdOut(sb1.toString().toUpperCase());
					int antInDec = Integer.parseInt(antIdIn);
					StringBuffer sb2 = new StringBuffer();
					sb2.append(Integer.toHexString((antInDec & 0xF000) >> 12));
					sb2.append(Integer.toHexString((antInDec & 0xF00) >> 8));
					sb2.append(Integer.toHexString((antInDec & 0xF0) >> 4));
					sb2.append(Integer.toHexString(antInDec & 0xF));
					ldp.setAntIdIn(sb2.toString().toUpperCase());
				}
			}
			
		}
	}
	
	
	public static ThreadPoolExecutor createThreadPool(){
		int cpuNum = Runtime.getRuntime().availableProcessors();
		return new ThreadPoolExecutor(
				cpuNum * 2,
				cpuNum * 2 + 10,
				60,
				TimeUnit.SECONDS,
				new ArrayBlockingQueue<Runnable>(50000),
				Executors.defaultThreadFactory(),
				new ThreadPoolExecutor.AbortPolicy()
			  );
	}
	public static void openChannel(CommunicationConfigPojo ccp){
		String way = ccp.getWay();
		switch(way){
		case "udp":
			EventLoopGroup work = new NioEventLoopGroup();
			Bootstrap boot = new Bootstrap();
			boot.group(work).channel(NioDatagramChannel.class).option(ChannelOption.RCVBUF_ALLOCATOR, new AdaptiveRecvByteBufAllocator(ccp.getMiniBuf(),
				ccp.getInitBuf(), ccp.getMaxBuf())).handler(new ZCKF_ZH_BusinessChain());
			for(String s : ccp.getIpAddress()){
				String[] tmpAddress = s.split(":");
				ChannelFuture f = boot.bind(new InetSocketAddress(tmpAddress[0], Integer.parseInt(tmpAddress[1])));
				//==========================
				ContainerUtil.getChannels().put(s, f);
			}
			break;
//		case "tcpServer":
//			System.out.println("1");
//			break;
//		case "tcpClient":
//			System.out.println("1");
//			break;
		}
	}
}
