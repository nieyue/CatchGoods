package com.nieyue.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nieyue.bean.RechargeTerm;
import com.nieyue.dao.RechargeTermDao;
import com.nieyue.service.RechargeTermService;
@Service
public class RechargeTermServiceImpl implements RechargeTermService{
	@Resource
	RechargeTermDao rechargeTermDao;
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public boolean addRechargeTerm(RechargeTerm rechargeTerm) {
		rechargeTerm.setCreateDate(new Date());
		rechargeTerm.setUpdateDate(new Date());
		boolean b = rechargeTermDao.addRechargeTerm(rechargeTerm);
		return b;
	}
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public boolean delRechargeTerm(Integer rechargeTermId) {
		boolean b = rechargeTermDao.delRechargeTerm(rechargeTermId);
		return b;
	}
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public boolean updateRechargeTerm(RechargeTerm rechargeTerm) {
		rechargeTerm.setUpdateDate(new Date());
		boolean b = rechargeTermDao.updateRechargeTerm(rechargeTerm);
		return b;
	}

	@Override
	public RechargeTerm loadRechargeTerm(Integer rechargeTermId) {
		RechargeTerm r = rechargeTermDao.loadRechargeTerm(rechargeTermId);
		return r;
	}

	@Override
	public int countAll() {
		int c = rechargeTermDao.countAll();
		return c;
	}

	@Override
	public List<RechargeTerm> browsePagingRechargeTerm(int pageNum, int pageSize,
			String orderName, String orderWay) {
		if(pageNum<1){
			pageNum=1;
		}
		if(pageSize<1){
			pageSize=0;//没有数据
		}
		List<RechargeTerm> l = rechargeTermDao.browsePagingRechargeTerm(pageNum-1, pageSize, orderName, orderWay);
		return l;
	}

	
}
