package com.rzrk.service;

import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.rzrk.entity.Admin;
import com.rzrk.entity.Product;

/**
 * 产品
 * @version $Id$
 * @author nerve
 * @since 2013-9-25 下午1:33:09
 */
public interface ProductService extends BaseService<Product, String> {
	public static final String DICT_TYPE = "PRODUCT_TYPE";
	public static final String DICT_CATEGRORIES = "PRODUCT_CATEGRORIES";
	public static final String DICT_AGGRESSIVE = "PRODUCT_AGGRESSIVE";
	public static final String DICT_ISENABLED = "PRODUCT_ISENABLED";
	public static final String DICT_SHOWHS300 = "PRODUCT_SHOWHS300";
	public static final String DICT_PURCHASESTATE = "PRODUCT_PURCHASESTATE";
	public static final String DICT_TOP = "PRODUCT_TOP";
	public static final String DICT_ISVALID = "PRODUCT_ISVALID";
	public static final String DICT_ISCUNXU = "PRODUCT_ISCUNXU";
	
	public static final String FIELD_name = "全称";
	public static final String FIELD_xuntouName = "迅投别名";
	public static final String FIELD_historyName = "历史别名";
	public static final String FIELD_nameShort = "简称";
	public static final String FIELD_nameSShort = "超简称";
	public static final String FIELD_type = "信托类型";
	public static final String FIELD_product_categories = "产品类型";
	public static final String FIELD_aggressive = "子类型";
	public static final String FIELD_isEnabled = "是否启用";
	public static final String FIELD_showHS300 = "净值走势是否显示HS300对比走势";
	public static final String FIELD_investRange = "投资范围";
	public static final String FIELD_parValue = "单位面值";
	public static final String FIELD_supervisionOrganization = "监管机构";
	public static final String FIELD_trustee = "受托人";
	public static final String FIELD_custodianBank = "保管银行";
	public static final String FIELD_broker = "证券经纪人";
	public static final String FIELD_futuresBroker = "期货经纪人";
	public static final String FIELD_investConsultant = "投资顾问";
	public static final String FIELD_investManager = "投资经理";
	public static final String FIELD_minSubAmount = "最低认购金额";
	public static final String FIELD_lockUpPeriod = "资金封闭期";
	public static final String FIELD_openDate = "开放日";
	public static final String FIELD_valuationDate = "估值日";
	public static final String FIELD_minAddAmount = "最低追加金额";
	public static final String FIELD_addDeadline = "追加资金到账日";
	public static final String FIELD_addAppDeadline = "提交追加申请截止日";
	public static final String FIELD_duration = "存续期";
	public static final String FIELD_subFee = "认购费率";
	public static final String FIELD_managerFee = "年管理费率";
	public static final String FIELD_redemptionFee = "赎回费率";
	public static final String FIELD_redemptionAppDeadline = "赎回申请截止日";
	public static final String FIELD_floatManagerFee = "浮动管理费";
	public static final String FIELD_trustDividend = "信托分红";
	public static final String FIELD_roadshowPeriod = "推介期";
	public static final String FIELD_establishDate = "成立日";
	public static final String FIELD_subAccountName = "认购帐户户名";
	public static final String FIELD_subAccountBank = "认购帐户开户行";
	public static final String FIELD_subAccount = "认购帐户帐号";
	public static final String FIELD_purchaseState = "产品属性";
	public static final String FIELD_top = "是否推荐";
	public static final String FIELD_isValid = "是否有效";
	public static final String FIELD_isCunxu = "是否存续";
	public static final String FIELD_weight = "权重";
	public static final String FIELD_receiverList = "净值收件人";

    /**
     * 获取全部可用产品
     * @return
     * @author nerve
     * @since 2013-10-15上午10:50:05
     */
    public List<Product> getAllEnabledProduct();

    /**
     * 获取首页推荐产品
     * @param count
     * @return
     * @author nerve
     * @since 2013-10-15上午10:49:45
     */
    public List<Product> getRecommendProduct();

    /**
     * 获取产品列表按照权重排序
     * @param count
     * @return
     * @author xy
     * @since 2014-01-28
     * 
     */
    public List<Product> getProductByWeight();

    public List<Product> findProductName();
    
    public List<Product> getProductByProductCategories();
    
    public List<Product> findByProductCategoriess();
    
    public List<Product> findByProductAggressive();
    
    public List<Product> findByProductAggressives();
    
    //灵活型
    public List<Product> findByProductFlexible();

	public Object getProductByName(String value);

	public Product getProductByXuntouName(String trim);

	public int getProductByIsValid(int i);

	public Product getProductByFullName(String value);
	public Product getProductByAnyName(String value);
	public List<Product> getProductListByAnyName(String value);
	
	public void uploadProduct(HSSFWorkbook hssfWorkbook,Admin loginAdmin);
}