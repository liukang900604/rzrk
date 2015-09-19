/**
 * Project Name: rzrk Web
 * Confidential and Proprietary                                                                
 * Copyright (C) 2013 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * Bean类 - 分页
 */

public class Pager {

	public static final Integer MAX_PAGE_SIZE = 500;// 每页最大记录数限制
	
	// 排序方式（递增、递减）
	public enum Order {
		asc, desc
	}

	private int pageNumber = 1;// 当前页码
	private int pageSize = 50;// 每页记录数
	private String searchBy;// 查找字段
	private String keyword;// 查找关键字
	private String orderBy;// 排序字段
	private Order order;// 排序方式
	private int totalCount;// 总记录数
	private List<?> result;// 返回结果
	private List<?> footer;// 页脚统计结果
	private String dateType;//日期类型  
	private String beginDate;//开始时间
	private String endDate;//结束时间
	private String isCunxu = "";
	private String adminId;//用户id
	private String currentId;//处理人Id
	private Map<String,Object> param = new HashMap<String,Object>();//查询语句 参数接收容器  模糊查询  
	private Map<String,Object> numberParam = new HashMap<String,Object>();//查询语句 参数接收容器   非模糊查询
	//private Vector<String>    workArray = new Vector<String>(); //我的工作集合
	private String [] flowList; 
	private String  flowType; // 1:内置流程  2：正常流程
	private String  showAll; //1：全显示  
	
	private String userPlanAdminId;
	
	@Deprecated //在projectService的find中处理了
	private String hasNoRoot;
	
	public String getHasNoRoot() {
		return hasNoRoot;
	}

	public void setHasNoRoot(String hasNoRoot) {
		this.hasNoRoot = hasNoRoot;
	}
	

	public String getUserPlanAdminId() {
		return userPlanAdminId;
	}

	public void setUserPlanAdminId(String userPlanAdminId) {
		this.userPlanAdminId = userPlanAdminId;
	}

	public String getIsCunxu() {
		return isCunxu;
	}

	public void setIsCunxu(String isCunxu) {
		this.isCunxu = isCunxu;
	}

	// 获取总页数
	public int getPageCount() {
		int pageCount = totalCount / pageSize;
		if (totalCount % pageSize > 0) {
			pageCount++;
		}
		return pageCount;
	}

    /**
     * 获取分页列表页码
     * @return
     * @author nerve
     * @since 2013-10-9下午3:16:42
     */
    public List<String> getPageNumList() {
        List<String> resultList = new ArrayList<String>();
        int pageCount = getPageCount();
        // 页码小于8页
        if (pageCount <= 8) {
            for (int i = 1; i <= pageCount; i++) {
                resultList.add(String.valueOf(i));
            }
            return resultList;
        }

        // 页码大于8页
        resultList.add("1");
        // 页码靠前
        if (pageNumber - 3 <= 1) {
            resultList.add("2");
            resultList.add("3");
            resultList.add("4");
            resultList.add("5");
            resultList.add("6");
            resultList.add("...");
            resultList.add(String.valueOf(pageCount));
            return resultList;
        }
        // 页码靠后
        else if (pageNumber + 3 >= pageCount) {
            resultList.add("...");
            resultList.add(String.valueOf(pageCount - 5));
            resultList.add(String.valueOf(pageCount - 4));
            resultList.add(String.valueOf(pageCount - 3));
            resultList.add(String.valueOf(pageCount - 2));
            resultList.add(String.valueOf(pageCount - 1));
            resultList.add(String.valueOf(pageCount));
        }
        // 页码靠中间
        else {
            resultList.add("...");
            resultList.add(String.valueOf(pageNumber - 2));
            resultList.add(String.valueOf(pageNumber - 1));
            resultList.add(String.valueOf(pageNumber));
            resultList.add(String.valueOf(pageNumber + 1));
            resultList.add(String.valueOf(pageNumber + 2));
            resultList.add("...");
            resultList.add(String.valueOf(pageCount));
        }
        return resultList;
    }

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		if (pageNumber < 1) {
			pageNumber = 1;
		}
		this.pageNumber = pageNumber;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		if (pageSize < 1) {
			pageSize = 1;
		} else if (pageSize > MAX_PAGE_SIZE) {
			pageSize = MAX_PAGE_SIZE;
		}
		this.pageSize = pageSize;
	}

	public String getSearchBy() {
		return searchBy;
	}

	public void setSearchBy(String searchBy) {
		this.searchBy = searchBy;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public List<?> getResult() {
		return result;
	}

	public void setResult(List<?> result) {
		this.result = result;
	}
	
	public List<?> getFooter() {
		return footer;
	}

	public void setFooter(List<?> footer) {
		this.footer = footer;
	}

	public Map<String, Object> getParam() {
		return param;
	}

	public void setParam(Map<String, Object> param) {
		this.param = param;
	}

	public Map<String, Object> getNumberParam() {
		return numberParam;
	}

	public void setNumberParam(Map<String, Object> numberParam) {
		this.numberParam = numberParam;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getDateType() {
		return dateType;
	}

	public void setDateType(String dateType) {
		this.dateType = dateType;
	}

	public String getAdminId() {
		return adminId;
	}

	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

	public String getCurrentId() {
		return currentId;
	}

	public void setCurrentId(String currentId) {
		this.currentId = currentId;
	}

	public String[] getFlowList() {
		return flowList;
	}

	public void setFlowList(String[] flowList) {
		this.flowList = flowList;
	}

	public String getFlowType() {
		return flowType;
	}

	public void setFlowType(String flowType) {
		this.flowType = flowType;
	}

	public String getShowAll() {
		return showAll;
	}

	public void setShowAll(String showAll) {
		this.showAll = showAll;
	}
	
	

/*	public Vector<String> getWorkArray() {
		return workArray;
	}

	public void setWorkArray(Vector<String> workArray) {
		this.workArray = workArray;
	}*/

	
	
	

	

}