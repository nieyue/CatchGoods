package com.nieyue.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.nieyue.bean.Goods;

/**
 * 物品数据库接口
 * @author yy
 *
 */
@Mapper
public interface GoodsDao {
	/** 新增物品*/	
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
			@Param("recommend")Integer recommend,
			@Param("singleCharge")Double singleCharge,
			@Param("chargeNumber")Integer chargeNumber,
			@Param("readingNumber")Integer readingNumber,
			@Param("goodsCateId")Integer goodsCateId,
			@Param("createDate")Date createDate,
			@Param("updateDate")Date updateDate,
			@Param("status")Integer status
			);	
	/** 分页物品信息 */
	public List<Goods> browsePagingGoods(
			@Param("recommend")Integer recommend,
			@Param("singleCharge")Double singleCharge,
			@Param("chargeNumber")Integer chargeNumber,
			@Param("readingNumber")Integer readingNumber,
			@Param("goodsCateId")Integer goodsCateId,
			@Param("createDate")Date createDate,
			@Param("updateDate")Date updateDate,
			@Param("status")Integer status,
			@Param("pageNum")int pageNum,
			@Param("pageSize")int pageSize,
			@Param("orderName")String orderName,
			@Param("orderWay")String orderWay) ;		
}
