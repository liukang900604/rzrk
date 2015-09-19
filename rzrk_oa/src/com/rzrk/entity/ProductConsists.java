package com.rzrk.entity;

public class ProductConsists extends BaseEntity {
	
	private String receiptNo;//单据编号

	private String date;//日期

	private String applicant;//申请人

	private String applyDeparment;//申请部门

	private String assetReleaseDate;//预计发行日期

	private String productName;//产品名称

	private String productType;//产品类型

	private String investStrategy;//投资策略

	private String tradeSystem;//交易系统

	private String riskMethod;//风控方式

	private String accountPWD;//账号密码

	private String investManager;//投资经理

	private String teamManager;//团队负责人

	private String projectAssits;//项目助理

	private String docStatstic;//建档统计

	private String assetsSum;//估值核算

	private String financialSum;//财务核算

	private String agent;//代销机构

	private String agentResponsbler;//代销项目负责人

	private String assestsManager;//资产管理人

	private String projectResponsibler;//资产管理项目负责人

	private String depositBank;//托管行

	private String depositBankResponsiber;//托管项目负责人

	private String onGroundDepartment;//落地营业部

	private String investmentAdviser;//投资顾问

	private String futureBusiness;//期货商

	private String futureBusinessResponsibler;//期货商项目负责人

	private String setupDate;//成立日

	private String firstCloseDate;//首次封闭期

	private String readyClose;//准封闭期

	private String openInterval;//开放间隔

	private String firstOpenDate;//首次开放日

	private String regularOpenDay;//正常开放日

	private String purchaseFee;//认购费用

	private String repurchaseFee;//赎回费用

	private String firstReleaseScale;//首发规模（万）

	private String managementFee;//资管费

	private String fixedInvestManagementFee;//固定管理费（投顾）

	private String fixedReturnManagementFee;//固定管理费（返还）

	private String firstPayDay;//首个支付日

	private String payWay;//支付方式

	private String floatManagementFee;//浮动管理费

	private String floatReturn;//浮动返还

	private String floatFirstPayDay;//浮动首个支付日

	private String floatPayWay;//浮动支付方式

	private String agentFee;//券商佣金

	private String feePromise;//佣金承诺

	private String feeReturn;//佣金返还

	private String cancelSingleFiveYuan;//取消单笔5.0元

	private String bGradePlan;//B级设计

	private String bDivide;//B级分配

	private String cGradePlan;//C级设计

	private String cGradeDivide;//C级分配

	private String depositFee;//托管费
	
	private String futureFee;//期货佣金
	
	private String alartLine;//预警线
	
	private String stopLossLine;//止损线
	
	private String enlargeMoneyDesign;//增强资设计
	
	private String enlargeMoneyMan;//增强资金人
	
	private String enlargeMoneyPayDate;//增强资金打款时间
	
	private String enlargeMoneyRepurchaseCondition; //增强资金赎回条件
	
	private String inferiorInfo; //跟投劣后信息
	
	
	private String recieveUnit;//收款单位
	private String bank;//开户行
	private String account;//开户行
	private String remark1;//备注
	private String riskBase;//风险敞口基数
	private String risk;//风险敞口
	private String growthEnterpriseMarketLimit;//创业板限制
	private String repurchaseRate;//正回购比例
	private String investRange;//投资范围
	private String investLimit;//投资限制
	private String remark;//备注
	public String getReceiptNo() {
		return receiptNo;
	}
	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getApplicant() {
		return applicant;
	}
	public void setApplicant(String applicant) {
		this.applicant = applicant;
	}
	public String getApplyDeparment() {
		return applyDeparment;
	}
	public void setApplyDeparment(String applyDeparment) {
		this.applyDeparment = applyDeparment;
	}
	public String getAssetReleaseDate() {
		return assetReleaseDate;
	}
	public void setAssetReleaseDate(String assetReleaseDate) {
		this.assetReleaseDate = assetReleaseDate;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getInvestStrategy() {
		return investStrategy;
	}
	public void setInvestStrategy(String investStrategy) {
		this.investStrategy = investStrategy;
	}
	public String getTradeSystem() {
		return tradeSystem;
	}
	public void setTradeSystem(String tradeSystem) {
		this.tradeSystem = tradeSystem;
	}
	public String getRiskMethod() {
		return riskMethod;
	}
	public void setRiskMethod(String riskMethod) {
		this.riskMethod = riskMethod;
	}
	public String getAccountPWD() {
		return accountPWD;
	}
	public void setAccountPWD(String accountPWD) {
		this.accountPWD = accountPWD;
	}
	public String getInvestManager() {
		return investManager;
	}
	public void setInvestManager(String investManager) {
		this.investManager = investManager;
	}
	public String getTeamManager() {
		return teamManager;
	}
	public void setTeamManager(String teamManager) {
		this.teamManager = teamManager;
	}
	public String getProjectAssits() {
		return projectAssits;
	}
	public void setProjectAssits(String projectAssits) {
		this.projectAssits = projectAssits;
	}
	public String getDocStatstic() {
		return docStatstic;
	}
	public void setDocStatstic(String docStatstic) {
		this.docStatstic = docStatstic;
	}
	public String getAssetsSum() {
		return assetsSum;
	}
	public void setAssetsSum(String assetsSum) {
		this.assetsSum = assetsSum;
	}
	public String getFinancialSum() {
		return financialSum;
	}
	public void setFinancialSum(String financialSum) {
		this.financialSum = financialSum;
	}
	public String getAgent() {
		return agent;
	}
	public void setAgent(String agent) {
		this.agent = agent;
	}
	public String getAgentResponsbler() {
		return agentResponsbler;
	}
	public void setAgentResponsbler(String agentResponsbler) {
		this.agentResponsbler = agentResponsbler;
	}
	public String getAssestsManager() {
		return assestsManager;
	}
	public void setAssestsManager(String assestsManager) {
		this.assestsManager = assestsManager;
	}
	public String getProjectResponsibler() {
		return projectResponsibler;
	}
	public void setProjectResponsibler(String projectResponsibler) {
		this.projectResponsibler = projectResponsibler;
	}
	public String getDepositBank() {
		return depositBank;
	}
	public void setDepositBank(String depositBank) {
		this.depositBank = depositBank;
	}
	public String getDepositBankResponsiber() {
		return depositBankResponsiber;
	}
	public void setDepositBankResponsiber(String depositBankResponsiber) {
		this.depositBankResponsiber = depositBankResponsiber;
	}
	public String getOnGroundDepartment() {
		return onGroundDepartment;
	}
	public void setOnGroundDepartment(String onGroundDepartment) {
		this.onGroundDepartment = onGroundDepartment;
	}
	public String getInvestmentAdviser() {
		return investmentAdviser;
	}
	public void setInvestmentAdviser(String investmentAdviser) {
		this.investmentAdviser = investmentAdviser;
	}
	public String getFutureBusiness() {
		return futureBusiness;
	}
	public void setFutureBusiness(String futureBusiness) {
		this.futureBusiness = futureBusiness;
	}
	public String getFutureBusinessResponsibler() {
		return futureBusinessResponsibler;
	}
	public void setFutureBusinessResponsibler(String futureBusinessResponsibler) {
		this.futureBusinessResponsibler = futureBusinessResponsibler;
	}
	public String getSetupDate() {
		return setupDate;
	}
	public void setSetupDate(String setupDate) {
		this.setupDate = setupDate;
	}
	public String getFirstCloseDate() {
		return firstCloseDate;
	}
	public void setFirstCloseDate(String firstCloseDate) {
		this.firstCloseDate = firstCloseDate;
	}
	public String getReadyClose() {
		return readyClose;
	}
	public void setReadyClose(String readyClose) {
		this.readyClose = readyClose;
	}
	public String getOpenInterval() {
		return openInterval;
	}
	public void setOpenInterval(String openInterval) {
		this.openInterval = openInterval;
	}
	public String getFirstOpenDate() {
		return firstOpenDate;
	}
	public void setFirstOpenDate(String firstOpenDate) {
		this.firstOpenDate = firstOpenDate;
	}
	public String getRegularOpenDay() {
		return regularOpenDay;
	}
	public void setRegularOpenDay(String regularOpenDay) {
		this.regularOpenDay = regularOpenDay;
	}
	public String getPurchaseFee() {
		return purchaseFee;
	}
	public void setPurchaseFee(String purchaseFee) {
		this.purchaseFee = purchaseFee;
	}
	public String getRepurchaseFee() {
		return repurchaseFee;
	}
	public void setRepurchaseFee(String repurchaseFee) {
		this.repurchaseFee = repurchaseFee;
	}
	public String getFirstReleaseScale() {
		return firstReleaseScale;
	}
	public void setFirstReleaseScale(String firstReleaseScale) {
		this.firstReleaseScale = firstReleaseScale;
	}
	public String getManagementFee() {
		return managementFee;
	}
	public void setManagementFee(String managementFee) {
		this.managementFee = managementFee;
	}
	public String getFixedInvestManagementFee() {
		return fixedInvestManagementFee;
	}
	public void setFixedInvestManagementFee(String fixedInvestManagementFee) {
		this.fixedInvestManagementFee = fixedInvestManagementFee;
	}
	public String getFixedReturnManagementFee() {
		return fixedReturnManagementFee;
	}
	public void setFixedReturnManagementFee(String fixedReturnManagementFee) {
		this.fixedReturnManagementFee = fixedReturnManagementFee;
	}
	public String getFirstPayDay() {
		return firstPayDay;
	}
	public void setFirstPayDay(String firstPayDay) {
		this.firstPayDay = firstPayDay;
	}
	public String getPayWay() {
		return payWay;
	}
	public void setPayWay(String payWay) {
		this.payWay = payWay;
	}
	public String getFloatManagementFee() {
		return floatManagementFee;
	}
	public void setFloatManagementFee(String floatManagementFee) {
		this.floatManagementFee = floatManagementFee;
	}
	public String getFloatReturn() {
		return floatReturn;
	}
	public void setFloatReturn(String floatReturn) {
		this.floatReturn = floatReturn;
	}
	public String getFloatFirstPayDay() {
		return floatFirstPayDay;
	}
	public void setFloatFirstPayDay(String floatFirstPayDay) {
		this.floatFirstPayDay = floatFirstPayDay;
	}
	public String getFloatPayWay() {
		return floatPayWay;
	}
	public void setFloatPayWay(String floatPayWay) {
		this.floatPayWay = floatPayWay;
	}
	public String getAgentFee() {
		return agentFee;
	}
	public void setAgentFee(String agentFee) {
		this.agentFee = agentFee;
	}
	public String getFeePromise() {
		return feePromise;
	}
	public void setFeePromise(String feePromise) {
		this.feePromise = feePromise;
	}
	public String getFeeReturn() {
		return feeReturn;
	}
	public void setFeeReturn(String feeReturn) {
		this.feeReturn = feeReturn;
	}
	public String getCancelSingleFiveYuan() {
		return cancelSingleFiveYuan;
	}
	public void setCancelSingleFiveYuan(String cancelSingleFiveYuan) {
		this.cancelSingleFiveYuan = cancelSingleFiveYuan;
	}
	public String getbGradePlan() {
		return bGradePlan;
	}
	public void setbGradePlan(String bGradePlan) {
		this.bGradePlan = bGradePlan;
	}
	public String getbDivide() {
		return bDivide;
	}
	public void setbDivide(String bDivide) {
		this.bDivide = bDivide;
	}
	public String getcGradePlan() {
		return cGradePlan;
	}
	public void setcGradePlan(String cGradePlan) {
		this.cGradePlan = cGradePlan;
	}
	public String getcGradeDivide() {
		return cGradeDivide;
	}
	public void setcGradeDivide(String cGradeDivide) {
		this.cGradeDivide = cGradeDivide;
	}
	public String getDepositFee() {
		return depositFee;
	}
	public void setDepositFee(String depositFee) {
		this.depositFee = depositFee;
	}
	public String getFutureFee() {
		return futureFee;
	}
	public void setFutureFee(String futureFee) {
		this.futureFee = futureFee;
	}
	public String getAlartLine() {
		return alartLine;
	}
	public void setAlartLine(String alartLine) {
		this.alartLine = alartLine;
	}
	public String getStopLossLine() {
		return stopLossLine;
	}
	public void setStopLossLine(String stopLossLine) {
		this.stopLossLine = stopLossLine;
	}
	public String getEnlargeMoneyDesign() {
		return enlargeMoneyDesign;
	}
	public void setEnlargeMoneyDesign(String enlargeMoneyDesign) {
		this.enlargeMoneyDesign = enlargeMoneyDesign;
	}
	public String getEnlargeMoneyMan() {
		return enlargeMoneyMan;
	}
	public void setEnlargeMoneyMan(String enlargeMoneyMan) {
		this.enlargeMoneyMan = enlargeMoneyMan;
	}
	public String getEnlargeMoneyPayDate() {
		return enlargeMoneyPayDate;
	}
	public void setEnlargeMoneyPayDate(String enlargeMoneyPayDate) {
		this.enlargeMoneyPayDate = enlargeMoneyPayDate;
	}
	public String getEnlargeMoneyRepurchaseCondition() {
		return enlargeMoneyRepurchaseCondition;
	}
	public void setEnlargeMoneyRepurchaseCondition(
			String enlargeMoneyRepurchaseCondition) {
		this.enlargeMoneyRepurchaseCondition = enlargeMoneyRepurchaseCondition;
	}
	public String getInferiorInfo() {
		return inferiorInfo;
	}
	public void setInferiorInfo(String inferiorInfo) {
		this.inferiorInfo = inferiorInfo;
	}
	public String getRecieveUnit() {
		return recieveUnit;
	}
	public void setRecieveUnit(String recieveUnit) {
		this.recieveUnit = recieveUnit;
	}
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getRemark1() {
		return remark1;
	}
	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}
	public String getRiskBase() {
		return riskBase;
	}
	public void setRiskBase(String riskBase) {
		this.riskBase = riskBase;
	}
	public String getRisk() {
		return risk;
	}
	public void setRisk(String risk) {
		this.risk = risk;
	}
	public String getGrowthEnterpriseMarketLimit() {
		return growthEnterpriseMarketLimit;
	}
	public void setGrowthEnterpriseMarketLimit(String growthEnterpriseMarketLimit) {
		this.growthEnterpriseMarketLimit = growthEnterpriseMarketLimit;
	}
	public String getRepurchaseRate() {
		return repurchaseRate;
	}
	public void setRepurchaseRate(String repurchaseRate) {
		this.repurchaseRate = repurchaseRate;
	}
	public String getInvestRange() {
		return investRange;
	}
	public void setInvestRange(String investRange) {
		this.investRange = investRange;
	}
	public String getInvestLimit() {
		return investLimit;
	}
	public void setInvestLimit(String investLimit) {
		this.investLimit = investLimit;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	

}
