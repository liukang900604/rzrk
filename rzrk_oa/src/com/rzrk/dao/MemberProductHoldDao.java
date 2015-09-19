package com.rzrk.dao;

import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;

import com.rzrk.entity.MemberProductHold;

/**
 * Dao接口-客户产品
 * @version $Id$
 * @author nerve
 * @since 2013-10-10 下午7:58:32
 */
public interface MemberProductHoldDao extends BaseDao<MemberProductHold, String> {

    public List<MemberProductHold> findByConditions(ListOrderedMap params);
    
    
    public List<MemberProductHold> findByMemberId(String memberId);

}