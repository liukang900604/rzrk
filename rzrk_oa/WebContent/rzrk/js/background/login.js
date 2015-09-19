var rzrk = {base: "/rzrk"};

if (self != top) {
	top.location = self.location;
};

//设置Cookie
function setCookie(name, value) {
	var expires = (arguments.length > 2) ? arguments[2] : null;
	var path = rzrk.base == "" ? "/" : rzrk.base;
	document.cookie = name + "=" + encodeURIComponent(value) + ((expires == null) ? "" : ("; expires=" + expires.toGMTString())) + ";path=" + path;
}

//获取Cookie
function getCookie(name) {
	var value = document.cookie.match(new RegExp("(^| )" + name + "=([^;]*)(;|$)"));
	if (value != null) {
		return decodeURIComponent(value[2]);
 } else {
		return null;
	}
}

//删除cookie
function removeCookie(name) {
	var expires = new Date();
	expires.setTime(expires.getTime() - 1000 * 60);
	setCookie(name, "", expires);
}

$(function() {
	var msg = $.messager;
	if (message){
		msg.alert("提示信息", message, "error");
	}
	var $loginForm = $("#loginForm");
	var $username = $("#username");
	var $password = $("#password");
	var $isRememberUsername = $("#isRememberUsername");
	var $submitForm = $("#submitForm");

	// 判断"记住用户名"功能是否默认选中,并自动填充登录用户名
	if (getCookie("adminUsername") != null) {
		$isRememberUsername.attr("checked", true);
		$username.val(getCookie("adminUsername"));
		$password.focus();
	} else {
		$isRememberUsername.attr("checked", false);
		$username.focus();
	}
	
	$submitForm.click(function(){
		if ($username.val() == "") {
			msg.alert("提示信息", "请输入您的用户名!", "info");
			return false;
		}
		if ($password.val() == "") {
			msg.alert("提示信息", "请输入您的密码!", "info");
			return false;
		}
		if ($isRememberUsername.attr("checked") == true) {
			var expires = new Date();
			expires.setTime(expires.getTime() + 1000 * 60 * 60 * 24 * 7);
			setCookie("adminUsername", $username.val(), expires);
		} else {
			removeCookie("adminUsername");
		}
		$loginForm.submit();
	});
	$username.bind("keyup", "return", function(){
		$submitForm.click();
	});
	$password.bind("keyup", "return", function(){
		$submitForm.click();
	});
});