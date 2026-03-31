package com.hospital.management.dto;

import jakarta.validation.constraints.NotNull;

public class AppointmentRequestDTO {

	@NotNull(message = "Doctor ID is required")
	private Long doctorId;
	
	@NotNull(message = "Patient ID is required")
	private Long patientId;
	
	@NotNull(message = "Appointment date and time is required")
	private String appointmentDateTime;

	public Long getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(Long doctorId) {
		this.doctorId = doctorId;
	}

	public Long getPatientId() {
		return patientId;
	}

	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}

	public String getAppointmentDateTime() {
		return appointmentDateTime;
	}

	public void setAppointmentDateTime(String appointmentDateTime) {
		this.appointmentDateTime = appointmentDateTime;
	}
	
	
}
