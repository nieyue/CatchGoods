package com.nieyue.service;

import java.util.List;

import com.nieyue.bean.RechargeTerm;

/**
 * 充值项逻辑层接口
 * @author yy
 *
 */
public interface RechargeTermService {
	/** 新增充值项 */	
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
	public List<RechargeTerm> browsePagingRechargeTerm(int pageNum,int pageSize,String orderName,String orderWay) ;
}
