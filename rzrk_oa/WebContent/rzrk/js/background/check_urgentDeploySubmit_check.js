var saveBtn = '';
var letterIdx = {};
var unique = {};
var editor = "";
function initValidate() {
	var rules = {}, messages = {};
	rules = {
		"work.workName" : {
			required : true
		},
		"work.workFlow.id" : {
			required : true
		},
		"work.flowType.id" : {
			required : true
		}

	};
	messages = {
		"work.workName" : {
			required : "不能为空，请输入"
		},
		"work.workFlow.id" : {
			required : "不能为空，请输入"
		},
		"work.flowType.id" : {
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
			url : '/admin/check!save.action',
			data : $('#atForm').serialize(),
			cache : false,
			dataType : 'json',
			success : function(data) {
				if (data.status == 'success') {
					msg.alert('保存信息', data.message, 'info', function() {
						window.top.reloadDataGridInTab("我的审批");
						window.top.closeTab('紧急部署提交审批');
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
		window.top.closeTab('紧急部署提交审批');
	});
	//文件上传
	$("#file").unbind('change').change(ajaxFileUpload);
	$("#selectRequest").unbind('click').click(function() {
		var url = "/admin/check!addRequirementList.action";
		var ezWin = window.top.createWin({
			title : "需求选择"
		});
		var iframe = window.top.createIframe(url);
		iframe.appendTo(ezWin);
		ezWin.window("open");
		return false;
	});

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



function createEditor(){
	editor = $("#editor");
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
}



$(function() {
	editor = new Editor($("#editor"));
	initValidate();
	initEvents();

});