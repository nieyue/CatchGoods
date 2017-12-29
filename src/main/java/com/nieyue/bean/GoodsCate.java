package com.nieyue.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 物品类型
 * @author yy
 *
 */
public class GoodsCate implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 物品类型id
	 */
	private Integer goodsCateId;
	
	/**
	 * 物品类型名
	 */
	private String name;
	/**
	 * 更新时间
	 */
	private Date updateDate;

	public GoodsCate(Integer goodsCateId, String name, String duty, Date updateDate) {
		super();
		this.goodsCateId = goodsCateId;
		this.name = name;
		this.updateDate = updateDate;
	}
	public GoodsCate() {
		super();
	}
	public Integer getGoodsCateId() {
		return goodsCateId;
	}
	public void setGoodsCateId(Integer goodsCateId) {
		this.goodsCateId = goodsCateId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "GoodsCate [GoodsCateId=" + goodsCateId + ", name=" + name + ", updateDate=" + updateDate + "]";
	}
	
}
