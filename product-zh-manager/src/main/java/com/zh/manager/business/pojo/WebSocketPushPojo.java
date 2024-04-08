package com.zh.manager.business.pojo;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WebSocketPushPojo {
	
	private int registNumber;
	private int todayRegistNumber;
	private int areaNumber;
	private WebSocketPushSevenDayPojo nearNum;
	private List<WebSocketPushDevPojo> devNum;
	private int excDevNum;
	private int nomoreNum;
	private WebSocketPushSevenDayPojo nearAlarmNum;
}
