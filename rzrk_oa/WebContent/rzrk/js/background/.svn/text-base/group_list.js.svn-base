var iconBasePath = '/rzrk/js/jquery-easyui-1.4.1/themes/icons/';
var pager = {
    beforePageText: '当前第',
    afterPageText: '页，共 {pages} 页',
    displayMsg: '当前第 {from} 至 {to} 条数据，共 {total} 条数据'
};
function operations(value, rowData, index) {
    var link = "<a href='#' data-id='{0}' data-idx='{1}' class='edit'>编辑</a>";
    return $.format(link, rowData.id, index);
}
function getColumns() {
    var columns = [[
            {title: "", field: "isChecked", width: 80, align: 'center', checkbox:true},
            {title: "组名", field: "groupName", width: 80, align: 'center', sortable: true},
            {title: "排序号", field: "indexNo", width: 90, align: 'center', sortable: true},
            {title: "状态", field: "status", width: 80, align: 'center', sortable: true},
            {title: "组类型", field: "groupType", width: 80, align: 'center', sortable: true},
            {title: "权限属性", field: "groupPrivlege", width: 80, align: 'center', sortable: true},
            //{title: "所属范围", field: "departmentOtherLeader", width: 80, align: 'center', sortable: true},
            {title: "描述", field: "desciption", width: 80, align: 'center', sortable: true}
        ]];
    return columns;
}
function getDataGridHeight(){
	var searchBarHeight = $('.searchBar').outerHeight();
	var winHeight = $(window).outerHeight();
	return winHeight - searchBarHeight - 25;
}
function getConfig(){
    return {
        loadMsg: '正在获取数据......',
        url: '/admin/group!getAjaxList.action',
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
        pageSize: 50,
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
    	var url = "/admin/group!edit.action?id=";
    	var id = $(this).data("id");
        var ezWin = window.top.createWin({
        	title:"编辑人员信息",
        	width: "500",
        	height: "440",
        	resizable: false,
        	maximizable: false
		});
        var iframe = window.top.createIframe(url + id);
        iframe.appendTo(ezWin);
        ezWin.window("open");
    	return false;
    });
    $('#addGroup').unbind('click').click(function(){
    	var url = '/admin/group!deparment.action';
        var ezWin = window.top.createWin({
        	title: "添加人员",
        	width: "500",
        	height: "396",
        	resizable: false,
        	maximizable: false
    	});
        var iframe = window.top.createIframe(url);
        iframe.appendTo(ezWin);
        ezWin.window("open");
    });
    $('#delGroups').unbind('click').click(function(){
		var msg = window.top.$.messager;
    	var url = "/admin/group!delete.action";
    	var ids = [];
    	var rows = table.datagrid("getSelections");
    	if (rows.length == 0){
    		msg.alert("批量删除", "请选择要删除的人员", "info");
    		return false;
    	}
    	msg.confirm("批量删除", '是否删除选中的人员？', function(r){
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