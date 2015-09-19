jQuery.validator.addMethod("positiveinteger", function(value, element) {var aint=parseInt(value); return aint>0&& (aint+"")==value;}, "请输入正整数.");  
var letterIdx = {};
var unique = {};
function initValidate(){
	var rules = {}, messages = {};
	rules = {
		'job.jobName': {
			required: true,
			minlength: 2,
			maxlength: 10
		},
		"job.sortNo": {
			"required": true,
			"positiveinteger": true
		},
		"job.duplicateSortDeal": "required",
	};
	messages = {
		'job.jobName': {
			required: "请输入岗位名称",
			minlength: "最小长度为2",
			maxlength: "最大长度为10"
		},
		"job.sortNo": {
			"required": "请输入排序号",
			"positiveinteger": "请输入正整数"
		},
		"job.duplicateSortDeal": "请选择重复序号处理方式",
		
	};
//	if (window.isAddAction){
//		rules['job.jobName'] = {
//			required: true,
//			minlength: 2,
//			maxlength: 10,
//			remote: "/admin/job!checkJobName.action"
//		};
//		messages['job.jobName'] = {
//			required: "不能为空，请输入",
//			minlength: "最小长度为2",
//			maxlength: "最大长度为10",
//			remote: "岗位名称已存在"
//		};
//	}
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
			url = "/admin/job!save.action";
		else
			url = "/admin/job!update.action";
		
		if($("#isKeyName").val() == 1){
			if($("#adminId").val() == null && $("#adminId").val() == ""){
				msg.alert('提示信息', '请选择处理人！', 'error');
				return false;
			}
		}
		var ex = /^\d+$/;
		if (!ex.test($("#priorityLevel").val())) {
			msg.alert('提示信息', '优先级请输入四位数之内的正整数！', 'error');
			return false;
		}
		
		var _this = $(this);
		if (_this.hasClass('disabled') || !admForm.valid()) return false;
		_this.addClass('disabled').linkbutton({iconCls: 'icon-loading'});
		$.post(url, admForm.serialize(), function(resp){
			msg.alert('保存信息', '保存信息成功！', 'info', function(){
				window.top.closeCurrentWindow();
			});
			window.top.reloadDataGridInTab('岗位列表');
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
	$("#isKeyName").unbind('change').change(function(){
		if($("#isKeyName").val() == 1){
			$("tr.undelete").show();
		}else{
			$("tr.undelete").hide();
		}
		
	})
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
function initData(){
	var url = "/admin/admin!getAllAjaxList.action";
	$.get(url, function(data){
		initDicts(data.rows);

		$("select[name='job.adminId']").combobox({
            filter: filterName
		});
	}, "json");
}
initData();
$(function(){
	initValidate();
    initEvents();
    if($("#isKeyName").val() == 1){
		$("tr.undelete").show();
	}else{
		$("tr.undelete").hide();
	}
});