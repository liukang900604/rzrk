jQuery.validator.addMethod("username",function(value, element){return this.optional(element) || /^[\u0391-\uFFE5\w]+$/.test(value);},"只允许包含中文、英文、数字和下划线");
jQuery.validator.addMethod("positiveinteger", function(value, element) {var aint=parseInt(value); return aint>0&& (aint+"")==value;}, "请输入正整数.");  
var letterIdx = {};
var unique = {};
function initValidate(){
	var rules = {}, messages = {};
	rules = {
		'title': {
			required: true,
			minlength: 2,
			maxlength: 20
		}
	};
	messages = {
		'title': {
			required: "请输入标题",
			minlength: "名称必须大于等于2",
			maxlength: "名称必须小于等于20"
		},
	};
	if (window.isAddAction){
		rules['title'] = {
			required: true,
			minlength: 2,
			maxlength: 20,
			remote: "/admin/deparment!checkDeparmentName.action"
		};
		messages['title'] = {
			required: "不能为空，请输入",
			minlength: "名称必须大于等于2",
			maxlength: "名称必须小于等于20",
			remote: "消息标题已存在"
		};
	}
	$('#admForm').validate({
		ignore: "",
        errorElement: "span",
        errorPlacement: function(error, el) {
        	error.appendTo(el.parents('td'));
        },
        rules: rules,
        messages: messages
	});
};
function getunReadCount()
{	
	$.ajax({
		   type: "POST",
		   url: "/admin/systemmessage!getCount.action",
		   dataType:"json",
		   success: function(data){
			var addSystemmessage =  $(window.parent.document).find("#UnreadSystemmessage");
			addSystemmessage.text("消息("+data+")");
		   }
		});
}
function initEvents(){
	var admForm = $('#admForm');
	var title = document.getElementById('system').value;
	saveBtn = $("#saveBtn");
	closeBtn = $("#closeBtn");
	saveBtn.unbind('click').click(function(){
		var msg = window.top.$.messager;
		var url = "";
		if (title=="")
			url = "/admin/systemmessage!save.action";
		else
			url = "/admin/systemmessage!update.action";
		var _this = $(this);
		if (_this.hasClass('disabled') || !admForm.valid()) return false;
		_this.addClass('disabled').linkbutton({iconCls: 'icon-loading'});
		$.post(url, admForm.serialize(), function(resp){
			resp = eval("("+resp+")");
			if (resp.status == 'success'){
				getunReadCount();
				msg.alert('保存信息', '保存信息成功！', 'info', function(){
					window.top.reloadDataGridInTab('人员信息');
					window.top.closeCurrentWindow();
				});
			}else{
				msg.alert('保存信息', '保存信息发生错误：' + resp.message, 'error');
			}
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
	$("ul").on("click", "li:not(.addColleague) span", function() {
		var _this = $(this);
		delete unique[_this.next().val()];
		_this.parents("li").remove();
	});
	$(".addColleague a").click(function() {
		$(this).hide();
		$(this).next().next().show();
	});
};


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

function selectUser(admin) {
	var lastLi = $(".addColleague");
	var btn = lastLi.find("a");
	var comboBox = btn.next();
	var li = '<li class="deleteAdminId">{0}<span>X</span><input type="hidden"  name="adminList.id" value="{1}"/></li>';
	li = $.validator.format(li, admin.name, admin.id);
	if (!unique[admin.id]) {
		$(li).insertBefore(lastLi);
	}
	unique[admin.id] = true;
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

function initDicts(admins) {
	$.each(admins, function(i, admin) {
			letterIdx[admin.name] = CTL.convert(admin.name).toString();
		
	});
}
function createEditor(){
    if(typeof(KindEditor) != "undefined") {
    	KindEditor.ready(function(K) {
    		editor = K.create("#editor", {
    			height: "350px",
    			width: "800px",
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
initData();
$(function(){
	initValidate();
    initEvents();
    createEditor();
});