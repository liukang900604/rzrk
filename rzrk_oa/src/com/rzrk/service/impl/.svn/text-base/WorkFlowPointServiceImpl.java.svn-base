package com.rzrk.service.impl;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rzrk.contants.WorkFlowContants;
import com.rzrk.dao.WorkFlowPointDao;
import com.rzrk.entity.Admin;
import com.rzrk.entity.WorkFlowPoint;
import com.rzrk.service.AdminService;
import com.rzrk.service.WorkFlowPointService;

@Transactional
@Service
public class WorkFlowPointServiceImpl extends BaseServiceImpl<WorkFlowPoint, String> implements WorkFlowPointService {

	@Resource
	private WorkFlowPointDao typeDao;

	@Resource(name = "workFlowPointDaoImpl")
	public void setBaseDao(WorkFlowPointDao typeDao) {
		super.setBaseDao(typeDao);
	}
	
	/**
	 * 用户service
	 */
	@Resource
	private AdminService adminService;


	/**
	 * 检查工作流表单名称是否唯一
	 * @return
	 */
	@Override
	public List<WorkFlowPoint> checkWorkFlowPointName(String pointName,String workFlowId) {
		String hql = "from WorkFlowPoint where pointName =:pointName and workFlow.id =:workFlowId";
		//获取工作流类型集合
		return this.getBaseDao().getSession().createQuery(hql).setParameter("pointName", pointName).setParameter("workFlowId", workFlowId).list();
	}
	
	/**
	 * 检查工作流表单序号是否唯一
	 * @return
	 */
	@Override
	public List<WorkFlowPoint> checkWorkFlowPointSort(String pointSort,String workFlowId) {
		String hql = "from WorkFlowPoint where pointSort =:pointSort and workFlow.id =:workFlowId";
		//获取工作流类型集合
		return this.getBaseDao().getSession().createQuery(hql).setParameter("pointSort", pointSort).setParameter("workFlowId", workFlowId).list();
	}
	
	/**
	 * 检查工作流位置是否唯一(开始、结束)
	 * @return
	 */
	public List<WorkFlowPoint> checkWorkFlowLocation(String pointLocation ,String workFlowId) {
		String hql = "from WorkFlowPoint where workFlow.id =:workFlowId and pointLocation =:pointLocation";
		//获取工作流类型集合
		return this.getBaseDao().getSession().createQuery(hql).setParameter("workFlowId", workFlowId).setParameter("pointLocation", pointLocation).list();
	}
	
	/**
	 * 获取工作流流程节点根据序号、位置
	 * @return
	 */
	public List<WorkFlowPoint> checkWorkFlowLocation(String pointLocation,String pointSort ,String workFlowId) {
		String hql = "from WorkFlowPoint where workFlow.id =:workFlowId and pointLocation =:pointLocation and pointSort =:pointSort and isAdd = 2";
		//获取工作流类型集合
		return this.getBaseDao().getSession().createQuery(hql).setParameter("workFlowId", workFlowId).setParameter("pointLocation", pointLocation).setParameter("pointSort", pointSort).list();
	}
	
	
	/**
	 * 获取工作指定的工作流
	 * @return
	 */
	public WorkFlowPoint getWorkFlowPoint(String pointLocation,String pointSort ,String workId) {
		String hql = null;
		if(StringUtils.isNotEmpty(pointSort)){//序号不为空
			hql = "from WorkFlowPoint where work.id =:workId and pointLocation =:pointLocation and pointSort =:pointSort ";
		}else{ //默认第一个节点
			hql = "from WorkFlowPoint where work.id =:workId and pointLocation =:pointLocation and pointSort = 2";
		}
		Query query = this.getBaseDao().getSession().createQuery(hql);
		query.setParameter("workId", workId).setParameter("pointLocation", pointLocation);
		if(StringUtils.isNotEmpty(pointSort)){//序号不为空
			query.setParameter("pointSort", pointSort);
		}
		//获取工作流类型集合
		return (WorkFlowPoint)query.uniqueResult();
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
		Query query = this.getBaseDao().getSession().createQuery(hql);
		query.setParameter("workFlowId", workFlowId).setParameter("pointLocation", pointLocation);
		if(StringUtils.isNotEmpty(pointSort)){//序号不为空
			query.setParameter("pointSort", pointSort);
		}
		//获取工作流类型集合
		return (WorkFlowPoint)query.uniqueResult();
	}

	/**
	 *  获取开始位置节点制定的节点
	 * @param pointLocation
	 * @param workFlowId
	 * @return
	 */
 
	public List<WorkFlowPoint> getFlowPointList(String workFlowId){
		//首先判断开始节点存不存在
		List<WorkFlowPoint> pointList = null;
		pointList = checkWorkFlowLocation(WorkFlowContants.BEGIN,workFlowId);
		
		if(pointList != null && pointList.size() > 0){
			String point = pointList.get(0).getNextPonit();//获取开始节点的下一节点
			String [] nextPoint = point.trim().split(",");
			List<WorkFlowPoint> tempList = new ArrayList<WorkFlowPoint>(); //下一节点集合
			for(String str : nextPoint){
				//封装节点集合
				List<WorkFlowPoint> flowpointList = checkWorkFlowPointSort(str,workFlowId);
				if(flowpointList != null && flowpointList.size() > 0){
					tempList.add(flowpointList.get(0));
				}
				
			}
			return tempList;
			
		}else{
			pointList = checkWorkFlowLocation(WorkFlowContants.BETWEEN,WorkFlowContants.END,workFlowId);
			return pointList;
		}
		
		
	}
	
	/**
	 * 查出子节点
	 * @param node
	 * @param workFlowId
	 * @return
	 */
	public List<WorkFlowPoint> getPointListByNextPoint(String str,String workFlowId){
		//首先判断开始节点存不存在
		List<WorkFlowPoint> pointList = new ArrayList<WorkFlowPoint>();
		if(StringUtils.isNotEmpty(str)){
			String [] nextPoint = str.trim().split(",");
			List<WorkFlowPoint> tempList = new ArrayList<WorkFlowPoint>(); //下一节点集合
			for(String strs : nextPoint){
				//封装节点集合
				List<WorkFlowPoint> flowpointList = checkWorkFlowPointSort(strs,workFlowId);
				if(flowpointList != null && flowpointList.size() > 0){
					tempList.add(flowpointList.get(0));
				}
				
			}
			return tempList;
			
		}else{
			return pointList;
		}
		
	}
	
  


   /**
    * 保存多个实体
    */
	@Override
	public void saveManyEntity(List<WorkFlowPoint> pointList) {
		//保存100内 实体可用
		 for(WorkFlowPoint point :pointList){
			 this.getBaseDao().getSession().saveOrUpdate(point);
		 }
	}

	/*@Override
	public void deleteSonObject(String id) {
		String sql = "delete from rzrk_admin_rzrk_workflow_point where point_set_id = ?";
		this.getBaseDao().getSession().createSQLQuery(sql).setParameter(1, id).executeUpdate();
		
	}*/
 
	/**
	 * 更换数据类型
	 */
	public void changeData(){
		String hql = "from WorkFlowPoint where isBranch = 1";
		List<WorkFlowPoint> pointList = this.getBaseDao().getSession().createQuery(hql).list();
		if(pointList != null){
			for(WorkFlowPoint point : pointList){
				String showCondition =  point.getShowCondition();
				JSONArray jsonArray =  JSONArray.fromObject(showCondition);
				StringBuffer nameStr = new StringBuffer();
				for(int j = 0; j < jsonArray.size(); j++){//遍历分支
					JSONArray tempJson =jsonArray.getJSONArray(j);//获取条件
					
					if(tempJson.size() == 7){
						if(StringUtils.isEmpty(tempJson.getString(5)) && StringUtils.isEmpty(tempJson.getString(6))){//用户和处理人都不在
							nameStr.append("空节点").append(",");
							continue;
						}else{
							if(StringUtils.isNotEmpty(tempJson.getString(5))){//处理人
								JSONArray userArray = tempJson.getJSONArray(5);//获取用户信息
								if(userArray != null && userArray.size() > 0){
									Admin admin = adminService.get(userArray.getString(0));
									if(admin != null){
										if(userArray.size() == 1){//只有一个用户
											nameStr.append(admin.getName()).append(",");
										}else{
											nameStr.append(admin.getName()).append("...").append(",");
										}
									}
									
									
								}
								
								continue;
							}
							
							 if(StringUtils.isNotEmpty(tempJson.getString(6))){//关键字
								 JSONArray keyArray = tempJson.getJSONArray(6);//获取关键字信息
								 nameStr.append(keyArray.getString(0)).append(",");
								 continue;
							 }
						}
					}else{
						if(!(StringUtils.isNotEmpty(tempJson.getString(5)))){//用户都不在
							nameStr.append("空节点").append(",");
							continue;
						}else{
							if(StringUtils.isNotEmpty(tempJson.getString(5))){//处理人
								JSONArray userArray = tempJson.getJSONArray(5);//获取用户信息
								if(userArray != null && userArray.size() > 0){
									Admin admin = adminService.get(userArray.getString(0));
									if(admin != null){
										if(userArray.size() == 1){//只有一个用户
											nameStr.append(admin.getName()).append(",");
										}else{
											nameStr.append(admin.getName()).append("...").append(",");
										}
									}
									
									
								}
								
								continue;
							}
						}
					}
					
					
					
				}
				if(StringUtils.isNotEmpty(nameStr)){
					point.setName(nameStr.substring(0,nameStr.length()-1));
					this.update(point);
				}
					
			}
			
		
		}
	}
	
	/**
	 * 获取工作流节点
	 */
	public WorkFlowPoint get(String id,Long version){
		//先根据id获取,id未找到从历史流程中寻找
		 return	typeDao.get( id, version);
	}
	
	/**
	 * 更换个人节点数据
	 */
	public void  changePointData(){
		String hql = "from WorkFlowPoint where isBranch = 2";
		List<WorkFlowPoint> pointList = this.getBaseDao().getSession().createQuery(hql).list();
		if(pointList != null){
			for(WorkFlowPoint point : pointList){
				if(StringUtils.isEmpty(point.getUserName())){
					point.setUserName(point.getName());
					this.update(point);
				}
			}
		}
	}

}
