package com.rzrk.dao.impl;

import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.rzrk.dao.MemberProductHoldLogDao;
import com.rzrk.entity.Member;
import com.rzrk.entity.MemberProductHold;
import com.rzrk.entity.MemberProductHoldLog;

/**
 * Dao实现类-客户产品操作日志
 * @version $Id$
 * @author nerve
 * @since 2013-10-10 下午8:06:59
 */
@Repository("memberProductHoldLogDaoImpl")
public class MemberProductHoldLogDaoImpl extends BaseDaoImpl<MemberProductHoldLog, String> implements MemberProductHoldLogDao {

    @SuppressWarnings("unchecked")
    @Override
    public List<MemberProductHoldLog> findByConditions(ListOrderedMap params) {
        StringBuffer hql = new StringBuffer("from MemberProductHoldLog where 1 = 1");

        for (int i = 0; i < params.size(); i++) {
            String key = (String) params.get(i);
            String value = (String) params.getValue(i);
            hql.append(String.format(" and %s = %s", key, value));
        }

        List<MemberProductHoldLog> list = getSession().createQuery(hql.toString()).list();
        return (list == null || list.isEmpty()) ? null : list;
    }

	@Override
	public List<MemberProductHoldLog> findByMemberId(String memberId) {
		Query query=null;
		String hql = "from MemberProductHoldLog as members where members.memberId='"+memberId+"' order by members.actionDate desc";
		query=getSession().createQuery(hql);
		List<MemberProductHoldLog> list=query.list();
		if(list!=null&&list.size()>0){
			return list;
		}else{
			return null;
		}
	}

	@Override
	public MemberProductHoldLog findMemberHoldLast(String memberId) {
		Query query=null;
		String hql = "from MemberProductHoldLog as members where members.memberId='"+memberId+"' order by actionDate asc";
		query=getSession().createQuery(hql);
		List<MemberProductHoldLog> list=query.list();
		if(list!=null&&list.size()>0){
			return ((MemberProductHoldLog)list.get(0));
		}else{
			return null;
		}
	}

}