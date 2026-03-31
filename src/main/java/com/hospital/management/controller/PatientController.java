package com.hospital.management.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.management.dto.ApiResponse;
import com.hospital.management.dto.PatientRequestDTO;
import com.hospital.management.dto.PatientResponseDTO;
import com.hospital.management.service.PatientService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/patients")
public class PatientController {
	
	private final PatientService patientService;
	
	public PatientController(PatientService patientService) {
		this.patientService = patientService;
	}
	
	@PostMapping
	public ResponseEntity<ApiResponse<PatientResponseDTO>> register(@Valid @RequestBody PatientRequestDTO dto) {
		return new ResponseEntity<>(new ApiResponse<>(HttpStatus.CREATED, patientService.registerPatient(dto)), HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<ApiResponse<List<PatientResponseDTO>>> getAll() {
		return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK, patientService.getAllPatients()));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<PatientResponseDTO>> getById(@PathVariable Long id) {
		return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK, patientService.getPatientById(id)));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<PatientResponseDTO>> update(@PathVariable Long id, @Valid @RequestBody PatientRequestDTO dto) {
		return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK, patientService.updatePatient(id, dto)));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<String>> delete(@PathVariable Long id) {
		patientService.deletePatient(id);
		return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK, "Patient deleted successfully"));
	}
	
	@GetMapping("/contact/{contact}")
	public ResponseEntity<ApiResponse<PatientResponseDTO>> getByContact(@PathVariable String contact) {
		return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK, patientService.getPatientByContact(contact)));
    }
	
	@GetMapping("/age/{age}")
	public ResponseEntity<ApiResponse<List<PatientResponseDTO>>> getByAge(@PathVariable int age) {
		return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK, patientService.getPatientsGreaterThanAge(age)));
	}
}