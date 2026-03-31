package com.hospital.management.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.management.dto.ApiResponse;
import com.hospital.management.dto.PrescriptionRequestDTO;
import com.hospital.management.dto.PrescriptionResponseDTO;
import com.hospital.management.service.PrescriptionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/prescriptions")
public class PrescriptionController {
	
	private final PrescriptionService prescriptionService;
	
	public PrescriptionController(PrescriptionService prescriptionService) {
		this.prescriptionService = prescriptionService;
	}
	
	@PostMapping
	public ResponseEntity<ApiResponse<PrescriptionResponseDTO>> create(@Valid @RequestBody PrescriptionRequestDTO dto) {
		return new ResponseEntity<>(new ApiResponse<>(HttpStatus.CREATED, prescriptionService.createPrescription(dto)), HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<ApiResponse<List<PrescriptionResponseDTO>>> getAll() {
		return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK, prescriptionService.getAllPrescriptions()));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<PrescriptionResponseDTO>> getById(@PathVariable Long id) {
		return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK, prescriptionService.getPrescriptionById(id)));
	}
	
	@GetMapping("/record/{recordId}")
	public ResponseEntity<ApiResponse<PrescriptionResponseDTO>> getByRecord(@PathVariable Long recordId) {
		return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK, prescriptionService.getPrescriptionByMedicalRecord(recordId)));
	}
	
	@GetMapping("/patient/{patientId}")
	public ResponseEntity<ApiResponse<List<PrescriptionResponseDTO>>> getByPatient(@PathVariable Long patientId) {
		return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK, prescriptionService.getPrescriptionsByPatient(patientId)));
	}
}