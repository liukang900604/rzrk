jQuery.validator.addMethod("positiveinteger", function(value, element) {var aint=parseInt(value); return aint>0&& (aint+"")==value;}, "请输入正整数.");  

function initValidate(){
	var rules = {}, messages = {};
	rules = {
		'rootContractCategory.name': {
			required: true,
			minlength: 2,
			maxlength: 10
		},
//		'rootContractCategory.rootCategoryType': {
//			required: true,
//		},

	};
	messages = {
		'rootContractCategory.name': {
			required: "请输入类型名称",
			minlength: "最小长度为2",
			maxlength: "最大长度为10"
		},
//		'rootContractCategory.rootCategoryType': {
//			required: "请选择类型",
//		},
	};
	if (window.isAddAction){
		rules['rootContractCategory.name'] = {
			required: true,
			minlength: 2,
			maxlength: 10,
			remote: "/admin/root_contract_category!checkRootName.action"
		};
		messages['rootContractCategory.name'] = {
			required: "不能为空，请输入",
			minlength: "最小长度为2",
			maxlength: "最大长度为10",
			remote: "类型已存在"
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
			url = "/admin/root_contract_category!save.action";
		else
			url = "/admin/root_contract_category!update.action";
		var _this = $(this);
		if (_this.hasClass('disabled') || !admForm.valid()) return false;
		_this.addClass('disabled').linkbutton({iconCls: 'icon-loading'});
		$.post(url, admForm.serialize(), function(resp){
			if (window.isAddAction)
				window.top.updateCategory();
			window.top.reloadDataGridInTab('一级分类');
			msg.alert('保存信息', '保存信息成功！', 'info', function(){
				window.top.closeCurrentWindow();
			});
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