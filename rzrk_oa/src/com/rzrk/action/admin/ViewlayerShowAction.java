package com.rzrk.action.admin;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
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
import org.springframework.beans.BeansException;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.rzrk.action.admin.BaseAdminAction.Status;
import com.rzrk.entity.Admin;
import com.rzrk.entity.ContractCategory;
import com.rzrk.entity.ContractField;
import com.rzrk.entity.Product;
import com.rzrk.entity.Project;
import com.rzrk.entity.QueryHistory;
import com.rzrk.entity.Viewlayer;
import com.rzrk.service.AdminService;
import com.rzrk.service.ContractCategoryService;
import com.rzrk.service.ProjectService;
import com.rzrk.service.QueryHistoryService;
import com.rzrk.service.RootContractCategoryService;
import com.rzrk.service.ViewlayerService;
import com.rzrk.util.JsonUtil;
import com.rzrk.util.date.DateUtils;
import com.rzrk.vo.queryhistory.TreeItem;
import com.rzrk.vo.querytree.Node;
import com.rzrk.vo.viewlayer.Definition;
import com.rzrk.vo.viewlayer.Field;

@ParentPackage("admin")
public class ViewlayerShowAction extends BaseAdminAction {

	Viewlayer viewlayer = new Viewlayer();
	private String preQuery; //预先设置的查询条件

	public String getPreQuery() {
		return preQuery;
	}

	public void setPreQuery(String preQuery) {
		this.preQuery = preQuery;
	}

	public Viewlayer getViewlayer() {
		return viewlayer;
	}

	public void setViewlayer(Viewlayer viewlayer) {
		this.viewlayer = viewlayer;
	}

	@Resource
	private ViewlayerService viewlayerService;
	@Resource
	AdminService adminService;
	@Resource
	ContractCategoryService contractCategoryService;
	@Resource
	RootContractCategoryService rootContractCategoryService;


	// 列表
	public String list() {
		viewlayer = viewlayerService.get(id);
		
		
		Definition definition = viewlayer.getDefinitionObj();
		List<Field> fieldList = viewlayerService.getPermissionField(definition, getLoginAdmin());
		
		ArrayList<String> titleArr = new ArrayList<String>();
		if(CollectionUtils.isNotEmpty(fieldList)){
			getRequest().setAttribute("definition", JsonUtil.toJson(fieldList));
			getRequest().setAttribute("queryOption", new Definition(fieldList).getTableFileds());
		}else{
			getRequest().setAttribute("definition", "[]");
			getRequest().setAttribute("queryOption", new Definition());
		}
		
		return LIST;
	}

	public String getAjaxList() {
		Admin admin = getLoginAdmin();
		processAjaxPagerRequestParameter();
		Node node = JsonUtil.toObject(preQuery, Node.class);
		pager = viewlayerService.findShowPager(pager,id,node);
		         
        Map<String,Object> map = new HashMap<String,Object>(); 
        
        map.put("total", pager.getTotalCount()); 
        map.put("rows", pager.getResult());
//        map.put("footer", pager.getFooter());

		return ajax(map);
	}

	public String download() throws Exception{
		Node node = JsonUtil.toObject(preQuery, Node.class);
		pager.setPageSize(Integer.MAX_VALUE);
		pager = viewlayerService.findShowPager(pager,id,node);
		ArrayList<HashMap<String, String>> result = (ArrayList<HashMap<String, String>>) pager.getResult();

		Viewlayer viewlayer = viewlayerService.get(id);
		Definition defList = JsonUtil.toObject(viewlayer.getDefinition(), Definition.class); 
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFFont titleFont = wb.createFont();
		titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		HSSFCellStyle titleStyle = wb.createCellStyle();
		titleStyle.setFont(titleFont);
		titleStyle.setFillForegroundColor(HSSFColor.PALE_BLUE.index);// 设置背景色  
		titleStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        HSSFSheet sheet = wb.createSheet(viewlayer.getName());  
        HSSFRow row = sheet.createRow(0);
        int i=0;
    	for(Field field : defList){
            HSSFCell cell = row.createCell((short) i++);  
            cell.setCellValue(field.getShowField());
            cell.setCellStyle(titleStyle);
    	}
    	int j=1;
    	for(HashMap<String, String> entity : result){
            row = sheet.createRow(j++);
            for(int m=0; m<defList.size();m++ ){
            	String value = entity.get(defList.get(m).getShowField());
                HSSFCell cell = row.createCell((short)m);  
                cell.setCellValue(value);
            }
    	}
        return responseHSSFWorkbook(wb,wb.getSheetAt(0).getSheetName()+"_"+DateUtils.formatDate(new Date(),"yyyyMMddHHmmss")+".xls");
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

}
