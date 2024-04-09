package com.zh.middware.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zh.middware.init.service.ISourceService;
import com.zh.middware.pojos.CommunicationConfigPojo;
import com.zh.middware.util.ToolUtil;

@Configuration
public class InitForSystem {
	@Autowired
	@Qualifier("SourceOperateOfConfig")
	private ISourceService sourceService;
	@Bean
	public void initSource(){
		try {
			CommunicationConfigPojo ccp = (CommunicationConfigPojo) sourceService.loadSource("business-config/zh-communication.cnf");
			for(CommunicationConfigPojo _ccp : ccp.getWays()){
				if(_ccp.isUsed()){
					ToolUtil.openChannel(_ccp);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
