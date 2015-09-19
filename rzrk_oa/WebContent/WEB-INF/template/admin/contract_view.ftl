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
    <link rel="stylesheet" href="/rzrk/css/background/work_input.css" />
    <link rel="stylesheet" href="/rzrk/css/background/admin_input.css" />
    <link rel="stylesheet" href="/rzrk/css/background/admin_input_jstree.css" />
    <link rel="stylesheet" href="/rzrk/css/background/departmentTree.css" />
    <link rel="stylesheet" href="/rzrk/css/background/contract_input.css" />
    <script type="text/javascript" src="/rzrk/js/alljs.js"></script>
    <script type="text/javascript" src="/rzrk/js/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="/rzrk/js/jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="/rzrk/js/jstree/jstree.min.js"></script>			
    <script type="text/javascript" src="/rzrk/js/background/index.js"></script>
    <script type="text/javascript">
    	window.isAddAction = <#if isAddAction>true<#else>false</#if>;
    	window.title0 = "${title0}";
    	$(function(){
            <#list expressionScriptList as expressionScript>
            ${expressionScript}
            </#list>
    	});
    	window.rowDataJson = 
    	 <#if rowDataJson??>
    	 ${rowDataJson} 
    	<#else>
    	 ""
    	</#if>
    </script>
    <script type="text/javascript" src="/rzrk/js/background/work_form_editor.js"></script>
    <script type="text/javascript" src="/rzrk/js/background/contract_input.js"></script>
    
</head>
<body class="input admin easyui-layout">
	<div data-options="region:'center'">
		<div class="easyui-panel"  data-options="fit:true,border:false">
			<form id="admForm" method="post">
				<input type="hidden" name="rowId" value="${(rowId)!}"/>
				<input type="hidden" name="contractCategoryId" id="contractCategoryId"  value="${contractCategoryId}"/>
				<input type="hidden" name="titles" value="${titles}"/>
		       
		         
		         <#if (contractCategory.formTemplate)?? && contractCategory.formTemplate != "" >
		         	<style type="text/css">table{width:100%!important;}</style>
		          	${(contractCategory.formTemplate)!}
		          <#else>
		            <table  style='width:100%;'  border="1"  cellspacing="0" >
		        	<colgroup>
		        		<col style="width:100px;"/>
		        		<col/>
		        	</colgroup>
		             <#list definition as field>
		             
	                <tr>
	                    <td>${field.name}</td>
	                    <td>
	                        <#if field_index == 0>
	                            <#if isAddAction>
	                                <input type="text" class="contractCategory cfield cid definition" field="${field.name}" contract_category_id="${contractCategoryId}" name="fields" value="${(rowData[field.name])!}" placeholder="编号(留空则自动生成)" maxlength="160" />
	                            <#else>
	                                <input type="hidden" class="contractCategory cfield cid definition" field="${field.name}" contract_category_id="${contractCategoryId}" name="fields" value="${(rowData[field.name])!}"/>
	                                ${(rowData[field.name])!}
	                            </#if>
	                        <#else>
	                            <#if field.built>
	                                <input type="text" builtsign="${field.builtsign}" 
	                                <#list field.builtdata.keySet() as builtdataKey> ${builtdataKey}="${field.builtdata.get(builtdataKey)}" </#list> 
	                                class="contractCategory cfield definition" field="${field.name}" name="fields" value="${(rowData[field.name])!}"/>
	                            <#else>
	                                <#if field.type=="文本框" >
	                                    <input type="text" class="contractCategory cfield definition" field="${field.name}" name="fields" value="${(rowData[field.name])!}"  maxlength="160" />
	                                <#elseif field.type=="日期框" >
	                                    <input type="text" class="contractCategory cfield cdate definition" field="${field.name}" name="fields" value="${(rowData[field.name])!}"  maxlength="160" />
	                                <#elseif field.type=="时间框" >
	                                    <input type="text" class="contractCategory cfield ctime definition" field="${field.name}" name="fields" value="${(rowData[field.name])!}"  maxlength="160" />
	                                <#elseif field.type=="日期时间框" >
	                                    <input type="text" class="contractCategory cfield cdatetime definition" field="${field.name}" name="fields" value="${(rowData[field.name])!}"  maxlength="160" />
	                                <#elseif field.type=="文本区" >
	                                    <textarea class="contractCategory cfield definition" field="${field.name}" name="fields" maxlength="10240" >${(rowData[field.name])!}</textarea>
	                                <#elseif field.type=="表达式" >
	                                    <input type="text" class="contractCategory cfield definition expression" style="border-style:dotted" readonly="readonly" field="${field.name}" name="fields" value=""  maxlength="10240" />
	                                <#elseif field.type=="下拉框" >
	                                <span class="select contractCategory cfield definition" field="${field.name}"  value="${(rowData[field.name])!}" >
	                                    <select name="fields_select">
	                                        <#list field.options as opt>
	                                        <option value="${opt}" >${opt}</option>
	                                        </#list>
	                                    </select>
	                                </span>
	                                <#elseif field.type=="复选框" >
	                                <span class="checkbox contractCategory cfield definition"  field="${field.name}"  value="${(rowData[field.name])!}" >
	                                        <#list field.options as opt>
	                                        <label>
	                                            <input type="checkbox" name="fields_checkbox" value="${opt}" />
	                                            ${opt}
	                                        </label>
	                                        </#list>
	                                </span>
	                                <#elseif field.type=="单选框" >
	                                <span class="radio contractCategory cfield definition" field="${field.name}"  value="${(rowData[field.name])!}" >
	                                        <#list field.options as opt>
	                                        <label>
	                                            <input type="radio" name="fields_radio" value="${opt}" />
	                                            ${opt}
	                                        </label>
	                                        </#list>
	                                </span>
	                                <#else>
	                                    <input type="text" class="contractCategory" field="${field.name}" name="fields" value="${(rowData[field.name])!}"  maxlength="160" />
	                                </#if>
	                            </#if>
	                        </#if>
	                    
	                    </td>
	                </tr>
	                </#list>

		             </table>
		                
		          </#if>

		    </form>
            <div class="easyui-panel" >
                  <table  style='width:100%;'  border="1"  cellspacing="0" >
                        <#if recordListSave!=null && recordListSave.size() != 0 >
                        <tr>
                            <td class="td-right bd-top bd-bottom" colspan="4">
                                <h3>创建审批记录</h3>
                            </td>
                        </tr>
                         <#list recordListSave as record>
                         <tr>
                            <td class="td-right bd-top" colspan="4">
                                <h3>${record.approvalPerson}&nbsp;&nbsp;${(record.createDate?string("yyyy-MM-dd HH:mm:ss"))!}&nbsp;&nbsp;审批节点: ${record.pointName}&nbsp;&nbsp;${record.advice}</h3>
                            </td>
                         </tr>       
                         </#list>
                        </#if>
    
                        <#if recordListModify!=null && recordListModify.size() != 0 >
                        <tr>
                            <td class="td-right bd-top bd-bottom" colspan="4">
                                <h3>修改审批记录</h3>
                            </td>
                        </tr>
                         <#list recordListModify as record>
                         <tr>
                            <td class="td-right bd-top" colspan="4">
                                <h3>${record.approvalPerson}&nbsp;&nbsp;${(record.createDate?string("yyyy-MM-dd HH:mm:ss"))!}&nbsp;&nbsp;审批节点: ${record.pointName}&nbsp;&nbsp;${record.advice}</h3>
                            </td>
                         </tr>       
                         </#list>
                        </#if>
    
                        <#if recordListDelete!=null && recordListDelete.size() != 0 >
                        <tr>
                            <td class="td-right bd-top bd-bottom" colspan="4">
                                <h3>删除审批记录</h3>
                            </td>
                        </tr>
                         <#list recordListDelete as record>
                         <tr>
                            <td class="td-right bd-top" colspan="4">
                                <h3>${record.approvalPerson}&nbsp;&nbsp;${(record.createDate?string("yyyy-MM-dd HH:mm:ss"))!}&nbsp;&nbsp;审批节点: ${record.pointName}&nbsp;&nbsp;${record.advice}</h3>
                            </td>
                         </tr>       
                         </#list>
                        </#if>
                         
                        <#if fileList!=null && fileList.size() != 0 >
                        <tr>
                            <td class="td-right bd-top bd-bottom" colspan="4">
                                <h3>附件列表</h3>
                            </td>
                        </tr>
                         <#list fileList as file>
                               <tr> <td height='25' width='100px'>${file.fileName}</td>
                              <td width='40px'>
                                <a href="file_upload!downloadFile.action?downloadFilePath=${file.filePath}&downloadFileName=${file.fileName}" >下载</a>
                             </td>
                             <td  width='40px'>
                                <a href="file_upload!downloadFile.action?downloadFilePath=${file.filePath}&downloadFileName=${file.fileName}" >预览</a>
                             </td>
                              </tr> 
                        </#list>
                        </#if>
                  </table>
            </div>      
		</div>
	</div>

</body>
</html>