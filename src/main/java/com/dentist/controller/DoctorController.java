package com.dentist.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.dentist.entity.AppointmentEntity;
import com.dentist.entity.DoctorEntity;
import com.dentist.pojo.Login;
import com.dentist.pojo.Result;
import com.dentist.service.DoctorService;
import com.dentist.utility.TokenUtility;

@RestController
@RequestMapping("/Doctor")
public class DoctorController {
	@Autowired
	private DoctorService service;
	@Autowired
	TokenUtility tolenUtility;

	@PostMapping("/login")
	public Result login(@RequestBody @Valid Login login) {

		return service.login(login);
	}

	@PostMapping("/Register")
	public Result addDoctor(@RequestBody DoctorEntity doctor) {

		return service.addOrUpdatedoctor(doctor);
	}

	@DeleteMapping("/deleteAppointment")
	public Result deleteAppointment(HttpServletRequest request, HttpServletResponse response,
			@Valid @RequestBody AppointmentEntity appointment) {
		Result result = tolenUtility.checkToken(request.getHeader("token"));
		if (result.getStatus() == 0) {
			return service.deleateAppointment(appointment);
		} else {
			return result;
		}

	}

	@PutMapping("/updateDoctor")
	public Result updateDoctor(HttpServletRequest request, HttpServletResponse response,
			@RequestBody DoctorEntity doctor) {
		Result result = tolenUtility.checkToken(request.getHeader("token"));
		if (result.getStatus() == 0) {
			return service.UpdateDoctorProfile(doctor);
		} else {
			return result;
		}
	}

	@PutMapping("/updateDoctorNationalId")
	public Result updateDoctorNationalId(HttpServletRequest request, HttpServletResponse response,
			@RequestBody DoctorEntity doctor) {
		Result result = tolenUtility.checkToken(request.getHeader("token"));
		if (result.getStatus() == 0) {
			return service.updateDoctorSetNationalIdWhereId(doctor);
		} else {
			return result;
		}
	}

	@PutMapping("/updateDoctorPhoneNumber")
	public Result updateDoctorPhoneNumber(HttpServletRequest request, HttpServletResponse response,
			@RequestBody DoctorEntity doctor) {
		Result result = tolenUtility.checkToken(request.getHeader("token"));
		if (result.getStatus() == 0) {
			return service.updateDoctorSetPhoneNumberWhereId(doctor);
		} else {
			return result;
		}
	}

	@PutMapping("/changeIsVisited")
	public Result DoctorchangeIsVisited(HttpServletRequest request, HttpServletResponse response,
			@RequestBody AppointmentEntity appointment) {
		Result result = tolenUtility.checkToken(request.getHeader("token"));
		if (result.getStatus() == 0) {
			return service.changeIsVisited(appointment);
		} else {
			return result;
		}
	}

	@PutMapping("/findAllavalabliTimeDocto")
	public List<AppointmentEntity> AppointmentEntity(HttpServletRequest request, HttpServletResponse response,
			@RequestBody AppointmentEntity appointment) {
		Result result = tolenUtility.checkToken(request.getHeader("token"));
		if (result.getStatus() == 0) {
			return service.findAllavalabli();
		} else {
			return null;
		}
	}

	@GetMapping("/patientVisitDoctor")
	public List<AppointmentEntity> patientVisitDocto(HttpServletRequest request, HttpServletResponse response,
			@RequestBody AppointmentEntity appointment) {
		Result result = tolenUtility.checkToken(request.getHeader("token"));
		if (result.getStatus() == 0) {
			return service.patientVisitDoctor(appointment.getPatient().getId(), appointment.getDoctor().getId());
		} else {
			return null;
		}
	}

	@GetMapping("/allappAppointmentEntities")
	public List<AppointmentEntity> allappAppointmentEntities(HttpServletRequest request, HttpServletResponse response,
			@RequestBody AppointmentEntity appointment) {
		Result result = tolenUtility.checkToken(request.getHeader("token"));
		if (result.getStatus() == 0) {
			return service.findallappAppointmentEntities();
		} else {
			return null;
		}
	}

	@GetMapping("/findPateintById")
	public Result findPateintById(HttpServletRequest request, HttpServletResponse response, @RequestBody int id) {
		Result result = tolenUtility.checkToken(request.getHeader("token"));
		if (result.getStatus() == 0) {
			return service.findPateintById(id);
		} else {
			return result;
		}
	}

	@GetMapping("/findBooked")
	public List<AppointmentEntity> findBook(HttpServletRequest request, HttpServletResponse response,
			@RequestBody int id) {
		Result result = tolenUtility.checkToken(request.getHeader("token"));
		if (result.getStatus() == 0) {
			return service.findBooked();
		} else {
			return null;
		}
	}


	
	@PutMapping("/updateDoctorPassword")
	public Result updateDoctorPasswordr(HttpServletRequest request, HttpServletResponse response,
			@RequestBody DoctorEntity doctor) {
		Result result = tolenUtility.checkToken(request.getHeader("token"));
		if (result.getStatus() == 0) {
			return service.updateDoctorSetPasswordWhereId(doctor);
		} else {
			return result;
		}
	}
	

	@PostMapping("/createAppointment")
	public Result createAppointment(HttpServletRequest request, HttpServletResponse response,
			@RequestBody AppointmentEntity appEntity) {
		Result result = tolenUtility.checkToken(request.getHeader("token"));
		if (result.getStatus() == 0) {
			return service.createAppointment(appEntity);
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

	@GetMapping("/allAppointment")
	public List<AppointmentEntity> allAppointment(HttpServletRequest request, HttpServletResponse response) {
		Result result = tolenUtility.checkToken(request.getHeader("token"));
		if (result.getStatus() == 0) {
			return service.findallappAppointmentEntities();
		} else {
			return null;
		}
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Result handleValidationExceptions(MethodArgumentNotValidException ex) {
		Result result = new Result();
		result.setStatus(1);

		Map<String, Object> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		result.setResultMap(errors);
		return result;

	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public Result handleAllExceptionMethod(Exception ex, WebRequest requset, HttpServletResponse res) {
		Result result = new Result();
		result.setStatus(1);

		Map<String, Object> errors = new HashMap<>();
		errors.put("Exception", ex.getCause());
		result.setResultMap(errors);
		return result;
	}

}
