package com.hospital.management.controller;

import com.hospital.management.dto.PatientRequestDTO;
import com.hospital.management.dto.PatientResponseDTO;
import com.hospital.management.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
public class PatientController {
	
	private final PatientService patientService;
	
	public PatientController(PatientService patientService) {
		this.patientService = patientService;
	}
	
	@PostMapping
	public PatientResponseDTO register(@Valid @RequestBody PatientRequestDTO dto) {
		return patientService.registerPatient(dto);
	}
	
	@GetMapping
	public List<PatientResponseDTO> getAll() {
		return patientService.getAllPatients();
	}
	
	@GetMapping("/{id}")
	public PatientResponseDTO getById(@PathVariable Long id) {
		return patientService.getPatientById(id);
	}
	
	@PutMapping("/{id}")
	public PatientResponseDTO update(@PathVariable Long id, @Valid @RequestBody PatientRequestDTO dto) {
		return patientService.updatePatient(id, dto);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		patientService.deletePatient(id);
	}
	
	@GetMapping("/contact/{contact}")
	public PatientResponseDTO getByContact(@PathVariable String contact) {
		return patientService.getPatientByContact(contact);
	}
	
	@GetMapping("/age/{age}")
	public List<PatientResponseDTO> getByAge(@PathVariable int age) {
		return patientService.getPatientsGreaterThanAge(age);
	}
}