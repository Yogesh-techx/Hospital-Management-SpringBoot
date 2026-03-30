package com.hospital.management.service.impl;

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
	
	// Create Prescription
	@Override
	public Prescription createPrescription(Prescription prescription) {
		
		Long recordId = prescription.getMedicalRecord().getRecordId();
		
		// Validate medical record exists
		MedicalRecord record = medicalRecordRepository.findById(recordId).orElseThrow(() -> new ResourceNotFoundException("Medical record not found with id: " + recordId));
		
		// Ensure prescription doesn't already exist
		if (prescriptionRepository.findByMedicalRecord_RecordId(recordId).isPresent()) {
			throw new InvalidOperationException("Prescription already exists for this medical record");
		}
		
		// Set proper relationship
		prescription.setMedicalRecord(record);
		
		return prescriptionRepository.save(prescription);
	}
	
	// Get All Prescriptions
	@Override
	public List<Prescription> getAllPrescriptions() {
		return prescriptionRepository.findAll();
	}
	
	// Get Prescription By ID
	@Override
	public Prescription getPrescriptionById(Long id) {
		return prescriptionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Prescription not found with id: " + id));
	}
	
	// Get Prescription By Medical Record
	@Override
	public Prescription getPrescriptionByMedicalRecord(Long recordId) {
		return prescriptionRepository.findByMedicalRecord_RecordId(recordId).orElseThrow(() -> new ResourceNotFoundException("Prescription not found for medical record id: " + recordId));
	}
	
	// Get Prescriptions By Patient
	@Override
	public List<Prescription> getPrescriptionsByPatient(Long patientId) {
		
		if (!patientRepository.existsById(patientId)) {
			throw new ResourceNotFoundException("Patient not found with id: " + patientId);
		}
		
		return prescriptionRepository.findByMedicalRecord_Patient_PatientId(patientId);
	}
}