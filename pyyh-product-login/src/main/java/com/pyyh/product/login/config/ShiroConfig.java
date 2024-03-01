package com.pyyh.product.login.config;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.pyyh.product.login.util.CustomRealm;

@Configuration
public class ShiroConfig {
	@Bean(name = "shiroFilter")
	public ShiroFilterFactoryBean shiroFilter(@Qualifier("securityManager")DefaultWebSecurityManager securityManager){
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		return shiroFilterFactoryBean;
	}
	@Bean(name = "securityManager")
	public DefaultWebSecurityManager securityManager(@Qualifier("customRealm")CustomRealm customRealm) {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(customRealm);
		return securityManager;
	}
	@Bean(name = "customRealm")
	public CustomRealm customRealm(){
		CustomRealm customRealm = new CustomRealm();
		return customRealm;
	}
}
