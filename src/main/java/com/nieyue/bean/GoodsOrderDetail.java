package com.nieyue.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 物品订单详情
 * @author 聂跃
 * @date 2017年8月12日
 */
public class GoodsOrderDetail implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 物品订单详情Id
	 */
	private Integer goodsOrderDetailId;
	/**
	 * 物品名称
	 */
	private String goodsName;
	/**
	 * 封面
	 */
	private String imgAddress;
	/**
	 * 收货地址姓名
	 */
	private String name;
	/**
	 * 收货地址手机号
	 */
	private String phone;
	/**
	 * 收货地址
	 */
	private String address;
	/**
	 * 创建时间
	 */
	private Date createDate;
	/**
	 * 更新时间
	 */
	private Date updateDate;
	/**
	 * 订单状态，0已下单，1已发货
	 */
	private Integer status;
	
	/**
	 * 物品订单ID
	 */
	private Integer goodsOrderId;
	public GoodsOrderDetail() {
		super();
	}
	public GoodsOrderDetail(Integer goodsOrderDetailId, String goodsName, String imgAddress, String name, String phone,
			String address, Date createDate, Date updateDate, Integer status, Integer goodsOrderId) {
		super();
		this.goodsOrderDetailId = goodsOrderDetailId;
		this.goodsName = goodsName;
		this.imgAddress = imgAddress;
		this.name = name;
		this.phone = phone;
		this.address = address;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.status = status;
		this.goodsOrderId = goodsOrderId;
	}
	public Integer getGoodsOrderDetailId() {
		return goodsOrderDetailId;
	}
	public void setGoodsOrderDetailId(Integer goodsOrderDetailId) {
		this.goodsOrderDetailId = goodsOrderDetailId;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getImgAddress() {
		return imgAddress;
	}
	public void setImgAddress(String imgAddress) {
		this.imgAddress = imgAddress;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
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
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getGoodsOrderId() {
		return goodsOrderId;
	}
	public void setGoodsOrderId(Integer goodsOrderId) {
		this.goodsOrderId = goodsOrderId;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
