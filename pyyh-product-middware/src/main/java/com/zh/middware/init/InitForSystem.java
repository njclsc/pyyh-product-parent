package com.zh.middware.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zh.middware.init.service.ISourceService;
import com.zh.middware.pojos.CommunicationConfigPojo;
import com.zh.middware.pojos.RemoteConfigPojo;
import com.zh.middware.util.ContainerUtil;
import com.zh.middware.util.ToolUtil;

@Configuration
public class InitForSystem {
	@Autowired
	@Qualifier("SourceOperateOfConfig")
	private ISourceService sourceService;
	@Bean
	public void initSource(){
		try {
			//channel
			CommunicationConfigPojo ccp = sourceService.loadSource("business-config/zh-communication.cnf", CommunicationConfigPojo.class);
			for(CommunicationConfigPojo _ccp : ccp.getWays()){
				if(_ccp.isUsed()){
					ToolUtil.openChannel(_ccp);
				}
			}
			//device cnf
			ContainerUtil.setRemoteConfig(sourceService.loadSource("business-config/zh-remote.cnf", RemoteConfigPojo.class));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
