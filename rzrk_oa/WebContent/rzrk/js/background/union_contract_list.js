var iconBasePath = '/rzrk/js/jquery-easyui-1.4.1/themes/icons/';
var saveBtn = '';
var letterIdx = {};
var unique = {};
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
    var link = "<a href='#' data-row_id='{0}' data-idx='{1}' class='view'>查看</a>";
//    link += "<em>|</em>";
//   link += "<a href='#' data-id='{0}' data-idx='{1}' class='downloadTemp'>下载模板</a>";
    if(rowData.rowId){
        return $.format(link, rowData.rowId, index);
    }else{
    	return "";
    }
}

function operationsExp(f,exp){
	var field = f;
	var expression = exp;
	return function(value, rowData, index) {
		if(rowData["total"]){
			return value;
		}else{
			var ex = expression.replace(/\$\((.+?)\)/g, "(1*rowData['$1'])");
			var result = eval(ex);
			if(!isNaN(result)){
				rowData[field]=result;
				return result ;
			}else{
				return "";
			}
		}
	}
}


function getColumns() {
    var columns = [[
        ]];
    
    for(var i=0;i<window.definition.length;i++){
    	if(window.definition[i].type==1){
        	columns[0].push({
        		title: "<span title='"+ window.definition[i].expression +"'>"+window.definition[i].showField+"<span>", 
        		field: window.definition[i].showField, width: 100, align: 'left',sortable : false 
//        		formatter: operationsExp(window.definition[i].showField,window.definition[i].expression)
        	});
    	}else{
        	columns[0].push({
        		title: window.definition[i].showField, field: window.definition[i].showField, width: 100, align: 'left',sortable : true
        	});
    	}
    }
//	columns[0].push({title: "操作", field: "operation", width: 120, align: 'center', formatter: operations});
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
	var queryContentStr = JSON.stringify(window.preQuery, null, 4);
    return {
        loadMsg: '正在获取数据......',
        url: '/admin/union_contract!getAjaxList.action?unionContractCategoryId='+window.unionContractCategoryId,
        method: 'post',
        width: '100%',
        rownumbers: true,
        height: getDataGridHeight(),
        singleSelect: false,
        selectOnCheck: true,
        checkOnSelect: true,
        autoRowHeight: false,
        remoteSort: true,
        showFooter: true,
        pagination: true,
        pageSize: getPageSize(),
        pageList: [50, 100, 500],
        pageNumber: 1,
        columns: getColumns(),
        data: {"rows":[],"total":0,"footer":[]},
        queryParams: {
//    		sort : "create_date",
//    		order : "desc",
    		preQuery : queryContentStr
    	},
    	onLoadSuccess:function(data){
    		var arr = [];
    		var totalItem = {"total":true};
    	    for(var i=0;i<window.definition.length;i++){
    	    	if(i==0){
    	    		totalItem[window.definition[i].showField]="总计";
    	    	}else{
    	    		var showField = window.definition[i].showField;
        	    	if(window.definition[i].total){
        	    		var recSum=0;
        	    		for(var ix in data.rows){
        	    			var num = 1*(data.rows[ix][window.definition[i].showField]);
        	    			if(!isNaN(num)){
            	    			recSum += num;
        	    			}
        	    		}
        	    		totalItem[window.definition[i].showField]=recSum;
        	    	}
    	    	}
    	    	
    	    }
    	    arr.push(totalItem);
    		$('#table').datagrid('reloadFooter',arr);
    	}
    };
}

;
//}

function doSearch(value){
    var table = $('#table');
    table.datagrid({
    	queryParams: {
//    		sort : "create_date",
//    		order : "desc",
    		preQuery : value
//    		searchBy: $('#searchBy').combobox("getValue"),
//    		keyword: value
    	}
    });


}


function _initEvents(){
	
    var table = $('#table');
    $("#saveQueryHistory").unbind('click').click(function(){
    	if($("#keyword").val()==""){
    		msg.alert("保存查询历史", "没有查询条件！", "warn");
    		return;
    	}else{
    		msg.prompt('保存', '历史查询名称', function(name){
    			if (name===undefined){
    				//这个是取消。。。
				}else if (name===""){
		    		msg.alert("保存查询历史", "您没有填写名称！", "warn");
				}else{
		    		var queryContent = $(".queryDom").queryTree("getValue");
//		    		queryContent.searchBy = $('#searchBy').combobox("getValue");
//		    		queryContent.keyword = $("#keyword").val();
		    		var queryContentStr = JSON.stringify(queryContent);
		    		
		    		var params = {};
		    		params['queryHistory.type']=1;
		    		params['queryHistory.name']=name;
		    		params['queryHistory.viewlayer.id']=window.id;
		    		params['queryHistory.content']=queryContentStr;
		    		
		    		$.post("/admin/query_history!save.action", 
		    				params,
			    		function(data) {
			    			if(data.status=="success"){
				    	    	msg.show({
				    	            title:'通知',
				    	            msg:'保存查询历史成功',
				    	            timeout:5000,
				    	            showType:'slide'
				    	        });
			    			}else{
				    	    	msg.alert('错误', data.message, "error");
			    			}
			    		}, "json");
				}
			});
    	}
    });
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
    $(".datagrid").on("click", ".view", function () {
    	var idx = $(this).data("idx");
        var item = table.datagrid("getData").rows[idx];
        for(var key in item){
        	$(".viewValue."+key).text(item[key]);
        }
        $(".viewTable").dialog('open');
    	return false;
    });
    
    $("#downloadContract").unbind('click').click(function(){
    	var url = "/admin/union_contract!download.action";
		var queryContent = $(".queryDom").queryTree("getValue");
		var queryContentStr = JSON.stringify(queryContent, null, 4);
    	$("#downloadForm").find("[name='unionContractCategoryId']").val(window.unionContractCategoryId);
    	$("#downloadForm").find("[name='preQuery']").val(queryContentStr);
    	$("#downloadForm").attr("action",url).submit();
    });

};

  function preQueryItem(){
	    $(".queryDom").queryTree({
			searchByData:window.queryOption,
			preData:window.preQuery
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
    _initEvents();
    preQueryItem();
    
});

