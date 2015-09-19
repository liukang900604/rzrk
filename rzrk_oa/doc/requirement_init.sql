-- 需求分类
-- rzrk
INSERT INTO `rzrk_root_contract_category` VALUES ('ff8080814e6b6cba014e718613020454', '2015-07-06 14:56:54', '2015-07-06 14:56:54', '', '开发类', '0');

INSERT INTO `rzrk_contract_category` VALUES ('ff8080814e617042014e6229c5af0033', '2015-07-06 14:59:41', '2015-07-06 14:59:41', '0', '编号,客户名称,区域,环境,系统版本,客户端版本,产品,券商、账号,需求描述,问题类型', '1', '需求列表', null, 'ff8080814e6b6cba014e718613020454', 'ff8080814e5e826a014e614b1225004d', '', '1', '','');

INSERT INTO `rzrk_root_contract_category` VALUES ('ff8080814e6b6cba014e71c1ab890464', '2015-07-09 15:39:54', '2015-07-09 15:39:54', '', '部署管理', '0');
INSERT INTO `rzrk_contract_category` VALUES ('ff8080814e84c613014e853ba01d000d', '2015-07-13 10:25:54', '2015-07-13 10:25:54', '0', '编号,用户,申请理由,功能,现有版本', '1', '部署申请表单', null, 'ff8080814e6b6cba014e71c1ab890464', '', '', '1', '', '');
INSERT INTO `rzrk_contract_category` VALUES ('ff8080814e84c613014e8b1f10de0521', '2015-07-14 13:52:25', '2015-07-16 23:05:20', '0', '编号,客户,缺陷说明,系统版本,产品', '1', '线上Bug管理', null, 'ff8080814e425556014e4256261a0000', 'ff8080814e5e826a014e614b1225004d', '', '1', '', '[{\"name\":\"编号\",\"total\":false,\"type\":\"文本框\",\"built\":false,\"builtsign\":\"\",\"options\":[]},{\"name\":\"客户\",\"total\":false,\"type\":\"文本框\",\"built\":false,\"builtsign\":\"\",\"options\":[]},{\"name\":\"缺陷说明\",\"total\":false,\"type\":\"文本框\",\"built\":false,\"builtsign\":\"\",\"options\":[]},{\"name\":\"系统版本\",\"total\":false,\"type\":\"文本框\",\"built\":false,\"builtsign\":\"\",\"options\":[]},{\"name\":\"产品\",\"total\":false,\"type\":\"\",\"built\":true,\"builtsign\":\"产品\",\"options\":[]}]');


-- 基金产品管理
INSERT INTO `rzrk_root_contract_category` VALUES ('ff8080814e976100014eaa74c2f81217', '2015-07-20 15:54:15', '2015-07-20 15:54:15', '', '基金产品管理', '0');
-- 产品要素
INSERT INTO `rzrk_contract_category` VALUES ('ff8080814e976100014eaa76b505121b', '2015-07-20 15:56:23', '2015-07-20 15:56:23', '0', '编号,成立日期,产品名称,产品类型,投资策略,交易系统,风控方式,账号密码,投资经理,交易风控,团队,团队负责人,产品经理,项目助理,建档统计,估值核算,财务核算,代销机构,落地营业部,代销项目负责,资产管理,项目负责人,托 管,项目负责,期货商,封闭期,开放间隔,开放日,认购费用,赎回费用,首发规模（万）,管理费（固定）,固定返还,支付方式,首个支付日,管理费（浮动）,浮动返还,返还标准,资管费,托管费,佣金设置,佣金承诺,佣金返还,单笔5.0元,预警,止损,B级设计,参与费用,B级分配,C级设计,C级分配,增强资金,增强资金人,增强资金打款时间,风险敞口基数,风险敞口,创业板限制,正回购比例,备  注', '1', '产品要素', null, 'ff8080814e976100014eaa74c2f81217', null, '', '1', '', '[{\"name\":\"编号\",\"type\":\"文本框\",\"built\":false,\"builtsign\":\"\",\"options\":[],\"total\":false},{\"name\":\"成立日期\",\"type\":\"文本框\",\"built\":false,\"builtsign\":\"\",\"options\":[],\"total\":false},{\"name\":\"产品名称\",\"type\":\"文本框\",\"built\":false,\"builtsign\":\"\",\"options\":[],\"total\":false},{\"name\":\"产品类型\",\"type\":\"文本框\",\"built\":false,\"builtsign\":\"\",\"options\":[],\"total\":false},{\"name\":\"投资策略\",\"type\":\"文本框\",\"built\":false,\"builtsign\":\"\",\"options\":[],\"total\":false},{\"name\":\"交易系统\",\"type\":\"文本框\",\"built\":false,\"builtsign\":\"\",\"options\":[],\"total\":false},{\"name\":\"风控方式\",\"type\":\"文本框\",\"built\":false,\"builtsign\":\"\",\"options\":[],\"total\":false},{\"name\":\"账号密码\",\"type\":\"文本框\",\"built\":false,\"builtsign\":\"\",\"options\":[],\"total\":false},{\"name\":\"投资经理\",\"type\":\"文本框\",\"built\":false,\"builtsign\":\"\",\"options\":[],\"total\":false},{\"name\":\"交易风控\",\"type\":\"文本框\",\"built\":false,\"builtsign\":\"\",\"options\":[],\"total\":false},{\"name\":\"团队\",\"type\":\"文本框\",\"built\":false,\"builtsign\":\"\",\"options\":[],\"total\":false},{\"name\":\"团队负责人\",\"type\":\"文本框\",\"built\":false,\"builtsign\":\"\",\"options\":[],\"total\":false},{\"name\":\"产品经理\",\"type\":\"文本框\",\"built\":false,\"builtsign\":\"\",\"options\":[],\"total\":false},{\"name\":\"项目助理\",\"type\":\"文本框\",\"built\":false,\"builtsign\":\"\",\"options\":[],\"total\":false},{\"name\":\"建档统计\",\"type\":\"文本框\",\"built\":false,\"builtsign\":\"\",\"options\":[],\"total\":false},{\"name\":\"估值核算\",\"type\":\"文本框\",\"built\":false,\"builtsign\":\"\",\"options\":[],\"total\":false},{\"name\":\"财务核算\",\"type\":\"文本框\",\"built\":false,\"builtsign\":\"\",\"options\":[],\"total\":false},{\"name\":\"代销机构\",\"type\":\"文本框\",\"built\":false,\"builtsign\":\"\",\"options\":[],\"total\":false},{\"name\":\"落地营业部\",\"type\":\"文本框\",\"built\":false,\"builtsign\":\"\",\"options\":[],\"total\":false},{\"name\":\"代销项目负责\",\"type\":\"文本框\",\"built\":false,\"builtsign\":\"\",\"options\":[],\"total\":false},{\"name\":\"资产管理\",\"type\":\"文本框\",\"built\":false,\"builtsign\":\"\",\"options\":[],\"total\":false},{\"name\":\"项目负责人\",\"type\":\"文本框\",\"built\":false,\"builtsign\":\"\",\"options\":[],\"total\":false},{\"name\":\"托 管\",\"type\":\"文本框\",\"built\":false,\"builtsign\":\"\",\"options\":[],\"total\":false},{\"name\":\"项目负责\",\"type\":\"文本框\",\"built\":false,\"builtsign\":\"\",\"options\":[],\"total\":false},{\"name\":\"期货商\",\"type\":\"文本框\",\"built\":false,\"builtsign\":\"\",\"options\":[],\"total\":false},{\"name\":\"封闭期\",\"type\":\"文本框\",\"built\":false,\"builtsign\":\"\",\"options\":[],\"total\":false},{\"name\":\"开放间隔\",\"type\":\"文本框\",\"built\":false,\"builtsign\":\"\",\"options\":[],\"total\":false},{\"name\":\"开放日\",\"type\":\"文本框\",\"built\":false,\"builtsign\":\"\",\"options\":[],\"total\":false},{\"name\":\"认购费用\",\"type\":\"文本框\",\"built\":false,\"builtsign\":\"\",\"options\":[],\"total\":false},{\"name\":\"赎回费用\",\"type\":\"文本框\",\"built\":false,\"builtsign\":\"\",\"options\":[],\"total\":false},{\"name\":\"首发规模（万）\",\"type\":\"文本框\",\"built\":false,\"builtsign\":\"\",\"options\":[],\"total\":false},{\"name\":\"管理费（固定）\",\"type\":\"文本框\",\"built\":false,\"builtsign\":\"\",\"options\":[],\"total\":false},{\"name\":\"固定返还\",\"type\":\"文本框\",\"built\":false,\"builtsign\":\"\",\"options\":[],\"total\":false},{\"name\":\"支付方式\",\"type\":\"文本框\",\"built\":false,\"builtsign\":\"\",\"options\":[],\"total\":false},{\"name\":\"首个支付日\",\"type\":\"文本框\",\"built\":false,\"builtsign\":\"\",\"options\":[],\"total\":false},{\"name\":\"管理费（浮动）\",\"type\":\"文本框\",\"built\":false,\"builtsign\":\"\",\"options\":[],\"total\":false},{\"name\":\"浮动返还\",\"type\":\"文本框\",\"built\":false,\"builtsign\":\"\",\"options\":[],\"total\":false},{\"name\":\"返还标准\",\"type\":\"文本框\",\"built\":false,\"builtsign\":\"\",\"options\":[],\"total\":false},{\"name\":\"资管费\",\"type\":\"文本框\",\"built\":false,\"builtsign\":\"\",\"options\":[],\"total\":false},{\"name\":\"托管费\",\"type\":\"文本框\",\"built\":false,\"builtsign\":\"\",\"options\":[],\"total\":false},{\"name\":\"佣金设置\",\"type\":\"文本框\",\"built\":false,\"builtsign\":\"\",\"options\":[],\"total\":false},{\"name\":\"佣金承诺\",\"type\":\"文本框\",\"built\":false,\"builtsign\":\"\",\"options\":[],\"total\":false},{\"name\":\"佣金返还\",\"type\":\"文本框\",\"built\":false,\"builtsign\":\"\",\"options\":[],\"total\":false},{\"name\":\"单笔5.0元\",\"type\":\"文本框\",\"built\":false,\"builtsign\":\"\",\"options\":[],\"total\":false},{\"name\":\"预警\",\"type\":\"文本框\",\"built\":false,\"builtsign\":\"\",\"options\":[],\"total\":false},{\"name\":\"止损\",\"type\":\"文本框\",\"built\":false,\"builtsign\":\"\",\"options\":[],\"total\":false},{\"name\":\"B级设计\",\"type\":\"文本框\",\"built\":false,\"builtsign\":\"\",\"options\":[],\"total\":false},{\"name\":\"参与费用\",\"type\":\"文本框\",\"built\":false,\"builtsign\":\"\",\"options\":[],\"total\":false},{\"name\":\"B级分配\",\"type\":\"文本框\",\"built\":false,\"builtsign\":\"\",\"options\":[],\"total\":false},{\"name\":\"C级设计\",\"type\":\"文本框\",\"built\":false,\"builtsign\":\"\",\"options\":[],\"total\":false},{\"name\":\"C级分配\",\"type\":\"文本框\",\"built\":false,\"builtsign\":\"\",\"options\":[],\"total\":false},{\"name\":\"增强资金\",\"type\":\"文本框\",\"built\":false,\"builtsign\":\"\",\"options\":[],\"total\":false},{\"name\":\"增强资金人\",\"type\":\"文本框\",\"built\":false,\"builtsign\":\"\",\"options\":[],\"total\":false},{\"name\":\"增强资金打款时间\",\"type\":\"文本框\",\"built\":false,\"builtsign\":\"\",\"options\":[],\"total\":false},{\"name\":\"风险敞口基数\",\"type\":\"文本框\",\"built\":false,\"builtsign\":\"\",\"options\":[],\"total\":false},{\"name\":\"风险敞口\",\"type\":\"文本框\",\"built\":false,\"builtsign\":\"\",\"options\":[],\"total\":false},{\"name\":\"创业板限制\",\"type\":\"文本框\",\"built\":false,\"builtsign\":\"\",\"options\":[],\"total\":false},{\"name\":\"正回购比例\",\"type\":\"文本框\",\"built\":false,\"builtsign\":\"\",\"options\":[],\"total\":false},{\"name\":\"备  注\",\"type\":\"文本框\",\"built\":false,\"builtsign\":\"\",\"options\":[],\"total\":false}]');
-- 应收款表单
INSERT INTO `rzrk_contract_category` VALUES ('ff8080814e976100014eaaef01511412', '2015-07-20 18:07:47', '2015-07-20 18:11:44', '0', '编号,产品名称,收款日期,应收款项,代销机构,资产管理', null, '应收款项', null, 'ff8080814e976100014eaa74c2f81217', null, '', '1', '', '[{\"name\":\"编号\",\"total\":false,\"type\":\"文本框\",\"built\":false,\"builtsign\":\"\",\"options\":[]},{\"name\":\"产品名称\",\"total\":false,\"type\":\"文本框\",\"built\":false,\"builtsign\":\"\",\"options\":[]},{\"name\":\"收款日期\",\"total\":false,\"type\":\"日期框\",\"built\":false,\"builtsign\":\"\",\"options\":[]},{\"name\":\"应收款项\",\"total\":false,\"type\":\"文本框\",\"built\":false,\"builtsign\":\"\",\"options\":[]},{\"name\":\"代销机构\",\"total\":false,\"type\":\"文本框\",\"built\":false,\"builtsign\":\"\",\"options\":[]},{\"name\":\"资产管理\",\"total\":false,\"type\":\"文本框\",\"built\":false,\"builtsign\":\"\",\"options\":[]}]');

 
-- 需求表单
INSERT INTO `rzrk_workflow_form` VALUES ('ff8080814e66c5d1014e67aa375300fd', '2015-07-07 16:38:05', '2015-07-07 16:41:40', '<table style=\"width:100%;\" cellpadding=\"2\" cellspacing=\"0\" border=\"1\" bordercolor=\"#000000\">\r\n	<tbody>\r\n		<tr>\r\n			<td>\r\n				<label>编号</label><input type=\"text\" class=\"contractCategory cfield cid\" field=\"编号\" contract_category_id=\"ff8080814e617042014e6229c5af0033\" placeholder=\"编号(留空则自动生成)\" maxlength=\"160\" /><br />\r\n			</td>\r\n		</tr>\r\n		<tr>\r\n			<td>\r\n				<label>需求描述</label><input type=\"text\" class=\"contractCategory cfield \" field=\"需求描述\" placeholder=\"需求描述\" maxlength=\"160\" /><br />\r\n			</td>\r\n		</tr>\r\n		<tr>\r\n			<td>\r\n				<label>区域</label><input type=\"text\" class=\"contractCategory cfield \" field=\"区域\" placeholder=\"区域\" maxlength=\"160\" /><br />\r\n			</td>\r\n		</tr>\r\n	</tbody>\r\n</table>\r\n<br />\r\n<table style=\"width:100%;\" cellpadding=\"2\" cellspacing=\"0\" border=\"1\" bordercolor=\"#000000\">\r\n	<tbody>\r\n	</tbody>\r\n</table>', '需求表单', null);
INSERT INTO `rzrk_workflow_form` VALUES ('ff8080814e84c613014e853d35d8000f', '2015-07-13 10:27:38', '2015-07-13 10:27:38', '<table style=\"width:100%;\" cellpadding=\"2\" cellspacing=\"0\" border=\"1\" bordercolor=\"#000000\">\r\n	<tbody>\r\n		<tr>\r\n			<td>\r\n				<label>编号</label><br />\r\n			</td>\r\n			<td>\r\n				<input type=\"text\" class=\"contractCategory cfield cid\" field=\"编号\" contract_category_id=\"ff8080814e84c613014e853ba01d000d\" placeholder=\"编号(留空则自动生成)\" maxlength=\"160\" /><br />\r\n			</td>\r\n		</tr>\r\n		<tr>\r\n			<td>\r\n				<label>用户</label><br />\r\n			</td>\r\n			<td>\r\n				<input type=\"text\" class=\"contractCategory cfield \" field=\"用户\" placeholder=\"用户\" maxlength=\"160\" /><br />\r\n			</td>\r\n		</tr>\r\n		<tr>\r\n			<td>\r\n				<label>申请理由</label><br />\r\n			</td>\r\n			<td>\r\n				<input type=\"text\" class=\"contractCategory cfield \" field=\"申请理由\" placeholder=\"申请理由\" maxlength=\"160\" /><br />\r\n			</td>\r\n		</tr>\r\n		<tr>\r\n			<td>\r\n				<label>功能</label><br />\r\n			</td>\r\n			<td>\r\n				<input type=\"text\" class=\"contractCategory cfield \" field=\"功能\" placeholder=\"功能\" maxlength=\"160\" /><br />\r\n			</td>\r\n		</tr>\r\n		<tr>\r\n			<td>\r\n				<label>现有版本</label><br />\r\n			</td>\r\n			<td>\r\n				<input type=\"text\" class=\"contractCategory cfield \" field=\"现有版本\" placeholder=\"现有版本\" maxlength=\"160\" /><br />\r\n			</td>\r\n		</tr>\r\n	</tbody>\r\n</table>\r\n<br />\r\n<br />', '部署申请表单', null);
INSERT INTO `rzrk_workflow_form` VALUES ('ff8080814e84c613014e8b2044070523', '2015-07-14 13:53:44', '2015-07-14 13:53:44', '<table style=\"width:100%;\" cellpadding=\"2\" cellspacing=\"0\" border=\"1\" bordercolor=\"#000000\">\r\n	<tbody>\r\n		<tr>\r\n			<td>\r\n				<label>编号</label><input type=\"text\" class=\"contractCategory cfield cid\" field=\"编号\" contract_category_id=\"ff8080814e84c613014e8b1f10de0521\" placeholder=\"编号(留空则自动生成)\" maxlength=\"160\" /><br />\r\n			</td>\r\n		</tr>\r\n		<tr>\r\n			<td>\r\n				<label>客户</label><input type=\"text\" class=\"contractCategory cfield \" field=\"客户\" placeholder=\"客户\" maxlength=\"160\" /><br />\r\n			</td>\r\n		</tr>\r\n		<tr>\r\n			<td>\r\n				<label>缺陷说明</label><input type=\"text\" class=\"contractCategory cfield \" field=\"缺陷说明\" placeholder=\"缺陷说明\" maxlength=\"160\" /><br />\r\n			</td>\r\n		</tr>\r\n		<tr>\r\n			<td>\r\n				<label>系统版本</label><input type=\"text\" class=\"contractCategory cfield \" field=\"系统版本\" placeholder=\"系统版本\" maxlength=\"160\" /><br />\r\n			</td>\r\n		</tr>\r\n		<tr>\r\n			<td>\r\n				<label>产品</label><input type=\"text\" class=\"contractCategory cfield \" field=\"产品\" placeholder=\"产品\" maxlength=\"160\" /><br />\r\n			</td>\r\n		</tr>\r\n	</tbody>\r\n</table>\r\n<br />', '线上Bug提交', null);


-- 外部需求流程
INSERT INTO `rzrk_workflow_type` VALUES ('ff8080814e6b6cba014e718613020454', '2015-07-08 08:39:50', '2015-07-08 08:39:50', '开发类', null);
INSERT INTO `rzrk_workflow_type` VALUES ('ff8080814e4f5bd4014e4f5caa150000', '2015-07-02 23:22:29', '2015-07-02 23:22:29', '行政类', null);


INSERT INTO `rzrk_workflow` VALUES ('ff8080814e66c5d1014e67ad214900ff', '2015-07-07 16:41:16', '2015-07-09 14:35:25', '提交人->经理->区域销售->技术总监->需求库', '外部需求审批', 'ff8080814e66c5d1014e67aa375300fd', 'ff8080814e6b6cba014e718613020454', null, null, null, null, null, null);
INSERT INTO `rzrk_workflow` VALUES ('ff8080814e66c5d1014e67ba7a440104', '2015-07-07 16:55:51', '2015-07-09 14:35:19', '内部人员提交需求的流程', '内部需求审批', 'ff8080814e66c5d1014e67aa375300fd', 'ff8080814e6b6cba014e718613020454', null, null, null, null, null, null);
INSERT INTO `rzrk_workflow` VALUES ('ff8080814e6b6cba014e715f60360445', '2015-07-09 13:52:32', '2015-07-09 14:35:14', '发起设立项目的申请', '立项申请流程', 'ff8080814e66c5d1014e67aa375300fd', 'ff8080814e6b6cba014e718613020454', null, null, null, null, null, null);
INSERT INTO `rzrk_workflow` VALUES ('ff8080814e6b6cba014e7162c2c2044d', '2015-07-09 13:56:14', '2015-07-09 14:35:08', '正常的部署流程', '正常部署流程', 'ff8080814e84c613014e853d35d8000f', 'ff8080814e6b6cba014e718613020454', null, null, null, null, null, null);
INSERT INTO `rzrk_workflow` VALUES ('ff8080814e6b6cba014e71635f8d0451', '2015-07-09 13:56:54', '2015-07-09 14:35:01', '紧急更新流程', '紧急更新流程', 'ff8080814e84c613014e853d35d8000f', 'ff8080814e6b6cba014e718613020454', null, null, null, null, null, null);
INSERT INTO `rzrk_workflow` VALUES ('ff8080814e84c613014e8b228d600525', '2015-07-14 13:56:14', '2015-07-14 13:56:14', '提交线上系统存在的bug', '线上bug提交', 'ff8080814e84c613014e8b2044070523', 'ff8080814e6b6cba014e718613020454', null, null, null, null, null, null);

-- 收款流程
INSERT INTO `rzrk_workflow` VALUES ('ff8080814e976100014eaaf46c9c141e', '2015-07-20 18:13:42', '2015-07-20 18:13:42', '收款的一个简单流程', '基金管理收款流程', 'ff8080814e66c5d1014e67aa375300fd', 'ff8080814e4f5bd4014e4f5caa150000', null, null, null, null, null, null);

-- 二级分类
INSERT INTO `rzrk`.`rzrk_contract_category` (`id`, `create_date`, `modify_date`, `approval_needed`, `fields`, `isView`, `name`, `approval_id`, `root_contract_category_id`, `workFlowId`, `totals`, `isSuperiorView`, `recyle`, `definition`, `control_type`, `control_field`, `form_template`, `content_provider`, `isSubDepView`) VALUES ('40288a924f4a3567014f4a7082c90004', '2015-08-20 17:28:51', '2015-08-24 20:36:29', '0', '编号,用户名,密码,确认密码,账号类型,最大同时登录数,过期时间,用户平台,间隔,是否激活,是否上传,模型类型,类型,权限', NULL, '模型用户', NULL, 'ff8080814e6b6cba014e71c1ab890464', NULL, '', NULL, '\0', '[{\"name\":\"编号\",\"required\":false,\"total\":false,\"type\":\"文本框\",\"built\":false,\"builtsign\":\"\",\"builtdata\":{},\"options\":[],\"expression\":\"\",\"departmentIds\":[],\"superiorView\":false,\"contentProvider\":false},{\"name\":\"用户名\",\"required\":true,\"total\":false,\"type\":\"文本框\",\"built\":true,\"builtsign\":\"用户名\",\"builtdata\":{},\"options\":[],\"expression\":\"\",\"departmentIds\":[],\"superiorView\":false,\"contentProvider\":false},{\"name\":\"密码\",\"required\":true,\"total\":false,\"type\":\"文本框\",\"built\":false,\"builtsign\":\"\",\"builtdata\":{},\"options\":[],\"expression\":\"\",\"departmentIds\":[],\"superiorView\":false,\"contentProvider\":false},{\"name\":\"确认密码\",\"required\":true,\"total\":false,\"type\":\"文本框\",\"built\":false,\"builtsign\":\"\",\"builtdata\":{},\"options\":[],\"expression\":\"\",\"departmentIds\":[],\"superiorView\":false,\"contentProvider\":false},{\"name\":\"账号类型\",\"required\":false,\"total\":false,\"type\":\"下拉框\",\"built\":false,\"builtsign\":\"\",\"builtdata\":{},\"options\":[\"个人\",\"公司\"],\"expression\":\"\",\"departmentIds\":[],\"superiorView\":false,\"contentProvider\":false},{\"name\":\"最大同时登录数\",\"required\":true,\"total\":false,\"type\":\"文本框\",\"built\":false,\"builtsign\":\"\",\"builtdata\":{},\"options\":[],\"expression\":\"\",\"departmentIds\":[],\"superiorView\":false,\"contentProvider\":false},{\"name\":\"过期时间\",\"required\":true,\"total\":false,\"type\":\"日期时间框\",\"built\":false,\"builtsign\":\"\",\"builtdata\":{},\"options\":[],\"expression\":\"\",\"departmentIds\":[],\"superiorView\":false,\"contentProvider\":false},{\"name\":\"用户平台\",\"required\":false,\"total\":false,\"type\":\"下拉框\",\"built\":false,\"builtsign\":\"\",\"builtdata\":{},\"options\":[\"thinktrader\"],\"expression\":\"\",\"departmentIds\":[],\"superiorView\":false,\"contentProvider\":false},{\"name\":\"间隔\",\"required\":true,\"total\":false,\"type\":\"文本框\",\"built\":false,\"builtsign\":\"\",\"builtdata\":{},\"options\":[],\"expression\":\"\",\"departmentIds\":[],\"superiorView\":false,\"contentProvider\":false},{\"name\":\"是否激活\",\"required\":false,\"total\":false,\"type\":\"下拉框\",\"built\":false,\"builtsign\":\"\",\"builtdata\":{},\"options\":[\"是\",\"否\"],\"expression\":\"\",\"departmentIds\":[],\"superiorView\":false,\"contentProvider\":false},{\"name\":\"是否上传\",\"required\":false,\"total\":false,\"type\":\"下拉框\",\"built\":false,\"builtsign\":\"\",\"builtdata\":{},\"options\":[\"是\",\"否\"],\"expression\":\"\",\"departmentIds\":[],\"superiorView\":false,\"contentProvider\":false},{\"name\":\"模型类型\",\"required\":false,\"total\":false,\"type\":\"下拉框\",\"built\":false,\"builtsign\":\"\",\"builtdata\":{},\"options\":[\"0\"],\"expression\":\"\",\"departmentIds\":[],\"superiorView\":false,\"contentProvider\":false},{\"name\":\"类型\",\"required\":false,\"total\":false,\"type\":\"下拉框\",\"built\":false,\"builtsign\":\"\",\"builtdata\":{},\"options\":[\"1\",\"2\",\"3\"],\"expression\":\"\",\"departmentIds\":[],\"superiorView\":false,\"contentProvider\":false},{\"name\":\"权限\",\"required\":true,\"total\":false,\"type\":\"选择树\",\"built\":false,\"builtsign\":\"\",\"builtdata\":{},\"options\":[],\"expression\":\"[{\\n\\t\\\"id\\\":\\\"组合模型权限\\\",\\n\\t\\\"text\\\":\\\"组合模型权限\\\",\\n\\t\\\"children\\\":[{\\n\\t\\t\\\"id\\\":\\\"组合模型\\\",\\n\\t\\t\\\"text\\\":\\\"组合模型\\\"\\n\\t}]\\n},\\n{\\n\\t\\\"id\\\":\\\"扩展数据权限\\\",\\n\\t\\\"text\\\":\\\"扩展数据权限\\\",\\n\\t\\\"children\\\":[{\\n\\t\\t\\\"id\\\":\\\"扩展数据\\\",\\n\\t\\t\\\"text\\\":\\\"扩展数据\\\"\\n\\t}]\\n},\\n{\\n\\t\\\"id\\\":\\\"自由交易权限\\\",\\n\\t\\\"text\\\":\\\"自由交易权限\\\",\\n\\t\\\"children\\\":[{\\n\\t\\t\\\"id\\\":\\\"模拟账户\\\",\\n\\t\\t\\\"text\\\":\\\"模拟账户\\\"\\n\\t},{\\n\\t\\t\\\"id\\\":\\\"交易\\\",\\n\\t\\t\\\"text\\\":\\\"交易\\\"\\n\\t}]\\n},\\n{\\n\\t\\\"id\\\":\\\"补数据权限\\\",\\n\\t\\\"text\\\":\\\"补数据权限\\\",\\n\\t\\\"children\\\":[{\\n\\t\\t\\\"id\\\":\\\"补分时数据权限\\\",\\n\\t\\t\\\"text\\\":\\\"补分时数据权限\\\"\\n\\t},{\\n\\t\\t\\\"id\\\":\\\"补财务数据权限\\\",\\n\\t\\t\\\"text\\\":\\\"补财务数据权限\\\"\\n\\t}]\\n}]\",\"departmentIds\":[],\"superiorView\":false,\"contentProvider\":false}]', NULL, '__create_admin', '', '\0', NULL);
-- 模型用户流程表单
INSERT INTO `rzrk`.`rzrk_workflow_form` (`id`, `create_date`, `modify_date`, `formContent`, `formName`, `isDelete`, `contract_category_id`, `contract_name`) VALUES ('40288a924f4a3567014f4ac28bf20008', '2015-08-20 18:58:27', '2015-08-24 20:38:31', '<table style=\"width:100%;\" cellpadding=\"2\" cellspacing=\"0\" border=\"1\" bordercolor=\"#000000\">\r\n	<tbody>\r\n		<tr>\r\n			<td>\r\n				<label style=\"white-space:nowrap;\">编号</label><br />\r\n			</td>\r\n			<td>\r\n				<input type=\"text\" class=\"contractCategory cfield cid definition\" field=\"编号\" contract_category_id=\"40288a924f4a3567014f4a7082c90004\" placeholder=\"编号(留空则自动生成)\" maxlength=\"160\" /><br />\r\n			</td>\r\n		</tr>\r\n		<tr>\r\n			<td>\r\n				<label style=\"white-space:nowrap;\">用户名</label><br />\r\n			</td>\r\n			<td>\r\n				<input type=\"text\" class=\"contractCategory cfield definition required \" builtsign=\"用户名\" field=\"用户名\" placeholder=\"用户名\" maxlength=\"160\" /><span class=\"required_sign\" style=\"color:red;\">*</span> <br />\r\n			</td>\r\n		</tr>\r\n		<tr>\r\n			<td>\r\n				<label style=\"white-space:nowrap;\">密码</label><br />\r\n			</td>\r\n			<td>\r\n				<input type=\"text\" class=\"contractCategory cfield definition required \" field=\"密码\" placeholder=\"密码\" maxlength=\"160\" /><span class=\"required_sign\" style=\"color:red;\">*</span> <br />\r\n			</td>\r\n		</tr>\r\n		<tr>\r\n			<td>\r\n				<label style=\"white-space:nowrap;\">确认密码</label><br />\r\n			</td>\r\n			<td>\r\n				<input type=\"text\" class=\"contractCategory cfield definition required \" field=\"确认密码\" placeholder=\"确认密码\" maxlength=\"160\" /><span class=\"required_sign\" style=\"color:red;\">*</span> <br />\r\n			</td>\r\n		</tr>\r\n		<tr>\r\n			<td>\r\n				<label style=\"white-space:nowrap;\">账号类型</label><br />\r\n			</td>\r\n			<td>\r\n				<span class=\"select contractCategory cfield definition  \" field=\"账号类型\"><select><option value=\"个人\">个人</option><option value=\"公司\">公司</option></select></span> <br />\r\n			</td>\r\n		</tr>\r\n		<tr>\r\n			<td>\r\n				<label style=\"white-space:nowrap;\">最大同时登录数</label><br />\r\n			</td>\r\n			<td>\r\n				<input type=\"text\" class=\"contractCategory cfield definition required \" field=\"最大同时登录数\" placeholder=\"最大同时登录数\" maxlength=\"160\" /><span class=\"required_sign\" style=\"color:red;\">*</span> <br />\r\n			</td>\r\n		</tr>\r\n		<tr>\r\n			<td>\r\n				<label style=\"white-space:nowrap;\">过期时间</label><br />\r\n			</td>\r\n			<td>\r\n				<input type=\"text\" class=\"contractCategory cfield cdatetime definition required \" field=\"过期时间\" placeholder=\"过期时间\" maxlength=\"160\" /><span class=\"required_sign\" style=\"color:red;\">*</span> <br />\r\n			</td>\r\n		</tr>\r\n		<tr>\r\n			<td>\r\n				<label style=\"white-space:nowrap;\">用户平台</label><br />\r\n			</td>\r\n			<td>\r\n				<span class=\"select contractCategory cfield definition  \" field=\"用户平台\"><select><option value=\"thinktrader\">thinktrader</option></select></span> <br />\r\n			</td>\r\n		</tr>\r\n		<tr>\r\n			<td>\r\n				<label style=\"white-space:nowrap;\">间隔</label><br />\r\n			</td>\r\n			<td>\r\n				<input type=\"text\" class=\"contractCategory cfield definition required \" field=\"间隔\" placeholder=\"间隔\" maxlength=\"160\" /><span class=\"required_sign\" style=\"color:red;\">*</span> <br />\r\n			</td>\r\n		</tr>\r\n		<tr>\r\n			<td>\r\n				<label style=\"white-space:nowrap;\">是否激活</label><br />\r\n			</td>\r\n			<td>\r\n				<span class=\"select contractCategory cfield definition\" field=\"是否激活\"><select><option value=\"是\">是</option><option value=\"否\">否</option></select></span><br />\r\n			</td>\r\n		</tr>\r\n		<tr>\r\n			<td>\r\n				<label style=\"white-space:nowrap;\">是否上传</label><br />\r\n			</td>\r\n			<td>\r\n				<span class=\"select contractCategory cfield definition\" field=\"是否上传\"><select><option value=\"是\">是</option><option value=\"否\">否</option></select></span><br />\r\n			</td>\r\n		</tr>\r\n		<tr>\r\n			<td>\r\n				<label style=\"white-space:nowrap;\">模型类型</label><br />\r\n			</td>\r\n			<td>\r\n				<span class=\"select contractCategory cfield definition\" field=\"模型类型\"><select><option value=\"0\">0</option></select></span><br />\r\n			</td>\r\n		</tr>\r\n		<tr>\r\n			<td>\r\n				<label style=\"white-space:nowrap;\">类型</label><br />\r\n			</td>\r\n			<td>\r\n				<span class=\"select contractCategory cfield definition\" field=\"类型\"><select><option value=\"1\">1</option><option value=\"2\">2</option><option value=\"3\">3</option></select></span><br />\r\n			</td>\r\n		</tr>\r\n		<tr>\r\n			<td>\r\n				<label style=\"white-space:nowrap;\">权限</label><br />\r\n			</td>\r\n			<td>\r\n				<span class=\"selectTree contractCategory cfield definition required \" tree=\"%5B%7B%0A%09%22id%22%3A%22%E7%BB%84%E5%90%88%E6%A8%A1%E5%9E%8B%E6%9D%83%E9%99%90%22%2C%0A%09%22text%22%3A%22%E7%BB%84%E5%90%88%E6%A8%A1%E5%9E%8B%E6%9D%83%E9%99%90%22%2C%0A%09%22children%22%3A%5B%7B%0A%09%09%22id%22%3A%22%E7%BB%84%E5%90%88%E6%A8%A1%E5%9E%8B%22%2C%0A%09%09%22text%22%3A%22%E7%BB%84%E5%90%88%E6%A8%A1%E5%9E%8B%22%0A%09%7D%5D%0A%7D%2C%0A%7B%0A%09%22id%22%3A%22%E6%89%A9%E5%B1%95%E6%95%B0%E6%8D%AE%E6%9D%83%E9%99%90%22%2C%0A%09%22text%22%3A%22%E6%89%A9%E5%B1%95%E6%95%B0%E6%8D%AE%E6%9D%83%E9%99%90%22%2C%0A%09%22children%22%3A%5B%7B%0A%09%09%22id%22%3A%22%E6%89%A9%E5%B1%95%E6%95%B0%E6%8D%AE%22%2C%0A%09%09%22text%22%3A%22%E6%89%A9%E5%B1%95%E6%95%B0%E6%8D%AE%22%0A%09%7D%5D%0A%7D%2C%0A%7B%0A%09%22id%22%3A%22%E8%87%AA%E7%94%B1%E4%BA%A4%E6%98%93%E6%9D%83%E9%99%90%22%2C%0A%09%22text%22%3A%22%E8%87%AA%E7%94%B1%E4%BA%A4%E6%98%93%E6%9D%83%E9%99%90%22%2C%0A%09%22children%22%3A%5B%7B%0A%09%09%22id%22%3A%22%E6%A8%A1%E6%8B%9F%E8%B4%A6%E6%88%B7%22%2C%0A%09%09%22text%22%3A%22%E6%A8%A1%E6%8B%9F%E8%B4%A6%E6%88%B7%22%0A%09%7D%2C%7B%0A%09%09%22id%22%3A%22%E4%BA%A4%E6%98%93%22%2C%0A%09%09%22text%22%3A%22%E4%BA%A4%E6%98%93%22%0A%09%7D%5D%0A%7D%2C%0A%7B%0A%09%22id%22%3A%22%E8%A1%A5%E6%95%B0%E6%8D%AE%E6%9D%83%E9%99%90%22%2C%0A%09%22text%22%3A%22%E8%A1%A5%E6%95%B0%E6%8D%AE%E6%9D%83%E9%99%90%22%2C%0A%09%22children%22%3A%5B%7B%0A%09%09%22id%22%3A%22%E8%A1%A5%E5%88%86%E6%97%B6%E6%95%B0%E6%8D%AE%E6%9D%83%E9%99%90%22%2C%0A%09%09%22text%22%3A%22%E8%A1%A5%E5%88%86%E6%97%B6%E6%95%B0%E6%8D%AE%E6%9D%83%E9%99%90%22%0A%09%7D%2C%7B%0A%09%09%22id%22%3A%22%E8%A1%A5%E8%B4%A2%E5%8A%A1%E6%95%B0%E6%8D%AE%E6%9D%83%E9%99%90%22%2C%0A%09%09%22text%22%3A%22%E8%A1%A5%E8%B4%A2%E5%8A%A1%E6%95%B0%E6%8D%AE%E6%9D%83%E9%99%90%22%0A%09%7D%5D%0A%7D%5D\" field=\"权限\"><input type=\"text\" /></span><span class=\"required_sign\" style=\"color:red;\">*</span> <br />\r\n			</td>\r\n		</tr>\r\n	</tbody>\r\n</table>\r\n<br />', '模型用户表单', NULL, '40288a924f4a3567014f4a7082c90004', '模型用户');
-- 模型用户流程定义
INSERT INTO `rzrk`.`rzrk_workflow` (`id`, `create_date`, `modify_date`, `flowContent`, `flowName`, `formId`, `flowTypeId`, `operateFormId`, `isDelete`, `isEmail`, `isMsg`, `isDeploy`, `jsonData`, `version`, `currentFlowId`, `isHistory`) VALUES ('40288a924f4e8171014f4ee7d1c40001', '2015-08-21 14:17:39', '2015-08-21 14:17:39', '模型用户表单', '模型用户流程', '40288a924f4a3567014f4ac28bf20008', 'ff8080814e6b6cba014e718613020454', NULL, NULL, NULL, NULL, NULL, NULL, '0', NULL, '1');

--基础资料 目标版本、产品版本的添加
INSERT INTO `rzrk_root_contract_category` VALUES ('40288a904fa05e03014fa065f7ef0000', '2015-09-06 10:04:41', '2015-09-06 10:04:41', '用于引用', '基础资料', '0');
INSERT INTO `rzrk_contract_category` VALUES ('40288a904fa05e03014fa068551e0001', '2015-09-06 10:07:16', '2015-09-06 10:07:24', '0', '编号,产品版本', '1', '产品版本', null, '40288a904fa05e03014fa065f7ef0000', null, '', '1', '', '[{\"name\":\"编号\",\"required\":false,\"total\":false,\"type\":\"文本框\",\"built\":false,\"builtsign\":\"\",\"builtdata\":{},\"options\":[],\"expression\":\"\",\"departmentIds\":[],\"adminIds\":[],\"superiorView\":false,\"contentProvider\":false},{\"name\":\"产品版本\",\"required\":false,\"total\":false,\"type\":\"文本框\",\"built\":false,\"builtsign\":\"\",\"builtdata\":{},\"options\":[],\"expression\":\"\",\"departmentIds\":[],\"adminIds\":[],\"superiorView\":false,\"contentProvider\":false}]', null, '__create_admin', '', '', '1', '0', '', null);
INSERT INTO `rzrk_contract_category` VALUES ('40288a904fa05e03014fa068e1790005', '2015-09-06 10:07:52', '2015-09-06 10:07:52', '0', '编号,目标版本', '1', '目标版本', null, '40288a904fa05e03014fa065f7ef0000', null, '', '1', '', '[{\"name\":\"编号\",\"required\":false,\"type\":\"文本框\",\"built\":false,\"builtsign\":\"\",\"builtdata\":{},\"options\":[],\"total\":false,\"expression\":\"\",\"departmentIds\":[],\"adminIds\":[],\"superiorView\":false,\"contentProvider\":false},{\"name\":\"目标版本\",\"required\":false,\"type\":\"文本框\",\"built\":false,\"builtsign\":\"\",\"builtdata\":{},\"options\":[],\"total\":false,\"expression\":\"\",\"departmentIds\":[],\"adminIds\":[],\"superiorView\":false,\"contentProvider\":false}]', null, '__create_admin', '', '', '1', '0', '', null);



