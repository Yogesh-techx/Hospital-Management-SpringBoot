package com.hospital.management.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "medical_record")
public class MedicalRecord {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long recordId;
	
	@Column(nullable = false)
	private String diagnosis;

	@Column(nullable = false)
	private String treatment;
	
	@Column(name = "visiting_date", nullable = false)
	private LocalDate visitingDate;
	
	@ManyToOne
	@JoinColumn(name = "patient_id", nullable = false)
	private Patient patient;
	
	@ManyToOne
	@JoinColumn(name = "doctor_id", nullable = false)
	private Doctor doctor;
	
	@OneToOne
	@JoinColumn(name = "appointment_id", nullable = false, unique = true)
	private Appointment appointment;
	
	public MedicalRecord() {
	}
	
	public MedicalRecord(Long recordId, String diagnosis, String treatment, LocalDate visitingDate, Patient patient, Doctor doctor, Appointment appointment) {
		this.recordId = recordId;
		this.diagnosis = diagnosis;
		this.treatment = treatment;
		this.visitingDate = visitingDate;
		this.patient = patient;
		this.doctor = doctor;
		this.appointment = appointment;
	}
	
	public Long getRecordId() {
		return recordId;
	}
	
	public void setRecordId(Long recordId) {
		this.recordId = recordId;
	}
	
	public String getDiagnosis() {
		return diagnosis;
	}
	
	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}
	
	public String getTreatment() {
		return treatment;
	}
	
	public void setTreatment(String treatment) {
		this.treatment = treatment;
	}
	
	public LocalDate getVisitingDate() {
		return visitingDate;
	}
	
	public void setVisitingDate(LocalDate visitingDate) {
		this.visitingDate = visitingDate;
	}
	
	public Patient getPatient() {
		return patient;
	}
	
	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	
	public Doctor getDoctor() {
		return doctor;
	}
	
	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}
	
	public Appointment getAppointment() {
		return appointment;
	}
	
	public void setAppointment(Appointment appointment) {
		this.appointment = appointment;
	}
}