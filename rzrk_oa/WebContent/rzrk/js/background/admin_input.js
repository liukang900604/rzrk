jQuery.validator.addMethod("positiveinteger", function(value, element) {
	var aint=parseInt(value);
	return aint>0 && (aint + "") == value;
}, "请输入正整数.");  
//手机号码验证
jQuery.validator.addMethod("isMobile", function(value, element) {
	return this.optional(element) || /^1[3|4|5|7|8][0-9]\d{8}$/.test(value);
}, "请正确填写您的手机号码");
 
// 电话号码验证
jQuery.validator.addMethod("isPhone", function(value, element) {
	return this.optional(element) || /^0[0-9]{2,3}-\d{8}$/.test(value);
}, "请正确填写您的电话号码");


//校验固定电话
jQuery.validator.addMethod("admin.officePhone", function(value, element) {
	return this.optional(element) || /^\d{3,4}-?\d{8,8}$/.test(value);
}, "请正确填写您的办公电话号码");

var msg = window.top.$.messager;

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


function ajaxFileUpload(){
	/*
	$("#loading").ajaxStart(function(){
		$(this).show();
	}).ajaxComplete(function(){
		$(this).hide();
	});
	*/
	var _this = $(this);
	var id = _this.attr("id");
	var url = "/admin/upload!imageFileUpload.action";
	var input = _this.siblings("input");
	var aLink = $("<a target='_blank' style='margin-left:10px;'>查看</a>");
	var aLinks = _this.siblings("a");
	$.ajaxFileUpload({
		url: url,
		secureuri:false,
		fileElementId:id,
		dataType: 'json',
		beforeSend:function(){
			//...
		},
		success: function (data, status){
            if(data.status == "success"){
				msg.alert("上传文件", "上传文件成功！", "info");
				$(".imageFileShow").attr("href",data.message).show();
//				aLinks.remove();
//				input.val(data.message);
//				$('#' + id).after(aLink.attr('href', data.message));
				$("[name='admin.url']").val(data.message);
             }else{
 				msg.alert("上传文件", "上传文件失败：" + data.message, "error");
             }
		},
		error: function (data, status, e){
			msg.alert("上传文件", "上传文件失败：" + e, "error");
		}
	});
	return false;
}

jQuery.validator.addMethod("username",function(value, element){return this.optional(element) || /^[\u0391-\uFFE5\w]+$/.test(value);},"只允许包含中文、英文、数字和下划线");
function initValidate(){
	var rules = {}, messages = {};
	rules = {
		'admin.password': {
			minlength: 4,
			maxlength: 20
		},
		'admin.rePassword': {
			equalTo: "#password"
		},
		"admin.email": {
			required: true,
			email: true
		},
		"admin.name": {
			required: true,
		},
		"admin.officePhone": {
			"admin.officePhone": true,
		},
		"admin.telephone": {
			required: true,
			"isMobile": true
		},
		"roleList.id": "required",
		"admin.sortNo": {
			"required": true,
			"positiveinteger": true
		},
		"admin.duplicateSortDeal": "required",
		"deparmentListTexts": "required",
		"mainJobListTexts": "required",
	};
	messages = {
		'admin.password': {
			minlength: "密码必须大于等于4",
			maxlength: "密码必须小于等于20"
		},
		'admin.rePassword': {
			equalTo: "两次密码输入不一致"
		},
		"admin.email": {
			required: "不能为空，请输入",
			email: "E-mail格式不正确"
		},
		"admin.name": {
			required: "不能为空，请输入",
		},
		"roleList.id": "请选择管理角色",
		"admin.sortNo": {
			"required": "请输入排序号",
			"positiveinteger": "请输入正整数"
		},
		"admin.duplicateSortDeal": "请选择重复序号处理方式",
		"admin.officePhone": {
			"admin.officePhone": "请输入合法的座机格式如:(010-12345678)"
		},
		"admin.telephone": {
			required: "不能为空，请输入",
			"isMobile": "请输入合法的手机号"
		},
		"deparmentListTexts": "请选择部门",
		"mainJobListTexts": "请选择主岗",
	};
	if (window.isAddAction){
		rules['admin.username'] = {
			required: true,
			username: true,
			minlength: 2,
			maxlength: 20,
			remote: "/admin/admin!checkUsername.action"
		};
		rules['admin.password'].required = true;
		rules['admin.rePassword'].required = true;
		rules['admin.name'].required = true;
		messages['admin.username'] = {
			required: "不能为空，请输入",
			username: "用户名只允许包含中文、英文、数字和下划线",
			minlength: "用户名必须大于等于2",
			maxlength: "用户名必须小于等于20",
			remote: "用户名已存在"
		};
		messages['admin.password'].required = '不能为空，请输入';
		messages['admin.rePassword'].required = '不能为空，请输入';
		messages['admin.name'].required = '不能为空，请输入';
	}
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
function initEvents(){
	var admForm = $('#admForm');
	saveBtn = $("#saveBtn");
	closeBtn = $("#closeBtn");
	saveBtn.unbind('click').click(function(){
		var msg = window.top.$.messager;
		var url = "";
		if (window.isAddAction)
			url = "/admin/admin!save.action";
		else
			url = "/admin/admin!update.action";
		var _this = $(this);
		if (_this.hasClass('disabled') || !admForm.valid()) return false;
		_this.addClass('disabled').linkbutton({iconCls: 'icon-loading'});
		$.post(url, admForm.serialize(), function(resp){
			resp = eval("("+resp+")");
			if (resp.status == 'success'){
				msg.alert('保存信息', '保存信息成功！', 'info', function(){
					window.top.reloadDataGridInTabIframe('人员信息','#cciframe');//added by huanghui ;2015/8/5
					window.top.closeCurrentWindow();
				});
			}else{
				msg.alert('保存信息', '保存信息发生错误：' + resp.message, 'error');
			}
			_this.removeClass('disabled').linkbutton({iconCls: 'icon-save'});
		}).error(function(resp){
			msg.alert('保存信息', '保存信息发生错误！', 'info');
			_this.removeClass('disabled').linkbutton({iconCls: 'icon-save'});
		}, 'json');
		return false;
	});
	closeBtn.unbind('click').click(function(){
		window.top.closeCurrentWindow();
	});
	
	$('.fileUpload').unbind('change').change(ajaxFileUpload);

    var buttons = $.extend([], $.fn.datebox.defaults.buttons);
    buttons.splice(1, 0, {
    	text: '清空',
    	handler: function(target){
    		$(target).datebox("clear");
    		$(target).datebox("hidePanel");
    	}
    });
    
    $("#adminBirthDate").datebox({
    	formatter: dateFormatter,
    	parser: dateParser,
    	editable:false,
    	buttons: buttons
    });
    
    $("#onBoardDate").datebox({
    	formatter: dateFormatter,
    	parser: dateParser,
    	editable:false,
    	buttons: buttons
    });
    
    $("#contractDueDay").datebox({
    	formatter: dateFormatter,
    	parser: dateParser,
    	editable:false,
    	buttons: buttons
    });
    
    $("#contractDueDay2").datebox({
    	formatter: dateFormatter,
    	parser: dateParser,
    	editable:false,
    	buttons: buttons
    });
    
    $("#contractDueDay3").datebox({
    	formatter: dateFormatter,
    	parser: dateParser,
    	editable:false,
    	buttons: buttons
    });
    
    $("#contractDueDay4").datebox({
    	formatter: dateFormatter,
    	parser: dateParser,
    	editable:false,
    	buttons: buttons
    });
    
    $("#turnRegularDate").datebox({
    	formatter: dateFormatter,
    	parser: dateParser,
    	editable:false,
    	buttons: buttons
    });
    
    $("#quitDate").datebox({
    	formatter: dateFormatter,
    	parser: dateParser,
    	editable:false,
    	buttons: buttons
    });
    

};


$(function(){
	initValidate();
    initEvents();
    
    //写在 initEvents函数里无效!!! 不知道为什么

    
    $("#deparmentList").selectDepartment({
    	"content" : '#contentDepartment',
    	"source" : window.departmentJobList,
    	"title" : "部门选择",
    	"hidden_name" : "deparmentList.id"

    });
    
    $("#mainJobList").selectPos({
    	"content" : '#contentPos',
    	"source" : window.jobList,
    	"title" : "主岗选择",
    	"hidden_name" : "mainJobList.id"

    });

    $("#viceJobList").selectDeputypos({
    	"content" : '#contentDeputyPost',
    	"source" : window.viceJobList,
    	"title" : "副岗选择",
    	"hidden_name" : "viceJobList.id"
    });

     
});