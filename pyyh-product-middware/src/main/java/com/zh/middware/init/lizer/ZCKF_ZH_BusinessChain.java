package com.zh.middware.init.lizer;

import com.zh.middware.business.coder.ZCKF_ZH_Decoder;
import com.zh.middware.business.coder.ZCKF_ZH_Handler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.DatagramChannel;

public class ZCKF_ZH_BusinessChain extends ChannelInitializer<DatagramChannel>{
	@Override
	protected void initChannel(DatagramChannel arg0) throws Exception {
		// TODO Auto-generated method stub
		arg0.pipeline().addLast(new ZCKF_ZH_Decoder());
		arg0.pipeline().addLast(new ZCKF_ZH_Handler());
	}

}
