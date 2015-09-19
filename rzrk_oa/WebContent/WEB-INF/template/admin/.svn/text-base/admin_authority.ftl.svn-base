<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>编辑权限 - Powered By UNICORN</title>
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
  //  window.authorityList =  <#if authorityList?? > ${authorityList} <#else> {} </#if>;
   //  window.contractCategoryList =  <#if contractCategoryList?? > ${contractCategoryList} <#else> {} </#if>;
    //  window.projectList =  <#if projectList?? > ${projectList} <#else> {} </#if>;
	var $validateErrorContainer = $("#validateErrorContainer");
	var $validateErrorLabelContainer = $("#validateErrorContainer ul");
	var $validateForm = $("#validateForm");
	
	var $allChecked = $("#validateForm .allChecked");

	
	
	
//	$.validator.addMethod("roleAuthorityListRequired", $.validator.methods.required, "请选择管理权限");
	
	$.validator.addClassRules("roleAuthorityList", {
		roleAuthorityListRequired: true
	});
	
	$(".all").click(function(){
		$(":checkbox").prop("checked", true);
	});
	
	$(".revert").click(function(){
		$(":checkbox").each(function(i, checkbox){
			if($(this).attr("roletype") == null || $(this).attr("roletype") == ""){
				$(checkbox).prop("checked", !$(checkbox).prop("checked"));
			}
		});
	});
	
	var cbs = $("tr").find(":checkbox:first");
	cbs.click(function(){
		if($(this).attr("roletype") == null || $(this).attr("roletype") == ""){
				var _this = $(this);
			var isChecked = _this.prop("checked");
		    var siblings = _this.parent().siblings().find(":checkbox");
		    siblings.each(function(i, c){
		   	if($(this).attr("roletype") == null || $(this).attr("roletype") == ""){
				$(this).prop("checked", isChecked);
		   	}
			
			});
		}
		
		
	});

	var cbss = $("tr").find(":checkbox:not(:first)");
	cbss.click(function(){
	if($(this).attr("roletype") == null || $(this).attr("roletype") == ""){
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
	}
		
	});
})
</script>
</head>
<body class="input role">
	<div class="bar">
		编辑权限
	</div>
	<div id="validateErrorContainer" class="validateErrorContainer">
		<div class="validateErrorTitle">以下信息填写有误,请重新填写</div>
		<ul></ul>
	</div>
	<div class="body">
		<form id="validateForm" action="admin!saveAuthority.action" method="post">
		<input type="hidden" name="admin.id" value="${(admin.id)!}" />
		<input type="hidden" name="deparmentId" value="${(deparmentId)!}" />
		
			<table class="inputTable">
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
						    <#if (authorityList.contains("ROLE_CONTENT"))!>
								<input type="checkbox" name="admin.authorityList" class="roleAuthorityList"  roletype="true" onclick = "return false;" value="ROLE_CONTENT" checked />内容管理
						    <#else>
						   		 <input type="checkbox" name="admin.authorityList" class="roleAuthorityList" value="ROLE_CONTENT"<#if (admin.authorityList.contains("ROLE_CONTENT"))!> checked</#if> />内容管理
						    </#if>
							</label>
							<label>
							   <#if (authorityList.contains("ROLE_CONTENT_ARTICLE"))!>
								<input type="checkbox" name="admin.authorityList" class="roleAuthorityList"  roletype="true" onclick = "return false;" value="ROLE_CONTENT_ARTICLE" checked />文章管理
							   <#else>
						   		 <input type="checkbox" name="admin.authorityList" class="roleAuthorityList" value="ROLE_CONTENT_ARTICLE"<#if (admin.authorityList.contains("ROLE_CONTENT_ARTICLE"))!> checked</#if> />文章管理
						        </#if>
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
					      <#if (authorityList.contains("ROLE_BASE_TYPE"))!>
						    <input type="checkbox" name="admin.authorityList" class="roleAuthorityList" roletype="true" onclick = "return false;" value="ROLE_BASE_TYPE" checked  />基础分类数据
						   <#else>
					   			<input type="checkbox" name="admin.authorityList" class="roleAuthorityList" value="ROLE_BASE_TYPE"<#if (admin.authorityList.contains("ROLE_BASE_TYPE"))!> checked</#if> />基础分类数据
					        </#if>
						
						</label>
					    <label>
					     <#if (authorityList.contains("ROLE_BASE_TYPE_ONE"))!>
						  <input type="checkbox" name="admin.authorityList" class="roleAuthorityList" roletype="true" onclick = "return false;" value="ROLE_BASE_TYPE_ONE"  checked />一级分类
						   <#else>
					   			<input type="checkbox" name="admin.authorityList" class="roleAuthorityList" value="ROLE_BASE_TYPE_ONE"<#if (admin.authorityList.contains("ROLE_BASE_TYPE_ONE"))!> checked</#if> />一级分类
					        </#if>
							
						</label>
						<label>
						 <#if (authorityList.contains("ROLE_BASE_TYPE_TWO"))!>
							  <input type="checkbox" name="admin.authorityList" class="roleAuthorityList" roletype="true" onclick = "return false;" value="ROLE_BASE_TYPE_TWO"  checked />二级分类
						   <#else>
					   			<input type="checkbox" name="admin.authorityList" class="roleAuthorityList" value="ROLE_BASE_TYPE_TWO"<#if (admin.authorityList.contains("ROLE_BASE_TYPE_TWO"))!> checked</#if> />二级分类
					        </#if>
							
						</label>
						<label>
						 <#if (authorityList.contains("ROLE_BASE_TYPE_PROJECT_LIST"))!>
							  <input type="checkbox" name="admin.authorityList" class="roleAuthorityList" roletype="true" onclick = "return false;" value="ROLE_BASE_TYPE_PROJECT_LIST"  checked />项目列表
						   <#else>
					   			<input type="checkbox" name="admin.authorityList" class="roleAuthorityList" value="ROLE_BASE_TYPE_PROJECT_LIST" <#if (admin.authorityList.contains("ROLE_BASE_TYPE_PROJECT_LIST"))!> checked</#if> />项目列表
					        </#if>
							
						</label>
					</td>
				</tr>
				</#if>
				<#if superAuthorities.contains("ROLE_TRUST")!>
				<tr class="authorityList">
					<th>
						<a href="#" class="allChecked" title="点击全选此类权限">信托产品: </a>
					</th>
					<td>
					    <label>
					     <#if (authorityList.contains("ROLE_TRUST"))!>
							  <input type="checkbox" name="admin.authorityList" class="roleAuthorityList" roletype="true" onclick = "return false;" value="ROLE_TRUST"  checked />信托产品
						   <#else>
					   			<input type="checkbox" name="admin.authorityList" class="roleAuthorityList" value="ROLE_TRUST"<#if (admin.authorityList.contains("ROLE_TRUST"))!> checked</#if> />信托产品
					        </#if>
							
						</label>
						<label>
						  <#if (authorityList.contains("ROLE_TRUST_PRODUCT"))!>
							  <input type="checkbox" name="admin.authorityList" class="roleAuthorityList" roletype="true" onclick = "return false;" value="ROLE_TRUST_PRODUCT"  checked />产品管理
						   <#else>
					   		<input type="checkbox" name="admin.authorityList" class="roleAuthorityList" value="ROLE_TRUST_PRODUCT"<#if (admin.authorityList.contains("ROLE_TRUST_PRODUCT"))!> checked</#if> />产品管理
					        </#if>
							
						</label>
						<label>
						  <#if (authorityList.contains("ROLE_TRUST_VALUE"))!>
							  <input type="checkbox" name="admin.authorityList" class="roleAuthorityList" roletype="true" onclick = "return false;" value="ROLE_TRUST_VALUE"  checked />净值管理
						   <#else>
					   		  <input type="checkbox" name="admin.authorityList" class="roleAuthorityList" value="ROLE_TRUST_VALUE"<#if (admin.authorityList.contains("ROLE_TRUST_VALUE"))!> checked</#if> />净值管理
					        </#if>
						
						</label>
						<label>
						  <#if (authorityList.contains("ROLE_TRUST_CONFIRM_RECEIPT"))!>
							  <input type="checkbox" name="admin.authorityList" class="roleAuthorityList" roletype="true" onclick = "return false;" value="ROLE_TRUST_CONFIRM_RECEIPT"  checked />基金管理费收款确认
						   <#else>
					   		  <input type="checkbox" name="admin.authorityList" class="roleAuthorityList" value="ROLE_TRUST_CONFIRM_RECEIPT"<#if (admin.authorityList.contains("ROLE_TRUST_CONFIRM_RECEIPT"))!> checked</#if> />基金管理费收款确认
					        </#if>
						
						</label>
					</td>
				</tr>
				</#if>
				<#if superAuthorities.contains("ROLE_BASIS")!>
				<tr class="authorityList">
					<th>
						<a href="#" class="allChecked" title="点击全选此类权限">基础数据: </a>
					</th>
					<td>
					    <label>
					     <#if (authorityList.contains("ROLE_BASIS"))!>
							  <input type="checkbox" name="admin.authorityList" class="roleAuthorityList" roletype="true" onclick = "return false;" value="ROLE_BASIS"  checked />基础数据
						   <#else>
					   		<input type="checkbox" name="admin.authorityList" class="roleAuthorityList" value="ROLE_BASIS"<#if (admin.authorityList.contains("ROLE_BASIS"))!> checked</#if> />基础数据
					        </#if>
							
						</label>
						<label>
						 <#if (authorityList.contains("ROLE_BASE_STOCK"))!>
							  <input type="checkbox" name="admin.authorityList" class="roleAuthorityList" roletype="true" onclick = "return false;" value="ROLE_BASE_STOCK"  checked />股指管理
						   <#else>
					   			<input type="checkbox" name="admin.authorityList" class="roleAuthorityList" value="ROLE_BASE_STOCK"<#if (admin.authorityList.contains("ROLE_BASE_STOCK"))!> checked</#if> />股指管理
					        </#if>
						</label>
					</td>
				</tr>
				</#if>
				<#if superAuthorities.contains("ROLE_CLIENT")!>
				<tr class="authorityList">
					<th>
						<a href="#" class="allChecked" title="点击全选此类权限">客户管理: </a>
					</th>
					<td>
					
					     <label>
					      <#if (authorityList.contains("ROLE_CLIENT"))!>
							  <input type="checkbox" name="admin.authorityList" class="roleAuthorityList" roletype="true" onclick = "return false;" value="ROLE_CLIENT"  checked />客户管理
						   <#else>
					   		<input type="checkbox" name="admin.authorityList" class="roleAuthorityList" value="ROLE_CLIENT"<#if (admin.authorityList.contains("ROLE_CLIENT"))!> checked</#if> />客户管理
					        </#if>
							
						</label>
						<label>
						    <#if (authorityList.contains("ROLE_CLIENT_PRODUCT"))!>
							  <input type="checkbox" name="admin.authorityList" class="roleAuthorityList" roletype="true" onclick = "return false;" value="ROLE_CLIENT_PRODUCT"  checked />客户管理产品
						   <#else>
					   			<input type="checkbox" name="admin.authorityList" class="roleAuthorityList" value="ROLE_CLIENT_PRODUCT"<#if (admin.authorityList.contains("ROLE_CLIENT_PRODUCT"))!> checked</#if> />客户管理产品
					        </#if>
						
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
						    <#if (authorityList.contains("ROLE_MAKE"))!>
							  <input type="checkbox" name="admin.authorityList" class="roleAuthorityList" roletype="true" onclick = "return false;" value="ROLE_MAKE"  checked />预约认购
						   <#else>
					   			 <input type="checkbox" name="admin.authorityList" class="roleAuthorityList" value="ROLE_MAKE"<#if (admin.authorityList.contains("ROLE_MAKE"))!> checked</#if> />预约认购
					        </#if>
                           
                        </label>
                        <label>
                            <#if (authorityList.contains("ROLE_MAKE_SUBSCRIBE"))!>
							  <input type="checkbox" name="admin.authorityList" class="roleAuthorityList" roletype="true" onclick = "return false;" value="ROLE_MAKE_SUBSCRIBE"  checked />认购列表
						   <#else>
					   		   <input type="checkbox" name="admin.authorityList" class="roleAuthorityList" value="ROLE_MAKE_SUBSCRIBE"<#if (admin.authorityList.contains("ROLE_MAKE_SUBSCRIBE"))!> checked</#if> />认购列表
					        </#if>
                         
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
					     <#if (authorityList.contains("ROLE_HR"))!>
							  <input type="checkbox" name="admin.authorityList" class="roleAuthorityList" roletype="true" onclick = "return false;" value="ROLE_HR"  checked />HR管理
						   <#else>
					   		    <input type="checkbox" name="admin.authorityList" class="roleAuthorityList" value="ROLE_HR"<#if (admin.authorityList.contains("ROLE_HR"))!> checked</#if> />HR管理
					        </#if>
                        </label>
                        <label>
                         <#if (authorityList.contains("ROLE_HR_DEPINFO"))!>
							  <input type="checkbox" name="admin.authorityList" class="roleAuthorityList" roletype="true" onclick = "return false;" value="ROLE_HR_DEPINFO"  checked />部门信息
						   <#else>
					   		       <input type="checkbox" name="admin.authorityList" class="roleAuthorityList" value="ROLE_HR_DEPINFO"<#if (admin.authorityList.contains("ROLE_HR_DEPINFO"))!> checked</#if> />部门信息
					        </#if>
                         
                        </label>
                          <label>
                            <#if (authorityList.contains("ROLE_HR_POST"))!>
							  <input type="checkbox" name="admin.authorityList" class="roleAuthorityList" roletype="true" onclick = "return false;" value="ROLE_HR_POST"  checked />岗位管理
						   <#else>
					   		       <input type="checkbox" name="admin.authorityList" class="roleAuthorityList" value="ROLE_HR_POST"<#if (admin.authorityList.contains("ROLE_HR_POST"))!> checked</#if> />岗位管理
					        </#if>
                          
                        </label>
                          <label>
                          <#if (authorityList.contains("ROLE_HR_POSITION"))!>
							  <input type="checkbox" name="admin.authorityList" class="roleAuthorityList" roletype="true" onclick = "return false;" value="ROLE_HR_POSITION"  checked />职务级别
						   <#else>
					   		       <input type="checkbox" name="admin.authorityList" class="roleAuthorityList" value="ROLE_HR_POSITION"<#if (admin.authorityList.contains("ROLE_HR_POSITION"))!> checked</#if> />职务级别
					        </#if>
                           
                        </label>
                          <label>
                          <#if (authorityList.contains("ROLE_HR_ROLE"))!>
							  <input type="checkbox" name="admin.authorityList" class="roleAuthorityList" roletype="true" onclick = "return false;" value="ROLE_HR_ROLE"  checked />角色管理
						   <#else>
					   		 <input type="checkbox" name="admin.authorityList" class="roleAuthorityList" value="ROLE_HR_ROLE"<#if (admin.authorityList.contains("ROLE_HR_ROLE"))!> checked</#if> />角色管理
					        </#if>
                            
                        </label>
                          <label>
                          <#if (authorityList.contains("ROLE_HR_STAFF"))!>
							  <input type="checkbox" name="admin.authorityList" class="roleAuthorityList" roletype="true" onclick = "return false;" value="ROLE_HR_STAFF"  checked />人员信息
						   <#else>
					   		       <input type="checkbox" name="admin.authorityList" class="roleAuthorityList" value="ROLE_HR_STAFF"<#if (admin.authorityList.contains("ROLE_HR_STAFF"))!> checked</#if> />人员信息
					        </#if>
                          
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
						  <#if (authorityList.contains("ROLE_LOG"))!>
							  <input type="checkbox" name="admin.authorityList" class="roleAuthorityList" roletype="true" onclick = "return false;" value="ROLE_LOG"  checked />操作日志
						   <#else>
					   		      <input type="checkbox" name="admin.authorityList" class="roleAuthorityList" value="ROLE_LOG"<#if (admin.authorityList.contains("ROLE_LOG"))!> checked</#if> />操作日志
					        </#if>
                          
                        </label>
                        <label>
                          <#if (authorityList.contains("ROLE_LOG_SEARCH"))!>
							  <input type="checkbox" name="admin.authorityList" class="roleAuthorityList" roletype="true" onclick = "return false;" value="ROLE_LOG_SEARCH"  checked />日志查看
						   <#else>
					   		   <input type="checkbox" name="admin.authorityList" class="roleAuthorityList" value="ROLE_LOG_SEARCH"<#if (admin.authorityList.contains("ROLE_LOG_SEARCH"))!> checked</#if> />日志查看
					        </#if>
                        
                        </label>
                        
                         <label>
                          <#if (authorityList.contains("ROLE_LOG_SYSYEM_MESSAGE"))!>
							  <input type="checkbox" name="admin.authorityList" class="roleAuthorityList" roletype="true" onclick = "return false;" value="ROLE_LOG_SYSYEM_MESSAGE"  checked />系统消息
						   <#else>
					   		   <input type="checkbox" name="admin.authorityList" class="roleAuthorityList" value="ROLE_LOG_SYSYEM_MESSAGE"<#if (admin.authorityList.contains("ROLE_LOG_SYSYEM_MESSAGE"))!> checked</#if> />系统消息
					        </#if>
                        
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
					      <#if (authorityList.contains("ROLE_INTERIOR"))!>
							  <input type="checkbox" name="admin.authorityList" class="roleAuthorityList" roletype="true" onclick = "return false;" value="ROLE_INTERIOR"  checked />内部管理
						   <#else>
					   		  <input type="checkbox" name="admin.authorityList" class="roleAuthorityList" value="ROLE_INTERIOR"<#if (admin.authorityList.contains("ROLE_INTERIOR"))!> checked</#if> />内部管理
					        </#if>
						</label>
                        <label>
                         <#if (authorityList.contains("ROLE_INTERIOR_COLLECT"))!>
							  <input type="checkbox" name="admin.authorityList" class="roleAuthorityList" roletype="true" onclick = "return false;" value="ROLE_INTERIOR_COLLECT"  checked />产品每日汇总列表
						   <#else>
					   		   <input type="checkbox" name="admin.authorityList" class="roleAuthorityList" value="ROLE_INTERIOR_COLLECT"<#if (admin.authorityList.contains("ROLE_INTERIOR_COLLECT"))!> checked</#if> />产品每日汇总列表
					        </#if>
                          
                        </label>
                         <label>
                             <#if (authorityList.contains("ROLE_INTERIOR_ATTENDANCE"))!>
							  <input type="checkbox" name="admin.authorityList" class="roleAuthorityList" roletype="true" onclick = "return false;" value="ROLE_INTERIOR_ATTENDANCE"  checked />考勤记录
						   <#else>
					   		   <input type="checkbox" name="admin.authorityList" class="roleAuthorityList" value="ROLE_INTERIOR_ATTENDANCE"<#if (admin.authorityList.contains("ROLE_INTERIOR_ATTENDANCE"))!> checked</#if> />考勤记录
					        </#if>
                            
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
					    <#if (authorityList.contains("ROLE_WORKFLOW"))!>
							  <input type="checkbox" name="admin.authorityList" class="roleAuthorityList" roletype="true" onclick = "return false;" value="ROLE_WORKFLOW"  checked />工作流
						   <#else>
					   		    <input type="checkbox" name="admin.authorityList" class="roleAuthorityList" value="ROLE_WORKFLOW"<#if ( admin.authorityList.contains("ROLE_WORKFLOW"))!> checked</#if> />工作流
                            
					        </#if>
                          
                        </label>
                        <label>
                          <#if (authorityList.contains("ROLE_WORKFLOW_DEFINITION"))!>
							  <input type="checkbox" name="admin.authorityList" class="roleAuthorityList" roletype="true" onclick = "return false;" value="ROLE_WORKFLOW_DEFINITION"  checked />工作流定义
						   <#else>
					   		  <input type="checkbox" name="admin.authorityList" class="roleAuthorityList" value="ROLE_WORKFLOW_DEFINITION"<#if (admin.authorityList.contains("ROLE_WORKFLOW_DEFINITION"))!> checked</#if> />工作流定义
                            
					        </#if>
                        
                        </label>
                         <label>
                             <#if (authorityList.contains("ROLE_WORK_CREATE"))!>
							  <input type="checkbox" name="admin.authorityList" class="roleAuthorityList" roletype="true" onclick = "return false;" value="ROLE_WORK_CREATE"  checked />工作创建
						   <#else>
					   		  <input type="checkbox" name="admin.authorityList" class="roleAuthorityList" value="ROLE_WORK_CREATE"<#if (admin.authorityList.contains("ROLE_WORK_CREATE"))!> checked</#if> />工作创建
                            
					        </#if>
                           
                        </label>
	                         <label>
	                         <#if (authorityList.contains("ROLE_WORKFLOW_MYWORK"))!>
								  <input type="checkbox" name="admin.authorityList" class="roleAuthorityList" roletype="true" onclick = "return false;" value="ROLE_WORKFLOW_MYWORK"  checked />我的工作
							   <#else>
						   		 <input type="checkbox" name="admin.authorityList" class="roleAuthorityList" value="ROLE_WORKFLOW_MYWORK"<#if (admin.authorityList.contains("ROLE_WORKFLOW_MYWORK"))!> checked</#if> />我的工作
	                            
						        </#if>
	                        </label>
	                           <label>
	                            <#if (authorityList.contains("ROLE_WORKFLOW_WORKAUDIT"))!>
								  <input type="checkbox" name="admin.authorityList" class="roleAuthorityList" roletype="true" onclick = "return false;" value="ROLE_WORKFLOW_WORKAUDIT"  checked />工作审核
							   <#else>
						   	   <input type="checkbox" name="admin.authorityList" class="roleAuthorityList" value="ROLE_WORKFLOW_WORKAUDIT"<#if (admin.authorityList.contains("ROLE_WORKFLOW_WORKAUDIT"))!> checked</#if> />工作审核
						        </#if>
	                         
	                        </label>
                        <label>
                         	<#if (authorityList.contains("ROLE_WORKFLOW_WORKSEARCH"))!>
							  <input type="checkbox" name="admin.authorityList" class="roleAuthorityList" roletype="true" onclick = "return false;" value="ROLE_WORKFLOW_WORKSEARCH"  checked />工作查询
						   <#else>
					   	  <input type="checkbox" name="admin.authorityList" class="roleAuthorityList" value="ROLE_WORKFLOW_WORKSEARCH"<#if (admin.authorityList.contains("ROLE_WORKFLOW_WORKSEARCH"))!> checked</#if> />工作查询
					        </#if>
                        </label>
                          <label>
                         	<#if (authorityList.contains("ROLE_WORKFLOW_MYPROCESSWORK"))!>
							  <input type="checkbox" name="admin.authorityList" class="roleAuthorityList" roletype="true" onclick = "return false;" value="ROLE_WORKFLOW_MYPROCESSWORK"  checked />进展中的工作
						   <#else>
					   	  <input type="checkbox" name="admin.authorityList" class="roleAuthorityList" value="ROLE_WORKFLOW_MYPROCESSWORK"<#if (admin.authorityList.contains("ROLE_WORKFLOW_MYPROCESSWORK"))!> checked</#if> />进展中的工作
					        </#if>
                        </label>
                         <!--  <label>
                            <input type="checkbox" name="admin.authorityList" class="roleAuthorityList" value="ROLE_WORKFLOW_TEST"<#if ( admin.authorityList.contains("ROLE_WORKFLOW_TEST"))!> checked</#if> />工作测试
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
					   	<#if (authorityList.contains("ROLE_PROJECT_LIST"))!>
							  <input type="checkbox" name="admin.authorityList" class="roleAuthorityList" roletype="true" onclick = "return false;" value="ROLE_PROJECT_LIST"  checked />项目管理
						   <#else>
					   	 <input type="checkbox" name="admin.authorityList" class="roleAuthorityList" value="ROLE_PROJECT_LIST"<#if (admin.authorityList.contains("ROLE_PROJECT_LIST"))!> checked</#if> />项目管理
					        </#if>
                           
                        </label>
                        <label>
                         	<#if (authorityList.contains("ROLE_PROJECT_MY_PROJECT"))!>
							  <input type="checkbox" name="admin.authorityList" class="roleAuthorityList" roletype="true" onclick = "return false;" value="ROLE_PROJECT_MY_PROJECT"  checked />我的项目
						   <#else>
					   	   <input type="checkbox" name="admin.authorityList" class="roleAuthorityList" value="ROLE_PROJECT_MY_PROJECT"<#if (admin.authorityList.contains("ROLE_PROJECT_MY_PROJECT"))!> checked</#if> />我的项目
					        </#if>
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
					  	   <#if (authorityList.contains("ROLE_WORKFLOW_DEVELOPMENT"))!>
							  <input type="checkbox" name="admin.authorityList" class="roleAuthorityList" roletype="true" onclick = "return false;" value="ROLE_WORKFLOW_DEVELOPMENT"  checked />需求开发管理 
						   <#else>
					   	    <input type="checkbox" name="admin.authorityList" class="roleAuthorityList" value="ROLE_WORKFLOW_DEVELOPMENT"<#if (admin.authorityList.contains("ROLE_WORKFLOW_DEVELOPMENT"))!> checked</#if> />需求开发管理 
					        </#if>
                       </label>
					   <label>
					  	   <#if (authorityList.contains("ROLE_WORKFLOW_EXTERNAL_REQUIREMENT"))!>
							  <input type="checkbox" name="admin.authorityList" class="roleAuthorityList" roletype="true" onclick = "return false;" value="ROLE_WORKFLOW_EXTERNAL_REQUIREMENT"  checked />外部需求审批
						   <#else>
					   	    <input type="checkbox" name="admin.authorityList" class="roleAuthorityList" value="ROLE_WORKFLOW_EXTERNAL_REQUIREMENT"<#if (admin.authorityList.contains("ROLE_WORKFLOW_EXTERNAL_REQUIREMENT"))!> checked</#if> />外部需求审批
					        </#if>
                        </label>
					   <label>
					   		 <#if (authorityList.contains("ROLE_WORKFLOW_INTERNAL_REQUIREMENT"))!>
							  <input type="checkbox" name="admin.authorityList" class="roleAuthorityList" roletype="true" onclick = "return false;" value="ROLE_WORKFLOW_INTERNAL_REQUIREMENT"  checked />内部需求审批
						   <#else>
					   	    <input type="checkbox" name="admin.authorityList" class="roleAuthorityList" value="ROLE_WORKFLOW_INTERNAL_REQUIREMENT"<#if (admin.authorityList.contains("ROLE_WORKFLOW_INTERNAL_REQUIREMENT"))!> checked</#if> />内部需求审批
					        </#if>
                        </label>
					   <label>
					   		 <#if (authorityList.contains("ROLE_WORKFLOW_REQUIREMENT_DEVELOPMENT"))!>
							  <input type="checkbox" name="admin.authorityList" class="roleAuthorityList" roletype="true" onclick = "return false;" value="ROLE_WORKFLOW_REQUIREMENT_DEVELOPMENT"  checked />提交开发审批
						   <#else>
					   	    <input type="checkbox" name="admin.authorityList" class="roleAuthorityList" value="ROLE_WORKFLOW_REQUIREMENT_DEVELOPMENT"<#if (admin.authorityList.contains("ROLE_WORKFLOW_REQUIREMENT_DEVELOPMENT"))!> checked</#if> />提交开发审批
					        </#if>
                            
                        </label>
                        <label>
					   		 <#if (authorityList.contains("ROLE_WORKFLOW_DEVELOPMENT_DEPLOY"))!>
							  <input type="checkbox" name="admin.authorityList" class="roleAuthorityList" roletype="true" onclick = "return false;" value="ROLE_WORKFLOW_DEVELOPMENT_DEPLOY"  checked />部署提交审批
						   <#else>
					   	    <input type="checkbox" name="admin.authorityList" class="roleAuthorityList" value="ROLE_WORKFLOW_DEVELOPMENT_DEPLOY"<#if (admin.authorityList.contains("ROLE_WORKFLOW_DEVELOPMENT_DEPLOY"))!> checked</#if> />部署提交审批
					        </#if>
                            
                        </label>
                        <label>
					   		 <#if (authorityList.contains("ROLE_WORKFLOW_DEVELOPMENT_URGENT_DEPLOY"))!>
							  <input type="checkbox" name="admin.authorityList" class="roleAuthorityList" roletype="true" onclick = "return false;" value="ROLE_WORKFLOW_DEVELOPMENT_URGENT_DEPLOY"  checked />紧急部署提交审批
						   <#else>
					   	    <input type="checkbox" name="admin.authorityList" class="roleAuthorityList" value="ROLE_WORKFLOW_DEVELOPMENT_URGENT_DEPLOY"<#if (admin.authorityList.contains("ROLE_WORKFLOW_DEVELOPMENT_URGENT_DEPLOY"))!> checked</#if> />紧急部署提交审批
					        </#if>
                            
                        </label>
                        <label>
					   		 <#if (authorityList.contains("ROLE_WORKFLOW_DEVELOPMENT_ONLINE"))!>
							  <input type="checkbox" name="admin.authorityList" class="roleAuthorityList" roletype="true" onclick = "return false;" value="ROLE_WORKFLOW_DEVELOPMENT_ONLINE"  checked />线上bug提交审批
						   <#else>
					   	    <input type="checkbox" name="admin.authorityList" class="roleAuthorityList" value="ROLE_WORKFLOW_DEVELOPMENT_ONLINE"<#if (admin.authorityList.contains("ROLE_WORKFLOW_DEVELOPMENT_ONLINE"))!> checked</#if> />线上bug提交审批
					        </#if>
                            
                        </label>
                        <label>
                        	 <#if (authorityList.contains("ROLE_WORKFLOW_REQUIREMENT_MYWORK"))!>
							  <input type="checkbox" name="admin.authorityList" class="roleAuthorityList" roletype="true" onclick = "return false;" value="ROLE_WORKFLOW_REQUIREMENT_MYWORK"  checked />我的审批
						   <#else>
					   	     <input type="checkbox" name="admin.authorityList" class="roleAuthorityList" value="ROLE_WORKFLOW_REQUIREMENT_MYWORK"<#if (admin.authorityList.contains("ROLE_WORKFLOW_REQUIREMENT_MYWORK"))!> checked</#if> />我的审批
					        </#if>
                           
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
						    <#if (contractCategoryList.contains("${item.id}"))!>
	                            <input type="checkbox" name="admin.ccList" class="roleAuthorityList" roletype="true" onclick = "return false;" value="${item.id}"   checked />${item.name}
	                         <#else>
	                         	<input type="checkbox" name="admin.ccList" class="roleAuthorityList" value="${item.id}" <#if (admin.ccList.contains("${item.id}"))!> checked</#if> />${item.name}
	                         </#if>
	                       </label>
						  <#list item.contractCategorySet as item1>
						   <#if (!item1.recyle)> 
							   <label>
							    <#if (contractCategoryList.contains("${item1.id}"))!>
	                            <input type="checkbox" name="admin.ccList" class="roleAuthorityList" roletype="true" onclick = "return false;" value="${item1.id}"  checked  />${item1.name}
	                        	 <#else> 
	                         	 <input type="checkbox" name="admin.ccList" class="roleAuthorityList" value="${item1.id}"<#if (admin.ccList.contains("${item1.id}"))!> checked</#if> />${item1.name}
	                         	</#if>
		                          
		                       </label>
		                    </#if>
	                      </#list>
  						  <#list item.projectCategorySet as item1>
							   <label>
							    <#if (projectList.contains("${item1.id}"))!>
	                            <input type="checkbox" name="admin.pcList" class="roleAuthorityList" roletype="true" onclick = "return false;" value="${item1.id}"  checked  />${item1.name}
	                        	 <#else> 
	                         	 <input type="checkbox" name="admin.pcList" class="roleAuthorityList" value="${item1.id}"<#if (admin.pcList.contains("${item1.id}"))!> checked</#if> />${item1.name}
	                         	</#if>
		      
		                       </label>
	                      </#list>
						</td>
					</tr>
		         </#list>
				</#if>
			</table>
			<div class="buttonArea">
				<input type="submit" class="formButton"  value="确  定" hidefocus />&nbsp;&nbsp;
				<input type="button" class="formButton" onclick="window.history.back(); return false;" value="返  回" hidefocus />
			</div>
		</form>
	</div>
</body>
</html>