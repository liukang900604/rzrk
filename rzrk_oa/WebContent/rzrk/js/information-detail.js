var setListHeight = function(){
    var height = $(".information-full").height();
    $(".info-menu").height(height);
};
$(function(){
    $(".header > ul > li > a").eq(4).addClass("active");
    setListHeight();
});