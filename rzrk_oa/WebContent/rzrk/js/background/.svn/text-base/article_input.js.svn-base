var msg = window.top.$.messager;
var editor = "";
function ajaxFileUpload(){
	/*
	$("#loading").ajaxStart(function(){
		$(this).show();
	}).ajaxComplete(function(){
		$(this).hide();
	});
	*/
	var _this = $(this);
	var id = _this.attr("id");
	var url = "/admin/upload!imageFileUpload.action";
	var input = _this.siblings("input");
	var aLink = $("<a target='_blank' style='margin-left:10px;'>查看</a>");
	var aLinks = _this.siblings("a");
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
				msg.alert("上传文件", "上传文件成功！", "info");
				aLinks.remove();
				input.val(data.message);
				$('#' + id).after(aLink.attr('href', data.message));
             }else{
 				msg.alert("上传文件", "上传文件失败：" + data.message, "error");
             }
		},
		error: function (data, status, e){
			msg.alert("上传文件", "上传文件失败：" + e, "error");
		}
	});
	return false;
}
function initValidate(){
	var rules = {}, messages = {};
	rules = {
		"article.title": "required",
		"article.articleCategory.id": "required"
	};
	messages = {
		"article.title": "不能为空，请输入",
		"article.content": "不能为空，请输入",
		"article.articleCategory.id": "不能为空，请输入"
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
    	KindEditor.ready(function(K) {
    		editor = K.create("#editor", {
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
    	});
    }
}
function articleCategoryChange(){
    var ids = document.getElementById("articleCategory");
    var id1 = ids.selectedIndex;
    var report = ids.options[id1].value;
    if(report=="402881e6414ab83301414abe8eae0005"){
       $("#report").hide();
       $("#pic").hide();
       $("#file").show();
    }else{
       $("#pic").show();
       $("#report").show();
       $("#file").hide();
    }
    if(report == "402881e6414ab83301414abe14050003"){
    	$("#types").show();
    }else{
    	$("#types").hide();
    }
}
function typeIdChange(){
    var selectIds = document.getElementById("typeId");
    var index = selectIds.selectedIndex;
    var typeId = selectIds.options[index].value;
    if (typeId == "1"){
    	$("#products").show();
    } else{
    	$("#products").hide();
    }
}
function initEvents(){
	var atForm = $('#atForm');
	saveBtn = $("#saveBtn");
	closeBtn = $("#closeBtn");
	saveBtn.unbind('click').click(function(){
		var msg = window.top.$.messager;
		var url = "";
		if (window.isAddAction)
			url = "/admin/article!save.action";
		else
			url = "/admin/article!update.action";
		var _this = $(this);
		if (_this.hasClass('disabled') || !atForm.valid()) return false;
		_this.addClass('disabled').linkbutton({iconCls: 'icon-loading'});
		$.post(url, atForm.serialize(), function(resp){
			msg.alert('保存信息', '保存信息成功！', 'info', function(){
				window.top.closeCurrentWindow();
			});
			window.top.reloadDataGridInTab('文章管理');
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
	$("#articleCategory").change(articleCategoryChange);
	$("#typeId").change(typeIdChange);
	$('body').on('change', '.fileUpload', ajaxFileUpload);
};
$(function(){
	createEditor();
	initValidate();
    initEvents();
    $("#articleCategory").change();
	$("#typeId").change();
});