var saveBtn = '';
var letterIdx = {};
var unique = {};
var editor = "";
window.saveBtnNumber = 0;
window.denyBtnNumber = 0;
window.denyFirstBtnNumber = 0;
function initValidate(){
};
function initEvents(){
	var atForm = $('#atForm');
	saveBtn = $("#saveBtn");
	denyBtn = $("#denyBtn");
	denyFirstBtn = $("#denyFirstBtn");
	saveBtn.unbind('click').click(function(){
		
		var msg = window.top.$.messager;
		var _this = $(this);
		
		/*if($("#searchName").val() == null || $("#searchName").val() == "" ){
			if($("#editor.contractCategory.cfield").attr("builtsign") != null){
				var point = $("#editor.contractCategory.cfield [field='"+$("#searchName").val()+"']");
				if(point != null && point.length > 0){
					if(point.val() == ""){
						msg.alert('提示信息', "请选择"+$("#searchName").val(), 'info');
						return false;
					}
				}
			}
		}*/
		$("#formContent").val(editor.html());
		$.messager.confirm("确认", "你确定审批通过吗?", function (r) { 
			 if(r){
				 	window.saveBtnNumber ++; 
					if(window.saveBtnNumber > 1){
						return false;
					}
				 
					$.ajax({
						type : 'post',
						url : '/admin/work!updateWorkCheck.action',
						data : $('#atForm').serialize(),
						cache : false,
						dataType : 'json',
						success : function(data) {
							if (data.status == 'success') {
								msg.alert('保存信息', data.message, 'info', function() {
									window.top.reloadDataGridInTab('工作审批');
									window.top.reloadDataGridInTab('我的审批');
									window.top.reloadDataGridInTab('首页导航');
									window.top.reloadDataGridInWindow('backframe');
									window.top.closeCurrentWindow();
								});

							} else {
								msg.alert('保存信息', data.message, 'error', function() {
									return false;
								});
							}
						}

					});
			 }
			
		});
	
	});
	denyBtn.unbind('click').click(function(){
	
		var msg = window.top.$.messager;
		var _this = $(this);
		$("#formContent").val(editor.html());
		$.messager.confirm("确认", "你确定驳回到上一级吗?", function (r) { 
			 if(r){
				 	window.denyBtnNumber ++; 
					if( window.denyBtnNumber > 1){
						return false;
					}
				 
				 $.ajax({
						type : 'post',
						url : '/admin/work!quitWorkCheck.action?type=1',
						data : $('#atForm').serialize(),
						cache : false,
						dataType : 'json',
						success : function(data) {
							if (data.status == 'success') {
								msg.alert('保存信息', data.message, 'info', function() {
									window.top.reloadDataGridInTab('工作审批');
									window.top.reloadDataGridInTab('我的审批');
									window.top.reloadDataGridInWindow('backframe');
									window.top.closeCurrentWindow();
								});

							} else {
								msg.alert('保存信息', data.message, 'error', function() {
									return false;
								});
							}
						}

					});
			 }
			
		})
		
	});
	denyFirstBtn.unbind('click').click(function(){
		var msg = window.top.$.messager;
		var _this = $(this);
		$("#formContent").val(editor.html());
		$.messager.confirm("确认", "你确定驳回到发起人吗?", function (r) { 
			 if(r){
				    window.denyFirstBtnNumber ++; 
					if(window.denyFirstBtnNumber > 1){
						return false;
					}
					$.ajax({
						type : 'post',
						url : '/admin/work!quitWorkCheck.action?type=2',
						data : $('#atForm').serialize(),
						cache : false,
						dataType : 'json',
						success : function(data) {
							if (data.status == 'success') {
								msg.alert('保存信息', data.message, 'info', function() {
									window.top.reloadDataGridInTab('工作审批');
									window.top.reloadDataGridInTab('我的审批');
									window.top.reloadDataGridInWindow('backframe');
									window.top.closeCurrentWindow();
								});

							} else {
								msg.alert('保存信息', data.message, 'error', function() {
									return false;
								});
							}
						}

					});
			 }
			
		})
	
	});
	$("#selectRequest").unbind('click').click(function() {
		if(window.workType == "1"){
			var url = "/admin/check!addRequirementList.action?isEdit=1&requestId="+$("#requestId").val()+"&isView=1";
			var ezWin = window.top.createWin({
				title : "需求选择"
			});
			var iframe = window.top.createIframe(url);
			iframe.appendTo(ezWin);
			ezWin.window("open");
			return false;
		}
		
	});
/*	$("ul").on("click", "li:not(.addColleague) span", function(){
		var _this = $(this);
		delete unique[_this.next().val()];
		_this.parents("li").remove();
	});
	$(".addColleague a").click(function(){
		$(this).hide();
		$(this).next().next().show();
	});*/
};
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
	var li = '<li class="deleteAdminId">{0}<span>X</span><input type="hidden" name="adminList.id" value="{1}"/></li>';
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
		$(".userBox").combobox({
            filter: filterName,
			valueField: "id",
			textField: "name",
			data: data.rows,
			onSelect: selectUser,
			onLoadSuccess: function(){
				if ($(".addColleague a").is(":hidden")) return;
				$(this).next().hide();
			}
		});
	}, "json");
}
function initWorkFlowPoint() {
	//如果为空  页面停止不动
	var keyVal = $("#pointId").val();
	if(keyVal==undefined || keyVal=="" || keyVal==null){
		return false;
	}
	
	$.ajax({
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
//initData();
$(function(){
	editor = new Editor($("#editor"));
//	$(".contractCategory.cid").attr("type","hidden").after("<span>"+$(".contractCategory.cid").val()+"</span>");
	$(".contractCategory.cid").attr("readonly","readonly").css("border-style","dotted");
	//initValidate();
    initEvents();
    initWorkFlowPoint();
    initDevelopment();
  /*  $("#pointId").change(function() {
	initWorkFlowPoint();
	})*/
});