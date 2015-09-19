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
import com.rzrk.entity.Brand;
import com.rzrk.entity.BrandCircle;
import com.rzrk.entity.Member;
import com.rzrk.service.BrandCircleService;
import com.rzrk.service.BrandService;
import com.rzrk.service.MemberService;
import org.junit.Test;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Ypu
 * Date: 12-5-22
 * Time: 下午12:15
 * To change this template use File | Settings | File Templates.
 */
public class BrandCircleServiceTest extends AbstractDependencyInjectionSpringContextTests {
    @Override
    protected String[] getConfigLocations() {
        return new String[] {
                "/applicationContext-test.xml","/applicationContext-cache.xml","/applicationContext-security.xml"
        };
    }

    @Test
    public void testAddBrandCircle(){

        BrandCircleService brandCircleService = (BrandCircleService)applicationContext.getBean("brandCircleServiceImpl");

        Set<Member> members = getMembers(5);

        Assert.notEmpty(members);

        Set<Brand> brands = getBrands(5);

        BrandCircle bc = new BrandCircle();
        bc.setName("Test");
//        bc.setMembers(new HashSet<Member>(members));
        bc.setBrands(new HashSet<Brand>(brands));
        bc.setCreateDate(new Date());

        brandCircleService.save(bc);

    }

    @Test
    public void testUpdateBrandCircle(){
        BrandCircleService brandCircleService = (BrandCircleService)applicationContext.getBean("brandCircleServiceImpl");

        BrandCircle bc = getBrandCircle();
        Assert.notNull(bc);
        bc.setName("HAHA");

        brandCircleService.update(bc);
    }

    @Test
    public void testListLinkedBrand(){
        BrandCircleService brandCircleService = (BrandCircleService)applicationContext.getBean("brandCircleServiceImpl");
        Assert.notNull(brandCircleService);

        List<Brand> brandList = brandCircleService.listLinkedBrand("4028918130316046013031d95ebc003c");
        Assert.notEmpty(brandList);

        for(Brand b: brandList){
            System.out.print(b.getId() + "  ");
            System.out.println(b.getName());
        }
    }

//    @Test
//    public void testAddMember(){
//
//        BrandCircleService brandCircleService = (BrandCircleService)applicationContext.getBean("brandCircleServiceImpl");
//
//        BrandCircle bc = getBrandCircle();
//        Assert.notNull(bc);
//
//        Set<Member> members = getMembers(5);
//        Assert.notEmpty(members);
//
//        String[] memberIds = new String[members.size()];
//        int i = 0;
//        for(Member m:members){
//            memberIds[i] = m.getId();
//            i++;
//        }
//
////        brandCircleService.addMembers(bc.getId(), memberIds);
//    }
//    @Test
//    public void testAddBrand(){
//        BrandCircleService brandCircleService = (BrandCircleService)applicationContext.getBean("brandCircleServiceImpl");
//
//        BrandCircle bc = getBrandCircle();
//        Assert.notNull(bc);
//
//        Set<Brand> brands = getBrands(8);
//        Assert.notEmpty(brands);
//
//        String[] brandIds = new String[brands.size()];
//        int i = 0;
//        for(Brand m:brands){
//            brandIds[i] = m.getId();
//            i++;
//        }
//
//        brandCircleService.addBrands(bc.getId(), brandIds);
//
//    }
//    @Test
//    public void testRemoveBrand(){
//
//        BrandCircleService brandCircleService = (BrandCircleService)applicationContext.getBean("brandCircleServiceImpl");
//
//        BrandCircle bc = getBrandCircle();
//        Assert.notNull(bc);
//
//        Pager pager = new Pager();
//        pager.setPageSize(2);
//
//        pager = brandCircleService.listBrands(bc.getId(),pager);
//
//        Assert.notEmpty(pager.getResult());
//
//        List<Brand> brands = (List<Brand>)pager.getResult();
//        String[] brandIds = new String[brands.size()];
//        int i = 0;
//        for(Brand m:brands){
//            brandIds[i] = m.getId();
//            i++;
//        }
//        brandCircleService.removeBrands(bc.getId(),brandIds);
//
//    }
//    @Test
//    public void testRemoveMember(){
//        BrandCircleService brandCircleService = (BrandCircleService)applicationContext.getBean("brandCircleServiceImpl");
//
//        BrandCircle bc = getBrandCircle();
//        Assert.notNull(bc);
//
//        Pager pager = new Pager();
//        pager.setPageSize(2);
//
//        pager = brandCircleService.listMembers(bc.getId(),pager);
//
//        Assert.notEmpty(pager.getResult());
//
//        List<Member> members = (List<Member>)pager.getResult();
//        String[] memberIds = new String[members.size()];
//        int i = 0;
//        for(Member m:members){
//            memberIds[i] = m.getId();
//            i++;
//        }
//        brandCircleService.removeMembers(bc.getId(), memberIds);
//
//    }

//    @Test
//    public void testListBrands(){
//        BrandCircleService brandCircleService = (BrandCircleService)applicationContext.getBean("brandCircleServiceImpl");
//
//        BrandCircle bc = getBrandCircle();
//        assertNotNull(bc);
//
//        Pager paper = new Pager();
//        paper.setPageSize(2);
//        paper.setPageNumber(1);
//        paper = brandCircleService.listBrands(bc.getId(),paper);
//        System.out.println(paper.getTotalCount());
//
//        List<Brand> brands = (List<Brand>)paper.getResult();
//        for(Brand b:brands){
//            System.out.println(" brand name : " + b.getName());
//        }
//    }

//    @Test
//    public void testListMembers(){
//        BrandCircleService brandCircleService = (BrandCircleService)applicationContext.getBean("brandCircleServiceImpl");
//
//        BrandCircle bc = getBrandCircle();
//        assertNotNull(bc);
//
//        Pager paper = new Pager();
//        paper.setPageSize(10);
//        paper.setPageNumber(1);
//        paper = brandCircleService.listMembers(bc.getId(), paper);
//        System.out.println(paper.getTotalCount());
//
//        List<Member> member = (List<Member>)paper.getResult();
//        for(Member b:member){
//            System.out.println(" member name : " + b.getUsername());
//        }
//    }
//
//    @Test
//    public void testListBrandCircles(){
//        BrandCircleService brandCircleService = (BrandCircleService)applicationContext.getBean("brandCircleServiceImpl");
//
//        Set<Brand>  brands = getBrands(1);
//        assertEquals(brands.size(),1);
//
//        Pager paper = new Pager();
//        paper.setPageSize(10);
//        paper.setPageNumber(1);
//        paper = brandCircleService.listBrandCircles(brands.iterator().next().getId(),paper);
//        System.out.println(paper.getTotalCount());
//
//        List<BrandCircle> member = (List<BrandCircle>)paper.getResult();
//        for(BrandCircle b:member){
//            System.out.println(" brandCircle name : " + b.getName());
//        }
//    }
//
//    @Test
//    public void testGetBrandCircle(){
//        BrandCircleService brandCircleService = (BrandCircleService)applicationContext.getBean("brandCircleServiceImpl");
//
//        BrandCircle bc = getBrandCircle();
//        assertNotNull(bc);
//
//        Set<Brand> brands = getBrands(1);
//        assertEquals(brands.size(),1);
//
//        boolean rt = brandCircleService.containBrand(bc.getId(),brands.iterator().next().getId());
//        assertEquals(rt,true);
//    }

    public Set<Member> getMembers(int num) {
        MemberService memberService =  (MemberService)applicationContext.getBean("memberServiceImpl");
        Pager pager = new Pager();
        pager.setPageSize(num);
        pager = memberService.findPager(pager);

        return new HashSet(pager.getResult());
    }

    public Set<Brand> getBrands(int num) {
        BrandService  brandService =  (BrandService)applicationContext.getBean("brandServiceImpl");
        Pager pager = new Pager();
        pager.setPageSize(num);
        pager = brandService.findPager(pager);

        return new HashSet(pager.getResult());
    }

    public BrandCircle getBrandCircle(){
        BrandCircleService brandCircleService = (BrandCircleService)applicationContext.getBean("brandCircleServiceImpl");
        Pager pager = new Pager();
        pager.setPageSize(1);
        pager = brandCircleService.findPager(pager);
        if(pager.getTotalCount() > 0){
            return (BrandCircle)pager.getResult().get(0);
        }else{
            return null;
        }
    }
}
