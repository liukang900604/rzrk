var iconBasePath = '/rzrk/js/jquery-easyui-1.4.1/themes/icons/';
var saveBtn = '';
var letterIdx = {};
var unique = {};
var msg = window.top.$.messager;
var key = window.location.href;
var storage = window.localStorage;
var cmenu;
var version = window.top.version;
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

// 操作
function operations(value, rowData, index) {
    var link = "<a href='#' data-row_id='{0}' data-idx='{1}' class='edit' onclick='editInfo.call(this);'>编辑</a>&nbsp;<a href='#' data-row_id='{0}' data-idx='{1}' class='view' onclick='modifyHistory.call(this);'>修改历史</a>";
//    link += "<em>|</em>";
//   link += "<a href='#' data-id='{0}' data-idx='{1}' class='downloadTemp'>下载模板</a>";
    if(rowData.rowId){
        return $.format(link, rowData.rowId, index);
    }else{
    	return "";
    }
}

// 所属项目
function projectIdFormatter(value, rowData, index){
	if(rowData.rowId){
		if(value==null){
			value="";
		}
		if(value in belongProjectMap){
			return "<font color='#0088cc'>"+belongProjectMap[value]+"</font>"
		}else if(window.top.value==null){
			window.top.value=true;
			window.location.reload();
		}else{
			return "";
		}
	}else{
		return ""
	};
}

// 所有计划
function planIdFormatter(value, rowData, index){
	var linkPlan = "<a href='javascript:void(0)' data-row_id='{0}' data-index='{1}' class='viewPlan' onclick='viewPlan.call(this);'>对应计划</a>";
	if(rowData.rowId){
		return $.format(linkPlan, rowData.rowId, index);
	}else{
		return "";
	}
}

function editInfo(){
	var url = "/admin/contract!edit.action?rowId="+$(this).data("row_id")+"&contractCategoryId="+window.contractCategoryId+"&id="+window.contractCategoryId;
    var ezWin = window.top.createWin({ title: "编辑" });
    var randomId = ("iframe"+Math.random()).replace(".","");
    ezWin.attr("id",randomId);
    var iframe = window.top.createIframe(url);
    iframe.appendTo(ezWin);
    iframe.attr("name",randomId);
    ezWin.window("open");
    iframe.get(0).contentWindow.__windowId__ = randomId;
	return false;
}

function modifyHistory(){
	var url = "/admin/contract_log!view.action?rowId="+$(this).data("row_id")+"&contractCategoryId="+window.contractCategoryId+"&id="+window.contractCategoryId;
    var ezWin = window.top.createWin({ title: "修改历史" });
    ezWin.attr("id","viewContractLog");
    var iframe = window.top.createIframe(url);
    iframe.appendTo(ezWin);
    ezWin.window("open");
	return false;
}

function viewPlan(){
    var table = $('#table');
	var index = $(this).data("index");
	var rowId = $(this).data("row_id");
	var projectId = table.datagrid("getData").rows[index].projectId;
	if($.isEmptyObject(projectId)){
		msg.alert("警告", "该需求没有所属项目", "info");
	}else{
    	var url = "/admin/project!projectPlanList.action?id="+projectId+"&requireId="+rowId;
        var ezWin = window.top.createWin({ title: "查看计划" });
        var iframe = window.top.createIframe(url);
        iframe.attr("id", "查看计划");
        iframe.appendTo(ezWin);
        ezWin.window("open");
	}
	return false;
}

function saveCookie(columns) {
	var columnKeys = [];
	$.each(columns[0],function(i, col){
		columnKeys.push(col);
	});
	$.cookie("columns_" + key + version,JSON.stringify(columnKeys),{expires:360});
	if (storage){
		storage.setItem("columns_" + key,JSON.stringify(columnKeys));
	}
}
function createColumnDict(){
	var colsDict = {};
	colsDict["选择"] = 1;
	colsDict["最后修改时间"] = 1;
	for (var i = 0; i < window.contractCategoryTitles.length; i++){
		colsDict[contractCategoryTitles[i]] = 1;
	}
	colsDict["所属项目"] = 1;
	colsDict["对应计划"] = 1;
	colsDict["操作"] = 1;
	colsDict["创建人"] = 1;
	colsDict["创建时间"] = 1;
	colsDict["最后修改人"] = 1;
	return colsDict;
}


function getColumns() {
	var colsDict = createColumnDict();
	var fmtrDict = {
		"operation": operations,
		"projectId": projectIdFormatter,
		"planId": planIdFormatter
	}
	var editorDict = {
		"projectId": { type:'combobox', options:{ valueField:'id', textField:'text', data:window.belongProjectOpts ,filter: filterName} }
	}
	
	var columns = [[
	                {title: "选择", field: "isChecked", width: 80, align: 'center', checkbox:true},
	                {title: "最后修改时间", field: "modifyDate", width: 120, align: 'center',sortable : true}
//      ,{title: window.contractData.title[0], field: window.contractData.title[0], width: 80, align: 'center'}
//      ,{title: window.contractData.title[1], field: window.contractData.title[1], width: 80, align: 'center'}
	                ]];
	
    
    for(var i=0;i<window.contractCategoryTitles.length;i++){
    	columns[0].push({title: window.contractCategoryTitles[i], field: window.contractCategoryTitles[i], width: 100, align: 'left',sortable : true});
    }
 
	if(window.contractCategorySpecify == "REQUIREMENT_CATEGORY" ){
    	columns[0].push({title: "所属项目", field: "projectId", width: 100, align: 'left',sortable : true, formatter: projectIdFormatter});
        columns[0].push({title: "对应计划", field: "planId", width: 100, align: 'center', formatter: planIdFormatter});
        columns[0].push({title: "开发状态", field: "projectStatus", width: 100, align: 'center' });
	}
    
    columns[0].push({title: "操作", field: "operation", width: 120, align: 'center', formatter: operations});
    columns[0].push({title: "创建人", field: "createAdminName", width: 120, align: 'left',sortable : true});
    columns[0].push({title: "创建时间", field: "createDate", width: 120, align: 'center',sortable : true});
    columns[0].push({title: "最后修改人", field: "modifyAdminName", width: 120, align: 'left',sortable : true});

	if (storage){
	    var columnKeys = storage.getItem("columns_" + key + version);
		if (columnKeys != null){
			var newColumns = [];
			columnKeys = JSON.parse(columnKeys);
			$.each(columnKeys,function(i,col){
				if (colsDict[col.title]){
					if (fmtrDict[col.field]){
						col.formatter = fmtrDict[col.field];
					}
					if (editorDict[col.field]){
						col.editor = editorDict[col.field];
					}
					newColumns.push(col);
				}
			});
			columns[0] = newColumns;
		}else{
			$.each(columns[0],function(i,col){
				if (editorDict[col.field]){
					col.editor = editorDict[col.field];
				}
			})
		}
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
function createColumnMenu(){
	var dg = $('#table');
    cmenu = $('<div/>').appendTo('body');
    cmenu.menu({
        onClick: function(item){
            if (item.iconCls == 'icon-ok'){
            	dg.datagrid('hideColumn', item.name);
                cmenu.menu('setIcon', {
                    target: item.target,
                    iconCls: 'icon-empty'
                });
            } else {
            	dg.datagrid('showColumn', item.name);
                cmenu.menu('setIcon', {
                    target: item.target,
                    iconCls: 'icon-ok'
                });
            }
        	var opts = dg.datagrid('options');
        	saveCookie(opts.columns);
        }
    });
    var fields = dg.datagrid('getColumnFields');
    for(var i=0; i<fields.length; i++){
        var field = fields[i];
        var col = dg.datagrid('getColumnOption', field);
        cmenu.menu('appendItem', {
            text: col.title,
            name: field,
            iconCls: col.hidden ? '' : 'icon-ok'
        });
    }
}
function getConfig(){
	var queryContentStr = JSON.stringify(window.preQuery, null, 4);
    return {
        loadMsg: '正在获取数据......',
        url: '/admin/contract!ajaxGetList.action?contractCategoryId='+window.contractCategoryId,
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
        sortName: "modifyDate",
        sortOrder: "desc",
        data: {"rows":[],"total":0,"footer":[]},
        queryParams: {
    		sort : "modifyDate",
    		order : "desc",
    		preQuery : queryContentStr
    	},
    	onEndEdit:function(index,field,kv){
    		// 修复tips不消失问题
    		$(".tdTips").poshytip('hide');
    		setTimeout(function(){
    			$(".tip-yellowsimple").remove();
    		}, 200);
    		
    		if("projectId" in kv){
    			value = kv["projectId"];
    			$.get("/admin/contract!joinProject.action",{
    				rowId: field.rowId,
    				projectId:value
    			},function(){
    				$.messager.show({
    	                title:'',
    	                msg:'加入项目成功',
    	                showType:'fade'
    	            });
    			}).error(function(){
    				alert("加入项目失败");
    			});
    		}
    	},
//    	onBeginEdit:function(index){
//    		var projectId = $('#table').datagrid("getData").rows[index].projectId;
//    		if(projectId==null || projectId=="" || belongProjectMap[projectId]==null){
//
//    		}else{
//    			$('#table').datagrid('cancelEdit', index);
//
//    		}
//    	},
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
    	},
    	rowStyler: function(index,rowData){
        	var statusDict = {
    			"紧急":"background-color:#fcbdbd;",
    			"严重":"background-color:#e3b7eb;",
//    			"开发中":"background-color:#66FFCC;",
//    			"认可":"background-color:#ffcd85;",
    			"一般":"background-color:#fff494;",
//    			"已分派":"background-color:#c2dfff;",
//    			"已解决":"background-color:#d2f5b0;",
//    			"已关闭":"background-color:#c9ccc4;"
        	};
        	return statusDict[rowData[window.buildLevelFieldName]];
        },
        onResizeColumn: function(field, width){
        	var opts = $(this).datagrid('options');
        	saveCookie(opts.columns);
        },
        onHeaderContextMenu: function(e, field){
            e.preventDefault();
            if (!cmenu){
                createColumnMenu();
            }
            cmenu.menu('show', {
                left:e.pageX,
                top:e.pageY
            });
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
    	var url = "/admin/contract!download.action";
		var queryContent = $(".queryDom").queryTree("getValue");
		var queryContentStr = JSON.stringify(queryContent, null, 4);
    	$("#downloadForm").find("[name='contractCategoryId']").val(window.contractCategoryId);
    	$("#downloadForm").find("[name='preQuery']").val(queryContentStr);
    	$("#downloadForm").attr("action",url).submit();
    });
    
    $("#removeContract").unbind('click').click(function(){
    	var url = "/admin/contract!delete.action";
    	var ids = [];
    	var rows = table.datagrid("getChecked");
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
//    	var url = "/admin/contract!edit.action?rowId="+$(this).data("row_id")+"&contractCategoryId="+window.contractCategoryId;
    	var url = "/admin/contract!add.action?contractCategoryId="+window.contractCategoryId;
        var randomId = ("iframe"+Math.random()).replace(".","");
        var ezWin = window.top.createWin({
        	title: "新增记录",
        	resizable: true,
        	maximizable: true
    	});
        ezWin.attr("id",randomId);
        var iframe = window.top.createIframe(url);
        iframe.appendTo(ezWin);
        iframe.attr("name",randomId);
        ezWin.window("open");
        iframe.get(0).contentWindow.__windowId__ = randomId;
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
//	$("#updateContract").unbind('mouseenter').mouseenter(function(){
//    	msg.show({
//            title:'警告',
//            msg:'导入的excel"'+window.contractCategoryTitles[0]+'"列不能重复',
//            timeout:5000,
//            showType:'slide'
//        });
//    });
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
	initDicts(window.belongProjectOpts);
    var table = $('#table');
    table.datagrid(getConfig()).datagrid('enableCellEditing');
    table.datagrid('columnMoving');
    table.datagrid('doCellTip',{ specialShowFields:getColumns()[0] });
    table.datagrid('getPager').pagination(pager);
    $(window).resize(function(){
        table.datagrid('resize', {
            height: getDataGridHeight()
        });
    });
    initEvents();
    preQueryItem();
});

