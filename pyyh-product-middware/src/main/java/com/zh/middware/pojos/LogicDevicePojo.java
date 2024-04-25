package com.zh.middware.pojos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LogicDevicePojo {
	private String address;
	private int port;
	private int type;
	private String deviceId;
	private int deviceIdDec;
	private String antIdIn;
	private String antIdOut;
	private long refresh;
}
