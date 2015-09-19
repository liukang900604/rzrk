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
    <link rel="stylesheet" href="/rzrk/css/background/work_flow_form_input.css" />
    <script type="text/javascript" src="/rzrk/js/alljs.js"></script>
    <script type="text/javascript" src="/rzrk/js/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="/rzrk/js/jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="${base}/template/common/editor/kindeditor.js"></script>
    <script type="text/javascript" src="/rzrk/js/background/ajaxfileupload.js"></script>
     <script type="text/javascript" src="/rzrk/js/background/workflowform_input.js"></script>
     <script>
        window.isAddAction = <#if isAddAction>true<#else>false</#if>;
        window.categoryTree=${categoryTree};
        window.contract_category_id_old=null;//用于检测插入的表单是否有变化
     </script>
     
</head>
<body class="input admin easyui-layout">
	<div data-options="region:'center'">
		<div class="easyui-panel" title="工作流表单信息" data-options="fit:true,border:false" style="padding:5px 0;">
			<form id="atForm" method="post">
	            <input type="hidden" name="id" value="${(workFlowForm.id)!}"/>
	            <input type="hidden" name="workFlowForm.id" value="${(workFlowForm.id)!}"/>
		        <table cellpadding="5" style='width:100%;'>
		        	<colgroup>
		        		<col style="width:80px;"/>
		        		<col/>
		        	</colgroup>
	                <tr>
	                    <td>表单名称</td>
	                    <td><input type="text" name="workFlowForm.formName" class="formText" value="${(workFlowForm.formName)!}" /></td>
	                </tr>
	                <tr>
	                    <td>字段选择</td>
	                    <td>
	                        <table>
	                            <tr>
	                                <td>
	                                <input class="categoryTree" />
	                                <input type="hidden" class="categoryRoot" />
	                                <input type="hidden" class="categoryChild" />
	                                <!--
	                                    <select class="categoryRoot">
	                                    </select>
	                                    -->
	                                </td>
	                                <td>
	                                <!--
	                                    <select class="categoryChild">
	                                    </select>
	                                    -->
	                                </td>
	                                <td>
	                                    <select class="categoryFiled">
	                                    </select>
	                                </td>
	                                <td>
	                                    <a href="javascript:void(0)" data-options="iconCls:'icon-add'" class="easyui-linkbutton" id="insertLabel">
	                                                                                                                                         插入标题
	                                    </a>
	                                    <a href="javascript:void(0)" data-options="iconCls:'icon-add'" class="easyui-linkbutton" id="insertField">
	                                                                                                                                         插入输入框
	                                    </a>
	                                </td>
	                            </tr>
	                        </table>
	                    </td>
	                </tr>
		            <tr id="report">
		                <td>内　容</td>
		                <td><textarea id="editor" name="workFlowForm.formContent" class="editor" value=""></textarea>
	                        <div id="editorContent" style="display:none">${(workFlowForm.formContent)!}</div>
		                </td>
		            </tr>
		        </table>
		    </form>
		</div>
	</div>
	<div data-options="region:'south',border:false" style="height:40px;overflow:hidden;">
		<div style="padding:10px;">
		    <a href="javascript:void(0)" data-options="iconCls:'icon-save'" class="easyui-linkbutton" id="saveBtn">&nbsp;保&nbsp;存&nbsp;&nbsp;</a>
		    &nbsp;&nbsp;
		    <a href="javascript:void(0)" data-options="iconCls:'icon-cancel'" class="easyui-linkbutton" id="closeBtn">&nbsp;返&nbsp;回&nbsp;&nbsp;</a>
		</div>
	</div>
</body>
</html>