<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>添加/编辑角色 - Powered By UNICORN</title>
<meta name="Author" content="UNICORN Team" />
<meta name="Copyright" content="UNICORN" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link href="${base}/template/admin/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/admin/css/admin.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/rzrk/js/alljs.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.validate.methods.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/base.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/admin.js"></script>
<script type="text/javascript">
$().ready(function() {
	var $validateErrorContainer = $("#validateErrorContainer");
	var $validateErrorLabelContainer = $("#validateErrorContainer ul");
	var $validateForm = $("#validateForm");
	
	var $allChecked = $("#validateForm .allChecked");
	
	$allChecked.click( function() {
		var $this = $(this);
		var $thisCheckbox = $this.parent().parent().find(":checkbox");
		if ($thisCheckbox.filter(":checked").size() > 0) {
			$thisCheckbox.attr("checked", false);
		} else {
			$thisCheckbox.attr("checked", true);
		}
		return false;
	});
	
	jQuery.validator.addMethod("username",function(value, element){return this.optional(element) || /^[\u0391-\uFFE5\w]+$/.test(value);},"只允许包含中文、英文、数字和下划线");
	
	// 表单验证
	$validateForm.validate({
		errorContainer: $validateErrorContainer,
		errorLabelContainer: $validateErrorLabelContainer,
		wrapper: "li",
		errorClass: "validateError",
		ignoreTitle: true,
		rules: {
			"role.name": {
				required: true,
				username: true,
				rangelength: [2, 8]
			},
			"role.description": {
				maxlength: 400
			}
		},
		messages: {
			"role.name":{
				required: "请填写角色名称",
				rangelength: "长度必须在 {0} - {1} 个字符之间"
			},
			"role.description": {
				maxlength: "长度必须小于 400 个字符"
			}
		},
		submitHandler: function(form) {
			$(form).find(":submit").attr("disabled", true);
			form.submit();
		}
	});
	
//	$.validator.addMethod("roleAuthorityListRequired", $.validator.methods.required, "请选择管理权限");
	
	$.validator.addClassRules("roleAuthorityList", {
		roleAuthorityListRequired: true
	});
	
	$(".all").click(function(){
		$(":checkbox").prop("checked", true);
	});
	
	$(".revert").click(function(){
		$(":checkbox").each(function(i, checkbox){
			$(checkbox).prop("checked", !$(checkbox).prop("checked"));
		});
	});
	
	var cbs = $("tr").find(":checkbox:first");
	cbs.click(function(){
		var _this = $(this);
		var isChecked = _this.prop("checked");
		var siblings = _this.parent().siblings().find(":checkbox");
		siblings.prop("checked", isChecked);
	});

	var cbss = $("tr").find(":checkbox:not(:first)");
	cbss.click(function(){
		var _this = $(this);
		var cb = _this.parents("td").find(":checkbox:first");
		var cbs = _this.parents("td").find(":checkbox:not(:first)");
		var isChecked = false;
		cbs.each(function(i, c){
			var checked = $(c).prop("checked");
			if (checked){
				isChecked = checked;
				return false;
			}
		});
		cb.prop("checked", isChecked);
	});
})
</script>
</head>
<body class="input role">
	<div class="bar">
		<#if isAddAction>添加角色<#else>编辑角色</#if>
	</div>
	<div id="validateErrorContainer" class="validateErrorContainer">
		<div class="validateErrorTitle">以下信息填写有误,请重新填写</div>
		<ul></ul>
	</div>
	<div class="body">
		<form id="validateForm" action="<#if isAddAction>role!save.action<#else>role!update.action</#if>" method="post">
			<input type="hidden" name="id" value="${id}" />
			<table class="inputTable">
				<tr>
					<th>
						角色名称: 
					</th>
					<td>
						<input type="text" name="role.name" class="formText" value="${(role.name)!}" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						描述: 
					</th>
					<td>
						<textarea name="role.description" class="formTextarea">${(role.description)!}</textarea>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						&nbsp;
					</td>
				</tr>
				<tr class="authorityList">
					<th></th>
					<td>
						<input type="button" value="全选" class="all formButton"/>
						&nbsp;&nbsp;
						<input type="button" value="反选" class="revert formButton"/>
					</td>
				</tr>
				<#if superAuthorities.contains("ROLE_CONTENT")!>
					<tr class="authorityList">
						<th>
							<a href="#" class="allChecked" title="点击全选此类权限">内容管理: </a>
						</th>
						<td>
						    <label>
								<input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_CONTENT"<#if (isAddAction || role.authorityList.contains("ROLE_CONTENT"))!> checked</#if> />内容管理
							</label>
							<label>
								<input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_CONTENT_ARTICLE"<#if (isAddAction || role.authorityList.contains("ROLE_CONTENT_ARTICLE"))!> checked</#if> />文章管理
							</label>
						</td>
					</tr>
				</#if>
				<#if superAuthorities.contains("ROLE_BASE_TYPE")!>
				<tr class="authorityList">
					<th>
						<a href="#" class="allChecked" title="点击全选此类权限">基础分类数据: </a>
					</th>
					<td>
					
					    <label>
							<input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_BASE_TYPE"<#if (isAddAction || role.authorityList.contains("ROLE_BASE_TYPE"))!> checked</#if> />基础分类数据
						</label>
					    <label>
							<input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_BASE_TYPE_ONE"<#if (isAddAction || role.authorityList.contains("ROLE_BASE_TYPE_ONE"))!> checked</#if> />一级分类
						</label>
						<label>
							<input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_BASE_TYPE_TWO"<#if (isAddAction || role.authorityList.contains("ROLE_BASE_TYPE_TWO"))!> checked</#if> />二级分类
						</label>
						<label>
							<input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_BASE_TYPE_PROJECT_LIST"<#if (isAddAction || role.authorityList.contains("ROLE_BASE_TYPE_PROJECT_LIST"))!> checked</#if> />项目列表
						</label>
					</td>
				</tr>
				</#if>
				<#if superAuthorities.contains("ROLE_CONTENT")!>
				<tr class="authorityList">
					<th>
						<a href="#" class="allChecked" title="点击全选此类权限">信托产品: </a>
					</th>
					<td>
					    <label>
							<input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_TRUST"<#if (isAddAction || role.authorityList.contains("ROLE_TRUST"))!> checked</#if> />信托产品
						</label>
						<label>
							<input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_TRUST_PRODUCT"<#if (isAddAction || role.authorityList.contains("ROLE_TRUST_PRODUCT"))!> checked</#if> />产品管理
						</label>
						<label>
							<input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_TRUST_VALUE"<#if (isAddAction || role.authorityList.contains("ROLE_TRUST_VALUE"))!> checked</#if> />净值管理
						</label>
						<label>
							<input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_TRUST_CONFIRM_RECEIPT"<#if (isAddAction || role.authorityList.contains("ROLE_TRUST_CONFIRM_RECEIPT"))!> checked</#if> />基金管理费收款确认
						</label>
						
					</td>
				</tr>
				</#if>
				<#if superAuthorities.contains("ROLE_CONTENT")!>				
				<tr class="authorityList">
					<th>
						<a href="#" class="allChecked" title="点击全选此类权限">基础数据: </a>
					</th>
					<td>
					    <label>
							<input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_BASIS"<#if (isAddAction || role.authorityList.contains("ROLE_BASIS"))!> checked</#if> />基础数据
						</label>
						<label>
							<input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_BASE_STOCK"<#if (isAddAction || role.authorityList.contains("ROLE_BASE_STOCK"))!> checked</#if> />股指管理
						</label>
					</td>
				</tr>
				</#if>
				<#if superAuthorities.contains("ROLE_CONTENT")!>				
				<tr class="authorityList">
					<th>
						<a href="#" class="allChecked" title="点击全选此类权限">客户管理: </a>
					</th>
					<td>
					
					     <label>
							<input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_CLIENT"<#if (isAddAction || role.authorityList.contains("ROLE_CLIENT"))!> checked</#if> />客户管理
						</label>
						<label>
							<input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_CLIENT_PRODUCT"<#if (isAddAction || role.authorityList.contains("ROLE_CLIENT_PRODUCT"))!> checked</#if> />客户管理产品
						</label>
					</td>
				</tr>
				</#if>
				<#if superAuthorities.contains("ROLE_MAKE")!>				
					<tr class="authorityList">
						<th>
							<a href="#" class="allChecked" title="点击全选此类权限">预约认购: </a>
						</th>
						<td>
							<label>
	                            <input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_MAKE"<#if (isAddAction || role.authorityList.contains("ROLE_MAKE"))!> checked</#if> />预约认购
	                        </label>
	                        <label>
	                            <input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_MAKE_SUBSCRIBE"<#if (isAddAction || role.authorityList.contains("ROLE_MAKE_SUBSCRIBE"))!> checked</#if> />认购列表
	                        </label>
						</td>
					</tr>
				</#if>
				<#if superAuthorities.contains("ROLE_HR")!>				
					<tr class="authorityList">
						<th>
							<a href="#" class="allChecked" title="点击全选此类权限">HR管理: </a>
						</th>
						<td>
						    <label>
	                            <input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_HR"<#if (isAddAction || role.authorityList.contains("ROLE_HR"))!> checked</#if> />HR管理
	                        </label>
	                        <label>
	                            <input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_HR_DEPINFO"<#if (isAddAction || role.authorityList.contains("ROLE_HR_DEPINFO"))!> checked</#if> />部门信息
	                        </label>
	                          <label>
	                            <input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_HR_POST"<#if (isAddAction || role.authorityList.contains("ROLE_HR_POST"))!> checked</#if> />岗位管理
	                        </label>
	                          <label>
	                            <input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_HR_POSITION"<#if (isAddAction || role.authorityList.contains("ROLE_HR_POSITION"))!> checked</#if> />职务级别
	                        </label>
	                          <label>
	                            <input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_HR_ROLE"<#if (isAddAction || role.authorityList.contains("ROLE_HR_ROLE"))!> checked</#if> />角色管理
	                        </label>
	                          <label>
	                            <input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_HR_STAFF"<#if (isAddAction || role.authorityList.contains("ROLE_HR_STAFF"))!> checked</#if> />人员信息
	                        </label>
						</td>
					</tr>
				</#if>
				<#if superAuthorities.contains("ROLE_LOG")!>				
					<tr class="authorityList">
						<th>
							<a href="#" class="allChecked" title="点击全选此类权限">操作日志: </a>
						</th>
						<td>
							<label>
	                            <input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_LOG"<#if (isAddAction || role.authorityList.contains("ROLE_LOG"))!> checked</#if> />操作日志
	                        </label>
	                        <label>
	                            <input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_LOG_SEARCH"<#if (isAddAction || role.authorityList.contains("ROLE_LOG_SEARCH"))!> checked</#if> />日志查看
	                        </label>
	                         <label>
	                            <input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_LOG_SYSYEM_MESSAGE"<#if (isAddAction || role.authorityList.contains("ROLE_LOG_SYSYEM_MESSAGE"))!> checked</#if> />系统消息
	                        </label>
						</td>
					</tr>
				</#if>
				<#if superAuthorities.contains("ROLE_INTERIOR")!>				
					<tr class="authorityList">
						<th>
							<a href="#" class="allChecked" title="点击全选此类权限">内部管理: </a>
						</th>
						<td>
						  <label>
								<input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_INTERIOR"<#if (isAddAction || role.authorityList.contains("ROLE_INTERIOR"))!> checked</#if> />内部管理
							</label>
	                        <label>
	                            <input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_INTERIOR_COLLECT"<#if (isAddAction || role.authorityList.contains("ROLE_INTERIOR_COLLECT"))!> checked</#if> />产品每日汇总列表
	                        </label>
	                         <label>
	                            <input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_INTERIOR_ATTENDANCE"<#if (isAddAction || role.authorityList.contains("ROLE_INTERIOR_ATTENDANCE"))!> checked</#if> />考勤记录
	                        </label>
	                      
						</td>
					</tr>
				</#if>
				<#if superAuthorities.contains("ROLE_WORKFLOW")!>				
					<tr class="authorityList">
						<th>
							<a href="#" class="allChecked" title="点击全选此类权限">工作流: </a>
						</th>
						<td>
						
						   <label>
	                            <input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_WORKFLOW"<#if (isAddAction || role.authorityList.contains("ROLE_WORKFLOW"))!> checked</#if> />工作流
	                        </label>
	                        <label>
	                            <input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_WORKFLOW_DEFINITION"<#if (isAddAction || role.authorityList.contains("ROLE_WORKFLOW_DEFINITION"))!> checked</#if> />工作流定义
	                        </label>
	                         <label>
	                            <input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_WORK_CREATE"<#if (isAddAction || role.authorityList.contains("ROLE_WORK_CREATE"))!> checked</#if> />工作创建
	                        </label>
		                      <label>
		                            <input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_WORKFLOW_MYWORK"<#if (isAddAction || role.authorityList.contains("ROLE_WORKFLOW_MYWORK"))!> checked</#if> />我的工作
		                        </label>
		                           <label>
		                            <input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_WORKFLOW_WORKAUDIT"<#if (isAddAction || role.authorityList.contains("ROLE_WORKFLOW_WORKAUDIT"))!> checked</#if> />工作审核
		                        </label>
	                        <label>
	                            <input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_WORKFLOW_WORKSEARCH"<#if (isAddAction || role.authorityList.contains("ROLE_WORKFLOW_WORKSEARCH"))!> checked</#if> />工作查询
	                        </label>
	                         <label>
	                            <input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_WORKFLOW_MYPROCESSWORK"<#if (isAddAction || role.authorityList.contains("ROLE_WORKFLOW_MYPROCESSWORK"))!> checked</#if> />进展中的工作
	                        </label>
	                         <!--  <label>
	                            <input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_WORKFLOW_TEST"<#if (isAddAction || role.authorityList.contains("ROLE_WORKFLOW_TEST"))!> checked</#if> />工作测试
	                        </label>-->
						</td>
					</tr>
				</#if>
				<#if superAuthorities.contains("ROLE_PROJECT_LIST")!>				
					<tr class="authorityList">
						<th>
							<a href="#" class="allChecked" title="点击全选此类权限">项目管理: </a>
						</th>
						<td>
						   <label>
	                            <input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_PROJECT_LIST"<#if (isAddAction || role.authorityList.contains("ROLE_PROJECT_LIST"))!> checked</#if> />项目管理
	                        </label>
	                        <label>
	                            <input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_PROJECT_MY_PROJECT"<#if (isAddAction || role.authorityList.contains("ROLE_PROJECT_MY_PROJECT"))!> checked</#if> />我的项目
	                        </label>
						</td>
					</tr>
				</#if>
				<#if superAuthorities.contains("ROLE_WORKFLOW_DEVELOPMENT")!>				
				
					<tr class="authorityList">
						<th>
							<a href="#" class="allChecked" title="点击全选此类权限">需求开发管理 : </a>
						</th>
						<td>
							<label>
	                            <input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_WORKFLOW_DEVELOPMENT"<#if (isAddAction || role.authorityList.contains("ROLE_WORKFLOW_DEVELOPMENT"))!> checked</#if> />需求开发管理 
	                        </label>
						   <label>
	                            <input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_WORKFLOW_EXTERNAL_REQUIREMENT"<#if (isAddAction || role.authorityList.contains("ROLE_WORKFLOW_EXTERNAL_REQUIREMENT"))!> checked</#if> />外部需求审批
	                        </label>
						   <label>
	                            <input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_WORKFLOW_INTERNAL_REQUIREMENT"<#if (isAddAction || role.authorityList.contains("ROLE_WORKFLOW_INTERNAL_REQUIREMENT"))!> checked</#if> />内部需求审批
	                        </label>
						   <label>
	                            <input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_WORKFLOW_REQUIREMENT_DEVELOPMENT"<#if (isAddAction || role.authorityList.contains("ROLE_WORKFLOW_REQUIREMENT_DEVELOPMENT"))!> checked</#if> />提交开发审批
	                        </label>
	                        <label>
	                            <input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_WORKFLOW_DEVELOPMENT_DEPLOY"<#if (isAddAction || role.authorityList.contains("ROLE_WORKFLOW_DEVELOPMENT_DEPLOY"))!> checked</#if> />部署提交审批
	                        </label>
	                        <label>
	                            <input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_WORKFLOW_DEVELOPMENT_URGENT_DEPLOY"<#if (isAddAction || role.authorityList.contains("ROLE_WORKFLOW_DEVELOPMENT_URGENT_DEPLOY"))!> checked</#if> />紧急部署提交审批
	                        </label>
	                        <label>
	                            <input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_WORKFLOW_DEVELOPMENT_ONLINE"<#if (isAddAction || role.authorityList.contains("ROLE_WORKFLOW_DEVELOPMENT_ONLINE"))!> checked</#if> />线上bug提交审批
	                        </label>
	                        <label>
	                            <input type="checkbox" name="role.authorityList" class="roleAuthorityList" value="ROLE_WORKFLOW_REQUIREMENT_MYWORK"<#if (isAddAction || role.authorityList.contains("ROLE_WORKFLOW_REQUIREMENT_MYWORK"))!> checked</#if> />我的审批
	                        </label>
						</td>
					</tr>
				</#if>
				<#if rootContractCagetoryList?? >
		        <#list rootContractCagetoryList as item>
					<tr class="authorityList">
						<th>
							<a href="#" class="allChecked" title="点击全选此类权限">${item.name}: </a>
						</th>
						<td>
						   <label>
	                            <input type="checkbox" name="role.ccList" class="roleAuthorityList" value="${item.id}"<#if (isAddAction || role.ccList.contains("${item.id}"))!> checked</#if> />${item.name}
	                       </label>
						  <#list item.contractCategorySet as item1>
						   <#if (!item1.recyle)> 
							   <label>
		                            <input type="checkbox" name="role.ccList" class="roleAuthorityList" value="${item1.id}"<#if (isAddAction || role.ccList.contains("${item1.id}"))!> checked</#if> />${item1.name}
		                       </label>
		                   </#if>
	                      </#list>
  						  <#list item.projectCategorySet as item1>
							   <label>
		                            <input type="checkbox" name="role.pcList" class="roleAuthorityList" value="${item1.id}"<#if (isAddAction || role.pcList.contains("${item1.id}"))!> checked</#if> />${item1.name}
		                       </label>
	                      </#list>
						</td>
					</tr>
		         </#list>
		         </#if>
				
				<#if (role.isSystem)!false>
					<tr>
						<th>
							&nbsp;
						</th>
						<td>
							<span class="warnInfo"><span class="icon">&nbsp;</span>系统提示: </b>系统内置角色不允许修改!</span>
						</td>
					</tr>
				</#if>
			</table>
			<div class="buttonArea">
				<input type="submit" class="formButton" value="确  定" hidefocus />&nbsp;&nbsp;
				<input type="button" class="formButton" onclick="window.history.back(); return false;" value="返  回" hidefocus />
			</div>
		</form>
	</div>
</body>
</html>