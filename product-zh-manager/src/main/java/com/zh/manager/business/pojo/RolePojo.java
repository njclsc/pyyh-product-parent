package com.zh.manager.business.pojo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RolePojo {
	private int id;
	private String roleName;
	private int unitIndex;
	private String authority;
	private String token;
	private int pages;
	private int begin;
	private int rows;
}
