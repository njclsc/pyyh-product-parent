package com.pyyh.product.business.handler;

import java.util.List;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

public class TestUDPHander extends MessageToMessageDecoder<Object>{

	@Override
	protected void decode(ChannelHandlerContext ctx, Object msg, List<Object> out) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("------->udp");
	}

}
