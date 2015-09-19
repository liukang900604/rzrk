package com.rzrk.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.ibatis.exceptions.PersistenceException;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.security.ui.preauth.PreAuthenticatedCredentialsNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.rzrk.common.product.ExtractDateManager;
import com.rzrk.contants.CategoryContants;
import com.rzrk.dao.AdminDao;
import com.rzrk.dao.ContractCategoryDao;
import com.rzrk.dao.ContractDao;
import com.rzrk.dao.ProductDailyRecordDao;
import com.rzrk.dao.ProductDao;
import com.rzrk.dao.ProductNetValueDao;
import com.rzrk.dao.ProductRecordDao;
import com.rzrk.dao.ProjectDao;
import com.rzrk.entity.Admin;
import com.rzrk.entity.ContractField;
import com.rzrk.entity.Product;
import com.rzrk.entity.ProductDailyRecord;
import com.rzrk.entity.ProductNetValue;
import com.rzrk.entity.ProductRecord;
import com.rzrk.entity.Project;
import com.rzrk.entity.Role;
import com.rzrk.entity.UserPlanLog;
import com.rzrk.service.AdminService;
import com.rzrk.service.DeparmentService;
import com.rzrk.service.MailService;
import com.rzrk.service.ProductRecordService;
import com.rzrk.service.ProjectService;
import com.rzrk.service.RoleService;
import com.rzrk.service.UserPlanLogService;
import com.rzrk.util.DateUtil;
import com.rzrk.util.GenerateUtil;
import com.rzrk.vo.contract.ContractEntity;

@Service("productRecordServiceImpl")
public class ProductRecordServiceImpl extends BaseServiceImpl<ProductRecord, String> implements ProductRecordService{
	
	@Resource(name = "productRecordDaoImpl")
	private ProductRecordDao productRecordDao;
	
	@Resource(name = "productRecordDaoImpl")
	public void setBaseDao(ProductRecordDao ProductRecordDao) {
		super.setBaseDao(ProductRecordDao);
	}
	@Resource
	private ContractCategoryDao contractCategoryDao;
	@Resource
	private ContractDao contractDao;
	@Resource
	private ProductDao productDao;
	@Resource
	private ProductDailyRecordDao productDailyRecordDao;
	@Resource(name="mailServiceImpl")
	private MailService mailService;
	@Resource
	private ExtractDateManager extractDateManager;
	@Resource
	private AdminDao adminDao;

	
	
	public void disposeAll(){
		List<ContractField> rowList = contractDao.getContractPrimaryRows(CategoryContants.PRODUCT_DETAIL_CATEGORY_ID);
		List<String> emailContentList = new ArrayList<String>();
		for(ContractField row : rowList){
			dispose(row.getId(),emailContentList);
		}
		System.out.println(StringUtils.join(emailContentList,"\r\n"));
		//TODO 发送邮件
//		mailService.sendMail("请提交日净值", StringUtils.join(emailContentList,"\r\n"), "");
	}
	
	
	
	
	public void dispose(String productDetailId,List<String> emailContentList){
		Admin admin = adminDao.getAdminByUsername("admin");
		Date today = new Date();
		LinkedHashMap<String, String> productDetail = (LinkedHashMap<String, String>) contractDao.getFieldList(CategoryContants.PRODUCT_DETAIL_CATEGORY_ID, productDetailId).get("entity");
		Product product = productDao.unique(DetachedCriteria.forClass(Product.class).add(Restrictions.eq("xuntouName", productDetail.get("产品名称"))));
		//TODO 暂时不报错
		//Assert.notNull(product, "无此产品："+productDetail.get("产品名称"));
		if(product==null)return;
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ProductRecord.class)
				.add(Restrictions.eq("productDetailId", productDetailId))
				.addOrder(Order.asc("extractIndex"));
		List<ProductRecord> productRecordList = new ArrayList<ProductRecord>(productRecordDao.find(detachedCriteria));
		if(productRecordList.size()==0){
			ProductRecord _productRecord = new ProductRecord();
			_productRecord.setProductDetailId(productDetailId);
			_productRecord.setProductName(product.getXuntouName());
			productRecordDao.save(_productRecord);
			productRecordList.add(_productRecord);
		}
		
		for(int extractIndex=0;;extractIndex++){
			if(extractIndex >= productRecordList.size())break;
			ProductRecord productRecord = productRecordList.get(extractIndex);
			if(!productRecord.isWriteBill()){
				java.sql.Date extractDate = productRecord.getExtractDate();
				if(extractDate==null){
					String startDateStr = (String) productDetail.get("成立日期");
					Date startDate;
					try {
						startDate = DateUtils.parseDate(startDateStr, new String[]{"yyyy-MM-dd","yyyy/MM/dd"});
						//TODO 先都使用年封季开
						extractDate = extractDateManager.getExtractDate("年封季开", new java.sql.Date(startDate.getTime()), extractIndex);
						Assert.notNull(extractDate, "计算计提时间失败："+startDateStr);
						productRecord.setExtractDate(extractDate);
					} catch (ParseException e) {
						throw new PersistenceException(startDateStr+" 解析失败");
					}
				}
				if(DateUtils.truncatedCompareTo(today,extractDate,  Calendar.DAY_OF_MONTH)>=0){
					// 查询净值
					ProductDailyRecord productDailyRecord = productDailyRecordDao.unique(DetachedCriteria.forClass(ProductDailyRecord.class)
							.add(Restrictions.eq("productId", product.getId()))
							.add(Restrictions.eq("recordDate", DateUtil.getDate(productRecord.getExtractDate())))
									);
					
					if(productDailyRecord==null){
						if(!productRecord.isSendEmail()){
							emailContentList.add(productDetail.get("产品名称")+" "+DateUtil.getDate(extractDate));
							productRecord.setSendEmail(true);
						}
						break;
					}else{
						double nac = productDailyRecord.getBeforeReduceNetValue();
						double maxNav = getMaxNav(productRecordList, extractIndex);
						double receivable = calculateEarn(maxNav,nac,productDailyRecord.getAmount(),NumberUtils.toDouble(productDetail.get("管理费（固定）")));
						double preNav = getPreNav(productRecordList, extractIndex);
						double nav = calcuNav(nac,preNav,NumberUtils.toDouble(productDetail.get("管理费（固定）")));
						ContractEntity<String, String> entity = new ContractEntity<String, String>();
	      				String primary = GenerateUtil.createPrimary(CategoryContants.RECEIVABLES_CATEGORY_ID);
						entity.put("编号", primary);
						entity.put("产品名称", productDetail.get("产品名称"));
						entity.put("收款日期", DateUtil.getDate(DateUtils.addDays(extractDate, 1)));
						entity.put("应收款项", receivable+"");
						entity.put("代销机构", productDetail.get("代销机构"));
						entity.put("资产管理", productDetail.get("资产管理"));
						contractDao.save(null,null,CategoryContants.RECEIVABLES_CATEGORY_ID, entity,admin , false);
						productRecord.setNac(nac);
						productRecord.setNav(nav);
						productRecord.setWriteBill(true);
					}
				}else{
					break;
				}
			}
			if((extractIndex+1) == productRecordList.size()){
				ProductRecord _productRecord = new ProductRecord();
				_productRecord.setExtractIndex(extractIndex+1);
				_productRecord.setProductDetailId(productDetailId);
				_productRecord.setProductName(product.getXuntouName());
				productRecordDao.save(_productRecord);
				productRecordList.add(_productRecord);
			}
		}
		
	}
	
	
	/**
	 * 计算提成
	 * @param maxNav, 通过sql select max可以得到
	 * @param nac,从净值列表读取
	 * @param totalAmount,从产品要素读取
	 * @param investRate, 从产品要素读取
	 * @return 提成
	 * @author songkez
	 * @since  2015-7-24 
	 */
	private double calculateEarn(double maxNav, double nac, double totalAmount, double investRate){
		if((nac - maxNav) <= 0){
			return 0.0;
		}
		return (nac - maxNav) * totalAmount * investRate;
	}
	/**
	 * 计算nav
	 * @param nac
	 * @param preNav 上一次提取的nav
	 * @param investRate 业绩报酬费率
	 * @return
	 */
	private double calcuNav(double nac,double preNav,double investRate){
		return nac - (nac -preNav)*investRate;
	}
	
	private double getMaxNav(List<ProductRecord> productRecordList, int n){
		if(n==0){
			return 1.0;
		}else{
			double max=0.0;
			for(int i=0;i<n;i++){
				ProductRecord pr = productRecordList.get(i);
				if(pr.getNav() > max){
					max = pr.getNav();
				}
			}
			return max;
		}
	}
	
	private double getPreNav(List<ProductRecord> productRecordList, int n){
		double navPre = 1.0;
		if(n>0){
			navPre = productRecordList.get(n-1).getNav();
		}
		return navPre;
	}

}
