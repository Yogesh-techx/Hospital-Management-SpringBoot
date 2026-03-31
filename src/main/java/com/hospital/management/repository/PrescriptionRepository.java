package com.hospital.management.repository;

import com.hospital.management.model.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
	
	// Get prescription by Medical Record
	Optional<Prescription> findByMedicalRecord_RecordId(Long recordId);
	
	// Get prescriptions by Patient (via MedicalRecord)
	List<Prescription> findByMedicalRecord_Patient_PatientId(Long patientId);
	
	// Checks if a prescription exists for the given medical record ID
	boolean existsByMedicalRecord_RecordId(Long recordId);
}