package com.hospital.management.service.impl;

import com.hospital.management.exception.DuplicateResourceException;
import com.hospital.management.exception.ResourceNotFoundException;
import com.hospital.management.model.Patient;
import com.hospital.management.repository.PatientRepository;
import com.hospital.management.service.PatientService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientServiceImpl implements PatientService {
	
	private final PatientRepository patientRepository;
	
	public PatientServiceImpl(PatientRepository patientRepository) {
		this.patientRepository = patientRepository;	
	}
	
	// Register Patient
	@Override
	public Patient registerPatient(Patient patient) {
		
		if (patientRepository.existsByContact(patient.getContact())) {
			throw new DuplicateResourceException("Patient already exists with contact: " + patient.getContact());	
		}
		
		if (patientRepository.existsByEmail(patient.getEmail())) {
			throw new DuplicateResourceException("Patient already exists with email: " + patient.getEmail());
		}
		
		return patientRepository.save(patient);
    }
	
	// Get All Patients
	@Override
	public List<Patient> getAllPatients() {
		return patientRepository.findAll();
	}
	
	// Get Patient By ID
	@Override
	public Patient getPatientById(Long id) {
		return patientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + id));
	}
	
	// Update Patient
	@Override
	public Patient updatePatient(Long id, Patient updatedPatient) {
		
		Patient existing = getPatientById(id);
		
		// Contact validation
		if (!existing.getContact().equals(updatedPatient.getContact()) && patientRepository.existsByContact(updatedPatient.getContact())) {
			throw new DuplicateResourceException("Contact already exists: " + updatedPatient.getContact());
		}
		
		// Email validation
		if (!existing.getEmail().equals(updatedPatient.getEmail()) && patientRepository.existsByEmail(updatedPatient.getEmail())) {
			throw new DuplicateResourceException("Email already exists: " + updatedPatient.getEmail());
		}
		
		existing.setPatientName(updatedPatient.getPatientName());
		existing.setAge(updatedPatient.getAge());
		existing.setGender(updatedPatient.getGender());
		existing.setContact(updatedPatient.getContact());
		existing.setEmail(updatedPatient.getEmail());
		
		return patientRepository.save(existing);
    }
	
	// Delete Patient
	@Override
	public void deletePatient(Long id) {
		Patient patient = getPatientById(id);
		patientRepository.delete(patient);
	}
	
	// Get Patient by Contact
	@Override
	public Patient getPatientByContact(String contact) {
		return patientRepository.findByContact(contact).orElseThrow(() -> new ResourceNotFoundException("Patient not found with contact: " + contact));
	}
	
	// Get Patients by Age > value
	@Override
	public List<Patient> getPatientsGreaterThanAge(int age) {
		return patientRepository.findByAgeGreaterThan(age);
	}
}