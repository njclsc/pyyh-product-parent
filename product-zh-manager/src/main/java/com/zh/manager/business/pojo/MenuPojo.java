package com.zh.manager.business.pojo;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MenuPojo {
	private int id;
	private String menuName;
	private String name;
	private int menuPosition;
	private int parentIndex;
	private String sourceUrl;
	private List<MenuPojo> children;
	private String icon;
	private int excludeIndex;
	private boolean read;
	private boolean write;
}
