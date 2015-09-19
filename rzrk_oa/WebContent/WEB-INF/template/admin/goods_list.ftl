<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>商品列表 - Powered By UNICORN</title>
<meta name="Author" content="UNICORN Team" />
<meta name="Copyright" content="UNICORN" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link href="${base}/template/admin/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/admin/css/admin.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/jquery.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.pager.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/base.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/admin.js"></script>
</head>
<body class="list">
	<div class="bar">
		商品列表&nbsp;总记录数: ${pager.totalCount} (共${pager.pageCount}页)
	</div>
	<div class="body">
		<form id="listForm" action="goods!list.action" method="post">
			<div class="listBar">
				<input type="button" class="formButton" onclick="location.href='goods!add.action'" value="添加商品" hidefocus />
				<input type="button" id="deleteButton" class="formButton" url="goods!delete.action" value="删 除" disabled hidefocus />
				&nbsp;&nbsp;
				<label>查找: </label>
				<select name="pager.searchBy">
					<option value="name"<#if pager.searchBy == "name"> selected</#if>>
						商品名称
					</option>
					<option value="goodsSn"<#if pager.searchBy == "goodsSn"> selected</#if>>
						商品编号
					</option>
					<option value="brand.name"<#if pager.searchBy == "brand.name"> selected</#if>>
						商品品牌
					</option>
				</select>
				<input type="text" name="pager.keyword" value="${pager.keyword!}" />
				<input type="button" id="searchButton" class="formButton" value="搜 索" hidefocus />
				&nbsp;&nbsp;
				<label>每页显示: </label>
				<select name="pager.pageSize" id="pageSize">
					<option value="10"<#if pager.pageSize == 10> selected</#if>>
						10
					</option>
					<option value="20"<#if pager.pageSize == 20> selected</#if>>
						20
					</option>
					<option value="50"<#if pager.pageSize == 50> selected</#if>>
						50
					</option>
					<option value="100"<#if pager.pageSize == 100> selected</#if>>
						100
					</option>
				</select>
			</div>
			<table id="listTable" class="listTable">
				<tr>
					<th class="check">
						<input type="checkbox" class="allCheck" />
					</th>
					<th>
						<a href="javascript:void(0);" class="sort" name="name" hidefocus>商品名称</a>
					</th>
					<th>
						<a href="javascript:void(0);" class="sort" name="goodsSn" hidefocus>商品编号</a>
					</th>
					<th>
						<a href="javascript:void(0);" class="sort" name="goodsCategory" hidefocus>分类</a>
					</th>
					<th>
						<a href="javascript:void(0);" class="sort" name="brand" hidefocus>品牌</a>
					</th>
					<th>
						<a href="javascript:void(0);" class="sort" name="area" hidefocus>所在地</a>
					</th>
					<th>
						<a href="javascript:void(0);" class="sort" name="price" hidefocus>销售价</span>
					</th>
					<th>
						<span>标签</span>
					</th>
					<th>
						<a href="javascript:void(0);" class="sort" name="isMarketable" hidefocus>上架</a>
					</th>
					<th>
						<a href="javascript:void(0);" class="sort" name="isPromotion" hidefocus>促销</a>
					</th>
					<th>
						<a href="javascript:void(0);" class="sort" name="store">库存</a>
					</th>
					<th>
						<span>操作</span>
					</th>
				</tr>
				<#list pager.result as goods>
					<tr>
						<td>
							<input type="checkbox" name="ids" value="${goods.id}" />
						</td>
						<td>
							<span title="${goods.name}">
								${goods.name, 30}
							</span>
						</td>
						<td>
							${goods.goodsSn}
						</td>
						<td>
							${(goods.goodsCategory.name)!}
						</td>
						<td>
							${(goods.brand.name)!}
						</td>
						<td>
							${(goods.area.displayName)!}
						</td>
						<td>
							${goods.price?string(currencyFormat)}
						</td>
						<td>
							<#list goods.tagSet as tag>
							${(tag.name)!}<#if tag_has_next>,</#if>
							</#list>
						</td>
						<td>
							<span class="${goods.isMarketable?string('true','false')}Icon">&nbsp;</span>
						</td>
						<td>
							<span class="${goods.isPromotion?string('true','false')}Icon">&nbsp;</span>
						</td>
						<td>
							${(goods.store)!"-"}
						</td>
						<td>
							<a href="goods!edit.action?id=${goods.id}" title="编辑">[编辑]</a>
							<a href="goods!copy.action?id=${goods.id}" title="复制">[复制]</a>
							<#if goods.isMarketable>
								<a href="${base}/rzrk/goods!displayDetail.action?id=${goods.id}" target="_blank" title="浏览">[浏览]</a>
							<#else>
								<span title="未上架">[未上架]</span>
							</#if>
						</td>
					</tr>
				</#list>
			</table>
			<#if (pager.result?size > 0)>
				<div class="pagerBar">
					<div class="pager">
						<#include "/WEB-INF/template/admin/pager.ftl" />
					</div>
				</div>
			<#else>
				<div class="noRecord">没有找到任何记录!</div>
			</#if>
		</form>
	</div>
</body>
</html>