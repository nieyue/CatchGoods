package com.nieyue.service;

import java.util.Date;
import java.util.List;

import com.nieyue.bean.GoodsOrderDetail;

/**
 * 物品订单详情逻辑层接口
 * @author yy
 *
 */
public interface GoodsOrderDetailService {
	/** 新增物品订单详情 */	
	public boolean addGoodsOrderDetail(GoodsOrderDetail goodsOrderDetail) ;	
	/** 删除物品订单详情 */	
	public boolean delGoodsOrderDetail(Integer goodsOrderDetailId) ;
	/** 更新物品订单详情*/	
	public boolean updateGoodsOrderDetail(GoodsOrderDetail goodsOrderDetail);
	/** 装载物品订单详情 */	
	public GoodsOrderDetail loadGoodsOrderDetail(Integer goodsOrderDetailId);	
	/** 物品订单详情总共数目 */	
	public int countAll(
			Integer goodsOrderId,
			Date createDate,
			Date updateDate,
			Integer status
			);
	/** 分页物品订单详情信息 */
	public List<GoodsOrderDetail> browsePagingGoodsOrderDetail(
			Integer goodsOrderId,
			Date createDate,
			Date updateDate,
			Integer status,
			int pageNum,
			int pageSize,
			String orderName,
			String orderWay) ;
}
