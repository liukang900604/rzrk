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
function operations(value, rowData, index) {
    var link = "";
    if(!rowData.isUrlView){
        link += "<a href='#' data-id='{0}' data-idx='{1}' data-title_name='{2}' class='record_list'>记录列表</a>";
        link += "<em>|</em>";
    }
    link += "<a href='#' data-id='{0}' data-idx='{1}' class='edit'>编辑</a>";
    if(!rowData.isUrlView){
        link += "<em>|</em>";
        link += "<a href='#' data-id='{0}' data-idx='{1}' class='downloadTemp'>下载模板</a>";
    }
    return $.format(link, rowData.id, index,rowData.name);
}
function operationTemplate(value, rowData, index) {
    if(!rowData.isUrlView){
    	return value
    }
    return "";
}
function saveCookie(columns) {
	var columnKeys = [];
	$.each(columns[0],function(i, col){
		columnKeys.push(col);
	});
	$.cookie("columns_"+key,JSON.stringify(columnKeys),{expires:360});
}
function getColumns() {
    var columns = [[
            {title: "", field: "isChecked", width: 80, align: 'center', checkbox:true},
            {title: "模板名", field: "name", width: 200, align: 'center', sortable: true},
            {title: "一级目录", field: "rootContractCategoryName", width: 200, align: 'center', sortable: true},
            {title: "是否审批", field: "isApprovalNeeded", width: 100, align: 'center',formatter:operationTemplate},
            {title: "模板结构", field: "fields", width: 800, align: 'left',formatter:operationTemplate},
            {title: "操作", field: "operation", width: 200, align: 'center', formatter: operations}
        ]];
    
    var columnKeys = $.cookie("columns_"+key);
	if (columnKeys != null){
		var newColumns = [];;
		columnKeys = JSON.parse(columnKeys);
		$.each(columnKeys,function(i,col){
			newColumns.push(col);
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
        url: '/admin/contract_category!ajaxGetList.action',
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
        onResizeColumn: function(field, width){
        	var opts = $(this).datagrid('options');
        	saveCookie(opts.columns);
        },
        onDblClickRow:function(index,rowData){
        	var url = "/admin/contract_category!edit.action?id=";
        	var id = rowData.id;
            var ezWin = window.top.createWin({ title: "编辑二级分类" });
            ezWin.attr("id","编辑二级分类");
            var iframe = window.top.createIframe(url + id);
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

function ajaxFileUpload(){
	$.messager.progress({
		"title":"",
		"msg":"导入中，请等待...",
		"text":""
	});
	var id = $(this).attr('id');
	var url = $(this).data('url');
	$.ajaxFileUpload({
		url: url,
		secureuri:false,
		fileElementId:id,
		dataType: 'json',
		beforeSend:function(){
		},
		success: function (data, status){
            if(data.status == "success"){
				msg.alert("上传文件", "上传文件成功！", "info", function(){
				try{
					window.top.reloadDataGridInTab('二级分类');
				}catch(e){
					location.reload();
				}
				});
             }else{
            	 var arr = data.message.split("\n",21)
            	 var _arr = arr.slice(0,20);
            	 var emsg = _arr.join("<br>");
            	 if(arr.length > 20){
            		 emsg += "<br>..."
            	 }
 				msg.alert("上传文件", "上传文件失败：<br>" + emsg, "error");
             }
			var inhtml = $("#uploadContractCategoryAndData").get(0).outerHTML;
			$("#uploadContractCategoryAndData").replaceWith(inhtml);
            $("#uploadContractCategoryAndData").unbind('change').change(ajaxFileUpload);
            $.messager.progress('close');
		},
		error: function (data, status, e){
			msg.alert("上传文件", "上传文件失败：" + e, "error");
			var inhtml = $("#uploadContractCategoryAndData").get(0).outerHTML;
			$("#uploadContractCategoryAndData").replaceWith(inhtml);
            $("#uploadContractCategoryAndData").unbind('change').change(ajaxFileUpload);
            $.messager.progress('close');
		}
	});
}

function initEvents(){
/*	$("#uploadContractCategoryAndData").width($("#uploadContractCategoryAndDataBtn").width());
	$("#uploadContractCategoryAndData").offset($("#uploadContractCategoryAndDataBtn").offset());*/
    var table = $('#table');
    $(".datagrid").on("click", ".record_list", function () {
    	var id = $(this).data("id");
        var url = "/admin/contract!list.action?contractCategoryId="+id;
        var title = $(this).data("title_name");
        var opt = {
                "id": id,
                "url": url,
                "title": title+""
            }
        window.top.createOrSelectTab(opt);
    	return false;
    }).on("click", ".edit", function () {
    	var url = "/admin/contract_category!edit.action?id=";
    	var id = $(this).data("id");
        var ezWin = window.top.createWin({ title: "编辑二级分类" });
        ezWin.attr("id","编辑二级分类");
        var iframe = window.top.createIframe(url + id);
        iframe.appendTo(ezWin);
        ezWin.window("open");
    	return false;
    }).on("click", ".downloadTemp", function(){
    	var url = "/admin/contract_category!downloadTemp.action";
    	$("#downloadTempForm").find("[name='id']").val($(this).data("id"));
    	$("#downloadTempForm").attr("action",url).submit();
    	return true;
    });
    $('#addContractCategory').unbind('click').click(function(){
    	var url = '/admin/contract_category!add.action';
        var ezWin = window.top.createWin({ title: "新增二级分类" });
        ezWin.attr("id","新增二级分类");
        var iframe = window.top.createIframe(url);
        iframe.appendTo(ezWin);
        ezWin.window("open");
    });
    $('#delContractCategory').unbind('click').click(function(){
		var msg = window.top.$.messager;
    	var url = "/admin/contract_category!delete.action";
    	var ids = [];
    	var rows = table.datagrid("getSelections");
    	if (rows.length == 0){
    		msg.alert("批量删除", "请选择要删除的类型", "info");
    		return false;
    	}
    	msg.confirm("批量删除", '是否删除选中的类型？', function(r){
    		if (!r) return false;
    		var len = rows.length;
    		for (var i = 0; i < len; i++){
    			ids.push('ids=' + rows[i].id);
    		}
    		
    		$.ajax({
  			  dataType: "json",
  			  url: url,
  			  type : "post",
  			  data: ids.join('&'),
  			  success: function(data){
	    			if (data.status == 'success'){
	    				window.top.updateSubCategory();
	    				window.top.reloadDataGridInTab('二级分类');
	    				msg.alert("批量删除", "批量删除成功！", "info");
	    			}else{
	    				msg.alert("批量删除", "批量删除失败：" + data.message, "error");
	    			}
	  			},
  	    		error: function (data, status, e){
    				msg.alert("批量删除", "批量删除失败："  +e, "error");
  	    		}
  			});
    	});
    });
    
  /*  $("#uploadContractCategoryAndData").unbind('change').change(ajaxFileUpload);*/

}
$(function(){
    var table = $('#table');
    table.datagrid(getConfig());
    table.datagrid('columnMoving');
    /*table.datagrid('doCellTip',   {   
		onlyShowInterrupt:true,   
		position:'bottom',
		maxWidth:'200px',
        specialShowFields:[{field:'status',showField:'statusDesc'}],
		tipStyler:{'backgroundColor':'#DDDDFF', borderColor:'#0000C6', boxShadow:'1px 1px 3px #292929'}
    });*/
    table.datagrid('getPager').pagination(pager);
    $(window).resize(function(){
        table.datagrid('resize', {
            height: getDataGridHeight()
        });
    });
    initEvents();
});