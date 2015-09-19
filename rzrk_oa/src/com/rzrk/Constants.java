package com.rzrk;

import java.util.HashMap;
import java.util.Map;

/**
 * 常量
 * @version $Id$
 * @author nerve
 * @since 2013-9-26 下午8:58:04
 */
public class Constants {

    public final static Integer YES = 1;
    public final static Integer NO = 0;
    public final static String YES_STR = "1";
    public final static String NO_STR = "0";
    
    public final static Double companyLocationX = 113.96403;     
    public final static Double companyLocationY = 22.545933;
    public final static Double validDistance = 50.00; //打开有效误差距离
    public final static String workStart = "09:00:00"; //上班时间
    public final static String workend = "18:00:00"; //下班时间

    public enum ACTION_TYPE {
        add, sell, buy // 追加，赎回。申购
    }

    /**
     * 股指
     * @version $Id$
     * @author nerve
     * @since 2013-10-12 下午4:50:24
     */
    public static class Stock {

        private String id;// ID

        private String code;// 简称

        private String name;// 中文全称

        public Stock(String id, String code, String name) {
            this.id = id;
            this.code = code;
            this.name = name;
        }

        public final static Stock HS300 = new Stock("1", "HS300", "沪深300");

        private static Map<String, Stock> stockMap = new HashMap<String, Constants.Stock>();

        static {
            stockMap.put(HS300.getId(), HS300);
        }

        public static Stock getStockById(String id) {
            return stockMap.get(id);
        }

        public String getId() {
            return id;
        }

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }

    }

    /** 估值日 */
    public final static Integer VALUATION_DATE = 100;
    /** 成立日 */
    public final static Integer INCEPTION_DATE = 103;
    /** 数据库执行批量操作最大值 */
    public final static Integer BATCH_MAX_ROW = 50;
    
    public static void main(String args[]){
    	System.out.println(String.format("%08d", 100));
    }
}
