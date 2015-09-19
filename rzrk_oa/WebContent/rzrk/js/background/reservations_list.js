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
function renderIsPublication(value, rowData, index){
	return value ? "<span style='color:green;'>是</span>" : "<span style='color:red;'>否</span>";
}
function renderIsRecommend(value, rowData, index){
	return value ? "<span style='color:green;'>是</span>" : "<span style='color:red;'>否</span>";
}
function getColumns() {
    var columns = [[
            {title: "", field: "isChecked", width: 80, align: 'center', checkbox:true},
            {title: "产品名称", field: "productName", width: 400, align: 'center', sortable: true},
            {title: "预约金额", field: "reservAmount", width: 200, align: 'center', sortable: true},
            {title: "联系人", field: "contactPerson", width: 120, align: 'center', sortable: true},
            {title: "联系手机", field: "contactMobile", width: 120, align: 'center', sortable: true},
            {title: "传真", field: "fax", width: 120,  align: 'center', sortable: true},
            {title: "邮件", field: "email", width: 200, align: 'center', sortable: true},
            {title: "公司名称", field: "companyName", width: 200, align: 'center', sortable: true},
            {title: "联系地址", field: "address", width: 200, align: 'center', sortable: true}
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
        url: '/admin/reservations!getAjaxList.action',
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
    		keyword: value
    	}
    });
}
function initEvents(){
    var table = $('#table');
    $('#delReservations').unbind('click').click(function(){
		var msg = window.top.$.messager;
    	var url = "/admin/reservations!delete.action";
    	var ids = [];
    	var rows = table.datagrid("getSelections");
    	if (rows.length == 0){
    		msg.alert("批量删除", "请选择要删除的认购", "info");
    		return false;
    	}
    	msg.confirm("批量删除", '是否删除选中的认购？', function(r){
    		if (!r) return false;
    		var len = rows.length;
    		for (var i = 0; i < len; i++){
    			ids.push('ids=' + rows[i].id);
    		}
    		$.post(url, ids.join('&'), function(data){
    			if (data.status == 'success'){
    				msg.alert("批量删除", "批量删除成功！", "info");
    				window.top.reloadDataGridInTab('认购列表');
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