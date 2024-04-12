package com.zh.collection.util;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

import javax.sql.DataSource;

import com.zh.collection.pojo.AreaPojo;
import com.zh.collection.pojo.CachePojo;
import com.zh.collection.pojo.DevicePojo;
import com.zh.collection.pojo.RulePojo;
import com.zh.collection.pojo.TagPojo;
import com.zh.collection.pojo.TimlyPojo;
import com.zh.collection.pojo.UnitPojo;
import com.zh.collection.pojo.VehiclePojo;

public class ContainerUtil {
	private static String addr1;
	private static String addr2;
	
	
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static DataSource dataSource;
	private static String rootPath;
	private static ThreadPoolExecutor threadPool;
	private static LinkedBlockingQueue<Object> inQueue = new LinkedBlockingQueue<>();
	private static LinkedBlockingQueue<Object> saveQueue = new LinkedBlockingQueue<>();
	private static HashMap<String, CachePojo<String, UnitPojo, AreaPojo, DevicePojo, TagPojo, RulePojo, TimlyPojo, VehiclePojo>> caches = new HashMap<>();
	public static DataSource getDataSource() {
		return dataSource;
	}

	public static void setDataSource(DataSource dataSource) {
		ContainerUtil.dataSource = dataSource;
	}

	public static String getRootPath() {
		return rootPath;
	}

	public static void setRootPath(String rootPath) {
		ContainerUtil.rootPath = rootPath;
	}

	

	public static SimpleDateFormat getSdf() {
		return sdf;
	}

	public static void setSdf(SimpleDateFormat sdf) {
		ContainerUtil.sdf = sdf;
	}

	public static HashMap<String, CachePojo<String, UnitPojo, AreaPojo, DevicePojo, TagPojo, RulePojo, TimlyPojo, VehiclePojo>> getCaches() {
		return caches;
	}

	public static void setCaches(
			HashMap<String, CachePojo<String, UnitPojo, AreaPojo, DevicePojo, TagPojo, RulePojo, TimlyPojo, VehiclePojo>> caches) {
		ContainerUtil.caches = caches;
	}

	public static LinkedBlockingQueue<Object> getSaveQueue() {
		return saveQueue;
	}

	public static String getAddr1() {
		return addr1;
	}

	public static void setAddr1(String addr1) {
		ContainerUtil.addr1 = addr1;
	}

	public static String getAddr2() {
		return addr2;
	}

	public static void setAddr2(String addr2) {
		ContainerUtil.addr2 = addr2;
	}

	public static void setSaveQueue(LinkedBlockingQueue<Object> saveQueue) {
		ContainerUtil.saveQueue = saveQueue;
	}

	public static ThreadPoolExecutor getThreadPool() {
		return threadPool;
	}

	public static LinkedBlockingQueue<Object> getInQueue() {
		return inQueue;
	}

	public static void setInQueue(LinkedBlockingQueue<Object> inQueue) {
		ContainerUtil.inQueue = inQueue;
	}

	public static void setThreadPool(ThreadPoolExecutor threadPool) {
		ContainerUtil.threadPool = threadPool;
	}





}
