var ContractCategoryEdit =  function(options){
	var content='#contractCategoryEdit';
	var source=[];
	var title="类型编辑";

	

	//装载所有岗位数据
	var initAllPos=function(){
		var len=source.length;
		for(var i=0;i<len;i++){
			opt+='<option value='+source[i]'>'+source[i]+'</option>';
		}
		$(content).find('.lselect').html(opt);
		opt='';				
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
	
	function eventSearch(){
		var searchtext = $(content).find('.txtSearch').val();
		$(content).find('.lselect option').each(function(){
			if($(this).text().indexOf(searchtext)!=-1){
				$(this).show();
			}else{
				$(this).hide();
			}
		});
	}

	function eventRight(){
		var optstr="";
		var opts=$(content).find('.lselect option:selected');
		var sltNum=opts.length;
		if(0==sltNum) return;
		opts.each(function(){
			var flag=checkOpt($(this).val());
			if(0==flag){
				optstr+='<option value='+$(this).val()+'>'+$(this).text()+'</option>';
			}
		});
		if(optstr!=''){
			$(content).find('.rselect').append(optstr);
		}
	}
	function eventLeft(){
		var opts= $(content).find('.rselect option:selected');
		opts.remove();
	}
	function init(options) {
        if(options=="show"){
        	alert("show");
        }else if($.isPlainObject(options)){
        	content = options.content;
        	source = options.source;
        	title = options.title;
        	modify = options.modify;
        	modify_dom = options.modify_dom;
        	
        	initAllPos();
        	
        	//覆写“右移”按钮事件
        	$(content).find('.btnRight').click(function(){
        		eventRight();
        	});
        	$(content).find('.lselect').on("dblclick",function(){
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
        	
        	$(content).find('.btnSearch').click(function(){
        		eventSearch.call(this);
        	});
        	$(content).find('.txtSearch').keydown(function(e){
        		if(e.keyCode == 13) {
        			eventSearch.call(this);
        		}
			});

        	//选择岗位分类事件
        	$(content).find('.ltype').change(function(){
        		var typeid=$(this).val();
        		if(typeid==''){
        			initAllPos();
        		}
        		else{
        			var len=source.length;
        			var typesArr=[];
        			//遍历查找指定岗位分类列表
        			for(var i=0;i<len;i++){
        				if(typeid==source[i].typeid){
        					typesArr=source[i].types;
        					break;
        				}
        			}
        			len=typesArr.length;
        			//遍历生成指定岗位分类所有岗位数据
        			for(var i=0;i<len;i++){
        				opt+='<option value='+typesArr[i].id+'>'+typesArr[i].name+'</option>';
        			}
        			//显示在备选列表中
        			$(content).find('.lselect').html('');
        			if(opt!==$.trim()){
        				$(content).find('.lselect').html(opt);
        				opt='';
        			}						
        		}
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
        			}
        		});


        		if(modify){
        		}else{
        			$(content).find('.modify').val(false);
            		var values = $(modify_dom).find(".fields").text();
            		var names= values.split(',');
        			var len=names.length;
        			var opt_html='';
        			//生成option村签后追加到select列表中
        			for(var i=0;i<len;i++){
        				opt_html+='<option value="'+$.trim(names[i])+'">'+$.trim(names[i])+'</option>';
        			}
        			$(content).find('.rselect').html(opt_html);
        		}
        	});

        }
    };
    init();
    return {
    	create : function(){
			$(content).find('.modify').val(false);
			$(content).find('.rselect').html("");
    	},
    	modify : function(ccname,fields){
			$(content).find('.modify').val(true);
    		var names= values.split(',');
			var len=names.length;
			var opt_html='';
			//生成option村签后追加到select列表中
			for(var i=0;i<len;i++){
				opt_html+='<option value="'+$.trim(names[i])+'">'+$.trim(names[i])+'</option>';
			}
			$(content).find('.rselect').html(opt_html);
    	}
    }
};