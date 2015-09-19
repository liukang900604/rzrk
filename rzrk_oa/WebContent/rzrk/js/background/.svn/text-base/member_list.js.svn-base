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
function renderMember(value, rowData, index){
    var link = "<a href='#' data-id='{1}' data-idx='{2}' class='detail'>{0}</a>";
    return $.format(link, value, rowData.id, index);
}
function getColumns() {
    var columns = [[
            {title: "", field: "isChecked", width: 80, align: 'center', checkbox:true},
            {title: "用户名", field: "username", width: 180, align: 'center', sortable: true},
            {title: "用户真实姓名", field: "realName", width: 180, align: 'center', formatter: renderMember},
            {title: "用户身份证", field: "userIdentification", width: 180, align: 'center', sortable: true},
            {title: "银行名称", field: "bank", width: 240, align: 'center'},
            {title: "银行账号", field: "bankAccount", width: 180,  align: 'center'},
            {title: "手机", field: "mobile", width: 140, align: 'center', sortable: true},
            {title: "Email", field: "email", width: 140, align: 'center', sortable: true},
            {title: "地址", field: "address", width: 300, align: 'center', sortable: true},
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
        url: '/admin/member!getAjaxList.action',
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
        data: []
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
    	var url = "/admin/member!edit.action?id=";
    	var id = $(this).data("id");
        var ezWin = window.top.createWin({
        	title: "编辑客户信息",
        	width: "450",
        	height: "500",
        	resizable: false,
        	maximizable: false
    	});
        var iframe = window.top.createIframe(url + id);
        iframe.appendTo(ezWin);
        ezWin.window("open");
    	return false;
    }).on("click", ".detail", function () {
    	var url = "/admin/memberproducthold!list.action?memberId=";
    	var id = $(this).data("id");
        var realName = $(this).text();
        var ezWin = window.top.createWin({ title: realName });
        var iframe = window.top.createIframe(url + id);
        iframe.appendTo(ezWin);
        ezWin.window("open");
    	return false;
    });
    $('#addMember').unbind('click').click(function(){
    	var url = '/admin/member!add.action';
        var ezWin = window.top.createWin({
        	title: "添加客户信息",
        	width: "450",
        	height: "430",
        	resizable: false,
        	maximizable: false
    	});
        var iframe = window.top.createIframe(url);
        iframe.appendTo(ezWin);
        ezWin.window("open");
    });
    $('#delMembers').unbind('click').click(function(){
		var msg = window.top.$.messager;
    	var url = "/admin/member!delete.action";
    	var ids = [];
    	var rows = table.datagrid("getSelections");
    	if (rows.length == 0){
    		msg.alert("批量删除", "请选择要删除的客户", "info");
    		return false;
    	}
    	msg.confirm("批量删除", '是否删除选中的客户？', function(r){
    		if (!r) return false;
    		var len = rows.length;
    		for (var i = 0; i < len; i++){
    			ids.push('ids=' + rows[i].id);
    		}
    		$.post(url, ids.join('&'), function(data){
    			if (data.status == 'success'){
    				msg.alert("批量删除", "批量删除成功！", "info");
    				window.top.reloadDataGridInTab('客户管理「产品」');
    			}else{
    				msg.alert("批量删除", "批量删除失败：" + data.message, "error");
    			}
    		});
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