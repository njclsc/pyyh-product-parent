package com.zh.collection.init;

import java.net.InetSocketAddress;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.zh.collection.lizer.UDPChannelinitializer;
import com.zh.collection.pojo.UDPConfigPojo;
import com.zh.collection.util.ContainerUtil;
import com.zh.collection.util.OperateUtil;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.AdaptiveRecvByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

@Component
public class InitApplication {

	@Bean
	public Object initCommunications() throws Exception{
		//路径
		ContainerUtil.setRootPath(System.getProperty("user.dir"));
		//开启udp端口
		UDPConfigPojo udpCnf = JSONObject.toJavaObject(OperateUtil.readConfig("business-config/communication-config.zh").getJSONObject("udp"), UDPConfigPojo.class);
		if(udpCnf.isUsed()){
			EventLoopGroup work = new NioEventLoopGroup();
			Bootstrap boot = new Bootstrap();
			boot.group(work).channel(NioDatagramChannel.class).option(ChannelOption.RCVBUF_ALLOCATOR, 
					new AdaptiveRecvByteBufAllocator(udpCnf.getMiniBuf(),
						udpCnf.getInitBuf(), udpCnf.getMaxBuf())).handler(new UDPChannelinitializer());
			for(String s : udpCnf.getIpAddress()){
				String[] tmpAddress = s.split(":");
				ChannelFuture f = boot.bind(new InetSocketAddress(tmpAddress[0], Integer.parseInt(tmpAddress[1])));
			}
		}
		return "";
	}
}
