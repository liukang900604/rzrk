<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>日志列表 - Powered By UNICORN</title>
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
</head>
<body class="list">
	<div class="bar">
		日志列表&nbsp;总记录数: ${pager.totalCount} (共${pager.pageCount}页)
	</div>
	<div class="body">
		<form id="listForm" action="mail_sms!list.action" method="post">
			<div class="listBar">
                <label>接收者: </label>
                <input type="text" name="search.keyword" value="${search.keyword}" />
                <label>开始时间: </label>
                <input onclick="WdatePicker()" type="text" name="search.startDate" value="${search.startDate}" />
                <label>结束时间: </label>
                <input onclick="WdatePicker()" type="text" name="search.endDate" value="${search.endDate}" />
                <label>状态: </label>
				<select name="search.status">
                    <option value="">

                    </option>
					<option value="success"<#if search.status == "success"> selected</#if>>
						成功
					</option>
					<option value="error"<#if search.status == "error"> selected</#if>>
                        失败
					</option>
				</select>
                <label>类型: </label>
                <select name="search.type">
                    <option value="">

                    </option>
                    <option value="email"<#if search.type== "email"> selected</#if>>
                        邮件
                    </option>
                    <option value="sms"<#if search.type == "sms"> selected</#if>>
                        短信
                    </option>
                </select>
				<input type="button" id="searchButton" class="formButton" value="搜 索" hidefocus />
				&nbsp;&nbsp;
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
					<#--<th class="check">-->
						<#--<input type="checkbox" class="allCheck" />-->
					<#--</th>-->
					<th>
						<a href="#" class="sort" name="receiver" hidefocus>接收者</a>
					</th>
					<th>
						<a href="#" class="sort" name="member" hidefocus>会员</a>
					</th>
					<th width="55%">
						<a href="#" hidefocus>主题</a>
					</th>
                    <th>
                        <a href="#" class="sort" name="status" hidefocus>状态</a>
                    </th>
                    <th>
                        <a href="#" class="sort" name="type" hidefocus>类型</a>
                    </th>
                    <th>
                        <a href="#" class="sort" name="createDate" hidefocus>发送时间</a>
                    </th>
				</tr>
				<#list pager.result as log>
					<tr>
						<#--<td>-->
							<#--<input type="checkbox" name="ids" value="${log.id}" />-->
						<#--</td>-->
						<td>
						    ${log.receiver}
                        </td>
						<td>
							<#if log.member != null>${log.member.username}</#if>
						</td>
						<td>
                            ${log.subject}
                        </td>
						<td>
                            <#if log.status == 'error'>
							    <span style="color: red">${action.getText("common." + log.status)}</span>
                            <#else>
                                <span style="color: green">${action.getText("common." + log.status)}</span>
                            </#if>
						</td>
                        <td>
                            <#if log.type == 'email'>
                                <span>${action.getText("type." + log.type)}</span>
                            <#else>
                                <span style="color: blue">${action.getText("type." + log.type)}</span>
                            </#if>
                        </td>
						<td>
							<span title="${log.createDate?string("yyyy-MM-dd HH:mm:ss")}">${log.createDate}</span>
						</td>
					</tr>
				</#list>
			</table>
			<#if (pager.result?size > 0)>
				<div class="pagerBar">
					<#--<div class="delete">-->
						<#--<input type="button" id="deleteButton" class="formButton" url="log!delete.action" value="删 除" disabled hidefocus />-->
					<#--</div>-->
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