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
    <script type="text/javascript" src="/rzrk/js/background/job_input.js"></script>
</head>
<body class="input admin easyui-layout">
	<div data-options="region:'center'">
		<div class="easyui-panel" title="岗位信息" data-options="fit:true,border:false">
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
		                <td>*岗位名称</td>
		                <td><input  type="text" name="job.jobName" value="${(job.jobName)!}"/></td>
		            </tr>
		            
		            <tr>
		                <td>岗位代码</td>
		                <td><input  type="text" name="job.jobCode" value="${(job.jobCode)!}"/></td>
		            </tr>
		            <tr>
		                <td>*排序号</td>
		                <td><input  type="text" name="job.sortNo" value="${(job.sortNo)!}"/></td>
		            </tr>
		            <tr>
		                <td>*重复序号处理</td>
		                <td>
							<input type="radio" name="job.duplicateSortDeal" value="1" <#if isEditAction && (job.duplicateSortDeal== 1)>checked="checked"</#if>/>插入
	                        <input type="radio" name="job.duplicateSortDeal" value="0" <#if isEditAction && (job.duplicateSortDeal== 0)>checked="checked"</#if>/>重复
						</td>
		            </tr>
		             <tr>
		                <td>是否为关键字</td>
		                <td>
	   	                    <select name="job.isKeyName" id="isKeyName">
								<option value="0"<#if isEditAction && job.isKeyName == 0>selected</#if>>否</option>
								<option value="1"<#if isEditAction && job.isKeyName == 1>selected</#if>>是</option>
							</select>
							<span style="color:red;">（是否为工作流程构建的关键字）</span>
		                </td>
		            </tr>
		            <tr class="undelete" style="display:none;">
		                <td><em>*</em>处理人</td>
	                	<td>
	                	<select name="job.adminId" id="adminId">
	                	  <#list adminList as admin>
	                		<option value="${admin.id}" <#if isEditAction && job.adminId == admin.id >selected="selected"</#if>>${admin.name}</option>
	                	  </#list>
	                	</select>
	                	<span style="color:#aaa;margin-left:10px;">支持首字母索引</span>
		                </td>
		            </tr>
		             <tr>
		                <td>*优先级</td>
		                <td><input  type="text" id="priorityLevel" maxlength="4" name="job.priorityLevel" value="${(job.priorityLevel)!}"/>
		                <span style="color:red;">数字越大，优先级越高</span>
		                </td>
		            </tr>
		            <tr>
		                <td>岗位类型</td>
		                <td>	
		                    <select name="job.jobType">
								<#list jobTypeList as type>
									<option value="${type}"<#if isEditAction && type == job.jobType> selected</#if>>
									${action.getText(type)}
									</option>
								</#list>
							</select>
		                </td>
						
		            </tr>
		            <tr>
		                <td>状态</td>
		                <td>
							<select name="job.status">
								<option value="1" <#if isEditAction && (job.status == 1)>selected</#if>>启用</option>
								<option value="0" <#if isEditAction && (job.status == 0)>selected</#if>>禁止</option>
							</select>
						</td>
		            </tr>
					<tr>
						<td>
							岗位描述 
						</td>
						<td>
							<textarea name="job.desciption"  class="formTextarea" style="width:280px; height:55px;">${(job.desciption)!}</textarea>
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