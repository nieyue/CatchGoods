package com.nieyue.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 每日数据类
 * 
 * @author yy
 * 
 */
public class DailyData implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 每日数据id
	 */
	private Integer dailyDataId;
	
	/**
	 * pv数
	 */
	private Long pvs;
	/**
	 * uv数
	 */
	private Long uvs;
	
	/**
	 * ip数
	 */
	private Long ips;
	/**
	 * 阅读数
	 */
	private Long readingNumber;
	
	/**
	 *创建时间
	 */
	private Date createDate;
	/**
	 * 物品ID
	 */
	private Integer goodsId;
	/**
	 * 账户ID
	 */
	private Integer acountId;
	public DailyData() {
		super();
	}
	public DailyData(Integer dailyDataId, Long pvs, Long uvs, Long ips,Long readingNumber, Date createDate, Integer goodsId,Integer acountId) {
		super();
		this.dailyDataId = dailyDataId;
		this.pvs = pvs;
		this.uvs = uvs;
		this.ips = ips;
		this.readingNumber=readingNumber;
		this.createDate = createDate;
		this.goodsId = goodsId;
		this.acountId=acountId;
	}
	public Integer getDailyDataId() {
		return dailyDataId;
	}
	public void setDailyDataId(Integer dailyDataId) {
		this.dailyDataId = dailyDataId;
	}
	public Long getPvs() {
		return pvs;
	}
	public void setPvs(Long pvs) {
		this.pvs = pvs;
	}
	public Long getUvs() {
		return uvs;
	}
	public void setUvs(Long uvs) {
		this.uvs = uvs;
	}
	public Long getIps() {
		return ips;
	}
	public void setIps(Long ips) {
		this.ips = ips;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Integer getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Integer getAcountId() {
		return acountId;
	}
	public void setAcountId(Integer acountId) {
		this.acountId = acountId;
	}
	public Long getReadingNumber() {
		return readingNumber;
	}
	public void setReadingNumber(Long readingNumber) {
		this.readingNumber = readingNumber;
	}
	@Override
	public String toString() {
		return "DailyData [dailyDataId=" + dailyDataId + ", pvs=" + pvs + ", uvs=" + uvs + ", ips=" + ips
				+ ", readingNumber=" + readingNumber + ", createDate=" + createDate + ", goodsId=" + goodsId
				+ ", acountId=" + acountId + "]";
	}
		
}
