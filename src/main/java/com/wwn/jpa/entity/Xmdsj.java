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
@Table(name="t_xmdsj",indexes = {@Index(name = "xdjlr",  columnList="xdjlr", unique = false),
        @Index(name = "xdshr", columnList="xdshr", unique = false)})
 
@Entity
public class Xmdsj implements Serializable {
	 
 
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 6120873461386011968L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer xd_id;//项目大事记id
	
	@Column(name="xdjlr",nullable=false)
    private String xdjlr;//项目大事记记录人
	
	@Column(name="xdshr" )
    private String xdshr;//项目大事记审核人
	
    @Temporal(TemporalType.DATE)
    @Column(name="xdrq",nullable=false)
    private Date xdrq;//项目大事日期
    
    @Temporal(TemporalType.DATE)
    @Column(name="xdshsj" )
    private Date xdshsj;//项目大事审核日期
    
    @Column(name="xdnr",nullable=false)
    private String xdnr;//项目大事记内容
	
    @Column(name="xdxmmc",nullable=false)
    private String xdxmmc;//项目大事记项目名称
    
    
    @Column(name="xdstatus",nullable=false)
    private String xdstatus ;//项目状态：需求分析   ：开发阶段，测试，上线
    
    @Column(name="xdshzt")
    private String xdshzt ;//项目大事记审核状态：已审核  ：未审核

	public Integer getXd_id() {
		return xd_id;
	}

	public void setXd_id(Integer xd_id) {
		this.xd_id = xd_id;
	}

	public String getXdjlr() {
		return xdjlr;
	}

	public void setXdjlr(String xdjlr) {
		this.xdjlr = xdjlr;
	}

	public String getXdshr() {
		return xdshr;
	}

	public void setXdshr(String xdshr) {
		this.xdshr = xdshr;
	}

	public Date getXdrq() {
		return xdrq;
	}

	public void setXdrq(Date xdrq) {
		this.xdrq = xdrq;
	}

	public Date getXdshsj() {
		return xdshsj;
	}

	public void setXdshsj(Date xdshsj) {
		this.xdshsj = xdshsj;
	}

	public String getXdnr() {
		return xdnr;
	}

	public void setXdnr(String xdnr) {
		this.xdnr = xdnr;
	}

	public String getXdxmmc() {
		return xdxmmc;
	}

	public void setXdxmmc(String xdxmmc) {
		this.xdxmmc = xdxmmc;
	}

	public String getXdstatus() {
		return xdstatus;
	}

	public void setXdstatus(String xdstatus) {
		this.xdstatus = xdstatus;
	}

	public String getXdshzt() {
		return xdshzt;
	}

	public void setXdshzt(String xdshzt) {
		this.xdshzt = xdshzt;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Xmdsj [xd_id=" + xd_id + ", xdjlr=" + xdjlr + ", xdshr=" + xdshr + ", xdrq=" + xdrq + ", xdshsj="
				+ xdshsj + ", xdnr=" + xdnr + ", xdxmmc=" + xdxmmc + ", xdstatus=" + xdstatus + ", xdshzt=" + xdshzt
				+ "]";
	}

    
	 
 }





