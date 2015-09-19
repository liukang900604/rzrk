<#assign sec=JspTaglibs["/WEB-INF/tld/security.tld"] />
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta content="IE=edge,chrome=1" http-equiv="X-UA-Compatible">
    <meta http-equiv="Cache-control" content="no-store">
    <title>文章管理 </title>
    <link rel="stylesheet" href="/rzrk/js/jquery-easyui-1.4.1/themes/icon.css" />
    <link rel="stylesheet" href="/rzrk/js/jquery-easyui-1.4.1/themes/metro/easyui.css" id="themes"/>
    <link rel="stylesheet" href="/rzrk/css/background/common.css" />
    <link rel="stylesheet" href="/rzrk/css/background/article_list.css" />
    <script type="text/javascript" src="/rzrk/js/alljs.js"></script>
    <script type="text/javascript" src="/rzrk/js/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
    <script type="text/javascript">
    	window.statusData = <#if statuArray??>${statuArray}<#else>{}</#if>;
       window.departmentList = 
    	<#if deparmentList??>
    	
			${deparmentList}
    	
   		<#else>
			{}
		</#if>
    	;
    	
    	window.deparmentAdminList = 
    	<#if deparmentAdminList??>
			${deparmentAdminList}
    	
   		<#else>
			{}
		</#if>
    	;
    	window.showAll = 
    	<#if showAll??>
			${showAll}
    	
   		<#else>
			""
		</#if>
    	;
    </script>
    <script type="text/javascript" src="/rzrk/js/background/user_plan_list.js"></script>
     <script type="text/javascript" src="/rzrk/js/background/libs/selectMembers.js"></script>
</head>
<body onselectstart="return false">
	<div class="searchBar">
	<#if !(showAll?? && showAll != "")>
		<a id="addPlan" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'">添加计划</a>
		<a id="delPlans" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove'">批量删除</a>
	
		&nbsp;&nbsp;&nbsp;&nbsp;
	</#if>
        <label for="nameQuery">计划名称：
            <input id="nameQuery" name="nameQuery" class="queryText" data-options="prompt:'请输入搜索关键字',searcher:doSearch" ></input>
        </label>
        <label for="creatorQuery" id="creatorQueryPerson">创建人：
            <input type="text" name="adminListText" id="creatorQuery" class="adminList" value=""/>
        </label>
        <label for="adminQuery" id="adminQueryPerson" >处理人：
           <input type="text" name="adminListText" id="adminQuery" class="adminList" value=""/>
        </label>
        <label for="statusQuery">状态：
            <select id="statusQuery" name="statusQuery" >
                <option value="">请选择</option>
                <option>新建</option>
                <option>开发中</option>
                <option>反馈</option>
                <option>认可</option>
                <option>已确认</option>
                <option>已解决</option>
                <option>已完成</option>
                <option>已关闭</option>
                <option>测试中</option>
            </select>
        </label>
        <!--
		<select id="searchBy" name="searchBy" style="width:80px;"></select>
		<input id="keyword" class="easyui-searchbox" data-options="prompt:'请输入搜索关键字',searcher:doSearch" style="width:300px"></input>
		-->
		<a id="search" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'">搜索</a>
	</div>
    <table id="table"></table>
</body>
</html>