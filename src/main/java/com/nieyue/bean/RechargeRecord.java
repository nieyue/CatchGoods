package com.nieyue.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 充值记录
 * @author yy
 *
 */
public class RechargeRecord implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 充值记录id
	 */
	private Integer rechargeRecordId;
	
	/**
	 * 充值类型，1支付宝支付，2微信支付，3银联支付
	 */
	private Integer type;
	/**
	 * 金额
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
	 * 账户id,外键
	 */
	private Integer acountId;
	public RechargeRecord(Integer rechargeRecordId, Integer type, Double money, Date createDate, Date updateDate,
			Integer status, Integer acountId) {
		super();
		this.rechargeRecordId = rechargeRecordId;
		this.type = type;
		this.money = money;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.status = status;
		this.acountId = acountId;
	}
	public RechargeRecord() {
		super();
	}
	public Integer getRechargeRecordId() {
		return rechargeRecordId;
	}
	public void setRechargeRecordId(Integer rechargeRecordId) {
		this.rechargeRecordId = rechargeRecordId;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
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
	public Integer getAcountId() {
		return acountId;
	}
	public void setAcountId(Integer acountId) {
		this.acountId = acountId;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
