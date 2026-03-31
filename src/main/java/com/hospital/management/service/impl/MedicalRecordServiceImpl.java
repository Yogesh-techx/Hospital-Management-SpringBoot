package com.hospital.management.service.impl;

import com.hospital.management.dto.MedicalRecordRequestDTO;
import com.hospital.management.dto.MedicalRecordResponseDTO;
import com.hospital.management.exception.InvalidOperationException;
import com.hospital.management.exception.ResourceNotFoundException;
import com.hospital.management.model.Appointment;
import com.hospital.management.model.MedicalRecord;
import com.hospital.management.repository.AppointmentRepository;
import com.hospital.management.repository.MedicalRecordRepository;
import com.hospital.management.service.MedicalRecordService;
import com.hospital.management.util.AppointmentStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicalRecordServiceImpl implements MedicalRecordService {
	
	private final MedicalRecordRepository medicalRecordRepository;
	private final AppointmentRepository appointmentRepository;
	
	public MedicalRecordServiceImpl(MedicalRecordRepository medicalRecordRepository, AppointmentRepository appointmentRepository) {
		this.medicalRecordRepository = medicalRecordRepository;
		this.appointmentRepository = appointmentRepository;
	}
	
	// Entity to DTO
	private MedicalRecordResponseDTO mapToDTO(MedicalRecord record) {
		
		MedicalRecordResponseDTO dto = new MedicalRecordResponseDTO();
		dto.setRecordId(record.getRecordId());
		dto.setDiagnosis(record.getDiagnosis());
		dto.setTreatment(record.getTreatment());
		dto.setVisitingDate(record.getVisitingDate().toString());
		dto.setDoctorName(record.getDoctor().getDoctorName());
		dto.setPatientName(record.getPatient().getPatientName());
		
		return dto;
	}
	
	// Create Record
	@Override
	public MedicalRecordResponseDTO createRecord(MedicalRecordRequestDTO dto) {
		
		// Fetch appointment
		Appointment appointment = appointmentRepository.findById(dto.getAppointmentId()).orElseThrow(() -> new ResourceNotFoundException("Appointment not found with id: " + dto.getAppointmentId()));
		
		// Must be COMPLETED
		if (appointment.getStatus() != AppointmentStatus.COMPLETED) {
			throw new InvalidOperationException("Medical record can only be created for completed appointments");
		}
		
		// Create record (IMPORTANT: doctor & patient from appointment)
		MedicalRecord record = new MedicalRecord();
		record.setAppointment(appointment);
		record.setDoctor(appointment.getDoctor());
		record.setPatient(appointment.getPatient());
		record.setDiagnosis(dto.getDiagnosis());
		record.setTreatment(dto.getTreatment());
		record.setVisitingDate(LocalDate.now());
		
		return mapToDTO(medicalRecordRepository.save(record));
	}
	
	// Get All
	@Override
	public List<MedicalRecordResponseDTO> getAllRecords() {
		return medicalRecordRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }
	
	// Get By ID
	@Override
	public MedicalRecordResponseDTO getRecordById(Long id) {
		
		MedicalRecord record = medicalRecordRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Medical record not found with id: " + id));
		
		return mapToDTO(record);
	}
	
	// Get by Patient
	@Override
	public List<MedicalRecordResponseDTO> getRecordsByPatient(Long patientId) {
		return medicalRecordRepository.findByPatient_PatientId(patientId).stream().map(this::mapToDTO).collect(Collectors.toList());
	}
	
	// Get by Date
	@Override
	public List<MedicalRecordResponseDTO> getRecordsByDate(LocalDate date) {
		return medicalRecordRepository.findByVisitingDate(date).stream().map(this::mapToDTO).collect(Collectors.toList());
	}
	
	// Get by Doctor
	@Override
	public List<MedicalRecordResponseDTO> getRecordsByDoctor(Long doctorId) {
		return medicalRecordRepository.findByDoctor_DoctorId(doctorId).stream().map(this::mapToDTO).collect(Collectors.toList());
	}
	
	// Update
	@Override
	public MedicalRecordResponseDTO updateRecord(Long id, MedicalRecordRequestDTO dto) {
		
		MedicalRecord existing = medicalRecordRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Medical record not found with id: " + id));
		
		existing.setDiagnosis(dto.getDiagnosis());
		existing.setTreatment(dto.getTreatment());
		
		return mapToDTO(medicalRecordRepository.save(existing));
	}
}