package com.pyyh.product.util;

import java.util.HashMap;

import io.netty.channel.ChannelInitializer;

public class ContainerUtil {
	private static HashMap<String, ChannelInitializer<?>> lizer;

	public static HashMap<String, ChannelInitializer<?>> getLizer() {
		return lizer;
	}

	public static void setLizer(HashMap<String, ChannelInitializer<?>> lizer) {
		ContainerUtil.lizer = lizer;
	}

	
}
