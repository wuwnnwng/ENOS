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
@Table(name="t_xxfb",indexes = {@Index(name = "ggbt",  columnList="ggbt", unique = false),
        @Index(name = "ggfbr", columnList="ggfbr", unique = false)})
@Entity
public class Xxfb implements Serializable {

	/**
	 * 序列化：便于传输和实例化，因为他会把对象变成二进制的形式传输
	 */
	private static final long serialVersionUID = -3970876202321272453L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer xf_id;//信息发布id 
	
    @Column(name="ggbt",nullable=false)
	private String ggbt;//公告标题
    
//    @Basic(fetch = FetchType.LAZY)
    @Lob 
    @Column(name = "ggnr", nullable=false)
	private String ggnr;//公告内容
	
	@Column(name = "ggfj" )
	private String ggfj;//公告附件
	
    @Temporal(TemporalType.DATE)
	@Column(name = "fbrq")
	private Date fbrq;//公告发布日期
	
    @Column(name="ggfbr",nullable=false)
	private String ggfbr;//公告发布人

	public Integer getXf_id() {
		return xf_id;
	}

	public void setXf_id(Integer xf_id) {
		this.xf_id = xf_id;
	}

	public String getGgbt() {
		return ggbt;
	}

	public void setGgbt(String ggbt) {
		this.ggbt = ggbt;
	}

	public String getGgnr() {
		return ggnr;
	}

	public void setGgnr(String ggnr) {
		this.ggnr = ggnr;
	}

	public String getGgfj() {
		return ggfj;
	}

	public void setGgfj(String ggfj) {
		this.ggfj = ggfj;
	}

	public Date getFbrq() {
		return fbrq;
	}

	public void setFbrq(Date fbrq) {
		this.fbrq = fbrq;
	}

	public String getGgfbr() {
		return ggfbr;
	}

	public void setGgfbr(String ggfbr) {
		this.ggfbr = ggfbr;
	}

	@Override
	public String toString() {
		return "Xxfb [xf_id=" + xf_id + ", ggbt=" + ggbt + ", ggnr=" + ggnr + ", ggfj=" + ggfj + ", fbrq=" + fbrq
				+ ", ggfbr=" + ggfbr + "]";
	}
	
	
	
}
