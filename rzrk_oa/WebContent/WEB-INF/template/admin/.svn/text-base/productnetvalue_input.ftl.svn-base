<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${command}产品净值信息 - Powered By rzrk</title>
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
			"pnv.date" : {
				required : true,
				rangelength : [ 1, 20 ],
				dateISO : true
			},
			"pnv.trustValue" : {
                required : true,
				rangelength : [ 1, 20 ],
				number : true
			},
            "pnv.trustValueStart" : {
                required : true,
                rangelength : [ 1, 20 ],
                number : true
            }
			<!--
			"pnv.trustValueAdd" : {
				rangelength : [ 1, 20 ],
                number : true
			},
			"pnv.growthRateMonth" : {
				rangelength : [ 1, 20 ],
                number : true
			},
			"pnv.growthRateMonth" : {
				rangelength : [ 1, 20 ],
                number : true
			}
			-->
		},
		messages : {
			"pnv.date" : {
				required : "日期不能为空",
				rangelength : "日期过长",
                dateISO : "必须为日期格式"
			},
			"pnv.trustValue" : {
                required : "信托单位净值不能为空",
                rangelength : "信托单位净值过长",
                number : "必须为数字格式"
			}
            <#--
			"pnv.trustValueAdd" : {
                rangelength : "信托单位累计值不能为空",
                number : "必须为数字格式"
			},
			"pnv.growthRateMonth" : {
                rangelength : "当月增长率不能为空",
                number : "必须为数字格式"
			},
			"pnv.growthRateMonth" : {
                rangelength : "累计增长率不能为空",
                number : "必须为数字格式"
			}
			-->
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
		${command}产品净值
	</div>
	<div class="body">
		<form id="validateForm" action="<#if isAddAction>productnetvalue!save.action<#else>productnetvalue!update.action</#if>" method="post">
			<input type="hidden" name="id" value="${id}" />
			<input type="hidden" name="pnv.productId" value="${product.id}" />
			<table class="inputTable">
                <tr>
                    <th>产品名称: </th>
                    <td>${product.name}</td>
                </tr>
                <tr>
                    <th>日期类型: </th>
                    <td>
                        <select name="pnv.dateType">
                            <#if ifExist>
                            <option value="100">估值日</option>
                            <#else>
                            <option value="103">成立日</option>
                            </#if>
                        </select>
                    </td>
                </tr>
				<tr>
					<th>日  期: </th>
					<td>
						<input name="pnv.date" maxlength="20" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" value="${(pnv.date)!}" /><span>(格式:yyyy-MM-dd)</span>
						<span class="requireField">* &nbsp; </span>
						<span tips="tips" style="display: none;"></span>
					</td>
				</tr>
				<tr>
					<th>信托单位净值: </th>
					<td>
						<input name="pnv.trustValue" maxlength="20" value="${(pnv.trustValue)!}" />
                        <span class="requireField">* &nbsp; </span>
						<span tips="tips" style="display: none;"></span>
					</td>
				</tr>
				<#--
				<tr>
					<th>信托单位累计值: </th>
					<td>
						<input name="pnv.trustValueAdd" maxlength="20" value="${(pnv.trustValueAdd)!}" />
						<span tips="tips" style="display: none;"></span>
					</td>
				</tr>
				<tr>
					<th>当月增长率: </th>
					<td>
						<input name="pnv.growthRateMonth" maxlength="20" value="${(pnv.growthRateMonth)!}" />
						<span tips="tips" style="display: none;"></span>
					</td>
				</tr>
				<tr>
					<th>累计增长率: </th>
					<td>
						<input name="pnv.growthRateAdd" maxlength="20" value="${(pnv.growthRateAdd)!}" />
						<span tips="tips" style="display: none;"></span>
					</td>
				</tr>
				-->
                <tr>
                    <th>指定累计净值: </th>
                    <td>
                        <input name="pnv.trustValueStart" maxlength="20" value="${(pnv.trustValueStart)!}" /><span>(此值将影响此后累计净值计算)</span>
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