package com.pyyh.product.util;

import java.util.HashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

import io.netty.channel.ChannelInitializer;

public class ContainerUtil {
	private static HashMap<String, ChannelInitializer<?>> lizer;
	private static ThreadPoolExecutor pool;
	private static BlockingQueue<Runnable> queue;
	
	public static HashMap<String, ChannelInitializer<?>> getLizer() {
		return lizer;
	}

	public static void setLizer(HashMap<String, ChannelInitializer<?>> lizer) {
		ContainerUtil.lizer = lizer;
	}

	public static ThreadPoolExecutor getPool() {
		return pool;
	}

	public static void setPool(ThreadPoolExecutor pool) {
		ContainerUtil.pool = pool;
	}

	public static BlockingQueue<Runnable> getQueue() {
		return queue;
	}

	public static void setQueue(BlockingQueue<Runnable> queue) {
		ContainerUtil.queue = queue;
	}

	
}
