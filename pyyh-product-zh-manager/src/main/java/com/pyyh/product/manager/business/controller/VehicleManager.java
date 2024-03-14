package com.pyyh.product.manager.business.controller;

import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.pyyh.product.manager.business.service.IVehicleService;
import com.pyyh.product.manager.pojo.VehiclePojo;
import com.pyyh.product.manager.util.QrCodeUtil;

@RestController
@RequestMapping("/vehicle")
public class VehicleManager {
	@Autowired
	@Qualifier("VehicleServiceImp")
	private IVehicleService vehicleService;
	@RequestMapping("regist")
	public Object regist(@RequestBody VehiclePojo vp){
		return vehicleService.regist(vp);
	}
	@RequestMapping(method = RequestMethod.GET, value = "scanCode")
	public Object scanCode(@RequestParam String unitIndex){
		return unitIndex;
	}
	@RequestMapping("qrCode")
	public Object createCode(@RequestBody Map<Object, Object> param, HttpServletResponse response){
		try {
			
			BufferedImage img = QrCodeUtil.createCode("http://www.zh-dzkj.com/?unit=1");
			OutputStream os = response.getOutputStream();
			ImageIO.write(img, "jpeg", os);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "--";
	}
}
