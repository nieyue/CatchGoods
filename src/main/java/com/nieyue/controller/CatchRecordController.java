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

import com.nieyue.bean.CatchRecord;
import com.nieyue.service.CatchRecordService;
import com.nieyue.util.ResultUtil;
import com.nieyue.util.StateResult;
import com.nieyue.util.StateResultList;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


/**
 * 抓取记录控制类
 * @author yy
 *
 */
@Api(tags={"catchRecord"},value="抓取记录",description="抓取记录")
@RestController
@RequestMapping("/catchRecord")
public class CatchRecordController {
	@Resource
	private CatchRecordService catchRecordService;
	
	/**
	 * 抓取记录分页浏览
	 * @param orderName 商品排序数据库字段
	 * @param orderWay 商品排序方法 asc升序 desc降序
	 * @return
	 */
	@ApiOperation(value = "抓取记录列表", notes = "抓取记录分页浏览")
	@RequestMapping(value = "/list", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList browsePagingCatchRecord(
			@RequestParam(value="pageNum",defaultValue="1",required=false)int pageNum,
			@RequestParam(value="pageSize",defaultValue="10",required=false) int pageSize,
			@RequestParam(value="orderName",required=false,defaultValue="update_date") String orderName,
			@RequestParam(value="orderWay",required=false,defaultValue="desc") String orderWay)  {
			List<CatchRecord> list = new ArrayList<CatchRecord>();
			list= catchRecordService.browsePagingCatchRecord(pageNum, pageSize, orderName, orderWay);
			if(list.size()>0){
				return ResultUtil.getSlefSRSuccessList(list);
			}else{
				return ResultUtil.getSlefSRFailList(list);
			}
	}
	/**
	 * 抓取记录修改
	 * @return
	 */
	@RequestMapping(value = "/update", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResult updateCatchRecord(@ModelAttribute CatchRecord catchRecord,HttpSession session)  {
		boolean um = catchRecordService.updateCatchRecord(catchRecord);
		return ResultUtil.getSR(um);
	}
	/**
	 * 抓取记录增加
	 * @return 
	 */
	@RequestMapping(value = "/add", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResult addCatchRecord(@ModelAttribute CatchRecord catchRecord, HttpSession session) {
		boolean am = catchRecordService.addCatchRecord(catchRecord);
		return ResultUtil.getSR(am);
	}
	/**
	 * 抓取记录删除
	 * @return
	 */
	@RequestMapping(value = "/delete", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResult delCatchRecord(@RequestParam("catchRecordId") Integer catchRecordId,HttpSession session)  {
		boolean dm = catchRecordService.delCatchRecord(catchRecordId);
		return ResultUtil.getSR(dm);
	}
	/**
	 * 抓取记录浏览数量
	 * @return
	 */
	@RequestMapping(value = "/count", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody int countAll(HttpSession session)  {
		int count = catchRecordService.countAll();
		return count;
	}
	/**
	 * 抓取记录单个加载
	 * @return
	 */
	@RequestMapping(value = "/{catchRecordId}", method = {RequestMethod.GET,RequestMethod.POST})
	public  StateResultList loadCatchRecord(@PathVariable("catchRecordId") Integer catchRecordId,HttpSession session)  {
		List<CatchRecord> list = new ArrayList<CatchRecord>();
		CatchRecord catchRecord = catchRecordService.loadCatchRecord(catchRecordId);
			if(catchRecord!=null &&!catchRecord.equals("")){
				list.add(catchRecord);
				return ResultUtil.getSlefSRSuccessList(list);
			}else{
				return ResultUtil.getSlefSRFailList(list);
			}
	}
	
}
