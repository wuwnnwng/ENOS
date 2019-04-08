package com.wwn.jpa.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Table(name="t_dk")

@Entity
public class Sbdk implements Serializable {

 

	/**
	 * 
	 */
	private static final long serialVersionUID = -4294958480364668925L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer  dk_id;//上班打卡id
	
	@Column(name = "dk_name", nullable=false)
	private String dk_name;//上班打卡人姓名
	
	@Lob 
    @Column(name = "dk_depart", nullable=false)
	private String dk_depart;//上班打卡部门
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dk_kssj", nullable=false)
	private Date dk_kssj;//上班打卡
	

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dk_jssj", nullable=false)
	private Date dk_jssj;//下班打卡


	public Integer getDk_id() {
		return dk_id;
	}


	public void setDk_id(Integer dk_id) {
		this.dk_id = dk_id;
	}


	public String getDk_name() {
		return dk_name;
	}


	public void setDk_name(String dk_name) {
		this.dk_name = dk_name;
	}


	public String getDk_depart() {
		return dk_depart;
	}


	public void setDk_depart(String dk_depart) {
		this.dk_depart = dk_depart;
	}


	public Date getDk_kssj() {
		return dk_kssj;
	}


	public void setDk_kssj(Date dk_kssj) {
		this.dk_kssj = dk_kssj;
	}


	public Date getDk_jssj() {
		return dk_jssj;
	}


	public void setDk_jssj(Date dk_jssj) {
		this.dk_jssj = dk_jssj;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	@Override
	public String toString() {
		return "Sbdk [dk_id=" + dk_id + ", dk_name=" + dk_name + ", dk_depart=" + dk_depart + ", dk_kssj=" + dk_kssj
				+ ", dk_jssj=" + dk_jssj + "]";
	}


	 
	
	
}














