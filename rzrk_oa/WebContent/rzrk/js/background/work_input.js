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
function _initEvents() {
	$(".workFlowTree").combotree({
		width:260,
	    data: window.workFlowTree,
	    onSelect:function(node){
	    	var parent =$('.workFlowTree').combotree('tree').tree('getParent',node.target);
	    	if(parent!=null){
	    		if(node.id == $(".workFlowId").val()){
	    			return false;
	    		}
//		    	editor.html("");
		    	$(".flowTypeId").val(parent.id);
		    	$(".workFlowId").val(node.id);	
		    	initWorkFlow();
	    	}else{
	    		throw "cao";//故意报异常，让页面停止
	    	}
	    }
	});
	var atForm = $('#atForm');
	saveBtn = $("#saveBtn");
	closeBtn = $("#closeBtn");
	saveBtn.unbind('click').click(function() {
		$("#formContent").val(editor.html());
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
		
		$.ajax({
			type : 'post',
			url : '/admin/work!save.action',
			data : $('#atForm').serialize(),
			cache : false,
			dataType : 'json',
			success : function(data) {
				if (data.status == 'success') {
					msg.alert('保存信息', data.message, 'info', function() {
						window.top.reloadDataGridInTab("我的工作");
						window.top.reloadDataGridInTab("工作查询");
						window.top.closeTab('工作创建');
					});

				} else {
					msg.alert('保存信息', data.message, 'error', function() {
						return false;
					});
				}
			}
		});
	});
	closeBtn.unbind('click').click(function() {
		//window.top.closeCurrentWindow();
		window.top.closeTab('工作创建');
	});
	//文件上传
	$("#file").unbind('change').change(ajaxFileUpload);
	/*$("ul").on("click", "li:not(.addColleague) span", function() {
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

function initWorkFlowType() {
	if( $("#flowTypeId").val() == null ||  $("#flowTypeId").val() == ""){
		return false;
	}
	$
			.ajax({
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
										+ "' rel='" +data.workFlowList[i].isDelete + "'> " + data.workFlowList[i].flowName
										+ "</option>");
					}
					
					   
					    editor.html(data.content);//给 textarea赋值
					    $("#isInternal").val("1");
					    if(data.pointList != null && data.pointList.length >= 1){
							// 生成节点
							$("#pointId").val(data.pointList[0].id);
						}else{
							$("#pointId").val("");
						}
					    
						$("#workFlowShow").html("");//清空流程图
						var flowShowStr = "<ul class='flow'><li class='start'><span class='top'>发起人</span></li><li class='link'><hr/></li>";
						if(data.pointShowList != null && data.pointShowList != ""){
							for(var i = 0; i < data.pointShowList.length; i++){
								if(data.pointShowList[i].isBranch == 1){//分支节点
									var banchName = data.pointShowList[i].name;
									if(banchName == null || banchName == "" ){
										return false;
									}
									flowShowStr += "<li class='branch' data-params='"+data.pointShowList[i].showCondition +"' " +
									" pointid='"+data.pointShowList[i].id+"' >  <table> <tbody> ";
									var banchNameArray = banchName.split(",");
									for(var j = 0; j < banchNameArray.length; j++){
										flowShowStr += "<tr> <td> <img src='/rzrk/images/background/icons/menus.png' alt>"+banchNameArray[j]+"</td> </tr>";
									}
									flowShowStr += "</tbody> </table> </li> <li class='link'><hr></li>"
									// <span class='top' title='"+data.pointShowList[i].userName+"'> "+data.pointShowList[i].name+" </span>  <span class='bottom'></span>  </li>  ";
								}else{
									flowShowStr += "<li class='middle' data-ids='"+data.pointShowList[i].userId +"' data-keys='"+data.pointShowList[i].searchName+"' " +
											" pointid='"+data.pointShowList[i].id+"' > <span class='top' title='"+data.pointShowList[i].userName+"'> "+data.pointShowList[i].name+" </span>  <span class='bottom'></span>  </li> <li class='link'><hr></li>  ";
								}
								
								
								
							}
						}
						flowShowStr += "<li class='end'></li> </ul>";
						$("#workFlowShow").append(flowShowStr);	
					
				}

			});

}

function createEditor(){
	editor = $("#editor");
}

function initWorkFlow() {
	$
			.ajax({
				type : 'post',
				url : '/admin/work!getAjaxWorkFlowDataByWorkFlowId.action',
				data : {
					flowTypeId : $("#workFlowId").val()
				},
				cache : false,
				dataType : 'json',
				success : function(data) {
					//$(".form").html(data.content);// 覆盖表单内容
					 $("#pContent").html("");
					editor.html(data.content);//给 textarea赋值
					
						$("#isInternal").val("1");
						if(data.pointList != null && data.pointList.length >= 1){
							// 生成节点
							$("#pointId").val(data.pointList[0].id);
						}else{
							$("#pointId").val("");
						}
						
						$("#workFlowShow").html("");//清空流程图
						var flowShowStr = "<ul class='flow'><li class='start'><span>发起人</span></li><li class='link'></li>";
						if(data.pointShowList != null && data.pointShowList != ""){
							for(var i = 0; i < data.pointShowList.length; i++){
								if(data.pointShowList[i].isBranch == 1){//分支节点
									var banchName = data.pointShowList[i].userName;
									if(banchName == null || banchName == "" ){
										return false;
									}
									flowShowStr += "<li class='branch' >";
									var banchNameArray = banchName.split(",");
									for(var j = 0; j < banchNameArray.length; j++){
										flowShowStr += "<span>" + banchNameArray[j] + "</span>";
									}
									flowShowStr += "</li><li class='link'></li>"
									// <span class='top' title='"+data.pointShowList[i].userName+"'> "+data.pointShowList[i].name+" </span>  <span class='bottom'></span>  </li>  ";
								}else{
									flowShowStr += "<li class='middle' >";
									var names = data.pointShowList[i].userName.split(",");
									$.each(names, function(i, name){
										flowShowStr += "<span>" + name+ "</span>";
									});
									flowShowStr += "</li><li class='link'></li>";
								}
								
								
								
							}
						}
						flowShowStr += "<li class='end'><span>结束</span></li> </ul>";
						$("#workFlowShow").append(flowShowStr);	
					
				}

			});

}


$(function() {
	editor = new Editor($("#editor"));
	//initData();
	initValidate();
	_initEvents();
	

});