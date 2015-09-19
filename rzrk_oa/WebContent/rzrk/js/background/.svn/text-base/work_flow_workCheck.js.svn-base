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
window.isPC = function(){
	var userAgent = navigator.userAgent;
	var Agents = ["Android", "iPhone", "SymbianOS", "Windows Phone", "iPad", "iPod", "Macintosh"];
	for (var v = 0; v < Agents.length; v++) {
		if (userAgent.indexOf(Agents[v]) > 0) {
			return false;
		}
	}
	return true;
}();
function dateFormatter(date){
	var y = date.getFullYear();
	var m = date.getMonth()+1;
	var d = date.getDate();
	return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
}
function dateParser(s){
    if (!s) return new Date();
    var ss = (s.split('-'));
    var y = parseInt(ss[0],10);
    var m = parseInt(ss[1],10);
    var d = parseInt(ss[2],10);
    if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
        return new Date(y,m-1,d);
    } else {
        return new Date();
    }
}
function renderWorkName(value, rowData, index){
	var link = '<a href="#">{0}</a>';
	return $.validator.format(link, value);
}
function renderStatus(value, rowData, index){
	var status = {
		"0": "<span style='color:#CE8E13;'>被撤回</span>",
		"1": "<span style='color:blue;'>待XX审批</span>",
		"2": "<span style='color:red;'>被驳回</span>"
	};
	return status[value];
}
function renderSponsor(value, rowData, index){
	var link = '<a href="#">{0}</a>';
	return $.validator.format(link, value);
}
function operations(value, rowData, index) {
	var link = "";
    link += "<a href='#' data-id='{0}' data-idx='{1}'  class='view'>查看</a>";
    link += "<em>|</em>";
    link += "<a href='#' data-id='{0}' data-idx='{1}'  class='edit'>办理</a>";
    //return $.validator.format(link, rowData.productId, index, rowData.productName);
    return  $.format(link, rowData.id, index);
}
function getColumns() {
    var columns = [[
            {title: "工作名称", field: "workName", width: 200, align: 'center', sortable: true},
            {title: "当前状态", field: "status", width: 120, align: 'center', sortable: true},
            {title: "流程名称", field: "flowName", width: 120, align: 'center', sortable: true},
            {title: "当前节点", field: "pointName", width: 120, align: 'center', sortable: true},
            {title: "发起人", field: "name", width: 120, align: 'center', sortable: true},
            {title: "发起时间", field: "createDate", width: 120, align: 'center', sortable: true},
            {title: "结束时间", field: "endDate", width: 120, align: 'center', sortable: true},
            {title: "最后审批人", field: "lastName", width: 120, align: 'center', sortable: true},
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
        text:'创建工作',
        iconCls:'icon-add',
        handler:function(){
        	var src = "/admin/work!addWork.action";
            var ezWin = window.top.createWin({
            	title: '创建工作'
    		});
            var iframe = window.top.createIframe(src);
            iframe.appendTo(ezWin);
            ezWin.window("open");
    		return false;
        }
    }/*, {
        text:'批量删除',
        iconCls:'icon-remove',
        handler:function(){alert('batch remove');}
    }*/];
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
        url: '/admin/work!getAjaxWorkList.action?statu=4',
        method: 'get',
        width: '100%',
       // toolbar:getToolbar(),
        rownumbers: true,
        height: getDataGridHeight(),
        singleSelect: true,
        selectOnCheck: false,
        checkOnSelect: false,
        autoRowHeight: false,
        remoteSort: true,
        fitColumns: true,
        showFooter: true,
        pagination: true,
        pageSize: getPageSize(),
        pageList: [50, 100, 500],
        pageNumber: 1,
        columns: getColumns(),
        data: [],
        onDblClickRow:function(index,rowData){
        	var id = rowData.id;
        	var src = "/admin/work!viewMyWork.action?id="+id;
            var ezWin = window.top.createWin({
            	title: '工作详情',
            	width:"860px",
            	height: "700px",
            	resizable: true,
            	maximizable: true
    		});
            var iframe = window.top.createIframe(src);
            iframe.appendTo(ezWin);
            ezWin.window("open");
    		return false;
        }
    };
}
function doSearch(value){
    var table = $('#table');
	var searchBy = $('#searchBy').combobox("getValue");
    table.datagrid({
    	queryParams: {
    		searchBy: searchBy,
    		keyword: value
    	}
    });
}
function initEvents(){
    var table = $('#table');
    var search = $('#search');
    var as = $(".letters a, .tags a");
    search.unbind('click').click(function(){
    	var flow = $('#flow').combobox("getValue");
    	var type = $('#type').combobox("getValue");
    	var statu = $('#status').combobox("getValue");
    	var dateStatu = $('#dateStatus').combobox("getValue");
    	var beginDate = $('#beginDate').datebox('getValue');
    	var endDate = $('#endDate').datebox('getValue');  
    	var queryType = $('#queryType').combobox("getValue");
    	var queryText= $('#queryText').val();
    	 var spell = $("a.selected").attr("letter");//缩写
        table.datagrid({
        	queryParams: {
        		flow: flow,
        		type: type,
        		workStatu:statu,
        		dateStatu:dateStatu,
        		beginDate:beginDate,
        		endDate:endDate,
        		queryType:queryType,
        		queryText:queryText,
        		spell: spell
        		
        	}
        });
    });
    as.click(function(){
    	var _this = $(this);
    	if (_this.hasClass("selected")) return false;
    	var spell = _this.attr("letter");
    	_this.siblings().removeClass("selected");
    	_this.addClass("selected");
    	var flow = $('#flow').combobox("getValue");
    	var type = $('#type').combobox("getValue");
    	var statu = $('#status').combobox("getValue");
    	var dateStatu = $('#dateStatus').combobox("getValue");
    	var beginDate = $('#beginDate').datebox('getValue');
    	var endDate = $('#endDate').datebox('getValue');  
    	var queryType = $('#queryType').combobox("getValue");
    	var queryText= $('#queryText').val();
    	table.datagrid({
        	queryParams: {
        		spell: spell,
        		flow: flow,
        		type: type,
        		workStatu:statu,
        		dateStatu:dateStatu,
        		beginDate:beginDate,
        		endDate:endDate,
        		queryType:queryType,
        		queryText:queryText
        	}
        });
    });
    $(".datagrid").on("click", ".view", function(){
      	var id = $(this).data("id");
    	var src = "/admin/work!viewMyWork.action?id="+id;
        var ezWin = window.top.createWin({
        	title: '工作详情',
        	width:"860px",
        	height: "700px",
        	resizable: true,
        	maximizable: true
		});
        var iframe = window.top.createIframe(src);
        iframe.appendTo(ezWin);
        ezWin.window("open");
		return false;
    }).on("click", ".edit", function(){
    	var id = $(this).data("id");
    	var src = "/admin/work!editWorkCheck.action?id="+id;
        var ezWin = window.top.createWin({
        	title: '办理',
        	width:"860px",
        	resizable: true,
        	maximizable: true
		});
        var iframe = window.top.createIframe(src);
        //iframe.attr('id', '产品每日汇总列表');
        iframe.appendTo(ezWin);
        ezWin.window("open");
		return false;
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
    
    $("#flow").combobox({    
	    url:'/admin/work!getWorkFlowList.action',    
	    valueField:'id',
	    textField:'text',
	    editable:false,
	    panelMinWidth:120,
	    panelMaxWidth:200,
	    panelHeight:300
	});
    
    $("#type").combobox({    
	    url:'/admin/work!getWorkFlowTypeList.action',    
	    valueField:'id',
	    textField:'text',
	    editable:false,
	    panelMinWidth:120,
	    panelMaxWidth:200,
	    panelHeight:300
	});
    
});