package com.pyyh.product.init.serviceimp;

import java.net.InetSocketAddress;
import java.util.HashMap;

import com.pyyh.product.init.pojo.CommunicationConfigPojo;
import com.pyyh.product.init.pojo.UDPConfigPojo;
import com.pyyh.product.util.ContainerUtil;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.AdaptiveRecvByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

public class CommunicationSourceServiceUDPImp extends AbstractSourceServiceImp{

	@Override
	public <T, P> T registSource(P p) throws Exception {
		// TODO Auto-generated method stub
		CommunicationConfigPojo ccp = (CommunicationConfigPojo)p;
		UDPConfigPojo udpCnf = ccp.getUdp();
		HashMap<String, ChannelFuture> udp = new HashMap<>();
		if(udpCnf.isUsed()){
			EventLoopGroup work = new NioEventLoopGroup();
			Bootstrap boot = new Bootstrap();
			boot.group(work).channel(NioDatagramChannel.class).option(ChannelOption.RCVBUF_ALLOCATOR, 
					new AdaptiveRecvByteBufAllocator(udpCnf.getMiniBuf(),
						udpCnf.getInitBuf(), udpCnf.getMaxBuf())).handler(
							ContainerUtil.getLizer().get(udpCnf.getLizer())
					);
			for(String s : udpCnf.getIpAddress()){
				String[] tmpAddress = s.split(":");
				ChannelFuture f = boot.bind(new InetSocketAddress(tmpAddress[0], Integer.parseInt(tmpAddress[1])));
				udp.put(s, f);
			}
		}
		if(udp.size() > 0){
			ContainerUtil.getCommunicationSources().put("protocol_udp", udp);
		}
		return null;
	}

}
