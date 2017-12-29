package com.nieyue.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.nieyue.bean.CatchRecord;

/**
 * 抓取记录数据库接口
 * @author yy
 *
 */
@Mapper
public interface CatchRecordDao {
	/** 新增抓取记录*/	
	public boolean addCatchRecord(CatchRecord catchRecord) ;	
	/** 删除抓取记录 */	
	public boolean delCatchRecord(Integer catchRecordId) ;
	/** 更新抓取记录*/	
	public boolean updateCatchRecord(CatchRecord catchRecord);
	/** 装载抓取记录 */	
	public CatchRecord loadCatchRecord(Integer catchRecordId);	
	/** 抓取记录总共数目 */	
	public int countAll();	
	/** 分页抓取记录信息 */
	public List<CatchRecord> browsePagingCatchRecord(@Param("pageNum")int pageNum,@Param("pageSize")int pageSize,@Param("orderName")String orderName,@Param("orderWay")String orderWay) ;		
}
