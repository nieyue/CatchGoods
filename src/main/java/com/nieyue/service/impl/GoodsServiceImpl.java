package com.nieyue.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nieyue.bean.Goods;
import com.nieyue.bean.GoodsCate;
import com.nieyue.dao.GoodsDao;
import com.nieyue.service.GoodsCateService;
import com.nieyue.service.GoodsService;
@Service
public class GoodsServiceImpl implements GoodsService{
	@Resource
	GoodsDao goodsDao;
	@Resource
	GoodsCateService goodsCateService;
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public boolean addGoods(Goods goods) {
		goods.setCreateDate(new Date());
		goods.setUpdateDate(new Date());
		goods.setPvs(0);
		goods.setUvs(0);
		goods.setIps(0);
		goods.setReadingNumber(0);
		if(goods.getRecommend()==null||goods.getRecommend().equals("")){
			goods.setRecommend(0);//默认不推
		}
		if(goods.getCameraNumber()==null||goods.getCameraNumber().equals("")){
			goods.setCameraNumber(1);//默认1
		}
		if(goods.getSingleCharge()==null||goods.getSingleCharge().equals("")){
			goods.setSingleCharge(0.0);//默认0
		}
		if(goods.getFreeNumber()==null||goods.getFreeNumber().equals("")){
			goods.setFreeNumber(0);//默认0
		}
		if(goods.getChargeNumber()==null||goods.getChargeNumber().equals("")){
			goods.setChargeNumber(0);//默认0
		}
		if(goods.getTotalCharge()==null||goods.getTotalCharge().equals("")){
			goods.setTotalCharge(0.0);//默认0
		}
		if(goods.getStatus()==null||goods.getStatus().equals("")){
			goods.setStatus(1);//默认空闲
		}
		boolean b = goodsDao.addGoods(goods);
		return b;
	}
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public boolean delGoods(Integer goodsId) {
		boolean b = goodsDao.delGoods(goodsId);
		return b;
	}
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public boolean updateGoods(Goods goods) {
		goods.setUpdateDate(new Date());
		boolean b = goodsDao.updateGoods(goods);
		return b;
	}

	@Override
	public Goods loadGoods(Integer goodsId) {
		Goods goods = goodsDao.loadGoods(goodsId);
		GoodsCate goodsCate = goodsCateService.loadGoodsCate(goods.getGoodsCateId(),null);
		goods.setGoodsCate(goodsCate);
		return goods;
	}

	@Override
	public int countAll(
			Integer recommend,
			Double singleCharge,
			Integer chargeNumber,
			Integer readingNumber,
			Integer goodsCateId,
			Date createDate,
			Date updateDate,
			Integer status) {
		int c = goodsDao.countAll(
				recommend,
				singleCharge,
				chargeNumber,
				readingNumber,
				goodsCateId,
				createDate,
				updateDate,
				status);
		return c;
	}

	@Override
	public List<Goods> browsePagingGoods(
			Integer recommend,
			Double singleCharge,
			Integer chargeNumber,
			Integer readingNumber,
			Integer goodsCateId,
			Date createDate,
			Date updateDate,
			Integer status,
			int pageNum, int pageSize,
			String orderName, String orderWay) {
		if(pageNum<1){
			pageNum=1;
		}
		if(pageSize<1){
			pageSize=0;//没有数据
		}
		List<Goods> l = goodsDao.browsePagingGoods(
				recommend,
				singleCharge,
				chargeNumber,
				readingNumber,
				goodsCateId,
				createDate,
				updateDate,
				status,
				pageNum-1, pageSize, orderName, orderWay);
		
		for (Goods goods : l) {
			GoodsCate goodsCate = goodsCateService.loadGoodsCate(goods.getGoodsCateId(),null);
			goods.setGoodsCate(goodsCate);
		}
		return l;
	}
	@Override
	public Goods loadSmallGoods(Integer goodsId) {
		Goods goods = goodsDao.loadSmallGoods(goodsId);
		return goods;
	}

	
}
