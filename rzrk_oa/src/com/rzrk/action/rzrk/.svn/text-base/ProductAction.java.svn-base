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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.ParentPackage;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.rzrk.Constants;
import com.rzrk.bean.Pager;
import com.rzrk.bean.Pager.Order;
import com.rzrk.entity.Article;
import com.rzrk.entity.Member;
import com.rzrk.entity.Product;
import com.rzrk.entity.ProductEarnCollect;
import com.rzrk.entity.ProductNetValue;
import com.rzrk.service.ArticleService;
import com.rzrk.service.MemberService;
import com.rzrk.service.ProductEarnCollectService;
import com.rzrk.service.ProductNetValueService;
import com.rzrk.service.ProductService;
import com.rzrk.util.date.DateUtils;

@ParentPackage("shop")
public class ProductAction extends BaseShopAction {

    private static final long serialVersionUID = 1L;

    @Resource(name = "productServiceImpl")
    private ProductService productService;

    @Resource(name = "productNetValueServiceImpl")
    private ProductNetValueService productNetValueService;
    @Resource(name = "memberServiceImpl")
    private MemberService memberService;
    @Resource(name = "productEarnCollectServiceImpl")
    private ProductEarnCollectService productEarnCollectService;
    @Resource(name = "articleServiceImpl")
    private ArticleService articleService;

    private Product product;
    private Member member;

    // 全产品预览
    public String preview() {
        try {
        	//进取型
            List<Product> productList = productService.findByProductAggressives();
            //其它
            List<Product> otherList = productService.getProductByProductCategories();
            //稳健型
            List<Product> stableList = productService.findByProductAggressive();
            //灵活型
            List<Product> flexibleList = productService.findByProductFlexible();
            //List<Product> otherstableList =productService.findByProductCategoriess();
            if (productList !=null){
            	logger.info(String.format("共计查询[%s]条可用产品成功!", productList.size()));
            	getRequest().setAttribute("productList", productList);
            }
            if (otherList!=null){
            	getRequest().setAttribute("otherList", otherList);
            }
            if (stableList!=null){
            	getRequest().setAttribute("stableList", stableList);
            }
            if (flexibleList!=null){
            	getRequest().setAttribute("flexibleList", flexibleList);
            }
            return "preview";
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            logger.error("获取可用产品异常!");
            return ERROR;
        }
    }
    
    
    // 全产品预览
    public String preiewList() {
        try {
    		// 获取推荐的产品数据
    		List<Product> productList = productService.getProductByWeight();
    		List<Product> productLists = productService.findByProductAggressives();
            List<Product> otherList = productService.getProductByProductCategories();
            List<Product> stableList = productService.findByProductAggressive();
            //灵活型
            List<Product> flexibleList = productService.findByProductFlexible();
            //List<Product> otherstableList =productService.findByProductCategoriess();
    		
    		// 遍历获取净值数据
    		for (Product item : productList) {
    			List<ProductNetValue> netValueList = productNetValueService.getNetValueByProductId(item.getId());
    			if (netValueList != null) {
    				item.setLastNetValue(netValueList.get(netValueList.size() - 1));
    				item.setNetValueList(netValueList);
    			}
    			List<ProductEarnCollect> earnCollectList =productEarnCollectService.getEarnByProductId(item.getId());
    			if (earnCollectList != null){
    				
    				item.setProductEarnCollect(earnCollectList.get(earnCollectList.size()-1));
    				item.setProductEarnCollectList(earnCollectList);
    			}
    			
    		} 		
    		getRequest().setAttribute("productList", productList);
    		
    		if (productLists !=null){
            	logger.info(String.format("共计查询[%s]条可用产品成功!", productList.size()));
            	getRequest().setAttribute("productLists", productLists);
            }
            if (otherList!=null){
            	getRequest().setAttribute("otherList", otherList);
            }
            if (stableList!=null){
            	getRequest().setAttribute("stableList", stableList);
            }
            if (flexibleList!=null){
            	getRequest().setAttribute("flexibleList", flexibleList);
            }
           
    		logger.info("首页查询数据库");
            return "preiewList";
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            logger.error("获取可用产品异常!");
            return ERROR;
        }
    }

    // 全产品预览-详情
    public String detail() {
        try {
        	List<Product> productList = productService.findByProductAggressives();
            List<Product> otherList = productService.getProductByProductCategories();
            List<Product> stableList = productService.findByProductAggressive();
            //灵活型
            List<Product> flexibleList = productService.findByProductFlexible();
            //List<Product> otherstableList =productService.findByProductCategoriess();
            if (productList !=null){
            	logger.info(String.format("共计查询[%s]条可用产品成功!", productList.size()));
            	getRequest().setAttribute("productList", productList);
            }
            if (otherList!=null){
            	getRequest().setAttribute("otherList", otherList);
            }
            if (stableList!=null){
            	getRequest().setAttribute("stableList", stableList);
            }
            if (flexibleList!=null){
            	getRequest().setAttribute("flexibleList", flexibleList);
            }
            String startIndex = getRequest().getParameter("startIndex");
            getRequest().setAttribute("startIndex", startIndex);
            String id = getRequest().getParameter("id");
            if (id == null || "".equals(id)) {
                logger.error("查看单个产品详情页ID为空!");
                return ERROR;
            }
            Product product = productService.get(id);
            getRequest().setAttribute("product", product);
            logger.info(String.format("查询产品[id=%s]成功!", id));
            return "detail";
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            logger.error("获取可用产品异常!");
            return ERROR;
        }
    }

    // 全产品预览-详情-单个产品详情
    public String summary() {
        try {
            String id = getRequest().getParameter("id");
            if (id == null || "".equals(id)) {
                logger.error("查看单个产品详情页ID为空!");
                return ERROR;
            }
            Product product = productService.get(id);
            getRequest().setAttribute("product", product);
            logger.info(String.format("查询产品[id=%s]成功!", id));
            return "detail-summary";
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            logger.error("根据ID获取产品异常!");
            return ERROR;
        }
    }

    // 全产品预览-详情-产品净值
    public String netValue() {
        try {
            String id = getRequest().getParameter("id");
            if (id == null || "".equals(id)) {
                logger.error("查看单个产品净值页ID为空!");
                return ERROR;
            }
            String beginTime = getRequest().getParameter("beginTime");
            String endTime = getRequest().getParameter("endTime");

            pager = (pager == null) ? new Pager() : pager;
            pager.setOrderBy("date");
            pager.setOrder(Order.desc);
            pager = productNetValueService.findPager(pager, (beginTime == null || "".equals(beginTime)) ? null : Restrictions.ge("date", DateUtils.parseDate(beginTime)),
                    (endTime == null || "".equals(endTime)) ? null : Restrictions.lt("date", DateUtils.parseDate(endTime)), Restrictions.eq("productId", id));

            getRequest().setAttribute("beginTime", beginTime);
            getRequest().setAttribute("endTime", endTime);
            logger.info(String.format("查询产品净值页[id=%s]成功!共计获取[%s]条!", id, pager == null ? 0 : pager.getResult().size()));
            return "detail-net-value";
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            logger.error("根据ID获取产品净值页异常!");
            return ERROR;
        }
    }

    public String subscription() {
        return "detail-subscription";
    }

    public String redemption() {
    	String id = getRequest().getParameter("id");
    	if (id != null) {
    		Product product = productService.get(id);
            getRequest().setAttribute("product", product);
         }
        return "detail-redemption";
    }
    
    //产品公告
    public String announcement() {
    	String id = getRequest().getParameter("id");
    	if (id != null) {
    		Article article = articleService.getArticleByProductId(id);
            getRequest().setAttribute("article", article);
         }
        return "detail-announcement";
    }
    //赎回申请书下载
  	public void docDownload(){
  		try {
  			if (id.equals("8")){
  				//路径
  				File file = new File(getServletContext().getRealPath("doc/兴业赎回申请书.doc"));
  			    // attachment表示网页会出现保存、打开对话框  
  	  			getResponse().addHeader("Content-Disposition","attachment;filename=" + URLEncoder.encode("赎回申购书.doc","UTF-8"));//设置文件名称
  	  			OutputStream out = new BufferedOutputStream(getResponse().getOutputStream());//获取输出流
  	  			InputStream is;
  	  			is = new BufferedInputStream(new FileInputStream(file));
  	  			byte[] b = new byte[is.available()];
  	  			is.read(b);
  	  			is.close();
  	  			out.write(b);//向页面输出（即是下载）
  	  			out.close();
  			}else if (!id.equals("8")&&!id.equals("4028814043cd3c140143cd42db290001")){
  				//路径
  				File file = new File(getServletContext().getRealPath("doc/中信信托量化对冲产品赎回申请书.docx"));
  			    // attachment表示网页会出现保存、打开对话框  
  	  			getResponse().addHeader("Content-Disposition","attachment;filename=" + URLEncoder.encode("赎回申购书.doc","UTF-8"));//设置文件名称
  	  			OutputStream out = new BufferedOutputStream(getResponse().getOutputStream());//获取输出流
  	  			InputStream is;
  	  			is = new BufferedInputStream(new FileInputStream(file));
  	  			byte[] b = new byte[is.available()];
  	  			is.read(b);
  	  			is.close();
  	  			out.write(b);//向页面输出（即是下载）
  	  			out.close();
  			}
  		} catch (FileNotFoundException e) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
  		}//将文件读取
  		catch (IOException e) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
  		}
  	}

    // 列表
    public String list() {
        pager = productService.findPager(pager);
        return LIST;
    }

    // 添加
    public String add() {
        getRequest().setAttribute("command", "添加");
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
        product.setIsEnabled(Constants.YES);
        productService.save(product);
        redirectUrl = "product!list.action";
        return SUCCESS;
    }

    // 保存修改
    @InputConfig(resultName = "error")
    public String update() {
        try {
            Product persistent = productService.get(id);
            BeanUtils.copyProperties(product, persistent, Product.getUnUpdateField());
            productService.update(persistent);
            redirectUrl = "product!list.action";
            return SUCCESS;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            logger.error(String.format("更新产品ID=[%s]异常!", id));
            return ERROR;
        }
    }

    // 获取产品收益信息
    public String getProductEarnCollectData() {
        String result = "{\"productId\":\"%s\",\"earnWeek\":\"%s\",\"earnMonth\":\"%s\",\"earnTotal\":\"%s\",\"aror\":\"%s\"}";
        String productId = getRequest().getParameter("productId");
        try {
            ProductEarnCollect item = productEarnCollectService.getProductEarnCollect(productId);

            // 当有手写数据时
            if (item != null && (item.getEarnWeek() != null || item.getEarnMonth() != null || item.getEarnTotal() != null || item.getAror() != null)) {
                String earnWeek = item.getEarnWeek() == null ? "-" : item.getEarnWeek() + "%";
                String earnMonth = item.getEarnMonth() == null ? "-" : item.getEarnMonth() + "%";
                String earnTotal = item.getEarnTotal() == null ? "-" : item.getEarnTotal() + "%";
                String aror = item.getAror() == null ? "-" : item.getAror() + "%";
                return ajax(String.format(result, productId, earnWeek, earnMonth, earnTotal, aror));
            }

            // 无手写数据通过程序计算
            // 查找最近一条净值数据
            ProductNetValue lastPnv = productNetValueService.getByProductId(productId);

            if (lastPnv == null) {
                return ajax(String.format(result, productId, "-", "-", "-", "-"));
            }

            Date today = lastPnv.getDate();
            Calendar todayCalc = Calendar.getInstance();
            todayCalc.setTime(today);

            Calendar todayCalc_7 = Calendar.getInstance();
            todayCalc_7.setTime(today);
            todayCalc_7.add(Calendar.DAY_OF_YEAR, -8);

            Calendar todayCalc_30 = Calendar.getInstance();
            todayCalc_30.setTime(today);
            todayCalc_30.add(Calendar.DAY_OF_YEAR, -31);

            Date today_7 = todayCalc_7.getTime();
            Date today_30 = todayCalc_30.getTime();

            ProductNetValue lastWeekPnv = productNetValueService.getByProductId(productId, today_7, today);
            ProductNetValue lastMonthPnv = productNetValueService.getByProductId(productId, today_30, today);
            ProductNetValue firstPnv = productNetValueService.getInceptionNetValue(productId);

            logger.info(String.format("首次查询值:[%s], 对象ID:[%s]", DateUtils.formatDateTime(firstPnv.getDate()), firstPnv.getId()));
            logger.info(String.format("今日查询值:[%s], 对象ID:[%s]", DateUtils.formatDateTime(today), lastPnv.getId()));
            logger.info(String.format("上周起始查询值:[%s], 对象ID:[%s]", DateUtils.formatDateTime(today_7), lastWeekPnv.getId()));
            logger.info(String.format("上月起始查询值:[%s], 对象ID:[%s]", DateUtils.formatDateTime(today_30), lastMonthPnv.getId()));

            // 本周累计净值
            double thisWeekNetValue = lastPnv.getTrustValueAdd();
            logger.info("本周净值 : " + thisWeekNetValue);
            // 上周累计净值
            double lastWeekNetValue = lastWeekPnv.getTrustValueAdd();
            logger.info("上周净值 : " + lastWeekNetValue);
            // 本月累计净值
            double thisMonthNetValue = lastPnv.getTrustValueAdd();
            logger.info("本月净值 : " + thisMonthNetValue);
            // 上月累计净值
            double lastMonthNetValue = lastMonthPnv.getTrustValueAdd();
            logger.info("上月净值 : " + lastMonthNetValue);

            String earnWeek = String.format("%.2f", (thisWeekNetValue / lastWeekNetValue - 1) * 100) + "%";
            String earnMonth = String.format("%.2f", (thisMonthNetValue / lastMonthNetValue - 1) * 100) + "%";
            String earnTotal = String.format("%.2f", (lastPnv.getTrustValueAdd() - 1) * 100) + "%";
            String aror = String.format("%.2f", (lastPnv.getTrustValueAdd() - 1) * 365 / (daysBetween(firstPnv.getDate(), new Date())) * 100) + "%";
            return ajax(String.format(result, productId, earnWeek, earnMonth, earnTotal, aror));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ajax(String.format(result, productId, "-", "-", "-", "-"));
        }
    }

    /**
     * 计算两个日期之间相差的天数
     * @param smdate 较小的时间
     * @param bdate 较大的时间
     * @return 相差天数
     */
    public static int daysBetween(Date smdate, Date bdate) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        smdate = sdf.parse(sdf.format(smdate));
        bdate = sdf.parse(sdf.format(bdate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

}