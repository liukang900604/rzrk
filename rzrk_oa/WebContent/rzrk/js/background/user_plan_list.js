var iconBasePath = '/rzrk/js/jquery-easyui-1.4.1/themes/icons/';
var msg = window.top.$.messager;
var key = window.location.href;
var storage = window.localStorage;

var pager = {
    beforePageText: '当前第',
    afterPageText: '页，共 {pages} 页',
    displayMsg: '当前第 {from} 至 {to} 条数据，共 {total} 条数据',
    onChangePageSize: function(pageSize){
    	if (storage){
    		storage.setItem(key, pageSize);
    	}
    }
};
function operations(value, rowData, index) {
    var link = "<a href='#' data-id='{0}' data-idx='{1}' class='edit'>查看</a>";
    link += "<em>|</em>";
    if(rowData.isFollow != null && rowData.isFollow != ""){
    	if(rowData.isFollow == 1){
    		  link += "<a href='#' data-id='{0}' data-idx='{1}' class='follow'>取消关注</a>";
    	}else{
    		  link += "<a href='#' data-id='{0}' data-idx='{1}' class='follow'>关注</a>";
    	}
    }else{
    	  link += "<a href='#' data-id='{0}' data-idx='{1}' class='follow'>关注</a>";
    }
  //  link += "<em>|</em>";
   // link += "<a href='#' data-id='{0}' data-idx='{1}' class='copyLink'>复制链接</a>";
    
    return $.format(link, rowData.id, index);
}
function renderContent(value, rowData, index){
	var a = "<a href='#' class='showContent' content='{0}'>查看</a>";        //modifiedBy huanghui ; 2015/7/28
	return $.validator.format(a, value);
}
function renderProgress(value, rowData, index){
	var span = "<span style='position: absolute;left:0;top:4px;display:block;border:1px solid {0};height:18px;width:100%;'><span style='background-color:{0};display:block;width:{1}%;height:18px;'></span></span><span style='display:block;z-index:1;position:relative;color:#333;'>{1}%</span>";
	var color = "#91c4ff;";
	if (parseFloat(value) >= 100){
		value = 100;
	}
	return $.validator.format(span, color, value);
}
function cellStyler(value, rowData, index){
    return 'position: relative;';
}
function contentStyler(value, rowData, index){
    return 'text-overflow: ellipsis;';
}
function getColumns() {
    var columns = [[
            {title: "", field: "isChecked", width: 180, align: 'center', checkbox:true},
            {title: "编号", field: "userPlanUUID", width: 110, align: 'center', sortable: true},
            {title: "计划名称", field: "name", width: 500, align: 'left', sortable: true},
//            {title: "计划者", field: "planuser", width: 80, align: 'center', sortable: true},
//            {title: "内容", field: "content", width: 80, align: 'center', sortable: true, styler: contentStyler, formatter: renderContent},
            {title: "开始时间", field: "beginTime", width: 80, align: 'center', sortable: true},
            {title: "预计结束时间", field: "preEndTime", width: 120, align: 'center', sortable: true},
            {title: "实际结束时间", field: "endTime", width: 120, align: 'center', sortable: true},
            {title: "创建人", field: "creator", width: 80, align: 'center', sortable: true},
            {title: "处理人", field: "planuser", width: 80, align: 'center', sortable: true},
            {title: "测试人", field: "testPerson", width: 80, align: 'center', sortable: true},
            {title: "进度", field: "progress", width: 80, align: 'center', sortable: true, styler: cellStyler, formatter: renderProgress},
            {title: "备注", field: "remark", width: 200, align: 'center', sortable: true},
            {title: "状态", field: "status", width: 80, align: 'center', sortable: true},
            {title: "项目", field: "project", width: 80, align: 'center', sortable: true},
            {title: "报告时间", field: "createDate", width: 120, align: 'center', sortable: true},
            {title: "最近更新", field: "modifyDate", width: 120, align: 'center', sortable: true},
            {title: "操作", field: "operation", width: 200, align: 'center', formatter: operations}
        ]];
    return columns;
}
function getDataGridHeight(){
	var searchBarHeight = $('.searchBar').outerHeight();
	var winHeight = $(window).outerHeight();
	return winHeight - searchBarHeight - 25;
}
function getPageSize(){
	var pageSize = 50;
	var size = 0;
	if (storage){
		size = storage.getItem(key);
	}
	return size || pageSize;
}
function getConfig(){
	var showAll = window.showAll;
    return {
        loadMsg: '正在获取数据......',
        url: '/admin/user_plan!getAjaxList.action?showAll='+showAll,
        method: 'get',
        width: '100%',
        rownumbers: true,
        height: getDataGridHeight(),
        singleSelect: false,
        selectOnCheck: true,
        checkOnSelect: true,
        autoRowHeight: false,
        remoteSort: true,
        showFooter: false,
        pagination: true,
        pageSize: getPageSize(),
        pageList: [50, 100, 500],
        pageNumber: 1,
        columns: getColumns(),
        data: [],
        rowStyler: function(index,rowData){
        	var statusDict = {
    			"新建":"background-color:#ffbfbf;",
    			"反馈":"background-color:#eabfff;",
    			"开发中":"background-color:#bfffea;",
    			"认可":"background-color:#ffeabf;",
    			"已确认":"background-color:#bfeaff;",
    			"已解决":"background-color:#ffbfea;",
    			"已完成":"background-color:#bfffbf;",
    			"已关闭":"background-color:#f2f4f8;",
    			"测试中":"background-color:#eaffbf;"
        	};
        	return statusDict[rowData.status];
        },
        onDblClickRow: function(index, rowData){
        	var url = "/admin/user_plan!realEdit.action?id=";
        	var id = rowData.id;
            var ezWin = window.top.createWin({
            	title:"编辑计划信息",
            	width: "1080",
            	resizable: true,
            	maximizable: true
    		});
            var iframe = window.top.createIframe(url + id);
            iframe.attr("id","用户计划");
            iframe.appendTo(ezWin);
            ezWin.window("open");
        }
    };
}
function doSearch(value){
    var table = $('#table');
    
    var createArray = $("#creatorQueryPerson").find("input[name=adminQueryId]");
    var adminArray = $("#adminQueryPerson").find("input[name=adminQueryId]");
    var adminQueryId = "";
    var createQueryId = "";
    $.each(createArray, function(i,create){
    	createQueryId += (i != 0 ? "," : "") + $(create).val();
	});
    $.each(adminArray, function(i,admin){
    	adminQueryId += (i != 0 ? "," : "") + $(admin).val();
	});
    table.datagrid({
    	queryParams: {
    		nameQuery :$("#nameQuery").val(),
    		creatorQuery :createQueryId,
    		adminQuery :adminQueryId,
    		statusQuery :$("#statusQuery").val()
    	}
    });
}
function _initEvents(){
    var table = $('#table');
    var search = $('#search');
    $(".datagrid").on("click", ".edit", function () {
    	var url = "/admin/user_plan!edit.action?id=";
    	var id = $(this).data("id");
        var ezWin = window.top.createWin({
        	title:"编辑计划信息",
        	width: "1080",
        	resizable: true,
        	maximizable: true
		});
        var iframe = window.top.createIframe(url + id);
        iframe.attr("id","用户计划");
        iframe.appendTo(ezWin);
        ezWin.window("open");
    	return false;
    }).on("click", ".follow", function () {
      var obj = $(this);
   	   $.ajax({    
         type:'post',        
         url: "/admin/user_plan!userPlanFollow.action?id="+$(this).data("id"),    
         data:{},    
         cache:false,    
         dataType:'json',    
         success:function(data){    
          if(data.status == 'success'){
       	   msg.alert('保存信息',data.message,'info',function(){
       		if(obj.text() == "关注"){
       			obj.text("取消关注");
       		}else{
       			obj.text("关注");
       		}
       	   });     	   
          }else{
       	   msg.alert('保存信息',data.message,'error',function(){
            	   return false;
       	   });
          }
         } 
            
       });
    }).on("click", ".copyLink", function () {
    	var obj = $(this);
   	 $.ajax({    
   	          type:'post',        
   	          url: "/admin/user_plan!copyLink.action?id="+$(this).data("id"),    
   	          data:{},    
   	          cache:false,    
   	          dataType:'json',    
   	          success:function(data){    
   	           if(data.status == 'success'){
   	        	   msg.alert('保存信息',data.message,'info',function(){
   	        		   return false;
   	        	   });     	   
   	           }else{
   	        	   msg.alert('保存信息',data.message,'error',function(){
   	             	   return false;
   	        	   });
   	           }
   	          } 
   	             
   	      });
   	
   }).on("click", ".showContent", function () {
    	var content = $(this).attr('content');		//modifiedBy huanghui ; 2015/7/28
        var ezWin = window.top.createWin({
        	title:"计划内容详细",
        	resizable: true,
        	maximizable: true
		});
        ezWin.html("<div style='padding:10px;'>" + content + "</div>");
        ezWin.window("open");
    	return false;
    });
    $('#addPlan').unbind('click').click(function(){
    	var url = '/admin/user_plan!add.action';
        var ezWin = window.top.createWin({
        	title: "添加计划",
        	width: "1080",
        	resizable: true,
        	maximizable: true
    	});
        var iframe = window.top.createIframe(url);
        iframe.attr("id","用户计划");
        iframe.appendTo(ezWin);
        ezWin.window("open");
    });
    $('#delPlans').unbind('click').click(function(){
		var msg = window.top.$.messager;
    	var url = "/admin/user_plan!delete.action";
    	var ids = [];
    	var rows = table.datagrid("getSelections");
    	if (rows.length == 0){
    		msg.alert("批量删除", "请选择要删除的计划", "info");
    		return false;
    	}
    	msg.confirm("批量删除", '是否删除选中的计划？', function(r){
    		if (!r) return false;
    		var len = rows.length;
    		for (var i = 0; i < len; i++){
    			ids.push('ids=' + rows[i].id);
    		}
    		$.post(url, ids.join('&'), function(data){
    			if (data.status == 'success'){
    				msg.alert("批量删除", "批量删除成功！", "info");
    				window.top.reloadDataGridInTab('我的计划');
    			}else{
    				msg.alert("批量删除", "批量删除失败：" + data.message, "error");
    			}
    		},"json");
    	});
    });
/*    $('#searchBy').combobox({
		textField: 'key',
    	valueField: 'value',
		data:[{
			key: '计划名称', value: 'name'
		},{
			key: '创建人', value: 'creator'
		},{
			key: '处理人', value: 'admin'
		},{
			key: '状态', value: 'status'
		}],
		editable:false,
    	onSelect: function(param){
    		var keyword = $("#keyword");
    		if (param.value != 'status'){
    			keyword.css('width', '300px').searchbox({
    				prompt: '请输入搜索关键字',
    				searcher: doSearch
    			});
    			search.hide();
    		}else{
        		keyword.css('width', '80px').combobox({
        			textField: 'key',
        	    	valueField: 'value',
        			data: window.statusData,
        			editable:false
    			}).combobox("select", "全部");
        		search.show();
    		}
    	}
    }).combobox("select", "name");
*/    
    search.unbind('click').click(function(){
    	doSearch();
    });
    $(".queryText").keypress(function (event) {
        var key = event.which;
        if (key == 13) {
        	doSearch();
        }
    });

}
$(function(){
    var table = $('#table');
    table.datagrid(getConfig());
    table.datagrid('getPager').pagination(pager);
    $(window).resize(function(){
        table.datagrid('resize', {
            height: getDataGridHeight()
        });
    });
    _initEvents();
    $("#creatorQuery").selectMembers({
    	hiddenName: 'adminQueryId'
    });
    $("#adminQuery").selectMembers({
    	hiddenName: 'adminQueryId'
    });
});