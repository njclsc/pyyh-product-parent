package com.pyyh.product.init.pojo;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
public class UDPConfigPojo {
	private boolean used;
	private List<String> ipAddress;
	private int miniBuf;
	private int initBuf;
	private int maxBuf;
	private int queueSize;
	private String lizer;
}
