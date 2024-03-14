package com.pyyh.product.manager.business.serviceimp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pyyh.product.manager.business.dao.IVehicleManagerDao;
import com.pyyh.product.manager.business.service.IVehicleService;
import com.pyyh.product.manager.pojo.ResponsePojo;
import com.pyyh.product.manager.pojo.VehiclePojo;

@Service("VehicleServiceImp")
public class VehicleServiceImp implements IVehicleService{
	@Autowired
	private IVehicleManagerDao vehicleDao;
	@Override
	public ResponsePojo regist(VehiclePojo vp) {
		// TODO Auto-generated method stub
		vehicleDao.regist(vp);
		ResponsePojo rp = new ResponsePojo();
		
		return null;
	}

}
