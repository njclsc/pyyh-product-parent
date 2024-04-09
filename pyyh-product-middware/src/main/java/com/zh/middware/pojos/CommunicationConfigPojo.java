package com.zh.middware.pojos;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommunicationConfigPojo {
	private String way;
	private boolean used;
	private List<String> ipAddress;
	private int miniBuf;
	private int initBuf;
	private int maxBuf;
	private int queueSize;
	private List<CommunicationConfigPojo> ways;
	
}
