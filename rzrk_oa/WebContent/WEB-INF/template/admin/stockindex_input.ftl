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
    <script type="text/javascript" src="/rzrk/js/alljs.js"></script>
    <script type="text/javascript" src="/rzrk/js/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="/rzrk/js/jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="/rzrk/js/background/index.js"></script>
    <script type="text/javascript">
    	window.isAddAction = <#if isAddAction>true<#else>false</#if>;
    </script>
    <script type="text/javascript" src="/rzrk/js/background/stockindex_input.js"></script>
</head>
<body class="input admin easyui-layout">
	<div data-options="region:'center'">
		<div class="easyui-panel" title="股指信息" data-options="fit:true,border:false" style="padding:5px;">
			<form id="stkForm" method="post">
	            <input type="hidden" name="sindex.stockId" value="${stock.id}" />
				<input type="hidden" name="id" value="${id}"/>
		        <table cellpadding="5" style='width:100%;'>
		        	<colgroup>
		        		<col style="width:80px;"/>
		        		<col/>
		        	</colgroup>
		            <tr>
		                <td>股票名称</td>
		                <td>
		                	${stock.code} - <span style="color: red;font-weight: bolder;">${stock.name}</span>
		                </td>
		            </tr>
		            <tr>
		                <td>结算日</td>
		                <td>
			                <#if isAddAction>
			                	<input id="settlementDate" name="sindex.date" type="text" class="easyui-datebox" data-options="formatter: dateFormatter, parser: dateParser, editable:false" style="width:154px;">
			                <#else>
			                	${sindex.date}
			                </#if>
		                </td>
		            </tr>
		            <tr>
		                <td>开　盘</td>
		                <td><input name="sindex.start" maxlength="10" value="${(sindex.start)!}" /></td>
		            </tr>
		            <tr>
		                <td>收　盘</td>
		                <td><input name="sindex.end" maxlength="10" value="${(sindex.end)!}" /></td>
		            </tr>
		            <tr>
		                <td>最　高</td>
		                <td><input name="sindex.max" maxlength="10" value="${(sindex.max)!}" /></td>
		            </tr>
		            <tr>
		                <td>最　低</td>
		                <td><input name="sindex.min" maxlength="10" value="${(sindex.min)!}" /></td>
		            </tr>
		        </table>
		    </form>
		</div>
	</div>
	<div data-options="region:'south',border:false" style="height:45px;overflow:hidden;">
		<div style="padding:10px;">
		    <a href="javascript:void(0)" data-options="iconCls:'icon-save'" class="easyui-linkbutton" id="saveBtn">&nbsp;保&nbsp;存&nbsp;&nbsp;</a>
		    &nbsp;&nbsp;
		    <a href="javascript:void(0)" data-options="iconCls:'icon-cancel'" class="easyui-linkbutton" id="closeBtn">&nbsp;返&nbsp;回&nbsp;&nbsp;</a>
		</div>
	</div>
</body>
</html>