/*
 * *
 *  * Project Name: rzrk Web
 *  * Confidential and Proprietary
 *  * Copyright (C) 2011 By
 *  * rzrk Company
 *  * All Rights Reserved
 *
 */

package com.rzrk.dao;

import com.rzrk.bean.Pager;
import com.rzrk.bean.Search;
import com.rzrk.entity.MailSmsLog;
import org.omg.Dynamic.Parameter;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**邮件和短信日志dao
 * Created with IntelliJ IDEA.
 * User: Ypu
 * Date: 12-5-29
 * Time: 下午6:28
 * To change this template use File | Settings | File Templates.
 */
public interface MailSmsLogDao extends BaseDao<MailSmsLog, String>{

    public void save(List<MailSmsLog> messageLogs,Date date,MailSmsLog.Status status);

    public Pager findPager(Pager pager,Search search);

}
