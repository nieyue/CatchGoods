package com.nieyue.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.nieyue.bean.GoodsOrderDetail;

/**
 * 物品订单详情数据库接口
 * @author yy
 *
 */
@Mapper
public interface GoodsOrderDetailDao {
	/** 新增物品订单详情*/	
	public boolean addGoodsOrderDetail(GoodsOrderDetail goodsOrderDetail) ;	
	/** 删除物品订单详情 */	
	public boolean delGoodsOrderDetail(Integer goodsOrderDetailId) ;
	/** 更新物品订单详情*/	
	public boolean updateGoodsOrderDetail(GoodsOrderDetail goodsOrderDetail);
	/** 装载物品订单详情 */	
	public GoodsOrderDetail loadGoodsOrderDetail(Integer goodsOrderDetailId);	
	/** 物品订单详情总共数目 */	
	public int countAll(
			@Param("goodsOrderId")Integer goodsOrderId,
			@Param("createDate")Date createDate,
			@Param("updateDate")Date updateDate,
			@Param("status")Integer status
			);	
	/** 分页物品订单详情信息 */
	public List<GoodsOrderDetail> browsePagingGoodsOrderDetail(
			@Param("goodsOrderId")Integer goodsOrderId,
			@Param("createDate")Date createDate,
			@Param("updateDate")Date updateDate,
			@Param("status")Integer status,
			@Param("pageNum")int pageNum,
			@Param("pageSize")int pageSize,
			@Param("orderName")String orderName,
			@Param("orderWay")String orderWay) ;		
}
