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
// 添加特殊校验
jQuery.validator.addMethod("stockMax", function(value, element) {
    var min = $("input[name='sindex.min']").val();
    if (min) {
        if (parseFloat(value) < parseFloat(min)) {
            return false;
        }
        return true;
    } else {
        return true;
    }
}, "最大值不能小于最小值");
jQuery.validator.addMethod("stockMin", function(value, element) {
    var max = $("input[name='sindex.max']").val();
    if (max) {
        if (parseFloat(value) > parseFloat(max)) {
            return false;
        }
        return true;
    } else {
        return true;
    }
}, "最小值不能大于最大值");
function initValidate(){
	var rules = {}, messages = {};
	rules = {
        "sindex.start" : {
            required : true,
            rangelength : [ 1, 10 ],
            number : true
        },
        "sindex.end" : {
            required : true,
            rangelength : [ 1, 10 ],
            number : true
        },
        "sindex.max" : {
            required : true,
            rangelength : [ 1, 10 ],
            number : true,
            stockMax : true
        },
        "sindex.min" : {
            required : true,
            rangelength : [ 1, 10 ],
            number : true,
            stockMin : true
        }
	};
	messages = {
		"sindex.start" : {
            required : "不能为空，请输入",
            rangelength : "开盘数字过长",
            number : "必须为数字格式"
		},
        "sindex.end" : {
            required : "不能为空，请输入",
            rangelength : "收盘数字过长",
            number : "必须为数字格式"
        },
        "sindex.max" : {
            required : "不能为空，请输入",
            rangelength : "最高数字过长",
            number : "必须为数字格式"
        },
        "sindex.min" : {
            required : "不能为空，请输入",
            rangelength : "最低数字过长",
            number : "必须为数字格式"
        }
	};
	if (window.isAddAction){
		rules['sindex.date'] = {
			required : true,
            remote : {
                url : "stockindex!ajaxCheckStockIndexDate.action",
                type : "get",
                data : {
                    stockDate : function() {
                        return $("input[name='sindex.date']").val();
                    }
                }
            }
		};
		messages['sindex.date'] = {
			required : "不能为空，请输入",
            remote : "已存在结算日期"
		};
	}
	$('#stkForm').validate({
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
	var stkForm = $('#stkForm');
	saveBtn = $("#saveBtn");
	closeBtn = $("#closeBtn");
	saveBtn.unbind('click').click(function(){
		var msg = window.top.$.messager;
		var url = "";
		if (window.isAddAction)
			url = "/admin/stockindex!save.action";
		else
			url = "/admin/stockindex!update.action";
		var _this = $(this);
		if (_this.hasClass('disabled') || !stkForm.valid()) return false;
		_this.addClass('disabled').linkbutton({iconCls: 'icon-loading'});
		$.post(url, stkForm.serialize(), function(resp){
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