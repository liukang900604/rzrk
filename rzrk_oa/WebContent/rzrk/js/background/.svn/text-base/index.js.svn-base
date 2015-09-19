/**
 * Created by Wei on 2014/6/24.
 */
var msg = $.messager;
var version = "0001";
function createWin(options){
    var defaultConfig = {
        title: "&nbsp;",
        width: $(window).width() - 500,
        height: $(window).height() - 200,
        collapsible:false,
        minimizable:false,
        closed:true,
        modal:true,
        onClose:function(){
            $(this).window("destroy");
        },
        onMove:function(left, top){
            if (top < 0)
                $(this).window("move", {left:left, top:0});
        }
    };
    $.extend(defaultConfig, options);
    var ezWin = window.top.$('<div class="ez-window"></div>');
    return ezWin.window(defaultConfig);
}
function createCustomWin(options, dom){
    var defaultConfig = {resizable:false, maximizable:false};
    $.extend(defaultConfig, options);
    var cWin = createWin(defaultConfig);
    var layout = window.top.$("<div class='custom-layout'></div>");
    cWin.append(layout);
    layout.layout({fit:true});
    layout.layout("add", {
        region: 'center',
        style : 'margin:10px;',
        content: dom.find(".center")[0].outerHTML
    });
    layout.layout("add", {
        region: 'south',
        height: 40,
        border: false,
        content: dom.find(".south")[0].outerHTML
    });
    var buttons = layout.find(".custom-linkbutton");
    buttons.each(function(i, button){
        var icon = $(button).data("icon");
        $(button).linkbutton({iconCls:icon});
    });
    return cWin;
}
function createIframe(src, callback) {
    if (!callback) callback = function(){};
    return $("<iframe></iframe>", {
        scrolling: "yes",
        frameborder: 0,
        style: "width:100%;height:99%;",
        src: src,
        load: callback
    });
}
// 设置菜单块高度，覆盖默认填充
function setMenuHeight(){
    var panels = $(".west .panel");
    panels.find(".panel-body").css("height","auto");
    panels.eq(0).find(".panel-tool a").removeClass("accordion-expand");
}
//添加Tab
function createTab(options){
    var iframe = '<iframe scrolling="yes" frameborder="0"  src="{0}" style="width:100%;height:99%;"></iframe>';
    var tabs = $(".easyui-tabs");
    tabs.tabs("add", {
        "id": options.id,
        "title": options.title,
        "selected": true,
        "closable": true,
        "content": jQuery.validator.format(iframe, options.url)
    });
}
// 关闭Tab
function closeFirstTab(){
    var tabs = $(".easyui-tabs");
	var panels = tabs.tabs('tabs');
	if (panels.length == 8){
		var opts = $(panels[0]).panel("options");
	    tabs.tabs("close", opts.title);
	}
}
//添加或选中Tab
function createOrSelectTab(options){
    var tabs = $(".easyui-tabs");
    if (tabs.tabs("getTab", options.title)){
        tabs.tabs("select", options.title);
    }else{
    	closeFirstTab();
        createTab(options);
    }
}
// 重新加载Tab内容（刷新iframe）
function reloadTabContent(tabTitle){
    var tabs = $(".easyui-tabs");
    var tab = tabs.tabs("getTab", tabTitle);
    if (tab != null){
        var iframe = tab.find("iframe");
        iframe.attr("src", iframe.attr("src"));
    }
}

//关闭标签页
function  closeTab(tabTitle){
	var tabs = $(".easyui-tabs");
	var tab = tabs.tabs("getTab", tabTitle);
	var index = tabs.tabs("getTabIndex", tab);
	if(index ==undefined || index==="" || index==null){
		return false;
	}else{
		tabs.tabs("close",  index);
	}
	
	 
	
}

/**
 * 通过窗口id 给窗口内容赋值
 * @param winTitle
 * @param content
 * @param dgId
 */
function insertDateByWindow(winTitle,content,dgId){
    var iframe = $("#" + winTitle);
	var jQuery = iframe.get(0).contentWindow.$;
	var dg = jQuery(dgId);
	dg.val(content);
}

/**
 * 通过标签给指定的ID赋值
 * @param tabTitle
 * @param content
 * @param dgId
 */
function insertDateByTab(tabTitle,content,dgId){
	    var tabs = $(".easyui-tabs");
		var tab = tabs.tabs("getTab", tabTitle);
	    var iframe = tab.find("iframe");
	    var jQuery = iframe.get(0).contentWindow.$;
		var dg = jQuery(dgId);
		dg.val(content);
	    
}

function reloadDataGridInTab(tabTitle, dgId){
    var id = '#table';
    var tabs = $(".easyui-tabs");
    var tabsPanels = $(".tabs-panels > .panel");
    var tab = tabs.tabs("getTab", tabTitle);
    var index = tabs.tabs("getTabIndex", tab);
    var iframe = tabsPanels.eq(index).find("iframe");
    id = dgId ? dgId : id;
	var jQuery = iframe.get(0).contentWindow.$;
	var dg = jQuery(id);
	dg.datagrid('reload');
}
function reloadDataGridInTabTab(parentTabTitle, tabTitle, dgId){
    var id = '#table';
    var pTabs = $(".easyui-tabs");
    var pTabsPanels = $(".tabs-panels > .panel");
    var pTab = pTabs.tabs("getTab", parentTabTitle);
    var index = pTabs.tabs("getTabIndex", pTab);
    var iframe = pTabsPanels.eq(index).find("iframe");
	var jQuery = iframe.get(0).contentWindow.$;
    var tabs = jQuery(".easyui-tabs");
    var tabsPanels = jQuery(".tabs-panels > .panel");
    var tab = tabs.tabs("getTab", tabTitle);
    index = tabs.tabs("getTabIndex", tab);
    iframe = tabsPanels.eq(index).find("iframe");
    id != dgId ? dgId : id;
	jQuery = iframe.get(0).contentWindow.$;
	var dg = jQuery(id);
	dg.datagrid('reload');
}
//added by huanghui ; 2015/8/5
function reloadDataGridInTabIframe(tabTitle,iframeId){
	var id = '#table';
    var pTabs = $(".easyui-tabs");
    var pTabsPanels = $(".tabs-panels > .panel");
    var pTab = pTabs.tabs("getTab", tabTitle);
    var index = pTabs.tabs("getTabIndex", pTab);
    var iframe = pTabsPanels.eq(index).find("iframe");
    var jQuery = iframe.get(0).contentWindow.$;
    iframe = jQuery(iframeId);
    jQuery = iframe.get(0).contentWindow.$;
    var dg = jQuery(id);
    dg.datagrid('reload');
}
function reloadDataGridInWindow(winTitle, dgId){
    var iframe = $("#" + winTitle);
    var id = '#table';
    id = dgId ? dgId : id;
    var  iframeVal = iframe.get(0); 
    if(iframeVal != null){
        var jQuery = iframeVal.contentWindow.$;
        var dg = jQuery(id);
        dg.datagrid('reload');
    }
	
}
function reloadDataGridInWindowTab(winTitle, tabTitle, dgId){
    var id = '#table';
    var iframe = $("#" + winTitle);
	var jQuery = iframe.get(0).contentWindow.$;
    var tabs = jQuery(".easyui-tabs");
    var tabsPanels = jQuery(".tabs-panels > .panel");
    var tab = tabs.tabs("getTab", tabTitle);
    var index = tabs.tabs("getTabIndex", tab);
    iframe = tabsPanels.eq(index).find("iframe");
    id != dgId ? dgId : id;
	jQuery = iframe.get(0).contentWindow.$;
	var dg = jQuery(id);
	dg.datagrid('reload');
}
function reloadIframeInWindow(winTitle){
    var iframe = $("#" + winTitle);
    iframe.attr("src", iframe.attr("src"));
}

//通过标签关闭窗口
function closeIfameWindowById(winTitle){
	  var cWin = $("#" + winTitle);
	  cWin.window("destroy");
}

// 关闭当前打开的模式窗口
function closeCurrentWindow(){
    var cWin = $(".ez-window:last");
    cWin.window("destroy");
}

// 更新一级分类
function updateCategory(id){
	var url = "/admin/page!getMenuList.action";
	var menu = $(".main-menu");
	var ul = menu.find("ul:eq(0)");
	var li = "<li id='{0}'><a href='javascript:'><i class='icon i-14'></i>{1}<i class='icon arrows'></i></a></li>";
	var isEnd = false;
	$.get(url, function(data){
		menu.mCustomScrollbar("destroy");
		if (id){
			$.each(data, function(i, category){
				if (id != category.rootId) return;
				ul.find("#" + id + " > a").text(category.rootName);
			});
		}else{
			$.each(data, function(i, category){
				if (ul.find("#" + category.rootId).size() > 0) return;
				ul.append($.validator.format(li, category.rootId, category.rootName));
			});
			initMenuScroll();
		}
	}, "json").error(function(){
		$.messager.alert("更新提示", "主菜单更新失败，请手动刷新页面", "error");
	});
}

// 删除一级分类
function deleteCategory(){
	var url = "/admin/page!getMenuList.action";
	var menu = $(".main-menu");
	var ul = menu.find("ul:eq(0)");
	var lis = ul.find("> li");
	$.get(url, function(data){
		var ids = {};
		for (var i = 0; i < data.length; i++){
			ids[data[i].rootId] = true;
		}
		menu.mCustomScrollbar("destroy");
		$.each(lis, function(i, li){
			var id = $(li).attr("id");
			if (id && !ids[id]){
				$(li).remove();
			}
		});
		initMenuScroll();
	}, "json").error(function(){
		$.messager.alert("更新提示", "主菜单更新失败，请手动刷新页面", "error");
	});
}

// 更新二级分类
function updateSubCategory(){
	var url = "/admin/page!getMenuList.action";
	var menu = $(".main-menu");
	var ul = menu.find("ul:eq(0)");
	var liStr = '<li id="{0}"><a href="javascript:" data-src="{1}"><i class="icon i-14"></i>{2}</a></li>';
	$.get(url, function(data){
		menu.mCustomScrollbar("destroy");
		$.each(data, function(i, category){
			var li = ul.find("#" + category.rootId);
			var subMenu = li.find(".sub-menu");
			if (subMenu.size() == 0){
				subMenu = "<ul class='sub-menu' style='display: block;'></ul>";
				li.append(subMenu);
			}
			subMenu.empty();
			$.each(category.child, function(i, sub){
				subMenu.append($.validator.format(liStr, sub.id, sub.url, sub.name));
			});
		});
		initMenuScroll();
	}, "json").error(function(){
		$.messager.alert("更新提示", "主菜单更新失败，请手动刷新页面", "error");
	});
}

function initPosClockOnWin(){
	var win = $('#posClockOn').window({
		title:"定位打卡",
		iconCls:'',
		collapsible:false,
		minimizable:false,
		maximizable:false,
		resizable:false,
		modal:true,
		closed:true,
		width: 270,
		height: 165,
		onClose: function(){
			$(this).find('.location').show();
			$(this).find('.clockOn').hide();
			$(this).find('#latitude').val('');
			$(this).find('#longitude').val('');
		}
	});
	return win;
}
// 初始化事件
function initEvents(){
	initMenuScroll();
    $(".nav").on("click", ".mCSB_container > ul > li > a", function(){
        var _self = $(this);
        var menu = $(".expand");
        if (menu[0] == _self[0] && _self.hasClass("expand")){
            var subMenu = _self.next();
            subMenu.slideUp(400, function(){
            	_self.removeClass("expand");
            });
        }else{
            _self.addClass("expand");
            _self.next().slideDown(400);
            menu.removeClass("expand");
            menu.next().slideUp(400, function(){
            	menu.removeClass("expand");
            });
        }
    });
    // 点击子菜单打开Tab
    $(".nav").on("click", ".sub-menu > li > a", function(){
        var _self = $(this);
        var tabs = $(".easyui-tabs");
        var options = {
            //"id": _self.data("id"),
            "url": _self.data("src"),
            "title": _self.text()
        };
        createOrSelectTab(options);
        /*if (tabs.tabs("getTab", options.title)){
            tabs.tabs("select", options.title);
        }else{
            createTab(options);
        }*/
        return false;
    });


    // 双击对话框标题最大化
    $("body").on("dblclick", ".panel-title", function(e){
        var _self = $(this);
        var ezWin = _self.parents(".window").find(".ez-window");
        var status = _self.data("status");
        if (status){
            _self.data("status", false);
            ezWin.window("restore");
        }else{
            _self.data("status", true);
            ezWin.window("maximize");
        }
    });
    
    $(".tabs").on("dblclick", "li", function(){ // 双击关闭Tab页
        var tabs = $(".easyui-tabs");
        var selectedTab = tabs.tabs('getSelected');
        var index = tabs.tabs('getTabIndex',selectedTab);
        tabs.tabs("close", index != 0 ? index : "");
    });

    // 按ESC关闭窗口
    $(document).bind('keydown', 'esc', function(){
        var activeWin = window.top.$(".ez-window:last");
        activeWin.window("destroy");
    });

    // 登出
    $(".logout").click(function(){
        var url = "/admin/logout";
        window.location.replace(url);
    });

    // 更换主题
    $(".changeThemes").combobox({
        onSelect: function(param){
            var url = "/media/libs/jquery-easyui-1.3.6/themes/{0}/easyui.css";
            var oldLink = $("#themes");
            var head = $("head");
            var link = $("<link>", {
                "rel" : "stylesheet",
                "id"  : "themes",
                "href": jQuery.validator.format(url, param.value),
                "load": function(){
                    oldLink.remove();
                }
            });
            link.insertAfter(head);
        }
    });
    
    var posClockOn = initPosClockOnWin();
    // 定位打卡窗口
    $('.clock-on').click(function(){
    	posClockOn.window('open');
    	return false;
    });
    // 定位
    $('.location').click(function(){
    	var _this = $(this),
    		clockOn = $('.clockOn'),
    		latitude = $('#latitude'),
    		longitude = $('#longitude');
    	_this.linkbutton({iconCls: 'icon-loading'});
    	
        navigator.geolocation.getCurrentPosition(function(pos){
        	latitude.val(pos.coords.latitude);
        	longitude.val(pos.coords.longitude);
        	_this.linkbutton({iconCls: 'icon-geolocation'});
        	_this.hide();
        	clockOn.show();
        	/*
            console.log(
                '  经度：' + pos.coords.latitude +
                '  纬度：' + pos.coords.longitude +
                '  高度：' + pos.coords.altitude +
                '  精确度(经纬)：' + pos.coords.accuracy +
                '  精确度(高度)：' + pos.coords.altitudeAccuracy +
                '  速度：' + pos.coords.speed
            );
            */
            }, function(err){ // 如果失败则执行该回调函数
            	_this.linkbutton({iconCls: 'icon-geolocation'});
            	msg.alert('定位失败', '请确认有共享位置信息，再次重试。', 'error');
            }, {
                enableHighAccuracy: true, // 提高精度(耗费资源)
                timeout: 3000, // 超过timeout则调用失败的回调函数
                maximumAge: 1000 // 获取到的地理信息的有效期，超过有效期则重新获取一次位置信息
            }
        );
    	return false;
    });
    // 打卡提交
    $('.clockOn').click(function(){
    	var _this = $(this),
	    	url = "/admin/checkin.action",
	    	clockOnTime = $('.clock-on-time'),
	    	location = posClockOn.find(".location"),
	    	latitude = posClockOn.find("#latitude"),
	    	longitude = posClockOn.find("#longitude");
    	var params = {
			x: posClockOn.find("#latitude").val(),
			y: posClockOn.find("#longitude").val()
    	};
    	$.post(url, params, function(resp){
    		alert(resp + " | " + JSON.stringify(resp));
    		if (resp.status == 'success'){
    			msg.alert('打卡提交', '打卡提交成功！', 'info');
    			clockOnTime.text(resp.lastCheckintime);
    			latitude.val('');
    			longitude.val('');
    			_this.hide();
    			location.show();
    		}else{
    			msg.alert('打卡提交', '打卡提交失败：' + resp.message, 'error');
    		}
    	}, "json");
    	return false;
    });
    // 系统信息
    var span = $("header span.sysmsg");
	$('.sysmsg').unbind('click').click(function(){
		var url = '/admin/systemmessage!show.action';
	    var ezWin = window.top.createWin({
	    	title: "查看消息",
	    	resizable: true,
	    	maximizable: true
		});
	    var iframe = window.top.createIframe(url);
	    iframe.attr("id","backframe");
	    iframe.appendTo(ezWin);
	    ezWin.window("open");
	});
	setInterval(function(){
		$.get('/admin/page!getUnreadMessageCount.action', function(count){
			count = parseInt(count);
			if (!isNaN(count)){
				if (count > 0){
					span.text(count > 99 ? 99 : count).show();
				}else{
					span.text(0).hide();
				}
			}else{
				span.text(0).hide();
			}
		}).error(function(){
			span.text(0).hide();
		});
	}, 30000);
	// 主菜单可横向拖动扩大
    $(".overlay").draggable({
    	axis: "h",
    	cursor: "e-resize",
    	onStartDrag:function(e){
    		$(this).css({"width": "10000px"});
    	},
    	onDrag:function(e){
    		var left = $(this).offset().left + 3;
			$(".container").css({'left': (left + 1) + "px"});
			$(".nav, .nav nav").css({'width':  left + "px"});
    	},
    	onStopDrag:function(e){
    		$(this).css({"width": "10px"});
			$(".container").resize();
    	}
    });
}
function initMenuScroll(){
    var menu = $(".main-menu");
    if (menu.size() > 0){
        menu.mCustomScrollbar({
            theme: 'minimal',
            setWidth: '156px',
            scrollInertia: 0
        });
    }
}
$(function(){
    initEvents();
});