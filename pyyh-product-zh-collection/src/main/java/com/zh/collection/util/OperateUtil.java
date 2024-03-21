package com.zh.collection.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.alibaba.fastjson.JSONObject;
public class OperateUtil {

	
	public static JSONObject readConfig(String url) throws Exception{
		FileInputStream fis = new FileInputStream(new File(ContainerUtil.getRootPath() + "/" + url));
		byte[] data = new byte[fis.available()];
		fis.read(data);
		fis.close();
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < data.length; i++){
			sb.append((char)data[i]);
		}
		return JSONObject.parseObject(sb.toString());
	}
	public static ThreadPoolExecutor createThreadPool(){
		int cpuNum = Runtime.getRuntime().availableProcessors();
		return new ThreadPoolExecutor(
				cpuNum * 2,
				cpuNum * 2 + 5,
				60,
				TimeUnit.SECONDS,
				new ArrayBlockingQueue<Runnable>(50000),
				Executors.defaultThreadFactory(),
				new ThreadPoolExecutor.AbortPolicy()
			  );
	}
}
