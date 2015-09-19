var msg = window.top.$.messager;
var editor = "";

function initValidate(){
	var rules = {}, messages = {};
	rules = {
		"workFlowForm.formName": "required",
		"workFlowForm.formContent": "required"
	};
	messages = {
		"workFlowForm.formName": "不能为空，请输入",
		 "workFlowForm.formContent": "不能为空，请输入"
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

function createEditor(){
    if(typeof(KindEditor) != "undefined") {
//    	KindEditor.ready(function(K) {
    		editor = KindEditor.create("#editor", {
    			height: "350px",
    			items: ["source", "|", "undo", "redo", "|", "preview", "print", "template", "cut", "copy", "paste", "plainpaste", "wordpaste", "|","justifyleft", "justifycenter", "justifyright", "justifyfull", "insertorderedlist", "insertunorderedlist", "indent", "outdent", "subscript", "superscript", "clearhtml", "quickformat", "selectall", "|", "fullscreen", "/", "formatblock", "fontname", "fontsize", "|", "forecolor", "hilitecolor", "bold", "italic", "underline", "strikethrough", "lineheight", "removeformat", "|", "image", "flash", "media", "insertfile", "table", "hr", "emoticons", "map", "pagebreak", "link", "unlink"],
    			syncType: "form",
    			allowFileManager: true,
    			uploadJson: "/admin/file!ajaxUpload.action",
    			fileManagerJson: "/admin/file!ajaxBrowser.action",
    			afterChange: function() {
    				this.sync();
    			}
    		});
    		editor.html($("#editorContent").html());
    		var $oldcid = $($("#editorContent").html()).find(".contractCategory.cid");
    		if($oldcid.length>0){
    			window.contract_category_id_old = $oldcid.attr("contract_category_id");
    		}
//    	});
    }
}

function initEvents(){
	function _onSelect(node){
    	var parent =$('.categoryTree').combotree('tree').tree('getParent',node.target);
    	if(parent!=null){
    		if(node.id!=window.contract_category_id_old){
		    	editor.html("");
		    	window.contract_category_id_old = node.id;
    		}
	    	$(".categoryRoot").val(parent.id);
	    	$(".categoryChild").val(node.id);
			var child = findChildEnity(parent.id,node.id);
			var optFiled="";
			if(child!=null && child.fields!=null && child.fields.length>0){
				$.each(child.fields.split(','), function(ifield,field){
					optFiled += "<option value='"+ field + "'>"+ field +"</option>";
				});
			}
			$(".categoryFiled").html(optFiled);
    	}else{
    		throw "cao";//故意报异常，让页面停止
    	}
    }
	$(".categoryTree").combotree({
	    data: window.categoryTree,
	    onSelect:_onSelect
	});
	if(window.contract_category_id_old!=null){
		$(".categoryTree").combotree("setValue",window.contract_category_id_old);
		var node = $(".categoryTree").combotree("tree").tree("getSelected");
		try {
			_onSelect(node);
		} catch (e) {
		}
	}
	
	
	var atForm = $('#atForm');
	saveBtn = $("#saveBtn");
	closeBtn = $("#closeBtn");
	saveBtn.unbind('click').click(function(){
		var msg = window.top.$.messager;
		var _this = $(this);
		if (_this.hasClass('disabled') || !atForm.valid()){
			return false;
		}
		var $edtHtml = $("<div>"+editor.html()+"</div>");
		var $contractCategory = $edtHtml.find(".contractCategory");
		if($contractCategory.length>0 && $contractCategory.filter(".cid").length==0){
			msg.alert('错误', '请插入主键 "'+$(".categoryFiled").find("option:first").text()+'"','error');
			return;
		}
		
		 $.ajax({    
          type:'post',        
          url: '/admin/work_flow!saveWorkFlowForm.action',    
          data: $('#atForm').serialize(),    
          cache:false,    
          dataType:'json',    
          success:function(data){    
           if(data.status == 'success'){
        	   msg.alert('保存信息', data.message,'info',function(){
        		   window.top.reloadDataGridInTabTab('工作流定义', '工作流表单');
            	   window.top.closeCurrentWindow();
        	   });
        	   
           }else{
        	   msg.alert('保存信息', data.message,'error',function(){
             	   return false;
        	   });
           }
          } 
             
        });
	});
	closeBtn.unbind('click').click(function(){
		var _this = $(this);
		window.top.closeCurrentWindow();
		_this.removeClass('disabled').linkbutton({iconCls: 'icon-save'});
	});
	$("#insertLabel").click(function(){
		var categoryFiled = $(".categoryFiled").val();
		if(categoryFiled!=null && categoryFiled !=""){
			editor.insertHtml('<label style="white-space:nowrap;">'+categoryFiled+'</label>');
		}
	});
	
	$("#insertField").click(function(){
		var rootId = $(".categoryRoot").val();
		var childId = $(".categoryChild").val();
		var fieldName = $(".categoryFiled").val();
		var field = findFieldEnity(rootId,childId,fieldName);
		var insertField = field.name;
		
		if(insertField!=null && insertField !=""){
			var $editContent = $("<div>"+editor.html()+"</div>");
			
			if($editContent.find(".contractCategory.cfield[field='"+insertField+"']").length > 0){
				msg.show({
	                title:'警告',
	                timeout:5000,
	                msg: insertField+"输入框重复",
	                showType:'fade'
	            });
			}else{
				var content="";
				var selectedIndex = $(".categoryFiled").get(0).selectedIndex;
				if(selectedIndex==0){
					content = '<input type="text" class="contractCategory cfield cid definition" field="'+insertField+'" contract_category_id="'+$(".categoryChild").val()+'" placeholder="'+insertField+'(留空则自动生成)"  maxlength=160></input>';
				}else{
					if(field.built){
						
						content = '<input type="text" class="contractCategory cfield definition '+((field.required==true)?"required":"")+' " builtsign="'+field.builtsign +'" '
						for(var key in field.builtdata){
							var value = field.builtdata[key];
							content += key+'="'+value+'" ';
						}
						content+=' field="'+insertField+'" placeholder="'+insertField+'" maxlength=160></input>'+((field.required==true)?"<span class='required_sign' style='color:red;'>*</span>":"")+' ';
					}else{
						if(field.type=="文本框"){
							content = '<input type="text" class="contractCategory cfield definition '+((field.required==true)?"required":"")+' " field="'+insertField+'" placeholder="'+insertField+'" maxlength=160></input>'+((field.required==true)?"<span class='required_sign' style='color:red;'>*</span>":"")+' ';
						}else if(field.type=="日期框"){
							content = '<input type="text" class="contractCategory cfield cdate definition '+((field.required==true)?"required":"")+' " field="'+insertField+'" placeholder="'+insertField+'" maxlength=160></input>'+((field.required==true)?"<span class='required_sign' style='color:red;'>*</span>":"")+' ';
						}else if(field.type=="时间框"){
							content = '<input type="text" class="contractCategory cfield ctime definition '+((field.required==true)?"required":"")+' " field="'+insertField+'" placeholder="'+insertField+'" maxlength=160></input>'+((field.required==true)?"<span class='required_sign' style='color:red;'>*</span>":"")+' ';
						}else if(field.type=="日期时间框"){
							content = '<input type="text" class="contractCategory cfield cdatetime definition '+((field.required==true)?"required":"")+' " field="'+insertField+'" placeholder="'+insertField+'" maxlength=160></input>'+((field.required==true)?"<span class='required_sign' style='color:red;'>*</span>":"")+' ';
						}else if(field.type=="文本区"){
							content = '<textarea class="contractCategory cfield definition '+((field.required==true)?"required":"")+' " field="'+insertField+'" placeholder="'+insertField+'" maxlength=2048></textarea>'+((field.required==true)?"<span class='required_sign' style='color:red;'>*</span>":"")+' ';
						}else if(field.type=="下拉框"){
							var opts = '';
							for(var i in field.options){
								opts+='<option value="'+field.options[i]+'">'+field.options[i]+'</option>';
							}
							content = '<span class="select contractCategory cfield definition '+((field.required==true)?"required":"")+' " field="'+insertField+'"><select>'+opts+'</select></span>'+((field.required==true)?"<span class='required_sign' style='color:red;'>*</span>":"")+' ';
						}else if(field.type=="复选框"){
							var opts = '';
							for(var i in field.options){
								opts+='<label><input type="checkbox" name="'+insertField+'" value="'+field.options[i]+'"/>'+field.options[i]+'</label>';
							}
							content = '<span class="checkbox contractCategory cfield definition '+((field.required==true)?"required":"")+' " field="'+insertField+'">'+opts+'</span>'+((field.required==true)?"<span class='required_sign' style='color:red;'>*</span>":"")+' ';
						}else if(field.type=="单选框"){
							var opts = '';
							for(var i in field.options){
								opts+='<label><input type="radio" name="'+insertField+'" value="'+field.options[i]+'"/>'+field.options[i]+'</label>';
							}
							content = '<span class="radio contractCategory cfield definition '+((field.required==true)?"required":"")+' " field="'+insertField+'">'+opts+'</span>'+((field.required==true)?"<span class='required_sign' style='color:red;'>*</span>":"")+' ';
						}else if(field.type=="表达式"){
							content = '<input type="text" class="contractCategory cfield definition '+((field.required==true)?"required":"")+'  expression " style="border-style:dotted" readonly="readonly" field="'+insertField+'" placeholder="'+insertField+'" maxlength=160></input>'+((field.required==true)?"<span class='required_sign' style='color:red;'>*</span>":"")+' ';
						}else if(field.type=="选择树"){
							content = '<span class="selectTree contractCategory cfield definition '+((field.required==true)?"required":"")+' " tree="'+encodeURIComponent(field.expression)+'" field="'+insertField+'"><input type="text" /></span>'+((field.required==true)?"<span class='required_sign' style='color:red;'>*</span>":"")+' ';
						}else{
							content = '<input type="text" class="contractCategory cfield definition '+((field.required==true)?"required":"")+' " field="'+insertField+'" placeholder="'+insertField+'" maxlength=160></input>'+((field.required==true)?"<span class='required_sign' style='color:red;'>*</span>":"")+' ';
						}
					}
				}
				editor.insertHtml(content);
			}
		}
	});
	
	
	function findRootEnity(rootId){
		for(i in window.categoryTree){
			if(window.categoryTree[i].id == rootId){
				return window.categoryTree[i];
			}
		};
		return null;
	}
	
	function findChildEnity(rootId,childId){
		for(i in window.categoryTree){
			var root = window.categoryTree[i];
			if(root.id == rootId){
				for(j in root.children){
					if(root.children[j].id == childId){
						return root.children[j];
					}
				};
			}
		};
		return null;
	}
	
	function findFieldEnity(rootId,childId,fieldName){
		var childEntity = findChildEnity(rootId,childId);
		for(var index in childEntity.definition){
			if(fieldName==childEntity.definition[index].name){
				return childEntity.definition[index];
			}
		}
		return null;
	}
	/*
	var optRoot="";
	$.each(window.categoryTree, function(index,root){
		optRoot += "<option value='"+ root.id + "'>"+ root.text +"</option>";
	});
	$(".categoryChild").change(function(){
		var rootId = $(".categoryRoot").val();
		var childId = $(this).val();
		var child = findChildEnity(rootId,childId);
		var optFiled="";
		if(child!=null && child.fields!=null && child.fields.length>0){
			$.each(child.fields.split(','), function(ifield,field){
				optFiled += "<option value='"+ field + "'>"+ field +"</option>";
			});
		}
		$(".categoryFiled").html(optFiled);
	});

	$(".categoryRoot").html(optRoot).change(function(){
		var rootId = $(this).val();
		var root = findRootEnity(rootId);
		var optChild="";
		$.each(root.children, function(ic,c){
			optChild += "<option value='"+ c.id + "'>"+ c.text +"</option>";
		});
		$(".categoryChild").html(optChild).triggerHandler("change");
	}).triggerHandler("change");
	
	var $edtHtml = $("<div>"+editor.html()+"</div>");
	var $cid = $edtHtml.find(".contractCategory.cid");
	if($cid.length>0){
		var contract_category_id = $cid.attr("contract_category_id");
		
		for(i in window.categoryTree){
			var rootEntity=window.categoryTree[i];
			for(j in rootEntity.child){
				var childEntity=rootEntity.children[j];
				if(childEntity.id == contract_category_id){
					$(".categoryRoot").val(rootEntity.id);
					$(".categoryRoot").triggerHandler("change");
					$(".categoryChild").val(childEntity.id);
					$(".categoryChild").triggerHandler("change");
					break;
				}
			};
		};
		
	}
	$(".categoryChild").change(function(){
		editor.html("");
	});
	*/
	if(!window.isAddAction){
	}else{
	}
};



/*function initEvents(){
	
	saveBtn = $("#saveBtn");
	closeBtn = $("#closeBtn");
	saveBtn.unbind('click').click(function(){
		var msg = window.top.$.messager;
		var url = "";
		if (window.isAddAction)
			url = "/admin/ask4leave!save.action";
		else
			url = "/admin/ask4leave!update.action";
		var _this = $(this);
		if (_this.hasClass('disabled') || !atForm.valid()) return false;
		_this.addClass('disabled').linkbutton({iconCls: 'icon-loading'});
		$.post(url, atForm.serialize(), function(resp){
			msg.alert('保存信息', '保存信息成功！', 'info', function(){
				window.top.closeCurrentWindow();
			});
			window.top.reloadDataGridInTab('请假申请');
			_this.removeClass('disabled').linkbutton({iconCls: 'icon-save'});
		}).error(function(resp){
			msg.alert('保存信息', '保存信息发生错误！', 'info');
			_this.removeClass('disabled').linkbutton({iconCls: 'icon-save'});
		});
		return false;
	});
	closeBtn.unbind('click').click(function(){
		window.top.closeCurrentWindow();
	});
};*/

$(function(){
	createEditor();
    //增加页面校验规则
	initValidate();
    initEvents();
});