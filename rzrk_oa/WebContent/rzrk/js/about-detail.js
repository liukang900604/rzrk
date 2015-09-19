var startIndex = "summary";
var urlConfig = function(index){
    var prefix = "about-detail/";
    url = {
        "summary": {url: "about!introduction.action", tabIndex: 0, title: "公司简介", callback: syncHeight},
        "framework": {url: "about!organization.action", tabIndex: 1, title: "组织架构", callback: syncHeight},
        "idea": {url: "about!idea.action", tabIndex: 2, title: "投资理念", callback: syncHeight},
        "flow": {url: "about!process.action", tabIndex: 3, title: "决策流程", callback: syncHeight},
        "system": {url: "about!windControl.action", tabIndex: 4, title: "风控体系", callback: syncHeight},
        "honour": {url: "about!rzrkHonor.action", tabIndex: 5, title: "公司荣誉", callback: initHonour},
        "contact": {url: "about!contact.action", tabIndex: 6, title: "联系我们", callback: initContact}
    };
    return url[index];
};
var resetAboutDetail = function(){
    var detail = $(".about-detail");
    detail.empty();
};
var setListHeight = function(){
    var height = $(".about-detail").height();
    $(".about-menu").height(height);
};
var selectListItem = function(index){
    var ul = $(".about-menu > ul");
    var li = ul.find(" > li[data-index=" + index + "]");
    if (li.size() == 0)
        ul.find("li:eq(0)").addClass("selected");
    else
        li.addClass("selected");
};
var initEvents = function(){
    var lis = $(".about-menu > ul > li");
    lis.click(function(){
        if ($(this).hasClass("selected"))
            return false;
        var pageIndex = $(this).data("index");
        lis.removeClass("selected");
        $(this).addClass("selected");
        window.location.assign("about!rzrkIndex.action?page=" + pageIndex);
    });
};
var syncHeight = function(data){
    setListHeight();
};
var initHonour = function(data){
    $(".fancybox-effects-a").fancybox({
        helpers: {
            title : {
                type : 'outside'
            },
            overlay : {
                speedOut : 0
            }
        },
        onComplete: setListHeight
    });
};
function initialize() {
	var mapOptions = {
		zoom: 13,
		center: new google.maps.LatLng(39.9694211, 116.46311709999998),
		mapTypeId: google.maps.MapTypeId.ROADMAP
	};
	var map = new google.maps.Map(document.getElementById("map_canvas"), mapOptions);
	var marker = new google.maps.Marker( {  
        position : new google.maps.LatLng(39.9694211, 116.46311709999998),  
        map : map,  
        title : "投资（北京）有限公司"
    });
	var contentString = '<div id="info">投资（北京）有限公司<br/>电话：86 (10) 84784865<br/>地址：北京市朝阳区京顺路109号亚之杰中心2F</div>';
	var infowindow = new google.maps.InfoWindow({
	      content: contentString
	  });
	google.maps.event.addListener(marker, 'click', function() {
	    infowindow.open(map,marker);
	});
}
var initContact = function(){
	function loadScript() {
		var script = document.createElement("script");
		script.type = "text/javascript";
		script.src = "http://maps.googleapis.com/maps/api/js?key=AIzaSyC_hNIWFgy5b7AB4npZGgsERurATH8HJdY&sensor=false&language=zh_CN&callback=initialize";
		document.body.appendChild(script);
	}
	loadScript();
    setListHeight();
};
var loadPageFragment = function(index){
    var config = urlConfig(index);
    config = config == undefined ? urlConfig(startIndex) : config;
    $(".about-menu > h3 span").text("> " + config.title);
    $(".about-detail").load(config.url, function(data){
        config.callback(data);
    });
};
$(function(){
    var params = getParams(window.location.href);
    selectListItem(params.page);
    initEvents();
    $(".header > ul > li > a").eq(2).addClass("active");
    loadPageFragment(params.page);
});