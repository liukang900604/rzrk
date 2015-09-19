<#assign sec=JspTaglibs["/WEB-INF/tld/security.tld"] />
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta content="IE=edge,chrome=1" http-equiv="X-UA-Compatible">
    <meta http-equiv="Cache-control" content="no-store">
    <title>记录 </title>
    <link rel="stylesheet" href="/rzrk/js/jquery-easyui-1.4.1/themes/icon.css" />
    <link rel="stylesheet" href="/rzrk/js/jquery-easyui-1.4.1/themes/metro/easyui.css" id="themes"/>
    <link rel="stylesheet" href="/rzrk/css/background/common.css" />
    <link rel="stylesheet" href="/rzrk/css/background/file_upload.css" />
    <link rel="stylesheet" href="/rzrk/css/background/contract_list.css" />
    <script type="text/javascript" src="/rzrk/js/alljs.js"></script>
    <script type="text/javascript" src="/rzrk/js/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="/rzrk/js/jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="/rzrk/js/background/index.js"></script>
    
    
    <script type="text/javascript">
    </script>
</head>
<body class="input admin easyui-layout">
	<div data-options="region:'center'">
	    <div class="easyui-panel" title="信息" data-options="fit:true,border:false">
	            <table cellpadding="5" style='width:100%;'>
	                <colgroup>
	                    <col style="width:100px;"/>
	                    <col/>
	                </colgroup>
	                <tr>
	                    <th>时间</th><th>操作人</th><th>字段</th><th>值变化</th>
	                </tr>
	                <#list contractLogList as contractLog>
	                <tr>
	                    <td>
	                        ${contractLog.createDate?string("yyyy-MM-dd HH:mm:ss")}
	                    </td>
	                    <td>
	                        ${contractLog.operator.name}
	                    </td>
	                    <td>
	                          <#list contractLog.getContractLogItemList() as logitem>
	                            <div>
	                            [${logitem.fieldName}]
	                            </div>
	                          </#list>
	                    </td>
	                    <td>
	                          <#list contractLog.getContractLogItemList() as logitem>
	                            <div>
	                                <#if logitem.oldValue==null || logitem.oldValue=="">
	                                [空]
	                                <#else>
	                                ${logitem.oldValue}
	                                </#if>
	                                 --> 
	                                <#if logitem.newValue==null || logitem.newValue=="">
	                                [空]
	                                <#else>
	                                ${logitem.newValue}
	                                 </#if>
	                                </div>
	                          </#list>
	                    </td>
	                </tr>
	                </#list>
	            </table>
	    </div>
	</div>
	<div data-options="region:'south',border:false" style="height:45px;overflow:hidden;">
		<div style="padding:10px;">
	        <a href="javascript:window.top.closeCurrentWindow();" data-options="iconCls:'icon-cancel'" class="easyui-linkbutton" id="closeBtn">&nbsp;关&nbsp;闭&nbsp;&nbsp;</a>
	    </div>
	</div>
</body>
</html>