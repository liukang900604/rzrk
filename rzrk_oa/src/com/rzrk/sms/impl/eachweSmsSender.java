/*
 * *
 *  * Project Name: rzrk Web
 *  * Confidential and Proprietary
 *  * Copyright (C) 2011 By
 *  * rzrk Company
 *  * All Rights Reserved
 *
 */

package com.rzrk.sms.impl;

import com.rzrk.RzrkException;
import com.rzrk.sms.SmsSender;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * 一纬通用短信接口
 * Created with IntelliJ IDEA.
 * User: Ypu
 * Date: 12-5-29
 * Time: 上午9:49
 * To change this template use File | Settings | File Templates.
 */
public class eachweSmsSender implements SmsSender {

    private String host;

    private String username;

    private String password;

    private String method = "sendsms";

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    @Override
    public void setHost(String host) {
        this.host = host;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int sendSms(String content, String receiver) throws RzrkException {
        URL url = null;
        HttpURLConnection httpurlconnection = null;
        try {
            url = new URL(host);
            httpurlconnection = (HttpURLConnection) url.openConnection();
            httpurlconnection.setDoOutput(true);
            httpurlconnection.setRequestMethod("POST");
            String param = "username=" + username + "&password=" + password + "&method=" + method + "&mobile=" + receiver + "&msg=" + content;

            httpurlconnection.getOutputStream().write(param.getBytes("utf8"));
            httpurlconnection.getOutputStream().flush();
            httpurlconnection.getOutputStream().close();

            int code = httpurlconnection.getResponseCode();
            String message = httpurlconnection.getResponseMessage();
            // String bakmsg=httpurlconnection.getOutputStream();
            System.out.println("code :" + code);

            int status =1;

            BufferedReader br = new BufferedReader(new InputStreamReader(httpurlconnection.getInputStream()));
            httpurlconnection.connect();
            String line = br.readLine();
            System.out.println(line);

            int start = line.indexOf("<error>");
            if (start > -1) {
                int end = line.indexOf("</error>");
                if (end > start && line.substring(start + 7, end).equals("0")) {
                    status = 0;
                }
            }

//            Document xmlDoc = DocumentHelper.parseText(line);             当返回不正常时，解析会非常慢，耗费资源  ypu
//            Node node = xmlDoc.selectSingleNode("/response/error");
//            if (node != null && node.getText() != null && node.getText().equals("0")) {
//                status = 0;
//            }
            System.out.println("status : " + status);
            return status;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RzrkException("send sms error");
        } finally {
            if (httpurlconnection != null)
                httpurlconnection.disconnect();
        }
    }

    @Override
    public int sendSms(String content, List<String> receiverList) throws RzrkException {
        StringBuilder receivers = new StringBuilder();
        for (String mobile : receiverList) {
            receivers.append(mobile).append(",");
        }
        return this.sendSms(content, receivers.toString());
    }

//    @Override
//    public void sendSms(List<String> contentList, List<String> receiverList) {
//        //To change body of implemented methods use File | Settings | File Templates.
//    }
}
