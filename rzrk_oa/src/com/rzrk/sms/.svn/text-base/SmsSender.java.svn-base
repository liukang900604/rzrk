/*
 * *
 *  * Project Name: rzrk Web
 *  * Confidential and Proprietary
 *  * Copyright (C) 2011 By
 *  * rzrk Company
 *  * All Rights Reserved
 *
 */

package com.rzrk.sms;

import com.rzrk.RzrkException;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Ypu
 * Date: 12-5-28
 * Time: 下午4:42
 * To change this template use File | Settings | File Templates.
 */
public interface SmsSender {

    public void setHost(String host);

    public void setUsername(String username);

    public void setPassword(String password);

    public int sendSms(String content,String receiver) throws RzrkException;

    public int sendSms(String contents,List<String> receiverList) throws RzrkException;

//    public void sendSms(List<String> contentList,List<String> receiverList);

}
