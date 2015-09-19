var msg = window.top.$.messager;
jQuery.validator.addMethod("username",function(value, element){return this.optional(element) || /^[\u0391-\uFFE5\w]+$/.test(value);},"只允许包含中文、英文、数字和下划线");
function initValidate(){
	var rules = {}, messages = {};
	rules = {
		'admin.password': {
			required: true,
			minlength: 4,
			maxlength: 20
		},
		'admin.rePassword': {
			required: true,
			equalTo: "#newpassword"
		},
		
		"password": {
			required: true,
			remote: "/admin/pass_change!checkPassword.action"
		},
	};
	messages = {
		'admin.password': {
			required: "不能为空，请输入",
			minlength: "密码必须大于等于4",
			maxlength: "密码必须小于等于20"
		},
		'admin.rePassword': {
			required: "不能为空，请输入",
			equalTo: "两次密码输入不一致"
		},
		
		"password": {
			required: "不能为空，请输入",
			remote: "密码输入错误"
		},
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
function initEvents(){
	var admForm = $('#admForm');
	saveBtn = $("#saveBtn");
	saveBtn.unbind('click').click(function(){
		var msg = window.top.$.messager;
		var url = "/admin/pass_change!updatePassword.action";
		var _this = $(this);
		if (_this.hasClass('disabled') || !admForm.valid()) return false;
		_this.addClass('disabled').linkbutton({iconCls: 'icon-loading'});
		$.post(url, admForm.serialize(), function(resp){
			resp = eval("("+resp+")");
			if (resp.status == 'success'){
				msg.alert('修改密码', '修改密码成功！', 'info', function(){
					$('#newpassword').val("");
					$('#oldpassword').val("");
					$('#repassword').val("");
					window.location.href = '/admin/logout';
				});
			}else{
				msg.alert('修改密码', '修改密码发生错误：' + resp.message, 'error');
			}
			_this.removeClass('disabled').linkbutton({iconCls: 'icon-save'});
		}).error(function(resp){
			msg.alert('修改密码', '修改密码发生错误！', 'info');
			_this.removeClass('disabled').linkbutton({iconCls: 'icon-save'});
		}, 'json');
		return false;
	});
};


$(function(){
	initValidate();
    initEvents();
});