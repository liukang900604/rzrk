var iconBasePath = '/rzrk/js/jquery-easyui-1.4.1/themes/icons/';
var msg = window.top.$.messager;
var pager = {
    beforePageText: '当前第',
    afterPageText: '页，共 {pages} 页',
    displayMsg: '当前第 {from} 至 {to} 条数据，共 {total} 条数据'
};
function operationsName(value, rowData, index) {
    var link = "<a href='#' data-id='{0}' data-idx='{1}' class='show'>"+value+"</a>";
    return $.format(link, rowData.id, index,rowData.name);
}

function operations(value, rowData, index) {
    var link = "<a href='#' data-id='{0}' data-idx='{1}' class='delete'>删除</a>";
    link += "<em>|</em>";
    link += "<a href='#' data-id='{0}' data-idx='{1}' class='edit'>编辑</a>";
    return $.format(link, rowData.id, index,rowData.name);
}


function getColumns() {
    var columns = [[
//            {title: "", field: "isChecked", width: 80, align: 'center', checkbox:true},
            {title: "视图名", field: "name", width: "60%", align: 'center', formatter: operationsName},
            {title: "操作", field: "operation", width: "40%", align: 'center', formatter: operations}
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
        url: '/admin/viewlayer!getAjaxList.action',
        method: 'get',
        width:'auto',
//        rownumbers: true,
//        height: getDataGridHeight(),
        singleSelect: true,
//        selectOnCheck: true,
//        checkOnSelect: false,
//        autoRowHeight: false,
//        fitColumns:true,
//        remoteSort: false,
//        showFooter: false,
//        pagination: true,
//        pageSize: 50,
//        pageList: [50, 100, 500],
//        pageNumber: 1,
        columns: getColumns(),
        data: [],
        toolbar:[{
            text:'添加',
            iconCls:'icon-add',
            handler:function(){
            	var url = '/admin/viewlayer!add.action';
                var ezWin = window.top.createWin({ title: "添加新表" });
                var iframe = window.top.createIframe(url);
                iframe.appendTo(ezWin);
                ezWin.window("open");
            }
        },"-",{
            text:'刷新',
            iconCls:'icon-reload',
            handler:function(){
            	$("#table").datagrid("reload")
            }
        }],
    	onBeforeExpand: function(row){
    		var id = row.id;
    		var node = $("tr[id$='" + id + "']");
    		node.next().show();
    	},
    	onBeforeCollapse: function(row){
    		var id = row.id;
    		var node = $("tr[id$='" + id + "']");
    		node.next().hide();
    	}
    };
}



function _initEvents(){
    var table = $('#table');
    $(".datagrid").on("click", ".delete", function () {
    	var id = $(this).data("id");
    	
    	msg.confirm("删除", '是否删除选中的新表？', function(r){
    		if (!r) return false;
    		var ids = [];
    		ids.push('ids=' + id);
    		
    		$.ajax({
  			  dataType: "json",
  			  url: "/admin/viewlayer!delete.action",
  			  type : "post",
  			  data: ids.join('&'),
  			  success: function(data){
	    			if (data.status == 'success'){
	    				msg.alert("删除", "删除成功！", "info");
	    				window.top.reloadDataGridInTab('我的新表');
	    			}else{
	    				msg.alert("删除", "删除失败：" + data.message, "error");
	    			}
	  			},
  	    		error: function (data, status, e){
    				msg.alert("删除", "删除失败："  +e, "error");
  	    		}
  			});
    	});
    	return false;
    }).on("click", ".edit", function () {
    	var url = "/admin/viewlayer!edit.action?id=";
    	var id = $(this).data("id");
        var ezWin = window.top.createWin({ title: "编辑" });
        var iframe = window.top.createIframe(url + id);
        iframe.appendTo(ezWin);
        ezWin.window("open");
    	return false;
    }).on("click", ".show", function () {
    	$("#cciframe").attr("src","/admin/viewlayer_show!list.action?id="+$(this).data("id"));
    	return false;
    });
    

}
$(function(){
    var table = $('#table');
    table.datagrid(getConfig());
//    table.datagrid('getPager').pagination(pager);
    $(window).resize(function(){
        table.datagrid('resize', {
//            height: getDataGridHeight()
        });
    });
    _initEvents();
});