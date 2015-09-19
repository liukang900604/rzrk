/*
Navicat MySQL Data Transfer

Source Server         : 219.232.254.50
Source Server Version : 50146
Source Host           : 219.232.254.50:3306
Source Database       : rzrk

Target Server Type    : MYSQL
Target Server Version : 50146
File Encoding         : 65001

Date: 2015-06-04 20:50:50
*/

SET FOREIGN_KEY_CHECKS=0;

/*
Navicat MySQL Data Transfer

Source Server         : 219.232.254.50
Source Server Version : 50146
Source Host           : 219.232.254.50:3306
Source Database       : rzrk

Target Server Type    : MYSQL
Target Server Version : 50146
File Encoding         : 65001

Date: 2015-07-08 00:45:07
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `rzrk_admin`
-- ----------------------------
DROP TABLE IF EXISTS `rzrk_admin`;
CREATE TABLE `rzrk_admin` (
  `id` varchar(255) NOT NULL,
  `create_date` datetime DEFAULT NULL,
  `modify_date` datetime DEFAULT NULL,
  `birth_date` varchar(255) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `duplicate_sort_deal` int(11) NOT NULL,
  `email` varchar(255) NOT NULL,
  `is_account_enabled` bit(1) NOT NULL,
  `is_account_expired` bit(1) NOT NULL,
  `is_account_locked` bit(1) NOT NULL,
  `is_credentials_expired` bit(1) NOT NULL,
  `locked_date` datetime DEFAULT NULL,
  `login_date` datetime DEFAULT NULL,
  `login_failure_count` int(11) NOT NULL,
  `login_ip` varchar(255) DEFAULT NULL,
  `mac_address` varchar(255) DEFAULT NULL,
  `man_type` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `office_phone` varchar(255) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `sex` int(11) NOT NULL,
  `sort_no` int(11) NOT NULL,
  `telephone` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `username` varchar(255) NOT NULL,
  `job_level_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  KEY `fk_admin_jobLevel` (`job_level_id`),
  CONSTRAINT `fk_admin_jobLevel` FOREIGN KEY (`job_level_id`) REFERENCES `rzrk_job_level` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=ujis;

-- ----------------------------
-- Records of rzrk_admin
-- ----------------------------
INSERT INTO `rzrk_admin` VALUES ('8a018b5443d16e6a0143d1f19ad40000', '2015-07-07 17:40:28', '2015-07-08 00:35:14', '', '', '0', '', '', '', '', '', null, '2015-07-08 00:35:14', '0', '0:0:0:0:0:0:0:1', null, '0', 'admin', '', '5b765acebfd83a3fa75086abf9811785', '0', '0', '', '', 'admin', null);

-- ----------------------------
-- Table structure for `rzrk_admin_role`
-- ----------------------------
DROP TABLE IF EXISTS `rzrk_admin_role`;
CREATE TABLE `rzrk_admin_role` (
  `admin_set_id` varchar(255) NOT NULL,
  `role_set_id` varchar(255) NOT NULL,
  PRIMARY KEY (`admin_set_id`,`role_set_id`),
  KEY `fk_admin_role_set` (`admin_set_id`),
  KEY `fk_role_admin_set` (`role_set_id`),
  CONSTRAINT `fk_admin_role_set` FOREIGN KEY (`admin_set_id`) REFERENCES `rzrk_admin` (`id`),
  CONSTRAINT `fk_role_admin_set` FOREIGN KEY (`role_set_id`) REFERENCES `rzrk_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of rzrk_admin_role
-- ----------------------------
INSERT INTO `rzrk_admin_role` VALUES ('8a018b5443d16e6a0143d1f19ad40000', 'ROLE00001');

-- ----------------------------
-- Table structure for `rzrk_role`
-- ----------------------------
DROP TABLE IF EXISTS `rzrk_role`;
CREATE TABLE `rzrk_role` (
  `id` varchar(255) NOT NULL,
  `create_date` datetime DEFAULT NULL,
  `modify_date` datetime DEFAULT NULL,
  `authority_list_store` longtext,
  `description` longtext,
  `is_system` bit(1) NOT NULL,
  `name` varchar(255) NOT NULL,
  `contract_category_list` varchar(3000) DEFAULT NULL,
  `project_category_list` varchar(3000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of rzrk_role
-- ----------------------------
INSERT INTO `rzrk_role` VALUES ('ROLE00001', '2013-09-23 20:09:46', '2015-08-04 13:00:39', '[\"ROLE_CONTENT\",\"ROLE_CONTENT_ARTICLE\",\"ROLE_BASE_TYPE\",\"ROLE_BASE_TYPE_ONE\",\"ROLE_BASE_TYPE_TWO\",\"ROLE_BASE_TYPE_PROJECT_LIST\",\"ROLE_TRUST\",\"ROLE_TRUST_PRODUCT\",\"ROLE_TRUST_VALUE\",\"ROLE_TRUST_CONFIRM_RECEIPT\",\"ROLE_BASIS\",\"ROLE_BASE_STOCK\",\"ROLE_CLIENT\",\"ROLE_CLIENT_PRODUCT\",\"ROLE_MAKE\",\"ROLE_MAKE_SUBSCRIBE\",\"ROLE_HR\",\"ROLE_HR_DEPINFO\",\"ROLE_HR_POST\",\"ROLE_HR_POSITION\",\"ROLE_HR_ROLE\",\"ROLE_HR_STAFF\",\"ROLE_LOG\",\"ROLE_LOG_SEARCH\",\"ROLE_LOG_SYSYEM_MESSAGE\",\"ROLE_INTERIOR\",\"ROLE_INTERIOR_COLLECT\",\"ROLE_INTERIOR_ATTENDANCE\",\"ROLE_WORKFLOW\",\"ROLE_WORKFLOW_DEFINITION\",\"ROLE_WORK_CREATE\",\"ROLE_WORKFLOW_MYWORK\",\"ROLE_WORKFLOW_WORKAUDIT\",\"ROLE_WORKFLOW_WORKSEARCH\",\"ROLE_PROJECT_LIST\",\"ROLE_PROJECT_MY_PROJECT\",\"ROLE_WORKFLOW_DEVELOPMENT\",\"ROLE_WORKFLOW_EXTERNAL_REQUIREMENT\",\"ROLE_WORKFLOW_INTERNAL_REQUIREMENT\",\"ROLE_WORKFLOW_REQUIREMENT_DEVELOPMENT\",\"ROLE_WORKFLOW_DEVELOPMENT_DEPLOY\",\"ROLE_WORKFLOW_DEVELOPMENT_URGENT_DEPLOY\",\"ROLE_WORKFLOW_DEVELOPMENT_ONLINE\",\"ROLE_WORKFLOW_REQUIREMENT_MYWORK\",\"ROLE_BASE\"]', '', '', '超级管理员', '[\"40288a924e8a4442014e8b9fd75c0000\",\"40288a924e8a4442014e8ba17fd80001\",\"40288a924e909666014e90af3de50013\",\"40288a924e914bfb014e91b77fe80002\",\"40288a924e94a537014e94b92ebf0000\",\"40288a924e94a537014e94c5b8cd0004\",\"40288a924e94a537014e955c46220093\",\"ff8080824e96aebe014e96bd4691001c\",\"40288a924e9ae344014e9aea976f0000\",\"40288a924eb4b455014eb4c57fe70004\",\"ff8080824ed3d248014ed3d32baf0000\",\"40288a924ed8eac1014ed903fc8b0024\",\"40288a924edcf488014edda669910000\",\"ff8080814e6b6cba014e718613020454\",\"ff8080814e617042014e6229c5af0033\",\"ff8080814e6b6cba014e71c1ab890464\",\"ff8080814e84c613014e853ba01d000d\",\"ff8080814e976100014eaa74c2f81217\",\"ff8080814e976100014eaa76b505121b\",\"ff8080814e976100014eaaef01511412\",\"ff8080824e8b8ee6014e8c093f770013\",\"ff8080824e8b8ee6014e8c0a52a70014\",\"ff8080824e95d70d014e95ebea820000\",\"ff8080824e962340014e9627ad01001b\",\"ff8080824e90f498014e947384c8003a\",\"ff8080824e90f498014e947582a7003b\",\"ff8080824e9a2087014e9ace4cf10073\",\"ff8080824eae689e014eae8b4212001f\",\"ff8080824ed3d7c1014ed3e3ecae0025\",\"ff8080824edcedf6014edd11ab93000f\",\"ff8080824edcedf6014edd2577b60651\",\"ff8080824ef655fe014ef67e87b2000a\",\"ff8080824e94c402014e94cc84960004\",\"ff8080824e94c402014e94de323d0cb0\",\"ff8080824e9b1600014e9b4b8f3d0047\",\"ff8080824ea92bc9014ea960988a0009\",\"ff8080824e962340014e96ac779d0068\",\"ff8080824e96aebe014e96b150bd0000\",\"ff8080824e9a2087014e9ac187f80027\",\"ff8080824e9a2087014e9afd07e502b0\",\"ff8080824e9b1600014e9b22814e0000\",\"ff8080824ebebc4f014ebec7cd280000\",\"ff8080824ebebc4f014ebec90f020002\"]', '[\"40288a924e90e64c014e90e7ae410000\",\"ff8080824e9b1600014e9b53c591013d\",\"ff8080824eb4f636014eb5301fe90052\",\"4028811e4ee344de014ee3f801000000\",\"ff8080824eb3cc8d014eb4f29bda00b7\",\"ff8080824eaa2772014eaa98424a00b3\",\"ff8080824eb01507014eb074b2de001c\",\"ff8080824eb01507014eb077b7a10022\",\"ff8080824e9b1600014e9b3ce7a6001f\"]');



//修改userplan的自增字段
alter table rzrk_user_plan add constraint unique key(user_planuuid);

