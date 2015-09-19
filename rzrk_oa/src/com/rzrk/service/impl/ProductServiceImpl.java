package com.rzrk.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;

import com.rzrk.Constants;
import com.rzrk.common.CheckInfo;
import com.rzrk.common.Dictionary;
import com.rzrk.common.DictionaryItem;
import com.rzrk.dao.ProductDao;
import com.rzrk.entity.Admin;
import com.rzrk.entity.Product;
import com.rzrk.exception.PersonalException;
import com.rzrk.service.DictionaryService;
import com.rzrk.service.ProductService;

/**
 * Service实现类 - 产品
 * @version $Id$
 * @author nerve
 * @since 2013-9-25 下午1:56:28
 */
@Service("productServiceImpl")
public class ProductServiceImpl extends BaseServiceImpl<Product, String> implements ProductService {
	
    @Resource(name = "productDaoImpl")
    private ProductDao productDao;
    @Resource
    private DictionaryService dictionaryService;
    
    @Override
    public List<Product> getAllEnabledProduct() {
        ListOrderedMap params = new ListOrderedMap();
        params.put("isEnabled", Constants.YES_STR);
        // params.put("weight", Constants.YES);
        return productDao.findByConditions(params);
    }

    @Override
    public List<Product> getRecommendProduct() {
        ListOrderedMap params = new ListOrderedMap();
        params.put("isEnabled", Constants.YES_STR);
        params.put("top", Constants.YES_STR);
        params.put("order by", "weight desc");
        return productDao.findByConditions(params);
    }

    @Resource(name = "productDaoImpl")
    public void setBaseDao(ProductDao dao) {
        super.setBaseDao(dao);
    }

    @Override
    public List<Product> findProductName() {
    	List<Product> productList = productDao.findProductName();
        return productList;
    }

    @Override
    public List<Product> getProductByWeight() {
        ListOrderedMap params = new ListOrderedMap();
        params.put("isEnabled", Constants.YES_STR);
        params.put("order by", "nameShort asc");
        return productDao.findByConditions(params);
    }

	@Override
	public List<Product> getProductByProductCategories() {
		ListOrderedMap params = new ListOrderedMap();
        params.put("isEnabled", Constants.YES_STR);
        params.put("order by", "nameShort asc");
        return productDao.findByProductCategories(params);
	}

	@Override
	public List<Product> findByProductAggressive() {
		ListOrderedMap params = new ListOrderedMap();
        params.put("isEnabled", Constants.YES_STR);
        params.put("order by", "nameShort asc");
        return productDao.findByProductAggressive(params);
        }

	@Override
	public List<Product> findByProductAggressives() {
		ListOrderedMap params = new ListOrderedMap();
        params.put("isEnabled", Constants.YES_STR);
        params.put("order by", "nameShort asc");
        return productDao.findByProductAggressives(params);
	}

	@Override
	public List<Product> findByProductCategoriess() {
		ListOrderedMap params = new ListOrderedMap();
        params.put("isEnabled", Constants.YES_STR);
        params.put("order by", "nameShort asc");
        return productDao.findByProductCategoriess(params);
	}
    //灵活型
	@Override
	public List<Product> findByProductFlexible() {
		ListOrderedMap params = new ListOrderedMap();
        params.put("isEnabled", Constants.YES_STR);
        params.put("order by", "nameShort asc");
        return productDao.findByProductFlexible(params);
	}

	@Override
	public Object getProductByName(String value) {
		return productDao.findByProductHistoryName(value);
	}

	@Override
	public Product getProductByAnyName(String value) {
		return productDao.getProductByAnyName(value);
	}

	@Override
	public Product getProductByFullName(String value) {
		return productDao.getProductByFullName(value);
	}

	@Override
	public List<Product> getProductListByAnyName(String value) {
		return productDao.getProductListByAnyName(value);
	}

	@Override
	public Product getProductByXuntouName(String trim) {
		return productDao.getProductByXuntouName(trim);
	}

	@Override
	public int getProductByIsValid(int i) {
		return productDao.getProductByIsValid(i);
	}
	
	private boolean checkNullRow(HSSFRow row,int len){
		for(int i=0;i<len;i++){
			HSSFCell _cell = row.getCell(i);
			String _cellStr = com.rzrk.util.StringUtils.parseExcel(_cell);
			if(!StringUtils.isEmpty(_cellStr)){
				return false;
			}
		}
		return true;
	}
	@PostConstruct
	public void initDictionary(){
		
		dictionaryService.addDictionary(new Dictionary(ProductService.DICT_TYPE, "信托类型")
		.addDictionaryItems(new DictionaryItem("-1", "未指定",true))
		.addDictionaryItems(new DictionaryItem("100", "开放型",false))
		.addDictionaryItems(new DictionaryItem("101", "证券信托",false))
		.addDictionaryItems(new DictionaryItem("102", "证券投资集合资金信托",false))
			);
		
		dictionaryService.addDictionary(new Dictionary(ProductService.DICT_CATEGRORIES, "产品类型")
		.addDictionaryItems(new DictionaryItem("0", "对冲类",false))
		.addDictionaryItems(new DictionaryItem("1", "其它",true))
			);
		
		dictionaryService.addDictionary(new Dictionary(ProductService.DICT_AGGRESSIVE, "子类型")
		.addDictionaryItems(new DictionaryItem("0", "进取型",true))
		.addDictionaryItems(new DictionaryItem("1", "稳健型",false))
		.addDictionaryItems(new DictionaryItem("2", "灵活型",false))
			);
		
		dictionaryService.addDictionary(new Dictionary(ProductService.DICT_ISENABLED, "是否启用")
		.addDictionaryItems(new DictionaryItem("1", "启用",true))
		.addDictionaryItems(new DictionaryItem("0", "禁用",false))
			);
		
		dictionaryService.addDictionary(new Dictionary(ProductService.DICT_SHOWHS300, "净值走势是否显示HS300对比走势")
		.addDictionaryItems(new DictionaryItem("1", "显示",true))
		.addDictionaryItems(new DictionaryItem("0", "不显示",false))
			);
		
		dictionaryService.addDictionary(new Dictionary(ProductService.DICT_PURCHASESTATE, "产品属性")
		.addDictionaryItems(new DictionaryItem("1", "开放",true))
		.addDictionaryItems(new DictionaryItem("2", "封闭",false))
			);
		
		dictionaryService.addDictionary(new Dictionary(ProductService.DICT_TOP, "是否推荐")
		.addDictionaryItems(new DictionaryItem("0", "否",true))
		.addDictionaryItems(new DictionaryItem("1", "是",false))
			);
		
		dictionaryService.addDictionary(new Dictionary(ProductService.DICT_ISVALID, "是否有效")
		.addDictionaryItems(new DictionaryItem("0", "否",true))
		.addDictionaryItems(new DictionaryItem("1", "是",false))
			);
		
		dictionaryService.addDictionary(new Dictionary(ProductService.DICT_ISCUNXU, "是否存续")
		.addDictionaryItems(new DictionaryItem("0", "否",true))
		.addDictionaryItems(new DictionaryItem("1", "是",false))
			);

	}
	
	
	@Override
	public void uploadProduct(HSSFWorkbook hssfWorkbook,Admin loginAdmin){

		
		
		CheckInfo parseInfo = new CheckInfo();
		HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
all:	do{
			if(hssfSheet==null){
				parseInfo.addError("sheet 为空");
				break all;
			}
			HSSFRow titleRow = hssfSheet.getRow(0);
			if(titleRow==null){
				parseInfo.addError("标题栏 为空");
				break all;
			}
			ArrayList<String> titleList = new ArrayList<String>();
			for(int i = 0;; i++){
				HSSFCell cell = titleRow.getCell(i);
				String title = com.rzrk.util.StringUtils.parseExcel(cell);
				if(StringUtils.isEmpty(title)){
					break;
				}else{
					titleList.add(title);
				}
			}
			if(titleList.size()==0){
				parseInfo.addError("标题栏 为空");
				break all;
			}
			int rowLastNum = hssfSheet.getLastRowNum();
			if(!parseInfo.isError()){
				ArrayList<String> tmpErrorList = new ArrayList<String>();
				boolean tailnull = true; //持续尾巴空行
				HashSet<String> indentifySet = new HashSet<String>();
				for(int j=rowLastNum;j>=1;j--){
					HSSFRow valueRow = hssfSheet.getRow(j);
					HSSFCell identifyCell = valueRow.getCell(0);
					String identifyStr = com.rzrk.util.StringUtils.parseExcel(identifyCell);
					if(StringUtils.isEmpty(identifyStr)){
						if(tailnull){
							if(checkNullRow(valueRow,titleList.size())){
								//TODO 发现空行
								rowLastNum--;
							}else{
								tailnull=false;
								tmpErrorList.add("第"+(j+1)+"行  主键不能为空 ");
							}
						}else{
							tmpErrorList.add("第"+(j+1)+"行  主键不能为空 ");
						}
						continue;
					}else{
						tailnull=false;
					}
					if(indentifySet.contains(identifyStr)){
						tmpErrorList.add("第"+(j+1)+"行 "+identifyStr+" 重复");
					}else{
						indentifySet.add(identifyStr);
					}
				}
				for(int y = tmpErrorList.size()-1;y>=0;y--){
					parseInfo.addError(tmpErrorList.get(y));
				}
			}
			
			if(!parseInfo.isError()){
				for(int j=1;j<=rowLastNum;j++){
					HSSFRow valueRow = hssfSheet.getRow(j);
					LinkedHashMap<String, String> entity = new LinkedHashMap<String, String>();
					for(int index=0;index<titleList.size();index++){
						String key = titleList.get(index);
						entity.put(key, com.rzrk.util.StringUtils.parseExcel(valueRow.getCell(index)));
					}
					if(StringUtils.isBlank(entity.get("全称"))){
						parseInfo.addError(j+"行  全称未填");
						break all;
					}else if(StringUtils.isBlank(entity.get("讯投别名"))){
						
					}
					Product product = getProductByFullName(entity.get(FIELD_name));
					if(product!=null){
						parseInfo.addError( entity.get(FIELD_name) +"已存在");
						break all;
					}else{
						product = new Product();
						product.setName(entity.get(FIELD_name));
						product.setXuntouName(entity.get(FIELD_xuntouName));
						product.setHistoryName(entity.get(FIELD_historyName));
						product.setNameShort(entity.get(FIELD_nameShort));
						product.setNameSShort(entity.get(FIELD_nameSShort));
						product.setType(Integer.parseInt(dictionaryService.findDictionaryByKey(DICT_TYPE).findByTextWithDefault(entity.get(FIELD_nameSShort)).getKey()));
						product.setProduct_categories(Integer.parseInt(dictionaryService.findDictionaryByKey(DICT_CATEGRORIES).findByTextWithDefault(entity.get(FIELD_product_categories)).getKey()));
						product.setAggressive(Integer.parseInt(dictionaryService.findDictionaryByKey(DICT_AGGRESSIVE).findByTextWithDefault(entity.get(FIELD_aggressive)).getKey()));
						product.setIsEnabled(Integer.parseInt(dictionaryService.findDictionaryByKey(DICT_ISENABLED).findByTextWithDefault(entity.get(FIELD_isEnabled)).getKey()));
						product.setShowHS300(Integer.parseInt(dictionaryService.findDictionaryByKey(DICT_SHOWHS300).findByTextWithDefault(entity.get(FIELD_showHS300)).getKey()));
						product.setInvestRange(entity.get(FIELD_investRange));
						product.setParValue(entity.get(FIELD_parValue));
						product.setSupervisionOrganization(entity.get(FIELD_supervisionOrganization));
						product.setTrustee(entity.get(FIELD_trustee));
						product.setCustodianBank(entity.get(FIELD_custodianBank));
						product.setBroker(entity.get(FIELD_broker));
						product.setFuturesBroker(entity.get(FIELD_futuresBroker));
						product.setInvestConsultant(entity.get(FIELD_investConsultant));
						product.setInvestManager(entity.get(FIELD_investManager));
						product.setMinSubAmount(entity.get(FIELD_minSubAmount));
						product.setLockUpPeriod(entity.get(FIELD_lockUpPeriod));
						product.setOpenDate(entity.get(FIELD_openDate));
						product.setValuationDate(entity.get(FIELD_valuationDate));
						product.setMinAddAmount(entity.get(FIELD_minAddAmount));
						product.setAddDeadline(entity.get(FIELD_addDeadline));
						product.setAddAppDeadline(entity.get(FIELD_addAppDeadline));
						product.setDuration(entity.get(FIELD_duration));
						product.setSubFee(entity.get(FIELD_subFee));
						product.setManagerFee(entity.get(FIELD_managerFee));
						product.setRedemptionFee(entity.get(FIELD_redemptionFee));
						product.setRedemptionAppDeadline(entity.get(FIELD_redemptionAppDeadline));
						product.setFloatManagerFee(entity.get(FIELD_floatManagerFee));
						product.setTrustDividend(entity.get(FIELD_trustDividend));
						product.setRoadshowPeriod(entity.get(FIELD_roadshowPeriod));
						product.setEstablishDate(entity.get(FIELD_establishDate));
						product.setSubAccountName(entity.get(FIELD_subAccountName));
						product.setSubAccountBank(entity.get(FIELD_subAccountBank));
						product.setSubAccount(entity.get(FIELD_subAccount));
						product.setPurchaseState(Integer.parseInt(dictionaryService.findDictionaryByKey(DICT_PURCHASESTATE).findByTextWithDefault(entity.get(FIELD_purchaseState)).getKey()));
						product.setTop(Integer.parseInt(dictionaryService.findDictionaryByKey(DICT_TOP).findByTextWithDefault(entity.get(FIELD_top)).getKey()));
						product.setIsValid(Integer.parseInt(dictionaryService.findDictionaryByKey(DICT_ISVALID).findByTextWithDefault(entity.get(FIELD_isValid)).getKey()));
						product.setIsCunxu(dictionaryService.findDictionaryByKey(DICT_ISCUNXU).findByTextWithDefault(entity.get(FIELD_isCunxu)).getKey());
						product.setWeight(NumberUtils.toInt(entity.get(FIELD_weight),0));
						product.setReceiverList(entity.get(FIELD_receiverList));
						save(product);
					}
				}
			}

		}while(false);
		
		if(parseInfo.isError()){
			throw new PersonalException(parseInfo.errorMessage());
		}

	}


}