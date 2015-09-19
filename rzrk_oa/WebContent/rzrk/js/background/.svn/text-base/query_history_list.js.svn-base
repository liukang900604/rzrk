var iconBasePath = '/rzrk/js/jquery-easyui-1.4.1/themes/icons/';
var saveBtn = '';
var letterIdx = {};
var unique = {};
var msg = window.top.$.messager;


function loadTree(source){
//	$(".queryHistoryTree").jstree({
//		'core' : {
//			'data' : window.queryHistoryTree
//		}
//	}).bind('select_node.jstree', function(event,data) {
//		var nodeData = data.node.data;
//		if(nodeData["history"]){
//			$("#cciframe").attr("src","/admin/contract!list.action?contractCategoryId="+data.node.parent+"&preQuery="+encodeURI(nodeData["content"]));
//		}
//	});
	function operations(value, rowData) {
		if(rowData.data["history"]){
		    var link = "<a href='#' data-id='{0}' class='delete'>删除</a>";
		    return $.format(link, rowData.id);
		}else{
			return "";
		}
	}
	function operationsLeaf(value, rowData) {
//	    link += "<em>|</em>";
	//   link += "<a href='#' data-id='{0}' data-idx='{1}' class='downloadTemp'>下载模板</a>";
	    if($.isEmptyObject(rowData["children"])){
	        var link = "<a href='#' data-id='{0}'class='show'>{1}</a>";
	        return $.format(link, rowData.id,value);
	    }else{
	    	return value;
	    }
	}

	function getColumns() {
	    var columns = [[
//	            {title: "", field: "isChecked", width: 80, align: 'center', checkbox:true},
	            {title: "表单", field: "text", width: "60%", align: 'left', formatter: operationsLeaf},
	            {title: "操作", field: "operation", width: "40%", align: 'center', formatter: operations}
	        ]];
	    return columns;
	}

	function getConfig(){
	    return {
	        loadMsg: '正在获取数据......',
	        url: '/admin/query_history!getAjaxList.action',
	        method: 'get',
	        width:'auto',
//	        fitColumns:true,
	        singleSelect: true,
	        idField: 'id',
	        treeField: 'text',
	        columns: getColumns(),
	        toolbar: [{
	            text:'刷新',
	    		iconCls: 'icon-reload',
	    		handler: function(){$("#queryHistoryTree").treegrid("reload")}
	    	}],
	    	onBeforeExpand: function(row){
	    		var id = row.id;
	    		var node = $("tr[id$='" + id + "']");
	    		node.next().show();
	    	},
	    	onBeforeCollapse: function(row){
	    		var id = row.id;
	    		var node = $("tr[id$='" + id + "']");
	    		node.next().hide();
	    	}
	    };
	}
	function getQueryHistoryFromTree(queryid){
		node=null;
		var itemArr = $("#queryHistoryTree").treegrid("getData");
		function loop(itemArr){
			for(index in itemArr){
				var item = itemArr[index];
				if(item.id == queryid){
					node = item;
					return;
				}else{
					if(item.children!=null){
						loop(item.children);
					}
				}
			}
		}
		loop(itemArr);
		return node;
	}

	$("#queryHistoryTree").treegrid(getConfig());
	
	$("#queryHistoryTreeDiv").on("click", ".delete", function () {
    	var id = $(this).data("id");
    	msg.confirm("删除", '是否删除选中的查询历史？', function(r){
    		if (!r) return false;
    		var ids = [];
    		ids.push('ids=' + id);
    		
    		$.ajax({
  			  dataType: "json",
  			  url: "/admin/query_history!delete.action",
  			  type : "post",
  			  data: ids.join('&'),
  			  success: function(data){
	    			if (data.status == 'success'){
	    				msg.alert("删除", "删除成功！", "info");
	    				$("#queryHistoryTree").treegrid("reload");
	    			}else{
	    				msg.alert("删除", "删除失败：" + data.message, "error");
	    			}
	  			},
  	    		error: function (data, status, e){
    				msg.alert("删除", "删除失败："  +e, "error");
  	    		}
  			});
    	});
    	return false;
    }).on("click", ".show", function () {
    	var node = getQueryHistoryFromTree($(this).data("id"));
    	$("#cciframe").attr("src","/admin/contract!list.action?contractCategoryId="+node.data["contractCategoryId"]+"&preQuery="+encodeURI(node.data["content"]));
    	return false;
    });
	
};

function loadDatagrid(){
	
	function operationsName(value, rowData, index) {
	    var link = "<a href='#' data-id='{0}' data-idx='{1}' class='show'>"+value+"</a>";
	    return $.format(link, rowData.id, index,rowData.text);
	}

	function operations(value, rowData, index) {
	    var link = "<a href='#' data-id='{0}' data-idx='{1}' class='delete'>删除</a>";
	    return $.format(link, rowData.id, index);
	}


	function getColumns() {
	    var columns = [[
//	            {title: "", field: "isChecked", width: 80, align: 'center', checkbox:true},
	            {title: "新表", field: "text", width: "60%", align: 'center', formatter: operationsName},
	            {title: "操作", field: "operation", width: "40%", align: 'center', formatter: operations}
	        ]];
	    return columns;
	}

	function getConfig(){
	    return {
	        loadMsg: '正在获取数据......',
	        url: '/admin/query_history!getAjaxList4Viewlayer.action',
	        method: 'get',
	        width:'auto',
	        fitColumns:true,
	        singleSelect: true,
	        columns: getColumns(),
	        toolbar: [{
	            text:'刷新',
	    		iconCls: 'icon-reload',
	    		handler: function(){$("#queryHistoryTree4Viewlayer").datagrid("reload")}
	    	}]
	    };
	}
	$("#queryHistoryTree4Viewlayer").datagrid(getConfig());

	$("#queryHistoryTree4ViewlayerDiv").on("click", ".delete", function () {
    	var id = $(this).data("id");
    	msg.confirm("删除", '是否删除选中的查询历史？', function(r){
    		if (!r) return false;
    		var ids = [];
    		ids.push('ids=' + id);
    		
    		$.ajax({
  			  dataType: "json",
  			  url: "/admin/query_history!delete.action",
  			  type : "post",
  			  data: ids.join('&'),
  			  success: function(data){
	    			if (data.status == 'success'){
	    				msg.alert("删除", "删除成功！", "info");
	    				$("#queryHistoryTree4Viewlayer").datagrid("reload");
	    			}else{
	    				msg.alert("删除", "删除失败：" + data.message, "error");
	    			}
	  			},
  	    		error: function (data, status, e){
    				msg.alert("删除", "删除失败："  +e, "error");
  	    		}
  			});
    	});
    	return false;
    }).on("click", ".show", function () {
    	var idx = $(this).data("idx");
    	var viewlayerId = $("#queryHistoryTree4Viewlayer").datagrid("getData").rows[idx].viewlayerId
    	var content = $("#queryHistoryTree4Viewlayer").datagrid("getData").rows[idx].content;
    	$("#cciframe").attr("src","/admin/viewlayer_show!list.action?id="+viewlayerId+"&preQuery="+encodeURI(content));
    	return false;
    });
	
	
}


function initEvents(){
	
};
function initData() {
}


$(function(){
	initData();
    initEvents();
    loadTree();
    loadDatagrid();
    $(window).resize(function(){

    });

});

