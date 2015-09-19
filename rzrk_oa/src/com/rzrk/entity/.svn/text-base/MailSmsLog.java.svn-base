/*
 * *
 *  * Project Name: rzrk Web
 *  * Confidential and Proprietary
 *  * Copyright (C) 2011 By
 *  * rzrk Company
 *  * All Rights Reserved
 *
 */

package com.rzrk.entity;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Index;

import javax.persistence.*;

/**mail & sms 发送日志
 * Created with IntelliJ IDEA.
 * User: Ypu
 * Date: 12-5-29
 * Time: 下午6:23
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class MailSmsLog extends BaseEntity{

    private static final long serialVersionUID = 647150295052860307L;

    public enum Type{
        email,sms
    }

    public enum Status{
        success,error
    }

    private String receiver ;

    private Member member ;

    private Type type;

    private Status status;

    private String subject;

    private String desc;

    @Column
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Column
    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    @ForeignKey(name = "fk_mailsms_log_member")
    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
    @Index(name="idx_mailsms_type")
    @Enumerated
    @Column
    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Enumerated
    @Column
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
    @Column(name="description")
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
