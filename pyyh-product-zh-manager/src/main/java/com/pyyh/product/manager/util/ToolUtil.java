package com.pyyh.product.manager.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.pyyh.product.manager.config.DataSourceConfiguer;
import com.pyyh.product.manager.pojo.UnitPojo;

public class ToolUtil {

	public static Map<String, Claim> tokenParse(String token){
		return JWT.decode(token).getClaims();
	}
	
	public static void createUnitAppDataTable(UnitPojo up) throws Exception{
		StringBuffer sql = new StringBuffer();
		sql.append("CREATE TABLE tb_tmp_area (");
		sql.append("id INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',");
		sql.append("areaName VARCHAR(100) DEFAULT NULL COMMENT '区域名称',");
		sql.append("type INT(2) DEFAULT NULL COMMENT '0:门禁1：停车场2：公寓',");
		sql.append("PRIMARY KEY (id)");
		sql.append(") ENGINE=INNODB DEFAULT CHARSET=utf8;");
		sql.append("###CREATE TABLE tb_tmp_device (");
		sql.append("id INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',");
		sql.append("deviceId VARCHAR(50) DEFAULT NULL COMMENT '设备ID',");
		sql.append("areaIndex INT(11) DEFAULT NULL COMMENT '关联区域id',");
		sql.append("type INT(11) DEFAULT NULL COMMENT '设备类型0:中心网关；1：低频地感',");
		sql.append("refreshTime VARCHAR(30) DEFAULT NULL COMMENT '刷新时间',");
		sql.append("PRIMARY KEY (id)");
		sql.append(") ENGINE=INNODB DEFAULT CHARSET=utf8");
		sql.append("###CREATE TABLE tb_tmp_tag (");
		sql.append("id INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',");
		sql.append("tagId VARCHAR(50) DEFAULT NULL COMMENT '标签id',");
		sql.append("status INT(3) DEFAULT NULL COMMENT '状态',");
		sql.append("type INT(11) DEFAULT NULL COMMENT '类型0:带低频1；2.4G',");
		sql.append("PRIMARY KEY (id)");
		sql.append(") ENGINE=INNODB DEFAULT CHARSET=utf8");
		sql.append("###CREATE TABLE tb_tmp_rule (");
		sql.append("id INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',");
		sql.append("ruleName VARCHAR(50) DEFAULT NULL COMMENT '规则名称',");
		sql.append("ruleType INT(11) DEFAULT NULL COMMENT '规则类型',");
		sql.append("areaIndex INT(11) DEFAULT NULL COMMENT '关联区域',");
		sql.append("PRIMARY KEY (id)");
		sql.append(") ENGINE=INNODB DEFAULT CHARSET=utf8");
		String[] sqls = sql.toString().split("###");
		Connection con = DataSourceConfiguer.getDataSource().getConnection();
		for(String sql1 : sqls){
			String _sql = sql1.toString().replaceAll("tb_tmp_", "tb_" + up.getId() + "_");
			Statement stat = con.createStatement();
			stat.executeUpdate(_sql);
			stat.close();
			
		}
		con.close();
		
		
	}
}
