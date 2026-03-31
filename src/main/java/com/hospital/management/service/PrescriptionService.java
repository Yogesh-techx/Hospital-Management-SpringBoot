package com.hospital.management.service;

import java.util.List;

import com.hospital.management.dto.PrescriptionRequestDTO;
import com.hospital.management.dto.PrescriptionResponseDTO;

public interface PrescriptionService {
	
	PrescriptionResponseDTO createPrescription(PrescriptionRequestDTO dto);
	
	List<PrescriptionResponseDTO> getAllPrescriptions();
	
	PrescriptionResponseDTO getPrescriptionById(Long id);
	
	PrescriptionResponseDTO getPrescriptionByMedicalRecord(Long recordId);
	
	List<PrescriptionResponseDTO> getPrescriptionsByPatient(Long patientId);
}