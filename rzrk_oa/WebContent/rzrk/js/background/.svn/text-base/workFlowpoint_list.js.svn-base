var iconBasePath = '/rzrk/js/jquery-easyui-1.4.1/themes/icons/';
var msg = window.top.$.messager;
var pager = {
	beforePageText : '当前第',
	afterPageText : '页，共 {pages} 页',
	displayMsg : '当前第 {from} 至 {to} 条数据，共 {total} 条数据'
};

function operations(value, rowData, index) {
	var link = "<a href='#' data-id='{0}' data-idx='{1}' class='edit'>编辑</a><em>|</em><a href='#' data-id='{0}' data-idx='{1}' class='delete'>删除</a>";
	return $.format(link, rowData.id, index);
}

function getColumns() {
	var columns = [ [ {
		title : "节点序号",
		field : "pointSort",
		width : 100,
		align : 'center',
		sortable : true
	}, {
		title : "节点名称",
		field : "pointName",
		width : 100,
		align : 'center',
		sortable : true
	}, {
		title : "节点位置",
		field : "pointLocation",
		width : 100,
		align : 'center',
		sortable : true
	}, {
		title : "下一节点",
		field : "nextPonit",
		width : 100,
		align : 'center',
		sortable : true
	}, {
		title : "操作",
		field : "operation",
		width : 400,
		align : 'center',
		formatter : operations
	} ] ];
	return columns;
}

function getDataGridHeight() {
	var searchBarHeight = $('.searchBar').outerHeight();
	var bottomHeight = $('.bottom').outerHeight();
	var winHeight = $(window).outerHeight();
	return winHeight - searchBarHeight - bottomHeight - 25;
}

function getConfig() {
	var workId = $("#workId").val();
	return {
		loadMsg : '正在获取数据......',
		url : '/admin/work_flow!getAjaxWorkFlowPointList.action?workId='
				+ workId,
		method : 'get',
		width : '100%',
		rownumbers : true,
		height : getDataGridHeight(),
		singleSelect : false,
		selectOnCheck : true,
		checkOnSelect : true,
		autoRowHeight : false,
		remoteSort : true,
		showFooter : false,
		pagination : true,
		pageSize : 50,
		pageList : [ 50, 100, 500 ],
		pageNumber : 1,
		columns : getColumns(),
		data : []
	};
}

function doSearch(value) {
	var table = $('#table');
	table.datagrid({
		queryParams : {
			searchBy : $('#searchBy').combobox("getValue"),
			keyword : value
		}
	});
}

function initEvents() {

	var table = $('#table');
	var workId = $("#workId").val();
	$("#addWorkFlowPoint").unbind('click').click(function() {
		var url = "/admin/work_flow!addWorkFlowPoint.action?workId=" + workId;
		var ezWin = window.top.createWin({
			title : "新增节点"
		});
		var iframe = window.top.createIframe(url);
		iframe.appendTo(ezWin);
		ezWin.window("open");
		return false;
	});
	$("#createWorkflow").unbind('click').click(function() {
		var workId = $("#workId").val();
		var url = "/admin/work_flow!pointdefinde.action?workId=" + workId;
		var ezWin = window.top.createWin({
			title : "新建流程"
		});
		var iframe = window.top.createIframe(url);
		iframe.attr("id", "createWorkflowWindow")
		iframe.appendTo(ezWin);
		ezWin.window("open");
		return false;
	});
	$(".datagrid").on("click", ".edit", function() {
		var workId = $("#workId").val();
		var url = "/admin/work_flow!editWorkFlowPoint.action?id="
				+ $(this).data("id") + "&workId=" + workId;
		var ezWin = window.top.createWin({
			title : "编辑"
		});
		var iframe = window.top.createIframe(url);
		iframe.appendTo(ezWin);
		ezWin.window("open");
		return false;
	}).on("click", ".delete", function() {
		$.ajax({
			type : 'post',
			url : '/admin/work_flow!deleteWorkFlowPoint.action',
			data : {
				"id" : $(this).data("id")
			},
			cache : false,
			dataType : 'json',
			success : function(data) {
				if (data.status == 'success') {
					msg.alert('删除信息', data.message, 'info', function() {
						window.location.reload();
					});

				} else {
					msg.alert('删除信息', data.message, 'info', function() {
						return false;
					});

				}
			}

		});
	})

}

$(function() {
	var table = $('#table');
	table.datagrid(getConfig());
	table.datagrid('getPager').pagination(pager);
	$(window).resize(function() {
		table.datagrid('resize', {
			height : getDataGridHeight()
		});
	});
	initEvents();
});
