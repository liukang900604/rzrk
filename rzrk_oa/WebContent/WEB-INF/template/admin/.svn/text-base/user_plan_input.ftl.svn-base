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
    <link rel="stylesheet" href="/rzrk/css/background/project_input.css" />
    <script type="text/javascript" src="/rzrk/js/alljs.js"></script>
    <script type="text/javascript" src="/rzrk/js/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="/rzrk/js/jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="${base}/template/common/editor/kindeditor.js"></script>
    <script type="text/javascript" src="/rzrk/js/background/index.js"></script>
    <script type="text/javascript">
    	window.isAddAction = <#if isAddAction>true<#else>false</#if>;
    	window.isEditAction = <#if isEditAction>true<#else>false</#if>;
    	window.id = "${projectId}";
    </script>
    <script type="text/javascript" src="/rzrk/js/background/ajaxfileupload.js"></script>
     <script type="text/javascript" src="/rzrk/js/background/page_data.js"></script>
    <script type="text/javascript" src="/rzrk/js/background/user_plan_input.js"></script>
     <script type="text/javascript" language="javascript">
      window.onload = function() {
            var os = document.getElementById("requireIds").options;
            for(var i=0; i<os.length; i++) os[i].title = os[i].innerHTML;
        };
    </script>
    <#if isView>
	    <style type="text/css">
			table#userPlanItem {
				font-family: verdana,arial,sans-serif;
				font-size:11px;
				color:#333333;
				border-width: 1px;
				border-color: #666666;
				border-collapse: collapse;
			}
			table#userPlanItem th {
				border-width: 1px;
				padding: 8px;
				border-style: solid;
				border-color: #666666;
				background-color: #dedede;
			}
			table#userPlanItem td {
				border-width: 1px;
				padding: 8px;
				border-style: solid;
				border-color: #666666;
				background-color: #ffffff;
				
			}
			table{
				table-layout:fixed;
			}
		</style>
    </#if>
</head>
<body class="input admin" style="overflow-x:hidden;">
	<div class="easyui-panel" title="项目计划信息" style="padding: 5px 0;width:100%;">
		<form id="admForm" method="post">
			<input type="hidden" id="planid"  name="id" value="${id}"/>	
	        <table cellpadding="5" style='width:100%;' id="userPlanItem">
	        	<colgroup>
	        		<col style="width:100px;"/>
	        		<col/>
	        	</colgroup>
	        	 <#if !isView>
	            <tr>
	                <td colspan="2" style="text-align: left;"><span class="warnInfo"><em>*</em>为必填项</span></td>
	            </tr>
	             </#if>
	            <tr>
	                <td style='width:80px'> <em>*</em>计划名称</td>
	                <!-- <td><#if isView>${(userPlan.name)!}<#else><input  type="text" name="userPlan.name" value="${(userPlan.name)!}" style="width:200px"/></#if></td> -->
	                <td><#if isView>${(userPlan.name)!}<#else><textarea name="userPlan.name" value="${(userPlan.name)!}" style="height:36px;min-height:18px;min-width:795px;max-width:795px;">${(userPlan.name)!}</textarea></#if></td>
	            </tr>
	            <tr>
	                <td><em>*</em>计划内容</td>
	              
                	<td><#if isView>${(userPlan.content)!?trim}<#else><textarea id="editor" name="userPlan.content" class="editor">${(userPlan.content)!}</textarea></#if></td>
	              
	            </tr>
	            <tr>
	                <td>开始时间</td>
	                <td><#if isView>${(userPlan.beginTime)!}<#else><input id="beginTime" name="userPlan.beginTime" value="${(userPlan.beginTime)!}" type="text" style="width:120px;"></#if></td>
	                
	            </tr>

	            <tr>
                    <td>预期结束时间</td>
                    <td><#if isView>${(userPlan.preEndTime)!}<#else><input id="preEndTime" name="userPlan.preEndTime" value="${(userPlan.preEndTime)!}" type="text" style="width:120px;"></#if></td>
                    
                </tr>
                <tr>
                    <td>实际结束时间</td>
                    <td><#if isView>${(userPlan.endTime)!}<#else><input id="endTime" name="userPlan.endTime" value="${(userPlan.endTime)!}" type="text" style="width:120px;"></#if></td>
                    
                </tr>
                <tr>
                    <td>类型</td>
                    <td>
                        <#if isView>${action.getText(userPlan.plantype)}<#else>
                    <select name="userPlan.plantype">
                            <#list plantypeList as plantype>
                                <option value="${plantype}"<#if isEditAction && plantype == userPlan.plantype> selected</#if>>
                                ${action.getText(plantype)}
                                </option>
                            </#list>
                    </select>
                        </#if>
                    </td>
                    
                </tr>
                <tr>
                    <td>出现频率</td>
                    <td>
                        <#if isView>${action.getText(userPlan.reproducibility)}<#else>
                    <select name="userPlan.reproducibility">
                            <#list reproducibilityList as reproducibility>
                                <option value="${reproducibility}"<#if isEditAction && reproducibility == userPlan.reproducibility> selected</#if>>
                                ${action.getText(reproducibility)}
                                </option>
                            </#list>
                    </select>
                        </#if>
                    </td>
                    
                </tr>
                <#if userPlan ?? && userPlan.copyLink ??>
	            <tr>
                   <td>链接地址</td>
                   <td>
                      ${(userPlan.copyLink)!}
                   </td>
                </tr>
	            </#if>
                <tr>
                    <td>严重性</td>
                    <td>
                        <#if isView>${action.getText(userPlan.severity)}<#else>
                    <select name="userPlan.severity">
                            <#list severityList as severity>
                                <option value="${severity}"<#if isEditAction && severity == userPlan.severity> selected</#if>>
                                ${action.getText(severity)}
                                </option>
                            </#list>
                    </select>
                        </#if>
                    </td>
                    
                </tr>
	            <tr>
	                <td><em>*</em>处理人</td>
	                <td>
	                   <#if isView>
	                        ${userPlan.admin.name}
	                   <#else>
	   	                    <select name="userPlan.admin.id">
								<#list adminList as admin>
									<option value="${admin.id}"<#if isEditAction && userPlan.admin.id == admin.id> selected</#if>>
									${admin.name}
									</option>
								</#list>
							</select>（支持模糊搜索）
	                   </#if>
						
	                </td>
	            </tr>
		            <tr>
		                <td><em>*</em>测试人</td>
		                <td>
		                   <#if isView>
		                    <#if (userPlan.testPerson) ??>
		                        ${userPlan.testPerson.name}
		                      </#if>
		                   <#else>
		   	                    <select name="userPlan.testPerson.id">
		   	                     <#if (userPlan.testPerson) ??>
									<#list adminList as admin>
										<option value="${admin.id}"<#if isEditAction && userPlan.testPerson.id == admin.id> selected</#if>>
										${admin.name}
										</option>
									</#list>
							     <#else>
							     <#list adminList as admin>
							     	<option value="${admin.id}">
										${admin.name}
										</option>
								    </#list>
							     </#if>>
								</select>（支持模糊搜索）
		                   </#if>
							
		                </td>
		            </tr>
	            <tr>
	                <td><em>*</em>进度</td>
	                <td><#if isView>${(userPlan.progress)!}%<#else><input name="userPlan.progress" type="text"  value="${(userPlan.progress)!0}" style="width:58px;margin: 0 5px 0 0;">%</#if></td>
	                
	            </tr>
	            <tr>
	            <tr>
			       <td >目标版本</td>
			        <td>
			         <#if isView>${(userPlan.objectiveVersionName)!}<#else>
			              <input type="hidden" name="userPlan.objectiveVersion" id="objectiveVersion" value="${(userPlan.objectiveVersion)!}"></input>
			              <input type="text" name="userPlan.objectiveVersionName" id="objectiveVersionName" value="${(userPlan.objectiveVersionName)!}" readonly="readonly"></input>
			          </#if>
					</td>			
	            </tr>
	             <tr>
			       <td >产品版本</td>
			        <td>
			        	<#if isView>${(userPlan.productVersionName)!}<#else>
			              <input type="hidden" name=userPlan.productVersion" id="productVersion" value="${(userPlan.productVersion)!}"></input>
			              <input type="text" name="userPlan.productVersionName" id="productVersionName" value="${(userPlan.productVersionName)!}" readonly="readonly"></input>
			              </#if>
					</td>			
	            </tr>
	            <tr>
	                <td>备注</td>
	                <td><#if isView>${(userPlan.remark)!}<#else><textarea name="userPlan.remark" style="width:800px;height:150px;">${(userPlan.remark)!}</textarea></#if></td>
	            </tr>
	            <tr>
	                <td><em>*</em>状态</td>
	                <td>
	                    <#if isView>${action.getText(userPlan.status)}<#else>
    	                    <select id="status" name="userPlan.status">
							<#list statusList as status>
								<option value="${status}"<#if isEditAction && status == userPlan.status> selected</#if>>
								${action.getText(status)}
								</option>
							</#list>
						</select>
	                    </#if>

	                </td>
	            </tr>
	            <tr>
	                <td><em>*</em>项目</td>
                	<td>
                	<#if isView>${project.name}<#else>
                    	<#if project??>
                    	   ${project.name}
                    	       <input type="hidden" name="userPlan.project.id" value="${project.id}" />
                    	<#else>
                            <select name="userPlan.project.id">
                                <#list projectList as project>  
                                    <option value="${project.id}" <#if (isEditAction && userPlan.project.id == project.id) || (isAddAction && projectId == project.id ) >selected="selected"</#if>>${project.name}</option>
                                </#list>
                            </select>
                    	</#if>
                	</#if>
	                </td>
	            </tr>
	            <#if project??>
                <tr>
                    <td>需求</td>
                    <td>
                    <#if isView>${requireNamesStr!}<#else>
                    <!--
                    <input type="text" name="requireIds" id="requireIds" values="${requireIdsExists!}" />
                    -->
                    <select name="requireIds"  id="requireIds" multiple="multiple"  >
                        <#list requireArray as require>  
                            <option value="${require.id}"   <#if requireIdsList?seq_contains(require.id) >selected="selected"   </#if>     >${require.text}</option>
                        </#list>
                    </select>
                    </#if>
                    </td>
                </tr>
                </#if>
                  <tr>
	                    <td class="td-right bd-top">
	                    	 附件列表：
	                    </td>
	                    <#if !isView>
	                    <td>
	                    <input id="file" type="file"  multiple=""  name="file" data-url='file_upload!fileUpload.action'  />               
	                    </td>
	                    <#else>
	                     <td>
	                      <#list fileList as file>
	                      <a href="file_upload!showFile.action?downloadFilePath=${file.filePath}&downloadFileName=${file.fileName}" >${file.fileName}</a>
	                      </#list>
	                      </td>
	                    </#if>
	                    
	                    
	               </tr>
                <#if !isView>
                	 <table style="width:initial">
	                
	               <#list fileList as file>
						  <tr><td  class="td-right bd-top" height="25px" width="100px">${file.fileName}</td>
						  <td width="40px">
						    <input type="hidden" name="fileName" value="${file.fileName}"/>
						    <input type="hidden" name="filePath" value="${file.filePath}"/>
							<a href="file_upload!downloadFile.action?downloadFilePath=${file.filePath}&downloadFileName=${file.fileName}" >下载</a>
						  </td>
						  <td width="40px"><a href="#" class="deleteFile" value="${file.filePath}">删除</a></td>
						  <td  width="40px">
							<a href="file_upload!showFile.action?downloadFilePath=${file.filePath}&downloadFileName=${file.fileName}" >预览</a>
						 </td>
						  </tr>		
				</#list>
		
          </table>
           </#if>
	               <table id="down">
	                
				         </table>
	        </table>
	    </form>
	</div>
	
	<div style="padding:10px 0">
	    <#if isView>
	    <a href="javascript:void(0)" data-options="iconCls:'icon-edit'" class="easyui-linkbutton" id="editBtn">&nbsp;编&nbsp;辑&nbsp;&nbsp;</a>
	    &nbsp;&nbsp;
	    <#else>
	    <a href="javascript:void(0)" data-options="iconCls:'icon-save'" class="easyui-linkbutton" id="saveBtn">&nbsp;保&nbsp;存&nbsp;&nbsp;</a>
	    &nbsp;&nbsp;
	    </#if>
	    <a href="javascript:void(0)" data-options="iconCls:'icon-cancel'" class="easyui-linkbutton" id="closeBtn">&nbsp;返&nbsp;回&nbsp;&nbsp;</a>
	</div>
	<br/>
	<#if isEditAction >
		<div class="easyui-panel" title="问题描述" style="padding: 5px 0;width:100%;">
		<table id="remarkTable" cellpadding="5" style='width:100%'>
		</table>
		</div>	
		<br>
		<div class="easyui-panel" title="添加问题描述" style="padding: 5px 0;width:100%;">
		<form id="problemForm" method="post">
			<input type="hidden" name="userPlanRemark.userPlan.id" value="${id}"/>
	        <table style='width:100%;'>
	            <tr>
	            <td><textarea id="content" name="userPlanRemark.content" style="width:500px;height:120px;"></textarea></td>
	            <td width="50%"></td>
	            </tr>
	        </table>
	    </form>
		</div>	
			<div style="padding:10px 0">
				<a href="javascript:void(0)" data-options="iconCls:'icon-save'" class="easyui-linkbutton" id="saveUserPlanRemark">&nbsp;保&nbsp;存&nbsp;&nbsp;</a>
			</div>
	</#if>
	<#if isEditAction>	
	<h3>问题历史</h3>
	      <table style="width:100%;border: solid 1px #000000; text-align: center;">
	        <tr style="background-color: #c8c8e8;color:#00000;font-weight: bold;"><th style="text-align: center;">日期</th><th>操作者</th><th>内容</th></tr>
			<#list logList as item>
                  <tr style="background-color: #d8d8e8;color:#00000;">
                  <td style="text-align: center;width:20%">${item.modifyDate?string("yyyy-MM-dd HH:mm:ss")}</td>
                  <td style="text-align: center;width:20%">${item.operator.name}</td>
                  <td style="text-align: left;width:60%">${item.content}</td></tr>
			</#list>			
		  </table>	
	</#if>
	
	<!-- 部门选择 HTML -->
	<div class="content" id="contentDepartment" style="display:none;">
		<div class="list">
			<div class="l">
				<div class="select_list" style="overflow:auto;border:1px solid #778;">
					<div class="domTree">
					</div>					
				</div>
			</div>
			<div class="l mv_btn">
				<input type="button" class="btnRight" value="右移" /><br/><br/>
				<input type="button" class="btnLeft" value="左移" />
			</div>
			<div class="l">
				<select class="rselect select_list" multiple="multiple">
				</select>					
			</div>	
		</div>
	</div>	
</body>
</html>