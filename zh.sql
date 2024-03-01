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

/*Table structure for table `tb_sys_account` */

DROP TABLE IF EXISTS `tb_sys_account`;

CREATE TABLE `tb_sys_account` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `accountName` varchar(50) DEFAULT NULL COMMENT '系统账号',
  `password` varchar(50) DEFAULT NULL COMMENT '密码',
  `unitIndex` int(11) DEFAULT NULL COMMENT '单位索引',
  `roleIndex` int(11) DEFAULT NULL COMMENT '角色索引',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `tb_sys_account` */

insert  into `tb_sys_account`(`id`,`accountName`,`password`,`unitIndex`,`roleIndex`) values (1,'zh','33',1,NULL);

/*Table structure for table `tb_sys_menu` */

DROP TABLE IF EXISTS `tb_sys_menu`;

CREATE TABLE `tb_sys_menu` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `menuName` varchar(50) DEFAULT NULL COMMENT '菜单名称',
  `menuPosition` int(11) DEFAULT NULL COMMENT '菜单位置[相对于同一级菜单位置]',
  `parentIndex` int(11) DEFAULT '-1' COMMENT '上一级菜单索引',
  `sourceUrl` varchar(150) DEFAULT NULL COMMENT '菜单资源连接',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

/*Data for the table `tb_sys_menu` */

insert  into `tb_sys_menu`(`id`,`menuName`,`menuPosition`,`parentIndex`,`sourceUrl`) values (1,'主页',0,-1,'/1'),(2,'数据列表',0,1,'/1/1'),(3,'数据看板',1,1,'/1/2'),(4,'车辆管理',1,-1,'/2'),(5,'设备管理',2,-1,'/3'),(6,'标签管理',0,5,'/3/1'),(7,'基站管理',1,5,'/3/2'),(8,'系统日志',3,-1,'/4'),(9,'报警数据',0,8,'/4/1'),(10,'违停数据',1,8,'/4/2'),(11,'系统配置',4,-1,'/5'),(12,'用户管理',0,11,'/5/1'),(13,'单位管理',1,11,'/5/2'),(14,'组织管理',2,11,'/5/3'),(15,'角色管理',3,11,'/5/4'),(16,'区域管理',4,11,'/5/5');

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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `tb_sys_role` */

insert  into `tb_sys_role`(`id`,`roleName`,`unitIndex`,`authority`) values (1,'管理员',1,NULL),(2,'普通用户',1,NULL);

/*Table structure for table `tb_sys_unit` */

DROP TABLE IF EXISTS `tb_sys_unit`;

CREATE TABLE `tb_sys_unit` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `unitName` varchar(150) DEFAULT NULL COMMENT '单位名称',
  `unitCode` varchar(150) DEFAULT NULL COMMENT '单位编码',
  `parentUnit` int(5) DEFAULT NULL COMMENT '上一级单位[受管理于单位索引]',
  `unitType` int(2) DEFAULT NULL COMMENT '0：开发商 1:学校；2：小区',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `tb_sys_unit` */

insert  into `tb_sys_unit`(`id`,`unitName`,`unitCode`,`parentUnit`,`unitType`) values (1,'正晗电子','ZHDZ_D_01',NULL,0);

/*Table structure for table `tb_tmp_area` */

DROP TABLE IF EXISTS `tb_tmp_area`;

CREATE TABLE `tb_tmp_area` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_tmp_device` */

/*Table structure for table `tb_tmp_tag` */

DROP TABLE IF EXISTS `tb_tmp_tag`;

CREATE TABLE `tb_tmp_tag` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_tmp_tag` */

/*Table structure for table `tb_tmp_timly` */

DROP TABLE IF EXISTS `tb_tmp_timly`;

CREATE TABLE `tb_tmp_timly` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_tmp_timly` */

/*Table structure for table `tb_tmp_vehicle` */

DROP TABLE IF EXISTS `tb_tmp_vehicle`;

CREATE TABLE `tb_tmp_vehicle` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_tmp_vehicle` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
