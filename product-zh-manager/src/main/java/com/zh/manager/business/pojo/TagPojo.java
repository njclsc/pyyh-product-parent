package com.zh.manager.business.pojo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TagPojo {
	private int id;
	private String tagId;
	private int vehicleIndex;
	private int status;
	private int type;
	private int unitIndex;
	private String installDate;
	private boolean expire;
	private String expireDateTime;
	private String token;
	private int pages;
	private int begin;
	private int rows;
}
