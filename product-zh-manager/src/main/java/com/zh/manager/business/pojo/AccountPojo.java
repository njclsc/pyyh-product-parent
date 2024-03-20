package com.zh.manager.business.pojo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountPojo {
	private int id;
	private String accountName;
	private String password;
	private String confirm;
	private int unitIndex;
	private int roleIndex;
	private int pages;
	private int begin;
	private int rows;
}
