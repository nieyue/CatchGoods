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

import com.nieyue.bean.ReceiptInfo;
import com.nieyue.service.ReceiptInfoService;
import com.nieyue.util.ResultUtil;
import com.nieyue.util.StateResult;
import com.nieyue.util.StateResultList;

import net.sf.json.JSONObject;


/**
 * 收货信息控制类
 * @author yy
 *
 */
@RestController
@RequestMapping("/receiptInfo")
public class ReceiptInfoController {
	@Resource
	private ReceiptInfoService receiptInfoService;
	
	/**
	 * 收货信息分页浏览
	 * @param orderName 收货信息排序数据库字段
	 * @param orderWay 收货信息排序方法 asc升序 desc降序
	 * @return
	 */
	@RequestMapping(value = "/list", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList browsePagingReceiptInfo(
			@RequestParam(value="acountId",required=false)Integer acountId,
			@RequestParam(value="isDefault",required=false)Integer isDefault,
			@RequestParam(value="createDate",required=false)Date createDate,
			@RequestParam(value="updateDate",required=false)Date updateDate,
			@RequestParam(value="pageNum",defaultValue="1",required=false)int pageNum,
			@RequestParam(value="pageSize",defaultValue="10",required=false) int pageSize,
			@RequestParam(value="orderName",required=false,defaultValue="receipt_info_id") String orderName,
			@RequestParam(value="orderWay",required=false,defaultValue="desc") String orderWay)  {
			List<ReceiptInfo> list = new ArrayList<ReceiptInfo>();
			list= receiptInfoService.browsePagingReceiptInfo(acountId,isDefault,createDate,updateDate,pageNum, pageSize, orderName, orderWay);
			if(list.size()>0){
				return ResultUtil.getSlefSRSuccessList(list);
			}else{
				return ResultUtil.getSlefSRFailList(list);
			}
	}
	/**
	 * 收货信息修改
	 * @return
	 */
	@RequestMapping(value = "/update", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResult updateReceiptInfo(@ModelAttribute ReceiptInfo receiptInfo,HttpSession session)  {
		System.out.println(JSONObject.fromObject(receiptInfo).toString());
		boolean um =receiptInfoService.updateReceiptInfo(receiptInfo);
		return ResultUtil.getSR(um); 
	}
	/**
	 * 收货信息增加
	 * @return 
	 */
	@RequestMapping(value = "/add", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResult addReceiptInfo(@ModelAttribute ReceiptInfo receiptInfo, HttpSession session) {
		boolean am = receiptInfoService.addReceiptInfo(receiptInfo);
		return ResultUtil.getSR(am);
	}
	/**
	 * 收货信息删除
	 * @return
	 */
	@RequestMapping(value = "/delete", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResult delReceiptInfo(@RequestParam("receiptInfoId") Integer receiptInfoId,HttpSession session)  {
		boolean dm = receiptInfoService.delReceiptInfo(receiptInfoId);
		return ResultUtil.getSR(dm);
	}
	/**
	 * 收货信息浏览数量
	 * @return
	 */
	@RequestMapping(value = "/count", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody int countAll(
			@RequestParam(value="acountId",required=false)Integer acountId,
			@RequestParam(value="isDefault",required=false)Integer isDefault,
			@RequestParam(value="createDate",required=false)Date createDate,
			@RequestParam(value="updateDate",required=false)Date updateDate,
			HttpSession session)  {
		int count = receiptInfoService.countAll(acountId,isDefault,createDate,updateDate);
		return count;
	}
	/**
	 * 收货信息单个加载
	 * @return
	 */
	@RequestMapping(value = "/{receiptInfoId}", method = {RequestMethod.GET,RequestMethod.POST})
	public  StateResultList loadReceiptInfo(@PathVariable("receiptInfoId") Integer receiptInfoId,HttpSession session)  {
		List<ReceiptInfo> list = new ArrayList<ReceiptInfo>();
		ReceiptInfo receiptInfo = receiptInfoService.loadReceiptInfo(receiptInfoId);
			if(receiptInfo!=null &&!receiptInfo.equals("")){
				list.add(receiptInfo);
				return ResultUtil.getSlefSRSuccessList(list);
			}else{
				return ResultUtil.getSlefSRFailList(list);
			}
	}
	/**
	 * 设置默认收货信息
	 * @return
	 */
	@RequestMapping(value = "/setIsDefault", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResult setReceiptInfoIsDefault(
			@RequestParam(value="receiptInfoId") Integer receiptInfoId,
			@RequestParam(value="acountId") Integer acountId,
			HttpSession session)  {
		boolean b=false;
			List<ReceiptInfo> l = receiptInfoService.browsePagingReceiptInfo(acountId, 1, null, null, 1, Integer.MAX_VALUE, "receipt_info_id", "asc");
			if(l.size()>0){
			for (int i = 0; i < l.size(); i++) {
				ReceiptInfo re = l.get(i);
				re.setIsDefault(0);
				b = receiptInfoService.updateReceiptInfo(re);
			}
			}
		ReceiptInfo newreceiptInfo = receiptInfoService.loadReceiptInfo(receiptInfoId);
		newreceiptInfo.setIsDefault(1);
		b=receiptInfoService.updateReceiptInfo(newreceiptInfo);
		return ResultUtil.getSR(b);   
	}
	
}
