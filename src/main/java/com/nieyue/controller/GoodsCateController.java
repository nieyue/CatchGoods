package com.nieyue.controller;

import java.util.ArrayList;
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

import com.nieyue.bean.GoodsCate;
import com.nieyue.service.GoodsCateService;
import com.nieyue.util.ResultUtil;
import com.nieyue.util.StateResult;
import com.nieyue.util.StateResultList;


/**
 * 物品类型控制类
 * @author yy
 *
 */
@RestController
@RequestMapping("/goodsCate")
public class GoodsCateController {
	@Resource
	private GoodsCateService goodsCateService;
	
	/**
	 * 物品类型分页浏览
	 * @param orderName 物品排序数据库字段
	 * @param orderWay 物品排序方法 asc升序 desc降序
	 * @return
	 */
	@RequestMapping(value = "/list", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList browsePagingGoodsCate(
			@RequestParam(value="pageNum",defaultValue="1",required=false)int pageNum,
			@RequestParam(value="pageSize",defaultValue="10",required=false) int pageSize,
			@RequestParam(value="orderName",required=false,defaultValue="goods_cate_id") String orderName,
			@RequestParam(value="orderWay",required=false,defaultValue="desc") String orderWay)  {
			List<GoodsCate> list = new ArrayList<GoodsCate>();
			list= goodsCateService.browsePagingGoodsCate(pageNum, pageSize, orderName, orderWay);
			if(list.size()>0){
				return ResultUtil.getSlefSRSuccessList(list);
			}else{
				return ResultUtil.getSlefSRFailList(list);
			}
	}
	/**
	 * 物品类型修改
	 * @return
	 */
	@RequestMapping(value = "/update", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResult updateGoodsCate(@ModelAttribute GoodsCate goodsCate,HttpSession session)  {
		boolean um = goodsCateService.updateGoodsCate(goodsCate);
		return ResultUtil.getSR(um);
	}
	/**
	 * 物品类型增加
	 * @return 
	 */
	@RequestMapping(value = "/add", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResult addGoodsCate(@ModelAttribute GoodsCate goodsCate, HttpSession session) {
		boolean am = goodsCateService.addGoodsCate(goodsCate);
		return ResultUtil.getSR(am);
	}
	/**
	 * 物品类型删除
	 * @return
	 */
	@RequestMapping(value = "/delete", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResult delGoodsCate(@RequestParam("goodsCateId") Integer goodsCateId,HttpSession session)  {
		boolean dm = goodsCateService.delGoodsCate(goodsCateId);
		return ResultUtil.getSR(dm);
	}
	/**
	 * 物品类型浏览数量
	 * @return
	 */
	@RequestMapping(value = "/count", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody int countAll(HttpSession session)  {
		int count = goodsCateService.countAll();
		return count;
	}
	/**
	 * 物品类型单个加载
	 * @return
	 */
	@RequestMapping(value = "/{goodsCateId}", method = {RequestMethod.GET,RequestMethod.POST})
	public  StateResultList loadGoodsCate(@PathVariable("goodsCateId") Integer goodsCateId,HttpSession session)  {
		List<GoodsCate> list = new ArrayList<GoodsCate>();
		GoodsCate goodsCate = goodsCateService.loadGoodsCate(goodsCateId,null);
			if(goodsCate!=null &&!goodsCate.equals("")){
				list.add(goodsCate);
				return ResultUtil.getSlefSRSuccessList(list);
			}else{
				return ResultUtil.getSlefSRFailList(list);
			}
	}
	
}
