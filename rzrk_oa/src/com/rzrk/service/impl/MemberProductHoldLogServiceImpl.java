package com.rzrk.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rzrk.dao.MemberProductHoldLogDao;
import com.rzrk.dao.ProductDao;
import com.rzrk.entity.MemberProductHold;
import com.rzrk.entity.MemberProductHoldLog;
import com.rzrk.entity.Product;
import com.rzrk.service.MemberProductHoldLogService;

/**
 * Service实现类-客户产品操作日志
 * @version $Id$
 * @author nerve
 * @since 2013-10-10 下午8:06:13
 */
@Service("memberProductHoldLogServiceImpl")
public class MemberProductHoldLogServiceImpl extends BaseServiceImpl<MemberProductHoldLog, String> implements MemberProductHoldLogService {

    @Resource(name = "memberProductHoldLogDaoImpl")
    private MemberProductHoldLogDao memberProductHoldLogDao;

    @Resource(name = "memberProductHoldLogDaoImpl")
    public void setBaseDao(MemberProductHoldLogDao dao) {
        super.setBaseDao(dao);
    }
    @Resource(name = "productDaoImpl")
    private ProductDao productDao;

	@Override
	public List<MemberProductHoldLog> findByMemberId(String memberId) {
		List<MemberProductHoldLog> list=memberProductHoldLogDao.findByMemberId(memberId);
		if(null!=list&&list.size()>0){
			for(MemberProductHoldLog member :list){
				Product product=productDao.get(member.getProductId());
				member.setProduct(product);
			}
		}
		return list;
	}

	@Override
	public MemberProductHoldLog findMemberHoldLast(String memberId) {
		return memberProductHoldLogDao.findMemberHoldLast(memberId);
	}

}