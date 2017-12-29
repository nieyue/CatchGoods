package com.nieyue.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nieyue.bean.RechargeRecord;
import com.nieyue.dao.RechargeRecordDao;
import com.nieyue.service.RechargeRecordService;
@Service
public class RechargeRecordServiceImpl implements RechargeRecordService{
	@Resource
	RechargeRecordDao rechargeRecordDao;
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public boolean addRechargeRecord(RechargeRecord rechargeRecord) {
		rechargeRecord.setCreateDate(new Date());
		rechargeRecord.setUpdateDate(new Date());
		if(rechargeRecord.getStatus()==null||"".equals(rechargeRecord.getStatus())){
			rechargeRecord.setStatus(1);//成功
			}
		boolean b = rechargeRecordDao.addRechargeRecord(rechargeRecord);
		return b;
	}
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public boolean delRechargeRecord(Integer rechargeRecordId) {
		boolean b = rechargeRecordDao.delRechargeRecord(rechargeRecordId);
		return b;
	}
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public boolean updateRechargeRecord(RechargeRecord rechargeRecord) {
		rechargeRecord.setUpdateDate(new Date());
		boolean b = rechargeRecordDao.updateRechargeRecord(rechargeRecord);
		return b;
	}

	@Override
	public RechargeRecord loadRechargeRecord(Integer rechargeRecordId) {
		RechargeRecord r = rechargeRecordDao.loadRechargeRecord(rechargeRecordId);
		return r;
	}

	@Override
	public int countAll() {
		int c = rechargeRecordDao.countAll();
		return c;
	}

	@Override
	public List<RechargeRecord> browsePagingRechargeRecord(int pageNum, int pageSize,
			String orderName, String orderWay) {
		if(pageNum<1){
			pageNum=1;
		}
		if(pageSize<1){
			pageSize=0;//没有数据
		}
		List<RechargeRecord> l = rechargeRecordDao.browsePagingRechargeRecord(pageNum-1, pageSize, orderName, orderWay);
		return l;
	}

	
}
