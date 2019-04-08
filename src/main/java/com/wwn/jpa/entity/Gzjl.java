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

@Table(name="t_gzjl",indexes = {@Index(name = "jlrxm",  columnList="jlrxm", unique = false),
        @Index(name = "pjjg", columnList="pjjg", unique = false)})

@Entity
public class Gzjl implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2516242117067067608L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer  gj_id;//工作记录id
	
	@Column(name = "jlrxm", nullable=false)
	private String jlrxm;//工作记录人姓名
	
	@Lob 
    @Column(name = "gznr", nullable=false)
	private String gznr;//工作记录内容
	
	@Temporal(TemporalType.DATE)
	@Column(name = "gzrq", nullable=false)
	private Date gzrq;//工作日期
	
	@Temporal(TemporalType.DATE)
	@Column(name = "gzshrq" )
	private Date gzshrq;//审核日期
	
	@Column(name = "gzwcqk", nullable=false)
	private String gzwcqk;//工作完成情况:已完成，正在进行
	
	@Column(name = "psrxm" )
	private String psrxm;//工作记录评审人姓名
	
    @Column(name = "pjjg" )
	private String pjjg;//工作记录评价结果：一般，良好，优秀，非常棒
	
    @Column(name = "pjly" )
	private String pjly;//工作记录评留言
	
    @Column(name = "bmmc", nullable=false)
	private String bmmc;//部门名称
	 
    @Column(name = "xmmc", nullable=false)
	private String xmmc;//项目名称

	public Integer getGj_id() {
		return gj_id;
	}

	public void setGj_id(Integer gj_id) {
		this.gj_id = gj_id;
	}

	public String getJlrxm() {
		return jlrxm;
	}

	public void setJlrxm(String jlrxm) {
		this.jlrxm = jlrxm;
	}

	public String getGznr() {
		return gznr;
	}

	public void setGznr(String gznr) {
		this.gznr = gznr;
	}

	public Date getGzrq() {
		return gzrq;
	}

	public void setGzrq(Date gzrq) {
		this.gzrq = gzrq;
	}

	public Date getGzshrq() {
		return gzshrq;
	}

	public void setGzshrq(Date gzshrq) {
		this.gzshrq = gzshrq;
	}

	public String getGzwcqk() {
		return gzwcqk;
	}

	public void setGzwcqk(String gzwcqk) {
		this.gzwcqk = gzwcqk;
	}

	public String getPsrxm() {
		return psrxm;
	}

	public void setPsrxm(String psrxm) {
		this.psrxm = psrxm;
	}

	public String getPjjg() {
		return pjjg;
	}

	public void setPjjg(String pjjg) {
		this.pjjg = pjjg;
	}

	public String getPjly() {
		return pjly;
	}

	public void setPjly(String pjly) {
		this.pjly = pjly;
	}

	public String getBmmc() {
		return bmmc;
	}

	public void setBmmc(String bmmc) {
		this.bmmc = bmmc;
	}

	public String getXmmc() {
		return xmmc;
	}

	public void setXmmc(String xmmc) {
		this.xmmc = xmmc;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Gzjl [gj_id=" + gj_id + ", jlrxm=" + jlrxm + ", gznr=" + gznr + ", gzrq=" + gzrq + ", gzshrq=" + gzshrq
				+ ", gzwcqk=" + gzwcqk + ", psrxm=" + psrxm + ", pjjg=" + pjjg + ", pjly=" + pjly + ", bmmc=" + bmmc
				+ ", xmmc=" + xmmc + "]";
	}

	
    
	
}














