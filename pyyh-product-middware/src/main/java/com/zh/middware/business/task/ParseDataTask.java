package com.zh.middware.business.task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;
import com.zh.middware.pojos.PushDataPojo;
import com.zh.middware.pojos.PushPojo;
import com.zh.middware.pojos.TagPojo;
import com.zh.middware.util.ContainerUtil;

public class ParseDataTask implements Runnable{
	private LinkedBlockingQueue<Object> inQueue;
	private long dtFlag;
	private PushPojo pp;
	private HttpClient cli;
	public ParseDataTask(LinkedBlockingQueue<Object> inQueue){
		this.inQueue = inQueue;
		this.pp = new PushPojo();
		this.pp.setData(new ArrayList<PushDataPojo>());
		this.dtFlag = System.currentTimeMillis();
//		this.reqCnf = RequestConfig.custom().setConnectTimeout(10000)
//						.setConnectionRequestTimeout(5000).setSocketTimeout(10000).build();
		this.cli = ContainerUtil.getHttpClient();
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
				String _cst = tp.get_stationId();
				String cst = tp.getStationId();
				String _ant = tp.get_antennalId();
				String ant = tp.getAntennalId();
				boolean _act = tp.is_isActive();
				boolean act = tp.isActive();
				boolean vol = tp.isVoltageOk();
				boolean dis = tp.isDisassOk();
				if(cst.equals(_cst) && ant.equals(_ant)){
					StringBuffer sb = new StringBuffer();
					sb.append(tp.getTagId());sb.append("    ");
					sb.append(cst);sb.append("    ");
					if(act && _act){
						sb.append("   into  ");
						if(vol && dis){
							sb.append(" noAlarm");
						}else{
							sb.append(" alarm");
						}
					}else if(!act && !_act){
						sb.append("   out  ");
						if(vol && dis){
							sb.append(" noAlarm");
						}else{
							sb.append(" alarm");
						}
					}
					
					
					System.out.println(sb.toString());
				}

			}catch(Exception e){
				e.printStackTrace();
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
