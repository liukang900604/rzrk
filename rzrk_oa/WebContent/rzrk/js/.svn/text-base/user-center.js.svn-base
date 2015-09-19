var startIndex = "userinfo";
var urlConfig = function(index){
    var prefix = "user-center/";
    url = {
        "userinfo": {url: "/rzrk/member!memberList.action", tabIndex: 0, callback: initUserInfo},
        "products": {url:  "/rzrk/member!productList.action", tabIndex: 1, callback: initProducts},
        "history": {url:  "/rzrk/member!history.action", tabIndex: 0, callback: initHistory},
        "change": {url:  "/rzrk/member!change.action", tabIndex: 0, callback: initChange}
    };
    return url[index];
};
var setListHeight = function(){
    var height = $(".user-detail > div").height();
    $(".user-menu").height(height);
};
var selectListItem = function(index){
    var ul = $(".user-menu > ul");
    var li = ul.find(" > li[data-index=" + index + "]");
    if (li.size() == 0)
        ul.find("li:eq(0)").addClass("selected");
    else
        li.addClass("selected");
};
var initEvents = function(){
    var lis = $(".user-menu > ul > li");
    lis.click(function(){
        if ($(this).hasClass("selected"))
            return false;
        var pageIndex = $(this).data("index");
        lis.removeClass("selected");
        $(this).addClass("selected");
        window.location.assign("/rzrk/member!memberCenter.action?page=" + pageIndex);
    });
};
var initUserInfo = function(data){
    setListHeight();
};
var initProducts = function(data){
    setListHeight();
};
var initHistory = function(data){
    setListHeight();
};

var formValidate = {};
formValidate.init = function(){
	var errorProccess = function(el){
		$(el).parent().find(".error").removeClass("success");
	};
	jQuery.validator.addMethod("regex", function(value, element, patt) {
		return this.optional(element) || patt.test(value);
	}, "");
	$("#changeForm").validate({
		success: function(success, element) {
			$(element).parent().find(".tips").hide();
			success.addClass("success").text("");
		},
		errorPlacement: function(error, element) {
			element.parent().find(".tips").hide();
			element.parent().append(error);
		},
		rules: {
			currentPwd: {
				required: true,
				rangelength: [6, 16],
				remote: {
					url: "/rzrk/member!vilidatePassWord.action",
					type: "post",
					data: {
						currentPwd: function(){
							return $("#currentPwd").val().trim();
						}
					}
				}
			},
			password: {
				required: true,
				rangelength: [6, 16]
			},
			repassword: {
				required: true,
				rangelength: [6, 16],
				equalTo: "input[name=password]"
			}
		},
		messages: {
			currentPwd: {
				required: function(is, el){
					errorProccess(el);
					return "旧密码不能为空";
				},
				rangelength: function(is, el){
					errorProccess(el);
					return "密码长度必须为 6 - 16 位";
				},
				remote: function(value){
					$("#currentPwd").parent().find(".error").removeClass("success");
					return "旧密码不对，请重新输入";
				}
			},
			password: {
				required: function(is, el){
					errorProccess(el);
					return "新密码不能为空";
				},
				rangelength: function(is, el){
					errorProccess(el);
					return "密码长度必须为 6 - 16 位";
				}
			},
			repassword: {
				required: function(is, el){
					errorProccess(el);
					return "确认密码不能为空";
				},
				rangelength: function(is, el){
					errorProccess(el);
					return "密码长度必须为 6 - 16 位";
				},
				equalTo: function(is, el){
					errorProccess(el);
					return "确认新密码输入不对，请重新输入";
				}
			}
		}
	});
};

formValidate.valid = function(){
	return $("#changeForm").valid();
};
var initChange = function(data){
    setListHeight();
    formValidate.init();
    $(".save").click(function(){
        var valid = formValidate.valid();
        if (valid){
        	var url = $("#changeForm").attr("action");
        	var method = $("#changeForm").attr("method");
        	var params = $("#changeForm").serialize();
        	$.ajax({
			     url : url,
		         data : params,
			     type : method,
			     dataType : "json",
			     success: function(res){
			    	 if(res.status!='success'){
			    		 alert(res.message);
			    		 return;
			    	 }
			    	 alert(res.message);
			     }
        	});
        }
        return false;
    });
};
var loadPageFragment = function(index){
    var config = urlConfig(index);
    config = config == undefined ? urlConfig(startIndex) : config;
    $(".user-detail").load(config.url, function(data){
        config.callback(data);
    });
};
$(function(){
    var params = getParams(window.location.href);
    selectListItem(params.page);
    initEvents();
    loadPageFragment(params.page);
});
