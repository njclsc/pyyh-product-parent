package com.zh.manager.business.pojo;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AreaPojo {
	private int id;
	private String areaName;
	private int type;
	private int unitIndex;
	private List<String> devices;
	private int pages;
	private int begin;
	private int rows;
	private String token;
}
