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

import com.rzrk.bean.Pager;
import com.rzrk.bean.Score;
import com.rzrk.entity.Brand;
import com.rzrk.entity.Goods;
import com.rzrk.entity.Member;
import com.rzrk.entity.ScoreLog;
import com.rzrk.service.BrandService;
import com.rzrk.service.GoodsService;
import com.rzrk.service.MemberService;
import com.rzrk.service.ScoreLogService;
import org.junit.Test;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;
import org.springframework.util.Assert;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Ypu
 * Date: 12-5-19
 * Time: 下午10:39
 * To change this template use File | Settings | File Templates.
 */

public class ScoreLogServiceTest extends AbstractDependencyInjectionSpringContextTests {

    @Override
    protected String[] getConfigLocations() {
        return new String[] {
                "/applicationContext-test.xml","/applicationContext-cache.xml","/applicationContext-security.xml"
        };
    }

    @Test
    public void testSavePointLog(){

        ScoreLogService logService = (ScoreLogService)applicationContext.getBean("scoreLogServiceImpl");

        Assert.notNull(logService);

        Goods goods = getGoods();
        Assert.notNull(goods);

        Member member = getMember();
        Assert.notNull(member);


        ScoreLog pl = new ScoreLog();
        pl.setScore(10);
        pl.setType(Score.ScoreType.register);
//        pl.setProduct(goods);
        pl.setMember(member);
        logService.save(pl);
        System.out.println(pl.getId());
    }


    @Test
    public void testRemoveScoreByMember(){

        ScoreLogService logService = (ScoreLogService)applicationContext.getBean("scoreLogServiceImpl");

        Assert.notNull(logService);

        Member member = getMember();
        Assert.notNull(member);

        logService.removeAllScoreByMember(member.getId());
    }

    @Test
    public void testCountBrandPoint(){

        ScoreLogService logService = (ScoreLogService)applicationContext.getBean("scoreLogServiceImpl");

        Assert.notNull(logService);

        Member member = getMember();
        Assert.notNull(member);

//        List rs = logService.countBrandScore(member.getId(), 2);
//
//        for(int i = 0 ; i <rs.size();i++){
//            System.out.print(((Object[])rs.get(i))[0]);
//            System.out.print(" : ");
//            System.out.println(((Object[])rs.get(i))[1]);
//        }

    }

    private Goods  getGoods(){
        GoodsService memberService =  (GoodsService)applicationContext.getBean("goodsServiceImpl");
        Pager pager = new Pager();
        pager.setPageSize(1);
        pager = memberService.findPager(pager);

        if(pager.getTotalCount() >0){
            return (Goods)pager.getResult().get(0);
        }else{
            return null;
        }
    }

    public Member getMember() {
        MemberService memberService =  (MemberService)applicationContext.getBean("memberServiceImpl");
        Pager pager = new Pager();
        pager.setPageSize(1);
        pager = memberService.findPager(pager);

        if(pager.getTotalCount() >0){
            return (Member)pager.getResult().get(0);
        }else{
            return null;
        }
    }

    public Brand getBrands() {
        BrandService brandService =  (BrandService)applicationContext.getBean("brandServiceImpl");
        Pager pager = new Pager();
        pager.setPageSize(1);
        pager = brandService.findPager(pager);

        if(pager.getTotalCount() >0){
            return (Brand)pager.getResult().get(0);
        }else{
            return null;
        }
    }

}
