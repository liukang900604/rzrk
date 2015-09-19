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
    <link rel="stylesheet" href="/rzrk/css/background/work_flow_viewmywork.css" />
     <link rel="stylesheet" href="/rzrk/css/background/work_flow_input.css" />
    <script type="text/javascript" src="/rzrk/js/alljs.js"></script>
    <script type="text/javascript" src="/rzrk/js/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="/rzrk/js/jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="/rzrk/js/background/index.js"></script>
    <script type="text/javascript">
    	window.productId = '${(pdr.productId)!}';
    	window.isAddAction = <#if isAddAction>true<#else>false</#if>;
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
    	window.checkSort = <#if (work.checkSort)??>${(work.checkSort)!}<#else>{}</#if>;
    	window.loginAdmin = <#if (work.admin)??>"${(work.admin.name)!}"<#else>""</#if>; 
    </script>
    <script type="text/javascript" src="/rzrk/js/background/work_flow_viewmywork.js"></script>
    <script type="text/javascript" src="/rzrk/js/background/work_form_readonly.js"></script>
</head>
<body class="input admin" style="overflow:auto;">
	<div class="view">
		<h2>标题：${work.workName}</h2>
		<div class="top_r">
            <span>发起人：</span>
            <span style="margin-right: 10px;">${(work.admin.name)!}</span>
            <span>发起时间：</span>
            <span>${(work.createDate?string("yyyy-MM-dd HH:mm:ss"))!}</span>
             <span>结束时间：</span>
              <#if work.status == 2 || work.status == 1>
              <span>  ${(work.endDate?string("yyyy-MM-dd HH:mm:ss"))!}  </span>
              <#else>
              <span>-</span>
              </#if> 
          
         
        </div>
        <div class="m_c">
			<table cellpadding="0" cellspacing="0">
	            <tbody>
	            	<tr>
	            		<input type="hidden" id="editWorkFlowId"  value="${(work.flowId)!}" />
	            		<input type="hidden" id="version"  value="${(work.version)!}" />
	                    <td class="td-left">所属流程：</td>
	                    <td class="td-right">${(work.flowName)!}</td>
	                    <td class="td-left">流程类型：</td>
	                    <td class="td-right">${(work.flowTypeName)!}</td>
	                </tr>
	                <tr>
	                    <td class="td-left bd-bottom">当前节点：</td>
	                    <#if (work.currentPointId)?? && work.currentPointId != "">
	                    <td class="td-right bd-bottom">${(work.currentPointName)!}</td>
	                    <#else>
	                    <#if work.status == 2 || work.status == 1>
	                      <td class="td-right bd-bottom">-</td>
	                    <#else>
	                      <td class="td-right bd-bottom"></td>
	                    </#if>
	                    </#if>
	                    <td class="td-left bd-bottom">节点审批人：</td>
	                    <#if work.status == 3>
	                     <td class="td-right bd-bottom">${(work.currentAdminName)!}</td>
	                    <#elseif work.status = 2 || work.status == 1>
	                      <td class="td-right bd-bottom">-</td>
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
	                    		${(work.formContent)!}
	                    	</div>
	                    </td>
	                </tr>
	                <tr>
	                    <td class="td-right bd-top bd-bottom" colspan="4">
	                        <h3>审批记录</h3>
	                    </td>
	                </tr>
	                 <#list recordList as record>
	                 <tr>
	                    <td class="td-right bd-top" colspan="4">
	                        <h3>${record.approvalPerson}&nbsp;&nbsp;${(record.createDate?string("yyyy-MM-dd HH:mm:ss"))!}&nbsp;&nbsp;<#if record.status != "重新提交">审批节点: ${record.pointName}</#if>&nbsp;&nbsp;${record.advice}</h3>
	                    </td>
	                    
	                </tr>		
						</#list>
	                <tr>
	                    <td class="td-right bd-top" colspan="4">
	                        <h3 style="float: left; margin-right: 15px;">附件列表</h3>
	                    </td>
	                </tr>
	                 <table id="down">
	                 <#list fileList as file>
						   <tr> <td height='25' width='100px'>${file.fileName}</td>
						  <td width='40px'>
							<a href="file_upload!downloadFile.action?downloadFilePath=${file.filePath}&downloadFileName=${file.fileName}" >下载</a>
						 </td>
						 <td  width='40px'>
							<a href="file_upload!showFile.action?downloadFilePath=${file.filePath}&downloadFileName=${file.fileName}" target ="_blank" >预览</a>
						 </td>
						  </tr>	
					</#list>
				         </table>
				         
	            </tbody>
			</table>
			<#if work.workType == 1>
			  <fieldset  form="atForm" >
		    				<legend style="text-align:center;">项目填写信息,<em style="color:red;">*</em>为必填信息</legend>
		    				<p>
		    				 <tr>
			                    <td class="td-left"><em style="color:red;">*</em>项目名称</td>
			                    <td class="td-right">
			                    <input type="text" name="workFlowVo.name" id="projectName" value="" readonly="readonly" ></input>
								</td>
			                </tr>
		    				 <tr>
			                    <td class="td-left"><em style="color:red;">*</em>负责人  </td>
			                    <td class="td-right">
			                    <select name="workFlowVo.responsiborId" id="responsiborId" >
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
			                    <input type="text" name="workFlowVo.personNumber" id="personNumber" value="" readonly="readonly"></input>
								</td>
			                </tr>
			                 <tr>
			                    <td class="td-left"><em style="color:red;">*</em>预计时间(天) </td>
			                    <td class="td-right">
			                    <input type="text" name="workFlowVo.predictDay" id="predictDay" value="" readonly="readonly" ></input>
								</td>
			                </tr>
			                <tr>
			                    <td class="td-left">项目描述 </td>
			                    <td class="td-right">
			                    <input type="text" name="workFlowVo.projectDesc" id="projectDesc" value="" readonly="readonly"></input>
								</td>
			                </tr>
			                </p>
			                
		    	  </fieldset>
		    </#if>
        </div>
 	    <#include "/WEB-INF/template/admin/work_flow_chart.ftl">  
	</div>
</body>
</html>