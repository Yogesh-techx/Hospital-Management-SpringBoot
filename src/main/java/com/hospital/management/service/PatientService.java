package com.hospital.management.service;

import com.hospital.management.model.Patient;

import java.util.List;

public interface PatientService {
	
	Patient registerPatient(Patient patient);
	
	List<Patient> getAllPatients();
	
	Patient getPatientById(Long id);
	
	Patient updatePatient(Long id, Patient patient);
	
	void deletePatient(Long id);
	
	Patient getPatientByContact(String contact);
	
	List<Patient> getPatientsGreaterThanAge(int age);
}