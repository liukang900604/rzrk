$(function(){
	$(".scroll").mCustomScrollbar({
		theme:"minimal-dark"
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
	subMenu.find("li").click(function(){
		var url = "product!detail.action?id=" + $(this).data("id");
		window.location.href = url;
	});
});