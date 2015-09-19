$(function(){
	var tabs = [{
		id: "workFlowType",
		title: "工作流类型",
		url: "/admin/work_flow!getWorkFlowTypeList.action",
		selected: true
	},{
		id: "workFlowForm",
		title: "工作流表单",
		url: "/admin/work_flow!getWorkFlowFormList.action",
		selected: false
	},{
		id: "workFlow",
		title: "工作流流程",
		url: "/admin/work_flow!list.action",
		selected: false
	}];
    function createTab(options){
        var iframe = '<iframe scrolling="yes" frameborder="0"  src="{0}" style="width:100%;height:99%;"></iframe>';
        var tabs = $(".easyui-tabs");
        tabs.tabs("add", {
            "id": options.id,
            "title": options.title,
            "selected": options.selected,
            "closable": false,
            "content": jQuery.validator.format(iframe, options.url),
            "fit": true
        });
    }
    function initTabs(){
    	var workflowForm=$("#workflowForm").val();//value 等于1  表示有权限看到则无
    	var workflowFlow=$("#workflowFlow").val();
    	var workflowType=$("#workflowType").val();
        $.each(tabs, function(i, tab){
        	if(workflowForm==1 && tab.id=="workFlowForm"){
        		createTab(tab);
        	}else
        	if(workflowFlow==1 && tab.id=="workFlow"){
        		createTab(tab);
        	}else
        	if(workflowType==1 && tab.id=="workFlowType"){
        		createTab(tab);
        	}
            
        });
    }
    initTabs();
});