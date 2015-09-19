var saveBtn = '';
var letterIdx = {};
var unique = {};
var editor = "";
function initValidate() {
	var rules = {}, messages = {};
	rules = {
		"work.workName" : {
			required : true
		}

	};
	messages = {
		"work.workName" : {
			required : "不能为空，请输入"
		}
	};
	$('#atForm').validate({
		ignore : "",
		errorElement : "span",
		errorPlacement : function(error, el) {
			error.appendTo(el.parents('td'));
		},
		rules : rules,
		messages : messages
	});
};
function initEvents() {
	var atForm = $('#atForm');
	saveBtn = $("#saveBtn");
	closeBtn = $("#closeBtn");
	saveBtn.unbind('click').click(function() {
		var msg = window.top.$.messager;
		var _this = $(this);
		if (_this.hasClass('disabled') || !atForm.valid()) {
			return false;
		}
		
		// 验证是否添加了审批人
		 if($("#pointId").val() != null && $("#pointId").val() != ""){
		 }else{ 
			 msg.alert('保存信息',"所选流程未定义节点,请定义节点",'info');
			 return false;
		 }
		 
		// 用textArea接收改动的结果
		$("#formContent").val(editor.html());
		$.ajax({
			type : 'post',
			url : '/admin/work!update.action',
			data : $('#atForm').serialize(),
			cache : false,
			dataType : 'json',
			success : function(data) {
				if (data.status == 'success') {
					msg.alert('保存信息', data.message, 'info', function() {
						window.top.reloadDataGridInTab('我的工作');
						window.top.reloadDataGridInWindow('backframe');
						window.top.closeCurrentWindow();
					});

				} else {
					msg.alert('保存信息', data.message, 'info', function() {
						return false;
					});
				}
			}

		});
	});
	closeBtn.unbind('click').click(function() {
		window.top.closeCurrentWindow();
	});
	
	$("#file").unbind('change').change(ajaxFileUpload);
	$(".deleteFile").unbind('click').click(deleteFile);
/*	$("ul").on("click", "li:not(.addColleague) span", function() {
		var _this = $(this);
		delete unique[_this.next().val()];
		_this.parents("li").remove();
	});
	$(".addColleague a").click(function() {
		$(this).hide();
		$(this).next().next().show();
	});*/

};

/**
 * 删除文件
 */
function deleteFile() {
	var _this = $(this);
	$
			.ajax({
				type : 'post',
				url : '/admin/file_upload!deleteloadFile.action',
				data : {
					downloadFilePath : $(this).attr("value")
				},
				cache : false,
				dataType : 'json',
				success : function(data) {
					if(data.status == "success"){
						$(_this).parent().parent().remove();//删除整行
					}
			
				}

			});

}


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
	var li = '<li class="deleteAdminId">{0}<span>X</span><input type="hidden"  name="adminList.id" value="{1}"/></li>';
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



  //编辑页面进来
   function initEditWorkFlowType() {
	$.ajax({
		type : 'post',
		url : '/admin/work!getAjaxData.action',
		data : {
			id : $("#workId").val(),flowTypeId : $("#flowTypeId").val(),workFlowId:$("#editWorkFlowId").val(),flowPointId:$("#editflowPointId").val()
		},
		cache : false,
		dataType : 'json',
		success : function(data) {
			$("#workFlowId").html("");
			for (var i = 0; i < data.workFlowList.length; i++) {
				if(data.workFlowList[i].id == $("#editWorkFlowId").val()){
					$("#workFlowId").append(
							"<option value='" + data.workFlowList[i].id
									+ "' selected> " + data.workFlowList[i].flowName
									+ "</option>");
				}else{
					$("#workFlowId").append(
							"<option value='" + data.workFlowList[i].id
									+ "'> " + data.workFlowList[i].flowName
									+ "</option>");
				}
				
			}
			   editor.html(data.content);//给 textarea赋值
			   
				if(data.pointList != null && data.pointList.length >= 1){
					// 生成节点
					$("#pointId").val(data.pointList[0].id);
				}else{
					$("#pointId").val("");
				}
			//}
			
			$("[name='work.workName']").attr("readonly","readonly");
			$("#flowTypeId").attr("disabled",true);
			$("#workFlowId").attr("disabled",true);
		
		}
	});
}


   function ajaxFileUpload(){
		
		$.messager.progress({
			"title":"",
			"msg":"导入中，请等待...",
			"text":""
		});
		var id = $("#file").attr('id');
		var url = $("#file").data('url');
		$.ajaxFileUpload({
			url: url,
			secureuri:false,
			fileElementId:id,
			dataType: 'json',
			success: function (data, status){
	            if(data.statu == "success"){
	            	var fileNames = data.fileFileName; //返回的文件名 
					var filePaths = data.filePath;     //返回的文件地址 
					for(var i=0;i<data.fileFileName.length;i++){
						//将上传后的文件 添加到页面中 以进行下载&downloadFileName=${file.fileName}
						$("#down").after("<tr><td height='25' width='100px'>"+fileNames[i]+
								"</td><td width='40px'><input type='hidden' name='fileName' value='"+fileNames[i]+"'/>" 
								+ "<input type='hidden' name='filePath' value='"+filePaths[i]+"'/>"+
									"<a href='file_upload!downloadFile.action?downloadFilePath="+filePaths[i]+"&downloadFileName="+fileNames[i]+"'>下载</a></td>" +"&nbsp;&nbsp;&nbsp;&nbsp;"+
								"<td width='40px'><a href='#' class='deleteFile' value='"+filePaths[i]+"'>删除</a></td>"+
								+"&nbsp;&nbsp;&nbsp;&nbsp;"+"<td width='40px'><a href='file_upload!showFile.action?downloadFilePath="+filePaths[i]+"&downloadFileName="+fileNames[i]+"' target ='_blank'>预览</a></td></tr>");
								
					}
					$(".deleteFile").unbind('click').click(deleteFile);
					msg.alert("上传文件", "上传文件成功！", "info", function(){
						  $("#file").unbind('change').change(ajaxFileUpload);
						  $.messager.progress('close');
					});
	             }else{
	            	 msg.alert("上传文件", "上传文件失败！" , "info");
	                 $("#file").unbind('change').change(ajaxFileUpload);
	                 $.messager.progress('close');
	             }
			}
		});
	}




$(function() {
	editor = new Editor($("#editor"));
	$(".contractCategory.cid").attr("readonly","readonly").css("border-style","dotted");
	//editor.html();
	//initData();
	initValidate();
	initEvents();
	
	

});