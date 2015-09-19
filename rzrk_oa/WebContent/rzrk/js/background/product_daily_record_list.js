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
function ajaxFileUpload(){
	/*
	$("#loading").ajaxStart(function(){
		$(this).show();
	}).ajaxComplete(function(){
		$(this).hide();
	});
	*/
	var id = $(this).attr('id');
	var url = $(this).data('url');
	$.ajaxFileUpload({
		url: url,
		secureuri:false,
		fileElementId:id,
		dataType: 'json',
		beforeSend:function(){
			//...
		},
		success: function (data, status){
            if(data.status == "success"){
				msg.alert("上传文件", "上传文件成功！", "info", function(){
					window.top.reloadDataGridInTab('产品每日汇总列表');
				});
             }else{
 				msg.alert("上传文件", "上传文件失败：" + data.message, "error");
             }
            var inhtml = $("#"+id).get(0).outerHTML;
            $("#"+id).replaceWith(inhtml);
            $("#"+id).unbind('change').change(ajaxFileUpload);
		},
		error: function (data, status, e){
			msg.alert("上传文件", "上传文件失败：" + e, "error");
            var inhtml = $("#"+id).get(0).outerHTML;
            $("#"+id).replaceWith(inhtml);
            $("#"+id).unbind('change').change(ajaxFileUpload);
		}
	});
	return false;
}
function operations(value, rowData, index) {
	if (!rowData.id) return '';
	var link = "";
	if (window.isPC){
		link = "<a href='#' data-id='{0}' data-idx='{1}' data-src='/admin/product_daily_record!trend.action?productId=' data-name='{2}' class='netValue'>净值</a>";
	}else{
		link = "<a href='/admin/product_daily_record!trend.action?productId={0}' target='_blink' class='netValue'>净值</a>";
	}
    
    /*
    link += "<em>|</em>";
    link += "<a href='#' data-id='{3}' data-idx='{1}' class='edit'>编辑</a>";
    */
    return $.validator.format(link, rowData.productId, index, rowData.productName);
}
function renderIsPublication(value, rowData, index){
	return value ? "<span style='color:green;'>是</span>" : "<span style='color:red;'>否</span>";
}
function renderIsRecommend(value, rowData, index){
	return value ? "<span style='color:green;'>是</span>" : "<span style='color:red;'>否</span>";
}
function getColumns() {
    var columns = [[
            {title: "产品历史名称", field: "productName", width: 200, align: 'center', sortable: true},
            {title: "证券账户总资产", field: "stockAccountTotalAmount", width: 120, align: 'center', sortable: true},
            {title: "期货账户总资产", field: "futureAccountTotalAmount", width: 120, align: 'center', sortable: true},
            {title: "股票总市值", field: "stockMarketValue", width: 120, align: 'center', sortable: true},
            {title: "空单总市值", field: "futureEmptyValue", width: 120, align: 'center', sortable: true},
            //{title: "合计", field: "sum", width: 120, align: 'center', sortable: true},
            {title: "银行存款", field: "bankAmount", width: 120, align: 'center', sortable: true},
            {title: "总资产", field: "total", width: 120, align: 'center', sortable: true},
            //{title: "未扣除各项费用总资产", field: "beforeReduce", width: 120, align: 'center', sortable: true},
            {title: "份额", field: "amount", width: 120, align: 'center', sortable: true},
            {title: "扣费净值", field: "reduceNetValue", width: 120, align: 'center', sortable: true},
            //{title: "未扣除各项费用净值", field: "beforeReduceNetValue", width: 120, align: 'center', sortable: true},
            //{title: "估值表净值", field: "assetsNetValue", width: 120, align: 'center', sortable: true},
            {title: "日期", field: "recordDate", width: 120, align: 'center', sortable: true},
            {title: "风险暴露度", field: "riskRate", width: 120, align: 'center', sortable: true},
            {title: "赎回金额", field: "repay", width: 120, align: 'center', sortable: true},
            {title: "申购金额", field: "buy", width: 120, align: 'center', sortable: true},
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
        text:'添加净值',
        iconCls:'icon-add',
        handler:function(){alert('add');}
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
        url: '/admin/product_daily_record!getAjaxList.action',
        method: 'get',
        width: '100%',
        //toolbar:getToolbar(),
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
        data: []
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
    if (window.isPC){
        $(".datagrid").on("click", ".netValue", function(){
        	var id = $(this).data("id");
    		var src = $(this).data("src") + id;
    		var name = $(this).data("name");
            var ezWin = window.top.createWin({ title: name });
            var iframe = window.top.createIframe(src);
            iframe.attr('id', '产品每日汇总列表');
            iframe.appendTo(ezWin);
            ezWin.window("open");
    		return false;
        });
    }
    
    //净值导出EXCEL    
    $("#export").unbind('click').click(function(){
    	var url = "/admin/product_daily_record!equity.action";
        var ezWin = window.top.createWin({ title: "净值导出",
        	width: 360,
        	height: 340,
        });
        var iframe = window.top.createIframe(url);
        iframe.appendTo(ezWin);
        ezWin.window("open");
    });
    
    $('#addNetValue').unbind('click').click(function(){
    	var url = '/admin/product_daily_record!add.action';
        var ezWin = window.top.createWin({
        	title: "添加净值",
        	width: 500,
        	height: 628,
        	resizable: true,
        	maximizable: false
        });
        var iframe = window.top.createIframe(url);
        iframe.appendTo(ezWin);
        ezWin.window("open");
    });
    $('#searchBy').combobox({
		textField: 'key',
    	valueField: 'value',
		data:[{
			key: '产品名称',
			value: 'productName'
		},{
			key: '日期',
			value: 'recordDate'
		}],
		editable:false,
    	onSelect: function(param){
    		var keyword = $("#keyword");
    		if (param.value == 'productName'){
    			keyword.css('width', '300px').searchbox({
    				prompt: '请输入搜索关键字',
    				searcher: doSearch
    			});
    			search.hide();
    		}else{
        		keyword.css('width', '120px').datebox({
        			formatter: dateFormatter,
    				parser: dateParser,
    				editable:false,
    				prompt: ''
    			});
        		search.show();
    		}
    	}
    }).combobox("select", "productName");
    search.unbind('click').click(function(){
    	var searchBy = $('#searchBy').combobox("getValue");
    	var keyword = $('#keyword').datebox('getValue');
        table.datagrid({
        	queryParams: {
        		searchBy: searchBy,
        		keyword: keyword
        	}
        });
    });
    $('.fileUpload').unbind('change').change(ajaxFileUpload);
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