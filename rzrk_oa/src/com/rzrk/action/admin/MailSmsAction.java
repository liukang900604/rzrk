/*
 * *
 *  * Project Name: rzrk Web
 *  * Confidential and Proprietary
 *  * Copyright (C) 2011 By
 *  * rzrk Company
 *  * All Rights Reserved
 *
 */

package com.rzrk.action.admin;

import com.rzrk.bean.Search;
import com.rzrk.service.MailSmsLogService;
import org.apache.struts2.convention.annotation.ParentPackage;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * User: Ypu
 * Date: 12-5-29
 * Time: 下午11:07
 * To change this template use File | Settings | File Templates.
 */
@ParentPackage("admin")
public class MailSmsAction extends BaseAdminAction {


    private static final long serialVersionUID = -7066374399108141074L;

    @Resource(name="mailSmsLogServiceImpl")
    private MailSmsLogService mailSmsLogService;

    private Search search;

    public Search getSearch() {
        return search;
    }

    public void setSearch(Search search) {
        this.search = search;
    }

    public String list(){
        pager = mailSmsLogService.findPager(pager,search);
        if(search == null){
            search = new Search();
        }
        return LIST;
    }
}
