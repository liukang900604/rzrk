<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>客户产品管理 - Powered By rzrk</title>
<meta name="Author" content="rzrk Team" />
<meta name="Copyright" content="rzrk" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link href="${base}/template/admin/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/admin/css/admin.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/jquery.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.pager.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/base.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.lSelect.js"></script>
<script type="text/javascript" src="${base}/template/common/datePicker/WdatePicker.js"></script>
<script>
	$().ready(function() {
		var $listForm = $("#listForm");// 列表表单
		if ($listForm.size() > 0) {
			$listForm.submit(preSubmit);
			var $searchButton = $("#searchButton");// 查找按钮
			var $allCheck = $("#listTable input.allCheck");// 全选复选框
			var $listTableTr = $("#listTable tr:gt(0)");
			var $idsCheck = $("#listTable input[name='ids']");// ID复选框
			var $deleteButton = $("#deleteButton");// 删除按钮
			var $pageNumber = $("#pageNumber");// 当前页码
			var $pageSize = $("#pageSize");// 每页显示数
			var $sort = $("#listTable .sort");// 排序
			var $orderBy = $("#orderBy");// 排序字段
			var $order = $("#order");// 排序方式

			// 全选
			$allCheck.click(function() {
				var $this = $(this);
				if ($this.attr("checked")) {
					$idsCheck.attr("checked", true);
					$deleteButton.attr("disabled", false);
					$listTableTr.addClass("checked");
				} else {
					$idsCheck.attr("checked", false);
					$deleteButton.attr("disabled", true);
					$listTableTr.removeClass("checked");
				}
			});

			// 无复选框被选中时,删除按钮不可用
			$idsCheck.click(function() {
				var $this = $(this);
				if ($this.attr("checked")) {
					$this.parent().parent().addClass("checked");
					$deleteButton.attr("disabled", false);
				} else {
					$this.parent().parent().removeClass("checked");
					var $idsChecked = $("#listTable input[name='ids']:checked");
					if ($idsChecked.size() > 0) {
						$deleteButton.attr("disabled", false);
					} else {
						$deleteButton.attr("disabled", true);
					}
				}
			});

			// 批量删除
			$deleteButton.click(function() {
				var url = $(this).attr("url");
				var $idsCheckedCheck = $("#listTable input[name='ids']:checked");
				$.dialog({
					type : "warn",
					content : "您确定要删除吗?",
					ok : "确 定",
					cancel : "取 消",
					modal : true,
					okCallback : batchDelete
				});
				function batchDelete() {
					$.ajax({
						url : url,
						data : $idsCheckedCheck.serialize(),
						type : "POST",
						dataType : "json",
						cache : false,
						success : function(data) {
							if (data.status == "success") {
								$idsCheckedCheck.parent().parent().remove();
							}
							$deleteButton.attr("disabled", true);
							$allCheck.attr("checked", false);
							$idsCheckedCheck.attr("checked", false);
							$.message({
								type : data.status,
								content : data.message
							});
						}
					});
				}
			});

			// 查找
			$searchButton.click(function() {
				$pageNumber.val("1");
				$listForm.submit();
			});

			// 每页显示数
			$pageSize.change(function() {
				$pageNumber.val("1");
				$listForm.submit();
			});

			// 排序
			$sort.click(function() {
				var $currentOrderBy = $(this).attr("name");
				if ($orderBy.val() == $currentOrderBy) {
					if ($order.val() == "") {
						$order.val("asc");
					} else if ($order.val() == "desc") {
						$order.val("asc");
					} else if ($order.val() == "asc") {
						$order.val("desc");
					}
				} else {
					$orderBy.val($currentOrderBy);
					$order.val("asc");
				}
				$pageNumber.val("1");
				$listForm.submit();
			});

			// 排序图标效果
			if ($orderBy.val() != "") {
				$sort = $("#listTable .sort[name='" + $orderBy.val() + "']");
				if ($order.val() == "asc") {
					$sort.removeClass("desc").addClass("asc");
				} else {
					$sort.removeClass("asc").addClass("desc");
				}
			}

			// 页码跳转
			$.gotoPage = function(id) {
				$pageNumber.val(id);
				$listForm.submit();
			};
		}
	});

	function preSubmit() {
		disableChange("keyword");
	}

	function searchChange(value) {}

	function disableChange(key) {
		$("#keyword").attr("disabled", true);
		$("#" + key).removeAttr("disabled");
	}
</script>
</head>

<body class="list">
    <div class="bar">客户管理 --> 客户产品管理 --> 可用产品数: ${productList.size()}</div>
    <div class="body">
        <form id="listForm" action="memberproducthold!list.action" method="post">
            <input type="hidden" name="memberId" value="${memberId}">
            <input type="hidden" name="productId" value="${productId}">
            <table class="listSearchTable">
                <tr>
                    <td colspan="4">您好! ${member.realName}</td>
                </tr>
                <#list productList?chunk(4) as item>
                <tr>
                    <#list item as miItem>
                    <td <#if miItem.id == productId>style="background-color: #BBFFBB;"</#if>>
                    <#assign productIndex = item_index * 4 + miItem_index + 1 />
                    (<#if productIndex < 10>0${productIndex}<#else>${productIndex}</#if>) &nbsp; &nbsp; 
                    <#if miItem.totalAmount != null>
                    <span>持有量 : </span><span style="color: red;font-weight: bolder;">${miItem.totalAmount}</span>
                    &nbsp; &nbsp;
                    <a href="memberproducthold!list.action?productId=${miItem.id}&memberId=${member.id}">${miItem.name}</a>
                    <#else>
                    <span style="color: green;font-weight: bolder;">未购入</span>
                    <a href="memberproducthold!add.action?productId=${miItem.id}&memberId=${member.id}">${miItem.name}</a>
                    </#if>
                    </td>
                    </#list>
                </tr>
                </#list>
            </table>
            <div class="listBar"></div>
            <table id="listTable" class="listTable">
                <tr>
                    <th class="check"><input type="checkbox" class="allCheck" /></th>
                    <th><a href="javascript:void(0);" class="sort" name="actionDate" hidefocus>操作时间</a></th>
                    <th><a href="javascript:void(0);" class="sort" name="actionType" hidefocus>操作类型</a></th>
                    <th><a href="javascript:void(0);" class="sort" name="amount" hidefocus>操作数量（元）</a></th>
                    <th><a href="javascript:void(0);" class="sort" name="createDate" hidefocus>创建时间</a></th>
                </tr>
                <#list pager.result as item>
                <tr>
                    <td><input type="checkbox" name="ids" value="${item.id}" /></td>
                    <td>${item.actionDate?string("yyyy年MM月dd日 hh:mm:ss")}</td>
                    <td>
                    <#if item.actionType == "add">
                    <span style="color: #FF5151;font-weight: bolder;">追加</span>
                    <#elseif item.actionType == "sell">
                    <span style="color: #019858;font-weight: bolder;">赎回</span>
                    <#elseif item.actionType == "buy">
                    <span style="color: #0000E3;font-weight: bolder;">申购</span>
                    <#else>
                    <span style="color: #EAC100;font-weight: bolder;">异常</span>
                    </#if>
                    </td>
                    <td>${item.amount}</td>
                    <td>${item.createDate?string("yyyy-MM-dd hh:mm:ss")}</td>
                </tr>
                </#list>
            </table>
            <#if (pager.result != null)>
            <#if (pager.result?size > 0)>
            <div class="pagerBar">
                <div class="delete">
                <#if productId != null>
                <input type="button" class="formButton" onclick="location.href='memberproducthold!add.action?memberId=${member.id}&productId=${product.id}'" value="添加记录" hidefocus />
                </#if>
                </div>
                <div class="pager"><#include "/WEB-INF/template/admin/pager.ftl" /></div>
            </div>
            <#else>
                <div class="noRecord">没有找到任何记录!</div>
            </#if>
            </#if>
        </form>
    </div>
</body>
</html>