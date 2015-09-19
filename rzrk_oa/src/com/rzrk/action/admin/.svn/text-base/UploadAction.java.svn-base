/**
\ * Project Name: Unicorn Online Shopping Center
 * Confidential and Proprietary
 * Copyright (C) 2011 By
 * Unicorn Development Company
 * All Rights Reserved
 */

package com.rzrk.action.admin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.rzrk.common.product.ProductRecordEngine;
import com.rzrk.entity.Product;
import com.rzrk.entity.ProductDailyRecord;
import com.rzrk.service.MailService;
import com.rzrk.service.ProductDailyRecordService;
import com.rzrk.service.ProductNetValueMailRecieverService;
import com.rzrk.service.ProductService;
import com.rzrk.util.ImageUtil;
import com.rzrk.util.RzrkLogger;

/**
 * 文件上传
 * 
 * @version 1.0
 * @author Leo
 * @since Aug 16, 2012
 */
@ParentPackage("admin")
public class UploadAction extends BaseAdminAction {

	private static final long serialVersionUID = -4041917848708433121L;
//	@Resource
//	ProductRecordEngine productRecordEngine;
	
	/** 文件对象 */
	private List<File> filedata;
	/** 文件名 */
	private List<String> filedataFileName;
	/** 文件内容类型 */
	private List<String> filedataContentType;
	
	private File singlefile;
	
	private File historyUpload;
	
	private File xuntouUpload;
	
	private String xuntouUploadFileName;
	
	private String contentType;
	
	
	private String imageFileFileName;
	
	private File imageFile;
	
	


	public String getImageFileFileName() {
		return imageFileFileName;
	}


	public void setImageFileFileName(String imageFileFileName) {
		this.imageFileFileName = imageFileFileName;
	}


	public File getImageFile() {
		return imageFile;
	}


	public void setImageFile(File imageFile) {
		this.imageFile = imageFile;
	}

	private static Map<String, List<String>> map = new HashMap<String,List<String>>();	


	public static Map<String, List<String>> getMap() {
		return map;
	}


	public static void setMap(Map<String, List<String>> map) {
		UploadAction.map = map;
	}

	@Resource(name="mailServiceImpl")
	private MailService mailService;
	
	



	public String getXuntouUploadFileName() {
		return xuntouUploadFileName;
	}


	public void setXuntouUploadFileName(String xuntouUploadFileName) {
		this.xuntouUploadFileName = xuntouUploadFileName;
	}


	public File getHistoryUpload() {
		return historyUpload;
	}


	public void setHistoryUpload(File historyUpload) {
		this.historyUpload = historyUpload;
	}


	public File getXuntouUpload() {
		return xuntouUpload;
	}


	public void setXuntouUpload(File xuntouUpload) {
		this.xuntouUpload = xuntouUpload;
	}


	public String getContentType() {
		return contentType;
	}


	public void setContentType(String contentType) {
		this.contentType = contentType;
	}


	/**
	 * Auto generated getter method
	 * @return the singlefile
	 */
	public File getSinglefile() {
		return singlefile;
	}


	/**
	 * Auto generated setter method
	 * @param singlefile the singlefile to set
	 */
	public void setSinglefile(File singlefile) {
		this.singlefile = singlefile;
	}
	
	

	public String upload() {
		try {
			if (filedata != null && filedata.size() > 0) {
				String path = ImageUtil.copyTempImageFile(
						ServletActionContext.getServletContext(),
						filedata.get(0));
				return ajax(Status.success, path);
			} else {
				return ajax(Status.error, "上传失败");
			}
		} catch (Exception e) {
			RzrkLogger.error(e);
			return ajax(Status.error, "上传失败");
		}
	}
	
	
	public String singleFileUpload(){
		if (singlefile != null) {
			if (singlefile.length() > 2 * 1024) {
				return ajax(Status.error, "图片文件大小超出限制!");
			}
			String defaultMemberImagePath = ImageUtil.copyTempImageFile(
					getServletContext(), singlefile);
			return ajax(Status.success, defaultMemberImagePath);
		} else {
			return ajax(Status.error, "图片上传失败!");
		}
	}
	
	public String imageFileUpload(){
		if (imageFile != null) {
			if (imageFile.length() > 2000 * 1024) {
				return ajax(Status.error, "图片文件大小超出限制!");
			}
			String defaultMemberImagePath = ImageUtil.copyTempImageFile(
					getServletContext(), imageFile);
			return ajax(Status.success, defaultMemberImagePath);
		} else {
			return ajax(Status.error, "图片上传失败!");
		}
	}
	
	@Resource(name = "productDailyRecordServiceImpl")
	private ProductDailyRecordService productDailyRecordService;
	
    @Resource(name = "productServiceImpl")
    private ProductService productService;
    
	@Resource(name = "productNetValueMailRecieverServiceImpl")
	private ProductNetValueMailRecieverService productNetValueMailRecieverService;
    
	public String historyExcelUpload(){
		if (historyUpload != null) {
			if (historyUpload.length() > 200 * 1024) {
				return ajax(Status.error, "文件大小超出限制!");
			}
			try {
				HSSFWorkbook hssfWorkbook = new HSSFWorkbook(new FileInputStream(historyUpload));
				//读取日期
				HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
				HSSFRow hssfRow = hssfSheet.getRow(1);
				HSSFCell dateCell = hssfRow.getCell(0);
				String date = getValue(dateCell);
				
		        ProductDailyRecord temp = null;
		        // 循环工作表Sheet
		        for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
		            hssfSheet = hssfWorkbook.getSheetAt(numSheet);
		            if (hssfSheet == null) {
		                continue;
		            }
		            // 循环行Row
		            for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
		                hssfRow = hssfSheet.getRow(rowNum);
		                if (hssfRow != null) {
		                	temp = new ProductDailyRecord();
		                	temp.setRecordDate(date);
		                    HSSFCell productName = hssfRow.getCell(2);
		                    HSSFCell stockAccountTotalAmount = hssfRow.getCell(3);
		                    HSSFCell futureAccountTotalAmount = hssfRow.getCell(4);
		                    HSSFCell stockMarketValue = hssfRow.getCell(5);
		                    HSSFCell futureEmptyValue = hssfRow.getCell(6);
		                    HSSFCell sum = hssfRow.getCell(7);
		                    HSSFCell bankAmount = hssfRow.getCell(8);
		                    HSSFCell total = hssfRow.getCell(9);
		                    HSSFCell beforeReduce = hssfRow.getCell(10);
		                    HSSFCell amount = hssfRow.getCell(11);
		                    HSSFCell reduceNetValue = hssfRow.getCell(12);
		                    HSSFCell beforeReduceNetValue = hssfRow.getCell(13);
		                    HSSFCell assetsNetValue = hssfRow.getCell(14);
		                    HSSFCell repay = hssfRow.getCell(15);
		                    HSSFCell buy = hssfRow.getCell(16);
		                    //HSSFCell recordDate = date;
		                    
		                    Product product = (Product) productService.getProductByName(getValue(productName).trim());
		                    temp.setProductName(product.getHistoryName());
		                    temp.setProductId(product.getId());
		                    
		                    ProductDailyRecord pdrTemp = productDailyRecordService.getPdrByProductIdAndDate(product.getId(), date);
		                    if (null != pdrTemp){
		                    	continue;
		                    }
		                    temp.setStockAccountTotalAmount(new Double(getValue(stockAccountTotalAmount)));
		                    temp.setFutureAccountTotalAmount(new Double(getValue(futureAccountTotalAmount)));
		                    temp.setStockMarketValue(new Double(getValue(stockMarketValue)));
		                    temp.setFutureEmptyValue(new Double(getValue(futureEmptyValue)));
		                    temp.setSum(temp.getStockAccountTotalAmount()+temp.getFutureAccountTotalAmount());
		                    temp.setBankAmount(new Double(getValue(bankAmount)));
		                    temp.setTotal(temp.getSum()+temp.getBankAmount());
		                    temp.setBeforeReduce(new Double(getValue(beforeReduce)));
		                    temp.setAmount(new Double(getValue(amount)));
		                    temp.setReduceNetValue(new Double(getValue(reduceNetValue)));
		                    temp.setBeforeReduceNetValue(new Double(getValue(beforeReduceNetValue)));
		                    temp.setRepay(new Double(getValue(repay)));
		                    temp.setBuy(new Double(getValue(buy)));
		                    productDailyRecordService.save(temp);
		                    
//		                    List<ProductNetValueMailReciever> pnmrList = productNetValueMailRecieverService.getProductNetValueMailRecieverListByProductId(temp.getProductId());
		                    
		                    String receiverListStr = product.getReceiverList();
		                    String[] pnmrList = null;
		                    if (null != receiverListStr && receiverListStr.trim().length() != 0){
		                    	pnmrList = receiverListStr.split(";");
		                    }
		                    
		                    if (null==pnmrList){
		                    	continue;
		                    }
		                    
		                    for(int i = 0; i<pnmrList.length;i++){
		                    	String text = temp.getProductName() +", "+date+"日净值为:"+temp.getReduceNetValue()+",仅供参考;";	
		                    	if(null != map && map.containsKey(pnmrList[i])){
		                    		List tempList = map.get(pnmrList[i]);
		                    		tempList.add(text);
		                    		map.put(pnmrList[i], tempList);
		                    	}else{
		                    		List tempList = new ArrayList<String>();
		                    		tempList.add(text);
		                    		map.put(pnmrList[i], tempList);
		                    	}
//			                    mailService.sendMail(temp.getProductName()+date, text, pnmrList.get(i).getMailaddress());
		                    }

		                }
		            }
		        }
		        
		        sendMail(date);
						
				return ajax(Status.success,"true");
				
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return ajax(Status.error,"上传失败");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return ajax(Status.success, "1");
			}   
			
			
		} else {
			return ajax(Status.error, "上传失败!");
		}
	}
	
	
	
	

	@SuppressWarnings("unchecked")
	public String xuntouExcelUpload(){
		if (xuntouUpload != null) {
			if (xuntouUpload.length() > 200 * 1024) {
				return ajax(Status.error, "文件大小超出限制!");
			}
			try {
				HSSFWorkbook hssfWorkbook = new HSSFWorkbook(new FileInputStream(xuntouUpload));
				//读取日期
				Date dateObj;
				try {
					dateObj = com.rzrk.util.DateUtil.convertStringToDate("yyyyMMdd",xuntouUploadFileName.substring(0, 8));
				} catch (ParseException e) {
					e.printStackTrace();
					return ajax(Status.error, "文件名错误!");
				}
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String date = sdf.format(dateObj);
				
		        ProductDailyRecord temp = null;
		        
    
		        // 循环工作表Sheet
		        for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
		        	HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
		            if (hssfSheet == null) {
		                continue;
		            }
		            // 循环行Row
		            for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
		            	HSSFRow hssfRow = hssfSheet.getRow(rowNum);
		                if (hssfRow != null) {
		                	temp = new ProductDailyRecord();
		                	temp.setRecordDate(date);
		                    HSSFCell productName = hssfRow.getCell(0);
		                    HSSFCell remark = hssfRow.getCell(1);
		                    HSSFCell stockMarketValue = hssfRow.getCell(2);
		                    HSSFCell futureEmptyValue = hssfRow.getCell(3);
		                    HSSFCell reduceNetValue = hssfRow.getCell(4);
		                    HSSFCell sum = hssfRow.getCell(5);
		                    //HSSFCell futureMarketVaue = hssfRow.getCell(6);
		                    HSSFCell futureAccountTotalAmount = hssfRow.getCell(6);
		                    HSSFCell stockAccountTotalAmount = hssfRow.getCell(7);
		                    
		                    HSSFCell riskRate = hssfRow.getCell(8);
		                    HSSFCell amount = hssfRow.getCell(9);
		                    HSSFCell repay = hssfRow.getCell(10);
		                    HSSFCell buy = hssfRow.getCell(11);
		                    
		                    //HSSFCell recordDate = date;
		                    
		                    
		                    Product product = (Product) productService.getProductByXuntouName(getValue(productName).trim());
		                    
		                    if(null == product){
		                    	continue;
		                    }
		                    temp.setProductName(product.getHistoryName());
		                    temp.setProductId(product.getId());
		                    
		                    ProductDailyRecord pdrTemp = productDailyRecordService.getPdrByProductIdAndDate(product.getId(), date);
		                    if (null != pdrTemp){
		                    	continue;
		                    }
		                    temp.setStockAccountTotalAmount(new Double(getValue(stockAccountTotalAmount)));
		                    temp.setFutureAccountTotalAmount(new Double(getValue(futureAccountTotalAmount)));
		                    temp.setStockMarketValue(new Double(getValue(stockMarketValue)));
		                    //temp.setFutureMarketVaue(new Double(getValue(futureMarketVaue)));
		                    temp.setSum(new Double(getValue(sum)));
		                    temp.setTotal(temp.getSum()+temp.getBankAmount());
		                    temp.setReduceNetValue(new Double(getValue(reduceNetValue)));
		                    temp.setRemark(getValue(remark));
		                    temp.setFutureEmptyValue(new Double(getValue(futureEmptyValue)));
		                    temp.setRiskRate(getValue(riskRate));
		                    temp.setAmount(new Double(getValue(amount)));
		                    temp.setRepay(new Double(getValue(repay)));
		                    temp.setBuy(new Double(getValue(buy)));
		                    productDailyRecordService.save(temp);
		                    
//		                    List<ProductNetValueMailReciever> pnmrList = productNetValueMailRecieverService.getProductNetValueMailRecieverListByProductId(temp.getProductId());
		                    String receiverListStr = product.getReceiverList();
		                    String[] pnmrList = null;
		                    if (null != receiverListStr && receiverListStr.trim().length() != 0){
		                    	pnmrList = receiverListStr.split(";");
		                    }
		                    
		                    if (null==pnmrList){
		                    	continue;
		                    }
		                    
		                    for(int i = 0; i<pnmrList.length;i++){
		                    	String text = temp.getProductName() +", "+date+"日净值为:"+temp.getReduceNetValue()+",仅供参考;";	
		                    	if(null != map && map.containsKey(pnmrList[i])){
		                    		List tempList = map.get(pnmrList[i]);
		                    		tempList.add(text);
		                    		map.put(pnmrList[i], tempList);
		                    	}else{
		                    		List tempList = new ArrayList<String>();
		                    		tempList.add(text);
		                    		map.put(pnmrList[i], tempList);
		                    	}
//			                    mailService.sendMail(temp.getProductName()+date, text, pnmrList.get(i).getMailaddress());
		                    }
		                }
		            }
		        }
		        
		        sendMail(date);
//		        productRecordEngine.postAll();
						
				return ajax(Status.success,"true");
				
				
			} catch (Exception e) {
				e.printStackTrace();
				return ajax(Status.error,e.getMessage());
			} 
			
			
		} else {
			return ajax(Status.error, "上传失败!");
		}
	}
	
	
	private void sendMail(String date){
        int size = productDailyRecordService.getCurrentDayCountByDate(date);
        int actualProductSize = productService.getProductByIsValid(1);
        
        if(size >= actualProductSize && null != map){
        	Set keys = map.keySet();
        	if (null != keys)
        	{
        		 for (Iterator iter = keys.iterator(); iter.hasNext();) {
        			 String receiverAddress = (String) iter.next();
					  List tempList = map.get(receiverAddress);
					  String sendMessage = "尊敬的客户： /n";
					  for (int i= 0; i< tempList.size(); i++){
						  sendMessage = sendMessage +"/n"+ tempList.get(i);
					  }
					  mailService.sendMail(date+"日净值", sendMessage, receiverAddress);
				 }	        		
        	}
        }
	}
	
	
	
	protected String getValue(HSSFCell cell) {
		if (cell == null || cell.toString() == null || cell.toString().trim().length() == 0)
			return "0";
		String strCell = "";
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_STRING:
			strCell = cell.getStringCellValue();
			break;
		case HSSFCell.CELL_TYPE_NUMERIC:
			if (DateUtil.isCellDateFormatted(cell)) {
				Date date = cell.getDateCellValue();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				strCell = sdf.format(date);
			} else {
				strCell = String.valueOf(cell.getNumericCellValue());
			}
			break;
		case HSSFCell.CELL_TYPE_BOOLEAN:
			// strCell = String.valueOf(cell.getBooleanCellValue());
			break;
		case HSSFCell.CELL_TYPE_BLANK:
			// strCell = "";
			break;
		default:
			// strCell = "";
			break;
		}
		/*
		 * if (strCell.equals("") || strCell == null) { return ""; }
		 */
		return strCell;
	}
	
	
	public String ExcelUpload(){
		if (singlefile != null) {
			if (singlefile.length() > 200 * 1024) {
				return ajax(Status.error, "图片文件大小超出限制!");
			}
			String defaultMemberImagePath = ImageUtil.copyTempImageFile(
					getServletContext(), singlefile);
			return ajax(Status.success, defaultMemberImagePath);
		} else {
			return ajax(Status.error, "图片上传失败!");
		}
	}

	public List<File> getFiledata() {
		return filedata;
	}

	public void setFiledata(List<File> filedata) {
		this.filedata = filedata;
	}

	public List<String> getFiledataFileName() {
		return filedataFileName;
	}

	public void setFiledataFileName(List<String> filedataFileName) {
		this.filedataFileName = filedataFileName;
	}

	public List<String> getFiledataContentType() {
		return filedataContentType;
	}

	public void setFiledataContentType(List<String> filedataContentType) {
		this.filedataContentType = filedataContentType;
	}

	public static void main(String args[]){
		System.out.println("你好；//n亲");
	}
}
