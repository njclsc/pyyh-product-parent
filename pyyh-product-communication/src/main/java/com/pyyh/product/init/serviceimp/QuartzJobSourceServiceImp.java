package com.pyyh.product.init.serviceimp;

import java.io.File;
import java.io.FileInputStream;

import com.alibaba.fastjson.JSONObject;
import com.pyyh.product.init.pojo.QuartzConfigPojo;

public class QuartzJobSourceServiceImp  extends AbstractSourceServiceImp{
	
	@Override
	public String getSourceLocation() {
		// TODO Auto-generated method stub
		return super.getSourceLocation();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T, P> T loadSource(P p) throws Exception {
		// TODO Auto-generated method stub
		FileInputStream quartz_fis = null;
		quartz_fis = new FileInputStream(new File(this.getSourceLocation() + "/" + (String)p));
		byte[] data_config = new byte[quartz_fis.available()];
		quartz_fis.read(data_config);
		QuartzConfigPojo qcf = JSONObject.parseObject(data_config, QuartzConfigPojo.class);
		quartz_fis.close();
		return (T)qcf;
	}

	@Override
	public <T, P> T registSource(P p) throws Exception {
		// TODO Auto-generated method stub
		
		return null;
	}

}
