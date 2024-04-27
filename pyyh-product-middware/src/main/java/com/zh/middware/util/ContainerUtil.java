package com.zh.middware.util;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.http.impl.client.CloseableHttpClient;

import com.zh.middware.pojos.LogicDevicePojo;
import com.zh.middware.pojos.RemoteConfigPojo;
import com.zh.middware.pojos.TagPojo;

import io.netty.channel.ChannelFuture;

public class ContainerUtil {
	private static RemoteConfigPojo remoteConfig;
	private static LinkedBlockingQueue<Object> inQueue;
	private static ThreadPoolExecutor threadPool;
	
	private static ConcurrentHashMap<String, LogicDevicePojo> lgDevices;
	private static ConcurrentHashMap<String, TagPojo> tags;
	private static ConcurrentHashMap<String, String> devAddr;
	
	private static CloseableHttpClient httpClient;
	private static HashMap<String, ChannelFuture> channels;
	
	
//	private static List<PushDataPojo> pushData1;
//	private static List<PushDataPojo> pushData2;
//	private static int pushFlag = 1;
	
	public static ThreadPoolExecutor getThreadPool() {
		return threadPool;
	}

	public static void setThreadPool(ThreadPoolExecutor threadPool) {
		ContainerUtil.threadPool = threadPool;
	}
	public static ConcurrentHashMap<String, LogicDevicePojo> getLgDevices() {
		return lgDevices;
	}

	public static HashMap<String, ChannelFuture> getChannels() {
		return channels;
	}

	public static void setChannels(HashMap<String, ChannelFuture> channels) {
		ContainerUtil.channels = channels;
	}

	public static ConcurrentHashMap<String, String> getDevAddr() {
		return devAddr;
	}

	public static void setDevAddr(ConcurrentHashMap<String, String> devAddr) {
		ContainerUtil.devAddr = devAddr;
	}

//	public static List<PushDataPojo> getPushData1() {
//		return pushData1;
//	}
//
//	public static void setPushData1(List<PushDataPojo> pushData1) {
//		ContainerUtil.pushData1 = pushData1;
//	}
//
//	public static List<PushDataPojo> getPushData2() {
//		return pushData2;
//	}
//
//	public static void setPushData2(List<PushDataPojo> pushData2) {
//		ContainerUtil.pushData2 = pushData2;
//	}
//
//	public static int getPushFlag() {
//		return pushFlag;
//	}
//
//	public static void setPushFlag(int pushFlag) {
//		ContainerUtil.pushFlag = pushFlag;
//	}

	public static ConcurrentHashMap<String, TagPojo> getTags() {
		return tags;
	}

	public static void setTags(ConcurrentHashMap<String, TagPojo> tags) {
		ContainerUtil.tags = tags;
	}

	public static void setLgDevices(ConcurrentHashMap<String, LogicDevicePojo> lgDevices) {
		ContainerUtil.lgDevices = lgDevices;
	}

	public static RemoteConfigPojo getRemoteConfig() {
		return remoteConfig;
	}

	public static void setRemoteConfig(RemoteConfigPojo remoteConfig) {
		ContainerUtil.remoteConfig = remoteConfig;
	}

	public static CloseableHttpClient getHttpClient() {
		return httpClient;
	}

	public static void setHttpClient(CloseableHttpClient httpClient) {
		ContainerUtil.httpClient = httpClient;
	}

	public static LinkedBlockingQueue<Object> getInQueue() {
		return inQueue;
	}

	public static void setInQueue(LinkedBlockingQueue<Object> inQueue) {
		ContainerUtil.inQueue = inQueue;
	}
}
