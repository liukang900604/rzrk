(function($) {
	var option = {
		searchByData : [],
		preData : [],
		checkbox : null
	};
	var $this = null;
	function get$root() {
		return $('<div class="qtreeRoot"></div>');
	}
	function get$node(rel) {
		var nodestr = null;
		if(rel=="or"){
			nodestr ='<div class="qnode"><div class="qrelation qrelation1" data-type="or"><span><span class="qfontor">或者</span></span><a href="javascript:void(0);">[转]</a></div></div>';
		}else{
			nodestr ='<div class="qnode"><div class="qrelation qrelation1" data-type="and"><span><span class="qfontand">并且</span></span><a href="javascript:void(0);">[转]</a></div></div>';
		}
		var $node = $(nodestr);
		$node.find(".qrelation>a").bind("click",function(){
			var _$qrelation = $(this).closest(".qrelation");
			if(_$qrelation.data("type")=="and"){
				_$qrelation.data("type","or");
				_$qrelation.children("span").html('<span class="qfontor">或者</span>');
			}else{
				_$qrelation.data("type","and");
				_$qrelation.children("span").html('<span class="qfontand">并且</span>');
			}
		});
		return $node;
	}
	
	function recdel($node){
		var $pnode = $node.parent(".qnode");
		if($pnode.children(".qnode").length>1){ //含有兄弟节点
			$node.remove();
			refreshRelationClass($pnode);
		}else if($pnode.length>0){ //含有父节点
			recdel($pnode);
		}else{
			$node.children(".qnode").remove();
			refreshRelationClass($node);
			if(option.checkbox!=null && option.checkbox.length > 1){
				$(option.checkbox).prop("checked",false);
			}else{
				$("#queryCondShow").prop("checked",false);
			}
		}
	}
	
	function get$cond() {
		var condstr = '<div class="qcond">'
		+ '<select class="qsearchBy" style="width:80px;" ></select>'
		+ '<select class="qoperate easyui-combobox" style="width:80px;" ><option value="eq">等于</option><option value="ne">不等于</option><option value="has">包含</option><option value="nohas">不包含</option></select>'
		+ '<input class="qkeyword easyui-searchbox" style="width:300px"></input>'
		+ '<span><a class="qdel" href="javascript:void(0);">[删除]</a></span>'
		+ '<span><a class="qadd" href="javascript:void(0);">[增加]</a></span>'
		+ '<span><a class="qaddg" href="javascript:void(0);">[增加组]</a></span>'
		+ '</div>'
		var $cond = $(condstr);
		$cond.find(".qsearchBy").combobox({
			textField : 'label',
			valueField : 'value',
			data : option.searchByData,
		}).combobox("select", option.searchByData[0].value);
		$cond.find(".qoperate").combobox({});
		$cond.find(".qdel").bind("click", function() {
			var _$qnode = $(this).closest(".qnode");
			recdel(_$qnode);
		    $(window).trigger("resize");
		});
		$cond.find(".qadd").bind("click", function() {
			var $condNode = get$condNode();
			$(this).closest(".qnode").after($condNode);
			refreshRelationClass($(this).closest(".qnode").parent(".qnode"));
		    $(window).trigger("resize");
		});
		$cond.find(".qaddg").bind("click", function() {
			var $nodeg = get$node();
			var $node = get$node();
			var $condNode = get$condNode();
			$node.append($condNode);
			$nodeg.append($node);
			$(this).closest(".qnode").after($nodeg);
			refreshRelationClass($(this).closest(".qnode").parent(".qnode"));
		    $(window).trigger("resize");
		});

		return $cond;
	}
	function get$condNode(rel) {
		var $node = get$node(rel);
		var $cond = get$cond(rel);
		$node.append($cond);
		return $node;
	}
	function $condNode($cond) {
		var $node = $cond.closest(".qnode");
		return $node;
	}
	function isCondNode($node) {
		if($node.children(".qcond").length==0){
			return false;
		}else{
			return true;
		}
	}
	function refreshRelationClass($node){
		$node.children(".qnode").children(".qrelation").removeClass("qrelation1");
		$node.children(".qnode").eq(0).children(".qrelation").addClass("qrelation1");
	}

	function setCondRelation($node,relation){
		var _$qrelation = $node.children(".qrelation");
		if(_$qrelation.data("type")=="and"){
			_$qrelation.data("type","or");
			_$qrelation.find("span").text("或者");
		}else{
			_$qrelation.data("type","and");
			_$qrelation.find("span").text("并且");
		}
	}
	function getCondRelation($node){
		return $node.children(".qrelation").data("type");
	}
	
	function setCondSearchBy($cond,searchBy){
		$cond.children(".qsearchBy").combobox("select",searchBy);
	}
	function getCondSearchBy($cond){
		return $cond.children(".qsearchBy").combobox("getValue");
	}
	function setCondOperate($cond,operate){
		$cond.children(".qoperate").combobox("select",operate);
	}
	function getCondOperate($cond){
		return $cond.children(".qoperate").combobox("getValue");
	}
	function setCondKeyword($cond,keyword){
		$cond.children(".qkeyword").val(keyword);
	}
	function getCondKeyword($cond){
		return $cond.children(".qkeyword").val();
	}
	function assembleNode($qnode,node){
		var $nodes = $qnode.children(".qnode");
		$nodes.each(function(){
			var $node = $(this);
			var relation = getCondRelation($node);
			var item = {
					"relation":relation,
					"children":[],
				};
			node.children.push(item);
			if(!isCondNode($node)){
				assembleNode($node,item);
			}else{
				var $cond = $node.children(".qcond")
				var relation = getCondRelation($cond);
				var searchBy = getCondSearchBy($cond);
				var operate = getCondOperate($cond);
				var keyword = getCondKeyword($cond);
				if(keyword=="" || keyword==null){
					throw $cond;
				}
				var cond = {
					"searchBy":searchBy,
					"operate":operate,
					"keyword":keyword
				};
				item["cond"]=cond;
			}
		});
	}
	
	function assembleDom($qnode,node){
		var $node = get$node(node.relation);
		$qnode.append($node);
		if(node["cond"] != null){
			var item = node["cond"];
			var $item = get$cond(item.relation);
			$node.append($item);
			setCondSearchBy($item,item.searchBy);
			setCondOperate($item,item.operate);
			setCondKeyword($item,item.keyword);
		}else{
			for(var index in node.children){
				var cnode = node.children[index];
				assembleDom($node,cnode);
			}
		}
		refreshRelationClass($qnode);
	}
	
	
	
	
	$.fn.queryTree = function(arg) {
		if (arg == "") {

		}else if (arg == "getValue") {
			var $root = $this.find(".qtreeRoot");
			var node = {
				relation:"and",
				children:[]
			};
			assembleNode($root.children(".qnode"),node);
			return node;
		} else if (arg == "show") {
			if($this.find(".qcond:first").length==0){
				var $node = get$condNode();
				$(this).find(".qnode:first").append($node);
			};
		}else if (arg == "remove") {
			$(this).find(".qnode:first").empty();
		} else if ($.isPlainObject(arg)) {
			$.extend(option, arg);
			$this = $(this);
			var $root = get$root();
			$this.empty().append($root);
			if ($.isEmptyObject(option.preData)) {
				var $nodeg = get$node();
//				var $node = get$node();
				$root.append($nodeg);
//				$nodeg.append($node);
//				$node.append(get$cond());
			}else{
				var node = option.preData;
				assembleDom($root,node);
			    $(window).trigger("resize");
			}

		}
	};
})(jQuery);
