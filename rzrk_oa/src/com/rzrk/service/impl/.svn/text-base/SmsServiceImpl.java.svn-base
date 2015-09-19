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

import com.rzrk.RzrkException;
import com.rzrk.bean.Setting;
import com.rzrk.bean.SmsTemplateConfig;
import com.rzrk.bean.TemplateConfig;
import com.rzrk.dao.MailSmsLogDao;
import com.rzrk.entity.MailSmsLog;
import com.rzrk.entity.Member;
import com.rzrk.service.SmsService;
import com.rzrk.sms.SmsSender;
import com.rzrk.util.SettingUtil;
import com.rzrk.util.TemplateConfigUtil;
import freemarker.ext.beans.BeansWrapper;
import freemarker.ext.beans.ResourceBundleModel;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.struts2.views.freemarker.FreemarkerManager;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.context.ServletContextAware;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Ypu
 * Date: 12-5-28
 * Time: 下午2:51
 * To change this template use File | Settings | File Templates.
 */
@Service("smsServiceImpl")
public class SmsServiceImpl implements SmsService,ServletContextAware {

    private ServletContext servletContext;
    @Resource(name = "javaSmsSender")
    private SmsSender smsSender;
    @Resource(name = "freemarkerManager")
    private FreemarkerManager freemarkerManager;
    @Resource(name = "taskExecutor")
    private TaskExecutor taskExecutor;
    @Resource(name = "mailSmsLogDaoImpl")
    private MailSmsLogDao mailSmsLogDao;

    public ServletContext getServletContext() {
        return servletContext;
    }

    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public Map<String, Object> getCommonData() {
        Map<String, Object> commonData = new HashMap<String, Object>();
        ResourceBundle resourceBundle = ResourceBundle.getBundle("i18n");
        ResourceBundleModel resourceBundleModel = new ResourceBundleModel(resourceBundle, new BeansWrapper());
        commonData.put("bundle", resourceBundleModel);
        commonData.put("base", getContextPath());
        commonData.put("setting", SettingUtil.getSetting());
        return commonData;
    }

    public void addSendSmsTask(final String content, final List<String> receiverList,final List<MailSmsLog> messageLogList) {
//        try {
            taskExecutor.execute(
                    new Runnable() {
                        public void run() {
                            Date date = new Date();
                            MailSmsLog.Status status = MailSmsLog.Status.success;
                            try {
                                if(smsSender.sendSms(content, receiverList) == 0){
                                    status = MailSmsLog.Status.success;
                                }else{
                                    status = MailSmsLog.Status.error;
                                }
                            } catch (RzrkException e) {
                                status = MailSmsLog.Status.error;
//                                System.out.println(" ==== sms send error ====");
//                                for(String s: receiverList){
//                                    System.out.println(" receiver : " + s);
//                                }
//                                System.out.println(" content : " + content);
                                e.printStackTrace();
                            } finally {
                                mailSmsLogDao.save(messageLogList, date, status);
                            }
                        }
                    }
            );
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    public void addSendSmsTask(final String content, final String receiver,final MailSmsLog messageLog){
//        try {
            taskExecutor.execute(
                    new Runnable() {
                        public void run() {
                            try {
                                messageLog.setCreateDate(new Date());
                                if(smsSender.sendSms(content,receiver) == 0){
                                    messageLog.setStatus(MailSmsLog.Status.success);
                                }else{
                                    messageLog.setStatus(MailSmsLog.Status.error);
                                    messageLog.setDesc("sms gateway error");
                                }

                            } catch (RzrkException e) {
                                messageLog.setStatus(MailSmsLog.Status.error);

//                                System.out.println(" ==== sms send error ====");
//                                System.out.println("receiver : " + receiver + " content : " + content);
                                e.printStackTrace();
                            }  finally {
                                mailSmsLogDao.save(messageLog);
                            }
                        }
                    }
            );
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    public void sendSms(String templatePath, Map<String, Object> data,String receive, MailSmsLog messageLog)  throws RzrkException{
        try {
            Setting setting = SettingUtil.getSetting();
            SmsSender smsSenderImpl = smsSender;
            smsSenderImpl.setHost(setting.getSmsHost());
            smsSenderImpl.setUsername(setting.getSmsUsername());
            smsSenderImpl.setPassword(setting.getSmsPassword());
            Configuration configuration = freemarkerManager.getConfiguration(servletContext);
            Template template = configuration.getTemplate(templatePath);
            String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, data);
            if(text.length() > 60){
                messageLog.setSubject(text.substring(0,60));
            }else{
                messageLog.setSubject(text);
            }
            addSendSmsTask(text, receive, messageLog);
        } catch (Exception e) {
            messageLog.setCreateDate(new Date());
            messageLog.setStatus(MailSmsLog.Status.error);
            messageLog.setDesc("sendSms error");
            mailSmsLogDao.save(messageLog);
            e.printStackTrace();
            throw new RzrkException("sendSms error");
        }
    }

    public void sendSms(String templatePath, Map<String, Object> data,List<String> receiverList, List<MailSmsLog> messageLog) throws RzrkException {
        try {
            Setting setting = SettingUtil.getSetting();
            SmsSender smsSenderImpl = smsSender;
            smsSenderImpl.setHost(setting.getSmsHost());
            smsSenderImpl.setUsername(setting.getSmsUsername());
            smsSenderImpl.setPassword(setting.getSmsPassword());
            Configuration configuration = freemarkerManager.getConfiguration(servletContext);
            Template template = configuration.getTemplate(templatePath);
            String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, data);

            if(receiverList.size() > 10){
                //按10人一批发送
                int cursor = 0;
                int all = receiverList.size();
                while(all >= (cursor + 10)){
                    addSendSmsTask(text, receiverList.subList(cursor, cursor + 10),messageLog.subList(cursor, cursor + 10));
                    cursor += 10;
                }
                if(all > cursor){
                    addSendSmsTask(text, receiverList.subList(cursor, all),messageLog.subList(cursor, all)); //flush
                }
            }else{
                addSendSmsTask(text, receiverList,messageLog);
            }
        } catch (Exception e) {
            mailSmsLogDao.save(messageLog, new Date(), MailSmsLog.Status.error);
            e.printStackTrace();
        }
    }

    @Override
    public void sendPasswordRecoverSms(Member member,String validateCode)  throws RzrkException{
        Map<String, Object> data =  getCommonData();
        data.put("member", member);
        data.put("validateCode", validateCode);
        TemplateConfig smsTemplateConfig = TemplateConfigUtil.getTemplateConfig(SmsTemplateConfig.PASSWORD_RECOVER);
        if(!smsTemplateConfig.getEnabled()){
            return;
        }
        String templatePath = smsTemplateConfig.getSmsTemplatePath();
        MailSmsLog messageLog = new MailSmsLog();
        messageLog.setMember(member);
        messageLog.setReceiver(member.getEmail());
        messageLog.setType(MailSmsLog.Type.email);
        sendSms(templatePath, data, member.getMobile(),messageLog);
    }

//    @Override
//    public void sendValidateSms(Member member,String validateCode)  throws UnicornException{
//        Map<String, Object> data =  getCommonData();
//        data.put("member", member);
//        data.put("validateCode", validateCode);
//        TemplateConfig smsTemplateConfig = TemplateConfigUtil.getTemplateConfig(SmsTemplateConfig.MOBILE_VALIDATE);
//        String templatePath = smsTemplateConfig.getSmsTemplatePath();
//        sendSms(templatePath, data, member.getMobile());
//    }

    @Override
    public void sendSmsTest(String host,String username,String password,String receiver)  throws RzrkException{
        Setting setting = SettingUtil.getSetting();
        Map<String, Object> data = getCommonData();
        TemplateConfig smsTemplateConfig = TemplateConfigUtil.getTemplateConfig(TemplateConfig.TEST);
        if(!smsTemplateConfig.getEnabled()){
            return;
        }
        String templatePath = smsTemplateConfig.getSmsTemplatePath();
        try {
            Configuration configuration = freemarkerManager.getConfiguration(servletContext);
            Template template = configuration.getTemplate(templatePath);
            String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, data);

            SmsSender smsSenderImpl = smsSender;
            smsSenderImpl.setHost(host);
            smsSenderImpl.setUsername(username);
            smsSenderImpl.setPassword(password);
            int status = smsSenderImpl.sendSms(text, receiver);
            if(status != 0){
                throw  new RzrkException("send error");
            }
        } catch (Exception e) {
            throw  new RzrkException("send error");
        }
    }

    private String getContextPath() {
        if (servletContext.getMajorVersion() < 2 || (servletContext.getMajorVersion() == 2 && servletContext.getMinorVersion() < 5)) {
            return SettingUtil.getSetting().getContextPath();
        } else {
            return servletContext.getContextPath();
        }
    }
}
