$(function(){
	$(".required_sign").hide();
	$(".contractCategory").each(function(){
		var val = $(this).val();
		if($(this).is("textarea,.select,.selectTree,.checkbox,.radio")){
			val=$(this).attr("value");
		}
		$(this).after("<span>"+val.replace(/\n/g,"<br>")+"</span>").hide();
	});
});