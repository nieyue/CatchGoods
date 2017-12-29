package com.nieyue.service;

import java.util.List;

import com.nieyue.bean.GoodsCate;

/**
 * 物品类型逻辑层接口
 * @author yy
 *
 */
public interface GoodsCateService {
	/** 新增物品类型 */	
	public boolean addGoodsCate(GoodsCate goodsCate) ;	
	/** 删除物品类型 */	
	public boolean delGoodsCate(Integer goodsCateId) ;
	/** 更新物品类型*/	
	public boolean updateGoodsCate(GoodsCate goodsCate);
	/** 装载物品类型 */	
	public GoodsCate loadGoodsCate(Integer goodsCateId,String name);	
	/** 物品类型总共数目 */	
	public int countAll();
	/** 分页物品类型信息 */
	public List<GoodsCate> browsePagingGoodsCate(int pageNum,int pageSize,String orderName,String orderWay) ;
}
