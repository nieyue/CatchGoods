package com.nieyue.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.aliyuncs.exceptions.ClientException;
import com.nieyue.bean.Acount;
import com.nieyue.bean.Finance;
import com.nieyue.bean.Role;
import com.nieyue.exception.AcountIsExistException;
import com.nieyue.exception.MySessionException;
import com.nieyue.exception.VerifyCodeErrorException;
import com.nieyue.limit.RequestLimitException;
import com.nieyue.rabbitmq.confirmcallback.Sender;
import com.nieyue.service.AcountService;
import com.nieyue.service.FinanceService;
import com.nieyue.service.RoleService;
import com.nieyue.util.MyDESutil;
import com.nieyue.util.MyValidator;
import com.nieyue.util.ResultUtil;
import com.nieyue.util.StateResult;
import com.nieyue.util.StateResultList;
import com.yayao.messageinterface.AliyunSms;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;


/**
 * 账户控制类
 * @author yy
 *
 */
@Api(tags={"acount"},value="账户",description="账户管理")
@RestController
@RequestMapping("/acount")
public class AcountController {
	@Resource
	private AcountService acountService;
	@Resource
	private RoleService roleService;
	@Resource
	private FinanceService financeService;
	@Resource
	private StringRedisTemplate stringRedisTemplate;
	@Resource
	private AliyunSms aliyunSms;
	@Resource
	private Sender sender;
	@Value("${myPugin.projectName}")
	String projectName;
	@Value("${spring.profiles.active}")
	String springProfilesActive;

	/**
	 * 账户分页浏览
	 * @param orderName 商品排序数据库字段
	 * @param orderWay 商品排序方法 asc升序 desc降序
	 * @return
	 */
	@ApiOperation(value = "账户列表", notes = "账户分页浏览")
/*	@ApiImplicitParams(
	{
	  @ApiImplicitParam(name="acountId",value="账户ID",dataType="integer", paramType = "query",example="1000"),
	  @ApiImplicitParam(name="spreadId",value="推广ID",dataType="integer", paramType = "query")}
	)*/
	@RequestMapping(value = "/list", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList browsePagingAcount(
			@RequestParam(value="acountId",required=false)Integer  acountId,
			@RequestParam(value="spreadId",required=false)Integer spreadId,
			@RequestParam(value="phone",required=false)String phone,
			@RequestParam(value="nickname",required=false)String nickname,
			@RequestParam(value="minScale",required=false)Double minScale,
			@RequestParam(value="maxScale",required=false)Double maxScale,
			@RequestParam(value="masterId",required=false)Integer masterId,
			@RequestParam(value="roleId",required=false)Integer roleId,
			@RequestParam(value="status",required=false)Integer status,
			@RequestParam(value="createDate",required=false)Date createDate,
			@RequestParam(value="loginDate",required=false)Date loginDate,
			@RequestParam(value="pageNum",defaultValue="1",required=false)int pageNum,
			@RequestParam(value="pageSize",defaultValue="10",required=false) int pageSize,
			@RequestParam(value="orderName",required=false,defaultValue="acount_id") String orderName,
			@RequestParam(value="orderWay",required=false,defaultValue="desc") String orderWay,HttpSession session)  {
			List<Acount> list = new ArrayList<Acount>();
			list= acountService.browsePagingAcount(acountId,spreadId,phone,nickname,minScale,maxScale,masterId,roleId,status,createDate,loginDate,pageNum, pageSize, orderName, orderWay);
			if(list.size()>0){
				return ResultUtil.getSlefSRSuccessList(list);
				
			}else{
				return ResultUtil.getSlefSRFailList(list);
			}
	}
	/**
	 * 数据账户分页浏览
	 * @param orderName 商品排序数据库字段
	 * @param orderWay 商品排序方法 asc升序 desc降序
	 * @return
	 */
	/*@RequestMapping(value = "/dataBySpreadId", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList browseDataPagingAcountBySpreadId(
			@RequestParam(value="spreadId")Integer spreadId,
			@RequestParam(value="createDate",required=false)Date createDate,
			@RequestParam(value="loginDate",required=false)Date loginDate,
			@RequestParam(value="pageNum",defaultValue="1",required=false)int pageNum,
			@RequestParam(value="pageSize",defaultValue="10",required=false) int pageSize,
			@RequestParam(value="orderName",required=false,defaultValue="acount_id") String orderName,
			@RequestParam(value="orderWay",required=false,defaultValue="desc") String orderWay,HttpSession session)  {
		List<SpreadAcountDTO> list = new ArrayList<SpreadAcountDTO>();
		list= acountService.browsePagingAcountBySpreadId(spreadId,createDate,loginDate,pageNum, pageSize, orderName, orderWay);
		
		if(list.size()>0){
			return ResultUtil.getSlefSRSuccessList(list);
			
		}else{
			return ResultUtil.getSlefSRFailList(list);
		}
	}*/

	/**
	 * 账户修改
	 * @return
	 */
	@RequestMapping(value = "/update", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResult updateAcount(@ModelAttribute Acount acount,HttpSession session)  {
		//账户已经存在
		if(acountService.loginAcount(acount.getPhone(), null,acount.getAcountId())!=null
				//||acountService.loginAcount(acount.getEmail(), null,acount.getAcountId())!=null
				){
			return ResultUtil.getSR(false);
		}
		//System.err.println(acount);
		boolean um = acountService.updateAcount(acount);
		session.setAttribute("acount", acount);
		//System.err.println(um);
		return ResultUtil.getSR(um);
	}
	/**
	 * 账户个人信息
	 * @param nickname 昵称
	 * @param icon 头像
	 * @return
	 */
	@RequestMapping(value = "/updateInfo", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList updateInfoAcount(@ModelAttribute Acount acount,HttpSession session)  {
		List<Object> list=new ArrayList<Object>();
		//判断是否存在
		Acount ac = acountService.loadAcount(acount.getAcountId());
		if(ac==null || ac.getAcountId()==null){
			list.add("账户不存在");
			return ResultUtil.getSlefSRFailList(list); 
		}
		acount.setPassword(ac.getPassword());
		acount.setPhone(ac.getPhone());
		acount.setEmail(ac.getEmail());
		acount.setMasterId(ac.getMasterId());
		acount.setOpenid(ac.getOpenid());
		acount.setUuid(ac.getUuid());
		acount.setStatus(ac.getStatus());
		acount.setSpreadId(ac.getSpreadId());
		acount.setScale(ac.getScale());
		acount.setRoleId(ac.getRoleId());
		boolean um = acountService.updateAcount(acount);
		if(um){
		session.setAttribute("acount", acount);
		list.add(acount);
		return ResultUtil.getSlefSRSuccessList(list); 
		}
		return ResultUtil.getSlefSRFailList(list); 
	}
	/**
	 * 账户修改密码
	 * @param adminName 手机号/电子邮箱
	 * @param password  新密码
	 * @param validCode 短信验证码
	 * @return
	 * @throws VerifyCodeErrorException 
	 */
	@RequestMapping(value = "/updatePassword", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList updateAcountPassword(
			@RequestParam("adminName")String adminName,
			@RequestParam("password")String password,
			@RequestParam(value="validCode",required=false) String validCode,
			HttpSession session) throws VerifyCodeErrorException  {
		List<Object> list=new ArrayList<Object>();
		//判断是否存在
		Acount ac = acountService.loginAcount(adminName, null, null);
		if(ac==null || ac.getAcountId()==null){
			list.add("账户不存在");
			return ResultUtil.getSlefSRFailList(list); 
		}
		//手机验证码
		BoundValueOperations<String, String> an = stringRedisTemplate.boundValueOps(projectName+"ValidCode"+adminName);
		String phoneValidCode= an.get();
		if(!phoneValidCode.equals(validCode)){
			throw new VerifyCodeErrorException();//验证码错误
		}
		ac.setPassword(MyDESutil.getMD5(password));
		boolean um = acountService.updateAcount(ac);
		if(um){
		list.add(ac);
		return ResultUtil.getSlefSRSuccessList(list);
		}
		return ResultUtil.getSlefSRFailList(list);
		
	}
	/**
	 * 账户修改真实姓名、手机号、微信号、支付宝账号
	 * @return
	 */
	@RequestMapping(value = "/updatePhone", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResult updatePhoneAcount(
			@RequestParam(value="acountId")Integer acountId,
			@RequestParam(value="phone",required=false)String phone,
			@RequestParam(value="wechat")String wechat,
			@RequestParam(value="realname")String realname,
			@RequestParam(value="alipay")String alipay,
			HttpSession session)  {
		Acount newa = acountService.loadAcount(acountId);
		if(!((Acount)session.getAttribute("acount")).getAcountId().equals(acountId)){
			return ResultUtil.getSR(false);
		}
		if(phone!=null&&!phone.equals("")){
		//微信登陆 账户已经存在
		if(((Acount)session.getAttribute("acount")).getPhone()==null||((Acount)session.getAttribute("acount")).getPhone().equals(""))
				{
				if(acountService.loginAcount(phone, null,null)!=null){
					//存在就错
					return ResultUtil.getSR(false);
				}else{
					newa.setPhone(phone);
					newa.setWechat(wechat);
					newa.setRealname(realname);
					newa.setAlipay(alipay);
				boolean um = acountService.updateAcount(newa);
				session.setAttribute("acount", newa);
				return ResultUtil.getSR(um);
				}
		}
		//手机号登陆 账户已经存在
		if(!phone.equals(((Acount)session.getAttribute("acount")).getPhone())){
			if(acountService.loginAcount(phone, null,null)==null){
				//不存在就错
				return ResultUtil.getSR(false);
			}else{
				newa.setPhone(phone);
				newa.setWechat(wechat);
				newa.setRealname(realname);
				newa.setAlipay(alipay);
			boolean um = acountService.updateAcount(newa);
			session.setAttribute("acount", newa);
			return ResultUtil.getSR(um);
			}
		}
		}
			newa.setPhone(null);
			newa.setWechat(wechat);
			newa.setRealname(realname);
			newa.setAlipay(alipay);
			boolean um = acountService.updateAcount(newa);
			session.setAttribute("acount", newa);
			return ResultUtil.getSR(um);
	}
	/**
	 * 账户增加
	 * @return 
	 */
	@RequestMapping(value = "/add", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResult addAcount(@ModelAttribute Acount acount, HttpSession session) {
		//账户已经存在
		if(acountService.loginAcount(acount.getPhone(), null,null)!=null ){
			return ResultUtil.getSR(false);
		}
		acount.setPassword(MyDESutil.getMD5(acount.getPassword()));
		boolean am = acountService.addAcount(acount);
		return ResultUtil.getSR(am);
	}
	/**
	 * 账户删除
	 * @return
	 */
	@ApiOperation(value = "账户删除", notes = "账户删除")
	@RequestMapping(value = "/delete", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResult delAcount(
			@RequestParam("acountId") Integer acountId,
			HttpSession session)  {
		boolean dm = acountService.delAcount(acountId);
		return ResultUtil.getSR(dm);
	}
	/**
	 * 账户浏览数量
	 * @return
	 */
	@RequestMapping(value = "/count", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody int countAll(
			@RequestParam(value="acountId",required=false)Integer acountId,
			@RequestParam(value="spreadId",required=false)Integer spreadId,
			@RequestParam(value="phone",required=false)String phone,
			@RequestParam(value="nickname",required=false)String nickname,
			@RequestParam(value="minScale",required=false)Double minScale,
			@RequestParam(value="maxScale",required=false)Double maxScale,
			@RequestParam(value="masterId",required=false)Integer masterId,
			@RequestParam(value="roleId",required=false)Integer roleId,
			@RequestParam(value="status",required=false)Integer status,
			@RequestParam(value="createDate",required=false)Date createDate,
			@RequestParam(value="loginDate",required=false)Date loginDate,
			HttpSession session)  {
		int count = acountService.countAll(acountId,spreadId,phone,nickname,minScale,maxScale,masterId,roleId,status,createDate,loginDate);
		return count;
	}
	/**
	 * 账户单个加载
	 * @return
	 */
	@RequestMapping(value = "/{acountId}", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList loadAcount(@PathVariable("acountId") Integer acountId,HttpSession session)  {
		List<Acount> list = new ArrayList<Acount>();
		Acount acount = acountService.loadAcount(acountId);
		if(acount!=null &&!acount.equals("")){
				list.add(acount);
				return ResultUtil.getSlefSRSuccessList(list);
			}else{
				return ResultUtil.getSlefSRFailList(list);
			}
	}
	/**
	 * 管理员登录
	 * @return
	 * @throws MySessionException 
	 */
	@RequestMapping(value = "/login", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList loginAcount(
			@RequestParam(value="adminName") String adminName,
			@RequestParam(value="password") String password,
			@RequestParam(value="random",required=false) String random,
			HttpSession session) throws MySessionException  {
		//1代验证码
		String ran= (String) session.getAttribute("random");
		List<Acount> list = new ArrayList<Acount>();
		if(!ran.equals(random)){
			return ResultUtil.getSlefSRFailList(list);
		}
		Acount acount = acountService.loginAcount(adminName, MyDESutil.getMD5(password),null);
		if(acount.getStatus().equals(1)){
			List<String> l1 = new ArrayList<String>();
			l1.add("账户锁定");
			return ResultUtil.getSlefSRFailList(l1);
		}else if(acount!=null&&!acount.equals("")){
			acount.setLoginDate(new Date());
			boolean b = acountService.updateAcount(acount);
			if(b){
			Integer roleId = acount.getRoleId();
			Role r = roleService.loadRole(roleId);
			if(r.getName().equals("用户")){
			throw new MySessionException();//没权限	
			}
			session.setAttribute("acount", acount);
			session.setAttribute("role", r);
			List<Finance> f = financeService.browsePagingFinance(null,acount.getAcountId(), 1, 1, "finance_id", "asc");
			session.setAttribute("finance", f.get(0));
			list.add(acount);
			return ResultUtil.getSlefSRSuccessList(list);
			}
		}
		return ResultUtil.getSlefSRFailList(list);
	}
	
	/**
	 * 手机验证码发送
	 * 
	 * @param adminName
	 * @param templateCode 1注册，2修改密码
	 * @return
	 * @throws RequestLimitException 
	 * @throws AcountIsExistException 
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/validCode", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody
	StateResultList validCode(
			@RequestParam("adminName")  String adminName,
			@RequestParam(value="templateCode",required=false,defaultValue="1")  Integer templateCode//默认注册
			)
					throws RequestLimitException, AcountIsExistException  {
		List<String> l=new ArrayList<String>();
		//注册，账户已经存在
		if(acountService.loginAcount(adminName, null,null)!=null && templateCode==1){
			throw new  AcountIsExistException();//账户已经存在异常
		}
		//修改密码
		if(acountService.loginAcount(adminName, null,null)==null && templateCode!=1){
			return ResultUtil.getSlefSRFailList(l);
		}
		if(!Pattern.matches(MyValidator.REGEX_PHONE,adminName)){
					return ResultUtil.getSlefSRFailList(l);
		}
		BoundValueOperations<String, String> an = stringRedisTemplate.boundValueOps(projectName+"ValidCode"+adminName);
		if(an.size()>0){
			throw new  RequestLimitException();//请求过快
		}
		
		Integer userValidCode=(int) (Math.random()*9000)+1000;
		an.set(userValidCode.toString(), 60, TimeUnit.SECONDS);
		if(springProfilesActive.equals("prod")){//生产环境
			//sMSInterface.SmsNumSend(String.valueOf(userValidCode), adminName,templateCode);
		 try {
			aliyunSms.sendSms(String.valueOf(userValidCode), adminName,templateCode);
		} catch (ClientException e) {
			 return ResultUtil.getSlefSRFailList(l);
		}
		}else if(springProfilesActive.equals("dev")){//本地环境
			l.add(userValidCode.toString());			
		}
		 return ResultUtil.getSlefSRSuccessList(l);
	}
	/**
	 * web用户登录
	 * @return
	 */
	@RequestMapping(value = "/weblogin", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList webLoginAcount(
			@RequestParam("adminName") String adminName,
			@RequestParam("password") String password,
			@RequestParam(value="random",required=false) String random,
			HttpSession session)  {
		//1代验证码
//		String ran= (String) session.getAttribute("random");
//		List<Acount> list = new ArrayList<Acount>();
//		if(!ran.equals(random)){
//			return ResultUtil.getSlefSRFailList(list);
//		}
		List<Object> list = new ArrayList<Object>();
		Acount acount = acountService.loginAcount(adminName, MyDESutil.getMD5(password),null);
		//自动登陆
		if(acount==null|| acount.equals("")){
			acount=acountService.loginAcount(adminName, password, null);
		}
		if(acount!=null&&!acount.equals("")&&(acount.getStatus().equals(0)||acount.getStatus().equals(2))){
			acount.setLoginDate(new Date());
			boolean b = acountService.updateAcount(acount);
			if(b){
			session.setAttribute("acount", acount);
			Integer roleId = acount.getRoleId();
			Role r = roleService.loadRole(roleId);
			session.setAttribute("role", r);
			List<Finance> f = financeService.browsePagingFinance(null,acount.getAcountId(), 1, 1, "finance_id", "asc");
			session.setAttribute("finance", f.get(0));
			list.add(acount);
			//return ResultUtil.getSlefSRSuccessList(list);
			return ResultUtil.getSlefSRSuccessList(list);
			}
		}else if(acount.getStatus().equals(1)){
			List<String> l1 = new ArrayList<String>();
			l1.add("账户锁定");
			return ResultUtil.getSlefSRFailList(l1);
		}
		return ResultUtil.getSlefSRFailList(list);
	}
	/**
	 * web用户注册
	 * @return
	 * @throws AcountIsExistException 
	 * @throws VerifyCodeErrorException 
	 */
	@RequestMapping(value = "/webregister", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList webRegisterAcount(
			@RequestParam("adminName") String adminName,
			@RequestParam("password") String password,
			HttpServletRequest request,
			@RequestParam(value="masterId",required=false) Integer masterId,
			@RequestParam(value="spreadId",required=false) Integer spreadId,
			@RequestParam(value="validCode",required=false) String validCode,
			HttpSession session) throws AcountIsExistException, VerifyCodeErrorException  {
		//手机验证码
		BoundValueOperations<String, String> an = stringRedisTemplate.boundValueOps(projectName+"ValidCode"+adminName);
		String phoneValidCode= an.get();
		List<Acount> list = new ArrayList<Acount>();
		if(!phoneValidCode.equals(validCode)){
			throw new VerifyCodeErrorException();//验证码错误
		}
		//判断是否存在
		Acount ac = acountService.loginAcount(adminName, null, null);
		if(ac!=null&&ac.getAcountId()!=null){
			throw new AcountIsExistException();
		}
			//新用户注册登录
				Acount acount=new Acount();
				
				//获取masterId
				if(masterId!=null&&!masterId.equals("")){
					acount.setMasterId(masterId);
				}
					acount.setScale(0.2);//默认0.2
					acount.setPhone(adminName);
					//acount.setNickname(adminName);
					acount.setPassword(MyDESutil.getMD5(password));
					acount.setLoginDate(new Date());
					List<Role> roleList = roleService.browsePagingRole(1, Integer.MAX_VALUE, "role_id","asc");
						for (Role role : roleList) {
							if(role!=null && role.getName().equals("用户") ){
								acount.setRoleId(role.getRoleId());
							}
						}
					acount.setLoginDate(new Date());
					acount.setSpreadId(spreadId);
					boolean b = acountService.addAcount(acount);
				if(b){
				session.setAttribute("acount", acount);
				Role role = roleService.loadRole(acount.getRoleId());
				session.setAttribute("role", role);
				List<Finance> f = financeService.browsePagingFinance(null,acount.getAcountId(), 1, 1, "finance_id", "asc");
				//System.out.println(f.get(0).toString());
				session.setAttribute("finance", f.get(0));
				list.add(acount);
				return ResultUtil.getSlefSRSuccessList(list);
				}else{
					return ResultUtil.getSlefSRFailList(list);
					
				}	
	}
	/**
	 * 微信登录
	 * @return
	 */
	@RequestMapping(value = "/wxlogin", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList WeixinBaseAcountLogin(
			@RequestParam("wxinfo") String wxinfo,
			HttpServletRequest request,
			@RequestParam(value="appVersion",required=false) String appVersion,
			@RequestParam(value="spreadId",required=false) Integer spreadId,
			HttpSession session)  {
			List<Object> list = new ArrayList<Object>();
			if(wxinfo==null||wxinfo.equals("")){
				
				return ResultUtil.getSlefSRFailList(list);
			}
		JSONObject jo = JSONObject.fromObject(wxinfo);
		String uuid = jo.getString("unionid");
		Acount acount = acountService.weixinBaseAcountLogin(uuid);
		
		//新用户注册登录
		if(acount==null||acount.equals("")){

			acount=new Acount();
			acount.setScale(0.2);
			//acount.setMasterId(bvomasterId);
			acount.setUuid(jo.getString("unionid"));
			if(jo.get("sex")!=null&&!jo.get("sex").equals("")){
				acount.setSex(new Integer(jo.get("sex").toString()));
			}else if(jo.get("gender").equals("男")){
				acount.setSex(1);
			}else if(jo.get("gender").equals("女")){
				acount.setSex(2);
			}else{
				acount.setSex(0);
			}
			
			if(jo.get("nickname")!=null&&!jo.get("nickname").equals("")){
				acount.setNickname(jo.getString("nickname"));
			}else if(jo.get("name")!=null&&!jo.get("name").equals("")){
				acount.setNickname(jo.getString("name"));
			}
			acount.setOpenid(jo.getString("openid"));
			if(jo.get("headimgurl")!=null&&!jo.get("headimgurl").equals("")){
				acount.setIcon(jo.getString("headimgurl"));
			}else if(jo.get("iconurl")!=null&&!jo.get("iconurl").equals("")){
				acount.setIcon(jo.getString("iconurl"));
			}
			acount.setCountry(jo.getString("country"));
			acount.setProvince(jo.getString("province"));
			acount.setCity(jo.getString("city"));
			acount.setLoginDate(new Date());
			acount.setRoleId(1004);
			acount.setSpreadId(spreadId);
			acountService.addAcount(acount);
		}
		acount.setLoginDate(new Date());
		acountService.updateAcount(acount);
		session.setAttribute("acount", acount);
		Role role = roleService.loadRole(acount.getRoleId());
		session.setAttribute("role", role);
		List<Finance> f = financeService.browsePagingFinance(null,acount.getAcountId(), 1, 1, "finance_id", "asc");
		//System.out.println(f.get(0).toString());
		session.setAttribute("finance", f.get(0));
		list.add(acount);
		return ResultUtil.getSlefSRSuccessList(list);
	}
	/**
	 * 登录
	 * @return
	 */
	@RequestMapping(value = "/islogin", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList isLoginAcount(
			HttpSession session)  {
		Acount acount = (Acount) session.getAttribute("acount");
		List<Acount> list = new ArrayList<Acount>();
		if(acount!=null && !acount.equals("")){
			list.add(acount);
			return ResultUtil.getSlefSRSuccessList(list);
		}
		return ResultUtil.getSlefSRFailList(list);
	}
	/**
	 * 登出
	 * @return
	 */
	@RequestMapping(value = "/loginout", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList loginoutAcount(
			HttpSession session)  {
		session.invalidate();
		return ResultUtil.getSlefSRSuccessList(null);
	}
	
}
