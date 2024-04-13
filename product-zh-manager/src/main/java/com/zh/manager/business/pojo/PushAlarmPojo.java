package com.zh.manager.business.pojo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PushAlarmPojo {
	private String ownerType;
	private String ownerName;
	private String movePhone;
	private String areaName;
	private String dateTime;
	private String alarmType;
	private String tagId;
	private int key;
	//演示用
	private int tagNum;
	private String posititon;
	private String deviceId;
	private String action;
}
