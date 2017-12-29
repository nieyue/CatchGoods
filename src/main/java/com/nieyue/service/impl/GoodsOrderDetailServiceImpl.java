package com.nieyue.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nieyue.bean.GoodsOrderDetail;
import com.nieyue.dao.GoodsOrderDetailDao;
import com.nieyue.service.GoodsOrderDetailService;
@Service
public class GoodsOrderDetailServiceImpl implements GoodsOrderDetailService{
	@Resource
	GoodsOrderDetailDao goodsOrderDetailDao;
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public boolean addGoodsOrderDetail(GoodsOrderDetail goodsOrderDetail) {
		goodsOrderDetail.setCreateDate(new Date());
		goodsOrderDetail.setUpdateDate(new Date());
		goodsOrderDetail.setStatus(0);//已下单
		boolean b = goodsOrderDetailDao.addGoodsOrderDetail(goodsOrderDetail);
		
		return b;
	}
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public boolean delGoodsOrderDetail(Integer goodsOrderDetailId) {
		boolean b = goodsOrderDetailDao.delGoodsOrderDetail(goodsOrderDetailId);
		return b;
	}
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public boolean updateGoodsOrderDetail(GoodsOrderDetail goodsOrderDetail) {
		goodsOrderDetail.setUpdateDate(new Date());
		boolean b = goodsOrderDetailDao.updateGoodsOrderDetail(goodsOrderDetail);
		
		return b;
	}

	@Override
	public GoodsOrderDetail loadGoodsOrderDetail(Integer goodsOrderDetailId) {
		GoodsOrderDetail goodsOrderDetail = goodsOrderDetailDao.loadGoodsOrderDetail(goodsOrderDetailId);
		return goodsOrderDetail;
	}

	@Override
	public int countAll(
			Integer goodsOrderId,
			Date createDate,
			Date updateDate,
			Integer status) {
		int c = goodsOrderDetailDao.countAll(
				goodsOrderId,
				createDate,
				updateDate,
				status
				);
		return c;
	}

	@Override
	public List<GoodsOrderDetail> browsePagingGoodsOrderDetail(
			Integer goodsOrderId,
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
		List<GoodsOrderDetail> l = goodsOrderDetailDao.browsePagingGoodsOrderDetail(
				goodsOrderId,
				createDate,
				updateDate,
				status,
				pageNum-1, pageSize, orderName, orderWay);
		return l;
	}

	
}
