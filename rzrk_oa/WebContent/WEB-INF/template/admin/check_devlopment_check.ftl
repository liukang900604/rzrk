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
    <link rel="stylesheet" href="/rzrk/css/background/admin_input_jstree.css" />
    <link rel="stylesheet" href="/rzrk/css/background/work_input.css" />
     <link rel="stylesheet" href="/rzrk/css/background/work_flow_input.css" />
    <link rel="stylesheet" href="/rzrk/css/background/file_upload.css" />
    <script type="text/javascript" src="/rzrk/js/alljs.js"></script>
    <script type="text/javascript" src="/rzrk/js/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="/rzrk/js/jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="/rzrk/js/jstree/jstree.min.js"></script>
    <script type="text/javascript" src="/rzrk/js/selectPos/layer.min.js"></script>
    <script type="text/javascript" src="/rzrk/js/background/index.js"></script>
    <script type="text/javascript" src="${base}/template/common/editor/kindeditor.js"></script>  
    <script type="text/javascript" src="/rzrk/js/background/ajaxfileupload.js"></script>
     <script type="text/javascript" src="/rzrk/js/background/file_upload.js"></script>
    <script type="text/javascript">
    	window.isAddAction = <#if isAddAction>true<#else>false</#if>;
    </script>
    <script type="text/javascript" src="/rzrk/js/background/work_form_editor.js"></script>
    <script type="text/javascript" src="/rzrk/js/background/check_develpment_check.js"></script>

    
</head>
<body class="input admin">
	<div class="view">
        <div class="m_c">
        <form id="atForm" method="post">
			<table cellpadding="0" cellspacing="0">
	            <tbody>
	            	<tr>
	                    <td class="td-left">工作名称：</td>
	                    <td class="td-right">
	                    	<input type="text" name="work.workName" />
	                    	<input type="hidden" name="work.id"/>
	                    	<input type= "hidden" id="isInternal" name="work.isInternal" value="" />
                            <input type= "hidden" name="work.formContent" id="formContent"  value=""></input> 
                            <input type= "hidden" id="workType" name="work.workType" value="1" />
                            <input type= "hidden"  name="work.currentPointId"   id="pointId" />
                            
						</td>
	                </tr>
	            	<tr>
	                    <td class="td-left">流程类型：</td>
	                    <td class="td-right">
	                    	<#if workFlow?? >
	                    		<#if workFlow.flowType??>
								<input type="hidden" name="work.flowType.id" id="flowTypeId" value="${(workFlow.flowType.id)!}"/>
								<input type="text"  value="${(workFlow.flowType.typeName)!}" readonly="readonly"/>
								<#else>
								<input type="hidden" name="work.flowType.id" id="flowTypeId" value=""/>
								<input type="text"  value="" readonly="readonly"/>
								</#if>
							<#else>
								<input type="hidden" name="work.flowType.id" id="flowTypeId" value=""/>
								<input type="text"  value="" readonly="readonly"/>
							</#if>
						</td>
	                </tr>
	            	<tr>
	                    <td class="td-left">流程名称：</td>
	                    <td class="td-right">
	                    <#if workFlow?? >
							<input type="hidden" name="work.workFlow.id" id="workFlowId" value="${(workFlow.id)!}"/>
							<input type="text" value="${(workFlow.flowName)!}" readonly="readonly"/>
						<#else>
						    <input type="hidden" name="work.workFlow.id" id="workFlowId" value=""/>
							<input type="text" value="" readonly="readonly"/>
						</#if>
						</td>
	                </tr>
                	<tr>
	                    <td class="bd-top bd-bottom" colspan="2" style="padding-top:10px;">
	                    	<div class="form" >
	                    	  <div id="editor">
	                    	  </div>
	                    	 <!--<textarea id="editor" name="work.formContent" class="editor" value="" style="width:100%;height:300px;display:none"></textarea>-->
	                    	</div>
	                    </td>
	                </tr>
	               <tr>
	                    <td class="td-left bd-top">
	                       <h3 style="white-space:nowrap;">附件列表：</h3>
	                    </td>
	                    <td>
	                    <input id="file" type="file"  multiple=""  name="file" data-url='file_upload!fileUpload.action'  />               
	                    </td>
	                </tr>
	                 <table id="down">
				         </table>
				         
				  
	            </tbody>
	   
			</table>
				   <fieldset  form="atForm" >
		    				<legend style="text-align:center;">项目填写信息,<em style="color:red;">*</em>为必填信息</legend>
		    				<p>
		    				 <tr>
			                    <td class="td-left"><em style="color:red;">*</em>项目名称</td>
			                    <td class="td-right">
			                    <input type="text" name="workFlowVo.name" id="projectName" value="" ></input>
								</td>
			                </tr>
		    				 <tr>
			                    <td class="td-left"><em style="color:red;">*</em>负责人  </td>
			                    <td class="td-right">
			                    <select name="workFlowVo.responsiborId" id="responsiborId">
	                	 		 <#list adminList as admin>
	                					<option value="${admin.id}" <#if isEditAction && project.responsibor.id == admin.id >selected="selected"</#if>>${admin.name}</option>
	                	 					 </#list>
	                			</select>
	                	        <span style="color:#aaa;margin-left:10px;">支持首字母索引</span>
								</td>
			                </tr>
		    				  <tr>
			                    <td class="td-left"><a href='#' id="selectRequest"><em style="color:red;">*</em>选择需求列表</a></td>
			                    <td class="td-right">
			                    <input type="hidden" name="requestField" id="requestId" value=""></input>
			                    <input type="text" name="requestName" id="requestName" value="" readonly="readonly"></input>
								</td>
			                </tr>
			                 <tr>
			                    <td class="td-left"><em style="color:red;">*</em>投入人数(人)  </td>
			                    <td class="td-right">
			                    <input type="text" name="workFlowVo.personNumber" id="personNumber" value="" ></input>
								</td>
			                </tr>
			                 <tr>
			                    <td class="td-left"><em style="color:red;">*</em>预计时间(天) </td>
			                    <td class="td-right">
			                    <input type="text" name="workFlowVo.predictDay" id="predictDay" value="" ></input>
								</td>
			                </tr>
			                <tr>
			                    <td class="td-left">项目描述 </td>
			                    <td class="td-right">
			                    <input type="text" name="workFlowVo.projectDesc" id="projectDesc" value="" ></input>
								</td>
			                </tr>
			                </p>
			                
		    	  </fieldset>
	    </form>
        </div>
	    <div id="workFlowShow">
	    <ul class="flow">
		<li class="start">
			<span>发起人</span>
		</li>
		<li class="link"></li>
		
		<li class="end"><span>结束</span></li>
		</ul>       
		</div> 
        
	</div>
	<div style="padding:10px 0">
	    <a href="javascript:void(0)" data-options="iconCls:'icon-save'" class="easyui-linkbutton" id="saveBtn">&nbsp;保&nbsp;存&nbsp;&nbsp;</a>
	    &nbsp;&nbsp;
	    <a href="javascript:void(0)" data-options="iconCls:'icon-cancel'" class="easyui-linkbutton" id="closeBtn">&nbsp;关&nbsp;闭&nbsp;&nbsp;</a>
	</div>

</body>
</html>