package com.zh.collection.lizer;

import com.zh.collection.business.handler.TestHandler;
import com.zh.collection.business.handler.ZHUDPDecoder;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.DatagramChannel;

public class UDPChannelinitializer extends ChannelInitializer<DatagramChannel>{

	@Override
	protected void initChannel(DatagramChannel arg0) throws Exception {
		// TODO Auto-generated method stub
		arg0.pipeline().addLast(new ZHUDPDecoder()).addLast(new TestHandler());
	}

}
