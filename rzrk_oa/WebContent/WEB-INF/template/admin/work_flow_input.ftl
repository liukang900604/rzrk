<#assign sec=JspTaglibs["/WEB-INF/tld/security.tld"] />
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta content="IE=edge,chrome=1" http-equiv="X-UA-Compatible">
    <meta http-equiv="Cache-control" content="no-store">
    <title></title>
    <link rel="stylesheet" href="/rzrk/css/background/jQuery-contextMenu-1.6.6/jquery.contextMenu.css" />
    <link rel="stylesheet" href="/rzrk/js/jquery-easyui-1.4.1/themes/icon.css" />
    <link rel="stylesheet" href="/rzrk/js/jquery-easyui-1.4.1/themes/metro/easyui.css" id="themes"/>
    <link rel="stylesheet" href="/rzrk/css/background/poshytip-1.2/tip-darkgray/tip-darkgray.css" />
    <link rel="stylesheet" href="/rzrk/css/background/common.css" />
    <link rel="stylesheet" href="/rzrk/css/background/work_flow_input.css" />
    <script type="text/javascript" src="/rzrk/js/alljs.js"></script>
    <script type="text/javascript" src="/rzrk/js/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="/rzrk/js/jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="${base}/template/common/editor/kindeditor.js"></script>
	<script type="text/javascript" src="/rzrk/js/background/workflow_input.js"></script>
	 <script type="text/javascript">
	  window.fieldList = 
    	<#if jsonObj??>
			${jsonObj}
   		<#else>
			{}
		</#if>;
		window.personFieldList = 
    	<#if personObj??>
			${personObj}
   		<#else>
			{}
		</#if>;
		window.jobList = 
    	<#if jobObj??>
			${jobObj}
   		<#else>
			{}
		</#if>;
	 </script>
</head>
<body class="easyui-layout">
	<div data-options="region:'center'">
		<div class="easyui-panel" title="工作流流程" data-options="fit:true,border:false" style="padding:5px 0;">
			<form id="atForm" method="post">
				<input type="hidden" id="workFlowId" name="workFlow.id" value="${(workFlow.id)!}"/>
				<input type="hidden" id="userArray"  name="userArray" value=""/>
				<input type="hidden" id="pointArray" name="pointArray" value=""/>
				<input type="hidden" id="userNameArray" name="userNameArray" value=""/>
				<input type="hidden" id="nameArray" name="nameArray" value=""/>
				<input type="hidden" id="conditionArray" name="conditionArray" value=""/>
				<input type="hidden" id="banchPointArray" name="banchPointArray" value=""/>
				<input type="hidden" id="keyArray" name="searchArray" value=""/>
				<input type="hidden" id="formKeyArray" name="formKeyArray" value=""/>
				
		        <table cellpadding="5" style='width:100%;' class="tb">
		        	<colgroup>
		        		<col style="width:80px;"/>
		        		<col/>
		        	</colgroup>
		            <tr>
		                <td>流程名称</td>
		                <td><input type="text" name="workFlow.flowName" class="formText" value="${(workFlow.flowName)!}" /></td>
		            </tr>
		             <tr>
			                <td>流程表单</td>
			                <td>
			                    <select name="workFlow.flowForm.id" id="flowId" >
									<#list formList as form>
										<option value="${form.id}" <#if isEdit && form.id == workFlow.flowForm.id> selected</#if>>
										${form.formName}
										</option>
									</#list>
								</select>
								<span style="color:red;margin-left:10px;">修改表单会重置流程，请慎重</span>
							</td>
			         </tr>
			          <tr>
			                <td>流程类型</td>
			                <td>
			                    <select name="workFlow.flowType.id" >
									<#list typeList as type>
										<option value="${type.id}" <#if isEdit && type.id == workFlow.flowType.id> selected</#if>>
										${type.typeName}
										</option>
									</#list>
								</select>
							</td>
			         </tr>
			        
			         <tr id="report">
		                <td>流程简介</td>
		                <td><textarea id="editor" name="workFlow.flowContent" class="editor" value="">${(workFlow.flowContent)!}</textarea></td>
		            </tr>
		             <tr>
			         <td><input type="checkbox"  value="1" name="workFlow.isMsg"  <#if isEdit && workFlow.isMsg == 1 >checked="checked"</#if>  />短信通知</td>
			          <td><input type="checkbox" value="1" name="workFlow.isEmail"<#if isEdit &&  workFlow.isEmail == 1 >checked="checked"</#if> />邮件通知</td>
			         </tr>
		        </table>
		    </form>
		    
	    	<h3>提示：单击流程中的某个节点，可以增加节点、删除节点、设置节点属性。</h3>
		   
		    <div id="workFlowShow">
			    <ul class="flow">
					<li class="start">
						<span>发起人</span>
					</li>
					<li class="link"></li>
					<li class="end">
						<span>结束</span>
					</li>
				</ul>       
			</div>
	
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