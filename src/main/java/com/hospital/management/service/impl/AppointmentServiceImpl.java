package com.hospital.management.service.impl;

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
	
	// Book Appointment
	@Override
	public Appointment bookAppointment(Appointment appointment) {
		
		Long doctorId = appointment.getDoctor().getDoctorId();
		Long patientId = appointment.getPatient().getPatientId();
		LocalDateTime dateTime = appointment.getAppointmentDateTime();
		
		// Validate doctor exists
		Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + doctorId));
		
		// Validate patient exists
		Patient patient = patientRepository.findById(patientId).orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + patientId));
		
		// Doctor cannot have 2 appointments at same time
		if (appointmentRepository.existsByDoctor_DoctorIdAndAppointmentDateTime(doctorId, dateTime)) {
			throw new InvalidOperationException("Doctor already has an appointment at this time");
		}
		
		// Patient cannot have multiple appointments in same day
		LocalDate date = dateTime.toLocalDate();
		LocalDateTime startOfDay = date.atStartOfDay();
		LocalDateTime endOfDay = date.atTime(23, 59, 59);
		
		if (!appointmentRepository.findByPatient_PatientIdAndAppointmentDateTimeBetween(patientId, startOfDay, endOfDay).isEmpty()) {
			throw new InvalidOperationException("Patient already has an appointment on this day");
		}
		
		// Set validated entities
		appointment.setDoctor(doctor);
		appointment.setPatient(patient);
		
		// Default status
		appointment.setStatus(AppointmentStatus.BOOKED);
		
		return appointmentRepository.save(appointment);
	}
	
	// Get All Appointments
	@Override
	public List<Appointment> getAllAppointments() {
		return appointmentRepository.findAll();
	}
	
	// Get Appointment By ID
	@Override
	public Appointment getAppointmentById(Long id) {
		return appointmentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Appointment not found with id: " + id));
	}
	
	// Cancel Appointment
	@Override
	public Appointment cancelAppointment(Long id) {
		
		Appointment appointment = getAppointmentById(id);
		
		appointment.setStatus(AppointmentStatus.CANCELLED);
		
		return appointmentRepository.save(appointment);
	}
	
	// Update Appointment Status
	@Override
	public Appointment updateAppointmentStatus(Long id, AppointmentStatus status) {
		
		Appointment appointment = getAppointmentById(id);
		
		appointment.setStatus(status);
		
		return appointmentRepository.save(appointment);
	}
	
	// Get Appointments By Date
	@Override
	public List<Appointment> getAppointmentsByDate(LocalDate date) {
		LocalDateTime start = date.atStartOfDay();
		LocalDateTime end = date.atTime(23, 59, 59);
		
		return appointmentRepository.findByAppointmentDateTimeBetween(start, end);
	}
	
	// Get Appointments By Status
	@Override
	public List<Appointment> getAppointmentsByStatus(AppointmentStatus status) {
		return appointmentRepository.findByStatus(status);
	}
	
	// Get Appointments By Patient
	@Override
	public List<Appointment> getAppointmentsByPatient(Long patientId) {
		if (!patientRepository.existsById(patientId)) {
			throw new ResourceNotFoundException("Patient not found with id: " + patientId);
		}
		
		return appointmentRepository.findByPatient_PatientId(patientId);
	}
	
	// Get Appointments By Doctor
	@Override
	public List<Appointment> getAppointmentsByDoctor(Long doctorId) {
		
		if (!doctorRepository.existsById(doctorId)) {
			throw new ResourceNotFoundException("Doctor not found with id: " + doctorId);
		}
		
		return appointmentRepository.findByDoctor_DoctorId(doctorId);
	}
}