var iconBasePath = '/rzrk/js/jquery-easyui-1.4.1/themes/icons/';
var saveBtn = '';
var letterIdx = {};
var unique = {};
var indexArray = new Array();
var msg = window.top.$.messager;
var pager = {
    beforePageText: '当前第',
    afterPageText: '页，共 {pages} 页',
    displayMsg: '当前第 {from} 至 {to} 条数据，共 {total} 条数据'
};
function operations(value, rowData, index) {
    var link = "<a href='#' data-row_id='{0}' data-idx='{1}' class='edit'>编辑</a>";
//    link += "<em>|</em>";
//   link += "<a href='#' data-id='{0}' data-idx='{1}' class='downloadTemp'>下载模板</a>";
    isCheck(value, rowData, index);
    if(rowData.rowId){
        return $.format(link, rowData.rowId, index);
    }else{
    	return "";
    }
}

function isCheck(value, rowData, index) {
	var requestId = window.requestIds;
	if(!(requestId == null || requestId == "")){	
		if(requestId.indexOf(rowData.rowId) != -1){
			indexArray.push(index);			
		}
	}
	
}

function getColumns() {
    var columns = [[
                    {title: "", field: "isChecked", width: 80, align: 'center', checkbox:true}
        ]];
    
    for(var i=0;i<window.contractCategoryTitles.length;i++){
    	columns[0].push({
    		title: window.contractCategoryTitles[i], field: window.contractCategoryTitles[i], width: 100, align: 'left',sortable : true
    	});
    	
    }
    columns[0].push(
            {title: "操作", field: "operation", width: 120, align: 'center', formatter: operations}
        );
    columns[0].push(
            {title: "创建人", field: "createAdminName", width: 120, align: 'left'}
        );
    columns[0].push(
            {title: "创建时间", field: "createDate", width: 120, align: 'center'}
        );
    columns[0].push(
            {title: "最后修改人", field: "modifyAdminName", width: 120, align: 'left'}
        );
    columns[0].push(
            {title: "最后修改时间", field: "modifyDate", width: 120, align: 'center'}
        );
    return columns;
}

function getDataGridHeight(){
	var searchBarHeight = $('.searchBar').outerHeight();
	var winHeight = $(window).outerHeight();
	return winHeight - searchBarHeight - (window.isView ? 25 : 52);
}

function getConfig(){
	var queryContentStr = JSON.stringify(window.preQuery, null, 4);
    return {
        loadMsg: '正在获取数据......',
        url: '/admin/contract!ajaxGetList.action?contractCategoryId='+window.contractCategoryId,
        method: 'post',
        width: '100%',
        rownumbers: true,
        height: getDataGridHeight(),
        singleSelect: false,
        selectOnCheck: false,
        checkOnSelect: false,
        autoRowHeight: false,
        remoteSort: true,
        showFooter: true,
        pagination: true,
        pageSize: 50,
        pageList: [50, 100, 500],
        pageNumber: 1,
        columns: getColumns(),
        data: {"rows":[],"total":0,"footer":[]},
        rowStyler: function(index,rowData){
        	var statusDict = {
    			"true":"background-color:#c9ccc4;"
        	};
        	if(!$.isEmptyObject(rowData.projectId)){
            	return statusDict["true"];
        	}
        },
        queryParams: {
    		preQuery : queryContentStr
    	},
    	onLoadSuccess:function(data){
    		var arr = [];
    		var totalItem = {"total":true};
    		for(var i=0;i<window.contractCategoryTitles.length;i++){
    	    	if(i==0){
    	    		
    	    	}else{
    	    		if(window.contractCategoryTotals.indexOf(window.contractCategoryTitles[i])!=-1){
        	    		var recSum=0;
        	    		for(var ix in data.rows){
        	    			var num = 1*(data.rows[ix][window.contractCategoryTitles[i]]);
        	    			if(!isNaN(num)){
            	    			recSum += num;
        	    			}
        	    		}
        	    		totalItem[window.contractCategoryTitles[i]]=recSum;
    	    		}
    	    	}
    	    	
    	    }
    	    arr.push(totalItem);
    		$('#table').datagrid('reloadFooter',arr);
    		checkRow();
    		disabledCheck();
    		indexArray = [];//清空数组
    	}

    };
}



function doSearch(value){
    var table = $('#table');
    table.datagrid({
    	queryParams: {
    		preQuery : value
    	}
    });
}




function initEvents(){
	
    var table = $('#table');
    saveBtn = $("#saveBtn");
	closeBtn = $("#closeBtn");
	saveBtn.unbind('click').click(function(){
		var rows = table.datagrid("getChecked");
		var ids = [];
		var names = [];
		if (rows.length == 0){
    		msg.alert("数据选择", "请勾选数据", "info");
    		return false;
    	}
		var len = rows.length;
		var key = window.key;
		for (var i = 0; i < len; i++){
			names.push(rows[i][key]);
			ids.push(rows[i].rowId);
		}		
		var categoryId = window.contractCategoryId;//二级分类id
		var idItem = window.idItem;
		var nameItem = window.nameItem;
		var page = window.page;
			if(window.isEdit){//提交开发审批编辑过来的
				window.top.insertDateByWindow(page,ids.join(","),"#"+idItem);
				window.top.insertDateByWindow(page,names.join(","),"#"+nameItem);
			}else{
				window.top.insertDateByTab(page,ids.join(","),"#"+idItem);
				window.top.insertDateByTab(page,names.join(","),"#"+nameItem);
			}
		
		window.top.closeCurrentWindow();
		_this.removeClass('disabled').linkbutton({iconCls: 'icon-save'});
	});
	closeBtn.unbind('click').click(function(){
		window.top.closeCurrentWindow();
		_this.removeClass('disabled').linkbutton({iconCls: 'icon-save'});
	});
    

   
  
    $("#saveQueryHistory").unbind('click').click(function(){
    	if($("#keyword").val()==""){
    		msg.alert("保存查询历史", "没有查询条件！", "warn");
    		return;
    	}else{
    		msg.prompt('保存', '历史查询名称', function(name){
    			if (name===undefined){
    				//这个是取消。。。
				}else if (name===""){
		    		msg.alert("保存查询历史", "您没有填写名称！", "warn");
				}else{
		    		var queryContent = $(".queryDom").queryTree("getValue");
//		    		queryContent.searchBy = $('#searchBy').combobox("getValue");
//		    		queryContent.keyword = $("#keyword").val();
		    		var queryContentStr = JSON.stringify(queryContent);
		    		
		    		var params = {};
		    		params['queryHistory.name']=name;
		    		params['queryHistory.contractCategory.id']=window.contractCategoryId;
		    		params['queryHistory.content']=queryContentStr;
		    		
		    		$.post("/admin/query_history!save.action", 
		    				params,
			    		function(data) {
		    			if(data.status=="success"){
			    	    	msg.show({
			    	            title:'通知',
			    	            msg:'保存查询历史成功',
			    	            timeout:5000,
			    	            showType:'slide'
			    	        });
		    			}else{
			    	    	msg.alert('错误', data.message, "error");
		    			}
		    		}, "json");
				}
			});
    	}
    });
    $("#queryBtn").unbind('click').click(function(){
    	try {
    		var queryContent = $(".queryDom").queryTree("getValue");
    		var queryContentStr = JSON.stringify(queryContent, null, 4);
    		doSearch(queryContentStr);
		} catch (e) {
			if(e instanceof jQuery){  
				msg.alert("错误", "未填写查询条件", "error",function(){
					e.find(".qkeyword").focus();
				});
			}
		}
		
    });
    $("#queryCondShow").unbind('change').change(function(){
    	if($(this).is(":checked")){
    		$(".queryDom").queryTree("show");
    	}else{
    		$(".queryDom").queryTree("remove");
    	}
    });
};

function initDicts(users) {
	$.each(users, function(i, user) {
		letterIdx[user.name] = CTL.convert(user.name).toString();
	});
}
function filterName(q, row) {
	var opts = $(this).combobox('options');
	if (row[opts.textField].toLocaleLowerCase().indexOf(q.toLocaleLowerCase()) != -1) {
		return true;
	}
	if (letterIdx[row[opts.textField]].toLocaleLowerCase().indexOf(
			q.toLocaleLowerCase()) != -1) {
		return true;
	}
	return false;
}

function selectUser(user) {
	var lastLi = $(".addColleague");
	var btn = lastLi.find("a");
	var comboBox = btn.next();
	var li = '<li class="deleteAdminId">{0}<span>X</span><input type="hidden"  name="adminList.id" value="{1}"/></li>';
	li = $.validator.format(li, user.name, user.id);
	if (!unique[user.id]) {
		$(li).insertBefore(lastLi);
	}
	unique[user.id] = true;
	btn.show();
	comboBox.combobox("setValue", "");
	comboBox.next().hide();
}

function initData() {
	var url = "/admin/admin!getAllAjaxList.action";
	$.get(url, function(data) {
		initDicts(data.rows);
		$(".userBox").combobox({
			filter : filterName,
			valueField : "id",
			textField : "name",
			data : data.rows,
			onSelect : selectUser,
			onLoadSuccess : function() {
				if ($(".addColleague a").is(":hidden"))
					return;
				$(this).next().hide();
			}
		});
	}, "json");
}




  function createUsed(){
         $("#userSelect").window("open");
  } 
  
  function preQueryItem(){
	    $(".queryDom").queryTree({
			searchByData:window.queryOption,
			preData:window.preQuery
	    });
  }

  initData();
  
  function checkRow(){
	  for(var i = 0; i < indexArray.length; i++){
		  $('#table').datagrid('checkRow', indexArray[i]);
	  }
	  
  }
  
  function disabledCheck(){
	  var rows = $("#table").datagrid("getRows"); 
	  var count =[];
	  for(var i=0;i<rows.length;i++){
//		  if(rows[i].isProjectRelation == "true"){
//			  count.push(i);
//		  }
// 换一个标志字段		  
		  if(!$.isEmptyObject(rows[i].projectId)){
			  count.push(i);
		  }
	  }
	
	  for(var i = 0; i < count.length; i++){
		 $("table").find(":checkbox:eq(" + count[i] + ")").prop('disabled', true);
	  }
  }
  

 
$(function(){
	
    var table = $('#table');
    table.datagrid(getConfig());
    table.datagrid('getPager').pagination(pager);
// table.datagrid(getConfig()).datagrid({loadFilter:pagerFilter}).datagrid('loadData',window.contractCategoryRows);
//    $('#table').datagrid("getPager").pagination({
//    	onBeforeRefresh:function(pageNumber, pageSize){
//        	location.reload();
//    	}
//    });
    $(".datagrid").on("click", ".edit", function () {
    	var url = "/admin/contract!edit.action?rowId="+$(this).data("row_id")+"&contractCategoryId="+window.contractCategoryId+"&id="+window.contractCategoryId;
        var ezWin = window.top.createWin({ title: "编辑" });
        ezWin.attr("id","addContractWindow");
        var iframe = window.top.createIframe(url);
        iframe.appendTo(ezWin);
        ezWin.window("open");
    	return false;
    })
    
//    $('#table').parents(".datagrid").find("a:has(.pagination-loading,.pagination-load)").click(function(){location.reload();});
    ;
    $(window).resize(function(){
        table.datagrid('resize', {
            height: getDataGridHeight()
        });
    });
    initEvents();
    preQueryItem();
    
    $(".datagrid-header-check :checkbox").prop("disabled",true);
    
//    $(".qselect").combobox({
//    	textField: 'label',
//    	valueField: 'value',
//    	data:window.queryOption
//    }).combobox("select",window.queryOption[0].value);
});

