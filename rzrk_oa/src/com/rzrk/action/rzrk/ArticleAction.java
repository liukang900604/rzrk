/**
 * Project Name: rzrk Web
 * Confidential and Proprietary                                                                
 * Copyright (C) 2013 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.action.rzrk;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.ParentPackage;

import com.rzrk.bean.Pager;
import com.rzrk.entity.Article;
import com.rzrk.entity.Product;
import com.rzrk.entity.ProductNetValue;
import com.rzrk.service.ArticleService;
import com.rzrk.service.ProductNetValueService;
import com.rzrk.service.ProductService;

/**
 * 前台Action类 - 文章
 */

@ParentPackage("shop")
public class ArticleAction extends BaseShopAction {

	private static final long serialVersionUID = -25541236985328967L;
	private Article article;
	private Article lastArticle;
	private Article nextArticle;
	/** 首页文章列表 */
	private List<Article> articleList;
	private List<Article> afficheList;
	private List<Article> newsList;
	private List<Article> countList;
	private List<Article> reportList;
	private int totalPage; //总页数
	private int totalNumber; //总记录数
	private List<Article> pageList;

	@Resource(name = "articleServiceImpl")
	private ArticleService articleService;

	@Resource(name = "productNetValueServiceImpl")
	private ProductNetValueService productNetValueService;

	@Resource(name = "productServiceImpl")
	private ProductService productService;

	/** 缓存的首页产品数据 */
	private static List<Product> cacheProductList = new ArrayList<Product>();

	/** 上次查询数据库时间 */
	private static Long lastQueryTime;

	/** 缓存时间一小时 */
	private static long cacheTime = 1000 * 60 * 60;

	//处理数据中的编辑器标签
	protected String regexContent(String regex,String content){
		Pattern pa = Pattern.compile(regex, Pattern.DOTALL);
		Matcher ma = pa.matcher(content);
		content = ma.replaceAll("");
		return content;
	}
	//首页新闻
	public String viewList() {
		articleList = articleService.getArticleList();
//		String regex = "<.*?>";
//		for( Article article: articleList){
//			String content = article.getContent();
//			content =regexContent(regex,content);
//			article.setContent(content);
//		}

		if (lastQueryTime != null && new Date().getTime() - lastQueryTime < cacheTime) {
			logger.info("首页直接返回缓存数据");
			getRequest().setAttribute("productList", cacheProductList);
		} else {
			// 获取推荐的产品数据
			List<Product> productList = productService.getRecommendProduct();
			// 遍历获取净值数据
			if (productList!=null){
				for (Product item : productList) {
					List<ProductNetValue> netValueList = productNetValueService.getNetValueByProductId(item.getId());
					if (netValueList != null) {
						item.setLastNetValue(netValueList.get(netValueList.size() - 1));
						item.setNetValueList(netValueList);
					}
				}
				lastQueryTime = new Date().getTime();
				cacheProductList = productList;
				getRequest().setAttribute("productList", productList);
			}
			
			logger.info("首页查询数据库");
		}

		return "view";
	}
	//公告
	public String affiche() {
		pageList=articleService.getArticleByName("公告");
		afficheList = articleService.getArticlePagerByName(pager,"公告");
		if(null!=pageList&&pageList.size()>0){
			totalNumber=pageList.size();
			pager.setTotalCount(totalNumber);	
		}


		return "affiche";
	}
	//新闻
	public String news() {
		pageList=articleService.getArticleByName("新闻");
		newsList = articleService.getArticlePagerByName(pager, "新闻");
		if(null!=pageList&&pageList.size()>0){
			totalNumber=pageList.size();
			pager.setTotalCount(totalNumber);	
		}
		return "news";
	}
	//导航栏资讯
	public String information(){
		articleList = articleService.getPrompt();
		return "information";
	}
	//文章详细
	public String articleList() {
		article = articleService.getArticleById(id);
		if(article!=null){
			lastArticle = articleService.getLastArticleById(id,article.getArticleCategory().getName());
			nextArticle = articleService.getNextArticleById(id,article.getArticleCategory().getName());
			pageList=articleService.getArticlelListByArticleName(article.getArticleCategory().getName());
		}
		return "detail";
	}

	public String articleIndex() {
		return "index";
	}
	//投资报告文件下载
	public void pdfDownload(){
		try {
			Article article = articleService.get(id);
			//文件下载路径
			File file = new File(getServletContext().getRealPath(article.getImagePath()));
			// attachment表示网页会出现保存、打开对话框  
			getResponse().addHeader("Content-Disposition","attachment;filename=" + URLEncoder.encode(article.getTitle(),"UTF-8"));//设置文件名称
			OutputStream out = new BufferedOutputStream(getResponse().getOutputStream());//获取输出流
			InputStream is;
			is = new BufferedInputStream(new FileInputStream(file));
			byte[] b = new byte[is.available()];
			is.read(b);
			is.close();
			out.write(b);//向页面输出（即是下载）
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//将文件读取
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	//投资报告
	public String report(){
		pageList=articleService.getArticleByName("投资报告");
		reportList=articleService.getArticlePagerByName(pager, "投资报告");
		if(null!=pageList&&pageList.size()>0){
			totalNumber=pageList.size();
			pager.setTotalCount(totalNumber);	
		}
		return "report";
	}

	public List<Article> getCountList() {
		return countList;
	}

	public void setCountList(List<Article> countList) {
		this.countList = countList;
	}

	public List<Article> getAfficheList() {
		return afficheList;
	}

	public void setAfficheList(List<Article> afficheList) {
		this.afficheList = afficheList;
	}

	public List<Article> getNewsList() {
		return newsList;
	}

	public void setNewsList(List<Article> newsList) {
		this.newsList = newsList;
	}

	public List<Article> getArticleList() {
		return articleList;
	}

	public void setArticleLists(List<Article> articleList) {
		this.articleList = articleList;
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public Article getLastArticle() {
		return lastArticle;
	}

	public void setLastArticle(Article lastArticle) {
		this.lastArticle = lastArticle;
	}

	public Article getNextArticle() {
		return nextArticle;
	}

	public void setNextArticle(Article nextArticle) {
		this.nextArticle = nextArticle;
	}

	public List<Article> getReportList() {
		return reportList;
	}

	public void setReportList(List<Article> reportList) {
		this.reportList = reportList;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getTotalNumber() {
		return totalNumber;
	}

	public void setTotalNumber(int totalNumber) {
		this.totalNumber = totalNumber;
	}

	public List<Article> getPageList() {
		return pageList;
	}

	public void setPageList(List<Article> pageList) {
		this.pageList = pageList;
	}


}