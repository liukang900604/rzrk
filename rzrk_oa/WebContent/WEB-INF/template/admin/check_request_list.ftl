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
    <script type="text/javascript" src="/rzrk/js/background/ajaxfileupload.js"></script>
    <script type="text/javascript" src="/rzrk/js/background/check_request_list.js"></script>
    <script type="text/javascript" src="/rzrk/js/selectPos/querytree.js"></script>
    
    
    <script type="text/javascript">
    window.isView = <#if isView?? && isView == 1>true<#else>false</#if>;
    window.isEdit = <#if isEdit??>true<#else>false</#if>;
        window.contractCategoryId = "${contractCategoryId}";
        window.contractCategoryName = "${contractCategoryName}";
        window.contractCategoryTitles = ${contractCategoryTitles};
        window.contractCategoryTotals = ${contractCategoryTotals};
        window.workType = "${workType}";
        window.requestIds =
        <#if requestId?? && requestId != "" > 
        "${requestId}";
        <#else>
         ""
        </#if>
      
//        window.contractCategoryRows = ${contractCategoryRows};
        
/*        
	
        <#if contractData??>
            window.contractData = ${contractData}
        <#else>
           window.contractData = {
                "id" : "40288ad14bee7994014beeab53c40001",
                "name" : "模板1",
                "title" : ['测试1','测试2'],
                "data" : [
                {
                    '测试1' : 'hehe1',
                    '测试2' : 'hehe2'
                },
                {
                    '测试1' : 'caca1',
                    '测试2' : 'caca2'
                }
                ]
            }
        </#if>
    	;
*/    
        <#if preQuery??>
            window.preQuery = ${preQuery};
        <#else>
            window.preQuery = {};
        </#if>
        window.queryOption = [
            <#list titleArr as item>
                <#if item_index!=0 >
                    ,
                </#if> 
                {
                label: '${item}',
                value: '${item}'
                }
            </#list>
        ];
    </script>
    <style>
       .qtreeRoot{
        margin-left:-80px;
       }
       .qnode{
        margin-left:40px;
       }
       .qrelation1{
        display:none;
       }
       .qfontand{
        color:#0F0
       }
       .qfontor{
        color:#F00
       }
    </style>
</head>
<body <#if isView?? && isView == 1><#else>class="easyui-layout"</#if>>
	<#if isView?? && isView == 1><#else><div data-options="region:'center'"></#if>
	<div class="searchBar">
      <label>
        <input type="checkbox" id="queryCondShow"/>查询条件
        </label>
		&nbsp;&nbsp;&nbsp;&nbsp;
		<table style="width:100%">
		  <tr>
              <td>
              <div class="queryDom">
              </div>
              </td>
		  </tr>
		</table>
        
        <div>
            <center>
                    <a id="queryBtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
                <#if preQuery??>
                <#else>
                    <a id="saveQueryHistory" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'">保存我的查询</a>
                </#if>
            </center>
        </div>
		
	</div>
	
    <table id="table"></table>
    <form id="downloadForm" action=""  method="get" style="display:none;">
    <input type="hidden" name="contractCategoryId" />
    <input type="hidden" name="preQuery" />
    </form>
   <#if isView?? && isView == 1>
   <#else>
	<div data-options="region:'south',border:false" style="height:40px;overflow:hidden;">
	    <div style="padding:10px;">
		    <a href="javascript:void(0)" data-options="iconCls:'icon-save'" class="easyui-linkbutton" id="saveBtn">&nbsp;保&nbsp;存&nbsp;&nbsp;</a>
		    &nbsp;&nbsp;
		    <a href="javascript:void(0)" data-options="iconCls:'icon-cancel'" class="easyui-linkbutton" id="closeBtn">&nbsp;返&nbsp;回&nbsp;&nbsp;</a>
		</div>
	</div>
   </#if>
   
   
 
</body>
</html>