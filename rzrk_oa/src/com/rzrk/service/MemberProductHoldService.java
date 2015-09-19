package com.rzrk.service;

import java.util.List;

import com.rzrk.entity.MemberProductHold;

/**
 * Service接口-客户产品
 * @version $Id$
 * @author nerve
 * @since 2013-10-10 下午8:02:26
 */
public interface MemberProductHoldService extends BaseService<MemberProductHold, String> {

    /**
     * 获取某用户持有所有产品情况
     * @param memberId
     * @return
     * @author nerve
     * @since 2013-10-11上午11:08:40
     */
    public List<MemberProductHold> getAllMemberHold(String memberId);

    /**
     * 获取某用户持有某产品情况
     * @param memberId
     * @param productId
     * @return
     * @author nerve
     * @since 2013-10-11上午11:08:55
     */
    public MemberProductHold getMemberHold(String memberId, String productId);
    
    public List<MemberProductHold> findByMemberId(String memberId);

}