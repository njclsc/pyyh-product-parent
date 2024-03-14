package com.zh.manager.business.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zh.manager.business.pojo.VehiclePojo;

@Repository
public interface IVehicleManagerDao {
	//管理
	public int add(VehiclePojo UnitPojo);
	public int update(VehiclePojo UnitPojo);
	public VehiclePojo findById(VehiclePojo UnitPojo);
	public List<VehiclePojo> findByAll(VehiclePojo UnitPojo);
	public void delete(VehiclePojo UnitPojo);
	//审核
	public void auditing(VehiclePojo UnitPojo);
	
}
