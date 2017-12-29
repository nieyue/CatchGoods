package com.nieyue.service;

import java.util.List;

import com.nieyue.bean.RechargeRecord;

/**
 * 充值记录逻辑层接口
 * @author yy
 *
 */
public interface RechargeRecordService {
	/** 新增充值记录 */	
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
	public List<RechargeRecord> browsePagingRechargeRecord(int pageNum,int pageSize,String orderName,String orderWay) ;
}
