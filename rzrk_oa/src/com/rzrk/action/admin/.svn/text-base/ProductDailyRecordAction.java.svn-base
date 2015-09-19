/**
 * Project Name: rzrk Web
 * Confidential and Proprietary                                                                
 * Copyright (C) 2013 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.action.admin;



import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import weibo4j.org.json.JSONArray;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.rzrk.bean.Pager.Order;
import com.rzrk.bean.ProductDailyRecordBean;
import com.rzrk.entity.Product;
import com.rzrk.entity.ProductDailyRecord;
import com.rzrk.entity.ProductNetValueMailReciever;
import com.rzrk.service.ProductDailyRecordService;
import com.rzrk.service.ProductNetValueMailRecieverService;
import com.rzrk.service.ProductService;
import com.rzrk.util.DateUtil;
import com.rzrk.util.date.DateUtils;

/**
 * 后台Action类 - 每日净值记录
 */

@ParentPackage("admin")
public class ProductDailyRecordAction extends BaseAdminAction {

	private static final long serialVersionUID = -6296393115930477663L;
	
	private ProductDailyRecord pdr;
	private List<ProductDailyRecord> PdrList;
	private List<Product> productList;
	private ProductNetValueMailReciever pnmr;
	
	public ProductNetValueMailReciever getPnmr() {
		return pnmr;
	}


	public void setPnmr(ProductNetValueMailReciever pnmr) {
		this.pnmr = pnmr;
	}


	public List<Product> getProductList() {
		return productList;
	}


	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}


	public ProductDailyRecord getPdr() {
		return pdr;
	}


	public void setPdr(ProductDailyRecord pdr) {
		this.pdr = pdr;
	}


	public List<ProductDailyRecord> getPdrList() {
		return PdrList;
	}


	public void setPdrList(List<ProductDailyRecord> pdrList) {
		PdrList = pdrList;
	}


	public ProductDailyRecordService getProductDailyRecordService() {
		return productDailyRecordService;
	}


	public void setProductDailyRecordService(
			ProductDailyRecordService productDailyRecordService) {
		this.productDailyRecordService = productDailyRecordService;
	}

	@Resource(name = "productDailyRecordServiceImpl")
	private ProductDailyRecordService productDailyRecordService;
	
    @Resource(name = "productServiceImpl")
    private ProductService productService;
    
	@Resource(name = "productNetValueMailRecieverServiceImpl")
	private ProductNetValueMailRecieverService productNetValueMailRecieverService;

	// 列表
	public String list() {		
		return LIST;
	}
	
	
	public String getAjaxList(){
		
		processAjaxPagerRequestParameter();
		String newestDate = productDailyRecordService.getNewestDate();
		if ((pager.getKeyword() == null || pager.getKeyword().trim().length() == 0 ) && newestDate != null){
			pager.setSearchBy("recordDate");
			pager.setKeyword(newestDate);
		}
		
		if ((pager.getOrderBy() == null || pager.getOrderBy().trim().length() == 0 )){
			pager.setOrderBy("recordDate");
			pager.setOrder(Order.desc);
		}
		
		pager = productDailyRecordService.findPager(pager);
//		pager.setOrderBy("productName");
//		pager.setOrder(Order.desc);
		
		List<ProductDailyRecord> prdTempList = (List<ProductDailyRecord>) pager.getResult();
		double stockAccountTotalAmount=0;// 证券账户总资产
		double futureAccountTotalAmount=0;//期货账户总资产
		double stockMarketValue=0;//股票总市值
		double futureMarketVaue=0;//期货保证金
		double futureEmptyValue=0;//空单市值 
		double sum=0;//合计
		double bankAmount=0;//银行存款
		double total=0;//总资产
		double beforeReduce=0;//未扣除各项费用总资产
		double amount=0;//份额
		
		for (int i=0; i < prdTempList.size();i++){
			
			ProductDailyRecord temp = prdTempList.get(i);
			stockAccountTotalAmount=stockAccountTotalAmount + temp.getStockAccountTotalAmount();// 证券账户总资产
			futureAccountTotalAmount=futureAccountTotalAmount + temp.getFutureAccountTotalAmount();//期货账户总资产
			stockMarketValue=stockMarketValue + temp.getStockMarketValue();//股票总市值
			futureMarketVaue=futureMarketVaue + temp.getFutureMarketVaue();//期货保证金
			futureEmptyValue=futureEmptyValue + temp.getFutureEmptyValue();//空单市值 
			sum=sum + temp.getSum();//合计
			bankAmount=bankAmount + temp.getBankAmount();//银行存款
			total=total + temp.getTotal();//总资产
			beforeReduce=beforeReduce + temp.getBeforeReduce();//未扣除各项费用总资产
			amount=amount + temp.getAmount();//份额
		}
		ProductDailyRecord totalTemp = new ProductDailyRecord();
		totalTemp.setStockAccountTotalAmount(stockAccountTotalAmount);
		totalTemp.setFutureAccountTotalAmount(futureAccountTotalAmount);
		totalTemp.setStockMarketValue(stockMarketValue);
		totalTemp.setFutureMarketVaue(futureMarketVaue);
		totalTemp.setFutureEmptyValue(futureEmptyValue);
		totalTemp.setSum(sum);
		totalTemp.setBankAmount(bankAmount);
		totalTemp.setTotal(total);
		totalTemp.setBeforeReduce(beforeReduce);
		totalTemp.setAmount(amount);
		
		
		net.sf.json.JSONArray jsonObjTotal = new net.sf.json.JSONArray();
        Map<String,Object> mapTotal = new HashMap<String,Object>(); 
        
        mapTotal.put("productName", totalTemp.getProductName());
        mapTotal.put("stockAccountTotalAmount", totalTemp.getStockAccountTotalAmount());
        mapTotal.put("futureAccountTotalAmount",totalTemp.getFutureAccountTotalAmount());
        mapTotal.put("stockMarketValue", totalTemp.getStockMarketValue());
        mapTotal.put("futureEmptyValue", totalTemp.getFutureEmptyValue());
        mapTotal.put("sum", totalTemp.getSum());
        mapTotal.put("bankAmount", totalTemp.getBankAmount());
        mapTotal.put("total", totalTemp.getTotal());
        mapTotal.put("beforeReduce", totalTemp.getBeforeReduce());
        mapTotal.put("amout", totalTemp.getAmount());
        jsonObjTotal.add(mapTotal);
			
		
		net.sf.json.JSONArray jsonObj = new net.sf.json.JSONArray();
		for(int i = 0; i < prdTempList.size(); i++ ){
			ProductDailyRecord temp = prdTempList.get(i);
	        Map<String,Object> map = new HashMap<String,Object>(); 
	        
	        map.put("id", temp.getId()); 
	        map.put("productId", temp.getProductId()); 
	        map.put("productName", temp.getProductName());
	        map.put("stockAccountTotalAmount", temp.getStockAccountTotalAmount());
	        map.put("futureAccountTotalAmount",temp.getFutureAccountTotalAmount());
	        map.put("stockMarketValue", temp.getStockMarketValue());
	        map.put("futureEmptyValue", temp.getFutureEmptyValue());
	        map.put("sum", temp.getSum());
	        map.put("bankAmount", temp.getBankAmount());
	        map.put("total", temp.getTotal());
	        map.put("beforeReduce", temp.getBeforeReduce());
	        map.put("reduceNetValue", temp.getReduceNetValue());
	        map.put("beforeReduceNetValue", temp.getBeforeReduceNetValue());
	        map.put("assetsNetValue", temp.getAssetsNetValue());
	        map.put("recordDate", temp.getRecordDate());
	        map.put("riskRate", temp.getRiskRate());
	        map.put("amount", temp.getAmount());
	        map.put("repay", temp.getRepay());
	        map.put("buy", temp.getBuy());
	        jsonObj.add(map);
		}
        
        Map<String,Object> map = new HashMap<String,Object>(); 
        
        map.put("total", pager.getTotalCount()); 
        map.put("rows", jsonObj); 
        map.put("footer", jsonObjTotal);
        
		return ajax(map);
		
	}
	
	@InputConfig(resultName = "error")
	public String savemail() {
		Product product = productService.get(pnmr.getProductId());
		pnmr.setProductName(product.getName());
		productNetValueMailRecieverService.save(pnmr);
		redirectUrl = "product_daily_record!mailList.action";
		return SUCCESS;
	}
	
	// 删除
	public String deletemail() {
		for (String id : ids) {
			ProductNetValueMailReciever p = productNetValueMailRecieverService.load(id);
			productNetValueMailRecieverService.delete(p);
		}
		return ajax(Status.success, "删除成功!");
	}
	
	
	// 更新
	@InputConfig(resultName = "error")
	public String updatemail() {
		Product product = productService.get(pnmr.getProductId());
		pnmr.setProductName(product.getName());
		ProductNetValueMailReciever persistent = productNetValueMailRecieverService.load(id);
		BeanUtils.copyProperties(pnmr, persistent, new String[] {"id", "createDate", "modifyDate"});
		productNetValueMailRecieverService.update(persistent);
		redirectUrl = "product_daily_record!mailList.action";
		return SUCCESS;
	}
	
    // 添加
    public String add() {
        getRequest().setAttribute("command", "添加");
        productList = productDailyRecordService.getAllProduct();
        getRequest().setAttribute("productId", getRequest().getParameter("productId"));
        return INPUT;
    }
    
    
	@InputConfig(resultName = "error")
	public String save() {
		Product product = productService.get(pdr.getProductId());
		pdr.setProductXuntouName(product.getXuntouName());

		
        pdr.getRecordDate();
		
        Product productPst = (Product) productService.get(pdr.getProductId());
        pdr.setProductName(product.getHistoryName());
        if(null == productPst){
        	return ajax(Status.error,"选择的产品不存在");
        }
        pdr.setProductId(product.getId());
        
        ProductDailyRecord pdrTemp = productDailyRecordService.getPdrByProductIdAndDate(product.getId(), pdr.getRecordDate());
        if (null != pdrTemp){
        	return ajax(Status.error,"已添加，请直接编辑");
        }
        
		productDailyRecordService.save(pdr);
		redirectUrl = "product_daily_record!list.action";
		return ajax(Status.success,"保存成功！");
	}

    // 编辑
    public String edit() {
        getRequest().setAttribute("command", "编辑");
        pdr = productDailyRecordService.load(id);
        return INPUT;
    }
    
    // 编辑
    public String editmail() {
        getRequest().setAttribute("command", "编辑");
        productList = productDailyRecordService.getAllProduct();
        pnmr = productNetValueMailRecieverService.load(id);
        return "mailinput";
    }
    
	// 列表
	public String mailList() {
		pager = productNetValueMailRecieverService.findPager(pager);
		return "mailList";
	}
	
	public String addMailReceiver(){
        getRequest().setAttribute("command", "添加");
        productList = productDailyRecordService.getAllProduct();
        return "mailinput";
	}
	
	
    // 保存修改
    @InputConfig(resultName = "error")
    public String update() {
        try {
            ProductDailyRecord persistent = productDailyRecordService.get(id);
            BeanUtils.copyProperties(pdr, persistent,  new String[] {"id", "createDate", "modifyDate", "productName", "recordDate", "remark", "productXuntouName", "productId", "riskRate"});
            productDailyRecordService.update(persistent);
            redirectUrl = "product_daily_record!list.action";
            return ajax(Status.success,"保存成功！");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            logger.error(String.format("更新产品ID=[%s]异常!", id));
            return ajax(Status.error,"保存成功！");
        }
    }
	
	// 删除
	public String delete() {
		StringBuffer logInfoStringBuffer = new StringBuffer("删除净值: ");
		for (String id : ids) {
			ProductDailyRecord temp = productDailyRecordService.load(id);
			productDailyRecordService.delete(temp);
			logInfoStringBuffer.append(temp.getRecordDate() + " ");
		}
		logInfo = logInfoStringBuffer.toString();
		return ajax(Status.success, "删除成功!");
	}
	
	 /**
     * 产品详情页走势图
     * @return
     * @author nerve
	 * @throws Exception 
     * @since 2013-10-15下午3:46:22
     */
    public String trendChart() throws Exception {
            String productId = getRequest().getParameter("productId");
            List<ProductDailyRecord> pdrList = productDailyRecordService.getPdrByProductId(productId);
            
            JSONArray arr = new JSONArray();

            for (int i = 0; i < pdrList.size(); i++) {
            	ProductDailyRecord item = pdrList.get(i);
                JSONArray itemArr = new JSONArray();
                // 特殊处理by nerve 加上8小时
                Date date = DateUtil.convertStringToDate(item.getRecordDate());
                itemArr.put(date.getTime()+ 28800000);
//                if (i == 0) {
//                    // 特殊处理by nerve 设置初始值为0.001
//                    itemArr.put(0.001);
//                } else {
                    itemArr.put(item.getReduceNetValue());
//                }
                arr.put(itemArr);
            }

            getResponse().getWriter().write(arr.toString());

        return NONE;
     }
    
    
	 /**
     * 迅投接口
     * @return
     * @author nerve
	 * @throws Exception 
     * @since 2013-10-15下午3:46:22
     */
    public String trendChartForXuntou() throws Exception {
            String productName = getRequest().getParameter("productXuntou");
            String from = getRequest().getParameter("from");
            String to = getRequest().getParameter("to");
            Product product = productService.getProductByXuntouName(productName);
            List<ProductDailyRecord> pdrList = productDailyRecordService.getPdrByProductName(product.getId(), from, to);
            
            JSONArray arr = new JSONArray();
            if (null != pdrList){
            	for (int i = 0; i < pdrList.size(); i++) {
                	ProductDailyRecord item = pdrList.get(i);
                    JSONArray itemArr = new JSONArray();
                    // 特殊处理by nerve 加上8小时
                    Date date = DateUtil.convertStringToDate(item.getRecordDate());
                    itemArr.put(date.getTime()+ 28800000);
//                    if (i == 0) {
//                        // 特殊处理by nerve 设置初始值为0.001
//                        itemArr.put(0.001);
//                    } else {
                        itemArr.put(item.getReduceNetValue());
//                    }
                    arr.put(itemArr);
                }

                getResponse().getWriter().write(arr.toString());
	
            }
            
        return NONE;
     }
    
    
    
    
    
    public String trend(){
        String productId = getRequest().getParameter("productId");
        Product pdt = productService.get(productId);
        pdr = new ProductDailyRecord();
        pdr.setProductId(productId);
        pdr.setProductName(pdt.getName());
    	return "trend";
    }
    
    
    @InputConfig(resultName = "error")
    public String downloadExcel()throws Exception{
    	
    	String beginDate = getRequest().getParameter("beginDate");
    	String endDate = getRequest().getParameter("endDate");

		
		List<ProductDailyRecord> prdTempList = productDailyRecordService.getListByFromTo(beginDate, endDate);
		List<ProductDailyRecordBean> pdrbList = new ArrayList<ProductDailyRecordBean>();
		
		
		double stockAccountTotalAmount=0;// 证券账户总资产
		double futureAccountTotalAmount=0;//期货账户总资产
		double stockMarketValue=0;//股票总市值
		double futureMarketVaue=0;//期货保证金
		double futureEmptyValue=0;//空单市值 
		double sum=0;//合计
		double bankAmount=0;//银行存款
		double total=0;//总资产
		double beforeReduce=0;//未扣除各项费用总资产
		double amount=0;//份额
		
	if(prdTempList!=null && prdTempList.size()>0){
		for (int i=0; i < prdTempList.size();i++){
			
			ProductDailyRecordBean tempPdrb = new ProductDailyRecordBean();
			
			
			
			ProductDailyRecord temp = prdTempList.get(i);
			
			tempPdrb.setProductName(temp.getProductName());
			tempPdrb.setStockAccountTotalAmount(temp.getStockAccountTotalAmount());
			tempPdrb.setFutureAccountTotalAmount(temp.getFutureAccountTotalAmount());
			tempPdrb.setStockMarketValue(temp.getStockMarketValue());
			tempPdrb.setFutureMarketVaue(temp.getFutureMarketVaue());
			tempPdrb.setFutureEmptyValue(temp.getFutureEmptyValue());
			tempPdrb.setSum(temp.getSum());
			tempPdrb.setBankAmount(temp.getBankAmount());
			tempPdrb.setTotal(temp.getTotal());
			tempPdrb.setBeforeReduce(temp.getBeforeReduce());
			tempPdrb.setAmount(temp.getAmount());
			tempPdrb.setReduceNetValue(temp.getReduceNetValue());
			tempPdrb.setBeforeReduceNetValue(temp.getBeforeReduceNetValue());
		    tempPdrb.setAssetsNetValue(temp.getAssetsNetValue());
		    tempPdrb.setRecordDate(temp.getRecordDate());
		    tempPdrb.setRiskRate(temp.getRiskRate());
		    pdrbList.add(tempPdrb);
		    
			stockAccountTotalAmount=stockAccountTotalAmount + temp.getStockAccountTotalAmount();// 证券账户总资产
			futureAccountTotalAmount=futureAccountTotalAmount + temp.getFutureAccountTotalAmount();//期货账户总资产
			stockMarketValue=stockMarketValue + temp.getStockMarketValue();//股票总市值
			futureMarketVaue=futureMarketVaue + temp.getFutureMarketVaue();//期货保证金
			futureEmptyValue=futureEmptyValue + temp.getFutureEmptyValue();//空单市值 
			sum=sum + temp.getSum();//合计
			bankAmount=bankAmount + temp.getBankAmount();//银行存款
			total=total + temp.getTotal();//总资产
			beforeReduce=beforeReduce + temp.getBeforeReduce();//未扣除各项费用总资产
			amount=amount + temp.getAmount();//份额
		}
		
		ProductDailyRecord totalTemp = new ProductDailyRecord();
		totalTemp.setStockAccountTotalAmount(stockAccountTotalAmount);
		totalTemp.setFutureAccountTotalAmount(futureAccountTotalAmount);
		totalTemp.setStockMarketValue(stockMarketValue);
		totalTemp.setFutureMarketVaue(futureMarketVaue);
		totalTemp.setFutureEmptyValue(futureEmptyValue);
		totalTemp.setSum(sum);
		totalTemp.setBankAmount(bankAmount);
		totalTemp.setTotal(total);
		totalTemp.setBeforeReduce(beforeReduce);
		totalTemp.setAmount(amount);
		
		prdTempList.add(totalTemp);
		
		
		ProductDailyRecordBean tempPdrb = new ProductDailyRecordBean();
		
		tempPdrb.setStockAccountTotalAmount(stockAccountTotalAmount);
		tempPdrb.setFutureAccountTotalAmount(futureAccountTotalAmount);
		tempPdrb.setStockMarketValue(stockMarketValue);
		tempPdrb.setFutureMarketVaue(futureMarketVaue);
		tempPdrb.setFutureEmptyValue(futureEmptyValue);
		tempPdrb.setSum(sum);
		tempPdrb.setBankAmount(bankAmount);
		tempPdrb.setTotal(total);
		tempPdrb.setBeforeReduce(beforeReduce);
		tempPdrb.setAmount(amount);
		pdrbList.add(tempPdrb);
		
	}
		
		
		 // 第一步，创建一个webbook，对应一个Excel文件  
        HSSFWorkbook wb = new HSSFWorkbook();  
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
        HSSFSheet sheet = wb.createSheet("净值");  
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
        HSSFRow row = sheet.createRow((int) 0);  
        // 第四步，创建单元格，并设置值表头 设置表头居中  
        HSSFCellStyle style = wb.createCellStyle();  
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式  
        
        HSSFCell cell = row.createCell((short) 0);  
        sheet.setColumnWidth(0, 30 * 256);//设置表格宽度
        cell.setCellValue("产品名称");  
        cell.setCellStyle(style);  
        cell = row.createCell((short) 1); 
        sheet.setColumnWidth(1, 13 * 256);
        cell.setCellValue("证券账户总资产");  
        cell.setCellStyle(style);  
        cell = row.createCell((short) 2);  
        sheet.setColumnWidth(2, 13 * 256);
        cell.setCellValue("期货账户总资产");  
        cell.setCellStyle(style);  
        cell = row.createCell((short) 3);  
        sheet.setColumnWidth(3, 13 * 256);
        cell.setCellValue("股票总市值");  
        cell.setCellStyle(style);  
        cell = row.createCell((short) 4);  
        sheet.setColumnWidth(4, 13 * 256);
        cell.setCellValue("期货保证金");  
        cell.setCellStyle(style);  
        cell = row.createCell((short) 5); 
        sheet.setColumnWidth(5, 13 * 256);
        cell.setCellValue("空单市值 ");  
        cell.setCellStyle(style);  
        cell = row.createCell((short) 6);  
        sheet.setColumnWidth(6, 13 * 256);
        cell.setCellValue("合计");  
        cell.setCellStyle(style);  
        cell = row.createCell((short) 7); 
        sheet.setColumnWidth(7, 13 * 256);
        cell.setCellValue("银行存款");  
        cell.setCellStyle(style);  
        cell = row.createCell((short) 8); 
        sheet.setColumnWidth(8, 13 * 256);
        cell.setCellValue("总资产");  
        cell.setCellStyle(style);  
        cell = row.createCell((short) 9);
        sheet.setColumnWidth(9, 20 * 256);
        cell.setCellValue("未扣除各项费用总资产");  
        cell.setCellStyle(style);  
        cell = row.createCell((short) 10);
        sheet.setColumnWidth(10, 13 * 256);
        cell.setCellValue("份额");  
        cell.setCellStyle(style);  
        cell = row.createCell((short) 11);
        sheet.setColumnWidth(11, 20 * 256);
        cell.setCellValue("扣除各项费用净值");  
        cell.setCellStyle(style);  
        cell = row.createCell((short) 12);
        sheet.setColumnWidth(12, 13 * 256);
        cell.setCellValue("未扣除更像费用净值");  
        cell.setCellStyle(style);  
        cell = row.createCell((short) 13); 
        sheet.setColumnWidth(13, 13 * 256);
        cell.setCellValue("估值表净值");  
        cell.setCellStyle(style);  
        cell = row.createCell((short) 14); 
        sheet.setColumnWidth(14, 13 * 256);
        cell.setCellValue("日期");  
        cell.setCellStyle(style);  
        cell = row.createCell((short) 15); 
        sheet.setColumnWidth(15, 13 * 256);
        cell.setCellValue("风险暴露度");  
        cell.setCellStyle(style);  
        
     if(prdTempList!=null && prdTempList.size()>0){
        for (int i = 0; i < prdTempList.size(); i++)  
        {  
            row = sheet.createRow((int) i + 1);  
            ProductDailyRecordBean stu = (ProductDailyRecordBean) pdrbList.get(i);  
            // 第四步，创建单元格，并设置值  
            row.createCell((short) 0).setCellValue((stu.getProductName()));  
            row.createCell((short) 1).setCellValue((double)stu.getStockAccountTotalAmount());  
            row.createCell((short) 2).setCellValue((double) stu.getFutureAccountTotalAmount());  
            row.createCell((short) 3).setCellValue((double) stu.getStockMarketValue());  
            row.createCell((short) 4).setCellValue((double) stu.getFutureMarketVaue());  
            row.createCell((short) 5).setCellValue((double)stu.getFutureEmptyValue());  
            row.createCell((short) 6).setCellValue((double) stu.getSum());  
            row.createCell((short) 7).setCellValue((double) stu.getBankAmount());  
            row.createCell((short) 8).setCellValue((double) stu.getTotal());  
            row.createCell((short) 9).setCellValue((double)stu.getBeforeReduce());  
            row.createCell((short) 10).setCellValue((double) stu.getAmount());  
            row.createCell((short) 11).setCellValue((double) stu.getReduceNetValue());  
            row.createCell((short) 12).setCellValue((double) stu.getBeforeReduceNetValue());  
            row.createCell((short) 13).setCellValue(stu.getAssetsNetValue());  
            row.createCell((short) 14).setCellValue((stu.getRecordDate()));  
            row.createCell((short) 15).setCellValue(stu.getRiskRate());  
        }  
     }    
         String fileName=wb.getSheetAt(0).getSheetName()+"_"+DateUtils.formatDate(new Date(),"yyyyMMddHHmmss")+".xls";
         HttpServletResponse response = ServletActionContext.getResponse();
 		 response.reset();  
         response.setContentType("application/vnd.ms-excel;charset=utf-8");  
         response.setHeader("Content-Disposition", "attachment;filename="+URLEncoder.encode(fileName, "UTF-8"));
 		 wb.write(response.getOutputStream());
		 return NONE;
    }
    
    
    
  public String equity(){
	  return "jingzhi";
  }
    
 

}