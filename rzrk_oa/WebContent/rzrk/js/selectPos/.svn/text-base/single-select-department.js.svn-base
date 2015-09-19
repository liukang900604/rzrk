(function($){
	var content='#contentDepartment';
	var source={};
	var title="部门选择";
	var hidden_name;
	
	var currTreeNode=null;
	
	function convert(jsonobj){
		var result = {};
		for ( var p in jsonobj){
			if (p != 'root'){
				result[p] = jsonobj[p];
			}
		}
		result.root = [];
		var root = jsonobj.root;
		for ( var i = 0; i < root.length; i++){
			var ri = root[i];
			ri.text = ri.name;
			delete ri.name;				 
			if (ri.parent != null && ri.parent != 'null' && ri.parent != ''){
				for ( var j = 0; j < root.length; j++){
					var rj = root[j];
					if (rj.id == ri.parent){
						rj.children = !rj.children ? [] : rj.children;
						rj.children.push (ri);
						break;
					}
				}
			}				 
			if (ri.parent == null || ri.parent == 'null' || ri.parent == ''){
				result.root.push (ri);
			}
		}
		 
		for(var i in result.root){
			if(result.root[i]!=null&&typeof(result.root[i])!="function"&&typeof(result.root[i])=="object")
			{
				find(result.root[i]);
			}
			delete result.root[i].id;
			delete result.root[i].parent;
		} 			 			 
		return result;
	};
	//初始化岗位分类列表
	function initPos(pos_data){
		var opt='';
		if(pos_data!=null && pos_data.length>0){
			var typesLen=0;
			for(var i=0;i<pos_data.length;i++){
				opt+='<option value='+pos_data[i].id+'>'+pos_data[i].name+'</option>';
			}
		}
		$(content).find('.lselect').html(opt);
	};

	function loadTree(source){
		var result = convert (source);
		$(content).find(".domTree").jstree({
			'core' : {
				'data' : result.root
			}
		}).bind('select_node.jstree', function(event,data) {
			currTreeNode = data.node;
		});
	};

	function output_choice(input_dom){
		var info='';					
		var id_hidden='';
		$(content).find('.rselect option').each(function(){
			if(''!=$.trim(info)){
				info+=','
			}
			info+=$(this).text();
			//生成input hidden标签，用于存放id编号
			id_hidden+='<input type="hidden" name="'+hidden_name+'" value="'+$(this).val()+'" />';
		});
		//将选择的数据回写到输入框中
//		if(''!=info){
			$(input_dom).val(info);
			var p=$(input_dom).parent();
			//移除旧的input hidden村签
			p.find('input:hidden').remove();
			//添加新的input hidden标签
			p.append(id_hidden);
//		}	
	};
	
	function checkOpt(id){
		var r=0;
		$(content).find('.rselect').find('option').each(function(){
			if(id==$(this).val()){
				r=1;
				return;
			}
		});
		return r;
	};

	function eventRight(){
		var optstr="";
		if ($(content).find('.rselect option').size() == 1){
			window.top.$.messager.alert("提示信息", "只能选择一个部门。", "info");
			return;
		}
		if(currTreeNode!=null){
			var flag=checkOpt(currTreeNode.id);
			if(flag==0){
				optstr+='<option value='+currTreeNode.data.id+'>'+currTreeNode.text+'</option>';
				$(content).find('.rselect').append(optstr);
			}
		}
	}
	function eventLeft(){
		var opts= $(content).find('.rselect option:selected');
		opts.remove();
	}
	$.fn.selectDepartment = function(options) {
        if(options=="show"){
        	alert("show");
        }else if($.isPlainObject(options)){
        	content = options.content;
        	source = options.source;
        	title = options.title;
        	hidden_name = options.hidden_name;
        	
        	loadTree(source);
        	
        	//覆写“右移”按钮事件
        	$(content).find('.btnRight').click(function(){
        		eventRight();
        	});
        	//左移事件
        	$(content).find('.btnLeft').click(function(){
        		eventLeft();
        	});
        	$(content).find('.rselect').on("dblclick",function(){
        		eventLeft();
        	});
        	
        	//上移事件
        	$(content).find('.btnUp').click(function(){
        		var o= $(content).find('.rselect option:selected').eq(0);
        		o.prev().before(o);
        	});
        	
        	//下移事件
        	$(content).find('.btnDown').click(function(){
        		var o= $(content).find('.rselect option:selected').eq(0);
        		o.next().after(o);
        	});
        	
        	$(this).click(function(){
        		var input_dom = $(this);
        		$.layer({
        			type: 1,
        			title: [title,'background-color:#dcdcdc;'],
        			border: [3, 0.3, '#000'],
        			closeBtn: [1,true],
        			shadeClose: false,
        			area: ['520px', '480px'],
        			fadeIn: 300,
        			btns: 1,
        			btn: ['保存'],
        			page:{
        				dom: content
        			},
        			yes:function(index){
        				//alert('此处添加“确定”回调代码');
        				output_choice(input_dom);
        				layer.close(index);
        			},
        			close:function(index){
        				//alert('此处添加“关闭”回调代码');
        				output_choice(input_dom);
        			}
        		});
        		/*
        		 * 将初始化的input hidden标签中id值填入id="rselect"的select标签列表框中
        		 * */
        		var p=$(this).parent();
        		var ids='';
        		//生成多id的字符串(逗号分隔)
        		p.find('input:hidden').each(function(){
        			if(ids!=''){
        				ids+=',';
        			}
        			ids+=$(this).val();
        		});	
        		if(ids=='')return;
        		var names= $(this).val().split(',');
        		var ids=ids.split(',');
        		if(null!=names && null!=ids){
        			var len=ids.length;
        			var opt_html='';
        			//生成option村签后追加到select列表中
        			for(var i=0;i<len;i++){
        				opt_html+='<option value="'+ids[i]+'">'+$.trim(names[i])+'</option>';
        			}
        			$(content).find('.rselect').html('');
        			$(content).find('.rselect').append(opt_html);
        		}
        	});

        }
        return $(this);
    };
    
    
})(jQuery);