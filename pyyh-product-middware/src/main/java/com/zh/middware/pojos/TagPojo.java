package com.zh.middware.pojos;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TagPojo {
	private String tagId;
	private String stationId;
	private String antennalId;
	private boolean isActive;
	private boolean voltageOk;
	private boolean disassOk;
	private boolean status;
	private long dateTime;
	private int action;
	private int dataType;
	private int dataNumber;
	private List<TagPojo> data;
}
