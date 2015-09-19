var msg = window.top.$.messager;
var dict = {};
var leftDict = {};
var rightDict = {};
var ezWin = null;
var el = null;
var opt = null;

function initLeftTable(){
	var tr = '<tr data-id="{0}"><td>{1}</td></tr>';
	var left = ezWin.find(".left table");
	left.empty();
	$.each(leftDict, function(id, user){
		left.append($.validator.format(tr, id, user.name));
	});
}

function initRightTable(){
	var tr = '<tr data-id="{0}"><td>{1}</td></tr>';
	var right = ezWin.find(".right table");
	right.empty();
	$.each(rightDict, function(id, user){
		right.append($.validator.format(tr, id, user.name));
	});
}

function initTables(){
	initLeftTable();
	initRightTable();
}

function initListEvents(){
	var left = ezWin.find(".left table");
	var right = ezWin.find(".right table");
	ezWin.find(".left, .right").on("click", "tr", function(){
		var _this = $(this);
		if (_this.hasClass("selected")){
			_this.removeClass("selected");
		}else{
			_this.addClass("selected");
		}
	});
	ezWin.find(".toRight").click(function(){
		var trs = left.find(".selected");
		$.each(trs, function(i, tr){
			var id = $(tr).data("id");
			rightDict[id] = leftDict[id];
			delete leftDict[id];
		});
		initTables();
	});
	ezWin.find(".toLeft").click(function(){
		var trs = right.find(".selected");
		$.each(trs, function(i, tr){
			var id = $(tr).data("id");
			leftDict[id] = rightDict[id];
			delete rightDict[id];
		});
		initTables();
	});
	ezWin.find("#saveNode").linkbutton({
	    iconCls: 'icon-save',
	    onClick: function(){
	    	var params = [];
	    	var ids = [];
	    	var names = [];
	    	var spt = '<li class="link"><hr/></li>';
	    	var liStr = '<li class="middle" data-ids=\'{0}\' pointId=""><span class="top" title="{1}">{2}</span><span class="bottom">{3}</span></li>';

	    	if ($.isEmptyObject(rightDict)){
	    		msg.alert('提示信息', '未选择', 'error');
	    	}
	    	
	    	$.each(rightDict, function(id, user){
	    		ids.push(id);
	    		names.push(user.name);
	    	});
	    	
	    	params.push(ids.join(","));
	    	params.push(names.join("，"));
	    	params.push(names[0] + (names.length > 1 ? "..." : ""));
	    	params.push('[协同]');
	    	
	    	liStr = $.validator.format(liStr, params);
	    	if (opt == "add"){
	    		$(spt + liStr).insertAfter(el);
	    	}else{
		    	el.replaceWith(liStr);
	    	}
	    	ezWin.window("destroy");
	    }
	});
}

function createListWin(title){
	var ezWin = window.top.createWin({
		title : title,
		width : 480,
		height: 390
	});
	var domStr = '<div class="userList">';
	domStr += '<div class="left"><table></table></div>';
	domStr += '<div class="buttons"><button class="toRight">右移</button><button class="toLeft">左移</button></div>';
	domStr += '<div class="right"><table></table></div>';
	domStr += '<div style="position: absolute;bottom:0;left:10px;"><a id="saveNode" href="#" data-options="iconCls:\'icon-save\'">&nbsp;保&nbsp;存&nbsp;</a></div>';
	domStr += '</div>';
	ezWin.append(domStr);
	return ezWin;
}

function addNode(){
	el = $(this);
	opt = "add";
	ezWin = createListWin("新加节点");
	if ($.isEmptyObject(dict)){
		msg.alert('提示信息', '数据未加载完成，请稍后再试....', 'info');
		return;
	}
	leftDict = $.extend({}, dict);
	rightDict = {};
	initTables();
	initListEvents();
	ezWin.window("open");
}

function editNode(){
	el = $(this);
	opt = "edit";
	ezWin = createListWin("编辑节点");
	var ids = el.data("ids").split(",");
	if ($.isEmptyObject(dict)){
		msg.alert('提示信息', '数据未加载完成，请稍后再试....', 'info');
		return;
	}
	leftDict = $.extend({}, dict);
	$.each(ids, function(i, id){
		rightDict[id] = leftDict[id];
		delete leftDict[id];
	});
	initTables();
	initListEvents();
	ezWin.window("open");
}

function deleteNode(){
	var link = $(this).prev();
	link.remove();
	$(this).remove();
}

function initEvents() {
	$.contextMenu({
		selector : 'ul.flow li.start',
		trigger: "left",
		items : {
			"add" : { name : "增加节点", icon : "add", callback: addNode }
		}
	});
	$.contextMenu({
		selector : 'ul.flow li.middle',
		trigger: "left",
		items : {
			"add" : { name : "增加节点", icon : "add", callback: addNode },
			"sep1" : "---------",
			"edit" : { name : "编辑节点", icon : "edit", callback: editNode },
			"sep2" : "---------",
			"delete" : { name : "删除节点", icon : "delete", callback: deleteNode }
		}
	});
	$("li.middle > span").poshytip({
		className: 'tip-darkgray',
		liveEvents: true
	});
	$("#saveWorkflow").unbind("click").click(function(){
		var userArray=[];//用户数组
		var pointArray=[];//节点id数组
		var userNameArray=[];//全名数组
		var nameArray=[];//名字数组
		$("li.middle").each(function(){
			var data = $(this).attr("data-ids");
			var point = $(this).attr("pointId");
			var title = $(this).find("span.top").attr("title");
			var val = $(this).find("span.top").text();
			if(data ==undefined || data=="" || data==null){
				userArray.push("0");//默认给0
			}else{
				userArray.push(data);
			}
           if(point ==undefined || point=="" || point==null){
        	   pointArray.push("0");
			}else{
				pointArray.push(point);
			}
           if(title ==undefined || title=="" || title==null ){
        	   userNameArray.push("0");
			}else{
				userNameArray.push(title);
			}
           
           if(val ==undefined || val=="" || val==null || val == "undefined"){
        	   nameArray.push("0");
			}else{
				nameArray.push(val);
			}
			
			
			
		})
		var msg = window.top.$.messager;
		var workId =$("#workId").val();
		$.ajax({
			type : 'post',
			url : '/admin/work_flow!saveFlowPoint.action',
			data : {'userArray':userArray.join("、"),'pointArray':pointArray.join("、"),'userNameArray':userNameArray.join("、"),'nameArray':nameArray.join("、"),"workFlow.id":workId},
			cache : false,
			dataType : 'json',
			success : function(data) {
				if (data.status == 'success') {
					msg.alert('保存信息', data.message,'info',function() {
						window.top.reloadIframeInWindow('flowDefinedWindow');
						window.top.closeCurrentWindow();
					});

				} else {
					msg.alert('保存信息', data.message, 'info',function() {
						return false;
					});
				}
			}
		});
	});
}

function initDict(data){
	$.each(data.rows, function(i, user){
		dict[user.id] = user;
	});
}

function initData(){
	var url = "/admin/admin!getAllAjaxList.action";
	console.log("数据请求中......");
	$.get(url, function(data){
		console.log(data);
		initDict(data);
	}, 'json');
}

$(function() {
	initEvents();
	initData();
});
