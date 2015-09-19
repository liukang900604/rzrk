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
    <link rel="stylesheet" href="/rzrk/css/background/work_flow_editworkCheck.css" />
     <link rel="stylesheet" href="/rzrk/css/background/work_flow_input.css" />
    <script type="text/javascript" src="/rzrk/js/alljs.js"></script>
    <script type="text/javascript" src="/rzrk/js/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="/rzrk/js/jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="/rzrk/js/background/index.js"></script>
    <script type="text/javascript" src="/template/common/editor/kindeditor.js"></script>
    <script type="text/javascript" src="/rzrk/js/background/work_form_editor.js"></script>
    <script type="text/javascript" src="/rzrk/js/background/work_flow_editworkCheck.js"></script>
   <script type="text/javascript">
    	window.checkSort = <#if (work.checkSort)??>${(work.checkSort)!}<#else>{}</#if>;
    	window.loginAdmin = <#if (work.admin)??>"${(work.admin.name)!}"<#else>""</#if>; 
    	window.workType = 
    	<#if (work.workType)??>
			${work.workType}
   		<#else>
			""
		</#if>
    	;
    	window.expand = 
    	<#if (work.expand)??>
			${work.expand}
   		<#else>
			{}
		</#if>
    	;
    </script>
    
    <!--
    <script type="text/javascript" src="/rzrk/js/background/work_form_readonly.js"></script>
    -->
    <script>
    	$(function(){
    		if($("#littleTitle").next().attr("id")=="fujian"){
    			$("#littleTitle").hide();
    		}else{
    			$("#fujian").prev().css("background","#99FF99");
    		}
    		

    	});
    </script>
</head>
<body class="input admin" style="overflow:auto;">
	<div class="view">
	   <form id="atForm" method="post">
	   <input type= "hidden" name="work.formContent" id="formContent"  value=""></input> 
	   <#if flowPoint??>
	             <input type="hidden" name="work.currentPointId" value="${(flowPoint.id)!}" />
	             <input type="hidden" id="searchName" value="${(flowPoint.searchName)!}" />
	   </#if>
	   			 <input type="hidden" id="editWorkFlowId"  value="${(work.flowId)!}" />
	   			 <input type="hidden" id="version"  value="${(work.version)!}" />
		<input type="hidden" id="workId" name="work.id" value="${(work.id)!}"/>
		<h2>标题：${work.workName}</h2>
		<div class="top_r">
            <span>发起人：</span>
            <span style="margin-right: 10px;">${(work.admin.name)!}</span>
            <span>发起时间：</span>
            <span>${(work.createDate?string("yyyy-MM-dd HH:mm:ss"))!}</span>
        </div>
        <div class="m_c">
			<table cellpadding="0" cellspacing="0">
	            <tbody>
	            	<tr>
	                    <td class="td-left">所属流程：</td>
	                    <td class="td-right">${(work.flowName)!}</td>
	                    <td class="td-left">流程类型：</td>
	                    <td class="td-right">${(work.flowTypeName)!}</td>
	                </tr>
	                <tr>
	                    <td class="td-left bd-bottom">当前节点：</td>
	                    <td class="td-right bd-bottom">${(work.currentPointName)!}</td>
	                    <td class="td-left bd-bottom">节点审批人：</td>
	                     <#if work.status == 3>
	                     <td class="td-right bd-bottom">${(work.currentAdminName)!}</td>
	                    <#else>
	                    <td class="td-right bd-bottom"></td>
	                    </#if>
                	</tr>
                	<tr>
	                    <td class="td-right bd-top" colspan="4">
	                        <h3>表单内容</h3>
	                    </td>
	                </tr>
                	<tr>
	                    <td colspan="4">
	                    	<div class="form">
	                    	<div id="editor">
	                    	${(work.formContent)!}
	                    	</div>
	                    	</div>
	                    </td>
	                </tr>
	                <tr>
	                    <td class="td-right bd-top" colspan="4" style="font-size:16px">
	                        <h3>审批记录</h3>
	                    </td>
	                    
	                </tr>
	                <tr id="littleTitle">
	                    <td class="td-right bd-top" colspan="4" style="font-size:14px">
	                        <h3>人员&nbsp;&nbsp;&nbsp;&nbsp;日期&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;事件&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;审批意见</h3>
	                    </td>
	                    
	                </tr>
	                <#list recordList as record>
	                 <tr>
	                    <td class="td-right bd-top" colspan="4" style="font-size:10px">
	                        <h3>${record.approvalPerson}&nbsp;&nbsp;${(record.createDate?string("yyyy-MM-dd HH:mm:ss"))!}&nbsp;&nbsp;<#if record.status != "重新提交">审批节点: ${record.pointName}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</#if>${record.advice}</h3>
	                    </td>
	                    
	                </tr>		
						</#list>
						
				
	                <tr id="fujian">
	                    <td class="td-right bd-top" colspan="4">
	                        <h3 style="float: left; margin-right: 15px;">附件列表</h3>
	                    </td>
	               </tr>
	               <table id="down">
		                 <#list fileList as file>
						  	 <tr><td height='25' width='100px'>${file.fileName}</td>
						  <td width='40px'>
							<a href="file_upload!downloadFile.action?downloadFilePath=${file.filePath}&downloadFileName=${file.fileName}" >下载</a>
						 </td>
						 <td  width='40px'>
							<a href="file_upload!showFile.action?downloadFilePath=${file.filePath}&downloadFileName=${file.fileName}" target ="_blank" >预览</a>
						 </td>
						  </tr>		
					</#list>
				         </table>
				      <#if work.workType == 1>
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
	                					<option value="${admin.id}">${admin.name}</option>
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
		    </#if>
	                <tr>
	                    <td class="td-right bd-top" colspan="4">
	                        <h3 style="float: left; margin-right: 15px;">审批意见</h3>
	                        <textarea row="1" col="1" name="record.advice"></textarea>
	                    </td>
	                </tr>
	            </tbody>
			</table>
			
			</form>
	
	
        </div>
     	
     	  <#include "/WEB-INF/template/admin/work_flow_chart.ftl">
	</div>
	
	  
	<div style="padding:10px 0">
	    <a href="javascript:void(0)" data-options="iconCls:'icon-save'" class="easyui-linkbutton" id="saveBtn">&nbsp;审批通过&nbsp;</a>
	    &nbsp;&nbsp;
	    <a href="javascript:void(0)" data-options="iconCls:'icon-cancel'" class="easyui-linkbutton" id="denyBtn">&nbsp;驳回到上一级&nbsp;</a>
	    &nbsp;&nbsp;
	    <a href="javascript:void(0)" data-options="iconCls:'icon-cancel'" class="easyui-linkbutton" id="denyFirstBtn">&nbsp;驳回到发文人&nbsp;</a>
	    &nbsp;&nbsp;
	   <!-- <a href="javascript:void(0)" data-options="iconCls:'icon-cancel'" class="easyui-linkbutton" id="denyToNodeBtn">&nbsp;驳回到其他节点&nbsp;</a>-->
	</div>
</body>
</html>