<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>添加/编辑品牌圈 - Powered By UNICORN</title>
<meta name="Author" content="UNICORN Team" />
<meta name="Copyright" content="UNICORN" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link href="${base}/template/admin/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/admin/css/admin.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/jquery.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.validate.methods.js"></script>
<#--<script type="text/javascript" src="${base}/template/common/editor/kindeditor.js"></script>-->
<script type="text/javascript" src="${base}/template/admin/js/base.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/admin.js"></script>
    <script type="text/javascript" src="${base}/template/common/datePicker/WdatePicker.js"></script>
<script type="text/javascript">
$().ready(function() {

	var $validateErrorContainer = $("#validateErrorContainer");
	var $validateErrorLabelContainer = $("#validateErrorContainer ul");
	var $validateForm = $("#validateForm");
	
	// 表单验证
	$validateForm.validate({
		errorContainer: $validateErrorContainer,
		errorLabelContainer: $validateErrorLabelContainer,
		wrapper: "li",
		errorClass: "validateError",
		ignoreTitle: true,
		rules: {
			"brandCircle.name": "required"
		},
		messages: {
			"brandCircle.name": "请填写品牌圈名称"
		},
		submitHandler: function(form) {
			$(form).find(":submit").attr("disabled", true);
			form.submit();
		}
	});
	
})
</script>
</head>
<body class="input">
	<div class="bar">
		<#if isAddAction>添加品牌圈<#else>编辑品牌圈</#if>
	</div>
	<div id="validateErrorContainer" class="validateErrorContainer">
		<div class="validateErrorTitle">以下信息填写有误,请重新填写</div>
		<ul></ul>
	</div>
	<div class="body">
		<form id="validateForm" action="<#if isAddAction>brand_circle!save.action<#else>brand_circle!update.action</#if>" enctype="multipart/form-data" method="post">
			<input type="hidden" name="brandCircle.id" value="${id}" />
			<table class="inputTable">

				<tr>
					<th>
						品牌圈名称:
					</th>
					<td>
						<input type="text" name="brandCircle.name" class="formText" value="${(brandCircle.name)!}" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						描述:
					</th>
					<td>
						<input type="text" name="brandCircle.description" class="formText" value="${(brandCircle.description)!}" />
					</td>
				</tr>
                <tr>
                    <th>
                        开始时间:
                    </th>
                    <td>
                        <input type="text" name="brandCircle.startDate"  onclick="WdatePicker()" class="formText" value="${(brandCircle.startDate)!}" />
                    </td>
                </tr>
                <tr>
                    <th>
                        结束时间:
                    </th>
                    <td>
                        <input type="text" name="brandCircle.endDate"  onclick="WdatePicker()" class="formText" value="${(brandCircle.endDate)!}" />
                    </td>
                </tr>
                <tr class="noneHover goodsType">
                    <th>
                        商品品牌:
                    </th>
                    <td>
                        <div class="brandSelect">
                            <ul>
                            <#list allBrandList as brand>
                                <li>
                                    <label>
                                        <input type="checkbox" name="brandList.id" value="${brand.id}"<#if (brandCircle.brands.contains(brand))!> checked</#if> />${brand.name}
                                    </label>
                                </li>
                            </#list>
                            </ul>
                        </div>
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