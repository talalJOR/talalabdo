package com.dentist.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "Doctor")
@Data
public class DoctorEntity {
	@Column(name = "id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "Doctor_username")
	private String userName;
	@Column(name = "Doctor_password")
	private String password;
	@Column(name = "Doctor_name")
	private String dname;
	@Column(name = "Doctor_nationalID")
	private int nationalID;
	@Column(name = "Doctor_specialty")
	private String specialty;
	@Column(name = "Doctor_phonenumber")
	private String phoneNumber;

}
