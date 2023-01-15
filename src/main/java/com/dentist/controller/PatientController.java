package com.dentist.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dentist.entity.AppointmentEntity;
import com.dentist.entity.DoctorEntity;
import com.dentist.entity.PatientEntity;
import com.dentist.pojo.Result;
import com.dentist.service.PatientService;
import com.dentist.utility.TokenUtility;

@RestController
@RequestMapping("/patient")
public class PatientController {
	@Autowired
	PatientService service;
	@Autowired
	TokenUtility tokenUtility;

	@PostMapping("/addPatient")
	public Result addpatient(HttpServletRequest request, HttpServletResponse response,
			@RequestBody @Valid PatientEntity patient) {
		Result result = tokenUtility.checkToken(request.getHeader("token"));
		if (result.getStatus() == 0) {
			return service.addPatient(patient);
		} else {
			return result;
		}
	}
	

	@PostMapping("/createAppointment")
	public Result createAppointment(HttpServletRequest request, HttpServletResponse response,
			@RequestBody AppointmentEntity appEntity) {
		Result result = tokenUtility.checkToken(request.getHeader("token"));
		if (result.getStatus() == 0) {
			return service.createAppointment(appEntity);
		} else {
			return result;
		}
	}
	@PutMapping("/updatePatient")
	public Result updatePatient(HttpServletRequest request, HttpServletResponse response,
			@RequestBody PatientEntity patient, @RequestBody Integer patientId) {
		Result result = tokenUtility.checkToken(request.getHeader("token"));
		if (result.getStatus() == 0) {
			return service.UpdatePatientProfile(patient);
		} else {
			return result;
		}
	}

	@GetMapping(value = "/dawnloadrport", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public Object dawanloadrepo(HttpServletRequest request, HttpServletResponse response) {
		String filepathe = service.createCsvFile();
		InputStream input = null;
		try {
			input = new BufferedInputStream(new FileInputStream(new File(filepathe)));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		InputStreamResource resource = new InputStreamResource(input);

		return resource;
	}

	@DeleteMapping("/deletePatientAppoitment")
	public Result deletePatientAppoitment(HttpServletRequest request, HttpServletResponse response,
			@RequestBody AppointmentEntity appointmentpatient, @RequestBody Integer patientId) {
		Result result = tokenUtility.checkToken(request.getHeader("token"));
		if (result.getStatus() == 0) {
			return service.deleateAppointment(appointmentpatient);
		} else {
			return result;
		}
	}

	@GetMapping("/findalldoctor")
	public List<DoctorEntity> findalldoctors() {
		return service.findalldoctor();
	}

	@GetMapping("/findAllPatient")
	public List<PatientEntity> findAllPatient() {
		return service.findallPatient();
	}

	@PutMapping("/UpdatePatient")
	public Result UpdatePatient(HttpServletRequest request, HttpServletResponse response,
			@RequestBody PatientEntity patient) {
		Result result = tokenUtility.checkToken(request.getHeader("token"));
		if (result.getStatus() == 0) {
			return service.UpdatePatientProfile(patient);
		} else {
			return result;
		}
	}

	@DeleteMapping("/deleateAppointment")
	public Result deleateAppointmentn(HttpServletRequest request, HttpServletResponse response,
			@RequestBody AppointmentEntity appointment) {
		Result result = tokenUtility.checkToken(request.getHeader("token"));
		if (result.getStatus() == 0) {
			return service.deleateAppointment(appointment);
		} else {
			return result;
		}
	}

	@PutMapping("/UpdatePatientUsername")
	public Result UpdatePatientUsername(HttpServletRequest request, HttpServletResponse response,
			@RequestBody PatientEntity patient) {
		Result result = tokenUtility.checkToken(request.getHeader("token"));
		if (result.getStatus() == 0) {
			return service.updatePatientSetUserNameWhereId(patient);
		} else {
			return result;
		}
	}

}
