package com.pyyh.product.init.serviceimp;

import java.io.File;
import java.io.FileInputStream;
import java.net.InetSocketAddress;

import com.alibaba.fastjson.JSONObject;
import com.pyyh.product.init.pojo.CommunicationConfigPojo;
import com.pyyh.product.init.pojo.TcpServerConfigPojo;
import com.pyyh.product.util.ContainerUtil;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.AdaptiveRecvByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class CommunicationSourceServiceTCPServerImp extends AbstractSourceServiceImp{

	@Override
	public <T, P> T registSource(P p) throws Exception {
		// TODO Auto-generated method stub
		CommunicationConfigPojo ccp = (CommunicationConfigPojo)p;
		TcpServerConfigPojo tscp = ccp.getTcpServer();
		if(tscp.isUsed()){
			EventLoopGroup boss = new NioEventLoopGroup();
			EventLoopGroup work = new NioEventLoopGroup();
			ServerBootstrap boot = new ServerBootstrap();
			boot.group(boss, work).channel(NioServerSocketChannel.class)
					.option(ChannelOption.SO_BACKLOG, tscp.getQueueSize())
					.childOption(ChannelOption.RCVBUF_ALLOCATOR, 
							new AdaptiveRecvByteBufAllocator(tscp.getMiniBuf(), 
									tscp.getInitBuf(), tscp.getMaxBuf())).childHandler(
							ContainerUtil.getLizer().get(tscp.getLizer())
			);
			for(String s : tscp.getIpAddress()){
				String[] tmpAddress = s.split(":");
				ChannelFuture f = boot.bind(new InetSocketAddress(tmpAddress[0], Integer.parseInt(tmpAddress[1])));
			}
		}
		return null;
	}

}
