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
    var link = "<a href='#' data-row_id='{0}' data-idx='{1}' class='edit'>编辑</a>";
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
//                    {title: "", field: "isChecked", width: 80, align: 'center', checkbox:true}
//                    ,{title: window.contractData.title[0], field: window.contractData.title[0], width: 80, align: 'center'}
//                    ,{title: window.contractData.title[1], field: window.contractData.title[1], width: 80, align: 'center'}
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
//    columns[0].push(
//        {title: "操作", field: "operation", width: 120, align: 'center', formatter: operations}
//    );
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
        url: '/admin/viewlayer_show!getAjaxList.action?id='+window.id,
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

//function pagerFilter(data){
//	if (typeof data.length == 'number' && typeof data.splice == 'function'){	// is array
//		data = {
//			total: data.length,
//			rows: data
//		}
//	}
//	var dg = $(this);
//	var opts = dg.datagrid('options');
//	var pager = dg.datagrid('getPager');
//	pager.pagination({
//		onSelectPage:function(pageNum, pageSize){
//			opts.pageNumber = pageNum;
//			opts.pageSize = pageSize;
//			pager.pagination('refresh',{
//				pageNumber:pageNum,
//				pageSize:pageSize
//			});
//			dg.datagrid('loadData',data);
//		}
//	});
//	if (!data.originalRows){
//		data.originalRows = (data.rows);
//	}
//	var start = (opts.pageNumber-1)*parseInt(opts.pageSize);
//	var end = start + parseInt(opts.pageSize);
//	data.rows = (data.originalRows.slice(start, end));
//	return data;
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

//    var tmpdata=window.contractCategoryRows;
//    var keyword = $('#keyword').val();
//    var searchBy = $('#searchBy').combobox("getValue");
//    if(keyword!="" && keyword!=null){
//    	tmpdata=[];
//    	$.each(window.contractCategoryRows, function(index, object) {
//    		var objectField = object[searchBy];
//    		  if(objectField!=null && objectField.indexOf(keyword)!=-1){
//    			  tmpdata.push(object);
//    		  }
//    	});
//    }
//    
//    $('#table').datagrid('loadData',tmpdata)
}



function ajaxFileUpload(){
	$.messager.progress({
		"title":"",
		"msg":"导入中，请等待...",
		"text":""
	});
	var id = $("#updateContract").attr('id');
	var url = $("#updateContract").data('url');
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
    					window.top.reloadTabContent(window.contractCategoryName);
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
			var inhtml = $("#updateContract").get(0).outerHTML;
			$("#updateContract").replaceWith(inhtml);
            $("#updateContract").unbind('change').change(ajaxFileUpload);
            $.messager.progress('close');
		},
		error: function (data, status, e){
			msg.alert("上传文件", "上传文件失败：" + e, "error");
			var inhtml = $("#updateContract").get(0).outerHTML;
			$("#updateContract").replaceWith(inhtml);
            $("#updateContract").unbind('change').change(ajaxFileUpload);
            $.messager.progress('close');
		}
	});
}

//弹出创建工作
function createWok(fieldId,isDelete){
	var src = "/admin/work!addWork.action?fieldId="+fieldId+"&isDelete="+isDelete;
    var ezWin = window.top.createWin({
    	title: '创建工作',
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


function ajaxFile(){
	
	var id = $("#updateContract").attr('id');
	var url = $("#updateContract").data('url');
	//url += "&userId="+str;
	$.messager.progress({
		"title":"",
		"msg":"导入中，请等待...",
		"text":""
	});
	$.ajaxFileUpload({
		url: url,
		secureuri:false,
		fileElementId:id,
		dataType: 'json',
		beforeSend:function(){
		},
		success: function (data, status){
            if(data.status == "success"){
            	if(data.isCheck == 1){//需要审核
					//createWok(data.fieldId,2);
					msg.alert('工作审批', '请进行工作审批！', 'info',function(){
						try{
	    					window.top.reloadTabContent(window.contractCategoryName);
						}catch(e){
							location.reload();
						}
					})
				}else{
					msg.alert("上传文件", "上传文件成功！", "info", function(){
						try{
	    					window.top.reloadTabContent(window.contractCategoryName);
						}catch(e){
							location.reload();
						}
					});
				}
            	
					
			
             }else{
            	 var arr = data.message.split("\n",21)
            	 var _arr = arr.slice(0,20);
            	 var emsg = _arr.join("<br>");
            	 if(arr.length > 20){
            		 emsg += "<br>..."
            	 }
 				msg.alert("上传文件", "上传文件失败：<br>" + emsg, "error");
             }
			var inhtml = $("#updateContract").get(0).outerHTML;
			$("#updateContract").replaceWith(inhtml);
            $("#updateContract").unbind('change').change(ajaxFileUpload);
            $.messager.progress('close');
		},
		error: function (data, status, e){
			msg.alert("上传文件", "上传文件失败：" + e, "error");
			var inhtml = $("#updateContract").get(0).outerHTML;
			$("#updateContract").replaceWith(inhtml);
            $("#updateContract").unbind('change').change(ajaxFileUpload);
            $.messager.progress('close');
		}
	});
}

function initEvents(){
	
    var table = $('#table');
    saveBtn = $("#saveBtn");
	closeBtn = $("#closeBtn");
    $("#updateContract").unbind('change').change(ajaxFile);
    $("#downloadContract").unbind('click').click(function(){
    	var url = "/admin/viewlayer_show!download.action";
		var queryContent = $(".queryDom").queryTree("getValue");
		var queryContentStr = JSON.stringify(queryContent, null, 4);
    	$("#downloadForm").find("[name='id']").val(window.id);
    	$("#downloadForm").find("[name='preQuery']").val(queryContentStr);
    	$("#downloadForm").attr("action",url).submit();
    });
    
    $("#removeContract").unbind('click').click(function(){
    	var url = "/admin/contract!delete.action";
    	var ids = [];
    	var rows = table.datagrid("getSelections");
    	if (rows.length == 0){
    		msg.alert("批量删除", "请选择要删除的记录", "info");
    		return false;
    	}
    	msg.confirm("批量删除", '是否删除选中的记录？', function(r){
    		if (!r) return false;
    		var len = rows.length;
    		for (var i = 0; i < len; i++){
    			ids.push('rowIds=' + rows[i].rowId);
    		}
        	$.messager.progress({
        		"title":"",
        		"msg":"删除中，请等待...",
        		"text":""
        	});
    		$.ajax({
			  dataType: "json",
			  url: url,
			  type : "post",
			  data: "contractCategoryId="+window.contractCategoryId+"&"+ids.join('&'),
			  success: function(data){
	    			if (data.status == 'success'){
	    				if(data.isCheck == 1){//需要审核
							//createWok(data.fieldId,1);
	    					msg.alert('工作审批', '请进行工作审批！', 'info',function(){

	    						try{
	    	    					window.top.reloadTabContent(window.contractCategoryName);
	    						}catch(e){
	    							location.reload();
	    						}
	    					})
						}else{
							msg.alert("批量删除", "批量删除成功！", "info",function(){
		    					try{
			    					window.top.reloadTabContent(window.contractCategoryName);
								}catch(e){
									location.reload();
								}
		    				});
						}
						
	    			}else{
	    				msg.alert("批量删除", "批量删除失败：" + data.message, "error");
	    			}
	                $.messager.progress('close');
	    		},
	    		error: function (data, status, e){
	    			msg.alert("批量删除", "批量删除失败：" +  e, "error");
	                $.messager.progress('close');
	    		}
			});
    	});

    });
    $("#addContract").unbind('click').click(function(){
    	var url = "/admin/contract!edit.action?rowId="+$(this).data("row_id")+"&contractCategoryId="+window.contractCategoryId;
        var ezWin = window.top.createWin({
        	title: "新增记录",
        	resizable: true,
        	maximizable: true
    	});
        ezWin.attr("id","addContractWindow");
        var iframe = window.top.createIframe(url);
        iframe.appendTo(ezWin);
        ezWin.window("open");
    	return false;
    });
    
    saveBtn.unbind('click').click(function() {
    	var str;
    	$("#userSelect input[name='adminList.id']").each(function(){
    		var temp = $(this).val();
    		 if(!(temp ==undefined || temp =="" || temp==null)){
    			 str += temp+",";
    		 }
    		
    	});
    	$("#userSelect").hide();
    	ajaxFile(str);
	});
    closeBtn.unbind('click').click(function() {
    	$("#userSelect").hide();
	});
	$("ul").on("click", "li:not(.addColleague) span", function() {
		var _this = $(this);
		delete unique[_this.next().val()];
		_this.parents("li").remove();
	});
	$(".addColleague a").click(function() {
		$(this).hide();
		$(this).next().next().show();
	});
	$("#updateContract").unbind('mouseenter').mouseenter(function(){
    	msg.show({
            title:'警告',
            msg:'导入的excel"'+window.contractCategoryTitles[0]+'"列不能重复',
            timeout:5000,
            showType:'slide'
        });
    });
    $(".importRecords").click(function(){
    	
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
};

function initDicts(users) {
	$.each(users, function(i, user) {
		letterIdx[user.name] = CTL.convert(user.name).toString();
	});
}
function filterName(q, row) {
	var opts = $(this).combobox('options');
	if (row[opts.textField].toLocaleLowerCase().indexOf(q.toLocaleLowerCase()) != -1) {
		return true;
	}
	if (letterIdx[row[opts.textField]].toLocaleLowerCase().indexOf(
			q.toLocaleLowerCase()) != -1) {
		return true;
	}
	return false;
}

function selectUser(user) {
	var lastLi = $(".addColleague");
	var btn = lastLi.find("a");
	var comboBox = btn.next();
	var li = '<li class="deleteAdminId">{0}<span>X</span><input type="hidden"  name="adminList.id" value="{1}"/></li>';
	li = $.validator.format(li, user.name, user.id);
	if (!unique[user.id]) {
		$(li).insertBefore(lastLi);
	}
	unique[user.id] = true;
	btn.show();
	comboBox.combobox("setValue", "");
	comboBox.next().hide();
}

function initData() {
	var url = "/admin/admin!getAllAjaxList.action";
	$.get(url, function(data) {
		initDicts(data.rows);
		$(".userBox").combobox({
			filter : filterName,
			valueField : "id",
			textField : "name",
			data : data.rows,
			onSelect : selectUser,
			onLoadSuccess : function() {
				if ($(".addColleague a").is(":hidden"))
					return;
				$(this).next().hide();
			}
		});
	}, "json");
}




  function createUsed(){
         $("#userSelect").window("open");
  } 
  
  function preQueryItem(){
	    $(".queryDom").queryTree({
			searchByData:window.queryOption,
			preData:window.preQuery
	    });
  }

  initData();
  
  

 
$(function(){
	
    var table = $('#table');
    table.datagrid(getConfig());
    table.datagrid('getPager').pagination(pager);
// table.datagrid(getConfig()).datagrid({loadFilter:pagerFilter}).datagrid('loadData',window.contractCategoryRows);
//    $('#table').datagrid("getPager").pagination({
//    	onBeforeRefresh:function(pageNumber, pageSize){
//        	location.reload();
//    	}
//    });
    $(".datagrid").on("click", ".edit", function () {
    	var url = "/admin/contract!edit.action?rowId="+$(this).data("row_id")+"&contractCategoryId="+window.contractCategoryId+"&id="+window.contractCategoryId;
        var ezWin = window.top.createWin({ title: "编辑" });
        ezWin.attr("id","addContractWindow");
        var iframe = window.top.createIframe(url);
        iframe.appendTo(ezWin);
        ezWin.window("open");
    	return false;
    })
    
//    $('#table').parents(".datagrid").find("a:has(.pagination-loading,.pagination-load)").click(function(){location.reload();});
    ;
    $(window).resize(function(){
        table.datagrid('resize', {
            height: getDataGridHeight()
        });
    });
    initEvents();
    preQueryItem();
    
//    $(".qselect").combobox({
//    	textField: 'label',
//    	valueField: 'value',
//    	data:window.queryOption
//    }).combobox("select",window.queryOption[0].value);
});

