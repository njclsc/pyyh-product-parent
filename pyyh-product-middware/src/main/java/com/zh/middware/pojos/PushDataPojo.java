package com.zh.middware.pojos;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PushDataPojo {
	private int action;
	private String tagId;
	private String stationId;
	private String antennalId;
	private String lfRssi;
	private String hfRssi;
	private boolean isActive;
	private boolean voltageOk;
	private boolean disassOk;
	private boolean status;
	private long dateTime;
}
