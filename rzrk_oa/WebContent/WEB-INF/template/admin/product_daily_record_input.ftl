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
    <link rel="stylesheet" href="/rzrk/css/background/product_daily_record_input.css" />
    <script type="text/javascript" src="/rzrk/js/alljs.js"></script>
    <script type="text/javascript" src="/rzrk/js/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="/rzrk/js/jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="/rzrk/js/background/index.js"></script>
    <script type="text/javascript">
    	window.productId = '${(pdr.productId)!}';
    	window.isAddAction = <#if isAddAction>true<#else>false</#if>;
    </script>
    <script type="text/javascript" src="/rzrk/js/background/product_daily_record_input.js"></script>
</head>
<body class="input admin easyui-layout">
	<div data-options="region:'center'">
		<div class="easyui-panel" title="当日信息" data-options="fit:true,border:false" style="padding:5px 0;">
			<form id="nvForm" method="post">
				<input type="hidden" name="id" value="${id}"/>
				<#if !(isAddAction && !productId)>
				<input type="hidden" name="pdr.productId" value="${productId}"/>
				</#if>
		        <table cellpadding="5">
					<#if isAddAction && !productId>
			            <tr>
			                <td>产品</td>
			                <td>
								<select name="pdr.productId" style="width:250px;">
									<#list productList as temp>
										<option value="${temp.id}">${temp.name}</option>
									</#list>
								</select>
							</td>
			            </tr>
					</#if>
		            <tr>
		                <td>证券账户总资产</td>
		                <td><input type="text" name="pdr.stockAccountTotalAmount" value="${(pdr.stockAccountTotalAmount)!}"/></td>
		            </tr>
		            <tr>
		                <td>期货账户总资产</td>
		                <td><input type="text" name="pdr.futureAccountTotalAmount" value="${(pdr.futureAccountTotalAmount)!}"/></td>
		            </tr>
		            <tr>
		                <td>股票总市值</td>
		                <td><input type="text" name="pdr.stockMarketValue" value="${(pdr.stockMarketValue)!}"/></td>
		            </tr>
		            <tr>
		                <td>期货保证金</td>
		                <td><input type="text" name="pdr.futureMarketVaue" value="${(pdr.futureMarketVaue)!}"/></td>
		            </tr>
		            <tr>
		                <td>空单市值</td>
		                <td><input type="text" name="pdr.futureEmptyValue" value="${(pdr.futureEmptyValue)!}"/></td>
		            </tr>
		            <tr>
		                <td>合计</td>
		                <td><input type="text" name="pdr.sum" value="${(pdr.sum)!}"/></td>
		            </tr>
		            <tr>
		                <td>银行存款</td>
		                <td><input type="text" name="pdr.bankAmount" value="${(pdr.bankAmount)!}"/></td>
		            </tr>
		            <tr>
		                <td>总资产</td>
		                <td><input type="text" name="pdr.total" value="${(pdr.total)!}"/></td>
		            </tr>
		            <tr>
		                <td>未扣除各项费用总资产</td>
		                <td><input type="text" name="pdr.beforeReduce" value="${(pdr.beforeReduce)!}"/></td>
		            </tr>
		            <tr>
		                <td>份额</td>
		                <td><input type="text" name="pdr.amount" value="${(pdr.amount)!}"/></td>
		            </tr>
		            <tr>
		                <td>扣除各项费用净值</td>
		                <td><input type="text" name="pdr.reduceNetValue" value="${(pdr.reduceNetValue)!}"/></td>
		            </tr>
		            <tr>
		                <td>未扣除各项费用净值</td>
		                <td><input type="text" name="pdr.beforeReduceNetValue" value="${(pdr.beforeReduceNetValue)!}"/></td>
		            </tr>
		            <tr>
		                <td>估值表净值</td>
		                <td><input type="text" name="pdr.assetsNetValue" value="${(pdr.assetsNetValue)!}"/></td>
		            </tr>
		            <tr>
		                <td>赎回金额</td>
		                <td><input type="text" name="pdr.repay" value="${(pdr.repay)!}"/></td>
		            </tr>
		            <tr>
		                <td>申购金额</td>
		                <td><input type="text" name="pdr.buy" value="${(pdr.buy)!}"/></td>
		            </tr>
					<#if isAddAction>
			            <tr>
			                <td>日期</td>
			                <td>
								<input id="recordDate" name="pdr.recordDate" type="text" class="easyui-datebox" data-options="formatter: dateFormatter, parser: dateParser, editable:false" style="width:154px;">
							</td>
			            </tr>
					</#if>
		        </table>
		    </form>
		</div>
	</div>
	<div data-options="region:'south',border:false" style="height:45px;overflow:hidden;">
		<div style="padding:10px;">
		    <a href="javascript:void(0)" data-options="iconCls:'icon-save'" class="easyui-linkbutton" id="saveBtn">&nbsp;保&nbsp;存&nbsp;&nbsp;</a>
		    &nbsp;&nbsp;
		    <a href="javascript:void(0)" data-options="iconCls:'icon-cancel'" class="easyui-linkbutton" id="closeBtn">&nbsp;返&nbsp;回&nbsp;&nbsp;</a>
		</div>
	</div>
</body>
</html>