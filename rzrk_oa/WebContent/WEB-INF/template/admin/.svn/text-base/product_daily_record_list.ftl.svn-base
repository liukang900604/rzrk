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
    <link rel="stylesheet" href="/rzrk/css/background/product_daily_record_list.css" />
    <script type="text/javascript" src="/rzrk/js/alljs.js"></script>
    <script type="text/javascript" src="/rzrk/js/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="/rzrk/js/background/index.js"></script>
    <script type="text/javascript" src="/rzrk/js/background/ajaxfileupload.js"></script>
    <script type="text/javascript" src="/rzrk/js/background/product_daily_record_list.js"></script>
</head>
<body>
	<div class="searchBar">
	    <a id="export" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'">净值导出</a>
		<a id="addNetValue" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'">添加净值</a>
		<a id="history" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'">上传历史数据</a>
		<input id="historyUpload" type="file" name="historyUpload" data-url='upload!historyExcelUpload.action' class="fileUpload">
		<a id="xuntou" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'">上传迅投数据</a>
		<input id="xuntouUpload" type="file" name="xuntouUpload" data-url='upload!xuntouExcelUpload.action' class="fileUpload">
		&nbsp;&nbsp;&nbsp;&nbsp;
		<label for="searchBy">查询：</label>
		<select id="searchBy" name="searchBy" style="width:80px;"></select>
		<#--
		<input id="recordDate" type="text" class="easyui-datebox" data-options="formatter: dateFormatter, parser: dateParser, editable:false" style="width:120px;">
		-->
		<input id="keyword" style="width:300px"></input>
		<a id="search" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'">搜索</a>
	</div>
    <table id="table"></table>
</body>
</html>