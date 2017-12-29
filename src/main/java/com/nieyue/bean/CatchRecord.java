package com.nieyue.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 抓取记录
 * @author yy
 *
 */
public class CatchRecord implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 抓取记录id
	 */
	private Integer catchRecordId;
	
	/**
	 * 抓取物品名称
	 */
	private String name;
	/**
	 * 封面图
	 */
	private String imgAddress;
	/**
	 * 消费金额
	 */
	private Double money;
	/**
	 * 创建时间
	 */
	private Date createDate;
	/**
	 * 更新时间
	 */
	private Date updateDate;
	/**
	 * 默认为1成功，2失败
	 */
	private Integer status;
	/**
	 * 物品id,外键
	 */
	private Integer goodsId;
	/**
	 * 账户id,外键
	 */
	private Integer acountId;
	
	public CatchRecord() {
		super();
	}


	public CatchRecord(Integer catchRecordId, String name, String imgAddress, Double money, Date createDate,
			Date updateDate, Integer status, Integer goodsId, Integer acountId) {
		super();
		this.catchRecordId = catchRecordId;
		this.name = name;
		this.imgAddress = imgAddress;
		this.money = money;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.status = status;
		this.goodsId = goodsId;
		this.acountId = acountId;
	}


	public Integer getCatchRecordId() {
		return catchRecordId;
	}

	public void setCatchRecordId(Integer catchRecordId) {
		this.catchRecordId = catchRecordId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImgAddress() {
		return imgAddress;
	}

	public void setImgAddress(String imgAddress) {
		this.imgAddress = imgAddress;
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

	public Integer getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}

	public Integer getAcountId() {
		return acountId;
	}

	public void setAcountId(Integer acountId) {
		this.acountId = acountId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}
	
}
