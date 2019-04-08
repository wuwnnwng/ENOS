package com.wwn.jpa.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@Table(name="t_wcgl")
 
@Entity
public class Wcgl implements Serializable {
	 
 
  

	/**
	 * 
	 */
	private static final long serialVersionUID = 3812309979420243080L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer wc_id;//外出管理id
	
	@Column(name="wcr",nullable=false)
    private String wcr;//外出人
	
	@Column(name="wcshr" )
    private String wcshr;//外出管理审核人
	
    @Temporal(TemporalType.DATE)
    @Column(name="wcksrq",nullable=false)
    private Date wcksrq;//外出管理开始日期
    
    @Temporal(TemporalType.DATE)
    @Column(name="wcjsrq",nullable=false)
    private Date wcjsrq;//外出管理结束日期
    
    @Temporal(TemporalType.DATE)
    @Column(name="wcshrq" )
    private Date wcshrq;//外出管理审核日期
    
    @Column(name="wcsy",nullable=false)
    private String wcsy;//外出事由
	
    @Column(name="wcglxm",nullable=false)
    private String wcglxm;//外出管理关联项目
    
    
    @Column(name="wcrbmmc",nullable=false)
    private String wcrbmmc ;//外出人所在部门
    
    @Column(name="wcshzt")
    private String wcshzt ;//外出管理审核状态：已审核  ：未审核

	public Integer getWc_id() {
		return wc_id;
	}

	public void setWc_id(Integer wc_id) {
		this.wc_id = wc_id;
	}

	public String getWcr() {
		return wcr;
	}

	public void setWcr(String wcr) {
		this.wcr = wcr;
	}

	public String getWcshr() {
		return wcshr;
	}

	public void setWcshr(String wcshr) {
		this.wcshr = wcshr;
	}

	public Date getWcksrq() {
		return wcksrq;
	}

	public void setWcksrq(Date wcksrq) {
		this.wcksrq = wcksrq;
	}

	public Date getWcjsrq() {
		return wcjsrq;
	}

	public void setWcjsrq(Date wcjsrq) {
		this.wcjsrq = wcjsrq;
	}

	public Date getWcshrq() {
		return wcshrq;
	}

	public void setWcshrq(Date wcshrq) {
		this.wcshrq = wcshrq;
	}

	public String getWcsy() {
		return wcsy;
	}

	public void setWcsy(String wcsy) {
		this.wcsy = wcsy;
	}

	public String getWcglxm() {
		return wcglxm;
	}

	public void setWcglxm(String wcglxm) {
		this.wcglxm = wcglxm;
	}

	public String getWcrbmmc() {
		return wcrbmmc;
	}

	public void setWcrbmmc(String wcrbmmc) {
		this.wcrbmmc = wcrbmmc;
	}

	public String getWcshzt() {
		return wcshzt;
	}

	public void setWcshzt(String wcshzt) {
		this.wcshzt = wcshzt;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Wcgl [wc_id=" + wc_id + ", wcr=" + wcr + ", wcshr=" + wcshr + ", wcksrq=" + wcksrq + ", wcjsrq="
				+ wcjsrq + ", wcshrq=" + wcshrq + ", wcsy=" + wcsy + ", wcglxm=" + wcglxm + ", wcrbmmc=" + wcrbmmc
				+ ", wcshzt=" + wcshzt + "]";
	}

	 
 }





