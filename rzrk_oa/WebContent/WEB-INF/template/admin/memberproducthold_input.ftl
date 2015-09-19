<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>添加客户持有产品 - Powered By rzrk</title>
<meta name="Author" content="rzrk Team" />
<meta name="Copyright" content="rzrk" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link href="${base}/template/admin/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/admin/css/admin.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/jquery.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.lSelect.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.validate.methods.js"></script>
<script type="text/javascript" src="${base}/template/common/datePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/base.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/admin.js"></script>
<script type="text/javascript">
$().ready(function() {

	var $validateForm = $("#validateForm");

	// 表单验证
	$validateForm.validate({
		rules : {
			"actionDate" : {
				required : true,
				rangelength : [ 1, 20 ],
				date : true
			},
			"holdLog.amount" : {
                required : true,
				rangelength : [ 1, 20 ],
				number : true
			}
		},
		messages : {
			"actionDate" : {
				required : "操作日期不能为空",
				rangelength : "操作日期过长",
                date : "必须为日期格式"
			},
			"holdLog.amount" : {
                required : "操作金额不能为空",
                rangelength : "操作金额过长",
                number : "操作金额必须为数字格式"
			}
		},
		errorElement : "span",
		success : function(success) {
			var tips = $("input[name=" + success.attr("htmlfor") + "]").parent().find("span[tips]");
			tips.attr("class", "jqVerifySuccess");
			tips.text("√");
			tips.show();
		},
		errorPlacement : function(error, element) {
			var tips = element.parent().find("span[tips]");
			tips.attr("class", "jqVerifyError");
			tips.text(error.text());
			tips.show();
		},
		submitHandler : function(form) {
			$(form).find(":submit").attr("disabled", true);
			form.submit();
		}
	});

});
</script>
</head>
<body class="input">
	<div class="bar">
		添加客户产品操作
	</div>
	<div class="body">
		<form id="validateForm" action="memberproducthold!save.action" method="post">
			<input type="hidden" name="holdLog.memberId" value="${memberId}" />
            <input type="hidden" name="holdLog.productId" value="${productId}" />
			<input type="hidden" name="totalAmount" value="${totalAmount}" />
			<table class="inputTable">
                <tr>
                    <th>客户名称: </th>
                    <td>${member.realName}</td>
                </tr>
                <tr>
                    <th>产品名称: </th>
                    <td>${product.name}</td>
                </tr>
                <tr>
                    <th>当前持有: </th>
                    <#if totalAmount>
                    <td>${totalAmount}</td>
                    <#else>
                    <td>未持有</td>
                    </#if>
                </tr>
                <tr>
                    <th>操作日期: </th>
                    <td>
                        <input type="text" name="actionDate" id="actionDate" onclick="WdatePicker()" class="formText" value="" readonly="readonly" />
                        <span class="requireField">* &nbsp; </span>
                        <span tips="tips" style="display: none;"></span>
                    </td>
                </tr>
                <tr>
                    <th>操作类型: </th>
                    <td>
                        <select name="holdLog.actionType">
                            <#if totalAmount>
                            <option value="add">追加</option>
                            <option value="sell">赎回</option>
                            <#else>
                            <option value="buy">申购</option>
                            </#if>
                        </select>
                    </td>
                </tr>
				<tr>
					<th>操作金额: </th>
					<td>
						<input name="holdLog.amount" maxlength="20" value="" />
                        <span class="requireField">* &nbsp; </span>
						<span tips="tips" style="display: none;"></span>
					</td>
				</tr>
			</table>
			<div class="buttonArea">
				<input type="submit" class="formButton" value="确  定" hidefocus />&nbsp;&nbsp;
				<input type="button" class="formButton" onclick="window.history.back(); return false;" value="返  回" hidefocus />
			</div>
		</form>
	</div>
</body>
</html>