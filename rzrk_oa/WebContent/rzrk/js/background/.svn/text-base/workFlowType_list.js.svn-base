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
	//不能删除
	if(rowData.isDelete == '2'){
		  var link = "";
		  return link;
	}else{
		  var link = "<a href='#' data-id='{0}' data-idx='{1}' class='edit'>编辑</a><em>|</em><a href='#' data-id='{0}' data-idx='{1}' class='delete'>删除</a>";
	}
  
    return $.format(link, rowData.id, index);
}


function getColumns() {
    var columns = [[
                    {title: "类型名称", field: "typeName", width: 500, align: 'center', sortable: true},
                    {title: "操作", field: "operation", width: 400, align: 'center', formatter: operations}
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
        url: '/admin/work_flow!getAjaxWorkFlowTypeList.action',
        method: 'get',
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
        onDblClickRow:function(index,rowData){
        	var url = "/admin/work_flow!editWorkFlowType.action?id="+rowData.id;
            var ezWin = window.top.createWin({ title: "编辑" });
            var iframe = window.top.createIframe(url);
            iframe.appendTo(ezWin);
            ezWin.window("open");
        	return false;
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



function initEvents(){
	
    var table = $('#table');
    $("#addWorkFlowType").unbind('click').click(function(){
    	var url = "/admin/work_flow!addWorkFlowType.action";
        var ezWin = window.top.createWin({ title: "新增类型" });
        var iframe = window.top.createIframe(url);
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
    $(".datagrid").on("click", ".edit", function () {
    	var url = "/admin/work_flow!editWorkFlowType.action?id="+$(this).data("id");
        var ezWin = window.top.createWin({ title: "编辑" });
        var iframe = window.top.createIframe(url);
        iframe.appendTo(ezWin);
        ezWin.window("open");
    	return false;
    })
      $(".datagrid").on("click", ".delete", function () {
    	  var id = $(this).data("id");
    	  $.messager.confirm("确认", "你确定要删除吗?", function (r) {  
     		 if(r){
     			$.ajax({    
     	              type:'post',        
     	              url: '/admin/work_flow!deleteFlowType.action',    
     	              data: {"id":id},    
     	              cache:false,    
     	              dataType:'json',    
     	              success:function(data){    
     	               if(data.status == 'success'){
     	            	   msg.alert('删除信息',data.message,'info',function(){
     	            		   window.location.reload();
     	            	   });
     	            	 
     	               }else{
     	            	   msg.alert('删除信息',data.message,'info',function(){
     	            		   return false;
     	            	   });
     	            	   
     	               }
     	              } 
     	                 
     	            })
     		 }
       })
    	  
    })
    initEvents();
});

