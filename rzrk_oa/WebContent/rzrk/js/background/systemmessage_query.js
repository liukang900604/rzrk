var iconBasePath = '/rzrk/js/jquery-easyui-1.4.1/themes/icons/';
var pager = {
    beforePageText: '当前第',
    afterPageText: '页，共 {pages} 页',
    displayMsg: '当前第 {from} 至 {to} 条数据，共 {total} 条数据'
};
function operations(value, rowData, index) {
	  var link  = "";
	  	link  += "<a href='#' data-id='{0}' data-idx='{1}' class='query'>查看</a>";
	  	if(rowData.workId != null && rowData.workId != "" ){
	  		link += "<em>|</em>";
	  		if(rowData.internal != null && rowData.internal != ""){
	  				 if(rowData.internal == 2){
	  					link += "<a href='#' data-id='{0}' data-idx='{1}' class='editInternal'>处理</a>";
	  				 }else{
	  					link += "<a href='#' data-id='{0}' data-idx='{1}' class='edit'>处理</a>";
	  				 }
	  		}else{
	  			link += "<a href='#' data-id='{0}' data-idx='{1}' class='handle'>处理</a>";
	  		}
	  		
	  	}
  return $.format(link, rowData.id, index);
}
function getColumns() {
    var columns = [[
            {title: "", field: "isChecked", width: 80, align: 'center', checkbox:true},
            {title: "消息标题", field: "title", width: 200, align: 'center', sortable: true},
            {title: "消息发布者", field: "admin", width: 200, align: 'center',  sortable: true},
            {title: "消息类型", field: "type", width: 200, align: 'center', sortable: true},
            {title: "消息内容", field: "context", width: 500, align: 'center',  sortable: true},
            {title: "发布时间", field: "createDate", width: 120,  align: 'center',  sortable: true},
            {title: "状态", field: "redtype", width: 120,  align: 'center',  sortable: true},
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

function  handleWork(workId){
	var src = "/admin/work!editWorkCheck.action?id="+workId;
    var ezWin = window.top.createWin({
    	title: '办理',
    	width:"860px",
    	resizable: false,
    	maximizable: false
	});
    var iframe = window.top.createIframe(src);
    //iframe.attr('id', '产品每日汇总列表');
    iframe.appendTo(ezWin);
    ezWin.window("open");
	return false;
}

//编辑内置流程工作
function  editInternalWork(workId){
	var src = "/admin/work!editDevelopmentWork.action?id="+workId;
    var ezWin = window.top.createWin({
    	title: '编辑',
    	width:"860px",
    	height: "700px",
    	resizable: false,
    	maximizable: true
	});
    var iframe = window.top.createIframe(src);
    iframe.attr('id', '内置流程编辑');
    iframe.appendTo(ezWin);
    ezWin.window("open");
	return false;
}

//编辑普通工作
function  editIsInternalWork(workId){
	var src = "/admin/work!editMyWork.action?id="+workId;
    var ezWin = window.top.createWin({
    	title: '编辑',
    	width:"860px",
    	height: "700px",
    	resizable: true,
    	maximizable: true
	});
    var iframe = window.top.createIframe(src);
    //iframe.attr('id', '产品每日汇总列表');
    iframe.appendTo(ezWin);
    ezWin.window("open");
	return false;
}

function getConfig(){
    return {
        loadMsg: '正在获取数据......',
        url: '/admin/systemmessage!getAjaxList.action',
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
        data: [],
        onDblClickRow: function(index, rowData){				//added by huanghui ; 2015/8/3
        	var url = "/admin/systemmessage!query.action?id=";
        	var id = rowData.id;
            var ezWin = window.top.createWin({
            	title:"查看系统消息",
            	width: "668",
            	resizable: true,
            	maximizable: false
    		});
            var iframe = window.top.createIframe(url + id);
            iframe.appendTo(ezWin);
            ezWin.window("open");
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
    $('#addSystemmessage').unbind('click').click(function(){
    	var url = '/admin/systemmessage!add.action';
        var ezWin = window.top.createWin({
        	title: "发布消息",
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
    $(".datagrid").on("click", ".query", function () {
    	var url = "/admin/systemmessage!query.action?id=";
    	var id = $(this).data("id");
        var ezWin = window.top.createWin({
        	title:"查看系统消息",
        	width: "880",
        	height: "580",
        	resizable: true,
        	maximizable: false
		});
        var a = $(this);
        var row = a.parent().parent().parent();
		row.children('td[field=redtype]').html("<div style='text-align:center;' class='datagrid-cell datagrid-cell-c1-72656474797065'>已读</div>");
        var iframe = window.top.createIframe(url + id);
        iframe.appendTo(ezWin);
        ezWin.window("open");
    	return false;
    	
    }).on("click", ".editInternal", function(){
    	
    var a = $(this);
    var row = a.parent().parent().parent();
		row.children('td[field=redtype]').html("<div style='text-align:center;' class='datagrid-cell datagrid-cell-c1-72656474797065'>已读</div>");
	$.ajax({
		type : 'post',
		url : '/admin/systemmessage!checkWorkBackData.action',
		data : {messageId:$(this).data("id")},
		cache : false,
		dataType : 'json',
		success : function(data) {
			if (data.status == 'success') {
				editInternalWork(data.message);//办理工作
			} else {
				msg.alert('信息提示', data.message, 'error', function() {
					return false;
				});
			}
		}

	});
    
    }).on("click", ".edit", function(){
    		var a = $(this);
    	    var row = a.parent().parent().parent();
    			row.children('td[field=redtype]').html("<div style='text-align:center;' class='datagrid-cell datagrid-cell-c1-72656474797065'>已读</div>");
    		$.ajax({
    			type : 'post',
    			url : '/admin/systemmessage!checkWorkBackData.action',
    			data : {messageId:$(this).data("id")},
    			cache : false,
    			dataType : 'json',
    			success : function(data) {
    				if (data.status == 'success') {
    					editIsInternalWork(data.message);//办理工作
    				} else {
    					msg.alert('信息提示', data.message, 'error', function() {
    						return false;
    					});
    				}
    			}

    		});
    
    }).on("click", ".handle", function () {
    	var a = $(this);
        var row = a.parent().parent().parent();
  		row.children('td[field=redtype]').html("<div style='text-align:center;' class='datagrid-cell datagrid-cell-c1-72656474797065'>已读</div>");
    	$.ajax({
			type : 'post',
			url : '/admin/systemmessage!checkWorkData.action',
			data : {messageId:$(this).data("id")},
			cache : false,
			dataType : 'json',
			success : function(data) {
				if (data.status == 'success') {
					handleWork(data.message);//办理工作
				} else {
					msg.alert('信息提示', data.message, 'error', function() {
						return false;
					});
				}
			}

		});
    });
    
    
    //删除
    $('#delSystemmessage').unbind('click').click(function(){
		var msg = window.top.$.messager;
    	var url = "/admin/systemmessage!delete.action";
    	var ids = [];
    	var rows = table.datagrid("getSelections");
    	if (rows.length == 0){
    		msg.alert("批量删除", "请选择要删除的信息", "info");
    		return false;
    	}
    	msg.confirm("批量删除", '是否删除选中的信息？', function(r){
    		if (!r) return false;
    		var len = rows.length;
    		for (var i = 0; i < len; i++){
    			ids.push('ids=' + rows[i].id);
    		}
    		$.post(url, ids.join('&'), function(data){
    			if (data.status == 'success'){
    				msg.alert("批量删除", "批量删除成功！", "info");
    				window.top.reloadIframeInWindow('backframe');
    				window.top.reloadDataGridInTab('人员列表');
    			}else{
    				msg.alert("批量删除", "批量删除失败：" + data.message, "error");
    			}
    		}, 'json');
    	});
    });
    
    //全部标为已读
    $('#markSystemmessage').unbind('click').click(function(){
		var msg = window.top.$.messager;
    	var url = "/admin/systemmessage!markSystemMessage.action";
    	var ids = [];
    	var rows = table.datagrid("getSelections");
    	msg.confirm("标为已读", '是否全部标为已读？', function(r){
    		if (!r) return false;
    		var len = rows.length;
    		for (var i = 0; i < len; i++){
    			ids.push('ids=' + rows[i].id);
    		}
    		$.post(url, ids.join('&'), function(data){
    			if (data.status == 'success'){
    				window.top.reloadIframeInWindow('backframe');
    				window.top.reloadDataGridInTab('人员列表');
    			}else{
    				msg.alert("标为已读", "全部标为已读失败：" + data.message, "error");
    			}
    		}, 'json');
    	});
    });
});