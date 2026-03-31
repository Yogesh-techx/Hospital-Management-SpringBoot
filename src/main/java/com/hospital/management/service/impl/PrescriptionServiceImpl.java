package com.hospital.management.service.impl;

import com.hospital.management.dto.PrescriptionRequestDTO;
import com.hospital.management.dto.PrescriptionResponseDTO;
import com.hospital.management.exception.InvalidOperationException;
import com.hospital.management.exception.ResourceNotFoundException;
import com.hospital.management.model.MedicalRecord;
import com.hospital.management.model.Prescription;
import com.hospital.management.repository.MedicalRecordRepository;
import com.hospital.management.repository.PatientRepository;
import com.hospital.management.repository.PrescriptionRepository;
import com.hospital.management.service.PrescriptionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PrescriptionServiceImpl implements PrescriptionService {
	
	private final PrescriptionRepository prescriptionRepository;
	private final MedicalRecordRepository medicalRecordRepository;
	private final PatientRepository patientRepository;
	
	public PrescriptionServiceImpl(PrescriptionRepository prescriptionRepository, MedicalRecordRepository medicalRecordRepository, PatientRepository patientRepository) {
		this.prescriptionRepository = prescriptionRepository;
		this.medicalRecordRepository = medicalRecordRepository;
		this.patientRepository = patientRepository;
	}
	
	// Entity to DTO
	private PrescriptionResponseDTO mapToDTO(Prescription prescription) {
		
		PrescriptionResponseDTO dto = new PrescriptionResponseDTO();
		dto.setPrescriptionId(prescription.getPrescriptionId());
		dto.setMedicine(prescription.getMedicine());
		dto.setDosageInstructions(prescription.getDosageInstructions());
		dto.setPatientName(prescription.getMedicalRecord().getPatient().getPatientName());
		dto.setDoctorName(prescription.getMedicalRecord().getDoctor().getDoctorName());
		
		return dto;
	}
	
	// Create Prescription
	@Override
	public PrescriptionResponseDTO createPrescription(PrescriptionRequestDTO dto) {
		
		// Validate medical record exists
		MedicalRecord record = medicalRecordRepository.findById(dto.getRecordId()).orElseThrow(() -> new ResourceNotFoundException("Medical record not found with id: " + dto.getRecordId()));
		
		// No duplicate prescription
		if (prescriptionRepository.findByMedicalRecord_RecordId(dto.getRecordId()).isPresent()) {
			throw new InvalidOperationException("Prescription already exists for this medical record");
		}
		
		// Create entity
		Prescription prescription = new Prescription();
		prescription.setMedicalRecord(record);
		prescription.setMedicine(dto.getMedicine());
		prescription.setDosageInstructions(dto.getDosageInstructions());
		
		return mapToDTO(prescriptionRepository.save(prescription));
	}
	
	// Get All
	@Override
	public List<PrescriptionResponseDTO> getAllPrescriptions() {
		return prescriptionRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
	}
	
	// Get By ID
	@Override
	public PrescriptionResponseDTO getPrescriptionById(Long id) {
		
		Prescription prescription = prescriptionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Prescription not found with id: " + id));
		
		return mapToDTO(prescription);
	}
	
	// Get By Medical Record
	@Override
	public PrescriptionResponseDTO getPrescriptionByMedicalRecord(Long recordId) {
		
		Prescription prescription = prescriptionRepository.findByMedicalRecord_RecordId(recordId).orElseThrow(() -> new ResourceNotFoundException("Prescription not found for record id: " + recordId));
		
		return mapToDTO(prescription);
	}
	
	// Get By Patient
	@Override
	public List<PrescriptionResponseDTO> getPrescriptionsByPatient(Long patientId) {
		
		if (!patientRepository.existsById(patientId)) {
			throw new ResourceNotFoundException("Patient not found with id: " + patientId);
		}
		
		return prescriptionRepository.findByMedicalRecord_Patient_PatientId(patientId).stream().map(this::mapToDTO).collect(Collectors.toList());
	}
}