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
import com.dentist.repository.PatientRepo;
import com.dentist.utility.TokenUtility;

@Service
public class PatientService {
	@Autowired
	private PatientRepo patientrepo;
	@Autowired
	private Appointment appointmentrepo;
	@Autowired
	TokenUtility tokenUtility;

	private static Map<LocalDate, List<AppointmentEntity>> appointments = new HashMap<>();
	private static final LocalTime START_TIME = LocalTime.of(8, 0);
	private static final LocalTime END_TIME = LocalTime.of(17, 0);

	public Result login(Login login) {
		Result result = new Result();
		Map<String, Object> mapResult = new HashMap<>();
		PatientEntity patient = patientrepo.findPatientByUserName(login.username);
		if (patient == null) {
			result.setStatus(1);
			result.setStatusDescription("patient Not Found ");
			return result;
		}
		if (!patient.getPassword().equalsIgnoreCase(login.password)) {

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

	public Result addPatient(PatientEntity patient) {
		Result result = new Result();
		Map<String, Object> mapResult = new HashMap<>();
		if (patient.getUserName() == null || patient.getUserName().isEmpty()) {
			result.setStatus(1);
			mapResult.put("patient", "Cannot Send Name Entity Empty");
			result.setResultMap(mapResult);
			return result;
		}

		if (patient.getId() != null) {
			if (patient.getId() < 0)
				result.setStatus(1);
			mapResult.put("Id", "Cannot Send Id negative");
			result.setResultMap(mapResult);
			return result;
		}
		patientrepo.save(patient);
		result.setStatus(0);
		result.setResultMap(mapResult);
		return result;
	}

	public Result UpdatePatientProfile(PatientEntity patient) {
		Result result = new Result();
		Map<String, Object> mapResult = new HashMap<>();
		if (patient.getUserName() == null || patient.getUserName().isEmpty()) {
			result.setStatus(1);
			mapResult.put("patient", "Cannot Send Name Entity Empty");
			result.setResultMap(mapResult);
			return result;
		}
		if (patient.getId() != null) {
			if (patient.getId() < 0)
				result.setStatus(1);
			mapResult.put("Id", "Cannot Send Id negative");
			result.setResultMap(mapResult);
			return result;
		}
		patient.setPassword(patient.getPassword());
		patient.setName(patient.getName());
		patient.setGender(patient.getGender());
		patient.setPhoneNumber(patient.getPhoneNumber());
		patientrepo.save(patient);
		result.setStatus(0);

		result.setResultMap(mapResult);
		return result;

	}

	public Result updatePatientSetUserNameWhereId(PatientEntity patient) {
		Result result = new Result();
		Map<String, Object> mapResult = new HashMap<>();
		if (patient.getUserName() == null || patient.getUserName().isEmpty()) {
			result.setStatus(1);
			mapResult.put("patient", "Cannot Send Name Entity Empty");
			result.setResultMap(mapResult);
			return result;
		}
		if (patient.getId() != null) {
			if (patient.getId() < 0)
				result.setStatus(1);
			mapResult.put("Id", "Cannot Send Id negative");
			result.setResultMap(mapResult);
			return result;
		}
		patientrepo.updatePatientSetphoneNumberWhereId(patient.getPhoneNumber(), patient.getId());
		result.setStatus(0);
		result.setResultMap(mapResult);
		return result;

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
		appointmentrepo.delete(appointmentEntity);
		result.setStatus(0);
		result.setStatusDescription("Delete successful.");
		return result;
	}

	public String createCsvFile() {
		
		List<AppointmentEntity> allpatient = appointmentrepo.findAll();
		File file =new File("C:\\\\Users\\\\talal\\\\eclipse-workspace\\\\.metadata\\\\.plugins\\\\org.eclipse.core.resources\\\\.projects\\\\jbasql\\\\reportcsv.csv\"");
		try (PrintWriter writer = new PrintWriter(file);) {
			writer.print("fromDate ,toDate,DoctorId");
			allpatient.forEach(x -> {
				writer.println(x.getDate() + "," + x.getTime() + "," + x.getPatient().getId());
			});
			writer.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return file.getAbsolutePath();
	}

	public List<PatientEntity> findallPatient() {
		return patientrepo.findAll();

	}

	public List<DoctorEntity> findalldoctor() {
		return findalldoctor();

	}

	public static LocalTime getStartTime() {
		return START_TIME;
	}

	public static LocalTime getEndTime() {
		return END_TIME;
	}
}
