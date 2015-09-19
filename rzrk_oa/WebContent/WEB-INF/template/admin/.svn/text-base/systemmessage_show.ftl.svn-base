<#assign sec=JspTaglibs["/WEB-INF/tld/security.tld"] />
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta content="IE=edge,chrome=1" http-equiv="X-UA-Compatible">
    <meta http-equiv="Cache-control" content="no-store">
    <title></title>
    <link rel="stylesheet" href="/rzrk/js/jquery-easyui-1.4.1/themes/icon.css" />
    <link rel="stylesheet" href="/rzrk/js/jquery-easyui-1.4.1/themes/metro/easyui.css" id="themes"/>
    <link rel="stylesheet" href="/rzrk/css/background/common.css" />
    <link rel="stylesheet" href="/rzrk/css/background/common.css" />
    <link rel="stylesheet" href="/rzrk/css/background/work_input.css" />
    <link rel="stylesheet" href="/rzrk/css/background/admin_input.css" />
    <script type="text/javascript" src="/rzrk/js/alljs.js"></script>
    <script type="text/javascript" src="/rzrk/js/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="/rzrk/js/jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js"></script>	
    <script type="text/javascript" src="/rzrk/js/background/index.js"></script>
    <script type="text/javascript" src="/rzrk/js/background/systemmessage_show.js"></script>

</head>
<body class="input admin">
	<div class="easyui-panel" title="系统消息" style="padding: 5px 0;width:100%;">
		<form id="admForm" method="post">
			<input type="hidden" name="id" value="${id}"/>
	        <table cellpadding="5" style='width:100%;'>
	        	<colgroup>
	        		<col style="width:100px;"/>
	        		<col/>
	        	</colgroup>
	        	<tr>
	                <td>消息发布者:</td>
	                <td><lable>${admin!}</lable></td>
	            </tr>
	            <tr>
	                <td>消息标题:</td>
	                <td><lable>${(systemMessage.title)!}</lable></td>
	            </tr>
				 
				<tr>
	                <td>发布时间:</td>
	                <td><lable>${(systemMessage.createDate)!}</lable></td>
	            </tr>
	            <tr>
	                <td>消息类型:</td>
	                <td>
					<lable>${messagetype?default('')}</lable>
					</td>
	            </tr>
				<tr>
					<td>
						消息内容:					</td>
					<td>
						<lable>${(systemMessage.context)!}</lable>
					</td>
				</tr>
				<tr>
					<td>
					状&nbsp;&nbsp;&nbsp;&nbsp;态:
					</td>
					<td>
						<lable>${redtype?default('')}</lable>
					</td>
				</tr>
	        </table>
	    </form>
	</div>
	<div style="padding:10px 0">
	    &nbsp;&nbsp;
	     <a href="javascript:void(0)"  a-options="iconCls:'icon-cancel'" class="easyui-linkbutton" id="closeBtn">&nbsp;返&nbsp;回&nbsp;&nbsp;</a>
	</div>	
</body>
</html>