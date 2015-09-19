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
    <link rel="stylesheet" href="/rzrk/css/background/article_input.css" />
    <script type="text/javascript" src="/rzrk/js/alljs.js"></script>
    <script type="text/javascript" src="/rzrk/js/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="/rzrk/js/jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="${base}/template/common/editor/kindeditor.js"></script>
    <script type="text/javascript" src="/rzrk/js/background/ajaxfileupload.js"></script>
    <script type="text/javascript">
    	window.isAddAction = <#if isAddAction>true<#else>false</#if>;
    </script>
    <script type="text/javascript" src="/rzrk/js/background/article_input.js"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'center'">
		<div class="easyui-panel" title="文章信息" data-options="fit:true,border:false">
			<form id="atForm" method="post">
				<input type="hidden" name="id" value="${id}"/>
		        <table cellpadding="5" style='width:100%;'>
		        	<colgroup>
		        		<col style="width:80px;"/>
		        		<col/>
		        	</colgroup>
		            <tr>
		                <td>文章标题</td>
		                <td><input type="text" name="article.title" class="formText" value="${(article.title)!}" /></td>
		            </tr>
		            <tr>
		                <td>文章分类</td>
		                <td>
							<select name="article.articleCategory.id" id="articleCategory">
								<#list articleCategoryTreeList as articleCategoryTree>
									<option value="${articleCategoryTree.id}"<#if (articleCategoryTree == article.articleCategory)!> selected</#if>>
										<#if articleCategoryTree.grade != 0>
											<#list 1..articleCategoryTree.grade as i>
												&nbsp;&nbsp;
											</#list>
										</#if>
										${articleCategoryTree.name}
									</option>
								</#list>
							</select>
		                </td>
		            </tr>
		            <tr id ="types" style="display:none;">
		                <td>公告类型</td>
		                <td>
							<select name="article.type" id="typeId">
								<option value="0" <#if (0 == article.type)!> selected</#if>>OA公告</option>
								<option value="1" <#if (1 == article.type)!> selected</#if>>产品公告</option>
							</select>
						</td>
		            </tr>
		            <tr id ="products" style="display:none;">
		                <td>产品列表</td>
		                <td>
							<select name="article.productId" id="productId">
								<#list productNamesTreeList as product>
								<option value="${product.id}" <#if (product.id == article.productId)!> selected</#if>>${product.nameSShort}</option>
								</#list>
							</select>
						</td>
		            </tr>
		            <tr>
		                <td>作　者</td>
		                <td><input type="text" class="formText" name="article.author" value="${(article.author)!}" /></td>
		            </tr>
		            <tr>
		                <td>设　置</td>
		                <td>
							<label>
								<input type="checkbox" name="article.isPublication" value="${(article.isPublication)!true}" />发布
							</label>
							<label>
								<input type="checkbox" name="article.isRecommend" value="${(article.isRecommend)!false}" />推荐
							</label>
							<label>
								<input type="checkbox" name="article.isTop" value="${(article.isTop)!false}" />置顶
							</label>
						</td>
		            </tr>
		            <tr id="pic">
		                <td>图　片</td>
	
		                <td>
		                	<input type="file" name="imageFile" id="inptPic" class="fileUpload"/>
		                	<#if ("" != article.imagePath)!>
		                		<a href='${article.imagePath}' target='_blank' style='margin-left:10px;'>查看</a>
		                	</#if>
		                	<input type="hidden" name="imageUrl"/>
	                	</td>
		            </tr>
		            <tr id="file">
		                <td>文　件</td>
		                <td>
		                	<input type="file" name="imageFile" id="inptFile" class="fileUpload"/>
		                	<#if ("" != article.imagePath)!>
		                		<a href='${article.imagePath}' target='_blank' style='margin-left:10px;'>查看</a>
		                	</#if>
		                	<input type="hidden" name="imageUrl"/>
	                	</td>
		            </tr>
		            <tr id="report">
		                <td>内　容</td>
		                <td><textarea id="editor" name="article.content" class="editor">${(article.content)!}</textarea></td>
		            </tr>
		            <tr>
		                <td>页面关键词</td>
		                <td><input type="text" class="formText" name="article.metaKeywords" value="${(article.metaKeywords)!}" /></td>
		            </tr>
		            <tr>
		                <td>页面描述</td>
		                <td><textarea name="article.metaDescription" class="formTextarea" cols="100" rows="5">${(article.metaDescription)!}</textarea></td>
		            </tr>
		        </table>
		    </form>
		</div>
	</div>
	<div data-options="region:'south',border:false" style="height:40px;overflow:hidden;">
		<div style="padding:10px;">
		    <a href="javascript:void(0)" data-options="iconCls:'icon-save'" class="easyui-linkbutton" id="saveBtn">&nbsp;保&nbsp;存&nbsp;&nbsp;</a>
		    &nbsp;&nbsp;
		    <a href="javascript:void(0)" data-options="iconCls:'icon-cancel'" class="easyui-linkbutton" id="closeBtn">&nbsp;返&nbsp;回&nbsp;&nbsp;</a>
		</div>
	</div>
</body>
</html>