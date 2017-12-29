package com.nieyue.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nieyue.bean.GoodsOrder;
import com.nieyue.bean.GoodsOrderDetail;
import com.nieyue.rabbitmq.confirmcallback.Sender;
import com.nieyue.service.GoodsOrderService;
import com.nieyue.util.ResultUtil;
import com.nieyue.util.StateResult;
import com.nieyue.util.StateResultList;

import net.sf.json.JSONObject;


/**
 * 物品订单控制类
 * @author yy
 *
 */
@RestController
@RequestMapping("/goodsOrder")
public class GoodsOrderController {
	@Resource
	private GoodsOrderService goodsOrderService;
	@Resource
	private Sender sender;
	@Value("${myPugin.paymentSystemDomainUrl}")
	String paymentSystemDomainUrl;
	
	/**
	 * 物品订单分页浏览
	 * @param orderName 物品订单排序数据库字段
	 * @param orderWay 物品订单排序方法 asc升序 desc降序
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/list", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList browsePagingGoodsOrder(
			@RequestParam(value="acountId",required=false)Integer acountId,
			@RequestParam(value="orderNumber",required=false)String orderNumber,
			@RequestParam(value="createDate",required=false)Date createDate,
			@RequestParam(value="updateDate",required=false)Date updateDate,
			@RequestParam(value="pageNum",defaultValue="1",required=false)int pageNum,
			@RequestParam(value="pageSize",defaultValue="10",required=false) int pageSize,
			@RequestParam(value="orderName",required=false,defaultValue="goods_order_id") String orderName,
			@RequestParam(value="orderWay",required=false,defaultValue="desc") String orderWay) throws Exception  {
			List<GoodsOrder> list = new ArrayList<GoodsOrder>();
			list= goodsOrderService.browsePagingGoodsOrder(acountId,orderNumber,createDate,updateDate,pageNum, pageSize, orderName, orderWay);
			if(list.size()>0){
				return ResultUtil.getSlefSRSuccessList(list);
			}else{
				return ResultUtil.getSlefSRFailList(list);
			}
	}
	/**
	 * 物品订单修改
	 * @return
	 */
	@RequestMapping(value = "/update", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResult updateGoodsOrder(@RequestBody GoodsOrder goodsOrder,HttpSession session)  {
		boolean um = goodsOrderService.updateGoodsOrder(goodsOrder);
		return ResultUtil.getSR(um);
	}
	/**
	 * 物品订单增加
	 * @return 
	 */
	@RequestMapping(value = "/add", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResult addGoodsOrder(@RequestBody GoodsOrder goodsOrder, HttpSession session) {
		//boolean am = GoodsOrderService.addGoodsOrderSynchronization(GoodsOrder);
		sender.sendGoodsOrder(goodsOrder);
		return ResultUtil.getSR(true);
	}
	/**
	 * 物品订单支付调用
	 * @return 
	 */
	@RequestMapping(value = "/payment", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResult paymentGoodsOrder(@RequestBody GoodsOrder goodsOrder, HttpSession session) {
		boolean b = goodsOrderService.addGoodsOrder(goodsOrder);
		//sender.sendGoodsOrder(GoodsOrder);
		return ResultUtil.getSR(b);
	}
	/**
	 * 物品订单支付回调
	 * @return 
	 */
	@RequestMapping(value = "/paymentNotifyUrl", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResult notifyUrlGoodsOrder(@RequestParam("params")String params, HttpSession session) {
		JSONObject json = JSONObject.fromObject(params);
		GoodsOrder goodsOrder = (GoodsOrder) JSONObject.toBean(json, GoodsOrder.class);
		int bodls = goodsOrder.getGoodsOrderDetailList().size();
		List<GoodsOrderDetail> bodl=new ArrayList<GoodsOrderDetail>();
		for (int i = 0; i < bodls; i++) {
			 Object bodobj = goodsOrder.getGoodsOrderDetailList().get(i);
			 JSONObject bodjson = JSONObject.fromObject(bodobj);
			 GoodsOrderDetail goodsOrderDetail23 = (GoodsOrderDetail) JSONObject.toBean(bodjson, GoodsOrderDetail.class);
			 bodl.add(goodsOrderDetail23);
		}
		goodsOrder.getGoodsOrderDetailList().clear();
		goodsOrder.getGoodsOrderDetailList().addAll(bodl);
		boolean b = goodsOrderService.addGoodsOrder(goodsOrder);
		//sender.sendGoodsOrder(GoodsOrder);
		return ResultUtil.getSR(b);
	}
	/**
	 * 物品订单删除
	 * @return
	 */
	@RequestMapping(value = "/delete", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResult delGoodsOrder(@RequestParam("goodsOrderId") Integer goodsOrderId,HttpSession session)  {
		boolean dm = goodsOrderService.delGoodsOrder(goodsOrderId);
		return ResultUtil.getSR(dm);
	}
	/**
	 * 物品订单浏览数量
	 * @return
	 */
	@RequestMapping(value = "/count", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody int countAll(
			@RequestParam(value="acountId",required=false)Integer acountId,
			@RequestParam(value="orderNumber",required=false)String orderNumber,
			@RequestParam(value="createDate",required=false)Date createDate,
			@RequestParam(value="updateDate",required=false)Date updateDate,
			HttpSession session)  {
		int count = goodsOrderService.countAll(acountId,orderNumber,createDate,updateDate);
		return count;
	}
	/**
	 * 物品订单单个加载
	 * @return
	 */
	@RequestMapping(value = "/{goodsOrderId}", method = {RequestMethod.GET,RequestMethod.POST})
	public  StateResultList loadGoodsOrder(@PathVariable("goodsOrderId") Integer goodsOrderId,HttpSession session)  {
		List<GoodsOrder> list = new ArrayList<GoodsOrder>();
		GoodsOrder goodsOrder = goodsOrderService.loadGoodsOrder(goodsOrderId);
			if(goodsOrder!=null &&!goodsOrder.equals("")){
				list.add(goodsOrder);
				return ResultUtil.getSlefSRSuccessList(list);
			}else{
				return ResultUtil.getSlefSRFailList(list);
			}
	}
	
}
