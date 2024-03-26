package com.zh.collection.pojo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlarmPojo {
	private int id;
	private String tagId;
	private int alarmType;
	private String position;
	private String ownerName;
	private String areaName;
	private String ownerType;
	private String dateTime;
}
