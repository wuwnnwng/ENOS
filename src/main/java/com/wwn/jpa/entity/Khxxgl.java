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
@Table(name="t_khxxgl")
 
@Entity
public class Khxxgl implements Serializable {
	  

 

	/**
	 * 
	 */
	private static final long serialVersionUID = 6078504771540304854L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer kh_id;//客户管理id
	
	@Column(name="khname",nullable=false)
    private String khname;//客户姓名
	
	@Column(name="khcjr",nullable=false)
    private String khcjr;//客户 创建人
	
	@Column(name="khxb" ,nullable=false)
    private String khxb;//客户性别
	
	@Column(name="khdwmc" )
    private String khdwmc;//客户所在单位
	
	@Column(name="khyx" )
    private String khyx;//客户邮箱
	
	@Column(name="khdh",nullable=false )
    private String khdh;//客户电话
	
	@Column(name="khdz" ,nullable=false)
    private String khdz;//客户地址
	
	@Column(name="khdjxm",nullable=false)
    private String khdjxm;//客户对接项目
		
	
    @Temporal(TemporalType.DATE)
    @Column(name="khhzkssq",nullable=false)
    private Date khhzkssq;//与公司合作开始日期
    
    @Temporal(TemporalType.DATE)
    @Column(name="khhzjsrq" )
    private Date khhzjsrq;//与公司合作结束日期
   
    @Column(name="khhzzt" )
    private String khhzzt ;//合作转态 ：进行中，已解除
    
    @Column(name="khbz" )
    private String khbz ;//客户管理备注

	public Integer getKh_id() {
		return kh_id;
	}

	public void setKh_id(Integer kh_id) {
		this.kh_id = kh_id;
	}

	public String getKhname() {
		return khname;
	}

	public void setKhname(String khname) {
		this.khname = khname;
	}

	public String getKhcjr() {
		return khcjr;
	}

	public void setKhcjr(String khcjr) {
		this.khcjr = khcjr;
	}

	public String getKhxb() {
		return khxb;
	}

	public void setKhxb(String khxb) {
		this.khxb = khxb;
	}

	public String getKhdwmc() {
		return khdwmc;
	}

	public void setKhdwmc(String khdwmc) {
		this.khdwmc = khdwmc;
	}

	public String getKhyx() {
		return khyx;
	}

	public void setKhyx(String khyx) {
		this.khyx = khyx;
	}

	public String getKhdh() {
		return khdh;
	}

	public void setKhdh(String khdh) {
		this.khdh = khdh;
	}

	public String getKhdz() {
		return khdz;
	}

	public void setKhdz(String khdz) {
		this.khdz = khdz;
	}

	public String getKhdjxm() {
		return khdjxm;
	}

	public void setKhdjxm(String khdjxm) {
		this.khdjxm = khdjxm;
	}

	public Date getKhhzkssq() {
		return khhzkssq;
	}

	public void setKhhzkssq(Date khhzkssq) {
		this.khhzkssq = khhzkssq;
	}

	public Date getKhhzjsrq() {
		return khhzjsrq;
	}

	public void setKhhzjsrq(Date khhzjsrq) {
		this.khhzjsrq = khhzjsrq;
	}

	public String getKhhzzt() {
		return khhzzt;
	}

	public void setKhhzzt(String khhzzt) {
		this.khhzzt = khhzzt;
	}

	public String getKhbz() {
		return khbz;
	}

	public void setKhbz(String khbz) {
		this.khbz = khbz;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Khxxgl [kh_id=" + kh_id + ", khname=" + khname + ", khcjr=" + khcjr + ", khxb=" + khxb + ", khdwmc="
				+ khdwmc + ", khyx=" + khyx + ", khdh=" + khdh + ", khdz=" + khdz + ", khdjxm=" + khdjxm + ", khhzkssq="
				+ khhzkssq + ", khhzjsrq=" + khhzjsrq + ", khhzzt=" + khhzzt + ", khbz=" + khbz + "]";
	}
 

	 
	 
 }





