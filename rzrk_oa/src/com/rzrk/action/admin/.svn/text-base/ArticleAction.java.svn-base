/**
 * Project Name: rzrk Web
 * Confidential and Proprietary                                                                
 * Copyright (C) 2013 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.action.admin;


import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.rzrk.entity.Article;
import com.rzrk.entity.ArticleCategory;
import com.rzrk.entity.Product;
import com.rzrk.service.ArticleCategoryService;
import com.rzrk.service.ArticleService;
import com.rzrk.service.CacheService;
import com.rzrk.util.ImageUtil;
import com.rzrk.util.date.DateUtils;

/**
 * 后台Action类 - 文章
 */

@ParentPackage("admin")
public class ArticleAction extends BaseAdminAction {

	private static final long serialVersionUID = -6825456589196458406L;

	private Article article;
	
	private File imageFile;//上传的文件
	private String imageFileName; //文件名称
	//private String imageContentType; //文件类型

	@Resource(name = "articleServiceImpl")
	private ArticleService articleService;
	@Resource(name = "articleCategoryServiceImpl")
	private ArticleCategoryService articleCategoryService;
	@Resource(name = "cacheServiceImpl")
	private CacheService cacheService;
	private UploadAction uploadAction;
	
	
	
    
	// 添加
	public String add() {
		List<Product> productNamesTreeList = articleCategoryService.getProductNamesTree();
		getRequest().setAttribute("productNamesTreeList", productNamesTreeList);
		return INPUT;
	}

	// 编辑
	public String edit() {
		article = articleService.load(id);
		List<Product> productNamesTreeList = articleCategoryService.getProductNamesTree();
		getRequest().setAttribute("productNamesTreeList", productNamesTreeList);
		return INPUT;
	}

	// 列表
	public String list() {
		//pager = articleService.findPager(pager);
		return LIST;
	}
	
	
	public String ajaxGetList(){
		processAjaxPagerRequestParameter();
		pager = articleService.findPager(pager);
		List<Article> articleList = (List<Article>) pager.getResult();
			
		
		JSONArray jsonObj = new JSONArray();
		for(int i = 0; i < articleList.size(); i++ ){
		    Article temp = articleList.get(i);
	        Map<String,Object> map = new HashMap<String,Object>(); 
	        
	        map.put("id", temp.getId()); 
	        map.put("title", temp.getTitle());
	        map.put("imagePath",temp.getImagePath());
	        map.put("articleCategory", temp.getArticleCategory().getName());
	        map.put("isPublication", temp.getIsPublication());
	        map.put("isRecommend", temp.getIsRecommend());
	        map.put("createDate", DateUtils.formatDate(temp.getCreateDate(), "yyyy-MM-dd"));
	        jsonObj.add(map);
		}
         
        Map<String,Object> map = new HashMap<String,Object>(); 
        
        map.put("total", pager.getTotalCount()); 
        map.put("rows", jsonObj); 
        
		return ajax(map);
		
	}
	
	
	

	
	
	//浏览
	
	public String overList(){
		article=articleService.getArticleById(id);
		return "overList";
	}

	// 删除
	public String delete() throws Exception {
		StringBuffer logInfoStringBuffer = new StringBuffer("删除文章: ");
		for (String id : ids) {
			Article article = articleService.load(id);
			articleService.delete(article);
			logInfoStringBuffer.append(article.getTitle() + " ");
		}
		logInfo = logInfoStringBuffer.toString();

		cacheService.flushArticleListPageCache(getRequest());

		return ajax(Status.success, "删除成功!");
	}

	// 保存
	@Validations(
			requiredStrings = { 
					@RequiredStringValidator(fieldName = "article.title", message = "标题不允许为空!"),
					
					@RequiredStringValidator(fieldName = "article.articleCategory.id", message = "文章分类不允许为空!")
			}
			)
	@InputConfig(resultName = "error")
	public String save() throws Exception {
        article.setImagePath(getRequest().getParameter("imageUrl"));
		articleService.save(article);
		logInfo = "添加文章: " + article.getTitle();
		redirectUrl = "article!list.action";
		return SUCCESS;
	}

	// 更新
	@Validations(
			requiredStrings = { 
					@RequiredStringValidator(fieldName = "article.title", message = "标题不允许为空!"),
					
					@RequiredStringValidator(fieldName = "article.articleCategory.id", message = "文章分类不允许为空!")
			}
			)
	@InputConfig(resultName = "error")
	public String update() throws Exception {
		Article persistent = articleService.load(id);
		BeanUtils.copyProperties(article, persistent, new String[] {"id", "createDate", "modifyDate", "pageCount", "htmlPath", "hits"});
		if (imageFile != null) {
            persistent.setImagePath(ImageUtil.copyImageFile(getServletContext(), imageFile));
        }
		articleService.update(persistent);
		logInfo = "编辑文章: " + article.getTitle();

		cacheService.flushArticleListPageCache(getRequest());

		redirectUrl = "article!list.action";
		return SUCCESS;
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public List<ArticleCategory> getArticleCategoryTreeList() {
		return articleCategoryService.getArticleCategoryTreeList();
	}
	
	public List<Product> getProductNamesTree(){
		return articleCategoryService.getProductNamesTree();
	}

	
	public File getImageFile() {
		return imageFile;
	}

	public void setImageFile(File imageFile) {
		this.imageFile = imageFile;
	}

	public String getImageFileName() {
		return imageFileName;
	}

	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}

//	public String getImageContentType() {
//		return imageContentType;
//	}
//
//	public void setImageContentType(String imageContentType) {
//		this.imageContentType = imageContentType;
//	}

	public UploadAction getUploadAction() {
		return uploadAction;
	}

	public void setUploadAction(UploadAction uploadAction) {
		this.uploadAction = uploadAction;
	}

}