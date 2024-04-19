package com.zh.middware.business.coder;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ZCKF_ZH_Handler extends ChannelInboundHandlerAdapter{

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// TODO Auto-generated method stub
		String data = String.valueOf(msg);
		String deviceId = data.substring(4, 8);
		String cmdType = data.substring(8, 10);
		int tagNum = Integer.parseInt(data.substring(12, 14), 16);
		for(int i = 0; i < tagNum; i++){
			String tagData = data.substring(i * 14 + 14, (i + 1) * 14 + 14);
			System.out.println("--- " + tagData);
		}
		System.out.println("===>>>- " + data + "  " + deviceId + "  " + cmdType);
	}

}
