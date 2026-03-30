package com.hospital.management.service;

import com.hospital.management.model.Doctor;
import com.hospital.management.util.AvailabilityDays;

import java.util.List;

public interface DoctorService {
	
	Doctor saveDoctor(Doctor doctor);
	
	List<Doctor> saveAllDoctors(List<Doctor> doctors);
	
	List<Doctor> getAllDoctors();
	
	Doctor getDoctorById(Long id);
	
	void deleteDoctor(Long id);
	
	List<Doctor> getDoctorsByDepartment(Long departmentId);
	
	List<Doctor> getDoctorsBySpecialization(String specialization);
	
	List<Doctor> getDoctorsByAppointment(Long appointmentId);
	
	List<Doctor> getDoctorsByPatient(Long patientId);
	
	List<Doctor> getDoctorsByAvailabilityDay(AvailabilityDays day);
}