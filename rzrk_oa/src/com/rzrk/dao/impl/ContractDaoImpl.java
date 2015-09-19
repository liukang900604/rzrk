/**
 * Project Name: rzrk Web
 * Confidential and Proprietary                                                                
 * Copyright (C) 2013 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.opensymphony.xwork2.ActionContext;
import com.rzrk.bean.Pager;
import com.rzrk.contants.CategoryContants;
import com.rzrk.contants.WorkFlowContants;
import com.rzrk.dao.AdminDao;
import com.rzrk.dao.ContractCategoryDao;
import com.rzrk.dao.ContractDao;
import com.rzrk.dao.DeparmentDao;
import com.rzrk.dao.JobDao;
import com.rzrk.dao.ProjectDao;
import com.rzrk.dao.SystemMessageDao;
import com.rzrk.dao.WorkContractRecordDao;
import com.rzrk.dao.WorkDao;
import com.rzrk.dao.WorkFlowDao;
import com.rzrk.dao.WorkFlowPointDao;
import com.rzrk.entity.Admin;
import com.rzrk.entity.ContractCategory;
import com.rzrk.entity.ContractField;
import com.rzrk.entity.Deparment;
import com.rzrk.entity.Job;
import com.rzrk.entity.Project;
import com.rzrk.entity.SystemMessage;
import com.rzrk.entity.WorkContractRecord;
import com.rzrk.entity.SystemMessage.RedType;
import com.rzrk.entity.SystemMessage.SystemmessageType;
import com.rzrk.entity.Work;
import com.rzrk.entity.WorkFlow;
import com.rzrk.entity.WorkFlowPoint;
import com.rzrk.exception.PersonalException;
import com.rzrk.service.DeparmentService;
import com.rzrk.util.CnSpellUtils;
import com.rzrk.util.DateUtil;
import com.rzrk.util.JsonUtil;
import com.rzrk.util.StrUtil;
import com.rzrk.util.date.DateUtils;
import com.rzrk.vo.DeparmentAdminVo;
import com.rzrk.vo.contract.ContractEntity;
import com.rzrk.vo.contract.Definition;
import com.rzrk.vo.contract.Field;
import com.rzrk.vo.querytree.Cond;
import com.rzrk.vo.querytree.KeyValueCollection;
import com.rzrk.vo.querytree.Node;
import com.rzrk.vo.workflow.Project4workflow;

/**
 * Dao实现类 - 分类
 */

@Repository("contractDaoImpl")
public class ContractDaoImpl extends BaseDaoImpl<ContractField, String> implements ContractDao{

	@Resource
	private AdminDao adminDao;
	
	/**
	 * 分类dao
	 */
	@Resource
	private ContractCategoryDao  categoryDao;
	
	/**
	 * 工作流程dao
	 */
	@Resource
	private WorkFlowDao  workFlowDao;
	
	/**
	 * 工作流节点dao
	 */
	@Resource
	private WorkFlowPointDao  workFlowPointDao;
	
	/**
	 * 系统消息Dao
	 */
	@Resource
	private SystemMessageDao  systemMessageDao;
	
	/**
	 * 工作Dao
	 */
	@Resource
	private  WorkDao  workDao; 
	
//	@Resource
//	private DeparmentDao deparmentDao;
	@Resource
	ContractCategoryDao contractCategoryDao;
	
	/**
	 * 项目dao
	 */
	@Resource
	private ProjectDao  projectDao;
	
	/**
	 * 部门Dao
	 */
	@Resource
	private DeparmentDao  deparmentDao;
	
	/**
	 * 工作dao
	 */
	@Resource
	private JobDao  jobDao;
	@Resource
	WorkContractRecordDao workContractRecordDao;
	
	public List<ContractField> getContractField(String rowId,String[] fieldArr){
		String hqlstr = "from ContractField as cf where cf.rowId = :rowId ";
		if(fieldArr!=null && fieldArr.length>0){
			hqlstr += " and cf.fieldName in :fieldName";
		}
		Query query = this.getSession().createQuery(hqlstr);
		query.setString("rowId", rowId);
		if(fieldArr!=null && fieldArr.length>0){
			query.setParameterList("fieldName", fieldArr);
		}
		
		return query.list();
	}

   /**
    * 添加部门领导到人员集合中	
    * @param deparment
    * @param adminList
    */
	public void  addDepermantManage(Deparment deparment,Set<String> generalList,Set<String> leaderList,ContractCategory category){
		
		if(deparment != null){
			if(deparment.getDeparmentLeader() != null){//部门领导人
				leaderList.add(deparment.getDeparmentLeader().getId());
			}
			
			if(WorkFlowContants.VIEW.equals(category.getIsSubDepView())){//允许部门及其子部门人查看
				List<DeparmentAdminVo> adminCollection = adminDao.getAdminByDeparmentAndSub(deparment.getId());
				for(DeparmentAdminVo temp :adminCollection){
					generalList.add(temp.getAdminId());
				}
			}else if(WorkFlowContants.VIEW.equals(category.getIsView())){//允许部门人查看
				List<DeparmentAdminVo> adminCollection = adminDao.getAdminByDeparment(deparment.getId());
				for(DeparmentAdminVo temp :adminCollection){
					generalList.add(temp.getAdminId());
				}
			}
			if(WorkFlowContants.VIEW.equals(category.getIsSuperiorView())){//允许上级部门人查看
				addParmnetDepermantManage(deparment.getParent(),leaderList);
			}
		}
	}
	/**
	 * 增加部门领导
	 * @param deparment
	 * @param adminList
	 */
	public void  addParmnetDepermantManage(Deparment deparment,Set<String> adminList){
		if(deparment != null){
			if(deparment.getDeparmentLeader() != null){//部门领导人
				adminList.add(deparment.getDeparmentLeader().getId());
			}
			for(Admin admin :deparment.getDeparmentAdmins()){
				adminList.add(admin.getId());
			}

			addParmnetDepermantManage(deparment.getParent(),adminList);
		}
	}
	
	@Deprecated
	public List<ContractField> getContractFields(String contractCategoryId,String[] fieldArr){
		Admin loginAdmin = adminDao.getAdminByUsername((String)ActionContext.getContext().getSession().get("SPRING_SECURITY_LAST_USERNAME"));
		ContractCategory category = null;
		//获取所有的部门领导人
		Set<String> leaderList = new HashSet<String>();//部门的领导
		Set<String> generalList = new HashSet<String>();//部门的普通员工
		if(StringUtils.isNotEmpty(contractCategoryId)){//分类
			category = categoryDao.get(contractCategoryId);//获取分类
			if(category != null){
				for(Deparment temp : category.getDeparmentSet()){
					addDepermantManage(temp,generalList,leaderList, category);
				}
			}
			
		}
		
		String hqlstr = "from ContractField as cf where cf.contractCategoryId = :contractCategoryId and (cf.statu = 0 or cf.statu = 1)";
		if(fieldArr!=null && fieldArr.length>0){
			hqlstr += " and cf.fieldName in (:fieldName) and cf.currentAdminName like :currentAdminName";
		}
//        if(!strList.contains(loginAdmin.getId())){//如果没包含说明不允许访问
//    			hqlstr += " and 1 = 2 ";
//		}
	
		
		Query query = this.getSession().createQuery(hqlstr);
		query.setString("contractCategoryId", contractCategoryId);
		if(fieldArr!=null && fieldArr.length>0){
			query.setParameterList("fieldName", fieldArr);
		}
		if(generalList.contains(loginAdmin.getId())||leaderList.contains(loginAdmin.getId()) ){
        }else{
			query.setString("currentAdminName", "%"+loginAdmin.getId()+"%");//判断自己是否可以查看
        }

		return query.list();
}
	
	
//	private void queryTreeAddCond(StringBuffer sqlCount,Node node,KeyValueCollection kc){
//		
//		if(node.getCond()!=null){
//			Cond cond = node.getCond();
//			String operate = cond.getOperate();
//			String operateExists = " exists ";
//			String operateEq = " = ";
//			String key = "";
//			String value = "";
//			if(Cond.eq.equals(operate)){
//				operateExists = " exists ";
//				operateEq = "=";
//			}else if(Cond.ne.equals(operate)){
//				operateExists = " not exists ";
//				operateEq = "=";
//			}else if(Cond.has.equals(operate)){
//				operateExists = " exists ";
//				operateEq = "like";
//			}else if(Cond.nohas.equals(operate)){
//				operateExists = " not exists ";
//				operateEq = "like";
//			}
//			String searchBy = "searchBy"+kc.getIndex();
//			String keyword = "keyword"+kc.getIndex();
//			kc.addIndex();
//			kc.getParamMap().put(searchBy, cond.getSearchBy());
//			if("like".equalsIgnoreCase(operateEq)){
//				kc.getParamMap().put(keyword,"%"+cond.getKeyword()+"%" );
//			}else{
//				kc.getParamMap().put(keyword, cond.getKeyword() );
//			}
//			kc.getOperateMap().put(keyword,operateEq );
//			sqlCount .append(operateExists+" (select 1 from rzrk_contract_field AS cf2 where cf2.row_id=cf.id and cf2.field_name=:"+searchBy+" and cf2.value "+operateEq+" :"+keyword+") ");
//			
//		}else{
//			boolean first=true;
//			for(Node _node : node.getChildren()){
//				String word="";
//				if (first){
//					first=false;
//				}else{
//					word = _node.getRelation();
//				}
//				sqlCount.append(" "+word +" ( ");
//				queryTreeAddCond(sqlCount,_node,kc);
//				sqlCount.append(" ) ");
//			}
//		}
//	}
	private void queryTreeAddCondWithIndex(StringBuffer sqlCount,Node node,KeyValueCollection kc){
		
		if(node.getCond()!=null){
			Cond cond = node.getCond();
			String operate = cond.getOperate();
			String operateExists = " exists ";
			String operateEq = "=";
			String key = "";
			String value = "";
			if(Cond.eq.equals(operate)){
				operateExists = " exists ";
				operateEq = "=";
			}else if(Cond.ne.equals(operate)){
				operateExists = " not exists ";
				operateEq = "=";
			}else if(Cond.has.equals(operate)){
				operateExists = " exists ";
				operateEq = "like";
			}else if(Cond.nohas.equals(operate)){
				operateExists = " not exists ";
				operateEq = "like";
			}else if(Cond.in.equals(operate)){
				operateExists = " exists ";
				operateEq = "in";
			}else if(Cond.notin.equals(operate)){
				operateExists = " not exists ";
				operateEq = "in";
			}
			String searchBy = "searchBy"+kc.getIndex();
			String keyword = "keyword"+kc.getIndex();
			kc.addIndex();
			kc.getParamMap().put(searchBy, cond.getSearchBy());
			if("like".equalsIgnoreCase(operateEq)){
				kc.getParamMap().put(keyword,"%"+cond.getKeyword()+"%" );
			}else if("in".equalsIgnoreCase(operateEq)){
				key="id";
			//	kc.getParamMap().put(keyword, StrUtil.toString(cond.getKeyword().split(",")));
			 // System.out.println("("+StrUtil.toString(cond.getKeyword().split(","))+")");
			}else{
				kc.getParamMap().put(keyword, cond.getKeyword() );
			}
			kc.getOperateMap().put(keyword,operateEq );
			if(StringUtils.isNotEmpty(key)){
				sqlCount .append(operateExists+" (select 1 from rzrk_contract_field AS cf2 where cf2.row_id=cf.id and cf2.field_name=? and cf2."+key+" "+operateEq+" ("+StrUtil.toString(cond.getKeyword().split(","))+")) ");
			}else{
				sqlCount .append(operateExists+" (select 1 from rzrk_contract_field AS cf2 where cf2.row_id=cf.id and cf2.field_name=? and cf2.value "+operateEq+" ?) ");
			}
			
			
		}else{
			boolean first=true;
			for(Node _node : node.getChildren()){
				String word="";
				if (first){
					first=false;
				}else{
					word = _node.getRelation();
				}
				sqlCount.append(" "+word +" ( ");
				queryTreeAddCondWithIndex(sqlCount,_node,kc);
				sqlCount.append(" ) ");
			}
		}
	}
	
	private String queryByPermission(ContractCategory category,Admin loginAdmin,Set<String> generalList){
		
		String byOpStr = "";
		if(StringUtils.contains(category.getControlType(), ContractCategory.CONTROL_TYPE_BY_OP)){
			byOpStr +=" (";
				if(!StringUtils.equals(category.getControlField(), ContractCategory.CONTROL_TYPE_BY_OP_CREATE_ADMIN)){
					byOpStr += " exists(select 1 from rzrk_contract_field as cfop where cfop.row_id = cf.id and cfop.field_name='"+category.getControlField()+"' and cfop.`value` ='"+loginAdmin.getName()+"' ) ";
//					+ " exists(select * from rzrk_admin_deparment as ad where ad.admin_id = cf.currentAdminName and ad.deparment_set_id in ("+StringUtils.defaultIfBlank(instr, "''")+")  ) "
				}else{
					byOpStr += " cf.create_admin_id= '"+loginAdmin.getId()+"' ";
				}
			byOpStr += " ) ";
		}

		String byDepStr = "";
		if(StringUtils.contains(category.getControlType(), ContractCategory.CONTROL_TYPE_BY_DEP)){
	        if(generalList.contains(loginAdmin.getId())){
	        	byDepStr+=" (true) "; 
	        }else{
	        	byDepStr+=" (false) "; 
	        }
		}
		String byAdmin = " cf.create_admin_id= '"+loginAdmin.getId()+"' "; //填报人肯定能看

		StringBuffer sb =new StringBuffer();
		sb.append(" ( ");
		if(StringUtils.isNotBlank(byOpStr) || StringUtils.isNotBlank(byDepStr) ){
			sb.append(" ( ");
			if(StringUtils.isNotBlank(byOpStr)){
				sb.append(byOpStr);
			}
			if(StringUtils.isNotBlank(byOpStr) && StringUtils.isNotBlank(byDepStr) ){
				sb.append(" and ");
			}
			if(StringUtils.isNotBlank(byDepStr)){
				sb.append(byDepStr);
			}
			sb.append(" ) ");
		sb.append(" OR ");
		}
		sb.append(byAdmin);
		sb.append(" ) ");

		return sb.toString();
	}
	
	private void queryTreeSetParams(SQLQuery query,Node node,KeyValueCollection kc){
		for(Entry<String, Object> entry : kc.getParamMap().entrySet()){
			String key = entry.getKey();
			Object value = entry.getValue();
			
//			if(key.startsWith("keyword")){
//				String operateEq = kc.getOperateMap().get(key);
//				if("like".equalsIgnoreCase(operateEq)){
//					value = "%"+value+"%";
//				}
//			}
			query.setParameter(key, value);
		}
	}	
	/**
	 * 依次为主键字段，其他字段，rowId，创建人Id 。。。。。。
	 */
	public Map<String,Object>  findPager(String contractCategoryId,Pager pager,Node node){
		return findPager(contractCategoryId,pager,node,new HashMap<String, Object>());
	};

	public Map<String,Object>  findPager(String contractCategoryId,Pager pager,Node node,Map<String,Object> rowCondMap){
		Map<String,Object> dataMap = new HashMap<String, Object>();
		int start = (pager.getPageNumber()-1)*pager.getPageSize();
		if(start<0){
			start = 0;
		}
		Admin loginAdmin = adminDao.getAdminByUsername((String)ActionContext.getContext().getSession().get("SPRING_SECURITY_LAST_USERNAME"));
		ContractCategory category = null;
		//获取所有的部门领导人
		Set<String> leaderList = new HashSet<String>();//部门的领导
		Set<String> generalList = new HashSet<String>();//部门的普通员工
		Assert.notNull(contractCategoryId, "二级分类类型不能为空");
		category = categoryDao.get(contractCategoryId);//获取分类
		Assert.notNull(category, "无效的二级分类类型");
		for(Deparment temp : category.getDeparmentSet()){
			addDepermantManage(temp,generalList,leaderList, category);
		}
			
       
		Connection conn =	this.getSession().connection();
		KeyValueCollection kcCount = new KeyValueCollection();
		String sqlCount=null;
			sqlCount = 
					"	SELECT"+
					"		count(1) as num "
					+ "		from"+
					"				(select cf.id"+
					"			FROM"+
					"				rzrk_contract_field AS cf "+
					"			WHERE"+
//					"				cf.indication = TRUE and cf.contract_category_id = '"+contractCategoryId+"' and (cf.statu = 0 or cf.statu = 1)  and cf.recyle=false ";
					"				cf.indication = TRUE and cf.contract_category_id = '"+contractCategoryId+"' and (cf.statu = 0 or cf.statu = 1) ";
			if(rowCondMap.containsKey("projectId")){
				Object projectIdObject = rowCondMap.get("projectId");
				if(projectIdObject!=null){
					String projectId = projectIdObject.toString();
					sqlCount+= " and cf.project_id = '" +projectId+"' ";
				}
			}
			if(rowCondMap.containsKey("parentRowId")){
				Object parentRowIdObject = rowCondMap.get("parentRowId");
				if(parentRowIdObject!=null){
					String parentRowId = parentRowIdObject.toString();
					sqlCount+= " and cf.parent_row_id = '" +parentRowId+"' ";
				}
			}
			
			
			if(StringUtils.isBlank(category.getControlType())){
				//无限制随便看
			}else if(StringUtils.contains(category.getControlType(), ContractCategory.CONTROL_TYPE_BY_DEP) && leaderList.contains(loginAdmin.getId())){
				//是领导，可以随便看无限制
			}else{
				sqlCount+= " and " + queryByPermission(category, loginAdmin, generalList);
			}
			
			StringBuffer tmpsqlCount = new StringBuffer();
			queryTreeAddCondWithIndex(tmpsqlCount,node,kcCount);
			if(StringUtils.isNotBlank(tmpsqlCount.toString())){
				sqlCount += " AND ("+ tmpsqlCount.toString()+") ";
			}
			sqlCount+= " 		) as temp     ";
		
		long total = 0;
		try {
			PreparedStatement stCount = conn.prepareStatement(sqlCount);
			List<Object> params=new ArrayList<Object>(kcCount.getParamMap().values());
			for(int pindex=0;pindex<params.size();pindex++){
				stCount.setObject(pindex+1,  params.get(pindex));
			}
			ResultSet rsCount = stCount.executeQuery();
			if(rsCount.next()){
				total = rsCount.getLong("num");
			}
			rsCount.close();
			stCount.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		dataMap.put("total", (int)total);
		
//////////////////////////////////////////////////		
		KeyValueCollection kc = new KeyValueCollection();
		String sql=null;
			sql = "	SELECT"+
					"		f.*, pro_status "+
					"	FROM"+
					"		rzrk_contract_field AS f,"+
					"		("+ 
					"		SELECT @rownum:=@rownum+1 AS rownum,tmpi.id,"+
					" 			( "+
					" 				SELECT CASE WHEN EXISTS ( "+
					" 						SELECT 1  "+
					" 						FROM rzrk_project as p  "+
					" 						WHERE p.id = tmpi.pro_id) THEN "+
					" 						( "+
					" 							SELECT CASE WHEN EXISTS ( "+
					" 									SELECT 1  "+
					" 									FROM rzrk_user_plan_require as preq  "+
					" 									WHERE preq.row_id = tmpi.id) THEN "+
					" 									( "+
					" 										SELECT CASE WHEN EXISTS ( "+
					" 												SELECT 1  "+
					" 												FROM rzrk_project as p2  "+
					" 												WHERE p2.id = tmpi.pro_id and p2.status = 0) THEN "+
					" 												'待部署' "+
					" 										ELSE  "+
					" 												'已立项' "+
					" 										END "+
					" 									) "+
					" 							ELSE  "+
					" 									'已立项' "+
					" 							END "+
					" 						) "+
					" 				ELSE  "+
					" 						'未分派' "+
					" 				END "+
					" 			) as pro_status "+
					"		FROM (SELECT @rownum:=0) rn,("+
					"			SELECT"+
					"				cf.id,pro.id as pro_id"+
					"			FROM"+
					"				rzrk_contract_field AS cf ";
					if(StringUtils.isNotEmpty(pager.getOrderBy())){
						sql += "			left join rzrk_contract_field AS cfo on cf.id = cfo.row_id and cfo.field_name='"+pager.getOrderBy()+"'";
					};
					sql += "			left join rzrk_project AS pro on cf.project_id = pro.id ";
					sql += "	WHERE"+
//					"				cf.indication = TRUE and cf.contract_category_id = '"+contractCategoryId+"' and (cf.statu = 0 or cf.statu = 1) and cf.recyle=false ";
					"				cf.indication = TRUE and cf.contract_category_id = '"+contractCategoryId+"' and (cf.statu = 0 or cf.statu = 1) ";
					if(rowCondMap.containsKey("projectId")){
						Object projectIdObject = rowCondMap.get("projectId");
						if(projectIdObject!=null){
							String projectId = projectIdObject.toString();
							sql+= " and cf.project_id = '" +projectId+"' ";
						}
					}
					if(rowCondMap.containsKey("parentRowId")){
						Object parentRowIdObject = rowCondMap.get("parentRowId");
						if(parentRowIdObject!=null){
							String parentRowId = parentRowIdObject.toString();
							sql+= " and cf.parent_row_id = '" +parentRowId+"' ";
						}
					}
					
					if(StringUtils.isBlank(category.getControlType())){
						//无限制随便看
					}else if(StringUtils.contains(category.getControlType(), ContractCategory.CONTROL_TYPE_BY_DEP) && leaderList.contains(loginAdmin.getId())){
						//是领导，可以随便看无限制
					}else{
						sql+= " and "+queryByPermission(category, loginAdmin, generalList);
					}
					
					StringBuffer tmpsql=new StringBuffer();
					queryTreeAddCondWithIndex(tmpsql,node,kc);
					if(StringUtils.isNotBlank(tmpsql.toString())){
						sql += " AND ("+ tmpsql.toString()+") ";
					}
					if(StringUtils.equals(pager.getOrderBy(), "createAdminName")){
			        	sql+=" order by cf.create_admin_name "+pager.getOrder();
					}else if(StringUtils.equals(pager.getOrderBy(), "createDate")){
			        	sql+=" order by cf.create_date "+pager.getOrder();
					}else if(StringUtils.equals(pager.getOrderBy(), "modifyAdminName")){
			        	sql+=" order by cf.modify_admin_name "+pager.getOrder();
					}else if(StringUtils.equals(pager.getOrderBy(), "modifyDate")){
			        	sql+=" order by cf.modify_date "+pager.getOrder();
					}else if(StringUtils.equals(pager.getOrderBy(), "projectId")){
			        	sql+=" order by pro.name "+pager.getOrder();
					}else if(StringUtils.isNotEmpty(pager.getOrderBy())){
			        	sql+=" order by CONVERT(cfo.`value`,DECIMAL) "+pager.getOrder()+" ,cfo.`value` "+pager.getOrder()+",cf.create_date desc ";
					}else{
			        	sql+=" order by cf.create_date desc ";
					}
				sql+="			LIMIT "+start+","+pager.getPageSize()+
					"		) AS tmpi"+
					"	) AS tmp"+

					"	where f.row_id = tmp.id order by tmp.rownum";
			
		List<Map<String,String>> orderlist = new ArrayList<Map<String,String>>(); //有序结果
		PreparedStatement st=null;
		ResultSet rs=null;
		try {
			st = conn.prepareStatement(sql);
			List<Object> params=new ArrayList<Object>(kc.getParamMap().values());
			for(int pindex=0;pindex<params.size();pindex++){
				st.setObject(pindex+1, params.get(pindex));
			}
			rs = st.executeQuery();
			List<Map> originalList = new ArrayList<Map>(); //原始集合
			while(rs.next()){
				String id = rs.getString("id");
				String rowId = rs.getString("row_id");
				String field_name = rs.getString("field_name");
				String value = rs.getString("value");
				Boolean indication = rs.getBoolean("indication");
				Map contractField = new HashMap();
				contractField.put("id",id);
				contractField.put("rowId",rowId);
				contractField.put("fieldName",field_name);
				contractField.put("value",value);
				contractField.put("indication",indication);
				
				if(indication){
					Timestamp createDateSt = rs.getTimestamp("create_date");
					Timestamp modifyDateSt = rs.getTimestamp("modify_date");
					String createAdminName = rs.getString("create_admin_name");
					String modifyAdminName = rs.getString("modify_admin_name");
					String createAdminId = rs.getString("create_admin_id");
					String pro_status = rs.getString("pro_status");
//					boolean isProjectRelation = rs.getBoolean("isProjectRelation");
					String projectId = StringUtils.defaultIfBlank(rs.getString("project_id"),"");
					contractField.put("projectId",projectId);
					contractField.put("projectStatus",pro_status);
//					contractField.setProjectRelation(isProjectRelation);
					contractField.put("createAdminName",createAdminName);
					contractField.put("modifyAdminName",modifyAdminName);
					contractField.put("createAdminId",createAdminId);
					if(createDateSt!=null){
						contractField.put("createDate",new Date(createDateSt.getTime()));
					}
					if(modifyDateSt!=null){
						contractField.put("modifyDate",new Date(modifyDateSt.getTime()));
					}
				}
				originalList.add(contractField);
			}
			rs.close();
			st.close();
			LinkedHashMap<String, Map<String, String>> noOrderMap = new LinkedHashMap<String, Map<String,String>>(); //行排序，列未排序集合
			for(Map field : originalList){
				if ((Boolean)field.get("indication")){
					HashMap<String, String> item = new HashMap<String, String>();
					item.put("rowId",ObjectUtils.toString(field.get("id"), ""));
					item.put("createAdminId", ObjectUtils.toString(field.get("createAdminId"), ""));
					item.put("createDate", DateUtils.formatDate((Date)field.get("createDate"),"yyyy-MM-dd HH:mm:ss"));
					item.put("modifyDate", DateUtils.formatDate((Date)field.get("modifyDate"),"yyyy-MM-dd HH:mm:ss"));
					item.put("createAdminName", ObjectUtils.toString(field.get("createAdminName"), ""));
					item.put("modifyAdminName", ObjectUtils.toString(field.get("modifyAdminName"), ""));
//					item.put("isProjectRelation", ""+field.isProjectRelation());
					item.put("projectId", ""+ObjectUtils.toString(field.get("projectId"), ""));
					item.put("projectStatus", ""+ObjectUtils.toString(field.get("projectStatus"), ""));
					noOrderMap.put(ObjectUtils.toString(field.get("id"), ""), item);
				}
			}
			for(Map field : originalList){
				Map<String, String> item =  noOrderMap.get(field.get("rowId"));
				if (item!=null){
					item.put(ObjectUtils.toString(field.get("fieldName"), ""), ObjectUtils.toString(field.get("value"), ""));
				}
			}
			Definition definition = category.getDefinitionObj();
			
			List<Field> fieldList = contractCategoryDao.getPermissionField(category, loginAdmin);
			Collection<String> titleList = CollectionUtils.collect(fieldList, new Transformer() {
				@Override
				public Object transform(Object arg0) {
					return ((Field)arg0).getName();
				}
			});
			for(Map<String, String> noOrderEntity: noOrderMap.values()){
				ContractEntity<String, String> orderEntity = new ContractEntity<String, String>();
				for(Field fieldDef : definition){
					String key = fieldDef.getName();
					if(titleList.contains(key)){
						orderEntity.put(key, StringUtils.defaultIfBlank(noOrderEntity.get(key),""));
					}else{
						orderEntity.put(key,"");
					}
				}
				orderEntity.put("rowId", noOrderEntity.get("rowId"));
				orderEntity.put("createAdminId", noOrderEntity.get("createAdminId"));
				orderEntity.put("createAdminName", noOrderEntity.get("createAdminName"));
				orderEntity.put("modifyAdminName", noOrderEntity.get("modifyAdminName"));
				orderEntity.put("createDate", noOrderEntity.get("createDate"));
				orderEntity.put("modifyDate", noOrderEntity.get("modifyDate"));
//				orderEntity.put("isProjectRelation", ""+noOrderEntity.get("isProjectRelation"));
				orderEntity.put("projectId", ""+noOrderEntity.get("projectId"));
				orderEntity.put("projectStatus", ""+noOrderEntity.get("projectStatus"));
				orderlist.add(orderEntity);
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}finally{
			if(rs!=null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(st!=null){
				try {
					st.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		dataMap.put("result", orderlist);
///////////////////////////////////////////////////////////////
				
		return dataMap;
}
	public String save(String parentRowId,String tempRowId,String contractCategoryId,ContractEntity<String, String> entity,Admin adminList){
		return save(parentRowId,tempRowId,contractCategoryId,entity,adminList,true);
	}

	public String save(String parentRowId,String tempRowId,String contractCategoryId,ContractEntity<String, String> entity,Admin adminList,boolean useWork){
		parentRowId = StringUtils.defaultIfBlank(parentRowId, null);
		StringBuffer buffer = new StringBuffer();
		Iterator<Entry<String, String>> iterator =  entity.entrySet().iterator();
		Entry<String, String> entryIndication = iterator.next();
		String rowId=null;
		Session session = this.getSession();
		String hqlCheckIndication = "from ContractField as cf where cf.contractCategoryId = :contractCategoryId"
				+ " and cf.fieldName = :fieldName and cf.value = :value";
		Query queryCheckIndication = session.createQuery(hqlCheckIndication);
		queryCheckIndication.setParameter("contractCategoryId", contractCategoryId);
		queryCheckIndication.setParameter("fieldName",entryIndication.getKey());
		queryCheckIndication.setParameter("value", entryIndication.getValue());
		ContractField contractqueryCheckIndication = (ContractField) queryCheckIndication.uniqueResult();
		ContractCategory category = null;
		if(StringUtils.isNotEmpty(contractCategoryId)){
			category = categoryDao.get(contractCategoryId);//获取分类
		}
		if(contractqueryCheckIndication==null){
			contractqueryCheckIndication=new ContractField();
			contractqueryCheckIndication.setContractCategoryId(contractCategoryId);
			if(category != null){
				//contractqueryCheckIndication.setDeparment(category.getDeparment());
				//contractqueryCheckIndication.setDeparmentSet(category.getDeparmentSet());//设置部门
				if(category.getApprovalNeeded() == WorkFlowContants.CHECK){//审核
					contractqueryCheckIndication.setStatu(WorkFlowContants.PRE_RELATION);//待关联状态
				}else{
					contractqueryCheckIndication.setStatu(WorkFlowContants.NONMAL);//正常状态
				}
			}
			contractqueryCheckIndication.setFieldName(entryIndication.getKey());
			contractqueryCheckIndication.setValue(entryIndication.getValue());
			contractqueryCheckIndication.setIndication(true);
			contractqueryCheckIndication.setParentRowId(parentRowId);
			if(adminList != null){
				contractqueryCheckIndication.setCurrentAdminName(adminList.getId());//当前处理人
				contractqueryCheckIndication.setCreateAdminName(adminList.getName());
				contractqueryCheckIndication.setModifyAdminName(adminList.getName());
				contractqueryCheckIndication.setCreateAdminId(adminList.getId());
				contractqueryCheckIndication.setModifyAdminId(adminList.getId());
			}
			session.saveOrUpdate(contractqueryCheckIndication);
			contractqueryCheckIndication.setRowId(contractqueryCheckIndication.getId());
			session.flush();
		}else{
//			if(contractqueryCheckIndication.isRecyle()){
//				throw new PersonalException(entryIndication.getKey()+entryIndication.getValue()+" 已被删除，请先在回收站清空");
//			}else{
				throw new PersonalException(entryIndication.getKey()+entryIndication.getValue()+" 已存在");
//			}
		}
		if(StringUtils.isNotBlank(tempRowId)){
			String childUpdate = "update ContractField set parentRowId=:rowId where parentRowId=:tempRowId";
			getSession().createQuery(childUpdate).setString("rowId", contractqueryCheckIndication.getId()).setString("tempRowId",tempRowId).executeUpdate();
		}
		
		rowId = contractqueryCheckIndication.getId();
		buffer.append(contractqueryCheckIndication.getId()).append(",");
		while(iterator.hasNext()){
			Entry<String, String> entry = iterator.next();
			String hqlCheck = "from ContractField as cf where cf.contractCategoryId = :contractCategoryId"
					+ " and cf.fieldName = :fieldName  and cf.rowId = :rowId ";
			Query queryCheck = session.createQuery(hqlCheck);
			queryCheck.setParameter("contractCategoryId", contractCategoryId);
			queryCheck.setParameter("fieldName", entry.getKey());
			queryCheck.setParameter("rowId", rowId);
			//queryCheck.setParameter("rowName", rowData[0]);

			ContractField contractField = (ContractField) queryCheck.uniqueResult();
			if(contractField==null){
				contractField =new ContractField();
			}
			if(category != null){
				//contractField.setDeparment(category.getDeparment());//部门
			//	contractField.setDeparmentSet(category.getDeparmentSet());//设置部门
				if(category.getApprovalNeeded() == WorkFlowContants.CHECK){//审核
					contractField.setStatu(WorkFlowContants.PRE_RELATION);//待关联状态
				}else{
					contractField.setStatu(WorkFlowContants.NONMAL);//正常状态
				}
			}
			contractField.setContractCategoryId(contractCategoryId);
			contractField.setFieldName(entry.getKey());
			contractField.setRowId(rowId);
			contractField.setValue(entry.getValue());
			if(adminList != null){
				contractField.setCurrentAdminName(adminList.getId());//当前处理人
			}
			
			contractField.setIndication(false);
			session.saveOrUpdate(contractField);
			session.flush();
			buffer.append(contractField.getId()).append(",");
		}
		
		if(useWork && category != null){
			//检测流程完整性
			if(WorkFlowContants.CHECK.intValue() == category.getApprovalNeeded()){//需要审核
				if(StringUtils.isNotEmpty(category.getWorkFlowId())){
					WorkFlow tempFlow = workFlowDao.get(category.getWorkFlowId());
					if(tempFlow != null){
						if(!(tempFlow.getWorkFlowPointSet() != null && tempFlow.getWorkFlowPointSet().size() > 0)){
							throw new PersonalException("所属二级分类审批流程节点未定义!");
						}
					}else{
						throw new PersonalException("所属二级分类流程不存在 ,请选择其他流程!");
					}
					
				}else{
					throw new PersonalException("所属二级分类审批流程未创建!");
				}
				List<ContractEntity<String, String>> resultList = new ArrayList<ContractEntity<String, String>>();//字段显示集合
				ContractEntity<String, String> contractEntity = new ContractEntity<String, String>(entity);
				contractEntity.setRowId(rowId);
				contractEntity.setContractCategoryId(contractCategoryId);
				resultList.add(contractEntity);
				//创建工作
				createWork(buffer.toString(),adminList,category,WorkFlowContants.UN_DELETE,resultList,WorkFlowContants.SAVE_MESSAGE);
			}
		}
		
		
		return buffer.toString();
		
	}
	
	
	

	/**
	 * 
	 * @param fieldId 二级分类id
	 * @param loginAdmin 当前处理人
	 * @param category  一级分类
	 */
	public void createWork(String fieldId,Admin loginAdmin,ContractCategory category,String isDelete,List<ContractEntity<String, String>> resultList ,String title){
		Work work = new Work();//创建工作
		work.setIsDelete(isDelete);//新增、更新、删除删除操作
		 String workName = category.getName()+ "数据审批"+DateUtils.formatDateTime(new Date(System.currentTimeMillis()));//二级分类名称+当前系统时间
		 work.setWorkName(workName);//工作名称
		 
		if(WorkFlowContants.DELETE.equals(isDelete) || WorkFlowContants.UPDATE.equals(isDelete) ){//是否删除、更新
  			//删除
  			 if(StringUtils.isNotEmpty(fieldId)){
      			 String [] field = fieldId.trim().split(",");
      			 for(int i = 0; i < field.length; i++){
      			 ContractField conField = this.get(field[i]);
      			 if(conField != null){
      				work.getFieldSet().add(conField);
      				//conField.setStatu(WorkFlowContants.RELATION);//关联工作流
      			  }
      			 }
      		 }
  		}else{
  			if(StringUtils.isNotEmpty(fieldId)){
      			 String [] field = fieldId.trim().split(",");
      			 for(int i = 0; i < field.length; i++){
      			 ContractField conField = this.get(field[i]);
      			 if(conField != null){
      				work.getFieldSet().add(conField);
      				conField.setStatu(WorkFlowContants.RELATION);//关联工作流
      			  }
      			 }
      		 }
  		}
		
		work.setAdmin(loginAdmin);//流程发起人
	    work.setStatus(WorkFlowContants.CHECK_STATU);//待审批状态
	    work.setIsComplete(WorkFlowContants.WAIT);//代办状态
	     WorkFlow tempWorkFlow = workFlowDao.get(category.getWorkFlowId());//获取工作流程对象
	     if(tempWorkFlow != null){
	    	 work.setVersion(tempWorkFlow.getVersion());
	    	 work.setWorkFlow(tempWorkFlow);//工作流程
			 work.setFlowType(tempWorkFlow.getFlowType());//工作流类型
			 work.setFlowName(tempWorkFlow.getFlowName());
			 work.setFlowId(tempWorkFlow.getId());
			 work.setFlowTypeName(tempWorkFlow.getFlowType().getTypeName());
			 work.setFlowTypeId(tempWorkFlow.getFlowType().getId());
			 
	     }else{
	    		throw new PersonalException("二级分类流程不存在,请选择其他流程!");
	     }
		
			
			
		    work.setWokrCode(CnSpellUtils.getStringPinYinHead(work.getWorkName()));//获取我的工作字符串首字母
			work.setCode(CnSpellUtils.getPinYinHeadChar(loginAdmin.getName()));//获取首字母
			work.setCreateDate(new Date(System.currentTimeMillis()));//创建时间
			work.setModifyDate(new Date(System.currentTimeMillis()));//修改时间
			//创建工作内容
			createContent(work,resultList,category,title);
	     
	    List<Admin> recepList = new ArrayList<Admin>();//接收人
	    List<Admin> adminList = new ArrayList<Admin>();//接收人
	    
		WorkFlowPoint tempPoint =	getWorkFlowPoint(WorkFlowContants.BETWEEN,WorkFlowContants.END,tempWorkFlow.getId());//获取工作流节点 
	    boolean  status = false;
		if(tempPoint != null){
			work.setCurrentPointId(tempPoint.getId());//设置为当前节点
			work.setCurrentPointName(tempPoint.getPointName());
			if(WorkFlowContants.UNBRANCH.equals(tempPoint.getIsBranch())){//如果为普通节点
				if(tempPoint.getWorkFlowSet() != null && tempPoint.getWorkFlowSet().size() > 0){
					 adminList = new ArrayList<Admin>(tempPoint.getWorkFlowSet());//节点观看人
					 recepList.addAll(adminList);//通知人
				 	if(StringUtils.isNotEmpty(tempPoint.getSearchName())){
				 		status = getAdminByKeyName(tempPoint.getSearchName(), recepList,loginAdmin,null,loginAdmin);
				 	}
				 	if(!status){
				 		setWorkName(work, recepList);//设置审批人名称
				 	}
				 	
				}else{
					if(StringUtils.isEmpty(tempPoint.getSearchName())){
						throw new PersonalException("流程节点未定义审批人!");
						}else{
							status= getAdminByKeyName(tempPoint.getSearchName(), recepList,loginAdmin,null,loginAdmin);
						 	if(!status){
						 		setWorkName(work, recepList);
						 	}
							
						}
					
				}
				workDao.save(work);
			}else{
				workDao.save(work);
				 //设置分支用户
	      		getUserByCondition(work,tempPoint,recepList,loginAdmin);
			}
	    }else{
	    	throw new PersonalException("二级分类审批流程节点未定义!");
	    }
		

		if(status){//继续找下一节点
			findLastPoint(work,tempPoint,recepList,loginAdmin);
			workDao.update(work);
		}
		//新增系统消息
		if(StringUtils.isNotEmpty(work.getCurrentAdminName())){
			saveSysMessage("工作审批通知","尊敬的"+work.getCurrentAdminName()+" ,您有新的工作审批,请及时处理。",loginAdmin,recepList,work.getId(),work.getCurrentPointId());
		}else{
			adminList = new ArrayList<Admin>();
			adminList.add(loginAdmin);//接收人就是自己
			saveSysMessage("工作审批结果","尊敬的"+work.getAdmin().getName()+",您的工作已结束。",loginAdmin,adminList,null,null);
		}
		
		for( ContractEntity<String, String> entity: resultList){
			ContractEntity contractEntity = (ContractEntity)entity;
			
			WorkContractRecord workContractRecord = new WorkContractRecord();
			workContractRecord.setContractCategoryId(contractEntity.getContractCategoryId());
			workContractRecord.setRowId(contractEntity.getRowId());
			workContractRecord.setWorkId(work.getId());
			workContractRecord.setWorkFlowId(work.getWorkFlow().getId());
			workContractRecordDao.save(workContractRecord);
		}
		
	}
	
	
	
	/**
	 * 根据条件获取当前用户
	 * @param work  我的工作
	 * @param tempPoint 当前节点
	 * @throws Exception
	 */
	public void getUserByCondition(Work work,WorkFlowPoint tempPoint,List<Admin> recepList,Admin loginAdmin){
		recepList = new ArrayList<Admin>();//清空处理人
		
		boolean  status = false;
		//分支节点  更新节点观看人
			if(tempPoint != null){
				if(WorkFlowContants.BRANCH.equals(tempPoint.getIsBranch())){//如果为分支节点
					
					String condition = null;//条件值
					String value = null;//关键字值
					int keyNum = 0; //计算关键字是否匹配上
					//获取关键字
					 for(ContractField field:work.getFieldSet()){
					     if(field.getFieldName().equals(tempPoint.getKeyName())){//匹配分支节点关键字 获取关键字的value
					    	value = field.getValue(); 
					    	keyNum ++;
					    	break;
					     }
					 }
					 
					 StringBuffer currentAdminName = new StringBuffer();
	     			 StringBuffer currentAdminId = new StringBuffer();//当前处理人id
					 if(keyNum == 0) {//没匹配上关键字
						 throw new PersonalException("当前条件节点分支条件名称无法匹配!");
						 
					 }else{//条件名称匹配上
						String [] userCondition = tempPoint.getUserCondition().trim().split(","); //用户节点数组
						int  defaultCondition = 0;
						if(StringUtils.isNotEmpty(value)){//比较的值不为空
							 for(String tempCondition : userCondition){ //用户条件组成格式   用户id-用户id:条件1-条件2
									String [] user = tempCondition.trim().split(":");
									String [] conditionArray = user[1].trim().split("_");//获取多个条件
									int conditionNum = 0;//条件数
									int containsNum = 0; //包含数目
									if(conditionArray.length == 2){//说明 < 值 < 
										for(int i= 0; i < conditionArray.length; i++){
											
											String tempcondition = conditionArray[i];
											if(StringUtils.isEmpty(tempcondition)){
												conditionNum ++;
												continue;
											}
											if(tempcondition.contains("<")){//如果判断大于
												if(StringUtils.isEmpty(tempcondition.substring(1))){
													conditionNum ++;
													continue;
												}
												if(!StrUtil.isNumeric(tempcondition.substring(1))){
													throw new PersonalException("分支节点条件<的值只能填写整数!");
												}
												if(i == 0){//条件值左边
													if(Integer.valueOf(value).intValue() > Integer.valueOf(tempcondition.substring(1)).intValue() ){
														conditionNum ++;
														continue;
													}else{
														break;
													}
												}else{
													if(Integer.valueOf(value).intValue() < Integer.valueOf(tempcondition.substring(1)).intValue() ){
														conditionNum ++;
														continue;
													}else{
														break;
													}
												}
												
											}
					
											if(tempcondition.contains("≤")){//如果判断大于等于
												if(StringUtils.isEmpty(tempcondition.substring(1))){
													conditionNum ++;
													continue;
												}
												if(!StrUtil.isNumeric(tempcondition.substring(1))){
													throw new PersonalException("分支节点条件≤的值只能填写整数!");
												}
												if(i == 0){//条件值左边
													if(Integer.valueOf(value).intValue() >= Integer.valueOf(tempcondition.substring(1)).intValue() ){
														conditionNum ++;
														continue;
													}else{
														break;
													}
												}else{
													if(Integer.valueOf(value).intValue() <= Integer.valueOf(tempcondition.substring(1)).intValue() ){
														conditionNum ++;
														continue;
													}else{
														break;
													}
												}
												
											}
											
											if(tempcondition.contains("=")){//如果判断等于
												if(tempcondition.substring(1).trim().equals(value)){
													conditionNum ++;
													continue;
												}else{
													break;
												}
											}
											if(tempcondition.contains(WorkFlowContants.CONDITION_CONTAINS)){//如果是包含 模糊查询
												if(value.indexOf(tempcondition.substring(2).trim()) != -1 ){
													containsNum ++;
													break;
												}
											}
											if(tempcondition.contains(WorkFlowContants.CONDITION_NO_EQUALS)){//如果判断不等于
												if(!(tempcondition.substring(1).trim().equals(value))){
													conditionNum ++;
													continue;
												}else{
													break;
												}
											}
											
										}
										
										
									}else if(conditionArray.length == 1){ // <值大于
										for(String tempcondition : conditionArray){
											if(StringUtils.isEmpty(tempcondition)){
												throw new PersonalException("分支节点不能无条件!");
											}
											
											if(tempcondition.contains("<")){//如果判断大于
												if(StringUtils.isEmpty(tempcondition.substring(1))){
													throw new PersonalException("分支节点条件<的值不能为空!");
												}
												if(!StrUtil.isNumeric(tempcondition.substring(1))){
													throw new PersonalException("分支节点条件<的值只能填写整数!");
												}
												if(Integer.valueOf(value).intValue() > Integer.valueOf(tempcondition.substring(1)).intValue() ){
													conditionNum ++;
													continue;
												}else{
													break;
												}
											}
					
											if(tempcondition.contains("≤")){//如果判断大于等于
												if(StringUtils.isEmpty(tempcondition.substring(1))){
													throw new PersonalException("分支节点条件≤的值不能为空!");
												}
												if(!StrUtil.isNumeric(tempcondition.substring(1))){
													throw new PersonalException("分支节点条件≤的值只能填写整数!");
												}
												if(Integer.valueOf(value).intValue() >= Integer.valueOf(tempcondition.substring(1)).intValue() ){
													conditionNum ++;
													continue;
												}else{
													break;
												}
											}
											
											if(tempcondition.contains("=")){//如果判断等于
												if(tempcondition.substring(1).trim().equals(value)){
													conditionNum ++;
													continue;
												}else{
													break;
												}
											}
											
											if(tempcondition.contains(WorkFlowContants.CONDITION_CONTAINS)){//如果是包含 模糊查询
												if(value.indexOf(tempcondition.substring(2).trim()) != -1 ){
													conditionNum ++;
													break;
												}
											}
											
											if(tempcondition.contains(WorkFlowContants.CONDITION_NO_EQUALS)){//如果判断不等于
												if(!(tempcondition.substring(1).trim().equals(value))){
													conditionNum ++;
													continue;
												}else{
													break;
												}
											}
										}
										
									}else{
										throw new PersonalException("分支节点存在无条件分支!");
									}
								
									
									if((conditionNum == conditionArray.length) || (containsNum > 0)){//条件全部满足
										defaultCondition ++;
										//
										String [] adminArray = user[0].split("_");
										if(user.length == 3){//包含了节点条件值
											if(StringUtils.isNotEmpty(user[0]) || StringUtils.isNotEmpty(user[2])){//有处理人的分支
												for(String temp : adminArray){
													Admin tempAdmin = adminDao.get(temp);
													if(tempAdmin != null && StringUtils.isNotEmpty(tempAdmin.getId())){
														recepList.add(tempAdmin);//接收人
													}
												
												}
												 status =	getAdminByKeyName(user[2],recepList,loginAdmin,work.getFormContent(),loginAdmin);//获取条件指定的人
												 if(!status){
													 setWorkName(work,recepList);
												 }
												 if(status){//继续找下一节点
								 				    findLastPoint(work,tempPoint,recepList,loginAdmin);
								 				 }
													
											}else{
												findLastPoint(work,tempPoint, recepList, loginAdmin);
											}
											
										}else{
											if(StringUtils.isNotEmpty(user[0])){//有处理人的分支
												for(String temp : adminArray){
													Admin tempAdmin = adminDao.get(temp);
													if(tempAdmin != null && StringUtils.isNotEmpty(tempAdmin.getId())){
														recepList.add(tempAdmin);//接收人
														currentAdminName.append(tempAdmin.getName()).append("、");
						 		 	     				currentAdminId.append(tempAdmin.getId()).append("、");
													}
													
												}
												 
								 				work.setCurrentAdminName(currentAdminName.substring(0,currentAdminName.length()-1).toString());//当前处理人
								     			work.setCurrentId(currentAdminId.substring(0,currentAdminId.length()-1).toString());//当前处理人id
											}else{
												findLastPoint(work,tempPoint, recepList, loginAdmin);
											}
											
											
										}
										break;
									
									}
									
								 }
							 
						}
						
						if(defaultCondition == 0){//条件分支没有匹配上  给默认分支 默认处理人
							/*String [] defaultConditions = tempPoint.getUserCondition().trim().split(","); //用户节点数组
							String [] user = defaultConditions[0].trim().split(":");
							String [] adminArray = user[0].split("_");
							if(user.length == 3){//包含了节点条件值
								
								for(String temp : adminArray){
									Admin tempAdmin = adminService.get(temp);
									if(tempAdmin != null && StringUtils.isNotEmpty(tempAdmin.getId())){
										recepList.add(tempAdmin);//接收人
									}
	 		 	     				
								}
								getAdminByKeyName(user[2],recepList,loginAdmin);//获取条件指定的人
								setWorkName(work,recepList);
							}else{
								for(String temp : adminArray){
									Admin tempAdmin = adminService.get(temp);
									if(tempAdmin != null && StringUtils.isNotEmpty(tempAdmin.getId())){
										recepList.add(tempAdmin);//接收人
										currentAdminName.append(tempAdmin.getName()).append("、");
		 		 	     				currentAdminId.append(tempAdmin.getId()).append("、");
									}
								}
				 				work.setCurrentAdminName(currentAdminName.substring(0,currentAdminName.length()-1).toString());//当前处理人
				     			work.setCurrentId(currentAdminId.substring(0,currentAdminId.length()-1).toString());//当前处理人id
							}
						*/
							throw new PersonalException("表单填写内容不合适,未找到符合要求的分支,请正确填写表单内容!");
						 }
					 }
					 
	     		   workDao.update(work);
				}
		    }else{
		    	throw new PersonalException("下一节点不存在!");
		    }
		
	}
	
	public void findLastPoint(Work work,WorkFlowPoint point,List<Admin> recepList,Admin loginAdmin){
		String flowId =  getMatchFlowId(work.getFlowId(), work.getVersion());//找到匹配的流程  历史还是现在
		WorkFlowPoint flowPoint = getWorkFlowPointByWorkFlow(WorkFlowContants.BETWEEN, point.getNextPonit(), flowId);//获取下一节点
		if(flowPoint != null){
			if(StringUtils.isEmpty(work.getLastPointId())){
				work.setLastPointId(point.getId());
				work.setLastPointName(point.getPointName());
			}
			work.setCurrentPointId(flowPoint.getId());//更换当前节点
			work.setCurrentPointName(flowPoint.getPointName());
			boolean status = false;
			if(WorkFlowContants.UNBRANCH.equals(flowPoint.getIsBranch())){//普通节点
				List<Admin> adminList = new ArrayList<Admin>();
				if(flowPoint.getWorkFlowSet() != null && flowPoint.getWorkFlowSet().size() > 0){
					 adminList = new ArrayList<Admin>(flowPoint.getWorkFlowSet());//节点观看人
	 				 	 recepList.addAll(adminList);//通知人
	 				 	if(StringUtils.isNotEmpty(flowPoint.getSearchName())){
	 				 		status =  getAdminByKeyName(flowPoint.getSearchName(), recepList,loginAdmin,work.getFormContent(),loginAdmin);
	 				 	}
	 				 	if(!status){
	 				 		setWorkName(work, recepList);//设置审批人名称
	 				 	}
	 				    if(status){//继续找下一节点
	 				    	findLastPoint(work,flowPoint,recepList,loginAdmin);
	 				    }
	 				 
				}else{
					//关键词
					if(StringUtils.isEmpty(flowPoint.getSearchName())){
						throw new PersonalException("下一节点无审批人!");
					}else{
						 status = getAdminByKeyName(flowPoint.getSearchName(), recepList,loginAdmin,work.getFormContent(),loginAdmin);
						 if(!status){
							 setWorkName(work, recepList);
						 }
						 if(status){//继续找下一节点
		 				    	findLastPoint(work,flowPoint,recepList,loginAdmin);
		 				 }
					}
					
				}
			}else{
				getUserByCondition(work, flowPoint,recepList,loginAdmin);
			}
		}else{
			Set<ContractField> deleteSet = new HashSet<ContractField>();
			//代表最后一节点了
			if(work.getIsDelete() != null && WorkFlowContants.DELETE.equals(work.getIsDelete())){//是否需要删除
				
				/*//删除节点
				for(ContractField field:temp.getFieldSet()){
					contractService.delete(field);
				}*/
				//删除工作流关联的信息
				deleteSet = work.getFieldSet();
				//temp.setFieldSet(new HashSet<ContractField>());
				
			}else if(work.getIsDelete() != null && WorkFlowContants.UPDATE.equals(work.getIsDelete())){//更新操作
				for(ContractField field : work.getFieldSet()){//更换状态
					field.setValue(field.getUpdateValue());
				}
			}else{
				for(ContractField field : work.getFieldSet()){//更换状态
					field.setStatu(WorkFlowContants.PASS);
				}
				
			}
			work.setFieldSet(new HashSet<ContractField>());//清除关系
			work.setCurrentAdminName("");//当前处理人
			work.setCurrentId("");//当前处理人id
			work.setIsComplete(WorkFlowContants.COMPLETE);//已完成
			work.setStatus(WorkFlowContants.END_STATU);//正常结束
			
			if(StringUtils.equals(work.getWorkFlow().getId(),CategoryContants.PROJECT_SETUP_ID)){
				String expand = work.getExpand();
				if(StringUtils.isNotBlank(expand)){
					Project4workflow project4workflow = JsonUtil.toObject(expand, Project4workflow.class);
					Project project = new Project();
					project.setName(project4workflow.getName());
					try {
						project.setProjectType(Project.ProjectType.valueOf(project4workflow.getProjectType()));
					} catch (Exception e) {
						project.setProjectType(Project.ProjectType.开发);
					}
					project.setBeginTime(DateUtil.getDateTime("yyyy-MM-dd HH:mm:ss", new Date()));
					project.setEndTime(DateUtil.addDay(project.getBeginTime(),NumberUtils.toInt(project4workflow.getPredictDay(),7 )));
					try {
						project.setStatus(Project.Status.valueOf(project4workflow.getStatus()));
					} catch (Exception e) {
						project.setStatus(Project.Status.未完成);
					}
					project.setContent(project4workflow.getProjectDesc());
					Admin responsibor = adminDao.get(project4workflow.getResponsiborId());
					project.setResponsibor(responsibor);
					Deparment  deparment = work.getAdmin().getDeparmentSet().iterator().next();
					project.setDeparment(deparment);
					Admin creator = work.getAdmin();
					project.setCreator(creator);
//					project.setRequestRowidList(project4workflow.getRequestRowids());
//					updateProjectRelation(StrUtil.toString(project4workflow.getRequestRowids()));
					projectDao.save(project);
					for(String requestRowid : project4workflow.getRequestRowids()){
						ContractField  contractField  = get(requestRowid);
						Assert.notNull(contractField, "不存在需求："+requestRowid);
						contractField.setProjectId(project.getId());
					}
				}
			}
			
			removeRelationWorkForDefined(work);//去除工作流与表单的关系
			
			workDao.update(work);
			if(!WorkFlowContants.UN_DELETE.equals(work.getIsInternal())){//非特殊工作流
				if(!(StringUtils.isNotEmpty(work.getCurrentPointId()))){//如果没有下一节点
					if(work.getIsDelete() != null && WorkFlowContants.DELETE.equals(work.getIsDelete())){//是否需要删除
						this.deleteContartField(deleteSet,work.getId());
					}
				}
			}
		}
	}
	
	
	
	public String getMatchFlowId(String workFlowId, Long version) {
		String sql = "select id  from  rzrk_workflow where id = ? and version = ? ";
		List<String> resultString =  this.getSession().createSQLQuery(sql).setParameter(1,workFlowId ).setParameter(2, version).setMaxResults(1).list();
		if(resultString != null && resultString.size() > 0){
			return resultString.get(0);
		}else{
			String historySql = "select id  from  rzrk_workflowHistor where uuid = ? and version = ?";
			List<String> historyResult =  this.getSession().createSQLQuery(sql).setParameter(1,workFlowId ).setParameter(2, version).setMaxResults(1).list();
			if(historyResult != null && historyResult.size() > 0){
				return historyResult.get(0);
			}
		}
		return null;
	}
	
	 /**
     * 更新项目关联
     * @param fieldString
     */
	@Deprecated
	public int  updateProjectRelation(String fieldString){
    	String sql = "update rzrk_contract_field set isProjectRelation = 1 where id in ("+fieldString+")";
    	return contractCategoryDao.getSession().createSQLQuery(sql).executeUpdate();
    	
    }
	
	/**
	 * 获取工作流指定的节点
	 * @param pointLocation  节点位置
	 * @param pointSort 节点序号
	 * @param workFlowId  工作流ID
	 * @return
	 */
	public WorkFlowPoint getWorkFlowPointByWorkFlow(String pointLocation,String pointSort ,String workFlowId) {
		String hql = null;
		if(StringUtils.isNotEmpty(pointSort)){//序号不为空
			hql = "from WorkFlowPoint where workFlow.id =:workFlowId and pointLocation =:pointLocation and pointSort =:pointSort ";
		}else{ //默认第一个节点
			hql = "from WorkFlowPoint where workFlow.id =:workFlowId and pointLocation =:pointLocation and pointSort = 2";
		}
		Query query = workFlowPointDao.getSession().createQuery(hql);
		query.setParameter("workFlowId", workFlowId).setParameter("pointLocation", pointLocation);
		if(StringUtils.isNotEmpty(pointSort)){//序号不为空
			query.setParameter("pointSort", pointSort);
		}
		//获取工作流类型集合
		return (WorkFlowPoint)query.uniqueResult();
	}
	
	/**
	 * 删除字段数据
	 * @param fieldList
	 */
	public void deleteContartField(Set<ContractField> fieldList,String workId){
		//String sql = "delete from rzrk_rzrk_work_contract_field where rzrk_work_id =:workId"; 
		//this.getBaseDao().getSession().createSQLQuery(sql).setParameter("workId", workId).executeUpdate();
		for(ContractField field :fieldList){
			delete(field);
		}
	}
	
	 /**
     * 去除于工作流定义的关系
     * @param work
     */
    public void removeRelationWorkForDefined(Work work){
    	work.setWorkFlow(null);
    	work.setFlowType(null);
    	work.setCurrentPointId(null);
    	work.setCurrentPointName(null);
    	work.setLastPointId(null);
    	work.setLastPointName(null);
    }
	
	/**
	 * 设置审批工作者名称、审批工作者id
	 * @param work
	 * @param recepList
	 */
	public void setWorkName(Work work, List<Admin> recepList){
		      StringBuffer currentAdminName = new StringBuffer();
 			 StringBuffer currentAdminId = new StringBuffer();//当前处理人id
 			 for(Admin admin : recepList){
 				Admin tempAdmin = adminDao.get(admin.getId());
 				currentAdminName.append(tempAdmin.getName()).append("、");
 				currentAdminId.append(tempAdmin.getId()).append("、");
 			 }
		    work.setCurrentAdminName(currentAdminName.substring(0,currentAdminName.length()-1).toString());//当前处理人
 			work.setCurrentId(currentAdminId.substring(0,currentAdminId.length()-1).toString());//当前处理人id
		
	}
	
	
	
	/**
	 * 获取工作名称Set集合
	 * @return
	 */
	public Set<String>  getJonSet(){
		List<Job> jobList = jobDao.getKeyJobList();
		Set<String> jobSet = new HashSet<String>();
		if(jobList != null){
			for(Job job : jobList){
				jobSet.add(job.getJobName());
			}
		}
		return jobSet;
	}
	
	/**
	 * 获取用户最大的优先级
	 */
	public String getPriorityLevelByUser(Admin admin){
		String level = "";
		if(admin != null){
			Set<Job> jobSet = admin.getMainJobSet();
			if(jobSet != null){
				for(Job job : jobSet){
					if(StringUtils.isNotEmpty(level)){
						if(StringUtils.isNotEmpty(job.getPriorityLevel())){
							if(Integer.parseInt(job.getPriorityLevel()) > Integer.parseInt(level)){
								level = job.getPriorityLevel();
							}
						}
					}else{
						level = job.getPriorityLevel();
					}
				}
			}
			
		}
		return level;
	}
	
	
	
	
	/**
	 * 根据关键字获取执行人
	 * @param work
	 * @param keyName
	 * @param recepList
	 * @param admin
	 */
	public boolean getAdminByKeyName(String keyName,List<Admin> recepList,Admin admin,String formContent,Admin loginAdmin){
		String [] keyArray =  keyName.trim().split("_");
		for(String temp : keyArray){
			Set<String> jobSet = getJonSet();
			if(jobSet.contains(temp)){
				String adminLevel = getPriorityLevelByUser(loginAdmin); //创建工作用户优先级
				if(StringUtils.isEmpty(adminLevel)){
					adminLevel = "0";//默认最低级别
				}
				Job tempJob = jobDao.getByName(temp);
				if(Integer.parseInt(adminLevel) >= Integer.parseInt(tempJob.getPriorityLevel())){ //关键字小于创建工作用户级别直接过
					return true;
				}else{
					if(WorkFlowContants.DEPARMENT_MANAGER.equals(temp)){//部门经理
						Set<Deparment> deparmentList  = admin.getDeparmentSet();
						if(deparmentList != null && deparmentList.size() > 0){//判断用户部门是否存在
							for(Deparment deparment : deparmentList){
								Admin leader = deparment.getDeparmentLeader();//领导是否存在
								if( leader != null){
									if(!recepList.contains(leader)){//去重
										recepList.add(leader);
									}
								}
							}
						}
						if(recepList.size() == 1 && recepList.contains(loginAdmin)){//判断处理人是否是自己,如果是自己就跳过
							recepList = new ArrayList<Admin>();
							return true;
						}
					}else{
						Admin tempAdmin = adminDao.get(tempJob.getAdminId());
						recepList.add(tempAdmin);
						if(recepList.size() == 1 && recepList.contains(loginAdmin)){//判断处理人是否是自己,如果是自己就跳过
							recepList = new ArrayList<Admin>();
							return true;
						}
					}
				}
				
				
			}else{ //非内置关键字
				throw new PersonalException("二级分类不支持包含表单关键字流程,请选择其他流程!");
				
			}
		}	
		return false;
	}
	
	/**
	 * 创建工作内容
	 * @param work
	 * @param entity
	 * @param category
	 */
	public void createContent(Work work,List<ContractEntity<String, String>> resultList,ContractCategory category,String title){
		if(CollectionUtils.isEmpty(category.getDefinitionObj())){
			return;
		}
		String[] field = category.getDefinitionObj().getTitles();
		StringBuffer buffer = new StringBuffer();
		buffer.append("<table style='width:100%;text-align:center;' cellpadding='2' align='center' cellspacing='0' border='1' bordercolor='#000000'>");
		buffer.append("<tbody>"); 
		buffer.append("<tr>");
		buffer.append("<td  colSpan='").append(field.length).append("'>");  
		buffer.append(title);
		buffer.append("</td></tr>"); 
		buffer.append("<tr>");   
		for(String tempField : field){//生成表头
			buffer.append("<td>").append(tempField).append("</td>");
		}
		buffer.append("</tr>"); 
		for(ContractEntity<String, String> entity:resultList){
			Iterator<Entry<String, String>> iterator =  entity.entrySet().iterator();
			buffer.append("<tr>");
			while(iterator.hasNext()){
				Entry<String, String> entry = iterator.next();
				buffer.append("<td>").append(entry.getValue()).append("</td>");
			}	
			buffer.append("</tr>"); 
		}
		
		buffer.append("</tbody>"); 
		buffer.append("</table><br />"); 
		work.setFormContent(buffer.toString());
	}
	
	  /**
     * 保存系统消息
     * @param title  标题
     * @param context 内容
     * @param admin  人员
     */
    public void  saveSysMessage(String title,String context,Admin admin,List<Admin> adminList,String workId,String currentPointId){
  	   SystemMessage message = new SystemMessage();
			message.setCreateTime(new Date(System.currentTimeMillis()));//创建时间
			message.setRedType(RedType.未读);//是否已读
			message.setTitle(title);
			if(StringUtils.isNotEmpty(workId)){
				message.setType(SystemmessageType.审批消息);
			}else{
				message.setType(SystemmessageType.个人消息);
			}
			message.setContext(context);
			message.setLoginAdmin(admin);//发布人
			message.setToMessagAdmin(adminList);//接收人
			message.setWorkId(workId);
			message.setCurrentPointId(currentPointId);
			systemMessageDao.save(message);
    }
    
    
    /**
   	 * 获取首节点
   	 * @return
   	 */
   	public WorkFlowPoint getWorkFlowPoint(String pointLocation,String pointSort ,String workFlowId) {
   		String hql = "from WorkFlowPoint where workFlow.id =:workFlowId and pointLocation =:pointLocation and pointSort =:pointSort";
   		//获取工作流类型集合
   		return (WorkFlowPoint) this.getSession().createQuery(hql).setParameter("workFlowId", workFlowId).setParameter("pointLocation", WorkFlowContants.BETWEEN).setParameter("pointSort", WorkFlowContants.END).uniqueResult();
   	}

	public Set<ContractField> save4work(String contractCategoryId,ContractEntity<String, String> entity){
		Admin loginAdmin = adminDao.getAdminByUsername((String)ActionContext.getContext().getSession().get("SPRING_SECURITY_LAST_USERNAME"));
		Set<ContractField> cfList = new LinkedHashSet<ContractField>();
		StringBuffer buffer = new StringBuffer();
		Iterator<Entry<String, String>> iterator =  entity.entrySet().iterator();
		Entry<String, String> entryIndication = iterator.next();
		String rowId=null;
		Session session = this.getSession();
		String hqlCheckIndication = "from ContractField as cf where cf.contractCategoryId = :contractCategoryId"
				+ " and cf.fieldName = :fieldName and cf.value = :value";
		Query queryCheckIndication = session.createQuery(hqlCheckIndication);
		queryCheckIndication.setParameter("contractCategoryId", contractCategoryId);
		queryCheckIndication.setParameter("fieldName",entryIndication.getKey());
		queryCheckIndication.setParameter("value", entryIndication.getValue());
		ContractField contractqueryCheckIndication = (ContractField) queryCheckIndication.uniqueResult();
		ContractCategory category = null;
		if(StringUtils.isNotEmpty(contractCategoryId)){
			category = categoryDao.get(contractCategoryId);//获取分类
		}
		if(contractqueryCheckIndication==null){
			contractqueryCheckIndication=new ContractField();
			contractqueryCheckIndication.setContractCategoryId(contractCategoryId);
			if(category != null){
//				contractqueryCheckIndication.setDeparmentSet(category.getDeparmentSet());
//				if(category.getApprovalNeeded() == WorkFlowContants.CHECK){//审核
					contractqueryCheckIndication.setStatu(WorkFlowContants.RELATION);//待关联状态
//				}else{
//					contractqueryCheckIndication.setStatu(WorkFlowContants.NONMAL);//正常状态
//				}
			}
			contractqueryCheckIndication.setFieldName(entryIndication.getKey());
			contractqueryCheckIndication.setValue(entryIndication.getValue());
			contractqueryCheckIndication.setIndication(true);
			contractqueryCheckIndication.setCurrentAdminName(loginAdmin.getId());
			contractqueryCheckIndication.setCreateAdminName(loginAdmin.getName());
			contractqueryCheckIndication.setModifyAdminName(loginAdmin.getName());
			contractqueryCheckIndication.setCreateAdminId(loginAdmin.getId());
			contractqueryCheckIndication.setModifyAdminId(loginAdmin.getId());
//			contractqueryCheckIndication.setAdminList(new HashSet<Admin>(adminList));//用户
//			StringBuffer sf = new StringBuffer("");
//			if(adminList != null && adminList.size() > 0 ){
//				for(Admin temp : adminList){
//					//Admin tempAdmin = adminService.get(temp.getId());
//					sf.append(temp.getId()).append(",");
//				}
//				contractqueryCheckIndication.setCurrentAdminName(sf.substring(0,sf.length()-1).toString());//当前处理人
//			}
			session.saveOrUpdate(contractqueryCheckIndication);
			cfList.add(contractqueryCheckIndication);
			contractqueryCheckIndication.setRowId(contractqueryCheckIndication.getId());
			session.flush();
		}else{
			throw new PersonalException(entryIndication.getValue()+" 已存在");
		}
		rowId = contractqueryCheckIndication.getId();
		buffer.append(contractqueryCheckIndication.getId()).append(",");
		while(iterator.hasNext()){
			Entry<String, String> entry = iterator.next();
			String hqlCheck = "from ContractField as cf where cf.contractCategoryId = :contractCategoryId"
					+ " and cf.fieldName = :fieldName  and cf.rowId = :rowId ";
			Query queryCheck = session.createQuery(hqlCheck);
			queryCheck.setParameter("contractCategoryId", contractCategoryId);
			queryCheck.setParameter("fieldName", entry.getKey());
			queryCheck.setParameter("rowId", rowId);

			//queryCheck.setParameter("rowName", rowData[0]);

			ContractField contractField = (ContractField) queryCheck.uniqueResult();
			if(contractField==null){
				contractField =new ContractField();
			}
			if(category != null){
//				contractField.setDeparment(category.getDeparment());//部门
//				if(category.getApprovalNeeded() == WorkFlowContants.CHECK){//审核
					contractField.setStatu(WorkFlowContants.RELATION);//待关联状态
///				}else{
//					contractField.setStatu(WorkFlowContants.NONMAL);//正常状态
//				}
			}
			contractField.setCurrentAdminName(loginAdmin.getId());
			contractField.setContractCategoryId(contractCategoryId);
			contractField.setFieldName(entry.getKey());
			contractField.setRowId(rowId);
			contractField.setValue(entry.getValue());
//			contractField.setAdminList(new HashSet<Admin>(adminList));//用户
//			StringBuffer sf = new StringBuffer("");
//			if(adminList != null && adminList.size() > 0 ){
//				for(Admin temp : adminList){
//					//Admin tempAdmin = adminService.get(temp.getId());
//					sf.append(temp.getId()).append(",");
//				}
//				contractField.setCurrentAdminName(sf.substring(0,sf.length()-1).toString());//当前处理人
//			}
			
			contractField.setIndication(false);
			session.saveOrUpdate(contractField);
			cfList.add(contractField);
			session.flush();
			buffer.append(contractField.getId()).append(",");
		}
		
		return cfList;
		
	}

	public String update(String parentRowId,String rowId, String contractCategoryId,ContractEntity<String, String> entity,Admin adminList){
		parentRowId = StringUtils.defaultIfBlank(parentRowId, null);
		StringBuffer buffer = new StringBuffer();
		Iterator<Entry<String, String>> iterator =  entity.entrySet().iterator();
		Entry<String, String> entryIndication = iterator.next();
		Session session = this.getSession();
		String hqlCheckIndication = "from ContractField as cf where cf.contractCategoryId = :contractCategoryId"
				+ " and cf.fieldName = :fieldName and cf.rowId = :rowId";
		Query queryCheckIndication = session.createQuery(hqlCheckIndication);
		queryCheckIndication.setParameter("contractCategoryId", contractCategoryId);
		queryCheckIndication.setParameter("fieldName", entryIndication.getKey());
		queryCheckIndication.setParameter("rowId", rowId);
		ContractField contractqueryCheckIndication = (ContractField) queryCheckIndication.uniqueResult();
		ContractCategory category = null;
		if(StringUtils.isNotEmpty(contractCategoryId)){
			category = categoryDao.get(contractCategoryId);//获取分类
		}
		
		if(contractqueryCheckIndication==null){
			throw new PersonalException(entryIndication.getKey()+entryIndication.getValue()+" 不存在");
		}
//		if(contractqueryCheckIndication.isRecyle()){
//			throw new PersonalException(entryIndication.getKey()+entryIndication.getValue()+" 已被删除，请先在回收站清空");
//		}
		contractqueryCheckIndication.setCurrentAdminName(adminList.getId());//当前处理人
		contractqueryCheckIndication.setModifyAdminName(adminList.getName());
		contractqueryCheckIndication.setModifyAdminId(adminList.getId());
		contractqueryCheckIndication.setParentRowId(parentRowId);
		rowId = contractqueryCheckIndication.getId();
		buffer.append(contractqueryCheckIndication.getId()).append(",");
//		iterator =  entity.entrySet().iterator();
//		int i=0;
		iterator = null;
		iterator = entity.entrySet().iterator();
		while(iterator.hasNext()){
			Entry<String, String> entry = iterator.next();
			String hqlCheck = "from ContractField as cf where cf.contractCategoryId = :contractCategoryId"
					+ " and cf.fieldName = :fieldName  and cf.rowId = :rowId ";
			Query queryCheck = session.createQuery(hqlCheck);
			queryCheck.setParameter("contractCategoryId", contractCategoryId);
			queryCheck.setParameter("fieldName",entry.getKey());
			queryCheck.setParameter("rowId", rowId);

			//queryCheck.setParameter("rowName", rowData[0]);

			ContractField contractField = (ContractField) queryCheck.uniqueResult();
			if(contractField==null){
				contractField =new ContractField();
			}
			/*if(category != null){
				//contractField.setDeparmentSet(category.getDeparmentSet());//设置部门
				if(category.getApprovalNeeded() == WorkFlowContants.CHECK){//审核
					contractField.setStatu(WorkFlowContants.PRE_RELATION);//待关联状态
				}else{
					contractField.setStatu(WorkFlowContants.NONMAL);//正常状态
				}
			}*/
			contractField.setContractCategoryId(contractCategoryId);
			contractField.setFieldName(entry.getKey());
			contractField.setRowId(rowId);

			if(category != null){
				//检测流程完整性
				if(WorkFlowContants.CHECK.intValue() == category.getApprovalNeeded()){//需要检查
					contractField.setUpdateValue(entry.getValue());//更新到存储变量里面去
				}else{
					contractField.setValue(entry.getValue());
				}
			}
			
			if(adminList != null){
				contractField.setCurrentAdminName(adminList.getId());//当前处理人
			}
//			contractField.setIndication((i==0)?true:false);
			session.saveOrUpdate(contractField);
			session.flush();
//			i++;
			buffer.append(contractField.getId()).append(",");
		}
		
		if(category != null){
			//检测流程完整性
			if(WorkFlowContants.CHECK.intValue() == category.getApprovalNeeded()){//需要审核
				if(StringUtils.isNotEmpty(category.getWorkFlowId())){
					WorkFlow tempFlow = workFlowDao.get(category.getWorkFlowId());
					if(tempFlow != null){
						if(!(tempFlow.getWorkFlowPointSet() != null && tempFlow.getWorkFlowPointSet().size() > 0)){
							throw new PersonalException("所属二级分类审批流程节点未定义!");
						}
					}else{
						throw new PersonalException("所属二级分类流程不存在 ,请选择其他流程!");
					}
					
				}else{
					throw new PersonalException("所属二级分类审批流程未创建!");
				}
				List<ContractEntity<String, String>> resultList = new ArrayList<ContractEntity<String, String>>();//字段显示集合
				ContractEntity<String, String> contractEntity = new ContractEntity<String, String>(entity);
				contractEntity.setRowId(rowId);
				contractEntity.setContractCategoryId(contractCategoryId);
				resultList.add(contractEntity);
				//创建工作
				createWork(buffer.toString(),adminList,category,WorkFlowContants.UPDATE,resultList,WorkFlowContants.UPDATE_MESSAGE);
			}
		}
		
		return buffer.toString();
	}

	public String update4work(String contractCategoryId,ContractEntity<String, String> entity){
		Admin loginAdmin = adminDao.getAdminByUsername((String)ActionContext.getContext().getSession().get("SPRING_SECURITY_LAST_USERNAME"));
		StringBuffer buffer = new StringBuffer();
		Iterator<Entry<String, String>> iterator =  entity.entrySet().iterator();
		Entry<String, String> entryIndication = iterator.next();
		Session session = this.getSession();
		String hqlCheckIndication = "from ContractField as cf where cf.contractCategoryId = :contractCategoryId"
				+ " and cf.fieldName = :fieldName and cf.value = :value";
		Query queryCheckIndication = session.createQuery(hqlCheckIndication);
		queryCheckIndication.setParameter("contractCategoryId", contractCategoryId);
		queryCheckIndication.setParameter("fieldName", entryIndication.getKey());
		queryCheckIndication.setParameter("value", entryIndication.getValue());
		ContractField contractqueryCheckIndication = (ContractField) queryCheckIndication.uniqueResult();
//		ContractCategory category = null;
//		if(StringUtils.isNotEmpty(contractCategoryId)){
//			category = categoryDao.get(contractCategoryId);//获取分类
//		}
		
		if(contractqueryCheckIndication==null){
			throw new PersonalException(entryIndication.getValue()+" 不存在");
		}
		contractqueryCheckIndication.setCurrentAdminName(loginAdmin.getId());
		contractqueryCheckIndication.setModifyAdminName(loginAdmin.getName());
		contractqueryCheckIndication.setModifyAdminId(loginAdmin.getId());
		String rowId = contractqueryCheckIndication.getId();
		buffer.append(contractqueryCheckIndication.getId()).append(",");
		iterator =  entity.entrySet().iterator();
		int i=0;
		while(iterator.hasNext()){
			Entry<String, String> entry = iterator.next();
			String hqlCheck = "from ContractField as cf where cf.contractCategoryId = :contractCategoryId"
					+ " and cf.fieldName = :fieldName  and cf.rowId = :rowId ";
			Query queryCheck = session.createQuery(hqlCheck);
			queryCheck.setParameter("contractCategoryId", contractCategoryId);
			queryCheck.setParameter("fieldName",entry.getKey());
			queryCheck.setParameter("rowId", rowId);

			//queryCheck.setParameter("rowName", rowData[0]);

			ContractField contractField = (ContractField) queryCheck.uniqueResult();
			if(contractField==null){
				contractField =new ContractField();
			}
//			if(category != null){
//				//contractField.setDeparmentSet(category.getDeparmentSet());//设置部门
//				if(category.getApprovalNeeded() == WorkFlowContants.CHECK){//审核
//					contractField.setStatu(WorkFlowContants.PRE_RELATION);//待关联状态
//				}else{
//					contractField.setStatu(WorkFlowContants.NONMAL);//正常状态
//				}
//			}
			contractField.setCurrentAdminName(loginAdmin.getId());
			contractField.setContractCategoryId(contractCategoryId);
			contractField.setFieldName(entry.getKey());
			contractField.setRowId(rowId);
			contractField.setValue(entry.getValue());
//			StringBuffer sf = new StringBuffer("");
//			contractField.setAdminList(new HashSet<Admin>(adminList));//用户
//			if(adminList != null && adminList.size() > 0 ){
//				for(Admin temp : adminList){
//					//Admin tempAdmin = adminService.get(temp.getId());
//					sf.append(temp.getId()).append(",");
//				}
//				contractField.setCurrentAdminName(sf.substring(0,sf.length()-1).toString());//当前处理人
//			}
			
			contractField.setIndication((i==0)?true:false);
			session.saveOrUpdate(contractField);
			session.flush();
			i++;
			buffer.append(contractField.getId()).append(",");
		}
		return buffer.toString();
	}

	public void saveOrUpdate(String contractCategoryId,ContractEntity<String, String> entity) {
		Iterator<Entry<String, String>> iterator =  entity.entrySet().iterator();
		Entry<String, String> entryIndication = iterator.next();
		String rowId=null;
		Session session = this.getSession();
		String hqlCheckIndication = "from ContractField as cf where cf.contractCategoryId = :contractCategoryId"
				+ " and cf.fieldName = :fieldName and cf.value = :value";
		Query queryCheckIndication = session.createQuery(hqlCheckIndication);
		queryCheckIndication.setParameter("contractCategoryId", contractCategoryId);
		queryCheckIndication.setParameter("fieldName", entryIndication.getKey());
		queryCheckIndication.setParameter("value", entryIndication.getValue());
		ContractField contractqueryCheckIndication = (ContractField) queryCheckIndication.uniqueResult();
		if(contractqueryCheckIndication==null){
			contractqueryCheckIndication=new ContractField();
			contractqueryCheckIndication.setContractCategoryId(contractCategoryId);
			contractqueryCheckIndication.setFieldName(entryIndication.getKey());
			contractqueryCheckIndication.setValue(entryIndication.getValue());
			contractqueryCheckIndication.setIndication(true);
			session.saveOrUpdate(contractqueryCheckIndication);
			contractqueryCheckIndication.setRowId(contractqueryCheckIndication.getId());
			session.flush();
		}
		rowId = contractqueryCheckIndication.getId();
		
		while(iterator.hasNext()){
			Entry<String, String> entry = iterator.next();
			String hqlCheck = "from ContractField as cf where cf.contractCategoryId = :contractCategoryId"
					+ " and cf.fieldName = :fieldName  and cf.rowId = :rowId ";
			Query queryCheck = session.createQuery(hqlCheck);
			queryCheck.setParameter("contractCategoryId", contractCategoryId);
			queryCheck.setParameter("fieldName",entry.getKey());
			queryCheck.setParameter("rowId", rowId);

			//queryCheck.setParameter("rowName", rowData[0]);

			ContractField contractField = (ContractField) queryCheck.uniqueResult();
			if(contractField==null){
				contractField =new ContractField();
			}
			contractField.setContractCategoryId(contractCategoryId);
			contractField.setFieldName(entry.getKey());
			contractField.setRowId(rowId);
			contractField.setValue(entry.getValue());
			contractField.setIndication(false);
			session.saveOrUpdate(contractField);
			session.flush();
		}
	}
	
	public List<String> selectForRowName(String contractCategoryId, String[] rowIds){
		Session session = this.getSession();
		String hqlDelete = " select value from ContractField where contractCategoryId = :contractCategoryId"
				+ " and rowId in (:rowIds) and indication = true ";
		Query query = session.createQuery(hqlDelete);
		  query.setString("contractCategoryId", contractCategoryId);
		  query.setParameterList("rowIds", rowIds);
		  return query.list();
	}
	
	public void deleteForRowIds(String contractCategoryId, String[] rowIds){
		Session session = this.getSession();
//		String hqlDelete = "update ContractField set recyle=true where contractCategoryId = :contractCategoryId"
		String hqlDelete = "delete ContractField  where contractCategoryId = :contractCategoryId"
				+ " and rowId in (:rowIds) ";
		Query query = session.createQuery(hqlDelete);
		  query.setString("contractCategoryId", contractCategoryId);
		  query.setParameterList("rowIds", rowIds);
		  query.executeUpdate();
	}
	
	/**
	 * 获取字段的集合
	 * @param contractCategoryId
	 * @param rowId
	 */
	public Map<String,Object> getFieldList(String contractCategoryId, String rowId){
		Map<String,Object> resultMap = new HashMap<String,Object>();//结果集Map
		ContractEntity<String, String>  result = new ContractEntity<String, String>();
		result.setRowId(rowId);
		result.setContractCategoryId(contractCategoryId);
		Session session = this.getSession();
		String hqlDelete = "from ContractField where contractCategoryId =:contractCategoryId"
				+ " and rowId =:rowId ";
		Query query = session.createQuery(hqlDelete);
		  query.setString("contractCategoryId", contractCategoryId);
		  query.setString("rowId", rowId);
		  List<ContractField>  fieldList =  query.list();
		  StringBuffer buffer = new StringBuffer();
		  for(ContractField field : fieldList){
			  buffer.append(field.getId()).append(",");
			  result.put(field.getFieldName(), field.getValue());
		  }
		  resultMap.put("fieldId", buffer.toString());
		  resultMap.put("entity", result);//实体
		  return resultMap;
	}
	
	public void deleteFromContractCategoryId(String contractCategoryId){
//		deleteForeign_key_checks();
		Session session = this.getSession();
		String hqlDelete = "delete from ContractField where contractCategoryId = :contractCategoryId";
		Query query = session.createQuery(hqlDelete);
		  query.setString("contractCategoryId", contractCategoryId);
		  query.executeUpdate();
		
	}
	
/*	//取消约束
	private void deleteForeign_key_checks(){
		Session session = this.getSession();
		String sql = "SET FOREIGN_KEY_CHECKS = 0";
		session.createSQLQuery(sql).executeUpdate();
	}
*/
	/**
	 * saveOrUpdate 的加速版
	 * @param contractCategoryId
	 * @param entity
	 * @throws Exception
	 */
	public String saveOrUpdateExt(String contractCategoryId,ContractEntity<String, String> entity,Admin loginAdmin){
		StringBuffer buffer = new StringBuffer();
		Iterator<Entry<String, String>> iterator =  entity.entrySet().iterator();
		Entry<String, String> entryIndication = iterator.next();
		String rowId=null;
		Session session = this.getSession();
		//String userId = ServletActionContext.getRequest().getParameter("userId");//获取用户id
		String hqlCheckIndication = "from ContractField as cf where cf.contractCategoryId = :contractCategoryId"
				+ " and cf.fieldName = :fieldName and cf.value = :value";
		Query queryCheckIndication = session.createQuery(hqlCheckIndication);
		queryCheckIndication.setParameter("contractCategoryId", contractCategoryId);
		queryCheckIndication.setParameter("fieldName", entryIndication.getKey());
		queryCheckIndication.setParameter("value", entryIndication.getValue());
		ContractField contractqueryCheckIndication = (ContractField) queryCheckIndication.uniqueResult();
		ContractCategory category = null;
		if(StringUtils.isNotEmpty(contractCategoryId)){
			category = categoryDao.get(contractCategoryId);//获取分类
		}
		if(contractqueryCheckIndication==null){
			contractqueryCheckIndication=new ContractField();
			contractqueryCheckIndication.setCreateAdminName(loginAdmin.getName());
			contractqueryCheckIndication.setCreateAdminId(loginAdmin.getId());
		}
		contractqueryCheckIndication.setModifyAdminName(loginAdmin.getName());
		contractqueryCheckIndication.setModifyAdminId(loginAdmin.getId());
//		if(contractqueryCheckIndication.isRecyle()){
//			throw new RuntimeException(entryIndication.getKey()+entryIndication.getValue()+" 已被删除，请先在回收站清空");
//		}
		
//		if(StringUtils.isNotEmpty(loginAdmin)){
			contractqueryCheckIndication.setCurrentAdminName(loginAdmin.getId());//用户id
//		}
		if(category != null){
		 //	contractqueryCheckIndication.setDeparment(category.getDeparment());
			//contractqueryCheckIndication.setDeparmentSet(category.getDeparmentSet());//设置部门
			if(category.getApprovalNeeded() == WorkFlowContants.CHECK){//审核
				contractqueryCheckIndication.setStatu(WorkFlowContants.PRE_RELATION);//待关联状态
			}else{
				contractqueryCheckIndication.setStatu(WorkFlowContants.NONMAL);//正常状态
			}
		}
		contractqueryCheckIndication.setContractCategoryId(contractCategoryId);
		contractqueryCheckIndication.setFieldName(entryIndication.getKey());
		contractqueryCheckIndication.setValue(entryIndication.getValue());
		contractqueryCheckIndication.setIndication(true);
		session.saveOrUpdate(contractqueryCheckIndication);
		contractqueryCheckIndication.setRowId(contractqueryCheckIndication.getId());
		session.flush();
		rowId = contractqueryCheckIndication.getId();
		buffer.append(contractqueryCheckIndication.getId()).append(",");
		
		String hqlDelete = "delete from ContractField where contractCategoryId = :contractCategoryId"
				+ " and rowId = :rowId and indication = :indication";
		Query queryDelete = session.createQuery(hqlDelete);
		queryDelete.setParameter("contractCategoryId", contractCategoryId);
		queryDelete.setBoolean("indication",false);
		queryDelete.setParameter("rowId", rowId);
		try{
		queryDelete.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}
		while(iterator.hasNext()){
			Entry<String, String> entry = iterator.next();
			ContractField contractField = new ContractField();
//		   if(StringUtils.isNotEmpty(userId)){
				contractField.setCurrentAdminName(loginAdmin.getId());//用户id
//			}
			if(category != null){
			//	contractField.setDeparment(category.getDeparment());
				//contractField.setDeparmentSet(category.getDeparmentSet());//设置部门
				if(category.getApprovalNeeded() == WorkFlowContants.CHECK){//审核
					contractField.setStatu(WorkFlowContants.PRE_RELATION);//待关联状态
				}else{
					contractField.setStatu(WorkFlowContants.NONMAL);//正常状态
				}
			}
			contractField.setContractCategoryId(contractCategoryId);
			contractField.setFieldName(entry.getKey());
			contractField.setRowId(rowId);
			contractField.setValue(entry.getValue());
			contractField.setIndication(false);
			session.saveOrUpdate(contractField);
			session.flush();
			buffer.append(contractField.getId()).append(",");
		}
	
		return buffer.toString();
		
	}

	
	public List<ContractField> getContractPrimaryRows(String contractCategoryId){
//		String hqlstr = "from ContractField as cf where cf.contractCategoryId = :contractCategoryId and (cf.statu = 0 or cf.statu = 1) and cf.indication = true and cf.recyle = false ";
		String hqlstr = "from ContractField as cf where cf.contractCategoryId = :contractCategoryId and (cf.statu = 0 or cf.statu = 1) and cf.indication = true ";
		return getSession().createQuery(hqlstr).setString("contractCategoryId", contractCategoryId).list();
	}

	public List<ContractField> getFieldListByColumn(String contractCategoryId,String fieldName){
		String hqlstr = "from ContractField as cf where cf.contractCategoryId = :contractCategoryId and cf.fieldName = :fieldName and exists( select 1 from ContractField as cf2 where cf2.id = cf.rowId and (cf2.statu = 0 or cf2.statu = 1) ) ";
		return getSession().createQuery(hqlstr).setString("contractCategoryId", contractCategoryId).setString("fieldName", fieldName).list();
	}
	
	public List<Map> getUserPlanRelation(String userPlanId){
		String hqlstr = "select f1.id as id,f1.value as number,f2.value as text  from rzrk_user_plan_require as ur,rzrk_contract_field as f1 left join rzrk_contract_field as f2 on (f1.id = f2.row_id and f2.field_name='需求内容')"
				+ " where f1.id = ur.row_id and f1.indication=true  and  ur.user_plan_id = :userPlanId and f1.contract_category_id='"+CategoryContants.REQUIREMENT_CATEGORY_ID+"' ";
		return getSession().createSQLQuery(hqlstr).setString("userPlanId", userPlanId).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		
	}
	public List<Map> getProjectRelation(String projectId){
		String sql = " select f1.id as id,f1.`value` as number,f2.`value` as text from rzrk_contract_field as f1 left join rzrk_contract_field as f2 on (f1.id = f2.row_id and f2.field_name='需求内容') "
				+ " where f1.indication=true "
				+ " and f1.project_id=:projectId and f1.contract_category_id='"+CategoryContants.REQUIREMENT_CATEGORY_ID+"' ";
		Query query = getSession().createSQLQuery(sql).setString("projectId", projectId).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.list();
	};

}