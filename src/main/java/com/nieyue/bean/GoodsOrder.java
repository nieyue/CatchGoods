package com.nieyue.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 物品订单
 * @author yy
 *
 */
public class GoodsOrder implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 物品订单id
	 */
	private Integer goodsOrderId;
	
	/**
	 * 订单号
	 */
	private String orderNumber;
	/**
	 * 创建时间
	 */
	private Date createDate;
	/**
	 * 更新时间
	 */
	private Date updateDate;
	/**
	 * 下单id,外键
	 */
	private Integer acountId;
	/**
	 * 多个订单物品
	 */
	private List<GoodsOrderDetail> goodsOrderDetailList;
	
	public GoodsOrder() {
		super();
	}

	public GoodsOrder(Integer goodsOrderId, String orderNumber, Date createDate, Date updateDate, Integer acountId,
			List<GoodsOrderDetail> goodsOrderDetailList) {
		super();
		this.goodsOrderId = goodsOrderId;
		this.orderNumber = orderNumber;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.acountId = acountId;
		this.goodsOrderDetailList = goodsOrderDetailList;
	}

	public Integer getGoodsOrderId() {
		return goodsOrderId;
	}

	public void setGoodsOrderId(Integer goodsOrderId) {
		this.goodsOrderId = goodsOrderId;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Integer getAcountId() {
		return acountId;
	}

	public void setAcountId(Integer acountId) {
		this.acountId = acountId;
	}

	public List<GoodsOrderDetail> getGoodsOrderDetailList() {
		return goodsOrderDetailList;
	}

	public void setGoodsOrderDetailList(List<GoodsOrderDetail> goodsOrderDetailList) {
		this.goodsOrderDetailList = goodsOrderDetailList;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
