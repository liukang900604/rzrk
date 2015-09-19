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
    <link rel="stylesheet" href="/rzrk/css/background/product_daily_record_trend.css" />
    <script type="text/javascript" src="/rzrk/js/alljs.js"></script>
    <script type="text/javascript" src="/rzrk/js/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
    <script type="text/javascript">
    	window.productId = '${(pdr.productId)!}';
    </script>
    <script type="text/javascript" src="/rzrk/js/background/product_daily_record_trend.js"></script>
</head>
<body>
    <div class="easyui-tabs" data-options="fit:true, border:false" style="padding-top:3px;">
        <div title="走势图" style="padding:10px">
			<div class="chart-content"></div>
        </div>
        <div title="净值列表" style="padding:5px 10px 0;">
    		<table id="table"></table>
        </div>
    </div>
</body>
</html>