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
    </script>
    <script type="text/javascript" src="/rzrk/js/background/user_plan_follow.js"></script>
</head>
<body onselectstart="return false">
	<div class="searchBar">
		<label for="searchBy">查询：</label>
		<select id="searchBy" name="searchBy" style="width:80px;"></select>
		<input id="keyword" class="easyui-searchbox" data-options="prompt:'请输入搜索关键字',searcher:doSearch" style="width:300px"></input>
		<a id="search" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'">搜索</a>
	</div>
    <table id="table"></table>
</body>
</html>