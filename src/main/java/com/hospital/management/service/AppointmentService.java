package com.hospital.management.service;

import com.hospital.management.model.Appointment;
import com.hospital.management.util.AppointmentStatus;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentService {
	
	Appointment bookAppointment(Appointment appointment);
	
	List<Appointment> getAllAppointments();
	
	Appointment getAppointmentById(Long id);
	
	Appointment cancelAppointment(Long id);
	
	Appointment updateAppointmentStatus(Long id, AppointmentStatus status);
	
	List<Appointment> getAppointmentsByDate(LocalDate date);
	
	List<Appointment> getAppointmentsByStatus(AppointmentStatus status);
	
	List<Appointment> getAppointmentsByPatient(Long patientId);
	
	List<Appointment> getAppointmentsByDoctor(Long doctorId);
}