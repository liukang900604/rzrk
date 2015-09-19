/**
 *
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Transient;

import org.hibernate.annotations.ForeignKey;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.userdetails.UserDetails;
                     
 */
package com.rzrk.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.ForeignKey;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.userdetails.UserDetails;

import com.rzrk.util.JsonUtil;

/**
 * 实体类 - 人员
 */

/**
 * @author songkez
 *
 */
@Entity
public class Admin extends BaseEntity implements UserDetails {

	private static final long serialVersionUID = -7519486823153844426L;
	
	
	private String name;// 姓名
	private String username;// 用户名
	private String password;// 密码
	
	private String rePassword;//重复密码
	private Boolean isAccountEnabled;// 账号是否启用，0, 停用；1，启用
	
	
	private String code;//岗位代码
    private int sortNo;//排序号
    private int duplicateSortDeal;// 重复序号处理 0，插入；1，排序
	
	private int sex;//0，男；1，女
	
	private String birthDate;//出生日期
	
	private String officePhone;//办公电话
	
	private String telephone;// 手机号码
	
	private String email;// 电子邮件
	
	//---------------------------------------
	
	private String location;//工作地点
	
	private String minzu;//民族
	
	private String politics;//政治面貌
	
	public enum MarriageStatus{
		未知, 未婚, 已婚 
	}
	
	private MarriageStatus  marriageStatus;//婚姻状况
	
	public enum DgreeEnum{
		专科, 本科, 硕士, 博士, 其他
	}
	
	private DgreeEnum dgreeEnum;//学历
	
	private String graduateSchool;//毕业院校
	
	private String major;//专业
	
	private String onBoardDate;//入职日期
	
	private String contractDueDay; //合同到期日
	
	private String contractDueDay2; //合同到期日2
	
	private String contractDueDay3; //合同到期日3
	
	private String contractDueDay4; //合同到期日4
	
	private String turnRegularDate;//转正时间
	
	private String document;//存档情况
	
	private String jobTitle;//职称
	
	private String identification;//身份证号码
	
	private String hukouType;//户口类别
	
	private String hukouLocation;//户口所在地
	
	private String resident;//现住址
	
	private String ergentCall;//紧急电话联系人
	
	private String quitDate;//离职日期
	
	private String contractType;//合同类型
	
	private String remark;//备注
	
	//----------------------------------------------------
	
	private JobLevel jobLevel;//职位级别
	
	private int manType;//0.非正式 ；1, 正式, 2.实习  3. 已离职，4:兼职
	
    private String url;//人员照片	

	private Boolean isAccountLocked;// 账号是否锁定
	private Boolean isAccountExpired;// 账号是否过期
	private Boolean isCredentialsExpired;// 凭证是否过期
	private Integer loginFailureCount;// 连续登录失败的次数
	private Date lockedDate;// 账号锁定日期
	private Date loginDate;// 最后登录日期
	private String loginIp;// 最后登录IP
	
	/*private WorkFlow   workFlow;//工作流
*/	
	private String macAddress; // 用户mac地址
	
	private GrantedAuthority[] authorities;// 角色信息
	
	//private Set<WorkFlowPoint> pointSet = new HashSet<WorkFlowPoint>();//工作流节点
	
	private Set<Role> roleSet = new HashSet<Role>();// 管理角色
	
	private Set<Job> mainJobSet = new HashSet<Job>();// 主岗
	
	private Set<Job> viceJobSet = new HashSet<Job>();// 副岗
	
	private Set<Deparment> deparmentSet = new HashSet<Deparment>();// 副岗
	
	private Set<QueryHistory> queryHistorySet = new LinkedHashSet<QueryHistory>();
	private Set<Viewlayer> viewlayerSet = new LinkedHashSet<Viewlayer>();
	private Set<ContractCategoryLog> contractCategoryLogSet = new LinkedHashSet<ContractCategoryLog>();
	private Set<ContractLog> contractLogSet = new LinkedHashSet<ContractLog>();
	
	private String authorityListStore;// 权限集合存储
	private String contractCategoryList;// 可以查看的二级分类
	private String projectCategoryList;// 可以查看的二级分类
	
	
	public String getContractType() {
		return contractType == null? "" : contractType; 
	}

	public void setContractType(String contractType) {
		this.contractType = contractType;
	}

	public String getRemark() {
		return remark == null? "" : remark; 
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getLocation() {
		return location==null ? "" : location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getMinzu() {
		return minzu==null ? "" : minzu;
	}

	public void setMinzu(String minzu) {
		this.minzu = minzu;
	}

	public String getPolitics() {
		return politics==null ? "" : politics;
	}

	public void setPolitics(String politics) {
		this.politics = politics;
	}

	public MarriageStatus getMarriageStatus() {
		return marriageStatus;
	}

	public void setMarriageStatus(MarriageStatus marriageStatus) {
		this.marriageStatus = marriageStatus;
	}

	public DgreeEnum getDgreeEnum() {
		return dgreeEnum;
	}

	public void setDgreeEnum(DgreeEnum dgreeEnum) {
		this.dgreeEnum = dgreeEnum;
	}

	public String getGraduateSchool() {
		return graduateSchool==null ? "" : graduateSchool;
	}

	public void setGraduateSchool(String graduateSchool) {
		this.graduateSchool = graduateSchool;
	}

	public String getMajor() {
		return major==null ? "" : major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getOnBoardDate() {
		return onBoardDate==null ? "" : onBoardDate;
	}

	public void setOnBoardDate(String onBoardDate) {
		this.onBoardDate = onBoardDate;
	}

	public String getContractDueDay() {
		return contractDueDay==null ? "" : contractDueDay;
	}

	public void setContractDueDay(String contractDueDay) {
		this.contractDueDay = contractDueDay;
	}

	public String getContractDueDay2() {
		return contractDueDay2==null ? "" : contractDueDay2;
	}

	public void setContractDueDay2(String contractDueDay2) {
		this.contractDueDay2 = contractDueDay2;
	}

	public String getContractDueDay3() {
		return contractDueDay3==null ? "" : contractDueDay3;
	}

	public void setContractDueDay3(String contractDueDay3) {
		this.contractDueDay3 = contractDueDay3;
	}

	public String getContractDueDay4() {
		return contractDueDay4==null ? "" : contractDueDay4;
	}

	public void setContractDueDay4(String contractDueDay4) {
		this.contractDueDay4 = contractDueDay4;
	}

	public String getTurnRegularDate() {
		return turnRegularDate==null ? "" : turnRegularDate;
	}

	public void setTurnRegularDate(String turnRegularDate) {
		this.turnRegularDate = turnRegularDate;
	}

	public String getDocument() {
		return document==null ? "" : document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public String getJobTitle() {
		return jobTitle==null ? "" : jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getIdentification() {
		return identification==null ? "" : identification;
	}

	public void setIdentification(String identification) {
		this.identification = identification;
	}

	public String getHukouType() {
		return hukouType==null ? "" : hukouType;
	}

	public void setHukouType(String hukouType) {
		this.hukouType = hukouType;
	}

	public String getHukouLocation() {
		return hukouLocation==null ? "" : hukouLocation;
	}

	public void setHukouLocation(String hukouLocation) {
		this.hukouLocation = hukouLocation;
	}

	public String getResident() {
		return resident==null ? "" : resident;
	}

	public void setResident(String resident) {
		this.resident = resident;
	}

	public String getErgentCall() {
		return ergentCall==null ? "" : ergentCall;
	}

	public void setErgentCall(String ergentCall) {
		this.ergentCall = ergentCall;
	}

	public String getQuitDate() {
		return quitDate==null ? "" : quitDate;
	}

	public void setQuitDate(String quitDate) {
		this.quitDate = quitDate;
	}

	@Transient
	public List<String> getAllAdminContractCategory(){
		List<String> contractCategoryIdList = new ArrayList<String>();
		
		if(StringUtils.isEmpty(getAuthorityListStore())  && StringUtils.isEmpty(getContractCategoryList()) && StringUtils.isEmpty(getProjectCategoryList())){
			for(Role temp : roleSet){
				List<String> tempList = temp.getCcList();
				if(null != tempList && tempList.size() >0 ){
					contractCategoryIdList.addAll(tempList);
				}
			}
		}else{
			List<String> tempList = getCcList();
			if(null != tempList && tempList.size() >0 ){
				contractCategoryIdList.addAll(tempList);
			}
			for(Role temp : roleSet){
				List<String> tempRoleList = temp.getCcList();
				if(null != tempRoleList && tempRoleList.size() >0 ){
					contractCategoryIdList.addAll(tempRoleList);
				}
			}
		}
		
		
		//去除重复
		HashSet h = new HashSet(contractCategoryIdList);
		contractCategoryIdList.clear();
		contractCategoryIdList.addAll(h);
		
		return contractCategoryIdList;
	}
	
	@Transient
	public List<String> getAllAdminProjectCategory(){
		List<String> projectCategoryIdList = new ArrayList<String>();
		if(StringUtils.isEmpty(getAuthorityListStore())  && StringUtils.isEmpty(getContractCategoryList()) && StringUtils.isEmpty(getProjectCategoryList())){
			for(Role temp : roleSet){
				List<String> tempList = temp.getPcList();
				if(null != tempList && tempList.size() >0 ){
					projectCategoryIdList.addAll(tempList);
				}
			}
			
		}else{
				List<String> tempList = getPcList();
				if(null != tempList && tempList.size() >0 ){
					projectCategoryIdList.addAll(tempList);
				}
				for(Role temp : roleSet){
					List<String> tempProjectList = temp.getPcList();
					if(null != tempProjectList && tempProjectList.size() >0 ){
						projectCategoryIdList.addAll(tempProjectList);
					}
				}
			
		}
		
		
		//去除重复
		HashSet h = new HashSet(projectCategoryIdList);
		projectCategoryIdList.clear();
		projectCategoryIdList.addAll(h);
		
		return projectCategoryIdList;
	}
	
	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name="rzrk_admin_deparment" ,joinColumns={@JoinColumn(name="admin_id")},inverseJoinColumns={@JoinColumn(name="deparment_set_id")})
	@OrderBy("createDate asc")
	@ForeignKey(name = "fk_admin_deparment_set")
	public Set<Deparment> getDeparmentSet() {
		return deparmentSet;
	}

	public void setDeparmentSet(Set<Deparment> deparmentSet) {
		this.deparmentSet = deparmentSet;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@OrderBy("createDate asc")
	@ForeignKey(name = "fk_admin_main_job")
	public Set<Job> getMainJobSet() {
		return mainJobSet;
	}

	public void setMainJobSet(Set<Job> mainJobSet) {
		this.mainJobSet = mainJobSet;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@OrderBy("createDate asc")
	@Column(nullable = true)
	@ForeignKey(name = "fk_admin_vice_job")
	public Set<Job> getViceJobSet() {
		return viceJobSet;
	}

	public void setViceJobSet(Set<Job> viceJobSet) {
		this.viceJobSet = viceJobSet;
	}

	public String getCode() {
		return code == null ? "" : code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getSortNo() {
		return sortNo;
	}

	public void setSortNo(int sortNo) {
		this.sortNo = sortNo;
	}

	public int getDuplicateSortDeal() {
		return duplicateSortDeal;
	}

	public void setDuplicateSortDeal(int duplicateSortDeal) {
		this.duplicateSortDeal = duplicateSortDeal;
	}

	@Transient
	public String getRePassword() {
		return rePassword;
	}

	public void setRePassword(String rePassword) {
		this.rePassword = rePassword;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getBirthDate() {
		return birthDate == null ? "" : birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getOfficePhone() {
		return officePhone == null ? "" : officePhone;
	}

	public void setOfficePhone(String officePhone) {
		this.officePhone = officePhone;
	}

	public String getTelephone() {
		return telephone == null ? "" : telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@ForeignKey(name = "fk_admin_jobLevel")
	public JobLevel getJobLevel() {
		return jobLevel;
	}

	public void setJobLevel(JobLevel jobLevel) {
		this.jobLevel = jobLevel;
	}

	public int getManType() {
		return manType;
	}

	public void setManType(int manType) {
		this.manType = manType;
	}

	public String getUrl() {
		return url == null ? "" : url;
	}

	public void setUrl(String url) {
		this.url = url;
	}


	@Override
    @Column(nullable = false, updatable = false, unique = true)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
    @Column(nullable = false)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(nullable = false)
	public String getEmail() {
		return email == null ? "" : email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name  == null ? "" : name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(nullable = false)
	public Boolean getIsAccountEnabled() {
		return isAccountEnabled;
	}

	public void setIsAccountEnabled(Boolean isAccountEnabled) {
		this.isAccountEnabled = isAccountEnabled;
	}

	@Column(nullable = false)
	public Boolean getIsAccountLocked() {
		return isAccountLocked;
	}

	public void setIsAccountLocked(Boolean isAccountLocked) {
		this.isAccountLocked = isAccountLocked;
	}

	@Column(nullable = false)
	public Boolean getIsAccountExpired() {
		return isAccountExpired;
	}

	public void setIsAccountExpired(Boolean isAccountExpired) {
		this.isAccountExpired = isAccountExpired;
	}

	@Column(nullable = false)
	public Boolean getIsCredentialsExpired() {
		return isCredentialsExpired;
	}

	public void setIsCredentialsExpired(Boolean isCredentialsExpired) {
		this.isCredentialsExpired = isCredentialsExpired;
	}

	@Column(nullable = false)
	public Integer getLoginFailureCount() {
		return loginFailureCount;
	}

	public void setLoginFailureCount(Integer loginFailureCount) {
		this.loginFailureCount = loginFailureCount;
	}

	public Date getLockedDate() {
		return lockedDate;
	}

	public void setLockedDate(Date lockedDate) {
		this.lockedDate = lockedDate;
	}

	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	public String getLoginIp() {
		return loginIp  == null ? "" : loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	@OrderBy("name asc")
	@ForeignKey(name = "fk_admin_role_set")
	public Set<Role> getRoleSet() {
		return roleSet;
	}

	public void setRoleSet(Set<Role> roleSet) {
		this.roleSet = roleSet;
	}
	
	
	
	/*@ManyToMany(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	@ForeignKey(name = "fk_admin_point_set")
	public Set<WorkFlowPoint> getPointSet() {
		return pointSet;
	}

	public void setPointSet(Set<WorkFlowPoint> pointSet) {
		this.pointSet = pointSet;
	}
*/
/*	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="workFlowId")
	public WorkFlow getWorkFlow() {
		return workFlow;
	}

	public void setWorkFlow(WorkFlow workFlow) {
		this.workFlow = workFlow;
	}*/
	@OneToMany(mappedBy="admin",cascade=CascadeType.ALL,orphanRemoval=true)
	public Set<QueryHistory> getQueryHistorySet() {
		return queryHistorySet;
	}


	public void setQueryHistorySet(Set<QueryHistory> queryHistorySet) {
		this.queryHistorySet = queryHistorySet;
	}
	//TODO admin 保存未排除这个
	@OneToMany(mappedBy="admin",cascade=CascadeType.ALL,orphanRemoval=true)
	@OrderBy("createDate desc")
	public Set<Viewlayer> getViewlayerSet() {
		return viewlayerSet;
	}


	public void setViewlayerSet(Set<Viewlayer> viewlayerSet) {
		this.viewlayerSet = viewlayerSet;
	}

	@OneToMany(mappedBy="operator",cascade=CascadeType.ALL,orphanRemoval=true)
	@OrderBy("createDate desc")
	public Set<ContractCategoryLog> getContractCategoryLogSet() {
		return contractCategoryLogSet;
	}

	public void setContractCategoryLogSet(
			Set<ContractCategoryLog> contractCategoryLogSet) {
		this.contractCategoryLogSet = contractCategoryLogSet;
	}

	@OneToMany(mappedBy="operator",cascade=CascadeType.ALL,orphanRemoval=true)
	@OrderBy("createDate desc")
	public Set<ContractLog> getContractLogSet() {
		return contractLogSet;
	}

	public void setContractLogSet(Set<ContractLog> contractLogSet) {
		this.contractLogSet = contractLogSet;
	}
	
	@Column(length = 30000)
	public String getProjectCategoryList() {
		return projectCategoryList;
	}

	public void setProjectCategoryList(String projectCategoryList) {
		this.projectCategoryList = projectCategoryList;
	}
	
	@Column(length = 30000)
	public String getContractCategoryList() {
		return contractCategoryList;
	}

	public void setContractCategoryList(String contractCategoryList) {
		this.contractCategoryList = contractCategoryList;
	}
	

	// 保存处理
	@Override
	@Transient
	public void onSave() {
		if (isAccountEnabled == null) {
			isAccountEnabled = false;
		}
		if (isAccountLocked == null) {
			isAccountLocked = false;
		}
		if (isAccountExpired == null) {
			isAccountExpired = false;
		}
		if (isCredentialsExpired == null) {
			isCredentialsExpired = false;
		}
		if (loginFailureCount == null || loginFailureCount < 0) {
			loginFailureCount = 0;
		}
	}
	
	// 更新处理
	@Override
	@Transient
	public void onUpdate() {
		if (isAccountEnabled == null) {
			isAccountEnabled = false;
		}
		if (isAccountLocked == null) {
			isAccountLocked = false;
		}
		if (isAccountExpired == null) {
			isAccountExpired = false;
		}
		if (isCredentialsExpired == null) {
			isCredentialsExpired = false;
		}
		if (loginFailureCount == null || loginFailureCount < 0) {
			loginFailureCount = 0;
		}
	}
	
	@Override
    @Transient
	public GrantedAuthority[] getAuthorities() {
		return authorities;
	}

	public void setAuthorities(GrantedAuthority[] authorities) {
		this.authorities = authorities;
	}

	@Override
    @Transient
	public boolean isEnabled() {
		return this.isAccountEnabled;
	}

	@Override
    @Transient
	public boolean isAccountNonLocked() {
		return !this.isAccountLocked;
	}

	@Override
    @Transient
	public boolean isAccountNonExpired() {
		return !this.isAccountExpired;
	}

	@Override
    @Transient
	public boolean isCredentialsNonExpired() {
		return !this.isCredentialsExpired;
	}
	
	@Column(name="authorityListStore",length = 3000)
	public String getAuthorityListStore() {
		return authorityListStore;
	}

	public void setAuthorityListStore(String authorityListStore) {
		this.authorityListStore = authorityListStore;
	}
	
	// 获取权限集合
	@SuppressWarnings("unchecked")
	@Transient
	public List<String> getAuthorityList() {
		if (StringUtils.isEmpty(authorityListStore)) {
			return null;
		}
		return JsonUtil.toObject(authorityListStore, ArrayList.class);
	}
		
	// 设置权限集合
	@Transient
	public void setAuthorityList(List<String> roleList) {
		if (roleList == null || roleList.size() == 0) {
			authorityListStore = null;
			return;
		}
		authorityListStore = JsonUtil.toJson(roleList);
	}
	
	// 获取二级分类权限集合
		@SuppressWarnings("unchecked")
		@Transient
		public List<String> getPcList() {
			if (StringUtils.isEmpty(projectCategoryList)) {
				return new ArrayList<String>();
			}
			return JsonUtil.toObject(projectCategoryList, ArrayList.class);
		}
			
		// 设置二级分类集合
		@Transient
		public void setPcList(List<String> ccList) {
			if (ccList == null || ccList.size() == 0) {
				projectCategoryList = null;
				return;
			}
			projectCategoryList = JsonUtil.toJson(ccList);
		}
		
		// 获取权限集合
		@SuppressWarnings("unchecked")
		@Transient
		public List<String> getCcList() {
			if (StringUtils.isEmpty(contractCategoryList)) {
				return new ArrayList<String>();
			}
			return JsonUtil.toObject(contractCategoryList, ArrayList.class);
		}
			
		// 设置二级分类集合
		@Transient
		public void setCcList(List<String> ccList) {
			if (ccList == null || ccList.size() == 0) {
				contractCategoryList = null;
				return;
			}
			contractCategoryList = JsonUtil.toJson(ccList);
		}

}