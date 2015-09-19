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
    <script type="text/javascript" src="/rzrk/js/background/viewlayer_show_list.js"></script>
    <script type="text/javascript" src="/rzrk/js/selectPos/querytree.js"></script>
    
    
    <script type="text/javascript">
        window.id = '${id}';
        window.definition = ${definition};
        <#if preQuery??>
            window.preQuery = ${preQuery};
        <#else>
            window.preQuery = {};
        </#if>
        window.queryOption = [
            <#list queryOption as item>
                <#if item_index!=0 >
                    ,
                </#if> 
                {
                label: '${item.showField}',
                value: '${item.showField}'
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
<body>
	<div class="searchBar">
		<a id="downloadContract" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'">导出记录</a>
    <#if preQuery??>
    <#else>
        <label>
        <input type="checkbox" id="queryCondShow"/>查询条件
        </label>
     </#if>
		&nbsp;&nbsp;&nbsp;&nbsp;
		<table style="width:100%">
		  <tr>
              <td>
              <div class="queryDom">
              </div>
              <!--
                <div class="qtreeRoot">
                    <div class="qtree">
                        <div>
                            <select id="searchBy" class="qselect" name="searchBy" style="width:80px;" >
                            <option>nima</option>
                            </select>
                            <input id="keyword" class="easyui-searchbox" data-options="prompt:'请输入搜索关键字',searcher:doSearch" style="width:300px"></input>
                            <span>[删除]</span><span>[增加]</span><span>[增加组]</span>
                        </div>
                    </div>
                    <div class="qtree">或者</div>
                    <div class="qtree">
                        <div class="qtree">
                            <div>
                                <select id="searchBy" class="qselect" name="searchBy" style="width:80px;" data-options="editable:false">
                                </select>
                                <input id="keyword" class="easyui-searchbox" data-options="prompt:'请输入搜索关键字',searcher:doSearch" style="width:300px"></input>
                                <span>[删除]</span><span>[增加]</span><span>[增加组]</span>
                            </div>
                        </div>
                        <div class="qtree">并且</div>
                        <div class="qtree">
                            <div>
                                <select id="searchBy" class="qselect" name="searchBy" style="width:80px;" data-options="editable:false">
                                </select>
                                <input id="keyword" class="easyui-searchbox" data-options="prompt:'请输入搜索关键字',searcher:doSearch" style="width:300px"></input>
                                <span>[删除]</span><span>[增加]</span><span>[增加组]</span>
                            </div>
                        </div>
                    </div>
                </div>
                -->
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
	
	<div id="userSelect" title="导入记录" class="easyui-window" data-options="closed:true,collapsible:false,minimizable:false,modal:true,width:600,height:300">
		<table>
			<tr>
                <td class="td-left bd-bottom">查看人：</td>
                <td class="td-right bd-bottom">
                	<ul class="userList" id="adminListId">
                		<li class="addColleague">
                			<a href="javascript:">查看人</a>
                			<input class="userBox" style="width:100px;display:none;" />
            			</li>
                	</ul>
				</td>
            </tr>
		</table>
	    <div style="padding:10px 0">
		    <a href="javascript:void(0)" data-options="iconCls:'icon-save'" class="easyui-linkbutton" id="saveBtn">&nbsp;保&nbsp;存&nbsp;&nbsp;</a>
		    &nbsp;&nbsp;
		    <a href="javascript:void(0)" data-options="iconCls:'icon-cancel'" class="easyui-linkbutton" id="closeBtn">&nbsp;关&nbsp;闭&nbsp;&nbsp;</a>
		</div>
	</div>
	
    <table id="table"></table>
    <form id="downloadForm" action=""  method="get" style="display:none;">
    <input type="hidden" name="id" />
    <input type="hidden" name="preQuery" />
    </form>
    
   
</body>
</html>