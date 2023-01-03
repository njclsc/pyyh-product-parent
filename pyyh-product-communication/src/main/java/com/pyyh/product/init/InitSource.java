package com.pyyh.product.init;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.pyyh.product.init.pojo.CommunicationConfigPojo;
import com.pyyh.product.init.pojo.ThreadConfigPojo;
import com.pyyh.product.init.service.ISourceService;
import com.pyyh.product.init.serviceimp.CommunicationSourceServiceLizerImp;
import com.pyyh.product.init.serviceimp.CommunicationSourceServiceTCPClientImp;
import com.pyyh.product.init.serviceimp.CommunicationSourceServiceTCPServerImp;
import com.pyyh.product.init.serviceimp.CommunicationSourceServiceThreadPoolImp;
import com.pyyh.product.init.serviceimp.CommunicationSourceServiceUDPImp;
import com.pyyh.product.util.ContainerUtil;

@Component
public class InitSource {

	@Bean
	public void communicationInit() throws Exception{
		//netty lizer业务链
		ISourceService sourceServiceLizer = new CommunicationSourceServiceLizerImp();
		CommunicationConfigPojo ccp = sourceServiceLizer.loadSource("business-config/communication-config.pyyh");
		ContainerUtil.setLizer(sourceServiceLizer.registSource(ccp));
		//线程池
		ISourceService threadPool = new CommunicationSourceServiceThreadPoolImp();
		ThreadConfigPojo tcp = (ThreadConfigPojo)threadPool.registSource(ccp);
		ContainerUtil.setPool(tcp.getPool());
		//tcpServer端口
		ISourceService sourceServiceTcp = new CommunicationSourceServiceTCPServerImp();
		sourceServiceTcp.registSource(ccp);
		//udp端口
		ISourceService sourceServiceUdp = new CommunicationSourceServiceUDPImp();
		sourceServiceUdp.registSource(ccp);
		//tcpClient端口
		ISourceService sourceServiceTcpClient = new CommunicationSourceServiceTCPClientImp();
		sourceServiceTcpClient.registSource(ccp);
	}
}
