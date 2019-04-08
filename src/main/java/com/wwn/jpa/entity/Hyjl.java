package com.wwn.jpa.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Table(name="t_hyjl",indexes = {@Index(name = "hyyt",  columnList="hyyt", unique = false)})
@Entity
public class Hyjl implements Serializable {

	
 

	/**
	 * 
	 */
	private static final long serialVersionUID = -4574122625423640613L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer  hj_id;//会议记录id
	
	@Column(name = "hyjlrxm", nullable=false)
	private String hyjlrxm;//会议记录人姓名
	
//	@Lob 
    @Column(name = "hyyt", nullable=false)
	private String hyyt;//会议议题
	
    @Lob 
    @Column(name = "hynr", nullable=false)
	private String hynr;//会议内容
    
    @Column(name = "hyjlun" )
	private String hyjlun;//会议结论
    
	@Temporal(TemporalType.DATE)
	@Column(name = "hyrq", nullable=false)
	private Date hyrq;//会议日期
	
	@Column(name = "hyxmmc", nullable=false)
	private String hyxmmc;//会议项目名称
	    
//	@Temporal(TemporalType.DATE)
	@Column(name = "hybmmc", nullable=false)
	private String hybmmc;//会议部门名称
	
	@Column(name = "hyly")
	private String hyly;// 会议留言
	
	@Column(name = "hylyrxm")
	private String hylyrxm;// 会议留言人姓名
	
	@Column(name = "hycyry" ,nullable=false)
	private String hycyry;// 会议参与人员

	public Integer getHj_id() {
		return hj_id;
	}

	public void setHj_id(Integer hj_id) {
		this.hj_id = hj_id;
	}

	public String getHyjlrxm() {
		return hyjlrxm;
	}

	public void setHyjlrxm(String hyjlrxm) {
		this.hyjlrxm = hyjlrxm;
	}

	public String getHyyt() {
		return hyyt;
	}

	public void setHyyt(String hyyt) {
		this.hyyt = hyyt;
	}

	public String getHynr() {
		return hynr;
	}

	public void setHynr(String hynr) {
		this.hynr = hynr;
	}

	public String getHyjlun() {
		return hyjlun;
	}

	public void setHyjlun(String hyjlun) {
		this.hyjlun = hyjlun;
	}

	public Date getHyrq() {
		return hyrq;
	}

	public void setHyrq(Date hyrq) {
		this.hyrq = hyrq;
	}

	public String getHyxmmc() {
		return hyxmmc;
	}

	public void setHyxmmc(String hyxmmc) {
		this.hyxmmc = hyxmmc;
	}

	public String getHybmmc() {
		return hybmmc;
	}

	public void setHybmmc(String hybmmc) {
		this.hybmmc = hybmmc;
	}

	public String getHyly() {
		return hyly;
	}

	public void setHyly(String hyly) {
		this.hyly = hyly;
	}

	public String getHylyrxm() {
		return hylyrxm;
	}

	public void setHylyrxm(String hylyrxm) {
		this.hylyrxm = hylyrxm;
	}

	public String getHycyry() {
		return hycyry;
	}

	public void setHycyry(String hycyry) {
		this.hycyry = hycyry;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Hyjl [hj_id=" + hj_id + ", hyjlrxm=" + hyjlrxm + ", hyyt=" + hyyt + ", hynr=" + hynr + ", hyjlun="
				+ hyjlun + ", hyrq=" + hyrq + ", hyxmmc=" + hyxmmc + ", hybmmc=" + hybmmc + ", hyly=" + hyly
				+ ", hylyrxm=" + hylyrxm + ", hycyry=" + hycyry + "]";
	}
 
	
}














