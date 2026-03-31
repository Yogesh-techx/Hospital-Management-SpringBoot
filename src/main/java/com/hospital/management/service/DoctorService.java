package com.hospital.management.service;

import java.util.List;

import com.hospital.management.dto.DoctorRequestDTO;
import com.hospital.management.dto.DoctorResponseDTO;
import com.hospital.management.util.AvailabilityDays;

public interface DoctorService {
	
	DoctorResponseDTO saveDoctor(DoctorRequestDTO dto);
	
	List<DoctorResponseDTO> saveAllDoctors(List<DoctorRequestDTO> dtos);
	
	List<DoctorResponseDTO> getAllDoctors();
	
	DoctorResponseDTO getDoctorById(Long id);
	
	void deleteDoctor(Long id);
	
	List<DoctorResponseDTO> getDoctorsByDepartment(Long departmentId);
	
	List<DoctorResponseDTO> getDoctorsBySpecialization(String specialization);
	
	List<DoctorResponseDTO> getDoctorsByAppointment(Long appointmentId);
	
	List<DoctorResponseDTO> getDoctorsByPatient(Long patientId);
	
	List<DoctorResponseDTO> getDoctorsByAvailabilityDay(AvailabilityDays day);
}