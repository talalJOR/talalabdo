package com.dentist.pojo;

import java.util.Map;

import com.dentist.entity.AppointmentEntity;

import lombok.Data;

@Data
public class Result  {
	private Map<AppointmentEntity, Object> resultMap1;
	private int status;
	private String statusDescription;
	private Map<String, Object> resultMap;
}
