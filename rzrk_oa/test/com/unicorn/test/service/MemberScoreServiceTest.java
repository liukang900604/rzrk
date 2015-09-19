/*
 * *
 *  * Project Name: Unicorn Online Shopping Center
 *  * Confidential and Proprietary
 *  * Copyright (C) 2011 By
 *  * Unicorn Development Company
 *  * All Rights Reserved
 *
 */

package com.rzrk.test.service;

import com.rzrk.UnicornException;
import com.rzrk.bean.Pager;
import com.rzrk.bean.Score;
import com.rzrk.entity.Brand;
import com.rzrk.entity.Member;
import com.rzrk.entity.Order;
import com.rzrk.entity.Product;
import com.rzrk.service.*;
import org.junit.Test;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;
import org.springframework.util.Assert;

import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Ypu
 * Date: 12-5-25
 * Time: 下午4:14
 * To change this template use File | Settings | File Templates.
 */
public class MemberScoreServiceTest  extends AbstractDependencyInjectionSpringContextTests {

    @Override
    protected String[] getConfigLocations() {
        return new String[] {
                "/applicationContext-test.xml","/applicationContext-cache.xml","/applicationContext-security.xml"
        };
    }

    @Test
    public void testAddScore(){
        MemberScoreService memberScoreService = (MemberScoreService)applicationContext.getBean("memberScoreServiceImpl");

        Set<Product> productSet = getProducts(1);
        Set<Member> memberSet = getMembers(1);
        Set<Order> orderSet = getOrders(1);

        Assert.notEmpty(productSet);
        Assert.notEmpty(memberSet);
        Assert.notEmpty(orderSet);

        Score score = new Score();
        score.setScore(10);
        score.setType(Score.ScoreType.payment);
        score.setMember(memberSet.iterator().next());
        score.setProduct(productSet.iterator().next());
        score.setOrder(orderSet.iterator().next());
        try {
            memberScoreService.addScore(score);
        } catch (UnicornException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    @Test
    public void testAddTypeScore(){
        MemberScoreService memberScoreService = (MemberScoreService)applicationContext.getBean("memberScoreServiceImpl");
        Set<Member> memberSet = getMembers(1);

        Assert.notEmpty(memberSet);

        try {
            memberScoreService.addScore(memberSet.iterator().next(), Score.ScoreType.login);
        } catch (UnicornException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    @Test
    public void testAddOrderScore(){
        MemberScoreService memberScoreService = (MemberScoreService)applicationContext.getBean("memberScoreServiceImpl");

        Set<Product> productSet = getProducts(1);
        Set<Member> memberSet = getMembers(1);
        Set<Order> orderSet = getOrders(1);

        Assert.notEmpty(productSet);
        Assert.notEmpty(memberSet);
        Assert.notEmpty(orderSet);

        try {
            memberScoreService.addOrderScore(orderSet.iterator().next().getId());
        } catch (UnicornException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    @Test
    public void testBackOrderScore(){
        MemberScoreService memberScoreService = (MemberScoreService)applicationContext.getBean("memberScoreServiceImpl");

        Set<Product> productSet = getProducts(1);
        Set<Member> memberSet = getMembers(1);
        Set<Order> orderSet = getOrders(1);

        Assert.notEmpty(productSet);
        Assert.notEmpty(memberSet);
        Assert.notEmpty(orderSet);

        try {
            memberScoreService.backOrderScore(orderSet.iterator().next().getId());
        } catch (UnicornException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public Set<Member> getMembers(int num) {
        MemberService memberService =  (MemberService)applicationContext.getBean("memberServiceImpl");
        Pager pager = new Pager();
        pager.setPageSize(num);
        pager = memberService.findPager(pager);

        return new HashSet(pager.getResult());
    }

    public Set<Product> getProducts(int num) {
        ProductService productService =  (ProductService)applicationContext.getBean("productServiceImpl");
        Pager pager = new Pager();
        pager.setPageSize(num);
        pager = productService.findPager(pager);
        return new HashSet(pager.getResult());
    }

    public Set<Order> getOrders(int num) {
        OrderService productService =  (OrderService)applicationContext.getBean("orderServiceImpl");
        Pager pager = new Pager();
        pager.setPageSize(num);
        pager = productService.findPager(pager);
        return new HashSet(pager.getResult());
    }



}
