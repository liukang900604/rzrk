<#assign s=JspTaglibs["/WEB-INF/tld/struts.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>添加订单<#if setting.isShowPoweredInfo> - Powered By UNICORN</#if></title>
<meta name="Author" content="UNICORN Team" />
<meta name="Copyright" content="UNICORN" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link href="${base}/template/rzrk/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/rzrk/css/rzrk.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/jquery.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.validate.methods.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.lSelect.js"></script>
<script type="text/javascript" src="${base}/template/rzrk/js/base.js"></script>
<script type="text/javascript" src="${base}/template/rzrk/js/rzrk.js"></script>
<!--[if lte IE 6]>
	<script type="text/javascript" src="${base}/template/common/js/belatedPNG.js"></script>
	<script type="text/javascript">
		// 解决IE6透明PNG图片BUG
		DD_belatedPNG.fix(".belatedPNG");
	</script>
<![endif]-->
<script type="text/javascript">
$().ready( function() {

	var $validateErrorContainer = $("#validateErrorContainer");
	var $validateErrorLabelContainer = $("#validateErrorContainer ul");
	var $orderInfoForm = $("#orderInfoForm");
	
	var $receiverId = $("#receiverTr .receiverId");
	var $areaSelect = $("#areaSelect");
	var $otherReceiver = $("#otherReceiver");
	var $otherReceiverTable = $("#otherReceiverTable");
	var $otherReceiverInput = $("#otherReceiverTable :input");
	var $deliveryTypeId = $("#deliveryTypeTable input");
	var $paymentConfigTr = $("#paymentConfigTr");
	var $paymentConfigId = $("#paymentConfigTable input");
	var $totalProductPrice = $("#totalProductPrice");
	var $deliveryFee = $("#deliveryFee");
	var $paymentFee = $("#paymentFee");
	var $orderAmount = $("#orderAmount");
	
	var deliveryFee = 0;// 配送费用
	var paymentFee = 0;// 支付费用
	
	$otherReceiverTable.show();
	
	// 地区选择菜单
	$areaSelect.lSelect({
		url: "${base}/rzrk/area!ajaxArea.action"// AJAX数据获取url
	});
	
	
	// 根据配送方式修改配送费用、订单总金额,并显示/隐藏支付方式
	$deliveryTypeId.click( function() {
		var $this = $(this);
		$paymentConfigId.attr("checked", false);
		paymentFee = 0;
		var deliveryMethod = $this.attr("deliveryMethod");
		if (deliveryMethod == "deliveryAgainstPayment") {
			$paymentConfigId.attr("disabled", false);
			$paymentConfigTr.show();
		} else {
			$paymentConfigId.attr("disabled", true);
			$paymentConfigTr.hide();
		}
		deliveryFee = $this.attr("deliveryFee");
		$deliveryFee.text(currencyFormat(deliveryFee));
		$paymentFee.text(currencyFormat(paymentFee));
		$orderAmount.text(currencyFormat(floatAdd(floatAdd(totalProductPrice, deliveryFee), paymentFee)));
	});
	
	// 根据支付方式修改订单总金额
	$paymentConfigId.click( function() {
		var $this = $(this);
		var paymentFeeType = $this.attr("paymentFeeType");
		var paymentFee = $this.attr("paymentFee");
		if (paymentFeeType == "scale") {
			paymentFee = floatMul(floatAdd(totalProductPrice, deliveryFee), floatDiv(paymentFee, 100));
		}
		$paymentFee.text(currencyFormat(paymentFee));
		$orderAmount.text(currencyFormat(floatAdd(totalProductPrice, floatAdd(deliveryFee, paymentFee))));
	});
	
	// 表单验证
	$orderInfoForm.validate({
		errorContainer: $validateErrorContainer,
		errorLabelContainer: $validateErrorLabelContainer,
		wrapper: "li",
		errorClass: "validateError",
		ignoreTitle: true,
		rules: {
			"receiver.id": "required",
			"receiver.name": "required",
			"areaId": "required",
			"receiver.address": "required",
			"receiver.mobile": {
				"requiredOne": "#receiverPhone"
			},
			"receiver.zipCode": "required",
			"deliveryType.id": "required",
			"paymentConfig.id": "required",
			"memo": {
				maxlength: 200
			}
		},
		messages: {
			"receiver.id": "请选择收货地址",
			"receiver.name": "请填写收货人姓名",
			"areaId": "请选择地区",
			"receiver.address": "请填写地址",
			"receiver.mobile": {
				"requiredOne": "电话或手机必须填写其中一项"
			},
			"receiver.zipCode": "请填写邮编",
			"deliveryType.id": "请选择配送方式",
			"paymentConfig.id": "请选择支付方式",
			"memo": {
				maxlength: "附言长度必须小于等于200"
			}
		},
		submitHandler: function(form) {
			$(form).find(":submit").attr("disabled", true);
			form.submit();
		}
	});

});
</script>
</head>
<body class="orderInfo">
	<div class="body">
		<div id="validateErrorContainer" class="validateErrorContainer">
			<div class="validateErrorTitle">以下信息填写有误,请重新填写</div>
			<ul></ul>
		</div>
		<div class="orderInfoDetail">
		     <br />
		     <br />
			<form id="orderInfoForm" action="order!addOrder.action" method="post">
				<@s.token />
				<table class="orderInfoTable">
				    <tr id="product">
				        <th>添加商品</th>
				        <td>
				           <label>商品 </label><input type="text" name="productSn" value="商品编号" /><br />
				           <label>数量 </label><input type="text" name="productQuantity" value="商品数量" />			           
				        </td>
				    </tr>
					<tr id="receiverTr">
						<th>收货信息</th>
						<td>
							<ul>								
								<li>
									<div class="blank"></div>
									<table id="otherReceiverTable" class="otherReceiverTable" style="display: block;">
										<tr>
											<th>
												收货人姓名: 
											</th>
											<td>
												<input type="text" name="receiver.name" class="formText" />
												<label class="requireField">*</label>
											</td>
										</tr>
										<tr>
											<th>
												地区: 
											</th>
											<td>
												<input type="text" id="areaSelect" name="areaId" class="hidden"/>
												<label class="requireField">*</label>
											</td>
										</tr>
										<tr>
											<th>
												地址: 
											</th>
											<td>
												<input type="text" name="receiver.address" class="formText" />
												<label class="requireField">*</label>
											</td>
										</tr>
										<tr>
											<th>
												电话: 
											</th>
											<td>
												<input type="text" id="receiverPhone" name="receiver.phone" class="formText" />
												<label class="requireField">*</label>
											</td>
										</tr>
										<tr>
											<th>
												手机: 
											</th>
											<td>
												<input type="text" name="receiver.mobile" class="formText" />
												<label class="requireField">*</label>
											</td>
										</tr>
										<tr>
											<th>
												邮编: 
											</th>
											<td>
												<input type="text" name="receiver.zipCode" class="formText" />
												<label class="requireField">*</label>
											</td>
										</tr>
									</table>
								</li>
							</ul>
						</td>
					</tr>
					<tr>
						<th>运费</th>
						<td>
							<table id="deliveryTypeTable" class="deliveryTypeTable">
								<#list allDeliveryTypeList as deliveryType>
									<tr>
										<td>
										   <input type="text" name="order.deliveryFee" value="0" />
										</td>
									</tr>
								</#list>
							</table>
						</td>
					</tr>
					<tr id="paymentConfigTr" class="paymentConfigTr">
						<th>支付方式</th>
						<td>
							<table id="paymentConfigTable" class="paymentConfigTable">
								<#list allPaymentConfigList as paymentConfig>
									<tr>
										<th>
											<label>
												<input type="radio" name="paymentConfig.id" value="${paymentConfig.id}" paymentFeeType="${paymentConfig.paymentFeeType}" paymentFee="${paymentConfig.paymentFee}" /> ${paymentConfig.name}
											</label>
										</th>
										<td>
											<#if paymentConfig.paymentFeeType == "scale" && paymentConfig.paymentFee != 0>
												[支付费率: ${paymentConfig.paymentFee}%]
											<#elseif paymentConfig.paymentFeeType == "fixed" && paymentConfig.paymentFee != 0>
												[支付费用: ${paymentConfig.paymentFee?string(currencyFormat)}]
											</#if>
											<#if paymentConfig.description??>
												<p>${paymentConfig.description}</p>
											</#if>
										</td>
									</tr>
								</#list>
							</table>
						</td>
					</tr>
					<tr>
						<th>附言</th>
						<td>
							<input type="text" name="memo" class="formText" />
						</td>
					</tr>
					<tr>
						<td colspan="2" align="center">
							<input type="submit" value="确定" />
						</td>
					</tr>
				</table>
				<div class="blank"></div>
				<div class="blank"></div>
			</form>
		</div>
	</div>
</body>
</html>