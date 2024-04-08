package com.zh.manager.business.pojo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RulePojo {
	private int id;
	private String ruleName;
	private int ruleType;
	private int areaIndex;
	private int time;
	private int unitIndex;
	private String token;
	private int pages;
	private int begin;
	private int rows;
}
