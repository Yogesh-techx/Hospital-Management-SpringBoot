package com.hospital.management.model;

import com.hospital.management.util.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(
		name = "patient",
		uniqueConstraints = {
			@UniqueConstraint(columnNames = "contact"),
			@UniqueConstraint(columnNames = "email")
		}
)
public class Patient {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long patientId;
	
	@Column(name = "patient_name", nullable = false)
	@NotBlank(message = "Patient name is required")
	private String patientName;
	
	@Column(nullable = false)
	@Min(value = 0, message = "Age cannot be negative")
	private int age;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Gender gender;
	
	@Column(nullable = false, unique = true)
	@Pattern(regexp = "^[0-9]{10}$", message = "Contact must be 10 digits")
	private String contact;
	
	@Column(nullable = false, unique = true)
	@Email(message = "Invalid email format")
	private String email;
	
	public Patient() {
    }
	
	public Patient(Long patientId, String patientName, int age, Gender gender, String contact, String email) {
		this.patientId = patientId;
		this.patientName = patientName;
		this.age = age;
		this.gender = gender;
		this.contact = contact;
		this.email = email;
	}
	
	public Long getPatientId() {
		return patientId;
	}
	
	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}
	
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
	
	public Gender getGender() {
		return gender;
	}
	
	public void setGender(Gender gender) {
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