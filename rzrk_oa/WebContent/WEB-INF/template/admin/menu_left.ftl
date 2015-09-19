<#assign sec=JspTaglibs["/WEB-INF/tld/security.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>管理菜单 - Powered By UNICORN</title>
<meta name="Author" content="UNICORN Team" />
<meta name="Copyright" content="UNICORN" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link href="${base}/template/admin/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/admin/css/admin.css" rel="stylesheet" type="text/css" />
</head>
<body class="menu">
	<div class="body">
			<dl>
				<dt>
					<span>内容管理</span>
				</dt>
					<dd>
						<a href="article!list.action" target="mainFrame">文章管理</a>
					</dd>
					<dd>
						<a href="article_category!list.action" target="mainFrame">文章分类</a>
					</dd>
			</dl>
			<dl>
				<dt>
					<span>信托产品</span>
				</dt>
					<dd>
						<a href="product!list.action?isCunxu=1" target="mainFrame">产品管理</a>
					</dd>
					<dd>
						<a href="productnetvalue!list.action" target="mainFrame">净值管理</a>
					</dd>
					<!--
				<dd>
					<a href="instant_messaging!edit.action" target="mainFrame">走势图生成</a>
				</dd>
				-->
			</dl>
			<dl>
				<dt>
					<span>股指管理</span>
				</dt>
				<dd>
					<a href="stockindex!list.action?stockId=1" target="mainFrame">股指管理</a>
				</dd>
			</dl>
			<dl>
				<dt>
					<span>客户管理</span>
				</dt>
					<dd>
						<a href="member!list.action" target="mainFrame">客户管理「产品」</a>
					</dd>
			</dl>
			<dl>
				<dt>
					<span>预约认购</span>
				</dt>
					<dd>
						<a href="reservations!list.action" target="mainFrame">认购列表</a>
					</dd>
			</dl>
			<dl>
				<dt>
					<span>人员&nbsp;</span>
				</dt>
					<dd>
						<a href="admin!list.action" target="mainFrame">人员信息</a>
					</dd>
					<dd>
						<a href="role!list.action" target="mainFrame">角色管理</a>
					</dd>
					<dd>
						<a href="deparment!list.action" target="mainFrame">部门信息</a>
					</dd>
					<dd>
						<a href="job!list.action" target="mainFrame">岗位管理</a>
					</dd>
					<dd>
						<a href="job_level!list.action" target="mainFrame">职务级别</a>
					</dd>
					<dd>
						<a href="group!list.action" target="mainFrame">组信息</a>
					</dd>
			</dl>
			<dl>
				<dt>
					<span>操作日志&nbsp;</span>
				</dt>
				<dd>
					<a href="log!list.action" target="mainFrame">查看日志</a>
				</dd>
			</dl>
			<dl>
				<dt>
					<span>内部管理&nbsp;</span>
				</dt>
				<dd>
					<a href="product_daily_record!list.action?pager.orderBy=productName&pager.order=asc" target="mainFrame">产品每日汇总列表</a>
				</dd>
			</dl>
	</div>
</body>
</html>