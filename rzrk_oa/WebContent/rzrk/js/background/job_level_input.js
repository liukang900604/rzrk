jQuery.validator.addMethod("positiveinteger", function(value, element) {var aint=parseInt(value); return aint>0&& (aint+"")==value;}, "请输入正整数.");  

function initValidate(){
	var rules = {}, messages = {};
	rules = {
		'jobLevel.name': {
			required: true,
			minlength: 2,
			maxlength: 10
		},
		"jobLevel.sortNo": {
			"required": true,
			"positiveinteger": true
		},
		"jobLevel.duplicateSortDeal": "required",
	};
	messages = {
		'jobLevel.name': {
			required: "请输入岗位名称",
			minlength: "最小长度为2",
			maxlength: "最大长度为10"
		},
		"jobLevel.sortNo": {
			"required": "请输入排序号",
			"positiveinteger": "请输入正整数"
		},
		"jobLevel.duplicateSortDeal": "请选择重复序号处理方式",
	};
	if (window.isAddAction){
		rules['jobLevel.name'] = {
			required: true,
			minlength: 2,
			maxlength: 10,
			remote: "/admin/job_level!checkJobLevelName.action"
		};
		messages['jobLevel.name'] = {
			required: "不能为空，请输入",
			minlength: "最小长度为2",
			maxlength: "最大长度为10",
			remote: "岗位级别已存在"
		};
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
			url = "/admin/job_level!save.action";
		else
			url = "/admin/job_level!update.action";
		var _this = $(this);
		if (_this.hasClass('disabled') || !admForm.valid()) return false;
		_this.addClass('disabled').linkbutton({iconCls: 'icon-loading'});
		$.post(url, admForm.serialize(), function(resp){
			msg.alert('保存信息', '保存信息成功！', 'info', function(){
				window.top.closeCurrentWindow();
			});
			window.top.reloadDataGridInTab('岗位级别列表');
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