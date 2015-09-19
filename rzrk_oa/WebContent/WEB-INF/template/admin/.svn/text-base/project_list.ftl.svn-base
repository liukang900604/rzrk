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
    <link rel="stylesheet" href="/rzrk/css/background/poshytip-1.2/tip-yellowsimple/tip-yellowsimple.css" />
    <link rel="stylesheet" href="/rzrk/css/background/common.css" />
    <link rel="stylesheet" href="/rzrk/css/background/article_list.css" />
    <script type="text/javascript" src="/rzrk/js/alljs.js"></script>
    <script type="text/javascript" src="/rzrk/js/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="/rzrk/js/jquery-easyui-1.4.1/plugins/jquery.easyui.dragcolumns.js"></script>
    <script type="text/javascript" src="/rzrk/js/jquery-easyui-1.4.1/plugins/jquery.cookie.js"></script>
        
    <script type="text/javascript">
		window.hasNoRoot = "${hasNoRoot}";
		window.statusData = <#if statuArray??>${statuArray}<#else>{}</#if>;
		
	</script>
    <script type="text/javascript" src="/rzrk/js/background/project_list.js"></script>

</head>
<body onselectstart="return false">
	<div class="searchBar">
		<a id="addDeparment" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'">添加项目</a>
		<a id="addExteralRequest" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'">外部需求审批</a>
		<a id="addInsideRequest" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'">内部需求审批</a>
		<a id="addDevelopment" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'">提交开发审批</a>
		<a id="addOnlineBug" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'">线上bug提交审批</a>
		<!-- <a id="delDeparments" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove'">批量删除</a> -->
		&nbsp;&nbsp;&nbsp;&nbsp;
        <label for="nameQuery">项目名称：
            <input id="nameQuery" name="nameQuery" class="queryText" data-options="prompt:'请输入搜索关键字',searcher:doSearch" ></input>
        </label>
        <label for="creatorQuery">创建人：
            <input id="creatorQuery" name="creatorQuery" class="queryText" data-options="prompt:'请输入搜索关键字',searcher:doSearch" ></input>
        </label>
        <label for="responsiborQuery">负责人：
            <input id="responsiborQuery" name="responsiborQuery" class="queryText" data-options="prompt:'请输入搜索关键字',searcher:doSearch" ></input>
        </label>
        <label for="statusQuery">状态：
            <select id="statusQuery" name="statusQuery" >
                <option value="">请选择</option>
                <option>已完成</option>
                <option>未完成</option>
                <option>已作废</option>
                <option>测试中</option>
            </select>
        </label>
		<!--
		<select id="searchBy" name="searchBy" style="width:80px;"></select>
		<input id="keyword" class="easyui-searchbox" data-options="prompt:'请输入搜索关键字',searcher:doSearch" style="width:300px"></input>
		-->
		<a id="search" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'">搜索</a>
		&nbsp;&nbsp;&nbsp;&nbsp;
	</div>
    <table id="table"></table>
</body>
</html>