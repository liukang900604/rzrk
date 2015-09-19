var panels = [];
var msg = window.top.$.messager;
//var storage = window.localStorage;
var storage = null;
var focusWin = '';
var sortable = '';
var layout = '';
var categoryDict = {};
var localData = [];

function addPanel(p){
	var li = $('<li data-url="' + p.url + '"><div id="' + p.id + '"></div></li>');
	sortable.append(li);
	var panel = li.find('#' + p.id);
	console.log(p);
	panel.panel({
		fit: true,
	    title: p.name,
	    href: '/admin/index_navigator_item!itemList.action',
	    queryParams:{
	    	"id": p.id,
	    	"listMaxCount": p.listMaxCount
	    },
	    closable: false,
	    border: false,
	    style: {'padding': '0'},
	    tools:[{
	        iconCls:'icon-reload',
	        handler:function(){
	        	panel.panel('open').panel('refresh');
	        }
	    }/*,{
	        iconCls:'icon-gear',
	        handler:function(){
	        	var settings = window.top.createWin({
	        		title: "设置显示数据条数",
	        		width: 240,
	        		height: 80,
	        		resizable: false,
	        		maximizable: false
	        	});
	        	var dom = $("<div style='padding:10px;'>显示条数：</label><input type='text' style='width:60px;' value='" + p.listMaxCount + "'/>&nbsp;<a href='javascript:' style='vertical-align: bottom;'>确认</a></div>");
	        	settings.append(dom);
	        	var inpt = settings.find('input');
	        	dom.find('a').linkbutton({
	        	    iconCls: 'icon-save',
	        	    height: 21,
	        	    onClick: function(){
	    	        	var num = $.trim(inpt.val());
	        	    	if (isNaN(num) || num.indexOf('.') != -1 || num.indexOf('-') != -1){
		        	    	msg.alert('输入错误', '请输入正整数', 'error');
	        	    		return false;
	        	    	}
	        	    	if (p.listMaxCount != parseInt(num)){
		        	    	p.listMaxCount = parseInt(num);
		    	        	panel.panel({queryParams:{"id": p.id,"listMaxCount": p.listMaxCount}});
		        	    	if (storage){
		        	        	storage.setItem("panels[" + window.userId + "]", JSON.stringify(localData));
		        	    	}
	        	    	}
	        	    	sortable.sortable('destroy');
	        	    	sortable.sortable({ handle: '.panel-header' });
	        	    	settings.window('destroy');
	        	    }
	        	});
	        	settings.window('open');
        	}
	    }*/]
	});
	sortable.sortable('destroy');
	sortable.sortable({ handle: '.panel-header' });
}
function initPanels(){
	/*if (storage){
		var ps = storage.getItem("panels[" + window.userId + "]");
		var tempDict = {};
		if (ps){
			localData = JSON.parse(ps);
			$.each(localData, function(i, data){
				tempDict[data.url] = data;
			});
			localData = [];
			$.each(window.focused, function(key, data){
				if (tempDict[key]){
					localData.push(tempDict[key]);
				}else{
					data.listMaxCount = 10;
					localData.push(data);
				}
			});
		}else{
			$.each(window.focused, function(i, data){
				data.listMaxCount = 10;
				localData.push(data);
			});
		}
	}*/
	$.each(window.focused, function(i, data){
		data.listMaxCount = 10;
		localData.push(data);
	});
	$.each(localData, function(i, p){
		addPanel(p);
	});
	$(window).resize();
}
function initEvents(){
	var addFocus = $(".addFocus");
	addFocus.click(function(){
		pretreat({
			"title": "请选择需要关注的内容"
		});
		focusWin.window("open");
	});
	
	sortable.on("click", "a.listItem", function(){
		var src = $(this).data("src");
		var title = $(this).attr("title");
        var ezWin = window.top.createWin({
        	title:title,
        	resizable: true,
        	maximizable: false
		});
        var iframe = window.top.createIframe(src);
        iframe.appendTo(ezWin);
        ezWin.window("open");
    	return false;
	});
	
	sortable.bind('sortupdate', function() {
        var lis = $(this).find('> li');
        localData = [];
        $.each(lis, function(i, li){
            var url = $(li).data("url");
            localData.push(window.focused[url]);
        });
        if (storage){
        	storage.setItem("panels[" + window.userId + "]", JSON.stringify(localData));
        }
    });
}
// 预处理
function pretreat(settings){
    
    // 初始化窗口
    createWin(settings);
    
    initLayout();
    
    initLinkButton();
    
    initData();
	
	initWinEvents();
}
// 初始化窗口布局
function initLayout(){
	layout = focusWin.find(".cc");
	layout.layout({
		fit:true
	}).layout("add", {
        region: 'center',
        style : {'padding': 0},
        content: ''
    }).layout("add", {
        region: 'south',
        height: 50,
        style : {'text-align': 'center'},
        content: '<div style="padding-top:10px;"><a href="javascript:" class="all" data-icon="icon-add">全选</a>&nbsp;<a href="javascript:" class="unall" data-icon="icon-remove">反选</a>&nbsp;<a href="javascript:" class="save" data-icon="icon-save">保存</a>&nbsp;<a href="javascript:" class="close" data-icon="icon-cancel">关闭</a></div>'
    });
}
// 初始化按钮
function initLinkButton(){
	var south = layout.layout('panel', 'south');
	var as = south.find('a');
	as.each(function(i, a){
		a = $(a);
		a.linkbutton({
			width: 70,
			height: 24,
		    iconCls: a.data('icon')
		});
	});
}
function initData(){
	layout = focusWin.find(".cc");
	var center = layout.layout('panel', 'center');
	$.each(window.focusList, function(id, focus){
		var panel = $('<div></div>');
		var cbxStr = "<label title='{1}' style='display:inline-block;margin:5px;width:120px;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;color:#777;cursor:pointer;'><input id='{0}' data-name='{1}' value='{2}' type='checkbox' {3} style='margin:0 5px 0 0;vertical-align:bottom;'/>{1}</label>";
		if (!$.isEmptyObject(focus.list)){
			$.each(focus.list, function(id, data){
				var checked = '';
				if (window.focused[data.url]){
					checked = "checked='checked'";
				}
				panel.append($.validator.format(cbxStr, data.id, data.name, data.url, checked));
			});
		}
		center.append(panel);
		panel.panel({
			title: focus.name,
			width: "100%",
			style: {"padding": "5px"},
		    tools:[{
		        iconCls:'icon-checkmark',
		        handler:function(){
		    		var cbxs = panel.find(":checkbox");
		    		$.each(cbxs, function(i, cbx){
		    			$(cbx).prop("checked", true);
		    		});
		        }
		    },{
		        iconCls:'icon-checkmark-cancel',
		        handler:function(){
		    		var cbxs = panel.find(":checkbox");
		    		$.each(cbxs, function(i, cbx){
		    			$(cbx).prop("checked", false);
		    		});
		        }
		    }]
		});
		panels.push(panel);
		categoryDict = $.extend({}, categoryDict, focus.list);
	});
	focusWin.window('restore');
}
function initWinEvents(){
	var center = layout.layout('panel', 'center'),
		south = layout.layout('panel', 'south');

	var all = south.find('.all');
	all.click(function(){
		var cbxs = focusWin.find(":checkbox");
		$.each(cbxs, function(i, cbx){
			$(cbx).prop("checked", true);
		});
	});
	
	var unall = south.find('.unall');
	unall.click(function(){
		var cbxs = focusWin.find(":checkbox");
		$.each(cbxs, function(i, cbx){
			$(cbx).prop("checked", !$(cbx).prop("checked"));
		});
	});
	
	var save = south.find('.save');
	save.click(function(){
		var url = "/admin/index_navigator_item!save.action?";
		var _this = $(this);
		if (_this.hasClass('disabled')) return false;
		_this.addClass('disabled').linkbutton({iconCls: 'icon-loading'});
		var cbxs = center.find(':checkbox:checked');
		var params = '';
		$.each(cbxs, function(i, cbx){
			params += (i ? '&' : '') + 'itemArray=' + '{"id":"' + $(cbx).attr("id") + '","url":"' + $(cbx).val() + '","name":"' + $(cbx).data("name") + '"}';
		});
		$.post(url, params, function(resp){
			if (resp.status == 'success'){
				//if (resp.result){
					var addData = [];
					localData = [];
					
					$.each(resp.result, function(i, p){
						if (!window.focused[p.url]){
							p.listMaxCount = 10;
							addData.push(p);
							addPanel(p);
						}else{
							localData.push(window.focused[p.url]);
						}
					});
					localData = localData.concat(addData);
					window.focused = {};
					$.each(localData, function(i, p){
						window.focused[p.url] = p;
					});
					var lis = sortable.find('li');
					$.each(lis, function(i, li){
						var url = $(li).data("url");
						if (!window.focused[url]){
							$(li).remove();
						}
					});
					if (storage){
						storage.setItem("panels[" + window.userId + "]", JSON.stringify(localData));
					}
				//}
				msg.alert('保存关注', '保存关注成功！', 'info', function(){
					focusWin.window("destroy");
				});
			}else{
				msg.alert('保存关注', '保存关注发生错误：' + resp.message, 'error');
			}
			_this.removeClass('disabled').linkbutton({iconCls: 'icon-save'});
		}, 'json').error(function(resp){
			msg.alert('保存信息', '保存信息发生错误！', 'error');
			_this.removeClass('disabled').linkbutton({iconCls: 'icon-save'});
		});
	});
	
	var close = south.find('.close');
	close.click(function(){
		focusWin.window("destroy");
	});
}
function reSizePanel(w){
	$.each(panels, function(i, p){
		$(p).panel('resize', {width: w});
	});
}
// 创建窗口
function createWin(options){
    var defaultConfig = {
        title: "&nbsp;",
        width: $(window).width() - 500,
        height: $(window).height() - 200,
        collapsible:false,
        minimizable:false,
        resizable:false,
        closed:true,
        modal:true,
        onClose:function(){
        	panels = [];
        	layout = '';
        	focusWin.window("destroy");
        },
        onMove:function(left, top){
            if (top < 0)
            	focusWin.window("move", {left:left, top:0});
        },
        onOpen:function(){
        	var w = $(focusWin.window('body')).innerWidth();
        	if (panels.length > 0 && layout){
            	reSizePanel(w -21);
        	}
        },
        onMaximize:function(){
        	var w = $(focusWin.window('body')).innerWidth();
        	if (panels.length > 0 && layout){
            	reSizePanel(w -20);
        	}
        },
        onRestore:function(){
        	var w = $(focusWin.window('body')).innerWidth();
        	if (panels.length > 0 && layout){
            	reSizePanel(w -20);
        	}
        }
    };
    $.extend(defaultConfig, options);
    
    var ezWin = window.top.$('<div class="ez-window" id="focusWindow"><div class="cc"></div></div>');
    
    focusWin = ezWin.window(defaultConfig);
}
$(function() {
	sortable = $('.sortable');
	initPanels();
	initEvents();
});