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

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

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
public class ProductAction extends BaseAdminAction {

    private static final long serialVersionUID = 1L;

    @Resource(name = "productServiceImpl")
    private ProductService productService;

    private Product product;
	@Resource 
	ContractParserProduct contractParserProduct;
	
	@Resource 
	DictionaryService dictionaryService;

	private File uploadProduct;
	private String uploadProductFileName;

	
	
    public File getUploadProduct() {
		return uploadProduct;
	}
	public void setUploadProduct(File uploadProduct) {
		this.uploadProduct = uploadProduct;
	}
	public String getUploadProductFileName() {
		return uploadProductFileName;
	}
	public void setUploadProductFileName(String uploadProductFileName) {
		this.uploadProductFileName = uploadProductFileName;
	}






	// 列表
    public String list() {
//    	String isCunxu = getRequest().getParameter("isCunxu");
//    	if (null != isCunxu && isCunxu.trim().length() != 0){
//    		pager.setKeyword(isCunxu);
//    		pager.setSearchBy("isCunxu");
//    	}
//        pager = productService.findPager(pager);
        return LIST;
    }
    
    
    
   
    
    
	public String ajaxGetList(){
		processAjaxPagerRequestParameter();
		pager = productService.findPager(pager);
		List<Product> productList = (List<Product>) pager.getResult();
			
		
		JSONArray jsonObj = new JSONArray();
		for(int i = 0; i < productList.size(); i++ ){
		    Product temp = productList.get(i);
	        Map<String,Object> map = new HashMap<String,Object>(); 
	        
	        map.put("id", temp.getId()); 
	        map.put("nameSShort", temp.getNameSShort());
	        map.put("weight",temp.getWeight());
	        map.put("top", temp.getTop());
	        map.put("isCunxu", temp.getIsCunxu());
	        map.put("isValid", temp.getIsValid());
	        map.put("type", temp.getType());
	        map.put("purchaseState", temp.getPurchaseState());
	        map.put("duration", temp.getDuration());
	        map.put("custodianBank", temp.getCustodianBank());
	        map.put("createDate", DateUtils.formatDate(temp.getCreateDate(), "yyyy-MM-dd"));
	        map.put("isEnabled", temp.getIsEnabled());
	        jsonObj.add(map);
		}
         
        Map<String,Object> map = new HashMap<String,Object>(); 
        
        map.put("total", pager.getTotalCount()); 
        map.put("rows", jsonObj); 
        
		return ajax(map);
		
	}
    

    // 删除
    public String delete() {
    	Admin admin = getLoginAdmin();
        try {
			productService.delete(ids);
	        logInfo = admin.getName()+"删除产品成功："+ids;
	        return ajax(Status.success, "删除成功!");
		} catch (Exception e) {
			//TODO 没有详细记录
			e.printStackTrace();
	        logInfo = admin.getName()+"删除产品失败："+ids;
	        return ajax(Status.success, "删除成功!");
		}
    }

    // 添加
    public String add() {
        getRequest().setAttribute("command", "添加");
        product = new Product();
        return INPUT;
    }

    // 编辑
    public String edit() {
        getRequest().setAttribute("command", "编辑");
        product = productService.load(id);
        return INPUT;
    }

    // 保存添加
    @InputConfig(resultName = "error")
    public String save() throws Exception {
    	Admin admin = getLoginAdmin();
        product.setIsEnabled(Constants.YES);
        try {
			productService.save(product);
	        redirectUrl = "product!list.action?isCunxu=1";
	        logInfo = admin.getName()+"新增产品成功："+product.getName();
		} catch (Exception e) {
	        logInfo = admin.getName()+"新增产品失败："+product.getName();
			e.printStackTrace();
		}
        return SUCCESS;
    }

    // 保存修改
    @InputConfig(resultName = "error")
    public String update() {
    	Admin admin = getLoginAdmin();
        try {
            Product persistent = productService.get(id);
            BeanUtils.copyProperties(product, persistent, Product.getUnUpdateField());
            productService.update(persistent);
            redirectUrl = "product!list.action?isCunxu=1";
            contractParserProduct.removeCache(persistent);
	        logInfo = admin.getName()+"更新产品成功："+product.getName();
            return SUCCESS;
        } catch (Exception e) {
	        logInfo = admin.getName()+"更新产品失败："+product.getName();
            logger.error(e.getMessage(), e);
            logger.error(String.format("更新产品ID=[%s]异常!", id));
            return ERROR;
        }
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
    

	public String getAjaxList4BuiltIn(){
		
		List<Product> productList = productService.getAllList();
		JSONArray jsonArray = new JSONArray();
		for(Product product :productList){
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("name", product.getName());
			jsonArray.add(jsonObj);
		}
		return ajax(jsonArray);
	}

	public String uploadProduct() throws FileNotFoundException, IOException{
		Admin admin = getLoginAdmin();
		try {
			HSSFWorkbook hssfWorkbook = new HSSFWorkbook(new FileInputStream(uploadProduct));
			productService.uploadProduct(hssfWorkbook,getLoginAdmin());
			logInfo = admin.getName() + " 导入产品成功" ;
			return ajax(Status.success, "上传成功!");
		} catch (Exception e) {
			e.printStackTrace();
			logInfo = admin.getName()+ " 导入产品失败: " +e.getMessage() ;
			return ajax(Status.error, e.getMessage());
		}

	}
	
	public String responseHSSFWorkbook(HSSFWorkbook wb,String fileName) throws IOException{
		HttpServletResponse response = ServletActionContext.getResponse();
		response.reset();  
        response.setContentType("application/vnd.ms-excel;charset=utf-8");  
        response.setHeader("Content-Disposition", "attachment;filename=" 
        		+ URLEncoder.encode(fileName, "UTF-8"));
		wb.write(response.getOutputStream());
		return NONE;
	}

	public String downloadProduct() throws FileNotFoundException, IOException{
		processAjaxPagerRequestParameter();
		pager = productService.findPager(pager);
		List<Product> productList = (List<Product>) pager.getResult();
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFFont titleFont = wb.createFont();
		titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		HSSFCellStyle titleStyle = wb.createCellStyle();
		titleStyle.setFont(titleFont);
		titleStyle.setFillForegroundColor(HSSFColor.PALE_BLUE.index);// 设置背景色  
		titleStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        HSSFSheet sheet = wb.createSheet("产品");  
        HSSFRow row = sheet.createRow(0);
        int i=0;
        HSSFCell cellTitle;
		cellTitle = row.createCell((short) i++);  
		cellTitle.setCellValue(ProductService.FIELD_nameShort);
		cellTitle.setCellStyle(titleStyle);
		cellTitle = row.createCell((short) i++);  
		cellTitle.setCellValue(ProductService.FIELD_top);
		cellTitle.setCellStyle(titleStyle);
		cellTitle = row.createCell((short) i++);  
		cellTitle.setCellValue(ProductService.FIELD_isCunxu);
		cellTitle.setCellStyle(titleStyle);
		cellTitle = row.createCell((short) i++);  
		cellTitle.setCellValue(ProductService.FIELD_isValid);
		cellTitle.setCellStyle(titleStyle);
		cellTitle = row.createCell((short) i++);  
		cellTitle.setCellValue(ProductService.FIELD_type);
		cellTitle.setCellStyle(titleStyle);
		cellTitle = row.createCell((short) i++);  
		cellTitle.setCellValue(ProductService.FIELD_purchaseState);
		cellTitle.setCellStyle(titleStyle);
		cellTitle = row.createCell((short) i++);  
		cellTitle.setCellValue(ProductService.FIELD_duration);
		cellTitle.setCellStyle(titleStyle);
		cellTitle = row.createCell((short) i++);  
		cellTitle.setCellValue(ProductService.FIELD_custodianBank);
		cellTitle.setCellStyle(titleStyle);
		cellTitle = row.createCell((short) i++);  
		cellTitle.setCellValue("创建日期");
		cellTitle.setCellStyle(titleStyle);
		cellTitle = row.createCell((short) i++);  
		cellTitle.setCellValue(ProductService.FIELD_isEnabled);
		cellTitle.setCellStyle(titleStyle);
		
		
    	int j=1;
    	for(Product entity : productList){
            row = sheet.createRow(j++);
            int m=0;
            HSSFCell cell ;
            row.createCell((short)m++).setCellValue(entity.getNameShort());
            row.createCell((short)m++).setCellValue(dictionaryService.findDictionaryByKey(ProductService.DICT_TOP)
            		.findByKeyWithDefault(entity.getTop()+"").getText());
            row.createCell((short)m++).setCellValue(dictionaryService.findDictionaryByKey(ProductService.DICT_ISCUNXU)
            		.findByKeyWithDefault(entity.getIsCunxu()+"").getText());
            row.createCell((short)m++).setCellValue(dictionaryService.findDictionaryByKey(ProductService.DICT_ISVALID)
            		.findByKeyWithDefault(entity.getIsValid()+"").getText());
            row.createCell((short)m++).setCellValue(dictionaryService.findDictionaryByKey(ProductService.DICT_TYPE)
            		.findByKeyWithDefault(entity.getType()+"").getText());
            row.createCell((short)m++).setCellValue(dictionaryService.findDictionaryByKey(ProductService.DICT_PURCHASESTATE)
            		.findByKeyWithDefault(entity.getPurchaseState()+"").getText());
            row.createCell((short)m++).setCellValue(entity.getDuration());
            row.createCell((short)m++).setCellValue(entity.getCustodianBank());
            row.createCell((short)m++).setCellValue(DateUtil.getDateTime("yyyy-MM-dd HH:mm:ss", entity.getCreateDate()));
            row.createCell((short)m++).setCellValue(dictionaryService.findDictionaryByKey(ProductService.DICT_ISENABLED)
            		.findByKeyWithDefault(entity.getIsEnabled()+"").getText());
    	}
        return responseHSSFWorkbook(wb,wb.getSheetAt(0).getSheetName()+"_"+DateUtils.formatDate(new Date(),"yyyyMMddHHmmss")+".xls");

	}
}