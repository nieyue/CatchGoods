package com.nieyue.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.nieyue.bean.GoodsOrder;

/**
 * 物品订单数据库接口
 * @author yy
 *
 */
@Mapper
public interface GoodsOrderDao {
	/** 新增物品订单*/	
	public boolean addGoodsOrder(GoodsOrder goodsOrder) ;	
	/** 删除物品订单 */	
	public boolean delGoodsOrder(Integer goodsOrderId) ;
	/** 更新物品订单*/	
	public boolean updateGoodsOrder(GoodsOrder goodsOrder);
	/** 装载物品订单 */	
	public GoodsOrder loadGoodsOrder(Integer goodsOrderId);	
	/** 物品订单总共数目 */	
	public int countAll(
			@Param("acountId")Integer acountId,
			@Param("orderNumber")String orderNumber,
			@Param("createDate")Date createDate,
			@Param("updateDate")Date updateDate
			);	
	/** 分页物品订单信息 */
	public List<GoodsOrder> browsePagingGoodsOrder(
			@Param("acountId")Integer acountId,
			@Param("orderNumber")String orderNumber,
			@Param("createDate")Date createDate,
			@Param("updateDate")Date updateDate,
			@Param("pageNum")int pageNum,
			@Param("pageSize")int pageSize,
			@Param("orderName")String orderName,
			@Param("orderWay")String orderWay) ;		
}
