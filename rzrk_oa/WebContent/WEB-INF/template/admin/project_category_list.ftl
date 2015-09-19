<#assign sec=JspTaglibs["/WEB-INF/tld/security.tld"] />
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta content="IE=edge,chrome=1" http-equiv="X-UA-Compatible">
    <meta http-equiv="Cache-control" content="no-store">
    <title>查看日志 </title>
    <link rel="stylesheet" href="/rzrk/js/jquery-easyui-1.4.1/themes/icon.css" />
    <link rel="stylesheet" href="/rzrk/js/jquery-easyui-1.4.1/themes/metro/easyui.css" id="themes"/>
    <link rel="stylesheet" href="/rzrk/css/background/common.css" />
    <link rel="stylesheet" href="/rzrk/css/background/article_list.css" />
    <script type="text/javascript" src="/rzrk/js/alljs.js"></script>
    <script type="text/javascript" src="/rzrk/js/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="/rzrk/js/background/index.js"></script>
    <script type="text/javascript" src="/rzrk/js/background/project_category_list.js"></script> 
</head>
<body>
	<div class="searchBar">
		<a id="addProjectCategory"  href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'">添加项目模版</a>
		<a id="delProjectCategory" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove'">批量删除</a>
		
		&nbsp;&nbsp;&nbsp;&nbsp;
		<label for="searchBy">查询：</label>
		<select id="searchBy" class="easyui-combobox" name="searchBy" style="width:80px;" data-options="editable:false">
			<option value="name">模板名</option>
		</select>
		<input id="keyword" class="easyui-searchbox" data-options="prompt:'请输入搜索关键字',searcher:doSearch" style="width:300px"></input>
		&nbsp;&nbsp;&nbsp;&nbsp;
	</div>
    <table id="table"></table>
</body>
</html>