var startIndex = "report";
var urlConfig = function(index){
    var prefix = "service-detail/";
    url = {
        "report": {url:"/rzrk/article!report.action", tabIndex: 0, title: "投资报告", callback: syncHeight},
        "prompt": {url: "/rzrk/reservations!prompt.action", tabIndex: 1, title: "", callback: initPrompt},
        "order": {url: "/rzrk/reservations!order.action", tabIndex: 2, title: "预约订购", callback: initOrder},
        "pef": {url: prefix + "service-private-equity-fund.html", tabIndex: 3, title: "关于私募基金", callback: initPEF},
        "products": {url: prefix + "service-products.html", tabIndex: 4, title: "关于产品", callback: initProducts},
        "sar": {url: prefix + "service-sbs-and-rdp.html", tabIndex: 5, title: "关于认购与赎回", callback: initSAR}
    };
    return url[index];
};
var resetServiceDetail = function(){
    var detail = $(".service-detail");
    detail.empty();
};
var issueEffect = function(){
    $("div p.answer").data("isOpen", false);
    var ps = $("div p.issue");
    ps.click(function(){
        var p = $(this).next();
        if (p.data("isOpen")){
            p.slideUp(300, function(){setListHeight();});
            $(this).removeClass("selected");
            p.data("isOpen", false);
        }else{
            p.slideDown(300, function(){setListHeight();});
            $(this).addClass("selected");
            p.data("isOpen", true);
        }
    });
};
var setListHeight = function(){
    var height = $(".service-detail").height();
    $(".service-menu").height(height);
};
var selectListItem = function(index){
    var lis = $(".service-menu > ul > li");
    if (index == "pef" || index == "products" || index == "sar"){
    	lis.eq(2).addClass("selected");
    }
    var ul = $(".service-menu > ul");
    var li = ul.find(" > li[data-index=" + index + "]");
    if (li.size() == 0)
        ul.find("li:eq(0)").addClass("selected");
    else
        li.addClass("selected");
};
var syncHeight = function(data){
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
	$("#orderForm").validate({
		success: function(success, element) {
			$(element).parent().find(".tips").hide();
			success.addClass("success").text("");
		},
		errorPlacement: function(error, element) {
			element.parent().find(".tips").hide();
			element.parent().append(error);
		},
		rules: {
			reservAmount: {
				required: true,
				min: 1000000
			},
			contactPerson: {
				required: true,
				minlength: 2,
				maxlength: 10
			},
			contactPhone: {
				required: true,
				regex: /^\d{3,4}-\d{8}$|^\d{8}$/
			},
			contactMobile: {
				required: true,
				regex: /^\d{11}$/
			},
			fax: {
				regex: /^\d{3,4}-\d{8}$|^\d{8}$/
			},
			email: {
				required: true,
				email: true
			},
			zipCode: {
				regex: /^\d{6}$/
			}
		},
		messages: {
			reservAmount: {
				required: function(is, el){
					errorProccess(el);
					return "此项不能为空";
				},
				min:  function(is, el){
					errorProccess(el);
					return "此产品最小认购金额不得低于1,000,000";
				}
			},
			contactPerson: {
				required: function(is, el){
					errorProccess(el);
					return "此项不能为空";
				},
				minlength: function(is, el){
					errorProccess(el);
					return "联系人名称最少输入2个字";
				},
				maxlength: function(is, el){
					errorProccess(el);
					return "联系人名称最大输入10个字";
				}
			},
			contactPhone: {
				required: function(is, el){
					errorProccess(el);
					return "此项不能为空";
				},
				regex: function(is, el){
					errorProccess(el);
					return "请输入有效的固话号码，如：010-12345678";
				}
			},
			contactMobile: {
				required: function(is, el){
					errorProccess(el);
					return "此项不能为空";
				},
				regex: function(is, el){
					errorProccess(el);
					return "请输入有效的移动号码";
				}
			},
			fax: {
				regex: function(is, el){
					errorProccess(el);
					return "请输入有效的传真号码";
				}
			},
			email: {
				required: function(is, el){
					errorProccess(el);
					return "此项不能为空";
				},
				email: function(is, el){
					errorProccess(el);
					return "请输入有效的电子邮箱地址";
				}
			},
			zipCode: {
				regex: function(is, el){
					errorProccess(el);
					return "请输入有效的邮编";
				}
			}
		}
	});
};

formValidate.valid = function(){
	return $("#orderForm").valid();
};
var initPrompt = function(data){
    $(".accept").click(function(){
        loadPageFragment("order");
        return false;
    });
    $(".reject").click(function(){
        window.location.assign("/rzrk/index!index.action");
        return false;
    });
    setListHeight();
};
var initOrder = function(data){
	formValidate.init();
    $(".confirm").click(function(){
    	var valid = formValidate.valid();
    	if (valid){
    		var url = $("#orderForm").attr("action");
    		var method = $("#orderForm").attr("method");
    		var params = $("#orderForm").serialize();
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
    $("#orderForm").find("input[type=text], textarea").click(function(){
    	var div = $(this).parent("div.row");
    	if (div.find(".error").size() == 0)
    		div.find(".tips").show();
    }).blur(function(){
    	var div = $(this).parent("div.row");
    	div.find(".tips").hide();
    });
    setListHeight();
};
var initPEF = function(data){
    setListHeight();
    issueEffect();
};
var initProducts = function(data){
    setListHeight();
    issueEffect();
};
var initSAR = function(data){
    setListHeight();
    issueEffect();
};
var initEvents = function(){
    var lis = $(".service-menu > ul > li");
    lis.click(function(){
        if ($(this).hasClass("selected"))
            return false;
        var pageIndex = $(this).data("index");
        if (pageIndex == "FAQ")
            return false;
        lis.removeClass("selected");
        $(this).addClass("selected");
        window.location.assign("/rzrk/reservations!serviceIndex.action?page=" + pageIndex);
    });
    var subMenu = $(".service-menu .sub-menu");
    lis.eq(2).unbind("click").click(function(){
        var isOpen = subMenu.data("isOpen");
        if (isOpen){
            subMenu.slideUp(500);
            subMenu.data("isOpen", false);
        }else{
            subMenu.slideDown(500);
            subMenu.data("isOpen", true);
        }
    });
};
var loadPageFragment = function(index,params){
    var config = urlConfig(index);
    config = config == undefined ? urlConfig(startIndex) : config;
    $(".service-menu > h3 span").text("> " + config.title);
    $(".service-detail").load(config.url + "?" + params, function(data){
        config.callback(index,data);
    });
};
$(function(){
    var params = getParams(window.location.href);
    var subMenu = $(".service-menu .sub-menu");
    if (params.page == "FAQ")
        params.page = "pef";
    if (params.page == "pef" || params.page == "products" || params.page == "sar"){
        subMenu.show().data("isOpen", true);
    }else{
        subMenu.data("isOpen", false);
    }
    selectListItem(params.page);
    initEvents();
    $(".header > ul > li > a").eq(3).addClass("active");
    loadPageFragment(params.page,getParamsStr(window.location.href));
});

