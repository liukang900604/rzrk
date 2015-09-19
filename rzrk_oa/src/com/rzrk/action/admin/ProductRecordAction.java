package com.rzrk.action.admin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import com.opensymphony.oscache.general.GeneralCacheAdministrator;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.rzrk.Constants;
import com.rzrk.action.admin.BaseAdminAction.Status;
import com.rzrk.common.CheckInfo;
import com.rzrk.common.contract.ContractParserProduct;
import com.rzrk.common.product.ProductRecordEngine;
import com.rzrk.entity.Admin;
import com.rzrk.entity.ContractCategory;
import com.rzrk.entity.Job;
import com.rzrk.entity.Product;
import com.rzrk.service.DictionaryService;
import com.rzrk.service.ProductService;
import com.rzrk.util.DateUtil;
import com.rzrk.util.JsonUtil;
import com.rzrk.util.date.DateUtils;
import com.rzrk.vo.querytree.Node;

@ParentPackage("admin")
public class ProductRecordAction extends BaseAdminAction {

    private static final long serialVersionUID = 1L;

    @Resource 
    private ProductRecordEngine productRecordEngine;
    
    String productDetailId;
    
    public String getProductDetailId() {
		return productDetailId;
	}
	public void setProductDetailId(String productDetailId) {
		this.productDetailId = productDetailId;
	}



	public String post(){
		Future future = null;
    	if(StringUtils.isEmpty(productDetailId)){
    		future = productRecordEngine.postAll();
    	}else{
 //   		future = productRecordEngine.post(productDetailId);
    	}
    	try {
			future.get();
	    	return ajax(Status.success);
		} catch (Exception e) {
			e.printStackTrace();
	    	return ajax(Status.error,e.getMessage());
		}
	}
	
	public String clear(){
		Future future = productRecordEngine.clearAll();
    	try {
			future.get();
	    	return ajax(Status.success);
		} catch (Exception e) {
			e.printStackTrace();
	    	return ajax(Status.error,e.getMessage());
		}
	}
	
	
	
	
	
}