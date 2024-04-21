package com.zh.middware.util;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

import com.zh.middware.pojos.RemoteConfigPojo;

public class ContainerUtil {
	private static RemoteConfigPojo remoteConfig;
	private static LinkedBlockingQueue<Object> inQueue;
	private static ThreadPoolExecutor threadPool;
	
	
	public static ThreadPoolExecutor getThreadPool() {
		return threadPool;
	}

	public static void setThreadPool(ThreadPoolExecutor threadPool) {
		ContainerUtil.threadPool = threadPool;
	}

	public static RemoteConfigPojo getRemoteConfig() {
		return remoteConfig;
	}

	public static void setRemoteConfig(RemoteConfigPojo remoteConfig) {
		ContainerUtil.remoteConfig = remoteConfig;
	}

	public static LinkedBlockingQueue<Object> getInQueue() {
		return inQueue;
	}

	public static void setInQueue(LinkedBlockingQueue<Object> inQueue) {
		ContainerUtil.inQueue = inQueue;
	}
}
