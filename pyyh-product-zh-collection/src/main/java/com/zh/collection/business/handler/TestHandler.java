package com.zh.collection.business.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class TestHandler extends ChannelInboundHandlerAdapter{

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// TODO Auto-generated method stub
//		super.channelRead(ctx, msg);
		System.out.println("-->" + msg);
	}

}
