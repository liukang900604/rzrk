var iconBasePath = '/rzrk/js/jquery-easyui-1.4.1/themes/icons/';
var msg = window.top.$.messager;

function operations(value, rowData, index) {
    var link = "";
    if(index!=0){
        link += "<a href='#' data-idx='{0}' class='delete'>删除</a>";
        link += "<em>|</em>";
        link += "<a href='#' data-idx='{0}' class='edit'>编辑</a>";
    }
    return $.format(link, index );
}

function operationsBoolean(value, rowData, index) {
    if(index!=0){
        if(!$.isEmptyObject(rowData.total)){
            return "是";
        }
    }	
    return "";
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
            {title: "来源表", field: "tableName", width: 100, align: 'left', sortable: false},
            {title: "来源列", field: "tableField", width: 100, align: 'left', sortable: false},
            {title: "列名", field: "showField", width: 100, align: 'left', sortable: false},
            {title: "表达式", field: "expression", width: 100, align: 'left', sortable: false},
            {title: "总计", field: "total", width: 100, align: 'center', formatter:function(value, rowData, index) {
                if(index!=0){
                    if(rowData.total){
                        return "是";
                    }
                }	
                return "";
            }},
            {title: "限制人员", field: "adminIds", width: 100, align: 'left', formatter:function(value, rowData, index) {
                if(index!=0){
                    if(!$.isEmptyObject(rowData.adminIds)){
                        return "是";
                    }
                }	
                return "";
            }},
            {title: "限制部门", field: "departmentIds", width: 100, align: 'left', formatter:function(value, rowData, index) {
                if(index!=0){
                    if(!$.isEmptyObject(rowData.departmentIds)){
                        return "是";
                    }
                }	
                return "";
            }},
            {title: "上级部门可见", field: "superiorView", width: 100, align: 'left', formatter:function(value, rowData, index) {
                if(index!=0){
                    if(rowData.superiorView){
                        return "是";
                    }
                }	
                return "";
            }},
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
        		$("#dlgEdit .dlgFieldContent").hide();
        		$("#dlgEdit .dlgExpContent").show();
            	$("#dlgEdit").dialog("open");
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
        data: window.unionContractCategoryData,
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
		'unionContractCategory.name': {
			required : true,
			minlength: 4,
			maxlength: 20
		},
		'unionContractCategory.primaryTable': {
			required : true
		},
		"deparmentListTexts":{
			"required" : function(){
				if($("[name='contractCategory.controlType'][value='bydep']").prop("checked")){
					return true;
				}else{
					return false;
				}
			}
		}

};
messages = {
	"unionContractCategory.name": {
		required: "不能为空，请输入",
		minlength: "请输入4~20个字",
		maxlength: "请输入4~20个字"
	},
	'unionContractCategory.primaryTable': {
		required : "请添加主表"
	},
	"deparmentListTexts":"部门不能为空",

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


var ControlFieldWidget = function($select,definition){
	var oldvalue;
	function _refresh(frist){
		if(frist!=null){
			oldvalue = $select.attr("oldvalue");
		}else{
			oldvalue = $select.val();
		}
		$select.empty();
		$("<option value='__create_admin'>创建人</option>").appendTo($select);
		for(var i in definition){
			var field = definition[i];
			if(field.builtsign == "用户名"){
				$("<option value='"+field.name+"'>"+field.name+"</option>").appendTo($select);
			}
		}
		$select.val(oldvalue);
		if($select.val()==null){
			$select.val("__create_admin");
		}
	};
	_refresh(true);
	
	return {
		"refresh":_refresh
	}
};

function initCategoryTree(){
	var primaryTmp=null; //指向主键的指针
	window.controlFieldWidget = ControlFieldWidget($("#controlFieldWidget"),window.unionContractCategoryData.rows);

	$(".categoryTree").combotree({
		width: 'auto',
		panelWidth : 'auto' ,
	    data: window.categoryTree,
	    onSelect:function(node){
	    	var parent =$('.categoryTree').combotree('tree').tree('getParent',node.target);
	    	if(parent!=null){
		    	$(".categoryRoot").val(parent.id);
		    	$(".categoryChild").val(node.id);
				var child = findChildEnity(parent.id,node.id);
				var optFiled="";
				if(child!=null && child.fields!=null && child.fields.length>0){
					$.each(child.fields.split(','), function(ifield,field){
						optFiled += "<option value='"+ field + "'>"+ field +"</option>";
					});
				}
				primaryTmp = child.definition[0];
				var data = [].concat(child.definition);
				data.splice(0,1);
				$(".categoryFiled").combobox({
					"data":data
				});
	    	}else{
	    		throw "cao";//故意报异常，让页面停止
	    	}
	    }
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
	
	function findFieldEnity(rootId,childId,fieldName){
		var childEntity = findChildEnity(rootId,childId);
		for(var index in childEntity.definition){
			if(fieldName==childEntity.definition[index].name){
				return childEntity.definition[index];
			}
		}
		return null;
	}
	/*	
	$(".categoryChild").change(function(){
		var rootId = $(".categoryRoot").val();
		var childId = $(this).val();
		if($.isEmptyObject(childId))return;
		var child = findChildEnity(rootId,childId);
		if($.isEmptyObject(child))return;
		primaryTmp = child.definition[0];
		
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
		var optFiled=[];
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
*/		
	
	function hasViewlayerData(showField){
		for(var ri in window.unionContractCategoryData.rows){
			if(window.unionContractCategoryData.rows[ri].showField == showField){
				return true;
			}
		}
		return false;
	}
	
	$("#dlgSave").click(function(){
		var rootId = $(".categoryRoot").val();
		var childId = $(".categoryChild").val();
		var ccentity = findChildEnity(rootId,childId);
		
		var ccid = ccentity.id;
		var ccname = ccentity.name;
		var dlgstr = $(".categoryFiled").combobox("getValues");
		if(dlgstr.length>0){
			
			if($("#primaryTable").val()==""){
				$("#primaryTable").val(ccname);
				$("#primaryField").val(primaryTmp.name);
				window.unionContractCategoryData.rows.push({
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
					window.unionContractCategoryData.rows.push({
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
				data:window.unionContractCategoryData
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
	$("#dlgEditSave").click(function(){
		
		
		var showField = $("#dlgEdit .showField").val();
		var expression = $("#dlgEdit .expression").val();
		var departmentIds = $("#dlgEdit .departmentIds").combotree("getValues");
		var adminIds = $("#dlgEdit .adminIds").combobox("getValues");
		var superiorView = $("#dlgEdit .superiorView").prop("checked");
		var total = $("#dlgEdit .total").prop("checked");
		var index = $("#dlgEdit .index").val();
		if(index==""){//只能新增表达式
			window.unionContractCategoryData.rows.push({
				type:1,
				tableId:"",
				tableName:"",
				tableField:"",
				showField:showField,
				expression:expression,
				total:total,
				departmentIds:[],
				adminIds:[],
				superiorView:superiorView
			});
		}else{
	    	var item = window.unionContractCategoryData.rows[index];
			var type = item.type;
			if(type==1){
		    	item.showField = showField;
		    	item.expression = expression;
			}else{
			}
	    	item.total = total;
	    	item.departmentIds = departmentIds;
	    	item.adminIds = adminIds;
	    	item.superiorView = superiorView;
		}
		$('#table').datagrid({
			data:window.unionContractCategoryData
		});
		$("#dlgEdit .index").val("");
		$("#dlgEdit .showField").val("");
		$("#dlgEdit .expression").val("");
		$("#dlgEdit .total").prop("checked",false);
		$("#dlgEdit .departmentIds").combotree("setValues",[]);
		$("#dlgEdit .adminIds").combobox("setValues",[]);
		$("#dlgEdit .superiorView").prop("checked",false);
		$("#dlgEdit").dialog('close');
		window.controlFieldWidget.refresh();
		
	});
	$("#dlgEditClose").click(function(){
		$("#dlgEdit .index").val("");
		$("#dlgEdit .showField").val("");
		$("#dlgEdit .expression").val("");
		$("#dlgEdit .total").prop("checked",false);
		$("#dlgEdit .departmentIds").combotree("setValues",[]);
		$("#dlgEdit .adminIds").combobox("setValues",[]);
		$("#dlgEdit .superiorView").prop("checked",false);
		$("#dlgEdit").dialog('close');
	});
}
function _initEvents(){
	$("#dlgEdit .departmentIds").combotree({
		panelHeight:300,
		lines: true,multiple:true,cascadeCheck:false,
		"data" : window.depTree4Easyui
	});
	$("#dlgEdit .adminIds").combobox({
		panelHeight:300,
		valueField: 'id',
        textField: 'name',
		multiple:true,
		"data" : window.admin4Easyui
	});

	var table = $('#table');
    $(".datagrid").on("change", ".total", function () {
    	var idx = $(this).data("idx");
    	window.unionContractCategoryData.rows[idx].total = $(this).is(":checked");
    	return false;
    }).on("click", ".delete", function () {
    	var idx = $(this).data("idx");
    	window.unionContractCategoryData.rows.splice(idx,1);
    	$('#table').datagrid({
			data:window.unionContractCategoryData
		});
		window.controlFieldWidget.refresh();
    	return false;
    }).on("click", ".edit", function () {
    	var idx = $(this).data("idx");
    	var item = window.unionContractCategoryData.rows[idx];
		$("#dlgEdit .index").val(idx);
		$("#dlgEdit .showField").val(item.showField);
		$("#dlgEdit .total").prop("checked",item.total);
		$("#dlgEdit .departmentIds").combotree("setValues",item.departmentIds);
		$("#dlgEdit .adminIds").combobox("setValues",item.adminIds);
		$("#dlgEdit .superiorView").prop("checked",item.superiorView);
    	if(item.type==1){
    		$("#dlgEdit .expression").val(item.expression);
    		$("#dlgEdit .dlgFieldContent").hide();
    		$("#dlgEdit .dlgExpContent").show();
    	}else{
    		$("#dlgEdit .expression").val("");
    		$("#dlgEdit .dlgFieldContent").show();
    		$("#dlgEdit .dlgExpContent").hide();
    	}
		$("#dlgEdit").dialog('open');
    	return false;
    });
	$("[name='unionContractCategory.controlType'][value='bydep']").change(function(){
		if($(this).prop("checked")){
			$(".depContractContent").removeClass("controlhide");
		}else{
			$(".depContractContent").addClass("controlhide");
		}
	});
	$("[name='unionContractCategory.controlType'][value='byop']").change(function(){
		if($(this).prop("checked")){
		    $(".opContractContent").removeClass("controlhide");
		}else{
			$(".opContractContent").addClass("controlhide");
		}
	});
	$("[name='unionContractCategory.controlType'][value='bydep']").triggerHandler("change");
	$("[name='unionContractCategory.controlType'][value='byop']").triggerHandler("change");
	
	$("[name='unionContractCategory.isSubDepView']").change(function(){
		if($(this).prop("checked")){
			$("[name='unionContractCategory.isView']").prop("checked",true)
		}
	});
	
	$("[name='unionContractCategory.isView']").change(function(){
		if(!$(this).prop("checked")){
			$("[name='unionContractCategory.isSubDepView']").prop("checked",false);
		}
	});

	var atForm = $('#atForm');
	saveBtn = $("#saveBtn");
	closeBtn = $("#closeBtn");
	saveBtn.unbind('click').click(function(){
		var defintion = JSON.stringify(window.unionContractCategoryData.rows);
		$("[name='unionContractCategory.definition']").val(defintion);
		var url = "";
		if (window.isAddAction)
			url = "/admin/union_contract_category!save.action";
		else
			url = "/admin/union_contract_category!update.action";
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
	var defintiton = $("[name='unionContractCategory.definition']").val();
	if(defintiton!=null && defintiton!=""){
		var def = eval(defintiton);
		window.unionContractCategoryData.rows.push(def);
	}
}

$(function(){
	initData();
	initValidate();
	var table = $('#table');
    table.datagrid(getConfig());
    _initEvents();
    $("#deparmentList").selectDepartment({
    	"content" : '#contentDepartment',
    	"source" : window.departmentJobList,
    	"title" : "部门选择",
    	"hidden_name" : "deparmentList.id"

    });
    $("#dlg").dialog('close');
    $("#dlgEdit").dialog('close');
});