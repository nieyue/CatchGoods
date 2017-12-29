package com.nieyue.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.nieyue.bean.GoodsCate;

/**
 * 物品类型数据库接口
 * @author yy
 *
 */
@Mapper
public interface GoodsCateDao {
	/** 新增物品类型*/	
	public boolean addGoodsCate(GoodsCate goodsCate) ;	
	/** 删除物品类型 */	
	public boolean delGoodsCate(Integer goodsCateId) ;
	/** 更新物品类型*/	
	public boolean updateGoodsCate(GoodsCate goodsCate);
	/** 装载物品类型 */	
	public GoodsCate loadGoodsCate(@Param("goodsCateId")Integer goodsCateId,@Param("name")String name);	
	/** 物品类型总共数目 */	
	public int countAll();	
	/** 分页物品类型信息 */
	public List<GoodsCate> browsePagingGoodsCate(@Param("pageNum")int pageNum,@Param("pageSize")int pageSize,@Param("orderName")String orderName,@Param("orderWay")String orderWay) ;		
}
