/**
 * Project Name: rzrk Web
 * Confidential and Proprietary                                                                
 * Copyright (C) 2013 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.dao.impl;

import java.math.BigDecimal;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.apache.commons.io.IOUtils;
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
import com.rzrk.dao.UnionContractCategoryDao;
import com.rzrk.dao.UnionContractDao;
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
import com.rzrk.entity.UnionContractCategory;
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
import com.rzrk.util.JsUtil;
import com.rzrk.util.JsonUtil;
import com.rzrk.util.StrUtil;
import com.rzrk.util.date.DateUtils;
import com.rzrk.vo.DeparmentAdminVo;
import com.rzrk.vo.querytree.Cond;
import com.rzrk.vo.querytree.KeyValueCollection;
import com.rzrk.vo.querytree.Node;
import com.rzrk.vo.unioncontract.Definition;
import com.rzrk.vo.unioncontract.Field;
import com.rzrk.vo.workflow.Project4workflow;

/**
 * Dao实现类 - 分类
 */

@Repository("unionContractDaoImpl")
public class UnionContractDaoImpl extends BaseDaoImpl<ContractField, String> implements UnionContractDao{

	@Resource
	private AdminDao adminDao;
	
	/**
	 * 分类dao
	 */
	@Resource
	private UnionContractCategoryDao  unionContractCategoryDao;
	@Resource
	private ContractCategoryDao contractCategoryDao;
	

   /**
    * 添加部门领导到人员集合中	
    * @param deparment
    * @param adminList
    */
	public void  addDepermantManage(Deparment deparment,Set<String> generalList,Set<String> leaderList,UnionContractCategory category){
		
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
	

	

	private void queryTreeAddCondWithIndex(Definition definition,StringBuffer sqlCount,Node node,KeyValueCollection kc){
		
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
			}
			String searchBy = "searchBy"+kc.getIndex();
			String keyword = "keyword"+kc.getIndex();
			kc.addIndex();
			String searchWd = cond.getSearchBy();
//			String[] _swdarr = searchWd.split("_"); //TODO 有问题
//			String searchByField = _swdarr[0];
//			String searchByTable = _swdarr[1];
			int oindex = org.apache.commons.lang3.StringUtils.lastIndexOf(searchWd, "_");
			String searchByField = searchWd.substring(0,oindex);
			String searchByTable = searchWd.substring(oindex+1);
			String contract_category_id = definition.getTableIdByName(searchByTable);
			kc.getParamMap().put(searchBy, searchByField);
			if("like".equalsIgnoreCase(operateEq)){
				kc.getParamMap().put(keyword,"%"+cond.getKeyword()+"%" );
			}else{
				kc.getParamMap().put(keyword, cond.getKeyword() );
			}
			kc.getOperateMap().put(keyword,operateEq );
			sqlCount .append(operateExists+" (select 1 from rzrk_contract_field AS cf2,rzrk_contract_field AS cf2f "
					+ " where cf2.indication = TRUE and cf2.contract_category_id = '"+contract_category_id+"' and (cf2.statu = 0 or cf2.statu = 1) and cf.`value`= cf2.`value` and "
							+ " cf2f.row_id=cf2.id and cf2f.field_name=? and cf2f.value "+operateEq+" ?) ");
			
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
				queryTreeAddCondWithIndex(definition,sqlCount,_node,kc);
				sqlCount.append(" ) ");
			}
		}
	}
	
	private String queryByPermission(UnionContractCategory category,Admin loginAdmin,Set<String> generalList){
		
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
	
	/**
	 * 依次为主键字段，其他字段，rowId，创建人Id 。。。。。。
	 */
	public Map<String,Object>  findPager(String unionContractCategoryId,Pager pager,Node node){
		return findPager(unionContractCategoryId,pager,node,new HashMap<String, Object>());
	};

	public Map<String,Object>  findPager(String unionContractCategoryId,Pager pager,Node node,Map<String,Object> rowCondMap){
		Map<String,Object> dataMap = new HashMap<String, Object>();
		int start = (pager.getPageNumber()-1)*pager.getPageSize();
		if(start<0){
			start = 0;
		}
		Admin loginAdmin = adminDao.getAdminByUsername((String)ActionContext.getContext().getSession().get("SPRING_SECURITY_LAST_USERNAME"));
		UnionContractCategory unionContractCategory = unionContractCategoryDao.get(unionContractCategoryId);
		Definition definition = unionContractCategory.getDefinitionObj();
		//获取所有的部门领导人
		Connection conn =	this.getSession().connection();
		
		//获取所有的部门领导人
		Set<String> leaderList = new HashSet<String>();//部门的领导
		Set<String> generalList = new HashSet<String>();//部门的普通员工
		for(Deparment temp : unionContractCategory.getDeparmentSet()){
			addDepermantManage(temp,generalList,leaderList, unionContractCategory);
		}
		String contractCategoryId = unionContractCategory.getDefinitionObj().getTableIdSet().iterator().next();
		{
			
			
			
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
//						"				cf.indication = TRUE and cf.contract_category_id = '"+contractCategoryId+"' and (cf.statu = 0 or cf.statu = 1)  and cf.recyle=false ";
						"				cf.indication = TRUE and cf.contract_category_id = '"+contractCategoryId+"' and (cf.statu = 0 or cf.statu = 1) ";
				
						if(StringUtils.isBlank(unionContractCategory.getControlType())){
							//无限制随便看
						}else if(StringUtils.contains(unionContractCategory.getControlType(), ContractCategory.CONTROL_TYPE_BY_DEP) && leaderList.contains(loginAdmin.getId())){
							//是领导，可以随便看无限制
						}else{
							sqlCount+= " and " + queryByPermission(unionContractCategory, loginAdmin, generalList);
						}
						StringBuffer tmpsqlCount = new StringBuffer();
						queryTreeAddCondWithIndex(definition,tmpsqlCount,node,kcCount);
						if(StringUtils.isNotBlank(tmpsqlCount.toString())){
							sqlCount += " AND ("+ tmpsqlCount.toString()+") ";
						}
						sqlCount+= " 		) as temp     ";
			
			long total = 0;
			try {
				PreparedStatement stCount = conn.prepareStatement(sqlCount);
				List<Object> params=new ArrayList<Object>(kcCount.getParamMap().values());
				for(int pindex=0;pindex<params.size();pindex++){
					stCount.setObject(pindex+1, params.get(pindex));
				}
				ResultSet rsCount = stCount.executeQuery();
				if(rsCount.next()){
					total = rsCount.getLong("num");
				}
				rsCount.close();
				stCount.close();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
			dataMap.put("total", (int)total);
			
			
		}
		
		{
			KeyValueCollection kc = new KeyValueCollection();
			String sql = "select * from (";
			int kcindex=0;

			for(String tableId : definition.getTableIdSet()){
				//获取所有的部门领导人
				ContractCategory tableCategory = contractCategoryDao.get(tableId);//获取分类
				Assert.notNull(tableCategory, "无效的二级分类类型");				

				if(kcindex!=0){
					sql += " union all ";
				}
				sql += "(	SELECT "
						+ "		f.id,f.field_name,f.`value`,f.indication,tmp.id as row_id ,'"+definition.getTableNameById(tableId)+"' AS table_name, tmp.rownum as rownum "
						+ "	FROM"
						+"		rzrk_contract_field AS fm,rzrk_contract_field AS f,"
						+ "		("
						+ "		SELECT @rownum:=@rownum+1 AS rownum,tmpi.id,tmpi.`value` "
						+ "		FROM (SELECT @rownum:=0) rn,(" 
						+ "			SELECT" 
						+ "				cf.id,cf.`value` "
						+ "			FROM" 
						+ "				rzrk_contract_field AS cf ";
				if (StringUtils.isNotEmpty(pager.getOrderBy())) {
					String orderBy = pager.getOrderBy();
					int oindex = org.apache.commons.lang3.StringUtils.lastIndexOf(orderBy, "_");
					String orderByField = orderBy.substring(0,oindex);
					String orderByTable = orderBy.substring(oindex+1);
					String contract_category_id = definition.getTableIdByName(orderByTable);
					
					sql += "			left join rzrk_contract_field AS cf2 on cf2.indication = TRUE and cf2.contract_category_id = '"+contract_category_id+"' and (cf2.statu = 0 or cf2.statu = 1) and cf.`value`= cf2.`value` "
						+ " 			left join rzrk_contract_field AS cfo on cfo.row_id=cf2.id and cfo.field_name='"+ orderByField + "' ";
				}
				;

				sql += "	WHERE"
//						+ "				cf.indication = TRUE and cf.contract_category_id = '"+ contractCategoryId + "' and (cf.statu = 0 or cf.statu = 1)   and cf.recyle=false ";
						+ "				cf.indication = TRUE and cf.contract_category_id = '"+ contractCategoryId + "' and (cf.statu = 0 or cf.statu = 1) ";
				
				if(StringUtils.isBlank(unionContractCategory.getControlType())){
					//无限制随便看
				}else if(StringUtils.contains(unionContractCategory.getControlType(), ContractCategory.CONTROL_TYPE_BY_DEP) && leaderList.contains(loginAdmin.getId())){
					//是领导，可以随便看无限制
				}else{
					sql+= " and " + queryByPermission(unionContractCategory, loginAdmin, generalList);
				}
				StringBuffer tmpsql = new StringBuffer();
				queryTreeAddCondWithIndex(definition,tmpsql, node, kc);
				if (StringUtils.isNotBlank(tmpsql.toString())) {
					sql += " AND (" + tmpsql.toString() + ") ";
				}
				if (StringUtils.isNotEmpty(pager.getOrderBy())) {
					sql += " order by CONVERT(cfo.`value`,DECIMAL) " + pager.getOrder()
							+ " ,cfo.`value` " + pager.getOrder()
							+ ",cf.create_date desc ";
				} else {
					sql += " order by cf.create_date desc ";
				}
				sql += "			LIMIT " + start + "," + pager.getPageSize() + "		) AS tmpi"
						+ "	) AS tmp "
						+"	where fm.indication = TRUE and fm.`value` = tmp.`value` and fm.contract_category_id='"+tableId+"' "
						+ " and f.row_id = fm.id "
						+ " order by tmp.rownum )";
				kcindex++;
							
				
			}
			sql += " ) as tmpu order by tmpu.rownum ";

			//收集原始值
			List<ContractField> originalList = new ArrayList<ContractField>();
			try {
				PreparedStatement st = conn.prepareStatement(sql);
				List<Object> params = new ArrayList<Object>(kc.getParamMap()
						.values());
				for (int pindex = 0; pindex < params.size(); pindex++) {
					st.setObject(pindex + 1, params.get(pindex));
				}
				ResultSet rs = st.executeQuery();
				while (rs.next()) {
					String id = rs.getString("id");
					String rowId = rs.getString("row_id");
					String field_name = rs.getString("field_name");
					String table_name = rs.getString("table_name");
					String value = rs.getString("value");
					Boolean indication = rs.getBoolean("indication");
					ContractField contractField = new ContractField();
					contractField.setId(id);
					contractField.setRowId(rowId);
					contractField.setFieldName(field_name+"_"+table_name);
					contractField.setValue(value);
					contractField.setIndication(indication);
					originalList.add(contractField);
				}
				rs.close();
				st.close();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
			 //行排序，列未排序集合
			LinkedHashMap<String, Map<String, String>> noOrderMap = new LinkedHashMap<String, Map<String,String>>();
			for(ContractField field : originalList){
				if (field.isIndication()){
					HashMap<String, String> item = new HashMap<String, String>();
					item.put("rowId", field.getRowId());
//					item.put("showField", field.getFieldName());
//					item.put("value", field.getValue());
					noOrderMap.put(field.getRowId(), item);
				}
			}
			for(ContractField field : originalList){
				Map<String, String> item =  noOrderMap.get(field.getRowId());
				if (item!=null){
					item.put(field.getFieldName(), field.getValue());
				}
			}
			//准备有权限的列名
//			List<Field> fieldList = definition;
			List<Field> fieldList = unionContractCategoryDao.getPermissionField(unionContractCategory, loginAdmin);
			Collection<String> titleList = CollectionUtils.collect(fieldList, new Transformer() {
				@Override
				public Object transform(Object arg0) {
					return ((Field)arg0).getShowField();
				}
			});
			 //有序结果
			List<Map<String,String>> orderlist = new ArrayList<Map<String,String>>();
			for(Map<String, String> noOrderEntity: noOrderMap.values()){
				LinkedHashMap<String, String> orderEntity = new LinkedHashMap<String, String>();
				for(Field fieldDef : definition){
					String key = fieldDef.getShowField();
					if(titleList.contains(key)){
						orderEntity.put(key, StringUtils.defaultIfBlank(noOrderEntity.get(key),""));
					}else{
						orderEntity.put(key,"");
					}
				}
				orderEntity.put("rowId",noOrderEntity.get("rowId"));
				orderlist.add(orderEntity);
			}
			//计算表达式
			Definition expDef = definition.getExpressionFileds();
	        Pattern pattern=Pattern.compile("\\$\\((.+?)\\)");
			for(Map<String, String> entity : orderlist){
				for(Field field : expDef){
					String expStr = field.getExpression();
					if(StringUtils.isNotBlank(expStr)){
				        Matcher matcher=pattern.matcher(expStr);
				        StringBuffer sb=new StringBuffer();
				        boolean find=false;
				        while(find=matcher.find()){
				        	String name = matcher.group(1);
				        	String placeholder = entity.get(name);
				        	if(StringUtils.isBlank(placeholder)){
				        		placeholder = "0";
				        	}
				        	matcher.appendReplacement(sb,placeholder);
				        }
				        matcher.appendTail(sb);
				        String expStrTarget = sb.toString();
				        Number num = JsUtil.getNumber(expStrTarget);
				        String res;
				        BigDecimal gd = BigDecimal.valueOf(num.doubleValue());
						if(gd.doubleValue() == gd.longValue()){
							res = gd.toBigInteger().toString();
						}else{
							res = BigDecimal.valueOf(num.doubleValue()).setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString();
						}
				        entity.put(field.getShowField(), res);
					}
				}
			}

			
			dataMap.put("result", orderlist);
			dataMap.put("footer", new ArrayList());
			
			
			
		}
		return dataMap;


	}
	
}