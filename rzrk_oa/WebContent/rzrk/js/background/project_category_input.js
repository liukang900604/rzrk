var msg = window.top.$.messager;
var letterIdx = {};
var unique = {};
jQuery.validator.addMethod("chars", function(value, element) {
	return this.optional(element) || /^[\u0391-\uFFE5\w]+$/.test(value);
}, "只能输入中英文数字和下划线");
function initValidate(whether){
	var rules = {}, messages = {};
	rules = {
		"projectCategory.name": {
			"required": true,
			chars:true
			 
		},
	};
	messages = {
		"projectCategory.name": {
			"required": "模板名称不能为空，请输入",
		},
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
};



function _initEvents(){
	var atForm = $('#atForm');
	saveBtn = $("#saveBtn");
	closeBtn = $("#closeBtn");
	saveBtn.unbind('click').click(function(){
		var msg = window.top.$.messager;
		var url = "";
		if (window.isAddAction)
			url = "/admin/project_category!save.action";
		else
			url = "/admin/project_category!update.action";
		var _this = $(this);
		if (_this.hasClass('disabled') || !atForm.valid()) return false;
		_this.addClass('disabled').linkbutton({iconCls: 'icon-loading'});
		$.post(url,atForm.serialize(),function(resp){
			if(resp.status == "success"){
				msg.alert('保存信息', '保存信息成功！', 'info', function(){
					window.top.reloadDataGridInTab('人员信息');
					window.top.closeCurrentWindow();
				});				
				_this.removeClass('disabled').linkbutton({iconCls: 'icon-save'});
			}else{
				msg.alert('保存信息', resp.message, 'error');
				_this.removeClass('disabled').linkbutton({iconCls: 'icon-save'});
			}
		},"json").error(function(resp){
			  msg.alert('保存信息', '保存信息发生错误！', 'error');
			_this.removeClass('disabled').linkbutton({iconCls: 'icon-save'});
		});
		
		return false;
	});
	closeBtn.unbind('click').click(function(){
		window.top.closeCurrentWindow();
	});
	$("#isFlow").unbind('change').change(function(){
		if($("#isFlow").val() == 1){
			$("tr.undelete").show();
		}else{
			$("tr.undelete").hide();
		}
		
	})
    
	$("ul").on("click", "li:not(.addColleague) span", function() {
		var _this = $(this);
		delete unique[_this.next().val()];
		_this.parents("li").remove();
	});
	$(".addColleague a").click(function() {
		$(this).hide();
		$(this).next().next().show();
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

function initDisplay(){
	var isYes = parseInt($("select[name='contractCategory.approvalNeeded']").val());
	if (isYes)
		$(".approver").show();
	else
		$(".approver").hide();
}
$(function(){
	var name=$("#contractCategoryName").val();
	var whether=false;
	if (name==""){
		whether="/admin/contract_category!checkContractCategoryName.action";
	}
	initValidate(whether);
    _initEvents();
});