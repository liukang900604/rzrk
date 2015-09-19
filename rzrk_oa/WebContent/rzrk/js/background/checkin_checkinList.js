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
function getColumns() {
    var columns = [[
            //{title: "ID", field: "id", width: 100, align: 'center', sortable: true},
            {title: "姓名", field: "name", width: 100, align: 'center', sortable: true},
            {title: "日期", field: "checkinDate", width: 140, align: 'center', sortable: true},
            {title: "上班时间", field: "checkinTime", width: 140, align: 'center', sortable: true},
            {title: "下班时间", field: "checkoutTime", width: 140, align: 'center', sortable: true},
            {title: "上班打卡位置", field: "checkinLocation", width: 180,  align: 'center', sortable: true},
            {title: "下班打卡位置", field: "checkoutLocation", width: 180, align: 'center', sortable: true},
            {title: "状态", field: "checkStatus", width: 100, align: 'center', sortable: true},
            {title: "迟到（小时）", field: "lateHours", width: 100, align: 'center', sortable: true},
            {title: "矿工（天）", field: "absenteeism", width: 100, align: 'center', sortable: true},
            {title: "未刷卡次数", field: "forgetCheckTime", width: 100, align: 'center', sortable: true},
            {title: "加班（小时）", field: "overtimes", width: 100, align: 'center', sortable: true}
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
        url: 'checkin!getAjaxList.action',
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
    var search = $('#search');
    $('#searchBy').combobox({
		textField: 'key',
    	valueField: 'value',
		data:[{
			key: '姓名',
			value: 'name'
		},{
			key: '日期',
			value: 'checkinDate'
		}],
		editable:false,
    	onSelect: function(param){
    		var keyword = $("#keyword");
    		if (param.value == 'name'){
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
    }).combobox("select", "name");
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