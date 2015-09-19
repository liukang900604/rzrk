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
    <link rel="stylesheet" href="/rzrk/css/selectPosition.css" />
    <link rel="stylesheet" href="/rzrk/css/background/project_input.css" />
    <link rel="stylesheet" href="/rzrk/css/background/admin_input_jstree.css" />
    <script type="text/javascript" src="/rzrk/js/alljs.js"></script>
    <script type="text/javascript" src="/rzrk/js/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="/rzrk/js/jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="/rzrk/js/jstree/jstree.min.js"></script>
    <script type="text/javascript" src="/rzrk/js/selectPos/single-select-department.js"></script>
    <script type="text/javascript" src="/rzrk/js/selectPos/layer.min.js"></script>
    <script type="text/javascript" src="/rzrk/js/background/index.js"></script>
	<script type="text/javascript" src="${base}/template/common/editor/kindeditor.js"></script>
    <script type="text/javascript">
    	window.isAddAction = <#if isAddAction>true<#else>false</#if>;
    	window.isEditAction = <#if isEditAction>true<#else>false</#if>;
    	window.departmentJobList = 
    	<#if jsonObj??>
    	{
			"root" :${jsonObj}
    	}
   		<#else>
			{}
		</#if>
    	;
    </script>
    <script type="text/javascript" src="/rzrk/js/background/libs/selectMembers.js"></script>
    <script type="text/javascript" src="/rzrk/js/background/project_input.js"></script>
    
</head>
<body class="input admin easyui-layout">
	<div data-options="region:'center'">
		<div class="easyui-panel" title="项目信息" data-options="fit:true,border:false" style="padding:0 0 5px 0;">
			<form id="admForm" method="post">
				<input type="hidden"  id="planid"  name="id" value="${id}"/>
		        <table cellpadding="5" style='width:100%;'>
		        	<colgroup>
		        		<col style="width:100px;"/>
		        		<col/>
		        	</colgroup>
		            <tr>
		                <td colspan="2" style="text-align: left;"><span class="warnInfo"><em>*</em>为必填项</span></td>
		            </tr>
		            <tr>
		                <td><em>*</em>项目名称</td>
		                <td>
		                <textarea name="project.name">${(project.name)!}</textarea>
		                <!--
		                  <input  type="text" name="project.name" <#if isEditAction >disabled="true"</#if>value="${(project.name)!}"/>
		                 -->
		                 <#if isAddAction > <input  type="hidden" name="project.projectCategory.id" value="${projectCategoryId}"/></#if>
	                    </td>
		            </tr>
		            <tr>
		                <td><em>*</em>项目类型</td>
		                <td>
		                	<select name="project.projectType">
								<#list projectTypeList as type>
									<option value="${type}"<#if isEditAction && type == project.projectType> selected</#if>>
									${action.getText(type)}
									</option>
								</#list>
							</select>
		                </td>
		            </tr>
		             <#if hasNoRoot==0>
			            <tr>
			                <td><em>*</em>一级分类</td>
			                <td>
		   	                    <select name="project.rootContractCategory.id">
		   	                    <#if rootContractCategoryList??>
									<#list rootContractCategoryList as temp>
										<option value="${temp.id}"<#if isEditAction && temp.id == project.rootContractCategory.id> selected</#if>>
										${temp.name}
										</option>
									</#list>
									<#else>
			                   </#if>
								</select>
			                </td>
			            </tr>
		             </#if>
		            <tr>
		                <td><em>*</em>项目内容</td>
		                <td>
		                	<textarea id="editor" name="project.content" class="editor">${(project.content)!}</textarea>
		                </td>
		            </tr>
		            <tr>
		                <td><em>*</em>开始时间</td>
		                <td><input id="beginTime" name="project.beginTime" value="${project.beginTime}" type="text" style="width:120px;"></td>
		            </tr>
		            <tr>
		                <td>结束时间</td>
		                <td><input id="endTime" name="project.endTime" value="${project.endTime}" type="text" style="width:120px;"></td>
		            </tr>
		            <tr>
		                <td><em>*</em>进度</td>
		                <td><input name="project.progress" type="text"  value="${project.progress}" style="width:58px;margin: 0 5px 0 0;">%</td>
		            </tr>
		            <tr>
		                <td><em>*</em>状态</td>
		                <td>
		                    <select name="project.status">
								<#list statusList as type>
									<option value="${type}"<#if isEditAction && type == project.status> selected</#if>>
									${action.getText(type)}
									</option>
								</#list>
							</select>
		                </td>
		            </tr>    
		            
		            <tr>
		                <td><em>*</em>负责人</td>
	                	<td>
	                	<select name="project.responsibor.id">
	                	  <#list adminList as admin>
	                		<option value="${admin.id}" <#if isEditAction && project.responsibor.id == admin.id >selected="selected"</#if>>${admin.name}</option>
	                	  </#list>
	                	</select>
	                	<span style="color:#aaa;margin-left:10px;">支持首字母索引</span>
		                </td>
		            </tr>
		            <tr>
		                <td><em>*</em>项目成员</td>
		                <td>
		                 	<input type="text" name="adminListText" id="adminList" class="adminList" value="<#if isEditAction><#list project.projectMember as admin><#if admin_index != 0>, </#if>${admin.name}</#list></#if>"/>
		                	<#if isEditAction>
			                	<#list project.projectMember as admin>
			                	<input type="hidden" name="adminList.id" value="${admin.id}"/>
			                	</#list>
		                	</#if>
	                	</td>
		            </tr>
                    <tr>
                        <td><em>*</em>所属部门</td>
                        <td>
                            <input id="deparmentList" name="project.deparment.name" type="text" readonly="true" <#if isEditAction >value="${project.deparment.name}"</#if>>
                            <#if isEditAction >
                                <input type="hidden" name="project.deparment.id" value="${project.deparment.id}">
                            </#if>
                        </td>
                    </tr>
                    <tr>
                        <td>权限控制</td>
                        <td>
                        <label>
                            <input type="checkbox"  value="true" name="project.permissioned"  <#if (project.permissioned?? && project.permissioned) >checked="checked"</#if>  />
                                                                                                    开启
                        </label>
                        </td>
                    </tr>
		        </table>
		    </form>
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
				<input type="hidden" name="userPlanRemark.project.id" value="${id}"/>
		        <table style='width:100%;'>
		            <tr>
		            <td></td>
		            <td><textarea id="content" name="userPlanRemark.content" style="width:500px;height:120px;"></textarea></td>
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
		      <table style="width:100%;border: solid 1px #000; text-align: center;">
		        <tr style="background-color: #c8c8e8;color:#000;font-weight: bold;"><th style="text-align: center;">日期</th><th>操作者</th><th>内容</th></tr>
				<#list logList as item>
	                  <tr style="background-color: #d8d8e8;color:#000;">
	                  <td style="text-align: center;width:20%">${item.modifyDate?string("yyyy-MM-dd HH:mm:ss")}</td>
	                  <td style="text-align: center;width:20%">${item.operator.name}</td>
	                  <td style="text-align: left;width:60%">${item.content}</td></tr>
				</#list>	
			  </table>	
		</#if>
	</div>
	<div data-options="region:'south',border:false" style="height:40px;overflow:hidden;">
		<div style="padding:10px;">
		    <a href="javascript:void(0)" data-options="iconCls:'icon-save'" class="easyui-linkbutton" id="saveBtn">&nbsp;保&nbsp;存&nbsp;&nbsp;</a>
		    &nbsp;&nbsp;
		    <a href="javascript:void(0)" data-options="iconCls:'icon-cancel'" class="easyui-linkbutton" id="closeBtn">&nbsp;返&nbsp;回&nbsp;&nbsp;</a>
		</div>
	</div>
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