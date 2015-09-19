<#assign sec=JspTaglibs["/WEB-INF/tld/security.tld"] />
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta content="IE=edge,chrome=1" http-equiv="X-UA-Compatible">
    <meta http-equiv="Cache-control" content="no-store">
    <title></title>
    <link rel="stylesheet" href="/rzrk/js/jquery-easyui-1.4.1/themes/icon.css" />
    <link rel="stylesheet" href="/rzrk/js/jquery-easyui-1.4.1/themes/metro/easyui.css" id="themes"/>
    <link rel="stylesheet" href="/rzrk/css/background/common.css" />
    <link rel="stylesheet" href="/rzrk/css/background/item_index.css" />
    <script type="text/javascript" src="/rzrk/js/alljs.js"></script>
    <script type="text/javascript" src="/rzrk/js/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="/rzrk/js/jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="/rzrk/js/background/libs/jquery.sortable.min.js"></script>
	<script type="text/javascript">
		window.userId = "${loginAdmin.id}";
		window.focused = {
	        <#list itemList as item>
	        	"${item.url}": {
	        		"id": "${item.id}",
	        		"name": "${item.name}",
	        		"url": "${item.url}"
	        	},
			</#list>
		};
		window.categoryIdx = {
			<#list rootContractCagetoryList as item>
				<#if (loginAdmin.allAdminContractCategory.contains("${item.id}"))!>
					"${item.id}": "${item.name}",
				</#if>
			</#list>
		};
		window.focusList = {
			<#list rootContractCagetoryList as item>
				<#if (loginAdmin.allAdminContractCategory.contains("${item.id}"))!>
					"${item.id}": {
						"id": "${item.id}",
						"name": "${item.name}",
						"list": {
							<#list item.contractCategorySet as item1>
								<#if (loginAdmin.allAdminContractCategory.contains("${item1.id}"))!>
									"contract!list.action?contractCategoryId=${item1.id}": {
										"id": "${item1.id}",
										"name": "${item1.name}",
										"url": "contract!list.action?contractCategoryId=${item1.id}"
									},
								</#if>
							</#list>
							<#list item.projectCategorySet as item1>
								<#if (loginAdmin.allAdminProjectCategory.contains("${item1.id}"))!>
									"project!projectPlanList.action?id=${item1.id}": {
										"id": "${item1.id}",
										"name": "${item1.name}",
										"url": "project!projectPlanList.action?id=${item1.id}"
									},
								</#if>
							</#list>

							
						}
					},
				</#if>
			</#list>
      		<@sec.authorize ifAnyGranted="ROLE_CONTENT_ARTICLE">
				"article!list.action": {
					"id": "",
					"name": "内容管理",
					"list": {
						"article!list.action": {
							"id": "",
							"name": "内容管理",
							"url": "article!list.action"
						}
					}	
				},
      		</@sec.authorize>
      		
     		<@sec.authorize ifAnyGranted="ROLE_BASE_TYPE_ONE,ROLE_BASE_TYPE_TWO,ROLE_BASE_TYPE_PROJECT_LIST">
				"base_data": {
					"id": "base_data",
					"name": "基础数据",
					"list": {
					  <@sec.authorize ifAllGranted="ROLE_BASE_TYPE_ONE">
						"root_contract_category!list.action": {
							"id": "",
							"name": "一级分类",
							"url": "root_contract_category!list.action"
						},
					  </@sec.authorize>
					  <@sec.authorize ifAllGranted="ROLE_BASE_TYPE_TWO">
						"contract_category!list.action": {
							"id": "",
							"name": "二级级分类",
							"url": "contract_category!list.action"
						},
					  </@sec.authorize>
					  <@sec.authorize ifAllGranted="ROLE_BASE_TYPE_PROJECT_LIST">
						"project!list.action?hasNoRoot=0": {
							"id": "",
							"name": "项目列表",
							"url": "project!list.action?hasNoRoot=0"
						}
					  </@sec.authorize>
					}	
				},
  			</@sec.authorize>
  			
      		<@sec.authorize ifAnyGranted="ROLE_LOG_SEARCH,ROLE_LOG_SYSYEM_MESSAGE">
				"log_view": {
					"id": "log_view",
					"name": "操作日志",
					"list": {
	               <!--  <@sec.authorize ifAllGranted="ROLE_LOG_SEARCH">
						"log!list.action": {
							"id": "",
							"name": "查看日志",
							"url": "log!list.action"
						},
	                 </@sec.authorize>-->
	                <@sec.authorize ifAllGranted="ROLE_LOG_SYSYEM_MESSAGE">
	 					"systemmessage!list.action": {
							"id": "",
							"name": "系统消息",
							"url": "systemmessage!list.action"
						},
	                 </@sec.authorize>
					}	
				},
                 
      		</@sec.authorize>
  			
      		<@sec.authorize ifAnyGranted="ROLE_INTERIOR_COLLECT,ROLE_INTERIOR_ATTENDANCE">
				"inner_management": {
					"id": "inner_management",
					"name": "内部管理",
					"list": {
		                <@sec.authorize ifAllGranted="ROLE_INTERIOR_COLLECT">
						"product_daily_record!list.action?sort=productName&order=asc": {
							"id": "",
							"name": "产品每日汇总列表",
							"url": "product_daily_record!list.action?sort=productName&order=asc"
						},
		                </@sec.authorize>
					}	
				},
      		</@sec.authorize>
      		
      		<@sec.authorize  ifAnyGranted="ROLE_PROJECT_MY_PROJECT" >
				"project_management": {
					"id": "project_management",
					"name": "项目管理",
					"list": {
		               <@sec.authorize  ifAllGranted="ROLE_PROJECT_MY_PROJECT"  > 
						"project!list.action?hasNoRoot=1": {
							"id": "",
							"name": "项目管理",
							"url": "project!list.action?hasNoRoot=1"
						},
		               </@sec.authorize>
					}	
				},
            </@sec.authorize>
      		
            <@sec.authorize  ifAnyGranted="ROLE_WORKFLOW_EXTERNAL_REQUIREMENT,ROLE_WORKFLOW_INTERNAL_REQUIREMENT,ROLE_WORKFLOW_REQUIREMENT_DEVELOPMENT,ROLE_WORKFLOW_DEVELOPMENT_DEPLOY,ROLE_WORKFLOW_DEVELOPMENT_URGENT_DEPLOY,ROLE_WORKFLOW_DEVELOPMENT_ONLINE,ROLE_WORKFLOW_REQUIREMENT_MYWORK" >
				"requirement_management": {
					"id": "requirement_management",
					"name": "需求开发管理",
					"list": { 
						<@sec.authorize  ifAllGranted="ROLE_WORKFLOW_REQUIREMENT_MYWORK">
							"check!getWorkCheck.action": {
								"id": "",
								"name": "需求开发审批",
								"url": "check!getWorkCheck.action"
							},
						</@sec.authorize>
					}	
				},
            </@sec.authorize>  
              
      		<@sec.authorize ifAnyGranted="ROLE_WORKFLOW_DEFINITION,ROLE_WORK_CREATE,ROLE_WORKFLOW_MYWORK,ROLE_WORKFLOW_WORKAUDIT,ROLE_WORKFLOW_WORKSEARCH,ROLE_WORKFLOW_MYPROCESSWORK">
				"workflow": {
					"id": "workflow",
					"name": "工作流",
					"list": {
		                <@sec.authorize  ifAllGranted="ROLE_WORKFLOW_MYWORK" >
						"getAjaxWorkList.action?statu=1&page=1": {
							"id": "",
							"name": "我的工作",
							"url": "getAjaxWorkList.action"
						},
		                </@sec.authorize>
		                <@sec.authorize  ifAllGranted="ROLE_WORKFLOW_WORKAUDIT" >
						"work!getWorkCheck.action": {
							"id": "",
							"name": "工作审批",
							"url": "work!getWorkCheck.action"
						},
		                </@sec.authorize>
		                <!-- <@sec.authorize  ifAllGranted="ROLE_WORKFLOW_WORKSEARCH" >
						"work!getWorkQuery.action": {
							"id": "",
							"name": "工作查询",
							"url": "work!getWorkQuery.action"
						},
		                </@sec.authorize>-->
		                <@sec.authorize  ifAllGranted="ROLE_WORKFLOW_MYPROCESSWORK" >
						"work!getMyProcessWork.action": {
							"id": "",
							"name": "进展中的工作",
							"url": "work!getMyProcessWork.action"
						},
		                </@sec.authorize>
					}	
				},
      		</@sec.authorize>
  			
		};
	</script>
	<script type="text/javascript" src="/rzrk/js/background/item_index.js"></script>
</head>
<body>
	<div style="padding: 0 5px;">
		<a href="javascript:" class="easyui-linkbutton addFocus" data-options="iconCls:'icon-add'">添加 / 编辑关注</a>
	</div>
	<ul class="sortable grid"></ul>
</body>
</html>