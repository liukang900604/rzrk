/*
 * *
 *  * Project Name: rzrk Web
 *  * Confidential and Proprietary
 *  * Copyright (C) 2011 By
 *  * rzrk Company
 *  * All Rights Reserved
 *
 */

package com.rzrk.service.impl;

import com.rzrk.bean.Pager;
import com.rzrk.bean.Search;
import com.rzrk.dao.MailSmsLogDao;
import com.rzrk.entity.MailSmsLog;
import com.rzrk.service.MailSmsLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Ypu
 * Date: 12-5-29
 * Time: 下午11:06
 * To change this template use File | Settings | File Templates.
 */
@Service("mailSmsLogServiceImpl")
public class MailSmsLogServiceImpl extends BaseServiceImpl<MailSmsLog,String> implements MailSmsLogService {

    @Resource(name = "mailSmsLogDaoImpl")
    private MailSmsLogDao mailSmsLogDao;

    @Resource(name = "mailSmsLogDaoImpl")
    public void setBaseDao(MailSmsLogDao mailSmsLogDao) {
        super.setBaseDao(mailSmsLogDao);
    }

    @Override
    public Pager findPager(Pager pager, Search search) {
        return mailSmsLogDao.findPager(pager,search);
    }
}
