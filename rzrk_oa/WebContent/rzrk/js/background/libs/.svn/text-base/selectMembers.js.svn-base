/**
 * 
 * @Plugin: 选择员工插件
 * @Author: MuWei
 * @Date: 2015.07.07
 * 
 */
(function($, undefined) {

	// 最顶层父级的消息对象
	var msg = window.top.$.messager;
	
	// 窗口引用
	var treeWin = null;
	
	// 布局引用
	var layout = null;
	var layout2 = null;
	
	// dom
	var dom = {
		'west': '<div class="department"></div>',
		'center': '<div class="cc2"></div>',
		'east': '<table class="selectedList" onselectstart="return false"><thead><tr><th>已选成员<a href="javascript:" class="clearSelected">[清空]</a></th></tr></thead><tbody></tbody></table>',
		'south': '<div style="padding-top:10px;"><a href="javascript:" class="all" data-icon="icon-add">全选</a>&nbsp;<a href="javascript:" class="save" data-icon="icon-save">确定</a>&nbsp;<a href="javascript:" class="close" data-icon="icon-cancel">关闭</a></div>'
	};
	
	var dom2 = {
		'north': '<div style="padding-top:10px;">姓名&nbsp;<input type="text" class="searchText" placeholder="支持模糊及首字母查询"/>&nbsp;<button class="searchBtn">查询</button></div>',
		'center': '<table class="memberList" onselectstart="return false"><thead><tr><th style="border-left:0;width:40%;">部门</th><th style="width:30%;">成员</th><th style="border-right:0;width:30%;">操作</th></tr></thead><tbody></tbody></table>'
	}
	
	var dptTree = null;
	
	// 部门索引字典（根据部门ID索引部门信息）
	var dptDict = {};
	
	// 成员索引字典（根据成员ID索引成员信息）
	var mbrDict = {};
	
	// 成员索引字典（根据部门ID索引部门成员）
	var mbrInDptDict = {};
	
	// 成员列表引用（Table）
	var mbrList = null;
	
	// 被选中成员列表引用（Table）
	var setdList = null;
	
	// 是否被选过
	var unique = {};
	
	// 默认配置
    var defaults = {
		'title': '选择成员',
    	'width': 700,
    	'height': 500,
    }
    
    // 成员姓名首字母索引
    var letterIdx = {};
    
    // 当前搜索文本
	var searchText = '';
    
    // 是否单选
    var single = false;
    
    var filterMbrs = {};
    
    // 隐藏域名字
    var hiddenName = '';
    
    var selectedNodeId = "";
    
    var els = [];
    
    var el = null;
    
    // 数据是否加载完成
    var isLoaded = false;
    
    var data = null;
    
    var loadingIcon = $('<img style="vertical-align:sub;margin-left:5px;" src="data:undefined;base64,R0lGODlhEAAQAPYAAOfn5xhFjMPL15CiwGWBrkttok5vo3GLs5urxcvR2p2txjRbmDhemT5inENnn0psoW2Isa+7zi5WlXSNtNfa39nc4LXA0YecvFh3p2SArbK9z8HJ1kZpoClTk4mdvaGwyGJ/rHyTt8/U3ISZuyJNkGyGsJanw2qFr6u4zFBwpCBLj6e1ypGkwSpTkxxIjdTX3t3f4nmRtoOZu9/h44GXuqCvx+Pk5eXl5rO+0LvF0+Hi5MXM2KWzytvd4cLJ1tHW3czR2r/I1bnD0rC7zs3T28fO2N3f4snP2XqRtqm3y6i1ylV1p1p4qGB9q2eDrk1vo0hqoLfB0XePtUBkndXZ3zpfmoufvl99qzthmzBXlpmqxFZ1pyZQkoabvGiDrkJlnrrD0r3G1NPX3q26zX6UuI6hv5ipw117qoyfvlRzplJypTJZl56txiROkSBLj6OyyRpGjJWnwzZcmShRkkRnn3aOtTxhmx5JjnKLszFZl1x6qW+Jsn+WuQAAAAAAAAAAACH/C05FVFNDQVBFMi4wAwEAAAAh/hpDcmVhdGVkIHdpdGggYWpheGxvYWQuaW5mbwAh+QQJCgAAACwAAAAAEAAQAAAHjYAAgoOEhYUbIykthoUIHCQqLoI2OjeFCgsdJSsvgjcwPTaDAgYSHoY2FBSWAAMLE4wAPT89ggQMEbEzQD+CBQ0UsQA7RYIGDhWxN0E+ggcPFrEUQjuCCAYXsT5DRIIJEBgfhjsrFkaDERkgJhswMwk4CDzdhBohJwcxNB4sPAmMIlCwkOGhRo5gwhIGAgAh+QQJCgAAACwAAAAAEAAQAAAHjIAAgoOEhYU7A1dYDFtdG4YAPBhVC1ktXCRfJoVKT1NIERRUSl4qXIRHBFCbhTKFCgYjkII3g0hLUbMAOjaCBEw9ukZGgidNxLMUFYIXTkGzOmLLAEkQCLNUQMEAPxdSGoYvAkS9gjkyNEkJOjovRWAb04NBJlYsWh9KQ2FUkFQ5SWqsEJIAhq6DAAIBACH5BAkKAAAALAAAAAAQABAAAAeJgACCg4SFhQkKE2kGXiwChgBDB0sGDw4NDGpshTheZ2hRFRVDUmsMCIMiZE48hmgtUBuCYxBmkAAQbV2CLBM+t0puaoIySDC3VC4tgh40M7eFNRdH0IRgZUO3NjqDFB9mv4U6Pc+DRzUfQVQ3NzAULxU2hUBDKENCQTtAL9yGRgkbcvggEq9atUAAIfkECQoAAAAsAAAAABAAEAAAB4+AAIKDhIWFPygeEE4hbEeGADkXBycZZ1tqTkqFQSNIbBtGPUJdD088g1QmMjiGZl9MO4I5ViiQAEgMA4JKLAm3EWtXgmxmOrcUElWCb2zHkFQdcoIWPGK3Sm1LgkcoPrdOKiOCRmA4IpBwDUGDL2A5IjCCN/QAcYUURQIJIlQ9MzZu6aAgRgwFGAFvKRwUCAAh+QQJCgAAACwAAAAAEAAQAAAHjIAAgoOEhYUUYW9lHiYRP4YACStxZRc0SBMyFoVEPAoWQDMzAgolEBqDRjg8O4ZKIBNAgkBjG5AAZVtsgj44VLdCanWCYUI3txUPS7xBx5AVDgazAjC3Q3ZeghUJv5B1cgOCNmI/1YUeWSkCgzNUFDODKydzCwqFNkYwOoIubnQIt244MzDC1q2DggIBACH5BAkKAAAALAAAAAAQABAAAAeJgACCg4SFhTBAOSgrEUEUhgBUQThjSh8IcQo+hRUbYEdUNjoiGlZWQYM2QD4vhkI0ZWKCPQmtkG9SEYJURDOQAD4HaLuyv0ZeB4IVj8ZNJ4IwRje/QkxkgjYz05BdamyDN9uFJg9OR4YEK1RUYzFTT0qGdnduXC1Zchg8kEEjaQsMzpTZ8avgoEAAIfkECQoAAAAsAAAAABAAEAAAB4iAAIKDhIWFNz0/Oz47IjCGADpURAkCQUI4USKFNhUvFTMANxU7KElAhDA9OoZHH0oVgjczrJBRZkGyNpCCRCw8vIUzHmXBhDM0HoIGLsCQAjEmgjIqXrxaBxGCGw5cF4Y8TnybglprLXhjFBUWVnpeOIUIT3lydg4PantDz2UZDwYOIEhgzFggACH5BAkKAAAALAAAAAAQABAAAAeLgACCg4SFhjc6RhUVRjaGgzYzRhRiREQ9hSaGOhRFOxSDQQ0uj1RBPjOCIypOjwAJFkSCSyQrrhRDOYILXFSuNkpjggwtvo86H7YAZ1korkRaEYJlC3WuESxBggJLWHGGFhcIxgBvUHQyUT1GQWwhFxuFKyBPakxNXgceYY9HCDEZTlxA8cOVwUGBAAA7AAAAAAAAAAAA"/>');
    
    function initData(){

    	els.each(function(i, el){
        	$(el).after(loadingIcon);
    	});
    	
    	$.ajax({
    		url: "/admin/deparment!getDeparmentPersondData.action",
    		dataType: "json",
    		timeout: 600000,
    		success: function(r){
    			
    	    	els.each(function(i, el){
    	        	$(el).next().remove();
    	    	});
    	    	
        		isLoaded = true;
        		
        		data = r;
        		
        		initDptDict(data.deparmentList);
        		
        		mbrInDptDict = data.deparmentAdminList;
        		
        		var roots = [];
        		$.each(data.deparmentList, function(i, root){
        			roots.push(root.id);
        		});

        		var mbrs = [];
        		$.each(roots, function(i, id){
        			mbrs = mbrs.concat(data.deparmentAdminList[id]);
        		});
        		
        		/*
        		$.each(data.deparmentAdminList, function(id, list){
        			mbrs = mbrs.length <= list.length ? list : mbrs;
        		});
        		*/
        		
        		$.each(mbrs, function(id, mbr){
        			mbrDict[mbr.id] = mbr;
        			letterIdx[mbr.name] = CTL.convert(mbr.name).toString();
        		});
    		},
    		error: function(r){
        		msg.alert("加载失败", "成员列表数据加载失败，请刷新后重试！", "info");
    		}
    	});
    	
    }
    
    // 插件样式
    function initCss(){
        var rules = "table.selectedList{width:100%;border-collapse:collapse;}"
                        + "table.selectedList thead tr{background-color:#eee;}"
                        + "table.selectedList thead th{text-align:center;height:26px;border: 1px solid #ddd;border-left:0;border-right:0;}"
                        + "table.selectedList thead th a.clearSelected{text-decoration:none;color:#0088cc;font-weight:normal;}"
                        + "table.selectedList tbody td{height:26px;border: 1px solid #ddd;padding:0 5px;}"
                        + "table.selectedList tbody td a.unselect{float:right;text-decoration:initial;color:#0088cc;}"
                        + "table.memberList{width:100%;border-collapse:collapse;}"
                        + "table.memberList thead tr{background-color:#eee;}"
                        + "table.memberList thead th, table.memberList tbody td{text-align:center;height:26px;border: 1px solid #ddd;}"
                        + "table.memberList tbody td a.select{text-decoration:none;color:#0088cc;}"
                        + "table.memberList tbody td a.unselect{text-decoration:none;color:red;}"
                        + "table.selectedList tbody tr, table.memberList tbody tr{cursor:default;}"
                        + "table.selectedList tbody tr:hover, table.memberList tbody tr:hover{background-color:#efefef;}"
                        + ".department .tree-folder{background: url('/rzrk/images/background/icons/tree_d_icons.png') no-repeat -208px 0;}"
                        + ".department .tree-folder-open{background: url('/rzrk/images/background/icons/tree_d_icons.png') no-repeat -224px 0;}"
                        + ".department .tree-file{background: url('/rzrk/images/background/icons/tree_d_icons.png') no-repeat -240px 0;}"
                        + ".department div[class*='tree-root'] span.tree-icon.tree-folder,.department div[class*='tree-root'] span.tree-icon.tree-folder.tree-folder-open{background: url('/rzrk/images/background/icons/tree_d_icons.png') no-repeat right -36px;}";
        var head = window.top.$('head');
        if (head.find('#SMCss').size() > 0) return false;
        head.append("<style id='SMCss'>"+ rules +"</style>");
    }

    // 初始化窗口布局
	function initLayout(){
		layout = treeWin.find(".cc");
		layout.layout({
			fit:true
		}).layout("add", {
	        region: 'west',
	        width: '30%',
	        content: dom.west
	    }).layout("add", {
	        region: 'center',
	        width : '50%',
	        style : {'padding': 0},
	        content: dom.center
	    }).layout("add", {
	        region: 'east',
	        width : '20%',
	        content: dom.east
	    }).layout("add", {
	        region: 'south',
	        height: 50,
	        style : {'text-align': 'center'},
	        content: dom.south
	    });
		var center = layout.layout('panel', 'center');
		layout2 = center.find(".cc2");
		layout2.layout({
			fit:true
		}).layout("add", {
	        region: 'north',
	        height: 45,
	        style : {'text-align': 'center'},
	        content: dom2.north
	    }).layout("add", {
	        region: 'center',
	        style : {'padding': 0},
	        content: dom2.center
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
	
	// 创建窗口
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
	        	unique = {};
	        	searchText = '';
	        	treeWin.window("destroy");
	        },
	        onMove:function(left, top){
	        	if(treeWin){
		            if (top < 0)
		            	treeWin.window("move", {left:left, top:0});
	        	}
	        }
	    };
	    $.extend(defaultConfig, options);
	    
	    var ezWin = window.top.$('<div class="ez-window"><div class="cc"></div></div>');
        
	    treeWin = ezWin.window(defaultConfig);
	}
	
	// 根据树结构递归部门索引
	function initDptDict(node){
		if ($.isArray(node) && node.length > 0){
			$.each(node, function(i, n){
				dptDict[n.id] = n;
				if (n.children){
					initDptDict(n.children);
				}
			});
		}
	}
	
	// 初始化事件
	function initEvents(){
		var west = layout.layout('panel', 'west'),
			center = layout.layout('panel', 'center'),
			east = layout.layout('panel', 'east'),
			south = layout.layout('panel', 'south');
		
		var searchTextInpt = center.find(".searchText");
		var searchBtn = center.find(".searchBtn");
		searchTextInpt.bind('keyup', 'return', function(){
			searchBtn.click();
		});
		searchBtn.click(function(){
			searchText = $.trim(searchTextInpt.val());
			refreshMbrs();
		});
		
		mbrList = center.find(".memberList tbody");
		mbrList.on('click', 'a.select', function(){
			var id = $(this).parents('tr').data('id');
			unique[id] = true;
			refreshSetd();
			refreshMbrs();
		}).on('click', 'a.unselect', function(){
			var id = $(this).parents('tr').data('id');
			delete unique[id];
			refreshSetd();
			refreshMbrs();
		}).on('dblclick', 'tr', function(){
			var id = $(this).data('id');
			if (unique[id]){
				delete unique[id];
			}else{
				unique[id] = true;
			}
			refreshSetd();
			refreshMbrs();
		});
		
		setdList = east.find(".selectedList tbody");
		setdList.on('click', 'a.unselect', function(){
			var id = $(this).parents('tr').data('id');
			delete unique[id];
			refreshSetd();
			refreshMbrs();
		}).on('dblclick', 'tr', function(){
			var id = $(this).data('id');
			delete unique[id];
			refreshSetd();
			refreshMbrs();
		});
		
		var clear = east.find(".selectedList thead a.clearSelected");
		clear.click(function(){
			var trs = setdList.find('tr');
			$.each(trs, function(i, tr){
				var id = $(tr).data('id');
				delete unique[id];
			});
			refreshSetd();
			refreshMbrs();
		});
		
		dptTree = west.find(".department");
		dptTree.tree({
			lines: true,
			data: data.deparmentList,
			onSelect: showMembersByDptId,
			onLoadSuccess: function(node, data){
				var nodes = dptTree.tree('getRoots');
				dptTree.tree('select', nodes[0].target);
			}
		});
		
		var all = south.find('.all');
		all.click(function(){
			$.each(filterMbrs, function(i, mbr){
				if (!unique[mbr.id]) unique[mbr.id] = true;
			});
			refreshSetd();
			refreshMbrs();
		});
		
		var save = south.find('.save');
		save.click(function(){
			var hidden = "<input type='hidden' name='{0}' value='{1}'/>", names = '';
			//var ids = [], names = '';
			
			el.siblings(':hidden').remove();
			$.each(unique, function(id){
				//ids.push(id);
				names += (!names ? '' : ', ') + mbrDict[id].name;
				el.after($.validator.format(hidden, hiddenName, id));
			});
			//el.val(names).data('ids', ids.join(','));
			el.val(names);
        	treeWin.window("destroy");
		});
		
		var close = south.find('.close');
		close.click(function(){
        	unique = {};
        	searchText = '';
        	treeWin.window("destroy");
		});
	}
	
	// 显示选中部门的成员
	function showMembersByDptId(node){
		selectedNodeId = node.id;
		searchText = '';
		refreshMbrs();
	}
	
	// 刷新选中部门的成员列表
	function refreshMbrs(){
		var tr = '<tr data-id="{0}"><td style="border-left:0;">{1}</td><td>{2}</td><td style="border-right:0;">{3}</td></tr>';
		var select = '<a href="javascript:" class="select">选择</a>';
		var unselect = '<a href="javascript:" class="unselect">取消选择</a>';
		mbrList.empty();
		filterMbrs = {};
		if (searchText){
			$.each(mbrInDptDict[selectedNodeId], function(i, mbr){
			    if (mbr.name.toLocaleLowerCase().indexOf(searchText.toLocaleLowerCase()) == -1 &&
			    		letterIdx[mbr.name].toLocaleLowerCase().indexOf(searchText.toLocaleLowerCase()) == -1){
			        return true;
			    }
				var a = unique[mbr.id] ? unselect : select;
				mbrList.append($.validator.format(tr, mbr.id, mbr.deparmentName, mbr.name, a));
				filterMbrs[mbr.id] = mbr;
			});
		}else{
			$.each(mbrInDptDict[selectedNodeId], function(i, mbr){
				var a = unique[mbr.id] ? unselect : select;
				mbrList.append($.validator.format(tr, mbr.id, mbr.deparmentName, mbr.name, a));
				filterMbrs[mbr.id] = mbr;
			});
		}
	}
	
	// 刷新已选中成员列表
	function refreshSetd(){
		var tr = '<tr data-id="{0}"><td style="border-left:0;border-right:0;"><a href="javascript:" class="unselect">[X]</a>{1}</td></tr>';
		setdList.empty();
		$.each(unique, function(id){
			var mbr = mbrDict[id];
			setdList.append($.validator.format(tr, mbr.id, mbr.name));
		});
	}
	
	// 预处理
	function pretreat(settings){
        
        // 初始化窗口
        createWin(settings);
	    
	    initLayout();
	    
	    initLinkButton();
	    
	    //initData();
		
		initEvents();
		
		refreshSetd();
	}
	
	var methods = {
		init : function(options) {
			
			els = this;
			
			// 初始化数据
			initData();
			
			// 初始化样式
			initCss();
			
			hiddenName = options.hiddenName;
			
	        // 绑定元素注册事件
			return this.each(function() {
				var _this = $(this);

		        // 覆盖默认配置的设置
				var settings = $.extend({}, defaults, options || {});
				
				_this.attr('readonly', 'readonly').click(function(e){
					
					if (!isLoaded){
						msg.alert("提示信息", "数据正在加载中，请稍后...", "info");
						return false;
					}
					
					el = $(this);
					
					// 当前已选中的成员
					//var ids = el.data('ids');
					var hs = el.siblings(':hidden');
					if (hs.length > 0){
						$.each(hs, function(i, h){
							var id = $(h).val();
							unique[id] = true;
						});
					}else{
						unique = {};
					}
					
					pretreat(settings);
					
					treeWin.window("open");
				});
			});

		},
		singleSelect: function(isSingleSelect){
			single = isSingleSelect;
		}
	};

	$.fn.selectMembers = function(method) {

		if (methods[method]) {
			return methods[method].apply(this, Array.prototype.slice.call(
					arguments, 1));
		} else if (typeof method === 'object' || !method) {
			return methods.init.apply(this, arguments);
		} else {
			$.error('Method ' + method + ' does not exist on jQuery.selectMembers');
		}
		
	};

})(jQuery);
