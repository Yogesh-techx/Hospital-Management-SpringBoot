package com.hospital.management.repository;

import com.hospital.management.model.Appointment;
import com.hospital.management.util.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
	
	// Get appointments by date range (for a specific day)
	List<Appointment> findByAppointmentDateTimeBetween(LocalDateTime start, LocalDateTime end);
	
	// Get appointments by status
	List<Appointment> findByStatus(AppointmentStatus status);
	
	// Get appointments by patient
	List<Appointment> findByPatient_PatientId(Long patientId);
	
	// Get appointments by doctor
	List<Appointment> findByDoctor_DoctorId(Long doctorId);
	
	// Check if doctor already has appointment at same time
	boolean existsByDoctor_DoctorIdAndAppointmentDateTime(Long doctorId, LocalDateTime dateTime);
	
	// Check if patient already has appointment on same day
	List<Appointment> findByPatient_PatientIdAndAppointmentDateTimeBetween(Long patientId, LocalDateTime start, LocalDateTime end);
}