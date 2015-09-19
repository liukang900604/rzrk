package com.rzrk.dao;

import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;

import com.rzrk.entity.MemberProductHold;
import com.rzrk.entity.MemberProductHoldLog;

/**
 * Dao接口-客户产品操作日志
 * @version $Id$
 * @author nerve
 * @since 2013-10-10 下午7:58:32
 */
public interface MemberProductHoldLogDao extends BaseDao<MemberProductHoldLog, String> {

    public List<MemberProductHoldLog> findByConditions(ListOrderedMap params);
    
    public List<MemberProductHoldLog> findByMemberId(String memberId);
    
    public MemberProductHoldLog findMemberHoldLast(String memberId);

}