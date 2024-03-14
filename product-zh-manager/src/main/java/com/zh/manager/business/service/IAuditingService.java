package com.zh.manager.business.service;

import com.zh.manager.business.pojo.ResponsePojo;
import com.zh.manager.business.pojo.VehiclePojo;

public interface IAuditingService {
	
	public ResponsePojo auditing(VehiclePojo vp);
}
