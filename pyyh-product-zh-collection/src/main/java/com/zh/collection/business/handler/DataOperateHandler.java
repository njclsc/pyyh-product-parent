package com.zh.collection.business.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class DataOperateHandler extends ChannelInboundHandlerAdapter{

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// TODO Auto-generated method stub
		String data = String.valueOf(msg);
		String deviceId = data.substring(4, 8);
		String cmdType = data.substring(8, 10);
		int tagNum = Integer.parseInt(data.substring(12, 14), 16);
		for(int i = 0; i < tagNum; i++){
			String tagData = data.substring(i * 16 + 14, (i + 1) * 16 + 14);
			String tagId = tagData.substring(0, 6);
			String status = tagData.substring(6, 8);
			String antId = tagData.substring(8, 12);
			String lowrsi = tagData.substring(12, 14);
			String hrsi = tagData.substring(14, 16);
			System.out.println(tagId + "  " + status + "  " + antId + "  " + lowrsi + "  " + hrsi);
		}
		System.out.println(deviceId + "  " + cmdType + "  " + tagNum + " " + ctx.channel().localAddress().toString().substring(1));
	}

}
