package com.hospital.management.service;

import java.time.LocalDate;
import java.util.List;

import com.hospital.management.dto.MedicalRecordRequestDTO;
import com.hospital.management.dto.MedicalRecordResponseDTO;

public interface MedicalRecordService {
	
	MedicalRecordResponseDTO createRecord(MedicalRecordRequestDTO dto);
	
	List<MedicalRecordResponseDTO> getAllRecords();
	
	MedicalRecordResponseDTO getRecordById(Long id);
	
	List<MedicalRecordResponseDTO> getRecordsByPatient(Long patientId);
	
	List<MedicalRecordResponseDTO> getRecordsByDate(LocalDate date);
	
	List<MedicalRecordResponseDTO> getRecordsByDoctor(Long doctorId);
	
	MedicalRecordResponseDTO updateRecord(Long id, MedicalRecordRequestDTO dto);
}