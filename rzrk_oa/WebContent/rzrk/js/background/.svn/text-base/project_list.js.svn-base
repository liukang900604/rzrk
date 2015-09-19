var iconBasePath = '/rzrk/js/jquery-easyui-1.4.1/themes/icons/';
var key = window.location.href;
var msg = window.top.$.messager;
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

function renderContent(value, rowData, index){
	var a = "<a href='#' class='showContent' data-idx='{0}'>查看</a>";
	return $.validator.format(a, index);
}

function renderStatus(value, rowData, index){
	/*
	var status = {
		'0': '已完成',
		'1': '未完成',
		'2': '已作废'
	};
	return status[value];
	*/
	return value;
}

function renderProjectType(value, rowData, index){
	/*
	var projectType = {
		'0': '开发',
		'1': '业务'
	};
	return projectType[value];
	*/
	return value;
}

function renderProgress(value, rowData, index){
	var span = "<span style='position: absolute;left:0;top:4px;display:block;border:1px solid {0};height:18px;width:100%;'><span style='background-color:{0};display:block;width:{1}%;height:18px;'></span></span><span style='display:block;z-index:1;position:relative;color:#333;'>{1}%</span>";
	var color = "#91c4ff;";
	if (parseFloat(value) >= 100){
		value = 100;
	}
	return $.validator.format(span, color, value);
}
  
function operations(value, rowData, index) {
    var link = "<a href='#' data-id='{0}' data-idx='{1}' class='edit'>编辑</a>";
    link += "<em>|</em>";
    link += "<a href='#' data-id='{0}' data-idx='{1}' class='showRequest'>查看需求</a>";
    link += "<em>|</em>";
    link += "<a href='#' data-id='{0}' data-idx='{1}' class='showPlan'>查看计划</a>";
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

function cellStyler(value, rowData, index){
    return 'position: relative;';
}

function contentStyler(value, rowData, index){
    return 'text-overflow: ellipsis;';
}

function saveCookie(columns) {
	var columnKeys = [];
	$.each(columns[0],function(){
		columnKeys.push(this["field"]);
	});
	$.cookie("columns_"+key,JSON.stringify(columnKeys),{expires:360});
}

function getColumns() {
    var columns = [[
//            {title: "", field: "isChecked", width: 80, align: 'center', checkbox:true},
            {title: "项目名称", field: "name", width: 140, align: 'center', sortable: true},
            {title: "最近更新", field: "modifyDate", width: 160, align: 'center', sortable: true},
            {title: "项目类型", field: "projectType", width: 80, align: 'center', formatter: renderProjectType, sortable: true},
//            {title: "项目内容", field: "content", width: 80, align: 'center', styler: contentStyler, formatter: renderContent, sortable: true},
            {title: "开始时间", field: "beginTime", width: 80, align: 'center', sortable: true},
            {title: "结束时间", field: "endTime", width: 80, align: 'center', sortable: true},
            {title: "进度", field: "progress", width: 80, align: 'center', styler: cellStyler, formatter: renderProgress, sortable: true},
            {title: "状态", field: "status", width: 80, align: 'center', formatter: renderStatus, sortable: true},
            {title: "创建人", field: "Creator", width: 80, align: 'center', sortable: true},
            {title: "负责人", field: "responsibor", width: 140, align: 'center', sortable: true},
            {title: "所属部门", field: "deparment", width: 140, align: 'center', sortable: true},
            {title: "报告日期", field: "createDate", width: 160, align: 'center', sortable: true},    
            {title: "操作", field: "operation", width: 260, align: 'center', formatter: operations}
        ]];
    
    var columnKeys = $.cookie("columns_"+key);
	if (columnKeys != null){
		var newColumns = new Array(columns[0].length);
		columnKeys = JSON.parse(columnKeys);
		$.each(columns[0],function(i,item){
			newColumns[columnKeys.indexOf(item["field"])] = item;
		});
		columns[0] = newColumns;
	}
    
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
        url: '/admin/project!getAjaxList.action?hasNoRoot='+window.hasNoRoot,
        method: 'get',
        width: '100%',
        rownumbers: true,
        height: getDataGridHeight(),
        singleSelect: true,
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
        sortName: "modifyDate",
        sortOrder: "desc",
        data: [],
        queryParams: {
    		sort : "modifyDate",
    		order : "desc",
    	},
        rowStyler: function(index, rowData){
        	var statusDict = {
    			"未完成":"background-color:#ffffff;",
    			"已完成":"background-color:#d2f5b0;",
    			"已作废":"background-color:#e99eff;",
    			"测试中":"background-color:#eaffbf;"
        	};
        	return statusDict[rowData.status];
        },
        onDblClickRow: function(index, rowData){
        	var url = "/admin/project!edit.action?id=";
        	var id = rowData.id;
            var ezWin = window.top.createWin({
            	title:"编辑项目信息",
            	width: "1080",
            	height: "690",
            	resizable: true,
            	maximizable: true
    		});
            var iframe = window.top.createIframe(url + id);
            iframe.appendTo(ezWin);
            ezWin.window("open");
        }
    };
}



function doSearch(){
    var table = $('#table');
    table.datagrid({
    	queryParams: {
    		nameQuery :$("#nameQuery").val(),
    		creatorQuery :$("#creatorQuery").val(),
    		responsiborQuery :$("#responsiborQuery").val(),
    		statusQuery :$("#statusQuery").val()
//    		searchBy: $('#searchBy').combobox("getValue"),
//    		keyword: value
    	}
    });
}
function _initEvents(){
    var table = $('#table');
    var search = $('#search');
    $(".datagrid").on("click", ".edit", function () {
    	var url = "/admin/project!edit.action?id=";
    	var id = $(this).data("id");
        var ezWin = window.top.createWin({
        	title:"编辑项目信息",
        	width: "1080",
        	height: "690",
        	resizable: true,
        	maximizable: true
		});
        var iframe = window.top.createIframe(url + id);
        iframe.appendTo(ezWin);
        ezWin.window("open");
    	return false;
    }).on("click", ".showContent", function () {
    	var idx = $(this).data('idx');
    	var data = $("#table").datagrid("getData").rows[idx];
        var ezWin = window.top.createWin({
        	title:"项目内容详细",
        	resizable: true,
        	maximizable: true
		});
        ezWin.html("<div style='padding:10px;'>" + data.content + "</div>");
        ezWin.window("open");
    	return false;
    }).on("click", ".showRequest", function () {
    	var url = "project!showRequestList.action?id=";
    	var id = $(this).data("id");
    	var row = $(this).parent().parent().parent();
    	var projectName = row.find("td[field=name]").text();
        var ezWin = window.top.createWin({
        	title:"查看"+projectName+"需求",
        	resizable: true,
        	maximizable: true
		});
        var iframe = window.top.createIframe(url + id);
        iframe.attr("id", "查看项目需求");
        iframe.appendTo(ezWin);
        ezWin.window("open");
    	return false;
    }).on("click", ".showPlan", function () {
    	var url = "project!projectPlanList.action?id=";
    	var id = $(this).data("id");
    	var row = $(this).parent().parent().parent();
    	var projectName = row.find("td[field=name]").text();
        var ezWin = window.top.createWin({
        	title:"查看"+projectName+"计划",
        	resizable: true,
        	maximizable: true
		});
        var iframe = window.top.createIframe(url + id);
        iframe.attr("id", "查看计划");
        iframe.appendTo(ezWin);
        ezWin.window("open");
    	return false;
    }).on("click", ".follow", function () {
        var obj = $(this);
    	   $.ajax({    
          type:'post',        
          url: "/admin/project!projectFollow.action?id="+$(this).data("id"),    
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
     });
    
    $('#addDeparment').unbind('click').click(function(){
    	var url = '/admin/project!add.action?hasNoRoot='+window.hasNoRoot;
        var ezWin = window.top.createWin({
        	title: "添加项目",
        	width: "1080",
        	height: "690",
        	resizable: true,
        	maximizable: true
    	});
        var iframe = window.top.createIframe(url);
        iframe.appendTo(ezWin);
        ezWin.window("open");
    });
    
    $('#addExteralRequest').unbind('click').click(function(){
        var url = '/admin/check!addExternalRequestWork.action';
      	var title= "外部需求审批";
      	var id = "";
      	  var opt = {
                    "id": id,
                    "url": url,
                    "title": title+""
                }
      	window.top.createOrSelectTab(opt);
    });
    
    $('#addInsideRequest').unbind('click').click(function(){
    	var url = '/admin/check!addInsideRequestWork.action';
      	var title= "内部需求审批";
      	var id = "";
      	  var opt = {
                    "id": id,
                    "url": url,
                    "title": title+""
                }
      	window.top.createOrSelectTab(opt);
    });
    
    $('#addDevelopment').unbind('click').click(function(){
        var url = '/admin/check!addDevelopmentWork.action';
    	var title= "提交开发审批";
    	var id = "";
    	  var opt = {
                  "id": id,
                  "url": url,
                  "title": title+""
              }
    	window.top.createOrSelectTab(opt);
    });
    
    $('#addOnlineBug').unbind('click').click(function(){
    	var url = '/admin/check!addOnlinBugSubmitWork.action';
    	var title= "线上bug提交审批";
    	var id = "";
    	  var opt = {
                  "id": id,
                  "url": url,
                  "title": title+""
              }
    	window.top.createOrSelectTab(opt);
    });
    
    
/*    $('#searchBy').combobox({
    	textField: 'key',
    	valueField: 'value',
    	data:[{
    		key: '项目名称', value: 'name'
    	},{
    		key: '创建人', value: 'creator'
    	},{
    		key: '负责人', value: 'responsibor'
    	},{
    		key: '状态', value: 'status'
    	}],
    	editable:false,
    	onSelect: function(param){
    		var keyword = $("#keyword");
    		if (param.value != 'status'){
    			keyword.css('width', '300px').searchbox({
    				prompt: '请输入搜索关键字',
    				searcher: doSearch
    			});
    			search.hide();
    		}else{
        		keyword.css('width', '80px').combobox({
        			textField: 'key',
        	    	valueField: 'value',
        			data: window.statusData,
        			editable:false
    			}).combobox("select", "全部");
        		search.show();
    		}
    	}
    }).combobox("select", "name");
*/    
    search.unbind('click').click(function(){
    	doSearch();
    });
    $(".queryText").keypress(function (event) {
        var key = event.which;
        if (key == 13) {
        	doSearch();
        }
    });
    
    
    
//    $('#delDeparments').unbind('click').click(function(){
//		var msg = window.top.$.messager;
//    	var url = "/admin/project!delete.action";
//    	var ids = [];
//    	var rows = table.datagrid("getSelections");
//    	if (rows.length == 0){
//    		msg.alert("批量删除", "请选择要删除的项目", "info");
//    		return false;
//    	}
//    	msg.confirm("批量删除", '是否删除选中的项目？', function(r){
//    		if (!r) return false;
//    		var len = rows.length;
//    		for (var i = 0; i < len; i++){
//    			ids.push('ids=' + rows[i].id);
//    		}
//    		$.post(url, ids.join('&'), function(data){
//    			if (data.status == 'success'){
//    				msg.alert("批量删除", "批量删除成功！", "info");
//    				window.top.reloadDataGridInTab('人员列表');
//    			}else{
//    				msg.alert("批量删除", "批量删除失败：" + data.message, "error");
//    			}
//    		}, 'json');
//    	});
//    });
}
$(function(){
    var table = $('#table');
    table.datagrid(getConfig());
    table.datagrid('columnMoving');
    table.datagrid('doCellTip',{ specialShowFields:getColumns()[0] });
    table.datagrid('getPager').pagination(pager);
    $(window).resize(function(){
        table.datagrid('resize', {
            height: getDataGridHeight()
        });
    });
    _initEvents();
});