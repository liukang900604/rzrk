<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>编辑模板 - Powered By UNICORN</title>
<meta name="Author" content="UNICORN Team" />
<meta name="Copyright" content="UNICORN" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link href="${base}/template/admin/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/admin/css/admin.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/jquery.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/base.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/admin.js"></script>
</head>
<body class="input">
	<div class="bar">
		 模板[${(templateConfig.description)!}]
	</div>
	<div class="body">
		<form action="template!update.action" method="post">
			<input type="hidden" class="formText" name="templateConfig.name" value="${templateConfig.name}" />
			<table class="inputTable">
                <tr>
                    <td width="80px" align="center">变量标签</td>
                    <td>${templateConfig.varDesc}</td>
                </tr>
                <tr>
                    <td width="80px" align="center">邮件标题</td>
                    <td><input class="formText"  name="templateConfig.subject" value="${templateConfig.subject}"></td>
                </tr>
				<tr>
                    <td width="80px" align="center">
                        邮件内容
                    </td>
					<td>
						<textarea name="templateConfig.mailTemplateContent" style="width: 100%; height: 250px; padding: 0px;">${templateConfig.mailTemplateContent?html}</textarea>
					</td>
				</tr>
                <tr>
                    <td width="80px" align="center">
                        短信内容
                    </td>
                    <td>
                        <textarea name="templateConfig.smsTemplateContent" style="width: 100%; height: 100px; padding: 0px;">${templateConfig.smsTemplateContent}</textarea>
                    </td>
                </tr>
                <tr>
                    <td width="80px" align="center">描述</td>
                    <td><input class="formText"  name="templateConfig.description" value="${templateConfig.description}"></td>
                </tr>
                <tr>
                    <td width="80px" align="center">是否启用</td>
                    <td><@checkbox name="templateConfig.enabled" value="${templateConfig.enabled}" />是</td>
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