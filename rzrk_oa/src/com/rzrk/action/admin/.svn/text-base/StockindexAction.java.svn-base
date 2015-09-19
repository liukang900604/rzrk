package com.rzrk.action.admin;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.rzrk.Constants.Stock;
import com.rzrk.bean.Pager.Order;
import com.rzrk.entity.StockIndex;
import com.rzrk.service.StockIndexService;
import com.rzrk.util.date.DateUtils;

@ParentPackage("admin")
public class StockindexAction extends BaseAdminAction {

    private static final long serialVersionUID = 1L;

    @Resource(name = "stockIndexServiceImpl")
    private StockIndexService stockIndexService;

    /** 单条股指对象 */
    private StockIndex sindex;

    // 列表
    public String list() {
        StringBuffer log = new StringBuffer();
        log.append(String.format("目的 : %s ; ", "获取股指列表"));

        try {
            // 获取参数
            String stockId = getRequest().getParameter("stockId");
            log.append(String.format("股票ID : %s ; ", stockId));
            if (isNull(stockId)) {
                log.append(" --> 必要参数为空!");
                logger.error(log.toString());
                return ERROR;
            }
            Stock stock = Stock.getStockById(stockId);
            if (stock == null) {
                log.append(" --> 股票对象不存在!");
                logger.error(log.toString());
                return ERROR;
            }
            getRequest().setAttribute("stock", stock);

            String startDateStr = getRequest().getParameter("startDate");
            log.append(String.format("起始时间 : %s ; ", startDateStr));
            Date startDate = null;
            if (!isNull(startDateStr)) {
                startDate = DateUtils.parseDate(startDateStr);
                getRequest().setAttribute("startDate", startDateStr);
            }

            String endDateStr = getRequest().getParameter("endDate");
            log.append(String.format("结束时间 : %s ; ", endDateStr));
            Date endDate = null;
            if (!isNull(endDateStr)) {
                endDate = DateUtils.parseDate(endDateStr);
                getRequest().setAttribute("endDate", endDateStr);
            }

            if (startDate != null && endDate != null) {
                if (startDate.after(endDate)) {
                    log.append(" --> 起始时间不能大于结束时间");
                    logger.error(log.toString());
                    return ERROR;
                }
            }

            // 执行逻辑
            pager.setOrderBy("date");
            pager.setOrder(Order.desc);
            pager = stockIndexService.findPager(pager, Restrictions.eq("stockId", stockId), startDate == null ? null : Restrictions.gt("date", DateUtils.formatStockDate(startDate)),
                    startDate == null ? null : Restrictions.lt("date", DateUtils.formatStockDate(endDate)));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            log.append(" --> 操作过程异常!");
            logger.error(log.toString());
            return ERROR;
        }

        return LIST;
    }

    public String ajaxGetList(){
		processAjaxPagerRequestParameter();
		if (pager.getKeyword() != null && pager.getKeyword().trim().length() != 0){
			pager.setKeyword(pager.getKeyword().replace("_", ""));
		}
		pager = stockIndexService.findPager(pager);
		List<StockIndex> siList = (List<StockIndex>) pager.getResult();
			
		
		JSONArray jsonObj = new JSONArray();
		for(int i = 0; i < siList.size(); i++ ){
			StockIndex temp = siList.get(i);
	        Map<String,Object> map = new HashMap<String,Object>(); 
	        
	        map.put("id", temp.getId()); 
	        map.put("date", temp.getDate());
	        map.put("start",temp.getStart());
	        map.put("end", temp.getEnd());
	        map.put("max", temp.getMax());
	        map.put("min", temp.getMin());
	        map.put("createDate", DateUtils.formatDate(temp.getCreateDate(), "yyyy-MM-dd"));
	        jsonObj.add(map);
		}
         
        Map<String,Object> map = new HashMap<String,Object>(); 
        
        map.put("total", pager.getTotalCount()); 
        map.put("rows", jsonObj); 
        
		return ajax(map);
    }
    public String ajaxCheckStockIndexDate() {
        StringBuffer log = new StringBuffer();
        log.append(String.format("目的 : %s ; ", "异步校验股指日期是否可添加"));
        try {
            // 验证参数
            String dateStr = getRequest().getParameter("stockDate");
            if (isNull(dateStr)) {
                log.append(" --> 日期为空!");
                logger.error(log.toString());
                getResponse().getWriter().write("false");
                return NONE;
            }

            log.append(String.format("结算日 : %s ; ", dateStr));
            dateStr = dateStr.trim();
            // 验证日期合法性
            Date date = DateUtils.parseDate(dateStr);

            // 查询数据库是否已存在
            // TODO 此处直接使用默认的股票!!
            List<StockIndex> stockIndexList = stockIndexService.getStockIndexByDate(date);
            log.append(String.format("获取股指信息 : %s条 ; ", stockIndexList == null ? 0 : stockIndexList.size()));
            if (stockIndexList == null) {
                getResponse().getWriter().write("true");
                log.append(" --> 股指信息不存在,允许添加!");
                logger.info(log.toString());
            } else {
                getResponse().getWriter().write("false");
                log.append(" --> 股指信息存在,不允许添加!");
                logger.info(log.toString());
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            log.append(" --> 校验过程异常!");
            logger.error(log.toString());
            try {
                getResponse().getWriter().write("false");
            } catch (Exception e2) {
                logger.error(e2.getMessage(), e2);
            }
        }
        return NONE;
    }

    // 删除
    public String delete() {
        stockIndexService.delete(ids);
        logger.info(String.format("删除股指ID=[%s]", StringUtils.join(ids, ",")));
        return ajax(Status.success, "删除成功!");
    }

    // 添加
    public String add() {
        StringBuffer log = new StringBuffer();
        log.append("目的 : 执行股指的添加操作 ; ");
        try {
            getRequest().setAttribute("command", "添加");
            String stockId = getRequest().getParameter("stockId");
            log.append(String.format("股票ID : %s ; ", stockId));

            if (isNull(stockId)) {
                log.append(" --> 股票ID不能为空!");
                logger.error(log.toString());
                return ERROR;
            }

            if (Stock.getStockById(stockId) == null) {
                log.append(" --> 非法的股票ID!");
                logger.error(log.toString());
                return ERROR;
            }

            getRequest().setAttribute("stock", Stock.getStockById(stockId));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            log.append(" --> 执行过程异常!");
            logger.error(log.toString());
            return ERROR;
        }

        return INPUT;
    }

    // 编辑
    public String edit() {
        StringBuffer log = new StringBuffer();
        log.append("目的 : 执行股指的编辑操作 ; ");
        try {
            getRequest().setAttribute("command", "编辑");
            String stockId = getRequest().getParameter("stockId");
            log.append(String.format("股票ID : %s ; ", stockId));

            if (isNull(stockId)) {
                log.append(" --> 股票ID不能为空!");
                logger.error(log.toString());
                return ERROR;
            }

            if (Stock.getStockById(stockId) == null) {
                log.append(" --> 非法的股票ID!");
                logger.error(log.toString());
                return ERROR;
            }

            sindex = stockIndexService.get(id);
            sindex.setDate(DateUtils.formatDate(DateUtils.parseDate(sindex.getDate(), "yyyyMMdd"), "yyyy-MM-dd"));
            getRequest().setAttribute("stock", Stock.getStockById(stockId));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            log.append(" --> 执行过程异常!");
            logger.error(log.toString());
            return ERROR;
        }

        return INPUT;
    }

    // 保存添加
    @InputConfig(resultName = "error")
    public String save() throws Exception {
        StringBuffer log = new StringBuffer();
        log.append("目的 : 执行股指的添加保存 ; ");
        try {
            // 获取参数
            String stockId = sindex.getStockId();
            log.append(String.format("股票ID : %s ; ", stockId));
            if (isNull(stockId)) {
                log.append(" --> 必要参数为空!");
                logger.error(log.toString());
                return ERROR;
            }
            Stock stock = Stock.getStockById(stockId);
            if (stock == null) {
                log.append(" --> 股票对象不存在!");
                logger.error(log.toString());
                return ERROR;
            }

            // 校验
            log.append(String.format("开盘 : %s ; ", sindex.getStart()));
            log.append(String.format("收盘 : %s ; ", sindex.getEnd()));
            log.append(String.format("最高 : %s ; ", sindex.getMax()));
            log.append(String.format("最低 : %s ; ", sindex.getMin()));

            if (sindex.getStart() == null || sindex.getEnd() == null || sindex.getMax() == null || sindex.getMin() == null) {
                log.append(" --> 必要参数为空!");
                logger.error(log.toString());
                return ERROR;
            }

            String dateStr = sindex.getDate();
            if (isNull(dateStr)) {
                log.append(" --> 结算日期为空!");
                logger.error(log.toString());
                return ERROR;
            }
            log.append(String.format("结算日 : %s ; ", dateStr));
            dateStr = dateStr.trim();
            // 验证日期合法性
            Date date = DateUtils.parseDate(dateStr);

            // 查询数据库是否已存在
            // TODO 此处直接使用默认的股票!!
            List<StockIndex> stockIndexList = stockIndexService.getStockIndexByDate(date);
            log.append(String.format("获取股指信息 : %s条 ; ", stockIndexList == null ? 0 : stockIndexList.size()));
            sindex.setDate(DateUtils.formatStockDate(date));
            if (stockIndexList == null) {
                log.append(" --> 股指信息不存在,允许添加!");
                stockIndexService.save(sindex);
                log.append(" --> 保存成功!");
                logger.info(log.toString());
            } else {
                log.append(" --> 股指信息存在,不允许添加!");
                logger.error(log.toString());
                return ERROR;
            }

            redirectUrl = "stockindex!list.action?stockId=" + stock.getId();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            log.append(" --> 执行过程异常!");
            logger.error(log.toString());
            return ERROR;
        }
        return SUCCESS;
    }

    // 保存修改
    @InputConfig(resultName = "error")
    public String update() {
        try {
            StockIndex persistent = stockIndexService.get(id);
            BeanUtils.copyProperties(sindex, persistent, StockIndex.getUnUpdateField());
            stockIndexService.update(persistent);
            redirectUrl = "stockindex!list.action?stockId=" + persistent.getStockId();
            logger.info(String.format("更新股指,股票ID=[%s],股指ID=[%s],date=[%s]成功!", persistent.getStockId(), id, persistent.getDate()));
            return SUCCESS;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            logger.info(String.format("更新股指,股票ID=[%s],股指ID=[%s]异常!", sindex.getStockId(), id));
            return ERROR;
        }
    }

    public StockIndex getSindex() {
        return sindex;
    }

    public void setSindex(StockIndex sindex) {
        this.sindex = sindex;
    }

}