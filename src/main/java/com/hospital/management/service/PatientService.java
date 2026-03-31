package com.hospital.management.service;

import java.util.List;

import com.hospital.management.dto.PatientRequestDTO;
import com.hospital.management.dto.PatientResponseDTO;

public interface PatientService {
	
	PatientResponseDTO registerPatient(PatientRequestDTO dto);
	
	List<PatientResponseDTO> getAllPatients();
	
	PatientResponseDTO getPatientById(Long id);
	
	PatientResponseDTO updatePatient(Long id, PatientRequestDTO dto);
	
	void deletePatient(Long id);
	
	PatientResponseDTO getPatientByContact(String contact);
	
	List<PatientResponseDTO> getPatientsGreaterThanAge(int age);
}