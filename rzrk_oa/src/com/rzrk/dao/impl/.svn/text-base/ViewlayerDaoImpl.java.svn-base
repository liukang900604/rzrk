package com.rzrk.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.opensymphony.xwork2.ActionContext;
import com.rzrk.bean.Pager;
import com.rzrk.contants.WorkFlowContants;
import com.rzrk.dao.AdminDao;
import com.rzrk.dao.ContractCategoryDao;
import com.rzrk.dao.ContractDao;
import com.rzrk.dao.DeparmentDao;
import com.rzrk.dao.ViewlayerDao;
import com.rzrk.entity.Admin;
import com.rzrk.entity.ContractCategory;
import com.rzrk.entity.ContractField;
import com.rzrk.entity.Deparment;
import com.rzrk.entity.Viewlayer;
import com.rzrk.vo.DeparmentAdminVo;
import com.rzrk.vo.queryhistory.DataItem;
import com.rzrk.vo.querytree.Cond;
import com.rzrk.vo.querytree.KeyValueCollection;
import com.rzrk.vo.querytree.Node;
import com.rzrk.vo.viewlayer.Definition;
import com.rzrk.vo.viewlayer.Field;

@Repository("viewlayerDaoImpl")
public class ViewlayerDaoImpl extends BaseDaoImpl<Viewlayer, String> implements ViewlayerDao {
	
	@Resource
	private AdminDao adminDao;
	@Resource
	private ContractCategoryDao contractCategoryDao;
	@Resource
	private ContractDao contractDao;
	
	public List<Viewlayer> getList(Admin admin){
		String sql = 
				"	SELECT"+
						"		rc.id as rcId,rc.`name` as rcName,"+
						"		cc.id as ccId,cc.`name` as ccName,"+
						"		qh.id as qhId,qh.`name` as qhName, qh.content as qhContent"+
						"	FROM"+
						"		rzrk_contract_category AS cc,"+
						"		rzrk_root_contract_category AS rc,"+
						"		rzrk_query_history AS qh"+
						"	WHERE"+
						"		qh.contract_category_id = cc.id"+
						"	AND"+
						"		rc.id = cc.root_contract_category_id"+
						"	AND qh.admin_id = :admin_id"+
						"	ORDER BY rc.create_date,cc.create_date,qh.create_date desc";
		List<Viewlayer> list = getSession().createSQLQuery(sql)
		.addScalar("rcId",Hibernate.STRING).addScalar("rcName",Hibernate.STRING)
		.addScalar("ccId",Hibernate.STRING).addScalar("ccName",Hibernate.STRING)
		.addScalar("qhId",Hibernate.STRING).addScalar("qhName",Hibernate.STRING).addScalar("qhContent",Hibernate.STRING)
		.setResultTransformer(Transformers.aliasToBean(DataItem.class))
		.setString("admin_id", admin.getId()).list();
		return list;
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
//				String[] _swdarr = searchWd.split("_"); //TODO 有问题
//				String searchByField = _swdarr[0];
//				String searchByTable = _swdarr[1];
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
		private String queryByPermission(ContractCategory category,Admin loginAdmin,Set<String> generalList){
			
			String byOpStr = "";
			if(StringUtils.contains(category.getControlType(), ContractCategory.CONTROL_TYPE_BY_OP)){
				byOpStr +=" (";
					if(!StringUtils.equals(category.getControlField(), ContractCategory.CONTROL_TYPE_BY_OP_CREATE_ADMIN)){
						byOpStr += " exists(select 1 from rzrk_contract_field as cfop where cfop.row_id = cf.id and cfop.field_name='"+category.getControlField()+"' and cfop.`value` ='"+loginAdmin.getName()+"' ) ";
//						+ " exists(select * from rzrk_admin_deparment as ad where ad.admin_id = cf.currentAdminName and ad.deparment_set_id in ("+StringUtils.defaultIfBlank(instr, "''")+")  ) "
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

		private String getSelect(Definition definition){
			ArrayList<String> selectList = new ArrayList<String>();
			
			for(String tableName : definition.getTableSet()){
				int i=0;
				for(Field field : definition){
					if(field.getTableName().equals(tableName)){
						String selectstr = "f"+i+"f."+field.getTableField();
						selectList.add(selectstr);
					}
				}
				i++;
			}
			String str = StringUtils.join(selectList,",");
			return str;
		}
		
		private String getJoin(Definition definition){
			ArrayList<String> joinList = new ArrayList<String>();
			
			for(String tableId : definition.getTableIdSet()){
				int i=0;
				String joinStr = "	left join rzrk_contract_field as f"+i+" on f"+i+".indication = TRUE and f"+i+".contract_category_id = '"+tableId+"' and (f"+i+".statu = 0 or f"+i+".statu = 1) and f"+i+".`value`= tmp.`value` " //TODO
						+ " left join rzrk_contract_field as f"+i+"f on f"+i+"f.row_id=f"+i+".id ";
				i++;
			}
			String str = StringUtils.join(joinList,"\n");
			return str;
		}
		
		@Resource
		ContractCategoryDao categoryDao;
		@Resource
		DeparmentDao deparmentDao;
	
		
	/**
	 * 和contractDao不同，没有直接查询出来最终排序的linkedhashmap。因为需要加工的太多
<pre>
SELECT
	*
FROM
	(
		(
			SELECT
				f.id,
				f.field_name,
				f.`value`,
				f.indication,
				tmp.id AS row_id,
				'zjfe二级分类' AS table_name,
				tmp.rownum AS rownum
			FROM
				rzrk_contract_field AS fm, #副表的主键字段
				rzrk_contract_field AS f,  #副表的普通字段
				(
					SELECT
						@rownum :=@rownum + 1 AS rownum, #序号排序
						tmpi.id,
						tmpi.`value`
					FROM
						(SELECT @rownum := 0) rn, #初始序号
						(
							SELECT
								cf.id,
								cf.`value`
							FROM
								rzrk_contract_field AS cf #主表主键
							WHERE
								cf.indication = TRUE
							AND cf.contract_category_id = 'ff8080824f263756014f26380f6d0005'
							AND (cf.statu = 0 OR cf.statu = 1)
							AND (
								(
									(
										cf.create_admin_id = '8a018b5443d16e6a0143d1f19ad40000'
									)
								)
								OR cf.create_admin_id = '8a018b5443d16e6a0143d1f19ad40000'
							)
							AND (
								(
									EXISTS (
										SELECT
											1
										FROM
											rzrk_contract_field AS cf2, #查询条件表主键
											rzrk_contract_field AS cf2f #查询条件表其他字段
										WHERE
											cf2.indication = TRUE
										AND cf2.contract_category_id = 'ff8080824f263756014f26380f6d0005'
										AND (cf2.statu = 0 OR cf2.statu = 1)
										AND cf.`value` = cf2.`value`
										AND cf2f.row_id = cf2.id
										AND cf2f.field_name =?
										AND cf2f.
										VALUE
											LIKE ?
									)
								)
							)
							ORDER BY
								cf.create_date DESC
							LIMIT 0,
							50
						) AS tmpi
				) AS tmp
			WHERE
				fm.indication = TRUE
			AND fm.`value` = tmp.`value`
			AND fm.contract_category_id = 'ff8080824f263756014f26380f6d0005'
			AND f.row_id = fm.id
			ORDER BY
				tmp.rownum
		)
		UNION ALL
			(
				SELECT
					f.id,
					f.field_name,
					f.`value`,
					f.indication,
					tmp.id AS row_id,
					'用户的4' AS table_name,
					tmp.rownum AS rownum
				FROM
					rzrk_contract_field AS fm,
					rzrk_contract_field AS f,
					(
						SELECT
							@rownum :=@rownum + 1 AS rownum,
							tmpi.id,
							tmpi.`value`
						FROM
							(SELECT @rownum := 0) rn,
							(
								SELECT
									cf.id,
									cf.`value`
								FROM
									rzrk_contract_field AS cf
								WHERE
									cf.indication = TRUE
								AND cf.contract_category_id = 'ff8080824f263756014f26380f6d0005'
								AND (cf.statu = 0 OR cf.statu = 1)
								AND (
									(
										(
											cf.create_admin_id = '8a018b5443d16e6a0143d1f19ad40000'
										)
									)
									OR cf.create_admin_id = '8a018b5443d16e6a0143d1f19ad40000'
								)
								AND (
									(
										EXISTS (
											SELECT
												1
											FROM
												rzrk_contract_field AS cf2,
												rzrk_contract_field AS cf2f
											WHERE
												cf2.indication = TRUE
											AND cf2.contract_category_id = 'ff8080824f263756014f26380f6d0005'
											AND (cf2.statu = 0 OR cf2.statu = 1)
											AND cf.`value` = cf2.`value`
											AND cf2f.row_id = cf2.id
											AND cf2f.field_name =?
											AND cf2f.
											VALUE
												LIKE ?
										)
									)
								)
								ORDER BY
									cf.create_date DESC
								LIMIT 0,
								50
							) AS tmpi
					) AS tmp
				WHERE
					fm.indication = TRUE
				AND fm.`value` = tmp.`value`
				AND fm.contract_category_id = 'ff8080824f1a90df014f1a9c5b02000a'
				AND f.row_id = fm.id
				ORDER BY
					tmp.rownum
			)
	) AS tmpu
ORDER BY
	tmpu.rownum
</pre>
	 */
	public Map<String,Object>  findShowPager(Definition definition,Pager pager,Node node){
		Map<String,Object> dataMap = new HashMap<String, Object>();
		int start = (pager.getPageNumber()-1)*pager.getPageSize();
		if(start<0){
			start = 0;
		}
		Admin loginAdmin = adminDao.getAdminByUsername((String)ActionContext.getContext().getSession().get("SPRING_SECURITY_LAST_USERNAME"));
		ContractCategory category = null;
		//获取所有的部门领导人
		Connection conn =	this.getSession().connection();
		
		String contractCategoryId = definition.getTableIdSet().iterator().next();
		{
			//获取所有的部门领导人
			Set<String> leaderList = new HashSet<String>();//部门的领导
			Set<String> generalList = new HashSet<String>();//部门的普通员工
			category = categoryDao.get(contractCategoryId);//获取分类
			Assert.notNull(category, "无效的二级分类类型");
			for(Deparment temp : category.getDeparmentSet()){
				addDepermantManage(temp,generalList,leaderList, category);
			}
			
			
			
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
				
						if(StringUtils.isBlank(category.getControlType())){
							//无限制随便看
						}else if(StringUtils.contains(category.getControlType(), ContractCategory.CONTROL_TYPE_BY_DEP) && leaderList.contains(loginAdmin.getId())){
							//是领导，可以随便看无限制
						}else{
							sqlCount+= " and " + queryByPermission(category, loginAdmin, generalList);
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
				Set<String> leaderList = new HashSet<String>();//部门的领导
				Set<String> generalList = new HashSet<String>();//部门的普通员工
				ContractCategory tableCategory = categoryDao.get(tableId);//获取分类
				Assert.notNull(tableCategory, "无效的二级分类类型");
				for(Deparment temp : tableCategory.getDeparmentSet()){
					addDepermantManage(temp,generalList,leaderList, category);
				}
				

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
				
				if(StringUtils.isBlank(category.getControlType())){
					//无限制随便看
				}else if(StringUtils.contains(category.getControlType(), ContractCategory.CONTROL_TYPE_BY_DEP) && leaderList.contains(loginAdmin.getId())){
					//是领导，可以随便看无限制
				}else{
					sql+= " and " + queryByPermission(category, loginAdmin, generalList);
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


			List<ContractField> list = new ArrayList<ContractField>();
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
					list.add(contractField);
				}
				rs.close();
				st.close();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
			dataMap.put("result", list);
			dataMap.put("footer", new ArrayList());
			
			
			
		}
		return dataMap;


	}
	
	 public Viewlayer find(String key,Object value) {
	        try {
				return (Viewlayer) getSession().createCriteria(Viewlayer.class)
						.add( Restrictions.eq(key,value)).setMaxResults(1).uniqueResult();
			} catch (HibernateException e) {
				return null;
			}
	}
	 
	 
	public List<Field> getPermissionField(Definition definition, Admin admin){
		List<Field> resList = new ArrayList<Field>();
		Set<String> permissionShowFieldNameList = new LinkedHashSet<String>();
		for(String tableId : definition.getTableIdSet()){
			ContractCategory contractCategory = contractCategoryDao.get(tableId);
			if(contractCategory!=null){
				List<com.rzrk.vo.contract.Field> contractFieldList = contractCategoryDao.getPermissionField(contractCategory, admin);
				for(com.rzrk.vo.contract.Field cfield : contractFieldList){
					String permissionShowFieldName = cfield.getName()+"_"+contractCategory.getName();
					permissionShowFieldNameList.add(permissionShowFieldName);
				}
			}
		}
		for(Field field : definition){
			if(permissionShowFieldNameList.contains(field.getShowField()) || field.getType()!=Field.TYPE_TABLE){
				resList.add(field);
			}
		}
		return resList;
	}


}
