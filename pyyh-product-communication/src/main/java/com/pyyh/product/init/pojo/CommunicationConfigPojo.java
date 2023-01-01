package com.pyyh.product.init.pojo;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
public class CommunicationConfigPojo {
	private TcpServerConfigPojo tcpServer;
	private TcpClientConfigPojo tcpClient;
	private UDPConfigPojo udp;
	private List<String> lizers;
	private ThreadConfigPojo threadPool;
}
