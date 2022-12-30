package com.pyyh.product.init.pojo;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ThreadConfigPojo {
	private int corePoolSize;
	private int maximumPoolSize;
	private int keepAliveTime;
	private String timeUnit;
	private int queueSize;
	private ThreadPoolExecutor pool;
	private BlockingQueue<Runnable> queue;
}
