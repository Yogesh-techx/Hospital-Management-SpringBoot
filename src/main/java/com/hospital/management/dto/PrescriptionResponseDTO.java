package com.hospital.management.dto;

public class PrescriptionResponseDTO {

	private Long prescriptionId;
	private String medicine;
	private String dosageInstructions;
	private String patientName;
	private String doctorName;
	
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
	public String getPatientName() {
		return patientName;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	public String getDoctorName() {
		return doctorName;
	}
	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}
	
	
}
