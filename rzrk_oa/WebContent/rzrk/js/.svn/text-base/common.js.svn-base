var loadSlider = function(){
    $(".slider").each(function(){
        $(this).nivoSlider({
            effect: "fade",
            animSpeed: 1000,
            pauseTime: 5000,
            prevText: "",
            nextText: "",
            controlNav: false
        });
    });
};
var navMenuSelected = function(menuIndex){
    var ul = $(".header > ul");
    var as = ul.find(">li > a").removeClass("active");
    as.eq(menuIndex).addClass("active");
};
var getParams = function(url) {
    if (url.indexOf("#") != -1){
        paramStr = url.substring(url.indexOf("?") + 1, url.indexOf("#")).split("&");
    }else{
        paramStr = url.substring(url.indexOf("?") + 1, url.length).split("&");
    };
    paramObj = {};
    for (var i = 0; i < paramStr.length; i++) {
        str = paramStr[i];
        paramObj[str.substring(0, str.indexOf("="))] = str.substring(str.indexOf("=") + 1, str.length);
    };
    return paramObj;
};
var getParamsStr = function(url) {
    if (url.indexOf("#") != -1){
        paramStr = url.substring(url.indexOf("?") + 1, url.indexOf("#"));
    }else{
        paramStr = url.substring(url.indexOf("?") + 1, url.length);
    };
    return paramStr;
};
var initChartsOptions = function(){
    Highcharts.setOptions({
        lang:{
            weekdays: ['星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六'],
            months: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'],
            shortMonths: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一', '十二'],
            rangeSelectorFrom: "从",
            rangeSelectorTo: "到",
            rangeSelectorZoom: "缩放"
        }
    });
};
!function(){
    /**
     * 初始化事件
    **/
    var initEvents = function(){
        var back = $(".logo a");
        var img = back.find("img");
        var text = back.find(".text");
        var en = back.find(".en");
        $(".logo a").hover(function(){
            img.animate({"bottom": "-45px"},{duration:200, queue: false});
            text.stop().fadeIn(200);
            en.stop().fadeIn(200);
        }, function(){
            img.animate({"bottom": "5px"},{duration:200, queue: false});
            text.stop().fadeOut(200);
            en.stop().fadeOut(200);
        });
        $(".partner > a").click(function(){
            $(this).addClass("open", 200, function(){
                $(".logo-wrap").fadeIn(300);
            });
            return false;
        });
        var clearId = 0;
        $(".header > ul > li").mouseenter(function(){
            clearTimeout(clearId);
            $(".header > ul > li > a").removeClass("hover");
            $(".header .sub-menu").hide();
            $(this).find(".sub-menu").show();
            $(this).find("> a").addClass("hover");
        }).mouseleave(function(){
            var $this = $(this);
            clearId = setTimeout(function(){
                $this.find(".sub-menu").hide();
                $this.find("> a").removeClass("hover");
            }, 150);
        });
        var error = $("div.error");
        $(".login input").focusin(function(){
            var tips = $(this).data("tips");
            tips = $("div." + tips);
            tips.fadeIn(300);
            $(this).addClass("focus", 300);
        }).blur(function(){
            var tips = $(this).data("tips");
            tips = $("div." + tips);
            tips.fadeOut(300);
            $(this).removeClass("focus", 300);
        });
        $(".logged a.user").click(function(){
            $(".logged .user-menu").fadeIn(300).focus();
            return false;
        });
        $(".logged .user-menu").blur(function(){
            $(this).fadeOut(300);
        });
        // 登录
        $(".input-panel").data("isShow", false);
        var close = $(".login a.close");
        $(".login a:not(.close)").click(function(){
            var isShow = $(".input-panel").data("isShow");
            if (isShow){
            	if ($("#userIdentification").val().trim() == ""){
            		$("#userIdentification").focus();
            		$(".input-panel div.error").text("身份证号不能为空").fadeIn(500).delay(1500).fadeOut(500);
            		return false;
            	}
            	if ($("#password").val().trim() == ""){
            		$("#password").focus();
            		$(".input-panel div.error").text("密码不能为空").fadeIn(500).delay(1500).fadeOut(500);
            		return false;
            	}
               $.ajax({  
                   url:"/rzrk/member!ajaxLogin.action",
                   data:{userIdentification:$("#userIdentification").val(),password:$("#password").val()},
                   type:"post",
                   dataType:"json",
                   success:function(res){
                       if(res.status!='success'){
                            $(".input-panel div.error").text(res.messages).fadeIn(500).delay(1500).fadeOut(500);
                            //$("#idCard").focus();
                            return
                        }
                        var user=eval(res.message)
                        $("#username").html(user.username);
                        $("#dateMsg").html(new Date().getHours()<11?"上午好!":"下午好!");
                        $(".login").hide();
                        $(".logged").show().css({display:'block'});
                    }
                });
            }else{
                $(".input-panel").data("isShow", true);
                $(".input-panel").fadeIn(300).css("display", "inline");
                close.fadeIn(500).animate({right: "-22px"},{duration:500, queue: false});
            }
            return false;
        });
        close.click(function(){
            $(".input-panel").data("isShow", false).fadeOut(300);
            close.fadeOut(500).animate({right: "0" },{duration:500, queue: false});
        });
        // 注销
        /*
        $(".logged a.logout").click(function(){
            $(".input-panel").data("isShow", false).hide();
            close.css({right: 0}).hide();
            $(".logged").hide();
            $(".login").show();
            return false;
        });
        */
    };
    var loadPartnerLogo = function(){
        var logoWrap = $(".logo-wrap");
        var logoPanel = $(".logo-panel");
        logoPanel.load("/rzrk/partner-logo.html", function(){
        	logoWrap.find("a.close").click(function(){
                $(".logo-wrap").fadeOut(300, function(){
                    $(".partner > a").removeClass("open", 200);
                });
                return false;
            });
        })
    };
    $(function(){
        initEvents();
        loadPartnerLogo();
    });
}()
