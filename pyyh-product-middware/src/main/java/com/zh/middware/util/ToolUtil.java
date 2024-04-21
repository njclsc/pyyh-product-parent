package com.zh.middware.util;

import java.net.InetSocketAddress;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.zh.middware.init.lizer.ZCKF_ZH_BusinessChain;
import com.zh.middware.pojos.CommunicationConfigPojo;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.AdaptiveRecvByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

public class ToolUtil {
	
	public static ThreadPoolExecutor createThreadPool(){
		int cpuNum = Runtime.getRuntime().availableProcessors();
		return new ThreadPoolExecutor(
				cpuNum * 2,
				cpuNum * 2 + 10,
				60,
				TimeUnit.SECONDS,
				new ArrayBlockingQueue<Runnable>(50000),
				Executors.defaultThreadFactory(),
				new ThreadPoolExecutor.AbortPolicy()
			  );
	}
	public static void openChannel(CommunicationConfigPojo ccp){
		String way = ccp.getWay();
		switch(way){
		case "udp":
			EventLoopGroup work = new NioEventLoopGroup();
			Bootstrap boot = new Bootstrap();
			boot.group(work).channel(NioDatagramChannel.class).option(ChannelOption.RCVBUF_ALLOCATOR, new AdaptiveRecvByteBufAllocator(ccp.getMiniBuf(),
				ccp.getInitBuf(), ccp.getMaxBuf())).handler(new ZCKF_ZH_BusinessChain());
			for(String s : ccp.getIpAddress()){
				String[] tmpAddress = s.split(":");
				ChannelFuture f = boot.bind(new InetSocketAddress(tmpAddress[0], Integer.parseInt(tmpAddress[1])));
				//==========================
			}
			break;
//		case "tcpServer":
//			System.out.println("1");
//			break;
//		case "tcpClient":
//			System.out.println("1");
//			break;
		}
	}
}
