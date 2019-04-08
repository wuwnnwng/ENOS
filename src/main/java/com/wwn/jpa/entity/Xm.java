package com.wwn.jpa.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Table(name="t_xm")
@Entity
 
public class Xm implements Serializable{

	 
	/**
	 * 
	 */
	private static final long serialVersionUID = 8542606664212435000L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer xm_id;//项目id
    @Column(unique =true)
    private String xm_name;//项目名称
    
	public Integer getXm_id() {
		return xm_id;
	}

	public void setXm_id(Integer xm_id) {
		this.xm_id = xm_id;
	}

	public String getXm_name() {
		return xm_name;
	}

	public void setXm_name(String xm_name) {
		this.xm_name = xm_name;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Xm [xm_id=" + xm_id + ", xm_name=" + xm_name + "]";
	}
    
}
