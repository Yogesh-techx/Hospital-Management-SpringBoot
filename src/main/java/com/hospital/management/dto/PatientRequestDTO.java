package com.hospital.management.dto;

import jakarta.validation.constraints.*;

public class PatientRequestDTO {

	@NotBlank(message = "Patient name is required")
	private String patientName;
	
	@Min(value = 0, message = "Age cannot be negative")
	private int age;
	
	@NotBlank(message = "Gender is required")
	private String gender;
	
	@Pattern(regexp = "^[0-9]{10}$", message = "Contact must be 10 digits")
	private String contact;
	
	@Email(message = "Invalid email format")
	private String email;
	
	public String getPatientName() {
		return patientName;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
