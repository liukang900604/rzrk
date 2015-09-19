var msg = window.top.$.messager;
var editor = "";
var saveBtn = '';
var letterIdx = {};
var unique = {};
function initValidate(){
/*	var rules = {}, messages = {};
	rules = {
		"article.title": "required",
		"article.articleCategory.id": "required"
	};
	messages = {
		"article.title": "不能为空，请输入",
		"article.content": "不能为空，请输入",
		"article.articleCategory.id": "不能为空，请输入"
	};
	$('#admForm').validate({
		ignore: "",
        errorElement: "span",
        errorPlacement: function(error, el) {
        	error.appendTo(el.parents('td'));
        },
        rules: rules,
        messages: messages
	});
*/};
var pager = {
	    beforePageText: '当前第',
	    afterPageText: '页，共 {pages} 页',
	    displayMsg: '当前第 {from} 至 {to} 条数据，共 {total} 条数据'
	};

	// 操作
	function operations(contractCategoryId){
		return function(value, rowData, index) {
		    var link = "<a href='#' data-row_id='{0}' data-idx='{1}' data-contract_category_id='{2}' class='edit' onclick='editInfo.call(this);'>编辑</a>";
//		    link += "<em>|</em>";
		//   link += "<a href='#' data-id='{0}' data-idx='{1}' class='downloadTemp'>下载模板</a>";
		    if(rowData.rowId){
		        return $.format(link, rowData.rowId, index,contractCategoryId);
		    }else{
		    	return "";
		    }
		}
	}
	function editInfo(){
		var url = "/admin/contract!edit.action?rowId="+$(this).data("row_id")+"&contractCategoryId="+$(this).data("contract_category_id")+"&parentRowId="+((window.isAddAction)?window.tempRowId : $("[name='rowId']").val());
        var randomId = ("iframe"+Math.random()).replace(".","");
	    var ezWin = window.top.createWin({ title: "子表编辑" });
	    ezWin.attr("id",randomId);
	    var iframe = window.top.createIframe(url);
	    iframe.appendTo(ezWin);
        iframe.attr("name",randomId);
	    ezWin.window("open");
        iframe.get(0).contentWindow.__windowId__ = randomId;
        iframe.get(0).contentWindow.__windowParentId__ = __windowId__;
		return false;
	}

function getColumns(contractCategoryId) {
	var columns = [[
	                {title: "选择", field: "isChecked", width: 80, align: 'center', checkbox:true},
	                ]];
	var definition = window.childTitlesMap[contractCategoryId];
    for(var i=0;i<definition.length;i++){
    	columns[0].push({title: definition[i].name, field: definition[i].name, width: 100, align: 'left',sortable : true});
    }
    
    columns[0].push({title: "操作", field: "operation", width: 120, align: 'center', formatter: operations(contractCategoryId)});
    columns[0].push({title: "创建人", field: "createAdminName", width: 120, align: 'left',sortable : true});
    columns[0].push({title: "创建时间", field: "createDate", width: 120, align: 'center',sortable : true});
    columns[0].push({title: "最后修改时间", field: "modifyDate", width: 120, align: 'center',sortable : true});
    columns[0].push({title: "最后修改人", field: "modifyAdminName", width: 120, align: 'left',sortable : true});
  
    return columns;
}

function getConfig(contractCategoryId){
    return {
        loadMsg: '正在获取数据......',
        url: '/admin/contract!ajaxGetList.action?contractCategoryId='+contractCategoryId+"&parentRowId="+((window.isAddAction)?window.tempRowId : $("[name='rowId']").val()),
        method: 'post',
        width: '100%',
        rownumbers: true,
        height: 200,
        singleSelect: false,
        selectOnCheck: true,
        checkOnSelect: true,
        autoRowHeight: false,
        remoteSort: true,
//        showFooter: true,
        pagination: true,
       pageSize: 10,
        pageList: [10,50, 100, 500],
        pageNumber: 1,
        columns: getColumns(contractCategoryId),
        sortName: "modifyDate",
        sortOrder: "desc",
        data: {"rows":[],"total":0},
        queryParams: {
    		sort : "modifyDate",
    		order : "desc",
    		preQuery : ""
    	}
    };
}


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

function _initEvents(){
	var admForm = $('#admForm');
	saveBtn = $("#saveBtn");
	closeBtn = $("#closeBtn");
	saveBtn.click(function(){
		var msg = window.top.$.messager;
//		if($.trim($("[name='fields']").eq(0).val())==""){
//			msg.alert('保存信息失败', window.title0 + "不能为空", 'error')
//			return false;
//		}
		var url = "";
		if (window.isAddAction)
			url = "/admin/contract!save.action";
		else
			url = "/admin/contract!update.action";
		var _this = $(this);
		if (_this.hasClass('disabled') 
//				|| !admForm.valid()
				) return false;
		var res = window.customEditor.getData();
		var sendData = {
				"contractEntity":JSON.stringify(res),
				"parentRowId":$("[name='parentRowId']").val(),
				"contractCategoryId":$("[name='contractCategoryId']").val(),
		};
		if (window.isAddAction){
			$.extend(sendData,{
				"tempRowId":window.tempRowId
			});
		}else{
			$.extend(sendData,{
				"rowId":$("[name='rowId']").val(),
			});
		}
		if($("#projectId").length>0){
			$.extend(sendData,{
				"projectId":$("#projectId").combobox("getValue")
			});
		}

		
		_this.addClass('disabled').linkbutton({iconCls: 'icon-loading'});
		$.post(url, sendData, function(resp){
			
			if(resp.status == "success"){
				if(resp.isCheck == 1){//需要审核
					msg.alert('工作审批', '请进行工作审批！', 'info',function(){
						try{
//							window.top.reloadTabContent(window.contractCategoryName);
							window.top.reloadDataGridInTab(window.contractCategoryName);
							
						}catch(e){
							location.reload();
						}
						_this.removeClass('disabled').linkbutton({iconCls: 'icon-save'});
//						window.top.closeIfameWindowById('addContractWindow');
						window.top.closeCurrentWindow();
						
					});
				
					
				}else{
					msg.alert('保存信息', '保存信息成功！', 'info', function(){
						try{
//							window.top.reloadTabContent(window.contractCategoryName);
//							window.top.reloadDataGridInTab(window.contractCategoryName);
							
							if(window.__windowParentId__!=null){
								var $$parentIframe = top.$("[name='"+window.__windowParentId__+"']");//window.name 是父iframe的tabid
								var parentIframe = $$parentIframe.get(0);
								parentIframe.contentWindow.$(parentIframe.contentWindow.document).find(".childtable").each(function(index,node){
									parentIframe.contentWindow.$(node).datagrid("reload");
								})
							}else{
								window.top.reloadDataGridInTab(window.contractCategoryName);
							}

						}catch(e){
							location.reload();
						}
						_this.removeClass('disabled').linkbutton({iconCls: 'icon-save'});
//						window.top.closeIfameWindowById('addContractWindow');
						
						window.top.closeCurrentWindow();			//added by huanghui ,2015/7/27
				
					});
				}
				
				
				
			}else{
				msg.alert('保存信息失败', resp.message, 'error',function(){
				});
				_this.removeClass('disabled').linkbutton({iconCls: 'icon-save'});
			}
		},"json").error(function(resp){
			msg.alert('保存信息', '保存信息发生错误！', 'error');
			_this.removeClass('disabled').linkbutton({iconCls: 'icon-save'});
		});
		return false;
	});
	closeBtn.click(function(){
		window.top.closeCurrentWindow();
	});
	/*$("ul").on("click", "li:not(.addColleague) span", function() {
		var _this = $(this);
		delete unique[_this.next().val()];
		_this.parents("li").remove();
	});
	$(".addColleague a").click(function() {
		$(this).hide();
		$(this).next().next().show();
	});*/
	
    $(".removeContract").click(function(){
    	var contractCategoryId = $(this).data("contract_category_id");
    	var url = "/admin/contract!delete.action";
    	var ids = [];
    	var rows = $(this).closest("div").find(".childtable").datagrid("getChecked");
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
			  data: "contractCategoryId="+contractCategoryId+"&"+ids.join('&'),
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
    $(".addContract").click(function(){
    	var contractCategoryId = $(this).data("contract_category_id");
//    	var url = "/admin/contract!edit.action?rowId="+$(this).data("row_id")+"&contractCategoryId="+window.contractCategoryId;
    	var url = "/admin/contract!add.action?contractCategoryId="+contractCategoryId+"&parentRowId="+((window.isAddAction)?window.tempRowId : $("[name='rowId']").val());
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
        iframe.get(0).contentWindow.__windowParentId__ = __windowId__;
    	return false;
    });
	$("#projectId").combobox({
		valueField: 'id',
        textField: 'text',
        data:window.belongProjectOpts
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
function initDicts(users) {
	$.each(users, function(i, user) {
		letterIdx[user.name] = CTL.convert(user.name).toString();
	});
}
function initJsonData(){
	var rowJson = window.rowDataJson;
	if(rowJson != null && rowJson != ""){
		$(".contractCategory.cfield").each(function(){
			var item = $(this).attr("field");
			if($(this).is("textarea")){
				$(this).text(rowJson[''+item+'']);
			}else if($(this).is("span")){
				$(this).attr("value",rowJson[''+item+'']);
			}else{
				$(this).val(rowJson[''+item+'']);
			}
		
		})
	}
	
}

function _initData(){
	var titlesStr = $("[name='titles']").val();
	if(!$.isEmptyObject(titlesStr)){
		var titlesArr = titlesStr.split(",");
        $(".contractCategory.cfield").each(function(){
        	var fieldName = $(this).attr("field");
        	if(titlesArr.indexOf(fieldName)==-1){
        		$(this).prop("disabled",true);
        	}
        });
	}
}
//initData();
$(function(){
	if(!window.isAddAction){
		$(".contractCategory.cid").attr("readonly","readonly").css("border-style","dotted");
	}
	_initData();
	initValidate();
    _initEvents();
    initJsonData();
    window.customEditor = CustomEditor($("#admForm"),$("#contractCategoryId").val());
    $(".childtable").each(function(){
    	var contractCategoryId = $(this).data("contract_category_id");
        var table = $(this);
        table.datagrid(getConfig(contractCategoryId));
    });
    
});