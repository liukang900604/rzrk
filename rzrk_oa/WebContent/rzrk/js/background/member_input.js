jQuery.validator.addMethod("username",function(value, element){return this.optional(element) || /^[\u0391-\uFFE5\w]+$/.test(value);},"只允许包含中文、英文、数字和下划线");
function initValidate(){
	var rules = {}, messages = {};
	rules = {
		'member.password': {
			minlength: 4,
			maxlength: 20
		},
		'member.realName': {
			required: true,
			username: true
		},
		"member.userIdentification": {
		    minlength: 1,
		    maxlength: 18,
			required: true,
			digits: true
		},
		"member.bank": {
			required: true,
			bank: true
		},
		"member.bankAccount": {
			required: true,
			digits: 0
		},
		"member.mobile": {
			required:true,
			digits:true
		},
		"member.email": {
			required: true,
			email: true
		},
		"member.address": {
			required: true,
			address: true
		}
	};
	messages = {
		'member.password': {
			minlength: "必须大于等于4",
			maxlength: "必须小于等于20"
		},
		"member.realName": {
			required: "不能为空，请输入",
			username: "不能使用特殊字符"
		},
		"member.userIdentification": {
			required: "不能为空，请输入",
			digits: "必须为合法的十八位数"
		},
		"member.bank": {
			required: "不能为空，请输入",
			bank: "必须为合理的银行名称"
		},
		"member.bankAccount": {
			required: "不能为空，请输入",
			digits: "必须为合理的银行账号"
		},
		"member.mobile": {
			required: "不能为空，请输入",
			digits: "必须为合理的手机号"
		},
		"member.email": {
			required: "不能为空，请输入",
			email: "必须为合理的E-mail"
		},
		"member.address": {
			required: "请填写地址",
			address: ""
		}
	};
	if (window.isAddAction){
		rules['member.username'] = {
			required: true,
			minlength: 2,
			maxlength: 20,
			username: true,
			remote: "member!checkUsername.action"
		};
		rules['member.password'].required = true;
		messages['member.username'] = {
			required: "不能为空，请输入",
			minlength: "长度必须大于等于2",
			maxlength: "长度必须小于等于20",
			username: "不能使用特殊字符",
			remote: "用户名已存在"
		};
		messages['member.password'].required = '不能为空，请输入';
	}
	$('#cstmForm').validate({
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
	var cstmForm = $('#cstmForm');
	saveBtn = $("#saveBtn");
	closeBtn = $("#closeBtn");
	saveBtn.unbind('click').click(function(){
		var msg = window.top.$.messager;
		var url = "";
		if (window.isAddAction)
			url = "/admin/member!save.action";
		else
			url = "/admin/member!update.action";
		var _this = $(this);
		if (_this.hasClass('disabled') || !cstmForm.valid()) return false;
		_this.addClass('disabled').linkbutton({iconCls: 'icon-loading'});
		$.post(url, cstmForm.serialize(), function(resp){
			msg.alert('保存信息', '保存信息成功！', 'info');
			window.top.reloadDataGridInTab('客户管理「产品」');
			_this.removeClass('disabled').linkbutton({iconCls: 'icon-save'});
		}).error(function(resp){
			msg.alert('保存信息', '保存信息发生错误！', 'info');
			_this.removeClass('disabled').linkbutton({iconCls: 'icon-save'});
		});
		return false;
	});
	closeBtn.unbind('click').click(function(){
		window.top.closeCurrentWindow();
	});
};
$(function(){
	initValidate();
    initEvents();
});