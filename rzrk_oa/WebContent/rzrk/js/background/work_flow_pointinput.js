var msg = window.top.$.messager;
var editor = "";
var letterIdx = {};
var unique = {};

function initValidate() {
	var rules = {}, messages = {};
	rules = {
		"workFlowPonit.pointSort" : "required",
		"workFlowPonit.pointName" : "required"
	};
	messages = {
		"workFlowPonit.pointSort" : "不能为空，请输入",
		"workFlowPonit.pointName" : "不能为空，请输入"
	};
	$('#atForm').validate({
		ignore : "",
		errorElement : "span",
		errorPlacement : function(error, el) {
			error.appendTo(el.parents('td'));
		},
		rules : rules,
		messages : messages
	});
};


function initEvents() {
	var atForm = $('#atForm');
	var saveBtn = $("#saveBtn");
	var closeBtn = $("#closeBtn");
	var extensions = $(".extension");
	saveBtn.unbind('click').click(function() {
		var msg = window.top.$.messager;
		var _this = $(this);
		if (_this.hasClass('disabled') || !atForm.valid()) {
			return false;
		}
		if(isNaN($("#pointSort").val())){
			
			msg.alert('提示', "节点序号只能为整数");
			$("#pointSort").focus();
			return;
		}
		if($("#pointLocation").val() == 0 || $("#pointLocation").val() == 1){
			var keyVal = $("#nextPonit").val();
			if(keyVal==undefined || keyVal=="" || keyVal==null){
				
				msg.alert('提示', "下一节点不能为空");
				$("#nextPonit").focus();
				return;
			}
		}
		
		$.ajax({
			type : 'post',
			url : '/admin/work_flow!saveWorkFlowPoint.action',
			data : $('#atForm').serialize(),
			cache : false,
			dataType : 'json',
			success : function(data) {
				if (data.status == 'success') {
					msg.alert('保存信息', data.message,'info',function() {
						window.top.reloadDataGridInWindowTab('flowEditWindow', '流程定义');
						window.top.closeCurrentWindow();
					});

				} else {
					msg.alert('保存信息', data.message, 'info',function() {
						return false;
					});
				}
			}
		});
	});
	closeBtn.unbind('click').click(function() {
		window.top.closeCurrentWindow();
		_this.removeClass('disabled').linkbutton({
			iconCls : 'icon-save'
		});
	});
	$(".nodePos").change(function() {
		if ($(this).val() != 0) {
			extensions.show();
		} else {
			extensions.hide();
		}
	});
	$("ul").on("click", "li:not(.addColleague) span", function(){
		var _this = $(this);
		delete unique[_this.next().val()];
		_this.parents("li").remove();
	});
	$(".addColleague a").click(function(){
		$(this).hide();
		$(this).next().next().show();
	});
};
function initDisplay(){
	var extensions = $(".extension");
	if ($(".nodePos").val() != 0) {
		extensions.show();
	} else {
		extensions.hide();
	}
}
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
		$(".userBox").combobox({
            filter: filterName,
			valueField: "id",
			textField: "name",
			data: data.rows,
			onSelect: selectUser,
				onLoadSuccess: function(){
				if ($(".addColleague a").is(":hidden")) return;
				$(this).next().hide();
			}
		});
	}, "json");
}
initData();
$(function() {
	// 增加页面校验规则
	initValidate();
	initEvents();
	initDisplay();
});