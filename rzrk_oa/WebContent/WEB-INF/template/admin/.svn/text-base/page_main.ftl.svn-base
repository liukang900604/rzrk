<#assign sec=JspTaglibs["/WEB-INF/tld/security.tld"] />
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta http-equiv="x-ua-compatible" content="ie-edge,chrome=1">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <meta http-equiv="Cache-control" content="no-store">
    <title>睿智融科BEST企业管理系统</title>
    <link rel="stylesheet" href="/rzrk/js/jquery-easyui-1.4.1/themes/icon.css" />
    <link rel="stylesheet" href="/rzrk/js/jquery-easyui-1.4.1/themes/metro/easyui.css" id="themes"/>
    <link rel="stylesheet" href="/rzrk/css/mCustomScrollbar/jquery.mCustomScrollbar.css" />
    <link rel="stylesheet" href="/rzrk/css/background/index.css"/>
    <script type="text/javascript" src="/rzrk/js/alljs.js"></script>
    <script type="text/javascript" src="/rzrk/js/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="/rzrk/js/background/index.js"></script>
</head>
<body>
    <div class="nav">
		<div class="overlay"></div>
        <div class="logo"></div>
        <nav class="main-menu">
            <ul>
                <li><a href="javascript:"><i class="icon i-1"></i>个人中心<i class="icon arrows"></i></a>
                     <ul class="sub-menu">
                        <li><a href="javascript:" data-src='query_history!list.action'><i class="icon i-1"></i>我的查询</a></li>
                        <li><a href="javascript:" data-src='viewlayer!list.action'><i class="icon i-1"></i>我的新表</a></li>
                        <li><a href="javascript:" data-src='index_navigator_item!index.action'><i class="icon i-1"></i>首页导航</a></li>
                        <li><a href="javascript:" data-src='user_plan!list.action'><i class="icon i-1"></i>我的计划</a></li>
                        <li><a href="javascript:" data-src='user_plan!follow.action'><i class="icon i-1"></i>我的关注</a></li>
                        <li><a href="javascript:" data-src='pass_change!list.action'><i class="icon i-1"></i>密码修改</a></li>
                        <li><a href="javascript:" data-src='work!getMyWork.action'><i class="icon i-1"></i>我的工作</a></li>
                        <li><a href="javascript:" data-src='work!getWorkCheck.action'><i class="icon i-1"></i>工作审批</a></li>
                        <li><a href="javascript:" data-src='deparment!getPersonList.action'><i class="icon i-1"></i>我的部门</a></li>
                        <li><a href="javascript:" data-src='user_plan!getAllList.action'><i class="icon i-1"></i>计划查询</a></li>
                    </ul>
                </li>
                
          		<@sec.authorize ifAnyGranted="ROLE_CONTENT_ARTICLE">
                <li><a href="javascript:"><i class="icon i-2"></i>内容管理<i class="icon arrows"></i></a>
                    <ul class="sub-menu">
                        <@sec.authorize ifAllGranted="ROLE_CONTENT_ARTICLE"><li><a href="javascript:" data-src='article!list.action'><i class="icon i-2"></i>文章管理</a></li></@sec.authorize>
                    </ul>
                </li>
          		</@sec.authorize>
          		
	     		<@sec.authorize ifAnyGranted="ROLE_BASE_TYPE_ONE,ROLE_BASE_TYPE_TWO,ROLE_BASE_TYPE_PROJECT_LIST">
                <li><a href="javascript:"><i class="icon i-3"></i>基础分类数据<i class="icon arrows"></i></a>
                    <ul class="sub-menu">
                        <@sec.authorize ifAllGranted="ROLE_BASE_TYPE_ONE"><li><a href="javascript:" data-src='root_contract_category!list.action'><i class="icon i-3"></i>一级分类</a></li></@sec.authorize>
                        <@sec.authorize ifAllGranted="ROLE_BASE_TYPE_TWO"><li><a href="javascript:" data-src='contract_category!list.action'><i class="icon i-3"></i>二级分类</a></li></@sec.authorize>
                        <@sec.authorize ifAllGranted="ROLE_BASE_TYPE_TWO"><li><a href="javascript:" data-src='union_contract_category!list.action'><i class="icon i-3"></i>二级分类联合表</a></li></@sec.authorize>
                        <@sec.authorize ifAllGranted="ROLE_BASE_TYPE_PROJECT_LIST"> <li><a href="javascript:" data-src='project!list.action?hasNoRoot=0'><i class="icon i-3"></i>项目列表</a></li></@sec.authorize>
                    </ul>
                </li>
          		</@sec.authorize>
          		
          		<@sec.authorize ifAnyGranted="ROLE_TRUST_PRODUCT,ROLE_TRUST_VALUE,ROLE_TRUST_CONFIRM_RECEIPT">  
                <li><a href="javascript:"><i class="icon i-4"></i>信托产品<i class="icon arrows"></i></a>
                    <ul class="sub-menu">
                        <@sec.authorize ifAllGranted="ROLE_TRUST_PRODUCT"><li><a href="javascript:" data-src='product!list.action'><i class="icon i-4"></i>产品管理</a></li></@sec.authorize>
                        <@sec.authorize ifAllGranted="ROLE_TRUST_VALUE"><li><a href="javascript:" data-src='productnetvalue!list.action'><i class="icon i-4"></i>净值管理</a></li></@sec.authorize>
                    </ul>
                </li>
          		</@sec.authorize>
          		
          		<@sec.authorize ifAnyGranted="ROLE_BASE_STOCK">
                <li><a href="javascript:"><i class="icon i-5"></i>基础数据<i class="icon arrows"></i></a>
                    <ul class="sub-menu">
                        <@sec.authorize ifAllGranted="ROLE_BASE_STOCK"><li><a href="javascript:" data-src='stockindex!list.action?stockId=1'><i class="icon i-5"></i>股指管理</a></li></@sec.authorize>
                    </ul>
                </li>
          		</@sec.authorize>
          		
          		<@sec.authorize ifAnyGranted="ROLE_CLIENT_PRODUCT">
                <li><a href="javascript:"><i class="icon i-6"></i>客户管理<i class="icon arrows"></i></a>
                    <ul class="sub-menu">
                        <@sec.authorize ifAllGranted="ROLE_CLIENT_PRODUCT"><li><a href="javascript:" data-src='member!list.action'><i class="icon i-6"></i>客户管理「产品」</a></li></@sec.authorize>
                    </ul>
                </li>
          		</@sec.authorize>
          		
          		<@sec.authorize ifAnyGranted="ROLE_MAKE_SUBSCRIBE">
                <li><a href="javascript:"><i class="icon i-7"></i>预约认购<i class="icon arrows"></i></a>
                    <ul class="sub-menu">
                        <@sec.authorize ifAllGranted="ROLE_MAKE_SUBSCRIBE"><li><a href="javascript:" data-src='reservations!list.action'><i class="icon i-7"></i>认购列表</a></li></@sec.authorize>
                    </ul>
                </li>
          		</@sec.authorize>
          		
          		<@sec.authorize ifAnyGranted="ROLE_HR_DEPINFO,ROLE_HR_POST,ROLE_HR_POSITION,ROLE_HR_ROLE,ROLE_HR_STAFF">
                <li><a href="javascript:"><i class="icon i-8"></i>HR管理<i class="icon arrows"></i></a>
                    <ul class="sub-menu">
                        <@sec.authorize ifAllGranted="ROLE_HR_DEPINFO"><li><a href="javascript:" data-src='deparment!list.action'><i class="icon i-8"></i>部门信息</a></li></@sec.authorize>
                        <@sec.authorize ifAllGranted="ROLE_HR_POST"><li><a href="javascript:" data-src='job!list.action'><i class="icon i-8"></i>岗位管理</a></li></@sec.authorize>
                        <@sec.authorize ifAllGranted="ROLE_HR_POSITION"><li><a href="javascript:" data-src='job_level!list.action'><i class="icon i-8"></i>职务级别</a></li></@sec.authorize>
                        <@sec.authorize ifAllGranted="ROLE_HR_ROLE"><li><a href="javascript:" data-src='role!list.action'><i class="icon i-8"></i>角色管理</a></li></@sec.authorize>
                        <@sec.authorize ifAllGranted="ROLE_HR_STAFF"><li><a href="javascript:" data-src='admin!listDep.action'><i class="icon i-8"></i>人员信息</a></li></@sec.authorize>
                    </ul>
                </li>
          		</@sec.authorize>
          		
          		<@sec.authorize ifAnyGranted="ROLE_LOG_SEARCH,ROLE_LOG_SYSYEM_MESSAGE">
                <li><a href="javascript:"><i class="icon i-9"></i>操作日志<i class="icon arrows"></i></a>
                    <ul class="sub-menu">
                        <@sec.authorize ifAllGranted="ROLE_LOG_SEARCH"><li><a href="javascript:" data-src='log!list.action'><i class="icon i-9"></i>查看日志</a></li></@sec.authorize>
                        <@sec.authorize ifAllGranted="ROLE_LOG_SYSYEM_MESSAGE"><li><a href="javascript:" data-src='systemmessage!list.action'><i class="icon i-9"></i>系统消息</a></li></@sec.authorize>
                    </ul>
                </li>
          		</@sec.authorize>
          		
          		<@sec.authorize ifAnyGranted="ROLE_INTERIOR_COLLECT,ROLE_INTERIOR_ATTENDANCE">
                <li><a href="javascript:"><i class="icon i-10"></i>内部管理<i class="icon arrows"></i></a>
                    <ul class="sub-menu">
                        <@sec.authorize ifAllGranted="ROLE_INTERIOR_COLLECT"><li><a href="javascript:" data-src='product_daily_record!list.action?sort=productName&order=asc'><i class="icon i-11"></i>产品每日汇总列表</a></li></@sec.authorize>
                        <@sec.authorize ifAllGranted="ROLE_INTERIOR_ATTENDANCE"><li><a href="javascript:" data-src='checkin!checkinList.action'><i class="icon i-11"></i>考勤记录</a></li></@sec.authorize>
                    </ul>
                </li>
          		</@sec.authorize>
          		
          		<@sec.authorize  ifAnyGranted="ROLE_PROJECT_MY_PROJECT" >
                <li><a href="javascript:"><i class="icon i-11"></i>项目管理<i class="icon arrows"></i></a>
                    <ul class="sub-menu">
                       <@sec.authorize  ifAllGranted="ROLE_PROJECT_MY_PROJECT"  > <li><a href="javascript:" data-src='project!list.action?hasNoRoot=1'><i class="icon i-11"></i>项目管理</a></li></@sec.authorize>
                    </ul>
                </li>
                </@sec.authorize>
          		
                <@sec.authorize  ifAnyGranted="ROLE_WORKFLOW_EXTERNAL_REQUIREMENT,ROLE_WORKFLOW_INTERNAL_REQUIREMENT,ROLE_WORKFLOW_REQUIREMENT_DEVELOPMENT,ROLE_WORKFLOW_DEVELOPMENT_DEPLOY,ROLE_WORKFLOW_DEVELOPMENT_URGENT_DEPLOY,ROLE_WORKFLOW_DEVELOPMENT_ONLINE,ROLE_WORKFLOW_REQUIREMENT_MYWORK" > 
                <li><a href="javascript:"><i class="icon i-12"></i>需求开发管理<i class="icon arrows"></i></a>
                    <ul class="sub-menu">
						<@sec.authorize  ifAllGranted="ROLE_WORKFLOW_EXTERNAL_REQUIREMENT"><li><a href="javascript:" data-src='check!addExternalRequestWork.action'><i class="icon i-12"></i>外部需求审批</a></li></@sec.authorize>
						<@sec.authorize  ifAllGranted="ROLE_WORKFLOW_INTERNAL_REQUIREMENT"><li><a href="javascript:" data-src='check!addInsideRequestWork.action'><i class="icon i-12"></i>内部需求审批</a></li></@sec.authorize>
						<@sec.authorize  ifAllGranted="ROLE_WORKFLOW_REQUIREMENT_DEVELOPMENT"><li><a href="javascript:" data-src='check!addDevelopmentWork.action'><i class="icon i-12"></i>提交开发审批</a></li></@sec.authorize>
						<@sec.authorize  ifAllGranted="ROLE_WORKFLOW_DEVELOPMENT_DEPLOY"><li><a href="javascript:" data-src='check!addDeploySubmitWork.action'><i class="icon i-12"></i>部署提交审批</a></li></@sec.authorize>
						<@sec.authorize  ifAllGranted="ROLE_WORKFLOW_DEVELOPMENT_URGENT_DEPLOY"><li><a href="javascript:" data-src='check!addUrgentDeploySubmitWork.action'><i class="icon i-12"></i>紧急部署提交审批</a></li></@sec.authorize>
						<@sec.authorize  ifAllGranted="ROLE_WORKFLOW_DEVELOPMENT_ONLINE"><li><a href="javascript:" data-src='check!addOnlinBugSubmitWork.action'><i class="icon i-12"></i>线上bug提交审批</a></li></@sec.authorize>
						<@sec.authorize  ifAllGranted="ROLE_WORKFLOW_REQUIREMENT_MYWORK"><li><a href="javascript:" data-src='check!getWorkCheck.action'><i class="icon i-12"></i>我的审批</a></li></@sec.authorize>
                    </ul>
                </li>
                </@sec.authorize>  
                  
          		<@sec.authorize ifAnyGranted="ROLE_WORKFLOW_DEFINITION,ROLE_WORK_CREATE,ROLE_WORKFLOW_MYWORK,ROLE_WORKFLOW_WORKAUDIT,ROLE_WORKFLOW_WORKSEARCH,ROLE_WORKFLOW_MYPROCESSWORK">
                <li><a href="javascript:"><i class="icon i-13"></i>工作流<i class="icon arrows"></i></a>
                    <ul class="sub-menu">
                        <@sec.authorize  ifAllGranted="ROLE_WORKFLOW_DEFINITION" >  <li><a href="javascript:" data-src='work_flow!workFlowdeFine.action'><i class="icon i-13"></i>工作流定义</a></li></@sec.authorize>
                        <@sec.authorize  ifAllGranted="ROLE_WORK_CREATE" ><li><a href="javascript:" data-src='work!addWork.action'><i class="icon i-13"></i>工作创建</a></li></@sec.authorize>
                        <@sec.authorize  ifAllGranted="ROLE_WORKFLOW_MYWORK" ><li><a href="javascript:" data-src='work!getMyWork.action'><i class="icon i-13"></i>我的工作</a></li></@sec.authorize>
                        <@sec.authorize  ifAllGranted="ROLE_WORKFLOW_WORKAUDIT" ><li><a href="javascript:" data-src='work!getWorkCheck.action'><i class="icon i-13"></i>工作审批</a></li></@sec.authorize>
                        <@sec.authorize  ifAllGranted="ROLE_WORKFLOW_WORKSEARCH" ><li><a href="javascript:" data-src='work!getWorkQuery.action'><i class="icon i-13"></i>工作查询</a></li></@sec.authorize>
                        <@sec.authorize  ifAllGranted="ROLE_WORKFLOW_MYPROCESSWORK" ><li><a href="javascript:" data-src='work!getMyProcessWork.action'><i class="icon i-13"></i>进展中的工作</a></li></@sec.authorize>
                    </ul>
                </li>
          		</@sec.authorize>
                
                <#list rootContractCagetoryList as item>
                	<#if (loginAdmin.allAdminContractCategory.contains("${item.id}"))!> 
		                <li id="${item.id}"><a href="javascript:"><i class="icon i-14"></i>${item.name}<i class="icon arrows"></i></a>
		                	<ul class="sub-menu">
								<#list item.contractCategorySet as item1>
									<#if (!item1.recyle) && (loginAdmin.allAdminContractCategory.contains("${item1.id}"))>
					                	<#if item1.isUrlView == 0>
		                        			<li><a href="javascript:" id="${item1.id}" data-src='contract!list.action?contractCategoryId=${item1.id}'><i class="icon i-14"></i>${item1.name}</a></li>
									    <#else>
		                        			<li><a href="javascript:" id="${item1.id}" data-src='${item1.url}'><i class="icon i-14"></i>${item1.name}${item.isUrlView}</a></li>
									    </#if>
									</#if>
								</#list>
								<#list item.projectCategorySet as item1>
									<#if (loginAdmin.allAdminProjectCategory.contains("${item1.id}"))!>
		                        		<li><a href="javascript:" data-src='project!projectPlanList.action?id=${item1.id}&projectName=${item1.name}'><i class="icon i-14"></i>${item1.name}</a></li>
									</#if>
								</#list>
							</ul>
		                </li>
                	</#if>
                </#list>
            </ul>
        </nav>
    </div>
    <div class="container">
        <header>
            <ul class="tools">
                <li>您好，<em><@sec.authentication property="name" /></em></li>
                <li><a href="javascript:" class="t-1"></a></li>
                <li><a href="javascript:" class="t-2"></a></li>
                <li><a href="javascript:" class="t-3"></a></li>
                <li><a href="javascript:" class="t-4 sysmsg"></a></li>
                <li><a href="javascript:" class="t-5 logout"></a></li>
            </ul>
            <#if (count > 0)><span class="sysmsg"><#if (count > 99)>99<#else>${count}</#if></span></#if>
            <a href="javascript:" class="add-tools"></a>
        </header>
        <div class="body">
	        <div class="easyui-tabs" data-options="fit:true,border:false,height:30">
	            <div title="首页导航" data-options="closable:true">
	            	<iframe scrolling="yes" frameborder="0" src="index_navigator_item!index.action" style="width:100%;height:99%;"></iframe>
	            </div>
	        </div>
        </div>
    </div>
</body>
</html>