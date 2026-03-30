package com.hospital.management.model;

import jakarta.persistence.*;

@Entity
@Table(name = "prescription")
public class Prescription {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long prescriptionId;
	
	@Column(nullable = false)
	private String medicine;
	
	@Column(name = "dosage_instructions", nullable = false)
	private String dosageInstructions;
	
	@OneToOne
	@JoinColumn(name = "record_id", nullable = false, unique = true)
	private MedicalRecord medicalRecord;
	
	public Prescription() {
	}
	
	public Prescription(Long prescriptionId, String medicine, String dosageInstructions, MedicalRecord medicalRecord) {
		this.prescriptionId = prescriptionId;
		this.medicine = medicine;
		this.dosageInstructions = dosageInstructions;
		this.medicalRecord = medicalRecord;
	}
	
	public Long getPrescriptionId() {
		return prescriptionId;
	}
	
	public void setPrescriptionId(Long prescriptionId) {
		this.prescriptionId = prescriptionId;
	}
	
	public String getMedicine() {
		return medicine;
	}
	
	public void setMedicine(String medicine) {
		this.medicine = medicine;
	}
	
	public String getDosageInstructions() {
		return dosageInstructions;
	}
	
	public void setDosageInstructions(String dosageInstructions) {
		this.dosageInstructions = dosageInstructions;
	}
	
	public MedicalRecord getMedicalRecord() {
		return medicalRecord;
	}
	
	public void setMedicalRecord(MedicalRecord medicalRecord) {
		this.medicalRecord = medicalRecord;
	}
}