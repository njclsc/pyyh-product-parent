package com.zh.collection.pojo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TagPojo {
	private int id;
	private String tagId;
	private int vehicleIndex;
	private int status;
	private int type;
	private String installDate;
	private boolean expire;
	private String expireDateTime;
}
