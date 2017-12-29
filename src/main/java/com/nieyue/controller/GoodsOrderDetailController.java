package com.nieyue.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nieyue.bean.GoodsOrderDetail;
import com.nieyue.service.GoodsOrderDetailService;
import com.nieyue.util.ResultUtil;
import com.nieyue.util.StateResult;
import com.nieyue.util.StateResultList;


/**
 * 物品订单详情控制类
 * @author yy
 *
 */
@RestController
@RequestMapping("/goodsOrderDetail")
public class GoodsOrderDetailController {
	@Resource
	private GoodsOrderDetailService goodsOrderDetailService;
	
	/**
	 * 物品订单详情分页浏览
	 * @param orderName 物品订单详情排序数据库字段
	 * @param orderWay 物品订单详情排序方法 asc升序 desc降序
	 * @return
	 */
	@RequestMapping(value = "/list", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList browsePagingGoodsOrderDetail(
			@RequestParam(value="goodsOrderId",required=false)Integer goodsOrderId,
			@RequestParam(value="createDate",required=false)Date createDate,
			@RequestParam(value="updateDate",required=false)Date updateDate,
			@RequestParam(value="status",required=false)Integer status,
			@RequestParam(value="pageNum",defaultValue="1",required=false)int pageNum,
			@RequestParam(value="pageSize",defaultValue="10",required=false) int pageSize,
			@RequestParam(value="orderName",required=false,defaultValue="goods_order_detail_id") String orderName,
			@RequestParam(value="orderWay",required=false,defaultValue="desc") String orderWay)  {
			List<GoodsOrderDetail> list = new ArrayList<GoodsOrderDetail>();
			list= goodsOrderDetailService.browsePagingGoodsOrderDetail(goodsOrderId,createDate,updateDate,status,pageNum, pageSize, orderName, orderWay);
			if(list.size()>0){
				return ResultUtil.getSlefSRSuccessList(list);
			}else{
				return ResultUtil.getSlefSRFailList(list);
			}
	}
	/**
	 * 物品订单详情修改
	 * @return
	 */
	@RequestMapping(value = "/update", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResult updateGoodsOrderDetail(@ModelAttribute GoodsOrderDetail goodsOrderDetail,HttpSession session)  {
		boolean um = goodsOrderDetailService.updateGoodsOrderDetail(goodsOrderDetail);
		return ResultUtil.getSR(um);
	}
	/**
	 * 物品订单详情增加
	 * @return 
	 */
	@RequestMapping(value = "/add", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResult addGoodsOrderDetail(@ModelAttribute GoodsOrderDetail goodsOrderDetail, HttpSession session) {
		boolean am = goodsOrderDetailService.addGoodsOrderDetail(goodsOrderDetail);
		return ResultUtil.getSR(am);
	}
	/**
	 * 物品订单详情删除
	 * @return
	 */
	@RequestMapping(value = "/delete", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResult delGoodsOrderDetail(@RequestParam("goodsOrderDetailId") Integer goodsOrderDetailId,HttpSession session)  {
		boolean dm = goodsOrderDetailService.delGoodsOrderDetail(goodsOrderDetailId);
		return ResultUtil.getSR(dm);
	}
	/**
	 * 物品订单详情浏览数量
	 * @return
	 */
	@RequestMapping(value = "/count", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody int countAll(
			@RequestParam(value="goodsOrderId",required=false)Integer goodsOrderId,
			@RequestParam(value="createDate",required=false)Date createDate,
			@RequestParam(value="updateDate",required=false)Date updateDate,
			@RequestParam(value="status",required=false)Integer status,
			HttpSession session)  {
		int count = goodsOrderDetailService.countAll(goodsOrderId,createDate,updateDate,status);
		return count;
	}
	/**
	 * 物品订单详情单个加载
	 * @return
	 */
	@RequestMapping(value = "/{goodsOrderDetailId}", method = {RequestMethod.GET,RequestMethod.POST})
	public  StateResultList loadGoodsOrderDetail(@PathVariable("goodsOrderDetailId") Integer goodsOrderDetailId,HttpSession session)  {
		List<GoodsOrderDetail> list = new ArrayList<GoodsOrderDetail>();
		GoodsOrderDetail goodsOrderDetail = goodsOrderDetailService.loadGoodsOrderDetail(goodsOrderDetailId);
			if(goodsOrderDetail!=null &&!goodsOrderDetail.equals("")){
				list.add(goodsOrderDetail);
				return ResultUtil.getSlefSRSuccessList(list);
			}else{
				return ResultUtil.getSlefSRFailList(list);
			}
	}
	
}
