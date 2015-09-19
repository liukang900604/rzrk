var saveBtn = '';
Date.prototype.format = function (format) {
    var o = {
        "M+": this.getMonth() + 1, //month
        "d+": this.getDate(), //day
        "h+": this.getHours(), //hour
        "m+": this.getMinutes(), //minute
        "s+": this.getSeconds(), //second
        "q+": Math.floor((this.getMonth() + 3) / 3), //quarter
        "S": this.getMilliseconds() //millisecond
    };
    if (/(y+)/.test(format)) {
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
        }
    }
    return format;
};
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
function initValidate(){
	var vRules = {
		required: true,
		number: true
	};
	var vMessages = {
		required: '不能为空，必须输入',
		number: '只能输入数字'
	};
	$('#nvForm').validate({
		ignore: "",
        errorElement: "span",
        errorPlacement: function(error, el) {
        	error.appendTo(el.parents('td'));
        },
        rules: {
        	'pdr.stockAccountTotalAmount': vRules,
        	'pdr.futureAccountTotalAmount': vRules,
        	'pdr.stockMarketValue': vRules,
        	'pdr.futureMarketVaue': vRules,
        	'pdr.futureEmptyValue': vRules,
        	'pdr.sum': vRules,
        	'pdr.bankAmount': vRules,
        	'pdr.total': vRules,
        	'pdr.beforeReduce': vRules,
        	'pdr.amount': vRules,
        	'pdr.reduceNetValue': vRules,
        	'pdr.beforeReduceNetValue': vRules,
        	'pdr.assetsNetValue': vRules,
        	'pdr.recordDate': {
        		required: true
        	}
        },
        messages: {
        	'pdr.stockAccountTotalAmount': vMessages,
        	'pdr.futureAccountTotalAmount': vMessages,
        	'pdr.stockMarketValue': vMessages,
        	'pdr.futureMarketVaue': vMessages,
        	'pdr.futureEmptyValue': vMessages,
        	'pdr.sum': vMessages,
        	'pdr.bankAmount': vMessages,
        	'pdr.total': vMessages,
        	'pdr.beforeReduce': vMessages,
        	'pdr.amount': vMessages,
        	'pdr.reduceNetValue': vMessages,
        	'pdr.beforeReduceNetValue': vMessages,
        	'pdr.assetsNetValue': vMessages,
        	'pdr.recordDate': {
        		required: "必须选择一个日期"
        	}
    	}
	});
};
function initEvents(){
	var nvForm = $('#nvForm');
	saveBtn = $("#saveBtn");
	closeBtn = $("#closeBtn");
	saveBtn.unbind('click').click(function(){
		var msg = window.top.$.messager;
		var url = "";
		if (window.isAddAction)
			url = "/admin/product_daily_record!save.action";
		else
			url = "/admin/product_daily_record!update.action";
		var _this = $(this);
		if (_this.hasClass('disabled') || !nvForm.valid()) return false;
		_this.addClass('disabled').linkbutton({iconCls: 'icon-loading'});
		$.post(url, nvForm.serialize(), function(resp){
			resp = eval("("+resp+")");
			if (resp.status == 'success'){
				msg.alert('保存信息', '保存信息成功！', 'info');
				window.top.reloadDataGridInTab('产品每日汇总列表');
				window.top.reloadDataGridInWindow('产品每日汇总列表');
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
};
function initData(){
	$('#recordDate').datebox('setValue', new Date().format('yyyy-MM-dd'));
}
$(function(){
	initValidate();
    initEvents();
    initData();
});