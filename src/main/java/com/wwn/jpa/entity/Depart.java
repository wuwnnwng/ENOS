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

import lombok.Getter;
import lombok.Setter;
@Table(name="t_depart")
@Entity
@Getter
@Setter
public class Depart implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4415623897272571553L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer d_id;//部门名称
    @Column(unique =true)
    private String d_name;//部门名称
  //mappedBy相当于之前的inverse，由UserInfo里面的depart属性的set方法去维护关联关系。简单点说就是本类放弃维护，即inverse=true
  	@OneToMany(targetEntity=UserInfo.class,mappedBy="depart")
  	private Set<UserInfo> userInfo = new HashSet<UserInfo>(0);
	public Integer getD_id() {
		return d_id;
	}
	public void setD_id(Integer d_id) {
		this.d_id = d_id;
	}
	public String getD_name() {
		return d_name;
	}
	public void setD_name(String d_name) {
		this.d_name = d_name;
	}
	public Set<UserInfo> getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(Set<UserInfo> userInfo) {
		this.userInfo = userInfo;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "Depart [d_id=" + d_id + ", d_name=" + d_name + ", userInfo=" + userInfo + "]";
	}
  	
 }