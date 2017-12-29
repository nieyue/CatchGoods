package com.nieyue.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.nieyue.bean.RechargeTerm;

/**
 * 充值项数据库接口
 * @author yy
 *
 */
@Mapper
public interface RechargeTermDao {
	/** 新增充值项*/	
	public boolean addRechargeTerm(RechargeTerm rechargeTerm) ;	
	/** 删除充值项 */	
	public boolean delRechargeTerm(Integer rechargeTermId) ;
	/** 更新充值项*/	
	public boolean updateRechargeTerm(RechargeTerm rechargeTerm);
	/** 装载充值项 */	
	public RechargeTerm loadRechargeTerm(Integer rechargeTermId);	
	/** 充值项总共数目 */	
	public int countAll();	
	/** 分页充值项信息 */
	public List<RechargeTerm> browsePagingRechargeTerm(@Param("pageNum")int pageNum,@Param("pageSize")int pageSize,@Param("orderName")String orderName,@Param("orderWay")String orderWay) ;		
}
