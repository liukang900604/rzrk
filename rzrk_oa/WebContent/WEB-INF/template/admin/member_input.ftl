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
    <link rel="stylesheet" href="/rzrk/css/background/admin_input.css" />
    <script type="text/javascript" src="/rzrk/js/alljs.js"></script>
    <script type="text/javascript" src="/rzrk/js/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="/rzrk/js/jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="/rzrk/js/background/index.js"></script>
    <script type="text/javascript">
    	window.isAddAction = <#if isAddAction>true<#else>false</#if>;
    </script>
    <script type="text/javascript" src="/rzrk/js/background/member_input.js"></script>
</head>
<body class="input admin easyui-layout">
	<div data-options="region:'center'">
		<div class="easyui-panel" title="客户信息" data-options="fit:true,border:false" style="padding:5px;">
			<form id="cstmForm" method="post">
				<input type="hidden" name="id" value="${id}"/>
		        <table cellpadding="5" style='width:100%;'>
		        	<colgroup>
		        		<col style="width:80px;"/>
		        		<col/>
		        	</colgroup>
		        	<#if isEditAction>
		            <tr>
		                <td colspan="2" style="text-align: left;"><span class="warnInfo">&nbsp;如需修改密码请填写密码，若留空则密码将保持不变！</span></td>
		            </tr>
		            </#if>
		            <tr>
		                <td>用户名</td>
		                <td>
			        	<#if isAddAction>
			                <input type="text" name="member.username"/>
		                <#else>
		                	${(member.username)!}
		                	<input type="hidden" name="member.username" value="${(member.username)!}" />
			            </#if>
		                </td>
		            </tr>
		            <tr>
		                <td>密　码</td>
		                <td><input type="text" id="password" name="member.password"/></td>
		            </tr>
		            <tr>
		                <td>真实姓名</td>
		                <td><input type="text" id="realName" name="member.realName" value="${(member.realName)!}"/></td>
		            </tr>
		            <tr>
		                <td>身份证</td>
		                <td><input type="text" name="member.userIdentification" value="${(member.userIdentification)!}"/></td>
		            </tr>
		            <tr>
		                <td>银行名称</td>
		                <td><input type="text" name="member.bank" value="${(member.bank)!}"/></td>
		            </tr>
		            <tr>
		                <td>银行账号</td>
		                <td><input type="text" name="member.bankAccount" value="${(member.bankAccount)!}"/></td>
		            </tr>
		            <tr>
		                <td>手机号</td>
		                <td><input type="text" name="member.mobile" value="${(member.mobile)!}"/></td>
		            </tr>
		            <tr>
		                <td>E-mail</td>
		                <td><input type="text" name="member.email" value="${(member.email)!}"/></td>
		            </tr>
		            <tr>
		                <td>地　址</td>
		                <td><input type="text" name="member.address" value="${(member.address)!}"/></td>
		            </tr>
		            <#if isEditAction>
		            <tr>
		                <td>注册日期</td>
		                <td>${(member.createDate?string("yyyy-MM-dd HH:mm:ss"))!}</td>
		            </tr>
					</#if>
		        </table>
		    </form>
		</div>
	</div>
	<div data-options="region:'south',border:false" style="height:45px;overflow:hidden;">
		<div style="padding:10px;">
		    <a href="javascript:void(0)" data-options="iconCls:'icon-save'" class="easyui-linkbutton" id="saveBtn">&nbsp;保&nbsp;存&nbsp;&nbsp;</a>
		    &nbsp;&nbsp;
		    <a href="javascript:void(0)" data-options="iconCls:'icon-cancel'" class="easyui-linkbutton" id="closeBtn">&nbsp;返&nbsp;回&nbsp;&nbsp;</a>
		</div>
	</div>
</body>
</html>