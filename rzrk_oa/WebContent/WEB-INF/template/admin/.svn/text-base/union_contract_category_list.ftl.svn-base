<#assign sec=JspTaglibs["/WEB-INF/tld/security.tld"] />
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta content="IE=edge,chrome=1" http-equiv="X-UA-Compatible">
    <meta http-equiv="Cache-control" content="no-store">
    <title>分类 </title>
    <link rel="stylesheet" href="/rzrk/js/jquery-easyui-1.4.1/themes/icon.css" />
    <link rel="stylesheet" href="/rzrk/js/jquery-easyui-1.4.1/themes/metro/easyui.css" id="themes"/>
    <link rel="stylesheet" href="/rzrk/css/background/common.css" />
    <link rel="stylesheet" href="/rzrk/css/background/file_upload.css" />
    <link rel="stylesheet" href="/rzrk/css/background/article_list.css" />
    <link rel="stylesheet" href="/rzrk/css/selectPosition.css" />
    <script type="text/javascript" src="/rzrk/js/alljs.js"></script>
    <script type="text/javascript" src="/rzrk/js/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="/rzrk/js/background/index.js"></script>
    <script type="text/javascript" src="/rzrk/js/background/ajaxfileupload.js"></script>
    <script type="text/javascript" src="/rzrk/js/jquery-easyui-1.4.1/plugins/jquery.easyui.dragcolumns.js"></script>
    <script type="text/javascript" src="/rzrk/js/jquery-easyui-1.4.1/plugins/jquery.cookie.js"></script>
    <script type="text/javascript" src="/rzrk/js/background/union_contract_category_list.js"></script>
</head>
<body>
	<div class="searchBar">
		<a id="addContractCategory" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'">添加</a>
		<a id="delContractCategory" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove'">批量删除</a>
		<!-- <a id="uploadContractCategoryAndDataBtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'">导入二级分类(模板和数据)</a> 
		<input id="uploadContractCategoryAndData" type="file" name="updateContract" data-url='contract_upload!uploadContractCategoryAndData.action' class="fileUpload" />-->
		&nbsp;&nbsp;&nbsp;&nbsp;
		<label for="searchBy">查询：</label>
		<select id="searchBy" class="easyui-combobox" name="searchBy" style="width:80px;" data-options="editable:false">
			<option value="name">模板名</option>
		</select>
		<input id="keyword" class="easyui-searchbox" data-options="prompt:'请输入搜索关键字',searcher:doSearch" style="width:300px"></input>
		&nbsp;&nbsp;&nbsp;&nbsp;
	</div>
    <table id="table"></table>
    <!-- 编辑框 -->
	<div class="content" id="contractCategoryEdit" style="display:none;">
		<input type="hidden" class="modify" />
		<div class="list">
			<div class="l">
				<div class="select_list_half" style="overflow:auto;border:1px solid #778;">
					<div class="domTree">
					</div>					
				</div>
				<select class="lselect select_list_half" multiple="multiple" >
				</select>
			</div>
			<div class="l mv_btn">
				<input type="button" class="btnRight" value="右移" /><br/><br/>
				<input type="button" class="btnLeft" value="左移" />
			</div>
			<div class="l">
				<select class="rselect select_list" multiple="multiple">
				</select>					
			</div>
			<div class="l mv_btn">
				<input type="button" class="btnUp" value="上移" /><br/><br/>
				<input type="button" class="btnDown" value="下移" />
			</div>			
		</div>
	</div>	
	<!--------------------------------->		
    <form id="downloadTempForm" action=""  method="get" style="display:none;">
    <input type="hidden" name="id" />
    </form>
</body>
</html>