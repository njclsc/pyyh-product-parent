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
		sql.append("PRIMARY KEY (id),");
		sql.append("UNIQUE KEY DEV_INDEX (tagId)");
		sql.append(") ENGINE=INNODB DEFAULT CHARSET=utf8");
		
		
		sql.append("###CREATE TABLE tb_tmp_rule (");
		sql.append("id INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',");
		sql.append("ruleName VARCHAR(50) DEFAULT NULL COMMENT '规则名称',");
		sql.append("ruleType INT(11) DEFAULT NULL COMMENT '规则类型',");
		sql.append("areaIndex INT(11) DEFAULT NULL COMMENT '关联区域',");
		sql.append("PRIMARY KEY (id)");
		sql.append(") ENGINE=INNODB DEFAULT CHARSET=utf8");
		
		sql.append("###CREATE TABLE tb_tmp_timly (");
		sql.append("id INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',");
		sql.append("tagId VARCHAR(50) DEFAULT NULL COMMENT '标签Id',");
		sql.append("oldDeviceId VARCHAR(50) DEFAULT NULL COMMENT '上一设备id',");
		sql.append("currentDeviceId VARCHAR(50) DEFAULT NULL COMMENT '当前设备id',");
		sql.append("oldDeviceTime VARCHAR(50) DEFAULT NULL COMMENT '上一设备时间',");
		sql.append("currentDeviceTime VARCHAR(50) DEFAULT NULL COMMENT '当前设备时间',");
		sql.append("hbStationId VARCHAR(50) DEFAULT NULL COMMENT '2.4G基站',");
		sql.append("PRIMARY KEY (id),");
		sql.append("UNIQUE KEY TAG_INDEX (tagId)");
		sql.append(") ENGINE=INNODB DEFAULT CHARSET=utf8");
		
		sql.append("###CREATE TABLE tb_tmp_vehicle (");
		sql.append("id INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',");
		sql.append("ownerName VARCHAR(11) DEFAULT NULL COMMENT '车主姓名',");
		sql.append("ownerType int(3) DEFAULT NULL COMMENT '车主身份',");
		sql.append("movePhone VARCHAR(30) DEFAULT NULL COMMENT '电话',");
		sql.append("ownerNumber varchar(30) DEFAULT NULL COMMENT '学工业主号',");
		sql.append("vehicleType INT(3) DEFAULT NULL COMMENT '车辆类型',");
		sql.append("vehicleBrand varchar(30) DEFAULT NULL COMMENT '车辆品牌',");
		sql.append("vehicleColor VARCHAR(11) DEFAULT NULL COMMENT '颜色',");
		sql.append("rfidId1 varchar(30) DEFAULT NULL COMMENT '设备1ID',");
		sql.append("rfidId2 VARCHAR(30) DEFAULT NULL COMMENT '设备2ID',");
		sql.append("validity int(5) DEFAULT NULL COMMENT '有效期 单位 月份',");
		sql.append("registDate VARCHAR(30) DEFAULT NULL COMMENT '注册时间',");
		sql.append("photos varchar(500) DEFAULT NULL COMMENT '照片',");
		sql.append("status INT(2) DEFAULT NULL COMMENT '0:审批通过;1:审批未通过;2:审批中',");
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
