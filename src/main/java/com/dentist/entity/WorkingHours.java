package com.dentist.entity;

public enum WorkingHours {
	first_HOUR("8 "), SEOONED_HOURS(" 9"), THIRD_HOURS(" 10"), FOURTH_HOUR("11"), fiFTH_HOURS("12"), SIXTH_HOURS("13"),
	SEVENTH_HOUR("14"), EGHTTH_HOURS("15"), NINTH_HOURS("16");

	private final String value;

	WorkingHours(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
