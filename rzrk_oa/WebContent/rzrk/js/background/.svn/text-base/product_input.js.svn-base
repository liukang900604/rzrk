function initValidate(){
	var rules = {}, messages = {};
	rules = {
		"product.name" : {
			required : true,
			rangelength : [ 1, 30 ],
		},
		"product.nameShort" : {
			rangelength : [ 1, 30 ]
		},
		"product.nameSShort" : {
			required : true,
			rangelength : [ 1, 30 ]
		},
		"product.investRange" : {
			rangelength : [ 1, 30 ]
		},
		"product.parValue" : {
			rangelength : [ 1, 20 ]
		},
		"product.supervisionOrganization" : {
			rangelength : [ 1, 30 ]
		},
		"product.trustee" : {
			rangelength : [ 1, 30 ]
		},
		"product.custodianBank" : {
			rangelength : [ 1, 30 ]
		},
		"product.broker" : {
			rangelength : [ 1, 30 ]
		},
        "product.futuresBroker" : {
            rangelength : [ 1, 30 ]
        },
		"product.investConsultant" : {
			rangelength : [ 1, 30 ]
		},
        "product.investManager" : {
            rangelength : [ 1, 30 ]
        },
		"product.minSubAmount" : {
			rangelength : [ 1, 30 ]
		},
		"product.lockUpPeriod" : {
			rangelength : [ 1, 30 ]
		},
		"product.openDate" : {
			rangelength : [ 1, 50 ]
		},
		"product.valuationDate" : {
			rangelength : [ 1, 30 ]
		},
		"product.minAddAmount" : {
			rangelength : [ 1, 30 ]
		},
		"product.addDeadline" : {
			rangelength : [ 1, 30 ]
		},
        "product.addAppDeadline" : {
            rangelength : [ 1, 30 ]
        },
		"product.duration" : {
			rangelength : [ 1, 30 ]
		},
		"product.subFee" : {
			rangelength : [ 1, 30 ]
		},
		"product.managerFee" : {
			rangelength : [ 1, 30 ]
		},
		"product.redemptionFee" : {
			rangelength : [ 1, 30 ]
		},
		"product.redemptionAppDeadline" : {
			rangelength : [ 1, 30 ]
		},
		"product.floatManagerFee" : {
			rangelength : [ 1, 30 ]
		},
		"product.trustDividend" : {
			rangelength : [ 1, 30 ]
		},
		"product.roadshowPeriod" : {
			rangelength : [ 1, 30 ]
		},
		"product.establishDate" : {
			rangelength : [ 1, 30 ]
		},
		"product.subAccountName" : {
			rangelength : [ 1, 30 ]
		},
		"product.subAccountBank" : {
			rangelength : [ 1, 30 ]
		},
		"product.subAccount" : {
			rangelength : [ 1, 30 ]
		},
		"product.weight" : {
			rangelength : [ 1, 5 ],
			required : true,
			digits : true
		}
	};
	messages = {
		"product.name" : {
			required : "产品全称不能为空",
			rangelength : "产品全称过长"
		},
		"product.nameShort" : {
			rangelength : "值过长"
		},
		"product.nameSShort" : {
			required : "产品超简称不能为空",
			rangelength : "值过长"
		},
		"product.investRange" : {
			rangelength : "值过长"
		},
		"product.parValue" : {
			rangelength : "值过长"
		},
		"product.supervisionOrganization" : {
			rangelength : "值过长"
		},
		"product.trustee" : {
			rangelength : "值过长"
		},
		"product.custodianBank" : {
			rangelength : "值过长"
		},
		"product.broker" : {
			rangelength : "值过长"
		},
		"product.futuresBroker" : {
			rangelength : "值过长"
		},
		"product.investConsultant" : {
			rangelength : "值过长"
		},
        "product.investManager" : {
            rangelength : "值过长"
        },
		"product.minSubAmount" : {
			rangelength : "值过长"
		},
		"product.lockUpPeriod" : {
			rangelength : "值过长"
		},
		"product.openDate" : {
			rangelength : "值过长"
		},
		"product.valuationDate" : {
			rangelength : "值过长"
		},
		"product.minAddAmount" : {
			rangelength : "值过长"
		},
		"product.addDeadline" : {
			rangelength : "值过长"
		},
        "product.addAppDeadline" : {
            rangelength : "值过长"
        },
		"product.duration" : {
			rangelength : "值过长"
		},
		"product.subFee" : {
			rangelength : "值过长"
		},
		"product.managerFee" : {
			rangelength : "值过长"
		},
		"product.redemptionFee" : {
			rangelength : "值过长"
		},
		"product.redemptionAppDeadline" : {
			rangelength : "值过长"
		},
		"product.floatManagerFee" : {
			rangelength : "值过长"
		},
		"product.trustDividend" : {
			rangelength : "值过长"
		},
		"product.roadshowPeriod" : {
			rangelength : "值过长"
		},
		"product.establishDate" : {
			rangelength : "值过长"
		},
		"product.subAccountName" : {
			rangelength : "值过长"
		},
		"product.subAccountBank" : {
			rangelength : "值过长"
		},
		"product.subAccount" : {
			rangelength : "值过长"
		},
		"product.weight" : {
			rangelength : "值过长",
			required : "权重不能为空",
			digits : "权重必须为整数"
		}
	};
	$('#pdtForm').validate({
		ignore: "",
		focusInvalid: true,
        errorElement: "span",
        errorPlacement: function(error, el) {
        	error.appendTo(el.parents('td'));
        },
        rules: rules,
        messages: messages
	});
};
function initEvents(){
	var pdtForm = $('#pdtForm');
	saveBtn = $("#saveBtn");
	closeBtn = $("#closeBtn");
	saveBtn.unbind('click').click(function(){
		var msg = window.top.$.messager;
		var url = "";
		if (window.isAddAction)
			url = "/admin/product!save.action";
		else
			url = "/admin/product!update.action";
		var _this = $(this);
		if (_this.hasClass('disabled') || !pdtForm.valid()) return false;
		_this.addClass('disabled').linkbutton({iconCls: 'icon-loading'});
		$.post(url, pdtForm.serialize(), function(resp){
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