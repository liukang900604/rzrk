var msg = window.top.$.messager;
var iconBasePath = '/rzrk/js/jquery-easyui-1.4.1/themes/icons/';
var pager = {
    beforePageText: '当前第',
    afterPageText: '页，共 {pages} 页',
    displayMsg: '当前第 {from} 至 {to} 条数据，共 {total} 条数据'
};
var initChartsOptions = function(){
    Highcharts.setOptions({
        lang:{
            weekdays: ['星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六'],
            months: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'],
            shortMonths: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一', '十二']
        }
    });
};
var seriesOptions = [],
    seriesCounter = 0,
    names = [[window.productId, '']];
var createChart = function(){
    $('.chart-content').highcharts('StockChart', {
        chart: {
            width: $(window).width() - 30,
            height: $(window).height() - 50

        },
        colors: [ '#ff0000','#2f7ed8'],
        credits: { enabled: false },
        rangeSelector : { enabled: false },
        yAxis: {
            labels: {
                formatter: function() {
                    // 非常规处理nerve 由初始值设置了0.001引起
                    return this.value;
                }
            },
            plotLines: [{
                value: 0,
                width: 2,
                color: 'silver'
            }]
        },
        tooltip: {
            pointFormat: '<b>{point.y}</b><br/>',
            valueDecimals: 4
        },
        series: seriesOptions,
        scrollbar: {
            enabled: false
        },
        navigator: {
        	enabled: false
        }
    });
};
var loadChart = function(){
    $.each(names, function(i, name) {
        $.getJSON('product_daily_record!trendChart.action?productId=' + window.productId, function(data){
            seriesOptions[i] = {
                name: name[i,1],
                data: data
            };
            seriesCounter++;
            if (seriesCounter == names.length) {
                createChart();
            }
        });
    });
    if ($('.chart-content').children().size() == 0) {
        $('.chart-content').html("<p style='margin: 10px;'>暂无数据</p>");
    }
};
function operations(value, rowData, index) {
	if (!rowData.id) return '';
    var link = "<a href='#' data-id='{0}' data-idx='{1}' class='edit'>编辑</a>";
    link += "<em>|</em>";
    link += "<a href='#' data-id='{0}' data-idx='{1}' class='del'>删除</a>";
    return $.validator.format(link, rowData.id, index);
}
function renderIsPublication(value, rowData, index){
	return value ? "<span style='color:green;'>是</span>" : "<span style='color:red;'>否</span>";
}
function renderIsRecommend(value, rowData, index){
	return value ? "<span style='color:green;'>是</span>" : "<span style='color:red;'>否</span>";
}
function getDataGridHeight(){
	var searchBarHeight = $('.searchBar').outerHeight();
	var winHeight = $(window).outerHeight();
	return winHeight - searchBarHeight - 55;
}
function addNetValue(){
	var url = '/admin/product_daily_record!add.action?productId=' + window.productId;
    var ezWin = window.top.createWin({
    	title: "添加净值",
    	width: 500,
    	height: 598,
    	resizable: true,
    	maximizable: false
    });
    var iframe = window.top.createIframe(url);
    iframe.appendTo(ezWin);
    ezWin.window("open");
}
function bulkDelete(){
    var table = $('#table');
	var url = "/admin/product_daily_record!delete.action";
	var ids = [];
	var rows = table.datagrid("getSelections");
	if (rows.length == 0){
		msg.alert("批量删除", "请选择要删除的净值", "info");
		return false;
	}
	msg.confirm("批量删除", '是否删除选中的净值？', function(r){
		if (!r) return false;
		var len = rows.length;
		for (var i = 0; i < len; i++){
			ids.push("ids=" + rows[i].id);
		}
		$.get(url, ids.join('&'), function(data){
			if (data.status == 'success'){
				msg.alert("批量删除", "批量删除成功！", "info");
				window.top.reloadDataGridInTab('产品每日汇总列表');
				window.top.reloadDataGridInWindow('产品每日汇总列表');
			}else{
				msg.alert("批量删除", "批量删除失败：" + data.message, "error");
			}
		}, 'json');
	});
}
function getToolbar(){
    var toolbar = [{
        text:'添加净值',
        iconCls:'icon-add',
        handler: addNetValue
    },{
        text:'批量删除',
        iconCls:'icon-remove',
        handler: bulkDelete
    }];
    return toolbar;
}
function getConfig(){
    return {
        loadMsg: '正在获取数据......',
        url: '/admin/product_daily_record!getAjaxList.action?searchBy=productId&keyword=' + window.productId,
        method: 'get',
        width: '100%',
        toolbar:getToolbar(),
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
        data: []
    };
}
function getColumns() {
    var columns = [[
        {title: "", field: "isChecked", width: 80, align: 'center', checkbox:true},
        //{title: "产品历史名称", field: "productName", width: 200, align: 'center', sortable: true},
        {title: "证券账户总资产", field: "stockAccountTotalAmount", width: 80, align: 'center', sortable: true},
        {title: "期货账户总资产", field: "futureAccountTotalAmount", width: 80, align: 'center', sortable: true},
        {title: "股票总市值", field: "stockMarketValue", width: 80, align: 'center', sortable: true},
        {title: "空单总市值", field: "futureEmptyValue", width: 80, align: 'center', sortable: true},
        {title: "合计", field: "sum", width: 80, align: 'center', sortable: true},
        {title: "银行存款", field: "bankAmount", width: 80, align: 'center', sortable: true},
        {title: "总资产", field: "total", width: 80, align: 'center', sortable: true},
        {title: "未扣除各项费用总资产", field: "beforeReduce", width: 80, align: 'center', sortable: true},
        {title: "份额", field: "amount", width: 80, align: 'center', sortable: true},
        {title: "扣费净值", field: "reduceNetValue", width: 80, align: 'center', sortable: true},
        //{title: "未扣除各项费用净值", field: "beforeReduceNetValue", width: 80, align: 'center', sortable: true},
        //{title: "估值表净值", field: "assetsNetValue", width: 80, align: 'center', sortable: true},
        {title: "日期", field: "recordDate", width: 80, align: 'center', sortable: true},
        {title: "风险暴露度", field: "riskRate", width: 80, align: 'center', sortable: true},
        {title: "操作", field: "operation", width: 80, align: 'center', formatter: operations}
    ]];
    return columns;
}
function initEvents(){
    $(".datagrid").on("click", ".edit", function () {
    	var url = "/admin/product_daily_record!edit.action?id=";
    	var id = $(this).data("id");
        var ezWin = window.top.createWin({
        	title: "编辑",
        	width: 500,
        	height: 562,
        	resizable: true,
        	maximizable: false
    	});
        var iframe = window.top.createIframe(url + id);
        iframe.appendTo(ezWin);
        ezWin.window("open");
    	return false;
    }).on("click", ".del", function () {
    	var url = "/admin/product_daily_record!delete.action";
    	var ids = [];
    	ids.push("ids=" + $(this).data('id'));
    	msg.confirm('删除净值', '确认删除净值？', function(r){
    		if (!r) return false;
    		$.get(url, ids.join('&'), function(data){
    			if (data.status == 'success'){
    				msg.alert("删除净值", "删除净值成功！", "info");
    				window.top.reloadDataGridInTab('产品每日汇总列表');
					window.top.reloadDataGridInWindow('产品每日汇总列表');
    			}else{
    				msg.alert("删除净值", "删除净值失败：" + data.message, "error");
    			}
    		}, 'json');
    	});
    });
}
$(function(){
	initChartsOptions();
	loadChart();
	$(window).resize(function(){
        createChart();
	});
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