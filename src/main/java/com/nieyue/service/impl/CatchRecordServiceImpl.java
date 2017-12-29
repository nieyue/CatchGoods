package com.nieyue.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nieyue.bean.CatchRecord;
import com.nieyue.dao.CatchRecordDao;
import com.nieyue.service.CatchRecordService;
@Service
public class CatchRecordServiceImpl implements CatchRecordService{
	@Resource
	CatchRecordDao catchRecordDao;
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public boolean addCatchRecord(CatchRecord catchRecord) {
		catchRecord.setCreateDate(new Date());
		catchRecord.setUpdateDate(new Date());
		if(catchRecord.getStatus()==null||"".equals(catchRecord.getStatus())){
			catchRecord.setStatus(1);//成功
			}
		boolean b = catchRecordDao.addCatchRecord(catchRecord);
		return b;
	}
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public boolean delCatchRecord(Integer catchRecordId) {
		boolean b = catchRecordDao.delCatchRecord(catchRecordId);
		return b;
	}
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public boolean updateCatchRecord(CatchRecord catchRecord) {
		catchRecord.setUpdateDate(new Date());
		boolean b = catchRecordDao.updateCatchRecord(catchRecord);
		return b;
	}

	@Override
	public CatchRecord loadCatchRecord(Integer catchRecordId) {
		CatchRecord r = catchRecordDao.loadCatchRecord(catchRecordId);
		return r;
	}

	@Override
	public int countAll() {
		int c = catchRecordDao.countAll();
		return c;
	}

	@Override
	public List<CatchRecord> browsePagingCatchRecord(int pageNum, int pageSize,
			String orderName, String orderWay) {
		if(pageNum<1){
			pageNum=1;
		}
		if(pageSize<1){
			pageSize=0;//没有数据
		}
		List<CatchRecord> l = catchRecordDao.browsePagingCatchRecord(pageNum-1, pageSize, orderName, orderWay);
		return l;
	}

	
}
