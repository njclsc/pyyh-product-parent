package com.pyyh.product.init.pojo;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class TcpClientConfigPojo {
	private boolean used;
	private List<String> localAddress;
	private String targetAddress;
	private int miniBuf;
	private int initBuf;
	private int maxBuf;
	private int queueSize;
	private String lizer;
}
