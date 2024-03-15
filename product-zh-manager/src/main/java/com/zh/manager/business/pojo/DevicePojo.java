package com.zh.manager.business.pojo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DevicePojo {
	private int id;
	private String deviceId;
	private int areaIndex;
	private int type;
	private String refreshTime;
	private int unitIndex;
}
