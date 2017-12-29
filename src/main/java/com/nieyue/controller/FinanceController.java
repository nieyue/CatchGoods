package com.nieyue.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nieyue.bean.Finance;
import com.nieyue.service.AcountService;
import com.nieyue.service.DataService;
import com.nieyue.service.FinanceService;
import com.nieyue.util.ResultUtil;
import com.nieyue.util.StateResult;
import com.nieyue.util.StateResultList;


/**
 * 财务控制类
 * @author yy
 *
 */
@RestController
@RequestMapping("/finance")
public class FinanceController {
	@Resource
	private FinanceService financeService;
	@Resource
	private AcountService acountService;
	@Resource
	private DataService dataService;
	@Resource
	private StringRedisTemplate stringRedisTemplate;
	
	/**
	 * 财务分页浏览
	 * @param orderName 商品排序数据库字段
	 * @param orderWay 商品排序方法 asc升序 desc降序
	 * @return
	 */
	@RequestMapping(value = "/list", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList browsePagingFinance(
			@RequestParam(value="money",required=false)Double money,
			@RequestParam(value="acountId",required=false)Integer acountId,
			@RequestParam(value="pageNum",defaultValue="1",required=false)int pageNum,
			@RequestParam(value="pageSize",defaultValue="10",required=false) int pageSize,
			@RequestParam(value="orderName",required=false,defaultValue="finance_id") String orderName,
			@RequestParam(value="orderWay",required=false,defaultValue="desc") String orderWay,HttpSession session)  {
			List<Finance> list = new ArrayList<Finance>();
			list= financeService.browsePagingFinance(money,acountId,pageNum, pageSize, orderName, orderWay);
			if(list.size()>0){
				
				return ResultUtil.getSlefSRSuccessList(list);
			}else{
				return ResultUtil.getSlefSRFailList(list);
			}
	}
	/**
	 * 财务数据
	 * @param orderName 商品排序数据库字段
	 * @param orderWay 商品排序方法 asc升序 desc降序
	 * @return
	 */
/*	@RequestMapping(value = "/data", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList browseFinanceData(HttpServletRequest request,
			@RequestParam(value="acountId",required=false)Integer acountId,
			HttpSession session)  {
		List<FinanceDataDTO> list = new ArrayList<FinanceDataDTO>();
		list= financeService.browseFinanceData(acountId);
		if(list.size()>0){
			return ResultUtil.getSlefSRSuccessList(list);
			
		}else{
			return ResultUtil.getSlefSRFailList(list);
		}
	}*/
	/**
	 * 财务日数据
	 * @param orderName 
	 * @param orderWay 
	 * @return
	 */
	//@RequestLimit(count=10,time=100000)
	/*@RequestMapping(value = "/daydata", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList browseFinanceDayData(
			HttpServletRequest request,
			@RequestParam(value="startDate",required=false)Date startDate,
			@RequestParam(value="endDate",required=false)Date endDate,
			@RequestParam(value="type",required=false)Integer type,
			@RequestParam(value="subtype",required=false)Integer subtype,
			HttpSession session)  {
		List<FinanceDayDataDTO> list = new ArrayList<FinanceDayDataDTO>();
		list= financeService.browseFinanceDayData(startDate,endDate,type,subtype);
		if(list.size()>0){
			return ResultUtil.getSlefSRSuccessList(list);
		}else{
			return ResultUtil.getSlefSRFailList(list);
		}
	}*/
	/**
	 * 财务修改
	 * @return
	 */
	@RequestMapping(value = "/update", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResult updateFinance(@ModelAttribute Finance finance,HttpSession session)  {
		boolean um = financeService.updateFinance(finance);
		return ResultUtil.getSR(um);
	}
	/**
	 * 财务增加
	 * @return 
	 */
	@RequestMapping(value = "/add", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResult addFinance(@ModelAttribute Finance finance, HttpSession session) {
		boolean am = financeService.addFinance(finance);
		return ResultUtil.getSR(am);
	}
	/**
	 * 财务删除
	 * @return
	 */
	@RequestMapping(value = "/delete", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResult delFinance(
			@RequestParam("financeId") Integer financeId,HttpSession session)  {
		boolean dm =financeService.delFinance(financeId);
		return ResultUtil.getSR(dm);
	}
	/**
	 * 财务浏览数量
	 * @return
	 */
	@RequestMapping(value = "/count", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody int countAll(
			@RequestParam(value="money",required=false)Double money,
			@RequestParam(value="acountId",required=false)Integer acountId,HttpSession session)  {
		int count = financeService.countAll(money,acountId);
		return count;
	}
	/**
	 * 财务单个加载
	 * @return
	 */
	@RequestMapping(value = "/{financeId}", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList loadFinance(@PathVariable("financeId") Integer financeId,HttpSession session)  {
		List<Finance> list = new ArrayList<Finance>();
		Finance finance = financeService.loadFinance(financeId);
			if(finance!=null &&!finance.equals("")){
				list.add(finance);
				return ResultUtil.getSlefSRSuccessList(list);
			}else{
				return ResultUtil.getSlefSRFailList(list);
			}
	}
	
}
