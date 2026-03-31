package com.hospital.management.service.impl;

import com.hospital.management.dto.AppointmentRequestDTO;
import com.hospital.management.dto.AppointmentResponseDTO;
import com.hospital.management.exception.InvalidOperationException;
import com.hospital.management.exception.ResourceNotFoundException;
import com.hospital.management.model.Appointment;
import com.hospital.management.model.Doctor;
import com.hospital.management.model.Patient;
import com.hospital.management.repository.AppointmentRepository;
import com.hospital.management.repository.DoctorRepository;
import com.hospital.management.repository.PatientRepository;
import com.hospital.management.service.AppointmentService;
import com.hospital.management.util.AppointmentStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentServiceImpl implements AppointmentService {
	
	private final AppointmentRepository appointmentRepository;
	private final DoctorRepository doctorRepository;
	private final PatientRepository patientRepository;
	
	public AppointmentServiceImpl(AppointmentRepository appointmentRepository, DoctorRepository doctorRepository, PatientRepository patientRepository) {
		this.appointmentRepository = appointmentRepository;
		this.doctorRepository = doctorRepository;
		this.patientRepository = patientRepository;
	}
	
	// Entity to DTO
	private AppointmentResponseDTO mapToDTO(Appointment appointment) {
		
		AppointmentResponseDTO dto = new AppointmentResponseDTO();
		dto.setAppointmentId(appointment.getAppointmentId());
		dto.setDoctorName(appointment.getDoctor().getDoctorName());
		dto.setPatientName(appointment.getPatient().getPatientName());
		dto.setAppointmentDateTime(appointment.getAppointmentDateTime().toString());
		dto.setStatus(appointment.getStatus().name());
		
		return dto;
	}
	
	// Book Appointment
	@Override
	public AppointmentResponseDTO bookAppointment(AppointmentRequestDTO dto) {
		
		// Fetch doctor
		Doctor doctor = doctorRepository.findById(dto.getDoctorId()).orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + dto.getDoctorId()));
		
		// Fetch patient
		Patient patient = patientRepository.findById(dto.getPatientId()).orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + dto.getPatientId()));
		
		// Convert String → LocalDateTime
		LocalDateTime dateTime = LocalDateTime.parse(dto.getAppointmentDateTime());
		
		// Doctor conflict
		if (appointmentRepository.existsByDoctor_DoctorIdAndAppointmentDateTime(doctor.getDoctorId(), dateTime)) {
			throw new InvalidOperationException("Doctor already has appointment at this time");
		}
		
		// Patient same-day restriction
		LocalDate date = dateTime.toLocalDate();
		
		if (!appointmentRepository.findByPatient_PatientIdAndAppointmentDateTimeBetween(patient.getPatientId(),date.atStartOfDay(),date.atTime(23, 59, 59)).isEmpty()) {
			throw new InvalidOperationException("Patient already has appointment on this day");
		}
		
		// Create entity
		Appointment appointment = new Appointment();
		appointment.setDoctor(doctor);
		appointment.setPatient(patient);
		appointment.setAppointmentDateTime(dateTime);
		appointment.setStatus(AppointmentStatus.BOOKED);
		
		return mapToDTO(appointmentRepository.save(appointment));
	}
	
	// Get All
	@Override
	public List<AppointmentResponseDTO> getAllAppointments() {
		return appointmentRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
	}
	
	// Get By ID
	@Override
	public AppointmentResponseDTO getAppointmentById(Long id) {
		
		Appointment appointment = appointmentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Appointment not found with id: " + id));
		
		return mapToDTO(appointment);
	}
	
	// Cancel
	@Override
	public AppointmentResponseDTO cancelAppointment(Long id) {
		
		Appointment appointment = appointmentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Appointment not found with id: " + id));
		
		appointment.setStatus(AppointmentStatus.CANCELLED);
		
		return mapToDTO(appointmentRepository.save(appointment));
	}
	
	// Update Status
	@Override
	public AppointmentResponseDTO updateAppointmentStatus(Long id, AppointmentStatus status) {
		
		Appointment appointment = appointmentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Appointment not found with id: " + id));
		
		appointment.setStatus(status);
		
		return mapToDTO(appointmentRepository.save(appointment));
	}
	
	// Get by Date
	@Override
	public List<AppointmentResponseDTO> getAppointmentsByDate(LocalDate date) {
		return appointmentRepository.findByAppointmentDateTimeBetween(date.atStartOfDay(),date.atTime(23, 59, 59)).stream().map(this::mapToDTO).collect(Collectors.toList());
	}
	
	// Get by Status
	@Override
	public List<AppointmentResponseDTO> getAppointmentsByStatus(AppointmentStatus status) {
		return appointmentRepository.findByStatus(status).stream().map(this::mapToDTO).collect(Collectors.toList());
	}
	
	// Get by Patient
	@Override
	public List<AppointmentResponseDTO> getAppointmentsByPatient(Long patientId) {
		
		if (!patientRepository.existsById(patientId)) {
			throw new ResourceNotFoundException("Patient not found");
		}
		
		return appointmentRepository.findByPatient_PatientId(patientId).stream().map(this::mapToDTO).collect(Collectors.toList());
	}
	
	// Get by Doctor
	@Override
	public List<AppointmentResponseDTO> getAppointmentsByDoctor(Long doctorId) {
		
		if (!doctorRepository.existsById(doctorId)) {
			throw new ResourceNotFoundException("Doctor not found");
		}
		
		return appointmentRepository.findByDoctor_DoctorId(doctorId).stream().map(this::mapToDTO).collect(Collectors.toList());
	}
}