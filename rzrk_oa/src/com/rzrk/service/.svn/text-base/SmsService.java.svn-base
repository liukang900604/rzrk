/*
 * *
 *  * Project Name: rzrk Web
 *  * Confidential and Proprietary
 *  * Copyright (C) 2011 By
 *  * rzrk Company
 *  * All Rights Reserved
 *
 */

package com.rzrk.service;

import com.rzrk.RzrkException;
import com.rzrk.entity.Member;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Ypu
 * Date: 12-5-28
 * Time: 下午4:28
 * To change this template use File | Settings | File Templates.
 */
public interface SmsService {

//    public void sendSms(String templatePath, Map<String, Object> data,String receive);
//
//    public void sendSms(String templatePath, Map<String, Object> data,List<String> receiverList);

    public void sendSmsTest(String host,String username,String password,String mobile)  throws RzrkException;

    public void sendPasswordRecoverSms(Member member,String validateCode)  throws RzrkException;

//    public void sendValidateSms(Member member,String validateCode) throws UnicornException;

}
