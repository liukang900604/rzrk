var msg = window.top.$.messager;
var editor = "";

jQuery.validator.addMethod("chars",function(value, element){return this.optional(element) || /^[\u0391-\uFFE5\w]+$/.test(value);},"只允许包含中文、英文、数字和下划线");
function initValidate(){
	var rules = {}, messages = {};
	rules = {
		"workFlowType.typeName": {
			required: true,
			chars: true,
			rangelength: [2, 10]
		}
	};
	messages = {
		"workFlowType.typeName": {
			required: "不能为空，请输入",
			rangelength: "长度只能在 {0} - {1} 之间"
		}
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

function initEvents(){
	var atForm = $('#atForm');
	saveBtn = $("#saveBtn");
	closeBtn = $("#closeBtn");
	saveBtn.unbind('click').click(function(){
		var msg = window.top.$.messager;
		var _this = $(this);
		if (_this.hasClass('disabled') || !atForm.valid()){
			return false;
		}
		 $.ajax({    
          type:'post',        
          url: '/admin/work_flow!saveWorkFlowType.action',    
          data: $('#atForm').serialize(),    
          cache:false,    
          dataType:'json',    
          success:function(data){    
           if(data.status == 'success'){
        	   msg.alert('保存信息', data.message,'info',function(){
        		   window.top.reloadDataGridInTabTab('工作流定义', '工作流类型');
            	   window.top.closeCurrentWindow();
        	   });
        	   
           }else{
        	   msg.alert('保存信息', data.message,"error",function(){
             	   return false;
        	   });
           }
          } 
             
        });
	});
	closeBtn.unbind('click').click(function(){
		var _this = $(this);
		window.top.closeCurrentWindow();
		_this.removeClass('disabled').linkbutton({iconCls: 'icon-save'});
	});
};




$(function(){
    //增加页面校验规则
	initValidate();
    initEvents();
});