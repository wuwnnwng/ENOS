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
@Table(name="t_qjgl")
 
@Entity
public class Qjgl implements Serializable {
	 
	/**
	 * 
	 */
	private static final long serialVersionUID = 7836559275699465657L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer qj_id;//请假管理id
	
	@Column(name="qjr",nullable=false)
    private String qjr;//请假人
	
	@Column(name="qjshr" )
    private String qjshr;//请假管理审核人
	
    @Temporal(TemporalType.DATE)
    @Column(name="qjksrq",nullable=false)
    private Date qjksrq;//请假管理开始日期
    
    @Temporal(TemporalType.DATE)
    @Column(name="qjjsrq",nullable=false)
    private Date qjjsrq;//请假管理结束日期
    
    @Temporal(TemporalType.DATE)
    @Column(name="qjshrq" )
    private Date qjshrq;//请假管理审核日期
    
    @Column(name="qjsy",nullable=false)
    private String qjsy;//请假事由
	
    @Column(name="qjlx",nullable=false)
    private String qjlx;//请假类型
    
    @Column(name="qjrbmmc",nullable=false)
    private String qjrbmmc ;//请假人所在部门
    
    @Column(name="qjshzt")
    private String qjshzt ;//请假管理审核状态：已审核  ：未审核

    
    @Column(name="qjrbz" )
    private String qjrbz ;//请假备注


	public Integer getQj_id() {
		return qj_id;
	}


	public void setQj_id(Integer qj_id) {
		this.qj_id = qj_id;
	}


	public String getQjr() {
		return qjr;
	}


	public void setQjr(String qjr) {
		this.qjr = qjr;
	}


	public String getQjshr() {
		return qjshr;
	}


	public void setQjshr(String qjshr) {
		this.qjshr = qjshr;
	}


	public Date getQjksrq() {
		return qjksrq;
	}


	public void setQjksrq(Date qjksrq) {
		this.qjksrq = qjksrq;
	}


	public Date getQjjsrq() {
		return qjjsrq;
	}


	public void setQjjsrq(Date qjjsrq) {
		this.qjjsrq = qjjsrq;
	}


	public Date getQjshrq() {
		return qjshrq;
	}


	public void setQjshrq(Date qjshrq) {
		this.qjshrq = qjshrq;
	}


	public String getQjsy() {
		return qjsy;
	}


	public void setQjsy(String qjsy) {
		this.qjsy = qjsy;
	}


	public String getQjlx() {
		return qjlx;
	}


	public void setQjlx(String qjlx) {
		this.qjlx = qjlx;
	}


	public String getQjrbmmc() {
		return qjrbmmc;
	}


	public void setQjrbmmc(String qjrbmmc) {
		this.qjrbmmc = qjrbmmc;
	}


	public String getQjshzt() {
		return qjshzt;
	}


	public void setQjshzt(String qjshzt) {
		this.qjshzt = qjshzt;
	}


	public String getQjrbz() {
		return qjrbz;
	}


	public void setQjrbz(String qjrbz) {
		this.qjrbz = qjrbz;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	@Override
	public String toString() {
		return "QJgl [qj_id=" + qj_id + ", qjr=" + qjr + ", qjshr=" + qjshr + ", qjksrq=" + qjksrq + ", qjjsrq="
				+ qjjsrq + ", qjshrq=" + qjshrq + ", qjsy=" + qjsy + ", qjlx=" + qjlx + ", qjrbmmc=" + qjrbmmc
				+ ", qjshzt=" + qjshzt + ", qjrbz=" + qjrbz + "]";
	}
 

	 
 }





