var iconBasePath = '/rzrk/js/jquery-easyui-1.4.1/themes/icons/';
var msg = window.top.$.messager;
var pager = {
    beforePageText: '',
    afterPageText: '',
    displayMsg: '',
    layout:['sep','refresh','sep']
};
function operations(value, rowData) {
    var link = "<a href='#' data-id='{0}' class='edit'>编辑</a>&nbsp;<a href='#' data-id='{0}' class='delete'>删除</a>";
    return $.format(link, rowData.id);
}

function viewdeparment(value, rowData, index) {
    var link = "<a href='#' data-id='{0}' class='person'>{2}</a>";
    return $.format(link, rowData.id, index, rowData.peopleAmount);
}

function renderStatus(value, rowData, index){
	return value ? "<span style='color:green;'>启用</span>" : "<span style='color:red;'>禁止</span>";
}

function renderSortNo(value, rowData, index){
	return value ? "<span style='color:green;'>插入</span>" : "<span style='color:blue;'>重复</span>";
}


function getColumns() {
    var columns = [[
//            {title: "", field: "isChecked", width: 80, align: 'center', checkbox:true},
            {title: "部门名称", field: "name", width: 300, align: 'left', sortable: false},
            {title: "部门代码", field: "departmentCode", width: 80, align: 'center', sortable: false},
            {title: "排序号", field: "sortNo", width: 80, align: 'center', sortable: false},
            {title: "重复序号处理方式", field: "duplicateSortDeal", width: 80, align: 'center', sortable: false, formatter: renderSortNo},
            {title: "状态", field: "status", width: 80, align: 'center', sortable: false, formatter: renderStatus},
            {title: "部门主管", field: "deparmentLeader", width: 80, align: 'center', sortable: false},
            {title: "部门分管领导", field: "departmentOtherLeader", width: 80, align: 'center', sortable: false},
            {title: "主管别名", field: "deparmentAlisa", width: 80, align: 'center', sortable: false},
//            {title: "部门人员", field: "deparmentAdmin", width: 80, align: 'center', sortable: false},
//            {title: "上级部门", field: "parent", width: 140, align: 'center', sortable: false},
//            {title: "部门人数", field: "peopleAmount", width: 140, align: 'center', sortable: false,formatter: viewdeparment},
            {title: "描述", field: "description", width: 280, align: 'center', sortable: false},
            {title: "操作", field: "operation", width: 80, align: 'center', formatter: operations}
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
        url: '/admin/deparment!getAjaxList.action',
        method: 'get',
        width: '100%',
        pagination: true,
    	pageSize:5000000,
    	pageList:[5000000],
        rownumbers: true,
        height: getDataGridHeight(),
        idField: 'id',
        treeField: 'name',
        columns: getColumns(),
        data: [],
    	onBeforeExpand: function(row){
    		var id = row.id;
    		var node = $("tr[id$='" + id + "']");
    		node.next().show();
    	},
    	onBeforeCollapse: function(row){
    		var id = row.id;
    		var node = $("tr[id$='" + id + "']");
    		node.next().hide();
    	},
    	onDblClickRow:function(row){
    		var url = "/admin/deparment!edit.action?id=";
        	var id = row.id;
            var ezWin = window.top.createWin({
            	title:"编辑部门信息",
            	width: "880",
            	height: "580",
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

/*
function doSearch(value){
    var table = $('#table');
    table.datagrid({
    	queryParams: {
    		searchBy: $('#searchBy').combobox("getValue"),
    		keyword: value
    	}
    });
}
*/
function _initEvents(){
    var table = $('#table');
    $(".datagrid").on("click", ".edit", function () {
    	var url = "/admin/deparment!edit.action?id=";
    	var id = $(this).data("id");
        var ezWin = window.top.createWin({
        	title:"编辑部门信息",
        	width: "880",
        	height: "580",
        	resizable: true,
        	maximizable: false
		});
        var iframe = window.top.createIframe(url + id);
        iframe.appendTo(ezWin);
        ezWin.window("open");
    	return false;
    }).on("click",".delete",function(){
    	var url = "/admin/deparment!delete.action";
    	var id = $(this).data("id");
    	var param = "ids="+id;
    	msg.confirm("删除", '是否删除选中的部门？', function(r){
    		if (!r) return false;
    		$.post(url, param, function(data){
    			if (data.status == 'success'){
    				msg.alert("删除", "删除成功！", "info");
    				$('#table').treegrid("reload");
    			}else{
    				msg.alert("删除", "删除失败：" + data.message, "error");
    			}
    		}, 'json');
    	});

    });
    $(".datagrid").on("click", ".person", function () {
    	var url = "/admin/deparment!personList.action?id=";
    	var id = $(this).data("id");
        var ezWin = window.top.createWin({
        	title:"查看部门人员信息",
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
    
    $('#addDeparment').unbind('click').click(function(){
    	var url = '/admin/deparment!add.action';
        var ezWin = window.top.createWin({
        	title: "添加部门",
        	width: "880",
        	height: "580",
        	resizable: true,
        	maximizable: false
    	});
        var iframe = window.top.createIframe(url);
        iframe.appendTo(ezWin);
        ezWin.window("open");
    });
 /*
    $('#delDeparments').unbind('click').click(function(){
		var msg = window.top.$.messager;
    	var url = "/admin/deparment!delete.action";
    	var ids = [];
    	var rows = table.datagrid("getSelections");
    	if (rows.length == 0){
    		msg.alert("批量删除", "请选择要删除的部门", "info");
    		return false;
    	}
    	msg.confirm("批量删除", '是否删除选中的部门？', function(r){
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
 */
}
$(function(){
    var table = $('#table');
   table.treegrid(getConfig()).treegrid('getPager').pagination(pager);
    $(window).resize(function(){
        table.treegrid('resize', {
            height: getDataGridHeight()
        });
    });
    _initEvents();
});