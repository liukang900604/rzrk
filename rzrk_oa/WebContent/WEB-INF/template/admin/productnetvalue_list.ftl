<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>产品净值管理 - Powered By rzrk</title>
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
			
            /**
             * 判断是否为浮点数
             * 
             * @param oNum
             * @returns {Boolean}
             */
            function isfloat(oNum) {
                if (!oNum)
                    return false;
                var strP = /^(-?\d+)(\.\d+)?$/;
                if (!strP.test(oNum))
                    return false;
                try {
                    if (parseFloat(oNum) != oNum)
                        return false;
                } catch (ex) {
                    return false;
                }
                return true;
            }
            
            // 保存产品收益信息
            $("#saveProductEarnCollect").click(function() {
                var earnWeek = $("#earnWeek").val();
                var earnMonth = $("#earnMonth").val();
                var earnTotal = $("#earnTotal").val();
                var aror = $("#aror").val();
                if (earnWeek != '' && !isfloat(earnWeek)) {
                    $.dialog({
                        type : "warn",
                        content : "最近一周收益为浮点数!",
                        modal : true,
                        autoCloseTime : 3000
                    });
                    return false;
                }
                if (earnMonth != '' && !isfloat(earnMonth)) {
                    $.dialog({
                        type : "warn",
                        content : "最近一月收益为浮点数!",
                        modal : true,
                        autoCloseTime : 3000
                    });
                    return false;
                }
                if (earnTotal != '' && !isfloat(earnTotal)) {
                    $.dialog({
                        type : "warn",
                        content : "至今收益为浮点数!",
                        modal : true,
                        autoCloseTime : 3000
                    });
                    return false;
                }
                if (aror != '' && !isfloat(aror)) {
                    $.dialog({
                        type : "warn",
                        content : "年化收益为浮点数!",
                        modal : true,
                        autoCloseTime : 3000
                    });
                    return false;
                }
            
                // 开始执行保存操作
                var url = "/admin/productnetvalue!saveProductEarnCollect.action";
                $.dialog({
                    type : "warn",
                    content : "确认保存?",
                    ok : "确 定",
                    cancel : "取 消",
                    modal : true,
                    okCallback : saveProductEarnCollect
                });
                function saveProductEarnCollect() {
                    $.ajax({
                        url : url,
                        data : {
                            productId : "${id}",
                            earnWeek : earnWeek,
                            earnMonth : earnMonth,
                            earnTotal : earnTotal,
                            aror : aror,
                        },
                        type : "POST",
                        dataType : "json",
                        cache : false,
                        success : function(data) {
                            if (data.status == "success") {
                                $.dialog({
                                    type : "success",
                                    title : "操作结果",
                                    content : "保存成功!",
                                    ok : "确定",
                                    //okCallback : redirectUrl,
                                    //cancelCallback : redirectUrl,
                                    width : 380,
                                    modal : true
                                });
                            } else {
                                $.dialog({
                                    type : "warn",
                                    content : data.message,
                                    modal : true,
                                    autoCloseTime : 3000
                                });
                            }
                        }
                    });
                }
            });
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
    <div class="bar">产品净值 --> 可用产品数:<#if productList> ${productList.size()}<#else>0</#if></div>
    <div class="body">
        <form id="listForm" action="productnetvalue!list.action" method="post">
            <#if id != null>
            <div class="listBar">
                <input type="button" class="formButton" onclick="location.href='productnetvalue!add.action?productId=${id}'" value="添加净值" hidefocus />
            </div>
            </#if>
            <input type="hidden" name="id" value="${id}">
            <table class="listSearchTable"> 
                <tr>
                    <td colspan="4">产品列表</td>
                </tr>
                <#if productList>
                <#list productList?chunk(4) as item>
                <tr>
                    <#list item as miItem>
                    <td <#if miItem.id == id>style="background-color: #BBFFBB;"</#if>>
                    <#assign productIndex = item_index * 4 + miItem_index + 1 />
                    (<#if productIndex < 10>0${productIndex}<#else>${productIndex}</#if>) &nbsp; &nbsp; 
                    <a href="productnetvalue!list.action?id=${miItem.id}">${miItem.name}</a>
                    </td>
                    </#list>
                </tr>
                </#list>
                </#if>
            </table>
            <div class="listBar"></div>
            <table id="listTable" class="listTable">
                <tr>
                    <th class="check"><input type="checkbox" class="allCheck" /></th>
                    <th><a href="javascript:void(0);" class="sort" name="dateType" hidefocus>日期类型</a></th>
                    <th><a href="javascript:void(0);" class="sort" name="date" hidefocus>更新日期</a></th>
                    <th><a href="javascript:void(0);" name="trustValue" hidefocus>信托单位净值（元）</a></th>
                    <th><a href="javascript:void(0);" name="trustValueStart" hidefocus>指定累计净值</a></th>
                    <th><a href="javascript:void(0);" name="trustValue" hidefocus>信托单位累计净值（元）</a></th>
                    <#--
                    <th><a href="javascript:void(0);" name="growthRateMonth" hidefocus>当期净值增长率</a></th>
                    <th><a href="javascript:void(0);" name="growthRateAdd" hidefocus>累计净值增长率</a></th>
                    -->
                    <th><a href="javascript:void(0);" name="growthRateAdd" hidefocus>累计净值增长率</a></th>
                    <th><a href="javascript:void(0);" name="createDate" hidefocus>录入日期</a></th>
                    <th><span>操作</span></th>
                </tr>
                <#list pager.result as item>
                <tr>
                    <td><input type="checkbox" name="ids" value="${item.id}" /></td>
                    <td><#if item.dateType == 100>估值日<#elseif item.dateType == 103>成立日<#else>-</#if></td>
                    <td>${item.date?string("yyyy年MM月dd日")}</td>
                    <td>${item.trustValue}</td>
                    <td><#if item.trustValueStart?if_exists><font color="red">${item.trustValueStart}<font><#else>-</#if></td>
                    <td>${item.trustValueAdd}</td>
                    <#--
                    <td><#if item.growthRateMonth?if_exists>${item.growthRateMonth?string.percent}<#else>-</#if></td>
                    <td><#if item.growthRateAdd?if_exists>${item.growthRateAdd?string.percent}<#else>-</#if></td>
                    -->
                    <td><#if item.growthRateAdd?if_exists>#{item.growthRateAdd * 100;M4}%<#else>-</#if></td>
                    <td>${item.createDate?string("yyyy-MM-dd hh:mm:ss")}</td>
                    <td><a href="productnetvalue!edit.action?id=${item.id}&productId=${id}" title="[编辑]">[编辑]</a></td>
                </tr>
                </#list>
            </table>
            <#if (pager.result != null)>
            <#if (pager.result?size > 0)>
            <div class="pagerBar">
                <div class="delete">
                    <input type="button" id="deleteButton" class="formButton" url="productnetvalue!delete.action" value="删 除" disabled hidefocus />
                </div>
                <div class="pager"><#include "/WEB-INF/template/admin/pager.ftl" /></div>
            </div>
            <#else>
                <div class="noRecord">没有找到任何记录!</div>
            </#if>
            </#if>
        </form>
        <#if id != null>
        <table id="productEarnCollectTable" class="listTable">
            <tr>
                <th><a href="javascript:void(0);" name="earnWeek" hidefocus>最近周收益</a></th>
                <th><a href="javascript:void(0);" name="earnMonth" hidefocus>最近月收益</a></th>
                <th><a href="javascript:void(0);" name="earnTotal" hidefocus>至今收益</a></th>
                <th><a href="javascript:void(0);" name="aror" hidefocus>年化收益</a></th>
                <th><span>操作</span></th>
            </tr>
            <tr>
                <td><input type="text" id="earnWeek" value="<#if productEarnCollect?if_exists><#if productEarnCollect.earnWeek?if_exists>#{productEarnCollect.earnWeek}<#else></#if></#if>"/></td>
                <td><input type="text" id="earnMonth" value="<#if productEarnCollect?if_exists><#if productEarnCollect.earnMonth?if_exists>#{productEarnCollect.earnMonth}<#else></#if></#if>"/></td>
                <td><input type="text" id="earnTotal" value="<#if productEarnCollect?if_exists><#if productEarnCollect.earnTotal?if_exists>#{productEarnCollect.earnTotal}<#else></#if></#if>"/></td>
                <td><input type="text" id="aror" value="<#if productEarnCollect?if_exists><#if productEarnCollect.aror?if_exists>#{productEarnCollect.aror}<#else></#if></#if>"/></td>
                <td><a href="javascript:void(0);" title="点击保存" id="saveProductEarnCollect">[保存]</a></td>
            </tr>
        </table>
        </#if>
    </div>
</body>
</html>