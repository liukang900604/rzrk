var iconBasePath = '/rzrk/js/jquery-easyui-1.4.1/themes/icons/';
var msg = window.top.$.messager;

function operations(value, rowData, index) {
    var link = "";
    if(index!=0){
        link += "<a href='#' data-idx='{0}' class='delete'>删除</a>";
        if(rowData.type==1){
            link += "<em>|</em>";
            link += "<a href='#' data-idx='{0}' class='edit'>编辑</a>";
        }
    }
    return $.format(link, index);
}

function operationsTotal(value, rowData, index) {
	var link="";
    if(index!=0){
        link += "<label><input type='checkbox' data-idx='{0}' class='total' ";
        if(rowData.total){
            link += "checked='checked'";
        }
        link += "></input></label>";
    }	
    return $.format(link, index);
}


function getColumns() {
    var columns = [[
//            {title: "", field: "isChecked", width: 80, align: 'center', checkbox:true},
            {title: "类型", field: "type", width: 100, align: 'center', sortable: false, formatter: function(value, rowData, index){
            	if(index==0){
            		return "主键";
            	}else if(value==0){
            		return "表字段";
            	}else{
            		return "表达式"
            	}
            }},
            {title: "来源表", field: "tableName", width: 100, align: 'center', sortable: false},
            {title: "来源列", field: "tableField", width: 100, align: 'center', sortable: false},
            {title: "列名", field: "showField", width: 100, align: 'center', sortable: false},
            {title: "表达式", field: "expression", width: 100, align: 'center', sortable: false},
            {title: "总计", field: "total", width: 100, align: 'center', formatter:operationsTotal},
            {title: "操作", field: "tableFiled", width: 100, align: 'center', formatter: operations}
        ]];
    return columns;
}

function getAddBtnName(){
	if($("#primaryTable").val()==""){
    	return "添加主表";
	}else{
    	return "添加副表";
	}
}
function toolbars(){
	return [{
        text:getAddBtnName(),
        iconCls:'icon-add',
        handler:function(){
        	if($("#primaryTable").val()==""){
        		$(".primaryTr").show();
        	}else{
        		$(".primaryTr").hide();
        	}
        	$("#dlg").dialog("open");
        }
    },{
        text:'添加表达式',
        iconCls:'icon-add',
        handler:function(){
        	if($("#primaryTable").val()==""){
        		msg.alert('警告', "请先添加表字段", 'info');
        	}else{
            	$("#dlgExp").dialog("open");
        	}
        }
    }]
}
function getConfig(){
    return {
        loadMsg: '正在获取数据......',
        width: '100%',
        rownumbers: true,
        height: getDataGridHeight(),
        singleSelect: false,
        selectOnCheck: false,
        checkOnSelect: false,
        autoRowHeight: false,
        showFooter: false,
        pagination: false,
        columns: getColumns(),
        data: window.viewlayerData,
        toolbar:toolbars()
    };
}

function getDataGridHeight(){
	var searchBarHeight = $('.searchBar').outerHeight();
	var winHeight = $(window).outerHeight();
	return winHeight - searchBarHeight - 25;
}



function initValidate(){
var rules = {}, messages = {};
rules = {
		'viewlayer.name': {
			required : true,
			minlength: 4,
			maxlength: 20
		},
		'viewlayer.primaryTable': {
			required : true
		}
};
messages = {
	"viewlayer.name": {
		required: "不能为空，请输入",
		minlength: "请输入4~20个字",
		maxlength: "请输入4~20个字"
	},
	'viewlayer.primaryTable': {
		required : "请添加主表"
	}
};
$('#atForm').validate({
	ignore: "",
    errorElement: "span",
    errorPlacement: function(error, el) {
    	error.appendTo(el.parents('td'));
    },
    rules: rules,
    messages: messages
});
}
function initCategoryTree(){
	var primaryTmp=null;
	
	var optRoot="";
	$.each(window.categoryTree, function(index,root){
		optRoot += "<option value='"+ root.id + "'>"+ root.name +"</option>";
	});
	
	function findRootEnity(rootId){
		for(i in window.categoryTree){
			if(window.categoryTree[i].id == rootId){
				return window.categoryTree[i];
			}
		};
		return null;
	}
	
	function findChildEnity(rootId,childId){
		for(i in window.categoryTree){
			var root = window.categoryTree[i];
			if(root.id == rootId){
				for(j in root.children){
					if(root.children[j].id == childId){
						return root.children[j];
					}
				};
			}
		};
		return null;
	}
	
	$(".categoryChild").change(function(){
		var rootId = $(".categoryRoot").val();
		var childId = $(this).val();
		if($.isEmptyObject(childId))return;
		var child = findChildEnity(rootId,childId);
		if($.isEmptyObject(child))return;
		primaryTmp = child.primaryField;
		
		$.ajax({
			url : "/admin/contract_category!getPermissionField.action?id="+child.id,
			dataType:"json",
			success:function(res){
				if(res.status=="success"){
					$(".categoryFiled").combobox({
						data:res.data
					});
				}else{
					msg.alert('刷新失败', "获取列表失败", 'error');
				}
			},
			error:function(){
				msg.alert('刷新失败', "获取列表失败", 'error');
			}
		});
/*		var optFiled=[];
		if(child!=null && child.fields!=null && child.fields.length>0){
			var first=true;
			$.each(child.fields.split(','), function(ifield,field){
				if(first){
					first=false;
					primaryTmp={};
					primaryTmp.id = field;
					primaryTmp.text = field+"(主键)";
				}else{
					var item={};
					item.id = field;
					item.text = field;
					optFiled.push(item);
				}
			});
		}
*/		
	});

	$(".categoryRoot").html(optRoot).change(function(){
		var rootId = $(this).val();
		var root = findRootEnity(rootId);
		var optChild="";
		$.each(root.children, function(ic,c){
			if($("#primaryField").val()=="" || c.primaryField.name == $("#primaryField").val()){
				optChild += "<option value='"+ c.id + "'>"+ c.name +"</option>";
			}
		});
		$(".categoryChild").html(optChild).triggerHandler("change");
	}).triggerHandler("change");
	
	function hasViewlayerData(showField){
		for(var ri in window.viewlayerData.rows){
			if(window.viewlayerData.rows[ri].showField == showField){
				return true;
			}
		}
		return false;
	}
	
	$("#dlgSave").click(function(){
		var ccid = $(".categoryChild option:selected").val();
		var ccname = $(".categoryChild option:selected").text();
		var dlgstr = $(".categoryFiled").combobox("getValues");
		if(dlgstr.length>0){
			
			if($("#primaryTable").val()==""){
				$("#primaryTable").val(ccname);
				$("#primaryField").val(primaryTmp.name);
				window.viewlayerData.rows.push({
					type:0,
					tableId:ccid,
					tableName:ccname,
					tableField:primaryTmp.name,
					showField:primaryTmp.name+"_"+ccname,
					expression:"",
					total:false
				});
			}
			$.each(dlgstr,function(i,field){
				
				if(!hasViewlayerData(field+"_"+ccname)){
					window.viewlayerData.rows.push({
						type:0,
						tableId:ccid,
						tableName:ccname,
						tableField:field,
						showField:field+"_"+ccname,
						expression:"",
						total:false
					});
				}
				
			})
			$('#table').datagrid({
				data:window.viewlayerData
			});
			$('#table').datagrid({
				toolbar:toolbars()
			});
		}
		$("#dlg").dialog('close');
	});
	$("#dlgClose").click(function(){
		$("#dlg").dialog('close');
	});
	$("#dlgExpSave").click(function(){
		var showField = $("#dlgExp .showField").val();
		var expression = $("#dlgExp .expression").val();
		var index = $("#dlgExp .index").val();
		if(index==""){
			window.viewlayerData.rows.push({
				type:1,
				tableId:"",
				tableName:"",
				tableField:"",
				showField:showField,
				expression:expression,
				total:false
			});
		}else{
	    	var item = window.viewlayerData.rows[index];
	    	item.showField = showField;
	    	item.expression = expression;
		}
		$('#table').datagrid({
			data:window.viewlayerData
		});
		$("#dlgExp .index").val("");
		$("#dlgExp .showField").val("");
		$("#dlgExp .expression").val("");
		$("#dlgExp").dialog('close');
	});
	$("#dlgExpClose").click(function(){
		$("#dlgExp .index").val("");
		$("#dlgExp .showField").val("");
		$("#dlgExp .expression").val("");
		$("#dlgExp").dialog('close');
	});
}
function _initEvents(){
	var table = $('#table');
    $(".datagrid").on("change", ".total", function () {
    	var idx = $(this).data("idx");
    	window.viewlayerData.rows[idx].total = $(this).is(":checked");
    	return false;
    }).on("click", ".delete", function () {
    	var idx = $(this).data("idx");
    	window.viewlayerData.rows.splice(idx,1);
    	$('#table').datagrid({
			data:window.viewlayerData
		});
    	return false;
    }).on("click", ".edit", function () {
    	var idx = $(this).data("idx");
    	var item = window.viewlayerData.rows[idx];
    	if(item.type==1){
    		$("#dlgExp .index").val(idx);
    		$("#dlgExp .showField").val(item.showField);
    		$("#dlgExp .expression").val(item.expression);
    		$("#dlgExp").dialog('open');
    	}
    	return false;
    });
	
	var atForm = $('#atForm');
	saveBtn = $("#saveBtn");
	closeBtn = $("#closeBtn");
	saveBtn.unbind('click').click(function(){
		var defintion = JSON.stringify(window.viewlayerData.rows);
		$("[name='viewlayer.definition']").val(defintion);
		var url = "";
		if (window.isAddAction)
			url = "/admin/viewlayer!save.action";
		else
			url = "/admin/viewlayer!update.action";
		var _this = $(this);
		if (_this.hasClass('disabled') || !atForm.valid()) return false;
		_this.addClass('disabled').linkbutton({iconCls: 'icon-loading'});
		$.ajax({
			type : 'post',
			url : url,
			data : $('#atForm').serialize(),
			dataType : 'json',
			success : function(data) {
				if (data.status == 'success') {
					msg.alert('保存信息', data.message, 'info', function() {
						window.top.reloadDataGridInTab("我的新表");
						_this.removeClass('disabled').linkbutton({iconCls: 'icon-save'});
						window.top.closeCurrentWindow();
					});

				} else {
					msg.alert('保存信息', data.message, 'error');
					_this.removeClass('disabled').linkbutton({iconCls: 'icon-save'});
				}
			},
			error : function(){
				msg.alert('保存信息', '保存信息发生错误！', 'info');
				_this.removeClass('disabled').linkbutton({iconCls: 'icon-save'});
			}
		});
	});
	closeBtn.unbind('click').click(function(){
		window.top.closeCurrentWindow();
	});
	initCategoryTree();
}

function initData() {
	var defintiton = $("[name='viewlayer.definition']").val();
	if(defintiton!=null && defintiton!=""){
		var def = eval(defintiton);
		window.viewlayerData.rows.push(def);
	}
}

$(function(){
	initData();
	initValidate();
	var table = $('#table');
    table.datagrid(getConfig());
    _initEvents();
});