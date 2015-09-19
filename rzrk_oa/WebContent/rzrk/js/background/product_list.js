var iconBasePath = '/rzrk/js/jquery-easyui-1.4.1/themes/icons/';
var key = window.location.href;
var storage = window.localStorage;
var pager = {
    beforePageText: '当前第',
    afterPageText: '页，共 {pages} 页',
    displayMsg: '当前第 {from} 至 {to} 条数据，共 {total} 条数据',
    onChangePageSize: function(pageSize){
    	if (storage){
    		storage.setItem(key, pageSize);
    	}
    }
};
function operations(value, rowData, index) {
    var link = "<a href='#' data-id='{0}' data-idx='{1}' class='edit'>编辑</a>";
    return $.format(link, rowData.id, index);
}
function renderTop(value, rowData, index){
	return value ? "<span style='color:green;'>是</span>" : "<span style='color:red;'>否</span>";
}
function renderType(value, rowData, index){
	var types = {
		100: "开放型",
		101: "证券信托"
	};
	return types[value] ? types[value] : "";
}
function renderPurchaseState(value, rowData, index){
	var types = {
		1: "<span style='color:green;'>开放</span>",
		2: "<span style='color:red;'>封闭</span>"
	};
	return types[value] ? types[value] : "";
}
function renderStatus(value, rowData, index){
	return value ? "<span style='color:green;'>启用</span>" : "<span style='color:red;'>禁用</span>";
}
function getColumns() {
    var columns = [[
            {title: "", field: "isChecked", width: 80, align: 'center', checkbox:true},
            {title: "简称", field: "nameSShort", width: 400, align: 'center', sortable: true},
            {title: "排序", field: "weight", width: 80, align: 'center', sortable: true},
            {title: "是否推荐", field: "top", width: 80,  align: 'center', sortable: true, formatter: renderTop},
            {title: "是否续存", field: "isCunxu", width: 80, align: 'center', sortable: true, formatter: renderTop},
            {title: "是否有效", field: "isValid", width: 80, align: 'center', sortable: true, formatter: renderTop},
            {title: "信托类型", field: "type", width: 80, align: 'center', sortable: true, formatter: renderType},
            {title: "产品属性", field: "purchaseState", width: 80, align: 'center', sortable: true, formatter: renderPurchaseState},
            {title: "续存期", field: "duration", width: 80, align: 'center', sortable: true},
            {title: "保管银行", field: "custodianBank", width: 300, align: 'center', sortable: true},
            {title: "创建日期", field: "createDate", width: 160, align: 'center', sortable: true},
            {title: "状态", field: "isEnabled", width: 80, align: 'center', formatter: renderStatus},
            {title: "操作", field: "operation", width: 80, align: 'center', formatter: operations}
        ]];
    return columns;
}
function getDataGridHeight(){
	var searchBarHeight = $('.searchBar').outerHeight();
	var winHeight = $(window).outerHeight();
	return winHeight - searchBarHeight - 25;
}
function getToolbar(){
    var toolbar = [{
        text:'添加产品',
        iconCls:'icon-add',
        handler:function(){alert('add');}
    },{
        text:'批量删除',
        iconCls:'icon-remove',
        handler:function(){alert('remove');}
    }];
    return toolbar;
}
function getPageSize(){
	var pageSize = 50;
	var size = 0;
	if (storage){
		size = storage.getItem(key);
	}
	return size || pageSize;
}
function getConfig(){
    return {
        loadMsg: '正在获取数据......',
        url: '/admin/product!ajaxGetList.action',
        method: 'get',
        width: '100%',
        //toolbar:getToolbar(),
        rownumbers: true,
        height: getDataGridHeight(),
        singleSelect: false,
        selectOnCheck: true,
        checkOnSelect: true,
        autoRowHeight: false,
        remoteSort: true,
        showFooter: false,
        pagination: true,
        pageSize: getPageSize(),
        pageList: [50, 100, 500],
        pageNumber: 1,
        columns: getColumns(),
        data: []
    };
}
function doSearch(value){
    var table = $('#table');
    table.datagrid({
    	queryParams: {
    		searchBy: $('#searchBy').combobox("getValue"),
    		keyword: value,
    		isCunxu: $("#isCunxu").combobox("getValue")
    	}
    });
    $("#downloadForm").find("[name='searchBy']").val($('#searchBy').combobox("getValue"));
    $("#downloadForm").find("[name='keyword']").val(value);
    $("#downloadForm").find("[name='isCunxu']").val($('#searchBy').combobox("isCunxu"));
}

function ajaxFile(){
	
	var id = $("#uploadProduct").attr('id');
	var url = $("#uploadProduct").data('url');
	//url += "&userId="+str;
	$.messager.progress({
		"title":"",
		"msg":"导入中，请等待...",
		"text":""
	});
	$.ajaxFileUpload({
		url: url,
		secureuri:false,
		fileElementId:id,
		dataType: 'json',
		beforeSend:function(){
		},
		success: function (data, status){
            if(data.status == "success"){
				msg.alert("上传文件", "上传文件成功！", "info", function(){
    				window.top.reloadDataGridInTab('产品管理');
				});
             }else{
            	 var arr = data.message.split("\n",21)
            	 var _arr = arr.slice(0,20);
            	 var emsg = _arr.join("<br>");
            	 if(arr.length > 20){
            		 emsg += "<br>..."
            	 }
 				msg.alert("上传文件", "上传文件失败：<br>" + emsg, "error");
             }
			var inhtml = $("#uploadProduct").get(0).outerHTML;
			$("#uploadProduct").replaceWith(inhtml);
            $("#uploadProduct").unbind('change').change(ajaxFile);
            $.messager.progress('close');
		}
	});
}

function _initEvents(){
    $("#uploadProduct").change(ajaxFile);
    $("#downloadProduct").click(function(){
    	$("#downloadForm").submit();
    });
    var table = $('#table');
    $(".datagrid").on("click", ".edit", function () {
    	var url = "/admin/product!edit.action?id=";
    	var id = $(this).data("id");
        var ezWin = window.top.createWin({ title: "编辑产品" });
        var iframe = window.top.createIframe(url + id);
        iframe.appendTo(ezWin);
        ezWin.window("open");
    	return false;
    });
    $('#addProduct').unbind('click').click(function(){
    	var url = '/admin/product!add.action';
        var ezWin = window.top.createWin({ title: "添加产品" });
        var iframe = window.top.createIframe(url);
        iframe.appendTo(ezWin);
        ezWin.window("open");
    });
    $('#delProducts').unbind('click').click(function(){
		var msg = window.top.$.messager;
    	var url = "/admin/product!delete.action";
    	var ids = [];
    	var rows = table.datagrid("getSelections");
    	if (rows.length == 0){
    		msg.alert("批量删除", "请选择要删除的产品", "info");
    		return false;
    	}
    	msg.confirm("批量删除", '是否删除选中的产品？', function(r){
    		if (!r) return false;
    		var len = rows.length;
    		for (var i = 0; i < len; i++){
    			ids.push('ids=' + rows[i].id);
    		}
    		$.post(url, ids.join('&'), function(data){
    			if (data.status == 'success'){
    				msg.alert("批量删除", "批量删除成功！", "info");
    				window.top.reloadDataGridInTab('产品管理');
    			}else{
    				msg.alert("批量删除", "批量删除失败：" + data.message, "error");
    			}
    		},"json");
    	});
    });
}
$(function(){
    var table = $('#table');
    table.datagrid(getConfig());
    table.datagrid('getPager').pagination(pager);
    $(window).resize(function(){
        table.datagrid('resize', {
            height: getDataGridHeight()
        });
    });
    _initEvents();
});