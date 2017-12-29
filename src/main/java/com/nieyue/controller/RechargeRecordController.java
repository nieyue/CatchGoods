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

import com.nieyue.bean.RechargeRecord;
import com.nieyue.service.RechargeRecordService;
import com.nieyue.util.ResultUtil;
import com.nieyue.util.StateResult;
import com.nieyue.util.StateResultList;


/**
 * 充值记录控制类
 * @author yy
 *
 */
@RestController
@RequestMapping("/rechargeRecord")
public class RechargeRecordController {
	@Resource
	private RechargeRecordService rechargeRecordService;
	
	/**
	 * 充值记录分页浏览
	 * @param orderName 商品排序数据库字段
	 * @param orderWay 商品排序方法 asc升序 desc降序
	 * @return
	 */
	@RequestMapping(value = "/list", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList browsePagingRechargeRecord(
			@RequestParam(value="pageNum",defaultValue="1",required=false)int pageNum,
			@RequestParam(value="pageSize",defaultValue="10",required=false) int pageSize,
			@RequestParam(value="orderName",required=false,defaultValue="update_date") String orderName,
			@RequestParam(value="orderWay",required=false,defaultValue="desc") String orderWay)  {
			List<RechargeRecord> list = new ArrayList<RechargeRecord>();
			list= rechargeRecordService.browsePagingRechargeRecord(pageNum, pageSize, orderName, orderWay);
			if(list.size()>0){
				return ResultUtil.getSlefSRSuccessList(list);
			}else{
				return ResultUtil.getSlefSRFailList(list);
			}
	}
	/**
	 * 充值记录修改
	 * @return
	 */
	@RequestMapping(value = "/update", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResult updateRechargeRecord(@ModelAttribute RechargeRecord rechargeRecord,HttpSession session)  {
		boolean um = rechargeRecordService.updateRechargeRecord(rechargeRecord);
		return ResultUtil.getSR(um);
	}
	/**
	 * 充值记录增加
	 * @return 
	 */
	@RequestMapping(value = "/add", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResult addRechargeRecord(@ModelAttribute RechargeRecord rechargeRecord, HttpSession session) {
		boolean am = rechargeRecordService.addRechargeRecord(rechargeRecord);
		return ResultUtil.getSR(am);
	}
	/**
	 * 充值记录删除
	 * @return
	 */
	@RequestMapping(value = "/delete", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResult delRechargeRecord(@RequestParam("rechargeRecordId") Integer rechargeRecordId,HttpSession session)  {
		boolean dm = rechargeRecordService.delRechargeRecord(rechargeRecordId);
		return ResultUtil.getSR(dm);
	}
	/**
	 * 充值记录浏览数量
	 * @return
	 */
	@RequestMapping(value = "/count", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody int countAll(HttpSession session)  {
		int count = rechargeRecordService.countAll();
		return count;
	}
	/**
	 * 充值记录单个加载
	 * @return
	 */
	@RequestMapping(value = "/{rechargeRecordId}", method = {RequestMethod.GET,RequestMethod.POST})
	public  StateResultList loadRechargeRecord(@PathVariable("rechargeRecordId") Integer rechargeRecordId,HttpSession session)  {
		List<RechargeRecord> list = new ArrayList<RechargeRecord>();
		RechargeRecord rechargeRecord = rechargeRecordService.loadRechargeRecord(rechargeRecordId);
			if(rechargeRecord!=null &&!rechargeRecord.equals("")){
				list.add(rechargeRecord);
				return ResultUtil.getSlefSRSuccessList(list);
			}else{
				return ResultUtil.getSlefSRFailList(list);
			}
	}
	
}
