function initWorkFlow() {
	if($("#editWorkFlowId").val() == null || $("#editWorkFlowId").val() == ""){
		return false;
	}
	$
			.ajax({
				type : 'post',
				url : '/admin/work!getWorkFlowPointByFlowId.action',
				data : {
					flowTypeId : $("#editWorkFlowId").val(),version:$("#version").val()
				},
				cache : false,
				dataType : 'json',
				success : function(data) {
						$("#workFlowShow").html("");//清空流程图
						var flowPointId = window.flowPointId;//当前对象
						var checkSort =  window.checkSort;//节点序号
						var flowShowStr = null; 
							
						if(window.loginAdmin != null && window.loginAdmin != ""){
							flowShowStr = "<ul class='flow'><li class='start'><span>"+window.loginAdmin+"</span></li><li class='link'></li>";
						}else{
							flowShowStr = "<ul class='flow'><li class='start'><span>发起人</span></li><li class='link'></li>";
						}
						if(flowPointId == null || flowPointId == ""){
							if(data.pointShowList != null && data.pointShowList != ""){
								for(var i = 0; i < data.pointShowList.length; i++){
									var handePerson = checkSort[''+data.pointShowList[i].pointSort+''];//处理人
									if(data.pointShowList[i].isBranch == 1){//分支节点
										if(handePerson != null){
											flowShowStr += "<li class='middle'> <span> "+handePerson+" </span>  </li> <li class='link'></li>  ";
										}else{
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
											
										}
										
										// <span class='top' title='"+data.pointShowList[i].userName+"'> "+data.pointShowList[i].name+" </span>  <span class='bottom'></span>  </li>  ";
									}else{
										if(handePerson != null){
											flowShowStr += "<li class='middle' > <span> "+handePerson+" </span> </li> <li class='link'></li>  ";
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
							}
							
							var status = window.status;//当前工作状态
							if(status == 2){
								flowShowStr += "<li class='end current	'><span>结束</span></li> </ul>";
							}else{
							    flowShowStr += "<li class='end'><span>结束</span></li> </ul>";
							}
							
						}else{
							if(data.pointShowList != null && data.pointShowList != ""){
								for(var i = 0; i < data.pointShowList.length; i++){
									var handePerson = checkSort[''+data.pointShowList[i].pointSort+''];//处理人
									if(data.pointShowList[i].isBranch == 1){//分支节点
										if(handePerson != null){
											if(flowPointId == data.pointShowList[i].id ||  flowPointId == data.pointShowList[i].lastPointId){
												flowShowStr += "<li class='middle current' > <span> "+handePerson+" </span> </li> <li class='link'></li>  ";
											}else{
												flowShowStr += "<li class='middle' > <span> "+handePerson+" </span> </li> <li class='link'></li>  ";
											}
										}else{
											var banchName = data.pointShowList[i].userName;
											if(banchName == null || banchName == "" ){
												return false;
											}
											if(flowPointId == data.pointShowList[i].id || flowPointId == data.pointShowList[i].lastPointId){
												flowShowStr += "<li class='branch current' >";
												var banchNameArray = banchName.split(",");
												var emptyArr = [];
												for(var j = 0; j < banchNameArray.length; j++){
													if(emptyArr.indexOf(banchNameArray[j])==-1){
														emptyArr.push(banchNameArray[j]);
													}
												}
												for(var m=0;m<emptyArr.length;m++){
													flowShowStr += "<span>" + emptyArr[m] + "</span>";
												}
												//flowShowStr += "<span>" + banchNameArray[j] + "</span>";
												flowShowStr += "</li><li class='link'></li>"
											}else{
												flowShowStr += "<li class='branch' >";
												var banchNameArray = banchName.split(",");
												for(var j = 0; j < banchNameArray.length; j++){
													flowShowStr += "<span>" + banchNameArray[j] + "</span>";
												}
												flowShowStr += "</li><li class='link'></li>"
											}
											
										}
										
										
										// <span class='top' title='"+data.pointShowList[i].userName+"'> "+data.pointShowList[i].name+" </span>  <span class='bottom'></span>  </li>  ";
									}else{
										if(handePerson != null){
											if(flowPointId == data.pointShowList[i].id ||  flowPointId == data.pointShowList[i].lastPointId){
												flowShowStr += "<li class='middle current' > <span> "+handePerson+" </span> </li> <li class='link'></li>  ";
											}else{
												flowShowStr += "<li class='middle' > <span> "+handePerson+" </span> </li> <li class='link'></li>  ";
											}
										}else{
											if(flowPointId == data.pointShowList[i].id ||  flowPointId == data.pointShowList[i].lastPointId){
												flowShowStr += "<li class='middle current' >";
												var names = data.pointShowList[i].userName.split(",");
												$.each(names, function(i, name){
													flowShowStr += "<span>" + name+ "</span>";
												});
												flowShowStr += "</li><li class='link'></li>";
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
									
									
									
								}
							}
							
							   flowShowStr += "<li class='end'><span>结束</span></li> </ul>";
							
							
						}
					
						$("#workFlowShow").append(flowShowStr);	






				}

			});

}


$(function() {
	initWorkFlow();

});