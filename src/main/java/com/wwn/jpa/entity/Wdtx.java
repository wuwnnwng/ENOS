package com.wwn.jpa.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@Table(name="t_wdtx",indexes = {@Index(name = "fbr",  columnList="fbr", unique = false),
                     @Index(name = "zdr", columnList="zdr", unique = false),
                     @Index(name = "laiyuan", columnList="laiyuan", unique = false),
                     @Index(name = "shixiang", columnList="shixiang", unique = false)})
 
@Entity
public class Wdtx implements Serializable {
	 
	/**
	 * 
	 */
	private static final long serialVersionUID = 6968011081028834488L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer wt_id;//我的提醒id
	 @Column(name="laiyuan",nullable=false)
    private String laiyuan;//来源  （发布者的所在部门）
    @Temporal(TemporalType.DATE)
    @Column()
    private Date riqi;//日期
    @Column(name="fbr",nullable=false)
    private String fbr;//发布人姓名
    @Column(name="zdr",nullable=false)
    private String zdr;//指定人姓名
    @Column(name="shixiang",nullable=false)
    private String shixiang;//事项
    @Column(name="status",columnDefinition = "int default 0")
    private Integer status ;//状态1：已经确定  0 ：未确认  
    @Column(name="stadec",nullable=false)
    private String stadec;//状态描述 1：已经确定  0 ：未确认
	public Integer getWt_id() {
		return wt_id;
	}
	public void setWt_id(Integer wt_id) {
		this.wt_id = wt_id;
	}
	public String getLaiyuan() {
		return laiyuan;
	}
	public void setLaiyuan(String laiyuan) {
		this.laiyuan = laiyuan;
	}
	public Date getRiqi() {
		return riqi;
	}
	public void setRiqi(Date riqi) {
		this.riqi = riqi;
	}
	public String getFbr() {
		return fbr;
	}
	public void setFbr(String fbr) {
		this.fbr = fbr;
	}
	public String getZdr() {
		return zdr;
	}
	public void setZdr(String zdr) {
		this.zdr = zdr;
	}
	public String getShixiang() {
		return shixiang;
	}
	public void setShixiang(String shixiang) {
		this.shixiang = shixiang;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getStadec() {
		return stadec;
	}
	public void setStadec(String stadec) {
		this.stadec = stadec;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "Wdtx [wt_id=" + wt_id + ", laiyuan=" + laiyuan + ", riqi=" + riqi + ", fbr=" + fbr + ", zdr=" + zdr
				+ ", shixiang=" + shixiang + ", status=" + status + ", stadec=" + stadec + "]";
	}
     
  	
 }





