package com.pyyh.product.init.serviceimp;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.pyyh.product.business.initializer.TestTcpChannelinitializer;
import com.pyyh.product.business.initializer.TestUDPChannelinitializer;
import com.pyyh.product.init.pojo.CommunicationConfigPojo;

import io.netty.channel.ChannelInitializer;

public class CommunicationSourceServiceLizerImp extends AbstractSourceServiceImp{

	@SuppressWarnings({ "resource", "unchecked" })
	@Override
	public <T, P> T loadSource(P p) throws Exception {
		// TODO Auto-generated method stub
		FileInputStream communication_fis = null;
		communication_fis = new FileInputStream(new File(this.getSourceLocation() + "/" + (String)p));
		byte[] data_config = new byte[communication_fis.available()];
		communication_fis.read(data_config);
		CommunicationConfigPojo ccp = JSONObject.parseObject(data_config, CommunicationConfigPojo.class);
		return (T)ccp;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public <T, P> T registSource(P p) throws Exception {
		// TODO Auto-generated method stub
		HashMap<String, ChannelInitializer> lizer = new HashMap<>();
		CommunicationConfigPojo ccp = (CommunicationConfigPojo)p;
		List<String> lizerKey = ccp.getLizers();
		lizer.put(lizerKey.get(0), new TestTcpChannelinitializer());
		lizer.put(lizerKey.get(1), new TestUDPChannelinitializer());
		//此处追加业务链  例如：
//		lizer.put(lizerKey.get(2), new X1Channelinitializer());
//		lizer.put(lizerKey.get(3), new X2Channelinitializer());
		return (T)lizer;
	}

}
