package com.zh.middware.init;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zh.middware.business.task.ParseDataTask;
import com.zh.middware.init.service.ISourceService;
import com.zh.middware.pojos.CommunicationConfigPojo;
import com.zh.middware.pojos.LogicDevicePojo;
import com.zh.middware.pojos.RemoteConfigPojo;
import com.zh.middware.pojos.TagPojo;
import com.zh.middware.util.ContainerUtil;
import com.zh.middware.util.ToolUtil;

import io.netty.channel.ChannelFuture;

@Configuration
public class InitForSystem {
	@Autowired
	@Qualifier("SourceOperateOfConfig")
	private ISourceService sourceService;
	@Bean
	public void initSource(){
		try {
			//oth
			ContainerUtil.setHttpClient(HttpClients.createDefault());
			ContainerUtil.setLgDevices(new ConcurrentHashMap<String, LogicDevicePojo>());
			ContainerUtil.setDevAddr(new ConcurrentHashMap<String, String>());
			ContainerUtil.setTags(new ConcurrentHashMap<String, TagPojo>());
			ContainerUtil.setChannels(new HashMap<String, ChannelFuture>());
			//queue
			ContainerUtil.setInQueue(new LinkedBlockingQueue<>());
			//channel
			CommunicationConfigPojo ccp = sourceService.loadSource("business-config/zh-communication.cnf", CommunicationConfigPojo.class);
			for(CommunicationConfigPojo _ccp : ccp.getWays()){
				if(_ccp.isUsed()){
					ToolUtil.openChannel(_ccp);
				}
			}
			//device cnf
			ContainerUtil.setRemoteConfig(sourceService.loadSource("business-config/zh-remote.cnf", RemoteConfigPojo.class));
			ToolUtil.forInitLoad();
			//pool
			ContainerUtil.setThreadPool(ToolUtil.createThreadPool());
			ContainerUtil.getThreadPool().execute(new ParseDataTask(ContainerUtil.getInQueue()));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
