package com.rzrk.entity;

import javax.persistence.Entity;

import com.rzrk.util.NumberUtil;

@Entity
public class ProductDailyRecord extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 488764470825865221L;
	
	private String productId;//外键
	
	private String productName;// 产品名称
	private double stockAccountTotalAmount=0;// 证券账户总资产
	private double futureAccountTotalAmount=0;//期货账户总资产
	private double stockMarketValue=0;//股票总市值
	private double futureMarketVaue=0;//期货保证金
	private double sum=0;//合计
	private double bankAmount=0;//银行存款
	private double total=0;//总资产
	private double beforeReduce=0;//未扣除各项费用总资产
	private double amount=0;//份额
	private double reduceNetValue=0;//扣除各项费用净值
	private double beforeReduceNetValue=0;//未扣除更像费用净值
	private double assetsNetValue=0;//估值表净值
	
	private String recordDate;

	private String productXuntouName;//迅投产品名称
	private String riskRate;//风险暴露度
	private String remark;// 备注
	private double futureEmptyValue;//空单市值 
	
	private double repay; //赎回
	
	private double buy; //申购
	
	public double getRepay() {
		return repay;
	}
	public void setRepay(double repay) {
		this.repay = repay;
	}
	public double getBuy() {
		return buy;
	}
	public void setBuy(double buy) {
		this.buy = buy;
	}
	public String getRiskRate() {
		return riskRate == null ? "" : riskRate;
	}
	public double getFutureEmptyValue() {
		return NumberUtil.getFormatedDouble(futureEmptyValue, 2);
	}
	public void setFutureEmptyValue(double futureEmptyValue) {
		this.futureEmptyValue = futureEmptyValue;
	}
	public void setRiskRate(String riskRate) {
		this.riskRate = riskRate;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getRecordDate() {
		return recordDate;
	}
	public void setRecordDate(String recordDate) {
		this.recordDate = recordDate;
	}
	public String getProductXuntouName() {
		return productXuntouName == null ? "" : productXuntouName;
	}
	public void setProductXuntouName(String productXuntouName) {
		this.productXuntouName = productXuntouName;
	}
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getProductName() {
		return productName == null ? "" : productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public double getStockAccountTotalAmount() {
		return  NumberUtil.getFormatedDouble(stockAccountTotalAmount, 2);
	}
	public void setStockAccountTotalAmount(double stockAccountTotalAmount) {
		this.stockAccountTotalAmount = stockAccountTotalAmount;
	}
	public double getFutureAccountTotalAmount() {
		return NumberUtil.getFormatedDouble(futureAccountTotalAmount, 2);
	}
	public void setFutureAccountTotalAmount(double futureAccountTotalAmount) {
		this.futureAccountTotalAmount = futureAccountTotalAmount;
	}
	public double getStockMarketValue() {
		return NumberUtil.getFormatedDouble(stockMarketValue, 2) ;
	}
	public void setStockMarketValue(double stockMarketValue) {
		this.stockMarketValue = stockMarketValue;
	}
	public double getFutureMarketVaue() {
		return NumberUtil.getFormatedDouble(futureMarketVaue, 2) ;
	}
	public void setFutureMarketVaue(double futureMarketVaue) {
		this.futureMarketVaue = futureMarketVaue;
	}
	public double getSum() {
		return NumberUtil.getFormatedDouble(sum, 2) ;
	}
	public void setSum(double sum) {
		this.sum = sum;
	}
	public double getBankAmount() {
		return NumberUtil.getFormatedDouble(bankAmount, 2) ;
	}
	public void setBankAmount(double bankAmount) {
		this.bankAmount = bankAmount;
	}
	public double getTotal() {
		return NumberUtil.getFormatedDouble(total, 2) ;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public double getBeforeReduce() {
		return NumberUtil.getFormatedDouble(beforeReduce, 2) ;
	}
	public void setBeforeReduce(double beforeReduce) {
		this.beforeReduce = beforeReduce;
	}
	public double getAmount() {
		return NumberUtil.getFormatedDouble(amount, 2) ;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public double getReduceNetValue() {
		return NumberUtil.getFormatedDouble(reduceNetValue, 4);
	}
	public void setReduceNetValue(double reduceNetValue) {
		this.reduceNetValue = reduceNetValue;
	}
	public double getBeforeReduceNetValue() {
		return NumberUtil.getFormatedDouble(beforeReduceNetValue, 4);

	}
	public void setBeforeReduceNetValue(double beforeReduceNetValue) {
		this.beforeReduceNetValue = beforeReduceNetValue;
	}
	public double getAssetsNetValue() {
		return NumberUtil.getFormatedDouble(assetsNetValue, 4);
	}
	public void setAssetsNetValue(double assetsNetValue) {
		this.assetsNetValue = assetsNetValue;
	}
	
	
	
	
}
