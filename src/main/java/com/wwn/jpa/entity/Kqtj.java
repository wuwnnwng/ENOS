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
@Table(name="v_kqtj")
 
@Entity
public class Kqtj implements Serializable {
	 
  
	/**
	 * 
	 */
	private static final long serialVersionUID = 6169260172805921766L;
   
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Integer dk_id;//考情统计id
	 
	@Column(name="dkname" )
    private String dkname;// 打卡人
	
	@Column(name="dkdepart",nullable=false)
    private String dkdepart;//打卡人所在部门
	
    @Temporal(TemporalType.DATE)
    @Column(name="rq")
    private Date rq;//打卡日期
    
    
//    @Temporal(TemporalType.TIME)
    @Column(name="qdsj")
    private String qdsj;//签到时间 
    
//    @Temporal(TemporalType.TIME)
    @Column(name="qtsj")
    private String qtsj;//签退时间 
    
    @Column(name="xq" )
    private String xq;//星期
    
    
//    @Temporal(TemporalType.TIME)
    @Column(name="gzsj")
    private String gzsj;//工作时间 
    
    @Column(name="cdsj" )
    private String cdsj;//迟到时间
    
    @Column(name="ztsj" )
    private String ztsj;//早退时间
    
    @Column(name="zlsj" )
    private String zlsj;//早来时间
    
    @Column(name="wtsj" )
    private String wtsj;//晚退时间
    
    @Column(name="sbkzt" )
    private String sbkzt;//上班卡状态
    
    @Column(name="xbkzt" )
    private String xbkzt;//下班卡状态

	public Integer getDk_id() {
		return dk_id;
	}

	public void setDk_id(Integer dk_id) {
		this.dk_id = dk_id;
	}

	public String getDkname() {
		return dkname;
	}

	public void setDkname(String dkname) {
		this.dkname = dkname;
	}

	public String getDkdepart() {
		return dkdepart;
	}

	public void setDkdepart(String dkdepart) {
		this.dkdepart = dkdepart;
	}

	public Date getRq() {
		return rq;
	}

	public void setRq(Date rq) {
		this.rq = rq;
	}

	public String getQdsj() {
		return qdsj;
	}

	public void setQdsj(String qdsj) {
		this.qdsj = qdsj;
	}

	public String getQtsj() {
		return qtsj;
	}

	public void setQtsj(String qtsj) {
		this.qtsj = qtsj;
	}

	public String getXq() {
		return xq;
	}

	public void setXq(String xq) {
		this.xq = xq;
	}

	public String getGzsj() {
		return gzsj;
	}

	public void setGzsj(String gzsj) {
		this.gzsj = gzsj;
	}

	public String getCdsj() {
		return cdsj;
	}

	public void setCdsj(String cdsj) {
		this.cdsj = cdsj;
	}

	public String getZtsj() {
		return ztsj;
	}

	public void setZtsj(String ztsj) {
		this.ztsj = ztsj;
	}

	public String getZlsj() {
		return zlsj;
	}

	public void setZlsj(String zlsj) {
		this.zlsj = zlsj;
	}

	public String getWtsj() {
		return wtsj;
	}

	public void setWtsj(String wtsj) {
		this.wtsj = wtsj;
	}

	public String getSbkzt() {
		return sbkzt;
	}

	public void setSbkzt(String sbkzt) {
		this.sbkzt = sbkzt;
	}

	public String getXbkzt() {
		return xbkzt;
	}

	public void setXbkzt(String xbkzt) {
		this.xbkzt = xbkzt;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Kqtj [dk_id=" + dk_id + ", dkname=" + dkname + ", dkdepart=" + dkdepart + ", rq=" + rq + ", qdsj="
				+ qdsj + ", qtsj=" + qtsj + ", xq=" + xq + ", gzsj=" + gzsj + ", cdsj=" + cdsj + ", ztsj=" + ztsj
				+ ", zlsj=" + zlsj + ", wtsj=" + wtsj + ", sbkzt=" + sbkzt + ", xbkzt=" + xbkzt + "]";
	}
 
	 
 }





