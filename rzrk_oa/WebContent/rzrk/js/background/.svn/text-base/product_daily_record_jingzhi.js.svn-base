var saveBtn = '';
var msg = window.top.$.messager;
Date.prototype.format = function (format) {
    var o = {
        "M+": this.getMonth() + 1, //month
        "d+": this.getDate(), //day
        "h+": this.getHours(), //hour
        "m+": this.getMinutes(), //minute
        "s+": this.getSeconds(), //second
        "q+": Math.floor((this.getMonth() + 3) / 3), //quarter
        "S": this.getMilliseconds() //millisecond
    };
    if (/(y+)/.test(format)) {
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
        }
    }
    return format;
};

function dateFormatter(date){
	var y = date.getFullYear();
	var m = date.getMonth()+1;
	var d = date.getDate();
	return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
}
function dateParser(s){
    if (!s) return new Date();
    var ss = (s.split('-'));
    var y = parseInt(ss[0],10);
    var m = parseInt(ss[1],10);
    var d = parseInt(ss[2],10);
    if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
        return new Date(y,m-1,d);
    } else {
        return new Date();
    }
}
function initEvents(){
	saveBtn = $("#saveBtn");
	closeBtn = $("#closeBtn");
	saveBtn.unbind('click').click(function(){
		  var beginDate =$("#jzForm").find("[name='beginDate']").val();
	      var endDate = $("#jzForm").find("[name='endDate']").val();
	      var start = new Date(beginDate.replace("-", "/").replace("-", "/"));
	      var end = new Date(endDate.replace("-", "/").replace("-", "/"));
	      if (end < start) {
	    	  msg.alert("日期", "结束日期不能小于开始日期！", "error");
	      }else{
		    	$("#jzForm").find("[name='beginDate']").val();
		    	$("#jzForm").find("[name='endDate']").val();
		    	$("#jzForm").attr("action","/admin/product_daily_record!downloadExcel.action").submit();
	      }
		    
	});
	
	
	closeBtn.unbind('click').click(function(){
		window.top.closeCurrentWindow();
	});
};
//初始化日期
/*function initData(){
	$('#beginDate').datebox('setValue', new Date().format('yyyy-MM-dd'));
	$('#endDate').datebox('setValue', new Date().format('yyyy-MM-dd'));
}*/
$(function(){
    initEvents();
});