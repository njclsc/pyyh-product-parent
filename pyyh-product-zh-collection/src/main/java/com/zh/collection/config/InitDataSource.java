package com.zh.collection.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.zh.collection.util.ContainerUtil;

@Configuration
public class InitDataSource {
	@Value("${spring.datasource.name}")
	private String name;
	@Value("${spring.datasource.driver-class-name}")
	private String driver_class_name;
	@Value("${spring.datasource.url}")
	private String url;
	@Value("${spring.datasource.username}")
	private String username;
	@Value("${spring.datasource.password}")
	private String password;
	@Value("${spring.datasource.druid.validationQuery}")
	private String validationQuery;
	@Value("${spring.datasource.druid.initialSize}")
	private String initialSize;
	@Value("${spring.datasource.druid.maxActive}")
	private String maxActive;
	@Value("${spring.datasource.druid.minIdle}")
	private String minIdle;
	@Value("${spring.datasource.druid.maxWait}")
	private String maxWait;
	@Value("${spring.datasource.druid.testOnBorrow}")
	private String testOnBorrow;
	@Value("${spring.datasource.druid.testOnReturn}")
	private String testOnReturn;
	@Value("${spring.datasource.druid.testWhileIdle}")
	private String testWhileIdle;
	@Value("${spring.datasource.druid.timeBetweenEvictionRunsMillis}")
	private String timeBetweenEvictionRunsMillis;
	@Value("${spring.datasource.druid.minEvictableIdleTimeMillis}")
	private String minEvictableIdleTimeMillis;
	@Value("${spring.datasource.druid.removeAbandoned}")
	private String removeAbandoned;
	@Value("${spring.datasource.druid.removeAbandonedTimeout}")
	private String removeAbandonedTimeout;
	@Value("${spring.datasource.druid.logAbandoned}")
	private String logAbandoned;
	
	@Bean(name = "dataSourceDruid")
	public DataSource initDataSourceDruid() throws Exception {
		Map<String, String> config = new HashMap<String, String>();
		config.put("driver_class_name", driver_class_name);
		config.put("url", url);
		config.put("username", username);
		config.put("password", password);
		config.put("removeAbandoned", removeAbandoned);
		config.put("minEvictableIdleTimeMillis", minEvictableIdleTimeMillis);
		config.put("timeBetweenEvictionRunsMillis", timeBetweenEvictionRunsMillis);
		config.put("testWhileIdle", testWhileIdle);
		config.put("testOnReturn", testOnReturn);
		config.put("testOnBorrow", testOnBorrow);
		config.put("maxWait", maxWait);
		config.put("minIdle", minIdle);
		config.put("maxActive", maxActive);
		config.put("validationQuery", validationQuery);
		config.put("initialSize", initialSize);
		config.put("removeAbandonedTimeout", removeAbandonedTimeout);
		config.put("logAbandoned", logAbandoned);
		DataSource ds = DruidDataSourceFactory.createDataSource(config);
		ContainerUtil.setDataSource(ds);
		return ds;
	}
	
}
