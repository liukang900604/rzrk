var msg = window.top.$.messager;

/**
 * 
 * @param obj   父页面的触发元素
 * @param id    父页面插入的id
 * @param name  父页面插入的value
 * @param page  返回的父页面id或者标签
 * @param key   操作页面返回内容字段
 * @param contractCategoryName 二级分类名称
 * @param  isEdit 新增或者编辑  1：编辑  0：新增
 */
function selectData(idItem,nameItem,page,key,contractCategoryName,isEdit){
		var url = "/admin/page!getDataList.action?idItem="+idItem+"&nameItem="+nameItem+"&page="+page+"&key="+key+"&contractCategoryName="+contractCategoryName+"&isEdit="+isEdit+"&requestId="+$("#"+idItem).val();
		var ezWin = window.top.createWin({
			title : "列表选择"
		});
		var iframe = window.top.createIframe(url);
		iframe.appendTo(ezWin);
		ezWin.window("open");
		return false;
	
}





