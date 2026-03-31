package com.hospital.management.dto;

import java.util.Set;

import com.hospital.management.util.AvailabilityDays;

public class DoctorRequestDTO {
	
	public String getDoctorName() {
		return doctorName;
	}
	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}
	public String getSpecialization() {
		return specialization;
	}
	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}
	public Long getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}
	public Set<AvailabilityDays> getAvailabilityDays() {
		return availabilityDays;
	}
	public void setAvailabilityDays(Set<AvailabilityDays> availabilityDays) {
		this.availabilityDays = availabilityDays;
	}
	private String doctorName;
	private String specialization;
	private Long departmentId;
	private Set<AvailabilityDays> availabilityDays;
}
