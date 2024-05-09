package com.zh.middware.business.task;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

import com.alibaba.fastjson.JSONObject;
import com.zh.middware.pojos.PushDataPojo;
import com.zh.middware.pojos.PushPojo;
import com.zh.middware.pojos.TagPojo;
import com.zh.middware.util.ContainerUtil;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.socket.DatagramPacket;

public class ParseDataTask implements Runnable{
	private LinkedBlockingQueue<Object> inQueue;
	private long dtFlag;
	private PushPojo pp;
	private HttpClient cli;
	private List<PushDataPojo> dpd;
	private HashSet<String> deduplic;
	private HashSet<String> sendBuf;
//	private long sendDT;
	private ConcurrentHashMap<String, String> devAddr;
	public ParseDataTask(LinkedBlockingQueue<Object> inQueue){
		this.inQueue = inQueue;
		this.pp = new PushPojo();
		this.pp.setData(new ArrayList<PushDataPojo>());
		this.dtFlag = System.currentTimeMillis();
		this.cli = ContainerUtil.getHttpClient();
		this.dpd = new ArrayList<>();
		this.deduplic = new HashSet<>();
		this.sendBuf = new HashSet<String>();
		this.devAddr = ContainerUtil.getDevAddr();
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			try{
				Thread.sleep(1);
				TagPojo tp = (TagPojo)inQueue.poll();
				if(tp == null){
					continue;
				}
				_pushTag(tp);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	private void _pushTag(TagPojo tp){
		long _dt = System.currentTimeMillis();
		String _cst = tp.get_stationId();
		String cst = tp.getStationId();
		String _ant = tp.get_antennalId();
		String ant = tp.getAntennalId();
		boolean _act = tp.is_isActive();
		boolean act = tp.isActive();
		boolean vol = tp.isVoltageOk();
		boolean dis = tp.isDisassOk();
		int devType = ContainerUtil.getLgDevices().get(cst).getType();
		if(devType == 1 || devType == 2 || devType == 3){
//			System.out.println(devType + "-----" + act);
			if(devType == 3 && act){
				if(!deduplic.contains(tp.getTagId())){
					PushDataPojo _pdp = new PushDataPojo();
					if(!vol || !dis){
						_pdp.setAction(6);
					}else{
						_pdp.setAction(2);
					}
					_pdp.setTagId("" + Integer.parseInt(tp.getTagId(), 16));
					_pdp.setStationId("" + Integer.parseInt(cst, 16));
					_pdp.setAntennalId(ant);
					_pdp.setActive(act);
					_pdp.setVoltageOk(vol);
					_pdp.setDisassOk(dis);
					_pdp.setDateTime(_dt);
					dpd.add(_pdp);
					deduplic.add(tp.getTagId());
				}
			}else if(devType == 2 && act){
				if(!deduplic.contains(tp.getTagId())){
					PushDataPojo _pdp = new PushDataPojo();
					if(!vol || !dis){
						_pdp.setAction(5);
					}else{
						_pdp.setAction(1);
					}
					_pdp.setTagId("" + Integer.parseInt(tp.getTagId(), 16));
					_pdp.setStationId("" + Integer.parseInt(cst, 16));
					_pdp.setAntennalId(ant);
					_pdp.setActive(act);
					_pdp.setVoltageOk(vol);
					_pdp.setDisassOk(dis);
					_pdp.setDateTime(_dt);
					dpd.add(_pdp);
					deduplic.add(tp.getTagId());
					sendBuf.add(tp.getStationId());
				}
			}else if(devType == 1 && act){
				String inAntId = ContainerUtil.getLgDevices().get(cst).getAntIdIn();
				String outAntId = ContainerUtil.getLgDevices().get(cst).getAntIdOut();
//				System.out.println(inAntId + "--2---" + outAntId);
				if(!ant.equals(tp.getDoorAnt1())){
					tp.setDoorAnt2(tp.getDoorAnt1());
					tp.setDoorAnt1(ant);
				}
//				System.out.println(tp.getDoorAnt1() + "---3--" + tp.getDoorAnt2());
				if(!tp.getDoorAnt1().equals("") && !tp.getDoorAnt2().equals("")){
					//in
					if(tp.getDoorAnt2().equals(outAntId) && tp.getDoorAnt1().equals(inAntId)){
//						System.out.println("in.....");
						if(!deduplic.contains(tp.getTagId())){
							PushDataPojo _pdp = new PushDataPojo();
							if(!vol || !dis){
								_pdp.setAction(5);
							}else{
								_pdp.setAction(1);
							}
							_pdp.setTagId("" + Integer.parseInt(tp.getTagId(), 16));
							_pdp.setStationId("" + Integer.parseInt(cst, 16));
							_pdp.setAntennalId(ant);
							_pdp.setActive(act);
							_pdp.setVoltageOk(vol);
							_pdp.setDisassOk(dis);
							_pdp.setDateTime(_dt);
							dpd.add(_pdp);
							deduplic.add(tp.getTagId());
							sendBuf.add(tp.getStationId());
						}
					//out
					}else if(tp.getDoorAnt2().equals(inAntId) && tp.getDoorAnt1().equals(outAntId)){
//						System.out.println("out.....");
						if(!deduplic.contains(tp.getTagId())){
							PushDataPojo _pdp = new PushDataPojo();
							if(!vol || !dis){
								_pdp.setAction(6);
							}else{
								_pdp.setAction(2);
							}
							_pdp.setTagId("" + Integer.parseInt(tp.getTagId(), 16));
							_pdp.setStationId("" + Integer.parseInt(cst, 16));
							_pdp.setAntennalId(ant);
							_pdp.setActive(act);
							_pdp.setVoltageOk(vol);
							_pdp.setDisassOk(dis);
							_pdp.setDateTime(_dt);
							dpd.add(_pdp);
							deduplic.add(tp.getTagId());
//							sendBuf.add(tp.getStationId());
						}
					}
				}
			}
		}else if(cst.equals(_cst) && ant.equals(_ant)){
			tp.setDoorAnt1("");tp.setDoorAnt2("");
			if(act && _act){
				if(!deduplic.contains(tp.getTagId())){
					PushDataPojo _pdp = new PushDataPojo();
					if(devType == 5 || devType == 6 || (!vol || !dis)){
						_pdp.setAction(5);
					}else{
						_pdp.setAction(1);
					}
					_pdp.setTagId("" + Integer.parseInt(tp.getTagId(), 16));
					_pdp.setStationId("" + Integer.parseInt(cst, 16));
					_pdp.setAntennalId(ant);
					_pdp.setActive(act);
					_pdp.setVoltageOk(vol);
					_pdp.setDisassOk(dis);
					_pdp.setDateTime(_dt);
					dpd.add(_pdp);
					deduplic.add(tp.getTagId());
					//只做公寓  所以不用判断发送与否
					sendBuf.add(tp.getStationId());
				}
			}else if(!act && !_act){
				if(!deduplic.contains(tp.getTagId())){
					PushDataPojo _pdp = new PushDataPojo();
					if(!vol || !dis){
						_pdp.setAction(6);
					}else{
						_pdp.setAction(2);
					}
					_pdp.setTagId("" + Integer.parseInt(tp.getTagId(), 16));
					_pdp.setStationId("" + Integer.parseInt(cst, 16));
					_pdp.setAntennalId(ant);
					_pdp.setActive(act);
					_pdp.setVoltageOk(vol);
					_pdp.setDisassOk(dis);
					_pdp.setDateTime(_dt);
					dpd.add(_pdp);
					deduplic.add(tp.getTagId());
				}
			}
		}
		long ct = System.currentTimeMillis();
		if(ct - dtFlag > 1000 && dpd.size() > 0){
			pp.setDataType(1);
			pp.setDataNumber(dpd.size());
			pp.setData(dpd);
			HttpPost httpPost = new HttpPost(ContainerUtil.getRemoteConfig().getPushUrl());
			httpPost.addHeader("Content-Type", "application/json;charset=utf-8");
			String data = JSONObject.toJSONString(pp);
			StringEntity entity = new StringEntity(data, "UTF-8");
			httpPost.setEntity(entity);
			CloseableHttpResponse rep = null;
			try {
				rep = (CloseableHttpResponse) cli.execute(httpPost);
//				HttpEntity repEntity = rep.getEntity();
				System.out.println(rep.getStatusLine() + "-->" + data);
				for(String devId : sendBuf){
					long sdt = ContainerUtil.getLgDevices().get(devId).getSendDT();
					int dt = ContainerUtil.getLgDevices().get(devId).getType();
					if(ct - sdt >= 4000){
						String adr = devAddr.get(devId);
						String[] addrs = adr.split(":");
						InetSocketAddress addr = new InetSocketAddress(addrs[0], Integer.parseInt(addrs[1]));
						String cmd = "";
						if((dt == 1 || dt == 2)){
							cmd = "rrpc,setpio,29,1,5000";
						}else if(dt > 3){
							cmd = "rrpc,setpio,26,1,5000";
						}
				        ByteBuf copiedBuffer = Unpooled.copiedBuffer(cmd.getBytes());
						DatagramPacket dp = new DatagramPacket(copiedBuffer, addr);
						ContainerUtil.getChannels().entrySet().iterator().next().getValue().channel().writeAndFlush(dp);
						ContainerUtil.getLgDevices().get(devId).setSendDT(ct);
					}
				}
//				if(ct - sendDT >= 4000){
//					for(String devId : sendBuf){
//						String adr = devAddr.get(devId);
//						String[] addrs = adr.split(":");
//						InetSocketAddress addr = new InetSocketAddress(addrs[0], Integer.parseInt(addrs[1]));
//				        ByteBuf copiedBuffer = Unpooled.copiedBuffer("rrpc,setpio,26,1,5000".getBytes());
//						DatagramPacket dp = new DatagramPacket(copiedBuffer, addr);
//						ContainerUtil.getChannels().entrySet().iterator().next().getValue().channel().writeAndFlush(dp);
//					}
//					sendDT = System.currentTimeMillis();
//				}
			}catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				try {
					if(rep != null){
						rep.close();
					}
					deduplic.clear();
					sendBuf.clear();
					dpd.clear();
					dtFlag = System.currentTimeMillis();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private void pushStation(){
		//基站数据
		pp.setDataType(2);
		pp.setDataNumber(2);
		List<PushDataPojo> dpd = new ArrayList<>();
		PushDataPojo dpd1 = new PushDataPojo();
		dpd1.setStationId("0058");
		dpd1.setStatus(true);
		dpd1.setDateTime(System.currentTimeMillis());
		PushDataPojo dpd2 = new PushDataPojo();
		dpd2.setStationId("0059");
		dpd2.setStatus(false);
		dpd2.setDateTime(System.currentTimeMillis());
		dpd.add(dpd1);
		dpd.add(dpd2);
		pp.setData(dpd);
		System.out.println("设备数据：" + JSONObject.toJSONString(pp));
		HttpPost httpPost = new HttpPost(ContainerUtil.getRemoteConfig().getPushUrl());
		httpPost.addHeader("Content-Type", "application/json;charset=utf-8");
		StringEntity entity = new StringEntity(JSONObject.toJSONString(pp), "UTF-8");
		httpPost.setEntity(entity);
		CloseableHttpResponse rep = null;
		try {
			rep = (CloseableHttpResponse) cli.execute(httpPost);
			HttpEntity repEntity = rep.getEntity();
			System.out.println(rep.getStatusLine());
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				dtFlag = System.currentTimeMillis();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	private void pushTag(){
		//标签数据
		pp.setDataType(1);
		pp.setDataNumber(5);
		List<PushDataPojo> dpd = new ArrayList<>();
		//进入
		PushDataPojo dpd1 = new PushDataPojo();
		dpd1.setAction(1);
		dpd1.setTagId("610000");
		dpd1.setStationId("0058");
		dpd1.setAntennalId("0100");
		dpd1.setLfRssi("1B");
		dpd1.setHfRssi("3F");
		dpd1.setActive(true);
		dpd1.setVoltageOk(true);
		dpd1.setDisassOk(true);
		dpd1.setDateTime(System.currentTimeMillis());
		dpd.add(dpd1);
		PushDataPojo dpd2 = new PushDataPojo();
		dpd2.setAction(2);
		dpd2.setTagId("610001");
		dpd2.setStationId("0058");
		dpd2.setAntennalId(null);
		dpd2.setLfRssi(null);
		dpd2.setHfRssi(null);
		dpd2.setActive(false);
		dpd2.setVoltageOk(true);
		dpd2.setDisassOk(true);
		dpd2.setDateTime(System.currentTimeMillis());
		dpd.add(dpd2);
		PushDataPojo dpd3 = new PushDataPojo();
		dpd3.setAction(4);
		dpd3.setTagId("610002");
		dpd3.setStationId("0058");
		dpd3.setAntennalId(null);
		dpd3.setLfRssi(null);
		dpd3.setHfRssi(null);
		dpd3.setActive(false);
		dpd3.setVoltageOk(false);
		dpd3.setDisassOk(true);
		dpd3.setDateTime(System.currentTimeMillis());
		dpd.add(dpd3);
		PushDataPojo dpd4 = new PushDataPojo();
		dpd4.setAction(5);
		dpd4.setTagId("610003");
		dpd4.setStationId("0058");
		dpd4.setAntennalId("0100");
		dpd4.setLfRssi("10");
		dpd4.setHfRssi("3A");
		dpd4.setActive(true);
		dpd4.setVoltageOk(true);
		dpd4.setDisassOk(false);
		dpd4.setDateTime(System.currentTimeMillis());
		dpd.add(dpd4);
		PushDataPojo dpd5 = new PushDataPojo();
		dpd5.setAction(6);
		dpd5.setTagId("610004");
		dpd5.setStationId("0058");
		dpd5.setAntennalId(null);
		dpd5.setLfRssi(null);
		dpd5.setHfRssi(null);
		dpd5.setActive(false);
		dpd5.setVoltageOk(false);
		dpd5.setDisassOk(false);
		dpd5.setDateTime(System.currentTimeMillis());
		dpd.add(dpd5);
		pp.setData(dpd);
		System.out.println(JSONObject.toJSONString(pp));
		HttpPost httpPost = new HttpPost(ContainerUtil.getRemoteConfig().getPushUrl());
//		httpPost.setConfig(reqCnf);
		httpPost.addHeader("Content-Type", "application/json;charset=utf-8");
		StringEntity entity = new StringEntity(JSONObject.toJSONString(pp), "UTF-8");
		httpPost.setEntity(entity);
		HttpResponse rep = null;
		try {
			rep = cli.execute(httpPost);
			HttpEntity repEntity = rep.getEntity();
			System.out.println(rep.getStatusLine());
			
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
