package com.dentist.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Entity
@Table(name = "appintment")
@Data
public class AppointmentEntity {
	@Column(name = "id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@ManyToOne
	@JoinColumn(name = "doctorapp", referencedColumnName = "id")
	private DoctorEntity doctor;
	@ManyToOne
	@JoinColumn(name = "patientapp", referencedColumnName = "id")
	private PatientEntity patient;

	@JsonFormat(pattern = "dd/MM/YYYY")
	@Column(name = "date1")
	private LocalDate date;
	@JsonFormat(pattern = "HH:mm")
	@Column(name = "time1")
	private LocalTime time;
	@Enumerated(EnumType.STRING)
	WorkingHours hours;
	@Column(name = "isVisited")
	private boolean isVisited;
	@Column(name = "isBooked")
	private boolean isBooked;

}
