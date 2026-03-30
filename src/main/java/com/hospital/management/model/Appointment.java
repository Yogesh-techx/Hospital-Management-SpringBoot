package com.hospital.management.model;

import com.hospital.management.util.AppointmentStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "appointment")
public class Appointment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long appointmentId;
	
	@Column(name = "appointment_datetime", nullable = false)
	private LocalDateTime appointmentDateTime;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private AppointmentStatus status;
	
	@ManyToOne
	@JoinColumn(name = "doctor_id", nullable = false)
	private Doctor doctor;
	
	@ManyToOne
	@JoinColumn(name = "patient_id", nullable = false)
	private Patient patient;
	
	public Appointment() {
    }
	
	public Appointment(Long appointmentId, LocalDateTime appointmentDateTime,
                       AppointmentStatus status, Doctor doctor, Patient patient) {
		this.appointmentId = appointmentId;
		this.appointmentDateTime = appointmentDateTime;
		this.status = status;
		this.doctor = doctor;
		this.patient = patient;
	}
	
	public Long getAppointmentId() {
		return appointmentId;
	}
	
	public void setAppointmentId(Long appointmentId) {
		this.appointmentId = appointmentId;
	}
	
	public LocalDateTime getAppointmentDateTime() {
		return appointmentDateTime;
	}
	
	public void setAppointmentDateTime(LocalDateTime appointmentDateTime) {
		this.appointmentDateTime = appointmentDateTime;
	}
	
	public AppointmentStatus getStatus() {
		return status;
	}
	
	public void setStatus(AppointmentStatus status) {
		this.status = status;
	}
	
	public Doctor getDoctor() {
		return doctor;
	}
	
	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}
	
	public Patient getPatient() {
		return patient;
	}
	
	public void setPatient(Patient patient) {
		this.patient = patient;
	}
}