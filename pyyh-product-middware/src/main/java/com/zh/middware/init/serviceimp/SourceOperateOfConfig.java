package com.zh.middware.init.serviceimp;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.zh.middware.pojos.CommunicationConfigPojo;
import com.zh.middware.util.ToolUtil;

@Service("SourceOperateOfConfig")
public class SourceOperateOfConfig extends SourceOperate{
	@Override
	public <T, P> T loadSource(P p) throws Exception {
		
		// TODO Auto-generated method stub
		FileInputStream communication_fis = new FileInputStream(new File(this.getRoot() + "/" + (String)p));
		byte[] data_config = new byte[communication_fis.available()];
		communication_fis.read(data_config);
		communication_fis.close();
		CommunicationConfigPojo ccp = JSONObject.parseObject(data_config, CommunicationConfigPojo.class);
		return (T)ccp;
	}

}
