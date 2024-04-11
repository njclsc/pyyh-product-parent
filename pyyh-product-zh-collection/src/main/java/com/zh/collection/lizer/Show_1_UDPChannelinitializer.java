package com.zh.collection.lizer;

import com.zh.collection.business.handler.DataOperateHandler;
import com.zh.collection.business.handler.Show_1_DataOperateHandler;
import com.zh.collection.business.handler.Show_1_ZHUDPDecoder;
import com.zh.collection.business.handler.TestHandler;
import com.zh.collection.business.handler.ZHUDPDecoder;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.DatagramChannel;

public class Show_1_UDPChannelinitializer extends ChannelInitializer<DatagramChannel>{

	@Override
	protected void initChannel(DatagramChannel arg0) throws Exception {
		// TODO Auto-generated method stub
		arg0.pipeline().addLast(new Show_1_ZHUDPDecoder()).addLast(new Show_1_DataOperateHandler());
	}

}
