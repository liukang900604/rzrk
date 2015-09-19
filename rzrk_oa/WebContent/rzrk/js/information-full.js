var startIndex = "affiche";
var urlConfig = function(index){
    var prefix = "information-full/";
    url = {
        "affiche": {url:"/rzrk/article!affiche.action", tabIndex: 0, title: "公告", callback: initAffiche},
        "news": {url:"/rzrk/article!news.action", tabIndex: 1, title: "新闻", callback: initNews}
    };
    return url[index];
};
var resetInformationDetail = function(){
    var list = $(".information-full .list");
    list.empty();
};
var setListHeight = function(){
    var height = $(".information-full").height();
    $(".info-menu").height(height);
};
var selectListItem = function(index){
    var ul = $(".info-menu > ul");
    var li = ul.find(" > li[data-index=" + index + "]");
    if (li.size() == 0)
        ul.find("li:eq(0)").addClass("selected");
    else
        li.addClass("selected");
};
var initEvents = function(){
    var lis = $(".info-menu > ul > li");
    lis.click(function(){
        if ($(this).hasClass("selected"))
            return false;
        var pageIndex = $(this).data("index");
        lis.removeClass("selected");
        $(this).addClass("selected");
        window.location.assign("/rzrk/article!articleIndex.action?page=" + pageIndex);
    });
};
var initAffiche = function(data){
    setListHeight();
};
var initNews = function(data){
    setListHeight();
};
var loadPageFragment = function(index,params){
    var config = urlConfig(index);
    config = config == undefined ? urlConfig(startIndex) : config;
    $(".info-menu > h3 span").text("> " + config.title);
    $(".information-full .list").load(config.url + "?" + params, function(data){
        config.callback(data);
    });
};
$(function(){
    var params = getParams(window.location.href);
    selectListItem(params.page);
    initEvents();
    $(".header > ul > li > a").eq(4).addClass("active");
    loadPageFragment(params.page,getParamsStr(window.location.href));
});