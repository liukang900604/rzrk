<#assign sec=JspTaglibs["/WEB-INF/tld/security.tld"] />
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta content="IE=edge,chrome=1" http-equiv="X-UA-Compatible">
    <meta http-equiv="Cache-control" content="no-store">
    <title>股指管理 </title>
    <link rel="stylesheet" href="/rzrk/js/jquery-easyui-1.4.1/themes/icon.css" />
    <link rel="stylesheet" href="/rzrk/js/jquery-easyui-1.4.1/themes/metro/easyui.css" id="themes"/>
    <link rel="stylesheet" href="/rzrk/css/background/common.css" />
    <link rel="stylesheet" href="/rzrk/css/background/stockindex_list.css" />
    <script type="text/javascript" src="/rzrk/js/alljs.js"></script>
    <script type="text/javascript" src="/rzrk/js/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="/rzrk/js/jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="/rzrk/js/background/index.js"></script>
    <script type="text/javascript" src="/rzrk/js/background/stockindex_list.js"></script>
</head>
<body>
	<div class="searchBar">
		<a id="addStockIndex" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'">添加股指</a>
		<a id="delStockIndexs" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove'">批量删除</a>
		&nbsp;&nbsp;&nbsp;&nbsp;
		<label for="createDate">日期：</label>
		<input id="createDate" type="text" class="easyui-datebox" data-options="formatter: dateFormatter, parser: dateParser, editable:false" style="width:120px;">
		&nbsp;&nbsp;
		<a id="search" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'">搜索</a>
	</div>
    <table id="table"></table>
</body>
</html>