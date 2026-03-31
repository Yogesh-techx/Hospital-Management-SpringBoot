package com.hospital.management.service.impl;

import com.hospital.management.dto.PatientRequestDTO;
import com.hospital.management.dto.PatientResponseDTO;
import com.hospital.management.exception.DuplicateResourceException;
import com.hospital.management.exception.ResourceNotFoundException;
import com.hospital.management.model.Patient;
import com.hospital.management.repository.PatientRepository;
import com.hospital.management.service.PatientService;
import com.hospital.management.util.Gender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientServiceImpl implements PatientService {
	
	private final PatientRepository patientRepository;
	
	public PatientServiceImpl(PatientRepository patientRepository) {
		this.patientRepository = patientRepository;
	}
	
	// DTO to Entity
	private Patient mapToEntity(PatientRequestDTO dto) {
		Patient patient = new Patient();
		patient.setPatientName(dto.getPatientName());
		patient.setAge(dto.getAge());
		
		// Convert String to Enum
		patient.setGender(Gender.valueOf(dto.getGender().toUpperCase()));
		
		patient.setContact(dto.getContact());
		patient.setEmail(dto.getEmail());
		
		return patient;
	}
	
	// Entity to DTO
	private PatientResponseDTO mapToDTO(Patient patient) {
		
		PatientResponseDTO dto = new PatientResponseDTO();
		dto.setPatientId(patient.getPatientId());
		dto.setPatientName(patient.getPatientName());
		dto.setAge(patient.getAge());
		dto.setGender(patient.getGender().name());
		dto.setContact(patient.getContact());
		dto.setEmail(patient.getEmail());
		return dto;
	}
	
	// Register Patient
	@Override
	public PatientResponseDTO registerPatient(PatientRequestDTO dto) {
		
		if (patientRepository.existsByContact(dto.getContact())) {
			throw new DuplicateResourceException("Patient already exists with contact: " + dto.getContact());
		}
		
		if (patientRepository.existsByEmail(dto.getEmail())) {
			throw new DuplicateResourceException("Patient already exists with email: " + dto.getEmail());
		}
		
		Patient saved = patientRepository.save(mapToEntity(dto));
		return mapToDTO(saved);
	}
	
	// Get All
	@Override
	public List<PatientResponseDTO> getAllPatients() {
		return patientRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
	}
	
	// Get By ID
	@Override
	public PatientResponseDTO getPatientById(Long id) {
		
		Patient patient = patientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + id));
		
		return mapToDTO(patient);
	}
	
	// Update Patient
	@Override
	public PatientResponseDTO updatePatient(Long id, PatientRequestDTO dto) {
		
		Patient existing = patientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + id));
		
		// Contact validation
		if (!existing.getContact().equals(dto.getContact()) && patientRepository.existsByContact(dto.getContact())) {
			throw new DuplicateResourceException("Contact already exists: " + dto.getContact());
		}
		
		// Email validation
		if (!existing.getEmail().equals(dto.getEmail()) && patientRepository.existsByEmail(dto.getEmail())) {
			throw new DuplicateResourceException("Email already exists: " + dto.getEmail());
		}
		
		existing.setPatientName(dto.getPatientName());
		existing.setAge(dto.getAge());
		existing.setGender(Gender.valueOf(dto.getGender().toUpperCase()));
		existing.setContact(dto.getContact());
		existing.setEmail(dto.getEmail());
		
		return mapToDTO(patientRepository.save(existing));
	}
	
	// Delete
	@Override
	public void deletePatient(Long id) {
		Patient patient = patientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + id));
		
		patientRepository.delete(patient);
	}
	
	// Get by Contact
	@Override
	public PatientResponseDTO getPatientByContact(String contact) {
		
		Patient patient = patientRepository.findByContact(contact).orElseThrow(() -> new ResourceNotFoundException("Patient not found with contact: " + contact));
		
		return mapToDTO(patient);
	}
	
	// Get by Age
	@Override
	public List<PatientResponseDTO> getPatientsGreaterThanAge(int age) {
		return patientRepository.findByAgeGreaterThan(age).stream().map(this::mapToDTO).collect(Collectors.toList());
	}
}