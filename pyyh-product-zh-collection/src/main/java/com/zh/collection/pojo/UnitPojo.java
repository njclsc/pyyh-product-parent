package com.zh.collection.pojo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UnitPojo {
	private int id;
	private String unitName;
	private String unitCode;
	private int parentUnit;
	private int unitType;
	private String channelAddr;
}
