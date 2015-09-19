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
            {title: "模板名称", field: "name", width: 200, align: 'center', sortable: true},
            {title: "一级分类", field: "root", width: 200, align: 'center',  sortable: true},
            {title: "备注", field: "remark", width: 200, align: 'center', sortable: true},
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
        text:'添加文章',
        iconCls:'icon-add',
        handler:function(){alert('add');}
    },{
        text:'批量删除',
        iconCls:'icon-remove',
        handler:function(){alert('remove');}
    }];
    return toolbar;
}
function getConfig(){
    return {
        loadMsg: '正在获取数据......',
        url: '/admin/project_category!getAjaxList.action',
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
$(function(){
    var table = $('#table');
    table.datagrid(getConfig());
    table.datagrid('getPager').pagination(pager);
    $(window).resize(function(){
        table.datagrid('resize', {
            height: getDataGridHeight()
        });
    });
    //增加
    $('#addProjectCategory').unbind('click').click(function(){
    	var url = '/admin/project_category!add.action';
        var ezWin = window.top.createWin({
        	title: "添加模板",
        	width: "880",
        	height: "580",
        	resizable: true,
        	maximizable: false
    	});
        var iframe = window.top.createIframe(url);
        iframe.appendTo(ezWin);
        ezWin.window("open");
    });
    //编辑
    var table = $('#table');
    $(".datagrid").on("click", ".edit", function () {
    	var url = "/admin/project_category!edit.action?id=";
    	var id = $(this).data("id");
        var ezWin = window.top.createWin({
        	title:"修改",
        	width: "880",
        	height: "580",
        	resizable: true,
        	maximizable: false
		});

        var iframe = window.top.createIframe(url + id);
        iframe.appendTo(ezWin);
        ezWin.window("open");
    	return false;
    });
    //删除
    $('#delProjectCategory').unbind('click').click(function(){
		var msg = window.top.$.messager;
    	var url = "/admin/project_category!delete.action";
    	var ids = [];
    	var rows = table.datagrid("getSelections");
    	if (rows.length == 0){
    		msg.alert("批量删除", "请选择要删除的模板", "info");
    		return false;
    	}
    	msg.confirm("批量删除", '是否删除选中的模板？', function(r){
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
    		}, 'json');
    	});
    });
});