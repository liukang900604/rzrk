var msg = window.top.$.messager;
var letterIdx = {};
var unique = {};
var editor = "";
jQuery.validator.addMethod("chars", function(value, element) {
	return this.optional(element) || /^[\u4e00-\u9fa5\w]+$/.test(value);
}, "只能输入中英文数字和下划线");
function initValidate(whether){
	var rules = {}, messages = {};
	rules = {
		"contractCategory.name": {
			"required": true,
			"remote": whether,
			"maxlength":9,
			chars:true
			 
		},
//		"contractCategory.controlType":"required",
//		"indication": "required",
		"deparmentListTexts":{
			"required" : function(){
				if($("[name='contractCategory.controlType'][value='bydep']").prop("checked")){
					return true;
				}else{
					return false;
				}
			}
		}
	};
	messages = {
		"contractCategory.name": {
			"required": "分类名称不能为空，请输入",
			"remote": "分类名称重复，请重新输入",
			"maxlength": "最多只能输入9个字符"
		},
		"indication": "主键不能为空，请输入",
//		"contractCategory.controlType":"查询控制不能为空",
		"deparmentListTexts":"部门不能为空",
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





function ajaxFileUpload(){

	var id = $(this).attr('id');
	var url = $(this).data('url');
	$.ajaxFileUpload({
		url: url,
		secureuri:false,
		fileElementId:id,
		dataType: 'json',
		beforeSend:function(){
			//...
		},
		success: function (data, status){
            if(data.status == "success"){
				msg.alert("上传文件", "上传文件成功！<font style='color:red'>请继续编辑字段属性</font>", "info", function(){
					var len = data.data.titleList.length;
					if(len>0){
						if(window.isAddAction){
							window.definitionWidget.clear();
							window.definitionWidget.appendData({"name":data.data.titleList[0]});
						}else{
							var indiVal = window.definitionWidget.getPrimary().name;
							if(indiVal!=data.data.titleList[0]){
								msg.alert('警告', '和原主键不一致！', 'error');
								return;
							}
							window.definitionWidget.clearNotPrimary();
						}
					}
					var opt="";
					for(var i=1;i<len;i++){
						window.definitionWidget.appendData({"name":data.data.titleList[i]});
//						opt+='<tr><td><div class="option" data-value='+data.data.titleList[i]+'>'+data.data.titleList[i]+'&nbsp;&nbsp;<label><input type="checkbox"/>统计</label></div></td></tr>';
					}
					refreshFieldSelectOption();
//					$('.filed_select').html(opt);
					if(window.isAddAction){
						if($('[name="contractCategory.name"]').val()==""){
							$('[name="contractCategory.name"]').val(data.data.fileName);
						}
					}
//					output_choice();
				});
             }else{
 				msg.alert("上传文件", "上传文件失败：" + data.message, "error");
             }
			var inhtml = $("#updateContractCategory").get(0).outerHTML;
			$("#updateContractCategory").replaceWith(inhtml);
            $("#updateContractCategory").unbind('change').change(ajaxFileUpload);
		},
		error: function (data, status, e){
			msg.alert("上传文件", "上传文件失败：" + e, "error");
			var inhtml = $("#updateContractCategory").get(0).outerHTML;
			$("#updateContractCategory").replaceWith(inhtml);
            $("#updateContractCategory").unbind('change').change(ajaxFileUpload);
		}
	});
}





function _initEvents(){
	var atForm = $('#atForm');
	saveBtn = $("#saveBtn");
	closeBtn = $("#closeBtn");
	$(".parentId").combobox({
		panelHeight:300,
		valueField: 'id',
        textField: 'name',
//		multiple:true,
		"data" : window.contractCategoryOptionList
	});
	$(".childId").combobox({
		panelHeight:300,
		valueField: 'id',
        textField: 'name',
		multiple:true,
		"data" : window.contractCategoryOptionList
	});
	$(".scriptLibraryList").combobox({
		panelHeight:'auto',
		valueField: 'id',
        textField: 'name',
		multiple:true,
		"data" : window.scriptLibraryOptionList
	});
	saveBtn.unbind('click').click(function(){
		var script = window.codeEditor.getValue();
		if(script==null){
			script="";
		}
		$("#code").val(script);
		
		var msg = window.top.$.messager;
		var url = "";
		if (window.isAddAction)
			url = "/admin/contract_category!save.action";
		else
			url = "/admin/contract_category!update.action";
		var _this = $(this);
		if (_this.hasClass('disabled') || !atForm.valid()) return false;
		
		var defData=window.definitionWidget.getData();
		if($.isEmptyObject(defData)){
			msg.alert('错误', '请添加主键', 'warn');
			return;
		}else if(defData[0].built==true || defData[0].type!="文本框"){
			msg.alert('错误', '主键只能为非内置字段的文本框', 'warn');
			return;
		}
		$("#atForm [name='contractCategory.definition']").val(JSON.stringify(defData));
		$("#atForm [name='contractCategory.contentProvider']").val(false);
		var fields = [];
		var totals=[];
		for(var i in defData){
			fields.push(defData[i].name);
			if(defData[i].total){
				totals.push(defData[i].name);
			}
			if(defData[i].contentProvider==true){
				$("#atForm [name='contractCategory.contentProvider']").val(true);
			}
		}
		//兼容
		$("#atForm [name='contractCategory.fields']").val(fields.join(","));
		$("#atForm [name='contractCategory.totals']").val(totals.join(","));
		if($('iframe.ke-edit-iframe').contents().find(".contractCategory.cfield").length > 0){
			
		/*	if($('iframe.ke-edit-iframe').contents().find(".contractCategory.cfield").length != $(".fieldList option").length){
				msg.alert('错误', '字段样式未包含所有字段列表信息', 'warn');
				return false;
			}
			$(".fieldList option").each(function(){
				if($('iframe.ke-edit-iframe').contents().find(".contractCategory.cfield[field="+$(this).text()+"]").length <= 0){
					msg.alert('错误', '字段样式未发现字段为'+$(this).text()+'的内容', 'warn');
					throw "cao";
				}
			})*/
				
	
		}else{
			if($('iframe.ke-edit-iframe').contents().find(".ke-content").text() != "" ){
				msg.alert('错误', '字段样式格式不对,请选择字段列表!', 'warn');
				return false;
			}else{
				$('iframe.ke-edit-iframe').contents().find(".ke-content").html("");
				$("#editor").val("");
			}
		}
	
		_this.addClass('disabled').linkbutton({iconCls: 'icon-loading'});
		$.post(url,atForm.serialize(),function(resp){
			if(resp.status == "success"){
				msg.alert('保存信息', '保存信息成功！', 'info', function(){
					window.top.closeCurrentWindow();
					if(window.parent!=window){
						if(window.isAddAction){
							window.top.closeIfameWindowById("新增二级分类");
						}else{
							window.top.closeIfameWindowById("编辑二级分类");
						}
					}
				});
				try{
					window.top.updateSubCategory();
					window.top.reloadDataGridInTab("二级分类");
				}catch(e){
					location.reload();
				}
				_this.removeClass('disabled').linkbutton({iconCls: 'icon-save'});
			}else{
				msg.alert('保存信息', resp.message, 'error');
				_this.removeClass('disabled').linkbutton({iconCls: 'icon-save'});
			}
		},"json").error(function(resp){
			  msg.alert('保存信息', '保存信息发生错误！', 'error');
			_this.removeClass('disabled').linkbutton({iconCls: 'icon-save'});
		});
		
		return false;
	});
	closeBtn.unbind('click').click(function(){
		if(window.isAddAction){
			window.top.closeIfameWindowById("新增二级分类");
		}else{
			window.top.closeIfameWindowById("编辑二级分类");
		}
	});
//	$("#isFlow").unbind('change').change(function(){
//		if($("#isFlow").val() == 1){
//			$("tr.undelete").show();
//		}else{
//			$("tr.undelete").hide();
//		}
//		
//	})
    $("#updateContractCategory").unbind('change').change(ajaxFileUpload);
	$("[name='contractCategory.controlType'][value='bydep']").change(function(){
		if($(this).prop("checked")){
			$(".depContractContent").removeClass("controlhide");
		}else{
			$(".depContractContent").addClass("controlhide");
		}
	});
	$("[name='contractCategory.controlType'][value='byop']").change(function(){
		if($(this).prop("checked")){
		    $(".opContractContent").removeClass("controlhide");
		}else{
			$(".opContractContent").addClass("controlhide");
		}
	});
	$("[name='contractCategory.controlType'][value='bydep']").triggerHandler("change");
	$("[name='contractCategory.controlType'][value='byop']").triggerHandler("change");
	
	$("#insertLabel").click(function(){
		var categoryFiled = $(".fieldList").val();
		if(categoryFiled!=null && categoryFiled !=""){
			editor.insertHtml('<label style="white-space:nowrap;">'+categoryFiled+'</label>');
		}
	});
	
	
	$("#insertField").click(function(){
		var fieldName = $(".fieldList").val();
		
		var defineField =  window.definitionWidget.getData();
		var field = "";
		 for(i in defineField){
			 var fields = defineField[i];
			 if(fields.name == fieldName){
				 field = defineField[i];
			 }
			 
		 }
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
				var selectedIndex = $(".fieldList").get(0).selectedIndex;
				if(selectedIndex==0){
					content = '<input type="text" class="contractCategory cfield cid definition" field="'+insertField+'"  placeholder="'+insertField+'(留空则自动生成)"  maxlength=160></input>';
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
	
	$("[name='contractCategory.isSubDepView']").change(function(){
		if($(this).prop("checked")){
			$("[name='contractCategory.isView']").prop("checked",true)
		}
	});
	
	$("[name='contractCategory.isView']").change(function(){
		if(!$(this).prop("checked")){
			$("[name='contractCategory.isSubDepView']").prop("checked",false);
		}
	});
	
//    ContractCategoryEdit();
    
	
//	$("ol.simple_with_drop").sortable();
//	$("ul").on("click", "li:not(.addColleague) span", function() {
//		var _this = $(this);
//		delete unique[_this.next().val()];
//		_this.parents("li").remove();
//	});
//	$(".addColleague a").click(function() {
//		$(this).hide();
//		$(this).next().next().show();
//	});
//	
//	$("ol.simple_with_drop").sortable({
//		  group: 'no-drop',
//		  handle: 'i.icon-move',
//		  onDragStart: function ($item, container, _super) {
//		    // Duplicate items of the no drop area
//		    if(!container.options.drop)
//		      $item.clone().insertAfter($item);
//		    _super($item, container);
//		  }
//		});
//		$("ol.simple_with_no_drop").sortable({
//		  group: 'no-drop',
//		  drop: false
//		});
//		$("ol.simple_with_no_drag").sortable({
//		  group: 'no-drop',
//		  drag: false
//		});
	
	$("#isUrlView").change(function(){
		showAndHideTr();
	});
	var value = $("#code").attr("value");
	window.codeEditor = CodeMirror.fromTextArea(document.getElementById("code"), {
	       lineNumbers: true,
	       mode:  "javascript"
	   });
	   $(".CodeMirror-scroll").hover(function(){
	       $(this).get(0).style.cursor = "text";
	   });
	   codeEditor.setValue(Base64.decode(value));
	   
	    $(".adminIds").selectMembers({
	    	hiddenName: 'adminIds'
	    });

};

function initDicts(users) {
	$.each(users, function(i, user) {
		letterIdx[user.name] = CTL.convert(user.name).toString();
	});
}
function filterName(q, row) {
	var opts = $(this).combobox('options');
	if (row[opts.textField].toLocaleLowerCase().indexOf(q.toLocaleLowerCase()) != -1) {
		return true;
	}
	if (letterIdx[row[opts.textField]].toLocaleLowerCase().indexOf(
			q.toLocaleLowerCase()) != -1) {
		return true;
	}
	return false;
}

function selectUser(user) {
	var lastLi = $(".addColleague");
	var btn = lastLi.find("a");
	var comboBox = btn.next();
	var li = '<li class="deleteAdminId">{0}<span>X</span><input type="hidden"  name="viewerAdminList.id" value="{1}"/></li>';
	li = $.validator.format(li, user.name, user.id);
	if (!unique[user.id]) {
		$(li).insertBefore(lastLi);
	}
	unique[user.id] = true;
	btn.show();
	comboBox.combobox("setValue", "");
	comboBox.next().hide();
}

function initData() {
	var url = "/admin/admin!getAllAjaxList.action";
	$.get(url, function(data) {
		initDicts(data.rows);
		$(".userBox").combobox({
			filter : filterName,
			valueField : "id",
			textField : "name",
			data : data.rows,
			onSelect : selectUser,
			onLoadSuccess : function() {
				if ($(".addColleague a").is(":hidden"))
					return;
				$(this).next().hide();
			}
		});
	}, "json");
}
/*
function ContractCategoryEdit(){
	var content=$('#atForm');
	var parserNames=window.parserNames;
	var $fields=$("[name='contractCategory.fields']");
	var $total=$("[name='contractCategory.totals']");
	var $definition=$("[name='contractCategory.definition']");
	

	//装载所有岗位数据
	var initBaseField=function(){
		var source=[];
		if(parserNames!="" && parserNames!=null){
			source = parserNames.split(",");
		}
		var len=source.length;
		var opt="";
		for(var i=0;i<len;i++){
			opt+='<option value='+source[i]+'>'+source[i]+'</option>';
		}
		$(content).find('.lselect').html(opt);
	};

	var initExistField = function(){
		var defObj = window.definition;
		if(!$.isEmptyObject(defObj)){
			var indication = defObj[0];
			$(".indication").val(indication.name);
			var opt_html='';
			for(var i=1;i<defObj.length;i++){
				var field = defObj[i];
				var checked = "";
				if(field.total){
					checked = "checked='checked'"
				}
				opt_html+='<tr><td><div class="option" data-value="'+$.trim(field.name)+'">'+field.name+'&nbsp;&nbsp;<label><input type="checkbox" '+checked+'/>统计</label></div></td></tr>';
			}
			$(content).find('.filed_select').html(opt_html);
			$definition.val(JSON.stringify(defObj));
		}else{
			//兼容 fields totals 描述
			var names= [];
			var totals= [];
			var totalstrs = $total.val();
			if(totalstrs !="" && totalstrs!=null){
				totals = totalstrs.split(',');
			}
			var existFields = $fields.val();
			if(existFields !="" && existFields!=null){
				names = existFields.split(',');
			}
			var len=names.length;
			var opt_html='';
			//生成option村签后追加到select列表中
			if(len>0){
				$(".indication").val(names[0]);
			}
			for(var i=1;i<len;i++){
				var checked = "";
				if($.inArray(names[i],totals)!=-1){
					checked = "checked='checked'"
				}
				opt_html+='<tr><td><div class="option" data-value="'+$.trim(names[i])+'">'+$.trim(names[i])+'&nbsp;&nbsp;<label><input type="checkbox" '+checked+'/>统计</label></div></td></tr>';
			}
			$(content).find('.filed_select').html(opt_html);
			
		}
	}
	
	function getField(opt){
		var tmp={
				name:"",
				total:false,
				type:"text",
				built:false,
				builtsign:"",
				options:[]
		}
		return $.extend(tmp,opt);
	}

	function output_choice(){
		var fieldArr = [];
		fieldArr.push($(".indication").val());
		$(content).find('.filed_select .option').each(function(){
			fieldArr.push($(this).data("value"));
		});
		$fields.val(fieldArr.join(","));
		
		var totalArr = [];
		$(content).find('.filed_select .option:has(:checked)').each(function(){
			totalArr.push($(this).data("value"));
		});
		$total.val(totalArr.join(","));
		
		var definition=[];
		var builtArr = $(".select_list option").map(function(){
			return $(this).text();
		}).get();
		var fieldIndication = getField({
			name:$(".indication").val()
		});
		definition.push(fieldIndication);
		$(content).find('.filed_select .option').each(function(){
			var name = $(this).data("value");
			var total=false;
			if($(this).find(":checked").length>0){
				total=true;
			}
			var type ="text";
			var built=false;
			var builtsign="";
			if(builtArr.indexOf(name)!=-1){
				type="built_text";
				built=true;
				builtsign=name;
			}
			var field = getField({
				"type":type,
				"name":name,
				"total":total,
				"built":built,
				"builtsign":builtsign
			});
			definition.push(field);
		});
		$definition.val(JSON.stringify(definition));
	};
	
	function checkOpt(id){
		var r=0;
		if(id==$(".indication").val()){
			r=1;
//			msg.alert('警告', '和主键重名！', 'warn');
			return;
		}
		$(content).find('.filed_select').find('.option').each(function(){
			if(id==$(this).data("value")){
				r=1;
			    //msg.alert('警告', id+'重复！', 'warn');
				return;
			}
		});
		return r;
	};
	

	function eventRight(){
		var optstr="";
		var opts=$(content).find('.lselect option:selected');
		var sltNum=opts.length;
		if(0==sltNum) return;
		opts.each(function(){
			var val = $.trim($(this).val());
			if(val!=""){
				var flag=checkOpt(val);
				if(0==flag){
					optstr+='<tr><td><div class="option" data-value='+$(this).val()+'>'+$(this).text()+'&nbsp;&nbsp;<label><input type="checkbox"/>统计</label></div></td></tr>';
				 }
		}
	});
		if(optstr!=''){
			$(content).find('.filed_select').append(optstr);
		}
		output_choice();
	}
	function eventLeft(){
		var opts= $(content).find('.filed_select tr:has(.option.selected)');
		opts.remove();
		output_choice();
	}
	
	
	initBaseField();
	if(window.isAddAction){
		$(content).find('.indication').val("编号");
		$(content).find('[name="contractCategory.fields"]').val("编号");
	}else{
		initExistField();
		$(".indication").attr("disabled",true);
	}

	
	//覆写“右移”按钮事件
	$(content).find('.btnRight').unbind("click").click(function(){
		eventRight();
	});
    
	
	$(content).find('.lselect').off("dblclick").on("dblclick",function(){
		  eventRight();
	});
//	$(content).find(".indication").focusout(function(){
//		var id = $(this).val();
//		$(content).find('.lselect').find('option').each(function(){
//			if(id==$(this).val()){
//				r=1;
//				msg.alert('警告', '主键与内置字段重复！', 'warn');
//				return;
//			}
//		});
//		$(content).find('.filed_select').find('option').each(function(){
//			if(id==$(this).val()){
//				r=1;
//				msg.alert('警告', '主键与选定字段重复！', 'warn');
//				return;
//			}
//		});
//
//	})
	//左移事件
	$(content).find('.btnLeft').unbind("click").click(function(){
		eventLeft();
	});
//	$(content).find('.filed_select').on("dblclick",function(){
//		eventLeft();
//	})

	
	//上移事件
	$(content).find('.btnUp').unbind("click").click(function(){
		var o= $(content).find('.filed_select tr:has(.option.selected)').eq(0);
		o.prev().before(o);
		output_choice();
	});
	
	//下移事件
	$(content).find('.btnDown').unbind("click").click(function(){
		var o= $(content).find('.filed_select tr:has(.option.selected)').eq(0);
		o.next().after(o);
		output_choice();
	});
	
	$(content).find('.btnCustom').click(function(){
		var addCategory = $.trim($(content).find('.textCustom').val()).replace(",","，");
		var optstr = "";
		if(addCategory!=""){
			var flag=checkOpt(addCategory);
			if(!/^[\u0391-\uFFE5\w]+$/.test(addCategory)){
				msg.show({
	                title:'警告',
	                timeout:5000,
	                msg:'<span style="color:red;">只能输入中英文数字和下划线</span>',
	                showType:'fade'
	            });
				return;
			}
			if(0==flag){
				optstr+='<tr><td><div class="option" data-value='+addCategory+'>'+addCategory+'&nbsp;&nbsp;<label><input type="checkbox"/>统计</label></div></td></tr>';
			}else{
				msg.show({
	                title:'警告',
	                timeout:5000,
	                msg:addCategory+'已存在',
	                showType:'fade'
	            });
			}
		}
		if(optstr!=''){
			$(content).find('.filed_select').append(optstr);
			$(content).find('.textCustom').val("");
		}
		output_choice();
	});
	$(content).find("#updateContractCategory").unbind('change').change(ajaxFileUpload);
	$(content).find(".indication").unbind('change').change(function(){
		output_choice();
	});

	$("select[name='contractCategory.approvalNeeded']").change(function(){
		initDisplay();
	});
	
	$(content).find('.filed_select').on("click",".option", function(){
		$(this).closest(".filed_select").find(".option").removeClass("selected");
		$(this).addClass("selected");
		
	}).on('dblclick','.option', function(){
		$(this).addClass("selected");
		eventLeft();
	}).on('change','.option [type="checkbox"]', function(){
		output_choice();
	});
};
*/
function DefinitionWidget($widget,$editTemp){
//init
	$widget.nestable({
        "group": 1,
		"maxDepth":1
	});
//fn
	function setData($item,field){
		var msg = field.name;
		if(field.built){
			msg += "&emsp;&emsp;内置&emsp;&emsp;"+field.builtsign;			
		}else{
			msg += "&emsp;&emsp;自定义&emsp;&emsp;"+field.type;			
		}
		if(field.required){
			msg+="&emsp;&emsp;必填 " ;
		}
		if(field.total){
			msg+="&emsp;&emsp;统计 " ;
		}
		if(field.departmentIds.length>0){
			msg+="&emsp;&emsp;限制可见部门 " ;
		}
		if(field.adminIds.length>0){
			msg+="&emsp;&emsp;限制可见人 " ;
		}
		if(field.contentProvider==true){
			msg+="&emsp;&emsp;二级分类列内容提供者";
		}
		$item.find(".dd3-content").html(msg);
		$item.data("field",field);
	}
	function createField(){
		var field = {
				"name":"",	
				"required":false,	
				"type":"文本框",
				"built":false,
				"builtsign":"",
				"builtdata":{},
				"options":[],
				"total":false,
				"expression":"",
				"departmentIds":[],
				"adminIds":[],
				"superiorView":false,
				"contentProvider":false
			};
		return field;
	}
	function createItem(field){
		var $item = $('<li class="dd-item dd3-item"><div class="dd-handle dd3-handle">Drag</div><div class="dd3-content"></div></li>');
		$item.find(".dd3-content").click(function(e){
			showEdit($item);
		});
		setData($item,field);
		return $item;
	}
	function getFieldList(){
		var defData=[];
		$widget.find("li").each(function(){
			var field = $(this).data("field");
			defData.push(field);
		});
		return defData;
	}
	function showEdit($item){
		//初始化事件
		var isAdd = ($item==null);
		var $edit = $("<div>"+$editTemp.html()+"</div>").css("width","450px").show();
		var ezWin = window.top.createWin({
	    	title: '修改字段',
	    	width:"500px",
	    	height: "500px",
	    	resizable: true,
	    	maximizable: true,
	    	onClose:function(){
	            $(this).window("destroy");
	        }
		});
		ezWin.append($edit);
		ezWin.window("open");
		ezWin.find("[name='built']").change(function(){
			if($(this).prop("checked")){
				ezWin.find(".builtTr").show();
				ezWin.find(".notbuiltTr").hide();
			}else{
				ezWin.find(".builtTr").hide();
				ezWin.find(".notbuiltTr").show();
			}
		});
		ezWin.find("[name='builtsign']").change(function(){
			if($(this).val()=="二级分类列内容"){
				ezWin.find(".builtdataContentProvider").show();
			}else{
				ezWin.find(".builtdataContentProvider").hide();
			}
		});
		ezWin.find(".builtdataContentProvider").hide();
		var $pt = ezWin.find("[name='builtdataContentProviderTable']").empty();
		$pt.append("<option value=''>请选择</option>");
		var $pf = ezWin.find("[name='builtdataContentProviderField']").empty();
		$pf.append("<option value=''>请选择</option>");
		for(var x in window.contentProviderList){
			var table = window.contentProviderList[x];
			var $opt =$("<option value='"+table.id+"'>"+table.name+"</option>");
			$pt.append($opt);
		}
		ezWin.find("[name='builtdataContentProviderTable']").change(function(){
			var tid = $(this).val();
			var $pf = ezWin.find("[name='builtdataContentProviderField']").empty();
			$pf.append("<option value=''>请选择</option>");
			var node=null;
			for(var i in window.contentProviderList){
				var _node = window.contentProviderList[i];
				if(_node.id == tid){
					node = _node;
					break;
				};
			}
			if(node!=null){
				for(var j in node.fields){
					var field = node.fields[j];
					var $opt =$("<option value='"+field.id+"'>"+field.name+"</option>");
					$pf.append($opt);
				}
			}
		});
		ezWin.find("[name='type']").change(function(){
			if($(this).val()=="表达式"||$(this).val()=="选择树"){
				ezWin.find("[name='expression']").closest("tr").show();
			}else{
				ezWin.find("[name='expression']").closest("tr").hide();
			}
			if($(this).val()=="下拉框" ||$(this).val()=="单选框" ||$(this).val()=="复选框"){
				ezWin.find("[name='options']").closest("tr").show();
			}else{
				ezWin.find("[name='options']").closest("tr").hide();
			}
		});
		ezWin.find(".departmentIds").combotree({
			panelHeight:300,
			lines: true,multiple:true,cascadeCheck:false,
			"data" : window.depTree4Easyui
		});
		$(ezWin.find(".adminIds").get(0)).selectMembers({
	    	hiddenName: 'adminIds'
	    });

//		ezWin.find(".adminIds").combobox({
//			panelHeight:300,
//			valueField: 'id',
//	        textField: 'name',
//			multiple:true,
//			"data" : window.admin4Easyui
//		});
		ezWin.find(".saveBtn").click(function(){
			var field = createField();
			field.name = ezWin.find("[name='name']").val();;
			if(field.name==""){
				msg.alert('错误', '名称不能为空', 'error');
				return false;
			}
			field.required  = ezWin.find("[name='required']").prop("checked");
			field.built  = ezWin.find("[name='built']").prop("checked");
			if(field.built){
				field.builtsign = ezWin.find("[name='builtsign']").val();
				if(field.builtsign=="二级分类列内容"){
					field.builtdata["builtdata_content_provider_table"]=ezWin.find("[name='builtdataContentProviderTable']").val();
					if($.isEmptyObject(field.builtdata["builtdata_content_provider_table"]) ){
						msg.alert('错误', '二级分类列内容的表不能为空', 'error');
						return false;						
					}
					field.builtdata["builtdata_content_provider_field"]=ezWin.find("[name='builtdataContentProviderField']").val();
					if($.isEmptyObject(field.builtdata["builtdata_content_provider_field"]) ){
						msg.alert('错误', '二级分类列内容的列不能为空', 'error');
						return false;						
					}
				}
			}else{
				field.type = ezWin.find("[name='type']").val();
				if(field.type=="表达式"||field.type=="选择树"){
					field.expression = ezWin.find("[name='expression']").val();
					if(field.expression==""){
						msg.alert('错误', '描述不能为空', 'error');
						return false;						
					}
				}else if(field.type=="下拉框"||field.type=="复选框"||field.type=="单选框"){
					var optionsStr = ezWin.find("[name='options']").val();
					if(optionsStr!=null && optionsStr!=""){
						field.options = $.grep(optionsStr.split(/[,;，；]/),function(n){
							return (n!=""&&n!=null);
						});
					}
					if($.isEmptyObject(field.options)){
						msg.alert('错误', '可选项不能为空', 'error');
						return false;
					}
				}
			}
			var total = ezWin.find("[name='total']").prop("checked");
			field.total = total;
			field.departmentIds = ezWin.find(".departmentIds").combotree("getValues");
			field.adminIds = ezWin.find("[name='adminIds']").map(function(){return $(this).val();}).get();
			var superiorView = ezWin.find("[name='superiorView']").prop("checked");
			field.superiorView = superiorView;
			var contentProvider = ezWin.find("[name='contentProvider']").prop("checked");
			field.contentProvider = contentProvider;
			
			
			var fieldExits=false;
			$widget.find("li").each(function(){
				var _field = $(this).data("field");
				if(_field.name==field.name && $(this).get(0)!=$item.get(0)){
					fieldExits=true;
					return false;
				}
			});
			if(fieldExits){
				msg.alert('错误', field.name+'已存在', 'error');
				return false;
			}
			
			if(isAdd){
				_$item = createItem(field);
				$widget.append(_$item);
			}else{
				setData($item,field);
			}
			
			refreshFieldSelectOption();
			window.controlFieldWidget.refresh();
			ezWin.window("close");
		});
		ezWin.find(".deleteBtn").click(function(){
			if($item){
				$item.remove();
			}
			refreshFieldSelectOption();
			window.controlFieldWidget.refresh();
			ezWin.window("close");
		});
		
			
		//初始化值
		if(isAdd){
			ezWin.find("[name='name']").val("");
			ezWin.find("[name='required']").prop("checked",false);
			ezWin.find("[name='built']").prop("checked",false).triggerHandler("change");
			ezWin.find("[name='builtsign']").find("option:first").prop("selected",true).triggerHandler("change");
			ezWin.find("[name='builtdataContentProviderTable']").val("")
			ezWin.find("[name='builtdataContentProviderField']").val("");
			ezWin.find("[name='type']").find("option:first").prop("selected",true).closest("[name='type']").triggerHandler("change");
			ezWin.find("[name='options']").val("");
			ezWin.find("[name='total']").prop("checked",false);
			ezWin.find("[name='expression']").val("");
			ezWin.find("[name='contentProvider']").prop("checked",false);
		}else {
			var field=$item.data("field");
			ezWin.find("[name='name']").val(field.name);
			ezWin.find("[name='required']").prop("checked",field.required);
			ezWin.find("[name='built']").prop("checked",field.built).triggerHandler("change");
			ezWin.find("[name='builtsign']").val(field.builtsign).triggerHandler("change");
			if(field.builtsign=="二级分类列内容"){
				ezWin.find("[name='builtdataContentProviderTable']").val(field.builtdata["builtdata_content_provider_table"]).triggerHandler("change");
				ezWin.find("[name='builtdataContentProviderField']").val(field.builtdata["builtdata_content_provider_field"]);
			}
			ezWin.find("[name='type']").val(field.type).triggerHandler("change");
			ezWin.find("[name='options']").val(field.options.join(","));
			ezWin.find("[name='total']").prop("checked",field.total);
			ezWin.find("[name='expression']").val(field.expression);
			if($.isArray(field.departmentIds)){
				ezWin.find(".departmentIds").combotree('setValues',field.departmentIds);
			}
			if($.isArray(field.adminIds)){
				var $$dminIds = ezWin.find(".adminIds");
				for(var adminId in field.adminIds){
					$('<input type="hidden" name="adminIds" value="'+adminId+'"/>').appendTo($$dminIds);
				}
			}
			ezWin.find("[name='superiorView']").prop("checked",field.superiorView);
			ezWin.find("[name='contentProvider']").prop("checked",field.contentProvider);
		}
	}


	return {
		"add" : function(){
			showEdit();
		},
		"appendData":function(row){
			if(row.name==""||row.name==null)return;
			var field = createField();
			$.extend(field,row);
			var fieldExits=false;
			$widget.find("li").each(function(){
				var field = $(this).data("field");
				if(field.name == row.name){
					fieldExits=true;
					setData($(this),field);
					return false;
				}else{
				}
			});
			if(!fieldExits){
				$item = createItem(field);
				$widget.append($item);
			}

		},
		"getPrimary":function(){
			return $widget.find("li:first").data("field");
		},
		"setData":function(defData){
			$widget.find("li").remove();
			if($.isEmptyObject(defData)){
				var field = createField();
				field.name="编号";
				$item = createItem(field);
				$widget.append($item);
			}else{
				for(var index in defData){
					var field = defData[index];
					field = $.extend(createField(),field); //后台传过来的field，属性可能不完全
					$item = createItem(field);
					$widget.append($item);
					if(index==0){
						$item.find(".dd-handle").hide();
						$item.find(".dd3-content").unbind("click");
					}
				}
			}
		},
		"getData":function(){
			return getFieldList();
		},
		"clear":function(){
			$widget.find("li").remove();
		},
		"clearNotPrimary":function(){
			$widget.find("li:not(:first)").remove();
		}
	};
}

var ControlFieldWidget = function($select){
	var oldvalue;
	function _refresh(frist){
		if(frist!=null){
			oldvalue = $select.attr("oldvalue");
		}else{
			oldvalue = $select.val();
		}
		$select.empty();
		$("<option value='__create_admin'>创建人</option>").appendTo($select);
		var definition = window.definitionWidget.getData();
		for(var i in definition){
			var field = definition[i];
			if(field.builtsign == "用户名"){
				$("<option value='"+field.name+"'>"+field.name+"</option>").appendTo($select);
			}
		}
		$select.val(oldvalue);
		if($select.val()==null){
			$select.val("__create_admin");
		}
	};
	_refresh(true);
	
	return {
		"refresh":_refresh
	}
};


function refreshFieldSelectOption(){
	var defineField =  window.definitionWidget.getData();
	var optFiled = "";
	 for(i in defineField){
		 var field = defineField[i];
		 optFiled += "<option value='"+ field.name + "'>"+ field.name +"</option>";
	 }
	 $("select.fieldList").html(optFiled);
	
}
function createDefinitionWidget(){

	window.definitionWidget = DefinitionWidget($("#definitionWidget"),$("#definitionEdit"));
	window.definitionWidget.setData(window.definition);
	window.controlFieldWidget = ControlFieldWidget($("#controlFieldWidget"),window.definition);
	$("#addField").click(function(){
		window.definitionWidget.add();
	});

}

function initDisplay(){
	var isYes = parseInt($("select[name='contractCategory.approvalNeeded']").val());
	if (isYes)
		$(".approver").show();
	else
		$(".approver").hide();
}

function createEditor(){
    if(typeof(KindEditor) != "undefined") {
//    	KindEditor.ready(function(K) {
    		editor = KindEditor.create("#editor", {
    			height: "350px",
    			items: ["source", "|", "undo", "redo", "|", "preview", "print", "template", "cut", "copy", "paste", "plainpaste", "wordpaste", "|","justifyleft", "justifycenter", "justifyright", "justifyfull", "insertorderedlist", "insertunorderedlist", "indent", "outdent", "subscript", "superscript", "clearhtml", "quickformat", "selectall",/* "|", "fullscreen", "/",*/ "formatblock", "fontname", "fontsize", "|", "forecolor", "hilitecolor", "bold", "italic", "underline", "strikethrough", "lineheight", "removeformat", "|", "image", "flash", "media", "insertfile", "table", "hr", "emoticons", "map", "pagebreak", "link", "unlink"],
    			syncType: "form",
    			allowFileManager: true,
    			uploadJson: "/admin/file!ajaxUpload.action",
    			fileManagerJson: "/admin/file!ajaxBrowser.action",
    			afterChange: function() {
    				this.sync();
    			}
    		});
    		editor.html($("#editorContent").html());
    		$("#editorContent").html("");
    }
}


initData();

function showAndHideTr(){
	var val = $("#isUrlView").val();
	var tStep = $(".trueStep");
	var fStep = $(".falseStep:not('.controlhide')");
	if (parseInt(val)){
		tStep.show();
		fStep.hide();
	}else{
		tStep.hide();
		fStep.show();
	}
}

$(function(){
	createEditor();
	var name=$("#contractCategoryName").val();
	var whether=false;
	if (name==""){
		whether="/admin/contract_category!checkContractCategoryName.action";
	}
	initValidate(whether);
    _initEvents();
    createDefinitionWidget();
    initDisplay();
    $("#deparmentList").selectDepartment({
    	"content" : '#contentDepartment',
    	"source" : window.departmentJobList,
    	"title" : "部门选择",
    	"hidden_name" : "deparmentList.id"

    });
    
//    if($("#isFlow").val() == 1){
//		$("tr.undelete").show();
//	}else{
//		$("tr.undelete").hide();
//	}
    refreshFieldSelectOption();
    showAndHideTr();
});