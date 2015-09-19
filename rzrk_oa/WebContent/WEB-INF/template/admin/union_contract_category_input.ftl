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
     <style>
     .controlhide{
        display:none;
     }
     
     </style>  
    <script type="text/javascript">
        window.isAddAction = <#if isAddAction>true<#else>false</#if>;
        window.categoryTree=${categoryTree};
        window.departmentJobList = 
        <#if departmentJobList??>
        {
            "root" :${departmentJobList}
        }
        <#else>
            {}
        </#if>
        ;
        window.depTree4Easyui = 
        <#if depTree4Easyui??>
        ${depTree4Easyui}
        <#else>
            []
        </#if>
        ;
        window.admin4Easyui = 
        <#if admin4Easyui??>
        ${admin4Easyui}
        <#else>
            []
        </#if>
        ;
        
        <#if (definitionJson)?? >
            window.unionContractCategoryData = {"total":0,"rows":${(definitionJson)!}};
        <#else>
            window.unionContractCategoryData = {"total":0,"rows":[]};
        </#if>
        window.controlFieldWidget = null; //可变的人员下拉框
    </script>
    <script type="text/javascript" src="/rzrk/js/background/ajaxfileupload.js"></script>
    <script type="text/javascript" src="/rzrk/js/background/union_contract_category_input.js"></script>
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
                    <input type="text" name="unionContractCategory.name" class="formText" value="${(unionContractCategory.name)!}" />
                    <input type="hidden" name="unionContractCategory.definition" class="formText" value="" />
                    </td>
                </tr>
                    <tr>
                        <td>一级分类</td>
                        <td>
                            <select name="unionContractCategory.rootContractCategory.id">
                            <#if rootContractCategoryList??>
                                <#list rootContractCategoryList as temp>
                                    <option value="${temp.id}"<#if isEditAction && temp.id == unionContractCategory.rootContractCategory.id> selected</#if>>
                                    ${temp.name}
                                    </option>
                                </#list>
                                <#else>
                           </#if>
                            </select>
                        </td>
                    </tr>
                    <tr>
                     <td>查询控制
                     </td>
                     <td>
                         <label>
                            <input type="checkbox" name="unionContractCategory.controlType" value="bydep" <#if isEditAction && ((unionContractCategory.controlType)!)?index_of("bydep")!=-1 >checked="checked"</#if> > 按部门限制
                         </label>
                         <label>
                            <input type="checkbox" name="unionContractCategory.controlType" value="byop" <#if ((unionContractCategory.controlType)!)?index_of("byop")!=-1 >checked="checked"</#if> > 按人员限制 
                         </label>
                     </td>
                    </tr>
                    <tbody class="opContractContent" >
                    <tr>
                     <td>限制字段
                     </td>
                     <td>
                        <select name="unionContractCategory.controlField" id="controlFieldWidget" oldvalue="${(unionContractCategory.controlField)!}" >
                            <option value="__create_admin">创建人</option>
                        </select>
                     </td>
                    </tr>
                    </tbody>
                    <tbody class="depContractContent" >
                    <tr>
                           <td>部门</td>
                            <td>
                            
                                <#assign deparmentSet = (unionContractCategory.deparmentSet)! />
    
                                <input type="text" id="deparmentList" name="deparmentListTexts" readonly="true" value="<#if !isAddAction><#list deparmentSet as temp>${temp.name},&nbsp;&nbsp;</#list></#if>" />
                                
                              <#if !isAddAction>
                                    <#list deparmentSet as temp>
                                            <input type="hidden" name="deparmentList.id" value="${temp.id}" />
                                    </#list>
                                </#if>
                                
                            </td>
                    </tr>
                    <tr>
                     <td></td>
                     <td>
                     <label>
                     <input type="checkbox"  value="1" name="unionContractCategory.isView"  <#if isAddAction || (unionContractCategory.isView)! == 1 >checked="checked"</#if>  />是否允许部门人员查看
                     </label>
                     </td>
                    </tr>
                    <tr>
                     <td></td>
                     <td>
                     <label>
                     <input type="checkbox"  value="1" name="unionContractCategory.isSubDepView"  <#if isAddAction || (unionContractCategory.isSubDepView)! == 1 >checked="checked"</#if>  />是否允许子部门查看
                     </label>
                     </td>
                    </tr>
                    <tr>
                     <td></td>
                     <td>
                     <label>
                     <input type="checkbox"  value="1" name="unionContractCategory.isSuperiorView"  <#if isAddAction || (unionContractCategory.isSuperiorView)! == 1 >checked="checked"</#if>  />是否允许上级部门查看
                     </label>
                     </td>
                    </tr>
                    </tbody>
                <tr>
                    <td>主表</td>
                    <td>
                    <input type="text" readonly="readonly" id="primaryTable" name="unionContractCategory.primaryTable" class="formText" value="${(unionContractCategory.primaryTable)!}" style="border:1px;border-bottom-style:none;border-top-style:none;border-left-style:none;border-right-style:none;"/>
                    </td>
                </tr>
                <tr>
                    <td>主键</td>
                    <td>
                    <input type="text" readonly="readonly" id="primaryField" name="unionContractCategory.primaryField" class="formText" value="${(unionContractCategory.primaryField)!}" style="border:1px;border-bottom-style:none;border-top-style:none;border-left-style:none;border-right-style:none;"/>
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
    <div id="dlg" class="easyui-dialog" title="表字段选择"  style="width:400px;height:200px;padding:10px;"
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
                    <td>二级分类</td>
                    <td>
                        <input class="categoryTree easyui-combotree" />
                        <input type="hidden" class="categoryRoot" />
                        <input type="hidden" class="categoryChild" />
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
                    valueField:'name',
                    textField:'name',
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
    <!-- 字段修改器 -->
    <div id="dlgEdit" class="easyui-dialog" title="字段编辑" style="width:400px;height:200px;padding:10px;"
            data-options="
                iconCls: 'icon-save',
                buttons: '#dlgexp-buttons'
            ">
            <table>
                <tr>
                    <td>统计</td>
                    <td>
                    <label>
                        <input type="checkbox"  class="total"/>
                    </label>
                </tr>
                <tr>
                    <td>限制可见人</td>
                    <td>
                        <input type="text" name="adminIds" class="formText adminIds adminIds" value="" maxlength="160"/>
                    </td>
                 </tr>
                <tr>
                    <td>限制可见部门</td>
                    <td>
                        <input type="text" name="departmentIds" class="formText departmentIds" value="" maxlength="160"/>
                    </td>
                 </tr>
                <tr>
                    <td>上级部门可见</td>
                    <td>
                        <input type="checkbox" name="superiorView" class="formText superiorView" value="1" />
                    </td>
                 </tr>
                <tbody class="dlgFieldContent">
                </tbody>
                <tbody class="dlgExpContent">
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
                </tbody>
            </table>
        <div id="dlgexp-buttons">
            <a href="javascript:void(0)" id="dlgEditSave" class="easyui-linkbutton" >确定</a>
            <a href="javascript:void(0)" id="dlgEditClose" class="easyui-linkbutton" >关闭</a>
        </div>
    </div>
    <!-- 部门选择 HTML -->
    <div class="content" id="contentDepartment" style="display:none;">
        <div class="list">
            <div class="l">
                <div class="select_list" style="overflow:auto;border:1px solid #778;">
                    <div class="domTree">
                    </div>                  
                </div>
            </div>
            <div class="l mv_btn">
                <input type="button" class="btnRight" value="右移" /><br/><br/>
                <input type="button" class="btnLeft" value="左移" />
            </div>
            <div class="l">
                <select class="rselect select_list" multiple="multiple">
                </select>                   
            </div>  
        </div>
    </div>

</body>
</html>