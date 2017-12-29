package com.nieyue.service;

import java.util.Date;
import java.util.List;

import com.nieyue.bean.GoodsOrder;

/**
 * 物品订单逻辑层接口
 * @author yy
 *
 */
public interface GoodsOrderService {
	/** 新增物品订单 */	
	public boolean addGoodsOrder(GoodsOrder goodsOrder) ;	
	/** 删除物品订单 */	
	public boolean delGoodsOrder(Integer goodsOrderId) ;
	/** 更新物品订单*/	
	public boolean updateGoodsOrder(GoodsOrder goodsOrder);
	/** 装载物品订单 */	
	public GoodsOrder loadGoodsOrder(Integer goodsOrderId);	
	/** 物品订单总共数目 */	
	public int countAll(
			Integer acountId,
			String orderNumber,
			Date createDate,
			Date updateDate);
	/** 分页物品订单信息 */
	public List<GoodsOrder> browsePagingGoodsOrder(
			Integer acountId,
			String orderNumber,
			Date createDate,
			Date updateDate,
			int pageNum,
			int pageSize,
			String orderName,
			String orderWay) ;
}
