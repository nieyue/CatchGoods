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

import com.nieyue.bean.RechargeTerm;
import com.nieyue.service.RechargeTermService;
import com.nieyue.util.ResultUtil;
import com.nieyue.util.StateResult;
import com.nieyue.util.StateResultList;


/**
 * 充值项控制类
 * @author yy
 *
 */
@RestController
@RequestMapping("/rechargeTerm")
public class RechargeTermController {
	@Resource
	private RechargeTermService rechargeTermService;
	
	/**
	 * 充值项分页浏览
	 * @param orderName 商品排序数据库字段
	 * @param orderWay 商品排序方法 asc升序 desc降序
	 * @return
	 */
	@RequestMapping(value = "/list", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList browsePagingRechargeTerm(
			@RequestParam(value="pageNum",defaultValue="1",required=false)int pageNum,
			@RequestParam(value="pageSize",defaultValue="10",required=false) int pageSize,
			@RequestParam(value="orderName",required=false,defaultValue="update_date") String orderName,
			@RequestParam(value="orderWay",required=false,defaultValue="desc") String orderWay)  {
			List<RechargeTerm> list = new ArrayList<RechargeTerm>();
			list= rechargeTermService.browsePagingRechargeTerm(pageNum, pageSize, orderName, orderWay);
			if(list.size()>0){
				return ResultUtil.getSlefSRSuccessList(list);
			}else{
				return ResultUtil.getSlefSRFailList(list);
			}
	}
	/**
	 * 充值项修改
	 * @return
	 */
	@RequestMapping(value = "/update", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResult updateRechargeTerm(@ModelAttribute RechargeTerm rechargeTerm,HttpSession session)  {
		boolean um = rechargeTermService.updateRechargeTerm(rechargeTerm);
		return ResultUtil.getSR(um);
	}
	/**
	 * 充值项增加
	 * @return 
	 */
	@RequestMapping(value = "/add", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResult addRechargeTerm(@ModelAttribute RechargeTerm rechargeTerm, HttpSession session) {
		boolean am = rechargeTermService.addRechargeTerm(rechargeTerm);
		return ResultUtil.getSR(am);
	}
	/**
	 * 充值项删除
	 * @return
	 */
	@RequestMapping(value = "/delete", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResult delRechargeTerm(@RequestParam("rechargeTermId") Integer rechargeTermId,HttpSession session)  {
		boolean dm = rechargeTermService.delRechargeTerm(rechargeTermId);
		return ResultUtil.getSR(dm);
	}
	/**
	 * 充值项浏览数量
	 * @return
	 */
	@RequestMapping(value = "/count", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody int countAll(HttpSession session)  {
		int count = rechargeTermService.countAll();
		return count;
	}
	/**
	 * 充值项单个加载
	 * @return
	 */
	@RequestMapping(value = "/{rechargeTermId}", method = {RequestMethod.GET,RequestMethod.POST})
	public  StateResultList loadRechargeTerm(@PathVariable("rechargeTermId") Integer rechargeTermId,HttpSession session)  {
		List<RechargeTerm> list = new ArrayList<RechargeTerm>();
		RechargeTerm rechargeTerm = rechargeTermService.loadRechargeTerm(rechargeTermId);
			if(rechargeTerm!=null &&!rechargeTerm.equals("")){
				list.add(rechargeTerm);
				return ResultUtil.getSlefSRSuccessList(list);
			}else{
				return ResultUtil.getSlefSRFailList(list);
			}
	}
	
}
