var iconBasePath = '/rzrk/js/jquery-easyui-1.4.1/themes/icons/';
var msg = window.top.$.messager;
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
    var link = "<a href='#' data-id='{0}' data-idx='{1}' class='edit'>查看</a>";
    link += "<em>|</em>";
    if(rowData.isFollow != null && rowData.isFollow != ""){
    	if(rowData.isFollow == 1){
    		  link += "<a href='#' data-id='{0}' data-idx='{1}' class='follow'>取消关注</a>";
    	}else{
    		  link += "<a href='#' data-id='{0}' data-idx='{1}' class='follow'>关注</a>";
    	}
    }else{
    	  link += "<a href='#' data-id='{0}' data-idx='{1}' class='follow'>关注</a>";
    }
    
    return $.format(link, rowData.id, index);
}
function renderContent(value, rowData, index){
	var a = "<a href='#' class='showContent' content='{0}'>查看</a>";        //modifiedBy huanghui ; 2015/7/28
	return $.validator.format(a, value);
}
function renderProgress(value, rowData, index){
	var span = "<span style='position: absolute;left:0;top:4px;display:block;border:1px solid {0};height:18px;width:100%;'><span style='background-color:{0};display:block;width:{1}%;height:18px;'></span></span><span style='display:block;z-index:1;position:relative;color:#333;'>{1}%</span>";
	var color = "#91c4ff;";
	if (parseFloat(value) >= 100){
		value = 100;
	}
	return $.validator.format(span, color, value);
}
function cellStyler(value, rowData, index){
    return 'position: relative;';
}
function contentStyler(value, rowData, index){
    return 'text-overflow: ellipsis;';
}
function getColumns() {
    var columns = [[
//            {title: "", field: "isChecked", width: 180, align: 'center', checkbox:true},
            {title: "用户名", field: "user", width: 200, align: 'center', sortable: true},
            {title: "账户类型", field: "account_type", width: 110, align: 'left', sortable: true},
            {title: "最大同时登陆数量", field: "count", width: 100, align: 'center', sortable: true},
            {title: "过期时间", field: "expiretime", width: 130, align: 'center', sortable: true},
            {title: "用户平台", field: "etp_user", width: 100, align: 'center', sortable: true},
            {title: "间隔", field: "interval", width: 80, align: 'center', sortable: true},
            {title: "是否激活", field: "isactive", width: 80, align: 'center', sortable: true},
            {title: "是否上传", field: "isupload", width: 80, align: 'center', sortable: true},
            {title: "模型类型", field: "modeltype", width: 80, align: 'center', sortable: true},
            {title: "类型", field: "type", width: 80, align: 'center', sortable: true},
//            {title: "报告时间", field: "createDate", width: 120, align: 'center', sortable: true},
//            {title: "最近更新", field: "modifyDate", width: 120, align: 'center', sortable: true},
//            {title: "操作", field: "operation", width: 100, align: 'center', formatter: operations}
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
        url: '/admin/model!getAjaxList.action',
        method: 'post',
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
        rowStyler: function(index,rowData){
        	var statusDict = {
    			"新建":"background-color:#ffbfbf;",
    			"反馈":"background-color:#eabfff;",
    			"开发中":"background-color:#bfffea;",
    			"认可":"background-color:#ffeabf;",
    			"已确认":"background-color:#bfeaff;",
    			"已解决":"background-color:#ffbfea;",
    			"已完成":"background-color:#bfffbf;",
    			"已关闭":"background-color:#f2f4f8;",
    			"测试中":"background-color:#eaffbf;"
        	};
        	return statusDict[rowData.status];
        },
        onDblClickRow: function(index, rowData){
        	var url = "/admin/user_plan!edit.action?id=";
        	var id = rowData.id;
            var ezWin = window.top.createWin({
            	title:"编辑计划信息",
            	width: "1080",
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
    		preQuery : value
    	}
    });
}
function _initEvents(){
	
	 $("#queryBtn").unbind('click').click(function(){
	    	try {
	    		var queryContent = $(".queryDom").queryTree("getValue");
	    		var queryContentStr = JSON.stringify(queryContent, null, 4);
	    		doSearch(queryContentStr);
			} catch (e) {
				if(e instanceof jQuery){  
					msg.alert("错误", "未填写查询条件", "error",function(){
						e.find(".qkeyword").focus();
					});
				}
			}
			
	    });
	
	$("#queryCondShow").unbind('change').change(function(){
    	if($(this).is(":checked")){
    		$(".queryDom").queryTree("show");
    	}else{
    		$(".queryDom").queryTree("remove");
    	}
    });
    /*var table = $('#table');
    var search = $('#search');
    $(".datagrid").on("click", ".edit", function () {
    	var url = "/admin/user_plan!edit.action?id=";
    	var id = $(this).data("id");
        var ezWin = window.top.createWin({
        	title:"编辑计划信息",
        	width: "1080",
        	resizable: true,
        	maximizable: false
		});
        var iframe = window.top.createIframe(url + id);
        iframe.appendTo(ezWin);
        ezWin.window("open");
    	return false;
    }).on("click", ".follow", function () {
      var obj = $(this);
   	   $.ajax({    
         type:'post',        
         url: "/admin/model!userPlanFollow.action?id="+$(this).data("id"),    
         data:{},    
         cache:false,    
         dataType:'json',    
         success:function(data){    
          if(data.status == 'success'){
       	   msg.alert('保存信息',data.message,'info',function(){
       		if(obj.text() == "关注"){
       			obj.text("取消关注");
       		}else{
       			obj.text("关注");
       		}
       	   });     	   
          }else{
       	   msg.alert('保存信息',data.message,'error',function(){
            	   return false;
       	   });
          }
         } 
            
       });
    }).on("click", ".showContent", function () {
    	var content = $(this).attr('content');		//modifiedBy huanghui ; 2015/7/28
        var ezWin = window.top.createWin({
        	title:"计划内容详细",
        	resizable: true,
        	maximizable: true
		});
        ezWin.html("<div style='padding:10px;'>" + content + "</div>");
        ezWin.window("open");
    	return false;
    });
    $('#addPlan').unbind('click').click(function(){
    	var url = '/admin/user_plan!add.action';
        var ezWin = window.top.createWin({
        	title: "添加计划",
        	width: "1080",
        	resizable: true,
        	maximizable: false
    	});
        var iframe = window.top.createIframe(url);
        iframe.appendTo(ezWin);
        ezWin.window("open");
    });
    $('#delPlans').unbind('click').click(function(){
		var msg = window.top.$.messager;
    	var url = "/admin/user_plan!delete.action";
    	var ids = [];
    	var rows = table.datagrid("getSelections");
    	if (rows.length == 0){
    		msg.alert("批量删除", "请选择要删除的计划", "info");
    		return false;
    	}
    	msg.confirm("批量删除", '是否删除选中的计划？', function(r){
    		if (!r) return false;
    		var len = rows.length;
    		for (var i = 0; i < len; i++){
    			ids.push('ids=' + rows[i].id);
    		}
    		$.post(url, ids.join('&'), function(data){
    			if (data.status == 'success'){
    				msg.alert("批量删除", "批量删除成功！", "info");
    				window.top.reloadDataGridInTab('我的计划');
    			}else{
    				msg.alert("批量删除", "批量删除失败：" + data.message, "error");
    			}
    		},"json");
    	});
    });
    search.unbind('click').click(function(){
    	doSearch();
    });
    $(".queryText").keypress(function (event) {
        var key = event.which;
        if (key == 13) {
        	doSearch();
        }
    });*/
}

function preQueryItem(){
	$(".queryDom").queryTree({
		searchByData:window.queryOption,
		preData:window.preQuery
	})
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
    _initEvents();
    preQueryItem();
});