function initEvents(){
		closeBtn = $("#closeBtn");
		closeBtn.unbind('click').click(function(){
			window.top.reloadDataGridInTab('消息列表');
			window.top.closeCurrentWindow();
		});
	};
	function getunReadCount()
	{	
		$.ajax({
			   type: "POST",
			   url: "/admin/systemmessage!getCount.action",
			   dataType:"json",
			   success: function(data){
				var addSystemmessage =  $(window.parent.document).find("#UnreadSystemmessage");
				addSystemmessage.text("消息("+data+")");
			   }
			});
	}
$(function(){
    initEvents();
    getunReadCount();
});