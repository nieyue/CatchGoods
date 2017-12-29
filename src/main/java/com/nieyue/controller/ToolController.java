package com.nieyue.controller;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.BoundZSetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.nieyue.comments.RequestToMethdoItemUtils;
import com.nieyue.comments.RequestToMethodItem;
import com.nieyue.limit.RequestLimit;
import com.nieyue.util.ResultUtil;
import com.nieyue.util.StateResult;
import com.nieyue.util.StateResultList;
import com.nieyue.util.barcode.QRCodeUtil;
import com.nieyue.verification.VerificationCode;

import net.sf.json.JSONObject;



/**
 * 控制类
 * @author yy
 *
 */
@RestController
public class ToolController {
	
	@Resource
	StringRedisTemplate stringRedisTemplate;
	@Resource
	RequestToMethdoItemUtils requestToMethdoItemUtils;
	@Resource
	VerificationCode verificationCode;
	@Value("${myPugin.projectName}")
	String projectName;
	/**
	 * 首页
	 * @return
	 */
	@RequestMapping(value={"/"}, method = {RequestMethod.GET,RequestMethod.POST})
	public RedirectView index(){
		return new RedirectView("/seller/index.html");
		
	}
	/**
	 * 验证码
	 * @param date
	 * @return
	 * @throws Exception 
	 */
	@RequestLimit(count=3,time=1000)
	@RequestMapping("/getVerificationCode")
	public void getVerificationCode(
			HttpSession session,HttpServletRequest request,HttpServletResponse response) throws Exception{
			ByteArrayOutputStream vc = verificationCode.execute(session);
			response.getOutputStream().write(vc.toByteArray());
		return ;
	}
	/**
	 * 获取API接口文档
	 * @return
	 */
	@RequestMapping(value={"/tool/getAPI"}, method = {RequestMethod.GET,RequestMethod.POST})
	public StateResultList getAPI(
			HttpServletRequest request
			){
		List<RequestToMethodItem> requestToMethdoItemUtilsresult = requestToMethdoItemUtils.getRequestToMethodItemList(request);
		return ResultUtil.getSlefSRSuccessList(requestToMethdoItemUtilsresult);
	
	}
	/**
	 * 获取Session
	 * @return
	 */
	@RequestMapping(value={"/tool/getSession"}, method = {RequestMethod.GET,RequestMethod.POST})
	public String getSession(
			HttpSession	 session
			){
		System.err.println(session.getAttribute("acount"));
		System.err.println(session.getAttribute("role"));
		System.err.println(session.getAttribute("finance"));
		return session.getId();
		
	}
	
	/**
	 * 设置全分享页面域名 article.html (如：光明网)
	 * @param session
	 * @param scaleValue
	 * @return
	 */
	@RequestMapping(value="/shareDomain/update", method = {RequestMethod.GET,RequestMethod.POST})
	public StateResult updateShareDomain(
			HttpSession session,
			@RequestParam("shareDomain")String shareDomain
			){
		BoundValueOperations<String, String> bvo=stringRedisTemplate.boundValueOps(projectName+"ShareDomain");
		bvo.getAndSet(shareDomain);
		return ResultUtil.getSuccess();
	}
	/**
	 * 获取全分享页面域名 article.html
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/shareDomain/get", method = {RequestMethod.GET,RequestMethod.POST})
	public StateResultList getShareDomain(
			HttpSession session){
		List<JSONObject> list = new ArrayList<JSONObject>();
		BoundValueOperations<String, String> bvo=stringRedisTemplate.boundValueOps(projectName+"ShareDomain");
		JSONObject jo=new JSONObject();
		jo.put("shareDomain", bvo.get());
		list.add(jo);
		return ResultUtil.getSlefSRSuccessList(list);
	}
	/**
	 * 设置跨域广告域名 ad.html 
	 * @param session
	 * @param scaleValue
	 * @return
	 */
	@RequestMapping(value="/adDomain/update", method = {RequestMethod.GET,RequestMethod.POST})
	public StateResult updateAdDomain(
			HttpSession session,
			@RequestParam("adDomain")String adDomain
			){
		BoundValueOperations<String, String> bvo=stringRedisTemplate.boundValueOps(projectName+"AdDomain");
		bvo.getAndSet(adDomain);
		return ResultUtil.getSuccess();
	}
	/**
	 * 获取跨域广告域名 ad.html 
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/adDomain/get", method = {RequestMethod.GET,RequestMethod.POST})
	public StateResultList getAdDomain(
			HttpSession session){
		List<JSONObject> list = new ArrayList<JSONObject>();
		BoundValueOperations<String, String> bvo=stringRedisTemplate.boundValueOps(projectName+"AdDomain");
		JSONObject jo=new JSONObject();
		jo.put("adDomain", bvo.get());
		list.add(jo);
		return ResultUtil.getSlefSRSuccessList(list);
	}
	/**
	 * 获取光明网
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/gmwDomain/list", method = {RequestMethod.GET,RequestMethod.POST})
	public StateResultList getGmwDomain(
			HttpSession session){
		List<String> list = new ArrayList<String>();
		BoundZSetOperations<String, String> bzso=stringRedisTemplate.boundZSetOps(projectName+"GmwDomain");
		Set<String> l = bzso.range(0, -1);
		list.addAll(l);
		return ResultUtil.getSlefSRSuccessList(list);
	}
	/**
	 * add光明网
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/gmwDomain/add", method = {RequestMethod.GET,RequestMethod.POST})
	public StateResultList addGmwDomain(
			@RequestParam("value") String value,
			@RequestParam(value="score",required=false,defaultValue="1") double score,
			HttpSession session			
			){
		List<String> list = new ArrayList<String>();
		BoundZSetOperations<String, String> bzso=stringRedisTemplate.boundZSetOps(projectName+"GmwDomain");
		bzso.add(value, score);
		Set<String> l = bzso.range(0, -1);
		list.addAll(l);
		return ResultUtil.getSlefSRSuccessList(list);
	}
	/**
	 * add光明网
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/gmwDomain/del", method = {RequestMethod.GET,RequestMethod.POST})
	public StateResultList delGmwDomain(
			@RequestParam("value") String value,
			HttpSession session			
			){
		List<String> list = new ArrayList<String>();
		BoundZSetOperations<String, String> bzso=stringRedisTemplate.boundZSetOps(projectName+"GmwDomain");
		bzso.remove(value);
		Set<String> l = bzso.range(0, -1);
		list.addAll(l);
		return ResultUtil.getSlefSRSuccessList(list);
	}
	
	/**
	 * 设置三俗页面域名 article.html 
	 * @param session
	 * @param scaleValue
	 * @return
	 */
	@RequestMapping(value="/ssDomain/update", method = {RequestMethod.GET,RequestMethod.POST})
	public StateResult updateSsDomain(
			HttpSession session,
			@RequestParam("ssDomain")String ssDomain
			){
		BoundValueOperations<String, String> bvo=stringRedisTemplate.boundValueOps(projectName+"SsDomain");
		bvo.getAndSet(ssDomain);
		return ResultUtil.getSuccess();
	}
	/**
	 * 获取三俗页面域名 article.html
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/ssDomain/get", method = {RequestMethod.GET,RequestMethod.POST})
	public StateResultList getSsDomain(
			HttpSession session){
		List<JSONObject> list = new ArrayList<JSONObject>();
		BoundValueOperations<String, String> bvo=stringRedisTemplate.boundValueOps(projectName+"SsDomain");
		JSONObject jo=new JSONObject();
		jo.put("ssDomain", bvo.get());
		list.add(jo);
		return ResultUtil.getSlefSRSuccessList(list);
	}
	/**
	 * 二维码
	 * @param date
	 * @return
	 * @throws Exception 
	 */
	@RequestLimit(count=3,time=1000)
	@RequestMapping(value="/getBarcode", method = {RequestMethod.GET,RequestMethod.POST})
	public void getBarcode(
			@RequestParam("acountId")Integer acountId,
			HttpSession session,HttpServletRequest request,HttpServletResponse response) throws Exception{
		BoundValueOperations<String, String> bvo=stringRedisTemplate.boundValueOps(projectName+"ShareDomain");
		String text = "http://"+bvo.get()+"/share.html?acountId="+acountId;
		QRCodeUtil.encode(text, response.getOutputStream());
		return ;
	}
	/**
	 * 二维码Url
	 * @param date
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/getBarcodeUrl", method = {RequestMethod.GET,RequestMethod.POST})
	public StateResultList getBarcodeUrl(
			@RequestParam("acountId")Integer acountId,
			HttpSession session,HttpServletRequest request,HttpServletResponse response) throws Exception{
		List<String> list = new ArrayList<String>();
		BoundValueOperations<String, String> bvo=stringRedisTemplate.boundValueOps(projectName+"ShareDomain");
		String text = "http://"+bvo.get()+"/share.html?acountId="+acountId;
		list.add(text);
		return ResultUtil.getSlefSRSuccessList(list);
	}
	
	/**
	 * 获取js代码广告列表
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/jsAd/list", method = {RequestMethod.GET,RequestMethod.POST})
	public StateResultList getJsAd(
			HttpSession session){
		List<String> list = new ArrayList<String>();
		BoundZSetOperations<String, String> bzso=stringRedisTemplate.boundZSetOps(projectName+"JsAd");
		Set<String> l = bzso.range(0, -1);
		list.addAll(l);
		return ResultUtil.getSlefSRSuccessList(list);
	}
	/**
	 * add js代码广告
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/jsAd/add", method = {RequestMethod.GET,RequestMethod.POST})
	public StateResultList addJsAd(
			@RequestParam("value") String value,
			@RequestParam(value="score",required=false,defaultValue="1") double score,
			HttpSession session			
			){
		List<String> list = new ArrayList<String>();
		BoundZSetOperations<String, String> bzso=stringRedisTemplate.boundZSetOps(projectName+"JsAd");
		bzso.add(value, score);
		Set<String> l = bzso.range(0, -1);
		list.addAll(l);
		return ResultUtil.getSlefSRSuccessList(list);
	}
	/**
	 * del js代码广告
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/jsAd/del", method = {RequestMethod.GET,RequestMethod.POST})
	public StateResultList delJsAd(
			@RequestParam("value") String value,
			HttpSession session			
			){
		List<String> list = new ArrayList<String>();
		BoundZSetOperations<String, String> bzso=stringRedisTemplate.boundZSetOps(projectName+"JsAd");
		bzso.remove(value);
		Set<String> l = bzso.range(0, -1);
		list.addAll(l);
		return ResultUtil.getSlefSRSuccessList(list);
	}
	
}
