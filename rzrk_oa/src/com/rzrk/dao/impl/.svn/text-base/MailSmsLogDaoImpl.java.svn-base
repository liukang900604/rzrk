/*
 * *
 *  * Project Name: rzrk Web
 *  * Confidential and Proprietary
 *  * Copyright (C) 2011 By
 *  * rzrk Company
 *  * All Rights Reserved
 *
 */

package com.rzrk.dao.impl;

import com.rzrk.bean.Pager;
import com.rzrk.bean.Search;
import com.rzrk.dao.MailSmsLogDao;
import com.rzrk.entity.MailSmsLog;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.jsoup.helper.StringUtil;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Ypu
 * Date: 12-5-29
 * Time: 下午6:30
 * To change this template use File | Settings | File Templates.
 */
@Repository("mailSmsLogDaoImpl")
public class MailSmsLogDaoImpl extends BaseDaoImpl<MailSmsLog, String> implements MailSmsLogDao {

    private DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Transactional
    @Override
    public String save(MailSmsLog entity) {
        return super.save(entity);
    }

    @Transactional
    @Override
    public void save(List<MailSmsLog> messageLogs,Date date,MailSmsLog.Status status) {
        for(MailSmsLog messageLog : messageLogs){
            messageLog.setCreateDate(date);
            messageLog.setStatus(status);
            this.save(messageLog);
        }
    }

    @Override
    public Pager findPager(Pager pager, Search search) {

        if(search == null){
            return this.findPager(pager);
        }

        Criteria criteria = getSession().createCriteria(MailSmsLog.class);
        if(StringUtils.isNotBlank(search.getKeyword())){
            criteria.add(Restrictions.eq("receiver",search.getKeyword()));
        }

        if(StringUtils.isNotBlank(search.getStartDate())){
            try {
                criteria.add(Restrictions.ge("createDate", sdf.parse(search.getStartDate() + " 00:00:00")));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        if(StringUtils.isNotBlank(search.getEndDate())){
            try {
                criteria.add(Restrictions.le("createDate", sdf.parse(search.getEndDate() + " 23:59:59")));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        if(StringUtils.isNotBlank(search.getType())){
            criteria.add(Restrictions.eq("type",MailSmsLog.Type.valueOf(search.getType())));
        }

        if(StringUtils.isNotBlank(search.getStatus())){
            criteria.add(Restrictions.eq("status",MailSmsLog.Status.valueOf(search.getStatus())));
        }

        return this.findPager(pager,criteria);
    }
}
