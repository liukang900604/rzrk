package com.rzrk.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Index;

/** 产品 */
@Entity
@Table(name = "rzrk_product")
public class Product extends BaseEntity {
	//产品分类
	public enum Categories {  
		  对冲,其它
		} 

    private static final long serialVersionUID = 1L;
    
    

    /** 全称 */
    private String name;
    /** 简称(short) */
    private String nameShort;
    /** 超简称(super short) */
    private String nameSShort;
    /** 信托类型 */
    private Integer type;// 100:开放型,101:证券信托,102:证券投资集合资金信托
    
    /** 产品分类*/
    private Integer product_categories; //0:对冲,1:其它
    
    /**进取型*/
    private Integer aggressive; //0,1,-1
	/** 是否可用 */
    private Integer isEnabled;
    /** 投资范围 */
    private String investRange;
	/** 单位面值 */
    private String parValue;
    /** 监管机构 */
    private String supervisionOrganization;
    /** 受托人 */
    private String trustee;
    /** 保管银行 */
    private String custodianBank;
    /** 证券经纪人 */
    private String broker;
    /** 期货经纪人 */
    private String futuresBroker;
    /** 投资顾问 */
    private String investConsultant;
    /** 投资经理 */
    private String investManager;
    /** 最低认购金额 */
    private String minSubAmount;
    /** 资金封闭期 */
    private String lockUpPeriod;
    /** 开放日 */
    private String openDate;
    /** 估值日 */
    private String valuationDate;
    /** 最低追加金额 */
    private String minAddAmount;
    /** 追加资金到账日 */
    private String addDeadline;
    /** 提交追加申请截止日 */
    private String addAppDeadline;
    /** 存续期 */
    private String duration;
    /** 认购费率(subscription fee) */
    private String subFee;
    /** 年管理费率 */
    private String managerFee;
    /** 赎回费率 */
    private String redemptionFee;
    /** 赎回申请截止日 */
    private String redemptionAppDeadline;
    /** 浮动管理费 */
    private String floatManagerFee;
    /** 信托分红 */
    private String trustDividend;
    /** 推介期 */
    private String roadshowPeriod;
    /** 成立日 */
    private String establishDate;
    /** 认购帐户户名 */
    private String subAccountName;
    /** 认购帐户开户行 */
    private String subAccountBank;
    /** 认购帐户帐号 */
    private String subAccount;
    /** 产品属性1:开发,2:封闭 */
    private Integer purchaseState;
    /** 是否推荐0:否,1:是 */
    private Integer top;
    /** 权重 */
    private Integer weight;
    
    /** 迅投别名 **/
    private String xuntouName;
    
    /** history name **/
    private String historyName;
    
    private Integer isValid = 0;
    
    private String isCunxu;
    
    private int showHS300;
    
	public int getShowHS300() {
		return showHS300;
	}

	public void setShowHS300(int showHS300) {
		this.showHS300 = showHS300;
	}

	public String getIsCunxu() {
		return isCunxu;
	}

	public void setIsCunxu(String isCunxu) {
		this.isCunxu = isCunxu;
	}

	public Integer getIsValid() {
		return isValid;
	}

	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}

	public String getHistoryName() {
		return historyName;
	}

	public void setHistoryName(String historyName) {
		this.historyName = historyName;
	}

	public String getXuntouName() {
		return xuntouName;
	}

	public void setXuntouName(String xuntouName) {
		this.xuntouName = xuntouName;
	}

    // 附加属性



	/** 客户持有份额start */
    private Double totalAmount;

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    /** 客户持有份额end */

    /** 净值start */
    private ProductNetValue lastNetValue;// 最近一条净值数据

    private List<ProductNetValue> netValueList;// 净值数据列表
    
    private ProductEarnCollect productEarnCollect;
    
    private List<ProductEarnCollect> productEarnCollectList;
    
    private String receiverList;
    
    public String getReceiverList() {
		return receiverList;
	}

	public void setReceiverList(String receiverList) {
		this.receiverList = receiverList;
	}

	@Transient
  
	public ProductEarnCollect getProductEarnCollect() {
		return productEarnCollect;
	}

	public void setProductEarnCollect(ProductEarnCollect productEarnCollect) {
		this.productEarnCollect = productEarnCollect;
	}
    
	@Transient
	public List<ProductEarnCollect> getProductEarnCollectList() {
		return productEarnCollectList;
	}

	public void setProductEarnCollectList(
			List<ProductEarnCollect> productEarnCollectList) {
		this.productEarnCollectList = productEarnCollectList;
	}

	

	@Transient
    public ProductNetValue getLastNetValue() {
        return lastNetValue;
    }

    public void setLastNetValue(ProductNetValue lastNetValue) {
        this.lastNetValue = lastNetValue;
    }

    @Transient
    public List<ProductNetValue> getNetValueList() {
        return netValueList;
    }

    public void setNetValueList(List<ProductNetValue> netValueList) {
        this.netValueList = netValueList;
    }

    /** 净值end */

    @Column(name = "name", length = 100, nullable = false)
    @Index(name = "idx_name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "name_short", length = 50, nullable = true)
    public String getNameShort() {
        return nameShort;
    }

    public void setNameShort(String nameShort) {
        this.nameShort = nameShort;
    }

    @Column(name = "name_s_short", length = 25, nullable = false)
    public String getNameSShort() {
        return nameSShort;
    }

    public void setNameSShort(String nameSShort) {
        this.nameSShort = nameSShort;
    }

    @Column(name = "type", length = 3, nullable = true)
    @Index(name = "idx_type")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
    
    @Column(name = "product_categories", length = 2, nullable = true)
    @Index(name = "idx_product_categories")
    public Integer getProduct_categories() {
		return product_categories;
	}

	public void setProduct_categories(Integer product_categories) {
		this.product_categories = product_categories;
	}
	
	@Column(name = "aggressive", length = 2, nullable = true)
    @Index(name = "idx_aggressive")
	public Integer getAggressive() {
		return aggressive;
	}

	public void setAggressive(Integer aggressive) {
		this.aggressive = aggressive;
	}
    
    @Column(name = "is_enabled", length = 1, nullable = false)
    @Index(name = "idx_is_enabled")
    public Integer getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Integer isEnabled) {
        this.isEnabled = isEnabled;
    }

    @Column(name = "invest_range", length = 100, nullable = true)
    public String getInvestRange() {
        return investRange;
    }

    public void setInvestRange(String investRange) {
        this.investRange = investRange;
    }

    @Column(name = "par_value", length = 20, nullable = true)
    public String getParValue() {
        return parValue;
    }

    public void setParValue(String parValue) {
        this.parValue = parValue;
    }

    @Column(name = "supervision_organization", length = 100, nullable = true)
    public String getSupervisionOrganization() {
        return supervisionOrganization;
    }

    public void setSupervisionOrganization(String supervisionOrganization) {
        this.supervisionOrganization = supervisionOrganization;
    }

    @Column(name = "trustee", length = 100, nullable = true)
    public String getTrustee() {
        return trustee;
    }

    public void setTrustee(String trustee) {
        this.trustee = trustee;
    }

    @Column(name = "custodian_bank", length = 100, nullable = true)
    public String getCustodianBank() {
        return custodianBank;
    }

    public void setCustodianBank(String custodianBank) {
        this.custodianBank = custodianBank;
    }

    @Column(name = "broker", length = 100, nullable = true)
    public String getBroker() {
        return broker;
    }

    public void setBroker(String broker) {
        this.broker = broker;
    }

    @Column(name = "futures_broker", length = 100, nullable = true)
    public String getFuturesBroker() {
        return futuresBroker;
    }

    public void setFuturesBroker(String futuresBroker) {
        this.futuresBroker = futuresBroker;
    }

    @Column(name = "invest_consultant", length = 100, nullable = true)
    public String getInvestConsultant() {
        return investConsultant;
    }

    public void setInvestConsultant(String investConsultant) {
        this.investConsultant = investConsultant;
    }

    @Column(name = "invest_manager", length = 100, nullable = true)
    public String getInvestManager() {
        return investManager;
    }

    public void setInvestManager(String investManager) {
        this.investManager = investManager;
    }

    @Column(name = "min_sub_amount", length = 50, nullable = true)
    public String getMinSubAmount() {
        return minSubAmount;
    }

    public void setMinSubAmount(String minSubAmount) {
        this.minSubAmount = minSubAmount;
    }

    @Column(name = "lock_up_period", length = 50, nullable = true)
    public String getLockUpPeriod() {
        return lockUpPeriod;
    }

    public void setLockUpPeriod(String lockUpPeriod) {
        this.lockUpPeriod = lockUpPeriod;
    }

    @Column(name = "open_date", length = 100, nullable = true)
    public String getOpenDate() {
        return openDate;
    }

    public void setOpenDate(String openDate) {
        this.openDate = openDate;
    }

    @Column(name = "valuation_date", length = 100, nullable = true)
    public String getValuationDate() {
        return valuationDate;
    }

    public void setValuationDate(String valuationDate) {
        this.valuationDate = valuationDate;
    }

    @Column(name = "min_add_amount", length = 50, nullable = true)
    public String getMinAddAmount() {
        return minAddAmount;
    }

    public void setMinAddAmount(String minAddAmount) {
        this.minAddAmount = minAddAmount;
    }

    @Column(name = "add_deadline", length = 100, nullable = true)
    public String getAddDeadline() {
        return addDeadline;
    }

    public void setAddDeadline(String addDeadline) {
        this.addDeadline = addDeadline;
    }

    @Column(name = "add_app_deadline", length = 100, nullable = true)
    public String getAddAppDeadline() {
        return addAppDeadline;
    }

    public void setAddAppDeadline(String addAppDeadline) {
        this.addAppDeadline = addAppDeadline;
    }

    @Column(name = "duration", length = 100, nullable = true)
    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    @Column(name = "sub_fee", length = 100, nullable = true)
    public String getSubFee() {
        return subFee;
    }

    public void setSubFee(String subFee) {
        this.subFee = subFee;
    }

    @Column(name = "manager_fee", length = 100, nullable = true)
    public String getManagerFee() {
        return managerFee;
    }

    public void setManagerFee(String managerFee) {
        this.managerFee = managerFee;
    }

    @Column(name = "redemption_fee", length = 100, nullable = true)
    public String getRedemptionFee() {
        return redemptionFee;
    }

    public void setRedemptionFee(String redemptionFee) {
        this.redemptionFee = redemptionFee;
    }

    @Column(name = "redemption_app_deadline", length = 100, nullable = true)
    public String getRedemptionAppDeadline() {
        return redemptionAppDeadline;
    }

    public void setRedemptionAppDeadline(String redemptionAppDeadline) {
        this.redemptionAppDeadline = redemptionAppDeadline;
    }

    @Column(name = "float_manager_fee", length = 100, nullable = true)
    public String getFloatManagerFee() {
        return floatManagerFee;
    }

    public void setFloatManagerFee(String floatManagerFee) {
        this.floatManagerFee = floatManagerFee;
    }

    @Column(name = "trust_dividend", length = 100, nullable = true)
    public String getTrustDividend() {
        return trustDividend;
    }

    public void setTrustDividend(String trustDividend) {
        this.trustDividend = trustDividend;
    }

    @Column(name = "roadshow_period", length = 100, nullable = true)
    public String getRoadshowPeriod() {
        return roadshowPeriod;
    }

    public void setRoadshowPeriod(String roadshowPeriod) {
        this.roadshowPeriod = roadshowPeriod;
    }

    @Column(name = "establish_date", length = 50, nullable = true)
    public String getEstablishDate() {
        return establishDate;
    }

    public void setEstablishDate(String establishDate) {
        this.establishDate = establishDate;
    }

    @Column(name = "sub_account_name", length = 100, nullable = true)
    public String getSubAccountName() {
        return subAccountName;
    }

    public void setSubAccountName(String subAccountName) {
        this.subAccountName = subAccountName;
    }

    @Column(name = "sub_account_bank", length = 100, nullable = true)
    public String getSubAccountBank() {
        return subAccountBank;
    }

    public void setSubAccountBank(String subAccountBank) {
        this.subAccountBank = subAccountBank;
    }

    @Column(name = "sub_account", length = 100, nullable = true)
    @Index(name = "idx_sub_account")
    public String getSubAccount() {
        return subAccount;
    }

    public void setSubAccount(String subAccount) {
        this.subAccount = subAccount;
    }

    @Column(name = "purchase_state", length = 11, nullable = false)
    @Index(name = "idx_purchase_state")
    public Integer getPurchaseState() {
        return purchaseState;
    }

    public void setPurchaseState(Integer purchaseState) {
        this.purchaseState = purchaseState;
    }

    @Column(name = "top", length = 1, nullable = false)
    @Index(name = "idx_top")
    public Integer getTop() {
        return top;
    }

    public void setTop(Integer top) {
        this.top = top;
    }

    @Column(name = "weight", length = 11, nullable = false)
    @Index(name = "idx_weight")
    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    @Override
    @Transient
    public void onSave() {
        createDate = new Date();
        modifyDate = new Date();
    }

    @Override
    @Transient
    public void onUpdate() {
        modifyDate = new Date();
    }

    public static String[] getUnUpdateField() {
        return new String[] { "createDate", "id" };
    }

}