package com.zh.manager.config;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import com.zh.manager.util.ContainerUtil;

@Configuration
public class WebSocketConfig {
	
	@Bean
	public ServerEndpointExporter serverEndpointExporter() throws Exception{
		return new ServerEndpointExporter();
	}
}
