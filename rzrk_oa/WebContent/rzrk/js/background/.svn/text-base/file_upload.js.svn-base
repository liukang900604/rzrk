var msg = window.top.$.messager;
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

/**
 * 加载流程图(特殊流程)
 * @returns {Boolean}
 */
function initWorkFlow() {
	if($("#workFlowId").val() == null || $("#workFlowId").val() == ""){
		return false;
	}
	$
			.ajax({
				type : 'post',
				url : '/admin/check!getAjaxWorkFlowDataByWorkFlowId.action',
				data : {
					flowTypeId : $("#workFlowId").val()
				},
				cache : false,
				dataType : 'json',
				success : function(data) {
					editor.html(data.content);//给 textarea赋值
					
						$("#isInternal").val("2");
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


$(function(){

    initWorkFlow();
});


