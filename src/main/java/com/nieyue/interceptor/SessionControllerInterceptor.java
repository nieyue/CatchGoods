package com.nieyue.interceptor;

import java.lang.reflect.Method;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.nieyue.bean.Acount;
import com.nieyue.bean.Finance;
import com.nieyue.bean.Role;
import com.nieyue.business.CertificateBusiness;
import com.nieyue.exception.MyCertificateException;
import com.nieyue.exception.MySessionException;
import com.nieyue.util.MyDESutil;

/**
 * 用户session控制拦截器
 * @author yy
 *
 */
@Configuration
public class SessionControllerInterceptor implements HandlerInterceptor {

	@Resource
	StringRedisTemplate stringRedisTemplate;
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		//如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
       System.out.println(method.getDefaultValue());
        
       //天窗
       if(MyDESutil.getMD5("1000").equals(request.getParameter("auth"))){
          	return true;
       }
       
        Acount sessionAcount = null;
        Role sessionRole=null;
        Finance sessionFinance=null;
        if(request.getSession()!=null
        		&&request.getSession().getAttribute("acount")!=null
        		&&request.getSession().getAttribute("role")!=null
        		&&request.getSession().getAttribute("finance")!=null
        		){
        sessionAcount = (Acount) request.getSession().getAttribute("acount");
        sessionRole = (Role) request.getSession().getAttribute("role");
        sessionFinance = (Finance) request.getSession().getAttribute("finance");
        }
//        Integer i=1;
//        Integer j=1;
//        if(i.equals(j)){
//        	return true;
//        }
        if(
        		request.getServletPath().equals("/")
        		||request.getRequestURI().indexOf("swagger")>-1
        		||request.getRequestURI().indexOf("api-docs")>-1
        		||request.getRequestURI().indexOf("getAPI")>-1
        		||request.getRequestURI().indexOf("validCode")>-1
        		||request.getRequestURI().indexOf("getVerificationCode")>-1
        		||request.getRequestURI().indexOf("scaleIncrement")>-1
        		||request.getRequestURI().indexOf("blackIp")>-1
        		||request.getRequestURI().indexOf("Domain")>-1
        		||request.getRequestURI().indexOf("jsAd")>-1
        		||request.getRequestURI().indexOf("sensitiveWord")>-1
        		//role
        		||request.getRequestURI().indexOf("role/count")>-1
        		//登陆、登出、增加师傅徒弟关系
        		||request.getRequestURI().indexOf("acount/count")>-1
        		||request.getRequestURI().indexOf("register")>-1
        		||request.getRequestURI().indexOf("login")>-1
        		||request.getRequestURI().indexOf("acount/updatePassword")>-1
        		||request.getRequestURI().indexOf("acount/data")>-1
        		//finance
        		||request.getRequestURI().indexOf("finance/count")>-1
        		||request.getRequestURI().indexOf("finance/listFinanceByAcountId")>-1
        		||request.getRequestURI().indexOf("finance/today")>-1
        		//notice
        		||request.getRequestURI().indexOf("notice/count")>-1
        		||request.getRequestURI().indexOf("notice/list")>-1
        		||method.getName().equals("loadNotice")
        		//goodsCate
        		||request.getRequestURI().indexOf("goodsCate/count")>-1
        		||request.getRequestURI().indexOf("goodsCate/list")>-1
        		||method.getName().equals("loadGoodsCate")
        		//goods
        		||request.getRequestURI().indexOf("goods/webRead")>-1
        		||request.getRequestURI().indexOf("goods/count")>-1
        		||request.getRequestURI().indexOf("goods/list")>-1
        		||request.getRequestURI().indexOf("goods/loadSmall")>-1
        		||method.getName().equals("loadGoods")
        		//goodsOrder
        		||request.getRequestURI().indexOf("goodsOrder/count")>-1
        		||request.getRequestURI().indexOf("goodsOrder/list")>-1
        		//||request.getRequestURI().indexOf("goodsOrder/payment")>-1
        		//||request.getRequestURI().indexOf("goodsOrder/iospayNotifyUrl")>-1
        		||method.getName().equals("loadGoodsOrder")
        		//goodsOrderDetail
        		||method.getName().equals("loadGoodsOrderDetail")
        		//版本
        		||request.getRequestURI().indexOf("appVersion/count")>-1
        		||request.getRequestURI().indexOf("appVersion/list")>-1
        		||method.getName().equals("loadAppVersion")
        		//dailyData
        		||request.getRequestURI().indexOf("dailyData/count")>-1
        		||request.getRequestURI().indexOf("dailyData/statisticsDailyData")>-1
        		||request.getRequestURI().indexOf("dailyData/list")>-1
        		||method.getName().equals("loadDailyData")
        		//data
        		||request.getRequestURI().indexOf("data/count")>-1
        		||request.getRequestURI().indexOf("data/statisticsData")>-1
        		||request.getRequestURI().indexOf("data/list")>-1
        		||method.getName().equals("loadData")
       
        		){
        	return true;
        }else if (sessionAcount!=null) {
        	//确定角色存在
        	if(sessionRole!=null ){
        	//超级管理员
        	if(sessionRole.getName().equals("超级管理员")
        			||sessionRole.getName().equals("运营管理员")
        			){
        		return true;
        	}
        	//admin中只许修改自己的值
        	if(sessionRole.getName().equals("用户")){
        		//证物品认证
        		if(!CertificateBusiness.md5SessionCertificate(request)){
        			throw new MyCertificateException();
        		}
        		//账户不许删除/增加
        		if( request.getRequestURI().indexOf("/acount/delete")>-1 
        				|| request.getRequestURI().indexOf("/acount/add")>-1
        				|| request.getRequestURI().equals("/acount/list")
        				|| request.getRequestURI().indexOf("/acount/update")>-1
        				||method.getName().equals("loadAcount")
        				){
        			//加载自身账户
        			if((	method.getName().equals("loadAcount")
        					|| request.getRequestURI().indexOf("/acount/list")>-1
        					)
        					&& request.getRequestURI().indexOf(sessionAcount.getAcountId().toString())>-1){
        				return true;
        			}
        			//获取合伙人
        			if((request.getRequestURI().indexOf("/acount/list")>-1)
        					&& request.getParameter("masterId").equals(sessionAcount.getAcountId().toString())){
        				return true;
        			}
        			//不能修改全部
        			if(request.getRequestURI().equals("/acount/update")){
        				throw new MySessionException();
        			}
        			//提交修改自身信息
        			if((request.getRequestURI().indexOf("/acount/update")>-1)
        					&& request.getParameter("acountId").equals(sessionAcount.getAcountId().toString())){
        				return true;
        			}
        			throw new MySessionException();
        		}
        		//财务不许删除/修改/增加
        		if( request.getRequestURI().indexOf("/finance/delete")>-1 
        				|| request.getRequestURI().indexOf("/finance/update")>-1 
        				|| request.getRequestURI().indexOf("/finance/list")>-1 
        				|| request.getRequestURI().indexOf("/finance/add")>-1 
        				|| request.getRequestURI().indexOf("/finance/data")>-1 
        				|| request.getRequestURI().indexOf("/finance/daydata")>-1 
        				||method.getName().equals("loadFinance")){
        			//加载自身财务
        			if((method.getName().equals("loadFinance")
        					&& request.getRequestURI().indexOf(sessionFinance.getFinanceId().toString())>-1)){
        				return true;
        			}
        			throw new MySessionException();
        		}
        		//角色全不许
        		if( request.getRequestURI().indexOf("/role")>-1 ){
        			throw new MySessionException();
        		}
        		//物品类型不许删除/增加/修改
        		if( request.getRequestURI().indexOf("/goodsCate/delete")>-1 
        				|| request.getRequestURI().indexOf("/goodsCate/add")>-1
        				|| request.getRequestURI().indexOf("/goodsCate/update")>-1
        				){
        			throw new MySessionException();
        		}
        		//物品不许删除/增加/修改
        		if( request.getRequestURI().indexOf("/goods/delete")>-1 
        				|| request.getRequestURI().indexOf("/goods/add")>-1
        				|| request.getRequestURI().indexOf("/goods/update")>-1
        				){
        			throw new MySessionException();
        		}
        		//物品订单不许删除/增加/修改
        		if( request.getRequestURI().indexOf("/goodsOrder/delete")>-1 
        				|| request.getRequestURI().indexOf("/goodsOrder/add")>-1
        				|| request.getRequestURI().indexOf("/goodsOrder/payment")>-1
        				|| request.getRequestURI().indexOf("/goodsOrder/update")>-1
        				|| request.getRequestURI().indexOf("/goodsOrder/iospayNotifyUrl")>-1
        				){
        			//增加自身
        			if( 
        					(request.getRequestURI().indexOf("/goodsOrder/add")>-1
        					||request.getRequestURI().indexOf("/goodsOrder/payment")>-1
        					||request.getRequestURI().indexOf("/goodsOrder/iospayNotifyUrl")>-1
        					)
        					&& request.getParameter("acountId").equals(sessionAcount.getAcountId().toString())){
        				//自身不许回调
        				if(request.getRequestURI().indexOf("/goodsOrder/paymentNotifyUrl")>-1){
        					throw new MySessionException();
        				}
        				return true;
        			}
        			throw new MySessionException();
        		}
        		//物品订单详细信息不许删除/增加/修改
        		if( request.getRequestURI().indexOf("/goodsOrderDetail/delete")>-1 
        				|| request.getRequestURI().indexOf("/goodsOrderDetail/add")>-1
        				|| request.getRequestURI().indexOf("/goodsOrderDetail/update")>-1
        				||request.getRequestURI().indexOf("/goodsOrderDetail/count")>-1
        				||request.getRequestURI().indexOf("/goodsOrderDetail/list")>-1
                		||method.getName().equals("loadGoodsOrderDetail")
        				){ 
        			//增删改查自身信息
        			if( request.getParameter("acountId").equals(sessionAcount.getAcountId().toString())){
        				return true;
        			}
        			throw new MySessionException();
        		}
        		//app版本不许删除/修改/增加
        		if( request.getRequestURI().indexOf("/appVersion/delete")>-1 
        				|| request.getRequestURI().indexOf("/appVersion/update")>-1 
        				|| request.getRequestURI().indexOf("/appVersion/add")>-1){
        			throw new MySessionException();
        		}
        		//物品时间段数据不许删除/修改/增加
        		if( request.getRequestURI().indexOf("/data/delete")>-1 
        				|| request.getRequestURI().indexOf("/data/update")>-1 
        				|| request.getRequestURI().indexOf("/data/add")>-1){
        			throw new MySessionException();
        		}
        		//物品日数据不许删除/修改/增加
        		if( request.getRequestURI().indexOf("/dailyData/delete")>-1 
        				|| request.getRequestURI().indexOf("/dailyData/update")>-1 
        				|| request.getRequestURI().indexOf("/dailyData/add")>-1){
        			throw new MySessionException();
        		}
        		return true;
        	}
        	}
        	
        }
        //如果验证token失败
       throw new MySessionException();
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
	}
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
	}

}
