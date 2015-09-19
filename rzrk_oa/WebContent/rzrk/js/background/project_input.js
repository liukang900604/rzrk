jQuery.validator.addMethod("username",function(value, element){return this.optional(element) || /^[\u0391-\uFFE5\w]+$/.test(value);},"只允许包含中文、英文、数字和下划线");
jQuery.validator.addMethod("positiveinteger", function(value, element) {var aint=parseInt(value); return aint>0&& (aint+"")==value;}, "请输入正整数.");
var msg = window.top.$.messager;
var letterIdx = {};
var unique = {};
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
function onSelectDate(date){
	var id = $(this).attr("id");
	var dateBox = id == "beginTime" ? $("#endTime") : $("#beginTime");
	
	var val = dateBox.datebox('getValue');
	dateBox.datebox().datebox('calendar').calendar({
        validator: function(d){
        	if (!date) return d;
            return id == "beginTime" ? date <= d : date >= d;
        }
    });
	if (val){
		dateBox.datebox('setValue', val);
	}
}
function initValidate(){
	var rules = {}, messages = {};
	rules = {
		'project.name': {
			required: true,
			remote: "/admin/project!checkIfNameExists.action?project.id="+$("input[name='id']").val()+$("input[name='project.name']").text()
		},
		"project.content": {
			required: true
		},
		"project.beginTime": {
			required: true
		},
//		"project.endTime": {
//			required: true
//		},
		"project.progress": {
			required: true,
			number: true,
			range: [0, 100]
		},
		"project.Creator": {
			required: true
		},
		"project.responsibor": {
			required: true
		},
		"project.deparment.name": {
			required: true
		},
		"project.rootContractCategory.id":{
			required: true
		}
	};
	messages = {
		'project.name': {
			required: "必填字段",
			remote: "项目名称已存在"
		},
		"project.content": {
			required: "必填字段"
		},
		"project.beginTime": {
			required: "必填字段"
		},
//		"project.endTime": {
//			required: "必填字段"
//		},
		"project.progress": {
			required: "必填字段",
			number: "只能输入数字",
			range: "数字范围只能在 {0} - {1} 之间"
		},
		"project.Creator": {
			required: "必填字段"
		},
		"project.responsibor": {
			required: "必填字段"
		},
		"project.deparment.name": {
			required: "必填字段"
		},
		"project.rootContractCategory.id":{
			required: "必填字段"
		}
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
};
function initValidateRemark(){
	var rules = {}, messages = {};
	rules = {
		'userPlanRemark.content': {
			required: true,
			maxlength: 240
		}
	};
	messages = {
		'userPlanRemark.content': {
			required: "必填字段",
			maxlength: "字数限制240"
		}
	};
	
	$('#problemForm').validate({
		ignore: "",
        errorElement: "span",
        errorPlacement: function(error, el) {
        	error.appendTo(el.parents('td'));
        },
        rules: rules,
        messages: messages
	});
};


/**
 * 
 */

function problem(){
	var id = $("#planid").val();
	$("#remarkTable").empty();
	$.ajax({
		   type: "POST",
		   url: "/admin/user_plan_remark!getProjectRemarkAjaxList.action?id="+id,
		   dataType:"json",
		   success: function(data){
			   for(var index in data.rows){
				   var item = data.rows[index];
				   $("#remarkTable").append("<tr><td style='width:0%'></td>" +
				   		"<td style='width:20%'>时间:"+item.createDate+"</td>" +
				   		"<td style='width:20%'>问题发布者:"+ item.adminName +"</td>" +
				   		"<td style='width:50%'>内容:"+item.content+"</td>" +
				   		"<td style='width:10%'><a href='javascript:void(0);' data-id='"+item.id+"' class='remark_delete' >删除</a></td>" +
				   		"</tr>");
			   }
		   }
		});
	
}


/**
 * 
 */
function initEvent(){
	var admForm = $('#problemForm');
	var saveBtn1 = $("#saveUserPlanRemark");
	var url = "/admin/user_plan_remark!saveProjectRemark.action";
	saveBtn1.unbind('click').click(function(){
		var _this = $(this);
		if (_this.hasClass('disabled') || !admForm.valid()) return false;
		_this.addClass('disabled').linkbutton({iconCls: 'icon-loading'});
		$.post(url, admForm.serialize(), function(resp){
			if(resp.status == "success"){
				msg.alert('保存信息', resp.message, 'info', function(){
					$('#content').val("");
					problem();
				});
			}else{
				msg.alert('保存信息', resp.message, 'error');
			}
			_this.removeClass('disabled').linkbutton({iconCls: 'icon-save'});
		}, 'json').error(function(){
			msg.alert('保存信息', "保存失败", 'error');
			_this.removeClass('disabled').linkbutton({iconCls: 'icon-save'});
		});
	});
	$("#remarkTable").on("click",".remark_delete",function(){
		var id = $(this).data("id");
		
    	msg.confirm("删除", '是否删除选中的描述？', function(r){
    		if (!r) return false;
    		$.ajax({
    			   type: "POST",
    			   url: "/admin/user_plan_remark!deleteProjectRemark.action?id="+id,
    			   dataType:"json",
    			   success: function(data){
    				   if(data.status == "success"){
    					   msg.alert('删除描述',"删除成功", 'info',function(){
    						   problem();
    					   });
    				   }else{
    					   msg.alert('删除描述',"删除失败", 'error');
    				   }
    			   },
    			   error:function(r){
    				   msg.alert('删除描述',"删除失败", 'error');
    			   }
    			});
    	});
		
		
	}).on("click",".remark_edit",function(){
		var id = $(this).data("id");
		alert(id);
	});
	
	
}

/**
 * 
 */
function initEvents(){
	var admForm = $('#admForm');
	saveBtn = $("#saveBtn");
	closeBtn = $("#closeBtn");
	saveBtn.unbind('click').click(function(){
		var msg = window.top.$.messager;
		var url = "";
		if (window.isAddAction)
			url = "/admin/project!save.action";
		else
			url = "/admin/project!update.action";
		var _this = $(this);
		if (_this.hasClass('disabled') || !admForm.valid()) return false;
		if ($("input:hidden[name='project.responsibor.id']").size() == 0){
			msg.alert("提示信息", "请选择项目负责人。", "info");
			return false;
		}else if ($("input:hidden[name='adminList.id']").size() == 0){
			msg.alert("提示信息", "至少选择一位项目成员。", "info");
			return false;
		}
		_this.addClass('disabled').linkbutton({iconCls: 'icon-loading'});
		$.post(url, admForm.serialize(), function(resp){
			if(resp.status == "success"){
				msg.alert('保存信息', resp.message, 'info', function(){
					window.top.closeCurrentWindow();
				});
				try{
					window.top.updateSubCategory();
					window.top.reloadDataGridInTab('项目管理');
				}catch(e){
					location.reload();
				}
				_this.removeClass('disabled').linkbutton({iconCls: 'icon-save'});
			}else{
				msg.alert('保存信息', resp.message, 'error');
				_this.removeClass('disabled').linkbutton({iconCls: 'icon-save'});
			}

		}, 'json').error(function(){
			msg.alert('保存信息', "项目保存失败!", 'error');
			_this.removeClass('disabled').linkbutton({iconCls: 'icon-save'});
		});
		return false;
	});
	closeBtn.unbind('click').click(function(){
		window.top.closeCurrentWindow();
	});
    
    var buttons = $.extend([], $.fn.datebox.defaults.buttons);
    buttons.splice(1, 0, {
    	text: '清空',
    	handler: function(target){
    		$(target).datebox("clear");
    		$(target).datebox("hidePanel");
    		onSelectDate.call(target);
    	}
    });
    
//    $("#beginTime").datebox({
//    	formatter: dateFormatter,
//    	parser: dateParser,
//    	editable:false,
//    	onSelect:onSelectDate,
//    	buttons: buttons
//    });
//    if (!window.isEditAction){
//    	$("#beginTime").datebox('setValue',new Date().format('yyyy-MM-dd'));
//    }
//    
//    $("#endTime").datebox({
//    	formatter: dateFormatter,
//    	parser: dateParser,
//    	editable:false,
//    	onSelect:onSelectDate,
//    	buttons: buttons
//    });
//create wyn
    var date = new Date();
    $("#beginTime").datetimebox({
       onSelect: sel
    });
    $("#endTime").datebox({
        onSelect:sel
    });
    $("#endTime").datebox().datebox('calendar').calendar({
        validator: function (d) {
            return date <= d;
        }
    });
    function sel(date){
        var id = $(this).attr("id");
        if(id=="beginTime"){
            var val = $("#endTime").datebox('getValue');
            $("#endTime").datebox().datebox('calendar').calendar({
                validator: function (d) {
                    return date <= d;
                }
            });
            if(val){
                $("#endTime").datebox('setValue',val);
            }
        }
        if(id=="endTime"){
            var val = $("#beginTime").datetimebox('getValue');
            $("#beginTime").datetimebox().datebox('calendar').calendar({
                validator: function (d) {
                    return date >= d;
                }
            });
            if(val){
                $("#beginTime").datetimebox('setValue',val);
            }
        }
    }
//wyn end
};
function initDicts(users){
	$.each(users, function(i, user){
		letterIdx[user.name] = CTL.convert(user.name).toString();
	});
}
function filterName(q, row){
    var opts = $(this).combobox('options');
    if (row[opts.textField].toLocaleLowerCase().indexOf(q.toLocaleLowerCase()) != -1){
        return true;
    }
    if (letterIdx[row[opts.textField]].toLocaleLowerCase().indexOf(q.toLocaleLowerCase()) != -1){
        return true;
    }
    return false;
}
function selectUser(user){
	var lastLi = $(".addColleague");
	var btn = lastLi.find("a");
	var comboBox = btn.next();
	var li = '<li>{0}<span>X</span><input type="hidden" name="adminList.id" value="{1}"/></li>';
	li = $.validator.format(li, user.name, user.id);
	if (!unique[user.id]){
		$(li).insertBefore(lastLi);
	}
	unique[user.id] = true;
	btn.show();
	comboBox.combobox("setValue", "");
	comboBox.next().hide();
}
function initData(){
	var url = "/admin/admin!getAllAjaxList.action";
	$.get(url, function(data){
		initDicts(data.rows);

		$("select[name='project.responsibor.id']").combobox({
            filter: filterName
		});
	}, "json");
}
initData();

function createEditor(){
    if(typeof(KindEditor) != "undefined") {
    	KindEditor.ready(function(K) {
    		editor = K.create("#editor", {
    			height: "350px",
    			width: "800px",
    			items: ["source", "|", "undo", "redo", "|", "preview", "print", "template", "cut", "copy", "paste", "plainpaste", "wordpaste", "|","justifyleft", "justifycenter", "justifyright", "justifyfull", "insertorderedlist", "insertunorderedlist", "indent", "outdent", "subscript", "superscript", "clearhtml", "quickformat", "selectall", "|", "fullscreen", "/", "formatblock", "fontname", "fontsize", "|", "forecolor", "hilitecolor", "bold", "italic", "underline", "strikethrough", "lineheight", "removeformat", "|", "image", "flash", "media", "insertfile", "table", "hr", "emoticons", "map", "pagebreak", "link", "unlink"],
    			syncType: "form",
    			allowFileManager: true,
    			uploadJson: "/admin/file!ajaxUpload.action",
    			fileManagerJson: "/admin/file!ajaxBrowser.action",
    			afterChange: function() {
    				this.sync();
    			}
    		});
    	});
    }
}
$(function(){
	problem();
	createEditor();
	initValidate();
	initValidateRemark();
    initEvents();
    initEvent();
    $("#deparmentList").selectDepartment({
    	"content" : '#contentDepartment',
    	"source" : window.departmentJobList,
    	"title" : "部门选择",
    	"hidden_name" : "project.deparment.id"
    });
    $("#adminList").selectMembers({
    	hiddenName: 'adminList.id'
    });
});