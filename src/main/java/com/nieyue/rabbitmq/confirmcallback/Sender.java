package com.nieyue.rabbitmq.confirmcallback;

import java.util.UUID;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nieyue.bean.CatchRecord;
import com.nieyue.bean.DataRabbitmqDTO;
import com.nieyue.bean.GoodsOrder;
import com.nieyue.bean.RechargeRecord;

/**
 * 消息生产者
 * @author 聂跃
 * @date 2017年5月31日
 */
@Component 
public class Sender  implements RabbitTemplate.ConfirmCallback{
	 private static final Logger LOGGER = LoggerFactory.getLogger(Sender.class);  
	/**
	 * 不能注入，否则没回调
	 */
	 private RabbitTemplate rabbitTemplate;
	 @Resource
	 private AmqpConfig amqpConfig;
	@Autowired  
	    public Sender(RabbitTemplate rabbitTemplate) {  
	        this.rabbitTemplate = rabbitTemplate;  
	        this.rabbitTemplate.setConfirmCallback(this); 
	    } 
	
	/**
	 * 物品订单
	 * @param orderRabbitmqDTO
	 */
	 public void sendGoodsOrder(GoodsOrder goodsOrder) { 
	        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
	        this.rabbitTemplate.convertAndSend(amqpConfig.GOODSORDER_DIRECT_EXCHANGE, amqpConfig.GOODSORDER_DIRECT_ROUTINGKEY, goodsOrder, correlationData);  
	     
	 }  
	 /**
	  * 充值支付
	  * @param orderRabbitmqDTO
	  */
	 public void sendRechargeRecord(RechargeRecord rechargeRecord) { 
		 CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
		 this.rabbitTemplate.convertAndSend(amqpConfig.RECHARGERECORD_DIRECT_EXCHANGE, amqpConfig.RECHARGERECORD_DIRECT_ROUTINGKEY, rechargeRecord, correlationData);  
		 
	 }  
	 /**
	  * 抓取记录（消费）
	  * @param orderRabbitmqDTO
	  */
	 public void sendCatchRecord(CatchRecord catchRecord) { 
		 CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
		 this.rabbitTemplate.convertAndSend(amqpConfig.CATCHRECORD_DIRECT_EXCHANGE, amqpConfig.CATCHRECORD_DIRECT_ROUTINGKEY, catchRecord, correlationData);  
		 
	 }  
	 
	 /**
	  * web物品阅读
	  * @param dataRabbitmqDTO
	  */
	 public void sendGoodsWebRead(DataRabbitmqDTO dataRabbitmqDTO) {  
		 CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
		 this.rabbitTemplate.convertAndSend(amqpConfig.GOODSWEBREAD_DIRECT_EXCHANGE, amqpConfig.GOODSWEBREAD_DIRECT_ROUTINGKEY, dataRabbitmqDTO, correlationData);  
	 }
	 /** 回调方法 */
	 @Override
	public void confirm(CorrelationData correlationData, boolean ack, String cause) {
	        if (ack) {
	        	LOGGER.info("消息发送确认成功");
	        } else {
	        	LOGGER.info("消息发送确认失败:" + cause);

	        }  
		
	}

}
