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

function renderStatus(value, rowData, index){
	return value ? "<span style='color:green;'>启用</span>" : "<span style='color:red;'>禁止</span>";
}

function renderSortNo(value, rowData, index){
	return value ? "<span style='color:green;'>插入</span>" : "<span style='color:blue;'>重复</span>";
}


function getColumns() {
    var columns = [[
            {title: "", field: "isChecked", width: 80, align: 'center', checkbox:true},
            {title: "职务级别名称", field: "name", width: 80, align: 'center', sortable: true},
            {title: "职务级别代码", field: "code", width: 80, align: 'center', sortable: true},
            {title: "排序号", field: "sortNo", width: 80, align: 'center', sortable: true},
            {title: "重复序号处理方式", field: "duplicateSortDeal", width: 80, align: 'center', sortable: true, formatter: renderSortNo},

            {title: "状态", field: "status", width: 80, align: 'center', sortable: true, formatter: renderStatus},
            {title: "描述", field: "description", width: 180, align: 'center', sortable: true},
            {title: "操作", field: "operation", width: 80, align: 'center', formatter: operations}
        ]];
    return columns;
}
function getDataGridHeight(){
	var searchBarHeight = $('.searchBar').outerHeight();
	var winHeight = $(window).outerHeight();
	return winHeight - searchBarHeight - 25;
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
        url: '/admin/job_level!getAjaxList.action',
        method: 'get',
        width: '100%',
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
        data: [],
        onDblClickRow:function(index,rowData){
        	var url = "/admin/job_level!edit.action?id=";
        	var id = rowData.id;
            var ezWin = window.top.createWin({
            	title:"编辑职务级别信息",
            	width: "500",
            	height: "440",
            	resizable: true,
            	maximizable: false
    		});
            var iframe = window.top.createIframe(url + id);
            iframe.appendTo(ezWin);
            ezWin.window("open");
        	return false;
        }
    };
}
function doSearch(value){
    var table = $('#table');
    table.datagrid({
    	queryParams: {
    		searchBy: $('#searchBy').combobox("getValue"),
    		keyword: value
    	}
    });
}
function initEvents(){
    var table = $('#table');
    $(".datagrid").on("click", ".edit", function () {
    	var url = "/admin/job_level!edit.action?id=";
    	var id = $(this).data("id");
        var ezWin = window.top.createWin({
        	title:"编辑职务级别信息",
        	width: "500",
        	height: "440",
        	resizable: true,
        	maximizable: false
		});
        var iframe = window.top.createIframe(url + id);
        iframe.appendTo(ezWin);
        ezWin.window("open");
    	return false;
    });
    $('#addJobLevel').unbind('click').click(function(){
    	var url = '/admin/job_level!add.action';
        var ezWin = window.top.createWin({
        	title: "添加职务级别",
        	width: "500",
        	height: "440",
        	resizable: true,
        	maximizable: false
    	});
        var iframe = window.top.createIframe(url);
        iframe.appendTo(ezWin);
        ezWin.window("open");
    });
    $('#delJobLevel').unbind('click').click(function(){
		var msg = window.top.$.messager;
    	var url = "/admin/job_level!delete.action";
    	var ids = [];
    	var rows = table.datagrid("getSelections");
    	if (rows.length == 0){
    		msg.alert("批量删除", "请选择要删除的职务级别", "info");
    		return false;
    	}
    	msg.confirm("批量删除", '是否删除选中的职务级别？', function(r){
    		if (!r) return false;
    		var len = rows.length;
    		for (var i = 0; i < len; i++){
    			ids.push('ids=' + rows[i].id);
    		}
    		$.post(url, ids.join('&'), function(data){
    			if (data.status == 'success'){
    				msg.alert("批量删除", "批量删除成功！", "info");
    				window.top.reloadDataGridInTab('人员列表');
    			}else{
    				msg.alert("批量删除", "批量删除失败：" + data.message, "error");
    			}
    		},'json');
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
    initEvents();
});