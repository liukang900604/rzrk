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
    <link rel="stylesheet" href="/rzrk/css/background/index.css" />
    <link rel="stylesheet" href="/rzrk/css/background/admin_input.css" />
    <script type="text/javascript" src="/rzrk/js/alljs.js"></script>
    <script type="text/javascript" src="/rzrk/js/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="/rzrk/js/jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="/rzrk/js/background/index.js"></script>
    <script type="text/javascript">
    	window.isAddAction = <#if isAddAction>true<#else>false</#if>;
    	window.jobList = ${jsonArray};
            window.departmentList = 
        <#if deparmentList??>
        
            ${deparmentList}
        
        <#else>
            {}
        </#if>
        ;
        
        window.deparmentAdminList = 
        <#if deparmentAdminList??>
            ${deparmentAdminList}
        
        <#else>
            {}
        </#if>
        ;
    </script>
    <script type="text/javascript" src="/rzrk/js/background/libs/selectMembers.js"></script>
    <script type="text/javascript" src="/rzrk/js/background/deparment_input.js"></script>
    
    <!--岗位选择   js css-->
    <link rel="stylesheet" href="/rzrk/css/selectPosition.css" />
    <script type="text/javascript" src="/rzrk/js/selectPos/select.js"></script>    
    <script type="text/javascript" src="/rzrk/js/selectPos/layer.min.js"></script>
    <script type="text/javascript">
    	/*
    		此步骤不可少，它用于初始化点击弹出的dom节点id、弹出框的标题描述、选择结果的输出的dom、被选的id编号所存放的hidden标签name名称
    		该脚本引用的位置介于select.js、layer.min.js 与 poplay.js之间
    	*/
		_output={
			click_dom:'#dept_Pos',
			title:'岗位选择',
			text_dom:'#dept_Pos',
			hidden_name:'jobList.id'
		};    	
    </script>
    <script type="text/javascript" src="/rzrk/js/selectPos/poplay.js"></script>    
    <!---------------->
    
</head>
<body class="input admin easyui-layout">
	<div data-options="region:'center'">
		<div class="easyui-panel" title="部门信息" data-options="fit:true,border:false">
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
		                <td>*部门名称</td>
		                <td><input  type="text" name="deparment.name" value="${(deparment.name)!}"/></td>
		            </tr>
		            <tr>
		                <td>部门代码</td>
		                <td><input  type="text" name="deparment.departmentCode" value="${(deparment.departmentCode)!}"/></td>
		            </tr>
		            <tr>
		                <td>*排序号</td>
		                <td><input  type="text" name="deparment.sortNo" value="${(deparment.sortNo)!}"/></td>
		            </tr>
		             <tr>
		                <td>主管别名</td>
		                <td><input  type="text" name="deparment.deparmentAlisa" value="${(deparment.deparmentAlisa)!}"/></td>
		            </tr>
		            <tr>
		                <td>*重复序号处理</td>
		                <td>
							<input type="radio" name="deparment.duplicateSortDeal" value="1" <#if isEditAction && (deparment.duplicateSortDeal== 1)>checked="checked"</#if>/>插入
	                        <input type="radio" name="deparment.duplicateSortDeal" value="0" <#if isEditAction && (deparment.duplicateSortDeal== 0)>checked="checked"</#if>/>重复
						</td>
		            </tr>
	
		            <tr>
		                <td>上级部门</td>
		                <td>	
		                    <select name="deparment.parent.id">
		                        <option value="" <#if isEditAction && !deparment.parent >selected</#if>>--无--</option>
								<#list parentDeparmentList as temp>
									<option value="${temp.id}" <#if isEditAction && deparment.parent && deparment.parent.id == temp.id >selected</#if>>${temp.name}</option>
								</#list>
							</select>
		                </td>
						
		            </tr>
		            <tr>
		                <td>部门岗位</td>
		                <td>	
		                   <input type="text" id="dept_Pos" name="jobNameList" readonly="true" value="<#if !isAddAction><#list deparment.deparmentJob as temp>${temp.jobName},&nbsp;&nbsp;</#list></#if>"/>
		                  <#if !isAddAction>
			                   <#list deparment.deparmentJob as temp>
			                   		<input type="hidden" name="jobList.id" value="${temp.id}"/>
			                   </#list>
		                  </#if>
		                </td>
		            </tr>
		            <tr>
		                <td>*状态</td>
		                <td>
							<select name="deparment.status">
								<option value="1" <#if isEditAction && (deparment.status == 1)>selected</#if>>启用</option>
								<option value="0" <#if isEditAction && (deparment.status == 0)>selected</#if>>禁止</option>
							</select>
						</td>
		            </tr>
		            <tr>
		                <td>*主管</td>
		                <td>
							<select id="zhuguan" name="deparment.deparmentLeader.id" class="easyui-combobox">
								<#list adminList as temp>
									<option value="${temp.id}" <#if isEditAction && (deparment.deparmentLeader.id == temp.id)>selected</#if>>${temp.name}</option>
								</#list>
							</select>
						</td>
		            </tr>
		            <tr>
		                <td>分管领导</td>
		                <td>
							<select id="fenguan" name="deparment.departmentOtherLeader.id" class="easyui-combobox">
								<#list adminList as temp>
									<option value="${temp.id}" <#if isEditAction  && (deparment.departmentOtherLeader.id == temp.id) >selected</#if>>${temp.name}</option>
								</#list>
							</select>
	
						</td>
		            </tr>
                    <tr>
                        <td><em>*</em>项目成员</td>
                        <td>
                            <input type="text" name="deparmentAdminsText" id="adminList" class="adminList" value="<#if isEditAction><#list deparment.deparmentAdmins as admin><#if admin_index != 0>, </#if>${admin.name}</#list></#if>"/>
                            <#if isEditAction>
                                <#list deparment.deparmentAdmins as admin>
                                <input type="hidden" name="deparmentAdmins.id" value="${admin.id}"/>
                                </#list>
                            </#if>
                        </td>
                    </tr>
					<tr>
						<td>
							部门描述 
						</td>
						<td>
							<textarea name="deparment.description"  class="formTextarea" style="width:280px; height:55px;">${(deparment.description)!}</textarea>
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
	
	<!--岗位选择   html---->
	<div id="content" style="display:none;">
		<div> 
			<input type="text" id="txtKey" />
			<input type="button" id="btnSearch" value="查找" />
		</div>
		<div class="list">
			<div class="l">
				<select id="pl" style="width:210px;margin-top:5px;">
				</select>
				<br/>
				<select id="lselect" class="select_list" multiple="multiple" style="height:326px;">
				</select>
			</div>
			<div class="l mv_btn">
				<input type="button" id="btnRight" value="右移" /><br/><br/>
				<input type="button" id="btnLeft" value="左移" />
			</div>
			<div class="l">
				<select id="rselect" class="select_list" multiple="multiple">
				</select>					
			</div>
			<div class="l mv_btn">
				<input type="button" id="btnUp" value="上移" /><br/><br/>
				<input type="button" id="btnDown" value="下移" />
			</div>			
		</div>
	</div>		
	<!---------------->
	
</body>
</html>