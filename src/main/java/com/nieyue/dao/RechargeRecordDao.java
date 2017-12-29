package com.nieyue.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.nieyue.bean.RechargeRecord;

/**
 * 充值记录数据库接口
 * @author yy
 *
 */
@Mapper
public interface RechargeRecordDao {
	/** 新增充值记录*/	
	public boolean addRechargeRecord(RechargeRecord rechargeRecord) ;	
	/** 删除充值记录 */	
	public boolean delRechargeRecord(Integer rechargeRecordId) ;
	/** 更新充值记录*/	
	public boolean updateRechargeRecord(RechargeRecord rechargeRecord);
	/** 装载充值记录 */	
	public RechargeRecord loadRechargeRecord(Integer rechargeRecordId);	
	/** 充值记录总共数目 */	
	public int countAll();	
	/** 分页充值记录信息 */
	public List<RechargeRecord> browsePagingRechargeRecord(@Param("pageNum")int pageNum,@Param("pageSize")int pageSize,@Param("orderName")String orderName,@Param("orderWay")String orderWay) ;		
}
