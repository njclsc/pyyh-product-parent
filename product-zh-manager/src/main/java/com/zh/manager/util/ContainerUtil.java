package com.zh.manager.util;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.Session;

import com.zh.manager.business.pojo.PushAlarmPojo;

public class ContainerUtil {
	//显示用
	private static HashMap<String, PushAlarmPojo> show_2_InfoBuf = new HashMap<>();
	private static HashMap<String, PushAlarmPojo> show_1_InfoBuf = new HashMap<>();
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static ConcurrentHashMap<String, Session> endpointSession = new ConcurrentHashMap<>();
	//报警推送过滤缓存[暂时不使用]只发变化数据
	private static ConcurrentHashMap<String, List<String>> alarmIds = new ConcurrentHashMap<>();
	public static SimpleDateFormat getSdf() {
		return sdf;
	}

	public static ConcurrentHashMap<String, List<String>> getAlarmIds() {
		return alarmIds;
	}

	public static void setAlarmIds(ConcurrentHashMap<String, List<String>> alarmIds) {
		ContainerUtil.alarmIds = alarmIds;
	}
	public static ConcurrentHashMap<String, Session> getEndpointSession() {
		return endpointSession;
	}


	public static HashMap<String, PushAlarmPojo> getShow_2_InfoBuf() {
		return show_2_InfoBuf;
	}

	public static void setShow_2_InfoBuf(HashMap<String, PushAlarmPojo> show_2_InfoBuf) {
		ContainerUtil.show_2_InfoBuf = show_2_InfoBuf;
	}

	public static HashMap<String, PushAlarmPojo> getShow_1_InfoBuf() {
		return show_1_InfoBuf;
	}

	public static void setShow_1_InfoBuf(HashMap<String, PushAlarmPojo> show_1_InfoBuf) {
		ContainerUtil.show_1_InfoBuf = show_1_InfoBuf;
	}

	public static void setEndpointSession(ConcurrentHashMap<String, Session> endpointSession) {
		ContainerUtil.endpointSession = endpointSession;
	}


	public static void setSdf(SimpleDateFormat sdf) {
		ContainerUtil.sdf = sdf;
	}
}
