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
    <link rel="stylesheet" href="/rzrk/css/background/admin_input_jstree.css" />
    <script type="text/javascript" src="/rzrk/js/alljs.js"></script>
    <script type="text/javascript" src="/rzrk/js/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="/rzrk/js/jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="/rzrk/js/jstree/jstree.min.js"></script>			
    <script type="text/javascript" src="/rzrk/js/background/index.js"></script>
    <script type="text/javascript">
    	window.isAddAction = <#if isAddAction>true<#else>false</#if>;
    	window.jobList = 
    	<#if jsonArray??>
			${jsonArray}
		<#else>
			[]
		</#if>
    	;
    	window.departmentJobList = 
    	<#if jsonObj??>
    	{
			"root" :${jsonObj}
    	}
   		<#else>
			{}
		</#if>
    	;
    	window.viceJobList = 
    	<#if jsonObj??>
    	{
			"root" :${jsonObj}
    	}
   		<#else>
			{}
		</#if>
    	;
    	

    </script>
    <script type="text/javascript" src="/rzrk/js/selectPos/select-department.js"></script>    
    <script type="text/javascript" src="/rzrk/js/selectPos/select-pos.js"></script>    
    <script type="text/javascript" src="/rzrk/js/selectPos/select-deputypos.js"></script>    
    <script type="text/javascript" src="/rzrk/js/background/admin_input.js"></script>
    <script type="text/javascript" src="/rzrk/js/background/ajaxfileupload.js"></script>
    
    <link rel="stylesheet" href="/rzrk/css/selectPosition.css" />
    <script type="text/javascript" src="/rzrk/js/selectPos/layer.min.js"></script>
    
</head>
<body class="input admin easyui-layout">
	<div data-options="region:'center'">
		<div class="easyui-panel" title="人员信息" data-options="fit:true,border:false">
			<form id="admForm" method="post">
				<input type="hidden" name="id" value="${id}"/>
		        <table cellpadding="5" style='width:100%;'>
		        	<colgroup>
		        		<col style="width:100px;"/>
		        		<col/>
		        	</colgroup>
		        	<#if !isAddAction>
		            <tr>
		                <td colspan="2" style="text-align: left;"><span class="warnInfo">&nbsp;如果要修改密码，请填写密码，若留空，密码将保持不变！</span></td>
		            </tr>
		            </#if>
		            <tr>
		                <td>*用户名</td>
		                <td><input type="text" name="admin.username" value="${(admin.username)!}" <#if isEditAction>disabled="true"</#if>/>(登录名)</td>
		            </tr>
		            <tr>
		                <td>*姓　名</td>
		                <td><input type="text" name="admin.name" value="${(admin.name)!}"/></td>
		            </tr>
		            <tr>
		                <td>*密　码</td>
		                <td><input type="password" id="password" name="admin.password" title="用户名只允许包含中文、英文、数字和下划线"/></td>
		            </tr>
		            <tr>
		                <td>*重复密码</td>
		                <td><input type="password" name="admin.rePassword" value=""/></td>
		            </tr>
		            
		            <tr>
		                <td>人员代码</td>
		                <td><input  type="text" name="admin.code" value="${(admin.code)!}"/></td>
		            </tr>
		            <tr>
		                <td>*排序号</td>
		                <td><input  type="text" name="admin.sortNo" value="${(admin.sortNo)!}"/></td>
		            </tr>
		            <tr>
		                <td>*重复序号处理</td>
		                <td>
							<input type="radio" name="admin.duplicateSortDeal" value="1" <#if isEditAction && (admin.duplicateSortDeal== 1)>checked="checked"</#if>/>插入
	                        <input type="radio" name="admin.duplicateSortDeal" value="0" <#if isEditAction && (admin.duplicateSortDeal== 0)>checked="checked"</#if>/>重复
						</td>
		            </tr>
		            <!-- ================================================================================ -->
		            <tr>
		                <td>工作地点</td>
		                <td><input type="text" name="admin.location" value="${(admin.location)!}"/></td>
		            </tr>
		            <tr>
		                <td>政治面貌</td>
		                <td><input type="text" name="admin.politics" value="${(admin.politics)!}"/></td>
		            </tr>
		            <tr>
		                <td>民族</td>
		                <td><input type="text" name="admin.minzu" value="${(admin.minzu)!}"/></td>
		            </tr>
		            <tr>
		                <td>婚姻状况</td>
		                <td>
		                   <select name="admin.marriageStatus">
								<#list mariageStatusList as type>
									<option value="${type}"<#if isEditAction && type == admin.marriageStatus> selected</#if>>
										${action.getText(type)}
									</option>
								</#list>
							</select>
	                   </td>
		            </tr>
		            <tr>
		                <td>学历</td>
		                <td>
		                	<select name="admin.dgreeEnum">
								<#list degreeEnumList as type>
									<option value="${type}"<#if isEditAction && type == admin.dgreeEnum> selected</#if>>
										${action.getText(type)}
									</option>
								</#list>
							</select>
						</td>
		            </tr>
		            <tr>
		                <td>毕业院校</td>
		                <td><input type="text" name="admin.graduateSchool" value="${(admin.graduateSchool)!}"/></td>
		            </tr>
		            <tr>
		                <td>专业</td>
		                <td><input type="text" name="admin.major" value="${(admin.major)!}"/></td>
		            </tr>
		            <tr>
		                <td>入职日期</td>
		                <td><input type="text" id="onBoardDate" name="admin.onBoardDate" value="${(admin.onBoardDate)!}"/></td>
		            </tr>
		            <tr>
			                <td>合同类型</td>
			                <td>
			                   <input type="text" name="admin.contractType" value="${(admin.contractType)!}"/></td>
							</td>
		            </tr>
		            <tr>
		                <td>合同到期日</td>
		                <td><input type="text" id="contractDueDay" name="admin.contractDueDay" value="${(admin.contractDueDay)!}"/></td>
		            </tr>
		            <tr>
		                <td>合同到期日2</td>
		                <td><input type="text" id="contractDueDay2" name="admin.contractDueDay2" value="${(admin.contractDueDay2)!}"/></td>
		            </tr>
		            <tr>
		                <td>合同到期日3</td>
		                <td><input type="text" id="contractDueDay3" name="admin.contractDueDay3" value="${(admin.contractDueDay3)!}"/></td>
		            </tr>
		            <tr>
		                <td>合同到期日4</td>
		                <td><input type="text" id="contractDueDay4" name="admin.contractDueDay4" value="${(admin.contractDueDay4)!}"/></td>
		            </tr>
		            <tr>
		                <td>转正时间</td>
		                <td><input type="text" id="turnRegularDate" name="admin.turnRegularDate" value="${(admin.turnRegularDate)!}"/></td>
		            </tr>
		            <tr>
		                <td>存档情况</td>
		                <td><input type="text" name="admin.document" value="${(admin.document)!}"/></td>
		            </tr>
		            <tr>
		                <td>职称</td>
		                <td><input type="text" name="admin.jobTitle" value="${(admin.jobTitle)!}"/></td>
		            </tr>
		            <tr>
		                <td>身份证号码</td>
		                <td><input type="text" name="admin.identification" value="${(admin.identification)!}"/></td>
		            </tr>
		            <tr>
		                <td>户口类别</td>
		                <td><input type="text" name="admin.hukouType" value="${(admin.hukouType)!}"/></td>
		            </tr>
		            <tr>
		                <td>户口所在地</td>
		                <td><input type="text" name="admin.hukouLocation" value="${(admin.hukouLocation)!}"/></td>
		            </tr>
		            <tr>
		                <td>现住址</td>
		                <td><input type="text" name="admin.resident" value="${(admin.resident)!}"/></td>
		            </tr>
		            <tr>
		                <td>紧急电话联系人</td>
		                <td><input type="text" name="admin.ergentCall" value="${(admin.ergentCall)!}"/></td>
		            </tr>
		            <tr>
		                <td>离职日期</td>
		                <td><input type="text" id="quitDate" name="admin.quitDate" value="${(admin.quitDate)!}"/></td>
		            </tr>
		            
		            <!-- ================================================================================ -->
		            
		            
		            <tr>
		                <td>*E-mail</td>
		                <td><input type="text" name="admin.email" value="${(admin.email)!}"/></td>
		            </tr>
		            <tr>
		                <td>生日</td>
		                <td>
		                	<input id="adminBirthDate" name="admin.birthDate" value="${(admin.birthDate)!}" type="text" style="width:154px;">
		                </td>
		            </tr>
		            <tr>
		                <td>性别</td>
		                <td>
							<select name="admin.sex">
								<option value="1" <#if isEditAction && (admin.sex == 1)>selected</#if>>男</option>
								<option value="0" <#if isEditAction && (admin.sex == 0)>selected</#if>>女</option>
							</select>	                </td>
		            </tr>
		            <tr>
		                <td>办公电话</td>
		                <td><input type="text" name="admin.officePhone" value="${(admin.officePhone)!}"/></td>
		            </tr>
		            <tr>
		                <td>*手机</td>
		                <td><input type="text" name="admin.telephone" value="${(admin.telephone)!}"/></td>
		            </tr>
		            
		            <tr>
		                <td>手机mac</td>
		                <td><input type="text" name="admin.macAddress" value="${(admin.macAddress)!}" title="打卡时必须先绑定员工手机mac地址"/></td>
		            </tr>
		            <tr>
		                <td>个人照片</td>
		                <td>
		                	<input type="file" name="imageFile" id="inptPic" class="fileUpload"/>
		                	<#if (admin.url)?? && (admin.url!="") >
		                		<a href='${admin.url}' class="imageFileShow" target='_blank' style='margin-left:10px;'>查看</a>
		                	<#else>
	                            <a href='void(0)' class="imageFileShow" target='_blank' style='margin-left:10px;display:none'>查看</a>
	                        </#if>
	                        <input type="hidden" name="admin.url" value='${(admin.url)!}' />
	                	</td>
		                
		                
		            </tr>
	
		            <tr>
		                <td>在职状态</td>
		                <td>
							<select name="admin.manType">
								<option value="0" <#if isEditAction && (admin.manType == 0)>selected</#if>>非正式</option>
								<option value="1" <#if isEditAction && (admin.manType == 1)>selected</#if>>正式</option>
								<option value="2" <#if isEditAction && (admin.manType == 2)>selected</#if>>实习</option>
								<option value="3" <#if isEditAction && (admin.manType == 3)>selected</#if>>已离职</option>
								<option value="4" <#if isEditAction && (admin.manType == 3)>selected</#if>>兼职</option>
							</select>
						</td>
		            </tr>
		            
		            
		            
		            <tr>
		                <td>*管理角色</td>
		                <td>
							<#assign roleSet = (admin.roleSet)! />
							<#list allRoleList as temp>
								<label>
									<input type="checkbox" name="roleList.id" value="${temp.id}"<#if (roleSet.contains(temp))!> checked</#if> />${temp.name}
								</label>
							</#list>
						</td>
		            </tr>
		            <tr>
			                <td>*部门</td>
			                <td>
			                
								<#assign deparmentSet = (admin.deparmentSet)! />
	
								<input type="text" id="deparmentList" name="deparmentListTexts" readonly="true" value="<#if !isAddAction><#list deparmentSet as temp>${temp.name},&nbsp;&nbsp;</#list></#if>" />
							    <#if !isAddAction>
									<#list deparmentSet as temp>
											<input type="hidden" name="deparmentList.id" value="${temp.id}" />
									</#list>
								</#if>
							</td>
			            </tr>            
			            <tr>
			                <td>*主岗</td>
			                <td>
			                	
								<#assign mainJobSet = (admin.mainJobSet)! />
	
								<input type="text" id="mainJobList" name="mainJobListTexts" value="<#if !isAddAction><#list mainJobSet as temp>${temp.jobName},&nbsp;&nbsp;</#list></#if>" readonly="true" />
							    <#if !isAddAction>
									<#list mainJobSet as temp>
											<input type="hidden" name="mainJobList.id" value="${temp.id}"/>
									</#list>
								</#if>
							</td>
			            </tr>
			            <tr>
			                <td>副岗</td>
			                <td>
			               
								<#assign viceJobSet = (admin.viceJobSet)! />
	
						
								<input type="text" id="viceJobList" name="viceJobListTexts" value="<#if !isAddAction><#list viceJobSet as temp>${temp.jobName},&nbsp;&nbsp;</#list></#if>" readonly="true" />
								<#if !isAddAction>
									<#list viceJobSet as temp>
											<input type="hidden" name="viceJobList.id" value="${temp.id}" />
									</#list>
								</#if>
							</td>
			            </tr>
			            <tr>
			                <td>职务级别</td>
			                <td>
								
			                    <select name="admin.jobLevel.id">
									<#list jobLevelList as temp>
										<option value="${temp.id}"<#if isEditAction && temp.id == admin.jobLevel.id> selected</#if>>
										${temp.name}
										</option>
									</#list>
								</select>
							</td>
			            </tr>
			            <tr>
			                <td>备注</td>
			                <td>
								<textarea id="editor" name="admin.remark" class="editor" value="" style="width:350px;height:150px;"><#if isEditAction >${admin.remark}</#if></textarea>
							</td>
			            </tr>
		            <tr>
		                <td>设置</td>
		                <td>
							<label>
								<input type="checkbox" name="admin.isAccountEnabled" value="true" <#if isEditAction &&  admin.isAccountEnabled>checked="checked"</#if>/>启用
							</label>
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
			<div class="l mv_btn">
				<input type="button" class="btnUp" value="上移" /><br/><br/>
				<input type="button" class="btnDown" value="下移" />
			</div>			
		</div>
	</div>	
	<!--------------------------------->		
	<!--岗位选择   html---->
	<div class="content" id="contentPos" style="display:none;">
		<div> 
			<input type="text" class="txtSearch" />
			<input type="button" id="btnSearch" value="查找" />
		</div>
		<div class="list">
			<div class="l">
				<select class="ltype" style="width:210px;margin-top:5px;">
				</select>
				<br/>
				<select class="lselect select_list" multiple="multiple" style="height:326px;">
				</select>
			</div>
			<div class="l mv_btn">
				<input type="button" class="btnRight" value="右移" /><br/><br/>
				<input type="button" class="btnLeft" value="左移" />
			</div>
			<div class="l">
				<select  class="rselect select_list" multiple="multiple">
				</select>					
			</div>
			<div class="l mv_btn">
				<input type="button" class="btnUp" value="上移" /><br/><br/>
				<input type="button" class="btnDown" value="下移" />
			</div>			
		</div>
	</div>		
	<!---------------->	
	<!-- 副岗选择 HTML -->
	<div class="content" id="contentDeputyPost" style="display:none;">
		<div> 
			<input type="text" class="txtSearch" />
			<input type="button" class="btnSearch" value="查找" />
		</div>
		<div class="list">
			<div class="l">
				<div class="select_list_half" style="overflow:auto;border:1px solid #778;">
					<div class="domTree">
					</div>					
				</div>
				<select class="lselect select_list_half" multiple="multiple" >
				</select>
			</div>
			<div class="l mv_btn">
				<input type="button" class="btnRight" value="右移" /><br/><br/>
				<input type="button" class="btnLeft" value="左移" />
			</div>
			<div class="l">
				<select class="rselect select_list" multiple="multiple">
				</select>					
			</div>
			<div class="l mv_btn">
				<input type="button" class="btnUp" value="上移" /><br/><br/>
				<input type="button" class="btnDown" value="下移" />
			</div>			
		</div>
	</div>	
	<!--------------------------------->		
	
</body>
</html>