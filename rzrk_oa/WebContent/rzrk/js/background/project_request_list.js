var iconBasePath = '/rzrk/js/jquery-easyui-1.4.1/themes/icons/';
var saveBtn = '';
var letterIdx = {};
var unique = {};
var indexArray = new Array();
var msg = window.top.$.messager;
var pager = {
    beforePageText: '当前第',
    afterPageText: '页，共 {pages} 页',
    displayMsg: '当前第 {from} 至 {to} 条数据，共 {total} 条数据'
};

function operations(value, rowData, index) {
    var link = "<a href='#' data-row_id='{0}' data-idx='{1}' class='view'>查看</a>";
//    link += "<em>|</em>";
//   link += "<a href='#' data-id='{0}' data-idx='{1}' class='downloadTemp'>下载模板</a>";
  //  isCheck(value, rowData, index);
    if(rowData.rowId){
        return $.format(link, rowData.rowId, index);
    }else{
    	return "";
    }
}

function getColumns() {
    var columns = [[

        ]];
    
    for(var i=0;i<window.contractCategoryTitles.length;i++){
    	columns[0].push({
    		title: window.contractCategoryTitles[i], field: window.contractCategoryTitles[i], width: 100, align: 'left',sortable : true
    	});
    	
    }
    columns[0].push(
            {title: "操作", field: "operation", width: 120, align: 'center', formatter: operations}
        );
    columns[0].push(
            {title: "创建人", field: "createAdminName", width: 120, align: 'left'}
        );
    columns[0].push(
            {title: "创建时间", field: "createDate", width: 120, align: 'center'}
        );
    columns[0].push(
            {title: "最后修改人", field: "modifyAdminName", width: 120, align: 'left'}
        );
    columns[0].push(
            {title: "最后修改时间", field: "modifyDate", width: 120, align: 'center'}
        );
    return columns;
}

function getDataGridHeight(){
	var searchBarHeight = $('.searchBar').outerHeight();
	var winHeight = $(window).outerHeight();
	return winHeight - searchBarHeight - 25;
}

function getConfig(){
	var queryContentStr = JSON.stringify(window.preQuery, null, 4);
    return {
        loadMsg: '正在获取数据......',
        url: '/admin/contract!ajaxGetList.action?contractCategoryId='+window.contractCategoryId+'&projectId='+window.projectId,
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
        pageSize: 50,
        pageList: [50, 100, 500],
        pageNumber: 1,
        columns: getColumns(),
        data: {"rows":[],"total":0,"footer":[]},
        queryParams: {
    		preQuery : queryContentStr
    	},
    	onLoadSuccess:function(data){
    		var arr = [];
    		var totalItem = {"total":true};
    		for(var i=0;i<window.contractCategoryTitles.length;i++){
    	    	if(i==0){
    	    		
    	    	}else{
    	    		if(window.contractCategoryTotals.indexOf(window.contractCategoryTitles[i])!=-1){
        	    		var recSum=0;
        	    		for(var ix in data.rows){
        	    			var num = 1*(data.rows[ix][window.contractCategoryTitles[i]]);
        	    			if(!isNaN(num)){
            	    			recSum += num;
        	    			}
        	    		}
        	    		totalItem[window.contractCategoryTitles[i]]=recSum;
    	    		}
    	    	}
    	    	
    	    }
    	    arr.push(totalItem);
    		$('#table').datagrid('reloadFooter',arr);
    		
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




function initEvents(){
	
    var table = $('#table');
	closeBtn = $("#closeBtn");
	closeBtn.unbind('click').click(function(){
		window.top.closeCurrentWindow();
		_this.removeClass('disabled').linkbutton({iconCls: 'icon-save'});
	});
    

   
  
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
		    		params['queryHistory.name']=name;
		    		params['queryHistory.contractCategory.id']=window.contractCategoryId;
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
    	var url = "/admin/contract!view.action?rowId="+$(this).data("row_id")+"&contractCategoryId="+window.contractCategoryId+"&id="+window.contractCategoryId;
        var ezWin = window.top.createWin({ title: "查看" });
        ezWin.attr("id","addContractWindow");
        var iframe = window.top.createIframe(url);
        iframe.appendTo(ezWin);
        ezWin.window("open");
    	return false;
    })
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
    ;
    $(window).resize(function(){
        table.datagrid('resize', {
            height: getDataGridHeight()
        });
    });
    initEvents();
    preQueryItem();
});

