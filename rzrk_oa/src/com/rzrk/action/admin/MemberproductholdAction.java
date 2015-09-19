package com.rzrk.action.admin;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.ParentPackage;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.rzrk.Constants;
import com.rzrk.bean.Pager;
import com.rzrk.bean.Pager.Order;
import com.rzrk.entity.Member;
import com.rzrk.entity.MemberProductHold;
import com.rzrk.entity.MemberProductHoldLog;
import com.rzrk.entity.Product;
import com.rzrk.service.MemberProductHoldLogService;
import com.rzrk.service.MemberProductHoldService;
import com.rzrk.service.MemberService;
import com.rzrk.service.ProductService;
import com.rzrk.util.date.DateUtils;

@ParentPackage("admin")
public class MemberproductholdAction extends BaseAdminAction {

	private static final long serialVersionUID = 1L;

	@Resource(name = "memberProductHoldServiceImpl")
	private MemberProductHoldService memberProductHoldService;

	@Resource(name = "memberProductHoldLogServiceImpl")
	private MemberProductHoldLogService memberProductHoldLogService;

	@Resource(name = "productServiceImpl")
	private ProductService productService;

	@Resource(name = "memberServiceImpl")
	private MemberService memberService;

	private MemberProductHoldLog holdLog;

	// 列表
	public String list() {
		StringBuffer log = new StringBuffer();
		log.append("目的 : 客户产品列表页 ; ");

		// 用户
		String memberId = getRequest().getParameter("memberId");
		log.append(String.format("用户ID : %s ; ", memberId));
		if (isNull(memberId)) {
			log.append(" --> 用户ID不能为空!");
			logger.error(log.toString());
			return ERROR;
		}
		Member member = memberService.get(memberId);
		if (member == null) {
			log.append(" --> 查询到用户为空!");
			logger.error(log.toString());
			return ERROR;
		}
		getRequest().setAttribute("member", member);
		getRequest().setAttribute("memberId", memberId);
		log.append(String.format("用户名 : %s ; ", member.getRealName()));

		// 获取全部产品
		List<Product> productList = productService.getAllEnabledProduct();
		getRequest().setAttribute("productList", productList);
		log.append(String.format("共计获取产品 : %s条 ; ", productList.size()));

		String productId = getRequest().getParameter("productId");
		log.append(String.format("选中的产品ID : %s ; ", productId));

		// 填充产品hold情况
		MemberProductHold hold = null;
		List<MemberProductHold> holdList = memberProductHoldService.getAllMemberHold(memberId);
		if (holdList != null) {
			for (MemberProductHold holdItem : holdList) {
				List<Product> productLists=productService.findProductName();
				Product productItem = getByProductId(productLists, holdItem.getProductId());
				productItem.setTotalAmount(holdItem.getTotalAmount());
				if (!isNull(productId) && holdItem.getProductId().equals(productId)) {
					hold = holdItem;
				}
			}
		}

		// 指定产品ID的情况
		if (!isNull(productId)) {
			Product product = productService.get(productId);
			if (product == null || product.getIsEnabled() == Constants.NO) {
				log.append(" --> 查询产品为null或不可用!");
				logger.error(log.toString());
				return ERROR;
			}
			getRequest().setAttribute("product", product);
			getRequest().setAttribute("productId", productId);

			// hold情况
			log.append(String.format("持有情况 : %s ; ", hold == null ? null : hold.getTotalAmount()));
			if (null != hold) {
				getRequest().setAttribute("hold", hold);
			}
			// hold日志情况
			if (hold != null) {
				pager = (pager == null) ? new Pager() : pager;
				pager.setPageSize(15);
				pager.setOrderBy("actionDate");
				pager.setOrder(Order.desc);
				pager = memberProductHoldLogService.findPager(pager, Restrictions.eq("productId", productId), Restrictions.eq("memberId", memberId));
				log.append(String.format("流水情况 : %s条 ; ", pager.getResult().size()));
			}
			log.append(" --> 处理成功!");
			logger.info(log.toString());
		} else {
			log.append(" --> 未选中某产品!直接返回成功!");
			logger.info(log.toString());
		}
		return LIST;
	}

	private Product getByProductId(List<Product> productList, String productId) {
		for (Product item : productList) {
			if (item.getId().equals(productId)) {
				return item;
			}
		}
		return null;
	}

	// 添加
	public String add() {
		StringBuffer log = new StringBuffer();
		log.append("目的 : 新建用户产品操作记录 ; ");
		try {
			// 获取必要参数
			String memberId = getRequest().getParameter("memberId");
			log.append(String.format("用户ID : %s ; ", memberId));
			String productId = getRequest().getParameter("productId");
			log.append(String.format("产品ID : %s ; ", productId));

			if (isNull(memberId) || isNull(productId)) {
				log.append(" --> 必要参数为空!");
				logger.error(log.toString());
				return ERROR;
			}

			// 查询总持有情况
			MemberProductHold hold = memberProductHoldService.getMemberHold(memberId, productId);
			getRequest().setAttribute("totalAmount", hold == null ? null : hold.getTotalAmount());
			getRequest().setAttribute("memberId", memberId);
			getRequest().setAttribute("member", memberService.get(memberId));
			getRequest().setAttribute("productId", productId);
			getRequest().setAttribute("product", productService.get(productId));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			log.append(" --> 操作异常!");
			logger.error(log.toString());
			return ERROR;
		}
		return INPUT;
	}

	// 保存添加
	@InputConfig(resultName = "error")
	public String save() throws Exception {
		StringBuffer log = new StringBuffer();
		log.append("目的 : 保存用户产品操作记录 ; ");
		try {
			// 获取必要参数
			String memberId = holdLog.getMemberId();
			log.append(String.format("用户ID : %s ; ", memberId));
			String productId = holdLog.getProductId();
			log.append(String.format("产品ID : %s ; ", productId));

			if (isNull(memberId) || isNull(productId)) {
				log.append(" --> 必要参数为空!");
				logger.error(log.toString());
				return ERROR;
			}
			String actionDateStr = getRequest().getParameter("actionDate");
			Date actionDate = DateUtils.parseDateTime(actionDateStr);
			holdLog.setActionDate(actionDate);
			MemberProductHold mbhold = memberProductHoldService.getMemberHold(memberId, productId);

			if(mbhold!=null){
				//xy 申购
				if (Constants.ACTION_TYPE.buy == holdLog.getActionType()) {
					holdLog.setLastAmount(holdLog.getAmount());
				}
				// xy如果是赎回将数值设为负数
				if (Constants.ACTION_TYPE.sell == holdLog.getActionType()) {
					holdLog.setAmount(-1 * holdLog.getAmount());
					holdLog.setLastAmount(mbhold.getTotalAmount()+holdLog.getAmount());
				}
				//xy追加
				if (Constants.ACTION_TYPE.add == holdLog.getActionType()) {

					holdLog.setLastAmount(mbhold.getTotalAmount()+holdLog.getAmount());
				}
			}

			memberProductHoldLogService.save(holdLog);

			// 维护汇总表
			MemberProductHold hold = memberProductHoldService.getMemberHold(memberId, productId);
			if (hold == null) {
				hold = new MemberProductHold();
				hold.setMemberId(memberId);
				hold.setProductId(productId);
				hold.setTotalAmount(holdLog.getAmount());
				memberProductHoldService.save(hold);
			} else {
				hold.setTotalAmount(holdLog.getAmount() + hold.getTotalAmount());
				memberProductHoldService.update(hold);
			}

			redirectUrl = String.format("memberproducthold!list.action?memberId=%s&productId=%s", holdLog.getMemberId(), holdLog.getProductId());
			log.append(" --> 新建成功!");
			logger.info(log.toString());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			log.append(" --> 操作异常!");
			logger.error(log.toString());
			return ERROR;
		}
		return SUCCESS;
	}

	// 保存修改
	@InputConfig(resultName = "error")
	public String update() {
		return ERROR;
	}

	public MemberProductHoldLog getHoldLog() {
		return holdLog;
	}

	public void setHoldLog(MemberProductHoldLog holdLog) {
		this.holdLog = holdLog;
	}

}