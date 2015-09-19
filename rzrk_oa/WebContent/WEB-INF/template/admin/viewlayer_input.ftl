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
    <link rel="stylesheet" href="/rzrk/css/background/file_upload.css" />
    <link rel="stylesheet" href="/rzrk/css/background/contract_category_input.css" />
    <link rel="stylesheet" href="/rzrk/css/background/admin_input.css" />
    <link rel="stylesheet" href="/rzrk/css/background/admin_input_jstree.css" />
    <link rel="stylesheet" href="/rzrk/css/selectPosition.css" />
    <script type="text/javascript" src="/rzrk/js/alljs.js"></script>
    <script type="text/javascript" src="/rzrk/js/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="/rzrk/js/jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="/rzrk/js/jstree/jstree.min.js"></script>	
     <script type="text/javascript" src="/rzrk/js/background/index.js"></script>
     <script type="text/javascript" src="/rzrk/js/selectPos/contract-department.js"></script>  
    <script type="text/javascript">
    	window.isAddAction = <#if isAddAction>true<#else>false</#if>;
        window.categoryTree=${categoryTree};
        
        <#if (viewlayer.definition)?? >
            window.viewlayerData = {"total":0,"rows":${(viewlayer.definition)!}};
        <#else>
            window.viewlayerData = {"total":0,"rows":[]};
        </#if>
    </script>
    <script type="text/javascript" src="/rzrk/js/background/ajaxfileupload.js"></script>
    <script type="text/javascript" src="/rzrk/js/background/viewlayer_input.js"></script>
      <link rel="stylesheet" href="/rzrk/css/selectPosition.css" />
    <script type="text/javascript" src="/rzrk/js/selectPos/layer.min.js"></script>
</head>
<body style="overflow:auto;">
	<div class="easyui-panel" title="新表" style="padding: 5px 0;width:100%;">
		<form id="atForm" method="post">
			<input type="hidden" name="id" value="${id}"/>
	        <table cellpadding="5" style='width:100%;'>
	        	<colgroup>
	        		<col style="width:80px;"/>
	        		<col/>
	        	</colgroup>
                <tr>
                    <td colspan="2" style="text-align: left;"><span class="warnInfo">
                    <#if isAddAction>
                     首个添加的表为主表
                    <#else>
                                                                       主表主键不能修改
                    </#if>
                    </span></td>
                </tr>
                <tr>
                    <td>表单名称</td>
                    <td>
                    <input type="text" name="viewlayer.name" class="formText" value="${(viewlayer.name)!}" />
                    <input type="hidden" name="viewlayer.definition" class="formText" value="" />
                    </td>
                </tr>
                <tr>
                    <td>主表</td>
                    <td>
                    <input type="text" readonly="readonly" id="primaryTable" name="viewlayer.primaryTable" class="formText" value="${(viewlayer.primaryTable)!}" style="border:1px;border-bottom-style:none;border-top-style:none;border-left-style:none;border-right-style:none;"/>
                    </td>
                </tr>
                <tr>
                    <td>主键</td>
                    <td>
                    <input type="text" readonly="readonly" id="primaryField" name="viewlayer.primaryField" class="formText" value="${(viewlayer.primaryField)!}" style="border:1px;border-bottom-style:none;border-top-style:none;border-left-style:none;border-right-style:none;"/>
                    </td>
                </tr>
                <tr>
                    <td>新表字段</td>
                    <td>
                        <table id="table"></table>
                    </td>
                </tr>
	        </table>
	    </form>
	</div>
	<div style="padding:10px 0">
	    <a href="javascript:void(0)" data-options="iconCls:'icon-save'" class="easyui-linkbutton" id="saveBtn">&nbsp;保&nbsp;存&nbsp;&nbsp;</a>
	    &nbsp;&nbsp;
	    <a href="javascript:void(0)" data-options="iconCls:'icon-cancel'" class="easyui-linkbutton" id="closeBtn">&nbsp;返&nbsp;回&nbsp;&nbsp;</a>
	</div>
    <!-- 表字段选择器 -->
    <div id="dlg" class="easyui-dialog" title="表字段选择" closed="true" style="width:400px;height:200px;padding:10px;"
            data-options="
                iconCls: 'icon-save',
                buttons: '#dlg-buttons'
            ">
            <table>
                <tr class="primaryTr" style="display:none">
                    <td colspan="2" style="text-align: left;"><span class="warnInfo">
                    主表将自动添加主键
                    </span></td>
                </tr>
                <tr>
                    <td>一级分类</td>
                    <td>
                        <select class="categoryRoot">
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>二级分类</td>
                    <td>
                        <select class="categoryChild">
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>字段选择</td>
                    <td>
                    <!--
                        <select class="categoryFiled">
                        </select>
                     -->
                     <input class="easyui-combobox categoryFiled" 
            name="language"
            data-options="
                    valueField:'id',
                    textField:'text',
                    multiple:true,
                    panelHeight:'auto'
            " />
                    </td>
                </tr>
            </table>
        <div id="dlg-buttons">
            <a href="javascript:void(0)" id="dlgSave" class="easyui-linkbutton" >确定</a>
            <a href="javascript:void(0)" id="dlgClose" class="easyui-linkbutton" >关闭</a>
        </div>
    </div>
    <!-- 表达式选择器 -->
    <div id="dlgExp" class="easyui-dialog" title="表达式" closed="true" style="width:400px;height:200px;padding:10px;"
            data-options="
                iconCls: 'icon-save',
                buttons: '#dlgexp-buttons'
            ">
            <table>
                <tr>
                    <td>列名</td>
                    <td>
                    <input type="hidden" class="index" >
                        <input type="text" class="formText valid showField" />
                    </td>
                </tr>
                <tr>
                    <td>格式</td>
                    <td>
                        $(表字段_表名) 运算符 $(表字段_表名)
                    </td>
                </tr>
                <tr>
                    <td>表达式</td>
                    <td>
                        <textarea class="formText valid expression" ></textarea>
                    </td>
                </tr>
            </table>
        <div id="dlgexp-buttons">
            <a href="javascript:void(0)" id="dlgExpSave" class="easyui-linkbutton" >确定</a>
            <a href="javascript:void(0)" id="dlgExpClose" class="easyui-linkbutton" >关闭</a>
        </div>
    </div>

</body>
</html>