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
    <link rel="stylesheet" href="/rzrk/css/background/admin_input_jstree.css" />
    <link rel="stylesheet" href="/rzrk/css/selectPosition.css" />
    <script type="text/javascript" src="/rzrk/js/alljs.js"></script>
    <script type="text/javascript" src="/rzrk/js/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="/rzrk/js/jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="/rzrk/js/jstree/jstree.min.js"></script>			
    <script type="text/javascript" src="/rzrk/js/background/index.js"></script>
    <script type="text/javascript" src="/rzrk/js/selectPos/single-select-department.js"></script>
    <script type="text/javascript" src="/rzrk/js/selectPos/layer.min.js"></script>
    <script type="text/javascript" src="${base}/template/common/editor/kindeditor.js"></script>



    <script type="text/javascript">
    	window.isAddAction = <#if isAddAction>true<#else>false</#if>;
    </script>
    <script type="text/javascript" src="/rzrk/js/background/systemmessage_input.js"></script>

 
    
</head>
<body class="input admin easyui-layout">
	<div data-options="region:'center'">
		<div class="easyui-panel" title="系统消息" data-options="fit:true,border:false" style="padding:5px 0 10px;">
			<form id="admForm" method="post">
				<input type="hidden" name="id" value="${id}"/>
		        <table cellpadding="5" style='width:100%;'>
		        	<colgroup>
		        		<col style="width:100px;"/>
		        		<col/>
		        	</colgroup>
		            <tr>
		                <td colspan="2" style="text-align: left;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="warnInfo">*为必填项</span></td>
		            </tr>
		            <tr>
		                <td>*信息标题</td>
		                <td><input  type="text" id="system" name="title" <#if isEditAction >disabled="true"</#if>value="${(systemMessage.title)!}"/></td>
		            </tr>
		            <tr>
		                <td>类型</td>
		                <td>
		                <select id="searchBy" name="type" style="width:90px;">
						<#list t as temp>
							<option value="${temp.name()}" <#if isEditAction && (deparment.deparmentLeader.id == temp.id)>selected</#if>>${temp.name()}</option>
						</#list>
						</select>
						</td>
		            </tr>
		            <tr>
		            	<td>提醒方式</td>
		            	<td><input type="checkbox"  value="1" name="systemMessage.isMsg"  />短信通知   <input type="checkbox" value="1" name="systemMessage.isEmail" />邮件通知</td>	
		            </tr>
					 <tr>
		                <td>*消息通知人</td>
		                <td>	             
		                	<ul class="userList">
		                	<#if isEditAction> 
			                	 <#list fieldList as type>
			                	<li>${type.name}<span>X</span><input type="hidden" name="adminList.id" value="${type.id}"/></li>
			                	</#list>
		                	</#if>
		                		<li class="addColleague">
		                			<a href="javascript:">添加选择人</a>
		                			<input class="userBox" style="width:100px;display:none;" />
	                			</li>
		                	</ul>
	                	</td>
		            </tr>
					<tr>
						<td>
							内容
						</td>
						<td>
							<textarea id="editor" name="context" class="editor">${(systemMessage.context)!}</textarea>
						</td>
					</tr>
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