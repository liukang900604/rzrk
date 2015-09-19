package com.rzrk.dao.impl;

import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.rzrk.dao.MemberProductHoldDao;
import com.rzrk.entity.Member;
import com.rzrk.entity.MemberProductHold;

/**
 * Dao实现类-客户产品
 * @version $Id$
 * @author nerve
 * @since 2013-10-10 下午8:06:59
 */
@Repository("memberProductHoldDaoImpl")
public class MemberProductHoldDaoImpl extends BaseDaoImpl<MemberProductHold, String> implements MemberProductHoldDao {

    @SuppressWarnings("unchecked")
    @Override
    public List<MemberProductHold> findByConditions(ListOrderedMap params) {
        StringBuffer hql = new StringBuffer("from MemberProductHold where 1 = 1");

        for (int i = 0; i < params.size(); i++) {
            String key = (String) params.get(i);
            String value = (String) params.getValue(i);
            hql.append(String.format(" and %s = %s", key, value));
        }

        List<MemberProductHold> list = getSession().createQuery(hql.toString()).list();
        return (list == null || list.isEmpty()) ? null : list;
    }

	@Override
	public List<MemberProductHold> findByMemberId(String memberId) {
		Query query=null;
		String hql = "from MemberProductHold as members where members.memberId='"+memberId+"' group by members.productId";
		query=getSession().createQuery(hql);
		List<MemberProductHold> list=query.list();
		if(list!=null&&list.size()>0){
			return list;
		}else{
			return null;
		}
	}
    
    

}