package com.wwn.jpa.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Table(name="t_qjlx")
@Entity
public class Qjlx implements Serializable {
	 
	/**
	 * 
	 */
	private static final long serialVersionUID = 6482495209134681794L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ql_id;//请假类型id
	
    @Column(name="ql_name",unique =true)
    private String ql_name;//请假类型

	public Integer getQl_id() {
		return ql_id;
	}

	public void setQl_id(Integer ql_id) {
		this.ql_id = ql_id;
	}

	public String getQl_name() {
		return ql_name;
	}

	public void setQl_name(String ql_name) {
		this.ql_name = ql_name;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Qjlx [ql_id=" + ql_id + ", ql_name=" + ql_name + "]";
	}
    
   
 }