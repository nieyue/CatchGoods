package com.nieyue.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nieyue.bean.GoodsOrder;
import com.nieyue.bean.GoodsOrderDetail;
import com.nieyue.business.PaymentBusiness;
import com.nieyue.dao.GoodsOrderDao;
import com.nieyue.rabbitmq.confirmcallback.Sender;
import com.nieyue.service.GoodsOrderDetailService;
import com.nieyue.service.GoodsOrderService;

@Service
public class GoodsOrderServiceImpl implements GoodsOrderService{
	@Resource
	GoodsOrderDao goodsOrderDao;
	@Resource
	StringRedisTemplate stringRedisTemplate;
	@Resource
	GoodsOrderDetailService goodsOrderDetailService;
	@Resource
	Sender sender;
	@Resource
	PaymentBusiness paymentBusiness;
	
	/**回调
	 * 
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public boolean addGoodsOrder(GoodsOrder goodsOrder) {
		goodsOrder.setCreateDate(new Date());
		goodsOrder.setUpdateDate(new Date());
		boolean b = goodsOrderDao.addGoodsOrder(goodsOrder);
		return b;
	}
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public boolean updateGoodsOrder(GoodsOrder goodsOrder) {
		goodsOrder.setUpdateDate(new Date());
		boolean b = goodsOrderDao.updateGoodsOrder(goodsOrder);
		return b;
	}

	@Override
	public GoodsOrder loadGoodsOrder(Integer goodsOrderId) {
		GoodsOrder goodsOrder = goodsOrderDao.loadGoodsOrder(goodsOrderId);
		GoodsOrderDetail goodsOrderDetail = goodsOrderDetailService.loadGoodsOrderDetail(goodsOrder.getGoodsOrderId());
		List<GoodsOrderDetail> list=new ArrayList<GoodsOrderDetail>();
		list.add(goodsOrderDetail);
		goodsOrder.setGoodsOrderDetailList(list);
		return goodsOrder;
	}

	@Override
	public int countAll(
			Integer acountId,
			String orderNumber,
			Date createDate,
			Date updateDate) {
		int c = goodsOrderDao.countAll(
				acountId,
				orderNumber,
				createDate,
				updateDate);
		return c;
	}

	@Override
	public List<GoodsOrder> browsePagingGoodsOrder(
			Integer acountId,
			String orderNumber,
			Date createDate,
			Date updateDate,
			int pageNum, int pageSize,
			String orderName, String orderWay) {
		if(pageNum<1){
			pageNum=1;
		}
		if(pageSize<1){
			pageSize=0;//没有数据
		}
		List<GoodsOrder> l = goodsOrderDao.browsePagingGoodsOrder(
				acountId,
				orderNumber,
				createDate,
				updateDate,
				pageNum-1, pageSize, orderName, orderWay);
		
		for (int i = 0; i < l.size(); i++) {
			GoodsOrder goodsOrder = l.get(i);
			GoodsOrderDetail goodsOrderDetail = goodsOrderDetailService.loadGoodsOrderDetail(goodsOrder.getGoodsOrderId());
			List<GoodsOrderDetail> list=new ArrayList<GoodsOrderDetail>();
			list.add(goodsOrderDetail);
			goodsOrder.setGoodsOrderDetailList(list);
		}
		return l;
	}
	@Override
	public boolean delGoodsOrder(Integer goodsOrderId) {
		boolean b = goodsOrderDao.delGoodsOrder(goodsOrderId);
		return b;
	}

	
}
