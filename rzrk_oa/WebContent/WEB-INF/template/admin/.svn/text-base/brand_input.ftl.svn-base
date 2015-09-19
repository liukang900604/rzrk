<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>添加/编辑品牌 - Powered By UNICORN</title>
<meta name="Author" content="UNICORN Team" />
<meta name="Copyright" content="UNICORN" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link href="${base}/template/admin/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/admin/css/admin.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/jquery.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.validate.methods.js"></script>
<script type="text/javascript" src="${base}/template/common/editor/kindeditor.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/base.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/admin.js"></script>
<script type="text/javascript">
$().ready(function() {
	var $goodsCategoryDiv = $("#goodsCategoryDiv");

	$("#addGoodsCategoryButton").click( function() {
		<@compress single_line = true>
			var goodsCategorySelectHtml = 
			'<div><select name="goodsCategoryList.id">
				<option value="">请选择...</option>
				<#list goodsCategoryTreeList as goodsCategoryTree>
					<option value="${goodsCategoryTree.id}">
						<#if goodsCategoryTree.grade != 0>
							<#list 1..goodsCategoryTree.grade as i>
								&nbsp;&nbsp;
							</#list>
						</#if>
						${goodsCategoryTree.name}
					</option>
				</#list>
			</select><input class="delGoodsCategory" type="button" value="删除分类" /></div>';
		</@compress>
		
		$goodsCategoryDiv.append(goodsCategorySelectHtml);
	});
	
	$("#goodsCategoryDiv .delGoodsCategory").live("click", function() {
		var $this = $(this);
		$.dialog({type: "warn", content: "您确定要删除吗?", ok: "确 定", cancel: "取 消", modal: true, okCallback: deleteGoodsCategory});
		function deleteGoodsCategory() {
			$this.parent().remove();
		}
	});
	
	var $brandIsJoinPointPlan = $("#brandIsJoinPointPlan");
	var $brandVipPoint = $("#brandVipPoint");
	var inital = $brandIsJoinPointPlan.is(":checked");
	$brandVipPoint.attr("disabled", !inital);
	$brandIsJoinPointPlan.click(function() {
		$brandVipPoint.attr("disabled", !this.checked);
	});

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
			"goodsCategoryList.id": "required",
			"brand.name": "required",
			"logo": "imageFile",
			"brand.orderList": "digits",
			"brand.vipPoint": {
				required: "#brandIsJoinPointPlan:checked",
				digits: true
			}
		},
		messages: {
			"goodsCategoryList.id": "请选择商品分类",
			"brand.name": "请填写品牌名称",
			"logo": "LOGO格式错误",
			"brand.orderList": "排序必须为零或正整数",
			"brand.vipPoint": {
				required: "请填写VIP所需积分",
				digits: "VIP所需积分必须为零或正整数"
			}
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
		<#if isAddAction>添加品牌<#else>编辑品牌</#if>
	</div>
	<div id="validateErrorContainer" class="validateErrorContainer">
		<div class="validateErrorTitle">以下信息填写有误,请重新填写</div>
		<ul></ul>
	</div>
	<div class="body">
		<form id="validateForm" action="<#if isAddAction>brand!save.action<#else>brand!update.action</#if>" enctype="multipart/form-data" method="post">
			<input type="hidden" name="id" value="${id}" />
			<table class="inputTable">
				<tr>
					<th>
						商品分类: 
					</th>
					<td><div id="goodsCategoryDiv">
						<#if isAddAction>
						<div>
						<select name="goodsCategoryList.id">
							<option value="">请选择...</option>
							<#list goodsCategoryTreeList as goodsCategoryTree>
								<option value="${goodsCategoryTree.id}">
									<#if goodsCategoryTree.grade != 0>
										<#list 1..goodsCategoryTree.grade as i>
											&nbsp;&nbsp;
										</#list>
									</#if>
									${goodsCategoryTree.name}
								</option>
							</#list>
						</select>
						</div>
						<#else>
						<#assign goodsCategorySet = (brand.goodsCategorySet)! />
						<#list goodsCategorySet as goodsCategory>
						<div>
						<select name="goodsCategoryList.id">
							<option value="">请选择...</option>
							<#list goodsCategoryTreeList as goodsCategoryTree>
								<option value="${goodsCategoryTree.id}"<#if (goodsCategoryTree == goodsCategory)!> selected</#if>>
									<#if goodsCategoryTree.grade != 0>
										<#list 1..goodsCategoryTree.grade as i>
											&nbsp;&nbsp;
										</#list>
									</#if>
									${goodsCategoryTree.name}
								</option>
							</#list>
						</select>
						<#if goodsCategory_index gt 0><input class="delGoodsCategory" type="button" value="删除分类" /></#if>
						</div>
						</#list>
						</#if></div>
						<label class="requireField">*</label>
						<input id="addGoodsCategoryButton" type="button" value="添加分类" />
					</td>
				</tr>
				<tr>
					<th>
						品牌名称: 
					</th>
					<td>
						<input type="text" name="brand.name" class="formText" value="${(brand.name)!}" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						网址: 
					</th>
					<td>
						<input type="text" name="brand.url" class="formText" value="${(brand.url)!}" />
					</td>
				</tr>
				<tr>
					<th>
						LOGO: 
					</th>
					<td>
						<input type="file" name="logo" />
						<#if (brand.logoPath??)!>
							&nbsp;&nbsp;&nbsp;<a href="${base}${brand.logoPath}" target="_blank">查看</a>
						</#if>
					</td>
				</tr>
				<tr>
					<th>
						积分计划: 
					</th>
					<td>
						<label>
							<@checkbox id="brandIsJoinPointPlan" name="brand.isJoinPointPlan" value="${(brand.isJoinPointPlan)!true}" />加入积分计划
						</label>
					</td>
				</tr>
				<tr>
					<th>
						VIP所需积分: 
					</th>
					<td>
						<input id="brandVipPoint" type="text" name="brand.vipPoint" class="formText" value="${(brand.vipPoint)!}" title="只允许输入零或正整数" />
					</td>
				</tr>
				<tr>
					<th>
						排序: 
					</th>
					<td>
						<input type="text" name="brand.orderList" class="formText" value="${(brand.orderList)!}" title="只允许输入零或正整数" />
					</td>
				</tr>
				<tr>
					<th>
						介绍: 
					</th>
					<td>
						<textarea name="brand.introduction" id="editor" class="editor">${(brand.introduction)!}</textarea>
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