package com.zh.middware.pojos;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PushPojo {
	private int dataType;
	private int dataNumber;
	private List<PushDataPojo> data;
}
