package com.dentist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dentist.entity.DoctorEntity;

@Repository
public interface DoctorRepo extends JpaRepository<DoctorEntity, Integer> {
	DoctorEntity findDoctorByUserName(String name);
	@Modifying
	@Query("update DoctorEntity u set u.dname = :name where u.id=:id")
	DoctorEntity updateDoctorSetUserNameWhereId(String name, int id);
	@Modifying
	@Query("update DoctorEntity u set u.password = :password where u.id=:id")
	DoctorEntity updateDoctorSetPasswordWhereId(String password, int id);
	@Modifying
	@Query("update DoctorEntity u set u.nationalID = :nationalID where u.id=:id")
	DoctorEntity updateDoctorSetNationalIdWhereId(int nationalID, int id);
	@Modifying
	@Query("update DoctorEntity u set u.specialty = :specialty where u.id=:id")
	DoctorEntity updateDoctorSetSpecialtyWhereId(String specialty, int id);
	@Modifying
	@Query("update DoctorEntity u set u.phoneNumber = :phoneNumber where u.id=:id")
	DoctorEntity updateDoctorSetPhoneNumberWhereId(String phoneNumber, int id);

}
//❖ The Doctor Can Update Profile: The doctors can update their profile with their password, name, and their national ID, specialty, and phone number.
