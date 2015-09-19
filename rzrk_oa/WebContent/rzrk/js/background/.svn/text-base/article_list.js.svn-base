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
    link += "<em>|</em>";
    link += "<a href='#' data-id='{0}' data-idx='{1}' class='browse'>浏览</a>";
    return $.format(link, rowData.id, index);
}
function renderIsPublication(value, rowData, index){
	return value ? "<span style='color:green;'>是</span>" : "<span style='color:red;'>否</span>";
}
function renderIsRecommend(value, rowData, index){
	return value ? "<span style='color:green;'>是</span>" : "<span style='color:red;'>否</span>";
}
function getColumns() {
    var columns = [[
            {title: "", field: "isChecked", width: 80, align: 'center', checkbox:true},
            {title: "标题", field: "title", width: 500, align: 'center', sortable: true},
            {title: "图片", field: "imagePath", width: 300, align: 'center'},
            {title: "分类", field: "articleCategory", width: 80, align: 'center', sortable: true},
            {title: "是否发布", field: "isPublication", width: 80, align: 'center', formatter: renderIsPublication},
            {title: "是否推荐", field: "isRecommend", width: 80,  align: 'center', formatter: renderIsRecommend},
            {title: "添加时间", field: "createDate", width: 160, align: 'center', sortable: true},
            {title: "操作", field: "operation", width: 120, align: 'center', formatter: operations}
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
        url: '/admin/article!ajaxGetList.action',
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
    $(".datagrid").on("click", ".edit", function () {
    	var url = "/admin/article!edit.action?id=";
    	var id = $(this).data("id");
        var ezWin = window.top.createWin({ title: "编辑" });
        var iframe = window.top.createIframe(url + id);
        iframe.appendTo(ezWin);
        ezWin.window("open");
    	return false;
    }).on("click", ".browse", function(){
    	var url = "/admin/article!overList.action?id=";
    	var id = $(this).data("id");
        var ezWin = window.top.createWin({ title: "浏览" });
        var iframe = window.top.createIframe(url + id);
        iframe.appendTo(ezWin);
        ezWin.window("open");
    	return false;
    });
    $('#addArticle').unbind('click').click(function(){
    	var url = '/admin/article!add.action';
        var ezWin = window.top.createWin({ title: "添加文章" });
        var iframe = window.top.createIframe(url);
        iframe.appendTo(ezWin);
        ezWin.window("open");
    });
    $('#delArticles').unbind('click').click(function(){
		var msg = window.top.$.messager;
    	var url = "/admin/article!delete.action";
    	var ids = [];
    	var rows = table.datagrid("getSelections");
    	if (rows.length == 0){
    		msg.alert("批量删除", "请选择要删除的文章", "info");
    		return false;
    	}
    	msg.confirm("批量删除", '是否删除选中的文章？', function(r){
    		if (!r) return false;
    		var len = rows.length;
    		for (var i = 0; i < len; i++){
    			ids.push('ids=' + rows[i].id);
    		}
    		$.get(url, ids.join('&'), function(data){
    			if (data.status == 'success'){
    				msg.alert("批量删除", "批量删除成功！", "info");
    				window.top.reloadDataGridInTab('文章管理');
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