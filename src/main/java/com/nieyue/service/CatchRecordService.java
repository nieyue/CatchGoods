package com.nieyue.service;

import java.util.List;

import com.nieyue.bean.CatchRecord;

/**
 * 抓取记录逻辑层接口
 * @author yy
 *
 */
public interface CatchRecordService {
	/** 新增抓取记录 */	
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
	public List<CatchRecord> browsePagingCatchRecord(int pageNum,int pageSize,String orderName,String orderWay) ;
}
