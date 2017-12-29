package com.nieyue.rabbitmq.confirmcallback;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
/**
 * 配置
 * @author 聂跃
 * @date 2017年5月31日
 */
@Configuration  
public class AmqpConfig {
	/**
	 * 物品订单
	 */
	@Value("${myPugin.rabbitmq.GOODSORDER_DIRECT_EXCHANGE}")
	public  String GOODSORDER_DIRECT_EXCHANGE ;  
	@Value("${myPugin.rabbitmq.GOODSORDER_DIRECT_ROUTINGKEY}")
	public String GOODSORDER_DIRECT_ROUTINGKEY;  
	@Value("${myPugin.rabbitmq.GOODSORDER_DIRECT_QUEUE}")
	public  String GOODSORDER_DIRECT_QUEUE; 
	/**
	 * 充值记录
	 */
	@Value("${myPugin.rabbitmq.RECHARGERECORD_DIRECT_EXCHANGE}")
    public  String RECHARGERECORD_DIRECT_EXCHANGE ;  
	@Value("${myPugin.rabbitmq.RECHARGERECORD_DIRECT_ROUTINGKEY}")
    public String RECHARGERECORD_DIRECT_ROUTINGKEY;  
	@Value("${myPugin.rabbitmq.RECHARGERECORD_DIRECT_QUEUE}")
    public  String RECHARGERECORD_DIRECT_QUEUE; 
	/**
	 *抓取记录（消费）
	 */
	@Value("${myPugin.rabbitmq.CATCHRECORD_DIRECT_EXCHANGE}")
	public  String CATCHRECORD_DIRECT_EXCHANGE ;  
	@Value("${myPugin.rabbitmq.CATCHRECORD_DIRECT_ROUTINGKEY}")
	public String CATCHRECORD_DIRECT_ROUTINGKEY;  
	@Value("${myPugin.rabbitmq.CATCHRECORD_DIRECT_QUEUE}")
	public  String CATCHRECORD_DIRECT_QUEUE; 
	
	/**
	 * web物品阅读
	 */
	@Value("${myPugin.rabbitmq.GOODSWEBREAD_DIRECT_EXCHANGE}")
	public  String GOODSWEBREAD_DIRECT_EXCHANGE ;  
	@Value("${myPugin.rabbitmq.GOODSWEBREAD_DIRECT_ROUTINGKEY}")
	public String GOODSWEBREAD_DIRECT_ROUTINGKEY; 
	@Value("${myPugin.rabbitmq.GOODSWEBREAD_DIRECT_QUEUE}")
	public  String GOODSWEBREAD_DIRECT_QUEUE; 
	
	
    @Autowired
    ConnectionFactory  connectionFactory ;
    /** 因为要设置回调类，所以应是prototype类型，如果是singleton类型，则回调类为最后一次设置 */  
    @Bean  
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)  
    public RabbitTemplate rabbitTemplate() {  
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);  
        return rabbitTemplate;  
    } 
    /** 
     *物品订单
     */  
    /*
     * 设置交换机类型
     */   
    @Bean  
    public DirectExchange goodsOrderDirectExchange() {  
        /** 
         * DirectExchange:按照routingkey分发到指定队列 
         * TopicExchange:多关键字匹配 
         * FanoutExchange: 将消息分发到所有的绑定队列，无routingkey的概念 
         * HeadersExchange ：通过添加属性key-value匹配 
         */  
    	DirectExchange de = new DirectExchange(GOODSORDER_DIRECT_EXCHANGE);
        return de;
    }
    /*
     * 设置队列
     */
    @Bean  
    public Queue goodsOrderDirectQueue() {  
        return new Queue(GOODSORDER_DIRECT_QUEUE);  
    }
    /*
     * 设置绑定
     */
    @Bean  
    public Binding goodsOrderDirectBinding() {  
        /** 将队列绑定到交换机 */  
        return BindingBuilder.bind(goodsOrderDirectQueue()).to(goodsOrderDirectExchange()).with(GOODSORDER_DIRECT_ROUTINGKEY);  
    } 
    
    /** 
     *充值支付
     */  
    /*
     * 设置交换机类型
     */   
    @Bean  
    public DirectExchange rechargeRecordDirectExchange() {  
        /** 
         * DirectExchange:按照routingkey分发到指定队列 
         * TopicExchange:多关键字匹配 
         * FanoutExchange: 将消息分发到所有的绑定队列，无routingkey的概念 
         * HeadersExchange ：通过添加属性key-value匹配 
         */  
    	DirectExchange de = new DirectExchange(RECHARGERECORD_DIRECT_EXCHANGE);
        return de;
    }
    /*
     * 设置队列
     */
    @Bean  
    public Queue rechargeRecordDirectQueue() {  
        return new Queue(RECHARGERECORD_DIRECT_QUEUE);  
    }
    /*
     * 设置绑定
     */
    @Bean  
    public Binding rechargeRecordDirectBinding() {  
        /** 将队列绑定到交换机 */  
        return BindingBuilder.bind(rechargeRecordDirectQueue()).to(rechargeRecordDirectExchange()).with(RECHARGERECORD_DIRECT_ROUTINGKEY);  
    } 
    
    
    /** 
     *抓取记录（消费）
     */  
    /*
     * 设置交换机类型
     */   
    @Bean  
    public DirectExchange catchRecordDirectExchange() {  
    	DirectExchange de = new DirectExchange(CATCHRECORD_DIRECT_EXCHANGE);
    	return de;
    }
    
    // 设置队列
    @Bean  
    public Queue catchRecordDirectQueue() {  
    	return new Queue(CATCHRECORD_DIRECT_QUEUE);  
    }
    
     // 设置绑定
    @Bean  
    public Binding catchRecordDirectBinding() {  
    	/** 将队列绑定到交换机 */  
    	return BindingBuilder.bind(catchRecordDirectQueue()).to(catchRecordDirectExchange()).with(CATCHRECORD_DIRECT_ROUTINGKEY);  
    } 
    
    
    /** 
     *物品web阅读
     */  
    /*
     * 设置交换机类型
     */   
    @Bean  
    public DirectExchange goodsWebReadDirectExchange() {  
    	DirectExchange de = new DirectExchange(GOODSWEBREAD_DIRECT_EXCHANGE);
    	return de;
    }
    
    // 设置队列
    @Bean  
    public Queue goodsWebReadDirectQueue() {  
    	return new Queue(GOODSWEBREAD_DIRECT_QUEUE);  
    }
    
    // 设置绑定
    @Bean  
    public Binding goodsWebReadDirectBinding() {  
    	/** 将队列绑定到交换机 */  
    	return BindingBuilder.bind(goodsWebReadDirectQueue()).to(goodsWebReadDirectExchange()).with(GOODSWEBREAD_DIRECT_ROUTINGKEY);  
    } 

}
