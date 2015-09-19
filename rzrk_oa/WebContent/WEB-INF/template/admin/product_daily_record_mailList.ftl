<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>人员列表 - Powered By UNICORN</title>
<meta name="Author" content="UNICORN Team" />
<meta name="Copyright" content="UNICORN" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link href="${base}/template/admin/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/admin/css/admin.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="${base}/template/common/js/jquery.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.pager.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/base.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/admin.js"></script>
<script type="text/javascript" src="${base}/template/common/datePicker/WdatePicker.js"></script>


<script type="text/javascript" src="http://www.phpletter.com/contents/ajaxfileupload/ajaxfileupload.js"></script>

</head>
<body class="list">
	<div class="bar">
		产品收件人列表&nbsp;总记录数: ${pager.totalCount} (共${pager.pageCount}页)
	</div>
	<div class="body">
		<form id="listForm" action="product_daily_record!list.action" method="post">
		<img id="loading" src="/images/loading.gif" style="display:none;">
			<div class="listBar">
				<input type="button" class="formButton" onclick="location.href='product_daily_record!addMailReceiver.action'" value="添加收件人" hidefocus />
				<label>每页显示: </label>
				<select name="pager.pageSize" id="pageSize">
					<option value="10"<#if pager.pageSize == 10> selected</#if>>
						10
					</option>
					<option value="20"<#if pager.pageSize == 20> selected</#if>>
						20
					</option>
					<option value="50"<#if pager.pageSize == 50> selected</#if>>
						50
					</option>
					<option value="100"<#if pager.pageSize == 100> selected</#if>>
						100
					</option>
				</select>
			</div>
			<table id="listTable" class="listTable">
				<tr>
					<th class="check">
						<input type="checkbox" class="allCheck" />
					</th>
					<th>
						<a href="#" class="sort" name="stockAccountTotalAmount" hidefocus>产品名称</a>
					</th>
					<th>
						<a href="#" class="sort" name="futureAccountTotalAmount" hidefocus>邮件地址</a>
					</th>
					<th>
						<span>操作</span>
					</th>
				</tr>
				<#list pager.result as temp>
					<tr>
						<td>
							<input type="checkbox" name="ids" value="${temp.id}" />
						</td>
						<td>
							${temp.productName}
						</td>
						<td>
							${(temp.mailaddress)!}
						</td>
						<td>
							<a href="product_daily_record!editmail.action?id=${temp.id}" title="编辑">[编辑]</a>
						</td>
					</tr>
				</#list>
			</table>
			<#if (pager.result?size > 0)>
				<div class="pagerBar">
					<div class="delete">
						<input type="button" id="deleteButton" class="formButton" url="product_daily_record!deletemail.action" value="删 除" disabled hidefocus />
					</div>
					<div class="pager">
						<#include "/WEB-INF/template/admin/pager.ftl" />
					</div>
				<div>
			<#else>
				<div class="noRecord">没有找到任何记录!</div>
			</#if>
		</form>
	</div>
</body>
</html>