/**
 * Project Name: rzrk Web
 * Confidential and Proprietary                                                                
 * Copyright (C) 2013 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.contants;



/**                                                                                                                                    
 * Purpose: category constants                                                                  
 * @version 1.0                                                          
 * @author songkez
 * @since 2015-7-6 
 */
public class CategoryContants {

 //需求分类ID
 public  static final String REQUIREMENT_CATEGORY_ID = "ff8080814e617042014e6229c5af0033".toString(); 
 
 //线上bug分类ID
 public  static final String ONLINE_BUG_REUIREMENT_CATEGORY_ID = "ff8080814e84c613014e8b1f10de0521".toString(); 
 
 //外部需求流程ID
 public  static final String EXTERNAL_REQUIREMENT_FLOW_ID = "ff8080814e66c5d1014e67ad214900ff".toString();
 
 //内部需求流程ID
 public  static final String INTERNAL_REQUIREMENT_FLOW_ID = "ff8080814e66c5d1014e67ba7a440104".toString();

 //立项申请流程ID -- rzrk_workflow
 public  static final String PROJECT_SETUP_ID = "ff8080814e6b6cba014e715f60360445".toString();
 //模型用户增加流程ID -- rzrk_workflow
 public  static final String MODEL_USER_ADD_ID = "40288a924f4e8171014f4ee7d1c40001".toString();
// 移动至setting  public  static final String MODEL_USER_ADD_URL = "http://210.14.136.67:8000/modeltest/addUserByOA.action";
 
 
 //正常部署流程ID -- rzrk_workflow
 public  static final String NORMAL_DEPLOY_ID = "ff8080814e6b6cba014e7162c2c2044d".toString();
 //紧急更新流程ID -- rzrk_workflow
 public  static final String EMERGENCE_DEPLOY_ID = "ff8080814e6b6cba014e71635f8d0451".toString();
 //线上bug提交流程ID -- rzrk_workflow
 public  static final String ONLINE_BUG_SUBMIT_ID = "ff8080814e84c613014e8b228d600525".toString();
 
 //产品要素
 public  static final String PRODUCT_DETAIL_CATEGORY_ID = "ff8080814e976100014eaa76b505121b".toString(); 
 
 //应收款分类id
 public  static final String RECEIVABLES_CATEGORY_ID = "ff8080814e976100014eaaef01511412".toString(); 
 
 //确认收款流程
 public  static final String CONFIRM_RECEIPT_WORKFLOW_ID = "ff8080814e976100014eaaf46c9c141e".toString(); 
 
 public static final String[] REQUIREMENT_APPROVE_ARRAY = {REQUIREMENT_CATEGORY_ID, EXTERNAL_REQUIREMENT_FLOW_ID, INTERNAL_REQUIREMENT_FLOW_ID, PROJECT_SETUP_ID, NORMAL_DEPLOY_ID, EMERGENCE_DEPLOY_ID,ONLINE_BUG_SUBMIT_ID,CONFIRM_RECEIPT_WORKFLOW_ID};
 
 

 /**
  * 二级分类初始化sql
  */
 //INSERT INTO `rzrk_contract_category` VALUES ('ff8080814e617042014e6229c5af0033', '2015-07-06 14:59:41', '2015-07-06 14:59:41', '0', '编号,客户名称,区域,环境,系统版本,客户端版本,产品,券商、账号,需求描述,问题类型', '1', '需求列表', null, 'ff8080814e617042014e6227373a0031', 'ff8080814e5e826a014e614b1225004d', '', '1', '');
 //INSERT INTO `rzrk_contract_category` VALUES ('ff8080814e84c613014e8b1f10de0521', '2015-07-14 13:52:25', '2015-07-16 23:05:20', '0', '编号,客户,缺陷说明,系统版本,产品', '1', '线上Bug管理', null, 'ff8080814e425556014e4256261a0000', 'ff8080814e5e826a014e614b1225004d', '', '1', '', '[{\"name\":\"编号\",\"total\":false,\"type\":\"文本框\",\"built\":false,\"builtsign\":\"\",\"options\":[]},{\"name\":\"客户\",\"total\":false,\"type\":\"文本框\",\"built\":false,\"builtsign\":\"\",\"options\":[]},{\"name\":\"缺陷说明\",\"total\":false,\"type\":\"文本框\",\"built\":false,\"builtsign\":\"\",\"options\":[]},{\"name\":\"系统版本\",\"total\":false,\"type\":\"文本框\",\"built\":false,\"builtsign\":\"\",\"options\":[]},{\"name\":\"产品\",\"total\":false,\"type\":\"\",\"built\":true,\"builtsign\":\"产品\",\"options\":[]}]');
 
 /**
   *  流程类型初始化sql 
   */
//INSERT INTO `rzrk_workflow_type` VALUES ('ff8080814e66c5d1014e67a8785600f9', '2015-07-07 16:36:10', '2015-07-08 08:40:04', '内部需求审批流程', null);
//INSERT INTO `rzrk_workflow_type` VALUES ('ff8080814e66c5d1014e6b1ab8350df1', '2015-07-08 08:39:50', '2015-07-08 08:39:50', '外部需求审批流程', null);
 
 /**
  * 流程表单初始化sql 
  */
// INSERT INTO `rzrk_workflow_form` VALUES ('ff8080814e66c5d1014e67aa375300fd', '2015-07-07 16:38:05', '2015-07-07 16:41:40', '<table style=\"width:100%;\" cellpadding=\"2\" cellspacing=\"0\" border=\"1\" bordercolor=\"#000000\">\r\n	<tbody>\r\n		<tr>\r\n			<td>\r\n				<label>编号</label><input type=\"text\" class=\"contractCategory cfield cid\" field=\"编号\" contract_category_id=\"ff8080814e617042014e6229c5af0033\" placeholder=\"编号(留空则自动生成)\" maxlength=\"160\" /><br />\r\n			</td>\r\n		</tr>\r\n		<tr>\r\n			<td>\r\n				<label>需求描述</label><input type=\"text\" class=\"contractCategory cfield \" field=\"需求描述\" placeholder=\"需求描述\" maxlength=\"160\" /><br />\r\n			</td>\r\n		</tr>\r\n		<tr>\r\n			<td>\r\n				<label>区域</label><input type=\"text\" class=\"contractCategory cfield \" field=\"区域\" placeholder=\"区域\" maxlength=\"160\" /><br />\r\n			</td>\r\n		</tr>\r\n	</tbody>\r\n</table>\r\n<br />\r\n<table style=\"width:100%;\" cellpadding=\"2\" cellspacing=\"0\" border=\"1\" bordercolor=\"#000000\">\r\n	<tbody>\r\n	</tbody>\r\n</table>', '需求表单', null);
 
 /**
  * 流程初始化sql 
  */
//INSERT INTO `rzrk_workflow` VALUES ('ff8080814e66c5d1014e67ad214900ff', '2015-07-07 16:41:16', '2015-07-07 16:45:15', '提交人->经理->区域销售->技术总监->需求库', '外部需求审批', 'ff8080814e66c5d1014e67aa375300fd', 'ff8080814e66c5d1014e67a8785600f9', null, null, null, null, null, null);
//INSERT INTO `rzrk_workflow` VALUES ('ff8080814e66c5d1014e67ba7a440104', '2015-07-07 16:55:51', '2015-07-07 16:55:51', '内部人员提交需求的流程', '内部需求审批', 'ff8080814e66c5d1014e67aa375300fd', 'ff8080814e66c5d1014e67a8785600f9', null, null, null, null, null, null);
//INSERT INTO `rzrk_workflow` VALUES ('ff8080814e6b6cba014e715f60360445', '2015-07-09 13:52:32', '2015-07-09 13:52:32', '发起设立项目的申请', '立项申请流程', 'ff8080814e66c5d1014e67aa375300fd', 'ff8080814e6b6cba014e715df6140442', null, null, null, null, null, null);
//INSERT INTO `rzrk_workflow` VALUES ('ff8080814e6b6cba014e7162c2c2044d', '2015-07-09 13:56:14', '2015-07-09 13:56:14', '正常的部署流程', '正常部署流程', 'ff8080814e66c5d1014e67aa375300fd', 'ff8080814e6b6cba014e715fd2470448', null, null, null, null, null, null);
//INSERT INTO `rzrk_workflow` VALUES ('ff8080814e6b6cba014e71635f8d0451', '2015-07-09 13:56:54', '2015-07-09 13:56:54', '紧急更新流程', '紧急更新流程', 'ff8080814e66c5d1014e67aa375300fd', 'ff8080814e6b6cba014e7160bb9c044b', null, null, null, null, null, null);

}