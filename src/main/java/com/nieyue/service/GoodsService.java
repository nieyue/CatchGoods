package com.nieyue.service;

import java.util.Date;
import java.util.List;

import com.nieyue.bean.Goods;

/**
 * 物品逻辑层接口
 * @author yy
 *
 */
public interface GoodsService {
	/** 新增物品 */	
	public boolean addGoods(Goods goods) ;	
	/** 删除物品 */	
	public boolean delGoods(Integer goodsId) ;
	/** 更新物品*/	
	public boolean updateGoods(Goods goods);
	/** 装载物品 */	
	public Goods loadGoods(Integer goodsId);	
	/** 装载small物品 */	
	public Goods loadSmallGoods(Integer goodsId);	
	/** 物品总共数目 */	
	public int countAll(
			Integer recommend,
			Double singleCharge,
			Integer chargeNumber,
			Integer readingNumber,
			Integer goodsCateId,
			Date createDate,
			Date updateDate,
			Integer status);
	/** 分页物品信息 */
	public List<Goods> browsePagingGoods(
			Integer recommend,
			Double singleCharge,
			Integer chargeNumber,
			Integer readingNumber,
			Integer goodsCateId,
			Date createDate,
			Date updateDate,
			Integer status,
			int pageNum,
			int pageSize,
			String orderName,
			String orderWay) ;
}
