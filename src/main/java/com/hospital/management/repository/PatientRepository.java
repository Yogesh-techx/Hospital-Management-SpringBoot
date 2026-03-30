package com.hospital.management.repository;

import com.hospital.management.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {
	
	// Get patient by contact number
	Optional<Patient> findByContact(String contact);
	
	// Get patients with age greater than given value
	List<Patient> findByAgeGreaterThan(int age);
	
	// Unique contact
	boolean existsByContact(String contact);
	
	// Unique email
	boolean existsByEmail(String email);
}