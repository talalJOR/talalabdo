package com.dentist.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.dentist.entity.AppointmentEntity;

import com.dentist.entity.PatientEntity;

public interface Appointment extends JpaRepository<AppointmentEntity, Integer> {
	PatientEntity findPatientById(int id);
	@Modifying
	@Query("update AppointmentEntity u set u.isVisited=:isVisited where u.id=:id")
	AppointmentEntity updateAapointmentIsVisited(boolean isVisited,int id);

	@Query(nativeQuery = true, value = "SELECT appointment.date, appointment.time ,patient.name\r\n"
			+ "FROM appoitment JOIN patient ON appointment.patientapp = patient.patient(id) where isBooked =ture ; ")
	List<AppointmentEntity> allBookedHours();

	@Query(nativeQuery = true, value = "select hours , date from appointment hours where isBooked = true")
	List<AppointmentEntity> allAvalabliHours();

	@Query(nativeQuery = true, value = "SELECT COUNT(*) FROM appointment JOIN patient ON appointment.patientapp = patient.patient(id) , doctor ON appointment.doctorapp = doctor.doctor(id) WHERE isVisited= true")
	List<AppointmentEntity> patientVisitdoctor(int patintid, int doctorid);
}
