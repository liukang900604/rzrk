package com.rzrk.action.rzrk;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.rzrk.action.admin.BaseAdminAction.Status;
import com.rzrk.entity.Article;
import com.rzrk.entity.Member;
import com.rzrk.entity.Product;
import com.rzrk.entity.Reservations;
import com.rzrk.service.ArticleService;
import com.rzrk.service.MemberService;
import com.rzrk.service.ProductService;
import com.rzrk.service.ReservationsService;

public class ReservationsAction extends BaseShopAction{

	/**
	 * 前台 认购
	 */
	private static final long serialVersionUID = -1432458240338208824L;

	private Reservations reservations;
	private Member member;

	@Resource(name = "reservationsServiceImpl")
	private ReservationsService reservationsService;
	@Resource(name = "productServiceImpl")
	private ProductService productService;
	@Resource(name = "articleServiceImpl")
	private ArticleService articleService;
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;


	private List<Product> getNameList;


	public Reservations getReservations() {
		return reservations;
	}

	public void setReservations(Reservations reservations) {
		this.reservations = reservations;
	}



	public List<Product> getGetNameList() {
		return getNameList;
	}

	public void setGetNameList(List<Product> getNameList) {
		this.getNameList = getNameList;
	}


	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public String order(){
		
		if(id==null){
			getNameList=productService.getAllEnabledProduct();
		}else if(id!=null){
			Product pd=productService.get(id);
			getRequest().setAttribute("pd", pd);
		}
		
		String username=(String)getSession().getAttribute(Member.USER_NAME);
		if(username!=null){
			member=memberService.getMemberByUsername(username);	
			getRequest().setAttribute("member",member); 
		}
		return "order";
	}

	public String serviceIndex(){

		return "index";
	}

	public String prompt(){

		return "prompt";
	}

	public String subscribe(){
		String reservAmount=getRequest().getParameter("reservAmount");
		String contactPerson=getRequest().getParameter("contactPerson");
		String contactPhone=getRequest().getParameter("contactPhone");
		String contactMobile=getRequest().getParameter("contactMobile");
		String fax=getRequest().getParameter("fax");
		String email=getRequest().getParameter("email");
		String companyName=getRequest().getParameter("companyName");
		String address=getRequest().getParameter("address");
		String zipCode=getRequest().getParameter("zipCode");
		String description=getRequest().getParameter("description");
		String productId=getRequest().getParameter("productId");

		if(StringUtils.isBlank(reservAmount)){
			return ajax(Status.error,"预约金额不能为空!");
		}
		if(StringUtils.isBlank(contactPerson)){
			return ajax(Status.error,"联系人不能为空!");
		}
		if(StringUtils.isBlank(contactPhone)){
			return ajax(Status.error,"联系电话不能为空!");
		}
		if(StringUtils.isBlank(contactMobile)){
			return ajax(Status.error,"移动电话不能为空!");
		}
		if(StringUtils.isBlank(email)){
			return ajax(Status.error,"E-mail不能为空!");
		}
		Reservations re=new Reservations();
		re.setReservAmount(Double.parseDouble(reservAmount));
		Product product=productService.get(productId);
		re.setProduct(product);
		re.setContactPerson(contactPerson);
		re.setContactPhone(contactPhone);
		re.setContactMobile(contactMobile);
		re.setFax(fax);
		re.setEmail(email);
		re.setCompanyName(companyName);
		re.setAddress(address);
		re.setZipCode(zipCode);
		re.setDescription(description);
		reservationsService.save(re);
		return ajax(Status.success,"订购成功");
	}


}









