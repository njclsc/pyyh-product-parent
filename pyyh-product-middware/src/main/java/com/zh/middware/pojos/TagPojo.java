package com.zh.middware.pojos;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TagPojo {
	private String tagId;
	private String stationId;
	private String _stationId;
	private String antennalId;
	private String _antennalId;
	private boolean isActive;
	private boolean _isActive;
	private boolean voltageOk;
	private boolean disassOk;
//	private boolean status;
	private long dateTime;
	
	private String doorAnt1 = "";
	private String doorAnt2 = "";
//	private int action;
//	private int dataType;
//	private int dataNumber;
//	private List<TagPojo> data;
}
