var startIndex = "";
var urlConfig = function(index){
    var prefix = "product-detail/product-detail-";
    url = {
        "chart": {url: prefix + "chart.html", tabIndex: 0, callback: initChart},
        "summary": {url: "product!summary.action", tabIndex: 1, callback: initSummary},
        "net-value": {url: "product!netValue.action", tabIndex: 2, callback: initNetValue},
        "subscription": {url: "product!subscription.action", tabIndex: 3, callback: initSubscription},
        "redemption": {url: "product!redemption.action", tabIndex: 4, callback: initRedemption},
        "product-announcement": {url: "product!announcement.action", tabIndex: 5, callback: initProductAnnouncement}
    };
    return url[index];
};
var resetTabZIndex = function(){
    var lis = $(".tab > li");
    lis.removeClass("selected");
    $.each(lis, function(i, li){
        $(li).css("z-index", lis.size() - i);
    });
};
var resetProductDetail = function(index){
    var details = $("div.detail");
    details.not("." + index).empty();
};
var setListHeight = function(){
    var height = $(".product-detail").height();
    $(".products").height(height);
};
var selectListItem = function(index){
    var lis = $(".products > ul > li");
    return lis.eq(index).addClass("selected").text();
};
var initEvents = function(){
    var productDetail = $(".product-detail");
    var span = $(".products > h3 span");
    var lis = $(".products ul.sub-menu > li");
	$(".scroll").mCustomScrollbar({
		theme:"minimal-dark"
	});
    lis.click(function(){
        var detailTitle = $(this).text();
        if ($(this).hasClass("selected"))
            return false;
        productDetail.find("h3").text(detailTitle);
        span.text("> " + detailTitle);
        lis.removeClass("selected");
        $(this).addClass("selected");
        loadPageFragment($(".tab > li[class=selected]").attr("data-index"), "id=" + $(".products li[class=selected]").attr("productId"));
    });
    var tabLis = $(".tab > li");
    tabLis.click(function(){
        if ($(this).hasClass("selected"))
            return false;
        var index = $(this).data("index");
        resetTabZIndex();
        tabLis.removeClass("selected");
        loadPageFragment(index, "id=" + $(".products li[class=selected]").attr("productId"));
    });
	var lists = $(".products .list");
	var subMenu = $(".products .sub-menu");
	var li = lists.find("li");
	li.data("isOpen", true).click(function(){
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
};
var initSummary = function(index, data){
    resetProductDetail(index);
    setListHeight();
};
var initNetValue = function(index, data){
    resetProductDetail(index);
    setListHeight();
    $( "#beginTime, #endTime" ).datepicker({
        dateFormat: "yy-mm-dd"
    });
};
var initChart = function(index, data){
    var seriesOptions = [],
        seriesCounter = 0,
        names = [[ $(".products li[class=selected]").attr("productId"), $(".products li[class=selected]").find("span").text() ], [ $(".products li[class=selected]").attr("productId"), "HS300" ]];
    if (window.showHS300 == 0){
        names = [[ $(".products li[class=selected]").attr("productId"), $(".products li[class=selected]").find("span").text() ]];
    }
    
    var createChart = function(){
        $('.chart-content').highcharts('StockChart', {
            chart: {
                width: 710,
                height: 400
            },
            colors: [ '#ff0000','#2f7ed8'],
            credits: { enabled: false },
            rangeSelector : { enabled: false },
            yAxis: {
                labels: {
                    formatter: function() {
                        // 非常规处理nerve 由初始值设置了0.001引起
                        return (this.value > 0 ? '+' : '') + this.value / 100000 + '%';
                    }
                },
                plotLines: [{
                    value: 0,
                    width: 2,
                    color: 'silver'
                }]
            },
            plotOptions: {
                series: {
                    compare: 'percent'
                }
            },
            tooltip: {
                pointFormat: '<span style="color:{series.color}">{series.name}</span>: <b>{point.y}%</b><br/>',
                valueDecimals: 2
            },
            series: seriesOptions,
            scrollbar: {
                enabled: false
            },
            navigator: {
            	enabled: false
            }
        });
    };
    var loadChart = function(){
        $.each(names, function(i, name) {
            $.getJSON('chart!trendChart.action?chartType=' + name[i,1] +'&productId=' + name[i,0], function(data){
                seriesOptions[i] = {
                    name: name[i,1],
                    data: data
                };
                seriesCounter++;
                if (seriesCounter == names.length) {
                    createChart();
                }
            });
        });
        if ($('.chart-content').children().size() == 0) {
            $('.chart-content').html("<p style='margin: 10px;'>暂无数据</p>");
        }
    };
    var loadEarnData = function() {
    	$.ajax({
            url : "/rzrk/product!getProductEarnCollectData.action",
            data : {
                productId : $(".products li[class=selected]").attr("productId")
            },
            type : "POST",
            dataType : "json",
            cache : false,
            success : function(data) {
                $("#earnWeek").text(data.earnWeek);
                $("#earnMonth").text(data.earnMonth);
                $("#earnTotal").text(data.earnTotal);
                $("#aror").text(data.aror);
            }
        });
    };
    resetProductDetail(index);
    setListHeight();
    loadChart();
    loadEarnData();
};
var initSubscription = function(index, data){
    resetProductDetail(index);
    setListHeight();
};
var initRedemption = function(index, data){
    resetProductDetail(index);
    setListHeight();
};
var loadPageFragment = function(index, params){
    var config = urlConfig(index);
    $(".tab > li").removeClass("selected")
        .eq(urlConfig(index).tabIndex)
            .css("z-index", 6)
                .addClass("selected");
    $("div." + index).load(config.url + "?" + params, function(data){
        config.callback(index, data);
    });
};
var initProductAnnouncement = function(index, data){
    resetProductDetail(index);
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
    setListHeight();
};
$(function(){
	startIndex = $("#startIndex").val() == "" ? "chart" : $("#startIndex").val();
    var params = getParams(window.location.href);
    var detailTitle = $(".products ul.sub-menu li[productid='" + params.id + "']").addClass("selected").find("span").text();
    initEvents();
    initChartsOptions();
    resetTabZIndex();
    $(".header > ul > li > a").eq(1).addClass("active");
    $(".product-detail h3").text(detailTitle);
    $(".products > h3 span").text("> " + detailTitle);
    var paramsStr = getParamsStr(window.location.href);
    loadPageFragment(startIndex, paramsStr);
});