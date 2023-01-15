package com.dentist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dentist.entity.PatientEntity;

@Repository
public interface PatientRepo extends JpaRepository<PatientEntity, Integer> {
	
	PatientEntity findPatientByUserName(String name);
	@Modifying
	@Query("update PatientEntity u set u.name=:name where u.id=:id")
	PatientEntity updatePatientSetUserNameWhereId(String name, int id);
	@Modifying
	@Query("update PatientEntity u set u.password=:password where u.id=:id")
	PatientEntity updatePatientSetPasswordWhereId(String password, int id);
	@Modifying
	@Query("update PatientEntity u set u.age=:age where u.id=:id")
	PatientEntity updatePatientSetAgeWhereId(int age, int id);
	@Modifying
	@Query("update PatientEntity u set u.phoneNumber=:phoneNumber where u.id=:id")
	PatientEntity updatePatientSetphoneNumberWhereId(String phoneNumber, int id);
	@Modifying
	@Query("update PatientEntity u set u.gender=:gendera where u.id=:id")
	PatientEntity updatePatientSetGenderWhereId(String gendera, int id);
}
