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
    <script type="text/javascript" src="/rzrk/js/background/job_level_input.js"></script>
</head>
<body class="input admin easyui-layout">
	<div data-options="region:'center'">
		<div class="easyui-panel" title="职务级别信息" data-options="fit:true,border:false">
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
		                <td>级别名称</td>
		                <td><input  type="text" name="jobLevel.name" <#if isEditAction >disabled="true"</#if>value="${(jobLevel.name)!}"/></td>
		            </tr>
		            <tr>
		                <td>职务级别代码</td>
		                <td><input  type="text" name="jobLevel.code" value="${(jobLevel.code)!}"/></td>
		            </tr>
		            <tr>
		                <td>*排序号</td>
		                <td><input  type="text" name="jobLevel.sortNo" value="${(jobLevel.sortNo)!}"/></td>
		            </tr>
		            <tr>
		                <td>*重复序号处理</td>
		                <td>
							<input type="radio" name="jobLevel.duplicateSortDeal" value="1" <#if isEditAction && (jobLevel.duplicateSortDeal== 1)>checked="checked"</#if>/>插入
	                        <input type="radio" name="jobLevel.duplicateSortDeal" value="0" <#if isEditAction && (jobLevel.duplicateSortDeal== 0)>checked="checked"</#if>/>重复
						</td>
		            </tr>
		            <tr>
		                <td>级别状态</td>
		                <td>
							<select name="jobLevel.status">
								<option value="1" <#if isEditAction && (jobLevel.status == 1)>selected</#if>>启用</option>
								<option value="0" <#if isEditAction && (jobLevel.status == 0)>selected</#if>>禁止</option>
							</select>
						</td>
		            </tr>
		            
					<tr>
						<td>
							岗位描述 
						</td>
						<td>
							<textarea name="jobLevel.description"  class="formTextarea" style="width:280px; height:55px;">${(jobLevel.description)!}</textarea>
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