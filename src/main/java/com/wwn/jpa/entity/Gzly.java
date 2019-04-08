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
@Table(name="t_gzly")
 
@Entity
public class Gzly implements Serializable {
	 
 
	/**
	 * 
	 */
	private static final long serialVersionUID = -7215951705373531683L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer gl_id;//工作留言id
	
	@Column(name="fjr",nullable=false)
    private String fjr;// 发件人
	
    @Temporal(TemporalType.DATE)
    @Column(name="fjrq")
    private Date fjrq;//发件日期
    
    @Temporal(TemporalType.DATE)
    @Column(name="qrrq")
    private Date qrrq;//确认日期
	
    @Column(name="sjr",nullable=false)
    private String sjr;//收件人
    
    @Column(name="lynr",nullable=false)
    private String lynr;//留言内容
    
    @Column(name="lystatus")
    private String lystatus ;//状态：已确定   ：未确认  

	public Integer getGl_id() {
		return gl_id;
	}

	public void setGl_id(Integer gl_id) {
		this.gl_id = gl_id;
	}

	public String getFjr() {
		return fjr;
	}

	public void setFjr(String fjr) {
		this.fjr = fjr;
	}

	public Date getFjrq() {
		return fjrq;
	}

	public void setFjrq(Date fjrq) {
		this.fjrq = fjrq;
	}

	public Date getQrrq() {
		return qrrq;
	}

	public void setQrrq(Date qrrq) {
		this.qrrq = qrrq;
	}

	public String getSjr() {
		return sjr;
	}

	public void setSjr(String sjr) {
		this.sjr = sjr;
	}

	public String getLynr() {
		return lynr;
	}

	public void setLynr(String lynr) {
		this.lynr = lynr;
	}

	public String getLystatus() {
		return lystatus;
	}

	public void setLystatus(String lystatus) {
		this.lystatus = lystatus;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Gzly [gl_id=" + gl_id + ", fjr=" + fjr + ", fjrq=" + fjrq + ", qrrq=" + qrrq + ", sjr=" + sjr
				+ ", lynr=" + lynr + ", lystatus=" + lystatus + "]";
	}
    
    
    
	 
 }





