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
    <link rel="stylesheet" href="/rzrk/css/background/file_upload.css" />
    <link rel="stylesheet" href="/rzrk/css/background/contract_category_input.css" />
    <link rel="stylesheet" href="/rzrk/css/background/admin_input.css" />
    <link rel="stylesheet" href="/rzrk/css/background/admin_input_jstree.css" />
    <link rel="stylesheet" href="/rzrk/css/selectPosition.css" />
    <script type="text/javascript" src="/rzrk/js/alljs.js"></script>
    <script type="text/javascript" src="/rzrk/js/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="/rzrk/js/jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="/rzrk/js/jstree/jstree.min.js"></script>	
     <script type="text/javascript" src="/rzrk/js/background/index.js"></script>
     <script type="text/javascript" src="/rzrk/js/selectPos/contract-department.js"></script>  
    <script type="text/javascript">
    	window.isAddAction = <#if isAddAction>true<#else>false</#if>;

    </script>
    <script type="text/javascript" src="/rzrk/js/background/ajaxfileupload.js"></script>
    <script type="text/javascript" src="/rzrk/js/background/project_category_input.js"></script>
      <link rel="stylesheet" href="/rzrk/css/selectPosition.css" />
    <script type="text/javascript" src="/rzrk/js/selectPos/layer.min.js"></script>
</head>
<body style="overflow:auto;">
	<div class="easyui-panel" title="模板" style="padding: 5px 0;width:100%;">
		<form id="atForm" method="post">
			<input type="hidden" name="id" value="${id}"/>
	        <table cellpadding="5" style='width:100%;'>
	        	<colgroup>
	        		<col style="width:80px;"/>
	        		<col/>
	        	</colgroup>              
	            <tr>
	                <td>模板名称</td>
	                <td>
							
		                <input type="text" name="projectCategory.name"  class="formText" value="${(projectCategory.name)!}" style="width:192px;" />
		             
 					   <#if isEditAction> <input type="hidden" id="projectCategory.id" name="projectCategory.id"eclass="formText" value="${(projectCategory.id)!}" /></#if> 	
	                </td>
	            </tr>
	            <tr>
	                <td>一级分类</td>
	                <td>
   	                    <select name="projectCategory.rootContractCategory.id">
   	                    <#if rootContractCategoryList??>
							<#list rootContractCategoryList as temp>
								<option value="${temp.id}"<#if isEditAction && temp.id == projectCategory.rootContractCategory.id> selected</#if>>
								${temp.name}
								</option>
							</#list>
							<#else>
	                   </#if>
						</select>
	                </td>
	                <tr>
					<td>
						备注
					</td>
					<td>
						<textarea name="projectCategory.remark"  class="formTextarea" style="width:280px; height:120px;" >${(projectCategory.remark)!}</textarea>
					</td>
				</tr>
	        </table>
	    </form>
	</div>
	<div style="padding:10px 0">
	    <a href="javascript:void(0)" data-options="iconCls:'icon-save'" class="easyui-linkbutton" id="saveBtn">&nbsp;保&nbsp;存&nbsp;&nbsp;</a>
	    &nbsp;&nbsp;
	    <a href="javascript:void(0)" data-options="iconCls:'icon-cancel'" class="easyui-linkbutton" id="closeBtn">&nbsp;返&nbsp;回&nbsp;&nbsp;</a>
	</div>
</body>
</html>