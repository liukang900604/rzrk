/**
 * 导航URL加载配置
**/
var urlConfig = function(index){
	url = {
		"index": {url: "/rzrk/article!viewList.action", menuIndex: 0, callback: initIndexPage},
		"product": {url: "/rzrk/product!preview.action", menuIndex: 1, callback: initProductPage},
		"about": {url: "/rzrk/about.html", menuIndex: 2, callback: initAboutPage},
		"service": {url: "/rzrk/service.html", menuIndex: 3, callback: initServicePage},
		"information": {url: "/rzrk/article!information.action", menuIndex: 4, callback: initInformationPage}
	};
	return url[index];
};
/**
 * 公用过度效果
**/

var transition = function(el, inOut, options){
	var nextEl = null;
	if (el.size() == 0 || !el.is("div"))
		return false;
		
	var opts = {};
	if (options.direction == "left"){
		opts = inOut == "out" ? {left:990} : {left:0};
		nextEl = el.prev();
	}else{
		opts = inOut == "out" ? {right:990} : {right:0};
		nextEl = el.next();
	};
	var config = {duration:500,easing:"easeInExpo",complete: function(){
			if ((nextEl.size() == 0 && inOut == "in" && options.direction == "right")
					||
				(!nextEl.is("div") && inOut == "in" && options.direction == "left")){
				var container = $(".container");
				$(".wrap-remove").remove();
				container.append($(".wrap").find(">div").removeAttr("style").unwrap());
				container.find(".nav-prev, .nav-next").show();
				options.callback();
			};
	}};
	if (inOut == "in") config.easing = "easeOutExpo";
	el.animate(opts,config);
	setTimeout(function(){transition(nextEl, inOut, options);},50);
};
/**
 * 页面动画预处理
**/
var pageAnimatePretreatment = function(options){
	var wrap, wrapRemove, inEl, outEl;
	wrap = $(".wrap");
	wrapRemove = $(".wrap-remove");
	if (options.direction == "left"){
		inEl = wrap.find(">div:last");
		outEl = wrapRemove.find(">div:last");
	}else{
		inEl = wrap.find("div:eq(0)");
		outEl = wrapRemove.find("div:eq(0)");
	};
	transition(outEl, "out", options);
	setTimeout(function(){transition(inEl, "in", options);}, 600);
};
/**
 * 左右导航
**/
var startNav = function(options){
	var wrap, container;
	container = $(".container");
	container.find(".nav-prev, .nav-next").remove();
	container.wrapInner("<div class='wrap-remove'></div>");
	wrap = $("<div class='wrap'></div>");
	wrap.load(options.url, function(){
		wrap.find(">div").css(options.direction, "-990px");
		container.append(wrap);
		pageAnimatePretreatment(options);
	});
};
var nav = function(index, direction){
	var options = {
		url: urlConfig(index).url,
		direction: direction,
		callback: urlConfig(index).callback
	};
	startNav(options);
	navMenuSelected(urlConfig(index).menuIndex);
	return false;
};
var initChartsOptions = function(){
    Highcharts.setOptions({
        lang:{
            weekdays: ['星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六'],
            months: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'],
            shortMonths: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一', '十二']
        }
    });
};
var loadCharts = function(title, data){
	$('.chart-content').highcharts('StockChart', {
        chart:{ height: 115 },
        colors: [ '#ff0000','#0d233a','#8bbc21','#910000','#1aadce','#492970','#f28f43','#77a1e5','#c42525','#a6c96a'],
        scrollbar: { enabled: false },
        rangeSelector : { enabled: false },
        navigator: { enabled: false },
        credits: { enabled: false },
        title: {
            text: title,
            align: "center",
            floating: true,
            style: "font-size:12px;"
        },
        series : [{
            data : data,
            shadow : false,
            tooltip : { valueDecimals : 2 },
            lineWidth: 1.5
        }],
        tooltip: {
            formatter: function() {
                var s = Highcharts.dateFormat('%Y %b %e', this.x);
                $.each(this.points, function(i, point) {
                    s += ' | <b>' + Math.round(point.y * 100 * 100) / 100 + '%</b>';
                });
                return s;
            }
        },
        yAxis: {
            labels: {
                formatter: function() {
                    return (this.value > 0 ? '+' : '') + this.value * 100 + '%';
                }
            },
            plotLines: [{
                value: 0,
                width: 2,
                color: 'silver'
            }]
        }
    });
};
/**
 * 初始化首页
**/
var initIndexPage = function(){
    var dataRows = $(".net-product-value tr[data-chart-data]");
    var title = dataRows.eq(0).find("td:eq(0)").text();;
    var data = dataRows.eq(0).data("chartData");
    //loadSlider();
    loadCharts(title, data);
    dataRows.mouseenter(function(){
        title = $(this).find("td:eq(0)").text();;
        data = $(this).data("chartData");
        loadCharts(title, data);
    });
    $.each(dataRows, function(i, n) {
        $(n).bind("click", function() {
            window.location.href = "/rzrk/product!detail.action?id=" + $(this).attr("id");
        });
    });
    $(".net-product-value .more").click(function(){
    	nav("product", "right");
    	return false;
    });
};
/**
 * 初始化旗下产品
 **/
var initProductPage = function(){
	var lists = $(".products .list");
	var subMenu = $(".products .sub-menu");
	var li = lists.find("li");
	$(".scroll").mCustomScrollbar({
		theme:"minimal-dark"
	});
	li.data("isOpen", true).click(function(){
		console.log($(this).data("isOpen"));
		var target = subMenu.eq(lists.index($(this).parents(".list")));
		var em = $(this).find("em");
		if ($(this).data("isOpen")){
			target.slideUp(300, function(){em.text("▼");});
			$(this).data("isOpen", false);
		}else{
			target.slideDown(300, function(){em.text("▲");});
			$(this).data("isOpen", true);
		}
	});
	var lis = $(".products ul.sub-menu > li");
	lis.click(function(){
        var id = $(this).attr("productId");
        lis.removeClass("selected");
        $(this).addClass("selected");
        window.location.assign("/rzrk/product!detail.action?id=" + id);
	});
    // loadSlider();
};
var initAboutPage = function(){
    // loadSlider();
};
var initServicePage = function(){
    // loadSlider();
    $(".s.ie").click(function(){
        $(".logo a").eq(0).click();
    });
};
var initInformationPage = function(){
	// loadSlider();
};
/**
 * 初始化事件
**/
var initEvents = function(){
	$(".header").find("> ul > li > a").click(function(){
		var index, oldIndex, newIndex, ul, as ,a;
		if ($(this).hasClass("active")) return false;
		index = $(this).data("index");
		ul = $(".header > ul");
		as = ul.find(">li a");
		a = ul.find(">li a.active");
		oldIndex = as.index(a);
		newIndex = as.index(this);
		if (newIndex < oldIndex)
			nav(index, "left");
		else
			nav(index, "right");
		return false;
	});
	$(".container").on("click", ".nav-prev" ,function(){
		var index = $(this).find("span").attr("class");
		nav(index, "left");
		return false;
	});
	$(".container").on("click", ".nav-next" ,function(){
		var index = $(this).find("span").attr("class");
		nav(index, "right");
		return false;
	});
	// 点击放大二维码
    $(".fancybox-effects-a").fancybox({
        helpers: {
            title : {
                type : 'outside'
            },
            overlay : {
                speedOut : 0
            }
        }
    });
    // 二维码关闭
    $(".quick-mark .close").click(function(){
    	$(".quick-mark").remove();
    	return false;
    });
};
$(function(){
    var params = getParams(window.location.href);
    var config = urlConfig(params.page);
    if (config == undefined){
        config = urlConfig("index");
        params.page = "index";
    }
    initEvents();
    initChartsOptions();
	$(".container").load(config.url, function(){
        config.callback();
		navMenuSelected(urlConfig(params.page).menuIndex);
	});
});