package com.zh.collection.pojo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TimlyPojo {
	private int id;
	private String tagId;
	private String oldDeviceId;
	private String currentDeviceId;
	private String oldDeviceTime;
	private String currentDeviceTime;
	private String hbStationId;
	private String mappingAddress;
	private String positionType;
	private String actionInfo = "none";
	//0=标签过期;1=进门后违停;2=电瓶入楼
	private int saveType;
}
