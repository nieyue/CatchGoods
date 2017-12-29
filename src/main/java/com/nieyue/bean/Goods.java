package com.nieyue.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 物品
 * @author yy
 *
 */
public class Goods implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 物品id
	 */
	private Integer goodsId;
	/**
	 * 物品名称
	 */
	private String goodsName;
	/**
	 *封面
	 */
	private String imgAddress;
	/**
	 *摄像头个数
	 */
	private Integer cameraNumber;
	/**
	 *单次收费
	 */
	private Double singleCharge;
	/**
	 *免费已玩次数
	 */
	private Integer freeNumber;
	/**
	 *收费已玩次数
	 */
	private Integer chargeNumber;
	/**
	 *总收费
	 */
	private Double totalCharge;
	/**
	 *推荐，默认0不推，1封推，2精推，3优推
	 */
	private Integer recommend;
	/**
	 *pv
	 */
	private Integer pvs;
	/**
	 *uv
	 */
	private Integer uvs;
	/**
	 *ip
	 */
	private Integer ips;
	/**
	 *阅读数
	 */
	private Integer readingNumber;
	/**
	 *0故障,1空闲中，2使用中
	 */
	private Integer status;
	/**
	 *物品类型id,外键
	 */
	private Integer goodsCateId;
	/**
	 * 物品创建时间
	 */
	private Date createDate;
	/**
	 * 更新时间
	 */
	private Date updateDate;
	/**
	 * 物品类型
	 */
	private GoodsCate goodsCate;

	public Goods() {
		super();
	}

	public Goods(Integer goodsId, String goodsName, String imgAddress, Integer cameraNumber, Double singleCharge,
			Integer freeNumber, Integer chargeNumber, Double totalCharge, Integer recommend, Integer pvs, Integer uvs,
			Integer ips, Integer readingNumber, Integer status, Integer goodsCateId, Date createDate, Date updateDate,
			GoodsCate goodsCate) {
		super();
		this.goodsId = goodsId;
		this.goodsName = goodsName;
		this.imgAddress = imgAddress;
		this.cameraNumber = cameraNumber;
		this.singleCharge = singleCharge;
		this.freeNumber = freeNumber;
		this.chargeNumber = chargeNumber;
		this.totalCharge = totalCharge;
		this.recommend = recommend;
		this.pvs = pvs;
		this.uvs = uvs;
		this.ips = ips;
		this.readingNumber = readingNumber;
		this.status = status;
		this.goodsCateId = goodsCateId;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.goodsCate = goodsCate;
	}

	public Integer getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
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

	public Integer getCameraNumber() {
		return cameraNumber;
	}

	public void setCameraNumber(Integer cameraNumber) {
		this.cameraNumber = cameraNumber;
	}

	public Double getSingleCharge() {
		return singleCharge;
	}

	public void setSingleCharge(Double singleCharge) {
		this.singleCharge = singleCharge;
	}

	public Integer getFreeNumber() {
		return freeNumber;
	}

	public void setFreeNumber(Integer freeNumber) {
		this.freeNumber = freeNumber;
	}

	public Integer getChargeNumber() {
		return chargeNumber;
	}

	public void setChargeNumber(Integer chargeNumber) {
		this.chargeNumber = chargeNumber;
	}

	public Double getTotalCharge() {
		return totalCharge;
	}

	public void setTotalCharge(Double totalCharge) {
		this.totalCharge = totalCharge;
	}

	public Integer getRecommend() {
		return recommend;
	}

	public void setRecommend(Integer recommend) {
		this.recommend = recommend;
	}

	public Integer getPvs() {
		return pvs;
	}

	public void setPvs(Integer pvs) {
		this.pvs = pvs;
	}

	public Integer getUvs() {
		return uvs;
	}

	public void setUvs(Integer uvs) {
		this.uvs = uvs;
	}

	public Integer getIps() {
		return ips;
	}

	public void setIps(Integer ips) {
		this.ips = ips;
	}

	public Integer getReadingNumber() {
		return readingNumber;
	}

	public void setReadingNumber(Integer readingNumber) {
		this.readingNumber = readingNumber;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getGoodsCateId() {
		return goodsCateId;
	}

	public void setGoodsCateId(Integer goodsCateId) {
		this.goodsCateId = goodsCateId;
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

	public GoodsCate getGoodsCate() {
		return goodsCate;
	}

	public void setGoodsCate(GoodsCate goodsCate) {
		this.goodsCate = goodsCate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
}
