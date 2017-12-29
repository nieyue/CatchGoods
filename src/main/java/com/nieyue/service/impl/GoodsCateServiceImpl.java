package com.nieyue.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nieyue.bean.GoodsCate;
import com.nieyue.dao.GoodsCateDao;
import com.nieyue.service.GoodsCateService;
@Service
public class GoodsCateServiceImpl implements GoodsCateService{
	@Resource
	GoodsCateDao goodsCateDao;
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public boolean addGoodsCate(GoodsCate goodsCate) {
		goodsCate.setUpdateDate(new Date());
		boolean b = goodsCateDao.addGoodsCate(goodsCate);
		return b;
	}
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public boolean delGoodsCate(Integer goodsCateId) {
		boolean b = goodsCateDao.delGoodsCate(goodsCateId);
		return b;
	}
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public boolean updateGoodsCate(GoodsCate goodsCate) {
		goodsCate.setUpdateDate(new Date());
		boolean b = goodsCateDao.updateGoodsCate(goodsCate);
		return b;
	}

	@Override
	public GoodsCate loadGoodsCate(Integer goodsCateId,String name) {
		GoodsCate r = goodsCateDao.loadGoodsCate(goodsCateId,name);
		return r;
	}

	@Override
	public int countAll() {
		int c = goodsCateDao.countAll();
		return c;
	}

	@Override
	public List<GoodsCate> browsePagingGoodsCate(int pageNum, int pageSize,
			String orderName, String orderWay) {
		if(pageNum<1){
			pageNum=1;
		}
		if(pageSize<1){
			pageSize=0;//没有数据
		}
		List<GoodsCate> l = goodsCateDao.browsePagingGoodsCate(pageNum-1, pageSize, orderName, orderWay);
		return l;
	}

	
}
