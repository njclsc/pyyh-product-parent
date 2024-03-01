package com.pyyh.product.login.pojo;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserPojo {
	private int id;
	private String accountName;
	private String password;
	private int unitIndex;
	private int roleIndex;
	private boolean login;
	private List<String> source;
}
