package com.rzrk.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.stereotype.Service;

import com.rzrk.dao.MemberProductHoldDao;
import com.rzrk.dao.ProductDao;
import com.rzrk.dao.ProductNetValueDao;
import com.rzrk.entity.MemberProductHold;
import com.rzrk.entity.Product;
import com.rzrk.entity.ProductNetValue;
import com.rzrk.service.MemberProductHoldService;

/**
 * Service实现类-客户产品
 * @version $Id$
 * @author nerve
 * @since 2013-10-10 下午8:06:13
 */
@Service("memberProductHoldServiceImpl")
public class MemberProductHoldServiceImpl extends BaseServiceImpl<MemberProductHold, String> implements MemberProductHoldService {

    @Resource(name = "memberProductHoldDaoImpl")
    private MemberProductHoldDao memberProductHoldDao;
    @Resource(name = "productDaoImpl")
    private ProductDao productDao;
    @Resource(name = "productNetValueDaoImpl")
    private ProductNetValueDao productNetValueDao;

    @Override
    public List<MemberProductHold> getAllMemberHold(String memberId) {
        ListOrderedMap list = new ListOrderedMap();
        list.put("memberId", "'" + memberId + "'");
        return memberProductHoldDao.findByConditions(list);
    }

    @Override
    public MemberProductHold getMemberHold(String memberId, String productId) {
        ListOrderedMap list = new ListOrderedMap();
        list.put("memberId", "'" + memberId + "'");
        list.put("productId", "'" + productId + "'");
        List<MemberProductHold> resultList = memberProductHoldDao.findByConditions(list);
        return resultList == null ? null : resultList.get(0);
    }

    @Resource(name = "memberProductHoldDaoImpl")
    public void setBaseDao(MemberProductHoldDao dao) {
        super.setBaseDao(dao);
    }

    @Override
    public List<MemberProductHold> findByMemberId(String memberId) {
        List<MemberProductHold> list = memberProductHoldDao.findByMemberId(memberId);
        if (null != list && list.size() > 0) {
            for (MemberProductHold member : list) {
                Product product = productDao.get(member.getProductId());
                ProductNetValue productNetValue = productNetValueDao.getByProductId(member.getProductId());
                member.setProductNetValue(productNetValue);
                member.setProduct(product);
            }
        }
        return list;
    }

}