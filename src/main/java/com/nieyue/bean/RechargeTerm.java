package com.nieyue.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 充值项
 * @author yy
 *
 */
public class RechargeTerm implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 充值项id
	 */
	private Integer rechargeTermId;
	
	/**
	 * 附加标题
	 */
	private String title;
	/**
	 * 充值真钱
	 */
	private Double rechargeMoney;
	/**
	 * 赠送金额
	 */
	private Double giveMoney;
	/**
	 * 创建时间
	 */
	private Date createDate;
	/**
	 * 更新时间
	 */
	private Date updateDate;
	/**
	 * 状态0下架，默认为1上架
	 */
	private Integer status;
	public RechargeTerm(Integer rechargeTermId, String title, Double rechargeMoney, Double giveMoney, Date createDate,
			Date updateDate, Integer status) {
		super();
		this.rechargeTermId = rechargeTermId;
		this.title = title;
		this.rechargeMoney = rechargeMoney;
		this.giveMoney = giveMoney;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.status = status;
	}
	public RechargeTerm() {
		super();
	}
	public Integer getRechargeTermId() {
		return rechargeTermId;
	}
	public void setRechargeTermId(Integer rechargeTermId) {
		this.rechargeTermId = rechargeTermId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Double getRechargeMoney() {
		return rechargeMoney;
	}
	public void setRechargeMoney(Double rechargeMoney) {
		this.rechargeMoney = rechargeMoney;
	}
	public Double getGiveMoney() {
		return giveMoney;
	}
	public void setGiveMoney(Double giveMoney) {
		this.giveMoney = giveMoney;
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
}
