package com.hospital.management.repository;

import com.hospital.management.model.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {
	// Get medical records by patient
	List<MedicalRecord> findByPatient_PatientId(Long patientId);
	
	// Get medical records by visiting date
	List<MedicalRecord> findByVisitingDate(LocalDate visitingDate);
	
	// Get medical records by doctor
	List<MedicalRecord> findByDoctor_DoctorId(Long doctorId);
	
	// Checks if a medical record exists for the given appointment ID
	boolean existsByAppointment_AppointmentId(Long appointmentId);
}