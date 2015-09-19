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
    <link rel="stylesheet" href="/rzrk/css/background/work_flow_pointinput.css" />
    <script type="text/javascript" src="/rzrk/js/alljs.js"></script>
    <script type="text/javascript" src="/rzrk/js/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="/rzrk/js/jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="${base}/template/common/editor/kindeditor.js"></script>
     <script type="text/javascript" src="/rzrk/js/background/work_flow_pointinput.js"></script>
</head>
<body>
	<div class="easyui-panel" title="请输入节点信息" style="padding: 5px 0;width:100%;">
		<form id="atForm" method="post">
			<input type="hidden" name="workFlowPoint.id" value="${(workFlowPoint.id)!}"/>
			<input type="hidden" name="workFlowPoint.workFlow.id" value="${(workId)!}"/>
	        <table cellpadding="5" style='width:100%;'>
	        	<colgroup>
	        		<col style="width:80px;"/>
	        		<col />
	        	</colgroup>
	            <tr>
	                <th style="text-align:right;">节点序号</th>
	                <td style="text-align:left;">
	                	<input type="text" id="pointSort" name="workFlowPoint.pointSort" value="${(workFlowPoint.pointSort)!}" placeholder="输入数字，如：1"/>
                	</td>
	            </tr>
	            <tr>
	                <th style="text-align:right;">节点名称</th>
	                <td style="text-align:left;">
	                	<input type="text" name="workFlowPoint.pointName" value="${(workFlowPoint.pointName)!}" />
                	</td>
	            </tr>
	            <tr>
	                <th style="text-align:right;">节点位置</th>
	                <td style="text-align:left;">
	                	<select name="workFlowPoint.pointLocation" id="pointLocation" class="nodePos">
	                		<option value="0"  <#if isEdit && 0 == workFlowPoint.pointLocation> selected</#if>>开始</option>
	                		<option value="1"  <#if isEdit && 1 == workFlowPoint.pointLocation> selected</#if>>中间段</option>
	                		<option value="2"  <#if isEdit && 2 == workFlowPoint.pointLocation> selected</#if>>结束</option>
	                	</select>
                	</td>
	            </tr>
	            <tr>
	                <th style="text-align:right;">下一节点</th>
	                <td style="text-align:left;">
	                	<input type="text"id="nextPonit" name="workFlowPoint.nextPonit" value="${(workFlowPoint.nextPonit)!}" placeholder="如：3,5 多节点用逗号分隔"/>
                	</td>
	            </tr>
	            <tr class="extension">
	                <th style="text-align:right;">审批人选择</th>
	                <td style="text-align:left;">
	                	<select name="workFlowPoint.checkSelect">
	                		<option value="1"  <#if isEdit && 1 == workFlowPoint.checkSelect > selected</#if>>审批时自由指定</option>
	                		<option value="2"  <#if isEdit && 2 == workFlowPoint.checkSelect > selected</#if>>审批时从指定列表中选择</option>
	                		<option value="3"  <#if isEdit && 3 == workFlowPoint.checkSelect> selected</#if>>直属主管</option>
	                	</select>
                	</td>
	            </tr>
	            <tr class="extension">
	                <th style="text-align:right;">可编辑附件</th>
	                <td style="text-align:left;">
	                	<select name="workFlowPoint.isFile">
	                		<option value="1" <#if isEdit && 1 == workFlowPoint.isFile> selected</#if>>是</option>
	                		<option value="2" <#if isEdit && 2 == workFlowPoint.isFile> selected</#if>>否</option>
	                	</select>
                	</td>
	            </tr>
	            <tr class="extension">
	                <th style="text-align:right;">审批人范围</th>
	                <td style="text-align:left;">
	                	<ul class=""userList>
	                	<#list adminList as admin>
	                	 <li>${admin.name}<span>X</span><input type="hidden" name="adminList.id" value="${admin.id}"/></li>
	                	</#list>
	                		<li class="addColleague">
	                			<a href="javascript:void();">添加同事</a>
	                			<input class="userBox" style="width:100px;display:none;" />
                			</li>
	                	</ul>
                	</td>
	            </tr>
	        </table>
	    </form>
	</div>
	<div style="padding:10px 0">
	    <a href="javascript:void(0)" data-options="iconCls:'icon-save'" class="easyui-linkbutton" id="saveBtn">&nbsp;保&nbsp;存&nbsp;&nbsp;</a>
	    &nbsp;&nbsp;
	    <a href="javascript:void(0)" data-options="iconCls:'icon-cancel'" class="easyui-linkbutton" id="closeBtn">&nbsp;关&nbsp;闭&nbsp;&nbsp;</a>
	</div>
</body>
</html>