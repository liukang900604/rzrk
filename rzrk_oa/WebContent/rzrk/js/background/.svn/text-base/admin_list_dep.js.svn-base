var iconBasePath = '/rzrk/js/jquery-easyui-1.4.1/themes/icons/';
var saveBtn = '';
var letterIdx = {};
var unique = {};
var msg = window.top.$.messager;


function loadTree(source){
	function operationsLeaf(value, rowData) {
        var link = "<a href='#' data-id='{0}'class='show'>{1}</a>";
        return $.format(link, rowData.id,value);
	}

	function getColumns() {
	    var columns = [[
//	            {title: "", field: "isChecked", width: 80, align: 'center', checkbox:true},
	            {title: "部门", field: "text", width: "90%", align: 'left', formatter: operationsLeaf},
	        ]];
	    return columns;
	}

	function getConfig(){
	    return {
	        loadMsg: '正在获取数据......',
	        url: '/admin/deparment!getAjaxTree4easyUi.action',
	        method: 'get',
//	        width:'auto',
	        fitColumns:true,
	        singleSelect: true,
	        idField: 'id',
	        treeField: 'text',
	        columns: getColumns(),
	        toolbar: [{
	            text:'刷新',
	    		iconCls: 'icon-reload',
	    		handler: function(){$("#depTree").treegrid("reload")}
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
	function getNodeFromTree(queryid){
		node=null;
		var itemArr = $("#depTree").treegrid("getData");
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

	$("#depTree").treegrid(getConfig());
	
	$("#depTreeDiv").on("click", ".show", function () {
    	var node = getNodeFromTree($(this).data("id"));
    	$("#cciframe").attr("src","/admin/admin!list.action?deparmentId="+node["id"]);
    	return false;
    });
	
};

function _initEvents(){
}

$(function(){
	
	loadTree();
    _initEvents();
    $(window).resize(function(){
    	
    });
    
    var url = '/admin/admin!getAjaxList.action';
	$.ajax({
		url:url,
		type:"get",
		dataType:"json",
		success:function(data){
			window.referenceData = data;
		}
	});
});