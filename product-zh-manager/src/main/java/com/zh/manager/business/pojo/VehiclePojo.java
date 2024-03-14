package com.zh.manager.business.pojo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VehiclePojo {
	private int id;
	private String ownerName;
	private int ownerType;
	private String movePhone;
	private String ownerNumber;
	private int vehicleType;
	private String vehicleBrand;
	private String vehicleColor;
	private String rfidId1;
	private String rfidId2;
	private int validity;
	private String registDate;
	private String photos;
	private int status;
	private int unitIndex;
}
