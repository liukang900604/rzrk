/*
 * _output对象定义在select.js中
 * */

//输出选择的数据
var output_choice=function(){
	var info='';					
	var id_hidden='';
	$('#rselect option').each(function(){
		if(''!=$.trim(info)){
			info+=','
		}
		info+=$(this).text();
		//生成input hidden标签，用于存放id编号
		id_hidden+='<input type="hidden" name="'+_output.hidden_name+'" value="'+$(this).val()+'" />';
	});
	//将选择的数据回写到输入框中
//	if(''!=info){
		$(_output.text_dom).val(info);
		var p=$(_output.text_dom).parent();
		//移除旧的input hidden村签
		p.find('input:hidden').remove();
		//添加新的input hidden标签
		p.append(id_hidden);
//	}	
};

$(function(){
	//弹出层事件,点击指定的弹出
	$(_output.click_dom).click(function(){
		$.layer({
			type: 1,
			title: [_output.title,'background-color:#dcdcdc;'],
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
				layer.close(index);
			},
			close:function(index){
				//alert('此处添加“关闭”回调代码');
				output_choice();
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
			$('#rselect').html('');
			$('#rselect').append(opt_html);
		}
	});
});