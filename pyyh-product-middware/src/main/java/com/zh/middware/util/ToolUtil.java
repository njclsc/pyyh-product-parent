package com.zh.middware.util;

import java.net.InetSocketAddress;

import com.zh.middware.pojos.CommunicationConfigPojo;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.AdaptiveRecvByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramChannel;
import io.netty.channel.socket.nio.NioDatagramChannel;

public class ToolUtil {
	
	public static void openChannel(CommunicationConfigPojo ccp){
		String way = ccp.getWay();
		switch(way){
		case "udp":
			EventLoopGroup work = new NioEventLoopGroup();
			Bootstrap boot = new Bootstrap();
			boot.group(work).channel(NioDatagramChannel.class).option(ChannelOption.RCVBUF_ALLOCATOR, new AdaptiveRecvByteBufAllocator(ccp.getMiniBuf(),
				ccp.getInitBuf(), ccp.getMaxBuf())).handler(new ChannelInitializer<DatagramChannel>(){
				@Override
				protected void initChannel(DatagramChannel arg0) throws Exception {
				// TODO Auto-generated method stub
					System.out.println("1xxx");	/////////////////////
			}});
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