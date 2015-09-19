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
function _initEvents() {
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
		if(window.workType  == "1"){//立项流程
			if($("#projectName").val() == null  || $("#projectName").val() == ""){
				 msg.alert('项目名称',"项目名称不能为空，请输入",'info');
				 return false;
			}
			if($("#responsiborId").val() == null  || $("#responsiborId").val() == ""){
				 msg.alert('项目责任人',"项目责任人不能为空，请输入",'info');
				 return false;
			}
			if($("#requestId").val() == null  || $("#requestId").val() == ""){
				 msg.alert('需求选择',"请选择需求列表",'info');
				 return false;
			}
			
			if($("#personNumber").val() == null  || $("#personNumber").val() == ""){
				 msg.alert('投入人数',"投入人数不能为空，请输入",'info');
				 return false;
			}
			if($("#predictDay").val() == null  || $("#predictDay").val() == ""){
				 msg.alert('预计天数',"预计天数不能为空，请输入",'info');
				 return false;
			}
		}
		
		
		// 用textArea接收改动的结果
		$("#formContent").val(editor.html());
		$.ajax({
			type : 'post',
			url : '/admin/check!save.action',
			data : $('#atForm').serialize(),
			cache : false,
			dataType : 'json',
			success : function(data) {
				var obj = data;
				if (data.status == 'success') {
					msg.alert('保存信息', data.message, 'info', function() {
						window.top.reloadDataGridInTab('我的审批');
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
	$("#selectRequest").unbind('click').click(function() {
		if(window.workType == "1"){
			var url = "/admin/check!addRequirementList.action?isEdit=1&requestId="+$("#requestId").val();
			var ezWin = window.top.createWin({
				title : "需求选择"
			});
			var iframe = window.top.createIframe(url);
			iframe.appendTo(ezWin);
			ezWin.window("open");
			return false;
		}else if(window.workType == "2"){
			var url = "/admin/check!addBugList.action?isEdit=1&requestId="+$("#requestId").val();
			var ezWin = window.top.createWin({
				title : "bug列表选择"
			});
			var iframe = window.top.createIframe(url);
			iframe.appendTo(ezWin);
			ezWin.window("open");
			return false;
		}
		
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
	$("#joinProject").combobox({
		valueField: 'id',
        textField: 'text',
        data:window.belongProjectOpts
	});

};



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


function initDicts(users){
	$.each(users, function(i, user){
		letterIdx[user.name] = CTL.convert(user.name).toString();
	});
}
function filterName(q, row){
    var opts = $(this).combobox('options');
    if (row[opts.textField].toLocaleLowerCase().indexOf(q.toLocaleLowerCase()) != -1){
        return true;
    }
    if (letterIdx[row[opts.textField]].toLocaleLowerCase().indexOf(q.toLocaleLowerCase()) != -1){
        return true;
    }
    return false;
}
function selectUser(user){
	var lastLi = $(".addColleague");
	var btn = lastLi.find("a");
	var comboBox = btn.next();
	var li = '<li>{0}<span>X</span><input type="hidden" name="adminList.id" value="{1}"/></li>';
	li = $.validator.format(li, user.name, user.id);
	if (!unique[user.id]){
		$(li).insertBefore(lastLi);
	}
	unique[user.id] = true;
	btn.show();
	comboBox.combobox("setValue", "");
	comboBox.next().hide();
}

function initData(){
	var url = "/admin/admin!getAllAjaxList.action";
	$.get(url, function(data){
		initDicts(data.rows);

		$("select[name='workFlowVo.responsiborId']").combobox({
            filter: filterName
		});
	}, "json");
}
initData();
function initWorkFlowType() {
	
			$.ajax({
				type : 'post',
				url : '/admin/work!getAjaxDataByWorkFlowId.action',
				data : {
					flowTypeId : $("#flowTypeId").val()
				},
				cache : false,
				dataType : 'json',
				success : function(data) {
					$("#workFlowId").html("");
					for (var i = 0; i < data.workFlowList.length; i++) {
						$("#workFlowId").append(
								"<option value='" + data.workFlowList[i].id
										+ "'> " + data.workFlowList[i].flowName
										+ "</option>");
					}
					editor.html(data.content);//给 textarea赋值
					// 生成节点
					$("#pointId").html("");
					for (var i = 0; i < data.pointList.length; i++) {
						$("#pointId").append(
								"<option value='" + data.pointList[i].id
										+ "'> " + data.pointList[i].pointName
										+ "</option>");
					}

					$(".deleteAdminId").remove();
					// 生成申请人
					for (var i = 0; i < data.adminList.length; i++) {
						$("#adminListId")
								.prepend(
										"<li class='deleteAdminId' >"
												+ data.adminList[i].name
												+ "<span>X</span><input type='hidden'  name='adminList.id' value='"
												+ data.adminList[i].id
												+ "'/></li>");
					}
				}
			});
     }



      function initWorkFlow() {
			$.ajax({
				type : 'post',
				url : '/admin/work!getAjaxWorkFlowDataByWorkFlowId.action',
				data : {
					flowTypeId : $("#workFlowId").val()
				},
				cache : false,
				dataType : 'json',
				success : function(data) {
					//$(".form").html(data.content);// 覆盖表单内容
					editor.html(data.content);//给 textarea赋值

					// 生成节点
					$("#pointId").html("");
					for (var i = 0; i < data.pointList.length; i++) {
						$("#pointId").append(
								"<option value='" + data.pointList[i].id
										+ "'> " + data.pointList[i].pointName
										+ "</option>");
					}

					$(".deleteAdminId").remove();
					// 生成申请人
					for (var i = 0; i < data.adminList.length; i++) {
						$("#adminListId")
								.prepend(
										"<li class='deleteAdminId' >"
												+ data.adminList[i].name
												+ "<span>X</span><input type='hidden'  name='adminList.id' value='"
												+ data.adminList[i].id
												+ "'/></li>");
					}
				}

			});

}

function initWorkFlowPoint() {
	$
			.ajax({
				type : 'post',
				url : '/admin/work!getAjaxWorkFlowPointDataByWorkFlowId.action',
				data : {
					flowTypeId : $("#pointId").val()
				},
				cache : false,
				dataType : 'json',
				success : function(data) {
					$(".deleteAdminId").remove();
					// 生成申请人
					for (var i = 0; i < data.adminList.length; i++) {
						$("#adminListId")
								.prepend(
										"<li class='deleteAdminId' >"
												+ data.adminList[i].name
												+ "<span>X</span><input type='hidden'  name='adminList.id' value='"
												+ data.adminList[i].id
												+ "'/></li>");
					}
				}

			});

}



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

//function createEditor(){
//	editor = $("#editor");
//    if(typeof(KindEditor) != "undefined") {
//    	KindEditor.ready(function(K) {
//    		editor = K.create("#editor", {
//    			height: "350px",
//    			items: ["source", "|", "undo", "redo", "|", "preview", "print", "template", "cut", "copy", "paste", "plainpaste", "wordpaste", "|","justifyleft", "justifycenter", "justifyright", "justifyfull", "insertorderedlist", "insertunorderedlist", "indent", "outdent", "subscript", "superscript", "clearhtml", "quickformat", "selectall", "|", "fullscreen", "/", "formatblock", "fontname", "fontsize", "|", "forecolor", "hilitecolor", "bold", "italic", "underline", "strikethrough", "lineheight", "removeformat", "|", "image", "flash", "media", "insertfile", "table", "hr", "emoticons", "map", "pagebreak", "link", "unlink"],
//    			syncType: "form",
//    			allowFileManager: true,
//    			uploadJson: "/admin/file!ajaxUpload.action",
//    			fileManagerJson: "/admin/file!ajaxBrowser.action",
//    			 afterBlur:function(){  
//                     this.sync();  
//                 },
//    		afterChange: function() {
//				this.sync();
//			}
//    		});
//    	});
//    }
//}

function  initDevelopment(){
	if(window.workType == null || window.workType == ""){
		return false;
	}else{
		if(window.workType == "1"){//如果是立项流程
			var expand =  window.expand ;//json信息
			$("#projectName").val(expand.name);//项目名称
			$("#responsiborId").val(expand.responsiborId);//项目负责人
			$("#requestName").val(expand.requestName.join(","));//需求名称
			$("#requestId").val(expand.requestRowids.join(","));//需求id
			$("#personNumber").val(expand.personNumber);
			$("#predictDay").val(expand.predictDay);
			$("#projectDesc").val(expand.projectDesc);
			
		}
			
	}
	
}

$(function() {
	editor = new Editor($("#editor"));
	//initData();
	initValidate();
	_initEvents();
	initDevelopment();

});