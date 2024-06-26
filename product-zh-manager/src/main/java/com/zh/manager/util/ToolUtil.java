package com.zh.manager.util;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Map;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.zh.manager.business.pojo.UnitPojo;
import com.zh.manager.config.DataSourceConfiguer;
public class ToolUtil {

	public static Map<String, Claim> tokenParse(String token){
		return JWT.decode(token).getClaims();
	}
	public static String int2HexStr(int num){
		return Integer.toHexString(num).toUpperCase();
	}
	public static int hexStr2Int(String str){
		return Integer.parseInt(str, 16);
	}
	public static void createUnitAppDataTable(UnitPojo up) throws Exception{
		StringBuffer sql = new StringBuffer();
		sql.append("CREATE TABLE tb_tmp_area (");
		sql.append("id INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',");
		sql.append("areaName VARCHAR(100) DEFAULT NULL COMMENT '区域名称',");
		sql.append("type INT(2) DEFAULT NULL COMMENT '0:门禁外;1:门禁内;2：停车场;3：公寓',");
		sql.append("PRIMARY KEY (id)");
		sql.append(") ENGINE=INNODB DEFAULT CHARSET=utf8;");
		//-----------------------
		sql.append("###CREATE TABLE tb_tmp_device (");
		sql.append("id INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',");
		sql.append("deviceId VARCHAR(50) DEFAULT NULL COMMENT '设备ID',");
		sql.append("areaIndex INT(11) DEFAULT NULL COMMENT '关联区域id',");
		sql.append("type INT(11) DEFAULT NULL COMMENT '设备类型0:中心网关；1：低频地感',");
		sql.append("refreshTime VARCHAR(30) DEFAULT NULL COMMENT '刷新时间',");
		sql.append("status INT(2) DEFAULT NULL COMMENT '设备状态0：正常1：异常',");
		sql.append("PRIMARY KEY (id),");
		sql.append("UNIQUE KEY DEVID_INDEX (deviceId)");
		sql.append(") ENGINE=INNODB DEFAULT CHARSET=utf8");
		//-----------------------
		sql.append("###CREATE TABLE tb_tmp_tag (");
		sql.append("id INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',");
		sql.append("tagId VARCHAR(50) DEFAULT NULL COMMENT '标签id',");
		sql.append("vehicleIndex INT(11) DEFAULT NULL COMMENT '对应车辆',");
		sql.append("status INT(3) DEFAULT NULL COMMENT '状态',");
		sql.append("type INT(11) DEFAULT NULL COMMENT '类型0:带低频1；2.4G',");
		sql.append("installDate VARCHAR(30) DEFAULT NULL COMMENT '安装时间',");
		sql.append("expire tinyint(1) DEFAULT '0' COMMENT '是否过期',");
		sql.append("expireDateTime INT(30) DEFAULT NULL COMMENT '过期时刻',");
		sql.append("PRIMARY KEY (id),");
		sql.append("UNIQUE KEY DEV_INDEX (tagId)");
		sql.append(") ENGINE=INNODB DEFAULT CHARSET=utf8");
		//-----------------------
		sql.append("###CREATE TABLE tb_tmp_rule (");
		sql.append("id INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',");
		sql.append("ruleName VARCHAR(50) DEFAULT NULL COMMENT '规则名称',");
		sql.append("ruleType INT(11) DEFAULT NULL COMMENT '0:违停提醒时间;1:确认违停时间;2:进出延时判断时间',");
		sql.append("areaIndex INT(11) DEFAULT NULL COMMENT '关联区域',");
		sql.append("time INT(11) DEFAULT NULL COMMENT '规则时限(单位：分钟)',");
		sql.append("PRIMARY KEY (id)");
		sql.append(") ENGINE=INNODB DEFAULT CHARSET=utf8");
		//-----------------------
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
		//-----------------------
		sql.append("###CREATE TABLE tb_tmp_alarm (");
		sql.append("id INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,");
		sql.append("tagId VARCHAR(20) DEFAULT NULL,");
		sql.append("alarmType INT(2) DEFAULT NULL COMMENT '报警类型0:过期1:违停2:电瓶入楼',");
		sql.append("position varchar(20) DEFAULT NULL COMMENT '位置对应设备ID',");
		sql.append("ownerName varchar(30) DEFAULT NULL,");
		sql.append("areaName VARCHAR(50) DEFAULT NULL,");
		sql.append("ownerType varchar(20) DEFAULT NULL COMMENT '车主类型',");
		sql.append("dateTime VARCHAR(30) DEFAULT NULL COMMENT '报警时间',");
		sql.append("status INT(1) DEFAULT NULL COMMENT '0:以处理；1：未处理',");
		sql.append("PRIMARY KEY (id)");
		sql.append(") ENGINE=InnoDB DEFAULT CHARSET=utf8");
		//-----------------------
		sql.append("###CREATE TABLE tb_tmp_vehicle (");
		sql.append("id INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',");
		sql.append("ownerName VARCHAR(11) DEFAULT NULL COMMENT '车主姓名',");
		sql.append("ownerType INT(3) DEFAULT NULL COMMENT '车主身份',");
		sql.append("movePhone VARCHAR(30) DEFAULT NULL COMMENT '电话',");
		sql.append("ownerNumber VARCHAR(30) DEFAULT NULL COMMENT '学工业主号',");
		sql.append("vehicleType INT(3) DEFAULT NULL COMMENT '车辆类型',");
		sql.append("vehicleBrand VARCHAR(30) DEFAULT NULL COMMENT '车辆品牌',");
		sql.append("vehicleColor VARCHAR(11) DEFAULT NULL COMMENT '颜色',");
		sql.append("rfidId1 VARCHAR(30) DEFAULT NULL COMMENT '设备1ID',");
		sql.append("rfidId2 VARCHAR(30) DEFAULT NULL COMMENT '设备2ID',");
		sql.append("validity INT(5) DEFAULT NULL COMMENT '有效期 单位 月份',");
		sql.append("registDate VARCHAR(30) DEFAULT NULL COMMENT '注册时间',");
		sql.append("photos VARCHAR(500) DEFAULT NULL COMMENT '照片',");
		sql.append("status INT(2) DEFAULT NULL COMMENT '0:以安装1:已通过;2:已驳回;3:待审核',");
		sql.append("reason VARCHAR(150) DEFAULT NULL COMMENT '审核信息备注',");
		sql.append("position VARCHAR(10) DEFAULT NULL COMMENT '车辆位置out:不在园区',");
		sql.append("PRIMARY KEY (id)");
		sql.append(") ENGINE=INNODB DEFAULT CHARSET=utf8");
		//-----------------------
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
