package com.pyyh.product.business.initializer;


import com.pyyh.product.business.handler.TestTcpHandler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

public class TestTcpChannelinitializer extends ChannelInitializer<SocketChannel>{

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		// TODO Auto-generated method stub
		ch.pipeline().addLast(new TestTcpHandler());
	}

}
