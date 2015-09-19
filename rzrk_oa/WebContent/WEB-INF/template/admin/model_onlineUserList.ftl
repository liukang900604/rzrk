<#assign sec=JspTaglibs["/WEB-INF/tld/security.tld"] />
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta content="IE=edge,chrome=1" http-equiv="X-UA-Compatible">
    <meta http-equiv="Cache-control" content="no-store">
    <title>文章管理 </title>
    <link rel="stylesheet" href="/rzrk/js/jquery-easyui-1.4.1/themes/icon.css" />
    <link rel="stylesheet" href="/rzrk/js/jquery-easyui-1.4.1/themes/metro/easyui.css" id="themes"/>
    <link rel="stylesheet" href="/rzrk/css/background/common.css" />
    <link rel="stylesheet" href="/rzrk/css/background/article_list.css" />
    <script type="text/javascript" src="/rzrk/js/alljs.js"></script>
    <script type="text/javascript" src="/rzrk/js/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
    <script type="text/javascript">
    	window.statusData = <#if statuArray??>${statuArray}<#else>{}</#if>;
    </script>
    <script type="text/javascript" src="/rzrk/js/background/model_onlineUser_list.js"></script>
    <script type="text/javascript" src="/rzrk/js/selectPos/querytree.js"></script>
     <script type="text/javascript">
    	
        window.queryOption = [
             {
                label: '用户',
                value: 'user'
                }
                    ,
                {
                label: '代理IP',
                value: 'proxyip'
                }
                    ,
                {
                label: '客户端IP',
                value: 'clientip'
                }
                    ,
                {
                label: '客户端端口号',
                value: 'clientport'
                }
                    ,
                {
                label: '登陆时间',
                value: 'datetime'
                }
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
<body onselectstart="return false">
	<div class="searchBar">
		&nbsp;&nbsp;&nbsp;&nbsp;
	</div>
	<label>
		<input type="checkbox" id="queryCondShow"/>查询条件
	</label>
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
			</center>
		</div>
    <table id="table"></table>
</body>
</html>