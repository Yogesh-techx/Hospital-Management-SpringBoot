package com.hospital.management.repository;

import com.hospital.management.model.Doctor;
import com.hospital.management.util.AvailabilityDays;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
	// Get doctors by department ID
	List<Doctor> findByDepartment_DepartmentId(Long departmentId);
	
	// Get doctors by specialization
	List<Doctor> findBySpecialization(String specialization);
	
	// Get doctors by availability day
	List<Doctor> findByAvailabilityDaysContaining(AvailabilityDays day);
	
	// Get doctors by appointment ID
	List<Doctor> findByAppointments_AppointmentId(Long appointmentId);
	
	// Get doctors by patient ID (via MedicalRecord)
	List<Doctor> findByMedicalRecords_Patient_PatientId(Long patientId);
}