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
@Table(name="t_ssgl")
 
@Entity
public class Ssgl implements Serializable {
	  

	/**
	 * 
	 */
	private static final long serialVersionUID = -5585500276113609197L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer sg_id;//宿舍管理id
	
	@Column(name="sgsqr",nullable=false)
    private String sgsqr;//宿舍申请人
	
	@Column(name="sgjbr" )
    private String sgjbr;//宿舍管理 经办人
	
    @Temporal(TemporalType.DATE)
    @Column(name="sgksrq",nullable=false)
    private Date sgksrq;//宿舍管理开始日期
    
    @Temporal(TemporalType.DATE)
    @Column(name="sgjsrq" )
    private Date sgjsrq;//宿舍管理结束日期
    
    
    @Column(name="sgssmc",nullable=false)
    private String sgssmc;//宿舍名称
	
    @Column(name="sgssdz",nullable=false)
    private String sgssdz;//宿舍地址
    
    @Column(name="sgjbzt" )
    private String sgjbzt ;//经办人审核转态：已批准，未批准
   
    @Column(name="sgbz" )
    private String sgbz ;//宿舍管理备注

	public Integer getSg_id() {
		return sg_id;
	}

	public void setSg_id(Integer sg_id) {
		this.sg_id = sg_id;
	}

	public String getSgsqr() {
		return sgsqr;
	}

	public void setSgsqr(String sgsqr) {
		this.sgsqr = sgsqr;
	}

	public String getSgjbr() {
		return sgjbr;
	}

	public void setSgjbr(String sgjbr) {
		this.sgjbr = sgjbr;
	}

	public Date getSgksrq() {
		return sgksrq;
	}

	public void setSgksrq(Date sgksrq) {
		this.sgksrq = sgksrq;
	}

	public Date getSgjsrq() {
		return sgjsrq;
	}

	public void setSgjsrq(Date sgjsrq) {
		this.sgjsrq = sgjsrq;
	}

	public String getSgssmc() {
		return sgssmc;
	}

	public void setSgssmc(String sgssmc) {
		this.sgssmc = sgssmc;
	}

	public String getSgssdz() {
		return sgssdz;
	}

	public void setSgssdz(String sgssdz) {
		this.sgssdz = sgssdz;
	}

	public String getSgjbzt() {
		return sgjbzt;
	}

	public void setSgjbzt(String sgjbzt) {
		this.sgjbzt = sgjbzt;
	}

	public String getSgbz() {
		return sgbz;
	}

	public void setSgbz(String sgbz) {
		this.sgbz = sgbz;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Ssgl [sg_id=" + sg_id + ", sgsqr=" + sgsqr + ", sgjbr=" + sgjbr + ", sgksrq=" + sgksrq + ", sgjsrq="
				+ sgjsrq + ", sgssmc=" + sgssmc + ", sgssdz=" + sgssdz + ", sgjbzt=" + sgjbzt + ", sgbz=" + sgbz + "]";
	}


	 
 }





