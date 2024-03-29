/*
SQLyog Ultimate v12.08 (64 bit)
MySQL - 5.7.37 : Database - db_zh_bicycle
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`db_zh_bicycle` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `db_zh_bicycle`;

/*Table structure for table `tb_1_alarm` */

DROP TABLE IF EXISTS `tb_1_alarm`;

CREATE TABLE `tb_1_alarm` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tagId` varchar(20) DEFAULT NULL,
  `alarmType` int(2) DEFAULT NULL COMMENT '报警类型0:过期1:违停2:电瓶入楼',
  `position` varchar(20) DEFAULT NULL COMMENT '位置对应设备ID',
  `ownerName` varchar(30) DEFAULT NULL,
  `areaName` varchar(50) DEFAULT NULL,
  `ownerType` varchar(20) DEFAULT NULL COMMENT '车主类型',
  `dateTime` varchar(30) DEFAULT NULL COMMENT '报警时间',
  `status` int(1) DEFAULT NULL COMMENT '0:以处理；1：未处理',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4904 DEFAULT CHARSET=utf8;

/*Data for the table `tb_1_alarm` */

insert  into `tb_1_alarm`(`id`,`tagId`,`alarmType`,`position`,`ownerName`,`areaName`,`ownerType`,`dateTime`,`status`) values (4901,'000001',0,'','gg','2','','2024-03-29 13:22:37',1),(4902,'000001',1,'','gg','2','','2024-03-29 13:26:08',1),(4903,'000001',2,'','gg','4','','2024-03-29 13:26:19',1);

/*Table structure for table `tb_1_alarm_2024_03` */

DROP TABLE IF EXISTS `tb_1_alarm_2024_03`;

CREATE TABLE `tb_1_alarm_2024_03` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tagId` varchar(20) DEFAULT NULL,
  `alarmType` int(2) DEFAULT NULL COMMENT '报警类型0:过期1:违停2:电瓶入喽',
  `position` varchar(20) DEFAULT NULL COMMENT '位置对应设备ID',
  `dateTime` varchar(30) DEFAULT NULL COMMENT '报警时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_1_alarm_2024_03` */

/*Table structure for table `tb_1_area` */

DROP TABLE IF EXISTS `tb_1_area`;

CREATE TABLE `tb_1_area` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `areaName` varchar(100) DEFAULT NULL COMMENT '区域名称',
  `type` int(2) DEFAULT NULL COMMENT '0:门禁1：停车场2：公寓',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `tb_1_area` */

insert  into `tb_1_area`(`id`,`areaName`,`type`) values (1,'大门0',0),(2,'大门1',1),(3,'停车场0',2),(4,'宿舍楼',3);

/*Table structure for table `tb_1_device` */

DROP TABLE IF EXISTS `tb_1_device`;

CREATE TABLE `tb_1_device` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `deviceId` varchar(50) DEFAULT NULL COMMENT '设备ID',
  `areaIndex` int(11) DEFAULT NULL COMMENT '关联区域id',
  `type` int(11) DEFAULT NULL COMMENT '设备类型0:中心网关；1：低频地感',
  `refreshTime` varchar(30) DEFAULT NULL COMMENT '刷新时间',
  `status` int(2) DEFAULT NULL COMMENT '设备状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Data for the table `tb_1_device` */

insert  into `tb_1_device`(`id`,`deviceId`,`areaIndex`,`type`,`refreshTime`,`status`) values (1,'0058',NULL,0,NULL,0),(2,'0001',1,1,NULL,0),(3,'0002',2,1,NULL,0),(4,'0003',3,1,NULL,0),(5,'0059',4,0,NULL,0);

/*Table structure for table `tb_1_rule` */

DROP TABLE IF EXISTS `tb_1_rule`;

CREATE TABLE `tb_1_rule` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `ruleName` varchar(50) DEFAULT NULL COMMENT '规则名称',
  `ruleType` int(11) DEFAULT NULL COMMENT '规则类型',
  `areaIndex` int(11) DEFAULT NULL COMMENT '关联区域',
  `time` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `tb_1_rule` */

insert  into `tb_1_rule`(`id`,`ruleName`,`ruleType`,`areaIndex`,`time`) values (1,'door',2,NULL,5000),(2,'parking',0,NULL,10000),(3,'parking1',1,NULL,15000);

/*Table structure for table `tb_1_tag` */

DROP TABLE IF EXISTS `tb_1_tag`;

CREATE TABLE `tb_1_tag` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `tagId` varchar(50) DEFAULT NULL COMMENT '标签id',
  `vehicleIndex` int(11) DEFAULT NULL COMMENT '对应车辆',
  `status` int(3) DEFAULT NULL COMMENT '状态',
  `type` int(11) DEFAULT NULL COMMENT '类型0:带低频1；2.4G',
  `installDate` varchar(30) DEFAULT NULL COMMENT '安装时间',
  `expire` tinyint(1) DEFAULT '0' COMMENT '是否过期',
  `expireDateTime` varchar(30) DEFAULT NULL COMMENT '过期时刻',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `tb_1_tag` */

insert  into `tb_1_tag`(`id`,`tagId`,`vehicleIndex`,`status`,`type`,`installDate`,`expire`,`expireDateTime`) values (1,'000001',3,NULL,NULL,'2024-01-01 00:00:00',1,'2024-03-01 00:00:00');

/*Table structure for table `tb_1_timly` */

DROP TABLE IF EXISTS `tb_1_timly`;

CREATE TABLE `tb_1_timly` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `tagId` varchar(50) DEFAULT NULL COMMENT '标签Id',
  `oldDeviceId` varchar(50) DEFAULT NULL COMMENT '上一设备id',
  `currentDeviceId` varchar(50) DEFAULT NULL COMMENT '当前设备id',
  `oldDeviceTime` varchar(50) DEFAULT NULL COMMENT '上一设备时间',
  `currentDeviceTime` varchar(50) DEFAULT NULL COMMENT '当前设备时间',
  `hbStationId` varchar(50) DEFAULT NULL COMMENT '2.4G基站',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `tb_1_timly` */

insert  into `tb_1_timly`(`id`,`tagId`,`oldDeviceId`,`currentDeviceId`,`oldDeviceTime`,`currentDeviceTime`,`hbStationId`) values (1,'000001',NULL,NULL,NULL,NULL,NULL);

/*Table structure for table `tb_1_vehicle` */

DROP TABLE IF EXISTS `tb_1_vehicle`;

CREATE TABLE `tb_1_vehicle` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `ownerName` varchar(11) DEFAULT NULL COMMENT '车主姓名',
  `ownerType` int(3) DEFAULT NULL COMMENT '车主身份',
  `movePhone` varchar(30) DEFAULT NULL COMMENT '电话',
  `ownerNumber` varchar(30) DEFAULT NULL COMMENT '学工业主号',
  `vehicleType` int(3) DEFAULT NULL COMMENT '车辆类型',
  `vehicleBrand` varchar(30) DEFAULT NULL COMMENT '车辆品牌',
  `vehicleColor` varchar(11) DEFAULT NULL COMMENT '颜色',
  `rfidId1` varchar(30) DEFAULT NULL COMMENT '设备1ID',
  `rfidId2` varchar(30) DEFAULT NULL COMMENT '设备2ID',
  `validity` int(5) DEFAULT NULL COMMENT '有效期 单位 月份',
  `registDate` varchar(30) DEFAULT NULL COMMENT '注册时间',
  `photos` varchar(500) DEFAULT NULL COMMENT '照片',
  `status` int(2) DEFAULT NULL COMMENT '0:以安装1:已通过;2:已驳回;3:待审核',
  `reason` varchar(150) DEFAULT NULL,
  `position` varchar(10) DEFAULT NULL COMMENT '车辆位置out:不在园区',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

/*Data for the table `tb_1_vehicle` */

insert  into `tb_1_vehicle`(`id`,`ownerName`,`ownerType`,`movePhone`,`ownerNumber`,`vehicleType`,`vehicleBrand`,`vehicleColor`,`rfidId1`,`rfidId2`,`validity`,`registDate`,`photos`,`status`,`reason`,`position`) values (3,'gg',0,'1','gg',0,'TJDW','rgb',NULL,NULL,NULL,'2024-03-01 13:58:56',NULL,0,NULL,NULL),(5,'ggxx',0,'1','gggg',1,'TJDW','rgbxx',NULL,NULL,NULL,'2024-03-02 14:00:41',NULL,0,'西部',NULL);

/*Table structure for table `tb_sym_operate_logger` */

DROP TABLE IF EXISTS `tb_sym_operate_logger`;

CREATE TABLE `tb_sym_operate_logger` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `unitIndex` int(11) DEFAULT NULL COMMENT '单位索引',
  `module` varchar(20) DEFAULT NULL COMMENT '模块',
  `action` varchar(10) DEFAULT NULL COMMENT '动作',
  `data` varchar(3000) DEFAULT NULL COMMENT '数据',
  `oldAddress` varchar(30) DEFAULT NULL COMMENT '数据更新前的单位地址，因为是缓存的key',
  `key` varchar(30) DEFAULT NULL COMMENT '缓存中的key',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_sym_operate_logger` */

/*Table structure for table `tb_sys_account` */

DROP TABLE IF EXISTS `tb_sys_account`;

CREATE TABLE `tb_sys_account` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `accountName` varchar(50) DEFAULT NULL COMMENT '系统账号',
  `password` varchar(50) DEFAULT NULL COMMENT '密码',
  `unitIndex` int(11) DEFAULT NULL COMMENT '单位索引',
  `roleIndex` int(11) DEFAULT NULL COMMENT '角色索引',
  PRIMARY KEY (`id`),
  UNIQUE KEY `ACCOUNT_KEY` (`accountName`,`unitIndex`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `tb_sys_account` */

insert  into `tb_sys_account`(`id`,`accountName`,`password`,`unitIndex`,`roleIndex`) values (1,'zh','33',1,1);

/*Table structure for table `tb_sys_menu` */

DROP TABLE IF EXISTS `tb_sys_menu`;

CREATE TABLE `tb_sys_menu` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `menuName` varchar(50) DEFAULT NULL COMMENT '菜单名称',
  `menuPosition` int(11) DEFAULT NULL COMMENT '菜单位置[相对于同一级菜单位置]',
  `parentIndex` int(11) DEFAULT '-1' COMMENT '上一级菜单索引',
  `sourceUrl` varchar(150) DEFAULT NULL COMMENT '菜单资源连接',
  `icon` varchar(50) DEFAULT NULL COMMENT '菜单图标',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

/*Data for the table `tb_sys_menu` */

insert  into `tb_sys_menu`(`id`,`menuName`,`menuPosition`,`parentIndex`,`sourceUrl`,`icon`) values (1,'主页',0,-1,'/1',NULL),(2,'数据列表',0,1,'/1/1',NULL),(3,'数据看板',1,1,'/1/2',NULL),(4,'车辆业务',1,-1,'/2',NULL),(5,'设备管理',2,-1,'/3',NULL),(6,'标签管理',0,5,'/3/1',NULL),(7,'基站管理',1,5,'/3/2',NULL),(8,'系统日志',3,-1,'/4',NULL),(9,'报警数据',0,8,'/4/1',NULL),(10,'违停数据',1,8,'/4/2',NULL),(11,'系统配置',4,-1,'/5',NULL),(12,'用户管理',0,11,'/5/1',NULL),(13,'单位管理',1,11,'/car/dataUnit.html',NULL),(14,'组织管理',2,11,'/5/3',NULL),(15,'角色管理',3,11,'/car/dataRole.html',NULL),(16,'区域管理',4,11,'/5/5',NULL),(17,'车辆注册',0,4,'/2/1',NULL);

/*Table structure for table `tb_sys_orginization` */

DROP TABLE IF EXISTS `tb_sys_orginization`;

CREATE TABLE `tb_sys_orginization` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `orgnizationName` varchar(150) DEFAULT NULL COMMENT '组织名称',
  `unitId` int(11) DEFAULT NULL COMMENT '所属单位',
  `parentOrgnizationIndex` int(11) DEFAULT NULL COMMENT '上级组织',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_sys_orginization` */

/*Table structure for table `tb_sys_role` */

DROP TABLE IF EXISTS `tb_sys_role`;

CREATE TABLE `tb_sys_role` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `roleName` varchar(50) DEFAULT NULL COMMENT '角色名称',
  `unitIndex` int(11) DEFAULT NULL COMMENT '单位索引',
  `authority` varchar(500) DEFAULT NULL COMMENT '拥有权限',
  PRIMARY KEY (`id`),
  UNIQUE KEY `ROLE_KEY` (`roleName`,`unitIndex`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

/*Data for the table `tb_sys_role` */

insert  into `tb_sys_role`(`id`,`roleName`,`unitIndex`,`authority`) values (1,'管理员',1,'r_-1#w_2,3,6,7,9,10,12,13,14,15,16,17'),(2,'普通用户',1,'r_2,3#w_7'),(3,'role1',1,'xxxggg'),(6,'测试',14,'r_2,3#w_7'),(7,'667',0,'888'),(8,'88',0,'7'),(9,'44',0,'22'),(10,'9955',0,'33'),(11,'哈哈哈',0,'23'),(12,'6',0,'6'),(13,'66',0,'661'),(14,'',0,'999'),(15,NULL,0,NULL);

/*Table structure for table `tb_sys_unit` */

DROP TABLE IF EXISTS `tb_sys_unit`;

CREATE TABLE `tb_sys_unit` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `unitName` varchar(150) DEFAULT NULL COMMENT '单位名称',
  `unitCode` varchar(150) DEFAULT NULL COMMENT '单位编码',
  `parentUnit` int(5) DEFAULT NULL COMMENT '上一级单位[受管理于单位索引]',
  `unitType` int(2) DEFAULT NULL COMMENT '0：开发商 1:学校；2：小区',
  `channelAddr` varchar(50) DEFAULT NULL COMMENT '接收硬件数据地址，一单位对应一个',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8;

/*Data for the table `tb_sys_unit` */

insert  into `tb_sys_unit`(`id`,`unitName`,`unitCode`,`parentUnit`,`unitType`,`channelAddr`) values (1,'上海正晗电子','ZHDZ_D_01',NULL,0,'192.168.3.188:10006');

/*Table structure for table `tb_tmp_alarm` */

DROP TABLE IF EXISTS `tb_tmp_alarm`;

CREATE TABLE `tb_tmp_alarm` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tagId` varchar(20) DEFAULT NULL,
  `alarmType` int(2) DEFAULT NULL COMMENT '报警类型0:过期1:违停2:电瓶入楼',
  `position` varchar(20) DEFAULT NULL COMMENT '位置对应设备ID',
  `ownerName` varchar(30) DEFAULT NULL,
  `areaName` varchar(50) DEFAULT NULL,
  `ownerType` varchar(20) DEFAULT NULL COMMENT '车主类型',
  `dateTime` varchar(30) DEFAULT NULL COMMENT '报警时间',
  `status` int(1) DEFAULT NULL COMMENT '0:以处理；1：未处理',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_tmp_alarm` */

/*Table structure for table `tb_tmp_area` */

DROP TABLE IF EXISTS `tb_tmp_area`;

CREATE TABLE `tb_tmp_area` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `areaName` varchar(100) DEFAULT NULL COMMENT '区域名称',
  `type` int(2) DEFAULT NULL COMMENT '0:门禁外；1门禁内2：停车场3：公寓',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_tmp_area` */

/*Table structure for table `tb_tmp_date_history` */

DROP TABLE IF EXISTS `tb_tmp_date_history`;

CREATE TABLE `tb_tmp_date_history` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_tmp_date_history` */

/*Table structure for table `tb_tmp_device` */

DROP TABLE IF EXISTS `tb_tmp_device`;

CREATE TABLE `tb_tmp_device` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `deviceId` varchar(50) DEFAULT NULL COMMENT '设备ID',
  `areaIndex` int(11) DEFAULT NULL COMMENT '关联区域id',
  `type` int(11) DEFAULT NULL COMMENT '设备类型0:中心网关；1：低频地感',
  `refreshTime` varchar(30) DEFAULT NULL COMMENT '刷新时间',
  `status` int(2) DEFAULT NULL COMMENT '设备状态0：正常1：异常',
  PRIMARY KEY (`id`),
  UNIQUE KEY `DEVID_INDEX` (`deviceId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_tmp_device` */

/*Table structure for table `tb_tmp_rule` */

DROP TABLE IF EXISTS `tb_tmp_rule`;

CREATE TABLE `tb_tmp_rule` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `ruleName` varchar(50) DEFAULT NULL COMMENT '规则名称',
  `ruleType` int(11) DEFAULT NULL COMMENT '0:违停提醒时间;1:确认违停时间;2:进出延时判断时间;3:公寓2.4G规则;4:标签过期时间',
  `areaIndex` int(11) DEFAULT NULL COMMENT '关联区域：0：所有区域',
  `time` int(3) DEFAULT NULL COMMENT '规则时限(单位：分钟)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_tmp_rule` */

/*Table structure for table `tb_tmp_tag` */

DROP TABLE IF EXISTS `tb_tmp_tag`;

CREATE TABLE `tb_tmp_tag` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `tagId` varchar(50) DEFAULT NULL COMMENT '标签id',
  `vehicleIndex` int(11) DEFAULT NULL COMMENT '对应车辆',
  `status` int(3) DEFAULT NULL COMMENT '状态',
  `type` int(11) DEFAULT NULL COMMENT '类型0:带低频1；2.4G',
  `installDate` varchar(30) DEFAULT NULL COMMENT '安装时间',
  `expire` tinyint(1) DEFAULT '0' COMMENT '是否过期',
  `expireDateTime` varchar(30) DEFAULT NULL COMMENT '过期时刻',
  PRIMARY KEY (`id`),
  UNIQUE KEY `DEV_INDEX` (`tagId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_tmp_tag` */

/*Table structure for table `tb_tmp_timly` */

DROP TABLE IF EXISTS `tb_tmp_timly`;

CREATE TABLE `tb_tmp_timly` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `tagId` varchar(50) DEFAULT NULL COMMENT '标签Id',
  `oldDeviceId` varchar(50) DEFAULT NULL COMMENT '上一设备id',
  `currentDeviceId` varchar(50) DEFAULT NULL COMMENT '当前设备id',
  `oldDeviceTime` varchar(50) DEFAULT NULL COMMENT '上一设备时间',
  `currentDeviceTime` varchar(50) DEFAULT NULL COMMENT '当前设备时间',
  `hbStationId` varchar(50) DEFAULT NULL COMMENT '2.4G基站',
  PRIMARY KEY (`id`),
  UNIQUE KEY `TAG_INDEX` (`tagId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_tmp_timly` */

/*Table structure for table `tb_tmp_vehicle` */

DROP TABLE IF EXISTS `tb_tmp_vehicle`;

CREATE TABLE `tb_tmp_vehicle` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `ownerName` varchar(11) DEFAULT NULL COMMENT '车主姓名',
  `ownerType` int(3) DEFAULT NULL COMMENT '车主身份',
  `movePhone` varchar(30) DEFAULT NULL COMMENT '电话',
  `ownerNumber` varchar(30) DEFAULT NULL COMMENT '学工业主号',
  `vehicleType` int(3) DEFAULT NULL COMMENT '车辆类型',
  `vehicleBrand` varchar(30) DEFAULT NULL COMMENT '车辆品牌',
  `vehicleColor` varchar(11) DEFAULT NULL COMMENT '颜色',
  `rfidId1` varchar(30) DEFAULT NULL COMMENT '设备1ID',
  `rfidId2` varchar(30) DEFAULT NULL COMMENT '设备2ID',
  `validity` int(5) DEFAULT NULL COMMENT '有效期 单位 月份',
  `registDate` varchar(30) DEFAULT NULL COMMENT '注册时间',
  `photos` varchar(500) DEFAULT NULL COMMENT '照片',
  `status` int(2) DEFAULT NULL COMMENT '0:以安装1:已通过;2:已驳回;3:待审核',
  `reason` varchar(150) DEFAULT NULL COMMENT '审核信息备注',
  `position` varchar(10) DEFAULT NULL COMMENT '车辆位置out:不在园区',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_tmp_vehicle` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
