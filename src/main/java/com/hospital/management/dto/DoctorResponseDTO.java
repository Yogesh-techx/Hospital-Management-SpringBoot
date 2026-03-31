package com.hospital.management.dto;

import java.util.Set;

import com.hospital.management.util.AvailabilityDays;

public class DoctorResponseDTO {

	private Long doctorId;
	private String doctorName;
	private String specialization;
	private String departmentName;
	private Set<AvailabilityDays> availabilityDays;
	public Long getDoctorId() {
		return doctorId;
	}
	public void setDoctorId(Long doctorId) {
		this.doctorId = doctorId;
	}
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
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public Set<AvailabilityDays> getAvailabilityDays() {
		return availabilityDays;
	}
	public void setAvailabilityDays(Set<AvailabilityDays> availabilityDays) {
		this.availabilityDays = availabilityDays;
	}
	
	
}
