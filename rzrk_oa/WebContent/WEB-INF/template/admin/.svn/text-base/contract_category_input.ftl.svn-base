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
    <link rel="stylesheet" href="/rzrk/css/jquery-nestable/jquery.nestable.css" />
    <link rel="stylesheet" href="/rzrk/js/codemirror/lib/codemirror.css" />
    
    <script type="text/javascript" src="/rzrk/js/alljs.js"></script>
    <script type="text/javascript" src="/rzrk/js/base64.js"></script>
    <script type="text/javascript" src="/rzrk/js/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="/rzrk/js/jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="/rzrk/js/jstree/jstree.min.js"></script> 
    <script type="text/javascript" src="/rzrk/js/jquery-nestable/jquery.nestable.js"></script> 
     <script type="text/javascript" src="/rzrk/js/background/index.js"></script>
     <script type="text/javascript" src="/rzrk/js/selectPos/contract-department.js"></script>
     <script type="text/javascript" src="/rzrk/js/codemirror/lib/codemirror.js"></script>
     <script type="text/javascript" src="/rzrk/js/codemirror/mode/javascript/javascript.js"></script>
     <style>
     .controlhide{
        display:none;
     }
     
     </style>  
    <script type="text/javascript">
    	window.isAddAction = <#if isAddAction>true<#else>false</#if>;
//    	window.parserNames = 
//    	<#if parserNames??>
//			"${parserNames}"
//		<#else>
//			""
//		</#if>
//    	;
        window.departmentJobList = 
        <#if jsonObj??>
        {
            "root" :${jsonObj}
        }
        <#else>
            {}
        </#if>
        ;
        window.depTree4Easyui = 
        <#if depTree4Easyui??>
        ${depTree4Easyui}
        <#else>
            []
        </#if>
        ;
        window.admin4Easyui = 
        <#if admin4Easyui??>
        ${admin4Easyui}
        <#else>
            []
        </#if>
        ;
    	window.definition = 
    	<#if (contractCategory.definition)??>
    	${contractCategory.definition}
        <#else>
            []
        </#if>
        ;
        window.contractCategoryOptionList = 
        <#if (contractCategoryOptionListJson)??>
        ${contractCategoryOptionListJson};
        <#else>[]</#if>
        ;
        window.scriptLibraryOptionList = 
        <#if (scriptLibraryOptionListJson)??>
        ${scriptLibraryOptionListJson};
        <#else>[]</#if>
        ;
        
        window.contentProviderList = ${contentProviderList};
        window.definitionWidget = null; //等待createDefinitionWidget初始化
        window.controlFieldWidget = null; //可变的人员下拉框
   
            window.departmentList = 
        <#if deparmentListJson??>
        
            ${deparmentListJson}
        
        <#else>
            {}
        </#if>
        ;
        
        window.deparmentAdminList = 
        <#if deparmentAdminListJson??>
            ${deparmentAdminListJson}
        
        <#else>
            {}
        </#if>
        ;
    </script>
    <script type="text/javascript" src="/rzrk/js/background/ajaxfileupload.js"></script>
    <script type="text/javascript" src="/rzrk/js/background/libs/selectMembers.js"></script>
    <script type="text/javascript" src="/rzrk/js/background/contract_category_input.js"></script>
      <link rel="stylesheet" href="/rzrk/css/selectPosition.css" />
    <script type="text/javascript" src="/rzrk/js/selectPos/layer.min.js"></script>
    <script type="text/javascript" src="${base}/template/common/editor/kindeditor.js"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'center'">
		<div class="easyui-panel" title="分类" data-options="fit:true,border:false">
			<form id="atForm" method="post">
				<input type="hidden" name="id" value="${id}"/>
		        <table cellpadding="5" style='width:100%;'>
		        	<colgroup>
		        		<col style="width:100px;"/>
		        		<col/>
		        	</colgroup>
	                <tr>
	                    <td colspan="2" style="text-align: left;"><span class="warnInfo">
	                    <#if isAddAction>
	                                                                        导入时，excel第一列为主键
	                    <#else>
	                                                                       主键不能修改， 导入时，字段将会被覆盖，请保证excel中字段正确
	                    </#if>
	                    </span></td>
	                </tr>
		            <tr>
		                <td>分类名称</td>
		                <td>
			                <input type="text" name="contractCategory.name"  class="formText" value="${(contractCategory.name)!}"  style="width:192px;" /><!--<#if isEditAction>disabled="disabled"</#if>-->
	                        <input type="hidden" name="contractCategory.fields" class="formText" value="${(contractCategory.fields)!}" />
	                        <input type="hidden" name="contractCategory.totals" class="formText" value="${(contractCategory.totals)!}" />
                            <input type="hidden" name="contractCategory.definition" class="formText" value="" /><!--由提交时初始化由js赋值-->
                            <input type="hidden" name="contractCategory.contentProvider" class="formText" value="" /><!--由提交时js赋值-->
                            <!--
			               <#if isEditAction> <input type="hidden" id="contractCategoryName" name="contractCategory.name" class="formText" value="${(contractCategory.name)!}" /></#if> 
	                       -->
		                </td>
		            </tr>
                    <tr>
                        <td>一级分类</td>
                        <td>
                            <select name="contractCategory.rootContractCategory.id">
                            <#if rootContractCategoryList??>
                                <#list rootContractCategoryList as temp>
                                    <option value="${temp.id}"<#if isEditAction && temp.id == contractCategory.rootContractCategory.id> selected</#if>>
                                    ${temp.name}
                                    </option>
                                </#list>
                                <#else>
                           </#if>
                            </select>
                        </td>
                    </tr>
		            <tr  style="<#if isEditAction>display:none</#if>" >
		                <td>页面直接访问</td>
		                <td>
	   	                    <select name="contractCategory.isUrlView" id="isUrlView">
								<option value="0"<#if isEditAction && contractCategory.isUrlView == 0>selected</#if>>否</option>
								<option value="1"<#if isEditAction && contractCategory.isUrlView == 1>selected</#if>>是</option>
							</select>
		                </td>
		            </tr>
		            <tr class="trueStep">
		                <td>url地址</td>
		                <td>
	   	                    <input type="input" name="contractCategory.url" class="formText" value="${(contractCategory.url)!}" />
		                </td>
		            </tr>
		            <tr style="display:none">
		                <td>是否审批</td>
		                <td>
	   	                    <select name="contractCategory.approvalNeeded" id="isFlow">
								<option value="0"<#if isEditAction && contractCategory.approvalNeeded == 0>selected</#if>>否</option>
								<option value="1"<#if isEditAction && contractCategory.approvalNeeded == 1>selected</#if>>是</option>
							</select>
							<span style="color:red;">（修改数据是否需要审核）</span>
		                </td>
		            </tr>
		            
		           
		             <tr class="undelete" style="display:none;">
		                <td>审批流程</td>
		                <td>
		                 <select name="contractCategory.workFlowId">
		                 <option value="">请选择</option>
		                  <#if (contractCategory.workFlowId)??>
		                    <#list workFlowList as type>
										<option value="${type.id}" <#if isEditAction && type.id == contractCategory.workFlowId> selected</#if>>
										${type.flowName}
										</option>
									</#list>
						 <#else>
						    <#list workFlowList as type>
										<option value="${type.id}">
										${type.flowName}
										</option>
									</#list>
						 
						 </#if>
							</select>
		                </td>
		            </tr>
                    <tr class="falseStep">
                     <td>查询控制
                     </td>
                     <td>
                         <label>
                            <input type="checkbox" name="contractCategory.controlType" value="bydep" <#if isEditAction && ((contractCategory.controlType)!)?index_of("bydep")!=-1 >checked="checked"</#if> > 按部门限制
                         </label>
                         <label>
                            <input type="checkbox" name="contractCategory.controlType" value="byop" <#if ((contractCategory.controlType)!)?index_of("byop")!=-1 >checked="checked"</#if> > 按人员限制 
                         </label>
                     </td>
                    </tr>
                    <tbody class="opContractContent falseStep" >
                    <tr>
                     <td>限制字段
                     </td>
                     <td>
                        <select name="contractCategory.controlField" id="controlFieldWidget" oldvalue="${(contractCategory.controlField)!}" >
                            <option value="__create_admin">创建人</option>
                        </select>
                     </td>
                    </tr>
                    </tbody>
                    <tbody class="depContractContent falseStep" >
		            <tr>
		             <!--   <td>查看人</td>
		                <td>
		                	<ul class=""userList id="adminListId">
		                <#if contractCategory??>
		                	  <#list contractCategory.viewPowerAdminList as type>
								  <li class="deleteAdminId">${type.name}<span>X</span><input type="hidden"  name="viewerAdminList.id" value="${type.id}"/></li>
							  </#list>
							<#else>
		                 </#if>
		                		<li class="addColleague">
		                			<a href="javascript:">查看人</a>
		                			<input class="userBox" style="width:100px;display:none;" />
	                			</li>
		                	</ul>
		                </td>-->
		                   <td>部门</td>
			                <td>
			                
								<#assign deparmentSet = (contractCategory.deparmentSet)! />
	
								<input type="text" id="deparmentList" name="deparmentListTexts" readonly="true" value="<#if !isAddAction><#list deparmentSet as temp>${temp.name},&nbsp;&nbsp;</#list></#if>" />
								
							  <#if !isAddAction>
									<#list deparmentSet as temp>
											<input type="hidden" name="deparmentList.id" value="${temp.id}" />
									</#list>
								</#if>
								
							</td>
		            </tr>
	                <tr>
	                 <td></td>
	                 <td>
	                 <label>
	                 <input type="checkbox"  value="1" name="contractCategory.isView"  <#if isAddAction || (contractCategory.isView)! == 1 >checked="checked"</#if>  />是否允许部门人员查看
	                 </label>
	                 </td>
	                </tr>
                    <tr>
                     <td></td>
                     <td>
                     <label>
                     <input type="checkbox"  value="1" name="contractCategory.isSubDepView"  <#if isAddAction || (contractCategory.isSubDepView)! == 1 >checked="checked"</#if>  />是否允许子部门查看
                     </label>
                     </td>
                    </tr>
                    <tr>
                     <td></td>
                     <td>
                     <label>
                     <input type="checkbox"  value="1" name="contractCategory.isSuperiorView"  <#if isAddAction || (contractCategory.isSuperiorView)! == 1 >checked="checked"</#if>  />是否允许上级部门查看
                     </label>
                     </td>
                    </tr>
	                </tbody>
		            <tr style="display:none">
		                <td>分类字段</td>
		                <td>
							<div class="content userList" id="contentPos" style="">
								<div class="list">
									<div class="l" >
										<div style="margin-top:10px;">
											待选择字段
										</div>
										<div style="margin-top:10px;">
											<input type="text" name="contractCategory.custom" class="textCustom formText" value="" maxlength="16"/>
											<input type="button" class="btnCustom" value="自定义" />
										</div>
										<div style="margin-top:10px;">
											<select class="lselect select_list" multiple="multiple" >
											</select>
										</div>
									</div>
									<div class="l mv_btn">
										<input type="button" class="btnRight" value="右移" /><br/><br/>
										<input type="button" class="btnLeft" value="左移" />
									</div>
									<div class="l">
										<div style="margin-top:10px;">
											选定字段
										</div>
										<div style="margin-top:10px;">
											主键 &nbsp;<input type="text" name="indication" class="indication formText" value="" />
										</div>
										<div style="margin-top:10px;">
										<!--
											<select  class="filed_select select_list" multiple="multiple">
											</select>	
										-->
										<div class="right">
										   <table class="filed_select">
										   </table></div>
										</div>
									</div>
									<div class="l mv_btn">
										<input type="button" class="btnUp" value="上移" /><br/><br/>
										<input type="button" class="btnDown" value="下移" />
									</div>			
								</div>
							</div>		
		                </td>
		            </tr>
                    <tr class="falseStep">
                        <td>父表</td>
                        <td>
                            <input type="text" class="parentId" name="contractCategory.parent.id" value= "<#if (contractCategory.parent)?? >${(contractCategory.parent.id)!}</#if>" >
                        </td>
                    </tr>
                    <#assign contractCategorySet = (contractCategory.childSet)! />
                    <tr class="falseStep">
                        <td>子表</td>
                        <td>
                            <input type="text"  class="childId"  name="contractCategoryList.id" value= "<#list contractCategorySet as item><#if item_index != 0>,</#if>${item.id}</#list>" >
                        </td>
                    </tr>
	                <tr class="falseStep">
	                    <td>导入模板</td>
	                    <td>
	                        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'">导入类型模板</a>
	                        <input id="updateContractCategory" type="file" name="updateContract" data-url='contract_upload!updateContractCategory.action' class="fileUpload" />
	                    </td>
	                </tr>
	                <tr class="falseStep">
	                    <td></td>
	                    <td>
                            <a href="#" id="addField" class="easyui-linkbutton" data-options="iconCls:'icon-add'">添加字段</a>&nbsp;&nbsp;&nbsp;&nbsp;（可拖拽，首个为主键）
	                    </td>
	                </tr>
                    <tr class="falseStep">
                        <td>字段列表</td>
                        <td>
                             <div class="dd" id="definitionWidget">
                                <ol class="dd-list">
                                <!--
                                    <li class="dd-item dd3-item" data-id="13">
                                        <div class="dd-handle dd3-handle" style="display:none">Drag</div>
                                        <div class="dd3-content" onclick="javascript:alert(1);" >
                                            <a href="#">Item 13</a>
                                            <label>
                                            <input type="checkbox" />
                                                                                                                                                    统计
                                            </label>
                                        </div>
                                    </li>
                                    <li class="dd-item dd3-item" data-id="14">
                                        <div class="dd-handle dd3-handle">Drag</div><div class="dd3-content">Item 14<input type="checkbox" /></div>
                                    </li>
                                 -->
                                </ol>
                            </div>
                        </td>
                    </tr>
                    <#assign scriptLibrarySet = (contractCategory.scriptLibrarySet)! />
                    <tr class="falseStep">
                        <td>功能脚本库</td>
                        <td>
                            <input type="text"  class="scriptLibraryList"  name="scriptLibraryList.id" value= "<#list scriptLibrarySet as item><#if item_index != 0>,</#if>${item.id}</#list>" >
                        </td>
                    </tr>
                    <tr class="falseStep">
                        <td>自定义功能脚本</td>
                        <td>
                            <textarea id="code" name="contractCategory.script" value="${encodeScript}"></textarea>
                        </td>
                    </tr>
	                <tr class="falseStep"> 
	                		     <td>
	                 				字段集合
	                                </td>
	                                   <td>
	                                   <select class="fieldList">
	                        		</select>
	                                    <a href="javascript:void(0)" data-options="iconCls:'icon-add'" class="easyui-linkbutton" id="insertLabel">
	                                                                                                                                         插入标题
	                                    </a>
		                                    <a href="javascript:void(0)" data-options="iconCls:'icon-add'" class="easyui-linkbutton" id="insertField">
		                                                                                                                                         插入输入框
		                                    </a>
	                                </td>
	                </tr>
	                <tr id="report" class="falseStep">
		                <td>字段样式 </td>
		                <td><textarea id="editor" name="contractCategory.formTemplate" class="editor" value=""></textarea>
		                  <div id="editorContent" style="display:none">${(contractCategory.formTemplate)!}</div>
		                </td>
		            </tr>
		        </table>
		    </form>
		</div>
        <!-- 弹出框 -->
	    <div class="content" id="definitionEdit" style="display:none;">
	            <form id="definitionForm" method="post">
	        <table style="width:450px">
	            <tr>
	                <td style="width:30%">名称</td>
	                <td>
	                    <input type="text" name="name" class="formText" value="" maxlength="16"/>
	                </td>
	            </tr>
                <tr>
                    <td>必填</td>
                    <td>
                        <input type="checkbox" name="required" class="formText" value="1" />
                    </td>
                </tr>
                <tr>
                    <td>内置</td>
                    <td>
                        <input type="checkbox" name="built" class="formText" value="1" />
                    </td>
                </tr>
	            <tbody class="builtTr">
                <tr >
                    <td>内置标记</td>
                    <td>
                        <select name="builtsign" >
                            <option value="用户名">用户名</option>
                            <option value="部门">部门</option>
                            <option value="岗位">岗位</option>
                            <option value="产品">产品</option>
                            <option value="级别">级别（行样式）</option>
                            <option value="" disabled>————————</option>
                            <option value="二级分类列内容">二级分类列内容</option>
                        <select>
                    </td>
                </tr>
                <tr  class="builtdataContentProvider">
                    <td>表名</td>
                    <td>
                        <select name="builtdataContentProviderTable" >
                        <select>
                    </td>
                </tr>
                <tr  class="builtdataContentProvider">
                    <td>列名</td>
                    <td>
                        <select name="builtdataContentProviderField" >
                        <select>
                    </td>
                </tr>
	            </tbody>
	            <tbody class="notbuiltTr">
	            <tr >
	                <td>类型</td>
	                <td>
	                    <select name="type" >
	                        <option value="文本框">文本框</option>
	                        <option value="日期框">日期框</option>
	                        <option value="时间框">时间框</option>
	                        <option value="日期时间框">日期时间框</option>
	                        <option value="文本区">文本区</option>
	                        <option value="下拉框">下拉框</option>
	                        <option value="复选框">复选框</option>
	                        <option value="单选框">单选框</option>
                            <option value="选择树">选择树</option>
                            <option value="表达式">表达式</option>
	                    <select>
	                </td>
	            </tr>
	            <tr >
	                <td>可选项(逗号分隔)</td>
	                <td>
	                    <input type="text" name="options" class="formText" value="" maxlength="160"/>
	                </td>
	            </tr>
	            </tbody>
                <tr>
                    <td>描述(表达式/选择树)</td>
                    <td>
                        <textarea name="expression" class="formText" ></textarea>
                    </td>
                 </tr>
                <tr>
                    <td>统计</td>
                    <td>
                        <input type="checkbox" name="total" class="formText" value="1" />
                    </td>
                 </tr>
                <tr>
                    <td>限制可见人</td>
                    <td>
                        <input type="text" class="formText adminIds" value="" maxlength="160"/>
                    </td>
                 </tr>
                <tr>
                    <td>限制可见部门</td>
                    <td>
                        <input type="text" name="departmentIds" class="formText departmentIds" value="" maxlength="160"/>
                    </td>
                 </tr>
                <tr>
                    <td>上级部门可见</td>
                    <td>
                        <input type="checkbox" name="superiorView" class="formText" value="1" />
                    </td>
                 </tr>
                <tr>
                    <td>作为内置</td>
                    <td>
                    <label>
                          <input type="checkbox" name="contentProvider" class="formText" value="1" />
                                                                                         二级分类列内容提供者
                    </label>
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td>
                        <div style="padding:10px;">
                            <a href="javascript:void(0)" data-options="iconCls:'icon-save'" class="easyui-linkbutton saveBtn" >&nbsp;保&nbsp;存&nbsp;&nbsp;</a>
                            &nbsp;&nbsp;
                            <a href="javascript:void(0)" data-options="iconCls:'icon-cancel'" class="easyui-linkbutton deleteBtn" >&nbsp;删&nbsp;除&nbsp;&nbsp;</a>
                        </div>
                    </td>
                 </tr>
	             
	        </table>
	        </form>
	    </div>
	    <#if isEditAction && clogs?? && (clogs?size>0) >
	       <h3>修改历史</h3>
	      <table style="width:100%;border: solid 1px #000000; text-align: center;">
	        <tr style="background-color: #c8c8e8;color:#00000;font-weight: bold;width:20%"><th style="text-align: center;width:20%">日期</th><th>操作者</th><th  style="text-align: center;width:60%">内容</th></tr>
	        <#list clogs as item>
	              <tr style="background-color: #d8d8e8;color:#00000;"><td style="text-align: center;">${item.modifyDate}</td><td>${item.operator.name}</td><td style="text-align: left">${item.content}</td></tr>
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