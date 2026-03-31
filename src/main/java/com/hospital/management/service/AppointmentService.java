package com.hospital.management.service;

import com.hospital.management.dto.AppointmentRequestDTO;
import com.hospital.management.dto.AppointmentResponseDTO;
import com.hospital.management.util.AppointmentStatus;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentService {
	
	AppointmentResponseDTO bookAppointment(AppointmentRequestDTO dto);
	
	List<AppointmentResponseDTO> getAllAppointments();
	
	AppointmentResponseDTO getAppointmentById(Long id);
	
	AppointmentResponseDTO cancelAppointment(Long id);
	
	AppointmentResponseDTO updateAppointmentStatus(Long id, AppointmentStatus status);
	
	List<AppointmentResponseDTO> getAppointmentsByDate(LocalDate date);
	
	List<AppointmentResponseDTO> getAppointmentsByStatus(AppointmentStatus status);
	
	List<AppointmentResponseDTO> getAppointmentsByPatient(Long patientId);
	
	List<AppointmentResponseDTO> getAppointmentsByDoctor(Long doctorId);
}