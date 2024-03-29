package com.zh.manager.util;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.Session;

public class ContainerUtil {
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static ConcurrentHashMap<String, Session> endpointSession = new ConcurrentHashMap<>();
	//报警推送过滤缓存
	private static HashMap<String, List<Integer>> alarmIds = new HashMap<>();
	public static SimpleDateFormat getSdf() {
		return sdf;
	}

	
	public static HashMap<String, List<Integer>> getAlarmIds() {
		return alarmIds;
	}


	public static void setAlarmIds(HashMap<String, List<Integer>> alarmIds) {
		ContainerUtil.alarmIds = alarmIds;
	}


	public static ConcurrentHashMap<String, Session> getEndpointSession() {
		return endpointSession;
	}

	public static void setEndpointSession(ConcurrentHashMap<String, Session> endpointSession) {
		ContainerUtil.endpointSession = endpointSession;
	}


	public static void setSdf(SimpleDateFormat sdf) {
		ContainerUtil.sdf = sdf;
	}
}
