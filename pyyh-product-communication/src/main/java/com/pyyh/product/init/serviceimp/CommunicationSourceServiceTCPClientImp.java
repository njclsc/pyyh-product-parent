package com.pyyh.product.init.serviceimp;

import java.net.InetSocketAddress;

import com.pyyh.product.init.pojo.CommunicationConfigPojo;
import com.pyyh.product.init.pojo.TcpClientConfigPojo;
import com.pyyh.product.util.ContainerUtil;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.AdaptiveRecvByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class CommunicationSourceServiceTCPClientImp  extends AbstractSourceServiceImp{

	@Override
	public <T, P> T registSource(P p) throws Exception {
		// TODO Auto-generated method stub
		CommunicationConfigPojo ccp = (CommunicationConfigPojo)p;
		TcpClientConfigPojo tccp = ccp.getTcpClient();
		if(tccp.isUsed()){
			EventLoopGroup work = new NioEventLoopGroup();
			Bootstrap boot = new Bootstrap();
			boot.group(work).channel(NioSocketChannel.class).option(ChannelOption.RCVBUF_ALLOCATOR, new AdaptiveRecvByteBufAllocator(
					tccp.getMiniBuf(), tccp.getInitBuf(), tccp.getMaxBuf())).handler(
							ContainerUtil.getLizer().get(tccp.getLizer()));
			String[] remoteAddress = tccp.getTargetAddress().split(":");
			for(String address : tccp.getLocalAddress()){
				String[] localAddress = address.split(":");
				ChannelFuture f = boot.connect(new InetSocketAddress(remoteAddress[0], Integer.parseInt(remoteAddress[1])), 
						new InetSocketAddress(localAddress[0], Integer.parseInt(localAddress[1])));
			}
		}
		return null;
	}

}
