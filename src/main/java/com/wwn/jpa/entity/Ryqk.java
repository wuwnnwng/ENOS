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
/**
 * @author Administrator
 *
 */
@Table(name="t_ryqk")
 
@Entity
public class Ryqk implements Serializable {
	 
 
 
 

	/**
	 * 
	 */
	private static final long serialVersionUID = 412535779907476929L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer rq_id;//人员情况id
	
	@Column(name="rqcxxm",nullable=false)
    private String rqcxxm;//人员情况参项人姓名
	
	@Column(name="rqzprxm",nullable=false)
    private String rqzprxm;//人员情况指派人姓名
	
	@Column(name="rqjslb",nullable=false )
    private String rqjslb;//人员情况技术类别
	
    @Temporal(TemporalType.DATE)
    @Column(name="rqksrq",nullable=false)
    private Date rqksrq;//人员情况开始日期
   
    
    @Column(name="rqxm",nullable=false)
    private String rqxm;//人员情况项目
	
    @Column(name="rqyjcxsj",nullable=false)
    private float rqyjcxsj;//人员情况预计出项时间
    
    
    @Column(name="rqbz" )
    private String rqbz ;//人员情况备注
    
    @Column(name="rqbmmc",nullable=false)
    private String rqbmmc ;//人员情况部门名称

	public Integer getRq_id() {
		return rq_id;
	}

	public void setRq_id(Integer rq_id) {
		this.rq_id = rq_id;
	}

	public String getRqcxxm() {
		return rqcxxm;
	}

	public void setRqcxxm(String rqcxxm) {
		this.rqcxxm = rqcxxm;
	}

	public String getRqzprxm() {
		return rqzprxm;
	}

	public void setRqzprxm(String rqzprxm) {
		this.rqzprxm = rqzprxm;
	}

	public String getRqjslb() {
		return rqjslb;
	}

	public void setRqjslb(String rqjslb) {
		this.rqjslb = rqjslb;
	}

	public Date getRqksrq() {
		return rqksrq;
	}

	public void setRqksrq(Date rqksrq) {
		this.rqksrq = rqksrq;
	}

	public String getRqxm() {
		return rqxm;
	}

	public void setRqxm(String rqxm) {
		this.rqxm = rqxm;
	}

	public float getRqyjcxsj() {
		return rqyjcxsj;
	}

	public void setRqyjcxsj(float rqyjcxsj) {
		this.rqyjcxsj = rqyjcxsj;
	}

	public String getRqbz() {
		return rqbz;
	}

	public void setRqbz(String rqbz) {
		this.rqbz = rqbz;
	}

	public String getRqbmmc() {
		return rqbmmc;
	}

	public void setRqbmmc(String rqbmmc) {
		this.rqbmmc = rqbmmc;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Ryqk [rq_id=" + rq_id + ", rqcxxm=" + rqcxxm + ", rqzprxm=" + rqzprxm + ", rqjslb=" + rqjslb
				+ ", rqksrq=" + rqksrq + ", rqxm=" + rqxm + ", rqyjcxsj=" + rqyjcxsj + ", rqbz=" + rqbz + ", rqbmmc="
				+ rqbmmc + "]";
	}

	 
 }





