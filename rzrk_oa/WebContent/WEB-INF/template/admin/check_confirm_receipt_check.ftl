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
    <link rel="stylesheet" href="/rzrk/css/background/work_flow_myWork.css" />
    <script type="text/javascript" src="/rzrk/js/alljs.js"></script>
    <script type="text/javascript" src="/rzrk/js/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="/rzrk/js/background/index.js"></script>
    <script type="text/javascript" src="/rzrk/js/background/ajaxfileupload.js"></script>
    <script type="text/javascript" src="/rzrk/js/background/check_confirmReceipt_check.js"></script>
</head>
<body>
<div class="searchBar">
		<div class="row">
			<label for="status">当前状态：</label>
			<select id="status" name="status" class="easyui-combobox" style="width:80px;" data-options="editable:false">
				<option value="0">　</option>
				<option value="1">取消</option>
				<option value="2">正常结束</option>
				<option value="3">正在办理</option>
				<option value="4">被撤回</option>
			</select>
			&nbsp;&nbsp;&nbsp;&nbsp;
			<select id="dateStatus" name="dateStatus" class="easyui-combobox" style="width:80px;" data-options="editable:false">
				<option value="0">发起时间</option>
				<option value="1">最后审批时间</option>
			</select>
			<input id="beginDate" name="beginDate" type="text" class="easyui-datebox" data-options="formatter: dateFormatter, parser: dateParser, editable:false" style="width:120px;">
			&nbsp;至&nbsp;
			<input id="endDate" name="endDate" type="text" class="easyui-datebox" data-options="formatter: dateFormatter, parser: dateParser, editable:false" style="width:120px;">
			&nbsp;&nbsp;&nbsp;&nbsp;
			<select id="queryType" name="queryType" class="easyui-combobox" style="width:140px;" data-options="editable:false">
				<option value="0">按工作名称查询</option>
				<option value="1">按发起人查询</option>
				<option value="2">按内容查询</option>
				<option value="3">按最后审批人查询</option>
			</select>
			<input id="queryText" name="queryText" class="easyui-textbox" type="text" name="name" />
			<a id="search" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
		</div>
		<div class="row letters">
			<a href="javascript:" letter="ALL" class="selected">所有</a>|
			<a href="javascript:" letter="A">A</a>|
			<a href="javascript:" letter="B">B</a>|
			<a href="javascript:" letter="C">C</a>|
			<a href="javascript:" letter="D">D</a>|
			<a href="javascript:" letter="E">E</a>|
			<a href="javascript:" letter="F">F</a>|
			<a href="javascript:" letter="G">G</a>|
			<a href="javascript:" letter="H">H</a>|
			<a href="javascript:" letter="I">I</a>|
			<a href="javascript:" letter="j">J</a>|
			<a href="javascript:" letter="k">K</a>|
			<a href="javascript:" letter="L">L</a>|
			<a href="javascript:" letter="M">M</a>|
			<a href="javascript:" letter="N">N</a>|
			<a href="javascript:" letter="O">O</a>|
			<a href="javascript:" letter="P">P</a>|
			<a href="javascript:" letter="Q">Q</a>|
			<a href="javascript:" letter="R">R</a>|
			<a href="javascript:" letter="S">S</a>|
			<a href="javascript:" letter="T">T</a>|
			<a href="javascript:" letter="U">U</a>|
			<a href="javascript:" letter="V">V</a>|
			<a href="javascript:" letter="W">W</a>|
			<a href="javascript:" letter="X">X</a>|
			<a href="javascript:" letter="Y">Y</a>|
			<a href="javascript:" letter="Z">Z</a>
		</div>
		<#--
		<input id="recordDate" type="text" class="easyui-datebox" data-options="formatter: dateFormatter, parser: dateParser, editable:false" style="width:120px;">
		-->
	</div>
    <table id="table"></table>
</body>
</html>