package com.hospital.management.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PrescriptionRequestDTO {

	@NotNull(message = "Medical Record ID is required")
	private Long recordId;
	
	@NotBlank(message = "Medicine is required")
	private String medicine;
	
	@NotBlank(message = "Dosage Instructions are required")
	private String dosageInstructions;
	
	public Long getRecordId() {
		return recordId;
	}
	public void setRecordId(Long recordId) {
		this.recordId = recordId;
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
}
