package com.dentist.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dentist.entity.AppointmentEntity;
import com.dentist.entity.DoctorEntity;
import com.dentist.entity.PatientEntity;
import com.dentist.pojo.Login;
import com.dentist.pojo.Result;
import com.dentist.repository.Appointment;
import com.dentist.repository.DoctorRepo;
import com.dentist.utility.TokenUtility;

@Service
public class DoctorService {
	@Autowired
	DoctorRepo doctorrepo;
	@Autowired
	Appointment appointment;
	@Autowired
	TokenUtility tokenUtility;
	private static Map<LocalDate, List<AppointmentEntity>> appointments = new HashMap<>();
	private static final LocalTime START_TIME = LocalTime.of(8, 0);
	private static final LocalTime END_TIME = LocalTime.of(17, 0);

	public Result login(Login login) {
		Result result = new Result();
		Map<String, Object> mapResult = new HashMap<>();
		DoctorEntity doctor = doctorrepo.findDoctorByUserName(login.username);
		if (doctor == null) {
			result.setStatus(1);
			result.setStatusDescription("doctor Not Found ");
			return result;
		}
		if (!doctor.getPassword().equalsIgnoreCase(login.getPassword())) {

			result.setStatusDescription("Inncorect Password");
			result.setStatus(1);
			return result;

		}
		String token = tokenUtility.generateToken(login.getUsername());
		mapResult.put("token", token);
		result.setStatus(0);
		result.setResultMap(mapResult);
		return result;
	}

	public Result createAppointment(AppointmentEntity appointment) {
		Result result = new Result();

		if (appointment.getTime().isBefore(START_TIME) || appointment.getTime().isAfter(END_TIME)) {
			result.setStatus(1);
			;
			result.setStatusDescription("The appointment is outside of the dentist's working hours.");
			return result;
		}
//
		LocalDate date = appointment.getDate();
		List<AppointmentEntity> dateAppointments = appointments.get(date);
		if (dateAppointments == null) {
			dateAppointments = new ArrayList<>();
		}

		if (appointment.isBooked()) {
			result.setStatus(1);
			result.setStatusDescription("The appointment overlaps with another appointment.");
			return result;
		}
		appointment.setHours(appointment.getHours());
		appointment.setBooked(true);
		dateAppointments.add(appointment);
		appointments.put(date, dateAppointments);

		result.setStatus(0);

		result.setStatusDescription("Appointment created successfully.");
		return result;
	}

//	public Result createAppointment( AppointmentEntity appointmentEntity ) {
//		Result result = new Result();
//		Map<String, Object> mapResult = new HashMap<>();
//		if (appointmentEntity == null || appointmentEntity.getPatient().getName().isEmpty()) {
//			result.setStatus("Failed");
//			mapResult.put("appointmentEntity", "Cannot Send appointmentEntity Empty");
//			result.setResultMap(mapResult);
//			return result;
//		}
//		if (appointmentEntity.getId() != null) {
//			if (appointmentEntity.getId() < 0)
//				result.setStatus("Failed");
//			mapResult.put("Id", "Cannot Send Id negative");
//			result.setResultMap(mapResult);
//			return result;
//		}
//	    appointment.save(appointmentEntity);
//		result.setStatus("successful.");
//		result.setResultMap(mapResult);
//		return result;	
//	}

	public Result findPateintById(Integer id) {
		Result result = new Result();
		Map<String, Object> mapResult = new HashMap<>();
		if (id == null || id < 0) {
			result.setStatus(1);
			mapResult.put("id", "Cannot send id null or empty");
			result.setResultMap(mapResult);
			return result;
		}
		result.setStatus(0);
		mapResult.put("Obj", appointment.findPatientById(id));
		result.setResultMap(mapResult);
		return result;
	}

	public List<AppointmentEntity> findallappAppointmentEntities() {
		return appointment.findAll();
	}

	public List<PatientEntity> findallPatient() {
		return findallPatient();
	}

	public Result deleateAppointment(AppointmentEntity appointmentEntity) {
		Result result = new Result();

		if (appointmentEntity == null || appointmentEntity.getPatient().getName().isEmpty()) {
			result.setStatus(1);
			result.setStatusDescription("Cannot Send appointmentEntity Empty");

			return result;
		}
		if (appointmentEntity.getId() != null) {
			if (appointmentEntity.getId() < 0)
				result.setStatus(1);
			result.setStatusDescription("Cannot Send Id negative");
			return result;
		}
		appointment.delete(appointmentEntity);
		result.setStatus(0);
		result.setStatusDescription("Delete successful.");
		return result;
	}

//	public Result alllAppointment( AppointmentEntity appointmentEntity ) {
//		Result result = new Result();
//		Map<String, Object> mapResult = new HashMap<>();
//		if (appointmentEntity == null || appointmentEntity.getPatient().getName().isEmpty()) {
//			result.setStatus(1);
//			mapResult.put("appointmentEntity", "Cannot Send appointmentEntity Empty");
//			result.setResultMap(mapResult);
//			return result;
//		}
//		if (appointmentEntity.getId() != null) {
//			if (appointmentEntity.getId() < 0)
//				result.setStatus(1);
//			mapResult.put("Id", "Cannot Send Id negative");
//			result.setResultMap(mapResult);
//			return result;
//		}
//	    appointment.delete(appointmentEntity);
//
//		result.setStatus(0);
//		result.setResultMap(mapResult);
//		return result;	
//	}

	public Result UpdateDoctorProfile(DoctorEntity doctor) {
		Result result = new Result();
		Map<String, Object> mapResult = new HashMap<>();
		if (doctor.getUserName() == null || doctor.getUserName().isEmpty()) {
			result.setStatus(1);
			mapResult.put("DoctorEntity ", "Cannot Send DoctorEntity  Empty");
			result.setResultMap(mapResult);
			return result;
		}
		if (doctor.getId() != null) {
			if (doctor.getId() < 0)
				result.setStatus(1);
			mapResult.put("Id", "Cannot Send Id negative");
			result.setResultMap(mapResult);
			return result;
		}
		doctor.setPassword(doctor.getPassword());
		doctor.setDname(doctor.getDname());
		doctor.setSpecialty(doctor.getSpecialty());
		doctor.setPhoneNumber(doctor.getPhoneNumber());
		doctorrepo.save(doctor);

		result.setStatus(0);
		result.setResultMap(mapResult);
		return result;

	}

	public Result addOrUpdatedoctor(DoctorEntity doctor) {
		Result result = new Result();
		Map<String, Object> mapResult = new HashMap<>();
		
		
		doctorrepo.save(doctor);

		result.setStatus(0);
		result.setResultMap(mapResult);
		return result;

	}

	public Result updateDoctorSetUserNameWhereId(DoctorEntity doctor) {
		Result result = new Result();
		Map<String, Object> mapResult = new HashMap<>();
		if (doctor.getUserName() == null || doctor.getUserName().isEmpty()) {
			result.setStatus(1);
			mapResult.put("NameEntity", "Cannot Send Name Entity Empty");
			result.setResultMap(mapResult);
			return result;
		}
		if (doctor.getId() != null) {
			if (doctor.getId() < 0)
				result.setStatus(1);
			mapResult.put("Id", "Cannot Send Id negative");
			result.setResultMap(mapResult);
			return result;
		}
		doctorrepo.updateDoctorSetUserNameWhereId(doctor.getUserName(), doctor.getId());
		result.setStatus(0);
		result.setResultMap(mapResult);
		return result;

	}

	public Result updateDoctorSetPasswordWhereId(DoctorEntity doctor) {
		Result result = new Result();
		Map<String, Object> mapResult = new HashMap<>();
		if (doctor.getUserName() == null || doctor.getUserName().isEmpty()) {
			result.setStatus(1);
			mapResult.put("NameEntity", "Cannot Send Name Entity Empty");
			result.setResultMap(mapResult);
			return result;
		}
		if (doctor.getId() != null) {
			if (doctor.getId() < 0)
				result.setStatus(1);
			mapResult.put("Id", "Cannot Send Id negative");
			result.setResultMap(mapResult);
			return result;
		}
		doctorrepo.updateDoctorSetPasswordWhereId(doctor.getPassword(), doctor.getId());
		result.setStatus(0);
		result.setResultMap(mapResult);
		return result;

	}

	public Result updateDoctorSetNationalIdWhereId(DoctorEntity doctor) {
		Result result = new Result();
		Map<String, Object> mapResult = new HashMap<>();
		if (doctor.getUserName() == null || doctor.getUserName().isEmpty()) {
			result.setStatus(1);
			mapResult.put("doctor", "Cannot Send Name Entity Empty");
			result.setResultMap(mapResult);
			return result;
		}
		if (doctor.getId() != null) {
			if (doctor.getId() < 0)
				result.setStatus(1);
			mapResult.put("Id", "Cannot Send Id negative");
			result.setResultMap(mapResult);
			return result;
		}
		doctorrepo.updateDoctorSetNationalIdWhereId(doctor.getNationalID(), doctor.getId());
		result.setStatus(0);
		result.setResultMap(mapResult);
		return result;

	}

	public Result updateDoctorSetSpecialtyWhereId(DoctorEntity doctor) {
		Result result = new Result();
		Map<String, Object> mapResult = new HashMap<>();
		if (doctor.getUserName() == null || doctor.getUserName().isEmpty()) {
			result.setStatus(1);
			mapResult.put("doctor", "Cannot Send Name Entity Empty");
			result.setResultMap(mapResult);
			return result;
		}
		if (doctor.getId() != null) {
			if (doctor.getId() < 0)
				result.setStatus(1);
			mapResult.put("Id", "Cannot Send Id negative");
			result.setResultMap(mapResult);
			return result;
		}
		doctorrepo.updateDoctorSetSpecialtyWhereId(doctor.getSpecialty(), doctor.getId());
		result.setStatus(0);
		result.setResultMap(mapResult);
		return result;

	}

	public List<AppointmentEntity> findAllavalabli() {

		return appointment.allAvalabliHours();
	}

	public Result changeIsVisited(AppointmentEntity appointmentEntity) {

		Result result = new Result();
		if (appointmentEntity.isVisited()) {
			result.setStatus(1);
			result.setStatusDescription("The patient visit doctor.");
			return result;
		}
		appointment.updateAapointmentIsVisited(appointmentEntity.isVisited(),appointmentEntity.getId());
//	    
		result.setStatus(0);

		result.setStatusDescription("Appointment created successfully.");
		return result;
	}

	public List<AppointmentEntity> findBooked() {

		return appointment.allBookedHours();
	}

	public List<AppointmentEntity> patientVisitDoctor(int patientid, int doctorid) {

		return appointment.patientVisitdoctor(patientid, doctorid);
	}

	public Result updateDoctorSetPhoneNumberWhereId(DoctorEntity doctor) {
		Result result = new Result();
		Map<String, Object> mapResult = new HashMap<>();
		if (doctor.getUserName() == null || doctor.getUserName().isEmpty()) {
			result.setStatus(1);
			mapResult.put("doctor", "Cannot Send Name Entity Empty");
			result.setResultMap(mapResult);
			return result;
		}
		if (doctor.getId() != null) {
			if (doctor.getId() < 0)
				result.setStatus(1);
			mapResult.put("Id", "Cannot Send Id negative");
			result.setResultMap(mapResult);
			return result;
		}
		doctorrepo.updateDoctorSetPhoneNumberWhereId(doctor.getPhoneNumber(), doctor.getId());
		result.setStatus(0);
		result.setResultMap(mapResult);
		return result;
	}

	public List<DoctorEntity> findallDoctor() {
		return doctorrepo.findAll();

	}

	public String createCsvFile() {
		List<AppointmentEntity> alldoctor = appointment.findAll();
		File file = new File("\"C:\\\\Users\\\\talal\\\\eclipse-workspace\\\\.metadata\\\\.plugins\\\\org.eclipse.core.resources\\\\.projects\\\\jbasql\\\\reportcsv.csv");
		try (PrintWriter writer = new PrintWriter(file);) {
			writer.print("fromDate ,toDate,DoctorId");
			alldoctor.forEach(x -> {
				writer.println(x.getDate()+ "," + x.getTime() + "," + x.getDoctor());
			});
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return file.getAbsolutePath();
	}

}
