<#assign sec=JspTaglibs["/WEB-INF/tld/security.tld"] />
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta content="IE=edge,chrome=1" http-equiv="X-UA-Compatible">
    <meta http-equiv="Cache-control" content="no-store">
    <title>产品管理 </title>
    <link rel="stylesheet" href="/rzrk/js/jquery-easyui-1.4.1/themes/icon.css" />
    <link rel="stylesheet" href="/rzrk/js/jquery-easyui-1.4.1/themes/metro/easyui.css" id="themes"/>
    <link rel="stylesheet" href="/rzrk/css/background/common.css" />
    <link rel="stylesheet" href="/rzrk/css/background/file_upload.css" />
    <link rel="stylesheet" href="/rzrk/css/background/product_list.css" />
    <script type="text/javascript" src="/rzrk/js/alljs.js"></script>
    <script type="text/javascript" src="/rzrk/js/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="/rzrk/js/background/index.js"></script>
    <script type="text/javascript" src="/rzrk/js/background/ajaxfileupload.js"></script>
    <script type="text/javascript" src="/rzrk/js/background/product_list.js"></script>
</head>
<body>
	<div class="searchBar">
        <a id="downloadProduct" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'">导出记录</a>
        <a id="downloadProductTemplate" href="/html/excel/%e4%ba%a7%e5%93%81%e6%a8%a1%e6%9d%bf.xls" class="easyui-linkbutton" data-options="iconCls:'icon-save'">下载模板</a>
        <a href="#" class="easyui-linkbutton importRecords" data-options="iconCls:'icon-add'">导入记录</a>
        <input id="uploadProduct" type="file" name="uploadProduct" data-url='/admin/product!uploadProduct.action' class="fileUpload" />
		<a id="addProduct" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'">添加产品</a>
		<a id="delProducts" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove'">批量删除</a>
		&nbsp;&nbsp;&nbsp;&nbsp;
		<label for="searchBy">查询：</label>
		<select id="searchBy" class="easyui-combobox" name="searchBy" style="width:120px;">
			<option value="name">产品全称</option>
			<option value="custodianBank">保管银行</option>
		</select>
		&nbsp;
		<label for="isCunxu">续存：</label>
		<select id="isCunxu" class="easyui-combobox" name="isCunxu" style="width:80px;">
			<option value="">全部</option>
			<option value="1">是</option>
			<option value="0">否</option>
		</select>
		<input id="keyword" class="easyui-searchbox" data-options="prompt:'请输入搜索关键字',searcher:doSearch" style="width:300px"></input>
	</div>
    <table id="table"></table>
    <form id="downloadForm" action="/admin/product!downloadProduct.action"  method="get" style="display:none;">
    <input type="hidden" name="searchBy" />
    <input type="hidden" name="keyword" />
    <input type="hidden" name="isCunxu" />
    </form>
</body>
</html>