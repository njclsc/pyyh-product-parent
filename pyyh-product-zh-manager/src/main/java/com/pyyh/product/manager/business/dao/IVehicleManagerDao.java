package com.pyyh.product.manager.business.dao;

import org.springframework.stereotype.Repository;
import com.pyyh.product.manager.pojo.VehiclePojo;

@Repository
public interface IVehicleManagerDao {

	public void regist(VehiclePojo vp);
}
