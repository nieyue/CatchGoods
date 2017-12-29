package com.nieyue.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nieyue.bean.Goods;
import com.nieyue.bean.DataRabbitmqDTO;
import com.nieyue.comments.IPCountUtil;
import com.nieyue.rabbitmq.confirmcallback.Sender;
import com.nieyue.service.GoodsService;
import com.nieyue.util.ResultUtil;
import com.nieyue.util.StateResult;
import com.nieyue.util.StateResultList;


/**
 * 物品控制类
 * @author yy
 *
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {
	@Resource
	private GoodsService goodsService;
	@Resource
	private Sender sender;
	
	/**
	 * web阅读文章获取 根据GoodsId、acountId、uv来统计数据
	 * @return
	 */
	@RequestMapping(value = "/webRead", method = {RequestMethod.GET,RequestMethod.POST})
	public StateResult webReadGoods(
			@RequestParam(value="goodsId") Integer goodsId,
			@RequestParam(value="acountId") Integer acountId,
			@RequestParam(value="uv",defaultValue="0",required=false) Integer uv,
			HttpSession session,HttpServletRequest request)  {
		if(uv!=0 &&uv!=1){
			return ResultUtil.getFail();
		}
		DataRabbitmqDTO drd=new DataRabbitmqDTO();
		drd.setAcountId(acountId);//转发    10积分（获得3个有效阅读）
		drd.setGoodsId(goodsId);
		drd.setUv(uv);
		drd.setIp(IPCountUtil.getIpAddr(request));//请求的ip地址
		sender.sendGoodsWebRead(drd);
		return ResultUtil.getSuccess();
	}
	
	/**
	 * 物品分页浏览
	 * @param orderName 物品排序数据库字段
	 * @param orderWay 物品排序方法 asc升序 desc降序
	 * @return
	 */
	@RequestMapping(value = "/list", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList browsePagingGoods(
			@RequestParam(value="recommend",required=false)Integer recommend,
			@RequestParam(value="singleCharge",required=false)Double singleCharge,
			@RequestParam(value="chargeNumber",required=false)Integer chargeNumber,
			@RequestParam(value="readingNumber",required=false)Integer readingNumber,
			@RequestParam(value="goodsCateId",required=false)Integer goodsCateId,
			@RequestParam(value="createDate",required=false)Date createDate,
			@RequestParam(value="updateDate",required=false)Date updateDate,
			@RequestParam(value="status",required=false)Integer status,
			@RequestParam(value="pageNum",defaultValue="1",required=false)int pageNum,
			@RequestParam(value="pageSize",defaultValue="10",required=false) int pageSize,
			@RequestParam(value="orderName",required=false,defaultValue="goods_id") String orderName,
			@RequestParam(value="orderWay",required=false,defaultValue="desc") String orderWay)  {
			List<Goods> list = new ArrayList<Goods>();
			list= goodsService.browsePagingGoods(recommend,singleCharge,chargeNumber,readingNumber,goodsCateId,createDate,updateDate,status,pageNum, pageSize, orderName, orderWay);
			if(list.size()>0){
				return ResultUtil.getSlefSRSuccessList(list);
			}else{
				return ResultUtil.getSlefSRFailList(list);
			}
	}
	/**
	 * 物品修改
	 * @return
	 */
	@RequestMapping(value = "/update", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResult updateGoods(@RequestBody Goods goods,HttpSession session)  {
		boolean um = goodsService.updateGoods(goods);
		return ResultUtil.getSR(um);
	}
	/**
	 * 物品增加
	 * @return 
	 */
	@RequestMapping(value = "/add", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResult addGoods(@RequestBody Goods goods, HttpSession session) {
		boolean am = goodsService.addGoods(goods);
		return ResultUtil.getSR(am);
	}

	/**
	 * 物品删除
	 * @return
	 */
	@RequestMapping(value = "/delete", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResult delGoods(@RequestParam("goodsId") Integer goodsId,HttpSession session)  {
		boolean dm = goodsService.delGoods(goodsId);
		return ResultUtil.getSR(dm);
	}
	/**
	 * 物品浏览数量
	 * @return
	 */
	@RequestMapping(value = "/count", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody int countAll(
			@RequestParam(value="recommend",required=false)Integer recommend,
			@RequestParam(value="singleCharge",required=false)Double singleCharge,
			@RequestParam(value="chargeNumber",required=false)Integer chargeNumber,
			@RequestParam(value="readingNumber",required=false)Integer readingNumber,
			@RequestParam(value="goodsCateId",required=false)Integer goodsCateId,
			@RequestParam(value="createDate",required=false)Date createDate,
			@RequestParam(value="updateDate",required=false)Date updateDate,
			@RequestParam(value="status",required=false)Integer status,
			HttpSession session)  {
		int count = goodsService.countAll(recommend,singleCharge,chargeNumber,readingNumber,goodsCateId,createDate,updateDate,status);
		return count;
	}
	/**
	 * 物品单个加载
	 * @return
	 */
	@RequestMapping(value = "/{goodsId}", method = {RequestMethod.GET,RequestMethod.POST})
	public  StateResultList loadGoods(@PathVariable("goodsId") Integer goodsId,HttpSession session)  {
		List<Goods> list = new ArrayList<Goods>();
		Goods goods = goodsService.loadGoods(goodsId);
			if(goods!=null &&!goods.equals("")){
				list.add(goods);
				return ResultUtil.getSlefSRSuccessList(list);
			}else{
				return ResultUtil.getSlefSRFailList(list);
			}
	}
	/**
	 * 物品单个加载
	 * @return
	 */
	@RequestMapping(value = "/loadSmall/{goodsId}", method = {RequestMethod.GET,RequestMethod.POST})
	public  StateResultList loadSmallGoods(@PathVariable("goodsId") Integer goodsId,HttpSession session)  {
		List<Goods> list = new ArrayList<Goods>();
		Goods goods = goodsService.loadSmallGoods(goodsId);
		if(goods!=null &&!goods.equals("")){
			list.add(goods);
			return ResultUtil.getSlefSRSuccessList(list);
		}else{
			return ResultUtil.getSlefSRFailList(list);
		}
	}
	
}
