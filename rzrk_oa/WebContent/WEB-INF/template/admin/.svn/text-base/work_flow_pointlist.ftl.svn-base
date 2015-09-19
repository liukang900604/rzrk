<#assign sec=JspTaglibs["/WEB-INF/tld/security.tld"] />
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta content="IE=edge,chrome=1" http-equiv="X-UA-Compatible">
    <meta http-equiv="Cache-control" content="no-store">
    <title>工作流类型</title>
    <link rel="stylesheet" href="/rzrk/js/jquery-easyui-1.4.1/themes/icon.css" />
    <link rel="stylesheet" href="/rzrk/js/jquery-easyui-1.4.1/themes/metro/easyui.css" id="themes"/>
    <link rel="stylesheet" href="/rzrk/css/background/common.css" />
    <link rel="stylesheet" href="/rzrk/css/background/file_upload.css" />
    <script type="text/javascript" src="/rzrk/js/alljs.js"></script>
    <script type="text/javascript" src="/rzrk/js/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="/rzrk/js/background/index.js"></script>
    <script type="text/javascript" src="/rzrk/js/background/ajaxfileupload.js"></script>
    <script type="text/javascript" src="/rzrk/js/background/workFlowpoint_list.js"></script>
    <script type="text/javascript">
    </script>
    
</head>
<body>
	<div class="searchBar">
		<a id="addWorkFlowPoint" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'">新增节点</a>
		&nbsp;&nbsp;&nbsp;&nbsp;
		<label for="searchBy">查询：</label>
		<select id="searchBy" class="easyui-combobox" name="searchBy" style="width:80px;">
			<option>节点名称</option>
		</select>
		<input id="keyword" class="easyui-searchbox" data-options="prompt:'请输入搜索关键字',searcher:doSearch" style="width:300px"></input>
		&nbsp;&nbsp;&nbsp;&nbsp;
	</div>
	<input type="hidden" id="workId" value="${workId}"/>
    <table id="table"></table>
    <div class="bottom" style="padding: 10px 0 0 0;">
    	<a id="createWorkflow" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'">新建流程</a>
    </div>
</body>
</html>