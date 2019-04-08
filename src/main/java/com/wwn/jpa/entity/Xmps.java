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
@Table(name="t_xmps")
 
@Entity
public class Xmps implements Serializable {
 
	/**
	 * 
	 */
	private static final long serialVersionUID = -2488011798347004166L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer xp_id;//项目评审id
	
	@Column(name="xpxmmc",nullable=false)
    private String xpxmmc;//项目评审 项目名称
	
	@Column(name="xpmkmc",nullable=false)
    private String xpmkmc;//项目评审模块名称
	
	@Column(name="xpyj",nullable=false )
    private String xpyj;//项目评审评审意见
	
    @Temporal(TemporalType.DATE)
    @Column(name="xprq",nullable=false)
    private Date xprq;//项目评审 日期
   
    
    @Column(name="xpr",nullable=false)
    private String xpr;//项目评审人
    

    @Column(name="xpbz" )
    private String xpbz;//项目评审备注


	public Integer getXp_id() {
		return xp_id;
	}


	public void setXp_id(Integer xp_id) {
		this.xp_id = xp_id;
	}


	public String getXpxmmc() {
		return xpxmmc;
	}


	public void setXpxmmc(String xpxmmc) {
		this.xpxmmc = xpxmmc;
	}


	public String getXpmkmc() {
		return xpmkmc;
	}


	public void setXpmkmc(String xpmkmc) {
		this.xpmkmc = xpmkmc;
	}


	public String getXpyj() {
		return xpyj;
	}


	public void setXpyj(String xpyj) {
		this.xpyj = xpyj;
	}


	public Date getXprq() {
		return xprq;
	}


	public void setXprq(Date xprq) {
		this.xprq = xprq;
	}


	public String getXpr() {
		return xpr;
	}


	public void setXpr(String xpr) {
		this.xpr = xpr;
	}


	public String getXpbz() {
		return xpbz;
	}


	public void setXpbz(String xpbz) {
		this.xpbz = xpbz;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	@Override
	public String toString() {
		return "Xmps [xp_id=" + xp_id + ", xpxmmc=" + xpxmmc + ", xpmkmc=" + xpmkmc + ", xpyj=" + xpyj + ", xprq="
				+ xprq + ", xpr=" + xpr + ", xpbz=" + xpbz + "]";
	}
	
	
     
	 
 }





