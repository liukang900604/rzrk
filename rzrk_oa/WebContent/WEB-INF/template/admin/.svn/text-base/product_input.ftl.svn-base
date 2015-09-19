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
    <link rel="stylesheet" href="/rzrk/css/background/product_input.css" />
    <script type="text/javascript" src="/rzrk/js/alljs.js"></script>
    <script type="text/javascript" src="/rzrk/js/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="/rzrk/js/jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="/rzrk/js/background/index.js"></script>
    <script type="text/javascript">
    	window.isAddAction = <#if isAddAction>true<#else>false</#if>;
    </script>
    <script type="text/javascript" src="/rzrk/js/background/product_input.js"></script>
</head>
<body class="input admin easyui-layout">
	<div data-options="region:'center'">
		<div class="easyui-panel" title="产品信息" data-options="fit:true,border:false">
			<form id="pdtForm" method="post">
				<input type="hidden" name="id" value="${id}"/>
		        <table cellpadding="5" style='width:100%;'>
		        	<colgroup>
		        		<col style="width:150px;"/>
		        		<col/>
		        	</colgroup>
		            <tr>
		                <td>全　称</td>
		                <td><input name="product.name" maxlength="30" value="${(product.name)!}" /></td>
		            </tr>
		            <tr>
		                <td>迅投别名</td>
		                <td><input name="product.xuntouName" maxlength="30" value="${(product.xuntouName)!}" />&nbsp;(用于导入迅投Excel)</td>
		            </tr>
		            <tr>
		                <td>历史别名</td>
		                <td><input name="product.historyName" maxlength="30" value="${(product.historyName)!}" />&nbsp;(用于导入历史别名Excel)</td>
		            </tr>
		            <tr>
		                <td>简　称</td>
		                <td><input name="product.nameShort" maxlength="30" value="${(product.nameShort)!}" /></td>
		            </tr>
		            <tr>
		                <td>超简称(用于页面展示)</td>
		                <td><input name="product.nameSShort" maxlength="30" value="${(product.nameSShort)!}" /></td>
		            </tr>
		            <tr>
		                <td>信托类型</td>
		                <td>
							<select name="product.type">
								<option value="-1">未指定</option>
								<option value="100" <#if isEditAction && (product.type == 100)>selected</#if>>开放型</option>
								<option value="101" <#if isEditAction && (product.type == 101)>selected</#if>>证券信托</option>
								<option value="102" <#if isEditAction && (product.type == 102)>selected</#if>>证券投资集合资金信托</option>
							</select>
						</td>
		            </tr>
		            <tr>
		                <td>产品类型</td>
		                <td>
							<select name="product.product_categories">
								<option value="0" <#if isEditAction && (product.product_categories == 0)>selected</#if>>对冲类</option>
								<option value="1" <#if isEditAction && (product.product_categories == 1)>selected</#if>>其它</option>
							</select>
						</td>
		            </tr>
		            <tr>
		                <td>子类型</td>
		                <td>
							<select name="product.aggressive">
								<option value="0" <#if isEditAction && (product.aggressive == 0)>selected</#if>>进取型</option>
								<option value="1" <#if isEditAction && (product.aggressive == 1)>selected</#if>>稳健型</option>
								<option value="2" <#if isEditAction && (product.aggressive == 2)>selected</#if>>灵活型</option>
							</select>
						</td>
		            </tr>
		            <tr>
		                <td>是否启用</td>
		                <td>
							<select name="product.isEnabled">
								<option value="1" <#if isEditAction && (product.isEnabled == 1)>selected</#if>>启用</option>
								<option value="0" <#if isEditAction && (product.isEnabled == 0)>selected</#if>>禁用</option>
							</select>
						</td>
		            </tr>
		            
		            <tr>
		                <td>净值走势是否显示HS300对比走势</td>
		                <td>
							<select name="product.showHS300">
								<option value="1" <#if isEditAction && (product.showHS300 == 1)>selected</#if>>显示</option>
								<option value="0" <#if isEditAction && (product.showHS300 == 0)>selected</#if>>不显示</option>
							</select>
						</td>
		            </tr>
		            
		            <tr>
		                <td>投资范围</td>
		                <td><input name="product.investRange" maxlength="30" value="${(product.investRange)!}" /></td>
		            </tr>
		            <tr>
		                <td>单位面值</td>
		                <td><input name="product.parValue" maxlength="20" value="${(product.parValue)!}" /></td>
		            </tr>
		            <tr>
		                <td>监管机构</td>
		                <td><input name="product.supervisionOrganization" maxlength="30" value="${(product.supervisionOrganization)!}" /></td>
		            </tr>
		            <tr>
		                <td>受托人</td>
		                <td><input name="product.trustee" maxlength="30" value="${(product.trustee)!}" /></td>
		            </tr>
		            <tr>
		                <td>保管银行</td>
		                <td><input name="product.custodianBank" maxlength="30" value="${(product.custodianBank)!}" /></td>
		            </tr>
		            <tr>
		                <td>证券经纪人</td>
		                <td><input name="product.broker" maxlength="30" value="${(product.broker)!}" /></td>
		            </tr>
		            <tr>
		                <td>期货经纪人</td>
		                <td><input name="product.futuresBroker" maxlength="30" value="${(product.futuresBroker)!}" /></td>
		            </tr>
		            <tr>
		                <td>投资顾问</td>
		                <td><input name="product.investConsultant" maxlength="30" value="${(product.investConsultant)!}" /></td>
		            </tr>
		            <tr>
		                <td>投资经理</td>
		                <td><input name="product.investManager" maxlength="30" value="${(product.investManager)!}" /></td>
		            </tr>
		            <tr>
		                <td>最低认购金额</td>
		                <td><input name="product.minSubAmount" maxlength="30" value="${(product.minSubAmount)!}" /></td>
		            </tr>
		            <tr>
		                <td>资金封闭期</td>
		                <td><input name="product.lockUpPeriod" maxlength="30" value="${(product.lockUpPeriod)!}" /></td>
		            </tr>
		            <tr>
		                <td>开放日</td>
		                <td><input name="product.openDate" maxlength="50" value="${(product.openDate)!}" /></td>
		            </tr>
		            <tr>
		                <td>估值日</td>
		                <td><input name="product.valuationDate" maxlength="30" value="${(product.valuationDate)!}" /></td>
		            </tr>
		            <tr>
		                <td>最低追加金额</td>
		                <td><input name="product.minAddAmount" maxlength="30" value="${(product.minAddAmount)!}" /></td>
		            </tr>
		            <tr>
		                <td>追加资金到账日</td>
		                <td><input name="product.addDeadline" maxlength="30" value="${(product.addDeadline)!}" /></td>
		            </tr>
		            <tr>
		                <td>提交追加申请截止日</td>
		                <td><input name="product.addAppDeadline" maxlength="30" value="${(product.addAppDeadline)!}" /></td>
		            </tr>
		            <tr>
		                <td>存续期</td>
		                <td><input name="product.duration" maxlength="30" value="${(product.duration)!}" /></td>
		            </tr>
		            <tr>
		                <td>认购费率</td>
		                <td><input name="product.subFee" maxlength="30" value="${(product.subFee)!}" /></td>
		            </tr>
		            <tr>
		                <td>年管理费率</td>
		                <td><input name="product.managerFee" maxlength="30" value="${(product.managerFee)!}" /></td>
		            </tr>
		            <tr>
		                <td>赎回费率</td>
		                <td><input name="product.redemptionFee" maxlength="30" value="${(product.redemptionFee)!}" /></td>
		            </tr>
		            <tr>
		                <td>赎回申请截止日</td>
		                <td><input name="product.redemptionAppDeadline" maxlength="30" value="${(product.redemptionAppDeadline)!}" /></td>
		            </tr>
		            <tr>
		                <td>浮动管理费</td>
		                <td><input name="product.floatManagerFee" maxlength="30" value="${(product.floatManagerFee)!}" /></td>
		            </tr>
		            <tr>
		                <td>信托分红</td>
		                <td><input name="product.trustDividend" maxlength="30" value="${(product.trustDividend)!}" /></td>
		            </tr>
		            <tr>
		                <td>推介期</td>
		                <td><input name="product.roadshowPeriod" maxlength="30" value="${(product.roadshowPeriod)!}" /></td>
		            </tr>
		            <tr>
		                <td>成立日</td>
		                <td><input name="product.establishDate" maxlength="30" value="${(product.establishDate)!}" /></td>
		            </tr>
		            <tr>
		                <td>认购帐户户名</td>
		                <td><input name="product.subAccountName" maxlength="30" value="${(product.subAccountName)!}" /></td>
		            </tr>
		            <tr>
		                <td>认购帐户开户行</td>
		                <td><input name="product.subAccountBank" maxlength="30" value="${(product.subAccountBank)!}" /></td>
		            </tr>
		            <tr>
		                <td>认购帐户帐号</td>
		                <td><input name="product.subAccount" maxlength="30" value="${(product.subAccount)!}" /></td>
		            </tr>
		            <tr>
		                <td>产品属性</td>
		                <td>
							<select name="product.purchaseState">
								<option value="1" <#if isEditAction && (product.purchaseState == 1)>selected</#if>>开放</option>
								<option value="2" <#if isEditAction && (product.purchaseState == 2)>selected</#if>>封闭</option>
							</select>
						</td>
		            </tr>
		            <tr>
		                <td>是否推荐</td>
		                <td>
							<select name="product.top">
								<option value="0" <#if isEditAction && (product.top == 0)>selected</#if>>否</option>
								<option value="1" <#if isEditAction && (product.top == 1)>selected</#if>>是</option>
							</select>
						</td>
		            </tr>
		            <tr>
		                <td>是否有效</td>
		                <td>
							<select name="product.isValid">
								<option value="0" <#if isEditAction && (product.isValid == 0)>selected</#if>>否</option>
								<option value="1" <#if isEditAction && (product.isValid == 1)>selected</#if>>是</option>
							</select>&nbsp;(是否发送邮件)
						</td>
		            </tr>
		            <tr>
		                <td>是否存续</td>
		                <td>
							<select name="product.isCunxu">
								<option value="0" <#if isEditAction && (product.isCunxu == 0)>selected</#if>>否</option>
								<option value="1" <#if isEditAction && (product.isCunxu == 1)>selected</#if>>是</option>
							</select>
						</td>
		            </tr>
		            <tr>
		                <td>权重(数值越大权重越大)</td>
		                <td><input name="product.weight" maxlength="5" value="${(product.weight)!0}" /></td>
		            </tr>
		            <tr>
		                <td>净值收件人</td>
		                <td><input name="product.receiverList"  value="${(product.receiverList)}" />&nbsp;(输入合法的邮件地址并以";"号分开)</td>
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