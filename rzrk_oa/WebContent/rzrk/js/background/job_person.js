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

function viewsPic(value, rowData, index) {
	var link = "-";
	if (value != ""){
		var link = "<a href='{0}' target='_blank' >查看</a>";
		return $.format(link, value);
	}
	return link;
    
    
}

function renderIsOnDuty(value, rowData, index){
	return value ? "<span style='color:green;'>正式</span>" : "<span style='color:red;'>非正式</span>";
}

function sex(value, rowData, index){
	return value ? "<span style='color:green;'>男</span>" : "<span style='color:red;'>女</span>";
}

function renderSortNo(value, rowData, index){
	return value ? "<span style='color:green;'>插入</span>" : "<span style='color:blue;'>重复</span>";
}
function getColumns() {
    var columns = [[
            {title: "用户名", field: "username", width: 90, align: 'center', sortable: true},
            {title: "姓名", field: "name", width: 80, align: 'center', sortable: true},
            {title: "人员代码", field: "code", width: 80, align: 'center', sortable: true},
            {title: "排序号", field: "sortNo", width: 80, align: 'center', sortable: true},
            {title: "重复序号处理方式", field: "duplicateSortDeal", width: 100, align: 'center', sortable: true, formatter: renderSortNo},
            {title: "在职状态", field: "manType", width: 80, align: 'center', sortable: true, formatter: renderIsOnDuty},
            {title: "Email", field: "email", width: 200, align: 'center', sortable: true},
            {title: "性别", field: "sex", width: 80, align: 'center', sortable: true, formatter: sex},
            {title: "生日", field: "birthDate", width: 80, align: 'center', sortable: true},
            {title: "办公电话", field: "officePhone", width: 80, align: 'center', sortable: true},
            {title: "手机", field: "telephone", width: 100, align: 'center', sortable: true},
            {title: "个人照片", field: "url", width: 80, align: 'center', sortable: true, formatter: viewsPic},
            {title: "岗位级别", field: "jobLevel", width: 80, align: 'center', sortable: true},
            {title: "最后登录时间", field: "loginDate", width: 80,  align: 'center', sortable: true},
            {title: "最后登录IP", field: "loginIp", width: 100, align: 'center', sortable: true},
            {title: "状态", field: "status", width: 60, align: 'center', sortable: true},
            {title: "创建日期", field: "createDate", width: 80, align: 'center', sortable: true}
        ]];
    return columns;
}
function getDataGridHeight(){
	var searchBarHeight = $('.searchBar').outerHeight();
	var winHeight = $(window).outerHeight();
	return winHeight - searchBarHeight - 25;
}
function getConfig(){
	var jobId = $("#jobId").val();
    return {
        loadMsg: '正在获取数据......',
        url: '/admin/job!getPersonAjaxList.action?jobId='+jobId,
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
        pagination: false,
      /*  pageSize: 50,
        pageList: [50, 100, 500],
        pageNumber: 1,*/
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
    	var url = "/admin/admin!edit.action?id=";
    	var id = $(this).data("id");
        var ezWin = window.top.createWin({
        	title:"编辑人员信息",
        	width: "700",
        	height: "840",
        	resizable: true,
        	maximizable: false
		});
        var iframe = window.top.createIframe(url + id);
        iframe.appendTo(ezWin);
        ezWin.window("open");
    	return false;
    });
    $('#addAdmin').unbind('click').click(function(){
    	var url = '/admin/admin!add.action';
        var ezWin = window.top.createWin({
        	title: "添加人员",
        	width: "700",
        	height: "840",
        	resizable: true,
        	maximizable: false
    	});
        var iframe = window.top.createIframe(url);
        iframe.appendTo(ezWin);
        ezWin.window("open");
    });
    $('#delAdmins').unbind('click').click(function(){
		var msg = window.top.$.messager;
    	var url = "/admin/admin!delete.action";
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
    		},"json");
    	});
    });
}
$(function(){
    var table = $('#table');
    table.datagrid(getConfig());
    //table.datagrid('getPager').pagination(pager);
    $(window).resize(function(){
        table.datagrid('resize', {
            height: getDataGridHeight()
        });
    });
    initEvents();
});