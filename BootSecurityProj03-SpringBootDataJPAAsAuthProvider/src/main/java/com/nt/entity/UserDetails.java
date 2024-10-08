package com.nt.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;


@Data
@Entity
@Table(name = "SECURITY_USERS")
public class UserDetails implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer unid;
	@Column(length = 20)
	private String uname;
	@Column(length = 200)
	private String pwd;
	@Column(length = 20)
	private String email;
	private Integer status;
	private Set<String> roles;
}
