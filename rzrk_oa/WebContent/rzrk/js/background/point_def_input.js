$(function() {
	var tabs = [ {
		id : "workFlowEdit",
		title : "编辑",
		url : "/admin/work_flow!edit.action?id=" + window.id,
		selected : true
	}];
	function createTab(options) {
		var iframe = '<iframe scrolling="yes" frameborder="0"  src="{0}" style="width:100%;height:99%;"></iframe>';
		var tabs = $(".easyui-tabs");
		tabs.tabs("add", {
			"id" : options.id,
			"title" : options.title,
			"selected" : options.selected,
			"closable" : false,
			"content" : jQuery.validator.format(iframe, options.url),
			"fit" : true
		});
	}
	function initTabs() {
		$.each(tabs, function(i, tab) {
			createTab(tab);
		});
	}
	initTabs();
});