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
import org.apache.commons.codec.digest.DigestUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**一纬高级短信接口
 * Created with IntelliJ IDEA.
 * User: Ypu
 * Date: 12-5-29
 * Time: 上午10:10
 * To change this template use File | Settings | File Templates.
 */
public class eachweAdvSmsSender implements SmsSender{

    private String host;

    private String username;

    private String password;

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
    public int sendSms(String content, String receiver)  throws RzrkException {
        URL url = null;
        HttpURLConnection httpurlconnection = null;
        try {
            url = new URL(host);
            httpurlconnection = (HttpURLConnection) url.openConnection();
            httpurlconnection.setDoOutput(true);
            httpurlconnection.setRequestMethod("POST");
            String param = "sn=" + username + "&pwd=" + DigestUtils.md5Hex(username+password) + "&mobile=" + receiver + "&content=" + content;

            httpurlconnection.getOutputStream().write(param.getBytes("utf8"));
            httpurlconnection.getOutputStream().flush();
            httpurlconnection.getOutputStream().close();

            int code = httpurlconnection.getResponseCode();
            String message = httpurlconnection.getResponseMessage();
            // String bakmsg=httpurlconnection.getOutputStream();
            System.out.println("code :" + code);
            System.out.println("response message :" + message);
            //todo 状态码检查
            BufferedReader br = new BufferedReader(new InputStreamReader(httpurlconnection.getInputStream()));
            httpurlconnection.connect();
            String line = br.readLine();
            while (line != null) {
                System.out.println(line);
                line = br.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        } finally {
            if (httpurlconnection != null)
                httpurlconnection.disconnect();
        }
        return 0;
    }

    @Override
    public int sendSms(String content, List<String> receiverList)  throws RzrkException {
        StringBuilder receivers = new StringBuilder();
        for(String mobile:receiverList){
            receivers.append(mobile).append(",");
        }
       return this.sendSms(content,receivers.toString());
    }

//    @Override
//    public void sendSms(List<String> contentList, List<String> receiverList) {
//        //To change body of implemented methods use File | Settings | File Templates.
//    }
}
