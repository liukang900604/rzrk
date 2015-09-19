//“岗位”数据
var d=window.jobList;
/*
var d=[
	{
		typeid:'0',
		typename:'管理类',
		types:[
			{id:0,name:'总经理'},{id:1,name:'副总经理'},{id:2,name:'总监'}
		]
	},
	{
		typeid:'1',
		typename:'技术类',
		types:[]
	}
];*/

//var opt='';		

//初始化岗位分类列表
var initTypes=function(jobList){
	var len=jobList.length;
	opt='<option value="">所有岗位</option>';
	for(var i=0;i<len;i++){
		opt+='<option value='+jobList[i].typeid+'>'+jobList[i].typename+'</option>';
	}
	$('#pl').html(opt);
	opt='';
	initAllPos();
};

//装载所有岗位数据
var initAllPos=function(){
	var len=d.length;
	var typesLen=0;
	for(var i=0;i<len;i++){
		typesLen=d[i].types.length;
		for(var j=0;j<typesLen;j++){
			opt+='<option value='+d[i].types[j].id+'>'+d[i].types[j].name+'</option>';
		}
	}
	$('#lselect').html(opt);
	opt='';				
};


$(function(){
	initTypes(d);
	
	//查询事件
	$('#btnSearch').click(function(){
		var key=$.trim($('#txtKey').val());
		
		//遍历匹配查询
		var len=d.length;
		var typesLen=0;
		for(var i=0;i<len;i++){
			typesLen=d[i].types.length;
			for(var j=0;j<typesLen;j++){
				if(key=='') {
					opt+='<option value='+d[i].types[j].id+'>'+d[i].types[j].name+'</option>';
				}else{
					if(-1!=d[i].types[j].name.indexOf(key)){
						opt+='<option value='+d[i].types[j].id+'>'+d[i].types[j].name+'</option>';
					}
				}
				
			}
		}
		if(opt!=''){
			$('#lselect').html(opt);
			opt='';
			$('#txtKey').val('');
		}
	});
	
	//选择岗位分类事件
	$('#pl').change(function(){
		var typeid=$(this).val();
		if(typeid==''){
			initAllPos();
		}
		else{
			var len=d.length;
			var typesArr=[];
			//遍历查找指定岗位分类列表
			for(var i=0;i<len;i++){
				if(typeid==d[i].typeid){
					typesArr=d[i].types;
					break;
				}
			}
			len=typesArr.length;
			//遍历生成指定岗位分类所有岗位数据
			for(var i=0;i<len;i++){
				opt+='<option value='+typesArr[i].id+'>'+typesArr[i].name+'</option>';
			}
			//显示在备选列表中
			$('#lselect').html('');
			if(opt!==$.trim()){
				$('#lselect').html(opt);
				opt='';
			}						
		}
	});
	
	//判断是否重复选择
	var checkOpt=function(id){
		var r=0;
		$('#rselect').find('option').each(function(){
			if(id==$(this).val()){
				r=1;
				return;
			}
		});
		return r;
	};
	
	//右移事件
	$('#btnRight').click(function(){
		var opts=$('#lselect option:selected');
		var sltNum=opts.length;
		if(0==sltNum) return;
		opts.each(function(){
			var flag=checkOpt($(this).val());
			if(0==flag){
				opt+='<option value='+$(this).val()+'>'+$(this).text()+'</option>';
			}
		});
		if(opt!=''){
			$('#rselect').append(opt);
			opt='';
		}
	});
	
	//左移事件
	$('#btnLeft').click(function(){
		var opts= $('#rselect option:selected');
		opts.remove();
	});
	
	//上移事件
	$('#btnUp').click(function(){
		var o= $('#rselect option:selected').eq(0);
		o.prev().before(o);
	});
	
	//下移事件
	$('#btnDown').click(function(){
		var o= $('#rselect option:selected').eq(0);
		o.next().after(o);
	});				
});

/*
	它用于初始化点击弹出的dom、弹出框的标题描述、选择结果的输出的dom、被选的id编号所存放的hidden标签name名称
	该对象值的修改脚本，在页面上被引用的位置介于select.js、layer.min.js 与 poplay.js之间
*/
var _output={
		click_dom:'#dept_Pos',
		title:'数据选择',
		text_dom:'#dept_Pos',
		hidden_name:'jobList.id'
};

/*
//输出选择的数据
var output_choice=function(){
	var info='';							
	$('#rselect option').each(function(){
		if(''!=$.trim(info)){
			info+=','
		}
		info+=$(this).text();								
	});
	//将选择的数据回写到输入框中
	if(''!=info){
		$(output_dom).val(info);							
	}	
};

$(function(){
	//弹出层事件,点击"部门岗位 输入框时"弹出
	$(output_dom).click(function(){
		$.layer({
			type: 1,
			title: ['岗位选择','background-color:#dcdcdc;'],
			border: [3, 0.3, '#000'],
			closeBtn: [1,true],
			shadeClose: false,
			area: ['520px', '480px'],
			fadeIn: 300,
			btns: 1,
			btn: ['保存'],
			page:{
				dom:'#content'
			},
			yes:function(index){
				//alert('此处添加“确定”回调代码');
				output_choice();
			},
			close:function(index){
				//alert('此处添加“关闭”回调代码');
				output_choice();
			}
		});
	});
});
*/
