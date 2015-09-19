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
    <script type="text/javascript" src="/rzrk/js/background/admin_list_dep.js"></script>
</head>
<body>
    <div id="cc" class="easyui-layout" fit="true">
        <div data-options="region:'west',split:true" title="部门树" style="width:300px;">
                <div id="depTreeDiv" title="表单"  data-options="selected:true"  style="padding:10px;" class="t-tree">
                    <table  id="depTree"></table >
                </div>
        </div>
        <div data-options="region:'center',title:'人员列表'">
            <iframe id="cciframe" src="/admin/admin!list.action?deparmentId=${rootId}" frameborder=0 height="99%" width="100%" scrolling="auto"></iframe>
        </div>
    </div>
    
</body>
</html>