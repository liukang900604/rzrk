var msg = window.top.$.messager;
var editor = "";
var dict = {};
var keyDict = null;
var bWin = "";

function initLeftTable(opt){
	var tr = '<tr data-id="{0}"><td>{1}</td></tr>';
	var left = opt.win.find(".left table");
	left.empty();
	$.each(opt.leftDict, function(id, key){
		left.append($.validator.format(tr, id, key.name));
	});
}

function initRightTable(opt){
	var tr = '<tr data-id="{0}"><td>{1}</td></tr>';
	var right = opt.win.find(".right table");
	right.empty();
	$.each(opt.rightDict, function(id, key){
		right.append($.validator.format(tr, id, key.name));
	});
}

function initTables(opt){
	initLeftTable(opt);
	initRightTable(opt);
}

function initListEvents(opt){
	var left = opt.win.find(".left table");
	var right = opt.win.find(".right table");
	opt.win.find(".left, .right").on("click", "tr", function(){
		var _this = $(this);
		if (_this.hasClass("selected")){
			_this.removeClass("selected");
		}else{
			_this.addClass("selected");
		}
	}).on('dblclick', 'tr', function(){
		var cls = $(this).parents('table').parent().attr('class');
		$(this).addClass("selected");
		if (cls == 'left'){
			opt.win.find(".toRight").click();
		}else{
			opt.win.find(".toLeft").click();
		}
	});
	opt.win.find(".toRight").click(function(){
		var trs = left.find(".selected");
		$.each(trs, function(i, tr){
			var id = $(tr).data("id");
			opt.rightDict[id] = opt.leftDict[id];
			delete opt.leftDict[id];
			delete originLeftDict[id];
		});
		initTables(opt);
	});
	opt.win.find(".toLeft").click(function(){
		var trs = right.find(".selected");
		$.each(trs, function(i, tr){
			var id = $(tr).data("id");
			opt.leftDict[id] = opt.rightDict[id];
			originLeftDict[id] = opt.rightDict[id];
			delete opt.rightDict[id];
		});
		initTables(opt);
	});
	opt.win.find("#saveNode").linkbutton({
	    iconCls: 'icon-save',
	    onClick: function(){
	    	var actions = {
    			"addNode": addAndEditNode,
    			"editNode": addAndEditNode,
    			"addKeys": addAndEditKeys,
    			"addPerson": addAndEditPerson,
    			"addFieldKeys": addAndEditFieldKeys
	    	};
	    	actions[opt.action](opt);
	    }
	});
	var originLeftDict = $.extend({}, opt.leftDict);
	var searchTextInpt = opt.win.find(".searchText");
	var searchBtn = opt.win.find(".searchBtn");
	searchBtn.click(function(){
		var searchText = $.trim(searchTextInpt.val());
		if (searchText){
			opt.leftDict = {};
			$.each(originLeftDict, function(id, key){
			    if (key.name.toLocaleLowerCase().indexOf(searchText.toLocaleLowerCase()) == -1 &&
			    		searchLetterIdx[key.name].toLocaleLowerCase().indexOf(searchText.toLocaleLowerCase()) == -1){
			        return true;
			    }
				opt.leftDict[id] = key;
			});
			initLeftTable(opt);
		}else{
			opt.leftDict = $.extend({}, originLeftDict);
			initLeftTable(opt);
		}
	});
	searchTextInpt.bind("keyup", "return", function(){
		searchBtn.click();
	});
	var searchLetterIdx = {};
	$.each(opt.leftDict, function(id, key){
		searchLetterIdx[key.name] = CTL.convert(key.name).toString();
	});
	$.each(opt.rightDict, function(id, key){
		searchLetterIdx[key.name] = CTL.convert(key.name).toString();
	});
	if (opt.isNode){
		opt.win.find("#addKeys").click(function(){
			var leftDict = $.extend({}, keyDict);
			var rightDict = {};
			var ids = $(this).attr("ids");
			if (ids && !$.isArray(ids)){
				ids = ids.split(",");
			}
			if ($.isArray(ids)){
				$.each(ids, function(i, id){
					rightDict[id] = leftDict[id];
					delete leftDict[id];
				});
			}
			createNodeWindow({
				title: "添加关键字",
				leftDict: leftDict,
				rightDict: rightDict,
				action: "addKeys",
				isNode: false,
				el: $(this)
			});
		});
		opt.win.find("#addFieldKeys").click(function(){
			var flowId = $("select#flowId").val();
			var leftDict = $.extend({}, window.personFieldList[flowId]);
			var rightDict = {};
			var ids = $(this).attr("ids");
			if (ids && !$.isArray(ids)){
				ids = ids.split(",");
			}
			if ($.isArray(ids)){
				$.each(ids, function(i, id){
					rightDict[id] = leftDict[id];
					delete leftDict[id];
				});
			}
			createNodeWindow({
				title: "添加表单关键字",
				leftDict: leftDict,
				rightDict: rightDict,
				action: "addFieldKeys",
				isNode: false,
				el: $(this)
			});
		});
	}
}

function createListWin(opt){
	var domStr = '';
	var ezWin = window.top.createWin({
		title : opt.title,
		width : 480,
		height: 430,
		maximizable: false
	});
	domStr += '<div class="userList" onselectstart="return false">';
	domStr += '<div class="searchBar">搜索<input type="text" class="searchText" placeholder="支持模糊及首字母查询"/><button class="searchBtn"> 查询 </bottom></div>';
	domStr += '<div class="left"><table></table></div>';
	domStr += '<div class="buttons"><button class="toRight">右移</button><button class="toLeft">左移</button></div>';
	domStr += '<div class="right"><table></table></div>';
	domStr += '<div style="position: absolute;bottom:0;left:10px;"><a id="saveNode" href="#" data-options="iconCls:\'icon-save\'">&nbsp;保&nbsp;存&nbsp;</a>';
	if (opt.isNode){
		domStr += '&nbsp;&nbsp;<a href="javascript:" id="addKeys" style="text-decoration:initial;color:#0088cc;">关键字</a>';
		domStr += '&nbsp;&nbsp;<a href="javascript:" id="addFieldKeys" style="text-decoration:initial;color:#0088cc;">表单关键字</a>';
	}
	domStr += '</div></div>';
	ezWin.append(domStr);
	return ezWin;
}

function createNodeWindow(options){
	
	var defaultOptions = {
		title: "",
		leftDict: {},
		rightDict: {},
		action: "",
		isNode: false
	};
	
	var opt = $.extend({}, defaultOptions, options);
	
	opt.win = createListWin(opt);

	initTables(opt);

	initListEvents(opt);
	
	opt.win.window("open");
	
	return opt.win;
}

function addAndEditNode(opt){
	var params = [];
	var ids = [];
	var names = [];
	var spt = '<li class="link"></li>';
	var liStr = '<li class="middle" data-ids=\'{0}\' data-keys=\'{1}\' data-fieldKeys=\'{2}\' pointId="">{3}</li>';
	var keys = [];
	var fieldKeys = [];
	
	var addKeys = opt.win.find("#addKeys");
	if (addKeys.size() > 0 && addKeys.attr("ids")){
		keys = addKeys.attr("ids").split(",");
	}
	
	var addFieldKeys = opt.win.find("#addFieldKeys");
	if (addFieldKeys.size() > 0 && addFieldKeys.attr("ids")){
		fieldKeys = addFieldKeys.attr("ids").split(",");
	}
	
	if ($.isEmptyObject(opt.rightDict) && keys.length == 0 && fieldKeys.length == 0){
		msg.alert('提示信息', '请选择人，关键字或表单关键字', 'error');
		return false;
	}else if(!$.isEmptyObject(opt.rightDict) && keys.length > 0 ||
			!$.isEmptyObject(opt.rightDict) && fieldKeys.length > 0 ||
			keys.length > 0 && fieldKeys.length > 0){
		msg.alert('提示信息', '选择人，关键字或表单关键字不能同时选择，请修改', 'error');
		return false;
	}
	
	$.each(opt.rightDict, function(id, user){
		ids.push(id);
		names.push(user.name);
	});
	
	if(ids == undefined || ids == null || ids == ""){
		params.push("");
	}else{
		params.push(ids.join(","));
	}

	if($.isArray(keys) && keys.length > 0){
		/*var span = "";
		$.each(keys, function(i, key){
			span += "<span>" + key + "</span>";
		});
		params.push(span);*/
		params.push(keys.join(","));
	}else{
		params.push("");
	}

	if($.isArray(fieldKeys) && fieldKeys.length > 0){
		/*var span = "";
		$.each(fieldKeys, function(i, key){
			span += "<span>" + key + "</span>";
		});
		params.push(span);*/
		params.push(fieldKeys.join(","));
	}else{
		params.push("");
	}
	
	if(names.length > 0){
		var span = "";
		$.each(names, function(i, name){
			span += "<span>" + name + "</span>";
		});
		params.push(span);
		//params.push(names[0] + (names.length > 1 ? "..." : ""));
	}else{
		params.push("");
	}
	
	if (params[params.length - 1] == ""){
		var span = '', names;
		if (params[params.length - 2] != ""){
			names = params[params.length - 2].split(",");
		}else if (params[params.length - 3] != ""){
			var keyNames = [];
			$.each(params[params.length - 3].split(","), function(i, key){
				keyNames.push(keyDict[key].name);
			});
			names = keyNames;
		}
		$.each(names, function(i, n){
			span += "<span>" + n + "</span>"
		});
		params[params.length - 1] = span;
	}
	
	liStr = $.validator.format(liStr, params);
	if (opt.action == "addNode"){
		$(spt + liStr).insertAfter(opt.el);
	}else{
    	opt.el.replaceWith(liStr);
	}
	opt.win.window("destroy");
}

function addAndEditPerson(opt){
	var ids = [];
	$.each(opt.rightDict, function(id, user){
		ids.push(id);
	});
	opt.el.text("添加人" + (ids.length ? $.validator.format("({0})", ids.length) : ""));
	ids.length ? opt.el.data("ids", ids) : opt.el.removeData("ids");
	opt.win.window("destroy");
}

function addAndEditKeys(opt){
	var ids = [];
	$.each(opt.rightDict, function(id, key){
		ids.push(id);
	});
	if (ids.length> 1){
		msg.alert('提示信息', '关键字只能选择一个，请修改', 'error');
		return false;
	}
	opt.el.text("关键字" + (ids.length ? $.validator.format("({0})", ids.length) : ""));
	ids.length ? opt.el.attr("ids", ids) : opt.el.removeAttr("ids");
	opt.win.window("destroy");
}

function addAndEditFieldKeys(opt){
	var ids = [];
	$.each(opt.rightDict, function(id, key){
		ids.push(id);
	});
	if (ids.length> 1){
		msg.alert('提示信息', '表单关键字只能选择一个，请修改', 'error');
		return false;
	}
	opt.el.text("表单关键字" + (ids.length ? $.validator.format("({0})", ids.length) : ""));
	ids.length ? opt.el.attr("ids", ids) : opt.el.removeAttr("ids");
	opt.win.window("destroy");
}

function addNode(){
	if ($.isEmptyObject(dict)){
		msg.alert('提示信息', '数据未加载完成，请稍后再试....', 'info');
		return;
	}
	var leftDict = $.extend({}, dict);
	var rightDict = {};
	createNodeWindow({
		title: "新增节点",
		leftDict: leftDict,
		rightDict: rightDict,
		action: "addNode",
		isNode: true,
		el: $(this)
	});
}

function editNode(){
	var el = $(this);
	var ids = [];
	if (el.data("ids")){
		ids = el.data("ids").split(",");
	}
	if ($.isEmptyObject(dict)){
		msg.alert('提示信息', '数据未加载完成，请稍后再试....', 'info');
		return;
	}
	var leftDict = $.extend({}, dict);
	var rightDict = {};
	$.each(ids, function(i, id){
		rightDict[id] = leftDict[id];
		delete leftDict[id];
	});
	var win = createNodeWindow({
		title: "编辑节点",
		leftDict: leftDict,
		rightDict: rightDict,
		action: "editNode",
		isNode: true,
		el: $(this)
	});
	if (el.data("keys") && win.find("#addKeys")){
		var keys = el.data("keys").split(",");
		var addKeys = win.find("#addKeys");
		addKeys.text("关键字" + (keys.length ? $.validator.format("({0})", keys.length) : ""));
		addKeys.attr("ids", keys.join(","));
	}
	if (el.data("fieldkeys") && win.find("#addFieldKeys")){
		var keys = el.data("fieldkeys").split(",");
		var addFieldKeys = win.find("#addFieldKeys");
		addFieldKeys.text("表单关键字" + (keys.length ? $.validator.format("({0})", keys.length) : ""));
		addFieldKeys.attr("ids", keys.join(","));
	}
}

function deleteNode(){
	var link = $(this).prev();
	link.remove();
	$(this).remove();
}

function initBranchEvents(){
	var listPanel = bWin.find(".branchList .listPanel");
	bWin.find(".branchList").on("click", ".del", function(){
		$(this).parents(".branch").remove();
	}).on("click", ".addPerson", function(){
		var ap = $(this);
		var ak = ap.parent().find(".addKeys");
		var ids = ak.attr("ids");
		if (ids){
			ids = ids.split(",");
		}
		if ($.isArray(ids) && ids.length > 0){
    		msg.alert("提示信息", "已添加关键字，不能同时添加人", "error");
    		return;
		}
		var leftDict = $.extend({}, dict);
		var rightDict = {};
		var bs = ap.parent().siblings();
		$.each(bs, function(i, b){
			var ids = $(b).find(".addPerson").data("ids");
			if ($.isArray(ids)){
				$.each(ids, function(i, id){
					delete leftDict[id];
				});
			}
		});
		var ids = ap.data("ids");
		if ($.isArray(ids)){
			$.each(ids, function(i, id){
				rightDict[id] = leftDict[id];
				delete leftDict[id];
			});
		}
		createNodeWindow({
			title: "添加人",
			leftDict: leftDict,
			rightDict: rightDict,
			action: "addPerson",
			isNode: false,
			el: $(this)
		});
	}).on("click", ".addKeys", function(){
		var ak = $(this);
		var ap = ak.parent().find(".addPerson");
		var ids = ap.data("ids");
		if ($.isArray(ids) && ids.length > 0){
    		msg.alert("提示信息", "已添加人，不能同时添加关键字", "error");
    		return;
		}
		var leftDict = $.extend({}, keyDict);
		var rightDict = {};
		var ids = ak.attr("ids");
		if (ids){
			ids = ids.split(",");
		}
		if ($.isArray(ids)){
			$.each(ids, function(i, id){
				rightDict[id] = leftDict[id];
				delete leftDict[id];
			});
		}
		createNodeWindow({
			title: "添加关键字",
			leftDict: leftDict,
			rightDict: rightDict,
			action: "addKeys",
			isNode: false,
			el: $(this)
		});
	});
	bWin.find("#addBranch").linkbutton({
	    iconCls: 'icon-add',
	    onClick: function(){
	    	var bStr = '<div class="branch" style="margin:10px 0;"><input type="text" class="valL" style="width:100px;"/>&nbsp;<select style="padding:1px 0;"><option value="<">&lt;</option><option value="≤">&le;</option></option><option value="=">=</option><option value="包含">包含</option><option value="不等于">不等于</option></select>&nbsp;条件字段&nbsp;<select style="padding:1px 0;"><option value="<">&lt;</option><option value="≤">&le;</option><option value="=">=</option><option value="包含">包含</option><option value="不等于">不等于</option></select>&nbsp;<input type="text" class="valR" style="width:100px;"/>&nbsp;<a href="javascript:" class="addPerson" style="text-decoration:initial;color:#0088cc;">添加人</a>&nbsp;<a href="javascript:" class="addKeys" style="text-decoration:initial;color:#0088cc;">关键字</a>&nbsp;<a href="javascript:" class="del" style="text-decoration:initial;color:#0088cc;">删除</a></div>';
	    	listPanel.append(bStr);
	    }
	});
	bWin.find("#saveBranchNode").linkbutton({
	    iconCls: 'icon-save',
	    onClick: function(){
	    	var params = [], temp = [];
	    	var spt = '<li class="link"></li>';
	    	//var liStr = '<li class="branch" data-params=\'{0}\'><span class="top" title="{1}">{2}</span><span class="bottom"></span></li>';
	    	var liStr = '<li class="branch" data-params=\'{0}\'>{1}</li>';
	    	var span = '';
	    	var field = bWin.find(".field").val();
	    	var bs = bWin.find(".branch");
	    	var pass = true;
	    	var pass2 = true;
	    	var cNull = 0;
	    	if (!field){
	    		msg.alert("保存错误", "此表单没有字段，请先添加字段", "error");
	    		return;
	    	}
	    	$.each(bs, function(i, b){
	    		var args = [];
	    		var persons = null;
	    		var keys = null;
	    		args.push($.trim($(b).find(".valL").val()));
	    		args.push($(b).find("select:eq(0)").val());
	    		args.push(field);
	    		args.push($(b).find("select:eq(1)").val());
	    		args.push($.trim($(b).find(".valR").val()));
	    		persons = $(b).find(".addPerson").data("ids");
	    		keys = $(b).find(".addKeys").attr("ids");
	    		if (keys){
	    			keys = keys.split(",");
	    		}
	    		if (args[0] == '' && args[4] == ''){
	    	    	pass2 = false;
	    			return false;
	    		}
	    		if ((!persons || ($.isArray(persons) && persons.length == 0))
	    				&& (!keys || ($.isArray(keys) && keys.length == 0))) {
	    			cNull++;
	    			if (cNull > 1){
		    			pass = false;
		    			return false;
	    			}
	    			span += "<span>空节点</span>";
    			}
	    		if ($.isArray(persons) && persons.length > 0) {
	    			$.each(persons, function(i, person){
		    			span += "<span>" + dict[person].name + "</span>";
	    			});
	    			args.push(persons);
    			}else{
	    			args.push("");
    			}
	    		if ($.isArray(keys) && keys.length > 0) {
	    			$.each(keys, function(i, key){
		    			span += "<span>" + keyDict[key].name + "</span>";
	    			});
	    			args.push(keys);
    			}else{
	    			args.push("");
    			}
	    		temp.push(args);
	    	});
	    	if (!pass2){
	    		msg.alert("保存错误", "条件值必须输入一边，不能两边都为空！", "error");
	    		return;
	    	}
	    	if (!pass){
	    		msg.alert("保存错误", "无处理人的条件只能有一个，请检查！", "error");
	    		return;
	    	}
	    	params.push(JSON.stringify(temp));
	    	params.push(span);
	    	//params.push("[条件]");
	    	liStr = $.validator.format(liStr, params);
	    	if (optb == "addBranch"){
	    		$(spt + liStr).insertAfter(el);
	    	}else{
		    	el.replaceWith(liStr);
	    	}
	    	optObj = null;
	    	bWin.window("destroy");
	    }
	});
}

function createBranchWin(title){
	var flowId = $("select#flowId").val();
	var ezWin = window.top.createWin({
		title : title,
		width : 640,
		height: 480,
		maximizable: false
	});
	var domStr = '<div class="branchList" onselectstart="return false" style="padding:10px;">';
	domStr += '<div><label style="vertical-align:middle;">条件字段：</label><select class="field" style="vertical-align:middle;padding:1px 0;"></select>&nbsp;<a id="addBranch" href="#" data-options="iconCls:\'icon-add\'">添加条件</a></div>';
	domStr += '<div class="listPanel" style="margin: 10px 0;border:1px solid #ddd;border-left:0;border-right:0;">';
	domStr += '<div class="branch" style="margin:10px 0;"><input type="text" class="valL" style="width:100px;"/>&nbsp;<select style="padding:1px 0;"><option value="<">&lt;</option><option value="≤">&le;</option></option><option value="=">=</option><option value="包含">包含</option><option value="不等于">不等于</option></select>&nbsp;条件字段&nbsp;<select style="padding:1px 0;"><option value="<">&lt;</option><option value="≤">&le;</option><option value="=">=</option><option value="包含">包含</option><option value="不等于">不等于</option></select>&nbsp;<input type="text" class="valR" style="width:100px;"/>&nbsp;<a href="javascript:" class="addPerson" style="text-decoration:initial;color:#0088cc;">添加人</a>&nbsp;<a href="javascript:" class="addKeys" style="text-decoration:initial;color:#0088cc;">关键字</a></div>';
	domStr += '</div>';
	domStr += '<div><a id="saveBranchNode" href="#" data-options="iconCls:\'icon-save\'">&nbsp;保存&nbsp;</a></div>';
	domStr += '</div>';
	domStr = $(domStr);
	select = domStr.find(".field");
	if (window.fieldList[flowId].length > 0){
		var fields = window.fieldList[flowId];
		var len = fields.length
		var option = "<option value='{0}'>{0}</option>";
		for (var i = 0; i < len; i++){
			select.append($.validator.format(option, fields[i]));
		}
	}
	ezWin.append(domStr);
	return ezWin;
}

function addBranchNode(){
	el = $(this);
	optb = "addBranch";
	bWin = createBranchWin("新增条件节点");
	if ($.isEmptyObject(dict)){
		msg.alert('提示信息', '数据未加载完成，请稍后再试....', 'info');
		return;
	}
	initBranchEvents();
	bWin.window("open");
}

function editBranchNode(){
	el = $(this);
	optb = "editBranch";
	if ($.isEmptyObject(dict)){
		msg.alert('提示信息', '数据未加载完成，请稍后再试....', 'info');
		return;
	}
	bWin = createBranchWin("编辑条件节点");
	var params = el.data("params");
	if (!$.isArray(params)){
		params = JSON.parse(params);
	}
	var bStr = '<div class="branch" style="margin: 10px 0;"><input type="text" class="valL" style="width:100px;" value="{0}"/>&nbsp;<select style="padding:1px 0;"><option value="<">&lt;</option><option value="≤">&le;</option></option><option value="=">=</option><option value="包含">包含</option><option value="不等于">不等于</option></select>&nbsp;条件字段&nbsp;<select style="padding:1px 0;"><option value="<">&lt;</option><option value="≤">&le;</option><option value="=">=</option><option value="包含">包含</option><option value="不等于">不等于</option></select>&nbsp;<input type="text" class="valR" style="width:100px;" value="{1}"/>&nbsp;<a href="javascript:" class="addPerson" style="text-decoration:initial;color:#0088cc;">添加人{2}</a>&nbsp;<a href="javascript:" class="addKeys" style="text-decoration:initial;color:#0088cc;">关键字{3}</a>&nbsp;<a href="javascript:" class="del" style="text-decoration:initial;color:#0088cc;">删除</a></div>';
	var fieldSelect = bWin.find(".field");
	var listPanel = bWin.find(".branchList .listPanel");
	listPanel.empty();
	$.each(params, function(i, args){
		var temp = [];
		temp.push(args[0]);
		temp.push(args[4]);
		temp.push(args[5].length ? $.validator.format("({0})", args[5].length) : "");
		temp.push($.isArray(args[6]) ? $.validator.format("({0})", args[6].length) : "");
		var b = $($.validator.format(bStr, temp));
		if (i == 0){
			fieldSelect.val(args[2]);
			b.find(".del").remove();
		}
		b.find("select:eq(0)").val(args[1]);
		b.find("select:eq(1)").val(args[3]);
		b.find(".addPerson").data("ids", args[5]);
		if ($.isArray(args[6])){
			b.find(".addKeys").attr("ids", args[6]);
		}
		listPanel.append(b);
	});
	initBranchEvents();
	bWin.window("open");
}


function initPointEvents() {
    $.contextMenu({
        selector: "ul.flow li:not(.end, .link)",
        trigger: "left",
        build: function($trigger, e){
            var options = {
                items: {
                    "addNode": {name: "新增节点", icon: "add", callback: addNode},
                    "sep1" : "---------",
                    "editNode": {name: "编辑节点", icon: "edit", callback: editNode},
                    "sep2" : "---------",
        			"addBranch" : { name : "新增条件节点", icon : "add", callback: addBranchNode },
                    "sep3" : "---------",
                    "editBranch": {name: "编辑节点", icon: "edit", callback: editBranchNode},
                    "sep4" : "---------",
        			"delete" : { name : "删除节点", icon : "delete", callback: deleteNode }
                }
            };
            if($trigger.hasClass('start')){
                delete options.items["sep2"];
                delete options.items["sep3"];
                delete options.items["sep4"];
                delete options.items["editNode"];
                delete options.items["editBranch"];
                delete options.items["delete"];
            }else if($trigger.hasClass('middle')){
                delete options.items["sep3"];
                delete options.items["editBranch"];
            }else if($trigger.hasClass('branch')){
                delete options.items["sep1"];
                delete options.items["editNode"];
            }
            return options;
        }
    });
	$("li.middle > span").poshytip({
		className: 'tip-darkgray',
		liveEvents: true
	});
	/*$("#saveWorkflow").unbind("click").click(function(){
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
*/
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

function initValidate(){
	var rules = {}, messages = {};
	rules = {
		"workFlow.flowName": "required",
		"workFlow.flowType.id": "required",
		"workFlow.flowForm.id": "required",
		"workFlow.flowContent": "required"
	};
	messages = {
		"workFlow.flowName": "不能为空，请输入",
		"workFlow.flowType.id": "不能为空，请输入",
		"workFlow.flowForm.id": "不能为空，请输入",
		"workFlow.flowContent": "不能为空，请输入"
	};
	$('#atForm').validate({
		ignore: "",
        errorElement: "span",
        errorPlacement: function(error, el) {
        	error.appendTo(el.parents('td'));
        },
        rules: rules,
        messages: messages
	});
};

function initEvents(){
	var atForm = $('#atForm');
	saveBtn = $("#saveBtn");
	closeBtn = $("#closeBtn");
	saveBtn.unbind('click').click(function(){
		var msg = window.top.$.messager;
		var _this = $(this);
		if (_this.hasClass('disabled') || !atForm.valid()){
			return false;
		}
		
		var userArray=[];//用户数组
		var pointArray=[];//节点id数组
		var userNameArray=[];//全名数组
		var nameArray=[];//名字数组
		var conditionArray = [];//用户条件数组
		var banchPointArray = [];//条件分支还是普通分支
		var keyArray = [];//关键字
		var formKeyArray=[];//表单关键字数组
		$("li").each(function(){		
			var className = $(this).attr("class");
			var val = null;
			if(className == "middle"){//普通分支
				banchPointArray.push("2");
				// val = $(this).find("span").text();
			}else if(className == "branch"){//条件分支
				banchPointArray.push("1");
				/*var  banchNameArray = []; 
				 $(this).find("td").each(function(){
					 banchNameArray.push($(this).text());
				})
				val = banchNameArray.join(",");*/
			}else{// return true 相当于 continue  return false 相当于break功能
				return true;
			}
			var data = $(this).attr("data-ids");
			var point = $(this).attr("pointId");
			var title = "";
			var spans = $(this).find("span");
			var condition = $(this).attr("data-params");//用户条件参数
			var key = $(this).attr("data-keys");//关键字
			var formKey = $(this).attr("data-fieldkeys");//表单关键字
			$.each(spans, function(i,span){
			    title += (i != 0 ? "," : "") + $(span).text();
			});
			
			if(data ==undefined || data=="" || data==null ){
				userArray.push("0");//默认给0
			}else{
				userArray.push(data);
			}
           if(point ==undefined || point=="" || point==null ){
        	   pointArray.push("0");
			}else{
				pointArray.push(point);
			}
           if(title ==undefined || title=="" || title==null ){
        	   userNameArray.push("0");
			}else{
				userNameArray.push(title);
			}
           
           if(val ==undefined || val=="" || val==null ){
        	   nameArray.push("0");
			}else{
				nameArray.push(val);
			}
			
           if(condition ==undefined || condition=="" || condition==null){
        	   conditionArray.push("0");
			}else{
				conditionArray.push(condition);
			}
           
           if(key ==undefined || key=="" || key==null){
        	   keyArray.push("0");
			}else{
				keyArray.push(key);
			}
           
           if(formKey ==undefined || formKey=="" || formKey==null){
        	   formKeyArray.push("0");
			}else{
			   formKeyArray.push(formKey);
			}
           
           
			
           
		})
		
		if(userNameArray.length == 0){
			 msg.alert('温馨提示',"流程图不能为空!",'error');
			 return false;
		}
		
		//节点赋值
		 $("#userArray").val(userArray.join("、"));
		 $("#pointArray").val(pointArray.join("、"));
		 $("#userNameArray").val(userNameArray.join("、"));
		 $("#nameArray").val(nameArray.join("、"));
		 $("#conditionArray").val(conditionArray.join("、"));
		 $("#banchPointArray").val(banchPointArray.join("、"));
		 $("#keyArray").val(keyArray.join("、"));
		 $("#formKeyArray").val(formKeyArray.join("、"));
		 
		 
		 
		/* $("li.branch").each(function(){
			 var hidden = "<input type='hidden' name='branch' value=\'{0}\'/>";
			 var params = $(this).data("params");
			 if ($.isArray(params)) params = JSON.stringify(params);
			 atForm.append($.validator.format(hidden, params));
		 });*/
		 
		 
		 $.ajax({    
          type:'post',        
          url: '/admin/work_flow!save.action',    
          data:$('#atForm').serialize(),    
          cache:false,    
          dataType:'json',    
          success:function(data){    
           if(data.status == 'success'){
        	   msg.alert('保存信息',data.message,'info',function(){
        		   window.top.reloadDataGridInTabTab('工作流定义', '工作流流程');
            	   window.top.closeCurrentWindow();
        	   });
//        	   
           }else{
        	   msg.alert('保存信息',data.message,'error',function(){
             	   return false;
        	   });
           }
          } 
             
        });
	});
	closeBtn.unbind('click').click(function(){
		window.top.closeCurrentWindow();
		_this.removeClass('disabled').linkbutton({iconCls: 'icon-save'});
	});
	$("#flowId").unbind('click').change(function(){
		var flow = $(".flow");
		flow.empty();
		flow.append('<li class="start"><span class="top">发起人</span></li>');
		flow.append('<li class="link"></li>');
		flow.append('<li class="end"><span>结束</span></li>');
	});
	/*
	$(".flow").on("click", "li.branch img", function(e){
		var _this = $(this);
		var params = _this.parents("li").data("params");
		var idx = _this.parents("tr").index();
		var table = [];
		var cWin = window.top.createWin({
			title: "条件信息",
			width: 400,
			height: 200
		});
		var cStr = params[idx][0] + " " + params[idx][1] + " " + params[idx][2] + " " + params[idx][3] + " " + params[idx][4];
		var pStr = "";
		if (params[idx][5]){
			$.each(params[idx][5], function(i, id){
				pStr += (i ? ", " : "") + dict[id].name;
			});
		}else if(params[idx][6]){
			$.each(params[idx][6], function(i, id){
				pStr += (i ? ", " : "") + keyDict[id].name;
			});
		}
		table.push("<table style='border-collapse:collapse;width:100%;'>");
		table.push("<tr><td style='width:60px;text-align:right;padding:5px;border:1px solid #ccc;'>条件</td><td style='padding:5px;border:1px solid #ccc;'>" + cStr + "</td></tr>");
		table.push("<tr><td style='width:60px;text-align:right;padding:5px;border:1px solid #ccc;'>处理人</td><td style='padding:5px;border:1px solid #ccc;'>" + pStr + "</td></tr>");
		table.push("</table>");
		cWin.append(table.join(""));
		cWin.window("open");
		return false;
	});
	*/
};


function initWorkFlow() {
	if($("#workFlowId").val() == null || $("#workFlowId").val() == ""){
		return false;
	}
	$
			.ajax({
				type : 'post',
				url : '/admin/work!getAjaxWorkFlowDataByWorkFlowId.action',
				data : {
					flowTypeId : $("#workFlowId").val()
				},
				cache : false,
				dataType : 'json',
				success : function(data) {
						
						$("#workFlowShow").html("");//清空流程图
						var flowShowStr = "<ul class='flow'><li class='start'><span>发起人</span></li><li class='link'></li>";
						if(data.pointShowList != null && data.pointShowList != ""){
							for(var i = 0; i < data.pointShowList.length; i++){
								if(data.pointShowList[i].isBranch == 1){//分支节点
									var banchName = data.pointShowList[i].userName;
									if(banchName == null || banchName == "" ){
										return false;
									}
									flowShowStr += "<li class='branch' data-params='"+data.pointShowList[i].showCondition +"' " +
									" pointid='"+data.pointShowList[i].id+"' >";
									var banchNameArray = banchName.split(",");
									for(var j = 0; j < banchNameArray.length; j++){
										flowShowStr += "<span>" + banchNameArray[j] + "</span>";
									}
									flowShowStr += "</li><li class='link'></li>"
									// <span class='top' title='"+data.pointShowList[i].userName+"'> "+data.pointShowList[i].name+" </span>  <span class='bottom'></span>  </li>  ";
								}else{  
									flowShowStr += "<li class='middle' data-ids='"+data.pointShowList[i].userId + "' ";
									if(data.pointShowList[i].formKeyName == ""){
										flowShowStr += "data-keys='" + data.pointShowList[i].searchName+"' data-fieldkeys  ";
									}else{
										flowShowStr += "data-keys  data-fieldkeys ='" + data.pointShowList[i].formKeyName+"' ";
									}
									
									flowShowStr += " pointid='" + data.pointShowList[i].id + "'>"
									var names = data.pointShowList[i].userName.split(",");
									$.each(names, function(i, name){
										flowShowStr += "<span>" + name+ "</span>";
									});
									flowShowStr += "</li><li class='link'></li>";
								}
								
								
								
							}
						}
						flowShowStr += "<li class='end'><span>结束</span></li></ul>";
						$("#workFlowShow").append(flowShowStr);	

				}

			});

}


$(function(){
    //增加页面校验规则
	initValidate();
    initEvents();
    initPointEvents();
    initData();
    initWorkFlow();
    keyDict = window.jobList;
});