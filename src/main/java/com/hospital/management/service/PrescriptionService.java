package com.hospital.management.service;

import com.hospital.management.model.Prescription;

import java.util.List;

public interface PrescriptionService {
	
	Prescription createPrescription(Prescription prescription);
	
	List<Prescription> getAllPrescriptions();
	
	Prescription getPrescriptionById(Long id);
	
	Prescription getPrescriptionByMedicalRecord(Long recordId);
	
	List<Prescription> getPrescriptionsByPatient(Long patientId);
}