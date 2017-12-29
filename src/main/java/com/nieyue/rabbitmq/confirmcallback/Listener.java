package com.nieyue.rabbitmq.confirmcallback;

import java.io.IOException;

import javax.annotation.Resource;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.nieyue.bean.Acount;
import com.nieyue.bean.CatchRecord;
import com.nieyue.bean.Goods;
import com.nieyue.bean.GoodsOrder;
import com.nieyue.bean.RechargeRecord;
import com.nieyue.bean.DailyData;
import com.nieyue.bean.Data;
import com.nieyue.bean.DataRabbitmqDTO;
import com.nieyue.business.AcountBusiness;
import com.nieyue.service.GoodsService;
import com.nieyue.service.RechargeRecordService;
import com.nieyue.service.CatchRecordService;
import com.nieyue.service.DailyDataService;
import com.nieyue.service.DataService;
import com.nieyue.service.GoodsOrderDetailService;
import com.nieyue.service.GoodsOrderService;
import com.nieyue.util.DateUtil;
import com.rabbitmq.client.Channel;


/**
 * 消息监听者
 * @author 聂跃
 * @date 2017年5月31日
 */
@Configuration  
public class Listener {
	@Resource
	private StringRedisTemplate stringRedisTemplate;
	@Resource
	private AcountBusiness acountBusiness;
	@Resource
	private GoodsService goodsService;
	@Resource
	private RechargeRecordService rechargeRecordService;
	@Resource
	private CatchRecordService catchRecordService;
	@Resource
	private GoodsOrderService goodsOrderService;
	@Resource
	private GoodsOrderDetailService goodsOrderDetailService;
	@Resource
	private DailyDataService dailyDataService;
	@Resource
	private DataService dataService;
	@Resource
	private Sender sender;
	@Value("${myPugin.projectName}")
	String projectName;
		/**
		 * 物品订单
		 * @param channel
		 * @param orderRabbitmqDTO
		 * @param message
		 * @throws IOException
		 */
	    @RabbitListener(queues="${myPugin.rabbitmq.GOODSORDER_DIRECT_QUEUE}") 
	    public void goodsOrder(Channel channel, GoodsOrder goodsOrder,Message message) throws IOException   {
	           try {
	        	 boolean b = goodsOrderService.addGoodsOrder(goodsOrder);
	        	 if(b){ 
	        	 channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
	        	 }
	        } catch (Exception e) {
				 try {
					channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,false);
				} catch (IOException e1) {
					channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,false);
					
					e1.printStackTrace();
				}
				//e.printStackTrace();
			} //确认消息成功消费 
	    }     
	    
	    /**
		 * 充值支付
		 * @param channel
		 * @param orderRabbitmqDTO
		 * @param message
		 * @throws IOException
		 */
	    @RabbitListener(queues="${myPugin.rabbitmq.RECHARGERECORD_DIRECT_QUEUE}") 
	    public void rechargeRecord(Channel channel,RechargeRecord rechargeRecord,Message message) throws IOException   {
	           try {
	        	   boolean b = rechargeRecordService.addRechargeRecord(rechargeRecord);
	        	   if(b){
	        	   channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
	        	   }
			} catch (Exception e) {
				 try {
					channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,false);
				} catch (IOException e1) {
					channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,false);
					
					e1.printStackTrace();
				}
				//e.printStackTrace();
			} //确认消息成功消费 
	    }     
	    /**
	     * 抓取记录（消费
	     * @param channel
	     * @param orderRabbitmqDTO
	     * @param message
	     * @throws IOException
	     */
	    @RabbitListener(queues="${myPugin.rabbitmq.CATCHRECORD_DIRECT_QUEUE}") 
	    public void catchRecord(Channel channel,CatchRecord catchRecord,Message message) throws IOException   {
	    	try {
	    		boolean b = catchRecordService.addCatchRecord(catchRecord);
	    		if(b){
	    			channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
	    		}
	    	} catch (Exception e) {
	    		try {
	    			channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,false);
	    		} catch (IOException e1) {
	    			channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,false);
	    			
	    			e1.printStackTrace();
	    		}
	    		//e.printStackTrace();
	    	} //确认消息成功消费 
	    }     
	    
	    /**
	     * web物品阅读 ip==阅读
	     * @param channel
	     * @param dataRabbitmqDTO
	     * @param message
	     * @throws IOException
	     */
		    @RabbitListener(queues="${myPugin.rabbitmq.GOODSWEBREAD_DIRECT_QUEUE}") 
		    public void GoodsWebRead(Channel channel, DataRabbitmqDTO dataRabbitmqDTO,Message message) throws IOException   {
		           try {
		        	  /**
		        	   * 判断是否存在
		        	   */
		        	 //如果物品不予统计  
		       		Goods inGoods = goodsService.loadSmallGoods(dataRabbitmqDTO.getGoodsId());
		       		if(inGoods==null||inGoods.equals("")){
		       		 channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
		       		 return;
		       		}
		       		//如果账户不存在则用，1000
		       		Acount readinacount = acountBusiness.getAcountByAcountId(dataRabbitmqDTO.getAcountId());//阅读账户
		       		Acount inacount = acountBusiness.getAcountByAcountId(1000);//平台账户
		       		if(readinacount==null||readinacount.equals("")){
		       			readinacount = inacount;
		       		}
		     	   		/**
		        	    * 统计data
		        	    */
		       			//统计当日当前物品每日ip(总统计，做区别ip,保证不同acountId同一IP)
		        	   BoundSetOperations<String, String> bsodatips = stringRedisTemplate.boundSetOps(projectName+"GoodsId"+dataRabbitmqDTO.getGoodsId()+"Data"+DateUtil.getImgDir()+"Ips");
		        	   int isAddIp=0;//默认没增加
		        	   int oldIPSize = bsodatips.members().size();
		        	   bsodatips.add(dataRabbitmqDTO.getIp());//总ip
		        	   int nowIPSize = bsodatips.members().size();
		        	   if(nowIPSize>oldIPSize){
		        		   isAddIp=1;//增加了
		        	   }
		        	   //统计当日当前人的当前物品每日ip
		        	   BoundSetOperations<String, String> bsodataips = stringRedisTemplate.boundSetOps(projectName+"AcountId"+readinacount.getAcountId()+"GoodsId"+dataRabbitmqDTO.getGoodsId()+"Data"+DateUtil.getImgDir()+"Ips");
				        	   if(isAddIp==1){
				        	   bsodataips.add(dataRabbitmqDTO.getIp());//ip存入redis数据库
				        	   //bsodataips.expire(DateUtil.currentToEndTime(), TimeUnit.SECONDS);//按天计算有用
				        	   }
		     	  	//三段时间数据
		   			Data realdata=new Data();
		   			//时间是3段:0-8,8-16,16-24
		   			realdata.setCreateDate(DateUtil.getDayPeriod(3));
		   			realdata.setGoodsId(dataRabbitmqDTO.getGoodsId());
		   			realdata.setAcountId(readinacount.getAcountId());
		   			dataService.saveOrUpdateData(realdata,dataRabbitmqDTO.getUv(), isAddIp,isAddIp);
		   			//日数据
		   			DailyData realdailydata=new DailyData();
		   			//时间是日
		   			realdailydata.setCreateDate(DateUtil.getStartTime());
		   			realdailydata.setGoodsId(dataRabbitmqDTO.getGoodsId());
		   			realdailydata.setAcountId(readinacount.getAcountId());
		   			dailyDataService.saveOrUpdateDailyData(realdailydata, dataRabbitmqDTO.getUv(), isAddIp, isAddIp);
		        	  /**
		        	   * 更新物品
		        	   */
		        	   //当前物品
		        	   Goods goods = goodsService.loadSmallGoods(dataRabbitmqDTO.getGoodsId());
		        	   goods.setReadingNumber(goods.getReadingNumber()+isAddIp);
		        	   goods.setPvs(goods.getPvs()+1);
		        	   goods.setUvs(goods.getUvs()+dataRabbitmqDTO.getUv());
		        	   goods.setIps(goods.getIps()+isAddIp);
		        	   goodsService.updateGoods(goods);//更新物品数据
		        	  channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					 try {
						channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,false);
					} catch (IOException e1) {
						channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,false);
						
						e1.printStackTrace();
					}
					//e.printStackTrace();
				} //确认消息成功消费 
		    }  
}
