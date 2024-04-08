package com.zh.manager.business.pojo;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WebSocketPushSevenDayPojo {
	private List<String> date;
	private List<Integer> registNumber;
	private List<Integer> alarmNum;
	private List<Integer> noOperate;
}
