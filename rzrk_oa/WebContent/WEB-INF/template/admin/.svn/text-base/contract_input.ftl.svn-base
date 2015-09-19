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
    <link rel="stylesheet" href="/rzrk/css/background/contract_input.css" />
    <link rel="stylesheet" href="/rzrk/css/background/admin_input_jstree.css" />
    <link rel="stylesheet" href="/rzrk/css/background/departmentTree.css" />
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
    	window.childTitlesMap = ${childTitlesMapJson} ;  //子表头
    	window.tempRowId = ("temp"+Math.random()).replace(".",""); //临时的本表rowid，用于父子表
        window.belongProjectOpts = <#if belongProjectOpts??>${belongProjectOpts}<#else>[]</#if>;
    </script>
    <script type="text/javascript" src="/rzrk/js/background/work_form_editor.js"></script>
    <script type="text/javascript" src="/rzrk/js/background/contract_input.js"></script>
    
</head>
<body class="input admin easyui-layout">
	<div data-options="region:'center'">
		<div class="easyui-panel"  data-options="fit:true,border:false">
			<form id="admForm" method="post">
                <input type="hidden" name="rowId" value="${(rowId)!}"/>
                <input type="hidden" name="parentRowId" value="${(parentRowId)!}"/>
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
	                                class="contractCategory cfield definition <#if field.required> required </#if>" field="${field.name}" name="fields" value="${(rowData[field.name])!}"/><#if field.required><span class='required_sign' style='color:red;'>*</span></#if>
	                            <#else>
	                                <#if field.type=="文本框" >
	                                    <input type="text" class="contractCategory cfield definition <#if field.required> required </#if>" field="${field.name}" name="fields" value="${(rowData[field.name])!}"  maxlength="160" /><#if field.required><span class='required_sign' style='color:red;'>*</span></#if>
	                                <#elseif field.type=="日期框" >
	                                    <input type="text" class="contractCategory cfield cdate definition <#if field.required> required </#if>" field="${field.name}" name="fields" value="${(rowData[field.name])!}"  maxlength="160" /><#if field.required><span class='required_sign' style='color:red;'>*</span></#if>
	                                <#elseif field.type=="时间框" >
	                                    <input type="text" class="contractCategory cfield ctime definition <#if field.required> required </#if>" field="${field.name}" name="fields" value="${(rowData[field.name])!}"  maxlength="160" /><#if field.required><span class='required_sign' style='color:red;'>*</span></#if>
	                                <#elseif field.type=="日期时间框" >
	                                    <input type="text" class="contractCategory cfield cdatetime definition <#if field.required> required </#if>" field="${field.name}" name="fields" value="${(rowData[field.name])!}"  maxlength="160" /><#if field.required><span class='required_sign' style='color:red;'>*</span></#if>
	                                <#elseif field.type=="文本区" >
	                                    <textarea class="contractCategory cfield definition <#if field.required> required </#if>" field="${field.name}" name="fields" maxlength="10240" >${(rowData[field.name])!}</textarea><#if field.required><span class='required_sign' style='color:red;'>*</span></#if>
                                    <#elseif field.type=="表达式" >
                                        <input type="text" class="contractCategory cfield definition <#if field.required> required </#if> expression" style="border-style:dotted" readonly="readonly" field="${field.name}" name="fields" value=""  maxlength="10240" /><#if field.required><span class='required_sign' style='color:red;'>*</span></#if>
                                    <#elseif field.type=="选择树" >
                                        <span class="selectTree contractCategory cfield definition <#if field.required> required </#if>"  tree='${field.expression}'  field="${field.name}"  value="${(rowData[field.name])!}" ><input type="text" /></span><#if field.required><span class='required_sign' style='color:red;'>*</span></#if>
	                                <#elseif field.type=="下拉框" >
	                                <span class="select contractCategory cfield definition <#if field.required> required </#if>" field="${field.name}"  value="${(rowData[field.name])!}" >
	                                    <select name="fields_select">
	                                        <#list field.options as opt>
	                                        <option value="${opt}" >${opt}</option>
	                                        </#list>
	                                    </select>
	                                </span><#if field.required><span class='required_sign' style='color:red;'>*</span></#if>
	                                <#elseif field.type=="复选框" >
	                                <span class="checkbox contractCategory cfield definition <#if field.required> required </#if>"  field="${field.name}"  value="${(rowData[field.name])!}" >
	                                        <#list field.options as opt>
	                                        <label>
	                                            <input type="checkbox" name="fields_checkbox" value="${opt}" />
	                                            ${opt}
	                                        </label>
	                                        </#list>
	                                </span><#if field.required><span class='required_sign' style='color:red;'>*</span></#if>
	                                <#elseif field.type=="单选框" >
	                                <span class="radio contractCategory cfield definition <#if field.required> required </#if>" field="${field.name}"  value="${(rowData[field.name])!}" >
	                                        <#list field.options as opt>
	                                        <label>
	                                            <input type="radio" name="fields_radio" value="${opt}" />
	                                            ${opt}
	                                        </label>
	                                        </#list>
	                                </span><#if field.required><span class='required_sign' style='color:red;'>*</span></#if>
	                                <#else>
	                                    <input type="text" class="contractCategory cfield definition <#if field.required> required </#if>" field="${field.name}" name="fields" value="${(rowData[field.name])!}"  maxlength="160" /><#if field.required><span class='required_sign' style='color:red;'>*</span></#if>
	                                </#if>
	                            </#if>
	                        </#if>
	                    
	                    </td>
	                </tr>
	                </#list>

		             </table>
		                
		          </#if>
	<!--	            
		            <#list titleArr as title>
		            <tr>
		                <td><#if title_index == 0>*</#if>${title}</td>
		                <td>
		                <#if isAddAction>
	                        <input type="text" class="contractCategory" field="${title}" name="fields" value="${(rowData[title])!}"/>
	                    <#else>
	                        <#if title_index == 0>
	                            <input type="hidden" class="contractCategory" field="${title}" name="fields" value="${(rowData[title])!}"/>
	                            ${(rowData[title])!}
	                        <#else>
	                            <input type="text" class="contractCategory" field="${title}" name="fields" value="${(rowData[title])!}"/>
	                        </#if>
	                    </#if>
		                
		                </td>
		            </tr>
		            </#list>
	-->	            
		            
		            
		         <!--   <tr>
		                    <td class="td-left bd-bottom">查看人：</td>
		                    <td class="td-right bd-bottom">
		                	
			                	<ul  id="adminListId">
			                	  <#list fieldList as type>
								  <li class="deleteAdminId">${type.name}<span>X</span><input type="hidden"  name="adminList.id" value="${type.id}"/></li>
							     </#list>
			                		<li class="addColleague">
			                			<a href="javascript:">添加查看人</a>
			                			<input class="userBox" style="width:100px;display:none;" />
		                			</li>
			                	</ul>
							</td>
		                </tr>
					-->
            <#if projectId ?? >
            <br>
                <div class="easyui-panel" >
                    <table  style='width:100%;'  border="1"  cellspacing="0">
                        <tr>
                            <td ><br>所属项目 &nbsp;&nbsp;<input type="text" id="projectId" name="projectId" value="${projectId!}"></input><br><br></td>
                        </tr>
                    </table>
                 </div>
            <br>
            </#if>
		    </form>
            <!--子表-->
            <#list contractCategory.childSet as childtable >
                <div class="easyui-panel" >
                       <span><br><b>${childtable.name}</b>&nbsp;</span>
                       <a href="#" class="addContract easyui-linkbutton" data-contract_category_id ="${childtable.id}"  data-options="iconCls:'icon-add'">新增记录</a>
                        <a href="#" class="removeContract easyui-linkbutton"  data-contract_category_id ="${childtable.id}" data-options="iconCls:'icon-remove'" >删除记录</a>
                      <table class="childtable" data-contract_category_id ="${childtable.id}" ></table>
                </div>
            </#list>
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
                                <a href="file_upload!showFile.action?downloadFilePath=${file.filePath}&downloadFileName=${file.fileName}"  target ="_blank">预览</a>
                             </td>
                              </tr> 
                        </#list>
                        </#if>
                  </table>
            </div>
            
                        <!--子表-->
                  
		</div>
		
		
		
	</div>
	<div data-options="region:'south',border:false" style="height:40px;overflow:hidden;">
		<div style="padding:10px;">
	    <a href="javascript:void(0)" data-options="iconCls:'icon-save'" class="easyui-linkbutton" id="saveBtn">&nbsp;保&nbsp;存&nbsp;&nbsp;</a>
	    &nbsp;&nbsp;
	    <a href="javascript:void(0)" data-options="iconCls:'icon-cancel'" class="easyui-linkbutton" id="closeBtn">&nbsp;返&nbsp;回&nbsp;&nbsp;</a>
	</div>

</body>
</html>