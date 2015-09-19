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
    <link rel="stylesheet" href="/rzrk/css/background/work_flow_input.css" />
    <script type="text/javascript" src="/rzrk/js/alljs.js"></script>
    <script type="text/javascript" src="/rzrk/js/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="/rzrk/js/jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="/rzrk/js/background/index.js"></script>
    <script TYPE="text/javascript" src="${base}/template/common/editor/kindeditor.js"></script>
    <script type="text/javascript" src="/rzrk/js/background/work_form_editor.js"></script>
    <script type="text/javascript" src="/rzrk/js/background/check_editDevelopmentwork.js"></script>
      <script type="text/javascript" src="/rzrk/js/background/ajaxfileupload.js"></script>
    <script type="text/javascript">
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
        window.checkSort = {};
    	window.loginAdmin = ""; 
    	window.belongProjectOpts = <#if belongProjectOpts??>${belongProjectOpts}<#else>[]</#if>;
    	
    </script>
</head>
<body class="input admin" style="overflow:auto;">
	<div class="view">
        <div class="m_c">
        <form id="atForm" method="post">
			<table cellpadding="0" cellspacing="0">
	            <tbody>
	            	<tr>
	                    <td class="td-left">工作名称：</td>
	                    <td class="td-right">
                            ${(work.workName)!}
                            <input type="hidden" name="work.workName" value="${(work.workName)!}"/>
	                    	<input type="hidden" id="workId" name="work.id" value="${(work.id)!}"/>
	                    	<input type="hidden" id="version"  value="${(work.version)!}" />
	                    	<input type="hidden" id="editWorkFlowId" value="${(work.workFlow.id)!}"/>
	                    	<input type= "hidden" id="isInternal" name="work.isInternal" value="${(work.isInternal)!}" />
	                    	<input type= "hidden" name="work.formContent" id="formContent"  value=""></input> 
	                    	<input type= "hidden"  name="work.workType" value="${(work.workType)!}" />
	                    	<input type= "hidden"  name="work.currentPointId" value="${(flowPoint.id)!}"  id="pointId" />
	                    	
	                    	
						</td>
	                </tr>
	            	<tr>
	                    <td class="td-left">流程类型：</td>
	                    <td class="td-right">
								${(work.flowTypeName)!}
						</td>
	                </tr>
	            	<tr>
	                    <td class="td-left bd-bottom">流程名称：</td>
	                    <td class="td-right bd-bottom">
								${(work.flowName)!}
						</td>
	                </tr>
                	<tr>
	                    <td class="bd-top  bd-bottom" colspan="2" style="padding-top:10px;">
                            <div class="form" >
                              <div id="editor">
                              	${work.formContent}
                              </div>
                             <!--<textarea id="editor" name="work.formContent" class="editor" value="" style="width:100%;height:300px;display:none"></textarea>-->
                            </div>
	                    </td>
	                </tr>
	                <#if work.workType == 2>
	                <tr>
	                    <td class="td-left"><a href='#' id="selectRequest"><em style="color:red;">*</em>选择bug列表</a></td>
	                    <td class="td-right">
	                       <input type="hidden" name="requestField" id="requestId" value=""></input>
			               <input type="text" name="requestName" id="requestName" value="" readonly="readonly"></input>
						</td>
	                </tr>
	                </#if>
	               <tr>
	                    <td class="td-right bd-top">
	                       <h3 style="white-space:nowrap;">附件列表：</h3>
	                    </td>
	                    <td>
	                    <input id="file" type="file"  multiple=""  name="file" data-url='file_upload!fileUpload.action'  />               
	                    </td>
	               </tr>
	            </tbody>
			</table>
		             <table style="width:initial">
		                
		               <#list fileList as file>
							  <tr><td height="25" width="100px">${file.fileName}</td>
							  <td width="40px">
							    <input type="hidden" name="fileName" value="${file.fileName}"/>
							    <input type="hidden" name="filePath" value="${file.filePath}"/>
								<a href="file_upload!downloadFile.action?downloadFilePath=${file.filePath}&downloadFileName=${file.fileName}" >下载</a>
							  </td>
							  <td width="40px"><a href="#" class="deleteFile" value="${file.filePath}">删除</a></td>
							  <td  width="40px">
								<a href="file_upload!showFile.action?downloadFilePath=${file.filePath}&downloadFileName=${file.fileName}" target ="_blank" >预览</a>
							 </td>
							  </tr>		
					</#list>
					</table>
	               <table id="down">
	                
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
		    <#if work.workType == 4 || work.workType == 5>
		          <fieldset  form="atForm" >
                            <legend style="text-align:center;">项目填写信息,<em style="color:red;">*</em>为必填信息</legend>
                            <p>
                            <table>
                             <tr>
                                <td class="td-left">加入项目：</td>
                                <td class="td-right">
                                   <input type="text" id="joinProject" name="joinProject" value="${belongProject!}" />
                                </td>
                            </tr>
                            </table>
                            <p>
                   </fieldset>
            </#if>
	    </form>
        </div>
         <#include "/WEB-INF/template/admin/work_flow_chart.ftl"> 
	</div>
	<div style="padding:10px 0">
	    <a href="javascript:void(0)" data-options="iconCls:'icon-save'" class="easyui-linkbutton" id="saveBtn">&nbsp;保&nbsp;存&nbsp;&nbsp;</a>
	    &nbsp;&nbsp;
	    <a href="javascript:void(0)" data-options="iconCls:'icon-cancel'" class="easyui-linkbutton" id="closeBtn">&nbsp;关&nbsp;闭&nbsp;&nbsp;</a>
	</div>
</body>
</html>